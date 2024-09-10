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
import org.eclipse.papyrus.uml.expressions.umlexpressions.impl.HasAppliedStereotypesExpressionImpl;
import org.eclipse.uml2.uml.Element;

/**
 * Override the default implementation of the generated class
 *
 */
public class CustomHasAppliedStereotypesExpression extends HasAppliedStereotypesExpressionImpl {

	/**
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.HasAppliedStereotypesExpressionImpl#evaluate(org.eclipse.emf.ecore.EObject)
	 *
	 * @param context
	 * @return
	 */
	@Override
	public Boolean evaluate(final EObject context) {
		Boolean result = Boolean.FALSE;
		if (context instanceof Element) {
			result = Boolean.valueOf(!((Element) context).getAppliedStereotypes().isEmpty());
		}
		return result;
	}
}
