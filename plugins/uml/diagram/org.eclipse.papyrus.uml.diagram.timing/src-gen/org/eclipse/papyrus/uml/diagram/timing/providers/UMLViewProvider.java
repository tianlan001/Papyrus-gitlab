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
package org.eclipse.papyrus.uml.diagram.timing.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.service.AbstractProvider;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.diagram.core.preferences.PreferencesHint;
import org.eclipse.gmf.runtime.diagram.core.providers.IViewProvider;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateChildViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateDiagramViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateEdgeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateNodeViewOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewForKindOperation;
import org.eclipse.gmf.runtime.diagram.core.services.view.CreateViewOperation;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.emf.core.util.EMFCoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Connector;
import org.eclipse.gmf.runtime.notation.DecorationNode;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Location;
import org.eclipse.gmf.runtime.notation.MeasurementUnit;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.NotationFactory;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.TitleStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramVersioningUtils;
import org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper;
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
import org.eclipse.papyrus.uml.diagram.timing.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class UMLViewProvider extends AbstractProvider implements IViewProvider {

	/**
	 * @generated
	 */
	@Override
	public final boolean provides(IOperation operation) {
		if (operation instanceof CreateViewForKindOperation) {
			return provides((CreateViewForKindOperation) operation);
		}
		assert operation instanceof CreateViewOperation;
		/* we check this view provider is the good one for the currently edited diagram */
		if (operation instanceof CreateChildViewOperation) {
			View container = ((CreateChildViewOperation) operation).getContainerView();
			Diagram diagram = container.getDiagram();
			if (!getDiagramProvidedId().equals(diagram.getType())) {
				return false;
			}
		}
		if (operation instanceof CreateDiagramViewOperation) {
			return provides((CreateDiagramViewOperation) operation);
		} else if (operation instanceof CreateEdgeViewOperation) {
			return provides((CreateEdgeViewOperation) operation);
		} else if (operation instanceof CreateNodeViewOperation) {
			return provides((CreateNodeViewOperation) operation);
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateViewForKindOperation op) {
		// check Diagram Type should be the class diagram
		String modelID = UMLVisualIDRegistry.getModelID(op.getContainerView());
		if (!getDiagramProvidedId().equals(modelID)) {
			return false;
		}
		String visualID = UMLVisualIDRegistry.getVisualID(op.getSemanticHint());
		if (Node.class.isAssignableFrom(op.getViewKind())) {
			return UMLVisualIDRegistry.canCreateNode(op.getContainerView(), visualID);
		}
		return true;
	}

	/**
	 * @generated
	 */
	protected String getDiagramProvidedId() {
		/*
		 * Indicates for which diagram this provider works for.
		 * <p>
		 * This method can be overloaded when diagram editor inherits from another one, but should never be <code>null</code>
		 * </p>
		 *
		 * @return the unique identifier of the diagram for which views are provided.
		 */
		return TimingDiagramEditPart.MODEL_ID;
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateDiagramViewOperation op) {
		return TimingDiagramEditPart.MODEL_ID.equals(op.getSemanticHint()) && UMLVisualIDRegistry.getDiagramVisualID(getSemanticElement(op.getSemanticAdapter())) != null
				&& !UMLVisualIDRegistry.getDiagramVisualID(getSemanticElement(op.getSemanticAdapter())).isEmpty();
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateNodeViewOperation op) {
		if (op.getContainerView() == null) {
			return false;
		}
		IElementType elementType = getSemanticElementType(op.getSemanticAdapter());
		EObject domainElement = getSemanticElement(op.getSemanticAdapter());
		String visualID;
		if (op.getSemanticHint() == null) {
			// Semantic hint is not specified. Can be a result of call from CanonicalEditPolicy.
			// In this situation there should be NO elementType, visualID will be determined
			// by VisualIDRegistry.getNodeVisualID() for domainElement.
			if (elementType != null || domainElement == null) {
				return false;
			}
			visualID = UMLVisualIDRegistry.getNodeVisualID(op.getContainerView(), domainElement);
		} else {
			visualID = UMLVisualIDRegistry.getVisualID(op.getSemanticHint());
			if (elementType != null) {
				if (!UMLElementTypes.isKnownElementType(elementType) || (!(elementType instanceof IHintedType))) {
					return false; // foreign element type
				}
				String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
				if (!op.getSemanticHint().equals(elementTypeHint)) {
					return false; // if semantic hint is specified it should be the same as in element type
				}
			} else {
				if (!TimingDiagramEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(op.getContainerView()))) {
					return false; // foreign diagram
				}
				if (visualID != null) {
					switch (visualID) {
					case StateDefinitionEditPart.VISUAL_ID:
					case FullStateInvariantVerticalLineEditPart.VISUAL_ID:
					case FreeTimingRulerEditPartCN.VISUAL_ID:
					case LinearTimingRulerEditPartCN.VISUAL_ID:
					case TickEditPart.VISUAL_ID:
						break; // pure design element
					case InteractionEditPartTN.VISUAL_ID:
					case FullStateInvariantEditPartCN.VISUAL_ID:
					case OccurrenceSpecificationEditPartCN.VISUAL_ID:
					case MessageOccurrenceSpecificationEditPartCN.VISUAL_ID:
					case TimeConstraintEditPart.VISUAL_ID:
					case TimeObservationEditPart.VISUAL_ID:
					case DurationConstraintEditPartCN.VISUAL_ID:
					case DurationObservationEditPartCN.VISUAL_ID:
					case GeneralOrderingEditPart.VISUAL_ID:
					case DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID:
					case LifelineEditPart.VISUAL_ID:
					case GateEditPart.VISUAL_ID:
					case FullLifelineEditPartCN.VISUAL_ID:
					case CompactLifelineEditPartCN.VISUAL_ID:
					case CompactStateInvariantEditPartCN.VISUAL_ID:
						if (domainElement == null || !visualID.equals(UMLVisualIDRegistry.getNodeVisualID(op.getContainerView(), domainElement))) {
							return false; // visual id in semantic hint should match visual id for domain element
						}
						break;
					default:
						return false;
					}
				}
			}
		}
		return UMLVisualIDRegistry.canCreateNode(op.getContainerView(), visualID);
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateEdgeViewOperation op) {
		IElementType elementType = getSemanticElementType(op.getSemanticAdapter());
		if (!UMLElementTypes.isKnownElementType(elementType) || (!(elementType instanceof IHintedType))) {
			return false; // foreign element type
		}
		String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
		if (elementTypeHint == null || (op.getSemanticHint() != null && !elementTypeHint.equals(op.getSemanticHint()))) {
			return false; // our hint is visual id and must be specified, and it should be the same as in element type
		}
		return true;
	}

	/**
	 * @generated
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Diagram createDiagram(IAdaptable semanticAdapter, String diagramKind, PreferencesHint preferencesHint) {
		Diagram diagram = NotationFactory.eINSTANCE.createDiagram();
		DiagramVersioningUtils.stampCurrentVersion(diagram);
		diagram.getStyles().add(NotationFactory.eINSTANCE.createDiagramStyle());
		diagram.setType(TimingDiagramEditPart.MODEL_ID);
		diagram.setElement(getSemanticElement(semanticAdapter));
		diagram.setMeasurementUnit(MeasurementUnit.PIXEL_LITERAL);
		return diagram;
	}

	/**
	 * @generated
	 */
	@Override
	public Node createNode(IAdaptable semanticAdapter, View containerView, String semanticHint, int index, boolean persisted, PreferencesHint preferencesHint) {
		final EObject domainElement = getSemanticElement(semanticAdapter);
		final String visualID;
		if (semanticHint == null) {
			visualID = UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		} else {
			visualID = UMLVisualIDRegistry.getVisualID(semanticHint);
		}
		if (visualID != null) {
			switch (visualID) {
			case InteractionEditPartTN.VISUAL_ID:
				return createInteraction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case FullLifelineEditPartCN.VISUAL_ID:
				return createLifeline_FullShape(domainElement, containerView, index, persisted, preferencesHint);
			case CompactLifelineEditPartCN.VISUAL_ID:
				return createLifeline_CompactShape(domainElement, containerView, index, persisted, preferencesHint);
			case FullStateInvariantEditPartCN.VISUAL_ID:
				return createStateInvariant_FullShape(domainElement, containerView, index, persisted, preferencesHint);
			case CompactStateInvariantEditPartCN.VISUAL_ID:
				return createStateInvariant_CompactShape(domainElement, containerView, index, persisted, preferencesHint);
			case OccurrenceSpecificationEditPartCN.VISUAL_ID:
				return createOccurrenceSpecification_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case MessageOccurrenceSpecificationEditPartCN.VISUAL_ID:
				return createMessageOccurrenceSpecification_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case StateDefinitionEditPart.VISUAL_ID:
				return createNode_StateDefinitionShape(domainElement, containerView, index, persisted, preferencesHint);
			case FullStateInvariantVerticalLineEditPart.VISUAL_ID:
				return createNode_StateInvariantTransitionShape(domainElement, containerView, index, persisted, preferencesHint);
			case TimeConstraintEditPart.VISUAL_ID:
				return createTimeConstraint_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case TimeObservationEditPart.VISUAL_ID:
				return createTimeObservation_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DurationConstraintEditPartCN.VISUAL_ID:
				return createDurationConstraint_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DurationObservationEditPartCN.VISUAL_ID:
				return createDurationObservation_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case GeneralOrderingEditPart.VISUAL_ID:
				return createGeneralOrdering_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case FreeTimingRulerEditPartCN.VISUAL_ID:
				return createNode_FreeTimeRulerShape(domainElement, containerView, index, persisted, preferencesHint);
			case LinearTimingRulerEditPartCN.VISUAL_ID:
				return createNode_LinearTimeRulerShape(domainElement, containerView, index, persisted, preferencesHint);
			case TickEditPart.VISUAL_ID:
				return createNode_TickShape(domainElement, containerView, index, persisted, preferencesHint);
			case DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID:
				return createDestructionOccurrenceSpecification_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case LifelineEditPart.VISUAL_ID:
				return createLifeline_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case GateEditPart.VISUAL_ID:
				return createGate_Shape(domainElement, containerView, index, persisted, preferencesHint);
			}
		}
		// can't happen, provided #provides(CreateNodeViewOperation) is correct
		return null;
	}

	/**
	 * @generated
	 */
	@Override
	public Edge createEdge(IAdaptable semanticAdapter, View containerView, String semanticHint, int index, boolean persisted, PreferencesHint preferencesHint) {
		IElementType elementType = getSemanticElementType(semanticAdapter);
		String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
		String vid = UMLVisualIDRegistry.getVisualID(elementTypeHint);
		if (vid != null) {
			switch (vid) {
			case MessageSyncEditPart.VISUAL_ID:
				return createMessage_SynchEdge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case MessageAsyncEditPart.VISUAL_ID:
				return createMessage_AsynchEdge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case MessageReplyEditPart.VISUAL_ID:
				return createMessage_ReplyEdge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case MessageCreateEditPart.VISUAL_ID:
				return createMessage_CreateEdge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case MessageDeleteEditPart.VISUAL_ID:
				return createMessage_DeleteEdge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case MessageLostEditPart.VISUAL_ID:
				return createMessage_LostEdge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case MessageFoundEditPart.VISUAL_ID:
				return createMessage_FoundEdge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			}
		}
		// can never happen, provided #provides(CreateEdgeViewOperation) is correct
		return null;
	}

	/**
	 * @generated
	 */
	public Node createInteraction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InteractionEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interaction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InteractionNameEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(InteractionCompartmentEditPartTN.VISUAL_ID), false, false, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(TimeRulerCompartmentEditPartCN.VISUAL_ID), false, false, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Interaction"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createLifeline_FullShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(FullLifelineEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "FullLifeline"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(FullLifelineNameEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(FullLifelineStateDefinitionCompartmentEditPartCN.VISUAL_ID), false, false, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(FullLifelineTimelineCompartmentEditPartCN.VISUAL_ID), false, false, false, false);
		createCompartment(node, UMLVisualIDRegistry.getType(FullLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID), false, false, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "FullLifeline"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createLifeline_CompactShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CompactLifelineEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CompactLifeline"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CompactLifelineNameEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(CompactLifelineCompartmentEditPartCN.VISUAL_ID), false, false, false, false);
		createCompartment(node, UMLVisualIDRegistry.getType(CompactLifelineTimeRulerCompartmentEditPartCN.VISUAL_ID), false, false, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "CompactLifeline"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createStateInvariant_FullShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(FullStateInvariantEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "FullStateInvariant"); //$NON-NLS-1$
		Node stateInvariant_FullStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(FullStateInvariantAppliedStereotypeEditPart.VISUAL_ID));
		stateInvariant_FullStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location stateInvariant_FullStereotypeLabel_Location = (Location) stateInvariant_FullStereotypeLabel.getLayoutConstraint();
		stateInvariant_FullStereotypeLabel_Location.setX(0);
		stateInvariant_FullStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createStateInvariant_CompactShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CompactStateInvariantEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CompactStateInvariant"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CompactStateInvariantNameEditPart.VISUAL_ID));
		Node stateInvariant_CompactStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(StateInvariantAppliedStereotypeEditPart.VISUAL_ID));
		stateInvariant_CompactStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location stateInvariant_CompactStereotypeLabel_Location = (Location) stateInvariant_CompactStereotypeLabel.getLayoutConstraint();
		stateInvariant_CompactStereotypeLabel_Location.setX(0);
		stateInvariant_CompactStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOccurrenceSpecification_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createNode();
		node.getStyles().add(NotationFactory.eINSTANCE.createDescriptionStyle());
		node.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OccurrenceSpecificationEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OccurrenceSpecification"); //$NON-NLS-1$
		Node occurrenceSpecification_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(OccurrenceSpecificationLabelEditPart.VISUAL_ID));
		occurrenceSpecification_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location occurrenceSpecification_NameLabel_Location = (Location) occurrenceSpecification_NameLabel.getLayoutConstraint();
		occurrenceSpecification_NameLabel_Location.setX(0);
		occurrenceSpecification_NameLabel_Location.setY(18);
		Node occurrenceSpecification_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OccurrenceSpecificationAppliedStereotypeEditPart.VISUAL_ID));
		occurrenceSpecification_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location occurrenceSpecification_StereotypeLabel_Location = (Location) occurrenceSpecification_StereotypeLabel.getLayoutConstraint();
		occurrenceSpecification_StereotypeLabel_Location.setX(0);
		occurrenceSpecification_StereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createMessageOccurrenceSpecification_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(MessageOccurrenceSpecificationEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "MessageOccurrenceSpecification"); //$NON-NLS-1$
		Node messageOccurrenceSpecification_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(MessageOccurrenceSpecificationLabelEditPart.VISUAL_ID));
		messageOccurrenceSpecification_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location messageOccurrenceSpecification_NameLabel_Location = (Location) messageOccurrenceSpecification_NameLabel.getLayoutConstraint();
		messageOccurrenceSpecification_NameLabel_Location.setX(0);
		messageOccurrenceSpecification_NameLabel_Location.setY(18);
		Node messageOccurrenceSpecification_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(MessageOccurrenceSpecificationAppliedStereotypeEditPart.VISUAL_ID));
		messageOccurrenceSpecification_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location messageOccurrenceSpecification_StereotypeLabel_Location = (Location) messageOccurrenceSpecification_StereotypeLabel.getLayoutConstraint();
		messageOccurrenceSpecification_StereotypeLabel_Location.setX(0);
		messageOccurrenceSpecification_StereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createNode_StateDefinitionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(StateDefinitionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "StateDefinition"); //$NON-NLS-1$
		Node node_StateDefinitionNameLabel = createLabel(node, UMLVisualIDRegistry.getType(StateDefinitionLabelEditPart.VISUAL_ID));
		node_StateDefinitionNameLabel.getStyles().add(NotationFactory.eINSTANCE.createDescriptionStyle());
		return node;
	}

	/**
	 * @generated
	 */
	public Node createNode_StateInvariantTransitionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(FullStateInvariantVerticalLineEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "VerticalLineStateInvariant"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTimeConstraint_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TimeConstraintEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeConstraint"); //$NON-NLS-1$
		Node timeConstraint_BodyLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeConstraintSpecificationEditPart.VISUAL_ID));
		timeConstraint_BodyLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeConstraint_BodyLabel_Location = (Location) timeConstraint_BodyLabel.getLayoutConstraint();
		timeConstraint_BodyLabel_Location.setX(-5);
		timeConstraint_BodyLabel_Location.setY(-15);
		Node timeConstraint_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeConstraintAppliedStereotypeEditPart.VISUAL_ID));
		timeConstraint_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeConstraint_StereotypeLabel_Location = (Location) timeConstraint_StereotypeLabel.getLayoutConstraint();
		timeConstraint_StereotypeLabel_Location.setX(0);
		timeConstraint_StereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTimeObservation_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TimeObservationEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeObservation"); //$NON-NLS-1$
		Node timeObservation_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeObservationNameEditPart.VISUAL_ID));
		timeObservation_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeObservation_NameLabel_Location = (Location) timeObservation_NameLabel.getLayoutConstraint();
		timeObservation_NameLabel_Location.setX(-5);
		timeObservation_NameLabel_Location.setY(-15);
		Node timeObservation_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeObservationAppliedStereotypeEditPart.VISUAL_ID));
		timeObservation_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeObservation_StereotypeLabel_Location = (Location) timeObservation_StereotypeLabel.getLayoutConstraint();
		timeObservation_StereotypeLabel_Location.setX(0);
		timeObservation_StereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDurationConstraint_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DurationConstraintEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DurationConstraint"); //$NON-NLS-1$
		Node durationConstraint_BodyLabel = createLabel(node, UMLVisualIDRegistry.getType(DurationConstraintSpecificationEditPart.VISUAL_ID));
		durationConstraint_BodyLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location durationConstraint_BodyLabel_Location = (Location) durationConstraint_BodyLabel.getLayoutConstraint();
		durationConstraint_BodyLabel_Location.setX(0);
		durationConstraint_BodyLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDurationObservation_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DurationObservationEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DurationObservation"); //$NON-NLS-1$
		Node durationObservation_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(DurationObservationNameEditPart.VISUAL_ID));
		durationObservation_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location durationObservation_NameLabel_Location = (Location) durationObservation_NameLabel.getLayoutConstraint();
		durationObservation_NameLabel_Location.setX(0);
		durationObservation_NameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createGeneralOrdering_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(GeneralOrderingEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "GeneralOrdering"); //$NON-NLS-1$
		Node generalOrdering_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(GeneralOrderingNameEditPart.VISUAL_ID));
		generalOrdering_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location generalOrdering_NameLabel_Location = (Location) generalOrdering_NameLabel.getLayoutConstraint();
		generalOrdering_NameLabel_Location.setX(-5);
		generalOrdering_NameLabel_Location.setY(-15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createNode_FreeTimeRulerShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(FreeTimingRulerEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "FreeTimingRuler"); //$NON-NLS-1$
		createCompartment(node, UMLVisualIDRegistry.getType(FreeTimeRulerCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "FreeTimingRuler"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createNode_LinearTimeRulerShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(LinearTimingRulerEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "LinearTimingRuler"); //$NON-NLS-1$
		createCompartment(node, UMLVisualIDRegistry.getType(LinearTimeRulerCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "LinearTimingRuler"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createNode_TickShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TickEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Tick"); //$NON-NLS-1$
		Node node_TickNameLabel = createLabel(node, UMLVisualIDRegistry.getType(TickNameEditPart.VISUAL_ID));
		node_TickNameLabel.getStyles().add(NotationFactory.eINSTANCE.createDescriptionStyle());
		node_TickNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location node_TickNameLabel_Location = (Location) node_TickNameLabel.getLayoutConstraint();
		node_TickNameLabel_Location.setX(0);
		node_TickNameLabel_Location.setY(12);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDestructionOccurrenceSpecification_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DestructionOccurrenceSpecificationEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DestructionOccurrenceSpecification"); //$NON-NLS-1$
		Node destructionOccurrenceSpecification_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(DestructionOccurrenceSpecificationLabelEditPart.VISUAL_ID));
		destructionOccurrenceSpecification_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location destructionOccurrenceSpecification_NameLabel_Location = (Location) destructionOccurrenceSpecification_NameLabel.getLayoutConstraint();
		destructionOccurrenceSpecification_NameLabel_Location.setX(0);
		destructionOccurrenceSpecification_NameLabel_Location.setY(18);
		Node destructionOccurrenceSpecification_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(DestructionOccurrenceSpecificationAppliedStereotypeEditPart.VISUAL_ID));
		destructionOccurrenceSpecification_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location destructionOccurrenceSpecification_StereotypeLabel_Location = (Location) destructionOccurrenceSpecification_StereotypeLabel.getLayoutConstraint();
		destructionOccurrenceSpecification_StereotypeLabel_Location.setX(0);
		destructionOccurrenceSpecification_StereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createLifeline_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(LifelineEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Lifeline"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createGate_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(GateEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Gate"); //$NON-NLS-1$
		Node gate_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(GateLabelEditPart.VISUAL_ID));
		gate_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location gate_NameLabel_Location = (Location) gate_NameLabel.getLayoutConstraint();
		gate_NameLabel_Location.setX(20);
		gate_NameLabel_Location.setY(0);
		return node;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createMessage_SynchEdge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(MessageSyncEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "MessageSync"); //$NON-NLS-1$
		Node message_SynchNameLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageSyncNameLabelEditPart.VISUAL_ID));
		message_SynchNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_SynchNameLabel_Location = (Location) message_SynchNameLabel.getLayoutConstraint();
		message_SynchNameLabel_Location.setX(0);
		message_SynchNameLabel_Location.setY(-10);
		Node message_SynchStereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageSyncAppliedStereotypeEditPart.VISUAL_ID));
		message_SynchStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_SynchStereotypeLabel_Location = (Location) message_SynchStereotypeLabel.getLayoutConstraint();
		message_SynchStereotypeLabel_Location.setX(0);
		message_SynchStereotypeLabel_Location.setY(-30);
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createMessage_AsynchEdge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(MessageAsyncEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "MessageAsync"); //$NON-NLS-1$
		Node message_AsynchNameLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageAsyncNameLabelEditPart.VISUAL_ID));
		message_AsynchNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_AsynchNameLabel_Location = (Location) message_AsynchNameLabel.getLayoutConstraint();
		message_AsynchNameLabel_Location.setX(0);
		message_AsynchNameLabel_Location.setY(-10);
		Node message_AsynchStereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageAsyncAppliedStereotypeEditPart.VISUAL_ID));
		message_AsynchStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_AsynchStereotypeLabel_Location = (Location) message_AsynchStereotypeLabel.getLayoutConstraint();
		message_AsynchStereotypeLabel_Location.setX(0);
		message_AsynchStereotypeLabel_Location.setY(-30);
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createMessage_ReplyEdge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(MessageReplyEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "MessageReply"); //$NON-NLS-1$
		Node message_ReplyNameLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageReplyNameLabelEditPart.VISUAL_ID));
		message_ReplyNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_ReplyNameLabel_Location = (Location) message_ReplyNameLabel.getLayoutConstraint();
		message_ReplyNameLabel_Location.setX(0);
		message_ReplyNameLabel_Location.setY(-10);
		Node message_ReplyStereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageReplyAppliedStereotypeEditPart.VISUAL_ID));
		message_ReplyStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_ReplyStereotypeLabel_Location = (Location) message_ReplyStereotypeLabel.getLayoutConstraint();
		message_ReplyStereotypeLabel_Location.setX(0);
		message_ReplyStereotypeLabel_Location.setY(-30);
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createMessage_CreateEdge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(MessageCreateEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "MessageCreate"); //$NON-NLS-1$
		Node message_CreateNameLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageCreateNameLabelEditPart.VISUAL_ID));
		message_CreateNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_CreateNameLabel_Location = (Location) message_CreateNameLabel.getLayoutConstraint();
		message_CreateNameLabel_Location.setX(0);
		message_CreateNameLabel_Location.setY(-10);
		Node message_CreateStereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageCreateAppliedStereotypeEditPart.VISUAL_ID));
		message_CreateStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_CreateStereotypeLabel_Location = (Location) message_CreateStereotypeLabel.getLayoutConstraint();
		message_CreateStereotypeLabel_Location.setX(0);
		message_CreateStereotypeLabel_Location.setY(-30);
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createMessage_DeleteEdge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(MessageDeleteEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "MessageDelete"); //$NON-NLS-1$
		Node message_DeleteNameLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageDeleteNameLabelEditPart.VISUAL_ID));
		message_DeleteNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_DeleteNameLabel_Location = (Location) message_DeleteNameLabel.getLayoutConstraint();
		message_DeleteNameLabel_Location.setX(0);
		message_DeleteNameLabel_Location.setY(-10);
		Node message_DeleteStereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageDeleteAppliedStereotypeEditPart.VISUAL_ID));
		message_DeleteStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_DeleteStereotypeLabel_Location = (Location) message_DeleteStereotypeLabel.getLayoutConstraint();
		message_DeleteStereotypeLabel_Location.setX(0);
		message_DeleteStereotypeLabel_Location.setY(-30);
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createMessage_LostEdge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(MessageLostEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "MessageLost"); //$NON-NLS-1$
		Node message_LostNameLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageLostNameLabelEditPart.VISUAL_ID));
		message_LostNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_LostNameLabel_Location = (Location) message_LostNameLabel.getLayoutConstraint();
		message_LostNameLabel_Location.setX(0);
		message_LostNameLabel_Location.setY(-10);
		Node message_LostStereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageLostAppliedStereotypeEditPart.VISUAL_ID));
		message_LostStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_LostStereotypeLabel_Location = (Location) message_LostStereotypeLabel.getLayoutConstraint();
		message_LostStereotypeLabel_Location.setX(0);
		message_LostStereotypeLabel_Location.setY(-30);
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createMessage_FoundEdge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(MessageFoundEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "MessageFound"); //$NON-NLS-1$
		Node message_FoundNameLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageFoundNameLabelEditPart.VISUAL_ID));
		message_FoundNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_FoundNameLabel_Location = (Location) message_FoundNameLabel.getLayoutConstraint();
		message_FoundNameLabel_Location.setX(0);
		message_FoundNameLabel_Location.setY(-10);
		Node message_FoundStereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(MessageFoundAppliedStereotypeEditPart.VISUAL_ID));
		message_FoundStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location message_FoundStereotypeLabel_Location = (Location) message_FoundStereotypeLabel.getLayoutConstraint();
		message_FoundStereotypeLabel_Location.setX(0);
		message_FoundStereotypeLabel_Location.setY(-30);
		return edge;
	}

	/**
	 * @generated
	 */
	protected void stampShortcut(View containerView, Node target) {
		if (!TimingDiagramEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(containerView))) {
			EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
			shortcutAnnotation.getDetails().put("modelID", TimingDiagramEditPart.MODEL_ID); //$NON-NLS-1$
			target.getEAnnotations().add(shortcutAnnotation);
		}
	}

	/**
	 * @generated
	 */
	protected Node createLabel(View owner, String hint) {
		DecorationNode rv = NotationFactory.eINSTANCE.createDecorationNode();
		rv.setType(hint);
		ViewUtil.insertChildView(owner, rv, ViewUtil.APPEND, true);
		return rv;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	protected Node createCompartment(View owner, String hint, boolean canCollapse, boolean hasTitle, boolean canSort, boolean canFilter) {
		Node rv = NotationFactory.eINSTANCE.createBasicCompartment();
		rv.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		if (hasTitle) {
			TitleStyle ts = NotationFactory.eINSTANCE.createTitleStyle();
			rv.getStyles().add(ts);
		}
		if (canSort) {
			rv.getStyles().add(NotationFactory.eINSTANCE.createSortingStyle());
		}
		if (canFilter) {
			rv.getStyles().add(NotationFactory.eINSTANCE.createFilteringStyle());
		}
		rv.setType(hint);
		ViewUtil.insertChildView(owner, rv, ViewUtil.APPEND, true);
		return rv;
	}

	/**
	 * @generated
	 */
	protected EObject getSemanticElement(IAdaptable semanticAdapter) {
		if (semanticAdapter == null) {
			return null;
		}
		EObject eObject = semanticAdapter.getAdapter(EObject.class);
		if (eObject != null) {
			return EMFCoreUtil.resolve(TransactionUtil.getEditingDomain(eObject), eObject);
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected IElementType getSemanticElementType(IAdaptable semanticAdapter) {
		if (semanticAdapter == null) {
			return null;
		}
		return semanticAdapter.getAdapter(IElementType.class);
	}
}
