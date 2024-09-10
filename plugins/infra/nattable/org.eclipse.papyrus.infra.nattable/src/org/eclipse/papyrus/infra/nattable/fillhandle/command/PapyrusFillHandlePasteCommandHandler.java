/*****************************************************************************
 * Copyright (c) 2016, 2017, 2018 CEA LIST and others.
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
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 519383
 *   Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Bug 535073
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.fillhandle.command;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.copy.InternalCellClipboard;
import org.eclipse.nebula.widgets.nattable.edit.command.EditUtils;
import org.eclipse.nebula.widgets.nattable.edit.command.UpdateDataCommand;
import org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommand;
import org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommand.FillHandleOperation;
import org.eclipse.nebula.widgets.nattable.hideshow.RowHideShowLayer;
import org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommandHandler;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer.MoveDirectionEnum;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.fillhandle.utils.PapyrusFillHandleUtils;
import org.eclipse.papyrus.infra.nattable.layer.PapyrusSelectionLayer;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.swt.graphics.Rectangle;

/**
 * The papyrus command handler for the fill handle paste. This allows to manage the string series and the row and column difference calculation.
 */
public class PapyrusFillHandlePasteCommandHandler extends FillHandlePasteCommandHandler {

	/**
	 * The current nattable model manager.
	 */
	protected INattableModelManager tableManager;

	/**
	 * The command to manage.
	 */
	protected FillHandlePasteCommand command;

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
	public PapyrusFillHandlePasteCommandHandler(final SelectionLayer selectionLayer, final InternalCellClipboard clipboard, final INattableModelManager tableManager) {
		super(selectionLayer, clipboard);
		this.tableManager = tableManager;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommandHandler#doCommand(org.eclipse.nebula.widgets.nattable.layer.ILayer, org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommand)
	 */
	@Override
	public boolean doCommand(final ILayer targetLayer, final FillHandlePasteCommand command) {
		TransactionalEditingDomain domain = TableEditingDomainUtils.getTableContextEditingDomain(tableManager.getTable());
		final AbstractTransactionalCommand transactionalCommand = new AbstractTransactionalCommand(domain, null, null) {

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				final boolean result;
				if (hasAtLeastOneHiddenRow()) {
					result = PapyrusFillHandlePasteCommandHandler.this.doCommandAdaptForHiddenRows(targetLayer, command);
				} else {
					result = PapyrusFillHandlePasteCommandHandler.super.doCommand(targetLayer, command);
				}

				return CommandResult.newOKCommandResult(Boolean.valueOf(result));
			}
		};
		domain.getCommandStack().execute(new GMFtoEMFCommandWrapper(transactionalCommand));
		return ((Boolean) transactionalCommand.getCommandResult().getReturnValue()).booleanValue();
	}

	/**
	 * @return <code>true</code> if there is at least one hidden row in the table, <code>false</code> otherwise
	 * @since 4.0
	 */
	protected boolean hasAtLeastOneHiddenRow() {
		if (this.selectionLayer instanceof PapyrusSelectionLayer &&
				((PapyrusSelectionLayer) this.selectionLayer).getSelectionUnderlyingLayer() instanceof RowHideShowLayer) {
			RowHideShowLayer rowHideShowLayer = (RowHideShowLayer) ((PapyrusSelectionLayer) this.selectionLayer).getSelectionUnderlyingLayer();
			if (!rowHideShowLayer.getHiddenRowIndexes().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Clone the method {@link FillHandlePasteCommandHandler#doCommand(ILayer, FillHandlePasteCommand)}.
	 * But before updating data, row index is adapted to row position when some rows are hidden.
	 *
	 * @param targetLayer
	 *            The target layer
	 * @param command
	 *            The command
	 * @return <code>true</code> if the command has been handled, <code>false</code> otherwise
	 * @since 4.0
	 */
	protected boolean doCommandAdaptForHiddenRows(final ILayer targetLayer, final FillHandlePasteCommand command) {
		if (null != this.clipboard.getCopiedCells()) {
			int pasteColumn = -1;
			int pasteRow = -1;
			int pasteWidth = this.clipboard.getCopiedCells().length;
			int pasteHeight = this.clipboard.getCopiedCells()[0].length;
			Rectangle handleRegion = this.selectionLayer.getFillHandleRegion();
			if (null != handleRegion) {
				pasteColumn = handleRegion.x;
				pasteRow = handleRegion.y;
				pasteWidth = handleRegion.width;
				pasteHeight = handleRegion.height;
			} else {
				PositionCoordinate coord = this.selectionLayer.getSelectionAnchor();
				pasteColumn = coord.getColumnPosition();
				pasteRow = coord.getRowPosition();
			}

			int pasteStartColumn = pasteColumn;

			int rowStartAdjustment = 0;
			if (command.direction == MoveDirectionEnum.UP) {
				rowStartAdjustment = pasteHeight % this.clipboard.getCopiedCells().length;
			}

			int columnStartAdjustment = 0;
			if (command.direction == MoveDirectionEnum.LEFT) {
				columnStartAdjustment = pasteWidth % this.clipboard.getCopiedCells()[0].length;
			}

			for (int i = 0; i < pasteHeight; i++) {
				ILayerCell[] cells = this.clipboard.getCopiedCells()[(i + rowStartAdjustment) % this.clipboard.getCopiedCells().length];
				for (int j = 0; j < pasteWidth; j++) {
					ILayerCell cell = cells[(j + columnStartAdjustment) % this.clipboard.getCopiedCells()[0].length];

					Object cellValue = getPasteValue(cell, command, pasteColumn, pasteRow);

					// BUG 519383: EditUtils.isCellEditable and UpdateDataCommand use row (column) position but not row (column) index as parameters.
					// But here, the variables pasteRow and pasteColumns are retrieved from fill handle region, which means they are index not position (see FillHandleDragMode.performDragAction).
					// As there is no hidden rows, row index and row position are the same in SelectionLayer and RowHideShowLayer.
					// When there is at least one hidden row, using the wrong index parameter in the SelectionLayer will lead to the wrong one in RowHideShowLayer.
					// It seems that this is a bug in NatTable.

					// A workaround is proposed here to adapt row index to row position when some rows are hidden.
					// It should work for actions like: hide all categories, show one category or using row filter in the column header.
					int pasteRowAdapt = adaptToRowPositionWhenHiddenRowsExist(pasteRow);

					if (EditUtils.isCellEditable(
							this.selectionLayer,
							command.configRegistry,
							new PositionCoordinate(this.selectionLayer,
									pasteColumn,
									pasteRowAdapt))) {
						this.selectionLayer.doCommand(new UpdateDataCommand(this.selectionLayer, pasteColumn, pasteRowAdapt, cellValue));
					}

					pasteColumn++;

					if (pasteColumn >= this.selectionLayer.getColumnCount()) {
						break;
					}
				}

				pasteRow++;
				pasteColumn = pasteStartColumn;
			}
		}
		return true;
	}

	/**
	 * Adapt row index to row position when some rows are hidden.
	 *
	 * @param rowIndex
	 *            The row index to be adapted
	 * @return the adapted row position
	 * @since 4.0
	 */
	protected int adaptToRowPositionWhenHiddenRowsExist(final int rowIndex) {
		int rowPos = rowIndex;

		if (this.selectionLayer instanceof PapyrusSelectionLayer &&
				((PapyrusSelectionLayer) this.selectionLayer).getSelectionUnderlyingLayer() instanceof RowHideShowLayer) {
			RowHideShowLayer rowHideShowLayer = (RowHideShowLayer) ((PapyrusSelectionLayer) this.selectionLayer).getSelectionUnderlyingLayer();
			for (Integer hiddenRowIndex : rowHideShowLayer.getHiddenRowIndexes()) {
				// Decrease row position by 1 for each hidden row index smaller than the row index
				if (hiddenRowIndex < rowIndex) {
					rowPos--;
				}
			}
		}

		return rowPos;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommandHandler#getPasteValue(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommand, int, int)
	 */
	@Override
	protected Object getPasteValue(final ILayerCell cell, final FillHandlePasteCommand command, final int toColumn, final int toRow) {
		Object result = null;

		this.command = command;

		if (command instanceof PapyrusFillHandlePasteCommand) {
			final Object value = cell.getDataValue();

			if (value instanceof String && FillHandleOperation.SERIES.equals(command.operation)) {
				final String stringValue = (String) value;
				result = stringValue;
				String templateString = ""; //$NON-NLS-1$
				String numberString = ""; //$NON-NLS-1$

				final String templateStringWithoutBeginningNumber = PapyrusFillHandleUtils.getTemplateWithoutBeginningNumber(stringValue);
				final String templateStringWithoutEndingNumber = PapyrusFillHandleUtils.getTemplateWithoutEndingNumber(stringValue);

				final String beginningNumberString = stringValue.replace(templateStringWithoutBeginningNumber, ""); //$NON-NLS-1$
				final String endingNumberString = stringValue.replace(templateStringWithoutEndingNumber, ""); //$NON-NLS-1$

				boolean isBeginningByNumber = PapyrusFillHandleUtils.isBeginningByNumber(stringValue, beginningNumberString);
				boolean isEndingByNumber = PapyrusFillHandleUtils.isEndingByNumber(stringValue, endingNumberString);

				// If string value is both prefixed and suffixed by numbers
				if (isBeginningByNumber && isEndingByNumber) {
					// Redefine its value based on the prefix of the command
					isBeginningByNumber = ((PapyrusFillHandlePasteCommand) command).isPrefix();
					isEndingByNumber = !isBeginningByNumber;
					if (isBeginningByNumber) {
						templateString = PapyrusFillHandleUtils.getTemplateWithoutBeginningNumber(stringValue);
						numberString = beginningNumberString;
					} else {
						templateString = PapyrusFillHandleUtils.getTemplateWithoutEndingNumber(stringValue);
						numberString = endingNumberString;
					}
				}

				// If string value is prefixed or suffixed, the template string and the number string are still empty
				// Calculate them according to the prefix or the suffix
				if (templateString.isEmpty() && numberString.isEmpty()) {
					if (isBeginningByNumber) {
						templateString = PapyrusFillHandleUtils.getTemplateWithoutBeginningNumber(stringValue);
						numberString = beginningNumberString;
					} else {
						templateString = PapyrusFillHandleUtils.getTemplateWithoutEndingNumber(stringValue);
						numberString = endingNumberString;
					}
				}

				if (!numberString.isEmpty()) {
					final int intValue = Integer.parseInt(numberString);

					Object diff = 0;
					if (MoveDirectionEnum.LEFT == command.direction || MoveDirectionEnum.RIGHT == command.direction) {
						diff = calculateVerticalStringDiff(templateString, cell, toColumn);
					} else if (MoveDirectionEnum.UP == command.direction || MoveDirectionEnum.DOWN == command.direction) {
						diff = calculateHorizontalStringDiff(templateString, cell, toRow);
					}

					// if we can not determine a common diff value we perform a copy
					if (null != diff) {
						int newValue = 0;
						if (((PapyrusFillHandlePasteCommand) command).isIncrement()) {
							newValue = intValue + ((Integer) diff);
						} else {
							newValue = intValue - ((Integer) diff);
						}

						int nbDigit;
						if (intValue == 0 && numberString.startsWith("-")) { //$NON-NLS-1$
							nbDigit = numberString.length() - 1; // avoid to get, when we increment from Req1-00 -> Req1000. Here we get Req100.
							// so we continue to get a bug here with minus sign used as name separator, particularly starting a zero.
						} else {
							nbDigit = intValue < 0 ? numberString.length() - 1 : numberString.length();
						}
						final String numDigitsFormat = PapyrusFillHandleUtils.getZeroLeadingFormatString(nbDigit, newValue);
						final String newValueString = String.format(numDigitsFormat, newValue);

						if (isBeginningByNumber) {
							result = newValueString + templateString;
						} else if (isEndingByNumber) {
							result = templateString + newValueString;
						}
					}
				}
			}
		}
		boolean isEditable = EditUtils.isCellEditable(
				this.selectionLayer,
				command.configRegistry,
				new PositionCoordinate(this.selectionLayer,
						cell.getColumnIndex(),
						adaptToRowPositionWhenHiddenRowsExist(cell.getRowIndex())));
		return !isEditable || null != result ? result : super.getPasteValue(cell, command, toColumn, toRow);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommandHandler#calculateHorizontalDiff(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, int, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 */
	@Override
	protected Number calculateHorizontalDiff(final ILayerCell cell, final int toRow, final IConfigRegistry configRegistry) {
		Number diff = super.calculateHorizontalDiff(cell, toRow, configRegistry);

		if (command instanceof PapyrusFillHandlePasteCommand) {
			if ((MoveDirectionEnum.UP == command.direction && !((PapyrusFillHandlePasteCommand) command).isIncrement())
					|| (MoveDirectionEnum.DOWN == command.direction && !((PapyrusFillHandlePasteCommand) command).isIncrement())) {
				if (diff instanceof Byte) {
					diff = 0 - diff.byteValue();
				} else if (diff instanceof Short) {
					diff = 0 - diff.shortValue();
				} else if (diff instanceof Integer) {
					diff = 0 - diff.intValue();
				} else if (diff instanceof Long) {
					diff = 0 - diff.longValue();
				} else if (diff instanceof Float) {
					diff = 0 - diff.floatValue();
				} else if (diff instanceof Double) {
					diff = 0 - diff.doubleValue();
				} else if (diff instanceof BigInteger) {
					diff = BigInteger.ZERO.subtract((BigInteger) diff);
				} else if (diff instanceof BigDecimal) {
					diff = BigDecimal.ZERO.subtract((BigDecimal) diff);
				}
			}
		}

		return diff;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommandHandler#calculateVerticalDiff(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, int, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 */
	@Override
	protected Number calculateVerticalDiff(final ILayerCell cell, final int toColumn, final IConfigRegistry configRegistry) {
		Number diff = super.calculateVerticalDiff(cell, toColumn, configRegistry);

		if (command instanceof PapyrusFillHandlePasteCommand) {
			if ((MoveDirectionEnum.LEFT == command.direction && !((PapyrusFillHandlePasteCommand) command).isIncrement())
					|| (MoveDirectionEnum.RIGHT == command.direction && !((PapyrusFillHandlePasteCommand) command).isIncrement())) {
				if (diff instanceof Byte) {
					diff = 0 - diff.byteValue();
				} else if (diff instanceof Short) {
					diff = 0 - diff.shortValue();
				} else if (diff instanceof Integer) {
					diff = 0 - diff.intValue();
				} else if (diff instanceof Long) {
					diff = 0 - diff.longValue();
				} else if (diff instanceof Float) {
					diff = 0 - diff.floatValue();
				} else if (diff instanceof Double) {
					diff = 0 - diff.doubleValue();
				} else if (diff instanceof BigInteger) {
					diff = BigInteger.ZERO.subtract((BigInteger) diff);
				} else if (diff instanceof BigDecimal) {
					diff = BigDecimal.ZERO.subtract((BigDecimal) diff);
				}
			}
		}

		return diff;
	}

	/**
	 * Calculate the vertical difference for string cells.
	 * 
	 * @param templateString
	 *            the template string.
	 * @param cell
	 *            The cell to modify.
	 * @param toColumn
	 *            The initial column index.
	 * @return The integer value difference.
	 */
	protected Number calculateVerticalStringDiff(final String templateString, final ILayerCell cell, final int toColumn) {

		final ILayerCell[][] cells = this.clipboard.getCopiedCells();
		final int columnDiff = getColumnDiff(cell, toColumn);
		final int rowArrayIndex = cell.getRowIndex() - this.clipboard.getCopiedCells()[0][0].getRowIndex();
		if (1 == cells[rowArrayIndex].length) {
			return columnDiff;
		} else {
			final Integer diff = calculateStringDiff(cells[rowArrayIndex][1], cells[rowArrayIndex][0], templateString);
			if (null == diff) {
				return null;
			}
			Integer temp = diff;
			for (int i = 1; i < cells.length; i++) {
				temp = calculateStringDiff(cells[rowArrayIndex][i], cells[rowArrayIndex][i - 1], templateString);
				if (null == temp || !temp.equals(diff)) {
					return null;
				}
			}
			return diff * columnDiff;
		}
	}

	/**
	 * Calculate the horizontal difference for string cells.
	 * 
	 * @param templateString
	 *            the template string.
	 * @param cell
	 *            The cell to modify.
	 * @param toRow
	 *            The initial row index.
	 * @return The integer value difference.
	 */
	protected Number calculateHorizontalStringDiff(final String templateString, final ILayerCell cell, final int toRow) {

		ILayerCell[][] cells = this.clipboard.getCopiedCells();
		int rowDiff = getRowDiff(cell, toRow);
		if (cells.length == 1) {
			return rowDiff;
		} else {
			int columnArrayIndex = cell.getColumnIndex() - this.clipboard.getCopiedCells()[0][0].getColumnIndex();
			Integer diff = calculateStringDiff(cells[1][columnArrayIndex], cells[0][columnArrayIndex], templateString);
			if (diff == null) {
				return null;
			}
			Integer temp = diff;
			for (int i = 1; i < cells.length; i++) {
				temp = calculateStringDiff(cells[i][columnArrayIndex], cells[i - 1][columnArrayIndex], templateString);
				if (temp == null || !temp.equals(diff)) {
					return null;
				}
			}
			return diff * rowDiff;
		}
	}

	/**
	 * This allows to calculate the string difference (without the template string).
	 * 
	 * @param c1
	 *            The first cell.
	 * @param c2
	 *            The second cell.
	 * @param templateString
	 *            The template string to remove from first and second cells.
	 * @return The integer difference;
	 */
	protected Integer calculateStringDiff(final ILayerCell c1, final ILayerCell c2, final String templateString) {
		Integer diff = null;
		if (null != c1.getDataValue() && null != c2.getDataValue()
				&& (c1.getDataValue() instanceof String) && (c2.getDataValue() instanceof String)
				&& ((String) c1.getDataValue()).contains(templateString) && ((String) c2.getDataValue()).contains(templateString)) {
			try {
				diff = new Integer(Integer.parseInt(((String) c1.getDataValue()).replace(templateString, "")) - Integer.parseInt(((String) c2.getDataValue()).replace(templateString, ""))); //$NON-NLS-1$ //$NON-NLS-2$
			} catch (Exception e) {
				// continue
			}
		}
		return diff;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommandHandler#getRowDiff(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, int)
	 */
	@Override
	protected int getRowDiff(final ILayerCell currentCell, final int toRow) {
		int rowDiff = 0;

		int lower = 0;
		int upper = 0;

		if (toRow > currentCell.getRowIndex()) {
			lower = currentCell.getRowIndex();
			upper = toRow;
		} else {
			lower = toRow;
			upper = currentCell.getRowIndex();
		}

		for (int index = lower; index < upper; index++) {

			final Object rowElement = AxisUtils.getRepresentedElement(tableManager.getRowElement(index));
			final Object columnElement = AxisUtils.getRepresentedElement(tableManager.getColumnElement(currentCell.getColumnIndex()));

			final boolean isEditable = CellManagerFactory.INSTANCE.isCellEditable(columnElement, rowElement, tableManager);

			if (isEditable) {
				rowDiff++;
			}
		}

		return rowDiff;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.fillhandle.command.FillHandlePasteCommandHandler#getColumnDiff(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, int)
	 */
	@Override
	protected int getColumnDiff(final ILayerCell currentCell, final int toColumn) {
		int columnDiff = 0;

		int lower = 0;
		int upper = 0;

		if (toColumn > currentCell.getColumnIndex()) {
			lower = currentCell.getColumnIndex();
			upper = toColumn;
		} else {
			lower = toColumn;
			upper = currentCell.getColumnIndex();
		}

		for (int index = lower; index < upper; index++) {

			final Object rowElement = AxisUtils.getRepresentedElement(tableManager.getRowElement(currentCell.getRowIndex()));
			final Object columnElement = AxisUtils.getRepresentedElement(tableManager.getColumnElement(index));

			final boolean isEditable = CellManagerFactory.INSTANCE.isCellEditable(columnElement, rowElement, tableManager);

			if (isEditable) {
				columnDiff++;
			}
		}

		return columnDiff;
	}

}
