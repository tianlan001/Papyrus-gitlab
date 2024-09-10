/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Shuai Li (CEA LIST) shuai.li@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.navigation.navigableElement;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationContributor;
import org.eclipse.papyrus.uml.navigation.navigableElement.ConnectorEndNavigableElement.ConnectorEndDerivedFeatureKind;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Element;

/**
 * NavigationContributor to navigate from TypedElement to their Type
 *
 */
public class ConnectorNavigationContributor implements NavigationContributor {

	public List<NavigableElement> getNavigableElements(Object fromElement) {
		List<NavigableElement> result = new LinkedList<NavigableElement>();
		
		Element element = UMLUtil.resolveUMLElement(fromElement);
		if (element instanceof Connector) {
			Connector connector = (Connector) element;
			
			if (connector.getEnds().size() > 0) {
				for (ConnectorEnd end : connector.getEnds()) {
					if (end.getRole() != null) {
						result.add(new ConnectorEndNavigableElement(end, ConnectorEndDerivedFeatureKind.ROLE));
					}
					
					if (end.getPartWithPort() != null) {
						result.add(new ConnectorEndNavigableElement(end, ConnectorEndDerivedFeatureKind.PART_WITH_PORT));
					}
					
					if (end.getDefiningEnd() != null) {
						result.add(new ConnectorEndNavigableElement(end, ConnectorEndDerivedFeatureKind.DEFINING_END));
					}
				}
			}
		}

		return result;
	}
}
