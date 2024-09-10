/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.preferences.ui.diagram;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.RulersAndGridGroup;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author Vincent Lorenzo
 *
 */
public class DiagramRulersAndGridGroup extends RulersAndGridGroup {

	/**
	 *
	 * Constructor.
	 *
	 * @param parent
	 * @param key
	 * @param dialogPage
	 */
	public DiagramRulersAndGridGroup(Composite parent, String key, DialogPage dialogPage) {
		super(parent, key, dialogPage);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.gmfdiag.preferences.ui.RulersAndGridGroup#getPreferenceConstant(int)
	 *
	 * @param preferenceType
	 * @return
	 */
	@Override
	protected String getPreferenceConstant(int preferenceType) {
		return PreferencesConstantsHelper.getDiagramConstant(getKey(), preferenceType);
	}

}
