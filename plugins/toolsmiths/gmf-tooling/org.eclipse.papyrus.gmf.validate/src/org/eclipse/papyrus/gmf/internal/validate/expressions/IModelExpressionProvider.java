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
package org.eclipse.papyrus.gmf.internal.validate.expressions;

import org.eclipse.emf.ecore.EClassifier;

/**
 * This interface represents implements language specific expression provider.
 * The implementations are registered through
 * <code>org.eclipse.papyrus.gmf.validate.expressions</code> extension point.
 * 
 * @see ExpressionProviderRegistry
 */
public interface IModelExpressionProvider {

	/**
	 * Creates expression from body in the specified context.
	 * <p>
	 * The context is represented by EClass referenceable from the expression
	 * body. Its instance passed at evalution time provides the evalution
	 * context.
	 * 
	 * @param body
	 *            definition of the evaluation logic in the language of this
	 *            expression provider
	 * @param context
	 *            the context classifier for parsing
	 * @return parsed model expression instance with the status indicating
	 *         success or possible failure.
	 */
	IModelExpression createExpression(String body, EClassifier context);

	/**
	 * Creates expression from body in the specified context class and extended environment.
	 * <p>
	 * In addition to the context EClass, extended environment with custom variables 
	 * is passed. The variable are referencable in the
	 * 
	 * @param body
	 *            definition of the evaluation logic in the language of this
	 *            expression provider
	 * @param context
	 *            the context classifier for parsing
	 * @return parsed model expression instance with the status indicating
	 *         success or possible failure.
	 *          
	 * @see EnvironmentProvider#createParseEnv()
	 */
	IModelExpression createExpression(String body, EClassifier context, IParseEnvironment extEnv);
}
