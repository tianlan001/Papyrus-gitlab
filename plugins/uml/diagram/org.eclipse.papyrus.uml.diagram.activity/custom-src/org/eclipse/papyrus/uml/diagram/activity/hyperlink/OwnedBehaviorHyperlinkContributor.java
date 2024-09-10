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

package org.eclipse.papyrus.uml.diagram.activity.hyperlink;

import java.util.ArrayList;
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
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityDiagramEditPart;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BehavioredClassifier;

/**
 * Returns a list of HyperLinkSpecificObjects (view elements) referencing
 * owned behaviors of a BehaviorClassifier
 *
 * @author Shuai Li
 *
 */
public class OwnedBehaviorHyperlinkContributor implements HyperlinkContributor {

	/**
	 * @see org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor#getHyperlinks(java.lang.Object)
	 *
	 * @param fromElement
	 * @return
	 */
	@Override
	public List<HyperLinkObject> getHyperlinks(Object fromElement) {
		ArrayList<HyperLinkObject> hyperlinks = new ArrayList<>();

		if (fromElement instanceof BehavioredClassifier) {
			List<Behavior> behaviors = ((BehavioredClassifier) fromElement).getOwnedBehaviors();
			List<Object> objectsInViews = new ArrayList<>();

			for (Behavior behavior : behaviors) {
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
					List<Object> viewerSearchResults = viewerSearchService.getViewersInCurrentModel(behavior, (BehavioredClassifier) fromElement, false, false);
					objectsInViews.addAll(viewerSearchResults);
				}
			}

			for (Object object : objectsInViews) {
				if (object instanceof View && ((View) object).getDiagram() != null) {
					if (((View) object).getDiagram().getType().equals(ActivityDiagramEditPart.MODEL_ID)) {
						HyperLinkSpecificObject hyperlink = new HyperLinkSpecificObject((EObject) object);
						hyperlinks.add(hyperlink);
					}
				}
			}
		}

		return hyperlinks;
	}

}
