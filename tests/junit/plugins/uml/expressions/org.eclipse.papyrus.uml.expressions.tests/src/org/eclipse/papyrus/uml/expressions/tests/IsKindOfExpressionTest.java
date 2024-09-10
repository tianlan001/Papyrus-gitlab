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

import org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsFactory;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the {@link IsKindOfExpression}
 *
 */
public class IsKindOfExpressionTest {


	@Test
	public void noMetaclassAndNoContextTest() {
		final IsKindOfExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfExpression();
		Assert.assertFalse(exp.evaluate(null));
	}

	@Test
	public void metaclassAndNoContext() {
		final IsKindOfExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfExpression();
		exp.setUmlEClass(UMLPackage.eINSTANCE.getElement());
		Assert.assertFalse(exp.evaluate(null));
	}

	@Test
	public void metaclassAndValidContext_1() {
		final IsKindOfExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfExpression();
		exp.setUmlEClass(UMLPackage.eINSTANCE.getElement());
		final Class clazz = UMLFactory.eINSTANCE.createClass();
		Assert.assertTrue(exp.evaluate(clazz));
	}

	@Test
	public void metaclassAndValidContext_2() {
		final IsKindOfExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfExpression();
		exp.setUmlEClass(UMLPackage.eINSTANCE.getClass_());
		final Class clazz = UMLFactory.eINSTANCE.createClass();
		Assert.assertTrue(exp.evaluate(clazz));
	}

	@Test
	public void metaclassAndInvalidContext() {
		final IsKindOfExpression exp = UMLExpressionsFactory.eINSTANCE.createIsKindOfExpression();
		exp.setUmlEClass(UMLPackage.eINSTANCE.getClass_());
		final Package clazz = UMLFactory.eINSTANCE.createPackage();
		Assert.assertFalse(exp.evaluate(clazz));
	}

}
