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
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.NotExpression;
import org.junit.Assert;
import org.junit.Test;

public class NotExpressionTests {

	private EObject dummyObject = EcoreFactory.eINSTANCE.createEClass();

	/**
	 * {@link NotExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpression is</li>
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
	public void NotExpression_Test1_1() {
		final NotExpression notExp = BooleanExpressionsFactory.eINSTANCE.createNotExpression();
		Assert.assertFalse(notExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link NotExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpression is</li>
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
	public void NotExpression_Test1_2() {
		final NotExpression notExp = BooleanExpressionsFactory.eINSTANCE.createNotExpression();
		Assert.assertFalse(notExp.evaluate(null));
	}

	/**
	 * {@link NotExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void NotExpression_Test2_1() {
		final LiteralTrueExpression trueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final NotExpression notExp = BooleanExpressionsFactory.eINSTANCE.createNotExpression();
		notExp.setOwnedExpression(trueExp);
		Assert.assertFalse(notExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link NotExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void NotExpression_Test2_2() {
		final LiteralTrueExpression trueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final NotExpression notExp = BooleanExpressionsFactory.eINSTANCE.createNotExpression();
		notExp.setOwnedExpression(trueExp);
		Assert.assertFalse(notExp.evaluate(null));
	}

	/**
	 * {@link NotExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void NotExpression_Test3_1() {
		final LiteralFalseExpression falseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final NotExpression notExp = BooleanExpressionsFactory.eINSTANCE.createNotExpression();
		notExp.setOwnedExpression(falseExp);
		Assert.assertTrue(notExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link NotExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void NotExpression_Test3_2() {
		final LiteralFalseExpression ownedFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final NotExpression notExp = BooleanExpressionsFactory.eINSTANCE.createNotExpression();
		notExp.setOwnedExpression(ownedFalseExp);
		Assert.assertTrue(notExp.evaluate(null));
	}


	/**
	 * {@link NotExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void NotExpression_Test4_1() {
		final LiteralFalseExpression ownedFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralTrueExpression refTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final NotExpression notExp = BooleanExpressionsFactory.eINSTANCE.createNotExpression();
		notExp.setOwnedExpression(ownedFalseExp);
		notExp.setReferencedExpression(refTrueExp);
		Assert.assertTrue(notExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link NotExpression} must return <code>true</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void NotExpression_Test4_2() {
		final LiteralFalseExpression ownedFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final LiteralTrueExpression refTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final NotExpression notExp = BooleanExpressionsFactory.eINSTANCE.createNotExpression();
		notExp.setOwnedExpression(ownedFalseExp);
		notExp.setReferencedExpression(refTrueExp);
		Assert.assertTrue(notExp.evaluate(null));
	}

	/**
	 * {@link NotExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li>not <code>null</code></li>
	 * </ul>
	 * <li>ownedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void NotExpression_Test4_3() {
		final LiteralTrueExpression ownedTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralFalseExpression refFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final NotExpression notExp = BooleanExpressionsFactory.eINSTANCE.createNotExpression();
		notExp.setOwnedExpression(ownedTrueExp);
		notExp.setReferencedExpression(refFalseExp);
		Assert.assertFalse(notExp.evaluate(this.dummyObject));
	}

	/**
	 * {@link NotExpression} must return <code>false</code> when
	 * <ul>
	 * <li>object to evaluate is</li>
	 * <ul>
	 * <li><code>null</code></li>
	 * </ul>
	 * <li>ownedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralTrueExpression}</li>
	 * </ul>
	 * <li>referencedExpression is</li>
	 * <ul>
	 * <li>{@link LiteralFalseExpression}</li>
	 * </ul>
	 * </ul>
	 */
	@Test
	public void NotExpression_Test4_4() {
		final LiteralTrueExpression ownedTrueExp = BooleanExpressionsFactory.eINSTANCE.createLiteralTrueExpression();
		final LiteralFalseExpression refFalseExp = BooleanExpressionsFactory.eINSTANCE.createLiteralFalseExpression();
		final NotExpression notExp = BooleanExpressionsFactory.eINSTANCE.createNotExpression();
		notExp.setOwnedExpression(ownedTrueExp);
		notExp.setReferencedExpression(refFalseExp);
		Assert.assertFalse(notExp.evaluate(null));
	}

}
