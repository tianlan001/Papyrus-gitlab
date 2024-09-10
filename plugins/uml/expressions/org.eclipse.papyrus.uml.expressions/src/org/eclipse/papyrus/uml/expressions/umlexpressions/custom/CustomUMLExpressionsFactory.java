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
package org.eclipse.papyrus.uml.expressions.umlexpressions.custom;

import org.eclipse.papyrus.uml.expressions.umlexpressions.HasAppliedStereotypesExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfStereotypeExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsStereotypedWithExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfStereotypeExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsFactoryImpl;

/**
 *
 * Override the default implementation of the generated class
 *
 */
public class CustomUMLExpressionsFactory extends UMLExpressionsFactoryImpl {

	/**
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsFactoryImpl#createIsStereotypedWithExpression()
	 *
	 * @return
	 */
	@Override
	public IsStereotypedWithExpression createIsStereotypedWithExpression() {
		return new CustomIsStereotypedWithExpression();
	}


	/**
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsFactoryImpl#createHasAppliedStereotypesExpression()
	 *
	 * @return
	 */
	@Override
	public HasAppliedStereotypesExpression createHasAppliedStereotypesExpression() {
		return new CustomHasAppliedStereotypesExpression();
	}


	/**
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsFactoryImpl#createIsKindOfExpression()
	 *
	 * @return
	 */
	@Override
	public IsKindOfExpression createIsKindOfExpression() {
		return new CustomIsKindOfExpression();
	}

	/**
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsFactoryImpl#createIsTypeOfExpression()
	 *
	 * @return
	 */
	@Override
	public IsTypeOfExpression createIsTypeOfExpression() {
		return new CustomIsTypeOfExpression();
	}

	/***
	 *
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsFactoryImpl#createIsKindOfStereotypeExpression()
	 *
	 * @return
	 */
	@Override
	public IsKindOfStereotypeExpression createIsKindOfStereotypeExpression() {
		return new CustomIsKindOfStereotypeExpression();
	}

	/***
	 *
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsFactoryImpl#createIsTypeOfStereotypeExpression()
	 *
	 * @return
	 */
	@Override
	public IsTypeOfStereotypeExpression createIsTypeOfStereotypeExpression() {
		return new CustomIsTypeOfStereotypeExpression();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsFactoryImpl#createSingleStereotypeAttributeEqualityExpression()
	 *
	 * @return
	 */
	@Override
	public SingleStereotypeAttributeEqualityExpression createSingleStereotypeAttributeEqualityExpression() {
		return new CustomSingleStereotypeAttributeEqualityExpression();
	}
}
