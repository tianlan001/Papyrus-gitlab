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
package org.eclipse.papyrus.uml.diagram.common.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * Rich text preferences page to choose basic or advanced renderer
 * 
 * @deprecated since 3.0. Use {@link org.eclipse.papyrus.infra.ui.preferences.RichtextPreferencePage} instead.
 */
@Deprecated
public class RichtextPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public static final String USE_HTML_RENDERER = "useHtmlRenderer";
	public static final String USE_CK_EDITOR = "useCkEditor";

	public RichtextPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription("Rich Text Preferences");
	}

	/**
	 * Creates the field editors.
	 */
	@Override
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(USE_HTML_RENDERER, "Use advanced rich text renderer (needs a reload of opened diagrams). Warning: may raise stability issues.", getFieldEditorParent()));
		addField(new BooleanFieldEditor(USE_CK_EDITOR, "Use advanced rich text editor", getFieldEditorParent()));
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
