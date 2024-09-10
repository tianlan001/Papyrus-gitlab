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
import org.eclipse.papyrus.infra.gmfdiag.navigation.ExistingNavigableElement;
import org.eclipse.papyrus.infra.gmfdiag.navigation.IModelLinker;
import org.eclipse.papyrus.infra.gmfdiag.navigation.INavigationRule;
import org.eclipse.papyrus.infra.gmfdiag.navigation.NavigableElement;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.UMLPackage;


public class StateRule implements INavigationRule {

	@Override
	public boolean handle(EObject element) {
		return element instanceof State;
	}

	@Override
	public List<NavigableElement> getNextPossibleElements(NavigableElement currentNavElement) {
		List<NavigableElement> nextPossibleElements = new LinkedList<NavigableElement>();

		final State state = (State) currentNavElement.getElement();

		if (state.getDoActivity() != null) {
			nextPossibleElements.add(new ExistingNavigableElement(state.getDoActivity(), UMLPackage.Literals.STATE__DO_ACTIVITY));
		} else {
			UMLRuleHelper.addBehaviorCreatedNavigableElements(nextPossibleElements, currentNavElement, UMLPackage.Literals.STATE__DO_ACTIVITY, new IModelLinker() {

				@Override
				public void linkToModel(EObject toLink) {
					state.setDoActivity((Behavior) toLink);
				}
			});
		}

		if (state.getEntry() != null) {
			nextPossibleElements.add(new ExistingNavigableElement(state.getEntry(), UMLPackage.Literals.STATE__ENTRY));
		} else {
			UMLRuleHelper.addBehaviorCreatedNavigableElements(nextPossibleElements, currentNavElement, UMLPackage.Literals.STATE__ENTRY, new IModelLinker() {

				@Override
				public void linkToModel(EObject toLink) {
					state.setEntry((Behavior) toLink);
				}
			});
		}

		if (state.getExit() != null) {
			nextPossibleElements.add(new ExistingNavigableElement(state.getExit(), UMLPackage.Literals.STATE__EXIT));
		} else {
			UMLRuleHelper.addBehaviorCreatedNavigableElements(nextPossibleElements, currentNavElement, UMLPackage.Literals.STATE__EXIT, new IModelLinker() {

				@Override
				public void linkToModel(EObject toLink) {
					state.setExit((Behavior) toLink);
				}
			});
		}

		return nextPossibleElements;
	}

}
