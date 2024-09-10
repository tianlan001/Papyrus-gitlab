/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.canonical.tests;

import static org.eclipse.papyrus.junit.framework.runner.ScenarioRunner.verificationPoint;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.ByteArrayInputStream;

import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.papyrus.infra.gmfdiag.canonical.tests.AbstractCSSCanonicalTest.CSSResource;
import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.runner.RunWith;

import com.google.common.base.Charsets;

/**
 * Tests the manipulation of canonical state by non-transactional operations and that undo/redo
 * maintain sanity of the diagrams.
 */
@PluginResource("models/classdiagram_cssext.di")
@CSSResource("models/classdiagram.css")
@ActiveDiagram("default")
@RunWith(ScenarioRunner.class)
public class CSSExternalStylesheetInClassDiagramTest extends AbstractCSSCanonicalTest {

	private org.eclipse.uml2.uml.Class foo;
	private Property foo_ok;
	private Operation foo_doit;
	private org.eclipse.uml2.uml.Class foo_nested;
	private Enumeration yesno;
	private EnumerationLiteral yesno_no;
	private EnumerationLiteral yesno_yes;

	public CSSExternalStylesheetInClassDiagramTest() {
		super();
	}

	@NeedsUIEvents
	@Scenario({ "initial", "non-canonical", "canonical", "undo", "redo" })
	public void editStylesheetRefresh() {
		// The classes are canonical
		if (verificationPoint()) {
			requireViews(foo_ok, foo_doit, foo_nested);
		}

		// Add an enumeration literal
		EnumerationLiteral yesno_maybe = UMLFactory.eINSTANCE.createEnumerationLiteral();
		yesno_maybe.setName("maybe");
		add(yesno, yesno_maybe, UMLPackage.Literals.ENUMERATION__OWNED_LITERAL);

		// The enumeration is not yet canonically synchronized
		if (verificationPoint()) {
			assertNoViews(yesno_no, yesno_yes, yesno_maybe);
		}

		addEnumerationRule();
		refreshDiagram();

		// The enumeration is now canonical after the non-transactional refresh
		if (verificationPoint()) {
			requireViews(yesno_no, yesno_yes, yesno_maybe);
		}

		// Revert the stylesheet resource and refresh
		revertStylesheet();
		refreshDiagram();

		// Undo creation of the new literal
		undo();

		if (verificationPoint()) {
			assertDetached(yesno_maybe);

			// No view any longer
			assertNoView(yesno_maybe);

			// And only two enumeration literal views in the compartment
			assertThat(getEnumerationLiteralCompartment(requireEditPart(yesno)).getChildren().size(), is(2));
		}

		redo();

		if (verificationPoint()) {
			assertAttached(yesno_maybe);

			// The view does not come back because the enumeration is not canonical
			assertNoView(yesno_maybe);

			// And only two enumeration literal views in the compartment
			assertThat(getEnumerationLiteralCompartment(requireEditPart(yesno)).getChildren().size(), is(2));
		}
	}

	/**
	 * Verify that correctly handling of undo creation of semantic element that was presented canonically
	 * after the canonical edit policy is deactivated doesn't depend on the same edit part managing the
	 * view as before.
	 */
	@Scenario({ "initial", "close-reopen", "undo" })
	public void editStylesheetRefresh_closeReopenDiagram_undo() {
		// Add an enumeration literal
		EnumerationLiteral yesno_maybe = UMLFactory.eINSTANCE.createEnumerationLiteral();
		yesno_maybe.setName("maybe");
		add(yesno, yesno_maybe, UMLPackage.Literals.ENUMERATION__OWNED_LITERAL);

		addEnumerationRule();
		refreshDiagram();

		if (verificationPoint()) {
			assertThat(yesno_maybe, notNullValue());
		}

		// Revert the stylesheet resource and refresh
		revertStylesheet();
		refreshDiagram();

		// Close the diagram and re-open it
		String diagramName = editor.closeDiagram();
		editor.openDiagram(diagramName);

		if (verificationPoint()) {
			// The view is still there, of course, but it would have a new edit part
			requireEditPart(yesno_maybe);
		}

		// Undo creation of the new literal
		undo();

		if (verificationPoint()) {
			assertDetached(yesno_maybe);

			// No view any longer
			assertNoView(yesno_maybe);

			// And only two enumeration literal views in the compartment
			assertThat(getEnumerationLiteralCompartment(requireEditPart(yesno)).getChildren().size(), is(2));
		}
	}

	//
	// Test framework
	//

	@Before
	public void getModelElements() {
		org.eclipse.uml2.uml.Package root = editor.getModel();
		foo = (org.eclipse.uml2.uml.Class) root.getOwnedType("Foo");
		foo_ok = foo.getOwnedAttribute("ok", null);
		foo_doit = foo.getOwnedOperation("doIt", null, null);
		foo_nested = (org.eclipse.uml2.uml.Class) foo.getNestedClassifier("Nested");
		yesno = (Enumeration) root.getOwnedType("YesNo");
		yesno_no = yesno.getOwnedLiteral("no");
		yesno_yes = yesno.getOwnedLiteral("yes");
	}

	protected void addEnumerationRule() {
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(String.format("%nEnumeration {%n canonical: true;%n}%n").getBytes(Charsets.UTF_8));
			cssFile.appendContents(bais, false, true, null);
		} catch (Exception e) {
			throw new WrappedException(e);
		}
	}

	protected void revertStylesheet() {
		try {
			cssFile.setContents(cssFile.getHistory(null)[0], false, true, null);
		} catch (Exception e) {
			throw new WrappedException(e);
		}
	}
}
