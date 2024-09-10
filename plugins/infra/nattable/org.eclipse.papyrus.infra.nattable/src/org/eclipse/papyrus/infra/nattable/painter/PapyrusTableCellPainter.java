/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 515737
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.painter;

import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.ICellPainter;
import org.eclipse.nebula.widgets.nattable.painter.cell.TableCellPainter;

/**
 * A customized table painter for Papyrus.
 *
 * NB: this class is created to bypass the NPE described in bug 517043.
 * So in case the bug is resolved, this class should not be used anymore
 * except if some specific behaviors have been overridden.
 *
 * @since 5.0
 */
public class PapyrusTableCellPainter extends TableCellPainter {

	/**
	 * Constructor.
	 *
	 * @param internalPainter
	 *            The ICellPainter that should be used to render the internal sub cells
	 */
	public PapyrusTableCellPainter(final ICellPainter internalPainter) {
		super(internalPainter);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Overridden to avoid the NPE when cell data is <code>null</code> (bug 517043 in NatTable).
	 */
	@Override
	protected Object[] getDataAsArray(final ILayerCell cell) {
		Object cellData = cell.getDataValue();

		if (null == cellData) {
			return null;
		}

		return super.getDataAsArray(cell);
	}
}
