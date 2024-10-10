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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.eef.common.ui.api.IEEFFormContainer;
import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.utils.EvalFactory;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.EEFExtMultipleReferenceLifecycleManager;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.EEFExtReferenceUIPlugin;
import org.eclipse.eef.ide.ui.ext.widgets.reference.internal.Messages;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider;
import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.widgets.editors.IElementSelector;
import org.eclipse.papyrus.infra.widgets.editors.MultipleValueSelectionDialog;
import org.eclipse.papyrus.infra.widgets.selectors.ReferenceSelector;
import org.eclipse.papyrus.infra.widgets.widgets.MultipleValueSelectionWidget;
import org.eclipse.papyrus.sirius.properties.common.utils.ContainerUtil;
import org.eclipse.papyrus.sirius.properties.editor.properties.advanced.controls.utils.PapyrusReferenceUtils;
import org.eclipse.papyrus.sirius.properties.eef.advanced.controls.eefadvancedcontrols.EEFExtEditableReferenceDescription;
import org.eclipse.papyrus.sirius.properties.uml.eef.ide.ui.advanced.controls.Activator;
import org.eclipse.papyrus.uml.tools.providers.UMLContentProvider;
import org.eclipse.papyrus.uml.tools.providers.UMLFilteredLabelProvider;
import org.eclipse.sirius.common.interpreter.api.IEvaluationResult;
import org.eclipse.sirius.common.interpreter.api.IInterpreter;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This lifecycle manager is used to handle the EEF Extension editable reference
 * widget for multi-valued EReferences.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 *
 */
@SuppressWarnings("restriction")
public class MultipleEditableReferenceLifecycleManager extends EEFExtMultipleReferenceLifecycleManager {

	/**
	 * Title dialog message when user creates a new element with button "Add".
	 */
	protected static final String CREATE_DIALOG_TITLE = "Create a new UML Element"; //$NON-NLS-1$

	/**
	 * The title of the "Browse" dialog.
	 */
	private static final String BROWSE_DIALOG_TITLE = "Browse "; //$NON-NLS-1$

	/**
	 * Tooltip of the "Edit" button.
	 */
	private static final String EDIT_BUTTON_TOOLTIP = "Edit the selected value"; //$NON-NLS-1$

	/**
	 * The edit button used to edit the selected element in the list of referenced
	 * elements.
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
	public MultipleEditableReferenceLifecycleManager(EEFExtEditableReferenceDescription description, EObject target,
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
		Table table = tableViewer.getTable();
		boolean manyItems = table.getItemCount() > 1;
		TableItem[] selection = table.getSelection();
		boolean elementIsSelected = selection != null && selection.length > 0;
		if (this.addButton != null && !this.addButton.isDisposed()) {
			if (tableViewer.getTable().getItemCount() < this.eReference.getUpperBound()
					|| this.eReference.getUpperBound() == -1) {
				this.addButton.setEnabled(isEnabled);
			} else {
				this.addButton.setEnabled(false);
			}
		}
		if (this.removeButton != null && !this.removeButton.isDisposed()) {
			if (elementIsSelected) {
				this.removeButton.setEnabled(isEnabled);
			} else {
				this.removeButton.setEnabled(false);
			}
		}
		if (this.browseButton != null && !this.browseButton.isDisposed()) {
			if (tableViewer.getTable().getItemCount() < this.eReference.getUpperBound()
					|| this.eReference.getUpperBound() == -1) {
				this.browseButton.setEnabled(isEnabled);
			} else {
				this.browseButton.setEnabled(false);
			}
		}
		if (this.editButton != null && !this.editButton.isDisposed()) {
			if (elementIsSelected) {
				this.editButton.setEnabled(isEnabled);
			} else {
				this.editButton.setEnabled(false);
			}
		}
		if (this.upButton != null && !this.upButton.isDisposed()) {
			if (manyItems && elementIsSelected && table.indexOf(selection[0]) > 0) {
				this.upButton.setEnabled(isEnabled);
			} else {
				this.upButton.setEnabled(false);
			}
		}
		if (this.downButton != null && !this.downButton.isDisposed()) {
			if (manyItems && elementIsSelected && table.indexOf(selection[0]) < table.getItemCount() - 1) {
				this.downButton.setEnabled(isEnabled);
			} else {
				this.downButton.setEnabled(false);
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
		this.tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				if (tableViewer.getSelection() instanceof IStructuredSelection) {
					IStructuredSelection structuredSelection = (IStructuredSelection) tableViewer.getSelection();
					variableManager.put(EEFExpressionUtils.EEFList.SELECTION, selectionToList(structuredSelection));
					setEnabled(isEnabled());
				}
			}
		});
		this.initializeEditButton();
	}

	@Override
	public void aboutToBeHidden() {
		super.aboutToBeHidden();
		this.removeListener(this.editButton, this.editButtonListener);
	}

	@Override
	protected void createTable(Composite parent) {
		super.createTable(parent);
		LabelProviderService service = null;
		try {
			ServicesRegistry registry = ServiceUtilsForEObject.getInstance().getServiceRegistry(this.target);
			service = registry.getService(LabelProviderService.class);
		} catch (ServiceException e) {
			Activator.getDefault().getLog()
					.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Unable to find the corresponding service.")); //$NON-NLS-1$
		}
		if (service != null) {
			ILabelProvider labelProvider = service.getLabelProvider(this.target);
			if (labelProvider instanceof IStyledLabelProvider) {
				IStyledLabelProvider styledprovider = (IStyledLabelProvider) labelProvider;
				this.tableViewer.setLabelProvider(styledprovider);
			} else {
				this.tableViewer.setLabelProvider(new DelegatingStyledCellLabelProvider(
						new AdapterFactoryLabelProvider.StyledLabelProvider(this.composedAdapterFactory, this.tableViewer)));
			}
		}
	}

	@Override
	protected void createButtons(Composite parent) {
		if (this.eReference.isOrdered()) {
			Image upImage = ExtendedImageRegistry.INSTANCE.getImage(
					EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.UP_ICON_PATH));
			this.upButton = this.createButton(parent, upImage);
			Image downImage = ExtendedImageRegistry.INSTANCE.getImage(EEFExtReferenceUIPlugin.getPlugin()
					.getImage(EEFExtReferenceUIPlugin.Implementation.DOWN_ICON_PATH));
			this.downButton = this.createButton(parent, downImage);
		}
		if (!this.eReference.isContainment()) {
			Image browseImage = ExtendedImageRegistry.INSTANCE.getImage(EEFExtReferenceUIPlugin.getPlugin()
					.getImage(EEFExtReferenceUIPlugin.Implementation.BROWSE_ICON_PATH));
			this.browseButton = this.createButton(parent, browseImage);
		}
		Image addImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.ADD_ICON_PATH));
		this.addButton = this.createButton(parent, addImage);
		Image removeImage = ExtendedImageRegistry.INSTANCE.getImage(
				EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.REMOVE_ICON_PATH));
		this.removeButton = this.createButton(parent, removeImage);
		Image editImage = ExtendedImageRegistry.INSTANCE.getImage(Activator.getDefault().getImage(Activator.EDIT_ICON)); // $NON-NLS-1$
		this.editButton = this.createButton(parent, editImage);
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
		if (tableViewer.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection selection = (IStructuredSelection) this.tableViewer.getSelection();
			if (selection.size() == 1) {
				EObject element = (EObject) selection.getFirstElement();
				if (target != null) {
					int returnCode = getController().getPropertiesUtils().displayEditionProperties(
							this.editingContextAdapter, element, this.variableManager, this.interpreter);
					if (returnCode == Window.OK) {
						this.refresh();
					}
				}
			}
		}
		return;
	}

	@Override
	protected void initializeMoveButton(Direction direction) {
		if (this.eReference.isOrdered()) {
			super.initializeMoveButton(direction);
		}
	}

	@Override
	protected void addButtonCallback() {
		List<Object> types = this.getController().getPropertiesUtils().getAllPossibleTypes(this.composedAdapterFactory,
				this.editingContextAdapter, this.target, this.eReference);
		int returnCode = Window.CANCEL;
		EObject objectCreated = null;
		if (types.size() == 1 && this.eReference.isContainment()) {
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
				if (!this.eReference.isContainment()) {
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

	@Override
	protected void browseButtonCallback() {
		UMLFilteredLabelProvider labelProvider = new UMLFilteredLabelProvider();
		ReferenceSelector selector = new ReferenceSelector(true);
		selector.setLabelProvider(labelProvider);
		UMLContentProvider contentProvider = new CustomUMLContentProvider(target, eReference);
		selector.setContentProvider(contentProvider);
		IEvaluationResult labelExpressionEvaluated = interpreter.evaluateExpression(variableManager.getVariables(),
				this.getWidgetDescription().getLabelExpression());
		String value = (String) labelExpressionEvaluated.getValue();
		MultipleValueSelectionDialog dialog = new MultipleValueSelectionDialog(Display.getCurrent().getActiveShell(),
				selector, BROWSE_DIALOG_TITLE + value, true, this.eReference.isOrdered()) {
			@Override
			protected MultipleValueSelectionWidget createWidget(IElementSelector selector, boolean unique,
					boolean ordered, int upperBound) {
				return new CustomMultipleValueSelectionWidget(selector, unique, ordered, upperBound);
			}
		};
		dialog.setLabelProvider(labelProvider);
		dialog.setUpperBound(-1);
		dialog.setContextElement(this.target);
		@SuppressWarnings("unchecked") // reference is multiple with the multipleEditableReference widget
		List<Object> initialRefElementsList = (List<Object>) this.target.eGet(this.eReference);
		List<Object> copyInitialRefElementsList = new ArrayList<>(initialRefElementsList);
		Object[] initialRefElements = initialRefElementsList.toArray();
		if (initialRefElementsList.isEmpty()) {
			dialog.setInitialSelections(new Object[0]);
		} else {
			dialog.setInitialSelections(initialRefElements);
		}
		if (dialog.open() == Window.OK) {
			List<Object> dialogResult = Arrays.asList(dialog.getResult());
			updateTargetReference(dialogResult, true);
			List<Object> newResultedElements = new ArrayList<>(dialogResult);
			newResultedElements.removeAll(copyInitialRefElementsList);
			this.refresh();
			this.tableViewer.getTable().deselectAll();
			this.setEnabled(this.isEnabled());
		} else {
			throw new OperationCanceledException();
		}
	}

	/**
	 * Update the reference of the widget target.
	 * If Browse expression is specified, the Browse Expression is evaluated for this widget.
	 * Otherwise, by default, new reference is added to the reference.
	 * 
	 * @param newRef
	 *            {@link EObject} or list of {@link EObject} to add to the reference
	 * @param clearList
	 *            <code>true</code> if the reference list should be clear and fully updated, <code>false</code> otherwise.
	 */
	@SuppressWarnings("unchecked")
	protected void updateTargetReference(Object newRef, boolean clearList) {
		String browseExpression = Optional.ofNullable(this.getController().getBrowseExpression()).orElse(""); //$NON-NLS-1$
		if (!browseExpression.isEmpty()) {
			this.variableManager.put(EEFExpressionUtils.EEFList.SELECTION, newRef);
			EvalFactory.of(this.interpreter, this.variableManager).evaluate(browseExpression);
		} else {
			if (this.eReference.isMany()) {
				Object ref = this.target.eGet(this.eReference);
				if (clearList) {
					((List<Object>) ref).clear();
				}
				if (newRef instanceof List<?>) {
					((List<Object>) ref).addAll((List<?>) newRef);
				} else if (newRef instanceof EObject) {
					((List<Object>) ref).add(newRef);
				}
			} else if (!this.eReference.isMany() && newRef instanceof EObject) {
				this.target.eSet(this.eReference, newRef);
			}
		}
	}

	/**
	 * Create a new UML element.
	 * If none create expression is described for the Papyrus reference widget, default creation is called.
	 * 
	 * @return the new UML element.
	 */
	protected EObject createUmlElement(EObject container, EStructuralFeature eStructuralFeature, String typeName) {
		EObject objectCreated = null;
		String createExpression = Optional.ofNullable(this.getController().getCreateExpression()).orElse(""); //$NON-NLS-1$
		if (!createExpression.isEmpty()) {
			this.variableManager.put(PapyrusReferenceUtils.NEW_ELEMENT_TYPE_NAME, typeName);
			this.variableManager.put(PapyrusReferenceUtils.NEW_ELEMENT_CONTAINER, container);
			this.variableManager.put(PapyrusReferenceUtils.NEW_ELEMENT_CONTAINER_FEATURE_NAME, eStructuralFeature.getName());
			objectCreated = (EObject) EvalFactory.of(this.interpreter, this.variableManager).evaluate(createExpression);
		} else {
			EClassifier eClassifier = UMLPackage.eINSTANCE.getEClassifier(typeName);
			if (eClassifier instanceof EClass) {
				EClass eClass = (EClass) eClassifier;
				objectCreated = UMLFactory.eINSTANCE.create(eClass);
				ContainerUtil.addToContainer(container, eStructuralFeature, objectCreated);
			}
		}
		return objectCreated;
	}


	/**
	 * Refresh the widget and buttons.
	 */
	@Override
	public void refresh() {
		super.refresh();
		this.setEnabled(this.isEnabled());
	}

	@Override
	protected void removeButtonCallback() {
		String removeExpression = getWidgetDescription().getRemoveExpression();
		if (removeExpression != null && !removeExpression.isBlank()) {
			EvalFactory.of(this.interpreter, this.variableManager).call(removeExpression);
		} else {
			super.removeButtonCallback();
		}
		this.tableViewer.setSelection(null);
		this.refresh();
	}

	@Override
	protected void moveButtonCallback(Direction direction) {
		super.moveButtonCallback(direction);
		this.refresh();
	}

	/**
	 * Returns a list containing all the objects of the given selection.
	 *
	 * @param selection
	 *            The selection
	 * @return The objects of the given selection
	 */
	private List<Object> selectionToList(ISelection selection) {
		List<Object> objects = new ArrayList<>();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			for (Object object : structuredSelection.toArray()) {
				objects.add(object);
			}
		}
		return objects;
	}

	private class CustomMultipleValueSelectionWidget extends MultipleValueSelectionWidget {

		CustomMultipleValueSelectionWidget(IElementSelector selector, boolean unique, boolean ordered,
				int upperBound) {
			super(selector, unique, ordered, upperBound);
		}

		@Override
		protected void createRightButtonsSection(Composite parent) {
			rightButtonsSection = new Composite(parent, SWT.NONE);
			rightButtonsSection.setLayout(new GridLayout(1, true));

			up = new Button(rightButtonsSection, SWT.PUSH);
			up.setImage(ExtendedImageRegistry.INSTANCE.getImage(EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.UP_ICON_PATH)));
			up.addSelectionListener(this);
			up.setToolTipText(Messages.ReferenceUpButton_tooltipText);

			down = new Button(rightButtonsSection, SWT.PUSH);
			down.setImage(ExtendedImageRegistry.INSTANCE.getImage(EEFExtReferenceUIPlugin.getPlugin().getImage(EEFExtReferenceUIPlugin.Implementation.DOWN_ICON_PATH)));
			down.addSelectionListener(this);
			down.setToolTipText(Messages.ReferenceDownButton_tooltipText);
		}
	}
}
