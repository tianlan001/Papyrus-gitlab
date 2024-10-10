/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
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
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.containerborder.internal;

import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.ide.ui.internal.widgets.EEFContainerLifecycleManager;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFContainerBorderDescription;
import org.eclipse.sirius.common.interpreter.api.IEvaluationResult;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;

/**
 * This lifecycle manager is used to handle the EEF Container Border control,
 * used to layout widgets or controls.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
@SuppressWarnings("restriction")
public class ContainerBorderLifecycleManager extends EEFContainerLifecycleManager {

	/**
	 * The container description to manage.
	 */
	private EEFContainerBorderDescription description;

	/**
	 * The variable manager which contain variables used by Interpreter to evaluate
	 * AQL expression
	 */
	private IVariableManager variableManager;

	/**
	 * The interpreter to evaluate AQL expressions.
	 */
	private IInterpreter interpreter;

	/**
	 * Constructor.
	 * 
	 * @param description           the container description to manage.
	 * @param variableManager       the variable manager which contain variables
	 *                              used by Interpreter to evaluate AQL expression
	 * @param interpreter           the interpreter to evaluate AQL expressions
	 * @param editingContextAdapter the adapter used to modify model elements
	 */
	public ContainerBorderLifecycleManager(EEFContainerBorderDescription description, IVariableManager variableManager,
			IInterpreter interpreter, EditingContextAdapter editingContextAdapter) {
		super(description, variableManager, interpreter, editingContextAdapter);
		this.description = description;
		this.variableManager = variableManager;
		this.interpreter = interpreter;
	}

	/**
	 * Returns the description of the control.
	 *
	 * @return The description of the control
	 */
	public EEFContainerBorderDescription getControlDescription() {
		return (EEFContainerBorderDescription) description;
	}

	@Override
	protected String getBorderLabel() {
		IEvaluationResult evaluationResult = interpreter.evaluateExpression(variableManager.getVariables(),
				getControlDescription().getLabelExpression());
		if (evaluationResult.success()) {
			return evaluationResult.asString();
		}
		return ""; //$NON-NLS-1$
	}

	@Override
	protected boolean isBorderedContainer() {
		return true;
	}

}
