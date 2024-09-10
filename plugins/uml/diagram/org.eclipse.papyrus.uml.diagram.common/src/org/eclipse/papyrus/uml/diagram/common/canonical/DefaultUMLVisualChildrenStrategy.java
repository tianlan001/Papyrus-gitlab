/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.common.canonical;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.canonical.strategy.IVisualChildrenStrategy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.AutomaticNotationEditPolicy;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

import com.google.common.collect.ImmutableSet;

/**
 * Default strategy for calculation of child views in the UML domain. This accounts for the compartments and labels
 * that present applied stereotypes, which have nothing to do with model-view synchronization.
 */
public class DefaultUMLVisualChildrenStrategy implements IVisualChildrenStrategy {
	/** View types that are managed by other edit-policies. */
	private static final Set<String> excludedViewTypes = new HashSet<>();

	public DefaultUMLVisualChildrenStrategy() {
		super();
	}

	/**
	 * Registers view types that should be ignored by the CanonicalEditPolicy for
	 * the purposes of view creation and deletion, because (usually) they are managed
	 * {@linkplain AutomaticNotationEditPolicy automatically} by other edit-policies.
	 * 
	 * @param viewTypes
	 *            view types to be excluded from canonical management
	 */
	public static void registerExcludedViewTypes(Collection<String> viewTypes) {
		excludedViewTypes.addAll(viewTypes);
	}

	@Override
	public List<? extends View> getCanonicalChildren(EditPart editPart, View view) {
		List<? extends View> result = DEFAULT.getCanonicalChildren(editPart, view);

		// Filter out child views that represent applied stereotypes
		final EObject semantic = ((IGraphicalEditPart) editPart).resolveSemanticElement();
		final Set<Stereotype> appliedStereotypes = (semantic instanceof Element)
				? ImmutableSet.copyOf(((Element) semantic).getAppliedStereotypes())
				: Collections.<Stereotype> emptySet();

		// And the transient views that support CSS-driven presentation
		result.removeIf(v -> excludedViewTypes.contains(v.getType())
				|| appliedStereotypes.contains(v.getElement()));

		return result;
	}

	@Override
	public List<? extends View> getCanonicalEdges(EditPart editPart, View view) {
		return DEFAULT.getCanonicalEdges(editPart, view);
	}

}
