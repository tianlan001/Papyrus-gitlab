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
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 *
 * This class is in charge to store the selection (selection position, IAxis and elements owned by the IAxis)
 * This class also provides a method to update the position of the selection using the real elements
 *
 * This class uses the {@link SelectionLayer} to define the current selection.
 * We don't use it in the general usecase, because with this implementation we lose the difference between a user cell selection and the row/column
 * selection. Losing this difference, we break arount 30 JUnit tests in Papyrus.
 *
 * @author Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>
 * @since 7.0
 *
 */
// TODO inheritance is here just for retrocompatibility.
// remove inheritance in Papyrus 5.0 and create common API (see bug 564477)
public class TableSelectionWrapper2 extends TableSelectionWrapper {

	/**
	 * The table manager for the NatTable instance
	 */
	private final INattableModelManager manager;

	/**
	 * The {@link SelectionLayer} use by the NatTable instance
	 */
	private final SelectionLayer selectionLayer;

	/*---------------------------ROWS----------------------*/
	/**
	 * This map save the row position in the {@link SelectionLayer} and the element associated to this row.
	 * Here, it is the element in the row list managed by NatTable (so very probably an IAxis, excepted in particular table)
	 */
	private Map<Integer, Object> rowPositionAndAxis = new HashMap<>();

	/**
	 * The elements represented by the row IAxis (or the IAxis itself, when the represented element is <code>null</code>)
	 */
	private final Collection<Object> rowAxisElements = new HashSet<>();

	/*---------------------------COLUMNS--------------------*/
	/**
	 * This map save the column position in the {@link SelectionLayer} and the element associated to this column.
	 * Here, it is the element in the column list managed by NatTable (so very probably an IAxis, excepted in particular table)
	 */
	private Map<Integer, Object> columnPositionAndAxis = new HashMap<>();

	/**
	 * The elements represented by the row IAxis (or the IAxis itself, when the represented element is <code>null</code>)
	 */
	private final Collection<Object> columnAxisElements = new HashSet<>();

	/*---------------------------CELL--------------------*/
	/**
	 * The list of all selected cells (including cells from a fully selected rows or columns)
	 * we need to kep the cell order for test PasteCellsOverwriteByOneLine_Test, so we use a LinkedHashMap
	 * when we fill the map, the cell are already ordinated by NatTable
	 */
	private final Map<PositionCoordinate, CellReference> allSelectedCells = new LinkedHashMap<>();

	/**
	 * This element represents the coordinate of single selected cells (cells outside of a selected rows and outside of a selected columns)
	 * and the associated CellReference object pointing on row IAxis and column IAxis;
	 */
	private final Map<PositionCoordinate, CellReference> singleCellsCoordinatesAndUniqueReference = new HashMap<>();

	/**
	 * This collection contains all values of single selected cells
	 */
	private final Collection<Object> singleCellValues = new ArrayList<>();

	/**
	 * this value allows to know if the current selection is equivalent to a select all
	 */
	private boolean isSelectAll = false;



	public TableSelectionWrapper2(final INattableModelManager manager, final SelectionLayer selectionLayer) {
		super(manager, Collections.emptyList(), Collections.emptyMap(), Collections.emptyMap());
		this.manager = manager;
		this.selectionLayer = selectionLayer;
		buildSelection();
	}

	/**
	 * This method is used to build the selection
	 */
	protected final void buildSelection() {
		// we are not able to distinguish the 2 ways to select a full axis :
		// - first way : clicking on axis header
		// - second way : clicking on first cell of the axis, Pressing SHIFT, clicking on the last cell of the axis (or selecting each cell of the axis pressing CTRL)
		// so we are not able to know if the user want to select the element represented by the axis OR all values displayed on the axis, without the element represented by the axis
		// we decided to implements this behavior for all kind of selection event :
		// 1- we add in the selection elements represented by fully selected rows
		// 2- we add in the selection elements represented by fully selected columns
		// 3- we add in the selection the contents of selected cell which are not included in the fully selected axis
		buildColumnSelection();
		buildRowSelection();
		/*
		 * The cell selection must be done last, because it reuse the row and column selection
		 */
		buildCellSelection();

		calculateSelectAll();
	}

	/**
	 * This method builds the column selection. It fills the fields {@link #columnAxisElements} and {@link #columnPositionAndAxis}
	 */
	protected void buildColumnSelection() {
		for (int currentPosition : this.selectionLayer.getFullySelectedColumnPositions()) {
			int currentIndex = this.selectionLayer.getColumnIndexByPosition(currentPosition);
			final Object el = this.manager.getColumnElement(currentIndex);
			this.columnPositionAndAxis.put(Integer.valueOf(currentPosition), el);
			final Object representedElement = AxisUtils.getRepresentedElement(el);
			if (representedElement != null) {
				this.columnAxisElements.add(representedElement);
			} else {
				// usefull or not ?
				this.columnAxisElements.add(el);
			}
		}
	}

	/**
	 * This method builds the column selection. It fills the fields {@link #rowAxisElements} and {@link #rowPositionAndAxis}
	 */
	protected void buildRowSelection() {
		for (int currentPosition : this.selectionLayer.getFullySelectedRowPositions()) {
			int currentIndex = this.selectionLayer.getRowIndexByPosition(currentPosition);
			final Object el = this.manager.getRowElement(currentIndex);
			this.rowPositionAndAxis.put(Integer.valueOf(currentPosition), el);
			final Object representedElement = AxisUtils.getRepresentedElement(el);
			if (representedElement != null) {
				this.rowAxisElements.add(representedElement);
			} else {
				// usefull or not ?
				this.rowAxisElements.add(el);
			}
		}
	}

	/**
	 * This method builds the cell selection. It fills the fields {#allSelectedCells} {@link #singleSelectedCells} and {@link #singleSelectedCells}
	 */
	protected void buildCellSelection() {
		for (final PositionCoordinate positionCoordinate : this.selectionLayer.getSelectedCellPositions()) {
			final ILayerCell cell = this.selectionLayer.getCellByPosition(positionCoordinate.getColumnPosition(), positionCoordinate.getRowPosition());
			if (cell != null) {
				int columnIndex = this.selectionLayer.getColumnIndexByPosition(positionCoordinate.getColumnPosition());
				int rowIndex = this.selectionLayer.getRowIndexByPosition(positionCoordinate.getRowPosition());
				final Object columnElement = this.manager.getColumnElement(columnIndex);
				final Object rowElement = this.manager.getRowElement(rowIndex);
				final CellReference reference = new CellReference(columnElement, rowElement);
				this.allSelectedCells.put(positionCoordinate, reference);

				/*
				 * if the selected cells is a single cell, we also fill the map of single cells and add the cell value in the list
				 */
				if (isSingleSelectedCell(positionCoordinate)) {
					this.singleCellsCoordinatesAndUniqueReference.put(positionCoordinate, reference);
					final Object value = cell.getDataValue();
					if (value != null) {
						if (value instanceof Collection<?>) {
							final Iterator<?> iter = ((Collection<?>) value).iterator();
							while (iter.hasNext()) {
								final Object current = iter.next();
								this.singleCellValues.add(current);
							}
						} else {
							this.singleCellValues.add(value);
						}
					} else {
						// Bug 481817 : When the value is null, we need to have the cell selection, so add the cell as selection instead of value
						this.singleCellValues.add(cell);
					}
				}
			}
		}
	}

	/**
	 * This method recalculates the position of selected elements.
	 * To use this method, you need to keep a reference to an instance of this class, do some stuff changing element location,
	 * then you can update them.
	 *
	 * This method has been developed to re-apply the selection after a refresh. To do that, the position of selected elements must updated
	 * (org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager.refreshInUIThread())
	 */
	@Override
	public void updatePositions() {
		updateRowPositions();
		updateColumnPositions();
		updateCellPositions();
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the selection represents a select all
	 */
	public boolean isSelectAll() {
		return this.isSelectAll;
	}

	/**
	 * This method must be called just after the build of the selection (method {@link #buildSelection()}
	 * This method MUST not be called after {@link #updatePosition()}, because some elements can have changed (columns/rows destruction/creation)
	 *
	 */
	private void calculateSelectAll() {
		boolean res = false;
		int columnCount = this.selectionLayer.getColumnCount();
		int rowCount = this.selectionLayer.getRowCount();
		if (this.rowPositionAndAxis.size() >= rowCount && this.columnPositionAndAxis.size() >= columnCount) {
			res = true;
		}
		this.isSelectAll = res;

	}

	/**
	 * This method updates the position of the selected rows
	 */
	private void updateRowPositions() {
		final Map<Integer, Object> rows = new HashMap<>();
		for (Object current : this.rowPositionAndAxis.values()) {
			int index = this.manager.getRowElementsList().indexOf(current);
			if (index >= 0) {
				int position = this.selectionLayer.getRowPositionByIndex(index);
				rows.put(Integer.valueOf(position), current);
			} else {
				this.rowAxisElements.remove(AxisUtils.getRepresentedElement(current));
				this.rowAxisElements.remove(current);
			}
		}
		this.rowPositionAndAxis = rows;
	}

	/**
	 * This method updates the position of the selected columns
	 */
	private void updateColumnPositions() {
		final Map<Integer, Object> columns = new HashMap<>();
		for (Object current : columnPositionAndAxis.values()) {
			int index = this.manager.getColumnElementsList().indexOf(current);
			if (index >= 0) {
				int position = this.selectionLayer.getColumnPositionByIndex(index);
				columns.put(Integer.valueOf(position), current);
			} else {
				this.columnAxisElements.remove(AxisUtils.getRepresentedElement(current));
				this.columnAxisElements.remove(current);
			}
		}
		this.columnPositionAndAxis = columns;
	}

	/**
	 * This method updates the position of the selected cells
	 */
	private void updateCellPositions() {
		final Collection<PositionCoordinate> toRemove = new ArrayList<>();
		// as the field allSelectedCells and singleSelectedCells use the same key, we just have to update allSelectedCells
		for (final Entry<PositionCoordinate, CellReference> current : this.allSelectedCells.entrySet()) {
			final PositionCoordinate pos = current.getKey();
			final CellReference ref = current.getValue();
			int columnIndex = this.manager.getColumnElementsList().indexOf(ref.getColumnAxis());
			int rowIndex = this.manager.getRowElementsList().indexOf(ref.getRowAxis());
			if (rowIndex >= 0 && columnIndex >= 0) {
				int columnPosition = this.selectionLayer.getColumnPositionByIndex(columnIndex);
				int rowPosition = this.selectionLayer.getRowPositionByIndex(rowIndex);
				if (columnPosition >= 0 && rowPosition >= 0) {
					pos.setColumnPosition(columnPosition);
					pos.setRowPosition(rowPosition);
				} else {
					toRemove.add(pos);
				}
			} else {
				toRemove.add(pos);
			}
		}
		for (PositionCoordinate removeMe : toRemove) {
			this.singleCellsCoordinatesAndUniqueReference.remove(removeMe);
		}
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper#getFullySelectedRows()
	 *
	 * @return
	 */
	@Override
	public Map<Integer, Object> getFullySelectedRows() {
		return this.rowPositionAndAxis;
	}

	/**
	 *
	 * @return
	 *         the position, according to the {@link SelectionLayer} of single cells
	 */
	@Override
	public Set<PositionCoordinate> getSingleSelectedCells() {
		return this.singleCellsCoordinatesAndUniqueReference.keySet();
	}

	/**
	 *
	 * @param cellLocation
	 * @return <code>true</code> if the cell is outside of a fully selected row and outside of a fully selected column
	 */
	private boolean isSingleSelectedCell(final PositionCoordinate cellLocation) {
		return !this.rowPositionAndAxis.containsKey(cellLocation.getRowPosition())
				&& !this.columnPositionAndAxis.containsKey(cellLocation.getColumnPosition());
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper#getFullySelectedRows()
	 *
	 * @return
	 */
	@Override
	public Map<Integer, Object> getFullySelectedColumns() {
		return this.columnPositionAndAxis;
	}

	/**
	 *
	 * @return
	 *         a collection of the selected elements
	 */
	@Override
	public Collection<Object> getSelectedElements() {
		final Collection<Object> selection = new HashSet<>();
		selection.addAll(this.rowAxisElements);
		selection.addAll(this.columnAxisElements);
		selection.addAll(this.singleCellValues);
		return selection;
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper#getSelectedCells()
	 *
	 * @return
	 */
	@Override
	public Collection<PositionCoordinate> getSelectedCells() {
		return this.allSelectedCells.keySet();
	}

	/**
	 * Returns a boolean determining if at least one selected cell is outside of the selected rows and columns.
	 *
	 * @return <code>true</code> if at least one selected cell is outside of the selected rows and columns, <code>false</code> otherwise.
	 */
	@Override
	public boolean isCellsOutsideOfAxis() {
		boolean result = false;

		// Loop on each selected cells to manage if one cell is outside of selected rows or columns
		final Iterator<PositionCoordinate> selectedCells = this.allSelectedCells.keySet().iterator();
		while (!result && selectedCells.hasNext()) {
			final PositionCoordinate selectedCell = selectedCells.next();

			// Check that the selected cell is not outside of selected rows and columns
			result = !(this.rowPositionAndAxis.containsKey(selectedCell.getRowPosition())
					|| this.columnPositionAndAxis.containsKey(selectedCell.getColumnPosition()));
		}

		return result;
	}

	/**
	 * Check if the selected rows are continuous.
	 *
	 * @return <code>true</code> if the selected rows are continuous, <code>false</code> otherwise.
	 */
	@Override
	public boolean isContinuousRows() {
		int firstRowIndex = -1;
		int lastRowIndex = -1;

		// To check if the selected rows are continuous, calculate :
		// - The first row index
		// - The last row index
		// To finalize :
		// - The number of selected rows must be equals to last index subtracted to the first index
		for (final int rowIndex : getFullySelectedRows().keySet()) {
			if (-1 == firstRowIndex || rowIndex < firstRowIndex) {
				firstRowIndex = rowIndex;
			}
			if (-1 == lastRowIndex || rowIndex > lastRowIndex) {
				lastRowIndex = rowIndex;
			}
		}

		return (lastRowIndex - firstRowIndex + 1) == getFullySelectedRows().size();
	}

	/**
	 * Check if the selected columns are continuous.
	 *
	 * @return <code>true</code> if the selected columns are continuous, <code>false</code> otherwise.
	 */
	@Override
	public boolean isContinuousColumns() {
		int firstColumnIndex = -1;
		int lastColumnIndex = -1;

		// To check if the selected columns are continuous, calculate :
		// - The first column index
		// - The last column index
		// To finalize :
		// - The number of selected column must be equals to last index subtracted to the first index
		for (final int rowIndex : getFullySelectedColumns().keySet()) {
			if (-1 == firstColumnIndex || rowIndex < firstColumnIndex) {
				firstColumnIndex = rowIndex;
			}
			if (-1 == lastColumnIndex || rowIndex > lastColumnIndex) {
				lastColumnIndex = rowIndex;
			}
		}

		return (lastColumnIndex - firstColumnIndex + 1) == getFullySelectedColumns().size();
	}

	/**
	 * Check if the selected cells are continuous.
	 *
	 * @return <code>true</code> if the selected cells are continuous, <code>false</code> otherwise.
	 */
	@Override
	public boolean isContinuousCells() {
		int firstRowIndex = -1;
		int lastRowIndex = -1;
		int firstColumnIndex = -1;
		int lastColumnIndex = -1;

		// To check if the selected cells are continuous, calculate :
		// - The first row index
		// - The last row index
		// - The first column index
		// - The last column index
		// To finalize :
		// - The number of selected cells must be equals to number of row index multiplied to number of column index
		for (final PositionCoordinate currentPosition : getSelectedCells()) {
			if (-1 == firstRowIndex || currentPosition.getRowPosition() < firstRowIndex) {
				firstRowIndex = currentPosition.getRowPosition();
			}
			if (-1 == firstColumnIndex || currentPosition.getColumnPosition() < firstColumnIndex) {
				firstColumnIndex = currentPosition.getColumnPosition();
			}
			if (-1 == lastRowIndex || currentPosition.getRowPosition() > lastRowIndex) {
				lastRowIndex = currentPosition.getRowPosition();
			}
			if (-1 == lastColumnIndex || currentPosition.getColumnPosition() > lastColumnIndex) {
				lastColumnIndex = currentPosition.getColumnPosition();
			}
		}

		return ((lastColumnIndex - firstColumnIndex + 1) * (lastRowIndex - firstRowIndex + 1)) == getSelectedCells().size();
	}


	/**
	 * @see org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper#clearWrappedSelection()
	 *
	 */
	@Override
	public void clearWrappedSelection() {
		super.clearWrappedSelection();
		this.columnAxisElements.clear();
		this.rowAxisElements.clear();
		this.columnPositionAndAxis.clear();
		this.rowPositionAndAxis.clear();
		this.singleCellsCoordinatesAndUniqueReference.clear();
		this.singleCellValues.clear();
		this.allSelectedCells.clear();
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper#equals(java.lang.Object)
	 *
	 * @param o
	 * @return
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof TableSelectionWrapper2)) {
			return false;
		}

		final TableSelectionWrapper2 wrapper = (TableSelectionWrapper2) o;

		if (wrapper.getNatTableModelManager() != this.manager) {
			return false;
		}
		if (wrapper.isSelectAll() != isSelectAll()) {
			return false;
		}
		if (!wrapper.getFullySelectedColumns().equals(getFullySelectedColumns())) {
			return false;
		}
		if (!wrapper.getFullySelectedRows().equals(getFullySelectedRows())) {
			return false;
		}
		if (!wrapper.getSingleSelectedCells().equals(getSingleSelectedCells())) {
			return false;
		}
		if (wrapper.getSelectedCells().equals(getSelectedCells())) {
			return false;
		}
		return true;
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper#getNatTableModelManager()
	 *
	 * @return
	 */
	@Override
	public INattableModelManager getNatTableModelManager() {
		return this.manager;
	}


	/**
	 *
	 * Util class used to save cell selection using row/column as pointer of the cell
	 *
	 */
	private final class CellReference {

		/**
		 * the column axis
		 */
		private Object columnAxis;

		/**
		 * the row axis
		 */
		private Object rowAxis;

		/**
		 *
		 * Constructor.
		 *
		 * @param columnAxis
		 *            the column axis on which is located to cell to reference
		 * @param rowAxis
		 *            the row axis on which is located to cell to reference
		 *
		 */
		public CellReference(final Object columnAxis, final Object rowAxis) {
			this.columnAxis = columnAxis;
			this.rowAxis = rowAxis;
		}

		/**
		 * @return the columnAxis
		 */
		public Object getColumnAxis() {
			return columnAxis;
		}

		/**
		 * @return the rowAxis
		 */
		public Object getRowAxis() {
			return rowAxis;
		}

	}
}
