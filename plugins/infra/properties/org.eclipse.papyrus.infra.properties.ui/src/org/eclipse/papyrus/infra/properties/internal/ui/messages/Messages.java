/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.internal.ui.messages;

import org.eclipse.osgi.util.NLS;

/**
 * String externalisation for plug-in org.eclipse.papyrus.infra.properties.ui
 *
 * @author Camille Letavernier
 */
public class Messages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.papyrus.infra.properties.internal.ui.messages.messages"; //$NON-NLS-1$

	public static String EcorePropertyEditorFactory_CreateANew;

	public static String EditionDialog_CanNotFindview;

	public static String EditionDialog_CreateANewElement;

	public static String Preferences_ConflictWarning1;

	public static String Preferences_ConflictWarning2;

	public static String Preferences_ConflictWarningTitle;

	public static String Preferences_Contexts;

	public static String Preferences_Custom;

	public static String Preferences_Plugin;

	public static String PropertyEditorFactory_CreateANewElement;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
