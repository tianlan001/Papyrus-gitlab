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
import org.eclipse.uml2.uml.Dependency;
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
 * Tests the manipulation of canonical state in various views in a class diagram that has not persisted
 * the canonical style on anything.
 */
@PluginResource("models/classdiagram_canonical.di")
@ActiveDiagram("default")
@RunWith(ScenarioRunner.class)
public class CanonicalStateInClassDiagramTest extends AbstractCanonicalTest {

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
	private Dependency types_subfoo_date;
	private ElementImport types_foo;
	private ElementImport types_bar;

	public CanonicalStateInClassDiagramTest() {
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
	public void toggleCanonicalOn() {
		setCanonical(true, foo, bar, super_, yesno, types);

		if (verificationPoint()) {
			// Nodes
			requireViews(foo_ok, foo_doit, foo_nested,
					yesno_no, yesno_yes,
					types_subfoo, types_date);

			// Edges
			requireViews(foo_bar, bar_super, super_yesno,
					types_foo, types_bar, types_subfoo_foo);

			// Nested in canonical views
			assertNoViews(types_subfoo_createdon, types_subfoo_date);
		}

		undo();

		if (verificationPoint()) {
			// Nodes
			assertNoViews(foo_ok, foo_doit, foo_nested,
					yesno_no, yesno_yes,
					types_subfoo, types_date);

			// Edges
			assertNoViews(foo_bar, bar_super, super_yesno,
					types_foo, types_bar);
		}

		redo();

		if (verificationPoint()) {
			// Nodes
			requireViews(foo_ok, foo_doit, foo_nested,
					yesno_no, yesno_yes,
					types_subfoo, types_date);

			// Edges
			requireViews(foo_bar, bar_super, super_yesno,
					types_foo, types_bar, types_subfoo_foo);

			// Nested in canonical views
			assertNoViews(types_subfoo_createdon, types_subfoo_date);
		}
	}

	@Scenario({ "execute", "undo", "redo" })
	public void toggleCanonicalOff() {
		setCanonical(true, foo, bar, super_, yesno, types);
		setCanonical(false, foo, bar, super_, yesno, types);

		if (verificationPoint()) {
			// Nodes are still there
			requireViews(foo_ok, foo_doit, foo_nested,
					yesno_no, yesno_yes,
					types_subfoo, types_date);

			// Edges are still there
			requireViews(foo_bar, bar_super, super_yesno,
					types_foo, types_bar, types_subfoo_foo);

			// Nested in canonical views never were there
			assertNoViews(types_subfoo_createdon, types_subfoo_date);
		}

		undo();

		if (verificationPoint()) {
			// Nodes are still there
			requireViews(foo_ok, foo_doit, foo_nested,
					yesno_no, yesno_yes,
					types_subfoo, types_date);

			// Edges are still there
			requireViews(foo_bar, bar_super, super_yesno,
					types_foo, types_bar, types_subfoo_foo);

			// Nested in canonical views never were there
			assertNoViews(types_subfoo_createdon, types_subfoo_date);
		}

		redo();

		if (verificationPoint()) {
			// Nodes are still there
			requireViews(foo_ok, foo_doit, foo_nested,
					yesno_no, yesno_yes,
					types_subfoo, types_date);

			// Edges are still there
			requireViews(foo_bar, bar_super, super_yesno,
					types_foo, types_bar, types_subfoo_foo);

			// Nested in canonical views never were there
			assertNoViews(types_subfoo_createdon, types_subfoo_date);
		}
	}

	@Scenario({ "on", "off" })
	public void toggleCanonicalInCanonicalNestedViews() {
		setCanonical(true, types);

		if (verificationPoint()) {
			requireViews(types_subfoo, types_date);
		}

		setCanonical(true, types_subfoo, types_date);

		if (verificationPoint()) {
			requireViews(types_subfoo_createdon, types_subfoo_foo);
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
		types_subfoo_date = getRelationship(types_subfoo, types_date, Dependency.class);
		types_foo = types.getElementImport(foo);
		types_bar = types.getElementImport(bar);
	}

}
