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

package org.eclipse.papyrus.uml.navigation.menuContributor;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.gmfdiag.navigation.menu.button.NavigationSubMenuButton;
import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuButton;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuContributor;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationService;
import org.eclipse.papyrus.uml.navigation.Activator;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.Element;

/**
 * The navigation menu contributor returns a navigation menu button that contains
 * the targets of the element that is clicked on.
 *
 */
public class TargetsButton implements NavigationMenuContributor {

	/**
	 * @see org.eclipse.papyrus.infra.services.navigation.service.NavigationMenuContributor#getButtons(java.lang.Object)
	 *
	 * @param fromElement
	 * @return
	 */
	public List<NavigationMenuButton> getButtons(Object fromElement) {
		List<NavigationMenuButton> buttons = new LinkedList<NavigationMenuButton>();

		if (fromElement instanceof Element) {
			try {
				NavigationService navigationService = ServiceUtilsForEObject.getInstance().getService(NavigationService.class, (Element) fromElement);

				List<DirectedRelationship> relationships = ((Element) fromElement).getSourceDirectedRelationships();

				if (!relationships.isEmpty()) {
					List<NavigableElement> navigableElements = new LinkedList<NavigableElement>();
					for (DirectedRelationship relationship : relationships) {
						for (Element target : relationship.getTargets()) {
							navigableElements.addAll(navigationService.getNavigableElements(target));
						}
					}

					if (!navigableElements.isEmpty()) {
						buttons.add(new NavigationSubMenuButton("Targets...", null, "Show targets", navigableElements));
					}
				}
			} catch (ServiceException e) {
				Activator.log.error(e);
			}
		}

		return buttons;
	}
}
