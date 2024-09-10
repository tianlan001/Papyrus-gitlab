/*****************************************************************************
 * Copyright (c) 2016 CEA LIST, ALL4TEC and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.documentation.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.papyrus.views.documentation.Activator;
import org.eclipse.papyrus.views.documentation.messages.Messages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Preference page to define default type of documentation comment.
 */
public class DocumentationViewPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.DocumentationViewPreferencePage_description);

	}

	/**
	 * Creates the field editors.
	 *
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 */
	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(DocumentationViewPreferences.TOOLBAR_INITIAL_EXPANDED, Messages.DocumentationViewPreferencePage_toolbarInitialExpandedLabel, getFieldEditorParent()));
		addField(new RadioGroupFieldEditor(DocumentationViewPreferences.USE_DOCUMENTATION_PROFILE, Messages.DocumentationViewPreferencePage_chooseUseProfile, 1,
				new String[][] {
						{ Messages.DocumentationViewPreferencePage_useFirstCommentChoice, "false" }, //$NON-NLS-1$
						{ Messages.DocumentationViewPreferencePage_useProfileChoice, "true" }, //$NON-NLS-1$
				}, getFieldEditorParent()));
	}
}
