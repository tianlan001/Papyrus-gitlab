/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515737
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.handler;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.papyrus.infra.nattable.applynamedstyle.PapyrusApplyNamedStyleCommand;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.NamedStyleConstants;

/**
 * Handler to display list on separated rows of the active table.
 *
 * @since 5.0
 */
public class SetDisplayListOnSeparatedRowsHandler extends AbstractTableHandler {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final TransactionalEditingDomain editingDomain = getTableEditingDomain();
		final INattableModelManager manager = getCurrentNattableModelManager();
		final NatTable natTable = getCurrentNatTable();
		if (null != editingDomain && null != manager && null != natTable) {

			// Get the selected axis index
			final int selectedAxisIndex = AxisUtils.getUniqueSelectedAxisIndex(manager);

			// Always get the column axis provider for invert or non-invert table
			final AbstractAxisProvider axisProvider = manager.getTable().getCurrentColumnAxisProvider();

			// If the axis index is not out of range
			if (null != axisProvider && 0 <= selectedAxisIndex && selectedAxisIndex < axisProvider.getAxis().size()) {
				final IAxis selectedAxis = axisProvider.getAxis().get(selectedAxisIndex);

				// Apply the displayListOnSeparatedRows named style on the selected axis
				natTable.doCommand(new PapyrusApplyNamedStyleCommand(editingDomain, selectedAxis, NamedStyleConstants.DISPLAY_LIST_ON_SEPARATED_ROWS));
			}
		}

		return null;
	}
}
