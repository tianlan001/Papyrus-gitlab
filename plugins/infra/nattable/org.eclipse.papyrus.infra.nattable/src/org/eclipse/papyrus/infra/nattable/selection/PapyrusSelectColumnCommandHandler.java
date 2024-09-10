/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.infra.nattable.selection;

import static org.eclipse.nebula.widgets.nattable.selection.SelectionUtils.bothShiftAndControl;
import static org.eclipse.nebula.widgets.nattable.selection.SelectionUtils.isControlOnly;
import static org.eclipse.nebula.widgets.nattable.selection.SelectionUtils.isShiftOnly;
import static org.eclipse.nebula.widgets.nattable.selection.SelectionUtils.noShiftOrControl;

import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectColumnCommandHandler;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectColumnCommand;
import org.eclipse.nebula.widgets.nattable.selection.event.ColumnSelectionEvent;
import org.eclipse.papyrus.infra.nattable.layer.PapyrusSelectionLayer;
import org.eclipse.swt.graphics.Rectangle;

/**
 * The select column command handler extended from the select column command handler (from natable) to manage th papyrus column selection event.
 */
public class PapyrusSelectColumnCommandHandler extends SelectColumnCommandHandler {

	/**
	 * The papyrus selection layer.
	 */
	protected final PapyrusSelectionLayer selectionLayer;

	/**
	 * Constructor.
	 *
	 * @param selectionLayer
	 *            The selection layer.
	 */
	public PapyrusSelectColumnCommandHandler(final PapyrusSelectionLayer selectionLayer) {
		super(selectionLayer);
		this.selectionLayer = selectionLayer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.selection.SelectColumnCommandHandler#doCommand(org.eclipse.nebula.widgets.nattable.layer.ILayer, org.eclipse.nebula.widgets.nattable.selection.command.SelectColumnCommand)
	 */
	@Override
	public boolean doCommand(final ILayer targetLayer, final SelectColumnCommand command) {
		if (command.convertToTargetLayer(this.selectionLayer)) {
			selectColumn(command.getColumnPosition(), command.getRowPosition(),
					command.isWithShiftMask(), command.isWithControlMask());
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.selection.SelectColumnCommandHandler#selectColumn(int, int, boolean, boolean)
	 */
	@Override
	protected void selectColumn(final int columnPosition, final int rowPosition,
			boolean withShiftMask, boolean withControlMask) {
		if (noShiftOrControl(withShiftMask, withControlMask)) {
			this.selectionLayer.clear(false);
			this.selectionLayer.selectCell(columnPosition, 0, false, false);
			this.selectionLayer
					.selectRegion(columnPosition, 0, 1, Integer.MAX_VALUE);
			this.selectionLayer.moveSelectionAnchor(columnPosition, rowPosition);
		} else if (bothShiftAndControl(withShiftMask, withControlMask)) {
			selectColumnWithShiftKey(columnPosition);
		} else if (isShiftOnly(withShiftMask, withControlMask)) {
			selectColumnWithShiftKey(columnPosition);
		} else if (isControlOnly(withShiftMask, withControlMask)) {
			selectColumnWithCtrlKey(columnPosition, rowPosition);
		}

		// Set last selected column position to the recently clicked column
		this.selectionLayer.setLastSelectedCell(columnPosition, rowPosition);

		this.selectionLayer.fireLayerEvent(new ColumnSelectionEvent(this.selectionLayer,
				columnPosition, withShiftMask, withControlMask));
	}

	/**
	 * This allows to select the columns with the Control key.
	 * 
	 * @param columnPosition
	 *            The column position.
	 * @param rowPosition
	 *            The row position
	 */
	private void selectColumnWithCtrlKey(final int columnPosition, final int rowPosition) {
		Rectangle selectedColumnRectangle = new Rectangle(columnPosition, 0, 1,
				Integer.MAX_VALUE);

		if (this.selectionLayer.isColumnPositionFullySelected(columnPosition)) {
			this.selectionLayer.clearSelection(selectedColumnRectangle);
			if (this.selectionLayer.getLastSelectedRegion() != null
					&& this.selectionLayer.getLastSelectedRegion().equals(
							selectedColumnRectangle)) {
				this.selectionLayer.setLastSelectedRegion(null);
			}
		} else {
			if (this.selectionLayer.getLastSelectedRegion() != null) {
				this.selectionLayer.getSelectionModel().addSelection(new Rectangle(
						this.selectionLayer.getLastSelectedRegion().x,
						this.selectionLayer.getLastSelectedRegion().y,
						this.selectionLayer.getLastSelectedRegion().width,
						this.selectionLayer.getLastSelectedRegion().height));
			}
			this.selectionLayer
					.selectRegion(columnPosition, 0, 1, Integer.MAX_VALUE);
			this.selectionLayer.moveSelectionAnchor(columnPosition, rowPosition);
		}
	}

	/**
	 * This allows to select the columns with the shift key.
	 * 
	 * @param columnPosition
	 *            The column position
	 */
	private void selectColumnWithShiftKey(final int columnPosition) {
		int numOfColumnsToIncludeInRegion = 1;
		int startColumnPosition = columnPosition;

		// if multiple selection is disabled, we need to ensure to only select
		// the current columnPosition
		// modifying the selection anchor here ensures that the anchor also
		// moves
		if (!this.selectionLayer.getSelectionModel().isMultipleSelectionAllowed()) {
			this.selectionLayer.getSelectionAnchor().columnPosition = columnPosition;
		}

		if (this.selectionLayer.getLastSelectedRegion() != null) {

			// Negative when we move left, but we are only concerned with the
			// num. of columns
			numOfColumnsToIncludeInRegion = Math.abs(this.selectionLayer
					.getSelectionAnchor().columnPosition - columnPosition) + 1;

			// Select to the Left
			if (columnPosition < this.selectionLayer.getSelectionAnchor().columnPosition) {
				startColumnPosition = columnPosition;
			} else {
				startColumnPosition = this.selectionLayer.getSelectionAnchor().columnPosition;
			}
		}
		this.selectionLayer.selectRegion(startColumnPosition, 0,
				numOfColumnsToIncludeInRegion, Integer.MAX_VALUE);
	}
}
