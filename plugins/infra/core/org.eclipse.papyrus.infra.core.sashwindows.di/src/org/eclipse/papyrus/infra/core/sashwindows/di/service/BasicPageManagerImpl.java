/*****************************************************************************
 * Copyright (c) 2013, 2016, 2019 Cedric Dumoulin, CEA, Christian W. Damus, and others.
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
 *  Christian W. Damus (CEA) - bug 433371
 *  Céline Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 415638
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 440754
 *  Christian W. Damus - bug 485220
 *  Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550569
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sashwindows.di.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.core.sashwindows.di.DiFactory;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;
import org.osgi.framework.FrameworkUtil;


/**
 * A basic implementation of the {@link IPageManager} service for manipulation of
 * the sash model in a headless (non-UI) context.
 *
 * @author cedric dumoulin
 *
 */
public class BasicPageManagerImpl implements IPageManager {

	/** Internal EMF model */
	private SashWindowsMngr diSashModel;

	/**
	 * Constructor.
	 *
	 * @param diSashModel
	 *            The model onto which operation of this class act.
	 *
	 */
	public BasicPageManagerImpl(SashWindowsMngr diSashModel) {
		super();

		this.diSashModel = diSashModel;
	}

	@Override
	public void closePage(Object pageIdentifier) {
		diSashModel.getSashModel().removePageAndEmptyFolder(pageIdentifier);
	}

	@Override
	public void closeAllOpenedPages() {
		diSashModel.getSashModel().removeAllPages();
	}

	@Override
	public void closeAllOpenedPages(Object pageIdentifier) {
		while (isOpen(pageIdentifier)) {
			closePage(pageIdentifier);
		}
	}

	@Override
	public void closeOtherPages(Object pageIdentifier) {
		diSashModel.getSashModel().removeOtherPages(pageIdentifier);
	}

	@Override
	@Deprecated
	public void addPage(Object pageIdentifier) {
		if (canAddPage(pageIdentifier)) {
			if (isLegacyMode()) {
				diSashModel.getPageList().addPage(pageIdentifier);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void doAddPage(Object pageIdentifier) {
		if (canAddPage(pageIdentifier)) {
			if (isLegacyMode()) {
				diSashModel.getPageList().addPage(pageIdentifier);
			}
			diSashModel.getSashModel().addPage(getCurrentFolder(), pageIdentifier);
		}
	}

	@SuppressWarnings("deprecation")
	private void doAddPage(PageRef page) {
		if (canAddPage(page)) {
			if (isLegacyMode()) {
				diSashModel.getPageList().addPage(page.getPageIdentifier());
			}
			diSashModel.getSashModel().addPage(getCurrentFolder(), page);
		}
	}

	/**
	 * Remove a page.
	 *
	 * @param pageIdentifier
	 *            identifies the page to remove
	 *
	 * @deprecated Use the {@linkplain #closeAllOpenedPages(Object)} method, instead
	 */
	@Deprecated
	public void removePage(Object pageIdentifier) {
		closeAllOpenedPages(pageIdentifier);
		if (isLegacyMode()) {
			diSashModel.getPageList().removePage(pageIdentifier);
		}
	}

	/**
	 * Reload the Diagram
	 * This used when a resource is reloaded, the related diagrams are reloaded as well
	 *
	 * @see org.eclipse.papyrus.infra.services.controlmode.listener.LoadResourceSnippet
	 *
	 * @param diagramProxy
	 *            Identifier of the page to reload
	 */
	@Override
	public void reloadPage(Object diagramProxy) {

		if (diagramProxy instanceof EObject) {

			PageRef proxyRef = diSashModel.getSashModel().lookupPage(diagramProxy);

			if (proxyRef.eContainer() instanceof TabFolder) {
				TabFolder folder = (TabFolder) proxyRef.eContainer();


				if (folder != null) {
					if (folder.getChildren() != null) {
						// get the initial index of the Diagram Tab
						int i = folder.getChildren().indexOf(proxyRef);

						if (i >= 0) {
							// Create a new Page
							PageRef newRef = DiFactory.eINSTANCE.createPageRef();
							newRef.setEmfPageIdentifier((EObject) diagramProxy);
							// Replace the previous by the new one
							folder.getChildren().set(i, newRef);
						}
					}
				}
			}
		}
	}

	@Override
	public List<Object> allPages() {
		List<Object> result;

		if (isLegacyMode()) {
			result = legacyAllPages();
		} else {
			// FIXME: Temporary, naive code. Need to implement a mechanism to contribute page providers
			// fixed in subclass to avoir cyclic dependency problem (see bug 548998)
			result = new ArrayList<>();

			List<Resource> notationResources = getResources("notation");
			for (Resource notationResource : notationResources) {
				for (EObject content : notationResource.getContents()) {
					if (isPage(content)) {
						result.add(content);
					}
				}
			}
		}

		return result;
	}

	@SuppressWarnings("deprecation")
	protected boolean isLegacyMode() {
		return diSashModel.getPageList() != null;
	}

	@SuppressWarnings("deprecation")
	private List<Object> legacyAllPages() {
		List<Object> result;

		if (diSashModel.getPageList() != null) {
			result = diSashModel.getPageList().getAvailablePage().stream()
					.map(PageRef::getPageIdentifier)
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
		} else {
			result = new ArrayList<>(0);
		}

		return result;
	}

	/**
	 * Gets the local pages.
	 *
	 * @param model
	 *            the model
	 * @return the local pages
	 * @see org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager#allLocalPages()
	 */
	@Override
	public List<Object> allLocalPages(ILocalPageService service) {
		List<Object> result = new LinkedList<>();

		for (Object next : allPages()) {
			if (service.isLocalPage(next)) {
				result.add(next);
			}
		}

		return result;
	}


	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager#getAssociatedPages(org.eclipse.emf.common.util.URI)
	 *
	 * @param uriTrim
	 * @return
	 */
	@Override
	public List<Object> getAssociatedPages(Object uriTrim) {

		List<Object> list = new ArrayList<>();

		SashModel sashModel = diSashModel.getSashModel();
		Iterator<?> iter = sashModel.eAllContents();

		while (iter.hasNext()) {
			Object next = iter.next();
			if (next instanceof PageRef) {
				PageRef pageRef = (PageRef) next;

				// pageRef is one of the pages referred into the sash resource
				if (pageRef != null) {
					EObject pageID = pageRef.getEmfPageIdentifier();
					if (pageID != null) {
						URI uriContainer;
						if (pageID.eIsProxy()) {
							InternalEObject internal = (InternalEObject) pageID;
							uriContainer = internal.eProxyURI().trimFragment().trimFileExtension();

						} else {
							uriContainer = pageID.eResource().getURI().trimFileExtension();
						}
						if (uriTrim instanceof URI) {
							if (uriContainer.equals(uriTrim)) {
								list.add(pageID);
							}
						}
					}
				}

			}

		}
		return list;
	}

	/**
	 * Gets the resources.
	 *
	 * @param fileExtension
	 *            the file extension
	 * @return the resources
	 * @since 1.3
	 */
	protected List<Resource> getResources(String fileExtension) {
		List<Resource> resourcesList = new LinkedList<>();

		// Get the contextual resources from the sash model
		ResourceSet resourceSet = diSashModel.eResource().getResourceSet();
		for (Resource resource : resourceSet.getResources()) {

			// Verify if the resource exist and is loaded
			if (resource != null && resource.isLoaded()) {
				// Verify if its extension correspond
				if (fileExtension.equals(resource.getURI().fileExtension())) {
					resourcesList.add(resource);
				}

			}
		}

		return resourcesList;
	}

	/**
	 * Checks if is page.
	 *
	 * @param content
	 *            the content
	 * @return true, if is page
	 */
	protected boolean isPage(EObject content) {
		// In the headless context, anything can be a page
		return true;
	}

	/**
	 * Determinate if the page can be added or not.
	 *
	 * @param pageIdentifier
	 *            The page identifier.
	 * @return <code>true</code> if the page can be added, <code>false</code> otherwise.
	 * @since 1.4
	 */
	protected boolean canAddPage(final Object pageIdentifier) {
		return true;
	}

	/**
	 * Determinate if the page can be added or not.
	 *
	 * @param page
	 *            The page ref.
	 * @return <code>true</code> if the page can be added, <code>false</code> otherwise.
	 * @since 1.4
	 */
	protected boolean canAddPage(final PageRef page) {
		return true;
	}

	/**
	 * Obtains the currently active tab folder, whatever that might mean in the
	 * context of the page manager.
	 *
	 * @return the current folder, never {@code null} because at least one folder always
	 *         implicitly exists
	 */
	protected TabFolder getCurrentFolder() {
		TabFolder result = diSashModel.getSashModel().getCurrentSelection();

		return result;
	}

	@Override
	public boolean isOpen(Object pageIdentifier) {
		return diSashModel.getSashModel().lookupPage(pageIdentifier) != null;
	}

	@Override
	public void openPage(Object pageIdentifier) {
		doAddPage(pageIdentifier);
	}

	@Override
	public void openPage(Object pageIdentifier, String editorID) {
		PageRef newPage = DiFactory.eINSTANCE.createPageRef();
		newPage.setFavoriteEditor(editorID);
		newPage.setPageIdentifier(pageIdentifier);
		doAddPage(newPage);
	}

	@Override
	public void selectPage(final Object pageIdentifier) {
		// In an headless context, there is no sensible realization of the "active page"
	}

	/**
	 * Executes an operation on my internal sash model.
	 *
	 * @param <T>
	 *            the generic type
	 * @param sashModelOperation
	 *            the operation to execute
	 * @return the operation's result
	 * @throws IllegalAccessException
	 *             on attempt to execute an operation defined by a client bundle
	 */
	public <T> T execute(SashModelOperation<T> sashModelOperation) throws IllegalAccessException {
		if (FrameworkUtil.getBundle(sashModelOperation.getClass()) != FrameworkUtil.getBundle(BasicPageManagerImpl.class)) {
			throw new IllegalAccessException("Attempt to access bundle-private API."); //$NON-NLS-1$
		}

		return doExecute(sashModelOperation);
	}

	protected <T> T doExecute(SashModelOperation<T> sashModelOperation) {
		return sashModelOperation.execute(diSashModel);
	}

	//
	// Private interfaces
	//

	/**
	 * An operation on the internal sash model of a page manager.
	 *
	 * @param <T>
	 *            the generic type
	 */
	@FunctionalInterface
	public static interface SashModelOperation<T> {

		/**
		 * Execute.
		 *
		 * @param sashWindowsManager
		 *            the sash windows manager
		 * @return the t
		 */
		T execute(SashWindowsMngr sashWindowsManager);
	}

}