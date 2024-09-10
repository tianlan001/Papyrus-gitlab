/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.fr - Initial API and implementation
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Bug 497289
 *
 *****************************************************************************/

package org.eclipse.papyrus.views.modelexplorer.newchild.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.views.modelexplorer.newchild.Activator;

/**
 * Initialiser for new child preferences in Model Explorer.
 *
 * @author Gabriel Pascual
 *
 */
public class NewChildPreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * Constructor.
	 *
	 */
	public NewChildPreferenceInitializer() {
		super();
	}

	/**
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 *
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(NewChildPreferences.DEFAULT_SELECTION, true);
		store.setDefault(NewChildPreferences.DEFAULT_EDITION, true);
	}

}
