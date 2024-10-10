/*******************************************************************************
 * Copyright (c) 2022, 2023 CEA LIST, Obeo
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
package org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.editablereference.internal;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.eef.common.ui.api.IEEFFormContainer;
import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.utils.EvalFactory;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.EEFExtReferenceUIPlugin;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.EEFExtSingleReferenceLifecycleManager;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.IFilteredLabelProvider;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.papyrus.sirius.properties.editor.properties.advanced.controls.utils.PapyrusReferenceUtils;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription;
import org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.Activator;
import org.eclipse.papyrus.uml.tools.providers.UMLFilteredLabelProvider;
import org.eclipse.sirius.common.interpreter.api.IEvaluationResult;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

/**
 * This lifecycle manager is used to handle the EEF Extension editable reference
 * widget for mono-valued EReferences.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
@SuppressWarnings("restriction")
public class SingleEditableReferenceLifecycleManager extends EEFExtSingleReferenceLifecycleManager {

	/**
	 * Title dialog message when user creates a new element with button "Add".
	 */
	private static final String CREATE_DIALOG_TITLE = "Create a new UML Element"; //$NON-NLS-1$

	/**
	 * The title of the "Browse" dialog.
	 */
	private static final String BROWSE_DIALOG_TITLE = "Browse "; //$NON-NLS-1$

	/**
	 * Tooltip of the "Edit" button.
	 */
	private static final String EDIT_BUTTON_TOOLTIP = "Edit the selected value"; //$NON-NLS-1$

	/**
	 * The edit button used to edit the referenced element.
	 */
	protected Button editButton;

	/**
	 * The listener for the edit button.
	 */
	protected ButtonSelectionListener editButtonListener;

	/**
	 * Constructor.
	 * 
	 * @param description
	 *            the reference description to manage
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
	public SingleEditableReferenceLifecycleManager(EEFExtEditableReferenceDescription description, EObject target,
			EReference eReference, IVariableManager variableManager, IInterpreter interpreter,
			EditingContextAdapter editingContextAdapter) {
		super(description, target, eReference, variableManager, interpreter, editingContextAdapter);
	}

	@Override
	protected void createMainControl(Composite parent, IEEFFormContainer formContainer) {
		super.createMainControl(parent, formContainer);
		this.controller = new EditableReferenceController(this, getWidgetDescription(), this.variableManager,
				this.interpreter, this.editingContextAdapter, this.isEnabled());
	}

	@Override
	protected EditableReferenceController getController() {
		return (EditableReferenceController) this.controller;
	}

	@Override
	protected void setEnabled(boolean isEnabled) {
		if (this.browseButton != null && !this.browseButton.isDisposed()) {
			this.browseButton.setEnabled(isEnabled);
		}
		if (this.addButton != null && !this.addButton.isDisposed()) {
			this.addButton.setEnabled(isEnabled);
		}
		boolean referenceExists = target.eGet(eReference) != null;
		if (this.removeButton != null && !this.removeButton.isDisposed()) {
			if (referenceExists) {
				removeButton.setEnabled(isEnabled);
			} else {
				removeButton.setEnabled(false);
			}
		}
		if (this.editButton != null && !this.editButton.isDisposed()) {
			if (referenceExists) {
				editButton.setEnabled(isEnabled);
			} else {
				editButton.setEnabled(false);
			}
		}
	}

	@Override
	protected EEFExtEditableReferenceDescription getWidgetDescription() {
		return (EEFExtEditableReferenceDescription) this.description;
	}

	@Override
	public void aboutToBeShown() {
		super.aboutToBeShown();
		this.initializeEditButton();
	}

	@Override
	public void aboutToBeHidden() {
		super.aboutToBeHidden();
		this.removeListener(this.editButton, this.editButtonListener);
	}

	@Override
	protected void createButtons(Composite parent) {
		int enabledButtons = getButtonsNumber();
		parent.setLayout(new GridLayout(enabledButtons, true));
		if (!this.eReference.isContainment()) {
			Image browseImage = ExtendedImageRegistry.INSTANCE.getImage(EEFExtReferenceUIPlugin.getPlugin()
					.getImage(EEFExtReferenceUIPlugin.Implementation.BROWSE_ICON_PATH));
			this.browseButton = this.createButton(parent, browseImage);
		}
		Image addImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.ADD_ICON_PATH));
		this.addButton = this.createButton(parent, addImage);
		if (!this.eReference.isRequired() && !this.eReference.isContainer()) {
			Image removeImage = ExtendedImageRegistry.INSTANCE.getImage(EEFExtReferenceUIPlugin.getPlugin()
					.getImage(EEFExtReferenceUIPlugin.Implementation.REMOVE_ICON_PATH));
			this.removeButton = this.createButton(parent, removeImage);
		}
		Image editImage = ExtendedImageRegistry.INSTANCE.getImage(Activator.getDefault().getImage(Activator.EDIT_ICON)); // $NON-NLS-1$
		this.editButton = this.createButton(parent, editImage);
	}

	@Override
	protected int getButtonsNumber() {
		int enabledButtons = 0;
		// Browse button displayed or not
		if (!this.eReference.isContainment()) {
			enabledButtons = enabledButtons + 1;
		}
		// Remove button displayed or not
		if (!this.eReference.isRequired() && !this.eReference.isContainer()) {
			enabledButtons = enabledButtons + 1;
		}
		enabledButtons = enabledButtons + 2; // Add and Edit buttons are always enabled.
		return enabledButtons;
	}

	/**
	 * Initializes the edit button.
	 */
	protected void initializeEditButton() {
		this.editButtonListener = new ButtonSelectionListener(this.editingContextAdapter,
				() -> this.editButtonCallback());
		this.editButton.addSelectionListener(editButtonListener);
		this.editButton.setToolTipText(EDIT_BUTTON_TOOLTIP);
	}

	/**
	 * This method is called once the edit button is clicked in order to open the
	 * "edit dialog".
	 */
	protected void editButtonCallback() {
		if (target != null) {
			Object value = target.eGet(eReference);
			if (value instanceof EObject) {
				EObject eObject = (EObject) value;
				int returnCode = getController().getPropertiesUtils().displayEditionProperties(
						this.editingContextAdapter, eObject, this.variableManager, this.interpreter);
				if (returnCode == Window.OK) {
					this.refreshWithResize();
				}
			}
		}
		return;
	}

	/**
	 *
	 * {@inheritDoc} Overridden to wrap the Image and Label in a "clickable"
	 * Composite, in order to allow double clicking on the element.
	 *
	 * @see org.eclipse.eef.ide.ui.ext.widgets.reference.internal.EEFExtSingleReferenceLifecycleManager#createLabel(Composite)(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createLabel(Composite parent) {
		// The parent's GridLayout was 3: one column for the image, one for the label
		// and one for the buttonsComposite. The image and label will be wrapped here in
		// a "clickable" Composite, so the parent's layout must be set to 2 columns.
		GridLayout parentLayout = (GridLayout) parent.getLayout();
		parentLayout.numColumns = parentLayout.numColumns - 1;

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;

		// This composite is used to fill the horizontal space without having the BORDER
		// style for the whole widget.
		Composite horizontalIntermediateComposite = new Composite(parent, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.verticalSpacing = 0;
		gridLayout.marginHeight = 0;
		horizontalIntermediateComposite.setLayout(gridLayout);
		horizontalIntermediateComposite.setLayoutData(gridData);

		GridData gd = new GridData();
		gd.grabExcessHorizontalSpace = false;
		gd.horizontalAlignment = SWT.FILL;

		// Create the "clickable" area.
		Composite clickableComposite = new Composite(horizontalIntermediateComposite, SWT.NONE);
		GridLayout clickableGridLayout = new GridLayout(2, false);
		clickableGridLayout.verticalSpacing = 0;
		clickableGridLayout.marginHeight = 0;
		clickableComposite.setLayout(clickableGridLayout);
		clickableComposite.setLayoutData(gd);

		super.createLabel(clickableComposite);

		MouseListener listener = new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				if (isEnabled()) {
					editingContextAdapter.performModelChange(() -> {
						editButtonCallback();
					});
				}
			}
		};
		clickableComposite.addMouseListener(listener);
		if (this.image != null) {
			this.image.addMouseListener(listener);
		}
		if (this.text != null) {
			this.text.addMouseListener(listener);
		}
		if (this.hyperlink != null) {
			this.hyperlink.addMouseListener(listener);
		}
	}

	@Override
	public void refresh() {
		super.refresh();
		LabelProviderService service = null;
		Object value = this.target.eGet(this.eReference);
		if (value instanceof EObject) {
			try {
				ServicesRegistry registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(this.target);
				service = registry.getService(LabelProviderService.class);
			} catch (ServiceException e) {
				Activator.getDefault().getLog()
						.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to find the corresponding service.")); //$NON-NLS-1$
			}

			if (service != null) {
				ILabelProvider labelProvider = service.getLabelProvider(this.target);
				if (labelProvider != null) {
					String onClickExpression = Optional.ofNullable(this.description.getOnClickExpression()).orElse(""); //$NON-NLS-1$
					if (onClickExpression.isEmpty()) {
						this.text.setText(labelProvider.getText(value));
					} else {
						this.hyperlink.setText(labelProvider.getText(value));
						this.hyperlink.setData(value);
					}
				}
			}
		}
		this.variableManager.put(EEFExpressionUtils.EEFList.SELECTION, this.target.eGet(eReference));
		this.setEnabled(this.isEnabled());

	}

	/**
	 * Refresh the widget, buttons and size of the clickable composite which
	 * contains the label.
	 */
	private void refreshWithResize() {
		this.refresh();
		text.requestLayout();
	}

	/**
	 * Update the reference of the widget target.
	 * If Browse expression is specified, the Browse Expression is evaluated for this widget.
	 * Otherwise, by default, new reference is set to the reference.
	 * 
	 * @param newRef
	 *            {@link EObject} to set to the reference
	 */
	private void updateTargetReference(Object newRef) {
		String browseExpression = Optional.ofNullable(this.getController().getBrowseExpression()).orElse(""); //$NON-NLS-1$
		if (!browseExpression.isEmpty()) {
			this.variableManager.put(EEFExpressionUtils.EEFList.SELECTION, newRef);
			EvalFactory.of(this.interpreter, this.variableManager).call(browseExpression);
		} else {
			this.target.eSet(this.eReference, newRef);
		}
	}

	/**
	 * Evaluates the Create Expression for this widget.
	 * 
	 * @return the object created by the Create Expression
	 */
	private EObject evaluateCreateExpression() {
		EObject objectCreated = null;
		String createExpression = getWidgetDescription().getCreateExpression();
		if (createExpression != null && !createExpression.isBlank()) {
			IEvaluationResult result = this.interpreter.evaluateExpression(variableManager.getVariables(), createExpression);
			if (result.success()) {
				objectCreated = (EObject) result.getValue();
			}
		}
		return objectCreated;
	}

	@Override
	protected void addButtonCallback() {
		List<Object> types = this.getController().getPropertiesUtils().getAllPossibleTypes(this.composedAdapterFactory,
				this.editingContextAdapter, this.target, this.eReference);
		int returnCode = Window.CANCEL;
		EObject objectCreated = null;
		if (types.size() == 1 && this.eReference.isContainment()) {
			this.variableManager.put(PapyrusReferenceUtils.NEW_ELEMENT_TYPE_NAME, ((EObject) types.get(0)).eClass().getName());
			this.variableManager.put(PapyrusReferenceUtils.NEW_ELEMENT_CONTAINER, this.target);
			this.variableManager.put(PapyrusReferenceUtils.NEW_ELEMENT_CONTAINER_FEATURE_NAME, this.eReference.getName());
			objectCreated = evaluateCreateExpression();
			if (objectCreated != null) {
				returnCode = this.getController().getPropertiesUtils().displayCreationProperties(editingContextAdapter,
						objectCreated, variableManager, interpreter);
			}
		} else {
			PapyrusEEFExtEObjectCreationWizard wizard = new PapyrusEEFExtEObjectCreationWizard(this.target,
					this.eReference, this.editingContextAdapter);
			wizard.setWindowTitle(CREATE_DIALOG_TITLE);
			WizardDialog wizardDialog = new WizardDialog(this.image.getShell(), wizard);
			returnCode = wizardDialog.open();
			if (returnCode != Window.OK) {
				throw new OperationCanceledException();
			}

			this.variableManager.put(PapyrusReferenceUtils.NEW_ELEMENT_TYPE_NAME, wizard.getSelectedTypeName());
			this.variableManager.put(PapyrusReferenceUtils.NEW_ELEMENT_CONTAINER, wizard.getSelectedEContainer());
			this.variableManager.put(PapyrusReferenceUtils.NEW_ELEMENT_CONTAINER_FEATURE_NAME, wizard.getSelectedEContainerFeature().getName());
			objectCreated = evaluateCreateExpression();
			if (objectCreated != null) {
				returnCode = this.getController().getPropertiesUtils().displayCreationProperties(editingContextAdapter,
						objectCreated, variableManager, interpreter);
				if (returnCode != Window.OK) {
					throw new OperationCanceledException();
				}
				updateTargetReference(objectCreated);
			}
		}
		if (returnCode == Window.OK) {
			this.refreshWithResize();
		} else {
			throw new OperationCanceledException();
		}
	}

	@Override
	protected void browseButtonCallback() {
		TreeSelectorDialog dialog = new TreeSelectorDialog(Display.getCurrent().getActiveShell());
		IFilteredLabelProvider labelProvider = new UMLFilteredLabelProvider();
		dialog.setLabelProvider(labelProvider);
		ITreeContentProvider provider = new CustomUMLContentProvider(target, eReference);
		dialog.setContentProvider(provider);
		dialog.setInitialElementSelections(Collections.singletonList(null));
		dialog.setTitle(BROWSE_DIALOG_TITLE + eReference.getEType().getName());
		if (dialog.open() == Window.OK && dialog.getResult().length == 1) {
			Object[] result = dialog.getResult();
			updateTargetReference(result[0]);
			this.refreshWithResize();
		} else {
			throw new OperationCanceledException();
		}
	}

	@Override
	protected void removeButtonCallback() {
		String removeExpression = getWidgetDescription().getRemoveExpression();
		EvalFactory.of(this.interpreter, this.variableManager).call(removeExpression);
		this.refreshWithResize();
	}

	@Override
	protected void initializeRemoveButton() {
		if (!this.eReference.isRequired() && !this.eReference.isContainer()) {
			super.initializeRemoveButton();
		}
	}
}
