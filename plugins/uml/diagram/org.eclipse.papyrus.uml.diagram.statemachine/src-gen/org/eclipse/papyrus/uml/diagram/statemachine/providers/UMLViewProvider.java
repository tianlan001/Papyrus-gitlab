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
package org.eclipse.papyrus.uml.diagram.statemachine.providers;

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
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentAnnotatedElementEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentBodyEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConnectionPointReferenceStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintBodyEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintConstrainedElementEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ConstraintNameLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ContextLinkAppliedStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ContextLinkEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.DeferrableTriggerEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.DoActivityStateBehaviorStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.EntryStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.ExitStateBehaviorEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.FinalStateStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.GeneralizationEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.GeneralizationStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.InternalTransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PackageEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateChoiceStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateDeepHistoryStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateEntryPointStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateExitPointStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateForkEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateForkNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateForkStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateInitialStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJoinEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJoinFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJoinStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateJunctionStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateShallowHistoryStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.PseudostateTerminateStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateCompartmentEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateFloatingLabelEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineCompartmentEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateMachineNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.StateNameEditPartTN;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionGuardEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionNameEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.edit.parts.TransitionStereotypeEditPart;
import org.eclipse.papyrus.uml.diagram.statemachine.part.UMLVisualIDRegistry;

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
		return PackageEditPart.MODEL_ID;
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateDiagramViewOperation op) {
		return PackageEditPart.MODEL_ID.equals(op.getSemanticHint()) && UMLVisualIDRegistry.getDiagramVisualID(getSemanticElement(op.getSemanticAdapter())) != null
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
				if (!PackageEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(op.getContainerView()))) {
					return false; // foreign diagram
				}
				if (visualID != null) {
					switch (visualID) {
					case StateMachineEditPart.VISUAL_ID:
					case StateEditPartTN.VISUAL_ID:
					case RegionEditPart.VISUAL_ID:
					case FinalStateEditPart.VISUAL_ID:
					case StateEditPart.VISUAL_ID:
					case PseudostateInitialEditPart.VISUAL_ID:
					case PseudostateJoinEditPart.VISUAL_ID:
					case PseudostateForkEditPart.VISUAL_ID:
					case PseudostateChoiceEditPart.VISUAL_ID:
					case PseudostateJunctionEditPart.VISUAL_ID:
					case PseudostateShallowHistoryEditPart.VISUAL_ID:
					case PseudostateDeepHistoryEditPart.VISUAL_ID:
					case PseudostateTerminateEditPart.VISUAL_ID:
					case PseudostateEntryPointEditPart.VISUAL_ID:
					case PseudostateExitPointEditPart.VISUAL_ID:
					case ConnectionPointReferenceEditPart.VISUAL_ID:
					case CommentEditPart.VISUAL_ID:
					case ConstraintEditPart.VISUAL_ID:
					case EntryStateBehaviorEditPart.VISUAL_ID:
					case DoActivityStateBehaviorStateEditPart.VISUAL_ID:
					case ExitStateBehaviorEditPart.VISUAL_ID:
					case DeferrableTriggerEditPart.VISUAL_ID:
					case InternalTransitionEditPart.VISUAL_ID:
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
		diagram.setType(PackageEditPart.MODEL_ID);
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
			case StateMachineEditPart.VISUAL_ID:
				return createStateMachine_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case StateEditPartTN.VISUAL_ID:
				return createState_Shape_TN(domainElement, containerView, index, persisted, preferencesHint);
			case RegionEditPart.VISUAL_ID:
				return createRegion_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case FinalStateEditPart.VISUAL_ID:
				return createFinalState_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case StateEditPart.VISUAL_ID:
				return createState_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case PseudostateInitialEditPart.VISUAL_ID:
				return createPseudostate_InitialShape(domainElement, containerView, index, persisted, preferencesHint);
			case PseudostateJoinEditPart.VISUAL_ID:
				return createPseudostate_JoinShape(domainElement, containerView, index, persisted, preferencesHint);
			case PseudostateForkEditPart.VISUAL_ID:
				return createPseudostate_ForkShape(domainElement, containerView, index, persisted, preferencesHint);
			case PseudostateChoiceEditPart.VISUAL_ID:
				return createPseudostate_ChoiceShape(domainElement, containerView, index, persisted, preferencesHint);
			case PseudostateJunctionEditPart.VISUAL_ID:
				return createPseudostate_JunctionShape(domainElement, containerView, index, persisted, preferencesHint);
			case PseudostateShallowHistoryEditPart.VISUAL_ID:
				return createPseudostate_ShallowHistoryShape(domainElement, containerView, index, persisted, preferencesHint);
			case PseudostateDeepHistoryEditPart.VISUAL_ID:
				return createPseudostate_DeepHistoryShape(domainElement, containerView, index, persisted, preferencesHint);
			case PseudostateTerminateEditPart.VISUAL_ID:
				return createPseudostate_TerminateShape(domainElement, containerView, index, persisted, preferencesHint);
			case PseudostateEntryPointEditPart.VISUAL_ID:
				return createPseudostate_EntryPointShape(domainElement, containerView, index, persisted, preferencesHint);
			case PseudostateExitPointEditPart.VISUAL_ID:
				return createPseudostate_ExitPointShape(domainElement, containerView, index, persisted, preferencesHint);
			case ConnectionPointReferenceEditPart.VISUAL_ID:
				return createConnectionPointReference_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case CommentEditPart.VISUAL_ID:
				return createComment_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintEditPart.VISUAL_ID:
				return createConstraint_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InternalTransitionEditPart.VISUAL_ID:
				return createTransition_InternalTransitionLabel(domainElement, containerView, index, persisted, preferencesHint);
			case EntryStateBehaviorEditPart.VISUAL_ID:
				return createBehavior_EntryBehaviorLabel(domainElement, containerView, index, persisted, preferencesHint);
			case DoActivityStateBehaviorStateEditPart.VISUAL_ID:
				return createBehavior_DoActivityBehaviorLabel(domainElement, containerView, index, persisted, preferencesHint);
			case ExitStateBehaviorEditPart.VISUAL_ID:
				return createBehavior_ExitBehaviorLabel(domainElement, containerView, index, persisted, preferencesHint);
			case DeferrableTriggerEditPart.VISUAL_ID:
				return createTrigger_DeferrableTriggerLabel(domainElement, containerView, index, persisted, preferencesHint);
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
			case TransitionEditPart.VISUAL_ID:
				return createTransition_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case GeneralizationEditPart.VISUAL_ID:
				return createGeneralization_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return createComment_AnnotatedElementEdge(containerView, index, persisted, preferencesHint);
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return createConstraint_ConstrainedElementEdge(containerView, index, persisted, preferencesHint);
			case ContextLinkEditPart.VISUAL_ID:
				return createConstraint_ContextEdge(containerView, index, persisted, preferencesHint);
			}
		}
		// can never happen, provided #provides(CreateEdgeViewOperation) is correct
		return null;
	}

	/**
	 * @generated
	 */
	public Node createStateMachine_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(StateMachineEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "StateMachine"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(StateMachineNameEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(StateMachineCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "StateMachine"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createState_Shape_TN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(StateEditPartTN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "State"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(StateNameEditPartTN.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(StateCompartmentEditPartTN.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "State"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createRegion_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(RegionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Region"); //$NON-NLS-1$
		createCompartment(node, UMLVisualIDRegistry.getType(RegionCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Region"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createFinalState_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(FinalStateEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "FinalState"); //$NON-NLS-1$
		Node finalState_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(FinalStateFloatingLabelEditPart.VISUAL_ID));
		finalState_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location finalState_FloatingNameLabel_Location = (Location) finalState_FloatingNameLabel.getLayoutConstraint();
		finalState_FloatingNameLabel_Location.setX(25);
		finalState_FloatingNameLabel_Location.setY(3);
		Node finalState_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(FinalStateStereotypeEditPart.VISUAL_ID));
		finalState_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location finalState_StereotypeLabel_Location = (Location) finalState_StereotypeLabel.getLayoutConstraint();
		finalState_StereotypeLabel_Location.setX(25);
		finalState_StereotypeLabel_Location.setY(-10);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createState_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(StateEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "State"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(StateNameEditPart.VISUAL_ID));
		Node state_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(StateFloatingLabelEditPart.VISUAL_ID));
		state_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location state_FloatingNameLabel_Location = (Location) state_FloatingNameLabel.getLayoutConstraint();
		state_FloatingNameLabel_Location.setX(40);
		state_FloatingNameLabel_Location.setY(0);
		createCompartment(node, UMLVisualIDRegistry.getType(StateCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "State"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPseudostate_InitialShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PseudostateInitialEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		Node pseudostate_InitialFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateInitialFloatingLabelEditPart.VISUAL_ID));
		pseudostate_InitialFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_InitialFloatingNameLabel_Location = (Location) pseudostate_InitialFloatingNameLabel.getLayoutConstraint();
		pseudostate_InitialFloatingNameLabel_Location.setX(25);
		pseudostate_InitialFloatingNameLabel_Location.setY(3);
		Node pseudostate_InitialStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateInitialStereotypeEditPart.VISUAL_ID));
		pseudostate_InitialStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_InitialStereotypeLabel_Location = (Location) pseudostate_InitialStereotypeLabel.getLayoutConstraint();
		pseudostate_InitialStereotypeLabel_Location.setX(25);
		pseudostate_InitialStereotypeLabel_Location.setY(-10);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPseudostate_JoinShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PseudostateJoinEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		Node pseudostate_JoinFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateJoinFloatingLabelEditPart.VISUAL_ID));
		pseudostate_JoinFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_JoinFloatingNameLabel_Location = (Location) pseudostate_JoinFloatingNameLabel.getLayoutConstraint();
		pseudostate_JoinFloatingNameLabel_Location.setX(25);
		pseudostate_JoinFloatingNameLabel_Location.setY(3);
		Node pseudostate_JoinStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateJoinStereotypeEditPart.VISUAL_ID));
		pseudostate_JoinStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_JoinStereotypeLabel_Location = (Location) pseudostate_JoinStereotypeLabel.getLayoutConstraint();
		pseudostate_JoinStereotypeLabel_Location.setX(25);
		pseudostate_JoinStereotypeLabel_Location.setY(-10);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPseudostate_ForkShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PseudostateForkEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		Node pseudostate_ForkFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateForkNameEditPart.VISUAL_ID));
		pseudostate_ForkFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_ForkFloatingNameLabel_Location = (Location) pseudostate_ForkFloatingNameLabel.getLayoutConstraint();
		pseudostate_ForkFloatingNameLabel_Location.setX(25);
		pseudostate_ForkFloatingNameLabel_Location.setY(3);
		Node pseudostate_ForkStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateForkStereotypeEditPart.VISUAL_ID));
		pseudostate_ForkStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_ForkStereotypeLabel_Location = (Location) pseudostate_ForkStereotypeLabel.getLayoutConstraint();
		pseudostate_ForkStereotypeLabel_Location.setX(25);
		pseudostate_ForkStereotypeLabel_Location.setY(-10);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPseudostate_ChoiceShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PseudostateChoiceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		Node pseudostate_ChoiceFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateChoiceFloatingLabelEditPart.VISUAL_ID));
		pseudostate_ChoiceFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_ChoiceFloatingNameLabel_Location = (Location) pseudostate_ChoiceFloatingNameLabel.getLayoutConstraint();
		pseudostate_ChoiceFloatingNameLabel_Location.setX(50);
		pseudostate_ChoiceFloatingNameLabel_Location.setY(3);
		Node pseudostate_ChoiceStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateChoiceStereotypeEditPart.VISUAL_ID));
		pseudostate_ChoiceStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_ChoiceStereotypeLabel_Location = (Location) pseudostate_ChoiceStereotypeLabel.getLayoutConstraint();
		pseudostate_ChoiceStereotypeLabel_Location.setX(25);
		pseudostate_ChoiceStereotypeLabel_Location.setY(-10);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPseudostate_JunctionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PseudostateJunctionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		Node pseudostate_JunctionFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateJunctionFloatingLabelEditPart.VISUAL_ID));
		pseudostate_JunctionFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_JunctionFloatingNameLabel_Location = (Location) pseudostate_JunctionFloatingNameLabel.getLayoutConstraint();
		pseudostate_JunctionFloatingNameLabel_Location.setX(25);
		pseudostate_JunctionFloatingNameLabel_Location.setY(3);
		Node pseudostate_JunctionStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateJunctionStereotypeEditPart.VISUAL_ID));
		pseudostate_JunctionStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_JunctionStereotypeLabel_Location = (Location) pseudostate_JunctionStereotypeLabel.getLayoutConstraint();
		pseudostate_JunctionStereotypeLabel_Location.setX(25);
		pseudostate_JunctionStereotypeLabel_Location.setY(-10);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPseudostate_ShallowHistoryShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PseudostateShallowHistoryEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		Node pseudostate_ShallowHistoryFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateShallowHistoryFloatingLabelEditPart.VISUAL_ID));
		pseudostate_ShallowHistoryFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_ShallowHistoryFloatingNameLabel_Location = (Location) pseudostate_ShallowHistoryFloatingNameLabel.getLayoutConstraint();
		pseudostate_ShallowHistoryFloatingNameLabel_Location.setX(25);
		pseudostate_ShallowHistoryFloatingNameLabel_Location.setY(3);
		Node pseudostate_ShallowHistoryStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateShallowHistoryStereotypeEditPart.VISUAL_ID));
		pseudostate_ShallowHistoryStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_ShallowHistoryStereotypeLabel_Location = (Location) pseudostate_ShallowHistoryStereotypeLabel.getLayoutConstraint();
		pseudostate_ShallowHistoryStereotypeLabel_Location.setX(25);
		pseudostate_ShallowHistoryStereotypeLabel_Location.setY(-10);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPseudostate_DeepHistoryShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PseudostateDeepHistoryEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		Node pseudostate_DeepHistoryFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateDeepHistoryFloatingLabelEditPart.VISUAL_ID));
		pseudostate_DeepHistoryFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_DeepHistoryFloatingNameLabel_Location = (Location) pseudostate_DeepHistoryFloatingNameLabel.getLayoutConstraint();
		pseudostate_DeepHistoryFloatingNameLabel_Location.setX(25);
		pseudostate_DeepHistoryFloatingNameLabel_Location.setY(3);
		Node pseudostate_DeepHistoryStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateDeepHistoryStereotypeEditPart.VISUAL_ID));
		pseudostate_DeepHistoryStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_DeepHistoryStereotypeLabel_Location = (Location) pseudostate_DeepHistoryStereotypeLabel.getLayoutConstraint();
		pseudostate_DeepHistoryStereotypeLabel_Location.setX(25);
		pseudostate_DeepHistoryStereotypeLabel_Location.setY(-10);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPseudostate_TerminateShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PseudostateTerminateEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		Node pseudostate_TerminateFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateTerminateFloatingLabelEditPart.VISUAL_ID));
		pseudostate_TerminateFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_TerminateFloatingNameLabel_Location = (Location) pseudostate_TerminateFloatingNameLabel.getLayoutConstraint();
		pseudostate_TerminateFloatingNameLabel_Location.setX(25);
		pseudostate_TerminateFloatingNameLabel_Location.setY(3);
		Node pseudostate_TerminateStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateTerminateStereotypeEditPart.VISUAL_ID));
		pseudostate_TerminateStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_TerminateStereotypeLabel_Location = (Location) pseudostate_TerminateStereotypeLabel.getLayoutConstraint();
		pseudostate_TerminateStereotypeLabel_Location.setX(25);
		pseudostate_TerminateStereotypeLabel_Location.setY(-10);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPseudostate_EntryPointShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PseudostateEntryPointEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		Node pseudostate_EntryPointFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateEntryPointFloatingLabelEditPart.VISUAL_ID));
		pseudostate_EntryPointFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_EntryPointFloatingNameLabel_Location = (Location) pseudostate_EntryPointFloatingNameLabel.getLayoutConstraint();
		pseudostate_EntryPointFloatingNameLabel_Location.setX(25);
		pseudostate_EntryPointFloatingNameLabel_Location.setY(3);
		Node pseudostate_EntryPointStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateEntryPointStereotypeEditPart.VISUAL_ID));
		pseudostate_EntryPointStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_EntryPointStereotypeLabel_Location = (Location) pseudostate_EntryPointStereotypeLabel.getLayoutConstraint();
		pseudostate_EntryPointStereotypeLabel_Location.setX(25);
		pseudostate_EntryPointStereotypeLabel_Location.setY(-10);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPseudostate_ExitPointShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PseudostateExitPointEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		Node pseudostate_ExitPointFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateExitPointFloatingLabelEditPart.VISUAL_ID));
		pseudostate_ExitPointFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_ExitPointFloatingNameLabel_Location = (Location) pseudostate_ExitPointFloatingNameLabel.getLayoutConstraint();
		pseudostate_ExitPointFloatingNameLabel_Location.setX(25);
		pseudostate_ExitPointFloatingNameLabel_Location.setY(3);
		Node pseudostate_ExitPointStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PseudostateExitPointStereotypeEditPart.VISUAL_ID));
		pseudostate_ExitPointStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location pseudostate_ExitPointStereotypeLabel_Location = (Location) pseudostate_ExitPointStereotypeLabel.getLayoutConstraint();
		pseudostate_ExitPointStereotypeLabel_Location.setX(25);
		pseudostate_ExitPointStereotypeLabel_Location.setY(-10);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "Pseudostate"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConnectionPointReference_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConnectionPointReferenceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ConnectionPointReference"); //$NON-NLS-1$
		Node connectionPointReference_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(ConnectionPointReferenceNameEditPart.VISUAL_ID));
		connectionPointReference_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location connectionPointReference_NameLabel_Location = (Location) connectionPointReference_NameLabel.getLayoutConstraint();
		connectionPointReference_NameLabel_Location.setX(25);
		connectionPointReference_NameLabel_Location.setY(3);
		Node connectionPointReference_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ConnectionPointReferenceStereotypeEditPart.VISUAL_ID));
		connectionPointReference_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location connectionPointReference_StereotypeLabel_Location = (Location) connectionPointReference_StereotypeLabel.getLayoutConstraint();
		connectionPointReference_StereotypeLabel_Location.setX(25);
		connectionPointReference_StereotypeLabel_Location.setY(-10);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ConnectionPointReference"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComment_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CommentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Comment"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CommentBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConstraintEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintNameLabelEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTransition_InternalTransitionLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(InternalTransitionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InternalTransition"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createBehavior_EntryBehaviorLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(EntryStateBehaviorEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "EntryStateBehavior"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createBehavior_DoActivityBehaviorLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(DoActivityStateBehaviorStateEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DoActivityStateBehavior"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createBehavior_ExitBehaviorLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(ExitStateBehaviorEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ExitStateBehavior"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTrigger_DeferrableTriggerLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(DeferrableTriggerEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DeferrableTrigger"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createTransition_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(TransitionEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Transition"); //$NON-NLS-1$
		Node transition_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(TransitionNameEditPart.VISUAL_ID));
		transition_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location transition_NameLabel_Location = (Location) transition_NameLabel.getLayoutConstraint();
		transition_NameLabel_Location.setX(0);
		transition_NameLabel_Location.setY(20);
		Node transition_GuardLabel = createLabel(edge, UMLVisualIDRegistry.getType(TransitionGuardEditPart.VISUAL_ID));
		transition_GuardLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location transition_GuardLabel_Location = (Location) transition_GuardLabel.getLayoutConstraint();
		transition_GuardLabel_Location.setX(0);
		transition_GuardLabel_Location.setY(-20);
		Node transition_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(TransitionStereotypeEditPart.VISUAL_ID));
		transition_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location transition_StereotypeLabel_Location = (Location) transition_StereotypeLabel.getLayoutConstraint();
		transition_StereotypeLabel_Location.setX(0);
		transition_StereotypeLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Transition"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createGeneralization_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(GeneralizationEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Generalization"); //$NON-NLS-1$
		Node generalization_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(GeneralizationStereotypeEditPart.VISUAL_ID));
		generalization_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location generalization_StereotypeLabel_Location = (Location) generalization_StereotypeLabel.getLayoutConstraint();
		generalization_StereotypeLabel_Location.setX(0);
		generalization_StereotypeLabel_Location.setY(40);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Generalization"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createComment_AnnotatedElementEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(CommentAnnotatedElementEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createConstraint_ConstrainedElementEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ConstraintConstrainedElementEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createConstraint_ContextEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ContextLinkEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		Node constraint_KeywordLabel = createLabel(edge, UMLVisualIDRegistry.getType(ContextLinkAppliedStereotypeEditPart.VISUAL_ID));
		constraint_KeywordLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Undefined"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	protected void stampShortcut(View containerView, Node target) {
		if (!PackageEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(containerView))) {
			EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
			shortcutAnnotation.getDetails().put("modelID", PackageEditPart.MODEL_ID); //$NON-NLS-1$
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
