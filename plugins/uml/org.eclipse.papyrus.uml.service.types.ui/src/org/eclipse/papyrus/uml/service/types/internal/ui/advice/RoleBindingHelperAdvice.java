/*****************************************************************************
 * Copyright (c) 2010, 2019 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Implement approveRequest
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.service.types.internal.ui.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateRelationshipCommand;
import org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.uml.service.types.internal.ui.dialogs.CollaborationRoleTreeContentProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.NamedElement;


public class RoleBindingHelperAdvice extends AbstractEditHelperAdvice {

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.gmf.runtime.emf.type.core.edithelper.AbstractEditHelperAdvice#approveRequest(org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest)
	 */
	@Override
	public boolean approveRequest(final IEditCommandRequest request) {
		if (request instanceof CreateRelationshipRequest) {
			if (((CreateRelationshipRequest) request).getSource() instanceof CollaborationUse && null == ((CreateRelationshipRequest) request).getTarget()) {
				return false;
			}
		}
		return super.approveRequest(request);
	}


	@Override
	protected ICommand getAfterCreateRelationshipCommand(CreateRelationshipRequest request) {
		final EObject reqSource = request.getSource();

		if (reqSource instanceof CollaborationUse) {
			final CollaborationUse source = (CollaborationUse) reqSource;
			final Dependency dependency = (Dependency) request.getNewElement();
			final NamedElement target = (NamedElement) request.getTarget();

			return new CreateRelationshipCommand(request) {

				@Override
				protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
					if (!canExecute()) {
						throw new ExecutionException("Invalid arguments in create link command"); //$NON-NLS-1$
					}


					// Create and open the selection dialog
					ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
					Shell currentShell = new Shell(Display.getCurrent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
					ElementTreeSelectionDialog dialog = new ElementTreeSelectionDialog(currentShell, new AdapterFactoryLabelProvider(adapterFactory), new CollaborationRoleTreeContentProvider());

					try {
						// Set dialog parameters
						dialog.setTitle("Collaboration role binding"); //$NON-NLS-1$
						dialog.setMessage("Select the role to bind:"); //$NON-NLS-1$
						dialog.setAllowMultiple(false);
						dialog.setHelpAvailable(false);
						// The source CollaborationUse is set as input for the selection dialog,
						// the CollaborationRoleTreeContentProvider provides the roles that can possibly be
						// selected.
						dialog.setInput(source);

						dialog.open();
					} finally {
						adapterFactory.dispose();
					}

					// If a ConnectableElement has been selected, complete command execution
					// using selection as the "newly created" element and make the edited
					// Collaboration reference it in the CollaborationRoles eReference.
					if (dialog.getReturnCode() == Window.OK) {

						ConnectableElement roleToBind = (ConnectableElement) dialog.getFirstResult();
						// Create a Dependency (the binding) between selected role and a ConnectableElement
						// (the target)
						dependency.setName("binding_" + roleToBind.getName() + "_" + target.getName()); //$NON-NLS-1$ //$NON-NLS-2$
						source.getRoleBindings().add(dependency);


						((CreateElementRequest) getRequest()).setNewElement(dependency);

						return CommandResult.newOKCommandResult(dependency);
					}

					// No role selected: abort element creation
					return CommandResult.newCancelledCommandResult();
				}
			};
		}

		return null;
	}

}
