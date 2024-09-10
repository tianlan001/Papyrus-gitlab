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
package org.eclipse.papyrus.infra.gmfdiag.preferences.pages;

import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.FontGroup;
import org.eclipse.papyrus.infra.ui.preferences.AbstractPapyrusPreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

/**
 * An abstract implementation of a Papyrus Preference Page.
 * <p>
 * This class create the default editorFields used in a Papyrus preference page.
 * </p>
 * <ul>
 * The following editor fields are initialized :
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.preferences.jface.preference.ColorFieldEditor} <em>ColorFieldEditor</em></li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.preferences.jface.preference.FontFieldEditor} <em>FontFieldEditor</em></li>
 * </ul>
 *
 * @author tlandre
 */
public abstract class AbstractPapyrusElementPreferencePage extends AbstractPapyrusPreferencePage {


	protected Layout getToolbarLayout() {
		return new GridLayout(2, false);
	}

	/**
	 * create a local composite
	 *
	 * @param parent
	 * @return
	 */
	protected Composite getEncapsulatedCompo(Composite parent) {
		Composite compo = new Composite(parent, SWT.NONE);
		compo.setLayout(new GridLayout());
		return compo;
	}

	@Override
	protected void createPageContents(Composite parent) {
		FontGroup fontGroupComposite = new FontGroup(parent, getPreferenceKey(), this);
		addPreferenceGroup(fontGroupComposite);

	}


	/**
	 * Get the specified preference type associated with this preference page.
	 *
	 * @param preferenceType
	 *            an int representing the preference type to retrieve. It must be a value defined in {@link PreferencesConstantsHelper}
	 * @return the preference constant used to store the given prefence type.
	 */
	protected String getPreferenceConstant(int preferenceType) {
		return PreferencesConstantsHelper.getElementConstant(getTitle(), preferenceType);
	}

}
