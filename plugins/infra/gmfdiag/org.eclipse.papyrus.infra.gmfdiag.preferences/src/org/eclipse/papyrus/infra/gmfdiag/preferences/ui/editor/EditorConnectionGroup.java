/*****************************************************************************
 * Copyright (c) 2010, 2018 CEA LIST.
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
 *	Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 531729
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.preferences.ui.editor;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.preferences.messages.Messages;
import org.eclipse.papyrus.infra.gmfdiag.preferences.ui.ConnectionGroup;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;

/**
 * The Class EditorConnectionGroup : a connection group specialized for the editor level
 */
public class EditorConnectionGroup extends ConnectionGroup {

	/**
	 * the title of the boolean field editor used to edit the draw common bendpoints label
	 */
	private static final String DRAW_COMMON_BENDPOINTS_EDITOR_LABEL = Messages.EditorConnectionGroup_DrawCommonBendpoint_EditorLabel;

	/**
	 * Instantiates a new editor connection group.
	 *
	 * @param parent
	 *            the parent composite
	 * @param key
	 *            the title
	 * @param dialogPage
	 *            the dialog page
	 */
	public EditorConnectionGroup(Composite parent, String key, DialogPage dialogPage) {
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



	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.preferences.ui.ConnectionGroup#createContent(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createContent(final Composite parent) {
		super.createContent(parent);

		// we are looking for the connection group created in the super class
		Group group = null;
		for (final Control current : parent.getChildren()) {
			if (current instanceof Group && org.eclipse.papyrus.infra.gmfdiag.preferences.Messages.AbstractPapyrusLinkPreferencePage_Connection.equals(((Group) current).getText())) {
				group = (Group) current;
				break;
			}
		}

		// we don't add this group in the super class, because this preference is currently applied on all links and for all diagrams
		if (null != group) {
			Group group1 = new Group(group, SWT.NONE);
			group1.setText(Messages.EditorConnectionGroup_ConnectionBendpoints);
			group1.setLayout(new GridLayout());
			final BooleanFieldEditor drawConnectionBenpoints = new BooleanFieldEditor(getPreferenceConstant(PreferencesConstantsHelper.DRAW_CONNECTION_POINT), DRAW_COMMON_BENDPOINTS_EDITOR_LABEL, group1);
			addFieldEditor(drawConnectionBenpoints);
		}
	}


}
