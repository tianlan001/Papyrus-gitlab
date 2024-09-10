/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and implementation
 *****************************************************************************/

package org.eclipse.papyrus.views.references.utils;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.views.references.Activator;
import org.eclipse.papyrus.views.references.views.ReferencesView;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class ReferencesViewUtil {

	/**
	 * Get the view 'References' if it's already opened.
	 */
	public static ReferencesView getOpenedReferencesView() {
		final IViewReference[] iViewVeferences = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
				.getViewReferences();

		for (final IViewReference iViewReference : iViewVeferences) {
			final IViewPart iViewPart = iViewReference.getView(false);
			if (iViewPart instanceof ReferencesView) {
				return (ReferencesView) iViewPart;
			}
		}

		return null;
	}

	/**
	 * Show the view 'References'.
	 */
	public static ReferencesView openReferencesView() {
		try {
			final IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			return (ReferencesView) activePage.showView(ReferencesView.ID, null, IWorkbenchPage.VIEW_VISIBLE);
		} catch (final PartInitException e) {
			Activator.logError(e.getMessage());
			return null;
		}
	}

	/**
	 * Return the 'Head' container of the EObject.
	 *
	 * @param eObject
	 *            The EObject
	 * @return
	 */
	public static EObject getContainer(final EObject eObject) {
		EObject container = eObject.eContainer();
		if (null != container) {
			return getContainer(container);
		}
		return eObject;
	}
}
