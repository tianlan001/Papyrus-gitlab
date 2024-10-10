/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.common.messages;

import org.eclipse.osgi.util.NLS;

/**
 * Helper class to obtains translated strings.
 * 
 * @author Jessy Mallet <jessy.mallet@obeo.fr>
 *
 */
// CHECKSTYLE:OFF
public final class Messages extends NLS {

	/**
	 * Bundle name.
	 */
	private static final String BUNDLE_NAME = "org.eclipse.papyrus.sirius.properties.common.messages.messages"; //$NON-NLS-1$

	/**
	 * Description of the EEF activation preference.
	 */
	public static String EEFPreferencePage_ActivateEEFDescription;

	/**
	 * The "Edit" action title for PropertiesDialog.
	 */
	public static String PropertiesDialogTitle_editAction;

	/**
	 * The "Create" action title for PropertiesDialog.
	 */
	public static String PropertiesDialogTitle_createAction;

	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
	// CHECKSTYLE:ON
}
