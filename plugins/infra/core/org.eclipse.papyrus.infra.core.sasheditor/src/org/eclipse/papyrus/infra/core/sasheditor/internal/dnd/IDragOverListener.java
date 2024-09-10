/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.internal.dnd;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

/**
 * Implementers of this interface will receive notifications when objects are dragged over
 * a particular SWT control.
 */
public interface IDragOverListener {

	/**
	 * Notifies the receiver that the given object has been dragged over
	 * the given position. Returns a drop target if the object may be
	 * dropped in this position. Returns null otherwise.
	 *
	 * @param draggedObject
	 *            object being dragged over this location
	 * @param position
	 *            location of the cursor
	 * @param dragRectangle
	 *            current drag rectangle (may be an empty rectangle if none)
	 * @return a valid drop target or null if none
	 */
	public IDropTarget drag(Control currentControl, Object draggedObject,
			Point position, Rectangle dragRectangle);
}
