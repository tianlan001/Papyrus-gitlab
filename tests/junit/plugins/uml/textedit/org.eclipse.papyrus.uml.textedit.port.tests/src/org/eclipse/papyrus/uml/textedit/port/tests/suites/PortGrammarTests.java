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
package org.eclipse.papyrus.uml.textedit.port.tests.suites;


import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.xtext.AbstractGrammarTest;
import org.eclipse.papyrus.uml.textedit.port.xtext.ui.contribution.PortXtextDirectEditorConfiguration;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@PluginResource("/model/xtextPortModel.uml")
public class PortGrammarTests extends AbstractGrammarTest<Port> {

	protected PrimitiveType type2; /* QName = model::Package1::type2 */

	protected DataType type1; /* QName = model::type1 */

	protected Model rootModel;

	protected Port testedPort;

	@Before
	public void loadTestModel() {
		type2 = findElement(PrimitiveType.class, "type2");
		type1 = findElement(DataType.class, "type1");
		rootModel = findElement(Model.class, "model");

		Component component = (Component) rootModel.createPackagedElement("Component1", UMLPackage.eINSTANCE.getComponent());
		testedPort = (Port) component.createOwnedAttribute("p1", null, UMLPackage.eINSTANCE.getPort());
	}

	@Test
	public void testParser() throws Exception {
		tester.parseText(testedPort, "~/p1: <Undefined>");
		Assert.assertTrue(testedPort.isDerived());
		Assert.assertEquals(VisibilityKind.PACKAGE_LITERAL, testedPort.getVisibility());

		tester.parseText(testedPort, "p2");
		Assert.assertTrue(!testedPort.isDerived()); /* Derived is mandatory, so typing "p2" means it shouldn't be derived anymore */
		Assert.assertEquals(VisibilityKind.PACKAGE_LITERAL, testedPort.getVisibility()); /* Visibility is optional; not typing it means it shouldn't change */
		Assert.assertEquals("p2", testedPort.getName());

		tester.parseText(testedPort, "p2: ~<Undefined>");
		Assert.assertTrue(testedPort.isConjugated());
	}

	@Test
	public void testInitialText() {
		Assert.assertEquals("+ p1 : <Undefined> {unique}", tester.getInitialText(testedPort));

		testedPort.setIsUnique(false);
		Assert.assertEquals("+ p1 : <Undefined>", tester.getInitialText(testedPort));

		testedPort.setIsDerived(true);
		testedPort.setVisibility(VisibilityKind.PROTECTED_LITERAL);
		testedPort.setType(type1);
		Assert.assertEquals("# /p1 : model::type1", tester.getInitialText(testedPort));

		testedPort.setIsConjugated(true);
		Assert.assertEquals("# /p1 : ~model::type1", tester.getInitialText(testedPort));
	}

	@Test
	public void testDefaultValues() throws Exception {
		testedPort.setIsUnique(false);

		LiteralReal defaultRealValue = UMLFactory.eINSTANCE.createLiteralReal();
		defaultRealValue.setValue(123.54);
		testedPort.setDefaultValue(defaultRealValue);

		Assert.assertEquals("+ p1 : <Undefined> = 123.54", tester.getInitialText(testedPort));
		tester.parseText(testedPort, "p1 = .2");
		Assert.assertEquals("The instance of ValueSpecification should not change when compatible types are used", defaultRealValue, testedPort.getDefaultValue());
		Assert.assertEquals(.2, defaultRealValue.getValue(), 0.001);
	}

	@Test
	public void testMultiplicity() throws Exception {
		// Manage two bounds (integer and unlimited natural) display
		final LiteralInteger lowerBound = UMLFactory.eINSTANCE.createLiteralInteger();
		lowerBound.setValue(2);
		testedPort.setLowerValue(lowerBound);

		final LiteralUnlimitedNatural upperBound = UMLFactory.eINSTANCE.createLiteralUnlimitedNatural();
		upperBound.setValue(4);
		testedPort.setUpperValue(upperBound);

		Assert.assertEquals("+ p1 : <Undefined> [2..4] {unique}", tester.getInitialText(testedPort));

		// Parse Integer and UnlimitedNatural
		tester.parseText(testedPort, "p1 : <Undefined> [1..*] {unique}");
		Assert.assertEquals("The instance of lower ValueSpecification should not change when compatible types are used", lowerBound, testedPort.getLowerValue());
		Assert.assertEquals("The instance of upper ValueSpecification should not change when compatible types are used", upperBound, testedPort.getUpperValue());
		Assert.assertEquals(1, lowerBound.getValue());
		Assert.assertEquals(-1, upperBound.getValue());

		// Manage only one bound display
		lowerBound.setValue(0);
		Assert.assertEquals("+ p1 : <Undefined> [*] {unique}", tester.getInitialText(testedPort));

		// Parse one String
		tester.parseText(testedPort, "p1 : <Undefined> [\"TEN\"] {unique}");
		Assert.assertTrue("The created lower ValueSpecification must be a LiteralString", testedPort.getLowerValue() instanceof LiteralString);
		Assert.assertTrue("The created upper ValueSpecification must be a LiteralString", testedPort.getUpperValue() instanceof LiteralString);
		Assert.assertEquals("TEN", ((LiteralString) testedPort.getLowerValue()).getValue());
		LiteralString upperStringBound = (LiteralString) testedPort.getUpperValue();
		Assert.assertEquals("TEN", upperStringBound.getValue());

		// Manage Integer and String
		lowerBound.setValue(3);
		testedPort.setLowerValue(lowerBound);
		Assert.assertEquals("+ p1 : <Undefined> [3..\"TEN\"] {unique}", tester.getInitialText(testedPort));

		// Parse Integer and String
		tester.parseText(testedPort, "p1 : <Undefined> [\"MIN\"..\"MAX\"] {unique}");
		Assert.assertTrue("The created lower ValueSpecification must be a LiteralString", testedPort.getLowerValue() instanceof LiteralString);
		Assert.assertEquals("The instance of upper ValueSpecification should not change when compatible types are used", upperStringBound, testedPort.getUpperValue());
		Assert.assertEquals("MIN", ((LiteralString) testedPort.getLowerValue()).getValue());
		Assert.assertEquals("MAX", upperStringBound.getValue());
	}

	@Override
	public DefaultXtextDirectEditorConfiguration getEditor() {
		return new PortXtextDirectEditorConfiguration();
	}
}
