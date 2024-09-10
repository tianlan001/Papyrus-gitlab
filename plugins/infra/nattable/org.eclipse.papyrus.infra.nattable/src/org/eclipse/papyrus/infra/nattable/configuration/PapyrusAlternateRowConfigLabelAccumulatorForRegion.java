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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.configuration;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.grid.cell.AlternatingRowConfigLabelAccumulator;
import org.eclipse.nebula.widgets.nattable.hideshow.RowHideShowLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.layerstack.BodyLayerStack;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;

/**
 * Custom configuration to get the expected row background color, according to the ShowHideLayer
 * This class is in visibility package, because the API (method setConfigRegistry) is not satisifaying.
 *
 * @since 6.7
 */
final class PapyrusAlternateRowConfigLabelAccumulatorForRegion extends AlternatingRowConfigLabelAccumulator {
	/**
	 * TODO : ask to NatTable to open its API
	 * the configured layer
	 */
	private final ILayer layer;

	/**
	 * The config registry for the current table
	 */
	private IConfigRegistry configRegistry;

	/**
	 * Constructor.
	 *
	 * @param layer
	 *            the configured label
	 */
	public PapyrusAlternateRowConfigLabelAccumulatorForRegion(final ILayer layer) {
		super(layer);
		this.layer = layer;
	}

	/**
	 *
	 * This method allows to set the {@link IConfigRegistry} to use to get the current edited NatTable widget
	 *
	 * @param configRegistry
	 *            the config registry
	 */
	void setConfigRegistry(final IConfigRegistry configRegistry) {
		this.configRegistry = configRegistry;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.grid.cell.AlternatingRowConfigLabelAccumulator#accumulateConfigLabels(org.eclipse.nebula.widgets.nattable.layer.LabelStack, int, int)
	 *
	 * @param configLabels
	 * @param columnPosition
	 * @param rowPosition
	 */
	@Override
	public void accumulateConfigLabels(LabelStack configLabels, int columnPosition, int rowPosition) {
		final BodyLayerStack layerStack = getBodyLayerStack();
		final NatTable natTable = getNatTable();
		if (layerStack != null && natTable != null) {
			final RowHideShowLayer rowHideShowLayer = layerStack.getRowHideShowLayer();
			// refind the initial row index
			int rowIndex = this.layer.getRowIndexByPosition(rowPosition);
			int realRowPosition = rowHideShowLayer.underlyingToLocalRowPosition(natTable, rowIndex);

			configLabels.addLabel((realRowPosition % 2 == 0 ? EVEN_ROW_CONFIG_TYPE : ODD_ROW_CONFIG_TYPE));

		}
		super.accumulateConfigLabels(configLabels, columnPosition, rowPosition);
	}

	/**
	 *
	 * @return
	 *         the natTable widget, or <code>null</code> (if not found or disposed)
	 */
	private NatTable getNatTable() {
		final INattableModelManager manager = getNattableModelManager();
		if (manager == null) {
			return null;
		}
		final NatTable natTable = manager.getAdapter(NatTable.class);
		if (natTable.isDisposed()) {
			return null;
		}
		return natTable;
	}

	/**
	 *
	 * @return
	 *         the {@link INattableModelManager} for which we are configuring the row color, or <code>null</code>
	 */
	private INattableModelManager getNattableModelManager() {
		if (this.configRegistry == null) {
			return null;
		}
		return this.configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
	}

	/**
	 *
	 * @return
	 *         the {@link BodyLayerStack} used by the table, or <code>null</code>
	 */
	private BodyLayerStack getBodyLayerStack() {
		final INattableModelManager manager = getNattableModelManager();
		if (manager == null) {
			return null;
		}
		return manager.getBodyLayerStack();
	}
}
