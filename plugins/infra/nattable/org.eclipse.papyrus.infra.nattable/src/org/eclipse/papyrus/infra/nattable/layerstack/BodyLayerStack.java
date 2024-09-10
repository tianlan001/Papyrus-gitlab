/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.layerstack;

import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.data.AutomaticSpanningDataProvider;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.action.KeyEditAction;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.hideshow.ColumnHideShowLayer;
import org.eclipse.nebula.widgets.nattable.hideshow.RowHideShowLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.reorder.ColumnReorderLayer;
import org.eclipse.nebula.widgets.nattable.selection.ITraversalStrategy;
import org.eclipse.nebula.widgets.nattable.selection.MoveCellSelectionCommandHandler;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.nebula.widgets.nattable.ui.matcher.KeyEventMatcher;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;
import org.eclipse.papyrus.infra.nattable.configuration.StyleConfiguration;
import org.eclipse.papyrus.infra.nattable.layer.PapyrusSelectionLayer;
import org.eclipse.papyrus.infra.nattable.layer.PapyrusSpanningDataLayer;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.reorder.CustomDefaultColumnReorderBindings;
import org.eclipse.papyrus.infra.nattable.utils.DefaultSizeUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableEditingDomainUtils;
import org.eclipse.swt.SWT;


/**
 * The BodyLayer stack used in Papyrus TableF
 *
 * @author Vincent Lorenzo
 *
 */
public class BodyLayerStack extends AbstractLayerTransform {

	private final SelectionLayer selectionLayer;

	private final DataLayer bodyDataLayer;

	private final ViewportLayer viewportLayer;

	private final ColumnHideShowLayer columnHideShowLayer;

	private final ColumnReorderLayer columnReorderLayer;

	private final RowHideShowLayer rowHideShowLayer;

	// private final RowReorderLayer rowReoderLayer;

	private AutomaticSpanningDataProvider spanProvider;

	public BodyLayerStack(final IDataProvider dataProvider, final INattableModelManager manager) {
		// this.bodyDataLayer = new DataLayer(dataProvider, DefaultSizeUtils.getDefaultCellWidth(), DefaultSizeUtils.getDefaultCellHeight());

		// this method is used to merge the cells of same value inside a table
		spanProvider = new AutomaticSpanningDataProvider(dataProvider, false, false) {

			@Override
			protected boolean valuesNotEqual(Object value1, Object value2) {
				if (value1 == value2) { // works for both null and not
					return false;
				}
				if ((value1 == null && value2 != null) || (value1 != null && value2 == null)) {
					return true;
				}
				return !value1.equals(value2);
			}
		};

		this.bodyDataLayer = new PapyrusSpanningDataLayer(TableEditingDomainUtils.getTableContextEditingDomain(manager.getTable()), manager, spanProvider, DefaultSizeUtils.getDefaultCellWidth(), DefaultSizeUtils.getDefaultCellHeight());

		this.bodyDataLayer.addConfiguration(new StyleConfiguration());

		this.columnReorderLayer = new ColumnReorderLayer(this.bodyDataLayer, false);

		// we register a custom configuration to manage the case where the reorder is forbidden
		this.columnReorderLayer.addConfiguration(new CustomDefaultColumnReorderBindings(manager));


		// to allow the reorder on the lines
		// this.rowReoderLayer = null;
		// this.rowReoderLayer = new RowReorderLayer(columnReorderLayer);
		// this.columnHideShowLayer = new ColumnHideShowLayer(this.rowReoderLayer);

		this.columnHideShowLayer = new ColumnHideShowLayer(this.columnReorderLayer);
		
		this.rowHideShowLayer = new RowHideShowLayer(columnHideShowLayer);
		this.selectionLayer = new PapyrusSelectionLayer(rowHideShowLayer);
		this.viewportLayer = new ViewportLayer(this.selectionLayer);
		setUnderlyingLayer(this.viewportLayer);
		setRegionName(GridRegion.BODY);
		
		//bug 476658
		viewportLayer.registerCommandHandler( new MoveCellSelectionCommandHandler( selectionLayer, ITraversalStrategy.TABLE_CYCLE_TRAVERSAL_STRATEGY));
	}

	public SelectionLayer getSelectionLayer() {
		return this.selectionLayer;
	}

	public DataLayer getBodyDataLayer() {
		return this.bodyDataLayer;
	}

	public ViewportLayer getViewportLayer() {
		return this.viewportLayer;
	}

	public ColumnHideShowLayer getColumnHideShowLayer() {
		return this.columnHideShowLayer;
	}

	public ColumnReorderLayer getColumnReorderLayer() {
		return this.columnReorderLayer;
	}

	public RowHideShowLayer getRowHideShowLayer() {
		return this.rowHideShowLayer;
	}

	@Override
	public void configure(ConfigRegistry configRegistry, UiBindingRegistry uiBindingRegistry) {
		super.configure(configRegistry, uiBindingRegistry);
		uiBindingRegistry.registerKeyBinding(new KeyEventMatcher(SWT.NONE, SWT.F2), new KeyEditAction());
		// configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, IEditableRule.ALWAYS_EDITABLE);
		// uiBindingRegistry.registerKeyBinding(new Mouse, new KeyEditAction());
		// uiBindingRegistry.
		// configRegistry.registerConfigAttribute(CellConfigAttributes.CELL_PAINTER, new CustomizedCellPainter(), DisplayMode.NORMAL, GridRegion.BODY);
	}


	public AutomaticSpanningDataProvider getBodyLayerSpanProvider() {
		return this.spanProvider;

	}
}
