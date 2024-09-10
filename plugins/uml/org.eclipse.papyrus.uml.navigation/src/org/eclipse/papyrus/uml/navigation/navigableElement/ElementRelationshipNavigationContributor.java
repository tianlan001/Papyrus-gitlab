/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
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

import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.papyrus.infra.services.navigation.service.NavigableElement;
import org.eclipse.papyrus.infra.services.navigation.service.NavigationContributor;
import org.eclipse.papyrus.uml.tools.utils.UMLUtil;
import org.eclipse.uml2.uml.DirectedRelationship;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Relationship;

/**
 * NavigationContributor to navigate from NamedElement
 */
public class ElementRelationshipNavigationContributor implements NavigationContributor {

	public List<NavigableElement> getNavigableElements(Object fromElement) {
		List<NavigableElement> result = new LinkedList<NavigableElement>();
		List<Element> targets = new UniqueEList<Element>();
		List<Element> sources = new UniqueEList<Element>();
		List<Element> children = new UniqueEList<Element>();
		List<Element> parents = new UniqueEList<Element>();

		Element element = UMLUtil.resolveUMLElement(fromElement);
		if (element instanceof Element) {
			for (Relationship relationship : ((Element) element).getRelationships()) {
				if (relationship instanceof Generalization) {
					Generalization generalization = (Generalization) relationship;
					
					// Parent
					if (generalization.getSpecific() == element) {
						if (generalization.getGeneral() != null) {
							parents.add(generalization.getGeneral());
						}
					}
					
					// Child
					if (generalization.getGeneral() == element) {
						if (generalization.getSpecific() != null) {
							children.add(generalization.getSpecific());
						}
					}
				} else if (relationship instanceof DirectedRelationship) {
					DirectedRelationship directedRelationship = (DirectedRelationship) relationship;
					
					// Targets
					if (directedRelationship.getSources().contains(element)) {
						for (Element target : directedRelationship.getTargets()) {
							targets.add(target);
						}
					}
					
					// Sources
					if (directedRelationship.getTargets().contains(element)) {
						for (Element source : directedRelationship.getSources()) {
							sources.add(source);
						}
					}
				}
			}
			
			for (Element parent : parents) {
				result.add(new ParentNavigableElement(parent));
			}
			
			for (Element child : children) {
				result.add(new ChildNavigableElement(child));
			}
			
			for (Element target : targets) {
				result.add(new TargetNavigableElement(target));
			}
			
			for (Element source : sources) {
				result.add(new SourceNavigableElement(source));
			}
		}

		return result;
	}
}
