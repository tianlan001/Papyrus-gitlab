/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.mouse.action;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.ui.action.IMouseAction;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.swt.events.MouseEvent;

/**
 * Abstract Cell Mouse action class
 *
 * @since 6.7
 */
public abstract class AbstractCellMouseAction implements IMouseAction {

	/**
	 * @see org.eclipse.nebula.widgets.nattable.ui.action.IMouseAction#run(org.eclipse.nebula.widgets.nattable.NatTable, org.eclipse.swt.events.MouseEvent)
	 *
	 * @param natTable
	 * @param event
	 */
	@Override
	public final void run(final NatTable natTable, final MouseEvent event) {
		if (natTable != null && !natTable.isDisposed() && natTable.getConfigRegistry() != null) {
			final INattableModelManager manager = natTable.getConfigRegistry().getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
			final int colPos = natTable.getColumnPositionByX(event.x);
			final int rowPos = natTable.getRowPositionByY(event.y);
			final int rowIndex = natTable.getRowIndexByPosition(rowPos);
			final int colIndex = natTable.getColumnIndexByPosition(colPos);
			final Object rowElement = manager.getRowElement(rowIndex);
			final Object columnElement = manager.getColumnElement(colIndex);
			doRun(natTable, event, rowElement, columnElement);
		}
	}

	/**
	 * This method do the action
	 *
	 * @param natTable
	 *            the current table
	 * @param event
	 *            the mouse event
	 * @param rowElement
	 *            the row element for the mouse event
	 * @param columnElement
	 *            the column element for the mouse event
	 */
	public abstract void doRun(final NatTable natTable, final MouseEvent event, final Object rowElement, final Object columnElement);
}