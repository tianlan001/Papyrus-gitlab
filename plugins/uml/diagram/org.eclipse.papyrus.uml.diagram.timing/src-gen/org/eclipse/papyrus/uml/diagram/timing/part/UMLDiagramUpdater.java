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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gmf.runtime.emf.core.util.CrossReferenceAdapter;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.updater.DiagramUpdater;
import org.eclipse.papyrus.uml.diagram.common.part.ICustomDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.timing.custom.parts.PropertyDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactLifelineCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactLifelineEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactLifelineTimeRulerCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.CompactStateInvariantEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DestructionOccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.DurationObservationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineStateDefinitionCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineTimeRulerCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullLifelineTimelineCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.FullStateInvariantEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.GateEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.GeneralOrderingEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.InteractionCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.InteractionEditPartTN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.LifelineEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageAsyncEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageCreateEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageDeleteEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageFoundEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageLostEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageOccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageReplyEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.MessageSyncEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.OccurrenceSpecificationEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeObservationEditPart;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimeRulerCompartmentEditPartCN;
import org.eclipse.papyrus.uml.diagram.timing.edit.parts.TimingDiagramEditPart;
import org.eclipse.papyrus.uml.diagram.timing.providers.UMLElementTypes;
import org.eclipse.uml2.uml.DestructionOccurrenceSpecification;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Gate;
import org.eclipse.uml2.uml.GeneralOrdering;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.StateInvariant;
import org.eclipse.uml2.uml.TimeConstraint;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLDiagramUpdater implements DiagramUpdater {

	/**
	 * @generated
	 */
	public static final org.eclipse.papyrus.uml.diagram.timing.part.UMLDiagramUpdater INSTANCE = new UMLDiagramUpdater();

	/**
	 * @generated
	 */
	protected UMLDiagramUpdater() {
		// to prevent instantiation allowing the override
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLNodeDescriptor> getSemanticChildren(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case TimingDiagramEditPart.VISUAL_ID:
				return getPackage_TimingDiagram_SemanticChildren(view);
			case InteractionEditPartTN.VISUAL_ID:
				return getInteraction_Shape_SemanticChildren(view);
			case InteractionCompartmentEditPartTN.VISUAL_ID:
				return getInteraction_SubfragmentCompartment_SemanticChildren(view);
			case FullLifelineStateDefinitionCompartmentEditPartCN.VISUAL_ID:
				return getLifeline_FullStateDefinitionCompartment_SemanticChildren(view);
			case FullLifelineTimelineCompartmentEditPartCN.VISUAL_ID:
				return getLifeline_FullSubfragmentCompartment_SemanticChildren(view);
			case CompactLifelineCompartmentEditPartCN.VISUAL_ID:
				return getLifeline_CompactSubfragmentCompartment_SemanticChildren(view);
			case TimeRulerCompartmentEditPartCN.VISUAL_ID:
				return getInteraction_TimeRulerCompartment_SemanticChildren(view);
			case FullLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID:
				return getLifeline_FullTimeRulerCompartment_SemanticChildren(view);
			case CompactLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID:
				return getLifeline_CompactTimeRulerCompartment_SemanticChildren(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_TimingDiagram_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InteractionEditPartTN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInteraction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Interaction modelElement = (Interaction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getFormalGates()
				.iterator(); it.hasNext();) {
			Gate childElement = (Gate) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (GateEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInteraction_SubfragmentCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Interaction modelElement = (Interaction) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getLifelines()
				.iterator(); it.hasNext();) {
			Lifeline childElement = (Lifeline) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (FullLifelineEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CompactLifelineEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getLifeline_FullStateDefinitionCompartment_SemanticChildren(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getLifeline_FullSubfragmentCompartment_SemanticChildren(View view) {
		ICustomDiagramUpdater<UMLNodeDescriptor> customUpdater = new PropertyDiagramUpdater();
		return customUpdater.getSemanticChildren(view);
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getLifeline_CompactSubfragmentCompartment_SemanticChildren(View view) {
		ICustomDiagramUpdater<UMLNodeDescriptor> customUpdater = new PropertyDiagramUpdater();
		return customUpdater.getSemanticChildren(view);
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInteraction_TimeRulerCompartment_SemanticChildren(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getLifeline_FullTimeRulerCompartment_SemanticChildren(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getLifeline_CompactTimeRulerCompartment_SemanticChildren(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getContainedLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case TimingDiagramEditPart.VISUAL_ID:
				return getPackage_TimingDiagram_ContainedLinks(view);
			case InteractionEditPartTN.VISUAL_ID:
				return getInteraction_Shape_ContainedLinks(view);
			case FullLifelineEditPartCN.VISUAL_ID:
				return getLifeline_FullShape_ContainedLinks(view);
			case CompactLifelineEditPartCN.VISUAL_ID:
				return getLifeline_CompactShape_ContainedLinks(view);
			case FullStateInvariantEditPartCN.VISUAL_ID:
				return getStateInvariant_FullShape_ContainedLinks(view);
			case CompactStateInvariantEditPartCN.VISUAL_ID:
				return getStateInvariant_CompactShape_ContainedLinks(view);
			case OccurrenceSpecificationEditPartCN.VISUAL_ID:
				return getOccurrenceSpecification_Shape_ContainedLinks(view);
			case MessageOccurrenceSpecificationEditPartCN.VISUAL_ID:
				return getMessageOccurrenceSpecification_Shape_ContainedLinks(view);
			case TimeConstraintEditPart.VISUAL_ID:
				return getTimeConstraint_Shape_ContainedLinks(view);
			case TimeObservationEditPart.VISUAL_ID:
				return getTimeObservation_Shape_ContainedLinks(view);
			case DurationConstraintEditPartCN.VISUAL_ID:
				return getDurationConstraint_Shape_ContainedLinks(view);
			case DurationObservationEditPartCN.VISUAL_ID:
				return getDurationObservation_Shape_ContainedLinks(view);
			case GeneralOrderingEditPart.VISUAL_ID:
				return getGeneralOrdering_Shape_ContainedLinks(view);
			case DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID:
				return getDestructionOccurrenceSpecification_Shape_ContainedLinks(view);
			case LifelineEditPart.VISUAL_ID:
				return getLifeline_Shape_ContainedLinks(view);
			case GateEditPart.VISUAL_ID:
				return getGate_Shape_ContainedLinks(view);
			case MessageSyncEditPart.VISUAL_ID:
				return getMessage_SynchEdge_ContainedLinks(view);
			case MessageAsyncEditPart.VISUAL_ID:
				return getMessage_AsynchEdge_ContainedLinks(view);
			case MessageReplyEditPart.VISUAL_ID:
				return getMessage_ReplyEdge_ContainedLinks(view);
			case MessageCreateEditPart.VISUAL_ID:
				return getMessage_CreateEdge_ContainedLinks(view);
			case MessageDeleteEditPart.VISUAL_ID:
				return getMessage_DeleteEdge_ContainedLinks(view);
			case MessageLostEditPart.VISUAL_ID:
				return getMessage_LostEdge_ContainedLinks(view);
			case MessageFoundEditPart.VISUAL_ID:
				return getMessage_FoundEdge_ContainedLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getIncomingLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case InteractionEditPartTN.VISUAL_ID:
				return getInteraction_Shape_IncomingLinks(view);
			case FullLifelineEditPartCN.VISUAL_ID:
				return getLifeline_FullShape_IncomingLinks(view);
			case CompactLifelineEditPartCN.VISUAL_ID:
				return getLifeline_CompactShape_IncomingLinks(view);
			case FullStateInvariantEditPartCN.VISUAL_ID:
				return getStateInvariant_FullShape_IncomingLinks(view);
			case CompactStateInvariantEditPartCN.VISUAL_ID:
				return getStateInvariant_CompactShape_IncomingLinks(view);
			case OccurrenceSpecificationEditPartCN.VISUAL_ID:
				return getOccurrenceSpecification_Shape_IncomingLinks(view);
			case MessageOccurrenceSpecificationEditPartCN.VISUAL_ID:
				return getMessageOccurrenceSpecification_Shape_IncomingLinks(view);
			case TimeConstraintEditPart.VISUAL_ID:
				return getTimeConstraint_Shape_IncomingLinks(view);
			case TimeObservationEditPart.VISUAL_ID:
				return getTimeObservation_Shape_IncomingLinks(view);
			case DurationConstraintEditPartCN.VISUAL_ID:
				return getDurationConstraint_Shape_IncomingLinks(view);
			case DurationObservationEditPartCN.VISUAL_ID:
				return getDurationObservation_Shape_IncomingLinks(view);
			case GeneralOrderingEditPart.VISUAL_ID:
				return getGeneralOrdering_Shape_IncomingLinks(view);
			case DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID:
				return getDestructionOccurrenceSpecification_Shape_IncomingLinks(view);
			case LifelineEditPart.VISUAL_ID:
				return getLifeline_Shape_IncomingLinks(view);
			case GateEditPart.VISUAL_ID:
				return getGate_Shape_IncomingLinks(view);
			case MessageSyncEditPart.VISUAL_ID:
				return getMessage_SynchEdge_IncomingLinks(view);
			case MessageAsyncEditPart.VISUAL_ID:
				return getMessage_AsynchEdge_IncomingLinks(view);
			case MessageReplyEditPart.VISUAL_ID:
				return getMessage_ReplyEdge_IncomingLinks(view);
			case MessageCreateEditPart.VISUAL_ID:
				return getMessage_CreateEdge_IncomingLinks(view);
			case MessageDeleteEditPart.VISUAL_ID:
				return getMessage_DeleteEdge_IncomingLinks(view);
			case MessageLostEditPart.VISUAL_ID:
				return getMessage_LostEdge_IncomingLinks(view);
			case MessageFoundEditPart.VISUAL_ID:
				return getMessage_FoundEdge_IncomingLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getOutgoingLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case InteractionEditPartTN.VISUAL_ID:
				return getInteraction_Shape_OutgoingLinks(view);
			case FullLifelineEditPartCN.VISUAL_ID:
				return getLifeline_FullShape_OutgoingLinks(view);
			case CompactLifelineEditPartCN.VISUAL_ID:
				return getLifeline_CompactShape_OutgoingLinks(view);
			case FullStateInvariantEditPartCN.VISUAL_ID:
				return getStateInvariant_FullShape_OutgoingLinks(view);
			case CompactStateInvariantEditPartCN.VISUAL_ID:
				return getStateInvariant_CompactShape_OutgoingLinks(view);
			case OccurrenceSpecificationEditPartCN.VISUAL_ID:
				return getOccurrenceSpecification_Shape_OutgoingLinks(view);
			case MessageOccurrenceSpecificationEditPartCN.VISUAL_ID:
				return getMessageOccurrenceSpecification_Shape_OutgoingLinks(view);
			case TimeConstraintEditPart.VISUAL_ID:
				return getTimeConstraint_Shape_OutgoingLinks(view);
			case TimeObservationEditPart.VISUAL_ID:
				return getTimeObservation_Shape_OutgoingLinks(view);
			case DurationConstraintEditPartCN.VISUAL_ID:
				return getDurationConstraint_Shape_OutgoingLinks(view);
			case DurationObservationEditPartCN.VISUAL_ID:
				return getDurationObservation_Shape_OutgoingLinks(view);
			case GeneralOrderingEditPart.VISUAL_ID:
				return getGeneralOrdering_Shape_OutgoingLinks(view);
			case DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID:
				return getDestructionOccurrenceSpecification_Shape_OutgoingLinks(view);
			case LifelineEditPart.VISUAL_ID:
				return getLifeline_Shape_OutgoingLinks(view);
			case GateEditPart.VISUAL_ID:
				return getGate_Shape_OutgoingLinks(view);
			case MessageSyncEditPart.VISUAL_ID:
				return getMessage_SynchEdge_OutgoingLinks(view);
			case MessageAsyncEditPart.VISUAL_ID:
				return getMessage_AsynchEdge_OutgoingLinks(view);
			case MessageReplyEditPart.VISUAL_ID:
				return getMessage_ReplyEdge_OutgoingLinks(view);
			case MessageCreateEditPart.VISUAL_ID:
				return getMessage_CreateEdge_OutgoingLinks(view);
			case MessageDeleteEditPart.VISUAL_ID:
				return getMessage_DeleteEdge_OutgoingLinks(view);
			case MessageLostEditPart.VISUAL_ID:
				return getMessage_LostEdge_OutgoingLinks(view);
			case MessageFoundEditPart.VISUAL_ID:
				return getMessage_FoundEdge_OutgoingLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_TimingDiagram_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteraction_Shape_ContainedLinks(View view) {
		Interaction modelElement = (Interaction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Message_SynchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Message_AsynchEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Message_ReplyEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Message_CreateEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Message_DeleteEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Message_LostEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLifeline_FullShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLifeline_CompactShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateInvariant_FullShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateInvariant_CompactShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOccurrenceSpecification_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessageOccurrenceSpecification_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeObservation_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationObservation_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralOrdering_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDestructionOccurrenceSpecification_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLifeline_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGate_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_SynchEdge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_AsynchEdge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_ReplyEdge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_CreateEdge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_DeleteEdge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_LostEdge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_FoundEdge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteraction_Shape_IncomingLinks(View view) {
		Interaction modelElement = (Interaction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLifeline_FullShape_IncomingLinks(View view) {
		Lifeline modelElement = (Lifeline) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLifeline_CompactShape_IncomingLinks(View view) {
		Lifeline modelElement = (Lifeline) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateInvariant_FullShape_IncomingLinks(View view) {
		StateInvariant modelElement = (StateInvariant) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateInvariant_CompactShape_IncomingLinks(View view) {
		StateInvariant modelElement = (StateInvariant) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOccurrenceSpecification_Shape_IncomingLinks(View view) {
		OccurrenceSpecification modelElement = (OccurrenceSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessageOccurrenceSpecification_Shape_IncomingLinks(View view) {
		MessageOccurrenceSpecification modelElement = (MessageOccurrenceSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_SynchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_AsynchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_ReplyEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_CreateEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_DeleteEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_FoundEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_Shape_IncomingLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeObservation_Shape_IncomingLinks(View view) {
		TimeObservation modelElement = (TimeObservation) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_Shape_IncomingLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationObservation_Shape_IncomingLinks(View view) {
		DurationObservation modelElement = (DurationObservation) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralOrdering_Shape_IncomingLinks(View view) {
		GeneralOrdering modelElement = (GeneralOrdering) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDestructionOccurrenceSpecification_Shape_IncomingLinks(View view) {
		DestructionOccurrenceSpecification modelElement = (DestructionOccurrenceSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_SynchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_AsynchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_ReplyEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_CreateEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_DeleteEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_FoundEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLifeline_Shape_IncomingLinks(View view) {
		Lifeline modelElement = (Lifeline) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGate_Shape_IncomingLinks(View view) {
		Gate modelElement = (Gate) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_SynchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_AsynchEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_ReplyEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_CreateEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_DeleteEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Message_FoundEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_SynchEdge_IncomingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_AsynchEdge_IncomingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_ReplyEdge_IncomingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_CreateEdge_IncomingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_DeleteEdge_IncomingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_LostEdge_IncomingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_FoundEdge_IncomingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_Message_LostEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteraction_Shape_OutgoingLinks(View view) {
		Interaction modelElement = (Interaction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLifeline_FullShape_OutgoingLinks(View view) {
		Lifeline modelElement = (Lifeline) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLifeline_CompactShape_OutgoingLinks(View view) {
		Lifeline modelElement = (Lifeline) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateInvariant_FullShape_OutgoingLinks(View view) {
		StateInvariant modelElement = (StateInvariant) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateInvariant_CompactShape_OutgoingLinks(View view) {
		StateInvariant modelElement = (StateInvariant) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOccurrenceSpecification_Shape_OutgoingLinks(View view) {
		OccurrenceSpecification modelElement = (OccurrenceSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessageOccurrenceSpecification_Shape_OutgoingLinks(View view) {
		MessageOccurrenceSpecification modelElement = (MessageOccurrenceSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_SynchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_AsynchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_ReplyEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_CreateEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_DeleteEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_LostEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_Shape_OutgoingLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeObservation_Shape_OutgoingLinks(View view) {
		TimeObservation modelElement = (TimeObservation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_Shape_OutgoingLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationObservation_Shape_OutgoingLinks(View view) {
		DurationObservation modelElement = (DurationObservation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralOrdering_Shape_OutgoingLinks(View view) {
		GeneralOrdering modelElement = (GeneralOrdering) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDestructionOccurrenceSpecification_Shape_OutgoingLinks(View view) {
		DestructionOccurrenceSpecification modelElement = (DestructionOccurrenceSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_SynchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_AsynchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_ReplyEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_CreateEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_DeleteEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_LostEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLifeline_Shape_OutgoingLinks(View view) {
		Lifeline modelElement = (Lifeline) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGate_Shape_OutgoingLinks(View view) {
		Gate modelElement = (Gate) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_SynchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_AsynchEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_ReplyEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_CreateEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_DeleteEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_LostEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_SynchEdge_OutgoingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_AsynchEdge_OutgoingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_ReplyEdge_OutgoingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_CreateEdge_OutgoingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_DeleteEdge_OutgoingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_LostEdge_OutgoingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMessage_FoundEdge_OutgoingLinks(View view) {
		Message modelElement = (Message) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_Message_FoundEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Message_SynchEdge(Interaction container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageSyncEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_SynchEdge, MessageSyncEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Message_AsynchEdge(Interaction container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageAsyncEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_AsynchEdge, MessageAsyncEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Message_ReplyEdge(Interaction container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageReplyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_ReplyEdge, MessageReplyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Message_CreateEdge(Interaction container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageCreateEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_CreateEdge, MessageCreateEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Message_DeleteEdge(Interaction container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageDeleteEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_DeleteEdge, MessageDeleteEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Message_LostEdge(Interaction container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageLostEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Element dst = link.getOwner();
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_LostEdge, MessageLostEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Message_FoundEdge(Interaction container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageFoundEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			Element src = link.getOwner();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_FoundEdge, MessageFoundEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Message_SynchEdge(MessageEnd target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getMessage_SendEvent() || false == setting.getEObject() instanceof Message) {
				continue;
			}
			Message link = (Message) setting.getEObject();
			if (!MessageSyncEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Message_SynchEdge, MessageSyncEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Message_AsynchEdge(MessageEnd target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getMessage_SendEvent() || false == setting.getEObject() instanceof Message) {
				continue;
			}
			Message link = (Message) setting.getEObject();
			if (!MessageAsyncEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Message_AsynchEdge, MessageAsyncEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Message_ReplyEdge(MessageEnd target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getMessage_SendEvent() || false == setting.getEObject() instanceof Message) {
				continue;
			}
			Message link = (Message) setting.getEObject();
			if (!MessageReplyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Message_ReplyEdge, MessageReplyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Message_CreateEdge(MessageEnd target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getMessage_SendEvent() || false == setting.getEObject() instanceof Message) {
				continue;
			}
			Message link = (Message) setting.getEObject();
			if (!MessageCreateEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Message_CreateEdge, MessageCreateEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Message_DeleteEdge(MessageEnd target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getMessage_SendEvent() || false == setting.getEObject() instanceof Message) {
				continue;
			}
			Message link = (Message) setting.getEObject();
			if (!MessageDeleteEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Message_DeleteEdge, MessageDeleteEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Message_LostEdge(Element target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getElement_Owner() || false == setting.getEObject() instanceof Message) {
				continue;
			}
			Message link = (Message) setting.getEObject();
			if (!MessageLostEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd src = link.getReceiveEvent();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Message_LostEdge, MessageLostEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Message_FoundEdge(MessageEnd target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getMessage_SendEvent() || false == setting.getEObject() instanceof Message) {
				continue;
			}
			Message link = (Message) setting.getEObject();
			if (!MessageFoundEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Element src = link.getOwner();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Message_FoundEdge, MessageFoundEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Message_SynchEdge(MessageEnd source) {
		Interaction container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Interaction) {
				container = (Interaction) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageSyncEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			MessageEnd src = link.getReceiveEvent();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_SynchEdge, MessageSyncEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Message_AsynchEdge(MessageEnd source) {
		Interaction container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Interaction) {
				container = (Interaction) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageAsyncEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			MessageEnd src = link.getReceiveEvent();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_AsynchEdge, MessageAsyncEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Message_ReplyEdge(MessageEnd source) {
		Interaction container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Interaction) {
				container = (Interaction) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageReplyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			MessageEnd src = link.getReceiveEvent();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_ReplyEdge, MessageReplyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Message_CreateEdge(MessageEnd source) {
		Interaction container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Interaction) {
				container = (Interaction) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageCreateEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			MessageEnd src = link.getReceiveEvent();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_CreateEdge, MessageCreateEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Message_DeleteEdge(MessageEnd source) {
		Interaction container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Interaction) {
				container = (Interaction) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageDeleteEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			MessageEnd src = link.getReceiveEvent();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_DeleteEdge, MessageDeleteEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Message_LostEdge(MessageEnd source) {
		Interaction container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Interaction) {
				container = (Interaction) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageLostEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Element dst = link.getOwner();
			MessageEnd src = link.getReceiveEvent();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_LostEdge, MessageLostEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Message_FoundEdge(Element source) {
		Interaction container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Interaction) {
				container = (Interaction) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getMessages()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Message) {
				continue;
			}
			Message link = (Message) linkObject;
			if (!MessageFoundEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			MessageEnd dst = link.getSendEvent();
			Element src = link.getOwner();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Message_FoundEdge, MessageFoundEditPart.VISUAL_ID));
		}
		return result;
	}
}
