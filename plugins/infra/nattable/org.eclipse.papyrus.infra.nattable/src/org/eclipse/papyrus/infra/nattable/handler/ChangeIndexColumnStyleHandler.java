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

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;

/**
 * the handler used to change the index style of the column
 *
 * @author Vincent Lorenzo
 *
 */
public class ChangeIndexColumnStyleHandler extends AbstractChangeIndexStyleHandler {

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.handler.AbstractChangeIndexStyleHandler#getAxisConfiguration()
	 *
	 * @return
	 */
	@Override
	protected AbstractHeaderAxisConfiguration getAxisConfiguration() {
		return HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTable(getCurrentNattableModelManager().getTable());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected AbstractHeaderAxisConfiguration createLocalHeaderConfiguration(final TransactionalEditingDomain domain, final ICompositeCommand compositeCommand) {
		final Table table = getCurrentNattableModelManager().getTable();
		AbstractHeaderAxisConfiguration configuration = null;
		IEditCommandRequest request = null;
		if (!table.isInvertAxis()) {
			configuration = HeaderAxisConfigurationManagementUtils
				.transformToLocalHeaderConfiguration((TableHeaderAxisConfiguration) HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisInTableConfiguration(table));
			request = new SetRequest(domain, table, NattablePackage.eINSTANCE.getTable_LocalColumnHeaderAxisConfiguration(), configuration);
		}else{
			configuration = HeaderAxisConfigurationManagementUtils
					.transformToLocalHeaderConfiguration((TableHeaderAxisConfiguration) HeaderAxisConfigurationManagementUtils.getRowAbstractHeaderAxisInTableConfiguration(table));
			request = new SetRequest(domain, table, NattablePackage.eINSTANCE.getTable_LocalRowHeaderAxisConfiguration(), configuration);
		}

		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(table);
		final ICommand cmd = provider.getEditCommand(request);
		compositeCommand.add(cmd);

		return configuration;
	}

}
