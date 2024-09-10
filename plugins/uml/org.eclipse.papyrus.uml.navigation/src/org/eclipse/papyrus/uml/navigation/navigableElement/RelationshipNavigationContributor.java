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
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;

/**
 * NavigationContributor to navigate from TypedElement to their Type
 *
 */
public class RelationshipNavigationContributor implements NavigationContributor {

	public List<NavigableElement> getNavigableElements(Object fromElement) {
		List<NavigableElement> result = new LinkedList<NavigableElement>();
		
		Element element = UMLUtil.resolveUMLElement(fromElement);
		if (element instanceof DirectedRelationship) {
			DirectedRelationship relationship = (DirectedRelationship) element;
			
			if (relationship.getTargets().size() > 0) {
				result.add(new TargetNavigableElement(relationship));
			}
			
			if (relationship.getSources().size() > 0) {
				result.add(new SourceNavigableElement(relationship));
			}
		} else if (element instanceof Association) {
			Association association =  (Association) element;
			if (association.getMemberEnds().size() > 0) {
				for (Property end : association.getMemberEnds()) {
					result.add(new MemberEndNavigableElement(end));
					if (end.getType() != null) {
						result.add(new MemberEndTypeNavigableElement(end));
					}
				}
			}
		}

		return result;
	}
}
