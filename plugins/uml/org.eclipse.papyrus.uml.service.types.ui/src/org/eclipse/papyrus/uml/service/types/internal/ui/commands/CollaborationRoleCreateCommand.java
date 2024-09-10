/*****************************************************************************
 * Copyright (c) 2009, 2014 CEA LIST and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 410346
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.internal.ui.commands;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.type.core.commands.EditElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.uml.service.types.internal.ui.dialogs.CollaborationRoleValidator;
import org.eclipse.papyrus.uml.service.types.internal.ui.messages.Messages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.ConnectableElement;

/**
 * <pre>
 * This class provides a custom creation command for CollaborationRole.
 * CollaborationRole are references toward any {@link ConnectableElement} in the model
 * (not necessarily owned by the {@link Collaboration}) graphically represented in
 * {@link Collaboration}.
 * </pre>
 */
public class CollaborationRoleCreateCommand extends EditElementCommand {

	@Override
	public boolean canExecute() {
		return true;
	}

	/**
	 * Constructor
	 *
	 * @param req
	 *            the creation request
	 */
	public CollaborationRoleCreateCommand(CreateElementRequest req) {
		super(req.getLabel(), req.getContainer(), req);
	}

	/**
	 * <pre>
	 * The method executes the creation :
	 *  - opens a selection dialog to choose a {@link ConnectableElement} to reference as a role
	 *  - add the selection in CollaborationRole eReference of the edited {@link Collaboration}.
	 * 
	 * {@inheritDoc}
	 * </pre>
	 */
	@Override
	protected CommandResult doExecuteWithResult(final IProgressMonitor monitor, final IAdaptable info) throws ExecutionException {

		// Retrieve the edited Collaboration
		Collaboration owner = (Collaboration) getElementToEdit();

		// Create and open the selection dialog
		ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(Display.getCurrent().getActiveShell(), new AdapterFactoryLabelProvider(adapterFactory), new AdapterFactoryContentProvider(adapterFactory));

		try {
			// Set dialog parameters
			dialog.setTitle(Messages.CollaborationRoleCreateCommand_CollaborationRoleCreation_Title);
			dialog.setMessage(Messages.CollaborationRoleCreateCommand_CollaborationRoleCreation_Message);
			dialog.setAllowMultiple(false);
			dialog.setHelpAvailable(false);
			// ConnectableElement from the whole model can be selected as role.
			dialog.setInput(owner.getModel());
			// CollaborationRoleValidator ensures that an the selected element is a ConnectableElement.
			dialog.setValidator(new CollaborationRoleValidator());

			dialog.open();
		} finally {
			adapterFactory.dispose();
		}

		// If a ConnectableElement has been selected, complete command execution
		// using selection as the "newly created" element and make the edited
		// Collaboration reference it in the CollaborationRoles eReference.
		if (dialog.getReturnCode() == Window.OK) {
			ConnectableElement role = (ConnectableElement) dialog.getFirstResult();

			owner.getCollaborationRoles().add(role);
			((CreateElementRequest) getRequest()).setNewElement(role);

			return CommandResult.newOKCommandResult(role);
		}

		// No role selected: abort element creation
		return CommandResult.newCancelledCommandResult();
	}
}
