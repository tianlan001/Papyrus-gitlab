/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.tools.utils.internal.preferences;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.RadioGroupFieldEditor;
import org.eclipse.papyrus.uml.tools.utils.Activator;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;


/**
 * Preferences for UML Named Element Naming Strategy (no index, quick index and unique index)
 * @since 3.3
 */
public class NamedElementNamingStrategyPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {


	/**
	 * the contents of the combo
	 */
	private String[][] fields = new String[][] {
			{ NamedElementIndexNamingStrategyEnum.NO_INDEX_INITIALIZATION.getDescription(), NamedElementIndexNamingStrategyEnum.NO_INDEX_INITIALIZATION.getName() },
			{ NamedElementIndexNamingStrategyEnum.QUICK_INDEX_INITIALIZATION.getDescription(), NamedElementIndexNamingStrategyEnum.QUICK_INDEX_INITIALIZATION.getName() },
			{ NamedElementIndexNamingStrategyEnum.UNIQUE_INDEX_INITIALIZATION.getDescription(), NamedElementIndexNamingStrategyEnum.UNIQUE_INDEX_INITIALIZATION.getName() },
	};

	/**
	 * 
	 * Constructor.
	 *
	 */
	public NamedElementNamingStrategyPreferencePage() {
		super(GRID);
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}



	/**
	 * 
	 * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
	 *
	 */
	@Override
	public void createFieldEditors() {
		addField(new RadioGroupFieldEditor(NameElementNamingStrategyPreferenceInitializer.NAMED_ELEMENT_INDEX_INITIALIZATION, "Name initialization:", 1, this.fields, getFieldEditorParent(), true));
	}

	/**
	 * 
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 *
	 * @param workbench
	 */
	@Override
	public void init(IWorkbench workbench) {
		// nothing to do
	}



}