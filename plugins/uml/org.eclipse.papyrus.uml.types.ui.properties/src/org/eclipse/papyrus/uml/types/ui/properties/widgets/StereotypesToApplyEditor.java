/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.types.ui.properties.widgets;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.MultipleReferenceEditor;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceFactory;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdvicePackage;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply;
import org.eclipse.papyrus.uml.types.ui.properties.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * A {@link MultipleReferenceEditor} for {@link StereotypeToApply}.
 */
public class StereotypesToApplyEditor extends MultipleReferenceEditor {

	/** The add menu. */
	private Menu addMenu;

	/**
	 * Constructor.
	 */
	public StereotypesToApplyEditor(final Composite parent, final int style) {
		super(parent, style);
	}

	/**
	 * Creates the Add/Remove controls,
	 * and the Up/Down controls if the collection is ordered
	 */
	@Override
	protected void createListControls() {
		up = createButton(Activator.getDefault().getImage("/icons/Up_12x12.gif"), Messages.StereotypesToApplyEditor_MoveSelectedElementsUp); //$NON-NLS-1$
		down = createButton(Activator.getDefault().getImage("/icons/Down_12x12.gif"), Messages.StereotypesToApplyEditor_MoveSelectedElementsDown); //$NON-NLS-1$
		add = createButton(Activator.getDefault().getImage("/icons/Add_12x12.gif"), Messages.StereotypesToApplyEditor_AddElements); //$NON-NLS-1$
		remove = createButton(Activator.getDefault().getImage("/icons/Delete_12x12.gif"), Messages.StereotypesToApplyEditor_RemoveSelectedElements); //$NON-NLS-1$
	}

	/**
	 * Populate the add menu.
	 */
	protected void populateAddMenu() {
		createMenuItem(Activator.getDefault().getImage(org.eclipse.papyrus.uml.types.core.Activator.PLUGIN_ID, "/icons/full/obj16/StereotypeToApply.gif"), //$NON-NLS-1$
				Messages.StereotypesToApplyEditor_AddStereotypeToApplyTitle, Messages.StereotypesToApplyEditor_AddStereotypeToApplyTooltip,
				ApplyStereotypeAdvicePackage.eINSTANCE.getApplyStereotypeAdviceConfiguration_StereotypesToApply());

		Object selection = ((ITreeSelection) getViewer().getSelection()).getFirstElement();
		// Only propose Feature to set creation if there is a selection
		if (null != selection) {
			createMenuItem(Activator.getDefault().getImage(org.eclipse.papyrus.uml.types.core.Activator.PLUGIN_ID, "/icons/full/obj16/FeatureToSet.gif"), //$NON-NLS-1$
					Messages.StereotypesToApplyEditor_AddFeatureToSetTitle, Messages.StereotypesToApplyEditor_AddFeatureToSetTooltip,
					ApplyStereotypeAdvicePackage.eINSTANCE.getStereotypeToApply_FeaturesToSet());
		}
	}


	/**
	 * Create {@link MenuItem} for actions menu.
	 * 
	 * @param image
	 *            the image to set.
	 * @param text
	 *            the text to set
	 * @param factory
	 *            the action id binded to the Menu item
	 */
	protected void createMenuItem(final Image image, final String text, final String tooltipMessage, final Object feature) {
		MenuItem stereotypeMenuItem = new MenuItem(addMenu, SWT.NONE);
		stereotypeMenuItem.setText(text);
		stereotypeMenuItem.setImage(image);
		stereotypeMenuItem.setToolTipText(tooltipMessage);
		stereotypeMenuItem.setData(feature);
		stereotypeMenuItem.addSelectionListener(this);
	}

	/**
	 * create popup menu to display available options.
	 * <br>
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.MultipleValueEditor#addAction()
	 */
	@Override
	protected void addAction() {
		Object selection = ((ITreeSelection) getViewer().getSelection()).getFirstElement();
		if (null != selection) {
			addMenu = new Menu(add);
			populateAddMenu();
			addMenu.setVisible(true);
		} else {
			// if no selected element
			EReference feature = ApplyStereotypeAdvicePackage.eINSTANCE.getApplyStereotypeAdviceConfiguration_StereotypesToApply();
			EObject container = (EObject) getContextElement();
			StereotypeToApply newObject = ApplyStereotypeAdviceFactory.eINSTANCE.createStereotypeToApply();
			addNewObject(feature, container, newObject);
			// Refresh
			getViewer().refresh();
			// Select new object
			getViewer().setSelection(new StructuredSelection(newObject), true);
		}
	}

	/**
	 * Override remove action to manage {@link StereotypeToApply} case.
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.MultipleValueEditor#removeAction()
	 */
	@Override
	protected void removeAction() {
		Object selection = ((ITreeSelection) getViewer().getSelection()).getFirstElement();
		if (selection instanceof FeatureToSet) {
			StereotypeToApply container = (StereotypeToApply) ((FeatureToSet) selection).eContainer();
			Command command = new RemoveCommand(getEditingDomain(), container.getFeaturesToSet(), selection);
			if (null != command && command.canExecute()) {
				getEditingDomain().getCommandStack().execute(command);
				// Refresh
				getViewer().refresh();
				// Select new object
				getViewer().setSelection(new StructuredSelection(container), true);
			}
		} else {
			super.removeAction();
		}
	}

	/**
	 * Manage add action selection for {@link StereotypeToApply} and {@link FeatureToSet}.
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.MultipleValueEditor#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(final SelectionEvent event) {
		super.widgetSelected(event);
		if ((event.getSource() instanceof MenuItem)) {
			// Get command for the add actions
			MenuItem selectedItem = (MenuItem) event.getSource();
			Object data = selectedItem.getData();
			EObject container = null;
			Object newObject = null;
			Object selection = ((ITreeSelection) getViewer().getSelection()).getFirstElement();

			// New Stereotype to apply
			if (ApplyStereotypeAdvicePackage.eINSTANCE.getApplyStereotypeAdviceConfiguration_StereotypesToApply().equals(data)) {
				// Create the new object
				newObject = ApplyStereotypeAdviceFactory.eINSTANCE.createStereotypeToApply();

				// get the container
				container = (EObject) getContextElement();
			} else
			// new Feature to set
			if (ApplyStereotypeAdvicePackage.eINSTANCE.getStereotypeToApply_FeaturesToSet().equals(data)) {
				// Create the new object
				newObject = ApplyStereotypeAdviceFactory.eINSTANCE.createFeatureToSet();

				// get the container
				if (selection instanceof StereotypeToApply) {
					container = ((EObject) selection);
				} else if (selection instanceof FeatureToSet) {
					container = ((EObject) selection).eContainer();
				}
			}
			if (data instanceof EStructuralFeature && null != container && null != newObject) {
				// Add the new object in the list
				addNewObject((EStructuralFeature) data, container, newObject);
				// Refresh
				getViewer().refresh();
				// Expands the container to be sure that the newObject should be selectable
				getViewer().expandToLevel(container, 2);
				// Select new object
				getViewer().setSelection(new StructuredSelection(newObject), true);
			}
		}
	}


	/**
	 * Adds a new object in through a command and execute it.
	 * 
	 * @param structuralFeature
	 *            The structural Feature
	 * @param container
	 *            The container
	 * @param newObject
	 *            the new object to add.
	 */
	protected void addNewObject(final EStructuralFeature structuralFeature, final EObject container, final Object newObject) {
		Command command = null;
		// Create the add command
		EditingDomain editingDomain = getEditingDomain();
		if (null != container && null != structuralFeature && null != newObject) {
			command = new AddCommand(editingDomain, container, (EStructuralFeature) structuralFeature, newObject);
		}

		// Check and execute command
		if (null != command && command.canExecute()) {
			editingDomain.getCommandStack().execute(command);
		}
	}

	/**
	 * Gets the editing domain of the context EObject.
	 */
	protected EditingDomain getEditingDomain() {
		return AdapterFactoryEditingDomain.getEditingDomainFor((EObject) getContextElement());
	}
}
