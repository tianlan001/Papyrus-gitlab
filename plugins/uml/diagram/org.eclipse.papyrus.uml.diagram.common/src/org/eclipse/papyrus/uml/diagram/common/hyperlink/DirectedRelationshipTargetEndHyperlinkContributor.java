/*****************************************************************************
 * Copyright (c) 2016 CEA LIST
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.hyperlink;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.papyrus.infra.core.sasheditor.editor.ISashWindowsContainer;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.services.BadStateException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceNotFoundException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkSpecificObject;
import org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor;
import org.eclipse.papyrus.infra.services.viewersearch.impl.ViewerSearchService;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.Element;

/**
 * Returns a list of HyperLinkSpecificObjects (view elements) referencing
 * targets of the directed relationship
 *
 * @author Shuai Li
 *
 */
public class DirectedRelationshipTargetEndHyperlinkContributor implements HyperlinkContributor {

	/**
	 * @see org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor#getHyperlinks(java.lang.Object)
	 *
	 * @param fromElement
	 * @return
	 */
	@Override
	public List<HyperLinkObject> getHyperlinks(Object fromElement) {
		ArrayList<HyperLinkObject> hyperlinks = new ArrayList<>();

		if (fromElement instanceof DirectedRelationship) {
			List<Element> targets = ((DirectedRelationship) fromElement).getTargets();

			List<Object> objectsInViews = new ArrayList<>();

			for (Element target : targets) {
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
					List<Object> viewerSearchResults = viewerSearchService.getViewersInCurrentModel(target, null, false, false);
					objectsInViews.addAll(viewerSearchResults);
				}
			}

			// Get active page to later check if a found object is in the active page (we don't want the object then)
			TreeIterator<EObject> allViewsOfActivatePage = null;
			View page = null;
			try {
				IPage activePage = ServiceUtilsForEObject.getInstance().getService(ISashWindowsContainer.class, (Element) fromElement).getActiveSashWindowsPage();

				if (activePage != null) {
					Object pageId = activePage.getRawModel();

					if (pageId instanceof PageRef) {
						Object emfPageId = ((PageRef) pageId).getEmfPageIdentifier();

						if (emfPageId instanceof View) {
							page = ((View) emfPageId);
						}
					}
				}
			} catch (Exception e) {
				Activator.log.error(e);
			}

			for (Object object : objectsInViews) {
				if (object instanceof View) {
					// Check if the activate page contains the object
					// If so, we do not create a hyperlink for the object
					boolean inActivePage = false;
					if (page != null) {
						allViewsOfActivatePage = page.eAllContents();
						while (allViewsOfActivatePage.hasNext()) {
							EObject next = allViewsOfActivatePage.next();
							if (!(next instanceof View)) {
								allViewsOfActivatePage.prune();
								continue;
							}

							View nextView = (View) next;
							if (object.equals(nextView)) {
								inActivePage = true;
								break;
							}
						}
					}

					if (!inActivePage) {
						HyperLinkSpecificObject hyperlink = new HyperLinkSpecificObject((EObject) object);
						hyperlinks.add(hyperlink);
					}
				}
			}
		}

		return hyperlinks;
	}

}
