/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Benoit Maggi (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.internal.ui.preferences;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.papyrus.uml.service.types.Activator;
import org.eclipse.papyrus.uml.service.types.internal.ui.messages.Messages;
import org.eclipse.papyrus.uml.service.types.preferences.UMLElementTypePreferenceInitializer;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * Preferences for UML element types
 */

public class UMLElementTypePreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public UMLElementTypePreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
		setDescription(Messages.UMLElementTypePreferencePage_PageDescription); 
	}

	@Override
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(UMLElementTypePreferenceInitializer.MOVE_STEREOTYPEAPPLICATION_ELEMENT_IN_SAME_RESOURCE,Messages.UMLElementTypePreferencePage_KeepStereotypeApplicationText, getFieldEditorParent()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
	}

}