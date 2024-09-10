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

package org.eclipse.papyrus.infra.emf.types.ui.properties.widgets;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.papyrus.infra.emf.types.ui.properties.messages.Messages;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.MultipleReferenceEditor;
import org.eclipse.swt.widgets.Composite;

/**
 * An AbstractCustomMultipleEditor which doesn't have edit button and the addAction have to be implemented. It's permits to avoid not relevant popup.
 * Useful to be used with multipleRefeence with properties view.
 */
public abstract class AbstractCustomMultipleEditor extends MultipleReferenceEditor {

	/**
	 * Constructor.
	 */
	public AbstractCustomMultipleEditor(final Composite parent, final int style) {
		super(parent, style);
	}

	/**
	 * Creates the Add/Remove controls,
	 * and the Up/Down controls if the collection is ordered
	 */
	@Override
	protected void createListControls() {
		up = createButton(Activator.getDefault().getImage("/icons/Up_12x12.gif"), Messages.AbstractCustomMultipleEditor_MoveUpTooltip); //$NON-NLS-1$
		down = createButton(Activator.getDefault().getImage("/icons/Down_12x12.gif"), Messages.AbstractCustomMultipleEditor_MoveDownTooltip); //$NON-NLS-1$
		add = createButton(Activator.getDefault().getImage("/icons/Add_12x12.gif"), Messages.AbstractCustomMultipleEditor_CreateTooltip); //$NON-NLS-1$
		remove = createButton(Activator.getDefault().getImage("/icons/Delete_12x12.gif"), Messages.AbstractCustomMultipleEditor_DeleteTooltip); //$NON-NLS-1$
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.MultipleValueEditor#addAction()
	 */
	@Override
	protected void addAction() {
		// To be implement
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
