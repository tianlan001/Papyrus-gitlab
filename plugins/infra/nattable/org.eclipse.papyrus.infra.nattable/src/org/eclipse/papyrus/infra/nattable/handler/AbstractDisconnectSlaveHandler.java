/*****************************************************************************
 * Copyright (c) 2013, 2015, 2020 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562864
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.MasterObjectAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderPackage;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Abstract handler used to change the value of the property IMasterAxisProvider#disconnectSlave
 *
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractDisconnectSlaveHandler extends AbstractTableHandler {

	/**
	 *
	 * @see org.eclipse.core.commands.AbstractHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		final AbstractAxisProvider axisProvider = getAxisProviderToEdit();
		if (axisProvider instanceof MasterObjectAxisProvider) {
			boolean newState = !HandlerUtil.toggleCommandState(event.getCommand());
			final TransactionalEditingDomain domain = getTableEditingDomain();
			final IEditCommandRequest request = new SetRequest(domain, axisProvider, NattableaxisproviderPackage.eINSTANCE.getIMasterAxisProvider_DisconnectSlave(), newState);
			final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(axisProvider);
			final ICommand cmd = provider.getEditCommand(request);
			domain.getCommandStack().execute(new GMFtoEMFCommandWrapper(cmd));
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         the axis provider to edit or null
	 */
	protected abstract AbstractAxisProvider getAxisProviderToEdit();

	/**
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler#computeEnable(Object)
	 *
	 * @return
	 */
	@Override
	protected boolean computeEnable(final Object evaluationContext) {
		boolean calculatedValue = super.computeEnable(evaluationContext);
		if (calculatedValue) {
			calculatedValue = getAxisProviderToEdit() instanceof MasterObjectAxisProvider;
		}
		return calculatedValue;
	}
}
