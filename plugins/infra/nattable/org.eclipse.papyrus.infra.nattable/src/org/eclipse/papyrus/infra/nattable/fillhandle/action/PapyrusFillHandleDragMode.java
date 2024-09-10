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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.fillhandle.action;

import java.util.Date;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.copy.InternalCellClipboard;
import org.eclipse.nebula.widgets.nattable.fillhandle.action.FillHandleDragMode;
import org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommand.FillHandleOperation;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.papyrus.infra.nattable.fillhandle.command.PapyrusFillHandlePasteCommand;
import org.eclipse.papyrus.infra.nattable.fillhandle.utils.PapyrusFillHandleUtils;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * The fill handler drag mode for papyrus to manage the menu and to allow the string series.
 */
public class PapyrusFillHandleDragMode extends FillHandleDragMode {

	/**
	 * Boolean to determinate if a double series menu must be displayed (to increment/decrement prefix or suffix string)
	 */
	protected boolean createDoubleSeriesMenu = false;

	/**
	 * Constructor.
	 * 
	 * @param selectionLayer
	 *            The {@link SelectionLayer} needed to determine the fill handle
	 *            region and perform the update command.
	 * @param clipboard
	 *            The internal clipboard that carries the cells for the copy
	 *            &amp; paste operation triggered by using the fill handle.
	 */
	public PapyrusFillHandleDragMode(final SelectionLayer selectionLayer, final InternalCellClipboard clipboard) {
		super(selectionLayer, clipboard);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.fillhandle.action.FillHandleDragMode#openMenu(org.eclipse.nebula.widgets.nattable.NatTable)
	 */
	@Override
	protected void openMenu(final NatTable natTable) {
		// lazily create the menu
		if (null != this.menu && !this.menu.isDisposed()) {
			this.menu.dispose();
			this.menu = null;
		}

		this.menu = new Menu(natTable);
		MenuItem copyItem = new MenuItem(this.menu, SWT.PUSH);
		copyItem.setText(Messages.PapyrusFillHandleDragMode_CopyCommandName);
		setPasteSelectionListener(copyItem, natTable, FillHandleOperation.COPY, false, false);

		// Calculate the menu item names
		String firstSeriesMenuName = null;
		String secondSeriesMenuName = null;

		if (createDoubleSeriesMenu) {
			firstSeriesMenuName = Messages.PapyrusFillHandleDragMode_IncrementPrefixCommandName;
			secondSeriesMenuName = Messages.PapyrusFillHandleDragMode_DecrementPrefixCommandName;
		} else {
			firstSeriesMenuName = Messages.PapyrusFillHandleDragMode_IncrementCommandName;
			secondSeriesMenuName = Messages.PapyrusFillHandleDragMode_DecrementCommandName;
		}

		// Create the series item menu
		MenuItem incrementItem = new MenuItem(this.menu, SWT.PUSH);
		incrementItem.setText(firstSeriesMenuName);
		setPasteSelectionListener(incrementItem, natTable, FillHandleOperation.SERIES, true, true);

		MenuItem decrementItem = new MenuItem(this.menu, SWT.PUSH);
		decrementItem.setText(secondSeriesMenuName);
		setPasteSelectionListener(decrementItem, natTable, FillHandleOperation.SERIES, false, true);

		if (createDoubleSeriesMenu) {
			MenuItem incrementSuffixItem = new MenuItem(this.menu, SWT.PUSH);
			incrementSuffixItem.setText(Messages.PapyrusFillHandleDragMode_IncrementSuffixCommandName);
			setPasteSelectionListener(incrementSuffixItem, natTable, FillHandleOperation.SERIES, true, false);

			MenuItem decrementSuffixItem = new MenuItem(this.menu, SWT.PUSH);
			decrementSuffixItem.setText(Messages.PapyrusFillHandleDragMode_DecrementSuffixCommandName);
			setPasteSelectionListener(decrementSuffixItem, natTable, FillHandleOperation.SERIES, false, false);
		}

		// add the dispose listener for disposing the menu
		natTable.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent e) {
				PapyrusFillHandleDragMode.this.menu.dispose();
			}
		});

		this.menu.setVisible(true);
	}

	/**
	 * Set the selection listener for the menu item.
	 * 
	 * @param menuItem
	 *            The menu item for the selection listener.
	 * @param natTable
	 *            The current nattable.
	 * @param operation
	 *            The {@link FillHandleOperation} that should be triggered.
	 * @param isIncrement
	 *            Boolean to determinate if this an increment action.
	 * @param isPrefix
	 *            Boolean to determinate if this is a prefix or suffix modification for string series.
	 */
	protected void setPasteSelectionListener(final MenuItem menuItem, final NatTable natTable, final FillHandleOperation operation, final boolean isIncrement, final boolean isPrefix) {
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				natTable.doCommand(
						new PapyrusFillHandlePasteCommand(
								operation,
								PapyrusFillHandleDragMode.this.direction,
								natTable.getConfigRegistry(),
								isIncrement,
								isPrefix));
				reset(natTable);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.fillhandle.action.FillHandleDragMode#showMenu(org.eclipse.nebula.widgets.nattable.NatTable)
	 */
	@Override
	protected boolean showMenu(final NatTable natTable) {
		boolean result = false;

		createDoubleSeriesMenu = false;
		String templateString = ""; //$NON-NLS-1$

		if (null != this.clipboard && null != this.clipboard.getCopiedCells()) {
			final ILayerCell[][] copiedCells = this.clipboard.getCopiedCells();

			result = true;
			Class<?> type = null;

			for (int cellsIndex = 0; cellsIndex < copiedCells.length && result; cellsIndex++) {
				final ILayerCell[] cells = copiedCells[cellsIndex];
				for (int cellIndex = 0; cellIndex < cells.length && result; cellIndex++) {
					final ILayerCell cell = cells[cellIndex];
					if (null == cell.getDataValue()) {
						result = false;
					} else if (null == type) {
						type = cell.getDataValue().getClass();
						if (String.class.isAssignableFrom(type)) {
							final String stringValue = (String) cell.getDataValue();

							final String templateStringWithoutBeginningNumber = PapyrusFillHandleUtils.getTemplateWithoutBeginningNumber(stringValue);
							final String templateStringWithoutEndingNumber = PapyrusFillHandleUtils.getTemplateWithoutEndingNumber(stringValue);

							final String beginningNumberString = stringValue.replace(templateStringWithoutBeginningNumber, ""); //$NON-NLS-1$
							final String endingNumberString = stringValue.replace(templateStringWithoutEndingNumber, ""); //$NON-NLS-1$

							// Calculate if the 'increment' and 'decrement' menu must be displayed
							// and calculate if the double series menu must be displayed
							// (increment/decrement the beginning or the ending of a string)
							if (!beginningNumberString.isEmpty() || !endingNumberString.isEmpty()) {
								final boolean isBeginningByNumber = PapyrusFillHandleUtils.isBeginningByNumber(stringValue, beginningNumberString);
								final boolean isEndingByNumber = PapyrusFillHandleUtils.isEndingByNumber(stringValue, endingNumberString);

								createDoubleSeriesMenu = isBeginningByNumber && isEndingByNumber;
							} else {
								result = false;
							}
						} else if (!Number.class.isAssignableFrom(type)
								&& !Date.class.isAssignableFrom(type)) {
							result = false;
						}
					} else {
						if (type != cell.getDataValue().getClass()) {
							result = false;
						} else if (String.class.isAssignableFrom(type)) {
							final String stringValue = (String) cell.getDataValue();
							final String currentTemplateString = PapyrusFillHandleUtils.getTemplateString(stringValue);
							final String number = stringValue.replace(currentTemplateString, "");

							result = templateString.equals(currentTemplateString) && !number.isEmpty();
						}
					}
				}
			}
		}

		return result;
	}

}
