/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Cedric Dumoulin (LIFL) cedric.dumoulin@lifl.fr - Initial API
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 440754
 *  Céline Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 415638
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sashwindows.di.service;

import java.util.List;

import org.eclipse.emf.ecore.EObject;

/**
 * <p>
 * Interface providing method to manage pages in the Sash Windows system.
 * This interface can be provided by a ContentProvider wishing to provide a standard
 * way to manage pages. This interface is not mandatory for the Sash Editor.
 * </p>
 * <p>
 * This interface provide basic access to the Sash Windows system.
 * It is intended to be used from the application in order to interact with the ContentProvider.
 * Then, the implementation of the editor UI will refresh its views.
 * </p>
 *
 * @author dumoulin
 */
public interface IPageManager {

	/**
	 * Add a Page identifier to the list of pages, do not open it.
	 *
	 * @param page
	 *            The object identifying the page to add. This object will be passed to the {@link IPageModelFactory#createIPageModel(EObject)}. This
	 *            identifier is stored in the sash model.
	 *            It should be a reference on a EMF object identifying the page.
	 * @deprecated The pages are now computed dynamically
	 */
	@Deprecated
	public void addPage(Object pageIdentifier);

	/**
	 * Reload the Diagram
	 * This used when a resource is reloaded, the related diagrams are reloaded as well
	 *
	 * @see org.eclipse.papyrus.infra.services.controlmode.listener.LoadResourceSnippet
	 *
	 * @param pageIdentifier
	 *            Identifier of the page to reload
	 */
	public void reloadPage(Object pageIdentifier);

	/**
	 * Close the page corresponding to the identifier.
	 * The identifier is removed from the Sash Windows, but not from the list of pages.
	 *
	 * If the page is open more than once on the current Sash windows, only
	 * one instance will be closed.
	 *
	 * @param pageIdentifier
	 *            The object identifying the page
	 *
	 *
	 * @see {@link #closeAllOpenedPages(Object)}
	 */
	public void closePage(Object pageIdentifier);


	/**
	 * Close all opened pages.
	 *
	 */
	public void closeAllOpenedPages();

	/**
	 * Close all opened pages except the one provided as parameter.
	 *
	 * @param pageIdentifier
	 */
	public void closeOtherPages(Object pageIdentifier);

	/**
	 * Open a Page corresponding to the identifier. If the page is not in the list of pages, add it.
	 * The identifier is first added to the current folder model. Then the Sash Windows should react and
	 * ask the {@link IPageModelFactory} to create the IPageModel. This later is then used to create the
	 * SWT page.
	 *
	 * @param page
	 *            The object identifying the page to add. This object will be passed to the {@link IPageModelFactory#createIPageModel(EObject)}. This
	 *            identifier is stored in the sash model.
	 *            It should be a reference on a EMF object identifying the page.
	 */
	public void openPage(Object pageIdentifier);

	/**
	 * Open a Page corresponding to the identifier.
	 * The identifier is first added to the current folder model. Then the Sash Windows should react and
	 * ask the {@link IPageModelFactory} to create the IPageModel. This later is then used to create the
	 * SWT page.
	 *
	 * The specified EditorID will be propagated to the {@link IPageModelFactory} to open the right editor
	 *
	 * @param page
	 *            The object identifying the page to add. This object will be passed to the {@link IPageModelFactory#createIPageModel(EObject)}. This
	 *            identifier is stored in the sash model.
	 *            It should be a reference on a EMF object identifying the page.
	 */
	default void openPage(Object pageIdentifier, String editorID) {
		openPage(pageIdentifier);
	}

	/**
	 * Return all available page identifiers (open and closed pages).
	 *
	 * @return List of registered page identifiers.
	 */
	public List<Object> allPages();

	/**
	 * Gets the local pages of a main model.
	 *
	 * @param mainModel
	 *            the main model
	 * @param service
	 *            the service to determine what is a local page
	 * @return the local pages
	 */
	public List<Object> allLocalPages(ILocalPageService service);

	/**
	 * Return true if a page is open for the specified pageIdentifier.
	 *
	 * @param diagram
	 * @return
	 */
	public boolean isOpen(Object pageIdentifier);

	/**
	 * Remove all pages used to render the specified pageIndentifier.
	 * If the pageIdentifier has more than one page rendering it, each of these
	 * pages are closed.
	 *
	 * @param pageIdentifier
	 */
	public void closeAllOpenedPages(Object pageIdentifier);

	/**
	 * Select the first page used to render the specified pageIdentifier.
	 * Do nothing if this page identifier is not rendered.
	 *
	 * @param pageIdentifier
	 */
	public void selectPage(Object pageIdentifier);

	/**
	 * Get the open pages associated to the passed Object
	 *
	 * @param uri
	 *            URI of the Object from which we want the associated open pages
	 * @return List of Pages identifier of the related object'URI
	 */
	public List<Object> getAssociatedPages(Object uri);


}
