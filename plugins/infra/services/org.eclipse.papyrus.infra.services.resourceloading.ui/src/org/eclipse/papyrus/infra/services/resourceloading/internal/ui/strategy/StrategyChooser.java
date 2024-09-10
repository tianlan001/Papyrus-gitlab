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
package org.eclipse.papyrus.infra.services.resourceloading.internal.ui.strategy;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.services.resourceloading.IStrategyChooser;
import org.eclipse.papyrus.infra.services.resourceloading.internal.ui.UIPlugin;
import org.eclipse.papyrus.infra.services.resourceloading.internal.ui.preferences.ICorePreferenceConstants;


/**
 * The Class StrategyChooser.
 */
public class StrategyChooser implements IStrategyChooser {

	/** The current strategy. */
	private static Integer currentStrategy = -1;

	/**
	 * Instantiates a new strategy chooser.
	 */
	public StrategyChooser() {
	}

	/**
	 * @see org.eclipse.papyrus.infra.services.resourceloading.IStrategyChooser#getCurrentStrategy()
	 */
	public int getCurrentStrategy() {
		if (currentStrategy == -1) {
			// set the current strategy at the first time
			currentStrategy = UIPlugin.getDefault().getPreferenceStore().getInt(ICorePreferenceConstants.PREF_CORE_DEFINE_LOADING_STRATEGY);
		}
		return currentStrategy;
	}

	/**
	 * Sets the current strategy.
	 *
	 * @param strategy
	 *            the new current strategy ID
	 */
	public static void setCurrentStrategy(int strategy) {
		currentStrategy = strategy;
		IPreferenceStore store = UIPlugin.getDefault().getPreferenceStore();
		if (store.getInt(ICorePreferenceConstants.PREF_CORE_DEFINE_LOADING_STRATEGY) != strategy) {
			store.setValue(ICorePreferenceConstants.PREF_CORE_DEFINE_LOADING_STRATEGY, String.valueOf(strategy));
		}
	}

	public boolean setStrategy(int strategy) {
		if (currentStrategy != strategy) {
			setCurrentStrategy(strategy);
		}

		return true;
	}

}
