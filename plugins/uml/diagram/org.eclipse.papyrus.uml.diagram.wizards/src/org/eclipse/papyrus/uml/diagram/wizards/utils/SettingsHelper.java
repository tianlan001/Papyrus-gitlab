/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Thibault Le Ouay (Sherpa Engineering) t.leouay@sherpa-eng.com  - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.wizards.utils;

import org.eclipse.jface.dialogs.IDialogSettings;


public class SettingsHelper {

	/** The Constant ARCHITECTURE_CONTEXTS. */
	private static final String ARCHITECTURE_CONTEXTS = "architectureContexts"; //$NON-NLS-1$

	/** The my settings. */
	private final IDialogSettings mySettings;

	/**
	 * Instantiates a new settings utils.
	 *
	 * @param settings
	 *            the settings
	 */
	public SettingsHelper(IDialogSettings settings) {
		mySettings = settings;
	}

	/**
	 * Gets the saved architecture contexts.
	 *
	 * @return the saved architecture contexts
	 * @since 3.0
	 */
	public String[] getArchitectureContexts() {
		String[] contexts = mySettings.getArray(ARCHITECTURE_CONTEXTS);
		return (contexts != null) ? contexts : new String[0];
	}

	/**
	 * Save the given architecture contexts.
	 *
	 * @param contexts
	 *            the architecture contexts
	 * @since 3.0
	 */
	public void saveArchitectureContexts(String[] contexts) {
		mySettings.put(ARCHITECTURE_CONTEXTS, contexts);
	}

}
