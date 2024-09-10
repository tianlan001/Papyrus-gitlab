/*****************************************************************************
 * Copyright (c) 2022 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent LORENZO (CEA LIST) - vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.services.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.papyrus.views.properties.services.Activator;
import org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider;
import org.eclipse.papyrus.views.properties.services.IPropertySheetPageProviderService;
import org.eclipse.papyrus.views.properties.services.PropertyRendererPreferencesConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertySheetPage;

/**
 *
 * This service is used to get the PropertySheetPage to use according to the user preferences
 *
 */
public class PropertySheetPageProviderService implements IPropertySheetPageProviderService {

	/**
	 * the selected renderer to use
	 */
	private String selectedRenderedID = null;

	/**
	 * Map of the registered {@link IPropertySheetPageProvider}
	 */
	private Map<String, Collection<IPropertySheetPageProvider>> map = new HashMap<>();

	/**
	 * ID of Eclipse Property Sheet View
	 */
	private static final String PROPERTY_SHEET_ID = "org.eclipse.ui.views.PropertySheet"; //$NON-NLS-1$

	private static final String PAPYRUS_EDITOR_ID = "org.eclipse.papyrus.infra.core.papyrusEditor";//$NON-NLS-1$ // dependency is not possible here

	/**
	 * the list of renderers not found (so called before the end of the registration of the renderer)
	 */
	private Collection<String> renderersRequiringReload = new ArrayList<>();

	/**
	 * map of provided
	 */
	private Map<IPropertySheetPage, IPropertySheetPageProvider> mapOfProvidedSheetPage = new HashMap<>();

	/**
	 * @Override
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProviderService#createPropertySheetPage(java.lang.Object, java.lang.String)
	 *
	 * @param realContributor
	 * @param viewID
	 * @return
	 */
	@Override
	public IPropertySheetPage createPropertySheetPage(final Object realContributor, final String viewID) {
		final String selectedRender = getSelectedRenderer();
		IPropertySheetPage returnedPage = null;
		// 1. look for the selected rendered
		if (selectedRender != null) {
			returnedPage = createPropertySheetPage(realContributor, viewID, selectedRender);
		}

		// 2. check if we found the wanted renderer
		if (returnedPage == null) {
			// as we don't found the required renderer with keep it for a future reload
			this.renderersRequiringReload.add(selectedRender);
		}

		// 3. use the first renderer as default one when the wanted one is not found
		if (returnedPage == null) {
			final Iterator<String> iter = getAvailableRenderers().iterator();

			while (returnedPage == null && iter.hasNext()) {
				returnedPage = createPropertySheetPage(realContributor, viewID, iter.next());
			}
		}

		return returnedPage;
	}

	/**
	 *
	 * * @param realContributor
	 * the caller wanting a Property View (the Papyrus editor, or the ModelExplorer for example
	 *
	 * @param viewID
	 *            the id of the caller
	 * @param rendererID
	 *            the renderer to use
	 * @return
	 *         the created {@link IPropertySheetPage} or <code>null</code> when there is no provider
	 */
	private IPropertySheetPage createPropertySheetPage(final Object realContributor, final String viewID, final String rendererID) {
		IPropertySheetPage returnedPage = null;
		final Collection<IPropertySheetPageProvider> values = this.map.get(rendererID);
		if (values != null) {// the preferred rendering has not yet been loaded
			final Iterator<IPropertySheetPageProvider> iter = values.iterator();

			while (iter.hasNext() && returnedPage == null) {
				final IPropertySheetPageProvider current = iter.next();
				if (current.provides(realContributor, viewID)) {
					returnedPage = current.createPropertyPage(realContributor, viewID);
					// save the provider and the provided page (for dispose usage)
					this.mapOfProvidedSheetPage.put(returnedPage, current);
				}
			}
		}
		return returnedPage;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProviderService#registerPropertySheetPageProvider(org.eclipse.papyrus.views.properties.services.IPropertySheetPageProvider)
	 *
	 * @param provider
	 */
	@Override
	public void registerPropertySheetPageProvider(final IPropertySheetPageProvider provider) {
		// 1. register the new provider
		final String renderer = provider.getRendererID();
		Collection<IPropertySheetPageProvider> coll = this.map.get(renderer);
		if (coll == null) {
			coll = new ArrayList<>();
			this.map.put(renderer, coll);
		}
		coll.add(provider);

		if (this.renderersRequiringReload.contains(renderer)) {
			// if we are here, the property view has already been initialized, but the wanted was not found during the previous call
			// that's why we reload the property view!
			reloadPropertyView();
		}
	}

	/**
	 * Reload the Property View
	 */
	private void reloadPropertyView() {
		// at this step, Eclipse probably doesn't yet finished to load, that's why we do an asynch exec
		// this runnable is in charge to hide the property view and to reopen it
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				// 1. find property view
				final IWorkbenchPage wp = getActivePage();
				final IViewReference properties = getPropertyView(wp);

				if (properties != null) {

					// 2. find all Papyrus editor (we assume only Papyrus (Papyrus-Sirius) is concerned by the save problem due to a reload
					for (final IEditorReference ref : getAllPapyrusEditors(wp)) {
						final IEditorPart p = ref.getEditor(false);
						// p can be null when there are several Papyrus CoreMultiDiagramEditors opened in the same time in Eclipse.
						// In this case, when we reopen Eclipse, only the first one is really loaded,
						// the others are just visible as tab, but they are not really created, consequently, 'p' is null for these editors.
						if (p != null && p.isDirty()) {
							// we force the save
							p.doSave(new NullProgressMonitor());
						}
					}
					// 3. we hide the property view
					wp.hideView(properties);
					try {
						wp.showView(PROPERTY_SHEET_ID);
					} catch (PartInitException e) {
						Activator.log.error(e.getLocalizedMessage(), e);
					}
				}

			}
		});
	}



	/**
	 *
	 * @return
	 *         the activePage or <code>null</code> when not found
	 */
	private IWorkbenchPage getActivePage() {
		final IWorkbench wb = PlatformUI.getWorkbench();
		if (wb != null) {
			final IWorkbenchWindow activeWorkbench = wb.getActiveWorkbenchWindow();
			if (activeWorkbench != null) {
				return activeWorkbench.getActivePage();

			}
		}
		return null;
	}

	/**
	 *
	 * @param wp
	 *            the workbench page
	 * @return
	 *         the {@link IViewReference} representing the Pro
	 */
	private IViewReference getPropertyView(final IWorkbenchPage wp) {
		for (final IViewReference current : wp.getViewReferences()) {
			if (PROPERTY_SHEET_ID.equals(current.getId())) {
				return current;
			}
		}
		return null;
	}

	/**
	 *
	 * @param wp
	 *            a workbench page
	 * @return
	 *         all {@link IEditorReference} representing a Papyrus Editor
	 */
	private Collection<IEditorReference> getAllPapyrusEditors(final IWorkbenchPage wp) {
		final Collection<IEditorReference> papyrus = new ArrayList<>();
		for (final IEditorReference current : wp.getEditorReferences()) {
			if (PAPYRUS_EDITOR_ID.equals(current.getId())) {
				papyrus.add(current);
			}
		}
		return papyrus;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProviderService#getSelectedRenderer()
	 *      If the user already defined a preference, we return the prefered renderer. If not, we sort the existing renderer by alphabetic order and we return the first one
	 *
	 * @return
	 *         the renderer to use or <code>null</code> if there is no renderer
	 */
	@Override
	public String getSelectedRenderer() {
		final String prefered = Activator.getDefault().getPreferenceStore().getString(PropertyRendererPreferencesConstants.PREFERRED_RENDERER);
		if (prefered != null && !prefered.isEmpty()) {
			return prefered;
		} else if (this.selectedRenderedID == null && this.map.keySet().size() > 0) {
			final List<String> keys = new ArrayList<>(this.map.keySet());
			Collections.sort(keys);
			this.selectedRenderedID = keys.get(0);
			Activator.getDefault().getPreferenceStore().setDefault(PropertyRendererPreferencesConstants.PREFERRED_RENDERER, this.selectedRenderedID);
		}
		return this.selectedRenderedID;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProviderService#getAvailableRenderers()
	 *
	 * @return
	 */
	@Override
	public List<String> getAvailableRenderers() {
		final List<String> keys = new ArrayList<>(this.map.keySet());
		Collections.sort(keys);
		return keys;
	}

	/**
	 * @see org.eclipse.papyrus.views.properties.services.IPropertySheetPageProviderService#dispose(org.eclipse.ui.views.properties.IPropertySheetPage)
	 *
	 * @param page
	 */
	@Override
	public void dispose(final IPropertySheetPage page) {
		final IPropertySheetPageProvider current = this.mapOfProvidedSheetPage.remove(page);
		if (current != null) {
			current.dispose(page);
		}
	}

}
