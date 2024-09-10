/*****************************************************************************
 * Copyright (c) 2013, 2016 Atos, Christian W. Damus, and others.
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
 *  Arthur Daussy (Atos) arthur.daussy@atos.net - Initial API and implementation
 *	Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 436952
 *  Christian W. Damus - bug 497865
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.controlmode.handler;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeManager;
import org.eclipse.papyrus.infra.services.controlmode.ControlModeRequest;
import org.eclipse.papyrus.infra.services.controlmode.IControlModeManager;
import org.eclipse.papyrus.infra.services.controlmode.messages.Messages;
import org.eclipse.papyrus.infra.ui.util.ServiceUtilsForSelection;
import org.eclipse.papyrus.infra.widgets.toolbox.notification.builders.NotificationBuilder;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Handler use to uncontrol an element
 *
 * @author adaussy
 *
 */
public class UncontrolCommandHandler extends AbstractHandler {

	/** The Constant NO_EDITING_DOMAIN_MESSAGE. */
	private static final String NO_EDITING_DOMAIN_MESSAGE = Messages.getString("UncontrolCommandHandler.domain.error"); //$NON-NLS-1$

	/** The Constant EMPTY_SELECTION_MESSAGE. */
	private static final String EMPTY_SELECTION_MESSAGE = Messages.getString("UncontrolCommandHandler.selection.empty.message"); //$NON-NLS-1$

	/**
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);

		if (selection instanceof IStructuredSelection) {
			if (selection == null || selection.isEmpty() || ((IStructuredSelection) selection).size() > 1) {
				NotificationBuilder.createInfoPopup(EMPTY_SELECTION_MESSAGE).run();
				return null;
			}

			try {
				TransactionalEditingDomain editingDomain = ServiceUtilsForSelection.getInstance().getTransactionalEditingDomain(selection);
				EObject eObjectToControl = EMFHelper.getEObject(((IStructuredSelection) selection).getFirstElement());
				ControlModeRequest controlRequest = ControlModeRequest.createUIUncontrolModelRequest(editingDomain, eObjectToControl);
				IControlModeManager controlMng = ControlModeManager.getInstance();

				Diagnostic problems = controlMng.approveRequest(controlRequest);
				if ((problems != null) && (problems.getSeverity() >= Diagnostic.ERROR)) {
					IStatus status = BasicDiagnostic.toIStatus(problems);
					StatusManager.getManager().handle(status, StatusManager.SHOW);
				} else {
					ICommand controlCommand = controlMng.getUncontrolCommand(controlRequest);
					editingDomain.getCommandStack().execute(new GMFtoEMFCommandWrapper(controlCommand));
				}
			} catch (ServiceException e) {
				NotificationBuilder.createInfoPopup(NO_EDITING_DOMAIN_MESSAGE).run();

			}

		} else {
			NotificationBuilder.createInfoPopup(EMPTY_SELECTION_MESSAGE).run();
		}

		return null;
	}
}
