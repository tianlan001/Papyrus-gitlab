/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which
 * accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:  
 *    Radek Dvorak (Borland) - initial API and implementation
 *    Artem Tikhomirov (Borland) - refactoring
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.validate;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IModelExpression;

public interface IDefElementProvider {
	IStatus getStatus();
	
	public interface ContextProvider extends IDefElementProvider {
		/**
		 * Gets context classifier.
		 * 
		 * @param resolutionContext
		 *            contextual instance to be used for resolving the resulting
		 *            context classifier.
		 * @return classifer object or <code>null</code> is this provider was
		 *         not able to find out suitable classifier in this
		 *         <code>resolutionContext</code>.
		 *         <p>
		 *         Note: if this provider status is not <code>OK</code>, this
		 *         method returns <code>null</code>.
		 */
		EClassifier getContextClassifier(EObject resolutionContext);
	}
	
	public interface TypeProvider extends IDefElementProvider {
		EClassifier getType(EObject context);
		boolean isAssignable(EObject context, IModelExpression expression);
	}
	
	public interface StringValProvider extends IDefElementProvider {
		String getValue(EObject contextInstance);
	}	
}
