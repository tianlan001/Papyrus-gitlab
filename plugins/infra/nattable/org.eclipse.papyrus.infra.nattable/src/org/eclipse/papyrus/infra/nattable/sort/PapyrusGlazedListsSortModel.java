/*******************************************************************************
 * Copyright (c) 2012, 2013, 2020 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Original authors and others - initial API and implementation
 *     Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 562646, 562619
 ******************************************************************************/
package org.eclipse.papyrus.infra.nattable.sort;

import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyResolver;
//import org.eclipse.nebula.widgets.nattable.extension.glazedlists.NatColumnTableFormat;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;
import org.eclipse.nebula.widgets.nattable.layer.event.StructuralRefreshEvent;
import org.eclipse.nebula.widgets.nattable.sort.SortDirectionEnum;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.nattable.glazedlists.PapyrusSortingState;
import org.eclipse.papyrus.infra.nattable.manager.refresh.CustomStructuralRefreshEvent;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.sort.copy.NatTableComparatorChooser;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;

import ca.odell.glazedlists.SortedList;


/**
 *
 * @author Vincent Lorenzo
 *         This class provides the sort model for an axis
 */
public class PapyrusGlazedListsSortModel extends AbstractGlazedListSortModel {

	/**
	 * the column accessor
	 */
	protected IColumnAccessor<Object> columnAccessor;

	/**
	 * the sorted list
	 */
	protected SortedList<Object> sortedList;

	private NatTableComparatorChooser<Object> comparatorChooser;

	// TODO : useful ?
	protected IColumnPropertyResolver columnPropertyResolver;

	protected ILayer columnHeaderDataLayer;

	protected PapyrusNatColumnTableFormat<Object> f;

	/**
	 * Constructor.
	 *
	 * @param manager
	 * @param sortedList
	 * @param columnAccessor
	 */
	public PapyrusGlazedListsSortModel(INattableModelManager manager, SortedList<Object> sortedList, IColumnAccessor<Object> columnAccessor) {
		super(manager);
		this.columnAccessor = columnAccessor;
		this.sortedList = sortedList;
	}




	protected NatTableComparatorChooser<Object> getComparatorChooser() {

		if (comparatorChooser == null) {
			f = new PapyrusNatColumnTableFormat<>(this.columnAccessor, getTableManager(), this.columnHeaderDataLayer);
			comparatorChooser = new PapyrusNatTableComparatorChooser(sortedList, f) {

				/**
				 * @see ca.odell.glazedlists.gui.AbstractTableComparatorChooser#createSortingState()
				 *
				 * @return
				 */
				@Override
				protected org.eclipse.papyrus.infra.nattable.glazedlists.copy.SortingState createSortingState() {
					return new PapyrusSortingState(this, getTableManager());
				}

			};
		}

		return comparatorChooser;
	}

	protected IConfigRegistry getConfigRegistry() {
		NatTable nat = getTableManager().getAdapter(NatTable.class);
		return nat.getConfigRegistry();
	}

	@Override
	public List<Integer> getSortedColumnIndexes() {
		return getComparatorChooser().getSortingColumns();
	}

	@Override
	public int getSortOrder(int columnIndex) {
		return getComparatorChooser().getClickSequence(columnIndex);
	}

	@Override
	public SortDirectionEnum getSortDirection(int columnIndex) {
		return getComparatorChooser().getSortDirectionForColumnIndex(columnIndex);
	}

	@Override
	public boolean isColumnIndexSorted(int columnIndex) {
		return getComparatorChooser().isColumnIndexSorted(columnIndex);
	}

	@Override
	public List<Comparator> getComparatorsForColumnIndex(int columnIndex) {
		return getComparatorChooser().getComparatorsForColumn(columnIndex);
	}

	@Override
	public void sort(int columnIndex, final SortDirectionEnum sortDirection, boolean accumulate) {
		TransactionalEditingDomain d = TableEditingDomainUtils.getTableEditingDomain(getTableManager().getTable());
		if (d == null) {
			d = TableEditingDomainUtils.getTableContextEditingDomain(getTableManager().getTable());
		}
		// 1. create the update command
		final ICommand cmd = new SaveSortedColumnCommand(d, getTableManager(), this.comparatorChooser, getSortedColumnIndexes());

		// 2. do the sort
		getComparatorChooser().sort(columnIndex, sortDirection, accumulate);

		// 3. execute the update command
		/*
		 * the sort generate a GlazedLink event -> so we will ask for a nattable refresh
		 * 1. executing the command in the stack will generate a stack event so we will ask for a nattable refresh
		 *
		 * -> 2 refresh wanted! but thank to the method org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager.refreshNatTable()
		 * only one be done for big model and two observed for small models (probably because the refresh is faster)
		 *
		 *
		 * 2. executing this command with GMFUnsafe generates only 1 refresh, but we don't get he undo...
		 * in addition, it seems the refresh comes quite late, so the user think the action is finished and the UI get a new freeze...
		 * -> worst user experience I think
		 * I get these result with a GenericTreeTable on a model with 3400 classes as child of the root package
		 *
		 * time is equivalent on my computer (35 secondes from 3400 rows (too long of course)
		 *
		 */
		d.getCommandStack().execute(new GMFtoEMFCommandWrapper(cmd));
		// worst user experience I think
		// try {
		// GMFUnsafe.write(d, cmd);
		// } catch (ExecutionException e) {
		// org.eclipse.papyrus.infra.nattable.Activator.log.error(e);
		// } catch (InterruptedException e) {
		// org.eclipse.papyrus.infra.nattable.Activator.log.error(e);
		// } catch (RollbackException e) {
		// org.eclipse.papyrus.infra.nattable.Activator.log.error(e);
		// }

	}

	@Override
	public void clear() {
		getComparatorChooser().clearComparator();
	}

	@Override
	public void handleLayerEvent(ILayerEvent event) {
		if (event instanceof CustomStructuralRefreshEvent
				&& ((CustomStructuralRefreshEvent) event).isSortModelAlreadyNotified()) {
			return; // already managed, nothing to do
		}

		if (event instanceof StructuralRefreshEvent && ((StructuralRefreshEvent) event).isHorizontalStructureChanged()) {
			// better rendering (less blinking) keeping this check
			String test = getComparatorChooser().toString();
			if (test.contains("-")) { //$NON-NLS-1$
				// avoid exception moving column where a filter is applied
				// the - sign is for negative column index...
				return;
			}

			this.comparatorChooser = null;


			// required after a table reload
			test = ColumnSortUtils.buildComparatorValues(getTableManager());
			getComparatorChooser().fromString(test);
		}

		if (event instanceof CustomStructuralRefreshEvent) {
			((CustomStructuralRefreshEvent) event).setSortModelAlreadyNotified();
		}
	}





	/**
	 * @param compositeLayer
	 */
	public void setColumnHeaderLayer(final ILayer compositeLayer) {
		if (this.columnHeaderDataLayer != null) {
			this.columnHeaderDataLayer.removeLayerListener(this);
		}
		this.columnHeaderDataLayer = compositeLayer;
		this.columnHeaderDataLayer.addLayerListener(this);

		// create/recreate a comparator chooser
		// this.comparatorChooser = null;
		final String encodedSort = ColumnSortUtils.buildComparatorValues(getTableManager());

		// to get sort just after a table creation with sorted columns
		getComparatorChooser().fromString(encodedSort);
		if (this.comparatorChooser instanceof PapyrusNatTableComparatorChooser) {
			((PapyrusNatTableComparatorChooser) this.comparatorChooser).rebuildComparator();
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.nattable.sort.IPapyrusSortModel#updateSort()
	 *
	 */
	// @Override
	public void updateSort() {


	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.sort.ISortModel#getColumnComparator(int)
	 *
	 * @param columnIndex
	 * @return
	 */
	@Override
	public Comparator<?> getColumnComparator(int columnIndex) {

		return null;
	}
}
