/**
 * Copyright (c) 2017, 2019 CEA LIST.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *    Maged Elaasar - Initial API and implementation
 *	  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550569
 *
 */
package org.eclipse.papyrus.infra.ui.architecture.handlers;

import java.util.Arrays;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.ui.architecture.dialogs.ArchitectureContextDialog;
import org.eclipse.papyrus.infra.ui.architecture.messages.Messages;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * A command handler for changing architecture context in a model set
 *
 * @since 1.0
 */
public class ChangeArchitectureContextHandler extends AbstractHandler {

	/**
	 * Constructor.
	 */
	public ChangeArchitectureContextHandler() {
		// nothing by default
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		EObject selection = getSelection(event);
		if (selection == null) {
			return new IContributionItem[0];
		}
		ResourceSet resourceSet = selection.eResource().getResourceSet();
		if (!(resourceSet instanceof ModelSet)) {
			return new IContributionItem[0];
		}
		final ArchitectureDescriptionUtils helper = new ArchitectureDescriptionUtils((ModelSet) resourceSet);
		String[] contextIds = new String[] { helper.getArchitectureContextId() };
		String[] viewpointIds = helper.getArchitectureViewpointIds().toArray(new String[0]);

		final Shell shell = Display.getCurrent().getActiveShell();
		ArchitectureContextDialog dialog = new ArchitectureContextDialog(shell, Messages.ChangeArchitectureContextHandler_dialogTitle, Messages.ChangeArchitectureContextHandler_dialogLabel);
		dialog.setSelectedContexts(contextIds);
		dialog.setSelectedViewpoints(viewpointIds);
		dialog.create();

		if (dialog.open() == Window.OK) {
			final TransactionalEditingDomain dom = helper.getModelSet().getTransactionalEditingDomain();
			final CompoundCommand cmd = new CompoundCommand("Change Architecture Context"); //$NON-NLS-1$
			if (!Arrays.equals(dialog.getSelectedContextIds(), contextIds)) {
				cmd.append(helper.switchArchitectureContextId(dialog.getSelectedContextIds()[0]));
			}
			if (!Arrays.equals(dialog.getSelectedViewpointIds(), viewpointIds)) {
				cmd.append(helper.switchArchitectureViewpointIds(dialog.getSelectedViewpointIds()));
			}
			if (!cmd.isEmpty()) {
				dom.getCommandStack().execute(cmd);
			}
		}
		return null;
	}

	/**
	 * Gets the current selection as an EObject
	 *
	 * @return The current selection, or <code>null</code> if it is not an EObject
	 */
	protected EObject getSelection(ExecutionEvent event) {
		Object selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection struct = (IStructuredSelection) selection;
			Object obj = struct.getFirstElement();
			return EMFHelper.getEObject(obj);
		}
		return null;
	}

}
