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
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.customization.nattableconfiguration.utils;

import org.eclipse.swt.graphics.Image;

/**
 * The constants for the nattable configuration wizard.
 */
public class NattableConfigurationConstants {
	
	/**
	 * the path of the wizban image.
	 */
	public static final String ICON_WIZBAN_PATH = "icons/papyrusNattableconfigurationWizban.png"; //$NON-NLS-1$
	
	/**
	 * The extension for the nattable configuration file.
	 */
	@Deprecated
	public static final String NATTABLE_CONFIGURATION_EXTENSION_FILE = org.eclipse.papyrus.infra.nattable.utils.NattableConfigurationConstants.NATTABLE_CONFIGURATION_EXTENSION_FILE;
	
	/**
	 * The config folder path.
	 */
	public static final String CONFIG_FOLDER = "/configs/"; //$NON-NLS-1$
	
	/**
	 * The name of the about file.
	 */
	public static final String ABOUT_FILE_NAME = "about.html"; //$NON-NLS-1$
	
	/**
	 * The path for the papyrus icon.
	 */
	public static final String PAPYRUS_ICON_PATH = "/icons/papyrus.png"; //$NON-NLS-1$
	
	/**
	 * The add icon path.
	 */
	public static final String ADD_ICON_PATH = "/icons/Add_12x12.gif"; //$NON-NLS-1$
	
	/**
	 * The edit icon path.
	 */
	public static final String EDIT_ICON_PATH = "/icons/Edit_12x12.gif"; //$NON-NLS-1$
	
	/**
	 * The browse icon path.
	 */
	public static final String BROWSE_ICON_PATH = "/icons/browse_12x12.gif"; //$NON-NLS-1$
	
	/**
	 * The delete icon path.
	 */
	public static final String DELETE_ICON_PATH = "/icons/Delete_12x12.gif"; //$NON-NLS-1$
	
	/**
	 * The down icon path.
	 */
	public static final String DOWN_ICON_PATH = "/icons/Down_12x12.gif"; //$NON-NLS-1$
	
	/**
	 * The up icon path.
	 */
	public static final String UP_ICON_PATH = "/icons/Up_12x12.gif"; //$NON-NLS-1$
	
	/**
	 * The checked image.
	 */
	public static final Image CHECKED = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("org.eclipse.papyrus.infra.nattable.common", "icons/checked.gif"); //$NON-NLS-1$ //$NON-NLS-2$

	/**
	 * The unchecked image.
	 */
	public static final Image UNCHECKED = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage("org.eclipse.papyrus.infra.nattable.common", "icons/unchecked.gif"); //$NON-NLS-1$ //$NON-NLS-2$

}
