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

package org.eclipse.papyrus.infra.emf.expressions.tests;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsFactory;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression;
import org.junit.Assert;
import org.junit.Test;

/**
 * Junit tests for {@link SingleEAttributeValueEqualityExpression}
 */
@SuppressWarnings("nls")
public class SingleEAttributeValueEqualityExpressionTests {

	private EClass dummyObject = EcoreFactory.eINSTANCE.createEClass();

	@Test
	public void singleEAttributeEqualityExpression_Test1() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		Assert.assertFalse(exp.evaluate(null));
	}

	@Test
	public void singleEAttributeEqualityExpression_Test2() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		Assert.assertFalse(exp.evaluate(dummyObject));
	}

	@Test
	public void singleEAttributeEqualityExpression_Test3() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getEAnnotation_Source());
		Assert.assertFalse(exp.evaluate(dummyObject));
	}

	@Test
	public void singleEAttributeEqualityExpression_Test4() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getEAnnotation_Source());
		exp.setExpectedValue("dummyValue");
		Assert.assertFalse(exp.evaluate(dummyObject));
	}

	@Test
	public void singleEAttributeEqualityExpression_Test5() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getENamedElement_Name());
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName("niceName");
		exp.setExpectedValue(eClass.getName());
		Assert.assertTrue(exp.evaluate(eClass));
	}

	@Test
	public void singleEAttributeEqualityExpression_Test6() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getENamedElement_Name());
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName("niceName");
		exp.setExpectedValue("anotherName");
		Assert.assertFalse(exp.evaluate(eClass));
	}

	/**
	 * tests for null string
	 */
	@Test
	public void singleEAttributeEqualityExpression_Test7() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getENamedElement_Name());
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName(null);
		exp.setExpectedValue(null);
		Assert.assertTrue(exp.evaluate(eClass));
	}

	/**
	 * tests for empty string
	 */
	@Test
	public void singleEAttributeEqualityExpression_Test8() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getENamedElement_Name());
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setName("");
		exp.setExpectedValue("");
		Assert.assertTrue(exp.evaluate(eClass));
	}

	public void singleEAttributeEqualityExpression_Test9() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getEClass_Abstract());
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setAbstract(true);
		exp.setExpectedValue("true");
		Assert.assertTrue(exp.evaluate(eClass));
	}

	public void singleEAttributeEqualityExpression_Test10() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getEClass_Abstract());
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setAbstract(true);
		exp.setExpectedValue("TRUE");
		Assert.assertTrue(exp.evaluate(eClass));
	}

	public void singleEAttributeEqualityExpression_Test11() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getEClass_Abstract());
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setAbstract(false);
		exp.setExpectedValue("false");
		Assert.assertTrue(exp.evaluate(eClass));
	}

	public void singleEAttributeEqualityExpression_Test12() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getEClass_Abstract());
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setAbstract(false);
		exp.setExpectedValue("FALSE");
		Assert.assertTrue(exp.evaluate(eClass));
	}

	public void singleEAttributeEqualityExpression_Test13() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getEClass_Abstract());
		final EClass eClass = EcoreFactory.eINSTANCE.createEClass();
		eClass.setAbstract(false);
		exp.setExpectedValue("dummyValue");
		Assert.assertFalse(exp.evaluate(eClass));
	}

	public void singleEAttributeEqualityExpression_Test14() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getETypedElement_LowerBound());
		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		eAttribute.setLowerBound(2);
		exp.setExpectedValue("2");
		Assert.assertTrue(exp.evaluate(eAttribute));
	}

	public void singleEAttributeEqualityExpression_Test15() {
		final SingleEAttributeValueEqualityExpression exp = BooleanExpressionsFactory.eINSTANCE.createSingleEAttributeValueEqualityExpression();
		exp.setEAttribute(EcorePackage.eINSTANCE.getETypedElement_LowerBound());
		final EAttribute eAttribute = EcoreFactory.eINSTANCE.createEAttribute();
		eAttribute.setLowerBound(2);
		exp.setExpectedValue("3");
		Assert.assertFalse(exp.evaluate(eAttribute));
	}
}
