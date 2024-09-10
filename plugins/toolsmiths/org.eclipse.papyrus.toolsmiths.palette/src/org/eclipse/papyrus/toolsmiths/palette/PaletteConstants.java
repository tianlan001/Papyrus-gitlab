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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.palette;

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.presentation.PaletteConfigurationEditorPlugin;
import org.eclipse.papyrus.infra.types.presentation.TypesConfigurationsEditorPlugin;

/**
 * The palette customization plugin constants.
 */
public class PaletteConstants {

	/**
	 * Constructor.
	 */
	private PaletteConstants() {
		// to prevent instantiation
	}

	/** the file extension for palette configuration */
	public static final String PALETTECONFIGURATION_EXTENSION = Collections.unmodifiableList(Arrays.asList(PaletteConfigurationEditorPlugin.INSTANCE.getString("_UI_PaletteconfigurationEditorFilenameExtensions").split("\\s*,\\s*"))).get(0); //$NON-NLS-1$ //$NON-NLS-2$

	/** the file extension for element type files */
	public static final String ELEMENTTYPE_EXTENSION = Collections.unmodifiableList(Arrays.asList(TypesConfigurationsEditorPlugin.INSTANCE.getString("_UI_ElementtypesconfigurationsEditorFilenameExtensions").split("\\s*,\\s*"))).get(0); //$NON-NLS-1$ //$NON-NLS-2$

	/** The postfix for semantic element type model file. */
	public static final String ELEMENTTYPE_SEMENTIC_IDENTIFIER_POSTFIX = "_Semantic"; //$NON-NLS-1$

	/** The postfix for ui element type model file. */
	public static final String ELEMENTTYPE_UI_IDENTIFIER_POSTFIX = "_UI";//$NON-NLS-1$

	/** The enumeration of the context of the palette configuration model in case of customization */
	public enum PaletteModelContextEnum {
		New, // It's a new palette from scratch. Files will be place on local folder.
		Workspace, // It's a workspace palette. Files will be place on WS.
		Plugin, // It's a palette coming from a plugin definition. It's read-only, a copy and associated files will be placed on local folder
		Local // It's come from the local folder
	};

}
