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
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@acea.fr - Bug 502559
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.filter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnAccessor;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowDataLayer;
import org.eclipse.nebula.widgets.nattable.filterrow.IFilterStrategy;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.filter.RegexFindEditor.RegexFindMatcher;
import org.eclipse.papyrus.infra.nattable.filter.StringMatcherEditorFactory.PapyrusTextMatcherEditor;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.services.IDisposable;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.matchers.CompositeMatcherEditor;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.util.concurrent.ReadWriteLock;



/**
 * the filter strategy used by Papyrus table. It allows to manage invert axis
 * 
 * NOT IN THE API
 *
 */
public class PapyrusFilterStrategy implements IFilterStrategy<Object>, IDisposable {

	/**
	 * the nattable model manager
	 */
	private NattableModelManager manager;

	/**
	 * the column accessor
	 */
	protected final IColumnAccessor<Object> columnAccessor;

	/**
	 * the row matcher editor
	 */
	protected CompositeMatcherEditor<Object> rowMatcherEditor;

	/**
	 * column matcher edito
	 */
	protected CompositeMatcherEditor<Object> columnMatcherEditor;

	/**
	 * the row filter list
	 */
	private FilterList<Object> rowFilterList;

	/**
	 * the column filter list
	 */
	private FilterList<Object> columnFilterList;

	/**
	 * the table
	 */
	private Table table;

	/**
	 * listener used to know when the user invert the table
	 */
	private Adapter invertAxisAdapter;

	/**
	 * 
	 * @return
	 * 		the table manager
	 * 
	 */
	private INattableModelManager getTableManager() {
		return this.manager;
	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param manager
	 *            the table manager
	 * @param columnAccessor
	 *            a column accessor
	 */
	public PapyrusFilterStrategy(NattableModelManager manager, final IColumnAccessor<Object> columnAccessor) {

		this.columnAccessor = columnAccessor;
		this.manager = manager;

		this.table = this.manager.getTable();
		if (this.table.isInvertAxis()) {
			this.rowFilterList = manager.getVerticalFilterList();
			this.columnFilterList = manager.getHorizontalFilterEventList();
		} else {
			this.rowFilterList = manager.getHorizontalFilterEventList();
			this.columnFilterList = manager.getVerticalFilterList();
		}
		// this.currentFilterList = this.manager.get
		this.invertAxisAdapter = new AdapterImpl() {

			/**
			 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 *
			 * @param msg
			 */
			@Override
			public void notifyChanged(Notification msg) {
				Object notifier = msg.getNotifier();
				if (notifier == getTableManager().getTable()) {
					if (msg.getFeature() == NattablePackage.eINSTANCE.getTable_InvertAxis()) {
						Display.getDefault().asyncExec(new Runnable() {

							@Override
							public void run() {
								resetFilter();
							}
						});

					}
				}
			}
		};
		table.eAdapters().add(invertAxisAdapter);

		this.rowMatcherEditor = new CompositeMatcherEditor<Object>();
		this.rowMatcherEditor.setMode(CompositeMatcherEditor.AND);
		this.columnMatcherEditor = new CompositeMatcherEditor<Object>();
		this.columnMatcherEditor.setMode(CompositeMatcherEditor.AND);

		this.columnFilterList.setMatcherEditor(this.columnMatcherEditor);
		this.rowFilterList.setMatcherEditor(this.rowMatcherEditor);
		resetFilter();
	}

	/**
	 * reset the row and the column filters
	 */
	public void resetFilter() {
		this.rowMatcherEditor.getMatcherEditors().clear();
		this.columnMatcherEditor.getMatcherEditors().clear();
	}


	private ReadWriteLock getReadWriteLockToUse() {
		if (this.table.isInvertAxis()) {
			return this.columnFilterList.getReadWriteLock();
		}
		return this.rowFilterList.getReadWriteLock();
	}

	private CompositeMatcherEditor<Object> getMatcherEditorToUse() {
		if (this.table.isInvertAxis()) {
			return this.columnMatcherEditor;
		}
		return this.rowMatcherEditor;
	}

	/**
	 * Create GlazedLists matcher editors and apply them to facilitate filtering.
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void applyFilter(final Map<Integer, Object> filterIndexToObjectMap) {
		// find the lock and the matcher to use, it depends on the table invert axis state
		ReadWriteLock filterLock = getReadWriteLockToUse();
		CompositeMatcherEditor<Object> matcherEditor = getMatcherEditorToUse();
		Map<Integer, Object> copyOfFilterIndexToObjectMap = new HashMap<Integer, Object>(filterIndexToObjectMap);
		// wait until all listeners had the chance to handle the clear event
		try {
			filterLock.writeLock().lock();
			clearMarcherEditors(matcherEditor, copyOfFilterIndexToObjectMap);
		} finally {
			filterLock.writeLock().unlock();
		}

		if (copyOfFilterIndexToObjectMap.isEmpty()) {
			return;
		}

		try {
			EventList<MatcherEditor<Object>> matcherEditors = new BasicEventList<MatcherEditor<Object>>();
			NatTable natTable = (NatTable) this.manager.getAdapter(NatTable.class);
			IConfigRegistry configRegistry = natTable.getConfigRegistry();
			for (Entry<Integer, Object> mapEntry : copyOfFilterIndexToObjectMap.entrySet()) {
				final Integer columnIndex = mapEntry.getKey();
				final Object value = mapEntry.getValue();
				StringBuilder configLabel = new StringBuilder(FilterRowDataLayer.FILTER_ROW_COLUMN_LABEL_PREFIX);
				configLabel.append(columnIndex.toString());


				IPapyrusMatcherEditorFactory matcherCreator = configRegistry.getConfigAttribute(NattableConfigAttributes.MATCHER_EDITOR_FACTORY, DisplayMode.NORMAL, configLabel.toString());
				if (matcherCreator != null) {
					if (value instanceof Collection<?>) {
						Collection<?> coll = (Collection<?>) value;
						if (!coll.isEmpty()) {
							Iterator<?> iter = coll.iterator();
							CompositeMatcherEditor<Object> composite = new CompositeMatcherEditor<Object>();
							composite.setMode(CompositeMatcherEditor.OR);
							while (iter.hasNext()) {
								Object next = iter.next();
								composite.getMatcherEditors().addAll(matcherCreator.instantiateMatcherEditors(columnAccessor, columnIndex, next, configRegistry));
							}
							matcherEditors.add(composite);
						}
					} else {
						matcherEditors.addAll(matcherCreator.instantiateMatcherEditors(columnAccessor, columnIndex, value, configRegistry));
					}
				} else {
					Activator.log.warn(NLS.bind("No matcher editor found for column {0}, we will use the default string matcher", columnIndex)); //$NON-NLS-1$
					matcherEditors.addAll(new StringMatcherEditorFactory<Object>().instantiateMatcherEditors(columnAccessor, columnIndex, value, configRegistry));
				}
			}

			// wait until all listeners had the chance to handle the clear event
			if (!matcherEditors.isEmpty()) {
				try {
					filterLock.writeLock().lock();
					matcherEditor.setMode(CompositeMatcherEditor.AND);
					matcherEditor.getMatcherEditors().addAll(matcherEditors);
				} finally {
					filterLock.writeLock().unlock();
				}
			}

		} catch (Exception e) {
			Activator.log.error("Error on applying a filter", e); //$NON-NLS-1$
		}
	}

	/**
	 * This allows to clear the needed editor which are not compliant with the map of filters by columns.
	 * 
	 * @param rootMatcherEditor
	 *            The existing matcher editors.
	 * @param filterIndexToObjectMap
	 *            The map of filters by columns.
	 * @since 3.0
	 */
	protected void clearMarcherEditors(final CompositeMatcherEditor<Object> rootMatcherEditor, final Map<Integer, Object> filterIndexToObjectMap) {
		final Iterator<MatcherEditor<Object>> subMatchersEditor = rootMatcherEditor.getMatcherEditors().iterator();
		while (subMatchersEditor.hasNext()) {
			final MatcherEditor<Object> subMatcherEditor = subMatchersEditor.next();

			// If this is a composite matcher editor, check its content
			if (subMatcherEditor instanceof CompositeMatcherEditor) {
				if (!isCompositeSubMatcherExist((CompositeMatcherEditor<Object>) subMatcherEditor, filterIndexToObjectMap)) {
					// If the composite is not corresponding with the filter map, remove it
					subMatchersEditor.remove();
				}

				// If this is a papyrus matcher, check its column and its object to match
			} else if (subMatcherEditor.getMatcher() instanceof AbstractSinglePapyrusMatcher) {
				int columnIndex = ((AbstractSinglePapyrusMatcher<Object>) subMatcherEditor.getMatcher()).getColumnIndex();

				// The column does not exist anymore in the filter map, remove the matcher
				if (!filterIndexToObjectMap.containsKey(columnIndex)) {
					subMatchersEditor.remove();
				} else {
					// The column exist, check the object to match
					final Object objectToMatch = ((AbstractSinglePapyrusMatcher<Object>) subMatcherEditor.getMatcher()).getObjectToMatch();
					final Object value = filterIndexToObjectMap.get(columnIndex);

					// If the object to match is equals, remove the matcher, else remove the filter from the filters map
					if (!objectToMatch.equals(value)) {
						subMatchersEditor.remove();
					} else {
						filterIndexToObjectMap.remove(columnIndex);
					}
				}

				// In other case, remove the filter, it must be recalculated
			} else {
				subMatchersEditor.remove();
			}

			// If the composite does not contain any matcher editor, remove it
			if (((CompositeMatcherEditor<Object>) subMatcherEditor).getMatcherEditors().isEmpty()) {
				subMatchersEditor.remove();
			}
		}
	}

	/**
	 * Check if the composite matcher editor exist in the filters map.
	 * 
	 * @param subCompositeMatcher
	 *            The composite matcher editor.
	 * @param filterIndexToObjectMap
	 *            The filters map.
	 * @return <code>true</code> if the composite matcher editor already exist and is the same than in the filters map, <code>false</code> otherwise.
	 * @since 3.0
	 */
	@SuppressWarnings("rawtypes")
	protected boolean isCompositeSubMatcherExist(final CompositeMatcherEditor<Object> subCompositeMatcher, final Map<Integer, Object> filterIndexToObjectMap) {
		boolean result = false;
		int columnIndex = -1;
		List<Object> objectsToMatch = new ArrayList<Object>();
		String matchingMode = ""; ////$NON-NLS-1$

		// Loop on matcher editors
		final Iterator<MatcherEditor<Object>> matchersEditor = subCompositeMatcher.getMatcherEditors().iterator();
		while (matchersEditor.hasNext()) {
			final MatcherEditor<Object> matcherEditor = matchersEditor.next();

			// Check if this is a papyrus matcher
			if (matcherEditor.getMatcher() instanceof AbstractSinglePapyrusMatcher) {
				// Get the first column index, the following must be the same
				if (-1 == columnIndex) {
					columnIndex = ((AbstractSinglePapyrusMatcher<Object>) matcherEditor.getMatcher()).getColumnIndex();
				}
				objectsToMatch.add(((AbstractSinglePapyrusMatcher<Object>) matcherEditor.getMatcher()).getObjectToMatch());

				// If this is a RegexFindMatcher, the object to match must contains 'regex:' more than objects to match
				if (matcherEditor.getMatcher() instanceof RegexFindMatcher) {
					matchingMode = PapyrusTextMatchingMode.REGEX_FIND.getMode();
				}
			}

			if (matcherEditor instanceof PapyrusTextMatcherEditor) {
				if (-1 == columnIndex) {
					columnIndex = ((PapyrusTextMatcherEditor) matcherEditor).getColumnIndex();
				}
				objectsToMatch.add(((PapyrusTextMatcherEditor) matcherEditor).getObjectToMatch());

				matchingMode = ((PapyrusTextMatcherEditor) matcherEditor).getPapyrusMode();
			}

			if (matcherEditor instanceof PapyrusThresholdMatcherEditor) {
				if (-1 == columnIndex) {
					columnIndex = ((PapyrusThresholdMatcherEditor) matcherEditor).getColumnIndex();
				}
				objectsToMatch.add(((PapyrusThresholdMatcherEditor) matcherEditor).getObjectToMatch());
			}
		}

		// Check if the column if existing
		if (-1 != columnIndex && filterIndexToObjectMap.containsKey(columnIndex)) {
			final Object value = filterIndexToObjectMap.get(columnIndex);

			// Check if the value in filters map is corresponding to the matcher editors.
			if ((value instanceof Collection && ((Collection<?>) value).containsAll(objectsToMatch) && objectsToMatch.containsAll((Collection<?>) value))
					|| (1 == objectsToMatch.size() && (value.equals(matchingMode + objectsToMatch.get(0)) || value.equals(objectsToMatch.get(0))))) {
				// In this case, remove the filter from the filters map.
				filterIndexToObjectMap.remove(columnIndex);
				result = true;
			}
		}

		return result;
	}

	protected FilterList<?> getRowFilterList() {
		return ((NattableModelManager) this.manager).getHorizontalFilterEventList();
	}

	protected ReadWriteLock getHorizontalFilterLock() {
		return getRowFilterList().getReadWriteLock();
	}

	/**
	 * @see org.eclipse.ui.services.IDisposable#dispose()
	 *
	 */
	@Override
	public void dispose() {
		this.table.eAdapters().remove(this.invertAxisAdapter);
		this.table = null;
		this.manager = null;
	}


}
