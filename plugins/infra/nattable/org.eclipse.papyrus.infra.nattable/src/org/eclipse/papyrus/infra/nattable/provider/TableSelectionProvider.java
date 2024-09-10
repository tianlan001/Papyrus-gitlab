/*****************************************************************************
 * Copyright (c) 2012, 2017, 2020 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) - bug 525221, 561370, 517617, 532452
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.nebula.widgets.nattable.coordinate.PositionCoordinate;
import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayerListener;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.event.CellSelectionEvent;
import org.eclipse.nebula.widgets.nattable.selection.event.ColumnSelectionEvent;
import org.eclipse.nebula.widgets.nattable.selection.event.ISelectionEvent;
import org.eclipse.nebula.widgets.nattable.selection.event.RowSelectionEvent;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper2;
import org.eclipse.papyrus.infra.nattable.utils.TypeSelectionEnum;
import org.eclipse.papyrus.infra.tools.util.ListHelper;
import org.eclipse.ui.services.IDisposable;

/**
 * The selection provider used by the table. The returned selection is used to display property views or to do the synchronization in the
 * ModelExplorer
 */
public class TableSelectionProvider implements ISelectionProvider, IDisposable, ILayerListener {

	/**
	 * the selection layer used in the table
	 */
	private SelectionLayer selectionLayer;

	/**
	 * the selection listener
	 */
	private final ILayerListener selectionListener;

	/**
	 * the current selection in the table
	 */
	private ISelection currentSelection;

	/**
	 * the list of listener on the selections
	 */
	private final List<ISelectionChangedListener> listeners;

	/**
	 * the table model manager to use to find selected elements
	 */
	private INattableModelManager manager;

	/**
	 * boolean indicating than the class is disposed
	 */
	private boolean isDisposed = false;

	/**
	 * boolean indicating if we must listen notification or not
	 * (used to suspend selection construction during a refresh)
	 */
	private boolean isActive = true;

	/**
	 * Constructor.
	 *
	 * @param manager
	 *            the table model manager to use to find selected elements
	 * @param selectionLayer
	 *            the selection layer
	 */
	public TableSelectionProvider(final INattableModelManager manager, final SelectionLayer selectionLayer) {
		this.selectionLayer = selectionLayer;
		this.selectionListener = this;
		this.selectionLayer.addLayerListener(this.selectionListener);
		this.currentSelection = new StructuredSelection();
		this.listeners = new ArrayList<>();
		this.manager = manager;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ISelectionProvider#addSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void addSelectionChangedListener(final ISelectionChangedListener listener) {
		this.listeners.add(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ISelectionProvider#getSelection()
	 */
	@Override
	public synchronized ISelection getSelection() {
		return this.currentSelection;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ISelectionProvider#removeSelectionChangedListener(org.eclipse.jface.viewers.ISelectionChangedListener)
	 */
	@Override
	public void removeSelectionChangedListener(final ISelectionChangedListener listener) {
		this.listeners.remove(listener);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.viewers.ISelectionProvider#setSelection(org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public synchronized void setSelection(final ISelection selection) {
		if (this.currentSelection.equals(selection)) {
			return;
		}
		this.currentSelection = selection;
		final SelectionChangedEvent event = new SelectionChangedEvent(this, this.currentSelection);
		for (final ISelectionChangedListener current : this.listeners) {
			current.selectionChanged(event);
		}
	}

	/**
	 * This allows to calculate the new selection from the event and the layer.
	 *
	 * @param event
	 *            The event caught.
	 */
	protected void calculateAndStoreNewSelection(final ILayerEvent event) {
		// the list of the selected elements
		Collection<Object> selection = new HashSet<>();
		final ISelection newSelection;
		if (event instanceof ISelectionEvent) {
			TableSelectionWrapper wrapper = new TableSelectionWrapper(this.manager, this.selectionLayer, ListHelper.asList(this.selectionLayer.getSelectedCellPositions()));// , new HashMap<Integer, Object>(0), new HashMap<Integer, Object>(0));

			// Get the type selection event if it is a specific selection event
			TypeSelectionEnum typeSelectionEvent = TypeSelectionEnum.NONE;
			boolean isWithShiftMask = false;
			boolean isWithControlMask = false;

			// Check if it is a simple cell selection event
			if (event instanceof CellSelectionEvent) {
				typeSelectionEvent = TypeSelectionEnum.CELL;
				isWithShiftMask = ((CellSelectionEvent) event).isWithShiftMask();
				isWithControlMask = ((CellSelectionEvent) event).isWithControlMask();
				// Check if it is a column selection event
			} else if (event instanceof ColumnSelectionEvent) {
				typeSelectionEvent = TypeSelectionEnum.COLUMN;
				isWithShiftMask = ((ColumnSelectionEvent) event).isWithShiftMask();
				isWithControlMask = ((ColumnSelectionEvent) event).isWithControlMask();
				// Check if it is a row selection event
			} else if (event instanceof RowSelectionEvent) {
				typeSelectionEvent = TypeSelectionEnum.ROW;
				isWithShiftMask = ((RowSelectionEvent) event).isWithShiftMask();
				isWithControlMask = ((RowSelectionEvent) event).isWithControlMask();
			}

			// If this is a specific selection event, manage the selection with event
			if (!TypeSelectionEnum.NONE.equals(typeSelectionEvent)) {
				// The shift mask will be managed by adding all the selection between the previous selection and the current
				if (isWithShiftMask) {
					// Calculate the selection with the shift mask
					selection = calculateSelectionWithShiftMask(wrapper, typeSelectionEvent);
					// The control mask will be managed by adding the current selection to the previous one
				} else if (isWithControlMask) {
					// Calculate the selection with the control mask
					selection = calculateSelectionWithControlMask(wrapper, typeSelectionEvent, event);
				} else {
					// Manage the rows and the columns selection event
					selection = calculateSelectionRowsAndColumnsWithTypeSelectionEvent(wrapper, typeSelectionEvent, event);
				}

			} else {
				wrapper = new TableSelectionWrapper2(this.manager, this.selectionLayer);
				selection = calculateSelectionRowsAndColumnsWithoutTypeSelectionEvent(wrapper, event);
			}
			// If no selection appended, the selection must be the context of the table
			if (selection.isEmpty()) {
				newSelection = new TableStructuredSelection(manager.getTable().getContext(), wrapper);
			} else {
				newSelection = new TableStructuredSelection(selection.toArray(), wrapper);
			}
		} else {
			newSelection = new StructuredSelection(manager.getTable().getContext());
		}
		setSelection(newSelection);
	}

	/**
	 * This allows to calculate the selection when the shift key is pressed with the selection.
	 *
	 * @param wrapper
	 *            The table selection wrapper to fill.è
	 * @param typeSelectionEvent
	 *            The type of selection from the event (cell, row or column selection).
	 * @return The collection of object selected.
	 */
	protected Collection<Object> calculateSelectionWithShiftMask(final TableSelectionWrapper wrapper, final TypeSelectionEnum typeSelectionEvent) {
		// If a cell is selected with shift mask, only keep the line and column selected (the cells added with be added automatically
		if (TypeSelectionEnum.CELL.equals(typeSelectionEvent)) {
			if (this.currentSelection instanceof TableStructuredSelection) {
				// Add the previous selected rows and columns to the current wrapper
				final TableSelectionWrapper existingWrapper = (TableSelectionWrapper) ((TableStructuredSelection) currentSelection).getAdapter(TableSelectionWrapper.class);
				wrapper.copyRowsSelection(existingWrapper);
				wrapper.copyColumnsSelection(existingWrapper);
			}
			// If a row is selected with shift mask, the selected rows by the layer must be the wrapper selected rows
		} else if (TypeSelectionEnum.ROW.equals(typeSelectionEvent)) {
			for (final int rowPosition : this.selectionLayer.getFullySelectedRowPositions()) {
				if (!wrapper.getFullySelectedRows().containsKey(rowPosition)) {
					// Check if the row to select is corresponding to at least one cell
					if (isSelectedCellsContainsRow(wrapper.getSelectedCells(), rowPosition)) {
						wrapper.addSelectedRow(rowPosition);
					}
				}
			}
			// If a row is selected with shift mask, the selected rows by the layer must be the wrapper selected rows
		} else if (TypeSelectionEnum.COLUMN.equals(typeSelectionEvent)) {
			// TODO : Some bugs exists in nattable column selection
			// When the bugs are resolved, we needed to be like rows shift selection

			if (this.currentSelection instanceof TableStructuredSelection) {
				// Add the previous selected rows and columns to the current wrapper
				final TableSelectionWrapper existingWrapper = (TableSelectionWrapper) ((TableStructuredSelection) currentSelection).getAdapter(TableSelectionWrapper.class);
				wrapper.copyRowsSelection(existingWrapper);
				wrapper.copyColumnsSelection(existingWrapper);
			}

			for (final int columnPosition : this.selectionLayer.getFullySelectedColumnPositions()) {
				if (!wrapper.getFullySelectedColumns().containsKey(columnPosition)) {
					// Check if the column to select is corresponding to at least one cell
					if (isSelectedCellsContainsColumn(wrapper.getSelectedCells(), columnPosition)) {
						wrapper.addSelectedColumn(columnPosition);
					}
				}

			}
		}
		wrapper.buildSingleCellSelection();
		return calculateSelectionFromWrapper(wrapper);
	}

	/**
	 * This allows to calculate the selection when the control key is pressed with the selection.
	 *
	 * @param wrapper
	 *            The table selection wrapper to fill.
	 * @param typeSelectionEvent
	 *            The type of selection from the event (cell, row or column selection).
	 * @param event
	 *            The event caught.
	 * @return The collection of object selected.
	 */
	protected Collection<Object> calculateSelectionWithControlMask(final TableSelectionWrapper wrapper, final TypeSelectionEnum typeSelectionEvent, final ILayerEvent event) {
		if (this.currentSelection instanceof TableStructuredSelection) {
			// Add the previous selected rows and columns to the current wrapper
			final TableSelectionWrapper existingWrapper = (TableSelectionWrapper) ((TableStructuredSelection) currentSelection).getAdapter(TableSelectionWrapper.class);
			wrapper.copyRowsSelection(existingWrapper);
			wrapper.copyColumnsSelection(existingWrapper);
		}

		// If a cell is selected by the control and was already selected, remove it and remove the selected row and column
		if (TypeSelectionEnum.CELL.equals(typeSelectionEvent)) {
			final int columnPosition = ((CellSelectionEvent) event).getColumnPosition();
			final int rowPosition = ((CellSelectionEvent) event).getRowPosition();
			final PositionCoordinate tmpCoordinate = new PositionCoordinate(selectionLayer, columnPosition, rowPosition);

			// Check if the cell was unselected
			if (!wrapper.getSelectedCells().contains(tmpCoordinate)) {
				if (wrapper.getFullySelectedRows().containsKey(rowPosition)) {
					wrapper.removeSelectedRow(rowPosition);// getFullySelectedRows().remove(rowPosition);
				}
				if (wrapper.getFullySelectedColumns().containsKey(columnPosition)) {
					wrapper.removeSelectedColumn(columnPosition);// wrapper.getFullySelectedColumns().remove(columnPosition);
				}
			}
		} else {
			calculateSelectionRowsAndColumnsWithTypeSelectionEvent(wrapper, typeSelectionEvent, event);
		}

		wrapper.buildSingleCellSelection();
		return calculateSelectionFromWrapper(wrapper);
	}

	/**
	 * This allows to calculate the selection for rows and columns (cells already added to the wrapper).
	 *
	 * @param wrapper
	 *            The table selection wrapper to fill.
	 * @param typeSelectionEvent
	 *            The type of selection from the event (cell, row or column selection).
	 * @param event
	 *            The event caught.
	 * @return The collection of object selected.
	 */
	protected Collection<Object> calculateSelectionRowsAndColumnsWithTypeSelectionEvent(final TableSelectionWrapper wrapper, final TypeSelectionEnum typeSelectionEvent, final ILayerEvent event) {
		// Manage the column selection event
		if (TypeSelectionEnum.COLUMN.equals(typeSelectionEvent)) {
			for (Range range : ((ColumnSelectionEvent) event).getColumnPositionRanges()) {
				for (int columnPosition = range.start; columnPosition < range.end; columnPosition++) {
					if (wrapper.getFullySelectedColumns().containsKey(columnPosition)) {
						// The selected column was only selected, so it need to be removed
						wrapper.removeSelectedColumn(columnPosition);
					} else {
						// Check if the column to select is corresponding to at least one cell
						// why this stupid test
						if (isSelectedCellsContainsColumn(wrapper.getSelectedCells(), columnPosition)) {
							// The selected column was not already in selection, add it
							wrapper.addSelectedColumn(columnPosition);
						}
					}
				}
			}
			// Manage the row selection event
		} else if (TypeSelectionEnum.ROW.equals(typeSelectionEvent)) {
			for (Range range : ((RowSelectionEvent) event).getRowPositionRanges()) {
				for (int rowPosition = range.start; rowPosition < range.end; rowPosition++) {
					if (wrapper.getFullySelectedRows().containsKey(rowPosition)) {
						// The selected row was only selected, so it need to be removed
						wrapper.removeSelectedRow(rowPosition);
					} else {
						// Check if the row to select is corresponding to at least one cell
						if (isSelectedCellsContainsRow(wrapper.getSelectedCells(), rowPosition)) {
							// The selected row was not already in selection, add it
							wrapper.addSelectedRow(rowPosition);
						}
					}
				}
			}
		}
		wrapper.buildSingleCellSelection();
		return calculateSelectionFromWrapper(wrapper);
	}

	/**
	 * This allows to determinate if the row index to add to the rows selected have at least one of its cells in the selected cells.
	 *
	 * @param selectedCells
	 *            The selected cells.
	 * @param rowIndex
	 *            The row index to search.
	 * @return <code>true</code> if the row corresponding to at least one cell, <code>false</code> otherwise.
	 */
	private boolean isSelectedCellsContainsRow(final Collection<PositionCoordinate> selectedCells, final int rowIndex) {
		boolean result = false;

		Iterator<PositionCoordinate> selectedCellsIterator = selectedCells.iterator();
		while (!result && selectedCellsIterator.hasNext()) {
			final PositionCoordinate selectedCell = selectedCellsIterator.next();
			if (selectedCell.getRowPosition() == rowIndex) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * This allows to determinate if the column index to add to the columns selected have at least one of its cells in the selected cells.
	 *
	 * @param selectedCells
	 *            The selected cells.
	 * @param columnIndex
	 *            The column index to search.
	 * @return <code>true</code> if the column corresponding to at least one cell, <code>false</code> otherwise.
	 */
	private boolean isSelectedCellsContainsColumn(final Collection<PositionCoordinate> selectedCells, final int columnIndex) {
		boolean result = false;

		Iterator<PositionCoordinate> selectedCellsIterator = selectedCells.iterator();
		while (!result && selectedCellsIterator.hasNext()) {
			final PositionCoordinate selectedCell = selectedCellsIterator.next();
			if (selectedCell.getColumnPosition() == columnIndex) {
				result = true;
			}
		}

		return result;
	}

	/**
	 * This allows to calculate the selected objects from the wrapper filled.
	 *
	 * @param wrapper
	 *            The wrapper filled.
	 * @return The collection of object selected.
	 */
	protected Collection<Object> calculateSelectionFromWrapper(final TableSelectionWrapper wrapper) {
		return wrapper.getSelectedElements();
	}

	/**
	 * This allows to manage the selection when any type of selection was done (cell, row or column selection from the selection event).
	 *
	 * @param wrapper
	 *            The wrapper to fill.
	 * @param event
	 *            The event caught.
	 * @return The collection of object selected.
	 */
	protected Collection<Object> calculateSelectionRowsAndColumnsWithoutTypeSelectionEvent(final TableSelectionWrapper wrapper, final ILayerEvent event) {
		return wrapper.getSelectedElements();
	}


	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.ui.services.IDisposable#dispose()
	 */
	@Override
	public void dispose() {
		this.isDisposed = true;
		this.manager = null;
		// To be sure, to fix the bug 469376: [Table] Memory Leak : (Tree)NattableWidgetManager, EObjectTreeItemAxis and others objects are not disposed when the table is closed
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=469376
		setSelection(StructuredSelection.EMPTY);

		if (this.selectionLayer != null) {
			this.selectionLayer.removeLayerListener(this.selectionListener);
			this.selectionLayer = null;
		}
		this.listeners.clear();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.nebula.widgets.nattable.layer.ILayerListener#handleLayerEvent(org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent)
	 */
	@Override
	public void handleLayerEvent(final ILayerEvent event) {
		if (!isDisposed && isActive && event instanceof ISelectionEvent) {
			calculateAndStoreNewSelection(event);
		}
	}


	/**
	 * @param activate
	 *            if <code>true</code> we listen selection change event to build a new selection
	 * @since 7.0
	 */
	public void setActive(boolean activate) {
		this.isActive = activate;
		if (activate) {
			// we need to recalculate the selection on reactivation, to get a correction cell selection
			// example for a table with 3 columns, 1 row is selected
			// we add one row
			// we refresh reselecting the row
			// if not, only 3 cells are selected, but now there are 4 cells on the row...
			calculateAndStoreNewSelection(new FakeSelectionEvent(this.selectionLayer));
		}
	}

	/**
	 *
	 * a fake selection event to relaunch a build of the selection
	 *
	 */
	private class FakeSelectionEvent implements ILayerEvent, ISelectionEvent {

		private final SelectionLayer selectionLayer;

		public FakeSelectionEvent(final SelectionLayer layer) {
			this.selectionLayer = layer;
		}

		/**
		 * @see org.eclipse.nebula.widgets.nattable.selection.event.ISelectionEvent#getSelectionLayer()
		 *
		 * @return
		 */
		@Override
		public SelectionLayer getSelectionLayer() {
			return selectionLayer;
		}

		/**
		 * @see org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent#convertToLocal(org.eclipse.nebula.widgets.nattable.layer.ILayer)
		 *
		 * @param localLayer
		 * @return
		 */
		@Override
		public boolean convertToLocal(ILayer localLayer) {
			// unused
			return false;
		}

		/**
		 * @see org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent#cloneEvent()
		 *
		 * @return
		 */
		@Override
		public ILayerEvent cloneEvent() {
			return new FakeSelectionEvent(this.selectionLayer);
		}

	}
}
