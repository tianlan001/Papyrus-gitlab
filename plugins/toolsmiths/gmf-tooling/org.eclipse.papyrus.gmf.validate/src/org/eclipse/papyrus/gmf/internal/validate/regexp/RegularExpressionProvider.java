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
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.validate.regexp;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IModelExpression;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IModelExpressionProvider;
import org.eclipse.papyrus.gmf.internal.validate.expressions.IParseEnvironment;

public class RegularExpressionProvider implements IModelExpressionProvider {

	public RegularExpressionProvider() {	
	}

	public IModelExpression createExpression(String body, EClassifier context) {
		return createExpression(body, context, null);		
	}
	
	public IModelExpression createExpression(String body, EClassifier context, IParseEnvironment extEnv) {
		return new RegularExpressionAdapter(body, context, extEnv, false);
	}
	
	public static class Negated extends RegularExpressionProvider {
		public IModelExpression createExpression(String body, EClassifier context, IParseEnvironment extEnv) {
			return new RegularExpressionAdapter(body, context, extEnv, true);
		}		
	}
}
