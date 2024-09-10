/*******************************************************************************
 * Copyright (c) 2007 CEA List.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     CEA List - initial API and implementation
 *******************************************************************************/

package org.eclipse.papyrus.uml.extensionpoints.standard;

import org.eclipse.papyrus.uml.extensionpoints.Activator;

/** Interface that stores IDs adn constants for the extension point */
public class ExtensionIds {

	/** ID for the profile extension */
	public static final String PROFILE_EXTENSION_ID = Activator.PLUGIN_ID + "." + "UMLProfile";

	/** ID for the library extension point */
	public static final String LIBRARY_EXTENSION_ID = Activator.PLUGIN_ID + "." + "UMLLibrary";

	/** ID for the metamodel extension point */
	public static final String METAMODEL_EXTENSION_ID = Activator.PLUGIN_ID + "." + "UMLMetamodel";

	/** name of the <code>name</code> attribute in the DTD */
	public static final String ATT_NAME = "name";

	/** name of the <code>path</code> attribute in the DTD */
	public static final String ATT_PATH = "path";

	/** name of the <code>icon</code> attribute in the DTD */
	public static final String ATT_ICON_PATH = "iconpath";

	/** name of the <code>description</code> attribute in the DTD */
	public static final String ATT_DESCRIPTION = "description";

	/** name of the <code>provider</code>attribute in the DTD */
	public static final String ATT_PROVIDER = "provider";
}
