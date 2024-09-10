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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.preferences;

import org.eclipse.jface.preference.IPreferencePage;

/**
 * This singleton has bee created to manage the button ok and apply of preference page.
 * In the case of button ok pressed, the behavior of eclipse try of apply in the first preference page found.
 * Here each page has a specific behavior. So to store the preference, the active page is called
 * 
 * @since 1.2
 *
 */
public class VisiblePageSingleton {

	private static VisiblePageSingleton instance;

	private IPreferencePage page;

	/**
	 *
	 * @return the instance of the {@link VisiblePageSingleton}
	 */
	public static VisiblePageSingleton getInstance() {
		if (instance == null) {
			instance = new VisiblePageSingleton();
		}
		return instance;
	}

	/**
	 * set the visible page
	 *
	 * @param page
	 *            a {@link IPreferencePage} --> {@link DiagramPreferencePage} or {@link AbstractPapyrusPreferencePage}
	 */
	public void setVisiblePage(IPreferencePage page) {
		this.page = page;
	}

	/**
	 *
	 * @return the Visible Page
	 */
	public IPreferencePage getVisiblePage() {
		return this.page;
	}

	/**
	 * call the visisble page in order to store preferences
	 */
	public void store() {
		if (this.page instanceof IPapyrusPreferencePage) {
			((IPapyrusPreferencePage) (this.page)).storeAllPreferences();
		}
	}
}
