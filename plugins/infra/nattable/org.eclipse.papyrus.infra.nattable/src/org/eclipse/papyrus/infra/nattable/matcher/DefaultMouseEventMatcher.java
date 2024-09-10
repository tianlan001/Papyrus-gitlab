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
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.matcher;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher;
import org.eclipse.swt.events.MouseEvent;

/**
 * This allows to define a default mouse event matcher for menu in no region.
 */
public class DefaultMouseEventMatcher extends MouseEventMatcher {

	/**
	 * Constructor
	 *
	 * @param stateMask
	 * @see "org.eclipse.swt.events.MouseEvent.stateMask"
	 * @param button
	 *            The button event (right, left, ...)
	 * @see org.eclipse.swt.events.MouseEvent#button
	 *      {@link MouseEventMatcher#LEFT_BUTTON},
	 *      {@link MouseEventMatcher#RIGHT_BUTTON} can be used for convenience
	 */
	public DefaultMouseEventMatcher(final int stateMask, final int button) {
		super(stateMask, null, button);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.nebula.widgets.nattable.ui.matcher.MouseEventMatcher#matches(org.eclipse.nebula.widgets.nattable.NatTable, org.eclipse.swt.events.MouseEvent, org.eclipse.nebula.widgets.nattable.layer.LabelStack)
	 */
	@Override
	public boolean matches(final NatTable natTable, final MouseEvent event, final LabelStack regionLabels) {

		boolean stateMaskMatches;
		if (0 != getStateMask()) {
			stateMaskMatches = (getStateMask() == event.stateMask) ? true : false;
		} else {
			stateMaskMatches = 0 == event.stateMask;
		}

		boolean eventRegionMatches = null == regionLabels && getEventRegion() == null;

		boolean buttonMatches = getButton() != 0 ? (getButton() == event.button) : true;

		return stateMaskMatches && eventRegionMatches && buttonMatches;
	}

}
