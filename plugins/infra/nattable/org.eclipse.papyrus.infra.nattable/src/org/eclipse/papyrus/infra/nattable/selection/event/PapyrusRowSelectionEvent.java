/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.infra.nattable.selection.event;

import java.util.Collection;

import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.event.RowSelectionEvent;

/**
 * The row selection event extended from row selection event (of nattable) to include the shift and the control masks.
 * 
 * @deprecated since 2.0
 */
@Deprecated
public class PapyrusRowSelectionEvent extends RowSelectionEvent {

	/**
	 * The shift mask used.
	 */
	private boolean withShiftMask = false;

	/**
	 * The control mask used.
	 */
	private boolean withControlMask = false;

	/**
	 * Constructor.
	 *
	 * @param selectionLayer
	 *            The selection layer.
	 * @param rowPositions
	 *            The row positions.
	 * @param rowPositionToMoveIntoViewport
	 *            The row position to move into viewport.
	 * @param withShiftMask
	 *            The shift mask used.
	 * @param withControlMask
	 *            The control mask used.
	 */
	public PapyrusRowSelectionEvent(final SelectionLayer selectionLayer, final Collection<Integer> rowPositions, final int rowPositionToMoveIntoViewport, final boolean withShiftMask, final boolean withControlMask) {
		super(selectionLayer, rowPositions, rowPositionToMoveIntoViewport);
		this.withShiftMask = withShiftMask;
		this.withControlMask = withControlMask;
	}

	/**
	 * Constructor by copy.
	 *
	 * @param event
	 *            The event to copy.
	 */
	protected PapyrusRowSelectionEvent(final PapyrusRowSelectionEvent event) {
		super(event);
		this.withShiftMask = event.withShiftMask;
		this.withControlMask = event.withControlMask;
	}

	/**
	 * Returns if the shift mask is used.
	 * 
	 * @return <code>true</code> if the shift mask is used, <code>false</code> otherwise.
	 */
	public boolean isWithShiftMask() {
		return this.withShiftMask;
	}

	/**
	 * Returns if the control mask is used.
	 * 
	 * @return <code>true</code> if the control mask is used, <code>false</code> otherwise.
	 */
	public boolean isWithControlMask() {
		return this.withControlMask;
	}
}
