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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsTypeOfExpressionImpl;

/**
 * 
 * Override the default implementation of the generated class
 *
 */
public class CustomIsTypeOfExpression extends IsTypeOfExpressionImpl {

	/**
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsInstanceOfExpressionImpl#evaluate(org.eclipse.emf.ecore.EObject)
	 *
	 * @param context
	 * @return
	 */
	@Override
	public Boolean evaluate(final EObject context) {
		boolean result = false;
		// check about element is not required for this expression
		if (null != context && null != getUmlEClass()) {
			result = getUmlEClass() == context.eClass();
		}
		return Boolean.valueOf(result);
	}
}
