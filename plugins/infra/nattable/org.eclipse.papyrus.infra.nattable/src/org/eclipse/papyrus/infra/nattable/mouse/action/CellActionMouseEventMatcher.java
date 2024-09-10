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

package org.eclipse.papyrus.infra.nattable.mouse.action;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.swt.events.MouseEvent;

/**
 * Matcher for cell action.
 * When the cell value is the actionId, the matcher must match!!!
 *
 * @since 6.7
 */
public class CellActionMouseEventMatcher extends MouseEventMatcher {

	/**
	 * the action id to match (the column string id).
	 * When the cell value is the actionId, the matcher must match
	 */
	private final String actionId;

	/**
	 *
	 * Constructor.
	 *
	 * @param stateMask
	 * @param eventRegion
	 * @param button
	 * @param actionId
	 */
	public CellActionMouseEventMatcher(final int stateMask, final String eventRegion, final int button, final String actionId) {
		super(stateMask, eventRegion, button);
		this.actionId = actionId;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher#matches(org.eclipse.nebula.widgets.nattable.NatTable, org.eclipse.swt.events.MouseEvent, org.eclipse.nebula.widgets.nattable.layer.LabelStack)
	 *
	 * @param natTable
	 * @param event
	 * @param regionLabels
	 * @return
	 */
	@Override
	public boolean matches(final NatTable natTable, final MouseEvent event, final LabelStack regionLabels) {
		final int colPos = natTable.getColumnPositionByX(event.x);
		final int rowPos = natTable.getRowPositionByY(event.y);
		final ILayerCell cellLayer = natTable.getCellByPosition(colPos, rowPos);
		return super.matches(natTable, event, regionLabels) && this.actionId.equals(cellLayer.getDataValue());
	}
}