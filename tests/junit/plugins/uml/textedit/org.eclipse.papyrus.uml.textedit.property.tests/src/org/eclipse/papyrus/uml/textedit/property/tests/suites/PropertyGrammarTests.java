/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.textedit.property.tests.suites;

import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.xtext.AbstractGrammarTest;
import org.eclipse.papyrus.uml.textedit.property.xtext.ui.contributions.PropertyXtextDirectEditorConfiguration;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@PluginResource("/model/xtextPropertyModel.uml")
public class PropertyGrammarTests extends AbstractGrammarTest<Property> {

	protected PrimitiveType type2; /* QName = model::Package1::type2 */

	protected DataType type1; /* QName = model::type1 */

	protected Model rootModel;

	protected Property testedProperty;

	@Before
	public void loadTestModel() {
		type2 = findElement(PrimitiveType.class, "type2");
		type1 = findElement(DataType.class, "type1");
		rootModel = findElement(Model.class, "model");

		Component component = (Component) rootModel.createPackagedElement("Component1", UMLPackage.eINSTANCE.getComponent());
		testedProperty = component.createOwnedAttribute("p1", null);
	}

	@Test
	public void testParser() throws Exception {
		tester.parseText(testedProperty, "~/p1: <Undefined>");
		Assert.assertTrue(testedProperty.isDerived());
		Assert.assertEquals(VisibilityKind.PACKAGE_LITERAL, testedProperty.getVisibility());

		tester.parseText(testedProperty, "p2");
		Assert.assertTrue(!testedProperty.isDerived()); /* Derived is mandatory, so typing "p2" means it shouldn't be derived anymore */
		Assert.assertEquals(VisibilityKind.PACKAGE_LITERAL, testedProperty.getVisibility()); /* Visibility is optional; not typing it means it shouldn't change */
		Assert.assertEquals("p2", testedProperty.getName());
	}

	@Test
	public void testInitialText() {
		Assert.assertEquals("+ p1 : <Undefined> {unique}", tester.getInitialText(testedProperty));

		testedProperty.setIsUnique(false);
		Assert.assertEquals("+ p1 : <Undefined>", tester.getInitialText(testedProperty));

		testedProperty.setIsDerived(true);
		testedProperty.setVisibility(VisibilityKind.PROTECTED_LITERAL);
		testedProperty.setType(type1);
		Assert.assertEquals("# /p1 : model::type1", tester.getInitialText(testedProperty));
	}

	@Test
	public void testDefaultValues() throws Exception {
		testedProperty.setIsUnique(false);

		LiteralReal defaultRealValue = UMLFactory.eINSTANCE.createLiteralReal();
		defaultRealValue.setValue(123.54);
		testedProperty.setDefaultValue(defaultRealValue);

		Assert.assertEquals("+ p1 : <Undefined> = 123.54", tester.getInitialText(testedProperty));
		tester.parseText(testedProperty, "p1 = .2");
		Assert.assertEquals("The instance of ValueSpecification should not change when compatible types are used", defaultRealValue, testedProperty.getDefaultValue());
		Assert.assertEquals(.2, defaultRealValue.getValue(), 0.001);
	}

	@Test
	public void testMultiplicity() throws Exception {
		// Manage two bounds (integer and unlimited natural) display
		final LiteralInteger lowerBound = UMLFactory.eINSTANCE.createLiteralInteger();
		lowerBound.setValue(2);
		testedProperty.setLowerValue(lowerBound);

		final LiteralUnlimitedNatural upperBound = UMLFactory.eINSTANCE.createLiteralUnlimitedNatural();
		upperBound.setValue(4);
		testedProperty.setUpperValue(upperBound);

		Assert.assertEquals("+ p1 : <Undefined> [2..4] {unique}", tester.getInitialText(testedProperty));

		// Parse Integer and UnlimitedNatural
		tester.parseText(testedProperty, "p1 : <Undefined> [1..*] {unique}");
		Assert.assertEquals("The instance of lower ValueSpecification should not change when compatible types are used", lowerBound, testedProperty.getLowerValue());
		Assert.assertEquals("The instance of upper ValueSpecification should not change when compatible types are used", upperBound, testedProperty.getUpperValue());
		Assert.assertEquals(1, lowerBound.getValue());
		Assert.assertEquals(-1, upperBound.getValue());

		// Manage only one bound display
		lowerBound.setValue(0);
		Assert.assertEquals("+ p1 : <Undefined> [*] {unique}", tester.getInitialText(testedProperty));

		// Parse one String
		tester.parseText(testedProperty, "p1 : <Undefined> [\"TEN\"] {unique}");
		Assert.assertTrue("The created lower ValueSpecification must be a LiteralString", testedProperty.getLowerValue() instanceof LiteralString);
		Assert.assertTrue("The created upper ValueSpecification must be a LiteralString", testedProperty.getUpperValue() instanceof LiteralString);
		Assert.assertEquals("TEN", ((LiteralString) testedProperty.getLowerValue()).getValue());
		LiteralString upperStringBound = (LiteralString) testedProperty.getUpperValue();
		Assert.assertEquals("TEN", upperStringBound.getValue());

		// Manage Integer and String
		lowerBound.setValue(3);
		testedProperty.setLowerValue(lowerBound);
		Assert.assertEquals("+ p1 : <Undefined> [3..\"TEN\"] {unique}", tester.getInitialText(testedProperty));

		// Parse Integer and String
		tester.parseText(testedProperty, "p1 : <Undefined> [\"MIN\"..\"MAX\"] {unique}");
		Assert.assertTrue("The created lower ValueSpecification must be a LiteralString", testedProperty.getLowerValue() instanceof LiteralString);
		Assert.assertEquals("The instance of upper ValueSpecification should not change when compatible types are used", upperStringBound, testedProperty.getUpperValue());
		Assert.assertEquals("MIN", ((LiteralString) testedProperty.getLowerValue()).getValue());
		Assert.assertEquals("MAX", upperStringBound.getValue());
	}

	@Override
	public DefaultXtextDirectEditorConfiguration getEditor() {
		return new PropertyXtextDirectEditorConfiguration();
	}
}
