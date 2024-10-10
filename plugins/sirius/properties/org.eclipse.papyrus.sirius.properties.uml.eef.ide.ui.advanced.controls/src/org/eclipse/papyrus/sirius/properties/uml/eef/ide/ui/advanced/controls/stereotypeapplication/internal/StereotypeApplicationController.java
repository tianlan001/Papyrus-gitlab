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
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.stereotypeapplication.internal;

import java.util.Optional;

import org.eclipse.eef.EEFWidgetDescription;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.controllers.AbstractEEFWidgetController;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFStereotypeApplicationDescription;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;

/**
 * The controller of the Stereotype Application widget is used to manage the
 * behavior of the Stereotype Application Widget.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class StereotypeApplicationController extends AbstractEEFWidgetController {

	/**
	 * The description of the stereotype application.
	 */
	private EEFStereotypeApplicationDescription description;

	/**
	 * 
	 * Constructor.
	 *
	 * @param description
	 *            the description of the stereotype application.
	 * @param variableManager
	 *            the variable manager which contain variables used by
	 *            Interpreter to evaluate AQL expression
	 * @param interpreter
	 *            the interpreter to evaluate AQL expressions
	 * @param contextAdapter
	 *            the adapter used to modify model elements
	 */
	public StereotypeApplicationController(EEFStereotypeApplicationDescription description, IVariableManager variableManager, IInterpreter interpreter, EditingContextAdapter editingContextAdapter) {
		super(variableManager, interpreter, editingContextAdapter);
		this.description = description;
	}

	@Override
	protected EEFWidgetDescription getDescription() {
		return null;
	}

	private EEFStereotypeApplicationDescription getWidgetDescription() {
		return this.description;
	}

	@Override
	protected EObject getValidationRulesContainer() {
		return this.getWidgetDescription();
	}

	@Override
	public void refresh() {
		super.refresh();

		String labelExpression = this.getWidgetDescription().getLabelExpression();
		Optional.ofNullable(this.newLabelConsumer).ifPresent(consumer -> {
			this.newEval().logIfInvalidType(String.class).call(labelExpression, consumer);
		});
	}

	@Override
	public void computeHelp() {
		String helpExpression = this.getWidgetDescription().getHelpExpression();
		Optional.ofNullable(this.newHelpConsumer).ifPresent(consumer -> {
			this.newEval().logIfInvalidType(String.class).call(helpExpression, consumer);
		});
	}

}
