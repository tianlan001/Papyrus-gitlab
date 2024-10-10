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
import java.util.Optional;

import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFInputContentPapyrusReferenceDescription;
import org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.editablereference.internal.MultipleEditableReferenceLifecycleManager;
import org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.editablereference.internal.PapyrusEEFExtEObjectCreationWizard;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.swt.widgets.Composite;

/**
 * This lifecycle manager is used to handle the EEF Input Content Papyrus Reference Widget to displayed
 * and managed many elements in a table.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@SuppressWarnings("restriction")
public class InputContentPapyrusReferenceLifecycleManager extends MultipleEditableReferenceLifecycleManager {

	/**
	 * Empty String constant.
	 */
	private static final String EMPTY_STRING = ""; //$NON-NLS-1$

	/**
	 * Constructor.
	 * 
	 * @param description
	 *            the input content papyrus reference description to manage
	 * @param target
	 *            the owner of the reference
	 * @param eReference
	 *            the reference to display
	 * @param variableManager
	 *            the variable manager which contain variables
	 *            used by Interpreter to evaluate AQL expression
	 * @param interpreter
	 *            the interpreter to evaluate AQL expressions
	 * @param editingContextAdapter
	 *            the adapter used to modify model elements
	 */
	public InputContentPapyrusReferenceLifecycleManager(EEFInputContentPapyrusReferenceDescription description,
			EObject target, EReference eReference, IVariableManager variableManager, IInterpreter interpreter,
			EditingContextAdapter editingContextAdapter) {
		super(description, target, eReference, variableManager, interpreter, editingContextAdapter);
	}

	@Override
	protected void createTable(Composite parent) {
		super.createTable(parent);
		String inputContentExpression = getInputContentExpression();
		if (!inputContentExpression.isEmpty()) {
			this.tableViewer.setContentProvider(new InputContentExpressionContentProvider(inputContentExpression, this.interpreter, this.variableManager));
		}
	}

	/**
	 * Gets the value of the Input Content Expression.
	 * 
	 * @return the value of the Input Content Expression.
	 */
	private String getInputContentExpression() {
		return Optional.ofNullable(this.getWidgetDescription().getInputContentExpression()).orElse(EMPTY_STRING);
	}

	@Override
	protected EEFInputContentPapyrusReferenceDescription getWidgetDescription() {
		return (EEFInputContentPapyrusReferenceDescription) this.description;
	}

	@Override
	protected void addButtonCallback() {
		List<Object> types = this.getController().getPropertiesUtils().getAllPossibleTypes(this.composedAdapterFactory,
				this.editingContextAdapter, this.target, this.eReference);
		int returnCode = Window.CANCEL;
		EObject objectCreated = null;
		String inputContentExpression = getInputContentExpression();
		if (types.size() == 1 && (this.eReference.isContainment() && inputContentExpression.isEmpty())) {
			objectCreated = createUmlElement(this.target, this.eReference, ((EObject) types.get(0)).eClass().getName());
			if (objectCreated != null) {
				returnCode = this.getController().getPropertiesUtils().displayCreationProperties(editingContextAdapter,
						objectCreated, variableManager, interpreter);
			}
		} else {
			PapyrusEEFExtEObjectCreationWizard wizard = new PapyrusEEFExtEObjectCreationWizard(this.target,
					this.eReference, this.editingContextAdapter);
			wizard.setWindowTitle(CREATE_DIALOG_TITLE);
			WizardDialog wizardDialog = new WizardDialog(this.tableViewer.getTable().getShell(), wizard);
			returnCode = wizardDialog.open();
			if (returnCode != Window.OK) {
				throw new OperationCanceledException();
			}
			objectCreated = createUmlElement(wizard.getSelectedEContainer(), wizard.getSelectedEContainerFeature(), wizard.getSelectedTypeName());
			if (objectCreated != null) {
				returnCode = this.getController().getPropertiesUtils().displayCreationProperties(editingContextAdapter,
						objectCreated, variableManager, interpreter);
				if (returnCode != Window.OK) {
					throw new OperationCanceledException();
				}
				if (!this.eReference.isContainment() || !inputContentExpression.isEmpty()) {
					// in case of non-containment reference, the new element must be added to the reference
					// of the target in addition to the reference of its container
					updateTargetReference(objectCreated, false);
				}
			}
		}
		if (returnCode == Window.OK) {
			this.refresh();
			this.tableViewer.getTable().deselectAll();
			this.setEnabled(this.isEnabled());
		} else {
			throw new OperationCanceledException();
		}
	}

	/**
	 * Overridden to disable buttons if no expression is provided.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void setEnabled(boolean isEnabled) {
		super.setEnabled(isEnabled);

		String createExpression = Optional.ofNullable(this.getWidgetDescription().getCreateExpression()).orElse(EMPTY_STRING);
		String browseExpression = Optional.ofNullable(this.getWidgetDescription().getBrowseExpression()).orElse(EMPTY_STRING);
		String removeExpression = Optional.ofNullable(this.getWidgetDescription().getRemoveExpression()).orElse(EMPTY_STRING);

		if (this.addButton != null && !this.addButton.isDisposed() && createExpression.isBlank()) {
			this.addButton.setEnabled(false);
		}
		if (this.browseButton != null && !this.browseButton.isDisposed() && browseExpression.isBlank()) {
			this.browseButton.setEnabled(false);
		}
		if (this.removeButton != null && !this.removeButton.isDisposed() && removeExpression.isBlank()) {
			this.removeButton.setEnabled(false);
		}
	}
}
