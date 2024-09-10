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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectRowCommandHandler;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectRowsCommand;
import org.eclipse.nebula.widgets.nattable.selection.event.RowSelectionEvent;
import org.eclipse.papyrus.infra.nattable.layer.PapyrusSelectionLayer;
import org.eclipse.swt.graphics.Rectangle;

/**
 * The select row command handler extended from the select row command handler (from natable) to manage th papyrus row selection event.
 */
public class PapyrusSelectRowCommandHandler extends SelectRowCommandHandler {

	/**
	 * The papyrus selection layer.
	 */
	private final PapyrusSelectionLayer selectionLayer;

	/**
	 * Constructor.
	 *
	 * @param selectionLayer
	 *            The selection layer.
	 */
	public PapyrusSelectRowCommandHandler(final PapyrusSelectionLayer selectionLayer) {
		super(selectionLayer);
		this.selectionLayer = selectionLayer;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.selection.SelectRowCommandHandler#doCommand(org.eclipse.nebula.widgets.nattable.layer.ILayer, org.eclipse.nebula.widgets.nattable.selection.command.SelectRowsCommand)
	 */
	@Override
	public boolean doCommand(final ILayer targetLayer, final SelectRowsCommand command) {
		if (command.convertToTargetLayer(this.selectionLayer)) {
			selectRows(
					command.getColumnPosition(),
					command.getRowPositions(),
					command.isWithShiftMask(),
					command.isWithControlMask(),
					command.getRowPositionToMoveIntoViewport());
			return true;
		}
		return false;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.selection.SelectRowCommandHandler#selectRows(int, java.util.Collection, boolean, boolean, int)
	 */
	protected void selectRows(
			final int columnPosition, Collection<Integer> rowPositions,
			final boolean withShiftMask, final boolean withControlMask,
			final int rowPositionToMoveIntoViewport) {

		Set<Range> changedRowRanges = new HashSet<Range>();

		for (int rowPosition : rowPositions) {
			changedRowRanges.addAll(
					internalSelectRow(
							columnPosition, rowPosition,
							withShiftMask, withControlMask));
		}

		Set<Integer> changedRows = new HashSet<Integer>();
		for (Range range : changedRowRanges) {
			for (int i = range.start; i < range.end; i++) {
				changedRows.add(Integer.valueOf(i));
			}
		}
		this.selectionLayer.fireLayerEvent(
				new RowSelectionEvent(
						this.selectionLayer, changedRows, rowPositionToMoveIntoViewport, withShiftMask, withControlMask));
	}

	/**
	 * Delegates the selection operations to execute regarding the state
	 * modifier keys.
	 *
	 * @param columnPosition
	 *            The column position of the {@link SelectRowsCommand}.
	 * @param rowPositions
	 *            The row position of the {@link SelectRowsCommand}.
	 * @param withShiftMask
	 *            The shift mask information of the {@link SelectRowsCommand}.
	 * @param withControlMask
	 *            The control mask information of the {@link SelectRowsCommand}.
	 * @return The changed selection.
	 */
	private Set<Range> internalSelectRow(
			final int columnPosition, final int rowPosition,
			final boolean withShiftMask, final boolean withControlMask) {

		Set<Range> changedRowRanges = new HashSet<Range>();

		if (noShiftOrControl(withShiftMask, withControlMask)) {
			changedRowRanges.addAll(this.selectionLayer.getSelectedRowPositions());
			this.selectionLayer.clear(false);
			this.selectionLayer.selectCell(0, rowPosition, withShiftMask, withControlMask);
			this.selectionLayer.selectRegion(0, rowPosition, Integer.MAX_VALUE, 1);
			this.selectionLayer.moveSelectionAnchor(columnPosition, rowPosition);
			changedRowRanges.add(new Range(rowPosition, rowPosition + 1));
		} else if (bothShiftAndControl(withShiftMask, withControlMask)) {
			changedRowRanges.add(selectRowWithShiftKey(columnPosition, rowPosition));
		} else if (isShiftOnly(withShiftMask, withControlMask)) {
			changedRowRanges.add(selectRowWithShiftKey(columnPosition, rowPosition));
		} else if (isControlOnly(withShiftMask, withControlMask)) {
			changedRowRanges.add(selectRowWithCtrlKey(columnPosition, rowPosition));
		}

		this.selectionLayer.setLastSelectedCell(columnPosition, rowPosition);

		return changedRowRanges;
	}

	/**
	 * Performs selection operations with pressed CTRL modifier.
	 *
	 * @param columnPosition
	 *            The column position of the {@link SelectRowsCommand}. Needed
	 *            to move the selection anchor.
	 * @param rowPositions
	 *            The row position of the {@link SelectRowsCommand}.
	 * @return The changed selection.
	 */
	private Range selectRowWithCtrlKey(final int columnPosition, final int rowPosition) {
		Rectangle selectedRowRectangle = new Rectangle(0, rowPosition, Integer.MAX_VALUE, 1);

		if (this.selectionLayer.isRowPositionFullySelected(rowPosition)) {
			this.selectionLayer.clearSelection(selectedRowRectangle);
			this.selectionLayer.setLastSelectedRegion(null);

			// if there is still a row selected but no selection anchor, we
			// need to set one for a consistent state
			int[] selectedRows = this.selectionLayer.getFullySelectedRowPositions();
			if (selectedRows.length > 0
					&& this.selectionLayer.getSelectionAnchor().rowPosition == SelectionLayer.NO_SELECTION) {

				// determine row to move the anchor to
				int toPos = selectedRows[0];
				for (int i = 0; i < selectedRows.length; i++) {
					if (selectedRows[i] < rowPosition) {
						toPos = selectedRows[i];
					} else {
						break;
					}
				}
				this.selectionLayer.moveSelectionAnchor(columnPosition, toPos);
			}
		} else {
			// Preserve last selected region
			if (this.selectionLayer.getLastSelectedRegion() != null) {
				this.selectionLayer.selectRegion(
						this.selectionLayer.getLastSelectedRegion().x,
						this.selectionLayer.getLastSelectedRegion().y,
						this.selectionLayer.getLastSelectedRegion().width,
						this.selectionLayer.getLastSelectedRegion().height);
			}
			this.selectionLayer.selectRegion(0, rowPosition, Integer.MAX_VALUE, 1);
			this.selectionLayer.moveSelectionAnchor(columnPosition, rowPosition);
		}

		return new Range(rowPosition, rowPosition + 1);
	}

	/**
	 * Performs selection operations with pressed SHIFT modifier.
	 *
	 * @param columnPosition
	 *            The column position of the {@link SelectRowsCommand}. Needed
	 *            to move the selection anchor.
	 * @param rowPositions
	 *            The row position of the {@link SelectRowsCommand}.
	 * @return The changed selection.
	 */
	private Range selectRowWithShiftKey(final int columnPosition, final int rowPosition) {
		int numOfRowsToIncludeInRegion = 1;
		int startRowPosition = rowPosition;

		// as this method will return the whole range based on the selection
		// anchor and the clicked position, we clear the selection prior adding
		// the newly calculated selection.
		this.selectionLayer.getSelectionModel().clearSelection();

		// if multiple selection is disabled, we need to ensure to only moving
		// the selection anchor
		if (!this.selectionLayer.getSelectionModel().isMultipleSelectionAllowed()) {
			this.selectionLayer.moveSelectionAnchor(columnPosition, rowPosition);
		}

		if (this.selectionLayer.getLastSelectedRegion() != null) {
			numOfRowsToIncludeInRegion = Math.abs(this.selectionLayer.getSelectionAnchor().rowPosition - rowPosition) + 1;
			if (startRowPosition < this.selectionLayer.getSelectionAnchor().rowPosition) {
				// Selecting above
				startRowPosition = rowPosition;
			} else {
				// Selecting below
				startRowPosition = this.selectionLayer.getSelectionAnchor().rowPosition;
			}
		}
		this.selectionLayer.selectRegion(
				0, startRowPosition, Integer.MAX_VALUE, numOfRowsToIncludeInRegion);

		return new Range(startRowPosition, startRowPosition + numOfRowsToIncludeInRegion);
	}

}
