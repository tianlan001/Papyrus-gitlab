/*****************************************************************************
 * Copyright (c) 2013, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 461629
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.statemachine.custom.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.DiagramHelper;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLDiagramEditorPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * @since 3.1
 */
public class CustomTransitionPreferencePage extends FieldEditorPreferencePage
		implements IWorkbenchPreferencePage {
	public CustomTransitionPreferencePage() {
		super(GRID);
		setPreferenceStore(UMLDiagramEditorPlugin.getInstance().getPreferenceStore());
		setDescription(
				"This preference page allows to customize label appearance on transitions and in entry/exit/do behaviors. " + //$NON-NLS-1$
						"Please note that per diagram or element settings can be done via CSS."); //$NON-NLS-1$
	}

	boolean updatePending;

	/**
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 *
	 */
	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(
				PreferenceConstants.INDICATE_PARAMETERS, "Indicate parameters", //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new BooleanFieldEditor(
				PreferenceConstants.LINEBREAK_BEFORE_EFFECT, "Line break before effect label", //$NON-NLS-1$
				getFieldEditorParent()));

		addField(new IntegerFieldEditor(
				PreferenceConstants.BODY_CUT_LENGTH, "Shown number of lines for opaque expressions/behaviors", //$NON-NLS-1$
				getFieldEditorParent()));

	}

	@Override
	public boolean performOk() {
		if (!updatePending) {
			Display.getDefault().asyncExec(new Runnable() {

				@Override
				public void run() {
					DiagramHelper.forceRefresh();
					updatePending = false;
				}
			});
		}
		updatePending = true;

		return super.performOk();
	}


	/**
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 *
	 * @param workbench
	 */
	@Override
	public void init(IWorkbench workbench) {
	}
}
