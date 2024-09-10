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

package org.eclipse.papyrus.infra.gmfdiag.navigation.menuContributor;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.EMFHelper;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.common.helper.NotationHelper;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.button.NavigationSubMenuButton;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuButton;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuContributor;

/**
 * @author Shuai Li (CEA LIST) <shuai.li@cea.fr>
 *
 */
public class OwnedDiagramsButton implements NavigationMenuContributor {

	/**
	 * @see org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuContributor#getButtons(java.lang.Object)
	 *
	 * @param fromElement
	 * @return
	 */
	public List<NavigationMenuButton> getButtons(Object fromElement) {
		List<NavigationMenuButton> buttons = new LinkedList<NavigationMenuButton>();

		if (fromElement != null) {
			List<Diagram> diagrams = getOwnedDiagrams(fromElement);
			if (!diagrams.isEmpty()) {
				buttons.add(new NavigationSubMenuButton("Owned diagrams...", null, "Show owned diagrams", diagrams));
			}
		}

		return buttons;
	}

	protected List<Diagram> getOwnedDiagrams(Object fromElement) {
		EObject eObject = EMFHelper.getEObject(fromElement); // Should not be null (Otherwise, return=)
		View currentView = NotationHelper.findView(fromElement); // May be null (e.g. Selection from the ModelExplorer)
		if (eObject instanceof View || eObject == null) {
			return Collections.emptyList();
		}

		try {
			List<Diagram> ownedDiagrams = new LinkedList<Diagram>();

			IPageManager pageManager = ServiceUtilsForEObject.getInstance().getService(IPageManager.class, eObject);
			for (Object pageObject : pageManager.allPages()) {
				if (pageObject instanceof Diagram) {
					Diagram diagram = (Diagram) pageObject;

					// Avoid navigation to the current diagram
					/*
					 * if (currentView != null && currentView.getDiagram() == diagram) {
					 * continue;
					 * }
					 */

					if (diagram.getElement() == eObject) {
						ownedDiagrams.add(diagram);
					}
				}
			}

			return ownedDiagrams;
		} catch (ServiceException ex) {
			return Collections.emptyList();
		}
	}
}
