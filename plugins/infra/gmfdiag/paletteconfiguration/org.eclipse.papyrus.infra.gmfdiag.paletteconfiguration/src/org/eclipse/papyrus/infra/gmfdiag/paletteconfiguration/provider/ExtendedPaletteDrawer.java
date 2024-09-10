/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Esterel Technologies SAS and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Sebastien Gabel (Esterel Technologies SAS) - implements IExtendedPaletteEntry interface (bug 513803)
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.provider;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.Activator;
import org.eclipse.ui.plugin.AbstractUIPlugin;


/**
 * Specific implementation of the Palette Drawer for Papyrus. The one from GMF is not used, as it is internal.
 */
public class ExtendedPaletteDrawer extends org.eclipse.gef.palette.PaletteDrawer implements IExtendedPaletteEntry {

	/**
	 * Creates a new PaletteDrawerEx, with the default icon
	 *
	 * @param label
	 *            the label of the drawer
	 * @param id
	 *            the unique identifier of this drawer.
	 */
	public ExtendedPaletteDrawer(String label, String id) {
		this(label, AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID, "/icons/drawer.gif"), id);
	}

	/**
	 * Creates a new PaletteDrawerEx.
	 *
	 * @param label
	 *            the label of the drawer
	 * @param icon
	 *            the icon of the drawer
	 * @param id
	 *            the unique identifier of this drawer.
	 */
	public ExtendedPaletteDrawer(String label, ImageDescriptor icon, String id) {
		super(label, icon);
		setId(id);
	}

}
