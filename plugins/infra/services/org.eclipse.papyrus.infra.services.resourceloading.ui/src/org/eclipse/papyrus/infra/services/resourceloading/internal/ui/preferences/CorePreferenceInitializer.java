/*****************************************************************************
 * Copyright (c) 2010, 2016 Atos Origin, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading.internal.ui.preferences;

import static org.eclipse.papyrus.infra.services.resourceloading.internal.ui.preferences.ICorePreferenceConstants.PREF_CORE_DEFINE_LOADING_STRATEGY;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.services.resourceloading.internal.ui.UIPlugin;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * This preference initializer initializes diagram preferences specific to the
 * activity diagram.
 */
public class CorePreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * Initialize default preferences
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(PREF_CORE_DEFINE_LOADING_STRATEGY, 0);

		// Migrate from the diagram bundle for upgrade from Papyrus 1.1 or earlier
		IPreferenceStore legacy = new ScopedPreferenceStore(InstanceScope.INSTANCE, "org.eclipse.papyrus.infra.gmfdiag.preferences"); //$NON-NLS-1$
		if (legacy.contains(PREF_CORE_DEFINE_LOADING_STRATEGY)) {
			store.setValue(PREF_CORE_DEFINE_LOADING_STRATEGY, legacy.getInt(PREF_CORE_DEFINE_LOADING_STRATEGY));
			legacy.setToDefault(PREF_CORE_DEFINE_LOADING_STRATEGY); // Removes it because now it has no default
		}
	}

	/**
	 * Get the preference store
	 */
	protected IPreferenceStore getPreferenceStore() {
		return UIPlugin.getDefault().getPreferenceStore();
	}
}
