/*****************************************************************************
 * Copyright (c) 2011 CEA LIST, LIFL.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.internal.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.core.sasheditor.Activator;
import org.eclipse.ui.preferences.ScopedPreferenceStore;


/**
 * An implementation retrieving values from Eclipse preferences
 *
 * @author cedric dumoulin
 *
 */
public class TabTooltipPreferences implements ITabTooltipPreferences {

	/**
	 * Store used to access preferences.
	 */
	protected IPreferenceStore store;

	public TabTooltipPreferences() {
		IPreferenceStore store = createPreferenceStore();
		setPreferenceStore(store);
	}

	/**
	 * Subclasses may implements this method in order to provide the requested preferenceStore.
	 *
	 * @return
	 */
	protected IPreferenceStore createPreferenceStore() {
		IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, Activator.getDefault().getBundle().getSymbolicName());

		return store;
	}


	/**
	 * @return the store
	 */
	public IPreferenceStore getPreferenceStore() {
		return store;
	}


	/**
	 * @param store
	 *            the store to set
	 */
	public void setPreferenceStore(IPreferenceStore store) {
		this.store = store;
	}

	/**
	 * @see org.eclipse.papyrus.examples.preferences.wsprefs.ITabTooltipPreferences#isTooltipEnable()
	 *
	 * @return
	 */
	@Override
	public boolean isTooltipEnable() {

		return store.getBoolean(ITabTooltipPreferences.isTooltipEnable);
	}

	/**
	 * @see org.eclipse.papyrus.examples.preferences.wsprefs.ITabTooltipPreferences#isTooltipForCurrentTabShown()
	 *
	 * @return
	 */
	@Override
	public boolean isTooltipForCurrentTabShown() {
		return store.getBoolean(ITabTooltipPreferences.isTooltipForCurrentTabShown);
	}

	/**
	 * @see org.eclipse.papyrus.examples.preferences.wsprefs.ITabTooltipPreferences#getScaledFactor()
	 *
	 * @return
	 */
	@Override
	public float getScaledFactor() {
		return (store.getInt(ITabTooltipPreferences.scaledFactor) / 100.0f);

	}

	/**
	 * @see org.eclipse.papyrus.examples.preferences.wsprefs.ITabTooltipPreferences#getScaledFactor()
	 *
	 * @return
	 */
	@Override
	public int getIntScaledFactor() {
		return store.getInt(ITabTooltipPreferences.scaledFactor);

	}

	/**
	 * @see org.eclipse.papyrus.examples.preferences.wsprefs.ITabTooltipPreferences#getTooltipAutoCloseDelay()
	 *
	 * @return
	 */
	@Override
	public int getTooltipAutoCloseDelay() {
		return store.getInt(ITabTooltipPreferences.tooltipAutoCloseDelay);
	}

}
