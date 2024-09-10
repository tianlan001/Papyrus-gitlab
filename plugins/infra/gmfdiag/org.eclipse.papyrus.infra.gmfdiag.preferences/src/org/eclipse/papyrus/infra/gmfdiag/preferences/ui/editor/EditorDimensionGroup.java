/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.preferences.ui.editor;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.DimensionGroup;
import org.eclipse.swt.widgets.Composite;

/**
 * The Class EditorDimensionGroup specialized for the editor level
 */
public class EditorDimensionGroup extends DimensionGroup {

	/**
	 * Instantiates a new editor dimension group.
	 *
	 * @param parent
	 *            the parent composite
	 * @param key
	 *            the key
	 * @param dialogPage
	 *            the dialog page
	 */
	public EditorDimensionGroup(Composite parent, String key, DialogPage dialogPage) {
		super(parent, key, dialogPage);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.preferences.ui.AbstractGroup#getPreferenceConstant(int)
	 */
	@Override
	protected String getPreferenceConstant(int preferenceType) {
		return PreferencesConstantsHelper.getPapyrusEditorConstant(preferenceType);
	}
}
