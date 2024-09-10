/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr
 *  Thibault Landre (Atos Origin)
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.preferences;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * The Class AbstractPreferenceGroup.
 * 
 * @since 1.2
 */
public abstract class AbstractPreferenceGroup extends Composite {

	/** The key to find preference */
	private String key;

	/**
	 * The fieldsEditor : a set that will contain all editor in the composite. It is in charge of
	 * loading / storing / setting the preference store / loading default of all its contained field
	 * editor
	 */
	private Set<FieldEditor> fieldsEditor;

	/**
	 * Gets the dialog page.
	 *
	 * @return the dialogPage
	 */
	protected DialogPage getDialogPage() {
		return dialogPage;
	}

	/** The dialog page. */
	protected DialogPage dialogPage;

	/**
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getKey() {
		return key;
	}

	/**
	 * Sets the title.
	 *
	 * @param title
	 *            the title to set
	 */
	protected void setKey(String title) {
		this.key = title;
	}

	/**
	 * Instantiates a new abstract group.
	 *
	 * @param parent
	 *            the parent of the composite
	 * @param String
	 *            the title of the page
	 * @param dialogPage
	 *            to set the page in field editor
	 */
	public AbstractPreferenceGroup(Composite parent, String key, DialogPage dialogPage) {
		super(parent, SWT.None);
		this.key = key;
		this.dialogPage = dialogPage;
		this.setLayout(new GridLayout());
		fieldsEditor = new HashSet<FieldEditor>();
	}

	/**
	 * Gets an encapsulated composite. This composite is used to contain a FieldEditor and to allow
	 * developers to work with a FieldEditor like Composite element.
	 *
	 * @param parent
	 *            the parent
	 *
	 * @return the encapsulated compo
	 */
	protected final Composite getEncapsulatedComposite(Composite parent) {
		Composite compo = new Composite(parent, SWT.NONE);
		compo.setLayout(new GridLayout());
		return compo;
	}

	/**
	 * Register field editor. It will add the fieldEditor to a map that will be used to
	 * store/load/loadDefault/set the PreferenceStore of contained fieldEditor
	 *
	 * @param fieldEditor
	 *            the fieldEditor to add.
	 */
	protected void addFieldEditor(FieldEditor fieldEditor) {
		fieldsEditor.add(fieldEditor);
	}

	/**
	 * Load preferences of all registered fieldEditors.
	 *
	 * @see org.eclipse.papyrus.infra.AbstractPreferenceGroup.preferences.ui.AbstractGroup#addFieldEditor(FieldEditor)
	 */
	public void load() {
		for (FieldEditor fe : fieldsEditor) {
			fe.load();
		}
	}

	/**
	 * Set the preference store of all registered fieldEditors.
	 *
	 * @see org.eclipse.papyrus.infra.AbstractPreferenceGroup.preferences.ui.AbstractGroup#addFieldEditor(FieldEditor)
	 */
	public final void setPreferenceStore(IPreferenceStore store) {
		for (FieldEditor fe : fieldsEditor) {
			fe.setPreferenceStore(store);
		}
	}

	/**
	 * Load default preferences of all registered fieldEditors.
	 *
	 * @see org.eclipse.papyrus.infra.AbstractPreferenceGroup.preferences.ui.AbstractGroup#addFieldEditor(FieldEditor)
	 */
	public final void loadDefault() {
		for (FieldEditor fe : fieldsEditor) {
			fe.loadDefault();
		}
	}

	/**
	 * Store preferences of the registered fieldEditors.
	 *
	 * @see org.eclipse.papyrus.infra.AbstractPreferenceGroup.preferences.ui.AbstractGroup#addFieldEditor(FieldEditor)
	 */
	public final void storePreferences() {
		for (FieldEditor fe : fieldsEditor) {
			fe.store();
		}
	}

}
