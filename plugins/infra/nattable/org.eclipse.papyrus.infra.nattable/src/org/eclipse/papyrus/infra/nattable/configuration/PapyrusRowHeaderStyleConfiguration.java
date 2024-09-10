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
package org.eclipse.papyrus.infra.nattable.configuration;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.config.DefaultRowHeaderStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CellPainterDecorator;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.papyrus.infra.nattable.painter.CustomImagePainter;
import org.eclipse.papyrus.infra.nattable.painter.CustomizedCellPainter;
import org.eclipse.papyrus.infra.nattable.painter.PapyrusBeveledBorderDecorator;

/**
 *
 * The default style for the row header. We provide a specific label provider and image painter
 *
 */
public class PapyrusRowHeaderStyleConfiguration extends DefaultRowHeaderStyleConfiguration {


	/**
	 *
	 * @see org.eclipse.nebula.widgets.nattable.layer.config.DefaultRowHeaderStyleConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param configRegistry
	 */
	@Override
	public void configureRegistry(final IConfigRegistry configRegistry) {
		this.cellPainter = new PapyrusBeveledBorderDecorator(new CellPainterDecorator(new CustomizedCellPainter(), CellEdgeEnum.LEFT, new CustomImagePainter()));
		// configureHeaderHasSelectionStyle(configRegistry);
		super.configureRegistry(configRegistry);
	}

}
