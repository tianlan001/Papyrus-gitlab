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
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralFalseExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralTrueExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.ReferenceBooleanExpression;
import org.junit.Assert;
import org.junit.Test;


public class ReferenceExpressionTests {

	private EObject dummyObject = EcoreFactory.eINSTANCE.createEClass();

	/**
	 * {@link ReferenceBooleanExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void ReferenceExpression_Test1() {
		ReferenceBooleanExpression exp = BooleanExpressionsFactory.eINSTANCE.createReferenceBooleanExpression();
		Assert.assertTrue(exp.evaluate(this.dummyObject));
	}

	/**
	 * {@link ReferenceBooleanExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void ReferenceExpression_Test2() {
		ReferenceBooleanExpression exp = BooleanExpressionsFactory.eINSTANCE.createReferenceBooleanExpression();
		Assert.assertTrue(exp.evaluate(null));
	}

	/**
	 * {@link ReferenceBooleanExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void ReferenceExpression_Test3() {
		LiteralTrueExpression refExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		ReferenceBooleanExpression exp = BooleanExpressionsFactory.eINSTANCE.createReferenceBooleanExpression();
		exp.setReferencedExpression(refExp);
		Assert.assertTrue(exp.evaluate(this.dummyObject));
	}

	/**
	 * {@link ReferenceBooleanExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void ReferenceExpression_Test4() {
		LiteralTrueExpression refExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		ReferenceBooleanExpression exp = BooleanExpressionsFactory.eINSTANCE.createReferenceBooleanExpression();
		exp.setReferencedExpression(refExp);
		Assert.assertTrue(exp.evaluate(null));
	}

	/**
	 * {@link ReferenceBooleanExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void ReferenceExpression_Test5() {
		LiteralFalseExpression refExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		ReferenceBooleanExpression exp = BooleanExpressionsFactory.eINSTANCE.createReferenceBooleanExpression();
		exp.setReferencedExpression(refExp);
		Assert.assertFalse(exp.evaluate(this.dummyObject));
	}

	/**
	 * {@link ReferenceBooleanExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	public void ReferenceExpression_Test6() {
		LiteralFalseExpression refExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		ReferenceBooleanExpression exp = BooleanExpressionsFactory.eINSTANCE.createReferenceBooleanExpression();
		exp.setReferencedExpression(refExp);
		Assert.assertFalse(exp.evaluate(null));
	}

}
