/*****************************************************************************
 * Copyright (c) 2020 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are the property of CEA LIST, their use is subject to specific
 * agreement with the CEA LIST.
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr>
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.mouse.action;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.swt.events.MouseEvent;

/**
 * Matcher for empty line selection in row header
 * 
 * @since 6.7
 */
public class EmptyLineRowHeaderEventMatch extends MouseEventMatcher {

	/**
	 *
	 * Constructor.
	 *
	 * @param stateMask
	 * @param eventRegion
	 * @param button
	 * @param actionId
	 */
	public EmptyLineRowHeaderEventMatch(final int stateMask, final String eventRegion, final int button) {
		super(stateMask, eventRegion, button);
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
		final Object value = cellLayer.getDataValue();
		if (value instanceof ITreeItemAxis) {
			return super.matches(natTable, event, regionLabels) && ((ITreeItemAxis) value).getElement() == null;
		}
		return false;
	}
}