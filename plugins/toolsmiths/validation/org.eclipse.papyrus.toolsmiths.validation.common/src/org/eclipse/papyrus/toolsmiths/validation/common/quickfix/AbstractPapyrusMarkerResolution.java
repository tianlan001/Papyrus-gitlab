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
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.quickfix;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMarkerResolution2;
import org.eclipse.ui.plugin.AbstractUIPlugin;

/**
 * Abstract {@link IMarkerResolution2} for Papyrus (provides the Papyrus icon
 */
public abstract class AbstractPapyrusMarkerResolution implements IMarkerResolution2 {

	/**
	 * @see org.eclipse.ui.IMarkerResolution2#getImage()
	 *
	 * @return
	 */
	@Override
	public Image getImage() {
		final ImageRegistry registry = Activator.getDefault().getImageRegistry();
		Image image = registry.get(Activator.PAPYRUS_ICON_PATH);
		if (null == image) {
			registry.put(Activator.PAPYRUS_ICON_PATH, AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, Activator.PAPYRUS_ICON_PATH));
			image = registry.get(Activator.PAPYRUS_ICON_PATH);
		}
		return image;
	}

}
