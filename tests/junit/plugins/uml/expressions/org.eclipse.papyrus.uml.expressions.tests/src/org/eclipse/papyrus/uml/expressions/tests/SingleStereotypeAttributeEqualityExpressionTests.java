/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.tests;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsFactory;
import org.eclipse.uml2.uml.Class;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


/**
 * Tests for {@link SingleStereotypeAttributeEqualityExpressionTests}
 *
 */
@PluginResource("resources/expressionModelTest.di")
public class SingleStereotypeAttributeEqualityExpressionTests {

	private final EObject dummyEObject = EcoreFactory.eINSTANCE.createEClass();

	private final String STEREOTYPED_CLASS_FOR_ATTRIBUTE_EQUALITY_NAME = "StereotypeAttributeEqualityClass"; //$NON-NLS-1$

	private final String NOT_STEREOTYPED_CLASS_NAME = "NoStereotypedClass"; //$NON-NLS-1$

	private final String ELEMENT_STEREOTYPE_QUALIFIED_NAME = "RootElement::AttributeEqualityExpressionStereotype"; //$NON-NLS-1$

	private Class stereotypeAttributeEquality;

	private Class notStereotypedClass;

	private final String PROFILE_URI = "papyrusExpressionProfile"; //$NON-NLS-1$

	@Rule
	public final PapyrusEditorFixture fixture = new PapyrusEditorFixture();

	@Before
	public void init() {
		stereotypeAttributeEquality = (Class) fixture.getModel().getMember(STEREOTYPED_CLASS_FOR_ATTRIBUTE_EQUALITY_NAME);
		notStereotypedClass = (Class) fixture.getModel().getMember(NOT_STEREOTYPED_CLASS_NAME);
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test1() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		Assert.assertFalse(exp.evaluate(dummyEObject));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test2() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		Assert.assertFalse(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test3() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		Assert.assertFalse(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test4() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setPropertyName("singleString"); //$NON-NLS-1$
		exp.setExpectedValue("dummyValue"); //$NON-NLS-1$
		Assert.assertFalse(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test5() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setPropertyName("singleString"); //$NON-NLS-1$
		exp.setExpectedValue("aNiceString"); //$NON-NLS-1$
		Assert.assertTrue(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test6() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setPropertyName("singleString"); //$NON-NLS-1$
		exp.setExpectedValue("aNiceString"); //$NON-NLS-1$
		Assert.assertTrue(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test7() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setPropertyName("singleString"); //$NON-NLS-1$
		exp.setExpectedValue("aNiceString"); //$NON-NLS-1$
		exp.setProfileURI("dummyURI"); //$NON-NLS-1$
		Assert.assertFalse(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test8() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setPropertyName("multiString"); //$NON-NLS-1$
		exp.setExpectedValue("string1"); //$NON-NLS-1$
		Assert.assertFalse(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test9() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setPropertyName("singleReal"); //$NON-NLS-1$
		exp.setExpectedValue("3.14"); //$NON-NLS-1$
		Assert.assertTrue(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test10() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setPropertyName("singleInteger"); //$NON-NLS-1$
		exp.setExpectedValue("-12"); //$NON-NLS-1$
		Assert.assertTrue(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test11() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setPropertyName("singleBoolean"); //$NON-NLS-1$
		exp.setExpectedValue("true"); //$NON-NLS-1$
		Assert.assertTrue(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test12() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setPropertyName("singleUnlimitedNatural"); //$NON-NLS-1$
		exp.setExpectedValue("4"); //$NON-NLS-1$
		Assert.assertTrue(exp.evaluate(stereotypeAttributeEquality));
	}

	@Test
	public void SingleStereotypeAttributeEqualityExpressionTests_Test13() {
		final SingleStereotypeAttributeEqualityExpression exp = UMLExpressionsFactory.eINSTANCE.createSingleStereotypeAttributeEqualityExpression();
		exp.setStereotypeQualifiedName(ELEMENT_STEREOTYPE_QUALIFIED_NAME);
		exp.setPropertyName("singleEnum"); //$NON-NLS-1$
		exp.setExpectedValue("value2"); //$NON-NLS-1$
		Assert.assertTrue(exp.evaluate(stereotypeAttributeEquality));
	}


}
