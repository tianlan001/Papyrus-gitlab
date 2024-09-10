/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Fanch Bonnabesse (ALL4TEC) fanch.bonnabesse@alltec.net - Initial API and implementation, Bug 419357
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.preferences.ui;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.preferences.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * The Class RestoreElementGroup contains a boolean field editor to always create a new link.
 */
public class RestoreElementGroup extends AbstractGroup {

	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param key
	 * @param dialogPage
	 */
	public RestoreElementGroup(final Composite parent, final String key, final DialogPage dialogPage) {
		super(parent, key, dialogPage);
		createContent(parent);
	}

	/**
	 * Create the content of the group.
	 * 
	 * @param parent
	 */
	private void createContent(final Composite parent) {
		// Create a Group to hold the restore element fields
		final Group group = new Group(parent, SWT.NONE);
		group.setText(Messages.RestoreElementGroup_RestoreElementLink);

		final GridLayout gridLayout = new GridLayout(1, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;


		// Add the fields to the group
		final BooleanFieldEditor restoreElement = new BooleanFieldEditor(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.RESTORE_LINK_ELEMENT), Messages.RestoreElementGroup_AlwaysCreateANewLink, group);
		addFieldEditor(restoreElement);

		group.setLayoutData(gridData);
		group.setLayout(gridLayout);
	}


	/**
	 * Initializes the default values of the group.
	 */
	public static void initDefaults(IPreferenceStore store) {
		store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.RESTORE_LINK_ELEMENT), false);
	}
}
