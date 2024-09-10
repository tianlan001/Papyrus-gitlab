/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *   Vincent LORENZO (CEA LIST) <vincent.lorenzo@cea.fr> - Bug 576651
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.emf.internal.facet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.IDialogSettingsProvider;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationCatalogManager;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationCatalogManagerFactory;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.core.internal.CustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;
import org.eclipse.papyrus.infra.emf.CustomizationComparator;
import org.eclipse.papyrus.infra.ui.internal.emf.Activator;
import org.eclipse.ui.PlatformUI;

/**
 * This class manages the applied customizations (apply/reset) and is able to save user changes with a workspace preferences
 *
 */
public class WorskpaceCustomizationUpdater implements ICustomizationManagerUpdater {

	/**
	 * The managed {@link CustomizationManager}
	 */
	private ICustomizationManager customizationManager;

	/**
	 * Preference to enforce loaded facet by default
	 */
	protected static final String DEFAULT_LOADED_FACET = "defaultLoadedFacet"; //$NON-NLS-1$

	/**
	 * Separator for DEFAULT_LOADED_FACET
	 */
	protected static final String SEPARATOR_DEFAULT_LOADED_FACET = ","; //$NON-NLS-1$

	/**
	 * The section name of the Dialog Settings for storing Customization Manager settings
	 */
	protected static final String CUSTOMIZATION_MANAGER_SECTION = Activator.PLUGIN_ID + ".customizationManager";//$NON-NLS-1$

	/**
	 * Key to store the facet order
	 */
	protected static final String LOADED_FACET_ORDER = Activator.PLUGIN_ID + ".facet.order"; //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 */
	public WorskpaceCustomizationUpdater() {
		this.customizationManager = org.eclipse.papyrus.infra.emf.Activator.getDefault().getCustomizationManager();
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.ui.emf.internal.facet.ICustomizationManagerUpdater#applyCustomizations()
	 *
	 */
	@Override
	public void applyCustomizations() {
		if (hasCustomizationPreferences()) {
			applyCustomizationFromUserPreferences();
		} else {
			applyDefaultLoadedCustomizationCatalog();
		}
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if a user preference is saved
	 */
	protected final boolean hasCustomizationPreferences() {
		return getBrowserCustomizationDialogSettings() != null;
	}

	/**
	 *
	 * @return
	 *         the {@link IDialogSettings} in which the user preference is saved if it exists, <code>null</code> otherwise
	 */
	protected IDialogSettings getBrowserCustomizationDialogSettings() {
		return getWorkspaceBrowserCustomizationDialogSettings();
	}

	/**
	 *
	 * @return
	 *         the dialog setting used to save the preferences or <code>null</code> if not found
	 */
	protected final IDialogSettings getWorkspaceBrowserCustomizationDialogSettings() {
		return getDialogSettingProvider().getDialogSettings().getSection(CUSTOMIZATION_MANAGER_SECTION);
	}

	/**
	 *
	 * @return
	 *         the {@link IDialogSettings} to user to save the user preference and create it if required
	 */
	protected IDialogSettings getOrCreateBrowserCustomizationDialogSettings() {
		return getOrCreateWorkspaceBrowserCustomizationDialogSettings();
	}

	/**
	 *
	 * @return
	 *         the dialog setting to used to save the preferences. We create it if it doesn't yet exist
	 */
	protected final IDialogSettings getOrCreateWorkspaceBrowserCustomizationDialogSettings() {
		IDialogSettings settings = getWorkspaceBrowserCustomizationDialogSettings();
		if (settings == null) {
			settings = getDialogSettingProvider().getDialogSettings().addNewSection(CUSTOMIZATION_MANAGER_SECTION);
			String loadedFacetPreferences = Activator.getDefault().getPreferenceStore().getString(DEFAULT_LOADED_FACET);
			if (loadedFacetPreferences != null && !"".equals(loadedFacetPreferences)) { //$NON-NLS-1$
				settings.put(LOADED_FACET_ORDER, loadedFacetPreferences.split(SEPARATOR_DEFAULT_LOADED_FACET));
			}
		}
		return settings;
	}

	/**
	 * This methods apply the customization given in parameters to the {@link ICustomizationManager}
	 *
	 * @param customizationsToApply
	 *            the list of customization to apply
	 */
	@Override
	public void applyCustomizations(final List<Customization> customizationsToApply) {
		if (this.customizationManager != null) {
			if (this.customizationManager.getManagedCustomizations().size() > 0) {
				this.customizationManager.getManagedCustomizations().clear();
			}
			this.customizationManager.getManagedCustomizations().addAll(customizationsToApply);
		}
	}

	/**
	 * This method applies the Customization with {@link Customization#isMustBeLoadedByDefault()} is <code>true</code>
	 */
	protected final void applyDefaultLoadedCustomizationCatalog() {
		final ICustomizationCatalogManager customCatalog = ICustomizationCatalogManagerFactory.DEFAULT.getOrCreateCustomizationCatalogManager(customizationManager.getResourceSet());
		List<Customization> registryAllCustomizations = customCatalog.getRegisteredCustomizations();
		List<Customization> orderedCustomizationList = new ArrayList<>();
		for (Customization customization : registryAllCustomizations) {
			if (customization.isMustBeLoadedByDefault()) {
				orderedCustomizationList.add(customization);
			}
		}
		Collections.sort(orderedCustomizationList, new CustomizationComparator());
		applyCustomizations(orderedCustomizationList);
	}

	/**
	 * Apply the Customizations from the user preferences
	 */
	protected final void applyCustomizationFromUserPreferences() {
		// the appearance can be customized here:
		final IDialogSettings settings = getBrowserCustomizationDialogSettings();
		try {
			// load customizations defined as default through the customization
			// extension
			ICustomizationCatalogManager customCatalog = ICustomizationCatalogManagerFactory.DEFAULT.getOrCreateCustomizationCatalogManager(customizationManager.getResourceSet());
			// no possibility to get default customization
			List<Customization> registryAllCustomizations = customCatalog.getRegisteredCustomizations();
			List<Customization> orderedCustomizationList = new ArrayList<>();
			for (Customization customization : registryAllCustomizations) {
				final String settingKey = getSettingKey(customization);
				if (settings.getBoolean(settingKey)) {
					orderedCustomizationList.add(customization);
				}
			}
			String[] loadedFacetOrder = settings.getArray(LOADED_FACET_ORDER);
			if (loadedFacetOrder != null && loadedFacetOrder.length > 0) {
				Map<String, Customization> mapProp = registryAllCustomizations.stream().collect(
						Collectors.toMap(e -> getSettingKey(e), e -> e));
				Stream<String> stream = Arrays.stream(loadedFacetOrder);
				Stream<Customization> map = stream.map(id -> mapProp.get(id.trim()));
				orderedCustomizationList = map.collect(Collectors.toList());
			} else {
				Collections.sort(orderedCustomizationList, new CustomizationComparator());
			}
			applyCustomizations(orderedCustomizationList);
		} catch (Throwable e) {
			Activator.log.log(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error applying customization from the preferences", e)); //$NON-NLS-1$
		}
	}


	/**
	 * @see org.eclipse.papyrus.infra.ui.emf.internal.facet.ICustomizationManagerUpdater#resetToDefaultCustomizations()
	 *
	 */
	@Override
	public void resetToDefaultCustomizations() {
		destroyUserPreferences();
		applyCustomizations();
	}

	/**
	 * Restores the default Customization Manager configuration
	 */
	protected void destroyUserPreferences() {
		final IDialogSettings settings = getBrowserCustomizationDialogSettings();
		if (settings instanceof DialogSettings) {
			((DialogSettings) settings).removeSection(CUSTOMIZATION_MANAGER_SECTION);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.emf.internal.facet.ICustomizationManagerUpdater#saveUserCustomizationsState()
	 *
	 */
	@Override
	public void saveUserCustomizationsState() {
		IDialogSettings dialogSettings = getOrCreateBrowserCustomizationDialogSettings();
		List<Customization> appliedCustomizations = this.customizationManager.getManagedCustomizations();
		final List<Customization> registeredCustomizations = ICustomizationCatalogManagerFactory.DEFAULT.getOrCreateCustomizationCatalogManager(this.customizationManager.getResourceSet()).getRegisteredCustomizations();
		for (Customization customization : registeredCustomizations) {
			boolean isApplied = appliedCustomizations.contains(customization);
			String settingKey = getSettingKey(customization);
			dialogSettings.put(settingKey, isApplied);
		}

		// We have to use a temporary list since CustomizationsDelegatingList is buggy (missing a lot of methods)
		List<Customization> customizationList = new ArrayList<>();
		customizationList.addAll(appliedCustomizations);
		String[] loadedCustomizationArray = customizationList.stream().map(customization -> customization.eResource().getURI().toString()).toArray(size -> new String[size]);
		dialogSettings.put(LOADED_FACET_ORDER, loadedCustomizationArray);

		// not required, Eclipse save itself the IDialogSettings when we close it
		// just here for debug and information purpose
		// getDialogSettingProvider().saveDialogSettings();
	}

	/**
	 * Returns the dialog setting provider
	 */
	protected final IDialogSettingsProvider getDialogSettingProvider() {
		return PlatformUI.getDialogSettingsProvider(Activator.getDefault().getBundle());
	}

	/**
	 *
	 * @param customization
	 *            the customization
	 * @return
	 *         the key used to save the customization
	 */
	protected final String getSettingKey(final Customization customization) {
		return customization.eResource().getURI().toString();
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.emf.internal.facet.ICustomizationManagerUpdater#getCustomizationManager()
	 *
	 * @return
	 */
	@Override
	public ICustomizationManager getCustomizationManager() {
		return this.customizationManager;
	}

}
