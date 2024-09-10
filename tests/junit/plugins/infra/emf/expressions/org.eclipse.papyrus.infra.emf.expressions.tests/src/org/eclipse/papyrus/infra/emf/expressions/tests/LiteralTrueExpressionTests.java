/**
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
 */
package org.eclipse.papyrus.infra.emf.expressions.tests;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsFactory;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralTrueExpression;
import org.junit.Assert;
import org.junit.Test;

public class LiteralTrueExpressionTests {

	private EObject dummyObject = EcoreFactory.eINSTANCE.createEClass();

	/**
	 * {@link LiteralTrueExpression} must return <code>true</code> when object to evaluate is not <code>null</code>
	 */
	@Test
	public void LiteralTrueExpression_Test1() {
		final LiteralTrueExpression trueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		Assert.assertTrue(trueExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link LiteralTrueExpression} must return <code>true</code> when object to evaluate is <code>null</code>
	 */
	@Test
	public void LiteralTrueExpression_Test2() {
		final LiteralTrueExpression trueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		Assert.assertTrue(trueExp.evaluate(null));
	}
}
