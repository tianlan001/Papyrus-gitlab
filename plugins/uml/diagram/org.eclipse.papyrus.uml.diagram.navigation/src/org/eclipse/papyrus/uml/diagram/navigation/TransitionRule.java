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
import org.eclipse.papyrus.infra.gmfdiag.navigation.ExistingNavigableElement;
import org.eclipse.papyrus.infra.gmfdiag.navigation.IModelLinker;
import org.eclipse.papyrus.infra.gmfdiag.navigation.INavigationRule;
import org.eclipse.papyrus.infra.gmfdiag.navigation.NavigableElement;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;


public class TransitionRule implements INavigationRule {

	@Override
	public boolean handle(EObject element) {
		return element instanceof Transition;
	}

	@Override
	public List<NavigableElement> getNextPossibleElements(NavigableElement currentNavElement) {
		List<NavigableElement> nextPossibleElements = new LinkedList<NavigableElement>();

		EStructuralFeature feature = UMLPackage.Literals.TRANSITION__EFFECT;

		final Transition transition = (Transition) currentNavElement.getElement();
		// two cases here :
		// if the effect is defined we want the possibility to navigate to it
		// otherwise we want the possibility to create either an interaction or an activity as an effect
		if (transition.getEffect() != null) {
			nextPossibleElements.add(new ExistingNavigableElement(transition.getEffect(), feature));
		} else {
			UMLRuleHelper.addBehaviorCreatedNavigableElements(nextPossibleElements, currentNavElement, feature, new IModelLinker() {

				@Override
				public void linkToModel(EObject toLink) {
					transition.setEffect((Behavior) toLink);
				}
			});
		}

		return nextPossibleElements;
	}

}
