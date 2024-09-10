/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr  - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.utils.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * 
 * Preference Initializer for the NamedElement naming strategy
 * @since 3.3
 */
public class NameElementNamingStrategyPreferenceInitializer extends AbstractPreferenceInitializer {


	/**
	 * Preference key for the NamedElement naming strategy
	 * 
	 */
	public static final String NAMED_ELEMENT_INDEX_INITIALIZATION = "NAMED_ELEMENT_INDEX_INITIALIZATION"; //$NON-NLS-1$

	/**
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 *
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = org.eclipse.papyrus.uml.tools.utils.Activator.getDefault().getPreferenceStore();
		store.setDefault(NAMED_ELEMENT_INDEX_INITIALIZATION, NamedElementIndexNamingStrategyEnum.QUICK_INDEX_INITIALIZATION.getName());
	}

}
