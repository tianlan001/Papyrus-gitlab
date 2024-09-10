/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.expressions.tests;

import org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsFactory;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for {@link IsTypeOfExpression}
 *
 */
public class IsTypeOfExpressionTests {

	@Test
	public void noMetaclassAndNoContextTest() {
		final IsTypeOfExpression exp = UMLExpressionsFactory.eINSTANCE.createIsTypeOfExpression();
		Assert.assertFalse(exp.evaluate(null));
	}

	@Test
	public void metaclassAndNoContext() {
		final IsTypeOfExpression exp = UMLExpressionsFactory.eINSTANCE.createIsTypeOfExpression();
		exp.setUmlEClass(UMLPackage.eINSTANCE.getElement());
		Assert.assertFalse(exp.evaluate(null));
	}

	@Test
	public void metaclassAndInValidContext_1() {
		final IsTypeOfExpression exp = UMLExpressionsFactory.eINSTANCE.createIsTypeOfExpression();
		exp.setUmlEClass(UMLPackage.eINSTANCE.getElement());
		final Class clazz = UMLFactory.eINSTANCE.createClass();
		Assert.assertFalse(exp.evaluate(clazz));
	}

	@Test
	public void metaclassAndValidContext_2() {
		final IsTypeOfExpression exp = UMLExpressionsFactory.eINSTANCE.createIsTypeOfExpression();
		exp.setUmlEClass(UMLPackage.eINSTANCE.getClass_());
		final Class clazz = UMLFactory.eINSTANCE.createClass();
		Assert.assertTrue(exp.evaluate(clazz));
	}
}
