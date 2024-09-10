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
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - Bug 576651
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.emf.internal.facet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.emf.facet.architecture.api.CustomizationFacetFactory;
import org.eclipse.papyrus.emf.facet.architecture.api.ICustomizationReferenceMerger;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationCatalogManager;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationCatalogManagerFactory;
import org.eclipse.papyrus.emf.facet.custom.core.ICustomizationManager;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;
import org.eclipse.papyrus.infra.architecture.ArchitectureDescriptionUtils;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.TreeViewerConfiguration;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.services.spi.IContextualServiceRegistryTracker;
import org.eclipse.papyrus.infra.ui.editor.IMultiDiagramEditor;
import org.eclipse.papyrus.infra.ui.emf.internal.listeners.ArchitectureDescriptionLanguageListener;
import org.eclipse.papyrus.infra.ui.internal.emf.Activator;
import org.eclipse.papyrus.infra.ui.util.EditorHelper;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IPartService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * This class manages the applied customizations using the ArchitectureFramework of the edited model
 * in addition, this class is able to save changes about applied customization for each user model instead of managed them for the whole workspace
 * That is to say this class replaces the workspace preference of the super class by a model preference.
 *
 * This class register an {@link IPartListener} on the Eclipse {@link IPartService} to be able to update the list of applied customization on each Papyrus editor changes
 * In order to be able to detect architecture framework switch for a given model, we also create the {@link ArchitectureDescriptionLanguageListener}
 * (declared with an extension point and using this class)
 *
 *
 */
public class ArchitectureFrameworkCustomizationManagerUpdater extends WorskpaceCustomizationUpdater {

	/**
	 * This class is a singleton
	 */
	public static final ArchitectureFrameworkCustomizationManagerUpdater INSTANCE = new ArchitectureFrameworkCustomizationManagerUpdater();

	/**
	 * listener in charge to update the customizations to apply on each Papyrus editor switch
	 */
	private final IPartListener papyrusEditorListener;

	/**
	 * The map of all registered customization, identified by their URI
	 */
	private final Map<String, Customization> CUSTOMIZATION_MAP;

	/**
	 *
	 * Constructor.
	 *
	 */
	private ArchitectureFrameworkCustomizationManagerUpdater() {
		super();
		this.papyrusEditorListener = new PapyrusEditorListener();
		registerPapyrusEditorListener();
		CUSTOMIZATION_MAP = new HashMap<>();
		initMap();
	}

	/**
	 * This method register the listener on the Eclipse PartService, to be notified w
	 */
	private void registerPapyrusEditorListener() {
		final IWorkbenchWindow ww = EditorHelper.getActiveWindow();
		if (ww != null && ww.getPartService() != null) {
			ww.getPartService().addPartListener(this.papyrusEditorListener);
		} else {
			Activator.log.warn("We are not able to get the PartService to register the papyrus editor listener"); //$NON-NLS-1$
		}
	}

	private void initMap() {
		final ICustomizationManager customizationManager = Activator.getDefault().getCustomizationManager();
		final ICustomizationCatalogManager customCatalog = ICustomizationCatalogManagerFactory.DEFAULT.getOrCreateCustomizationCatalogManager(customizationManager.getResourceSet());
		for (final Customization current : customCatalog.getRegisteredCustomizations()) {
			// we use the name and not the URI, to avoid trouble between platform:/plugins and platform:/resources
			CUSTOMIZATION_MAP.put(current.getName(), current);
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.ui.emf.internal.facet.WorskpaceCustomizationUpdater#applyCustomizations()
	 *
	 */
	@Override
	public void applyCustomizations() {
		if (getEditedModelSet() != null) {
			if (hasCustomizationPreferences()) {
				applyCustomizationFromUserPreferences();
			} else {
				List<Customization> customizationsToApply;
				try {
					customizationsToApply = getCustomizationsFromArchitectureFramework();
				} catch (BadTreeViewerConfigurationException e) {
					Activator.log.error(e.getMessage(), e);
					applyCustomizations(e.getIntermediateResult());
					return;
				}
				if (customizationsToApply.size() > 0) {
					applyCustomizations(customizationsToApply);
				} else {
					super.applyCustomizations();
				}
			}
		}
	}


	/**
	 * @see org.eclipse.papyrus.infra.ui.emf.internal.facet.WorskpaceCustomizationUpdater#getBrowserCustomizationDialogSettings()
	 *
	 * @return
	 */
	@Override
	protected IDialogSettings getBrowserCustomizationDialogSettings() {
		return getModelSetBrowserCustomizationDialogSettings();
	}

	/**
	 *
	 * @return
	 *         the dialog setting used to save the preference associated with the current {@link ModelSet} or <code>null</code> if not found
	 */
	protected final IDialogSettings getModelSetBrowserCustomizationDialogSettings() {
		final IDialogSettings settings = super.getWorkspaceBrowserCustomizationDialogSettings();
		if (settings != null) {
			final ModelSet modelSet = getEditedModelSet();
			return settings.getSection(modelSet.getURIWithoutExtension().toString());
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.emf.internal.facet.WorskpaceCustomizationUpdater#getOrCreateBrowserCustomizationDialogSettings()
	 *
	 * @return
	 */
	@Override
	protected IDialogSettings getOrCreateBrowserCustomizationDialogSettings() {
		return getOrCreateModelSetBrowserCustomizationDialogSettings();
	}

	/**
	 *
	 * @return
	 *         the dialog settings used to save preference associated to the current {@link ModelSet}, it will be created if it doesn't yet exists
	 */
	protected final IDialogSettings getOrCreateModelSetBrowserCustomizationDialogSettings() {
		IDialogSettings settings = getOrCreateWorkspaceBrowserCustomizationDialogSettings();
		final ModelSet modelSet = getEditedModelSet();
		final String modelKey = modelSet.getURIWithoutExtension().toString();
		if (settings.getSection(modelKey) == null) {
			settings = settings.addNewSection(modelKey);
			String loadedFacetPreferences = Activator.getDefault().getPreferenceStore().getString(DEFAULT_LOADED_FACET);
			if (loadedFacetPreferences != null && !"".equals(loadedFacetPreferences)) { //$NON-NLS-1$
				settings.put(LOADED_FACET_ORDER, loadedFacetPreferences.split(SEPARATOR_DEFAULT_LOADED_FACET));
			}
		} else {
			settings = settings.getSection(modelKey);
		}
		return settings;
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.emf.internal.facet.WorskpaceCustomizationUpdater#saveUserCustomizationsState()
	 *
	 */
	@Override
	public void saveUserCustomizationsState() {
		if (getEditedModelSet() != null) {
			super.saveUserCustomizationsState();
		} else {
			Activator.log.warn("There is no active editor, the applied Customizations won't be saved"); //$NON-NLS-1$
		}
	}

	/**
	 *
	 * @return
	 *         the list of {@link EMFFacetTreeViewerConfiguration} defined in the Architecture model associated to the edited model
	 */
	private final Collection<EMFFacetTreeViewerConfiguration> getEMFFacetConfiguration() {
		return getMergedTreeViewerConfiguration()
				.stream()
				.filter(EMFFacetTreeViewerConfiguration.class::isInstance)
				.map(EMFFacetTreeViewerConfiguration.class::cast)
				.collect(Collectors.toList());
	}


	/**
	 *
	 * @return
	 *         the list of merged customization
	 * @throws BadTreeViewerConfigurationException
	 *             this exception is thrown when the {@link EMFFacetTreeViewerConfiguration} defined in the architecture file is invalid
	 */
	private final List<Customization> getCustomizationsFromArchitectureFramework() throws BadTreeViewerConfigurationException {
		final Collection<EMFFacetTreeViewerConfiguration> confs = getEMFFacetConfiguration();
		if (confs != null && confs.isEmpty()) {
			return Collections.emptyList();
		}
		final ICustomizationReferenceMerger merger = CustomizationFacetFactory.getCustomizationReferenceMerger(confs);

		// until here, we are working with the Customization loaded in the ModelSet of the model.
		// we need to return the equivalent customization loaded in the ResourceSet of the CustomizationManager
		final boolean isValid = merger.doValidationAndMerge();
		final List<Customization> realResult = new ArrayList<>(merger.getMergedCustomizations().size());
		for (final Customization tmp : merger.getMergedCustomizations()) {
			final Customization equivalentCusto = CUSTOMIZATION_MAP.get(tmp.getName());
			if (equivalentCusto != null) {
				realResult.add(equivalentCusto);
			}
		}


		if (!isValid) {
			StringBuilder builder = new StringBuilder();
			builder.append(NLS.bind("{0} defined in the current {1} are invalid", CustomizationConfigurationPackage.eINSTANCE.getEMFFacetTreeViewerConfiguration().getName(), ArchitecturePackage.eINSTANCE.getArchitectureDescriptionLanguage().getName())); //$NON-NLS-1$
			for (final Entry<EObject, IStatus> current : merger.getStatus().entrySet()) {
				builder.append(NLS.bind("\t\t{0} has the error {1}", current.getKey().toString(), current.getValue().toString())); //$NON-NLS-1$
			}
			throw new BadTreeViewerConfigurationException(builder.toString(), realResult);
		}
		return realResult;
	}

	/**
	 *
	 * @return
	 *         the current {@link ModelSet
	 */
	protected final ModelSet getEditedModelSet() {
		final ServicesRegistry servReg = getServiceRegistry();
		if (servReg != null) {
			try {
				return servReg.getService(ModelSet.class);
			} catch (ServiceException e) {
				// silent exception (no active editor
			}
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         the current active {@link ServicesRegistry}
	 */
	protected final ServicesRegistry getServiceRegistry() {
		final IContextualServiceRegistryTracker tracker = org.eclipse.papyrus.infra.core.Activator.getDefault().getContextualServiceRegistryTracker();
		if (tracker != null) {
			return tracker.getServiceRegistry();
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.ui.emf.internal.facet.WorskpaceCustomizationUpdater#destroyUserPreferences()
	 *
	 */
	@Override
	protected void destroyUserPreferences() {
		final ModelSet modelSet = getEditedModelSet();
		if (modelSet == null) {
			Activator.log.warn("There is no active editor, the applied Customizations won't be reset"); //$NON-NLS-1$
		}
		// we call the super method to get the parent setting, to be able to destroy the child setting referencing
		// the preference for the current Modelset
		DialogSettings settings = (DialogSettings) super.getBrowserCustomizationDialogSettings();
		if (settings != null) {
			settings.removeSection(modelSet.getURIWithoutExtension().toString());
		}
	}

	protected final Collection<TreeViewerConfiguration> getMergedTreeViewerConfiguration() {
		final ModelSet modelSet = getEditedModelSet();
		if (modelSet != null) {
			final ArchitectureDescriptionUtils adUtils = new ArchitectureDescriptionUtils(modelSet);
			final MergedArchitectureContext ctx = adUtils.getArchitectureContext();
			if (ctx instanceof MergedArchitectureDescriptionLanguage) {
				return ((MergedArchitectureDescriptionLanguage) ctx).getTreeViewerConfigurations();
			}
		}
		return Collections.emptyList();
	}

	/**
	 * This listener is notified when an editor status changes (open/close/focus).
	 * We does something when event comes from {@link IMultiDiagramEditor};
	 *
	 */
	private final class PapyrusEditorListener implements IPartListener {

		/**
		 * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
		 *
		 * @param part
		 */
		@Override
		public void partActivated(IWorkbenchPart part) {
			if (part instanceof IMultiDiagramEditor) {
				ArchitectureFrameworkCustomizationManagerUpdater.INSTANCE.applyCustomizations();
			}
		}

		/**
		 * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
		 *
		 * @param part
		 */
		@Override
		public void partBroughtToTop(IWorkbenchPart part) {
			// nothing to do
		}

		/**
		 * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
		 *
		 * @param part
		 */
		@Override
		public void partClosed(IWorkbenchPart part) {
			// nothing to do
		}

		/**
		 * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
		 *
		 * @param part
		 */
		@Override
		public void partDeactivated(IWorkbenchPart part) {
			// nothing to do
		}

		/**
		 * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
		 *
		 * @param part
		 */
		@Override
		public void partOpened(IWorkbenchPart part) {
			// nothing to do
		}
	}
}
