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

package org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.custom;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.SingleEAttributeValueEqualityExpressionImpl;

/**
 * Custom implementationf for {@link SingleEAttributeValueEqualityExpression}
 */
public class CustomSingleEAttributeValueEqualityExpression extends SingleEAttributeValueEqualityExpressionImpl {

	/**
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.SingleEAttributeValueEqualityImpl#evaluate(org.eclipse.emf.ecore.EObject)
	 *
	 * @param context
	 * @return
	 */
	@Override
	public Boolean evaluate(final EObject context) {
		if (null != context && null != eAttribute) {
			if (context.eClass().getEAllAttributes().contains(this.eAttribute)) {
				final Object value = context.eGet(this.eAttribute);
				if (null == expectedValue && null == value) {
					return Boolean.TRUE;
				}
				if (null != this.expectedValue) {
					return Boolean.valueOf(this.expectedValue.equals(value.toString()));
				}
			}

		}

		return Boolean.FALSE;
	}
}
