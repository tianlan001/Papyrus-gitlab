/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.statemachine.custom.strategies;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLSemanticChildrenStrategy;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.TransitionKind;

/**
 * @since 3.1
 *
 */
public class StateSemanticConnectionsStrategy extends DefaultUMLSemanticChildrenStrategy {

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLSemanticChildrenStrategy#getCanonicalSemanticConnections(org.eclipse.emf.ecore.EObject, org.eclipse.gmf.runtime.notation.View)
	 *
	 * @param semanticFromEditPart
	 * @param viewFromEditPart
	 * @return
	 */
	@Override
	public List<? extends EObject> getCanonicalSemanticConnections(EObject semanticFromEditPart, View viewFromEditPart) {
		List<? extends EObject> result = super.getCanonicalSemanticConnections(semanticFromEditPart, viewFromEditPart);
		List<Transition> toRemove = new ArrayList<>();
		for (EObject object : result) {
			if (object instanceof Transition) {
				Transition transition = (Transition) object;
				if (transition.getKind().getValue() == TransitionKind.INTERNAL) {
					toRemove.add(transition);
				}
			}
		}
		result.removeAll(toRemove);
		return result;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.canonical.DefaultUMLSemanticChildrenStrategy#getCanonicalSemanticChildren(org.eclipse.emf.ecore.EObject, org.eclipse.gmf.runtime.notation.View)
	 *
	 * @param semanticFromEditPart
	 * @param viewFromEditPart
	 * @return
	 */
	@Override
	public List<? extends EObject> getCanonicalSemanticChildren(EObject semanticFromEditPart, View viewFromEditPart) {
		List<EObject> result = (List<EObject>) super.getCanonicalSemanticChildren(semanticFromEditPart, viewFromEditPart);
		List<Transition> toAdd = new ArrayList<>();
		if (semanticFromEditPart instanceof State) {
			State state = (State) semanticFromEditPart;
			for (Transition transition : state.getOutgoings()) {
				if (transition.getKind().getValue() == TransitionKind.INTERNAL) {
					toAdd.add(transition);
				}
			}
		}
		result.addAll(toAdd);
		return result;
	}
}
