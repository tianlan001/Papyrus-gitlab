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
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AndExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsFactory;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralFalseExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralTrueExpression;
import org.junit.Assert;
import org.junit.Test;



public class AndExpressionTests {

	private EObject dummyObject = EcoreFactory.eINSTANCE.createEClass();

	/**
	 * {@link AndExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>empty</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>empty</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test1() {
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		Assert.assertTrue(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>empty</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>empty</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test2() {
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		Assert.assertTrue(andExp.evaluate(null));
	}

	/**
	 * {@link AndExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>empty</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test3() {
		final LiteralTrueExpression ownedTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedTrueExp);
		Assert.assertTrue(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>empty</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test4() {
		final LiteralTrueExpression ownedTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedTrueExp);
		Assert.assertTrue(andExp.evaluate(null));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>empty</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test5() {
		final LiteralFalseExpression ownedFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedFalseExp);
		Assert.assertFalse(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>empty</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test6() {
		final LiteralFalseExpression ownedFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedFalseExp);
		Assert.assertFalse(andExp.evaluate(null));
	}


	/**
	 * {@link AndExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test7() {
		final LiteralTrueExpression ownedTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression refTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedTrueExp);
		andExp.getReferencedExpressions().add(refTrueExp);
		Assert.assertTrue(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test8() {
		final LiteralTrueExpression ownedTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression refTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedTrueExp);
		andExp.getReferencedExpressions().add(refTrueExp);
		Assert.assertTrue(andExp.evaluate(null));
	}


	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test9() {
		final LiteralFalseExpression ownedFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralTrueExpression refTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedFalseExp);
		andExp.getReferencedExpressions().add(refTrueExp);
		Assert.assertFalse(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test10() {
		final LiteralFalseExpression ownedFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralTrueExpression refTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedFalseExp);
		andExp.getReferencedExpressions().add(refTrueExp);
		Assert.assertFalse(andExp.evaluate(null));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test11() {
		final LiteralTrueExpression ownedTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralFalseExpression refFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedTrueExp);
		andExp.getReferencedExpressions().add(refFalseExp);
		Assert.assertFalse(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test12() {
		final LiteralTrueExpression ownedTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralFalseExpression refFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedTrueExp);
		andExp.getReferencedExpressions().add(refFalseExp);
		Assert.assertFalse(andExp.evaluate(null));
	}



	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test13() {
		final LiteralFalseExpression refFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralFalseExpression ownedFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedFalseExp);
		andExp.getReferencedExpressions().add(refFalseExp);
		Assert.assertFalse(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test14() {
		final LiteralFalseExpression refFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralFalseExpression ownedFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(ownedFalseExp);
		andExp.getReferencedExpressions().add(refFalseExp);
		Assert.assertFalse(andExp.evaluate(null));
	}

	// ----Tests with 2 owned and 2 referenced expressions-----


	/**
	 * {@link AndExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test15() {
		final LiteralTrueExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertTrue(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test16() {
		final LiteralTrueExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertTrue(andExp.evaluate(null));
	}


	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test17() {
		final LiteralFalseExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralTrueExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertFalse(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test18() {
		final LiteralFalseExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralTrueExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertFalse(andExp.evaluate(null));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test19() {
		final LiteralTrueExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralFalseExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralTrueExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertFalse(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test20() {
		final LiteralTrueExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralFalseExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralTrueExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertFalse(andExp.evaluate(null));
	}


	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test21() {
		final LiteralTrueExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralFalseExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralTrueExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertFalse(andExp.evaluate(this.dummyObject));
	}


	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test22() {
		final LiteralTrueExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralFalseExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralTrueExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertFalse(andExp.evaluate(null));
	}


	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test23() {
		final LiteralTrueExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralFalseExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertFalse(andExp.evaluate(this.dummyObject));
	}


	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test24() {
		final LiteralTrueExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralTrueExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralFalseExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertFalse(andExp.evaluate(null));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test25() {
		final LiteralFalseExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralFalseExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralFalseExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralFalseExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertFalse(andExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link AndExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpressions</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void AndExpression_Test26() {
		final LiteralFalseExpression firstOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralFalseExpression secondOwnedExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralFalseExpression firstRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralFalseExpression secondRefExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();

		final AndExpression andExp = BooleanExpressionsFactory.eINSTANCE.createAndExpression();
		andExp.getOwnedExpressions().add(firstOwnedExp);
		andExp.getOwnedExpressions().add(secondOwnedExp);
		andExp.getReferencedExpressions().add(firstRefExp);
		andExp.getReferencedExpressions().add(secondRefExp);

		Assert.assertFalse(andExp.evaluate(null));
	}
}
