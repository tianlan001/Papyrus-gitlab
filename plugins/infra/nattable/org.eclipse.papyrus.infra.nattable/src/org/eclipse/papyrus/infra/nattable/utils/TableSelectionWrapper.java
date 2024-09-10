/*****************************************************************************
 * Copyright (c) 2013, 2017, 2020 CEA LIST.
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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 476618
 *  Vincent Lorenzo (CEA LIST) - bug 525221, 517617, 532452
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.utils;

import java.util.ArrayList;
import java.util.Collection;
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
 * Wrapper for the selection in the table
 */
public class TableSelectionWrapper {

	/**
	 * The table manager for the NatTable instance
	 */
	private final INattableModelManager manager;

	/**
	 * The {@link SelectionLayer} use by the NatTable instance
	 */
	private SelectionLayer selectionLayer;

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
	 * we need to get the cell order for test PasteCellsOverwriteByOneLine_Test, so we use a LinkedHashMap
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
	 * Constructor.
	 *
	 * @param selectionCells
	 *            The selected cells.
	 * @deprecated since 5.0
	 */
	@Deprecated
	public TableSelectionWrapper(final Collection<PositionCoordinate> selectionCells) {
		this(selectionCells, new HashMap<>(), new HashMap<>());
	}

	/**
	 * Constructor.
	 *
	 * @param selectionCells
	 *            The selected cells.
	 * @param fullySelectedRowObject
	 *            The selected rows (index or object affected).
	 * @param fullySelectedColumnObject
	 *            The selected columns (index or object affected).
	 * @deprecated since 5.0
	 */
	@Deprecated
	public TableSelectionWrapper(final Collection<PositionCoordinate> selectionCells, final Map<Integer, Object> fullySelectedRowObject, final Map<Integer, Object> fullySelectedColumnObject) {
		fillAllSelectedCellsMap(selectionCells);
		this.rowPositionAndAxis = fullySelectedRowObject;
		this.columnPositionAndAxis = fullySelectedColumnObject;
		this.manager = null;
	}

	/**
	 * Constructor.
	 *
	 * @param manager
	 *            the table manager from where the selection comes
	 * @param selectionCells
	 *            The selected cells.
	 * @since 5.0
	 */
	@Deprecated
	public TableSelectionWrapper(final INattableModelManager manager, final Collection<PositionCoordinate> selectionCells) {
		this(manager, selectionCells, new HashMap<>(), new HashMap<>());
	}

	/**
	 * Constructor.
	 *
	 * @param manager
	 *            the table manager from where the selection comes
	 * @param selectionCells
	 *            The selected cells.
	 * @param fullySelectedRowObject
	 *            The selected rows (index or object affected).
	 * @param fullySelectedColumnObject
	 *            The selected columns (index or object affected).
	 * @since 5.0
	 */
	@Deprecated
	public TableSelectionWrapper(final INattableModelManager manager, final Collection<PositionCoordinate> selectionCells, final Map<Integer, Object> fullySelectedRowObject, final Map<Integer, Object> fullySelectedColumnObject) {
		this.manager = manager;
		this.selectionLayer = null;
		fillAllSelectedCellsMap(selectionCells);
		this.rowPositionAndAxis = fullySelectedRowObject;
		this.columnPositionAndAxis = fullySelectedColumnObject;


	}

	/**
	 * Constructor.
	 *
	 * @param manager
	 *            the table manager from where the selection comes
	 * @param the
	 *            selection layer
	 * @param selectionCells
	 *            The selected cells.
	 * @since 7.0
	 */
	public TableSelectionWrapper(final INattableModelManager manager, final SelectionLayer selectionLayer, final Collection<PositionCoordinate> selectionCells) {
		this.manager = manager;
		this.selectionLayer = selectionLayer;
		fillAllSelectedCellsMap(selectionCells);
	}

	/**
	 * This method fill a map with the selected cell, referencing the initial row and column object
	 *
	 * @param selectedCells
	 *            the selected cells
	 */
	private void fillAllSelectedCellsMap(final Collection<PositionCoordinate> selectedCells) {
		if (this.selectionLayer == null && manager != null) {
			this.selectionLayer = this.manager.getBodyLayerStack().getSelectionLayer();
		}
		if (selectionLayer == null) {
			return;
		}
		for (final PositionCoordinate positionCoordinate : selectedCells) {
			final ILayerCell cell = this.selectionLayer.getCellByPosition(positionCoordinate.getColumnPosition(), positionCoordinate.getRowPosition());
			if (cell != null) {
				int columnIndex = this.selectionLayer.getColumnIndexByPosition(positionCoordinate.getColumnPosition());
				int rowIndex = this.selectionLayer.getRowIndexByPosition(positionCoordinate.getRowPosition());
				final Object columnElement = this.manager.getColumnElement(columnIndex);
				final Object rowElement = this.manager.getRowElement(rowIndex);
				final CellReference reference = new CellReference(columnElement, rowElement);
				this.allSelectedCells.put(positionCoordinate, reference);
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
	 *
	 * @since 7.0
	 */
	public void updatePositions() {
		updateRowPositions();
		updateColumnPositions();
		updateCellPositions();
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
	 * @since 7.0
	 */
	public void buildSingleCellSelection() {
		for (final PositionCoordinate positionCoordinate : this.allSelectedCells.keySet()) {
			final ILayerCell cell = this.selectionLayer.getCellByPosition(positionCoordinate.getColumnPosition(), positionCoordinate.getRowPosition());
			if (cell != null) {

				/*
				 * if the selected cells is a single cell, we fill the map of single cells and add the cell value in the list
				 */
				if (isSingleSelectedCell(positionCoordinate)) {
					this.singleCellsCoordinatesAndUniqueReference.put(positionCoordinate, this.allSelectedCells.get(positionCoordinate));
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
	 *
	 * @return
	 *         a collection of the selected elements
	 * @since 7.0
	 */
	public Collection<Object> getSelectedElements() {
		final Collection<Object> selection = new HashSet<>();
		selection.addAll(this.rowAxisElements);
		selection.addAll(this.columnAxisElements);
		selection.addAll(this.singleCellValues);
		return selection;
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
	 *
	 * @return
	 *         the position, according to the {@link SelectionLayer} of single cells
	 * @since 7.0
	 */
	public Set<PositionCoordinate> getSingleSelectedCells() {
		return this.singleCellsCoordinatesAndUniqueReference.keySet();
	}

	/**
	 * Get the selected cells.
	 *
	 * @return
	 *         the list of the {@link PositionCoordinate} of all selected cells
	 */
	public Collection<PositionCoordinate> getSelectedCells() {
		return allSelectedCells.keySet();
	}

	/**
	 * Get the selected rows.
	 *
	 * @return
	 *         a map with the fully selected rows and their position in the selection layer
	 */
	public Map<Integer, Object> getFullySelectedRows() {
		return this.rowPositionAndAxis;
	}

	/**
	 * This method allows to register a new row in the current selection
	 *
	 * @param rowPosition
	 *            the position of the selected row
	 *
	 * @since 7.0
	 */
	public void addSelectedRow(final int rowPosition) {
		final int rowIndex = this.selectionLayer.getRowIndexByPosition(rowPosition);
		Object el = this.manager.getRowElement(rowIndex);
		this.rowPositionAndAxis.put(Integer.valueOf(rowPosition), el);
		final Object realElement = AxisUtils.getRepresentedElement(el);
		if (realElement != null) {
			this.rowAxisElements.add(realElement);
		} else {
			this.rowAxisElements.add(el);
		}
	}

	/**
	 * This method allows to remove a row in the current selection
	 *
	 * @param rowPosition
	 *            the position of the selected row
	 *
	 * @since 7.0
	 */
	public void removeSelectedRow(final int rowPosition) {
		final int rowIndex = this.selectionLayer.getRowIndexByPosition(rowPosition);
		Object el = this.manager.getRowElement(rowIndex);
		this.rowPositionAndAxis.remove(Integer.valueOf(rowPosition));
		final Object realElement = AxisUtils.getRepresentedElement(el);
		if (realElement != null) {
			this.rowAxisElements.remove(realElement);
		} else {
			this.rowAxisElements.remove(el);
		}
	}

	/**
	 * This method allows to register a new column in the current selection
	 *
	 * @param columnPosition
	 *            the position of the selected column
	 *
	 * @since 7.0
	 */
	public void addSelectedColumn(final int columnPosition) {
		final int columnIndex = this.selectionLayer.getColumnIndexByPosition(columnPosition);
		Object el = this.manager.getColumnElement(columnIndex);
		this.columnPositionAndAxis.put(Integer.valueOf(columnPosition), el);
		final Object realElement = AxisUtils.getRepresentedElement(el);
		if (realElement != null) {
			this.columnAxisElements.add(realElement);
		} else {
			this.columnAxisElements.add(el);
		}
	}

	/**
	 * This method allows to remove a column in the current selection
	 *
	 * @param columnPosition
	 *            the position of the selected column
	 *
	 * @since 7.0
	 */
	public void removeSelectedColumn(final int columnPosition) {
		final int rowIndex = this.selectionLayer.getRowIndexByPosition(columnPosition);
		Object el = this.manager.getColumnElement(rowIndex);
		this.columnPositionAndAxis.remove(Integer.valueOf(columnPosition));
		final Object realElement = AxisUtils.getRepresentedElement(el);
		if (realElement != null) {
			this.columnAxisElements.remove(realElement);
		} else {
			this.columnAxisElements.remove(el);
		}
	}

	/**
	 * Get the selected columns.
	 *
	 * @return
	 *         a map with the fully selected columns and their position in the selection layer
	 */
	public Map<Integer, Object> getFullySelectedColumns() {
		return this.columnPositionAndAxis;
	}

	/**
	 * Returns a boolean determining if at least one selected cell is outside of the selected rows and columns.
	 *
	 * @return <code>true</code> if at least one selected cell is outside of the selected rows and columns, <code>false</code> otherwise.
	 */
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
	 * This method clear the values stored in the collections and in the maps of this wrapper
	 */
	public void clearWrappedSelection() {
		this.rowPositionAndAxis.clear();
		this.columnPositionAndAxis.clear();
		this.allSelectedCells.clear();
		this.rowAxisElements.clear();
		this.columnAxisElements.clear();
		this.singleCellValues.clear();
		this.singleCellsCoordinatesAndUniqueReference.clear();
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 *
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof TableSelectionWrapper)) {
			return false;
		}

		if (o == this) {
			return true;
		}

		final TableSelectionWrapper w2 = (TableSelectionWrapper) o;
		if (w2.getNatTableModelManager() != this.manager) {
			return false;
		}
		if (w2.getNatTableModelManager() == this.manager && w2.getFullySelectedColumns().equals(this.columnPositionAndAxis)
				&& w2.getFullySelectedRows().equals(this.rowPositionAndAxis)
				&& w2.getSelectedCells().equals(this.allSelectedCells.keySet())
				&& w2.getSingleSelectedCells().equals(this.singleCellValues)) {
			return true;
		}
		return false;
	}

	/**
	 *
	 * @return
	 *         the table manager from where the selection comes or <code>null</code> if a deprecated constructor has been used
	 * @since 5.0
	 */
	public INattableModelManager getNatTableModelManager() {
		return this.manager;
	}


	/**
	 * This method is used to fill referenced rows from another {@link TableSelectionWrapper}
	 *
	 * @param sourceWrapper
	 *            the source selection wrapper used to copy the rows selection
	 *
	 * @since 7.0
	 */
	public void copyRowsSelection(final TableSelectionWrapper sourceWrapper) {
		this.rowPositionAndAxis.putAll(sourceWrapper.rowPositionAndAxis);
		this.rowAxisElements.addAll(sourceWrapper.rowAxisElements);
	}

	/**
	 * This method is used to fill referenced columns from another {@link TableSelectionWrapper}
	 *
	 * @param sourceWrapper
	 *            the source selection wrapper used to copy the columns selection
	 *
	 * @since 7.0
	 */
	public void copyColumnsSelection(final TableSelectionWrapper sourceWrapper) {
		this.columnPositionAndAxis.putAll(sourceWrapper.columnPositionAndAxis);
		this.columnAxisElements.addAll(sourceWrapper.columnAxisElements);
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
