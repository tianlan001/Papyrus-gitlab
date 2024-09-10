/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Benoit Maggi (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.uml.service.types.Activator;

public class UMLElementTypePreferenceInitializer extends AbstractPreferenceInitializer {

	
	/**
	 * Preference key for allowing move of stereotype application in the same resource as 
	 * their base element
	 */
	public static final String MOVE_STEREOTYPEAPPLICATION_ELEMENT_IN_SAME_RESOURCE = "MOVE_STEREOTYPEAPPLICATION_ELEMENT_IN_SAME_RESOURCE"; //$NON-NLS-1$

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(MOVE_STEREOTYPEAPPLICATION_ELEMENT_IN_SAME_RESOURCE, true);
	}

}