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
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.languageexpression.internal;

import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.controllers.AbstractEEFWidgetController;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFLanguageExpressionDescription;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;

/**
 * The controller of the Language Expression widget is used to manage the
 * behavior of the Language Expression Widget.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
public class LanguageExpressionController extends AbstractEEFWidgetController {

	/**
	 * The description of the language expression.
	 */
	private EEFLanguageExpressionDescription description;

	/**
	 * Constructor.
	 * 
	 * @param description     the description of the language expression.
	 * @param variableManager the variable manager which contain variables used by
	 *                        Interpreter to evaluate AQL expression
	 * @param interpreter     the interpreter to evaluate AQL expressions
	 * @param contextAdapter  the adapter used to modify model elements
	 */
	public LanguageExpressionController(EEFLanguageExpressionDescription description, IVariableManager variableManager,
			IInterpreter interpreter, EditingContextAdapter contextAdapter) {
		super(variableManager, interpreter, contextAdapter);
		this.description = description;
	}

	@Override
	protected EEFLanguageExpressionDescription getDescription() {
		return this.description;
	}

}
