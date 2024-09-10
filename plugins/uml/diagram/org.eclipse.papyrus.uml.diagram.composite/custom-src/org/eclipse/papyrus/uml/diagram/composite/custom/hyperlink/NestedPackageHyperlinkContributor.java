/*****************************************************************************
 * Copyright (c) 2015, 2016 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.composite.custom.hyperlink;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.services.BadStateException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceNotFoundException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.object.HyperLinkEditor;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor;
import org.eclipse.papyrus.infra.services.viewersearch.impl.ViewerSearchService;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CompositeStructureDiagramEditPart;

/**
 * Returns a list of HyperLinkEditor objects referencing views directly owned by
 * the nested packages of the double-clicked package.
 * 
 * @author Shuai Li
 *
 */
public class NestedPackageHyperlinkContributor implements HyperlinkContributor {

	/**
	 * @see org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor#getHyperlinks(java.lang.Object)
	 *
	 * @param fromElement
	 * @return
	 */
	@Override
	public List<HyperLinkObject> getHyperlinks(Object fromElement) {
		ArrayList<HyperLinkObject> hyperlinks = new ArrayList<HyperLinkObject>();

		if (fromElement instanceof org.eclipse.uml2.uml.Package) {
			List<org.eclipse.uml2.uml.Package> nestedPackages = ((org.eclipse.uml2.uml.Package) fromElement).getNestedPackages();
			List<Object> pages = new ArrayList<Object>();

			for (org.eclipse.uml2.uml.Package nestedPackage : nestedPackages) {
				ViewerSearchService viewerSearchService = null;
				try {
					viewerSearchService = ServiceUtilsForEObject.getInstance().getService(ViewerSearchService.class, (EObject) fromElement);
				} catch (ServiceException e) {
					if (e instanceof ServiceNotFoundException) {
						viewerSearchService = new ViewerSearchService();
						try {
							viewerSearchService.startService();
							ServiceUtilsForEObject.getInstance().getServiceRegistry((EObject) fromElement).add(ViewerSearchService.class, 1, viewerSearchService);
						} catch (ServiceException e1) {
							Activator.log.error(e1);
						}
					} else if (e instanceof BadStateException) {
						try {
							ServiceUtilsForEObject.getInstance().getServiceRegistry((EObject) fromElement).startRegistry();
							viewerSearchService = ServiceUtilsForEObject.getInstance().getService(ViewerSearchService.class, (EObject) fromElement);
						} catch (Exception e1) {
							Activator.log.error(e1);
						}
					}
				}

				if (viewerSearchService != null) {
					List<Object> viewerSearchResults = viewerSearchService.getViewersInCurrentModel(null, nestedPackage, true, false);
					pages.addAll(viewerSearchResults);
				}
			}

			for (Object page : pages) {

				if (page instanceof Diagram
						&& ((Diagram) page).getType().equals(CompositeStructureDiagramEditPart.MODEL_ID)) {
					try {
						// Page must not be active page
						IPage activeSashPage = ServiceUtilsForEObject.getInstance().getService(ISashWindowsContainer.class, (org.eclipse.uml2.uml.Package) fromElement).getActiveSashWindowsPage();
						Object activePage = null;

						if (activeSashPage != null) {
							Object pageId = activeSashPage.getRawModel();

							if (pageId instanceof PageRef) {
								Object emfPageId = ((PageRef) pageId).getEmfPageIdentifier();

								if (emfPageId instanceof View) {
									activePage = emfPageId;
								}
							}
						}

						if (activePage == null || !activePage.equals(page)) {
							HyperLinkEditor hyperlink = new HyperLinkEditor();
							hyperlink.setObject(page);
							hyperlinks.add(hyperlink);
						}
					} catch (Exception e) {
						Activator.log.error(e);
					}
				}


			}
		}

		return hyperlinks;
	}

}
