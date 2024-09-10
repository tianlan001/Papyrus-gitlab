/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.painter;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.sort.painter.SortIconPainter;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AbstractHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.utils.HeaderAxisConfigurationManagementUtils;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.swt.graphics.Image;

/**
 * Inherited From SortIconPainter to manage the icon painter only for the header label and not for the index header.
 */
public class PapyrusSortIconPainter extends SortIconPainter {

	/**
	 * Constructor.
	 *
	 * @param paintBg
	 *            <code>true</code> if it should render the background itself,
	 *            <code>false</code> if the background rendering should be
	 *            skipped in here.
	 */
	public PapyrusSortIconPainter(final boolean paintBg) {
		super(paintBg);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.sort.painter.SortIconPainter#getImage(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 */
	@Override
	protected Image getImage(final ILayerCell cell, final IConfigRegistry configRegistry) {
		boolean isDisplayIndex = false;
		
		int position = cell.getRowPosition();
		if (position == 0) {
			INattableModelManager manager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
			AbstractHeaderAxisConfiguration config = HeaderAxisConfigurationManagementUtils.getColumnAbstractHeaderAxisConfigurationUsedInTable(manager.getTable());
			if (config != null && config.isDisplayIndex()) {
				isDisplayIndex = true;
			}
		}
		
		return isDisplayIndex ? null : super.getImage(cell, configRegistry);
	}

}
