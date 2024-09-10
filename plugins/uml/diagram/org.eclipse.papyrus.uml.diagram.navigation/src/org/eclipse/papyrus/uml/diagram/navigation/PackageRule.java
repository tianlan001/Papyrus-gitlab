/*****************************************************************************
 * Copyright (c) 2010 Atos Origin.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Mathieu Velten (Atos Origin) mathieu.velten@atosorigin.com - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.navigation;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.papyrus.infra.gmfdiag.navigation.CreatedNavigableElement;
import org.eclipse.papyrus.infra.gmfdiag.navigation.ExistingNavigableElement;
import org.eclipse.papyrus.infra.gmfdiag.navigation.IModelLinker;
import org.eclipse.papyrus.infra.gmfdiag.navigation.INavigationRule;
import org.eclipse.papyrus.infra.gmfdiag.navigation.NavigableElement;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;


public class PackageRule implements INavigationRule {

	@Override
	public boolean handle(EObject element) {
		return element instanceof Package;
	}

	@Override
	public List<NavigableElement> getNextPossibleElements(NavigableElement currentNavElement) {
		List<NavigableElement> nextPossibleElements = new LinkedList<NavigableElement>();

		EStructuralFeature packagedFeature = UMLPackage.Literals.PACKAGE__PACKAGED_ELEMENT;

		final Package pack = (Package) currentNavElement.getElement();

		for (PackageableElement element : pack.getPackagedElements()) {
			if (element instanceof Collaboration) {
				nextPossibleElements.add(new ExistingNavigableElement(element, packagedFeature));
			}
		}

		Collaboration collab = UMLFactory.eINSTANCE.createCollaboration();
		nextPossibleElements.add(new CreatedNavigableElement(collab, currentNavElement, packagedFeature, new IModelLinker() {

			@Override
			public void linkToModel(EObject toLink) {
				pack.getPackagedElements().add((PackageableElement) toLink);
			}
		}, UMLBaseNameSetter.instance));

		// provide the possibility to create behavior in package as structural element :
		// this allows to easily share behavior between elements using a package as library
		UMLRuleHelper.addBehaviorCreatedNavigableElements(nextPossibleElements, currentNavElement, UMLPackage.Literals.ELEMENT__OWNED_ELEMENT, new IModelLinker() {

			@Override
			public void linkToModel(EObject toLink) {
				pack.getPackagedElements().add((Behavior) toLink);
			}
		});

		return nextPossibleElements;
	}


}
