/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *  Camille Letavernier (camille.letavernier@cea.fr) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Standard file extensions used in Papyrus
 *
 * @author Camille Letavernier
 *
 */
public class FileExtensions {

	public static final Map<String, String> umlExtensions = new LinkedHashMap<String, String>();

	public static final Map<String, String> umlProfileExtensions = new LinkedHashMap<String, String>();

	public static final Map<String, String> cssStylesheetsExtension = new LinkedHashMap<String, String>();

	public static final Map<String, String> imagesFilesExtensions = new LinkedHashMap<String, String>();
	
	public static final Map<String, String> allFilesExtensions = new LinkedHashMap<String, String>();

	static {
		umlExtensions.put("*.uml", "UML (*.uml)"); //$NON-NLS-1$ //$NON-NLS-2$
		umlExtensions.put("*.profile.uml", "UML Profiles (*.profile.uml)"); //$NON-NLS-1$ //$NON-NLS-2$

		umlProfileExtensions.put("*.profile.uml", "UML Profiles (*.profile.uml)"); //$NON-NLS-1$ //$NON-NLS-2$
		umlProfileExtensions.put("*.uml", "UML (*.uml)"); //$NON-NLS-1$ //$NON-NLS-2$

		cssStylesheetsExtension.put("*.css", "CSS Stylesheets (*.css)"); //$NON-NLS-1$ //$NON-NLS-2$
		
		imagesFilesExtensions.put("*.png", "PNG (*.png"); //$NON-NLS-1$ //$NON-NLS-2$
		imagesFilesExtensions.put("*.jpg", "JPG (*.jpg"); //$NON-NLS-1$ //$NON-NLS-2$

		allFilesExtensions.put("*", "All (*)"); //$NON-NLS-1$ //$NON-NLS-2$
	}


}
