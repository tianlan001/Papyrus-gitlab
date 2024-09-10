/*****************************************************************************
 * Copyright (c) 2010, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.preferences;

import org.eclipse.jface.preference.IPreferencePage;

/**
 * Specialized protocol for preference pages participating in the {@link VisiblePageSingleton}
 * mechanism.
 * 
 * @since 1.2
 */
public interface IPapyrusPreferencePage extends IPreferencePage {
	/**
	 * Requests the page to store all of its preferences in the preference store.
	 */
	void storeAllPreferences();
}
