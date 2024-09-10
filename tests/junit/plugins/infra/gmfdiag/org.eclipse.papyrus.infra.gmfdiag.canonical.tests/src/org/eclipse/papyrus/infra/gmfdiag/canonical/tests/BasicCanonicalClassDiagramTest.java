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

/**
 * Tests the presentation of canonical content in a class diagram that has persisted
 * the canonical style on various views.
 */
@PluginResource("models/classdiagram_canonical.di")
@ActiveDiagram("canonical")
public class BasicCanonicalClassDiagramTest extends AbstractCanonicalTest {

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
	private Dependency types_subfoo_date;

	public BasicCanonicalClassDiagramTest() {
		super();
	}

	@Test
	public void canonicalNodesInClassDiagram() {
		requireView(foo_ok);
		requireView(foo_doit);
		requireView(foo_nested);

		requireView(yesno_no);
		requireView(yesno_yes);

		requireView(types_subfoo);
		requireView(types_date);
	}

	@Test
	public void noCanonicalViewInCanonicalView() {
		assertNoView(types_subfoo_createdon);
	}

	@Test
	public void canonicalEdgesInClassDiagram() {
		requireView(foo_bar);
		requireView(bar_super);
		requireView(super_yesno);

		requireView(types_foo);
		requireView(types_bar);
	}

	@Test
	public void noCanonicalEdgeOnCanonicalView() {
		// This is an edge on a synchronized view (and an unsynchronized view)
		requireView(types_subfoo_foo);

		// This is an edge between two unsynchronized views
		assertNoView(types_subfoo_date);
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
