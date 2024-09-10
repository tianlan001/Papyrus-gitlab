/*******************************************************************************
 * Copyright (c) 2010 CEA List.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Shuai Li (CEA List) <shuai.li@cea.fr> - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.infra.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.papyrus.infra.ui.messages.Messages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Rich text preferences page to choose basic or advanced renderer
 * 
 * @since 2.0
 */
public class RichtextPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {
	
	public static final String USE_HTML_RENDERER = "useHtmlRenderer"; //$NON-NLS-1$
	public static final String USE_CK_EDITOR = "useCkEditor"; //$NON-NLS-1$
	
	public RichtextPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.RichtextPreferencePage_Description);
	}

	/**
	 * Creates the field editors.
	 */
	@Override
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(USE_HTML_RENDERER, Messages.RichtextPreferencePage_FirstBooleanEditorName, getFieldEditorParent()));
		addField(new BooleanFieldEditor(USE_CK_EDITOR, Messages.RichtextPreferencePage_SecondBooleanEditorName, getFieldEditorParent()));
	}

	/**
	 * Init.
	 *
	 * @param workbench
	 *            the workbench
	 */
	@Override
	public void init(IWorkbench workbench) {
	}

}
