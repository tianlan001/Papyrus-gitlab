/*******************************************************************************
 * Copyright (c) 2012, 2013 Original authors and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Original authors and others - initial API and implementation
 *     Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 502559
 ******************************************************************************/
package org.eclipse.papyrus.infra.nattable.dataprovider;



import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.edit.command.DeleteCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.convert.IDisplayConverter;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowDataLayer;
import org.eclipse.nebula.widgets.nattable.filterrow.IFilterStrategy;
import org.eclipse.nebula.widgets.nattable.filterrow.event.FilterAppliedEvent;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.persistence.IPersistable;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.util.ObjectUtils;
import org.eclipse.papyrus.infra.nattable.filter.IFilterValueToMatchManager;
import org.eclipse.papyrus.infra.nattable.filter.configuration.IFilterConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.Style;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;


/**
 * Data provider for the filter row
 * <ul>
 * <li>Stores filter strings</li>
 * <li>Applies them to the ca.odell.glazedlists.matchers.MatcherEditor on the ca.odell.glazedlists.FilterList</li>
 * </ul>
 * 
 * Duplicated and adapted code from nattable
 */
public class FilterRowDataProvider<T> implements IDataProvider /* , IPersistable */ {

	/**
	 * Replacement for the pipe character | that is used for persistence.
	 * If regular expressions are used for filtering, the pipe character can be used
	 * in the regular expression to specify alternations. As the persistence
	 * mechanism in NatTable uses the pipe character for separation of values,
	 * the persistence breaks for such cases.
	 * By replacing the pipe in the regular expression with some silly uncommon
	 * value specified here, we ensure to be able to also persist pipes in the
	 * regular expressions, aswell as being backwards compatible with already
	 * saved filter row states.
	 */
	public static final String PIPE_REPLACEMENT = "°~°"; //$NON-NLS-1$

	/**
	 * The prefix String that will be used to mark that the following filter
	 * value in the persisted state is a collection.
	 */
	public static final String FILTER_COLLECTION_PREFIX = "°coll("; //$NON-NLS-1$

	/**
	 * The {@link IFilterStrategy} to which the set filter value should be applied.
	 */
	private final IFilterStrategy<T> filterStrategy;
	/**
	 * The column header layer where this {@link IDataProvider} is used for filtering.
	 * Needed for retrieval of column indexes and firing according filter events.
	 */
	private final ILayer columnHeaderLayer;
	/**
	 * The {@link IDataProvider} of the column header.
	 * This is necessary to retrieve the real column count of the column header and not a
	 * transformed one. (e.g. hiding a column would change the column count in the column header
	 * but not in the column header {@link IDataProvider}).
	 */
	private final IDataProvider columnHeaderDataProvider;

	// /**
	// * The {@link IConfigRegistry} needed to retrieve the {@link IDisplayConverter} for converting
	// * the values on state save/load operations.
	// */
	// private final IConfigRegistry configRegistry;

	private final INattableModelManager manager;
	/**
	 * Contains the filter objects mapped to the column index.
	 * Basically the data storage for the set filters in the filter row so they are
	 * visible to the user who entered them.
	 */
	private Map<Integer, Object> filterIndexToObjectMap = new HashMap<Integer, Object>();

	private Runnable refreshFilterRunnable;

	/* Flag to avoid reentrant call to refresh */
	private AtomicBoolean isFilterRefreshing = new AtomicBoolean(false);

	// private ResourceSetListener listener;


	/**
	 * 
	 * @param filterStrategy
	 *            The {@link IFilterStrategy} to which the set filter value should be applied.
	 * @param columnHeaderLayer
	 *            The column header layer where this {@link IDataProvider} is used for filtering
	 *            needed for retrieval of column indexes and firing according filter events..
	 * @param columnHeaderDataProvider
	 *            The {@link IDataProvider} of the column header needed to retrieve the real
	 *            column count of the column header and not a transformed one.
	 * @param tableManager
	 *            The {@link IConfigRegistry} needed to retrieve the {@link IDisplayConverter} for
	 *            converting the values on state save/load operations.
	 */
	public FilterRowDataProvider(IFilterStrategy<T> filterStrategy, ILayer columnHeaderLayer, IDataProvider columnHeaderDataProvider, INattableModelManager tableManager) {
		this.filterStrategy = filterStrategy;
		this.columnHeaderLayer = columnHeaderLayer;
		this.columnHeaderDataProvider = columnHeaderDataProvider;
		this.manager = tableManager;
		// TransactionalEditingDomain domain = TableEditingDomainUtils.getTableEditingDomain(this.manager.getTable());
		// listener = new ResourceSetListener() {
		//
		// @Override
		// public Command transactionAboutToCommit(ResourceSetChangeEvent event) throws RollbackException {
		// 
		// return null;
		// }
		//
		// @Override
		// public void resourceSetChanged(ResourceSetChangeEvent event) {
		// 
		// for (Notification current : event.getNotifications()) {
		// if (current.getNotifier() instanceof IAxis) {
		// IAxis axis = (IAxis) current.getNotifier();
		// EObject parent = axis.eContainer();
		// if (current.getOldValue() instanceof NamedStyle) {
		// NamedStyle style = (NamedStyle) current.getOldValue();
		// if (style.getName().equals(IFilterConfiguration.FILTER_VALUE_TO_MATCH)) {
		// int index = manager.getColumnElementsList().indexOf(axis);
		// getDataValue(index, 0);
		// applyFilter();
		// }
		// }
		// if (current.getNewValue() instanceof NamedStyle) {
		// NamedStyle style = (NamedStyle) current.getOldValue();
		// if (style.getName().equals(IFilterConfiguration.FILTER_VALUE_TO_MATCH)) {
		// int index = manager.getColumnElementsList().indexOf(axis);
		// getDataValue(index, 0);
		// applyFilter();
		// }
		// }
		// }
		//
		// }
		//
		// }
		//
		// @Override
		// public boolean isPrecommitOnly() {
		// 
		// return false;
		// }
		//
		// @Override
		// public boolean isPostcommitOnly() {
		// 
		// return false;
		// }
		//
		// @Override
		// public boolean isAggregatePrecommitListener() {
		// 
		// return false;
		// }
		//
		// @Override
		// public NotificationFilter getFilter() {
		// 
		// return null;
		// }
		// };
		// domain.addResourceSetListener(listener);
	}

	/**
	 * Returns the map that contains the filter objects mapped to the column index.
	 * It is the data storage for the inserted filters into the filter row by the user.
	 * <p>
	 * Note: Usually it is not intended to modify this Map directly. You should rather call <code>setDataValue(int, int, Object)</code> or <code>clearAllFilters()</code> to modify this Map to ensure consistency in other framework code. It is made visible
	 * because there might be code that needs to modify the Map without index transformations or firing events.
	 * 
	 * @return Map that contains the filter objects mapped to the column index.
	 */
	public Map<Integer, Object> getFilterIndexToObjectMap() {
		return this.filterIndexToObjectMap;
	}

	/**
	 * Set the map that contains the filter objects mapped to the column index to be the
	 * data storage for the inserted filters into the filter row by the user.
	 * <p>
	 * Note: Usually it is not intended to set this Map from the outside as it is created in the constructor. But there might be use cases where you e.g. need to connect filter rows to each other. In this case it might be useful to override the local Map with
	 * the one form another FilterRowDataProvider. This is not a typical use case, therefore you should use this method carefully!
	 * 
	 * @param filterIndexToObjectMap
	 *            Map that contains the filter objects mapped to the column index.
	 */
	public void setFilterIndexToObjectMap(Map<Integer, Object> filterIndexToObjectMap) {
		this.filterIndexToObjectMap = filterIndexToObjectMap;
	}

	@Override
	public int getColumnCount() {
		return columnHeaderDataProvider.getColumnCount();
	}

	public void updateMapValue(int columnIndex) {
		if (manager != null) {
			NatTable natTable = (NatTable) manager.getAdapter(NatTable.class);
			IConfigRegistry configRegistry = natTable.getConfigRegistry();


			IFilterValueToMatchManager loader = getFilterValueToMatchManager(columnIndex);
			if (loader != null) {
				Object columnObject = manager.getColumnElement(columnIndex);
				Object savedValue = loader.getValueToMatch(configRegistry, columnObject);
				// to manage undo/redo
				Object mapValue = filterIndexToObjectMap.get(columnIndex);
				if (savedValue != mapValue) {
					if ((mapValue != null && !mapValue.equals(savedValue)) || (savedValue != null && !savedValue.equals(mapValue))) {
						if (savedValue == null) {
							filterIndexToObjectMap.remove(columnIndex);
						} else {
							filterIndexToObjectMap.put(columnIndex, savedValue);
						}
						applyFilter();
					}
				}
			}
		}
	}

	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		// NatTable natTable = (NatTable) manager.getAdapter(NatTable.class);
		// IConfigRegistry configRegistry = natTable.getConfigRegistry();
		//
		// if (manager != null) {
		// IFilterValueToMatchManager loader = getFilterValueToMatchManager(columnIndex);
		// if (loader != null) {
		// Object columnObject = manager.getColumnElement(columnIndex);
		// Object savedValue = loader.getValueToMatch(configRegistry, columnObject);
		// // to manage undo/redo
		// Object mapValue = filterIndexToObjectMap.get(columnIndex);
		// if (savedValue != mapValue) {
		// if ((mapValue != null && !mapValue.equals(savedValue)) || (savedValue != null && !savedValue.equals(mapValue))) {
		// if (savedValue == null) {
		// filterIndexToObjectMap.remove(columnIndex);
		// } else {
		// filterIndexToObjectMap.put(columnIndex, savedValue);
		// }
		// applyFilter();
		//
		// }
		// }
		// }
		// }

		return filterIndexToObjectMap.get(columnIndex);
	}

	/**
	 * refresh the view.
	 */
	protected void refreshFilterInUIThread() {
		NatTable natTable = getNatTable();
		// Need to refresh, even if (temporarily) invisible
		// (Better alternative?: store refresh event and execute once visible again)
		if (natTable != null && natTable.isDisposed()) {
			return;
		}

		// avoid reentrant call
		// Refresh only of we are not already refreshing.
		if (isFilterRefreshing.compareAndSet(false, true)) {
			filterStrategy.applyFilter(filterIndexToObjectMap);
			columnHeaderLayer.fireLayerEvent(new FilterAppliedEvent(columnHeaderLayer));
			isFilterRefreshing.set(false);
		}
	}

	/**
	 *
	 * @return
	 * 		a new runnable for the refreash action
	 */
	private Runnable createRefreshFilterRunnable() {
		return new Runnable() {

			@Override
			public void run() {
				// Only run if I'm still pending
				synchronized (this) {
					if (refreshFilterRunnable != this) {
						return;
					}

					refreshFilterRunnable = null;
				}
				refreshFilterInUIThread();
				((NattableModelManager) manager).refreshNatTable();
			}
		};
	}

	private NatTable getNatTable() {
		return (NatTable) manager.getAdapter(NatTable.class);
	}

	public void applyFilter() {
		// This refresh code has been duplicated from the refresh of the ModelExplorer (class ModelExplorerView)
		final Runnable schedule;
		NatTable natTable = getNatTable();
		if (natTable != null && !natTable.isDisposed()) {
			synchronized (this) {
				if (refreshFilterRunnable == null) {
					// No refresh is yet pending. Schedule one
					schedule = createRefreshFilterRunnable();
					refreshFilterRunnable = schedule;
				} else {
					schedule = null;
				}
			}

			if (schedule != null) {
				Control control = natTable;
				Display display = ((control == null) || control.isDisposed()) ? null : control.getDisplay();

				if (display != null) {
					// Don't need to schedule a refresh if we have no control or it's disposed
					display.asyncExec(schedule);
				}
			}
		}
	}



	@Override
	public int getRowCount() {
		return 1;
	}

	@Override
	public void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		columnIndex = columnHeaderLayer.getColumnIndexByPosition(columnIndex);
		// INattableModelManager manager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		if (manager != null) {
			NatTable natTable = (NatTable) manager.getAdapter(NatTable.class);
			IConfigRegistry configRegistry = natTable.getConfigRegistry();

			IFilterValueToMatchManager loader = getFilterValueToMatchManager(columnIndex);
			if (loader != null) {
				Object columnObject = manager.getColumnElement(columnIndex);
				loader.saveValueToMatch(configRegistry, columnObject, newValue);
			}
		}
		if (ObjectUtils.isNotNull(newValue)) {
			filterIndexToObjectMap.put(columnIndex, newValue);
		} else {
			filterIndexToObjectMap.remove(columnIndex);
		}

		filterStrategy.applyFilter(filterIndexToObjectMap);

		columnHeaderLayer.fireLayerEvent(new FilterAppliedEvent(columnHeaderLayer));
	}

	/**
	 * 
	 * @param columnIndex
	 *            the index of the column
	 * @return
	 * 		the filter value to match manager to use
	 */
	private IFilterValueToMatchManager getFilterValueToMatchManager(int columnIndex) {
		StringBuilder builder = new StringBuilder();
		builder.append(FilterRowDataLayer.FILTER_ROW_COLUMN_LABEL_PREFIX);
		builder.append(Integer.valueOf(columnIndex));
		return getConfigRegistry().getConfigAttribute(NattableConfigAttributes.FILTER_VALUE_TO_MATCH_MANAGER, DisplayMode.NORMAL, builder.toString());
	}

	/**
	 * 
	 * @return
	 * 		the config registry to used by the nattable widget
	 */
	private IConfigRegistry getConfigRegistry() {
		NatTable natTable = (NatTable) this.manager.getAdapter(NatTable.class);
		return natTable.getConfigRegistry();
	}

	// Load/save state
	//
	// @Override
	// public void saveState(String prefix, Properties properties) {
	// Map<Integer, String> filterTextByIndex = new HashMap<Integer, String>();
	// for(Integer columnIndex : filterIndexToObjectMap.keySet()) {
	// final IDisplayConverter converter = configRegistry.getConfigAttribute(
	// CellConfigAttributes.DISPLAY_CONVERTER,
	// DisplayMode.NORMAL,
	// FilterRowDataLayer.FILTER_ROW_COLUMN_LABEL_PREFIX + columnIndex);
	//
	// String filterText = getFilterStringRepresentation(filterIndexToObjectMap.get(columnIndex), converter);
	// filterText = filterText.replace("|", PIPE_REPLACEMENT); //$NON-NLS-1$
	// filterTextByIndex.put(columnIndex, filterText);
	// }
	//
	// String string = PersistenceUtils.mapAsString(filterTextByIndex);
	//
	// if (!isEmpty(string)) {
	// properties.put(prefix + FilterRowDataLayer.PERSISTENCE_KEY_FILTER_ROW_TOKENS, string);
	// }
	// }

	// @Override
	// public void loadState(String prefix, Properties properties) {
	// filterIndexToObjectMap.clear();
	//
	// try {
	// Object property = properties.get(prefix + FilterRowDataLayer.PERSISTENCE_KEY_FILTER_ROW_TOKENS);
	// Map<Integer, String> filterTextByIndex = PersistenceUtils.parseString(property);
	// for (Integer columnIndex : filterTextByIndex.keySet()) {
	// final IDisplayConverter converter = configRegistry.getConfigAttribute(
	// CellConfigAttributes.DISPLAY_CONVERTER,
	// DisplayMode.NORMAL,
	// FilterRowDataLayer.FILTER_ROW_COLUMN_LABEL_PREFIX + columnIndex);
	//
	// String filterText = filterTextByIndex.get(columnIndex);
	// filterText = filterText.replace(PIPE_REPLACEMENT, "|"); //$NON-NLS-1$
	// filterIndexToObjectMap.put(columnIndex, getFilterFromString(filterText, converter));
	// }
	// } catch (Exception e) {
	// log.error("Error while restoring filter row text!", e); //$NON-NLS-1$
	// }

	// filterStrategy.applyFilter(filterIndexToObjectMap);
	// }

	/**
	 * This method is used to support saving of a filter collection, e.g. in the context of the
	 * Excel like filter row. In such cases the filter value is not a simple String but a
	 * Collection of filter values that need to be converted to a String representation.
	 * As the state persistence is encapsulated to be handled here, we need to take care
	 * of such states here also.
	 * 
	 * @param filterValue
	 *            The filter value object that is used for filtering.
	 * @param converter
	 *            The converter that is used to convert the filter value, which is necessary
	 *            to support filtering of custom types.
	 * @return The String representation of the filter value.
	 */
	private String getFilterStringRepresentation(Object filterValue, IDisplayConverter converter) {
		// in case the filter value is a collection of values, we need to create a special
		// string representation
		if (filterValue instanceof Collection) {
			String collectionSpec = FILTER_COLLECTION_PREFIX + filterValue.getClass().getName() + ")"; //$NON-NLS-1$
			StringBuilder builder = new StringBuilder(collectionSpec);
			builder.append("["); //$NON-NLS-1$
			Collection<?> filterCollection = (Collection<?>) filterValue;
			for (Iterator<?> iterator = filterCollection.iterator(); iterator.hasNext();) {
				Object filterObject = iterator.next();
				builder.append(converter.canonicalToDisplayValue(filterObject));
				if (iterator.hasNext())
					builder.append(IPersistable.VALUE_SEPARATOR);
			}

			builder.append("]"); //$NON-NLS-1$
			return builder.toString();
		}
		return (String) converter.canonicalToDisplayValue(filterValue);
	}

	/**
	 * This method is used to support loading of a filter collection, e.g. in the context of the
	 * Excel like filter row. In such cases the saved filter value is not a simple String but
	 * represents a Collection of filter values that need to be converted to the corresponding values.
	 * As the state persistence is encapsulated to be handled here, we need to take care
	 * of such states here also.
	 * 
	 * @param filterText
	 *            The String representation of the applied saved filter.
	 * @param converter
	 *            The converter that is used to convert the filter value, which is necessary
	 *            to support filtering of custom types.
	 * @return The filter value that will be used to apply a filter to the IFilterStrategy
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object getFilterFromString(String filterText, IDisplayConverter converter) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		if (filterText.startsWith(FILTER_COLLECTION_PREFIX)) {
			// the filter text represents a collection
			int indexEndCollSpec = filterText.indexOf(")"); //$NON-NLS-1$
			String collectionSpec = filterText.substring(filterText.indexOf("(") + 1, indexEndCollSpec); //$NON-NLS-1$
			Collection filterCollection = (Collection) Class.forName(collectionSpec).newInstance();

			// also get rid of the collection marks
			filterText = filterText.substring(indexEndCollSpec + 2, filterText.length() - 1);
			String[] filterSplit = filterText.split(IPersistable.VALUE_SEPARATOR);
			for (String filterString : filterSplit) {
				filterCollection.add(converter.displayToCanonicalValue(filterString));
			}

			return filterCollection;
		}
		return converter.displayToCanonicalValue(filterText);
	}

	/**
	 * Clear all filters that are currently applied.
	 */
	public void clearAllFilters() {
		filterIndexToObjectMap.clear();
		filterStrategy.applyFilter(filterIndexToObjectMap);

		// we clear the filter
		if (manager != null) {
			CompoundCommand cc = new CompoundCommand("Clear All Filter"); //$NON-NLS-1$
			TransactionalEditingDomain domain = TableEditingDomainUtils.getTableEditingDomain(manager.getTable());
			List<?> columnElement = manager.getColumnElementsList();
			for (Object current : columnElement) {
				if (current instanceof IAxis) {
					Style idToDestroy = ((IAxis) current).getNamedStyle(NattablestylePackage.eINSTANCE.getStringValueStyle(), IFilterConfiguration.FILTER_SYSTEM_ID);
					Style keyToDestroy = ((IAxis) current).getNamedStyle(NattablestylePackage.eINSTANCE.getStyle(), IFilterConfiguration.FILTER_FORCED_BY_USER_ID);

					if (idToDestroy != null) {
						Command cmd = DeleteCommand.create(domain, idToDestroy);
						if (cmd != null && cmd.canExecute()) {
							cc.append(cmd);
						}
					}
					if (idToDestroy != null) {
						Command cmd = DeleteCommand.create(domain, keyToDestroy);
						if (cmd != null && cmd.canExecute()) {
							cc.append(cmd);
						}
					}
				}
			}
			if (!cc.isEmpty() && cc.canExecute()) {
				domain.getCommandStack().execute(cc);
			}
		}

	}
}
