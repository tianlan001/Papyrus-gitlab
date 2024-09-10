/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr
 *  Thibault Landre (Atos Origin)
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.preferences.ui;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.ui.preferences.AbstractPreferenceGroup;
import org.eclipse.swt.widgets.Composite;

/**
 * The Class AbstractGroupComposite.
 */
public abstract class AbstractGroup extends AbstractPreferenceGroup {

	/**
	 * Instantiates a new abstract group.
	 *
	 * @param parent
	 *            the parent of the composite
	 * @param String
	 *            the title of the page
	 * @param dialogPage
	 *            to set the page in field editor
	 */
	public AbstractGroup(Composite parent, String key, DialogPage dialogPage) {
		super(parent, key, dialogPage);
	}

	/**
	 * Get the specified preference type associated with this preference page.
	 *
	 * @param preferenceType
	 *            an int representing the preference type to retrieve. It must be a value defined in {@link PreferencesConstantsHelper}
	 *
	 * @return the preference constant used to store the given preference type.
	 */
	protected String getPreferenceConstant(int preferenceType) {
		return PreferencesConstantsHelper.getElementConstant(getKey(), preferenceType);
	}

	/**
	 * Gets an encapsulated compo. This composite is used to contain a FieldEditor and to allow
	 * developers to work with a FieldEditor like Composite element.
	 *
	 * @param parent
	 *            the parent
	 *
	 * @return the encapsulated compo
	 * 
	 * @deprecated Use the {@link AbstractPreferenceGroup#getEncapsulatedComposite(Composite)} API, instead
	 */
	@Deprecated
	protected final Composite getEncapsulatedCompo(Composite parent) {
		return getEncapsulatedComposite(parent);
	}

}
