/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * Benoit Maggi benoit.maggi@cea.fr - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.preferences.ui;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.common.preferences.PreferencesConstantsHelper;
import org.eclipse.papyrus.infra.gmfdiag.preferences.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * The Class ExternalReferenceGroup contains a combobox to select 
 * external reference strategy
 */
public class ExternalReferenceGroup extends AbstractGroup {

	public static final String EXTERNAL_REFERENCE_NONE = "EXTERNAL_REFERENCE_NONE";	//$NON-NLS-1$
	public static final String EXTERNAL_REFERENCE_CONTAINER = "EXTERNAL_REFERENCE_CONTAINER"; //$NON-NLS-1$
	public static final String EXTERNAL_REFERENCE_OWNER = "EXTERNAL_REFERENCE_OWNER"; //$NON-NLS-1$
	
	/**
	 * Constructor.
	 *
	 * @param parent
	 * @param key
	 * @param dialogPage
	 */
	public ExternalReferenceGroup(final Composite parent, final String key, final DialogPage dialogPage) {
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
		group.setText(Messages.ExternalReferenceGroup_Title);

		final GridLayout gridLayout = new GridLayout(1, false);
		final GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 1;

		String papyrusEditorConstant = PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.EXTERNAL_REFERENCE_STRATEGY);
		String[][] options = {{Messages.ExternalReferenceGroup_NoReferences,  EXTERNAL_REFERENCE_NONE},
				{Messages.ExternalReferenceGroup_DifferentContainer, EXTERNAL_REFERENCE_CONTAINER},
				{Messages.ExternalReferenceGroup_DifferentOwner, EXTERNAL_REFERENCE_OWNER}};
		final ComboFieldEditor restoreElement = new ComboFieldEditor(papyrusEditorConstant, Messages.ExternalReferenceGroup_ComboTitle, options, group);
		addFieldEditor(restoreElement);
		group.setLayoutData(gridData);
		group.setLayout(gridLayout);
	}


	/**
	 * Initializes the default values of the group.
	 */
	public static void initDefaults(IPreferenceStore store) {
		store.setDefault(PreferencesConstantsHelper.getPapyrusEditorConstant(PreferencesConstantsHelper.EXTERNAL_REFERENCE_STRATEGY), EXTERNAL_REFERENCE_CONTAINER);
	}
}
