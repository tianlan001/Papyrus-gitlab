/**
 * Copyright (c) 2017, 2019 CEA LIST.
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
package org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.custom;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AndExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralFalseExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralTrueExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.NotExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.OrExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.ReferenceBooleanExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsFactoryImpl;

public class CustomBooleanExpressionsFactory extends BooleanExpressionsFactoryImpl {

	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.eobjectbooleanexpressions.impl.EObjectBooleanExpressionsFactoryImpl#createAndExpression()
	 *
	 * @return
	 */

	@Override
	public AndExpression createAndExpression() {
		return new CustomAndExpression();
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.eobjectbooleanexpressions.impl.EObjectBooleanExpressionsFactoryImpl#createLiteralTrueExpression()
	 *
	 * @return
	 */

	@Override
	public LiteralTrueExpression createLiteralTrueExpression() {
		return new CustomLiteralTrueExpression();
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.eobjectbooleanexpressions.impl.EObjectBooleanExpressionsFactoryImpl#createOrExpression()
	 *
	 * @return
	 */

	@Override
	public OrExpression createOrExpression() {
		return new CustomOrExpression();
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.eobjectbooleanexpressions.impl.EObjectBooleanExpressionsFactoryImpl#createNotExpression()
	 *
	 * @return
	 */

	@Override
	public NotExpression createNotExpression() {
		return new CustomNotExpression();
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.eobjectbooleanexpressions.impl.EObjectBooleanExpressionsFactoryImpl#createLiteralFalseExpression()
	 *
	 * @return
	 */

	@Override
	public LiteralFalseExpression createLiteralFalseExpression() {
		return new CustomLiteralFalseExpression();
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressionspackage.impl.EObjectBooleanExpressionsFactoryImpl#createReferenceBooleanExpression()
	 *
	 * @return
	 */
	@Override
	public ReferenceBooleanExpression createReferenceBooleanExpression() {
		return new CustomReferenceBooleanExpression();
	}

	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsFactoryImpl#createSingleEAttributeValueEquality()
	 *
	 * @return
	 */
	@Override
	public SingleEAttributeValueEqualityExpression createSingleEAttributeValueEqualityExpression() {
		return new CustomSingleEAttributeValueEqualityExpression();
	}
}
