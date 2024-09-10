/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 500219 - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.uml.modelexplorer.preferences;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.uml.modelexplorer.Activator;
import org.eclipse.papyrus.uml.tools.profile.definition.LabelStylersEnum;
import org.eclipse.papyrus.uml.tools.profile.definition.LabelTypesEnum;

/**
 * Initializer for UML label customization preferences in Model Explorer.
 * @since 2.0
 */
public class CustomizableLabelPreferenceInitializer extends AbstractPreferenceInitializer {

	/**
	 * Constructor.
	 */
	public CustomizableLabelPreferenceInitializer() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
	 */
	@Override
	public void initializeDefaultPreferences() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		store.setDefault(CustomizableLabelPreferences.CUSTOMIZED_TYPES, LabelTypesEnum.STEREOTYPE.getLiteral() + " " + LabelTypesEnum.LABEL.getLiteral());//$NON-NLS-1$
		store.setDefault(CustomizableLabelPreferences.CUSTOMIZED_STYLES, LabelStylersEnum.DEFAULT.getLiteral() + " " + LabelStylersEnum.DEFAULT.getLiteral());//$NON-NLS-1$
	}

}
