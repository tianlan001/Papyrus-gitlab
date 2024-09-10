/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 473155
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisIndexStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.handlers.RadioState;


/**
 * The abstract handler used to change the axis index style.
 *
 * @author Vincent Lorenzo
 *
 */
public abstract class AbstractChangeIndexStyleHandler extends AbstractTableHandler {

	/**
	 *
	 * @param event
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		if (HandlerUtil.matchesRadioState(event)) {
			return null; // we are already in the updated state - do nothing
		}
		String currentState = event.getParameter(RadioState.PARAMETER_ID);
		TransactionalEditingDomain domain = getTableEditingDomain();
		AxisIndexStyle newStyle = AxisIndexStyle.get(currentState);

		final ICompositeCommand compositeCommand = new CompositeCommand("Change index style"); //$NON-NLS-1$
		AbstractHeaderAxisConfiguration configuration = getAxisConfiguration();

		// Bug 473155 : Create a local header configuration if it does not exist
		if (null == configuration) {
			configuration = createLocalHeaderConfiguration(domain, compositeCommand);
		}

		final IEditCommandRequest request = new SetRequest(domain, configuration, NattableaxisconfigurationPackage.eINSTANCE.getAbstractHeaderAxisConfiguration_IndexStyle(), newStyle);
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(configuration);
		final ICommand cmd = provider.getEditCommand(request);
		compositeCommand.add(cmd);
		domain.getCommandStack().execute(new GMFtoEMFCommandWrapper(compositeCommand));

		// and finally update the current state
		HandlerUtil.updateRadioState(event.getCommand(), currentState);
		return new Status(IStatus.OK, Activator.PLUGIN_ID, "Change axis index style successfully done"); //$NON-NLS-1$
	}

	/**
	 *
	 * @return
	 * 		the axis configuration to edit
	 */
	protected abstract AbstractHeaderAxisConfiguration getAxisConfiguration();

	/**
	 * This allow to create the local configuration corresponding to the modification.
	 * 
	 * @param domain
	 *            The transactional editing domain.
	 * @param compositeCommand
	 *            The composite command (where add the created command).
	 * @return The command to add the configuration on the table.
	 */
	protected abstract AbstractHeaderAxisConfiguration createLocalHeaderConfiguration(final TransactionalEditingDomain domain, final ICompositeCommand compositeCommand);

}
