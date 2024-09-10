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

package org.eclipse.papyrus.infra.nattable.painter;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter;
import org.eclipse.swt.graphics.Image;

/**
 * This painter will paint the image action, when the cell value is the actionId
 *
 * @since 6.7
 */
public class ActionImagePainter extends ImagePainter {

	/**
	 * the id of the action
	 */
	private final String actionId;

	/**
	 * Constructor.
	 *
	 */
	public ActionImagePainter(final Image image, final String actionName) {
		super(image);
		this.actionId = actionName;
	}

	/**
	 * @see org.eclipse.nebula.widgets.nattable.painter.cell.ImagePainter#getImage(org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell, org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
	 *
	 * @param cell
	 * @param configRegistry
	 * @return
	 */
	@Override
	protected Image getImage(final ILayerCell cell, final IConfigRegistry configRegistry) {
		final Object value = cell.getDataValue();
		if (this.actionId.equals(value)) {
			return super.getImage(cell, configRegistry);
		}
		return null;
	}

}
