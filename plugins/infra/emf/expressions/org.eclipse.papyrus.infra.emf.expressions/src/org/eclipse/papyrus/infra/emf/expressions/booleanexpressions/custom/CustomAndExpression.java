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
package org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.custom;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AndExpressionImpl;

public class CustomAndExpression extends AndExpressionImpl {

	@Override
	public Boolean evaluate(EObject context) {
		for (IBooleanExpression<EObject> current : getOwnedExpressions()) {
			if (Boolean.FALSE.equals(current.evaluate(context))) {
				return Boolean.FALSE;
			}
		}

		for (IBooleanExpression<EObject> current : getReferencedExpressions()) {
			if (Boolean.FALSE.equals(current.evaluate(context))) {
				return Boolean.FALSE;
			}
		}


		return Boolean.TRUE;
	}
}