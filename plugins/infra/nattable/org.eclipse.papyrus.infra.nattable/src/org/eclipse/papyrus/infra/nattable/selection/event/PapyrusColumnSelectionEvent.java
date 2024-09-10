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

import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.event.ColumnSelectionEvent;

/**
 * The column selection event extended from column selection event (of nattable) to include the shift and the control masks.
 * 
 * @deprecated since 2.0
 */
@Deprecated
public class PapyrusColumnSelectionEvent extends ColumnSelectionEvent {

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
	 * @param columnPosition
	 *            The column position.
	 * @param withShiftMask
	 *            The shift mask used.
	 * @param withControlMask
	 *            The control mask used.
	 */
	public PapyrusColumnSelectionEvent(final SelectionLayer selectionLayer, final int columnPosition, final boolean withShiftMask, final boolean withControlMask) {
		super(selectionLayer, columnPosition);
		this.withShiftMask = withShiftMask;
		this.withControlMask = withControlMask;
	}

	/**
	 * Constructor by copy.
	 *
	 * @param event
	 *            The event to copy.
	 */
	protected PapyrusColumnSelectionEvent(final PapyrusColumnSelectionEvent event) {
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
