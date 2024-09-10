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
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Benoit  Maggi   benoit.maggi@cea.fr - #451968
 *****************************************************************************/
package org.eclipse.papyrus.infra.viewpoints.policy;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Initializes the default preferences for the viewpoints
 *
 * @author Laurent Wouters
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(PreferenceConstants.P_CONF_TYPE, PreferenceConstants.P_CONF_TYPE_DEFAULT_VALUE);
		store.setDefault(PreferenceConstants.P_CONF_PATH_SCHEME, PreferenceConstants.P_CONF_PATH_SCHEME_FILE_VALUE);
		store.setDefault(PreferenceConstants.P_CONF_PATH, "");
		store.setDefault(PreferenceConstants.P_STAKEHOLDER, "");
		store.setDefault(PreferenceConstants.P_VIEWPOINT, "");
		store.setDefault(PreferenceConstants.P_FORCE_MULTIPLICITY, "false");
	}

}
