/*****************************************************************************
 * Copyright (c) 2009 - 2015 CEA LIST & LIFL
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
 *  Camille Letavernier (CEA LIST) - camille.letavernier@cea.fr - Bug 476625
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider;

import java.util.Collections;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.core.sasheditor.contentprovider.IPageModel;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;


/**
 * Factory used to create IPageModel from an object identifying a page.
 * IPageModel are objects used by the Sash Windows to create a page.
 *
 * @author cedric dumoulin
 */
public interface IPageModelFactory {

	/**
	 * Generic EditorID that represents the default editor associated to any page identifier
	 * 
	 * @since 2.0
	 */
	String DEFAULT_EDITOR = IPageManager.class.getCanonicalName() + ".Default"; //$NON-NLS-1$

	/**
	 * Create the IPageModel for the pageIdentifier. The pageIdentifier is the object passed to
	 * the {@link IPageManager#addEditor(EObject)}.
	 * This factory method is called by the Sash Windows whenever it needs to create a page. The identifier
	 * is found in the sash model.
	 *
	 * @param pageIdentifier
	 *            The identifier identifying the page to create.
	 * @return
	 */
	public IPageModel createIPageModel(Object pageIdentifier);

	/**
	 * @see org.eclipse.papyrus.infra.core.sasheditor.di.contentprovider.IPageModelFactory#createIPageModel(java.lang.Object)
	 *
	 * @param pageIdentifier
	 * @return
	 * @since 2.0
	 */
	default IPageModel createIPageModel(Object pageIdentifier, String favoriteEditorID) {
		return createIPageModel(pageIdentifier);
	}

	/**
	 * Finds the EditorIDs that can open the given pageIdentifier. This list is a hint
	 *
	 * The exact behavior is implementation-specific; this method shouldn't be used
	 * to determine whether a page identifier can actually be opened.
	 *
	 * Implementations may return a list containing the {@link IPageManager#DEFAULT_EDITOR} ID,
	 * and the list might be incomplete (i.e. editors not listed might
	 * still be able to open the given pageIdentifier)
	 *
	 * @param pageIdentifier
	 * @return A map of (ID -> Label) of the editors that can open the given page
	 *
	 * @see {@link IPageManager#DEFAULT_EDITOR}
	 * @since 2.0
	 */
	default Map<String, String> getEditorIDsFor(Object pageIdentifier) {
		return Collections.singletonMap(DEFAULT_EDITOR, "Default editor"); //$NON-NLS-1$
	}
}
