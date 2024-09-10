/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.textedit.valuespecification.tests.suites;


import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.junit.utils.xtext.AbstractGrammarTest;
import org.eclipse.papyrus.uml.textedit.valuespecification.xtext.ui.contribution.ValueSpecificationXtextDirectEditorConfiguration;
import org.eclipse.papyrus.uml.xtext.integration.DefaultXtextDirectEditorConfiguration;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralReal;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;
import org.eclipse.uml2.uml.VisibilityKind;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@PluginResource("/model/xtextValueSpecificationModel.uml")
public class ValueSpecificationGrammarTests extends AbstractGrammarTest<EStructuralFeature> {

	/**
	 * The instance specification (QName = model::InstanceSpecification1).
	 */
	protected InstanceSpecification instanceSpecification;

	/**
	 * The enumeration literal (QName = model::Package1::EnumerationLiteral1).
	 */
	protected EnumerationLiteral enumerationLiteral;

	/**
	 * The root model.
	 */
	protected Model rootModel;

	/**
	 * The tested property.
	 */
	protected Property testedProperty;

	/**
	 * The boolean type (already created in the model).
	 */
	private Type booleanType;

	/**
	 * The integer type (already created in the model).
	 */
	private Type integerType;

	/**
	 * The real type (already created in the model).
	 */
	private Type realType;

	@Before
	public void loadTestModel() {
		instanceSpecification = findElement(InstanceSpecification.class, "InstanceSpecification1");
		enumerationLiteral = findElement(EnumerationLiteral.class, "EnumerationLiteral1");
		rootModel = findElement(Model.class, "model");
		booleanType = findElement(Property.class, "PropertyBooleanType").getType();
		integerType = findElement(Property.class, "PropertyIntegerType").getType();
		realType = findElement(Property.class, "PropertyRealType").getType();

		Class createdClass = (Class) rootModel.createPackagedElement("Class1", UMLPackage.eINSTANCE.getClass_());
		testedProperty = createdClass.createOwnedAttribute("Property", null, UMLPackage.eINSTANCE.getProperty());
	}

	@Test
	public void testParser() throws Exception {

		// **************************************//
		// Check the text parser object creation //
		// **************************************//

		// Check undefined
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "<Undefined>");
		Assert.assertNull(testedProperty.getDefaultValue());

		// ***************************************************//
		// Check the basic parser with all the possible types //
		// ***************************************************//

		// Check instance value and instance specification
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "~instanceValue=InstanceSpecification1");
		Assert.assertEquals(VisibilityKind.PACKAGE_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("instanceValue", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getInstanceValue(), testedProperty.getDefaultValue().eClass());
		Assert.assertEquals(instanceSpecification, ((InstanceValue) testedProperty.getDefaultValue()).getInstance());

		// Check literal boolean parse
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "-boolean=true");
		Assert.assertEquals(VisibilityKind.PRIVATE_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("boolean", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralBoolean(), testedProperty.getDefaultValue().eClass());
		Assert.assertEquals(true, ((LiteralBoolean) testedProperty.getDefaultValue()).isValue());

		// Check literal unlimited natural
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "+unlimitedNatural=12");
		Assert.assertEquals(VisibilityKind.PUBLIC_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("unlimitedNatural", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralUnlimitedNatural(), testedProperty.getDefaultValue().eClass());
		Assert.assertEquals(12, ((LiteralUnlimitedNatural) testedProperty.getDefaultValue()).getValue());

		// Check literal integer
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "#integer=34");
		Assert.assertEquals(VisibilityKind.PROTECTED_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("integer", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralUnlimitedNatural(), testedProperty.getDefaultValue().eClass());
		Assert.assertEquals(34, ((LiteralUnlimitedNatural) testedProperty.getDefaultValue()).getValue());

		// Check literal real
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "123.45");
		Assert.assertEquals(VisibilityKind.PROTECTED_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("integer", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralReal(), testedProperty.getDefaultValue().eClass());
		Assert.assertEquals(123.45, ((LiteralReal) testedProperty.getDefaultValue()).getValue(), 0);

		// Check literal null
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "+null");
		Assert.assertEquals(VisibilityKind.PUBLIC_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("integer", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralNull(), testedProperty.getDefaultValue().eClass());

		// Check return to null
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "");
		Assert.assertEquals(null, testedProperty.getDefaultValue());

		// Check literal string
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "-name=\"abcdef\"");
		Assert.assertEquals(VisibilityKind.PRIVATE_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("name", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralString(), testedProperty.getDefaultValue().eClass());
		Assert.assertEquals("abcdef", ((LiteralString) testedProperty.getDefaultValue()).getValue());

		// Check opaque expression
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "+#id=\"test\"");
		Assert.assertEquals(VisibilityKind.PRIVATE_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("+#id=\"test\"", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getOpaqueExpression(), testedProperty.getDefaultValue().eClass());

		// *********************************************//
		// Check the update of same value specification //
		// *********************************************//

		// Check literal unlimited natural
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "+unlimitedNatural=12");
		Assert.assertEquals(VisibilityKind.PUBLIC_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("unlimitedNatural", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralUnlimitedNatural(), testedProperty.getDefaultValue().eClass());
		Assert.assertEquals(12, ((LiteralUnlimitedNatural) testedProperty.getDefaultValue()).getValue());

		// Get the id of the default value to check that the default value is not created an other time
		final String currentIdentifier = ((XMIResource) testedProperty.getDefaultValue().eResource()).getID(testedProperty.getDefaultValue());
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "34");
		Assert.assertEquals(currentIdentifier, ((XMIResource) testedProperty.getDefaultValue().eResource()).getID(testedProperty.getDefaultValue()));

		// ************************************************************************************//
		// Check when the type of parent is not compatible with the value specification needed //
		// ************************************************************************************//

		// Check the boolean text parser with real type on property -> Opaque Expression will be created
		testedProperty.setType(realType);
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "-boolean=true");
		Assert.assertEquals("-boolean=true", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getOpaqueExpression(), testedProperty.getDefaultValue().eClass());

		// Check the unlimited natural text parser with real type on property -> Literal real will be created
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "+unlimitedNatural=12");
		Assert.assertEquals(VisibilityKind.PUBLIC_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("unlimitedNatural", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralReal(), testedProperty.getDefaultValue().eClass());
		Assert.assertEquals(12, ((LiteralReal) testedProperty.getDefaultValue()).getValue(), 0);

		// Check the unlimited natural text parser with integer type on property -> Literal integer will be created
		testedProperty.setType(integerType);
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "-integer=34");
		Assert.assertEquals(VisibilityKind.PRIVATE_LITERAL, testedProperty.getDefaultValue().getVisibility());
		Assert.assertEquals("integer", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getLiteralInteger(), testedProperty.getDefaultValue().eClass());
		Assert.assertEquals(34, ((LiteralInteger) testedProperty.getDefaultValue()).getValue(), 0);

		// Check the integer text parser with boolean type on property -> Opaque Expression will be created
		testedProperty.setType(booleanType);
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "+integer=56");
		Assert.assertEquals("+integer=56", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getOpaqueExpression(), testedProperty.getDefaultValue().eClass());

		// ******************************//
		// Check the multi valued option //
		// ******************************//
		testedProperty.setType(null);
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "integer=34");
		testedProperty.setUpper(2);
		// Check the boolean xtext parser with multi valuation -> Opaque Expression will be created
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "-boolean=true");
		Assert.assertEquals("-boolean=true", testedProperty.getDefaultValue().getName());
		Assert.assertEquals(UMLPackage.eINSTANCE.getOpaqueExpression(), testedProperty.getDefaultValue().eClass());

		// ****************************//
		// Check the empty text parser //
		// ****************************//

		// Check the integer text parser with boolean type on property -> Opaque Expression will be created
		tester.parseText(testedProperty, UMLPackage.Literals.PROPERTY__DEFAULT_VALUE, "");
		Assert.assertNull(testedProperty.getDefaultValue());

	}

	@Test
	public void testInitialText() {
		ValueSpecification testedValueSpecification = testedProperty.createDefaultValue("valueSpec", null, UMLPackage.eINSTANCE.getLiteralBoolean());
		Assert.assertEquals("valueSpec=false", tester.getParentInitialText(testedValueSpecification));

		((LiteralBoolean) testedValueSpecification).setValue(true);
		Assert.assertEquals("valueSpec=true", tester.getParentInitialText(testedValueSpecification));

		testedValueSpecification.setName("boolean");
		Assert.assertEquals("boolean=true", tester.getParentInitialText(testedValueSpecification));

		testedValueSpecification.eUnset(UMLPackage.Literals.NAMED_ELEMENT__NAME);
		Assert.assertEquals("true", tester.getParentInitialText(testedValueSpecification));
	}

	@Test
	public void testDefaultValues() throws Exception {

		// Check the instance specification
		ValueSpecification testedValueSpecification = testedProperty.createDefaultValue("literalInstanceValue", null, UMLPackage.eINSTANCE.getInstanceValue());
		((InstanceValue) testedValueSpecification).setInstance(enumerationLiteral);
		Assert.assertEquals("literalInstanceValue=EnumerationLiteral1", tester.getParentInitialText(testedValueSpecification));

		// Check the literal boolean
		testedValueSpecification = testedProperty.createDefaultValue("literalBoolean", null, UMLPackage.eINSTANCE.getLiteralBoolean());
		((LiteralBoolean) testedValueSpecification).setValue(true);
		Assert.assertEquals("literalBoolean=true", tester.getParentInitialText(testedValueSpecification));

		// Check the literal unlimited natural
		testedValueSpecification = testedProperty.createDefaultValue("literalUnlimitedNatural", null, UMLPackage.eINSTANCE.getLiteralUnlimitedNatural());
		((LiteralUnlimitedNatural) testedValueSpecification).setValue(12);
		Assert.assertEquals("literalUnlimitedNatural=12", tester.getParentInitialText(testedValueSpecification));

		// Check the literal integer
		testedValueSpecification = testedProperty.createDefaultValue("literalInteger", null, UMLPackage.eINSTANCE.getLiteralInteger());
		((LiteralInteger) testedValueSpecification).setValue(-34);
		Assert.assertEquals("literalInteger=-34", tester.getParentInitialText(testedValueSpecification));

		// Check the literal real
		testedValueSpecification = testedProperty.createDefaultValue("literalReal", null, UMLPackage.eINSTANCE.getLiteralReal());
		((LiteralReal) testedValueSpecification).setValue(123.45);
		Assert.assertEquals("literalReal=123.45", tester.getParentInitialText(testedValueSpecification));

		// Check the literal null
		testedValueSpecification = testedProperty.createDefaultValue("literalNull", null, UMLPackage.eINSTANCE.getLiteralNull());
		Assert.assertEquals("literalNull=null", tester.getParentInitialText(testedValueSpecification));

		// Check the literal string
		testedValueSpecification = testedProperty.createDefaultValue("literalString", null, UMLPackage.eINSTANCE.getLiteralString());
		((LiteralString) testedValueSpecification).setValue("abcdef");
		Assert.assertEquals("literalString=\"abcdef\"", tester.getParentInitialText(testedValueSpecification));

		// Check the opaque expression
		testedValueSpecification = testedProperty.createDefaultValue("opaqueExpression=Nothing", null, UMLPackage.eINSTANCE.getOpaqueExpression());
		Assert.assertEquals("opaqueExpression=Nothing", tester.getParentInitialText(testedValueSpecification));
	}

	@Override
	public DefaultXtextDirectEditorConfiguration getEditor() {
		return new ValueSpecificationXtextDirectEditorConfiguration();
	}
}
