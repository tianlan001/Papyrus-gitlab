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
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Operation;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Tests various add/delete scenarios on canonical views in the diagram, with undo and redo.
 * These scenarios perform the equivalent of "hide element" to request deletion of the view
 * only, but because it's canonical, the semantic element is deleted, too.
 */
@PluginResource("models/classdiagram_canonical.di")
@ActiveDiagram("canonical")
@RunWith(ScenarioRunner.class)
public class CanonicalViewDeletionInClassDiagramTest extends AbstractCanonicalTest {
	private org.eclipse.uml2.uml.Package root;

	private org.eclipse.uml2.uml.Class foo;
	private Operation foo_doit;
	private Association foo_bar;

	private org.eclipse.uml2.uml.Package types;
	private org.eclipse.uml2.uml.Class types_subfoo;
	private Generalization types_subfoo_foo;

	public CanonicalViewDeletionInClassDiagramTest() {
		super();
	}

	@Scenario({ "execute", "undo", "redo" })
	public void deleteOperationViewFromClass() {
		delete(requireEditPart(foo_doit));

		if (verificationPoint()) {
			assertDetached(foo_doit);
		}

		undo();

		if (verificationPoint()) {
			requireEditPart(foo_doit);
			assertAttached(foo_doit);
		}

		redo();

		if (verificationPoint()) {
			assertDetached(foo_doit);
		}
	}

	@NeedsUIEvents
	@Scenario({ "execute", "undo", "redo" })
	public void deleteClassViewFromPackage() {
		delete(requireEditPart(types_subfoo));

		if (verificationPoint()) {
			assertDetached(types_subfoo);
		}

		undo();

		if (verificationPoint()) {
			requireEditPart(types_subfoo);
			assertAttached(types_subfoo);
		}

		redo();

		if (verificationPoint()) {
			assertDetached(types_subfoo);
		}
	}

	@NeedsUIEvents
	@Scenario({ "execute", "undo", "redo" })
	public void deleteGeneralizationViewFromClass() {
		delete(requireConnectionEditPart(types_subfoo_foo));

		if (verificationPoint()) {
			assertDetached(types_subfoo_foo);
		}

		undo();

		if (verificationPoint()) {
			requireConnectionEditPart(types_subfoo_foo);
			assertAttached(types_subfoo_foo);
		}

		redo();

		if (verificationPoint()) {
			assertDetached(types_subfoo_foo);
		}
	}

	@NeedsUIEvents
	@Scenario({ "execute", "undo", "redo" })
	public void deleteAssociationViewFromClass() {
		delete(requireConnectionEditPart(foo_bar));

		if (verificationPoint()) {
			assertDetached(foo_bar);
		}

		undo();

		if (verificationPoint()) {
			requireConnectionEditPart(foo_bar);
			assertAttached(foo_bar);
		}

		redo();

		if (verificationPoint()) {
			assertDetached(foo_bar);
		}
	}


	//
	// Test framework
	//

	@Before
	public void getModelElementsAndEnsureCanonicalConnections() {
		root = editor.getModel();

		foo = (org.eclipse.uml2.uml.Class) root.getOwnedType("Foo");
		foo_doit = foo.getOwnedOperation("doIt", null, null);
		foo_bar = foo.getOwnedAttribute("bar", null).getAssociation();

		types = root.getNestedPackage("types");
		types_subfoo = (org.eclipse.uml2.uml.Class) types.getOwnedType("SubFoo");
		types_subfoo_foo = types_subfoo.getGeneralization(foo);

		// Ensure canonical connections
		setCanonical(true, requireEditPart(root));
		setCanonical(true, requireEditPart(types_subfoo));
	}

}
