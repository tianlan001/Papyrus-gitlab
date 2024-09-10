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
package org.eclipse.papyrus.infra.gmfdiag.preferences;

import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.dialog.ApplyValueOnPreferenceKeyDialog;
import org.eclipse.papyrus.infra.ui.preferences.AbstractPapyrusPreferenceStore;
import org.eclipse.papyrus.infra.ui.preferences.dialog.AbstractApplyValueOnPreferenceKeyDialog;

/**
 * this class is a specific store that is used to look for value of element
 * by taking account the structure of preferences : papyrus editor-> Diagram -> Elements of Diagram
 *
 */
public class PapyrusPreferenceStore extends AbstractPapyrusPreferenceStore {

	/**
	 *
	 * Constructor.
	 *
	 * @param context
	 * @param qualifier
	 * @param defaultQualifierPath
	 */
	public PapyrusPreferenceStore(IScopeContext context, String qualifier, String defaultQualifierPath) {
		super(context, qualifier, defaultQualifierPath, PreferencesConstantsHelper.PAPYRUS_EDITOR_PREFERENCE_PREFIX, PreferencesConstantsHelper.DIAGRAM_PREFERENCE_PREFIX, PreferencesConstantsHelper.DIAGRAM_ELEMENT);
	}


	/**
	 *
	 * Constructor.
	 *
	 * @param context
	 * @param qualifier
	 */
	public PapyrusPreferenceStore(IScopeContext context, String qualifier) {
		super(context, qualifier, PreferencesConstantsHelper.PAPYRUS_EDITOR_PREFERENCE_PREFIX, PreferencesConstantsHelper.DIAGRAM_PREFERENCE_PREFIX, PreferencesConstantsHelper.DIAGRAM_ELEMENT);
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.ui.preferences.AbstractPapyrusPreferenceStore#createPreferenceKeyDialog(java.lang.String[])
	 *
	 * @param keys
	 * @return
	 */
	@Override
	protected AbstractApplyValueOnPreferenceKeyDialog createPreferenceKeyDialog(String[] keys) {
		return new ApplyValueOnPreferenceKeyDialog(keys);
	}





}
