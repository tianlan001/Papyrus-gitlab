/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
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
 *  Emilien Perico (Atos Origin) emilien.perico@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.resourceloading.internal.ui.preferences;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.services.resourceloading.Activator;
import org.eclipse.papyrus.infra.services.resourceloading.internal.ui.UIPlugin;
import org.eclipse.papyrus.infra.services.resourceloading.internal.ui.strategy.StrategyChooser;
import org.eclipse.papyrus.infra.ui.preferences.AbstractPapyrusPreferencePage;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

public class LoadingStrategyPreferencePage extends AbstractPapyrusPreferencePage {

	private LoadinStrategyGroup groupComposite;

	private IProject project;

	/**
	 * @generated
	 */
	@Override
	protected String getBundleId() {
		return UIPlugin.PLUGIN_ID;
	}

	/**
	 * @generated
	 */
	public static void initDefaults(IPreferenceStore store) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createPageContents(Composite parent) {
		groupComposite = new LoadinStrategyGroup(parent, getTitle(), this);
		addPreferenceGroup(groupComposite);
		LoadedAuthorizedResourceGroup loadedAuthorizedResourceGroup = new LoadedAuthorizedResourceGroup(parent, getTitle(), this);
		addPreferenceGroup(loadedAuthorizedResourceGroup);
		// TODO : if no value is set for the radio buttons (loading strategy) on
		// a project, use the workspace loading strategy.
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean performOk() {
		boolean result = super.performOk();
		StrategyChooser.setCurrentStrategy(doGetPreferenceStore().getInt(ICorePreferenceConstants.PREF_CORE_DEFINE_LOADING_STRATEGY));
		IPreferenceStore modifiedPrefStore = doGetPreferenceStore();
		try {
			if (modifiedPrefStore instanceof ScopedPreferenceStore) {
				((ScopedPreferenceStore) modifiedPrefStore).save();
			}
		} catch (Exception e) {
			Activator.logError(e);
		}

		return result;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#getElement()
	 */
	@Override
	public IAdaptable getElement() {
		return project;
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchPropertyPage#setElement(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	public void setElement(IAdaptable element) {
		project = (IProject) element.getAdapter(IResource.class);
	}
}
