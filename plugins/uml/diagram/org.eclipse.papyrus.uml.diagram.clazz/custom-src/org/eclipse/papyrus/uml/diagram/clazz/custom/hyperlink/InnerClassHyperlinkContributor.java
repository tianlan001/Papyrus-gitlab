/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.uml.diagram.clazz.custom.hyperlink;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.services.BadStateException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceNotFoundException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkSpecificObject;
import org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor;
import org.eclipse.papyrus.infra.services.viewersearch.impl.ViewerSearchService;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.ModelEditPart;
import org.eclipse.uml2.uml.Element;

/**
 * Returns a list of HyperLinkSpecificObjects (view elements) referencing
 * inner classes of the class
 *
 * @author Shuai Li
 * @since 3.0
 *
 */
public class InnerClassHyperlinkContributor implements HyperlinkContributor {

	/**
	 * @see org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor#getHyperlinks(java.lang.Object)
	 *
	 * @param fromElement
	 * @return
	 */
	@Override
	public List<HyperLinkObject> getHyperlinks(Object fromElement) {
		ArrayList<HyperLinkObject> hyperlinks = new ArrayList<>();

		if (fromElement instanceof org.eclipse.uml2.uml.Class) {
			List<Element> elements = ((org.eclipse.uml2.uml.Class) fromElement).getOwnedElements();
			List<org.eclipse.uml2.uml.Class> classes = new LinkedList<>();
			for (Element element : elements) {
				if ("Class".equals(element.eClass().getName())) {
					classes.add((org.eclipse.uml2.uml.Class) element);
				}
			}
			List<Object> objectsInViews = new ArrayList<>();

			for (org.eclipse.uml2.uml.Class clazz : classes) {
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
					List<Object> viewerSearchResults = viewerSearchService.getViewersInCurrentModel(clazz, (org.eclipse.uml2.uml.Class) fromElement, false, false);
					objectsInViews.addAll(viewerSearchResults);
				}
			}

			for (Object object : objectsInViews) {
				if (object instanceof View && ((View) object).getDiagram() != null) {
					if (((View) object).getDiagram().getType().equals(ModelEditPart.MODEL_ID)) {
						HyperLinkSpecificObject hyperlink = new HyperLinkSpecificObject((EObject) object);
						hyperlinks.add(hyperlink);
					}
				}
			}
		}

		return hyperlinks;
	}

}
