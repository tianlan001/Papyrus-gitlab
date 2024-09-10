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

package org.eclipse.papyrus.uml.diagram.composite.custom.hyperlink;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.services.BadStateException;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServiceNotFoundException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.hyperlink.object.HyperLinkEditor;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor;
import org.eclipse.papyrus.infra.services.viewersearch.impl.ViewerSearchService;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CompositeStructureDiagramEditPart;

/**
 * Returns a list of HyperLinkEditors referencing
 * inner composite structure diagrams
 * 
 * @author Shuai Li
 *
 */
public class InnerCompositeStructureHyperlinkContributor implements HyperlinkContributor {

	/**
	 * @see org.eclipse.papyrus.infra.hyperlink.service.HyperlinkContributor#getHyperlinks(java.lang.Object)
	 *
	 * @param fromElement
	 * @return
	 */
	public List<HyperLinkObject> getHyperlinks(Object fromElement) {
		ArrayList<HyperLinkObject> hyperlinks = new ArrayList<HyperLinkObject>();
		
		if (fromElement instanceof org.eclipse.uml2.uml.Class) {
			List<Object> objectsInViews = new ArrayList<Object>();
			
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
				List<Object> viewerSearchResults = viewerSearchService.getViewersInCurrentModel((org.eclipse.uml2.uml.Class) fromElement, (org.eclipse.uml2.uml.Class) fromElement, true, false);
				objectsInViews.addAll(viewerSearchResults);
			}
			
			for (Object object : objectsInViews) {
				if (object instanceof Diagram) {
					if (((Diagram) object).getType().equals(CompositeStructureDiagramEditPart.MODEL_ID)) {
						HyperLinkEditor hyperlink = new HyperLinkEditor();
						hyperlink.setObject(object);
						hyperlinks.add(hyperlink);
					}
				}
			}
		}
		
		return hyperlinks;
	}

}
