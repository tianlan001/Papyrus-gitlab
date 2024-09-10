/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 486101
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.configuration;

import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.BeveledBorderDecorator;
import org.eclipse.nebula.widgets.nattable.painter.cell.decorator.CellPainterDecorator;
import org.eclipse.nebula.widgets.nattable.sort.config.DefaultSortConfiguration;
import org.eclipse.nebula.widgets.nattable.sort.painter.SortableHeaderTextPainter;
import org.eclipse.nebula.widgets.nattable.ui.util.CellEdgeEnum;
import org.eclipse.papyrus.infra.nattable.painter.CustomImagePainter;
import org.eclipse.papyrus.infra.nattable.painter.CustomizedCellPainter;
import org.eclipse.papyrus.infra.nattable.painter.PapyrusSortIconPainter;

/**
 * The abstract configuration used for the sort the sort
 *
 * @author Vincent Lorenzo
 *
 */
public abstract class PapyrusClickSortConfiguration extends DefaultSortConfiguration {

	/**
	 * Constructor.
	 */
	public PapyrusClickSortConfiguration() {
		super(new BeveledBorderDecorator(new CellPainterDecorator(new SortableHeaderTextPainter(new CustomizedCellPainter(), CellEdgeEnum.RIGHT, new PapyrusSortIconPainter(true)), CellEdgeEnum.LEFT, new CustomImagePainter())));
	}

}
