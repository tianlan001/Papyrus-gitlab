/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Activator;

/**
 * Initialize profile migration tool preferences
 */
public class ProfileMigrationInitializer extends AbstractPreferenceInitializer {

	/**
	 * Constructor.
	 *
	 */
	public ProfileMigrationInitializer() {
		super();
	}

	/**
	 * Set default preference, true for all dialogs
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = getPreferenceStore();
		for (String constPref : ProfileMigrationPreferenceConstants.mapPrefConstToLabel.keySet()) {
			store.setDefault(constPref, true);
		}
	}

	/**
	 * Get the preference store
	 * 
	 * @return the preference store
	 */
	protected IPreferenceStore getPreferenceStore() {
		return Activator.getDefault().getPreferenceStore();
	}

}
