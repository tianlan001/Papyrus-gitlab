/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.internal.dnd;

import org.eclipse.jface.util.Geometry;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

/**
 * The utility methods about the drag of sash editors.
 */
public class PapyrusDragUtils {

	/**
	 * Shorthand method. returns the bounding rectangle for the given control, in display coordinates. Note that all 'Shell' controls are expected to be 'top level' so DO NOT do the origin offset for them.
	 *
	 * @param boundsControl
	 *            The given {@link Control}.
	 * @return The bounding rectangle for the given control.
	 */
	public static Rectangle getDisplayBounds(final Control boundsControl) {
		final Control parent = boundsControl.getParent();
		if (null == parent || boundsControl instanceof Shell) {
			return boundsControl.getBounds();
		}

		return Geometry.toDisplay(parent, boundsControl.getBounds());
	}

	/**
	 * Returns the location of the given event, in display coordinates.
	 *
	 * @return The location of the given event, in display coordinates.
	 */
	public static Point getEventLoc(Event event) {
		final Control ctrl = (Control) event.widget;
		return ctrl.toDisplay(new Point(event.x, event.y));
	}
}
