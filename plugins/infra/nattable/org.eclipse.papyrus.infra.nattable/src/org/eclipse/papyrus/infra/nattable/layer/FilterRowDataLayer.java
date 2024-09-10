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
 *     Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Bug 562619
 ******************************************************************************/

package org.eclipse.papyrus.infra.nattable.layer;

import org.eclipse.nebula.widgets.nattable.command.ILayerCommand;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.filterrow.IFilterStrategy;
import org.eclipse.nebula.widgets.nattable.filterrow.command.ClearAllFiltersCommand;
import org.eclipse.nebula.widgets.nattable.filterrow.command.ClearFilterCommand;
import org.eclipse.nebula.widgets.nattable.filterrow.config.DefaultFilterRowConfiguration;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.event.RowStructuralRefreshEvent;
import org.eclipse.papyrus.infra.nattable.dataprovider.FilterRowDataProvider;
import org.eclipse.papyrus.infra.nattable.manager.refresh.StructuralRefreshConfiguration;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;

/**
 * {@link DataLayer} with a custom {@link IDataProvider} which stores/tracks the filter text on columns.
 * Applies region name of {@link GridRegion#FILTER_ROW} to the filter row.
 * Persists the filter text to the properties file.
 *
 * @param <T>
 *            type of the underlying row object
 *
 *            Duplicated and adapted code from Nattable
 */
public class FilterRowDataLayer<T> extends DataLayer {

	/** Prefix of the column label applied to each column in the filter row */
	public static final String FILTER_ROW_COLUMN_LABEL_PREFIX = "FILTER_COLUMN_"; //$NON-NLS-1$

	/** Prefix for the persistence key in the properties file */
	public static final String PERSISTENCE_KEY_FILTER_ROW_TOKENS = ".filterTokens"; //$NON-NLS-1$


	public FilterRowDataLayer(IFilterStrategy<T> filterStrategy, ILayer columnHeaderLayer, IDataProvider columnHeaderDataProvider, INattableModelManager tableManager) {
		super(new FilterRowDataProvider<>(filterStrategy, columnHeaderLayer, columnHeaderDataProvider, tableManager));

		addConfiguration(new DefaultFilterRowConfiguration());
		addConfiguration(new StructuralRefreshConfiguration());// bug 562619
	}

	@SuppressWarnings("unchecked")
	public FilterRowDataProvider<T> getFilterRowDataProvider() {
		return (FilterRowDataProvider<T>) dataProvider;
	}

	@Override
	public boolean doCommand(ILayerCommand command) {
		boolean handled = false;
		if (command instanceof ClearFilterCommand && command.convertToTargetLayer(this)) {
			int columnPosition = ((ClearFilterCommand) command).getColumnPosition();
			dataProvider.setDataValue(columnPosition, 0, null);
			handled = true;
		} else if (command instanceof ClearAllFiltersCommand) {
			getFilterRowDataProvider().clearAllFilters();
			handled = true;
		}

		if (handled) {
			fireLayerEvent(new RowStructuralRefreshEvent(this));
			return true;
		} else {
			return super.doCommand(command);
		}
	}

	@Override
	public LabelStack getConfigLabelsByPosition(int columnPosition, int rowPosition) {
		// At the data layer level position == index
		final LabelStack labels = super.getConfigLabelsByPosition(columnPosition, rowPosition);
		labels.addLabel(FILTER_ROW_COLUMN_LABEL_PREFIX + columnPosition);
		labels.addLabel(GridRegion.FILTER_ROW);
		return labels;
	}

}
