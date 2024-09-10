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

import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.ElementImport;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Usage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests the manipulation of canonical state in various views by CSS in a class diagram
 * that has not persisted the canonical style on anything.
 */
@PluginResource("models/classdiagram_css.di")
@ActiveDiagram("default")
@RunWith(ScenarioRunner.class)
public class CSSCanonicalStateInClassDiagramTest extends AbstractCSSCanonicalTest {

	private org.eclipse.uml2.uml.Class foo;
	private Property foo_ok;
	private Operation foo_doit;
	private org.eclipse.uml2.uml.Class foo_nested;
	private org.eclipse.uml2.uml.Class bar;
	private org.eclipse.uml2.uml.Class super_;
	private Enumeration yesno;
	private EnumerationLiteral yesno_no;
	private EnumerationLiteral yesno_yes;

	private Association foo_bar;
	private Generalization bar_super;
	private Usage super_yesno;

	private org.eclipse.uml2.uml.Package types;
	private org.eclipse.uml2.uml.Class types_subfoo;
	private Property types_subfoo_createdon;
	private DataType types_date;

	private Generalization types_subfoo_foo;
	private ElementImport types_foo;
	private ElementImport types_bar;

	public CSSCanonicalStateInClassDiagramTest() {
		super();
	}

	@Test
	public void controlTestForNothingCanonical() {
		// Nodes
		assertNoViews(foo_ok, foo_doit, foo_nested,
				yesno_no, yesno_yes,
				types_subfoo, types_date);

		// Edges
		assertNoViews(foo_bar, bar_super, super_yesno,
				types_foo, types_bar);
	}

	@Scenario({ "execute", "undo", "redo" })
	public void addStylesheet() {
		referenceEmbeddedStylesheet("canonical_styles");

		if (verificationPoint()) {
			// Nodes
			requireViews(foo_ok, foo_doit, foo_nested,
					yesno_no, yesno_yes);

			// Edges
			requireViews(foo_bar, bar_super, super_yesno,
					types_foo, types_bar);

			// These require an explicit style on the package
			assertNoViews(types_subfoo_createdon, types_subfoo_foo);
		}

		undo();

		if (verificationPoint()) {
			// Nodes
			assertNoViews(foo_ok, foo_doit, foo_nested,
					yesno_no, yesno_yes);

			// Edges
			assertNoViews(foo_bar, bar_super, super_yesno,
					types_foo, types_bar);
		}

		redo();

		if (verificationPoint()) {
			// Nodes
			requireViews(foo_ok, foo_doit, foo_nested,
					yesno_no, yesno_yes);

			// Edges
			requireViews(foo_bar, bar_super, super_yesno,
					types_foo, types_bar);

			// These require an explicit style on the package
			assertNoViews(types_subfoo_createdon, types_subfoo_foo);
		}
	}

	@StylesheetRef("canonical_styles")
	@NeedsUIEvents
	@Scenario({ "execute", "undo", "redo" })
	public void addStyleClass() {
		addStyleClass(requireView(types), "synch");

		if (verificationPoint()) {
			// Nodes
			requireViews(types_subfoo, types_date,
					types_subfoo_createdon);

			// Edges
			requireView(types_subfoo_foo);
		}

		undo();

		if (verificationPoint()) {
			// Nodes
			assertNoViews(types_subfoo, types_date,
					types_subfoo_createdon);

			// Edges
			assertNoView(types_subfoo_foo);
		}

		redo();

		if (verificationPoint()) {
			// Nodes
			requireViews(types_subfoo, types_date,
					types_subfoo_createdon);

			// Edges
			requireView(types_subfoo_foo);
		}
	}

	@StylesheetRef("canonical_styles")
	@NeedsUIEvents
	@Test
	public void overrideSelectorRule() {
		// Override the canonical style
		setCanonical(false, requireEditPart(foo));

		// Delete some child views that were created canonically
		delete(requireEditPart(foo_ok));
		delete(requireEditPart(foo_doit));
		delete(requireEditPart(foo_nested));

		// The elements still exist (no canonical synch)
		assertAttached(foo_ok, foo_doit, foo_nested);
	}

	@StylesheetRef("canonical_styles")
	@NeedsUIEvents
	@Test
	public void overrideExplicitClassRule() {
		addStyleClass(requireView(types), "synch");

		// Override the canonical style
		setCanonical(false, requireEditPart(types));

		// Delete some child views that were created canonically
		delete(requireEditPart(types_subfoo));
		delete(requireEditPart(types_date));

		// The elements still exist (no canonical synch)
		assertAttached(types_subfoo, types_date);
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
		bar = (org.eclipse.uml2.uml.Class) root.getOwnedType("Bar");
		super_ = (org.eclipse.uml2.uml.Class) root.getOwnedType("Super");
		yesno = (Enumeration) root.getOwnedType("YesNo");
		yesno_no = yesno.getOwnedLiteral("no");
		yesno_yes = yesno.getOwnedLiteral("yes");

		foo_bar = foo.getOwnedAttribute("bar", null).getAssociation();
		bar_super = bar.getGeneralization(super_);
		super_yesno = getRelationship(super_, yesno, Usage.class);

		types = root.getNestedPackage("types");
		types_subfoo = (org.eclipse.uml2.uml.Class) types.getOwnedType("SubFoo");
		types_subfoo_createdon = types_subfoo.getOwnedAttribute("createdOn", null);
		types_date = (DataType) types.getOwnedType("Date");

		types_subfoo_foo = types_subfoo.getGeneralization(foo);
		types_foo = types.getElementImport(foo);
		types_bar = types.getElementImport(bar);
	}

}
