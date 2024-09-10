/*****************************************************************************
 * Copyright (c) 2011 CEA LIST, LIFL.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.internal.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.preference.ScaleFieldEditor;
import org.eclipse.papyrus.infra.core.sasheditor.Activator;
import org.eclipse.papyrus.infra.core.sasheditor.messages.Messages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * This class represents a preference page that
 * is contributed to the Preferences dialog. By
 * subclassing <samp>FieldEditorPreferencePage</samp>, we
 * can use the field support built into JFace that allows
 * us to create a page that is small and knows how to
 * save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the preference store that belongs to the main plug-in class. That way, preferences can be accessed directly via the preference store.
 */

public class TabTooltipPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	public TabTooltipPreferencePage() {
		super(GRID);

		// IPreferenceStore store = new ScopedPreferenceStore(new InstanceScope(), Activator.getDefault().getBundle().getSymbolicName());
		IPreferenceStore store = createPreferenceStore();
		setPreferenceStore(store);
		setDescription(Messages.TabTooltipPreferencePage_SashWindowsTabsTooltipPreferencePageName);

	}

	/**
	 * Subclasses may implements this method in order to provide the requested preferenceStore.
	 *
	 * @return
	 */
	protected IPreferenceStore createPreferenceStore() {
		IPreferenceStore store = new ScopedPreferenceStore(InstanceScope.INSTANCE, Activator.getDefault().getBundle().getSymbolicName());

		return store;
	}


	/**
	 * Creates the field editors. Field editors are abstractions of
	 * the common GUI blocks needed to manipulate various types
	 * of preferences. Each field editor knows how to save and
	 * restore itself.
	 */
	@Override
	public void createFieldEditors() {
		addField(new BooleanFieldEditor(ITabTooltipPreferences.isTooltipEnable, Messages.TabTooltipPreferencePage_IsTooltipEnable, getFieldEditorParent()));

		addField(new BooleanFieldEditor(ITabTooltipPreferences.isTooltipForCurrentTabShown, Messages.TabTooltipPreferencePage_IsTooltipForCurrentTabShown, getFieldEditorParent()));

		addField(new IntegerFieldEditor(ITabTooltipPreferences.tooltipAutoCloseDelay, Messages.TabTooltipPreferencePage_AutoCloseAndDelay, getFieldEditorParent(), 10));

		addField(new ScaleFieldEditor(ITabTooltipPreferences.scaledFactor, Messages.TabTooltipPreferencePage_TooltipScale, getFieldEditorParent(), 0, 100, 1, 10));

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	@Override
	public void init(IWorkbench workbench) {
	}

}