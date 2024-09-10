/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.toolsmiths.plugin.builder.Activator;

/**
 * This class allows to initialize the Papyrus Plugin Builder preferences
 */
public class PluginBuilderPreferencesInitializer extends AbstractPreferenceInitializer {

	/**
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 *
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		// the main builder is unactivated by default
		store.setDefault(PluginBuilderPreferencesConstants.ACTIVATE_MAIN_PAPYRUS_BUILDER, Boolean.FALSE);

		store.setDefault(PluginBuilderPreferencesConstants.ACTIVATE_PAPYRUS_MANIFEST_BUILDER, Boolean.TRUE);
		store.setDefault(PluginBuilderPreferencesConstants.PAPYRUS_MANIFEST_BUILDER_CHECK_NO_REEXPORT, Boolean.TRUE);
		store.setDefault(PluginBuilderPreferencesConstants.PAPYRUS_MANIFEST_BUILDER_CHECK_DEPENDENCY_RANGE, Boolean.TRUE);

		store.setDefault(PluginBuilderPreferencesConstants.ACTIVATE_PAPYRUS_MODEL_BUILDER, Boolean.TRUE);
		store.setDefault(PluginBuilderPreferencesConstants.PAPYRUS_MODEL_BUILDER_CHECK_MODEL_DEPENDENCIES, Boolean.TRUE);
		store.setDefault(PluginBuilderPreferencesConstants.PAPYRUS_MODEL_BUILDER_VALIDATE_MODEL, Boolean.TRUE);

	}

}
