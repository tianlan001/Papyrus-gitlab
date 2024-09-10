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

package org.eclipse.papyrus.infra.editor.welcome.nattable.widgets;

import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.CellConfigAttributes;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.IColumnPropertyAccessor;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.data.ListDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.CompositeLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.style.CellStyleAttributes;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.style.HorizontalAlignmentEnum;
import org.eclipse.nebula.widgets.nattable.style.Style;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.editor.welcome.nattable.ServiceConfigAttributes;
import org.eclipse.papyrus.infra.editor.welcome.nattable.painter.LabelPainter;
import org.eclipse.papyrus.infra.editor.welcome.nattable.painter.LabelProviderImagePainter;
import org.eclipse.papyrus.infra.editor.welcome.nattable.painter.LabelProviderTextPainter;
import org.eclipse.papyrus.infra.editor.welcome.nattable.sorting.ColumnHeaderHelper;
import org.eclipse.papyrus.infra.editor.welcome.nattable.sorting.EventListObservableAdapter;
import org.eclipse.papyrus.infra.editor.welcome.nattable.sorting.PapyrusGlazedListEventsLayer;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.properties.ui.modelelement.DataSource;
import org.eclipse.papyrus.infra.properties.ui.modelelement.ModelElement;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;

/**
 * A {@link NatTable} composite with a "flat" presentation suitable for use in an SWT Forms context.
 */
public abstract class FormTable<E> extends Composite {
	private final IConfigRegistry configRegistry;
	private final NatTable table;
	private final EventListObservableAdapter<E> backingList;
	private final PapyrusGlazedListEventsLayer<E> eventLayer;

	private String propertyPath;
	private DataSource input;

	/**
	 * Initializes me with neither label text nor filter field.
	 *
	 * @param parent
	 *            the composite in which I am created
	 * @param style
	 *            my styling (affects the nested {@code NatTable} widget, only)
	 * @param columnAccessor
	 *            the column property accessor for the nested {@code NatTable} to present columns
	 * @param columnTitle
	 *            the titles of the columns, which must correspond to the {@code columnAccessor}
	 */
	public FormTable(Composite parent, int style, IColumnPropertyAccessor<E> columnAccessor, String... columnTitle) {
		this(parent, style, false, null, columnAccessor, columnTitle);
	}

	/**
	 * Initializes me without any label text.
	 *
	 * @param parent
	 *            the composite in which I am created
	 * @param style
	 *            my styling (affects the nested {@code NatTable} widget, only)
	 * @param filter
	 *            whether to show a filter field above the table
	 * @param columnAccessor
	 *            the column property accessor for the nested {@code NatTable} to present columns
	 * @param columnTitle
	 *            the titles of the columns, which must correspond to the {@code columnAccessor}
	 */
	public FormTable(Composite parent, int style, boolean filter, IColumnPropertyAccessor<E> columnAccessor, String... columnTitle) {
		this(parent, style, filter, null, columnAccessor, columnTitle);
	}

	/**
	 * Initializes me without a filter field.
	 *
	 * @param parent
	 *            the composite in which I am created
	 * @param style
	 *            my styling (affects the nested {@code NatTable} widget, only)
	 * @param label
	 *            the label to show above the table, or {@code null} to show no label
	 * @param columnAccessor
	 *            the column property accessor for the nested {@code NatTable} to present columns
	 * @param columnTitle
	 *            the titles of the columns, which must correspond to the {@code columnAccessor}
	 */
	public FormTable(Composite parent, int style, String label, IColumnPropertyAccessor<E> columnAccessor, String... columnTitle) {
		this(parent, style, false, label, columnAccessor, columnTitle);
	}

	/**
	 * Initializes me.
	 *
	 * @param parent
	 *            the composite in which I am created
	 * @param style
	 *            my styling (affects the nested {@code NatTable} widget, only)
	 * @param filter
	 *            whether to show a filter field above the table
	 * @param label
	 *            the label to show above the table (and filter field, if any), or {@code null} to show no label
	 * @param columnAccessor
	 *            the column property accessor for the nested {@code NatTable} to present columns
	 * @param columnTitle
	 *            the titles of the columns, which must correspond to the {@code columnAccessor}
	 */
	public FormTable(Composite parent, int style, boolean filter, String label, IColumnPropertyAccessor<E> columnAccessor, String... columnTitle) {
		super(parent, style);

		setBackground(parent.getBackground());
		setBackgroundMode(SWT.INHERIT_DEFAULT);

		setLayout(new GridLayout());

		configRegistry = new ConfigRegistry();

		// Optional label for the table
		if ((label != null) && !label.isEmpty()) {
			Label _label = new Label(parent, SWT.LEFT);
			_label.setText(label);
		}

		// Optional filter field
		Text filterField = null;
		if (filter) {
			filterField = new Text(parent, SWT.BORDER | SWT.SEARCH | SWT.ICON_CANCEL);
			filterField.setMessage("filter");
			filterField.setToolTipText("Filter the table contents (use * to match any string)");
			GridDataFactory.fillDefaults().applyTo(filterField);
		}

		// Body of the table
		backingList = new EventListObservableAdapter<>();
		FilterList<E> filteredList = null;
		SortedList<E> sortedList;
		if (filter) {
			filteredList = new FilterList<>(backingList);
			sortedList = new SortedList<>(filteredList, null);
		} else {
			sortedList = new SortedList<>(backingList, null);
		}
		IDataProvider dataProvider = new ListDataProvider<>(sortedList, columnAccessor);
		DataLayer bodyDataLayer = new DataLayer(dataProvider);
		eventLayer = new PapyrusGlazedListEventsLayer<>(bodyDataLayer, backingList);
		SelectionLayer selectionLayer = new SelectionLayer(eventLayer, false);
		addConfigurations(selectionLayer);
		ViewportLayer viewportLayer = new ViewportLayer(selectionLayer);
		viewportLayer.setRegionName(GridRegion.BODY);

		// Column headers
		ColumnHeaderHelper<E> headerHelper = new ColumnHeaderHelper<>(configRegistry, viewportLayer, selectionLayer);
		ILayer headers = headerHelper.createHeaderLayer(sortedList, columnAccessor, columnTitle);

		if (filteredList != null) {
			// Filtering
			headers = headerHelper.createFilterLayer(filteredList);
			headerHelper.setFilterField(filterField);
		}

		// Arrange the headers and body together
		CompositeLayer composition = new CompositeLayer(1, 2);
		composition.setChildLayer(GridRegion.COLUMN_HEADER, headers, 0, 0);
		composition.setChildLayer(GridRegion.BODY, viewportLayer, 0, 1);

		// Sorting configuration
		headerHelper.configureSorting();

		//
		// Presentation configuration
		//
		ICellPainter commonPainter = new LabelProviderTextPainter(false, true, 3, true);
		ICellPainter iconAndTextPainter = new LabelPainter(commonPainter, CellEdgeEnum.LEFT, new LabelProviderImagePainter());

		// Headers
		Style headerStyle = new Style();
		headerStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, parent.getBackground());
		headerStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.LEFT);
		headerStyle.setAttributeValue(CellStyleAttributes.FONT, JFaceResources.getFont(JFaceResources.BANNER_FONT));
		headerHelper.configureHeaders(commonPainter, headerStyle);

		// Body
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, iconAndTextPainter, DisplayMode.NORMAL, GridRegion.BODY);
		Style bodyStyle = new Style();
		bodyStyle.setAttributeValue(CellStyleAttributes.BACKGROUND_COLOR, parent.getBackground());
		bodyStyle.setAttributeValue(CellStyleAttributes.HORIZONTAL_ALIGNMENT, HorizontalAlignmentEnum.LEFT);
		configureBodyStyle(bodyStyle);
		configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_STYLE, bodyStyle, DisplayMode.NORMAL, GridRegion.BODY);
		configRegistry.registerConfigAttribute(CellConfigAttributes.RENDER_GRID_LINES, false);

		table = new NatTable(parent, composition, false);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(table);
		table.setConfigRegistry(configRegistry);
		table.setBackground(parent.getBackground());
		table.addConfiguration(new DefaultNatTableStyleConfiguration());
		table.configure();
	}

	protected void addConfigurations(SelectionLayer selectionLayer) {
		// Pass
	}

	protected void configureBodyStyle(Style bodyStyle) {
		// Pass
	}

	/**
	 * Sets the qualified property name (including model element path) of the
	 * {@linkplain DataSource data-source} property to access. The referenced
	 * property must be an {@linkplain IObservableList observable list}.
	 * 
	 * @param propertyPath
	 *            the property path
	 */
	public void setProperty(String propertyPath) {
		this.propertyPath = propertyPath;
		checkInput();
	}

	public String getProperty() {
		return propertyPath;
	}

	/**
	 * Sets the input DataSource for this Property editor.
	 *
	 * @param input
	 */
	public void setInput(DataSource input) {
		this.input = input;
		checkInput();
	}

	protected void checkInput() {
		String propertyPath = getProperty();

		if ((input != null) && (propertyPath != null)) {
			String elementPath = propertyPath.substring(0, propertyPath.lastIndexOf(':'));
			String propertyName = propertyPath.substring(elementPath.length() + 1);

			ModelElement element = input.getModelElement(elementPath);

			ServiceConfigAttributes.with(configRegistry, this::getService)
					.register(LabelProviderService.class)
					.register(NavigationService.class)
					.register(IPageManager.class);
			ServiceConfigAttributes.registerService(ISelectionProvider.class, configRegistry,
					getService(IMultiDiagramEditor.class).getEditorSite().getSelectionProvider());

			eventLayer.setActive(false);
			try {
				@SuppressWarnings("unchecked")
				IObservableList<E> diagrams = (IObservableList<E>) element.getObservable(propertyName);
				this.backingList.setDelegate(diagrams);
			} finally {
				eventLayer.setActive(true);
				table.refresh();
			}
		}
	}

	/**
	 * @return the input DataSource for this Property editor
	 */
	public DataSource getInput() {
		return input;
	}

	protected EObject getModelElement() {
		return EMFHelper.getEObject(input.getSelection().getFirstElement());
	}

	protected <S> S getService(Class<? extends S> serviceType) {
		try {
			return ServiceUtilsForEObject.getInstance().getService(serviceType, getModelElement());
		} catch (ServiceException e) {
			throw new IllegalStateException(e);
		}
	}
}
