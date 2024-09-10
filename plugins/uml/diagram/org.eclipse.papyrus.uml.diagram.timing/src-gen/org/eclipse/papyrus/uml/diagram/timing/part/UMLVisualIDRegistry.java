/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 */
package org.eclipse.papyrus.uml.diagram.timing.part;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.structure.DiagramStructure;
import org.eclipse.papyrus.uml.diagram.timing.custom.utils.LifelineUtils;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactLifelineCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactLifelineEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactLifelineNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactLifelineTimeRulerCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactStateInvariantEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactStateInvariantNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DestructionOccurrenceSpecificationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DestructionOccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DestructionOccurrenceSpecificationLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationConstraintSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationObservationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationObservationNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FreeTimeRulerCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FreeTimingRulerEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineStateDefinitionCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineTimeRulerCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineTimelineCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantVerticalLineEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.GateEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.GateLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.GeneralOrderingEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.GeneralOrderingNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.InteractionCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.InteractionEditPartTN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.InteractionNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.LinearTimeRulerCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.LinearTimingRulerEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageAsyncAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageAsyncEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageAsyncNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageCreateAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageCreateEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageCreateNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageDeleteAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageDeleteEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageDeleteNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageFoundAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageFoundEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageFoundNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageLostAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageLostEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageLostNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageOccurrenceSpecificationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageOccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageOccurrenceSpecificationLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageReplyAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageReplyEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageReplyNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageSyncAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageSyncEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageSyncNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.OccurrenceSpecificationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.OccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.OccurrenceSpecificationLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.StateDefinitionEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.StateDefinitionLabelEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.StateInvariantAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TickEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TickNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeConstraintAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeConstraintSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeObservationAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeObservationEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeObservationNameEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeRulerCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimingDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.timing.expressions.UMLOCLFactory;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * This registry is used to determine which type of visual object should be
 * created for the corresponding Diagram, Node, ChildNode or Link represented
 * by a domain model object.
 *
 * @generated
 */
public class UMLVisualIDRegistry {

	// Uncomment for debug purpose ?
	// /**
	// * @generated
	// */
	// private static final String DEBUG_KEY = "org.eclipse.papyrus.uml.diagram.timing/debug/visualID"; //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static String getVisualID(View view) {
		if (view instanceof Diagram) {
			if (TimingDiagramEditPart.MODEL_ID.equals(view.getType())) {
				return TimingDiagramEditPart.VISUAL_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		return org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.getVisualID(view.getType());
	}

	/**
	 * @generated
	 */
	public static String getModelID(View view) {
		View diagram = view.getDiagram();
		while (view != diagram) {
			EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
			if (annotation != null) {
				return annotation.getDetails().get("modelID"); //$NON-NLS-1$
			}
			view = (View) view.eContainer();
		}
		return diagram != null ? diagram.getType() : null;
	}

	/**
	 * @generated
	 */
	public static String getVisualID(String type) {
		return type;
	}

	/**
	 * @generated
	 */
	public static String getType(String visualID) {
		return visualID;
	}

	/**
	 * @generated
	 */
	public static String getDiagramVisualID(EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		return TimingDiagramEditPart.VISUAL_ID;
	}

	/**
	 * @generated
	 */
	public static String getNodeVisualID(View containerView, EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		String containerModelID = org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.getModelID(containerView);
		if (!TimingDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			return ""; //$NON-NLS-1$
		}
		String containerVisualID;
		if (TimingDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = TimingDiagramEditPart.VISUAL_ID;
			} else {
				return ""; //$NON-NLS-1$
			}
		}
		if (containerVisualID != null) {
			switch (containerVisualID) {
			case TimingDiagramEditPart.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getInteraction().isSuperTypeOf(domainElement.eClass())) {
					return InteractionEditPartTN.VISUAL_ID;
				}
				break;
			case InteractionEditPartTN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getGate().isSuperTypeOf(domainElement.eClass())) {
					return GateEditPart.VISUAL_ID;
				}
				break;
			case InteractionCompartmentEditPartTN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getLifeline().isSuperTypeOf(domainElement.eClass()) && isLifeline_FullShape(containerView, (Lifeline) domainElement)) {
					return FullLifelineEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getLifeline().isSuperTypeOf(domainElement.eClass()) && isLifeline_CompactShape(containerView, (Lifeline) domainElement)) {
					return CompactLifelineEditPartCN.VISUAL_ID;
				}
				break;
			case FullLifelineTimelineCompartmentEditPartCN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getStateInvariant().isSuperTypeOf(domainElement.eClass())) {
					return FullStateInvariantEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getOccurrenceSpecification().isSuperTypeOf(domainElement.eClass())) {
					return OccurrenceSpecificationEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getMessageOccurrenceSpecification().isSuperTypeOf(domainElement.eClass())) {
					return MessageOccurrenceSpecificationEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDestructionOccurrenceSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDurationConstraint().isSuperTypeOf(domainElement.eClass())) {
					return DurationConstraintEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDurationObservation().isSuperTypeOf(domainElement.eClass())) {
					return DurationObservationEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getTimeConstraint().isSuperTypeOf(domainElement.eClass())) {
					return TimeConstraintEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getTimeObservation().isSuperTypeOf(domainElement.eClass())) {
					return TimeObservationEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getGeneralOrdering().isSuperTypeOf(domainElement.eClass())) {
					return GeneralOrderingEditPart.VISUAL_ID;
				}
				break;
			case CompactLifelineCompartmentEditPartCN.VISUAL_ID:
				if (UMLPackage.eINSTANCE.getStateInvariant().isSuperTypeOf(domainElement.eClass())) {
					return CompactStateInvariantEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getOccurrenceSpecification().isSuperTypeOf(domainElement.eClass())) {
					return OccurrenceSpecificationEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getMessageOccurrenceSpecification().isSuperTypeOf(domainElement.eClass())) {
					return MessageOccurrenceSpecificationEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDestructionOccurrenceSpecification().isSuperTypeOf(domainElement.eClass())) {
					return DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDurationConstraint().isSuperTypeOf(domainElement.eClass())) {
					return DurationConstraintEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getDurationObservation().isSuperTypeOf(domainElement.eClass())) {
					return DurationObservationEditPartCN.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getTimeConstraint().isSuperTypeOf(domainElement.eClass())) {
					return TimeConstraintEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getTimeObservation().isSuperTypeOf(domainElement.eClass())) {
					return TimeObservationEditPart.VISUAL_ID;
				}
				if (UMLPackage.eINSTANCE.getGeneralOrdering().isSuperTypeOf(domainElement.eClass())) {
					return GeneralOrderingEditPart.VISUAL_ID;
				}
				break;
			}
		}
		return ""; //$NON-NLS-1$
	}

	/**
	 * @generated
	 */
	public static boolean canCreateNode(View containerView, String nodeVisualID) {
		String containerModelID = org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.getModelID(containerView);
		if (!TimingDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			return false;
		}
		String containerVisualID;
		if (TimingDiagramEditPart.MODEL_ID.equals(containerModelID)) {
			containerVisualID = org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.getVisualID(containerView);
		} else {
			if (containerView instanceof Diagram) {
				containerVisualID = TimingDiagramEditPart.VISUAL_ID;
			} else {
				return false;
			}
		}
		if (containerVisualID != null) {
			switch (containerVisualID) {
			case TimingDiagramEditPart.VISUAL_ID:
				if (InteractionEditPartTN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case InteractionEditPartTN.VISUAL_ID:
				if (InteractionNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (InteractionCompartmentEditPartTN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TimeRulerCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (GateEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case FullLifelineEditPartCN.VISUAL_ID:
				if (FullLifelineNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (FullLifelineStateDefinitionCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (FullLifelineTimelineCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (FullLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case CompactLifelineEditPartCN.VISUAL_ID:
				if (CompactLifelineNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CompactLifelineCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CompactLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case FullStateInvariantEditPartCN.VISUAL_ID:
				if (FullStateInvariantAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case CompactStateInvariantEditPartCN.VISUAL_ID:
				if (CompactStateInvariantNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (StateInvariantAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case OccurrenceSpecificationEditPartCN.VISUAL_ID:
				if (OccurrenceSpecificationLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (OccurrenceSpecificationAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MessageOccurrenceSpecificationEditPartCN.VISUAL_ID:
				if (MessageOccurrenceSpecificationLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MessageOccurrenceSpecificationAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case StateDefinitionEditPart.VISUAL_ID:
				if (StateDefinitionLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case TimeConstraintEditPart.VISUAL_ID:
				if (TimeConstraintSpecificationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TimeConstraintAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case TimeObservationEditPart.VISUAL_ID:
				if (TimeObservationNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TimeObservationAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DurationConstraintEditPartCN.VISUAL_ID:
				if (DurationConstraintSpecificationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DurationObservationEditPartCN.VISUAL_ID:
				if (DurationObservationNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case GeneralOrderingEditPart.VISUAL_ID:
				if (GeneralOrderingNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case FreeTimingRulerEditPartCN.VISUAL_ID:
				if (FreeTimeRulerCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case LinearTimingRulerEditPartCN.VISUAL_ID:
				if (LinearTimeRulerCompartmentEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case TickEditPart.VISUAL_ID:
				if (TickNameEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID:
				if (DestructionOccurrenceSpecificationLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DestructionOccurrenceSpecificationAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case GateEditPart.VISUAL_ID:
				if (GateLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case InteractionCompartmentEditPartTN.VISUAL_ID:
				if (FullLifelineEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (CompactLifelineEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case FullLifelineStateDefinitionCompartmentEditPartCN.VISUAL_ID:
				if (StateDefinitionEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case FullLifelineTimelineCompartmentEditPartCN.VISUAL_ID:
				if (FullStateInvariantEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (OccurrenceSpecificationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MessageOccurrenceSpecificationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DurationConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DurationObservationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (FullStateInvariantVerticalLineEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TimeConstraintEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TimeObservationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (GeneralOrderingEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case CompactLifelineCompartmentEditPartCN.VISUAL_ID:
				if (CompactStateInvariantEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (OccurrenceSpecificationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MessageOccurrenceSpecificationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DurationConstraintEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (DurationObservationEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TimeConstraintEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (TimeObservationEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (GeneralOrderingEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case TimeRulerCompartmentEditPartCN.VISUAL_ID:
				if (FreeTimingRulerEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (LinearTimingRulerEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case FullLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID:
				if (FreeTimingRulerEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case CompactLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID:
				if (FreeTimingRulerEditPartCN.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case FreeTimeRulerCompartmentEditPart.VISUAL_ID:
				if (TickEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case LinearTimeRulerCompartmentEditPart.VISUAL_ID:
				if (TickEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MessageSyncEditPart.VISUAL_ID:
				if (MessageSyncNameLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MessageSyncAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MessageAsyncEditPart.VISUAL_ID:
				if (MessageAsyncNameLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MessageAsyncAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MessageReplyEditPart.VISUAL_ID:
				if (MessageReplyNameLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MessageReplyAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MessageCreateEditPart.VISUAL_ID:
				if (MessageCreateNameLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MessageCreateAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MessageDeleteEditPart.VISUAL_ID:
				if (MessageDeleteNameLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MessageDeleteAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MessageLostEditPart.VISUAL_ID:
				if (MessageLostNameLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MessageLostAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			case MessageFoundEditPart.VISUAL_ID:
				if (MessageFoundNameLabelEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				if (MessageFoundAppliedStereotypeEditPart.VISUAL_ID.equals(nodeVisualID)) {
					return true;
				}
				break;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static String getLinkWithClassVisualID(EObject domainElement) {
		if (domainElement == null) {
			return ""; //$NON-NLS-1$
		}
		if (UMLPackage.eINSTANCE.getMessage().isSuperTypeOf(domainElement.eClass()) && isMessage_SynchEdge((Message) domainElement)) {
			return MessageSyncEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getMessage().isSuperTypeOf(domainElement.eClass()) && isMessage_AsynchEdge((Message) domainElement)) {
			return MessageAsyncEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getMessage().isSuperTypeOf(domainElement.eClass()) && isMessage_ReplyEdge((Message) domainElement)) {
			return MessageReplyEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getMessage().isSuperTypeOf(domainElement.eClass()) && isMessage_CreateEdge((Message) domainElement)) {
			return MessageCreateEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getMessage().isSuperTypeOf(domainElement.eClass()) && isMessage_DeleteEdge((Message) domainElement)) {
			return MessageDeleteEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getMessage().isSuperTypeOf(domainElement.eClass()) && isMessage_LostEdge((Message) domainElement)) {
			return MessageLostEditPart.VISUAL_ID;
		}
		if (UMLPackage.eINSTANCE.getMessage().isSuperTypeOf(domainElement.eClass()) && isMessage_FoundEdge((Message) domainElement)) {
			return MessageFoundEditPart.VISUAL_ID;
		}
		return ""; //$NON-NLS-1$
	}

	// Uncomment for debug purpose ?
	// /**
	// * User can change implementation of this method to handle some specific
	// * situations not covered by default logic.
	// *
	// * @generated
	// */
	// private static boolean isDiagram(Package element) {
	// return true;
	// }

	/**
	 * @generated
	 */
	private static boolean isLifeline_FullShape(View containerView, Lifeline domainElement) {
		return LifelineUtils.getLifelineType(domainElement) == LifelineUtils.LifelineType.full;
	}

	/**
	 * @generated
	 */
	private static boolean isLifeline_CompactShape(View containerView, Lifeline domainElement) {
		return LifelineUtils.getLifelineType(domainElement) == LifelineUtils.LifelineType.compact;
	}

	/**
	 * @generated
	 */
	private static boolean isMessage_SynchEdge(Message domainElement) {
		Object result = UMLOCLFactory.getExpression(0, UMLPackage.eINSTANCE.getMessage(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isMessage_AsynchEdge(Message domainElement) {
		Object result = UMLOCLFactory.getExpression(1, UMLPackage.eINSTANCE.getMessage(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isMessage_ReplyEdge(Message domainElement) {
		Object result = UMLOCLFactory.getExpression(2, UMLPackage.eINSTANCE.getMessage(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isMessage_CreateEdge(Message domainElement) {
		Object result = UMLOCLFactory.getExpression(3, UMLPackage.eINSTANCE.getMessage(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isMessage_DeleteEdge(Message domainElement) {
		Object result = UMLOCLFactory.getExpression(4, UMLPackage.eINSTANCE.getMessage(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isMessage_LostEdge(Message domainElement) {
		Object result = UMLOCLFactory.getExpression(5, UMLPackage.eINSTANCE.getMessage(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	private static boolean isMessage_FoundEdge(Message domainElement) {
		Object result = UMLOCLFactory.getExpression(6, UMLPackage.eINSTANCE.getMessage(), null).evaluate(domainElement);
		return result instanceof Boolean && ((Boolean) result).booleanValue();
	}

	/**
	 * @generated
	 */
	public static boolean checkNodeVisualID(View containerView, EObject domainElement, String candidate) {
		if (candidate == null) {
			// unrecognized id is always bad
			return false;
		}
		String basic = getNodeVisualID(containerView, domainElement);
		return candidate.equals(basic);
	}

	/**
	 * @generated
	 */
	public static boolean isCompartmentVisualID(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case InteractionCompartmentEditPartTN.VISUAL_ID:
			case FullLifelineStateDefinitionCompartmentEditPartCN.VISUAL_ID:
			case FullLifelineTimelineCompartmentEditPartCN.VISUAL_ID:
			case CompactLifelineCompartmentEditPartCN.VISUAL_ID:
			case TimeRulerCompartmentEditPartCN.VISUAL_ID:
			case FullLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID:
			case CompactLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID:
			case FreeTimeRulerCompartmentEditPart.VISUAL_ID:
			case LinearTimeRulerCompartmentEditPart.VISUAL_ID:
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static boolean isSemanticLeafVisualID(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case TimingDiagramEditPart.VISUAL_ID:
				return false;
			case StateDefinitionEditPart.VISUAL_ID:
			case FullStateInvariantEditPartCN.VISUAL_ID:
			case OccurrenceSpecificationEditPartCN.VISUAL_ID:
			case MessageOccurrenceSpecificationEditPartCN.VISUAL_ID:
			case TimeConstraintEditPart.VISUAL_ID:
			case TimeObservationEditPart.VISUAL_ID:
			case DurationObservationEditPartCN.VISUAL_ID:
			case DurationConstraintEditPartCN.VISUAL_ID:
			case FreeTimingRulerEditPartCN.VISUAL_ID:
			case LinearTimingRulerEditPartCN.VISUAL_ID:
			case TickEditPart.VISUAL_ID:
			case DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID:
			case CompactStateInvariantEditPartCN.VISUAL_ID:
			case FullStateInvariantVerticalLineEditPart.VISUAL_ID:
			case LifelineEditPart.VISUAL_ID:
			case GeneralOrderingEditPart.VISUAL_ID:
			case GateEditPart.VISUAL_ID:
				return true;
			}
		}
		return false;
	}

	/**
	 * @generated
	 */
	public static final DiagramStructure TYPED_INSTANCE = new DiagramStructure() {
		/**
		 * @generated
		 */
		@Override
		public String getVisualID(View view) {
			return org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.getVisualID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getModelID(View view) {
			return org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.getModelID(view);
		}

		/**
		 * @generated
		 */
		@Override
		public String getNodeVisualID(View containerView, EObject domainElement) {
			return org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean checkNodeVisualID(View containerView, EObject domainElement, String candidate) {
			return org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.checkNodeVisualID(containerView, domainElement, candidate);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isCompartmentVisualID(String visualID) {
			return org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.isCompartmentVisualID(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public boolean isSemanticLeafVisualID(String visualID) {
			return org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry.isSemanticLeafVisualID(visualID);
		}
	};
}
