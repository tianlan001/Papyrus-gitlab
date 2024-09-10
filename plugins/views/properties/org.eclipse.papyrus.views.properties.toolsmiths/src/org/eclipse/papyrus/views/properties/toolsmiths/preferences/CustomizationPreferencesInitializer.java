/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 573987
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;

/**
 * The initializer for @see {@link CustomizationPreferencePage}
 *
 * @author Camille Letavernier
 */
public class CustomizationPreferencesInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(CustomizationPreferencePage.OPEN_CUSTOMIZATION_PERSPECTIVE, true);
		store.setDefault(CustomizationPreferencePage.ASK_FOR_CONFIRMATION, true);
		store.setDefault(CustomizationPreferencePage.OPEN_CUSTOMIZATION_PERSPECTIVE, CustomizationEditorActionKind.getDefault().name());
	}

}
