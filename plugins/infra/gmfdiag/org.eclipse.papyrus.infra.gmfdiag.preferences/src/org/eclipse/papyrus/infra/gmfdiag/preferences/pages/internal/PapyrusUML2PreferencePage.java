/****************************************************************************
 * Copyright (c) 2008 Atos Origin.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *		Thibault Landre (Atos Origin) - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.preferences.pages.internal;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.preferences.Activator;
import org.eclipse.papyrus.infra.ui.preferences.AbstractPapyrusPreferencePage;
import org.eclipse.swt.widgets.Composite;

/**
 * Represent the main preference page of the Papyrus UML2 editor
 *
 * <p>
 * This class shouldn't be overriden.
 * </p>
 *
 * @author tlandre
 */
public class PapyrusUML2PreferencePage extends AbstractPapyrusPreferencePage {

	@Override
	protected void createPageContents(Composite parent) {
		// Do nothing

	}

	@Override
	protected String getBundleId() {
		return Activator.PLUGIN_ID;
	}

	/**
	 * Initialize the fields editor of this preference page. This method is call during the
	 * initialization of the preference page.
	 *
	 * @param store
	 *            the preference store used
	 */
	public static void initDefaults(IPreferenceStore store) {
		
	}

}
