/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.editor.welcome.nattable.sorting;

import java.text.Collator;
import java.util.Comparator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.GlazedListsSortModel;
import org.eclipse.nebula.widgets.nattable.extension.glazedlists.filterrow.DefaultGlazedListsStaticFilterStrategy;
import org.eclipse.nebula.widgets.nattable.filterrow.FilterRowHeaderComposite;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.cell.ColumnOverrideLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.layer.event.RowStructuralRefreshEvent;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.sort.ISortModel;
import org.eclipse.nebula.widgets.nattable.sort.SortConfigAttributes;
import org.eclipse.nebula.widgets.nattable.sort.SortHeaderLayer;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;
import org.eclipse.nebula.widgets.nattable.sort.painter.SortableHeaderTextPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.IStyle;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.papyrus.infra.editor.welcome.nattable.ServiceConfigAttributes;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.swt.widgets.Text;

import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.Matchers;

/**
 * A helper utility for the configuration of sorting a NatTable that renders a
 * SWT Forms-compatible flat presentation.
 */
public class ColumnHeaderHelper<T> {
	private final IConfigRegistry configRegistry;

	private final ILayer viewportLayer;
	private final SelectionLayer selectionLayer;

	private IColumnPropertyAccessor<T> columnAccessor;
	private IDataProvider headerProvider;
	private DataLayer headerData;
	private SortHeaderLayer<T> sortHeaders;
	private SortedList<T> sortedData;

	private FilterMatcherEditor filterMatcher;

	/**
	 * Initializes me.
	 *
	 * @param configRegistry
	 *            the NatTable configuration registry
	 * @param viewportLayer
	 *            the viewport layer to track for positioning of header cells
	 * @param selectionLayer
	 *            the selection layer in the table body
	 */
	public ColumnHeaderHelper(IConfigRegistry configRegistry, ILayer viewportLayer, SelectionLayer selectionLayer) {
		super();

		this.configRegistry = configRegistry;
		this.viewportLayer = viewportLayer;
		this.selectionLayer = selectionLayer;
	}

	/**
	 * Creates the header layer. <b>Note</b> that this must be done exactly once, as the returned
	 * layer and other parts of it are retained for follow-up work.
	 * 
	 * @param data
	 *            the sorted Glazed-list backing store of the table
	 * @param columnAccessor
	 *            the column accessor describing the columns to be presented
	 * @param columnHeadings
	 *            the localized heading titles corresponding to each column
	 * 
	 * @return the column header layer
	 */
	public ILayer createHeaderLayer(SortedList<T> data, IColumnPropertyAccessor<T> columnAccessor, String... columnHeadings) {
		this.columnAccessor = columnAccessor;
		this.sortedData = data;

		headerProvider = new DefaultColumnHeaderDataProvider(columnHeadings);
		headerData = new DataLayer(headerProvider);
		ColumnHeaderLayer headers = new ColumnHeaderLayer(headerData, viewportLayer, selectionLayer);
		ISortModel sortModel = new GlazedListsSortModel<>(sortedData, columnAccessor, configRegistry, headerData);
		sortHeaders = new SortHeaderLayer<>(headers, sortModel, false);

		return sortHeaders;
	}

	public FilterRowHeaderComposite<T> createFilterLayer(FilterList<T> filteredData) {
		DefaultGlazedListsStaticFilterStrategy<T> filterStrategy = new DefaultGlazedListsStaticFilterStrategy<>(filteredData, columnAccessor, configRegistry);
		filterMatcher = new FilterMatcherEditor();
		filterStrategy.addStaticFilter(filterMatcher);
		FilterRowHeaderComposite<T> result = new FilterRowHeaderComposite<T>(filterStrategy, sortHeaders, headerProvider, configRegistry);
		result.setFilterRowVisible(false);
		return result;
	}

	/**
	 * Configures the header layer {@linkplain #createHeaderLayer previously created} for sorting
	 * control behavior.
	 * 
	 * @see #createHeaderLayer(SortedList, IColumnPropertyAccessor, String...)
	 */
	public void configureSorting() {
		ColumnOverrideLabelAccumulator labelAccumulator = new ColumnOverrideLabelAccumulator(headerData);
		headerData.setConfigLabelAccumulator(labelAccumulator);

		for (int i = 0; i < columnAccessor.getColumnCount(); i++) {
			String label = sortLabel(i);
			labelAccumulator.registerColumnOverrides(i, label);
			configRegistry.registerConfigAttribute(SortConfigAttributes.SORT_COMPARATOR, getByLabelOrdering(), DisplayMode.NORMAL, label);
		}
	}

	/**
	 * Configures the visual styling of the header layer {@linkplain #createHeaderLayer previously created}
	 * for presentation in a SWT Forms UI with a flat appearance.
	 * 
	 * @see #createHeaderLayer(SortedList, IColumnPropertyAccessor, String...)
	 */
	public void configureHeaders(ICellPainter cellPainter, IStyle style) {
		sortHeaders.addConfiguration(new SingleClickSortConfiguration(new SortableHeaderTextPainter(cellPainter, CellEdgeEnum.LEFT, true, 3, true)));
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, cellPainter, DisplayMode.NORMAL, GridRegion.COLUMN_HEADER);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, style, DisplayMode.NORMAL, GridRegion.COLUMN_HEADER);
		for (int i = 0; i < columnAccessor.getColumnCount(); i++) {
			String label = sortLabel(i);
			configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, cellPainter, DisplayMode.NORMAL, label);
			configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, style, DisplayMode.NORMAL, label);
		}
	}

	String sortLabel(int columnIndex) {
		return sortLabel(columnAccessor.getColumnProperty(columnIndex));
	}

	static String sortLabel(String propertyName) {
		return "sortBy:" + propertyName;
	}

	private Comparator<Object> getByLabelOrdering() {
		Collator collator = Collator.getInstance();
		collator.setStrength(Collator.PRIMARY);
		return (a, b) -> collator.compare(getLabel(a), getLabel(b));
	}

	private String getLabel(Object object) {
		LabelProviderService labels = ServiceConfigAttributes.getService(LabelProviderService.class, configRegistry, DisplayMode.NORMAL);
		Object value = (object instanceof IObservableValue<?>) ? ((IObservableValue<?>) object).getValue() : object;
		ILabelProvider labelProvider = (value == null) ? null : labels.getLabelProvider(value);
		return (labelProvider == null) ? null : labelProvider.getText(value);
	}

	public void setFilterField(Text filterField) {
		if (filterField != null) {
			filterField.addModifyListener(__ -> filterMatcher.setFilter(filterField.getText()));
		}
	}

	//
	// Nested types
	//

	class FilterMatcherEditor extends AbstractMatcherEditor<T> {
		void setFilter(String filterText) {
			Matcher<T> matcher = Matchers.trueMatcher();

			if ((filterText != null) && !filterText.isEmpty()) {
				// First, collapse sequences of asterisks
				filterText = filterText.replaceAll("\\*{2,}", "*"); //$NON-NLS-1$//$NON-NLS-2$

				// Then split on the asterisks
				String[] sections = filterText.split("\\*"); //$NON-NLS-1$

				// And compose the pattern
				Pattern pattern = Pattern.compile(Stream.of(sections)
						.map(Pattern::quote)
						.collect(Collectors.joining(".*")), //$NON-NLS-1$
						Pattern.CASE_INSENSITIVE);
				java.util.regex.Matcher regex = pattern.matcher(""); //$NON-NLS-1$

				matcher = item -> {
					boolean match = false;

					int cols = columnAccessor.getColumnCount();
					for (int i = 0; !match && (i < cols); i++) {
						String label = getLabel(columnAccessor.getDataValue(item, i));
						// The null label never matches
						if (label != null) {
							regex.reset(label);
							match = regex.find();
						}
					}

					return match;
				};
			}

			fireChanged(matcher);
			viewportLayer.fireLayerEvent(new RowStructuralRefreshEvent(viewportLayer));
		}
	}
}
