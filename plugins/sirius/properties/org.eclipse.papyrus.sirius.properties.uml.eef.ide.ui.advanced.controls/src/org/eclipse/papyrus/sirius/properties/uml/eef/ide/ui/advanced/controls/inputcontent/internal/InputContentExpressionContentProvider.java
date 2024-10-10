/*******************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.inputcontent.internal;

import java.util.List;

import org.eclipse.eef.core.api.utils.EvalFactory;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;

/**
 * This content provider allows to filter or managed all the content displayed
 * in the table of the widget by using the input content expression.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class InputContentExpressionContentProvider implements IStructuredContentProvider {

	/**
	 * The input content expression of the widget used to manage elements to display.
	 */
	private String inputContentExpression;

	/**
	 * The interpreter to evaluate AQL expressions.
	 */
	private IInterpreter interpreter;

	/**
	 * the variable manager which contain variables used by Interpreter to evaluate AQL expression.
	 */
	private IVariableManager variableManager;

	/**
	 * 
	 * Constructor.
	 *
	 * @param inputContentExpression
	 *            the input content expression of the widget used to manage elements to display
	 * @param interpreter
	 *            the interpreter to evaluate AQL expressions
	 * @param variableManager
	 *            the variable manager which contain variables
	 *            used by Interpreter to evaluate AQL expression
	 */
	public InputContentExpressionContentProvider(String inputContentExpression, IInterpreter interpreter, IVariableManager variableManager) {
		this.inputContentExpression = inputContentExpression;
		this.interpreter = interpreter;
		this.variableManager = variableManager;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		Object[] result = new Object[] {};
		Object expressionResult = EvalFactory.of(this.interpreter, this.variableManager).evaluate(this.inputContentExpression);
		if (expressionResult instanceof List) {
			List<?> list = (List<?>) expressionResult;
			result = list.toArray();
		}
		return result;
	}
}