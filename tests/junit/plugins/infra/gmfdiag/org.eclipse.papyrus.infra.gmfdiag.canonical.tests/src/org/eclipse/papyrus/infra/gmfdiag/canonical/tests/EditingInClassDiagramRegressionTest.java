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
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPart;
import org.eclipse.papyrus.junit.framework.runner.Scenario;
import org.eclipse.papyrus.junit.framework.runner.ScenarioRunner;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * Regression tests for add/delete scenarios in the diagram using the palette tools that operate on notation
 * and semantics together (usual non-canonical work), with undo and redo.
 */
@PluginResource("models/classdiagram_canonical.di")
@ActiveDiagram("canonical")
@RunWith(ScenarioRunner.class)
public class EditingInClassDiagramRegressionTest extends AbstractCanonicalTest {
	private org.eclipse.uml2.uml.Package root;

	private org.eclipse.uml2.uml.Class foo;
	private org.eclipse.uml2.uml.Class bar;
	private Enumeration yesno;
	private EnumerationLiteral yesno_no;

	private Association foo_bar;

	private org.eclipse.uml2.uml.Package types;
	private DataType types_date;

	public EditingInClassDiagramRegressionTest() {
		super();
	}

	@Scenario({ "execute", "undo", "redo" })
	public void createPropertyInClass() {
		Property property = createWithView(bar, UMLPackage.Literals.PROPERTY, Property.class);
		EditPart editPart = requireEditPart(property);

		if (verificationPoint()) {
			assertThat(getClassAttributeCompartment(requireEditPart(bar)), is(editPart.getParent()));
		}

		undo();

		if (verificationPoint()) {
			assertNoView(property);
		}

		redo();

		if (verificationPoint()) {
			editPart = requireEditPart(property);
			assertThat(getClassAttributeCompartment(requireEditPart(bar)), is(editPart.getParent()));
		}
	}

	@Scenario({ "execute", "undo", "redo" })
	public void deleteLiteralInEnumeration() {
		removeWithView(yesno_no);

		if (verificationPoint()) {
			assertNoView(yesno_no);
		}

		undo();

		if (verificationPoint()) {
			EditPart editPart = requireEditPart(yesno_no);
			assertThat(getEnumerationLiteralCompartment(requireEditPart(yesno)), is(editPart.getParent()));
		}

		redo();

		if (verificationPoint()) {
			assertNoView(yesno_no);
		}
	}

	@Scenario({ "execute", "undo", "redo" })
	public void createInterfaceInPackage() {
		Interface interface_ = createWithView(types, UMLPackage.Literals.INTERFACE, Interface.class);
		EditPart editPart = requireEditPart(interface_);

		if (verificationPoint()) {
			assertThat(getPackageContentsCompartment(requireEditPart(types)), is(editPart.getParent()));
		}

		undo();

		if (verificationPoint()) {
			assertNoView(interface_);
		}

		redo();

		if (verificationPoint()) {
			editPart = requireEditPart(interface_);
			assertThat(getPackageContentsCompartment(requireEditPart(types)), is(editPart.getParent()));
		}
	}

	@Scenario({ "execute", "undo", "redo" })
	public void deleteDataTypeFromPackage() {
		removeWithView(types_date);

		if (verificationPoint()) {
			assertNoView(types_date);
		}

		undo();

		if (verificationPoint()) {
			EditPart editPart = requireEditPart(types_date);
			assertThat(getPackageContentsCompartment(requireEditPart(types)), is(editPart.getParent()));
		}

		redo();

		if (verificationPoint()) {
			assertNoView(types_date);
		}
	}

	@NeedsUIEvents
	@Scenario({ "execute", "undo", "redo" })
	public void createDependencyInClass() {
		// Ensure canonical connections
		setCanonical(true, requireEditPart(root));
		setCanonical(true, requireEditPart(types_date));

		Dependency dependency = createDependencyWithView(bar, types_date);

		if (verificationPoint()) {
			ConnectionEditPart editPart = (ConnectionEditPart) requireConnectionEditPart(dependency);
			assertThat(editPart.getSource(), is((EditPart) requireEditPart(bar)));
			assertThat(editPart.getTarget(), is((EditPart) requireEditPart(types_date)));
		}

		undo();

		if (verificationPoint()) {
			assertNoView(dependency);
		}

		redo();

		if (verificationPoint()) {
			ConnectionEditPart editPart = (ConnectionEditPart) requireConnectionEditPart(dependency);
			assertThat(editPart.getSource(), is((EditPart) requireEditPart(bar)));
			assertThat(editPart.getTarget(), is((EditPart) requireEditPart(types_date)));
		}
	}

	@NeedsUIEvents
	@Scenario({ "execute", "undo", "redo" })
	public void deleteAssociationFromClass() {
		removeWithView(foo_bar);

		if (verificationPoint()) {
			assertNoView(foo_bar);
		}

		undo();

		if (verificationPoint()) {
			ConnectionEditPart editPart = (ConnectionEditPart) requireConnectionEditPart(foo_bar);
			assertThat(editPart.getSource(), is((EditPart) requireEditPart(foo)));
			assertThat(editPart.getTarget(), is((EditPart) requireEditPart(bar)));
		}

		redo();

		if (verificationPoint()) {
			assertNoView(foo_bar);
		}
	}


	//
	// Test framework
	//

	@Before
	public void getModelElements() {
		root = editor.getModel();

		foo = (org.eclipse.uml2.uml.Class) root.getOwnedType("Foo");
		bar = (org.eclipse.uml2.uml.Class) root.getOwnedType("Bar");
		yesno = (Enumeration) root.getOwnedType("YesNo");
		yesno_no = yesno.getOwnedLiteral("no");

		foo_bar = foo.getOwnedAttribute("bar", null).getAssociation();

		types = root.getNestedPackage("types");
		types_date = (DataType) types.getOwnedType("Date");
	}

}
