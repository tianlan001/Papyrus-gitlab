/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.internal.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.ui.Activator;

/**
 * Accessor for the Papyrus Editor preferences.
 */
public class EditorPreferences {
	public static final String PREF_CONVERT_SHARED_LAYOUT = "convertSharedLayout"; //$NON-NLS-1$

	private static final EditorPreferences INSTANCE = new EditorPreferences();
	private final IPreferenceStore store;

	private EditorPreferences() {
		super();

		store = Activator.getDefault().getPreferenceStore();
	}

	public static EditorPreferences getInstance() {
		return INSTANCE;
	}

	/**
	 * Queries whether the user prefers to migrate shared editor layout to private storage
	 * always, never, or interactively pop up a dialog to ask (the default).
	 * 
	 * @return the shared layout conversion on first open preference, which is never
	 *         {@code null} and defaults to {@link YesNo#PROMPT}
	 */
	public YesNo getConvertSharedPageLayoutToPrivate() {
		return YesNo.valueOf(store.getString(PREF_CONVERT_SHARED_LAYOUT));
	}

	/**
	 * Sets whether the editor will always, never, or ask the user to migrate shared
	 * (in the {@code *.di} resource) page layout into the private storage ({@code *.sash} resource}
	 * on the first opening of a Papyrus model in the workspace that uses the shared
	 * storage (usually from pre-1.0 release).
	 * 
	 * @param convert
	 *            the preference setting to assign, or {@code null} for the default, which
	 *            is {@link YesNo#PROMPT}
	 */
	public void setConvertSharedPageLayoutToPrivate(YesNo convert) {
		if (convert == null) {
			convert = YesNo.PROMPT;
		}

		store.setValue(PREF_CONVERT_SHARED_LAYOUT, convert.name());
	}

	//
	// Nested types
	//

	/**
	 * Initializer of defaults for the editor preferences.
	 */
	public static class Initializer extends AbstractPreferenceInitializer {

		public Initializer() {
			super();
		}

		@Override
		public void initializeDefaultPreferences() {
			IPreferenceStore store = Activator.getDefault().getPreferenceStore();

			store.setDefault(PREF_CONVERT_SHARED_LAYOUT, YesNo.PROMPT.name());
		}
	}
}
