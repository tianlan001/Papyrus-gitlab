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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import org.eclipse.gmf.runtime.diagram.core.services.ViewService;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
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
import org.eclipse.uml2.uml.Package;

/**
 * @generated
 */
public class UMLDiagramContentInitializer {

	/**
	 * @generated
	 */
	private Map myDomain2NotationMap = new HashMap();

	/**
	 * @generated
	 */
	private Collection myLinkDescriptors = new LinkedList();

	/**
	 * @generated
	 */
	public void initDiagramContent(Diagram diagram) {
		if (!TimingDiagramEditPart.MODEL_ID.equals(diagram.getType())) {
			UMLDiagramEditorPlugin.getInstance().logError("Incorrect diagram passed as a parameter: " + diagram.getType());
			return;
		}
		if (false == diagram.getElement() instanceof Package) {
			UMLDiagramEditorPlugin.getInstance().logError("Incorrect diagram element specified: " + diagram.getElement() + " instead of Package");
			return;
		}
		createPackage_TimingDiagram_Children(diagram);
		createLinks(diagram);
	}

	/**
	 * @generated
	 */
	private void createPackage_TimingDiagram_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getPackage_TimingDiagram_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createInteraction_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getInteraction_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getInteraction_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createInteraction_SubfragmentCompartment_Children(getCompartment(view, InteractionCompartmentEditPartTN.VISUAL_ID));
		createInteraction_TimeRulerCompartment_Children(getCompartment(view, TimeRulerCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createLifeline_FullShape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getLifeline_FullShape_OutgoingLinks(view));
		createLifeline_FullStateDefinitionCompartment_Children(getCompartment(view, FullLifelineStateDefinitionCompartmentEditPartCN.VISUAL_ID));
		createLifeline_FullSubfragmentCompartment_Children(getCompartment(view, FullLifelineTimelineCompartmentEditPartCN.VISUAL_ID));
		createLifeline_FullTimeRulerCompartment_Children(getCompartment(view, FullLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createLifeline_CompactShape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getLifeline_CompactShape_OutgoingLinks(view));
		createLifeline_CompactSubfragmentCompartment_Children(getCompartment(view, CompactLifelineCompartmentEditPartCN.VISUAL_ID));
		createLifeline_CompactTimeRulerCompartment_Children(getCompartment(view, CompactLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createStateInvariant_FullShape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getStateInvariant_FullShape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createStateInvariant_CompactShape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getStateInvariant_CompactShape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createOccurrenceSpecification_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getOccurrenceSpecification_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createMessageOccurrenceSpecification_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getMessageOccurrenceSpecification_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createTimeConstraint_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getTimeConstraint_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createTimeObservation_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getTimeObservation_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createDurationConstraint_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDurationConstraint_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createDurationObservation_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDurationObservation_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createGeneralOrdering_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getGeneralOrdering_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createDestructionOccurrenceSpecification_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDestructionOccurrenceSpecification_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createLifeline_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getLifeline_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createGate_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getGate_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createInteraction_SubfragmentCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getInteraction_SubfragmentCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createLifeline_FullStateDefinitionCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getLifeline_FullStateDefinitionCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createLifeline_FullSubfragmentCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getLifeline_FullSubfragmentCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createLifeline_CompactSubfragmentCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getLifeline_CompactSubfragmentCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createInteraction_TimeRulerCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getInteraction_TimeRulerCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createLifeline_FullTimeRulerCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getLifeline_FullTimeRulerCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createLifeline_CompactTimeRulerCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getLifeline_CompactTimeRulerCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createNode(View parentView, UMLNodeDescriptor nodeDescriptor) {
		final String nodeType = UMLVisualIDRegistry.getType(nodeDescriptor.getVisualID());
		Node node = ViewService.createNode(parentView, nodeDescriptor.getModelElement(), nodeType, UMLDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
		switch (nodeDescriptor.getVisualID()) {
		case InteractionEditPartTN.VISUAL_ID:
			createInteraction_Shape_Children(node);
			return;
		case FullLifelineEditPartCN.VISUAL_ID:
			createLifeline_FullShape_Children(node);
			return;
		case CompactLifelineEditPartCN.VISUAL_ID:
			createLifeline_CompactShape_Children(node);
			return;
		case FullStateInvariantEditPartCN.VISUAL_ID:
			createStateInvariant_FullShape_Children(node);
			return;
		case CompactStateInvariantEditPartCN.VISUAL_ID:
			createStateInvariant_CompactShape_Children(node);
			return;
		case OccurrenceSpecificationEditPartCN.VISUAL_ID:
			createOccurrenceSpecification_Shape_Children(node);
			return;
		case MessageOccurrenceSpecificationEditPartCN.VISUAL_ID:
			createMessageOccurrenceSpecification_Shape_Children(node);
			return;
		case TimeConstraintEditPart.VISUAL_ID:
			createTimeConstraint_Shape_Children(node);
			return;
		case TimeObservationEditPart.VISUAL_ID:
			createTimeObservation_Shape_Children(node);
			return;
		case DurationConstraintEditPartCN.VISUAL_ID:
			createDurationConstraint_Shape_Children(node);
			return;
		case DurationObservationEditPartCN.VISUAL_ID:
			createDurationObservation_Shape_Children(node);
			return;
		case GeneralOrderingEditPart.VISUAL_ID:
			createGeneralOrdering_Shape_Children(node);
			return;
		case DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID:
			createDestructionOccurrenceSpecification_Shape_Children(node);
			return;
		case LifelineEditPart.VISUAL_ID:
			createLifeline_Shape_Children(node);
			return;
		case GateEditPart.VISUAL_ID:
			createGate_Shape_Children(node);
			return;
		}
	}

	/**
	 * @generated
	 */
	private void createLinks(Diagram diagram) {
		for (boolean continueLinkCreation = true; continueLinkCreation;) {
			continueLinkCreation = false;
			Collection additionalDescriptors = new LinkedList();
			for (Iterator it = myLinkDescriptors.iterator(); it.hasNext();) {
				UMLLinkDescriptor nextLinkDescriptor = (UMLLinkDescriptor) it.next();
				if (!myDomain2NotationMap.containsKey(nextLinkDescriptor.getSource()) || !myDomain2NotationMap.containsKey(nextLinkDescriptor.getDestination())) {
					continue;
				}
				final String linkType = UMLVisualIDRegistry.getType(nextLinkDescriptor.getVisualID());
				Edge edge = ViewService.getInstance().createEdge(nextLinkDescriptor.getSemanticAdapter(), diagram, linkType, ViewUtil.APPEND, true, UMLDiagramEditorPlugin.DIAGRAM_PREFERENCES_HINT);
				if (edge != null) {
					edge.setSource((View) myDomain2NotationMap.get(nextLinkDescriptor.getSource()));
					edge.setTarget((View) myDomain2NotationMap.get(nextLinkDescriptor.getDestination()));
					it.remove();
					if (nextLinkDescriptor.getModelElement() != null) {
						myDomain2NotationMap.put(nextLinkDescriptor.getModelElement(), edge);
					}
					continueLinkCreation = true;
					switch (nextLinkDescriptor.getVisualID()) {
					case MessageSyncEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getMessage_SynchEdge_OutgoingLinks(edge));
						break;
					case MessageAsyncEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getMessage_AsynchEdge_OutgoingLinks(edge));
						break;
					case MessageReplyEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getMessage_ReplyEdge_OutgoingLinks(edge));
						break;
					case MessageCreateEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getMessage_CreateEdge_OutgoingLinks(edge));
						break;
					case MessageDeleteEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getMessage_DeleteEdge_OutgoingLinks(edge));
						break;
					case MessageLostEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getMessage_LostEdge_OutgoingLinks(edge));
						break;
					case MessageFoundEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getMessage_FoundEdge_OutgoingLinks(edge));
						break;
					}
				}
			}
			myLinkDescriptors.addAll(additionalDescriptors);
		}
	}

	/**
	 * @generated
	 */
	private Node getCompartment(View node, String visualID) {
		String type = UMLVisualIDRegistry.getType(visualID);
		for (Iterator it = node.getChildren().iterator(); it.hasNext();) {
			View nextView = (View) it.next();
			if (nextView instanceof Node && type.equals(nextView.getType())) {
				return (Node) nextView;
			}
		}
		return null;
	}
}
