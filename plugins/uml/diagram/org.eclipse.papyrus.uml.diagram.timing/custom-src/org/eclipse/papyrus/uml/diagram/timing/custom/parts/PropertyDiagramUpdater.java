/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom.parts;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.part.ICustomDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactStateInvariantEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DestructionOccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationObservationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageOccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.OccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.part.UMLNodeDescriptor;
import org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;

public class PropertyDiagramUpdater implements ICustomDiagramUpdater<UMLNodeDescriptor> {

	@Override
	public List<UMLNodeDescriptor> getSemanticChildren(final View view) {
		return null;
	}

	public static List<UMLNodeDescriptor> getLifeline_FullSubfragmentCompartment_SemanticChildren(final View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		final View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		final Lifeline modelElement = (Lifeline) containerView.getElement();
		final LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		if ((modelElement).getCoveredBys().size() > 0) {
			final Interaction interaction = modelElement.getInteraction();
			for (final Object element : interaction.getFragments()) {
				final InteractionFragment childElement = (InteractionFragment) element;
				final String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
				if (FullStateInvariantEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
				if (OccurrenceSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
				if (MessageOccurrenceSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
				if (DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
			}
		}
		if ((modelElement).getCoveredBys().size() > 0) {
			final Interaction interaction = modelElement.getInteraction();
			for (final Object element : interaction.getOwnedRules()) {
				final Constraint childElement = (Constraint) element;
				final String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
				if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
				if (DurationObservationEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
			}
		}
		return result;
	}

	public static List<UMLNodeDescriptor> createLifeline_CompactSubfragmentCompartment_Children(final View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		final View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		final Lifeline modelElement = (Lifeline) containerView.getElement();
		final LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		if ((modelElement).getCoveredBys().size() > 0) {
			final Interaction interaction = modelElement.getInteraction();
			for (final Object element : interaction.getFragments()) {
				final InteractionFragment childElement = (InteractionFragment) element;
				final String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
				if (CompactStateInvariantEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
				if (OccurrenceSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
				if (MessageOccurrenceSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
				if (DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
			}
		}
		if ((modelElement).getCoveredBys().size() > 0) {
			final Interaction interaction = modelElement.getInteraction();
			for (final Object element : interaction.getOwnedRules()) {
				final Constraint childElement = (Constraint) element;
				final String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
				if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
				if (DurationObservationEditPartCN.VISUAL_ID.equals(visualID)) {
					result.add(new UMLNodeDescriptor(childElement, visualID));
					continue;
				}
			}
		}
		return result;
	}
}
