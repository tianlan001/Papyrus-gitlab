/**
 * Copyright (c) 2018 CEA LIST and others.
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
package org.eclipse.papyrus.uml.diagram.activity.providers;

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
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper;

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
		return ActivityDiagramEditPart.MODEL_ID;
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateDiagramViewOperation op) {
		return ActivityDiagramEditPart.MODEL_ID.equals(op.getSemanticHint()) && UMLVisualIDRegistry.getDiagramVisualID(getSemanticElement(op.getSemanticAdapter())) != null
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
				if (!ActivityDiagramEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(op.getContainerView()))) {
					return false; // foreign diagram
				}
				if (visualID != null) {
					switch (visualID) {
					case ActivityEditPart.VISUAL_ID:
					case ParameterEditPart.VISUAL_ID:
					case ConstraintInActivityAsPrecondEditPart.VISUAL_ID:
					case ConstraintInActivityAsPostcondEditPart.VISUAL_ID:
					case InitialNodeEditPart.VISUAL_ID:
					case ActivityFinalNodeEditPart.VISUAL_ID:
					case FlowFinalNodeEditPart.VISUAL_ID:
					case OpaqueActionEditPart.VISUAL_ID:
					case ValuePinInOpaqueActEditPart.VISUAL_ID:
					case ActionInputPinInOpaqueActEditPart.VISUAL_ID:
					case InputPinInOpaqueActEditPart.VISUAL_ID:
					case OutputPinInOpaqueActEditPart.VISUAL_ID:
					case CallBehaviorActionEditPart.VISUAL_ID:
					case ValuePinInCallBeActEditPart.VISUAL_ID:
					case ActionInputPinInCallBeActEditPart.VISUAL_ID:
					case InputPinInCallBeActEditPart.VISUAL_ID:
					case OutputPinInCallBeActEditPart.VISUAL_ID:
					case CallOperationActionEditPart.VISUAL_ID:
					case ActionInputPinInCallOpActEditPart.VISUAL_ID:
					case ValuePinInCallOpActEditPart.VISUAL_ID:
					case InputPinInCallOpActEditPart.VISUAL_ID:
					case OutputPinInCallOpActEditPart.VISUAL_ID:
					case ValuePinInCallOpActAsTargetEditPart.VISUAL_ID:
					case ActionInputPinInCallOpActAsTargetEditPart.VISUAL_ID:
					case InputPinInCallOpActAsTargetEditPart.VISUAL_ID:
					case DurationConstraintAsLocalPrecondEditPart.VISUAL_ID:
					case DurationConstraintAsLocalPostcondEditPart.VISUAL_ID:
					case TimeConstraintAsLocalPrecondEditPart.VISUAL_ID:
					case TimeConstraintAsLocalPostcondEditPart.VISUAL_ID:
					case IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID:
					case IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID:
					case ConstraintAsLocalPrecondEditPart.VISUAL_ID:
					case ConstraintAsLocalPostcondEditPart.VISUAL_ID:
					case DecisionNodeEditPart.VISUAL_ID:
					case MergeNodeEditPart.VISUAL_ID:
					case ForkNodeEditPart.VISUAL_ID:
					case JoinNodeEditPart.VISUAL_ID:
					case DataStoreNodeEditPart.VISUAL_ID:
					case SendObjectActionEditPart.VISUAL_ID:
					case ValuePinInSendObjActAsReqEditPart.VISUAL_ID:
					case ActionInputPinInSendObjActAsReqEditPart.VISUAL_ID:
					case InputPinInSendObjActAsReqEditPart.VISUAL_ID:
					case ValuePinInSendObjActAsTargetEditPart.VISUAL_ID:
					case ActionInputPinInSendObjActAsTargetEditPart.VISUAL_ID:
					case InputPinInSendObjActAsTargetEditPart.VISUAL_ID:
					case SendSignalActionEditPart.VISUAL_ID:
					case ActionInputPinInSendSigActEditPart.VISUAL_ID:
					case ValuePinInSendSigActEditPart.VISUAL_ID:
					case InputPinInSendSigActEditPart.VISUAL_ID:
					case ValuePinInSendSigActAsTargetEditPart.VISUAL_ID:
					case ActionInputPinInSendSigActAsTargetEditPart.VISUAL_ID:
					case InputPinInSendSigActAsTargetEditPart.VISUAL_ID:
					case ActivityParameterNodeEditPart.VISUAL_ID:
					case AcceptEventActionEditPart.VISUAL_ID:
					case OutputPinInAcceptEventActionEditPart.VISUAL_ID:
					case ValueSpecificationActionEditPart.VISUAL_ID:
					case OutputPinInValSpecActEditPart.VISUAL_ID:
					case ConditionalNodeEditPart.VISUAL_ID:
					case ExpansionRegionEditPart.VISUAL_ID:
					case ExpansionNodeAsInEditPart.VISUAL_ID:
					case ExpansionNodeAsOutEditPart.VISUAL_ID:
					case LoopNodeEditPart.VISUAL_ID:
					case InputPinInLoopNodeAsVariableEditPart.VISUAL_ID:
					case ValuePinInLoopNodeAsVariableEditPart.VISUAL_ID:
					case ActionPinInLoopNodeAsVariableEditPart.VISUAL_ID:
					case OutputPinInLoopNodeAsBodyOutputEditPart.VISUAL_ID:
					case OutputPinInLoopNodeAsLoopVariableEditPart.VISUAL_ID:
					case OutputPinInLoopNodeAsResultEditPart.VISUAL_ID:
					case SequenceNodeEditPart.VISUAL_ID:
					case StructuredActivityNodeEditPart.VISUAL_ID:
					case InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
					case ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
					case ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
					case OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
					case ActivityPartitionEditPart.VISUAL_ID:
					case InterruptibleActivityRegionEditPart.VISUAL_ID:
					case CommentEditPartCN.VISUAL_ID:
					case ReadSelfActionEditPart.VISUAL_ID:
					case ReadSelfActionOutputPinEditPart.VISUAL_ID:
					case ActivityEditPartCN.VISUAL_ID:
					case CreateObjectActionEditPart.VISUAL_ID:
					case OutputPinInCreateObjectActionAsResultEditPart.VISUAL_ID:
					case ShapeNamedElementEditPart.VISUAL_ID:
					case ReadStructuralFeatureActionEditPart.VISUAL_ID:
					case InputPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
					case ValuePinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
					case ActionPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
					case OutputPinInReadStructuralFeatureAsResultEditPart.VISUAL_ID:
					case AddStructuralFeatureValueActionEditPart.VISUAL_ID:
					case InputPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
					case InputPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
					case InputPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
					case ValuePinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
					case ValuePinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
					case ValuePinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
					case ActionPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
					case ActionPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
					case ActionPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
					case OutputPinInAddStructuralFeatureValueActionAsResultEditPart.VISUAL_ID:
					case DestroyObjectActionEditPart.VISUAL_ID:
					case InputPinInDestroyObjectActionEditPart.VISUAL_ID:
					case ValuePinInDestroyObjectActionEditPart.VISUAL_ID:
					case ActionPinInDestroyObjectActionEditPart.VISUAL_ID:
					case ReadVariableActionEditPart.VISUAL_ID:
					case OutputPinInReadVariableActionAsResultEditPart.VISUAL_ID:
					case AddVariableValueActionEditPart.VISUAL_ID:
					case InputPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
					case InputPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
					case ValuePinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
					case ValuePinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
					case ActionPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
					case ActionPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
					case BroadcastSignalActionEditPart.VISUAL_ID:
					case InputPinInBroadcastSignalActionEditPart.VISUAL_ID:
					case ValuePinInBroadcastSignalActionEditPart.VISUAL_ID:
					case ActionPinInBroadcastSignalActionEditPart.VISUAL_ID:
					case CentralBufferNodeEditPart.VISUAL_ID:
					case ConstraintEditPartCN.VISUAL_ID:
					case StartObjectBehavoiurActionEditPart.VISUAL_ID:
					case OutputPinInStartObjectBehaviorActionEditPart.VISUAL_ID:
					case InputPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
					case ValuePinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
					case ActionPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
					case InputPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
					case ValuePinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
					case ActionPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
					case TestIdentityActionEditPart.VISUAL_ID:
					case OutputPinInTestIdentityActionEditPart.VISUAL_ID:
					case InputPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
					case InputPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
					case ValuePinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
					case ValuePinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
					case ActionPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
					case ActionPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
					case ClearStructuralFeatureActionEditPart.VISUAL_ID:
					case OutputPinInClearStructuralFeatureActionEditPart.VISUAL_ID:
					case InputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
					case ValuePinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
					case ActionInputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
					case CreateLinkActionEditPart.VISUAL_ID:
					case InputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
					case ValuePinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
					case ActionInputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
					case ReadLinkActionEditPart.VISUAL_ID:
					case OutputPinInReadLinkActionEditPart.VISUAL_ID:
					case InputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
					case ValuePinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
					case ActionInputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
					case DestroyLinkActionEditPart.VISUAL_ID:
					case InputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
					case ValuePinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
					case ActionInputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
					case ClearAssociationActionEditPart.VISUAL_ID:
					case InputPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
					case ValuePinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
					case ActionPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
					case ReadExtentActionEditPart.VISUAL_ID:
					case OutputPinInReadExtentActionEditPart.VISUAL_ID:
					case ReclassifyObjectActionEditPart.VISUAL_ID:
					case InputPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
					case ValuePinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
					case ActionPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
					case ReadIsClassifiedObjectActionEditPart.VISUAL_ID:
					case OutputPinInReadIsClassifiedObjectActionEditPart.VISUAL_ID:
					case InputPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
					case ValuePinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
					case ActionPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
					case ReduceActionEditPart.VISUAL_ID:
					case OutputPinInReduceActionEditPart.VISUAL_ID:
					case InputPinInReduceActionAsCollectionEditPart.VISUAL_ID:
					case ValuePinInReduceActionAsCollectionEditPart.VISUAL_ID:
					case ActionPinInReduceActionAsCollectionEditPart.VISUAL_ID:
					case StartClassifierBehaviorActionEditPart.VISUAL_ID:
					case InputPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
					case ValuePinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
					case ActionPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
					case CreateLinkObjectActionEditPart.VISUAL_ID:
					case InputPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
					case ValuePinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
					case ActionPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
					case OutputPinInCreateLinkObjectActionEditPart.VISUAL_ID:
					case UnmarshallActionEditPart.VISUAL_ID:
					case InputPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
					case ValuePinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
					case ActionPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
					case OutputPinInUnmarshallActionAsResultEditPart.VISUAL_ID:
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
		diagram.setType(ActivityDiagramEditPart.MODEL_ID);
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
			case ActivityEditPart.VISUAL_ID:
				return createActivity_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ParameterEditPart.VISUAL_ID:
				return createParameter_ParameterLabel(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintInActivityAsPrecondEditPart.VISUAL_ID:
				return createConstraint_PreconditionLabel(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintInActivityAsPostcondEditPart.VISUAL_ID:
				return createConstraint_PostconditionLabel(domainElement, containerView, index, persisted, preferencesHint);
			case InitialNodeEditPart.VISUAL_ID:
				return createInitialNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ActivityFinalNodeEditPart.VISUAL_ID:
				return createActivityFinalNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case FlowFinalNodeEditPart.VISUAL_ID:
				return createFlowFinalNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OpaqueActionEditPart.VISUAL_ID:
				return createOpaqueAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInOpaqueActEditPart.VISUAL_ID:
				return createValuePin_OpaqueActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInOpaqueActEditPart.VISUAL_ID:
				return createActionInputPin_OpaqueActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInOpaqueActEditPart.VISUAL_ID:
				return createInputPin_OpaqueActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInOpaqueActEditPart.VISUAL_ID:
				return createOutputPin_OpaqueActionOutputShape(domainElement, containerView, index, persisted, preferencesHint);
			case CallBehaviorActionEditPart.VISUAL_ID:
				return createCallBehaviorAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInCallBeActEditPart.VISUAL_ID:
				return createValuePin_CallBehaviorActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInCallBeActEditPart.VISUAL_ID:
				return createActionInputPin_CallBehaviorActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInCallBeActEditPart.VISUAL_ID:
				return createInputPin_CallBehaviorActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInCallBeActEditPart.VISUAL_ID:
				return createOutputPin_CallBehaviorActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case CallOperationActionEditPart.VISUAL_ID:
				return createCallOperationAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInCallOpActEditPart.VISUAL_ID:
				return createActionInputPin_CallOperationActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInCallOpActEditPart.VISUAL_ID:
				return createValuePin_CallOperationActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInCallOpActEditPart.VISUAL_ID:
				return createInputPin_CallOperationActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInCallOpActEditPart.VISUAL_ID:
				return createOutputPin_CallOperationActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInCallOpActAsTargetEditPart.VISUAL_ID:
				return createValuePin_CallOperationActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInCallOpActAsTargetEditPart.VISUAL_ID:
				return createActionInputPin_CallOperationActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInCallOpActAsTargetEditPart.VISUAL_ID:
				return createInputPin_CallOperationActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case DurationConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return createDurationConstraint_LocalPreconditionShape(domainElement, containerView, index, persisted, preferencesHint);
			case DurationConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return createDurationConstraint_LocalPostconditionShape(domainElement, containerView, index, persisted, preferencesHint);
			case TimeConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return createTimeConstraint_LocalPreconditionShape(domainElement, containerView, index, persisted, preferencesHint);
			case TimeConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return createTimeConstraint_LocalPostconditionShape(domainElement, containerView, index, persisted, preferencesHint);
			case IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return createIntervalConstraint_LocalPreconditionShape(domainElement, containerView, index, persisted, preferencesHint);
			case IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return createIntervalConstraint_LocalPostconditionShape(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return createConstraint_LocalPreconditionShape(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return createConstraint_LocalPostconditionShape(domainElement, containerView, index, persisted, preferencesHint);
			case DecisionNodeEditPart.VISUAL_ID:
				return createDecisionNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case MergeNodeEditPart.VISUAL_ID:
				return createMergeNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ForkNodeEditPart.VISUAL_ID:
				return createForkNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case JoinNodeEditPart.VISUAL_ID:
				return createJoinNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DataStoreNodeEditPart.VISUAL_ID:
				return createDataStoreNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case SendObjectActionEditPart.VISUAL_ID:
				return createSendObjectAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInSendObjActAsReqEditPart.VISUAL_ID:
				return createValuePin_SendObjectActionRequestShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInSendObjActAsReqEditPart.VISUAL_ID:
				return createActionInputPin_SendObjectActionRequestShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInSendObjActAsReqEditPart.VISUAL_ID:
				return createInputPin_SendObjectActionRequestShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInSendObjActAsTargetEditPart.VISUAL_ID:
				return createValuePin_SendObjectActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInSendObjActAsTargetEditPart.VISUAL_ID:
				return createActionInputPin_SendObjectActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInSendObjActAsTargetEditPart.VISUAL_ID:
				return createInputPin_SendObjectActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case SendSignalActionEditPart.VISUAL_ID:
				return createSendSignalAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInSendSigActEditPart.VISUAL_ID:
				return createActionInputPin_SendSignalActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInSendSigActEditPart.VISUAL_ID:
				return createValuePin_SendSignalActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInSendSigActEditPart.VISUAL_ID:
				return createInputPin_SendSignalActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInSendSigActAsTargetEditPart.VISUAL_ID:
				return createValuePin_SendSignalActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInSendSigActAsTargetEditPart.VISUAL_ID:
				return createActionInputPin_SendSignalActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInSendSigActAsTargetEditPart.VISUAL_ID:
				return createInputPin_SendSignalActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActivityParameterNodeEditPart.VISUAL_ID:
				return createActivityParameterNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case AcceptEventActionEditPart.VISUAL_ID:
				return createAcceptEventAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInAcceptEventActionEditPart.VISUAL_ID:
				return createOutputPin_AcceptEventActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValueSpecificationActionEditPart.VISUAL_ID:
				return createValueSpecificationAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInValSpecActEditPart.VISUAL_ID:
				return createOutputPin_ValueSpecificationActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case ConditionalNodeEditPart.VISUAL_ID:
				return createConditionalNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ExpansionRegionEditPart.VISUAL_ID:
				return createExpansionRegion_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ExpansionNodeAsInEditPart.VISUAL_ID:
				return createExpansionNode_InputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ExpansionNodeAsOutEditPart.VISUAL_ID:
				return createExpansionNode_OutputShape(domainElement, containerView, index, persisted, preferencesHint);
			case LoopNodeEditPart.VISUAL_ID:
				return createLoopNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return createInputPin_LoopNodeVariableInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return createValuePin_LoopNodeVariableInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return createActionInputPin_LoopNodeVariableInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInLoopNodeAsBodyOutputEditPart.VISUAL_ID:
				return createOutputPin_LoopNodeBodyOutputShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInLoopNodeAsLoopVariableEditPart.VISUAL_ID:
				return createOutputPin_LoopNodeVariableShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInLoopNodeAsResultEditPart.VISUAL_ID:
				return createOutputPin_LoopNodeResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case SequenceNodeEditPart.VISUAL_ID:
				return createSequenceNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case StructuredActivityNodeEditPart.VISUAL_ID:
				return createStructuredActivityNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return createInputPin_StructuredActivityNodeInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return createValuePin_StructuredActivityNodeInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return createActionInputPin_StructuredActivityNodeInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return createOutputPin_StructuredActivityNodeOutputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActivityPartitionEditPart.VISUAL_ID:
				return createActivityPartition_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InterruptibleActivityRegionEditPart.VISUAL_ID:
				return createInterruptibleActivityRegion_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case CommentEditPartCN.VISUAL_ID:
				return createComment_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ReadSelfActionEditPart.VISUAL_ID:
				return createReadSelfAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ReadSelfActionOutputPinEditPart.VISUAL_ID:
				return createOutputPin_ReadSelfActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActivityEditPartCN.VISUAL_ID:
				return createActivity_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case CreateObjectActionEditPart.VISUAL_ID:
				return createCreateObjectAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInCreateObjectActionAsResultEditPart.VISUAL_ID:
				return createOutputPin_CreateObjectActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case ShapeNamedElementEditPart.VISUAL_ID:
				return createNamedElement_DefaultShape(domainElement, containerView, index, persisted, preferencesHint);
			case ReadStructuralFeatureActionEditPart.VISUAL_ID:
				return createReadStructuralFeatureAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return createInputPin_ReadStructuralFeatureActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return createValuePin_ReadStructuralFeatureActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return createActionInputPin_ReadStructuralFeatureActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInReadStructuralFeatureAsResultEditPart.VISUAL_ID:
				return createOutputPin_ReadStructuralFeatureActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case AddStructuralFeatureValueActionEditPart.VISUAL_ID:
				return createAddStructuralFeatureValueAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return createInputPin_AddStructuralFeatureValueActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return createInputPin_AddStructuralFeatureValueActionValueShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return createInputPin_AddStructuralFeatureValueActionInsertAtShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return createValuePin_AddStructuralFeatureValueActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return createValuePin_AddStructuralFeatureValueActionValueShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return createValuePin_AddStructuralFeatureValueActionInsertAtShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return createActionInputPin_AddStructuralFeatureValueActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return createActionInputPin_AddStructuralFeatureValueActionValueShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return createActionInputPin_AddStructuralFeatureValueActionInsertAtShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInAddStructuralFeatureValueActionAsResultEditPart.VISUAL_ID:
				return createOutputPin_AddStructuralFeatureValueActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case DestroyObjectActionEditPart.VISUAL_ID:
				return createDestroyObjectAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInDestroyObjectActionEditPart.VISUAL_ID:
				return createInputPin_DestroyObjectActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInDestroyObjectActionEditPart.VISUAL_ID:
				return createValuePin_DestroyObjectActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInDestroyObjectActionEditPart.VISUAL_ID:
				return createActionInputPin_DestroyObjectActionTargetShape(domainElement, containerView, index, persisted, preferencesHint);
			case ReadVariableActionEditPart.VISUAL_ID:
				return createReadVariableAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInReadVariableActionAsResultEditPart.VISUAL_ID:
				return createOutputPin_ReadVariableActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case AddVariableValueActionEditPart.VISUAL_ID:
				return createAddVariableValueAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return createInputPin_AddVariableValueActionInsertAtShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return createInputPin_AddVariableValueActionValueShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return createValuePin_AddVariableValueActionInsertAtShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return createValuePin_AddVariableValueActionValueShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return createActionInputPin_AddVariableValueActionInsertAtShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return createActionInputPin_AddVariableValueActionValueShape(domainElement, containerView, index, persisted, preferencesHint);
			case BroadcastSignalActionEditPart.VISUAL_ID:
				return createBroadcastSignalAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInBroadcastSignalActionEditPart.VISUAL_ID:
				return createInputPin_BroadcastSignalActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInBroadcastSignalActionEditPart.VISUAL_ID:
				return createValuePin_BroadcastSignalActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInBroadcastSignalActionEditPart.VISUAL_ID:
				return createActionInputPin_BroadcastSignalActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case CentralBufferNodeEditPart.VISUAL_ID:
				return createCentralBufferNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintEditPartCN.VISUAL_ID:
				return createConstraint_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case StartObjectBehavoiurActionEditPart.VISUAL_ID:
				return createStartObjectBehaviorAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInStartObjectBehaviorActionEditPart.VISUAL_ID:
				return createOutputPin_StartObjectBehaviorActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return createInputPin_StartObjectBehaviorActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return createValuePin_StartObjectBehaviorActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return createActionInputPin_StartObjectBehaviorActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return createInputPin_StartObjectBehaviorActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return createValuePin_StartObjectBehaviorActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return createActionInputPin_StartObjectBehaviorActionArgumentShape(domainElement, containerView, index, persisted, preferencesHint);
			case TestIdentityActionEditPart.VISUAL_ID:
				return createTestIdentityAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInTestIdentityActionEditPart.VISUAL_ID:
				return createOutputPin_TestIdentityActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return createInputPin_TestIdentityActionFirstShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return createInputPin_TestIdentityActionSecondShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return createValuePin_TestIdentityActionFirstShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return createValuePin_TestIdentityActionSecondShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return createActionInputPin_TestIdentityActionFirstShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return createActionInputPin_TestIdentityActionSecondShape(domainElement, containerView, index, persisted, preferencesHint);
			case ClearStructuralFeatureActionEditPart.VISUAL_ID:
				return createClearStructuralFeatureAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInClearStructuralFeatureActionEditPart.VISUAL_ID:
				return createOutputPin_ClearStructuralFeatureActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return createInputPin_ClearStructuralFeatureActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return createValuePin_ClearStructuralFeatureActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return createActionInputPin_ClearStructuralFeatureActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case CreateLinkActionEditPart.VISUAL_ID:
				return createCreateLinkAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return createInputPin_CreateLinkActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return createValuePin_CreateLinkActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return createActionInputPin_CreateLinkActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ReadLinkActionEditPart.VISUAL_ID:
				return createReadLinkAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInReadLinkActionEditPart.VISUAL_ID:
				return createOutputPin_ReadLinkActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return createInputPin_ReadLinkActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return createValuePin_ReadLinkActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return createActionInputPin_ReadLinkActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case DestroyLinkActionEditPart.VISUAL_ID:
				return createDestroyLinkAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return createInputPin_DestroyLinkActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return createValuePin_DestroyLinkActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionInputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return createActionInputPin_DestroyLinkActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ClearAssociationActionEditPart.VISUAL_ID:
				return createClearAssociationAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return createInputPin_ClearAssociationActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return createValuePin_ClearAssociationActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return createActionInputPin_ClearAssociationActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ReadExtentActionEditPart.VISUAL_ID:
				return createReadExtentAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInReadExtentActionEditPart.VISUAL_ID:
				return createOutputPin_ReadExtentActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case ReclassifyObjectActionEditPart.VISUAL_ID:
				return createReclassifyObjectAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return createInputPin_ReclassifyObjectActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return createValuePin_ReclassifyObjectActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return createActionInputPin_ReclassifyObjectActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return createReadIsClassifiedObjectAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return createOutputPin_ReadIsClassifiedObjectActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return createInputPin_ReadIsClassifiedObjectActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return createValuePin_ReadIsClassifiedObjectActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return createActionInputPin_ReadIsClassifiedObjectActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ReduceActionEditPart.VISUAL_ID:
				return createReduceAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInReduceActionEditPart.VISUAL_ID:
				return createOutputPin_ReduceActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return createInputPin_ReduceActionCollectionShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return createValuePin_ReduceActionCollectionShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return createActionInputPin_ReduceActionCollectionShape(domainElement, containerView, index, persisted, preferencesHint);
			case StartClassifierBehaviorActionEditPart.VISUAL_ID:
				return createStartClassifierBehaviorAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return createInputPin_StartClassifierBehaviorActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return createValuePin_StartClassifierBehaviorActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return createActionInputPin_StartClassifierBehaviorActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case CreateLinkObjectActionEditPart.VISUAL_ID:
				return createCreateLinkObjectAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return createInputPin_CreateLinkObjectActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return createValuePin_CreateLinkObjectActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return createActionInputPin_CreateLinkObjectActionInputShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInCreateLinkObjectActionEditPart.VISUAL_ID:
				return createOutputPin_CreateLinkObjectActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
			case UnmarshallActionEditPart.VISUAL_ID:
				return createUnmarshallAction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InputPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return createInputPin_UnmarshallActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ValuePinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return createValuePin_UnmarshallActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case ActionPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return createActionInputPin_UnmarshallActionObjectShape(domainElement, containerView, index, persisted, preferencesHint);
			case OutputPinInUnmarshallActionAsResultEditPart.VISUAL_ID:
				return createOutputPin_UnmarshallActionResultShape(domainElement, containerView, index, persisted, preferencesHint);
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
			case ActionLocalPreconditionEditPart.VISUAL_ID:
				return createAction_LocalPreconditionEdge(containerView, index, persisted, preferencesHint);
			case ActionLocalPostconditionEditPart.VISUAL_ID:
				return createAction_LocalPostconditionEdge(containerView, index, persisted, preferencesHint);
			case ObjectFlowEditPart.VISUAL_ID:
				return createObjectFlow_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case ControlFlowEditPart.VISUAL_ID:
				return createControlFlow_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case ExceptionHandlerEditPart.VISUAL_ID:
				return createExceptionHandler_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case CommentLinkEditPart.VISUAL_ID:
				return createComment_AnnotatedElementEdge(containerView, index, persisted, preferencesHint);
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return createConstraint_ConstrainedElementEdge(containerView, index, persisted, preferencesHint);
			}
		}
		// can never happen, provided #provides(CreateEdgeViewOperation) is correct
		return null;
	}

	/**
	 * @generated
	 */
	public Node createActivity_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActivityEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Activity"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ActivityNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ActivityIsSingleExecutionEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityActivityParametersCompartmentEditPart.VISUAL_ID), false, false, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityActivityPreConditionsCompartmentEditPart.VISUAL_ID), false, false, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityActivityPostConditionsCompartmentEditPart.VISUAL_ID), false, false, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityActivityContentCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Activity"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createParameter_ParameterLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(ParameterEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Parameter"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_PreconditionLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(ConstraintInActivityAsPrecondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_PostconditionLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(ConstraintInActivityAsPostcondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInitialNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InitialNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InitialNode"); //$NON-NLS-1$
		Node initialNode_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InitialNodeFloatingNameEditPart.VISUAL_ID));
		initialNode_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location initialNode_FloatingNameLabel_Location = (Location) initialNode_FloatingNameLabel.getLayoutConstraint();
		initialNode_FloatingNameLabel_Location.setX(0);
		initialNode_FloatingNameLabel_Location.setY(15);
		Node initialNode_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InitialNodeAppliedStereotypeEditPart.VISUAL_ID));
		initialNode_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location initialNode_StereotypeLabel_Location = (Location) initialNode_StereotypeLabel.getLayoutConstraint();
		initialNode_StereotypeLabel_Location.setX(0);
		initialNode_StereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "InitialNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActivityFinalNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActivityFinalNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActivityFinalNode"); //$NON-NLS-1$
		Node activityFinalNode_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActivityFinalNodeFloatingNameEditPart.VISUAL_ID));
		activityFinalNode_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location activityFinalNode_FloatingNameLabel_Location = (Location) activityFinalNode_FloatingNameLabel.getLayoutConstraint();
		activityFinalNode_FloatingNameLabel_Location.setX(0);
		activityFinalNode_FloatingNameLabel_Location.setY(15);
		Node activityFinalNode_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActivityFinalNodeAppliedStereotypeEditPart.VISUAL_ID));
		activityFinalNode_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location activityFinalNode_StereotypeLabel_Location = (Location) activityFinalNode_StereotypeLabel.getLayoutConstraint();
		activityFinalNode_StereotypeLabel_Location.setX(0);
		activityFinalNode_StereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ActivityFinalNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createFlowFinalNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(FlowFinalNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "FlowFinalNode"); //$NON-NLS-1$
		Node flowFinalNode_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(FlowFinalNodeFloatingNameEditPart.VISUAL_ID));
		flowFinalNode_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location flowFinalNode_FloatingNameLabel_Location = (Location) flowFinalNode_FloatingNameLabel.getLayoutConstraint();
		flowFinalNode_FloatingNameLabel_Location.setX(0);
		flowFinalNode_FloatingNameLabel_Location.setY(15);
		Node flowFinalNode_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(FlowFinalNodeAppliedStereotypeEditPart.VISUAL_ID));
		flowFinalNode_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location flowFinalNode_StereotypeLabel_Location = (Location) flowFinalNode_StereotypeLabel.getLayoutConstraint();
		flowFinalNode_StereotypeLabel_Location.setX(0);
		flowFinalNode_StereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "FlowFinalNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOpaqueAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OpaqueActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OpaqueAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(OpaqueActionNameEditPart.VISUAL_ID));
		Node opaqueAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OpaqueActionFloatingNameEditPart.VISUAL_ID));
		opaqueAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location opaqueAction_FloatingNameLabel_Location = (Location) opaqueAction_FloatingNameLabel.getLayoutConstraint();
		opaqueAction_FloatingNameLabel_Location.setX(0);
		opaqueAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_OpaqueActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInOpaqueActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_OpaqueActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInOActLabelEditPart.VISUAL_ID));
		valuePin_OpaqueActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_OpaqueActionInputNameLabel_Location = (Location) valuePin_OpaqueActionInputNameLabel.getLayoutConstraint();
		valuePin_OpaqueActionInputNameLabel_Location.setX(0);
		valuePin_OpaqueActionInputNameLabel_Location.setY(15);
		Node valuePin_OpaqueActionInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInOActValueEditPart.VISUAL_ID));
		valuePin_OpaqueActionInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_OpaqueActionInputValueLabel_Location = (Location) valuePin_OpaqueActionInputValueLabel.getLayoutConstraint();
		valuePin_OpaqueActionInputValueLabel_Location.setX(0);
		valuePin_OpaqueActionInputValueLabel_Location.setY(15);
		Node valuePin_OpaqueActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInOActAppliedStereotypeEditPart.VISUAL_ID));
		valuePin_OpaqueActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_OpaqueActionInputStereotypeLabel_Location = (Location) valuePin_OpaqueActionInputStereotypeLabel.getLayoutConstraint();
		valuePin_OpaqueActionInputStereotypeLabel_Location.setX(0);
		valuePin_OpaqueActionInputStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_OpaqueActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInOpaqueActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_OpaqueActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInOActLabelEditPart.VISUAL_ID));
		actionInputPin_OpaqueActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_OpaqueActionInputNameLabel_Location = (Location) actionInputPin_OpaqueActionInputNameLabel.getLayoutConstraint();
		actionInputPin_OpaqueActionInputNameLabel_Location.setX(0);
		actionInputPin_OpaqueActionInputNameLabel_Location.setY(15);
		Node actionInputPin_OpaqueActionInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInOActValueEditPart.VISUAL_ID));
		actionInputPin_OpaqueActionInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_OpaqueActionInputValueLabel_Location = (Location) actionInputPin_OpaqueActionInputValueLabel.getLayoutConstraint();
		actionInputPin_OpaqueActionInputValueLabel_Location.setX(0);
		actionInputPin_OpaqueActionInputValueLabel_Location.setY(15);
		Node actionInputPin_OpaqueActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInOActAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_OpaqueActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_OpaqueActionInputStereotypeLabel_Location = (Location) actionInputPin_OpaqueActionInputStereotypeLabel.getLayoutConstraint();
		actionInputPin_OpaqueActionInputStereotypeLabel_Location.setX(0);
		actionInputPin_OpaqueActionInputStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_OpaqueActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInOpaqueActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_OpaqueActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInOActLabelEditPart.VISUAL_ID));
		inputPin_OpaqueActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_OpaqueActionInputNameLabel_Location = (Location) inputPin_OpaqueActionInputNameLabel.getLayoutConstraint();
		inputPin_OpaqueActionInputNameLabel_Location.setX(0);
		inputPin_OpaqueActionInputNameLabel_Location.setY(15);
		Node inputPin_OpaqueActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInOActAppliedStereotypeEditPart.VISUAL_ID));
		inputPin_OpaqueActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_OpaqueActionInputStereotypeLabel_Location = (Location) inputPin_OpaqueActionInputStereotypeLabel.getLayoutConstraint();
		inputPin_OpaqueActionInputStereotypeLabel_Location.setX(0);
		inputPin_OpaqueActionInputStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_OpaqueActionOutputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInOpaqueActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_OpaqueActionOutputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInOActLabelEditPart.VISUAL_ID));
		outputPin_OpaqueActionOutputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_OpaqueActionOutputNameLabel_Location = (Location) outputPin_OpaqueActionOutputNameLabel.getLayoutConstraint();
		outputPin_OpaqueActionOutputNameLabel_Location.setX(0);
		outputPin_OpaqueActionOutputNameLabel_Location.setY(15);
		Node outputPin_OpaqueActionOutputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInOActAppliedStereotypeEditPart.VISUAL_ID));
		outputPin_OpaqueActionOutputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_OpaqueActionOutputStereotypeLabel_Location = (Location) outputPin_OpaqueActionOutputStereotypeLabel.getLayoutConstraint();
		outputPin_OpaqueActionOutputStereotypeLabel_Location.setX(0);
		outputPin_OpaqueActionOutputStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createCallBehaviorAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CallBehaviorActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CallBehaviorAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CallBehaviorActionNameEditPart.VISUAL_ID));
		Node callBehaviorAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(CallBehaviorActionFloatingNameEditPart.VISUAL_ID));
		callBehaviorAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location callBehaviorAction_FloatingNameLabel_Location = (Location) callBehaviorAction_FloatingNameLabel.getLayoutConstraint();
		callBehaviorAction_FloatingNameLabel_Location.setX(0);
		callBehaviorAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_CallBehaviorActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInCallBeActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_CallBehaviorActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCBActLabelEditPart.VISUAL_ID));
		valuePin_CallBehaviorActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CallBehaviorActionArgumentNameLabel_Location = (Location) valuePin_CallBehaviorActionArgumentNameLabel.getLayoutConstraint();
		valuePin_CallBehaviorActionArgumentNameLabel_Location.setX(0);
		valuePin_CallBehaviorActionArgumentNameLabel_Location.setY(15);
		Node valuePin_CallBehaviorActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCBActValueEditPart.VISUAL_ID));
		valuePin_CallBehaviorActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CallBehaviorActionArgumentValueLabel_Location = (Location) valuePin_CallBehaviorActionArgumentValueLabel.getLayoutConstraint();
		valuePin_CallBehaviorActionArgumentValueLabel_Location.setX(0);
		valuePin_CallBehaviorActionArgumentValueLabel_Location.setY(15);
		Node valuePin_CallBehaviorActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCBActAppliedStereotypeEditPart.VISUAL_ID));
		valuePin_CallBehaviorActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CallBehaviorActionArgumentStereotypeLabel_Location = (Location) valuePin_CallBehaviorActionArgumentStereotypeLabel.getLayoutConstraint();
		valuePin_CallBehaviorActionArgumentStereotypeLabel_Location.setX(0);
		valuePin_CallBehaviorActionArgumentStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_CallBehaviorActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInCallBeActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_CallBehaviorActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCBActLabelEditPart.VISUAL_ID));
		actionInputPin_CallBehaviorActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CallBehaviorActionArgumentNameLabel_Location = (Location) actionInputPin_CallBehaviorActionArgumentNameLabel.getLayoutConstraint();
		actionInputPin_CallBehaviorActionArgumentNameLabel_Location.setX(0);
		actionInputPin_CallBehaviorActionArgumentNameLabel_Location.setY(15);
		Node actionInputPin_CallBehaviorActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCBActValueEditPart.VISUAL_ID));
		actionInputPin_CallBehaviorActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CallBehaviorActionArgumentValueLabel_Location = (Location) actionInputPin_CallBehaviorActionArgumentValueLabel.getLayoutConstraint();
		actionInputPin_CallBehaviorActionArgumentValueLabel_Location.setX(0);
		actionInputPin_CallBehaviorActionArgumentValueLabel_Location.setY(15);
		Node actionInputPin_CallBehaviorActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCBActAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_CallBehaviorActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CallBehaviorActionArgumentStereotypeLabel_Location = (Location) actionInputPin_CallBehaviorActionArgumentStereotypeLabel.getLayoutConstraint();
		actionInputPin_CallBehaviorActionArgumentStereotypeLabel_Location.setX(0);
		actionInputPin_CallBehaviorActionArgumentStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_CallBehaviorActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInCallBeActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_CallBehaviorActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInCBActLabelEditPart.VISUAL_ID));
		inputPin_CallBehaviorActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_CallBehaviorActionArgumentNameLabel_Location = (Location) inputPin_CallBehaviorActionArgumentNameLabel.getLayoutConstraint();
		inputPin_CallBehaviorActionArgumentNameLabel_Location.setX(0);
		inputPin_CallBehaviorActionArgumentNameLabel_Location.setY(15);
		Node inputPin_CallBehaviorActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInCBActAppliedStereotypeEditPart.VISUAL_ID));
		inputPin_CallBehaviorActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_CallBehaviorActionArgumentStereotypeLabel_Location = (Location) inputPin_CallBehaviorActionArgumentStereotypeLabel.getLayoutConstraint();
		inputPin_CallBehaviorActionArgumentStereotypeLabel_Location.setX(0);
		inputPin_CallBehaviorActionArgumentStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_CallBehaviorActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInCallBeActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_CallBehaviorActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInCBActLabelEditPart.VISUAL_ID));
		outputPin_CallBehaviorActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_CallBehaviorActionResultNameLabel_Location = (Location) outputPin_CallBehaviorActionResultNameLabel.getLayoutConstraint();
		outputPin_CallBehaviorActionResultNameLabel_Location.setX(0);
		outputPin_CallBehaviorActionResultNameLabel_Location.setY(15);
		Node outputPin_CallBehaviorActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInCBActAppliedStereotypeEditPart.VISUAL_ID));
		outputPin_CallBehaviorActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_CallBehaviorActionResultStereotypeLabel_Location = (Location) outputPin_CallBehaviorActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_CallBehaviorActionResultStereotypeLabel_Location.setX(0);
		outputPin_CallBehaviorActionResultStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createCallOperationAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CallOperationActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CallOperationAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CallOperationActionNameEditPart.VISUAL_ID));
		Node callOperationAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(CallOperationActionFloatingNameEditPart.VISUAL_ID));
		callOperationAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location callOperationAction_FloatingNameLabel_Location = (Location) callOperationAction_FloatingNameLabel.getLayoutConstraint();
		callOperationAction_FloatingNameLabel_Location.setX(0);
		callOperationAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_CallOperationActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInCallOpActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_CallOperationActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCOActLabelEditPart.VISUAL_ID));
		actionInputPin_CallOperationActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CallOperationActionArgumentNameLabel_Location = (Location) actionInputPin_CallOperationActionArgumentNameLabel.getLayoutConstraint();
		actionInputPin_CallOperationActionArgumentNameLabel_Location.setX(0);
		actionInputPin_CallOperationActionArgumentNameLabel_Location.setY(15);
		Node actionInputPin_CallOperationActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCOActValueEditPart.VISUAL_ID));
		actionInputPin_CallOperationActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CallOperationActionArgumentValueLabel_Location = (Location) actionInputPin_CallOperationActionArgumentValueLabel.getLayoutConstraint();
		actionInputPin_CallOperationActionArgumentValueLabel_Location.setX(0);
		actionInputPin_CallOperationActionArgumentValueLabel_Location.setY(15);
		Node actionInputPin_CallOperationActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCOActAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_CallOperationActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CallOperationActionArgumentStereotypeLabel_Location = (Location) actionInputPin_CallOperationActionArgumentStereotypeLabel.getLayoutConstraint();
		actionInputPin_CallOperationActionArgumentStereotypeLabel_Location.setX(0);
		actionInputPin_CallOperationActionArgumentStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_CallOperationActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInCallOpActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_CallOperationActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCOActLabelEditPart.VISUAL_ID));
		valuePin_CallOperationActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CallOperationActionArgumentNameLabel_Location = (Location) valuePin_CallOperationActionArgumentNameLabel.getLayoutConstraint();
		valuePin_CallOperationActionArgumentNameLabel_Location.setX(0);
		valuePin_CallOperationActionArgumentNameLabel_Location.setY(15);
		Node valuePin_CallOperationActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCOActValueEditPart.VISUAL_ID));
		valuePin_CallOperationActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CallOperationActionArgumentValueLabel_Location = (Location) valuePin_CallOperationActionArgumentValueLabel.getLayoutConstraint();
		valuePin_CallOperationActionArgumentValueLabel_Location.setX(0);
		valuePin_CallOperationActionArgumentValueLabel_Location.setY(15);
		Node valuePin_CallOperationActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCOActAppliedStereotypeEditPart.VISUAL_ID));
		valuePin_CallOperationActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CallOperationActionArgumentStereotypeLabel_Location = (Location) valuePin_CallOperationActionArgumentStereotypeLabel.getLayoutConstraint();
		valuePin_CallOperationActionArgumentStereotypeLabel_Location.setX(0);
		valuePin_CallOperationActionArgumentStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_CallOperationActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInCallOpActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_CallOperationActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInCOActLabelEditPart.VISUAL_ID));
		inputPin_CallOperationActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_CallOperationActionArgumentNameLabel_Location = (Location) inputPin_CallOperationActionArgumentNameLabel.getLayoutConstraint();
		inputPin_CallOperationActionArgumentNameLabel_Location.setX(0);
		inputPin_CallOperationActionArgumentNameLabel_Location.setY(15);
		Node inputPin_CallOperationActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInCOActAppliedStereotypeEditPart.VISUAL_ID));
		inputPin_CallOperationActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_CallOperationActionArgumentStereotypeLabel_Location = (Location) inputPin_CallOperationActionArgumentStereotypeLabel.getLayoutConstraint();
		inputPin_CallOperationActionArgumentStereotypeLabel_Location.setX(0);
		inputPin_CallOperationActionArgumentStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_CallOperationActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInCallOpActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_CallOperationActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInCOActLabelEditPart.VISUAL_ID));
		outputPin_CallOperationActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_CallOperationActionResultNameLabel_Location = (Location) outputPin_CallOperationActionResultNameLabel.getLayoutConstraint();
		outputPin_CallOperationActionResultNameLabel_Location.setX(0);
		outputPin_CallOperationActionResultNameLabel_Location.setY(15);
		Node outputPin_CallOperationActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInCOActAppliedStereotypeEditPart.VISUAL_ID));
		outputPin_CallOperationActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_CallOperationActionResultStereotypeLabel_Location = (Location) outputPin_CallOperationActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_CallOperationActionResultStereotypeLabel_Location.setX(0);
		outputPin_CallOperationActionResultStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_CallOperationActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInCallOpActAsTargetEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_CallOperationActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCOActAsTargetLabelEditPart.VISUAL_ID));
		valuePin_CallOperationActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CallOperationActionTargetNameLabel_Location = (Location) valuePin_CallOperationActionTargetNameLabel.getLayoutConstraint();
		valuePin_CallOperationActionTargetNameLabel_Location.setX(0);
		valuePin_CallOperationActionTargetNameLabel_Location.setY(15);
		Node valuePin_CallOperationActionTargetValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCOActAsTargetValueEditPart.VISUAL_ID));
		valuePin_CallOperationActionTargetValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CallOperationActionTargetValueLabel_Location = (Location) valuePin_CallOperationActionTargetValueLabel.getLayoutConstraint();
		valuePin_CallOperationActionTargetValueLabel_Location.setX(0);
		valuePin_CallOperationActionTargetValueLabel_Location.setY(15);
		Node valuePin_CallOperationActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCOActAsTargetAppliedStereotypeEditPart.VISUAL_ID));
		valuePin_CallOperationActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CallOperationActionTargetStereotypeLabel_Location = (Location) valuePin_CallOperationActionTargetStereotypeLabel.getLayoutConstraint();
		valuePin_CallOperationActionTargetStereotypeLabel_Location.setX(0);
		valuePin_CallOperationActionTargetStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_CallOperationActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInCallOpActAsTargetEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_CallOperationActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCOActAsTargetLabelEditPart.VISUAL_ID));
		actionInputPin_CallOperationActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CallOperationActionTargetNameLabel_Location = (Location) actionInputPin_CallOperationActionTargetNameLabel.getLayoutConstraint();
		actionInputPin_CallOperationActionTargetNameLabel_Location.setX(0);
		actionInputPin_CallOperationActionTargetNameLabel_Location.setY(15);
		Node actionInputPin_CallOperationActionTargetValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCOActAsTargetValueEditPart.VISUAL_ID));
		actionInputPin_CallOperationActionTargetValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CallOperationActionTargetValueLabel_Location = (Location) actionInputPin_CallOperationActionTargetValueLabel.getLayoutConstraint();
		actionInputPin_CallOperationActionTargetValueLabel_Location.setX(0);
		actionInputPin_CallOperationActionTargetValueLabel_Location.setY(15);
		Node actionInputPin_CallOperationActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCOActAsTargetAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_CallOperationActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CallOperationActionTargetStereotypeLabel_Location = (Location) actionInputPin_CallOperationActionTargetStereotypeLabel.getLayoutConstraint();
		actionInputPin_CallOperationActionTargetStereotypeLabel_Location.setX(0);
		actionInputPin_CallOperationActionTargetStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_CallOperationActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInCallOpActAsTargetEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_CallOperationActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInCOActAsTargetLabelEditPart.VISUAL_ID));
		inputPin_CallOperationActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_CallOperationActionTargetNameLabel_Location = (Location) inputPin_CallOperationActionTargetNameLabel.getLayoutConstraint();
		inputPin_CallOperationActionTargetNameLabel_Location.setX(0);
		inputPin_CallOperationActionTargetNameLabel_Location.setY(15);
		Node inputPin_CallOperationActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInCOActAsTargetAppliedStereotypeEditPart.VISUAL_ID));
		inputPin_CallOperationActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_CallOperationActionTargetStereotypeLabel_Location = (Location) inputPin_CallOperationActionTargetStereotypeLabel.getLayoutConstraint();
		inputPin_CallOperationActionTargetStereotypeLabel_Location.setX(0);
		inputPin_CallOperationActionTargetStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDurationConstraint_LocalPreconditionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DurationConstraintAsLocalPrecondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DurationConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DurationConstraintAsLocalPrecondNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(DurationConstraintAsLocalPrecondBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDurationConstraint_LocalPostconditionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DurationConstraintAsLocalPostcondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DurationConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DurationConstraintAsLocalPostcondNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(DurationConstraintAsLocalPostcondBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTimeConstraint_LocalPreconditionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TimeConstraintAsLocalPrecondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(TimeConstraintAsLocalPrecondNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(TimeConstraintAsLocalPrecondBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTimeConstraint_LocalPostconditionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TimeConstraintAsLocalPostcondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(TimeConstraintAsLocalPostcondNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(TimeConstraintAsLocalPostcondBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createIntervalConstraint_LocalPreconditionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "IntervalConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(IntervalConstraintAsLocalPrecondNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(IntervalConstraintAsLocalPrecondBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createIntervalConstraint_LocalPostconditionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "IntervalConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(IntervalConstraintAsLocalPostcondNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(IntervalConstraintAsLocalPostcondBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_LocalPreconditionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConstraintAsLocalPrecondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintAsLocalPrecondNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintAsLocalPrecondBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_LocalPostconditionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConstraintAsLocalPostcondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintAsLocalPostcondNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintAsLocalPostcondBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDecisionNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DecisionNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DecisionNode"); //$NON-NLS-1$
		Node decisionNode_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DecisionNodeFloatingNameEditPart.VISUAL_ID));
		decisionNode_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location decisionNode_FloatingNameLabel_Location = (Location) decisionNode_FloatingNameLabel.getLayoutConstraint();
		decisionNode_FloatingNameLabel_Location.setX(0);
		decisionNode_FloatingNameLabel_Location.setY(15);
		Node decisionNode_DecisionInputLabel = createLabel(node, UMLVisualIDRegistry.getType(DecisionInputEditPart.VISUAL_ID));
		decisionNode_DecisionInputLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location decisionNode_DecisionInputLabel_Location = (Location) decisionNode_DecisionInputLabel.getLayoutConstraint();
		decisionNode_DecisionInputLabel_Location.setX(0);
		decisionNode_DecisionInputLabel_Location.setY(15);
		Node decisionNode_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(DecisionNodeAppliedStereotypeEditPart.VISUAL_ID));
		decisionNode_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location decisionNode_StereotypeLabel_Location = (Location) decisionNode_StereotypeLabel.getLayoutConstraint();
		decisionNode_StereotypeLabel_Location.setX(0);
		decisionNode_StereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "DecisionNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createMergeNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(MergeNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "MergeNode"); //$NON-NLS-1$
		Node mergeNode_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(MergeNodeFloatingNameEditPart.VISUAL_ID));
		mergeNode_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location mergeNode_FloatingNameLabel_Location = (Location) mergeNode_FloatingNameLabel.getLayoutConstraint();
		mergeNode_FloatingNameLabel_Location.setX(0);
		mergeNode_FloatingNameLabel_Location.setY(15);
		Node mergeNode_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(MergeNodeAppliedStereotypeEditPart.VISUAL_ID));
		mergeNode_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location mergeNode_StereotypeLabel_Location = (Location) mergeNode_StereotypeLabel.getLayoutConstraint();
		mergeNode_StereotypeLabel_Location.setX(0);
		mergeNode_StereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "MergeNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createForkNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ForkNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ForkNode"); //$NON-NLS-1$
		Node forkNode_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ForkNodeFloatingNameEditPart.VISUAL_ID));
		forkNode_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location forkNode_FloatingNameLabel_Location = (Location) forkNode_FloatingNameLabel.getLayoutConstraint();
		forkNode_FloatingNameLabel_Location.setX(0);
		forkNode_FloatingNameLabel_Location.setY(15);
		Node forkNode_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ForkNodeAppliedStereotypeEditPart.VISUAL_ID));
		forkNode_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location forkNode_StereotypeLabel_Location = (Location) forkNode_StereotypeLabel.getLayoutConstraint();
		forkNode_StereotypeLabel_Location.setX(0);
		forkNode_StereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ForkNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createJoinNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(JoinNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "JoinNode"); //$NON-NLS-1$
		Node joinNode_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(JoinNodeFloatingNameEditPart.VISUAL_ID));
		joinNode_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location joinNode_FloatingNameLabel_Location = (Location) joinNode_FloatingNameLabel.getLayoutConstraint();
		joinNode_FloatingNameLabel_Location.setX(0);
		joinNode_FloatingNameLabel_Location.setY(15);
		Node joinNode_JoinSpecLabel = createLabel(node, UMLVisualIDRegistry.getType(JoinSpecEditPart.VISUAL_ID));
		joinNode_JoinSpecLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location joinNode_JoinSpecLabel_Location = (Location) joinNode_JoinSpecLabel.getLayoutConstraint();
		joinNode_JoinSpecLabel_Location.setX(0);
		joinNode_JoinSpecLabel_Location.setY(15);
		Node joinNode_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(JoinNodeAppliedStereotypeEditPart.VISUAL_ID));
		joinNode_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location joinNode_StereotypeLabel_Location = (Location) joinNode_StereotypeLabel.getLayoutConstraint();
		joinNode_StereotypeLabel_Location.setX(0);
		joinNode_StereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "JoinNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDataStoreNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DataStoreNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DataStoreNode"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DataStoreNodeLabelEditPart.VISUAL_ID));
		Node dataStoreNode_SelectionLabel = createLabel(node, UMLVisualIDRegistry.getType(DataStoreSelectionEditPart.VISUAL_ID));
		dataStoreNode_SelectionLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dataStoreNode_SelectionLabel_Location = (Location) dataStoreNode_SelectionLabel.getLayoutConstraint();
		dataStoreNode_SelectionLabel_Location.setX(0);
		dataStoreNode_SelectionLabel_Location.setY(15);
		Node dataStoreNode_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DataStoreNodeFloatingNameEditPart.VISUAL_ID));
		dataStoreNode_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dataStoreNode_FloatingNameLabel_Location = (Location) dataStoreNode_FloatingNameLabel.getLayoutConstraint();
		dataStoreNode_FloatingNameLabel_Location.setX(0);
		dataStoreNode_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSendObjectAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(SendObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "SendObjectAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(SendObjectActionNameEditPart.VISUAL_ID));
		Node sendObjectAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(SendObjectActionFloatingNameEditPart.VISUAL_ID));
		sendObjectAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location sendObjectAction_FloatingNameLabel_Location = (Location) sendObjectAction_FloatingNameLabel.getLayoutConstraint();
		sendObjectAction_FloatingNameLabel_Location.setX(0);
		sendObjectAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_SendObjectActionRequestShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInSendObjActAsReqEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_SendObjectActionRequestNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendObjActAsReqLabelEditPart.VISUAL_ID));
		valuePin_SendObjectActionRequestNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendObjectActionRequestNameLabel_Location = (Location) valuePin_SendObjectActionRequestNameLabel.getLayoutConstraint();
		valuePin_SendObjectActionRequestNameLabel_Location.setX(0);
		valuePin_SendObjectActionRequestNameLabel_Location.setY(15);
		Node valuePin_SendObjectActionRequestValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendObjActAsReqValueEditPart.VISUAL_ID));
		valuePin_SendObjectActionRequestValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendObjectActionRequestValueLabel_Location = (Location) valuePin_SendObjectActionRequestValueLabel.getLayoutConstraint();
		valuePin_SendObjectActionRequestValueLabel_Location.setX(0);
		valuePin_SendObjectActionRequestValueLabel_Location.setY(15);
		Node valuePin_SendObjectActionRequestStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendObjActAsReqAppliedStereotypeEditPart.VISUAL_ID));
		valuePin_SendObjectActionRequestStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendObjectActionRequestStereotypeLabel_Location = (Location) valuePin_SendObjectActionRequestStereotypeLabel.getLayoutConstraint();
		valuePin_SendObjectActionRequestStereotypeLabel_Location.setX(0);
		valuePin_SendObjectActionRequestStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_SendObjectActionRequestShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInSendObjActAsReqEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_SendObjectActionRequestNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendObjActAsReqLabelEditPart.VISUAL_ID));
		actionInputPin_SendObjectActionRequestNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendObjectActionRequestNameLabel_Location = (Location) actionInputPin_SendObjectActionRequestNameLabel.getLayoutConstraint();
		actionInputPin_SendObjectActionRequestNameLabel_Location.setX(0);
		actionInputPin_SendObjectActionRequestNameLabel_Location.setY(15);
		Node actionInputPin_SendObjectActionRequestValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendObjActAsReqValueEditPart.VISUAL_ID));
		actionInputPin_SendObjectActionRequestValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendObjectActionRequestValueLabel_Location = (Location) actionInputPin_SendObjectActionRequestValueLabel.getLayoutConstraint();
		actionInputPin_SendObjectActionRequestValueLabel_Location.setX(0);
		actionInputPin_SendObjectActionRequestValueLabel_Location.setY(15);
		Node actionInputPin_SendObjectActionRequestStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendObjActAsReqAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_SendObjectActionRequestStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendObjectActionRequestStereotypeLabel_Location = (Location) actionInputPin_SendObjectActionRequestStereotypeLabel.getLayoutConstraint();
		actionInputPin_SendObjectActionRequestStereotypeLabel_Location.setX(0);
		actionInputPin_SendObjectActionRequestStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_SendObjectActionRequestShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInSendObjActAsReqEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_SendObjectActionRequestNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInSendObjActAsReqLabelEditPart.VISUAL_ID));
		inputPin_SendObjectActionRequestNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_SendObjectActionRequestNameLabel_Location = (Location) inputPin_SendObjectActionRequestNameLabel.getLayoutConstraint();
		inputPin_SendObjectActionRequestNameLabel_Location.setX(0);
		inputPin_SendObjectActionRequestNameLabel_Location.setY(15);
		Node inputPin_SendObjectActionRequestStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInSendObjActAsReqAppliedStereotypeEditPart.VISUAL_ID));
		inputPin_SendObjectActionRequestStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_SendObjectActionRequestStereotypeLabel_Location = (Location) inputPin_SendObjectActionRequestStereotypeLabel.getLayoutConstraint();
		inputPin_SendObjectActionRequestStereotypeLabel_Location.setX(0);
		inputPin_SendObjectActionRequestStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_SendObjectActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInSendObjActAsTargetEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_SendObjectActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendObjActAsTargetLabelEditPart.VISUAL_ID));
		valuePin_SendObjectActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendObjectActionTargetNameLabel_Location = (Location) valuePin_SendObjectActionTargetNameLabel.getLayoutConstraint();
		valuePin_SendObjectActionTargetNameLabel_Location.setX(0);
		valuePin_SendObjectActionTargetNameLabel_Location.setY(15);
		Node valuePin_SendObjectActionTargetValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendObjActAsTargetValueEditPart.VISUAL_ID));
		valuePin_SendObjectActionTargetValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendObjectActionTargetValueLabel_Location = (Location) valuePin_SendObjectActionTargetValueLabel.getLayoutConstraint();
		valuePin_SendObjectActionTargetValueLabel_Location.setX(0);
		valuePin_SendObjectActionTargetValueLabel_Location.setY(15);
		Node valuePin_SendObjectActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendObjActAsTargetAppliedStereotypeEditPart.VISUAL_ID));
		valuePin_SendObjectActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendObjectActionTargetStereotypeLabel_Location = (Location) valuePin_SendObjectActionTargetStereotypeLabel.getLayoutConstraint();
		valuePin_SendObjectActionTargetStereotypeLabel_Location.setX(0);
		valuePin_SendObjectActionTargetStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_SendObjectActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInSendObjActAsTargetEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_SendObjectActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendObjActAsTargetLabelEditPart.VISUAL_ID));
		actionInputPin_SendObjectActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendObjectActionTargetNameLabel_Location = (Location) actionInputPin_SendObjectActionTargetNameLabel.getLayoutConstraint();
		actionInputPin_SendObjectActionTargetNameLabel_Location.setX(0);
		actionInputPin_SendObjectActionTargetNameLabel_Location.setY(15);
		Node actionInputPin_SendObjectActionTargetValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendObjActAsTargetValueEditPart.VISUAL_ID));
		actionInputPin_SendObjectActionTargetValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendObjectActionTargetValueLabel_Location = (Location) actionInputPin_SendObjectActionTargetValueLabel.getLayoutConstraint();
		actionInputPin_SendObjectActionTargetValueLabel_Location.setX(0);
		actionInputPin_SendObjectActionTargetValueLabel_Location.setY(15);
		Node actionInputPin_SendObjectActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendObjActAsTargetAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_SendObjectActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendObjectActionTargetStereotypeLabel_Location = (Location) actionInputPin_SendObjectActionTargetStereotypeLabel.getLayoutConstraint();
		actionInputPin_SendObjectActionTargetStereotypeLabel_Location.setX(0);
		actionInputPin_SendObjectActionTargetStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_SendObjectActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInSendObjActAsTargetEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_SendObjectActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInSendObjActAsTargetLabelEditPart.VISUAL_ID));
		inputPin_SendObjectActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_SendObjectActionTargetNameLabel_Location = (Location) inputPin_SendObjectActionTargetNameLabel.getLayoutConstraint();
		inputPin_SendObjectActionTargetNameLabel_Location.setX(0);
		inputPin_SendObjectActionTargetNameLabel_Location.setY(15);
		Node inputPin_SendObjectActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInSendObjActAsTargetAppliedStereotypeEditPart.VISUAL_ID));
		inputPin_SendObjectActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_SendObjectActionTargetStereotypeLabel_Location = (Location) inputPin_SendObjectActionTargetStereotypeLabel.getLayoutConstraint();
		inputPin_SendObjectActionTargetStereotypeLabel_Location.setX(0);
		inputPin_SendObjectActionTargetStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSendSignalAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(SendSignalActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "SendSignalAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(SendSignalActionNameEditPart.VISUAL_ID));
		Node sendSignalAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(SendSignalActionFloatingNameEditPart.VISUAL_ID));
		sendSignalAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location sendSignalAction_FloatingNameLabel_Location = (Location) sendSignalAction_FloatingNameLabel.getLayoutConstraint();
		sendSignalAction_FloatingNameLabel_Location.setX(0);
		sendSignalAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_SendSignalActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInSendSigActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_SendSignalActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendSigActLabelEditPart.VISUAL_ID));
		actionInputPin_SendSignalActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendSignalActionArgumentNameLabel_Location = (Location) actionInputPin_SendSignalActionArgumentNameLabel.getLayoutConstraint();
		actionInputPin_SendSignalActionArgumentNameLabel_Location.setX(0);
		actionInputPin_SendSignalActionArgumentNameLabel_Location.setY(15);
		Node actionInputPin_SendSignalActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendSigActValueEditPart.VISUAL_ID));
		actionInputPin_SendSignalActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendSignalActionArgumentValueLabel_Location = (Location) actionInputPin_SendSignalActionArgumentValueLabel.getLayoutConstraint();
		actionInputPin_SendSignalActionArgumentValueLabel_Location.setX(0);
		actionInputPin_SendSignalActionArgumentValueLabel_Location.setY(15);
		Node actionInputPin_SendSignalActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendSigActAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_SendSignalActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendSignalActionArgumentStereotypeLabel_Location = (Location) actionInputPin_SendSignalActionArgumentStereotypeLabel.getLayoutConstraint();
		actionInputPin_SendSignalActionArgumentStereotypeLabel_Location.setX(0);
		actionInputPin_SendSignalActionArgumentStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_SendSignalActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInSendSigActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_SendSignalActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendSigActLabelEditPart.VISUAL_ID));
		valuePin_SendSignalActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendSignalActionArgumentNameLabel_Location = (Location) valuePin_SendSignalActionArgumentNameLabel.getLayoutConstraint();
		valuePin_SendSignalActionArgumentNameLabel_Location.setX(0);
		valuePin_SendSignalActionArgumentNameLabel_Location.setY(15);
		Node valuePin_SendSignalActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendSigActValueEditPart.VISUAL_ID));
		valuePin_SendSignalActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendSignalActionArgumentValueLabel_Location = (Location) valuePin_SendSignalActionArgumentValueLabel.getLayoutConstraint();
		valuePin_SendSignalActionArgumentValueLabel_Location.setX(0);
		valuePin_SendSignalActionArgumentValueLabel_Location.setY(15);
		Node valuePin_SendSignalActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendSigActAppliedStereotypeEditPart.VISUAL_ID));
		valuePin_SendSignalActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendSignalActionArgumentStereotypeLabel_Location = (Location) valuePin_SendSignalActionArgumentStereotypeLabel.getLayoutConstraint();
		valuePin_SendSignalActionArgumentStereotypeLabel_Location.setX(0);
		valuePin_SendSignalActionArgumentStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_SendSignalActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInSendSigActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_SendSignalActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInSendSigActLabelEditPart.VISUAL_ID));
		inputPin_SendSignalActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_SendSignalActionArgumentNameLabel_Location = (Location) inputPin_SendSignalActionArgumentNameLabel.getLayoutConstraint();
		inputPin_SendSignalActionArgumentNameLabel_Location.setX(0);
		inputPin_SendSignalActionArgumentNameLabel_Location.setY(15);
		Node inputPin_SendSignalActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInSendSigActAppliedStereotypeEditPart.VISUAL_ID));
		inputPin_SendSignalActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_SendSignalActionArgumentStereotypeLabel_Location = (Location) inputPin_SendSignalActionArgumentStereotypeLabel.getLayoutConstraint();
		inputPin_SendSignalActionArgumentStereotypeLabel_Location.setX(0);
		inputPin_SendSignalActionArgumentStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_SendSignalActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInSendSigActAsTargetEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_SendSignalActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendSigActAsTargetLabelEditPart.VISUAL_ID));
		valuePin_SendSignalActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendSignalActionTargetNameLabel_Location = (Location) valuePin_SendSignalActionTargetNameLabel.getLayoutConstraint();
		valuePin_SendSignalActionTargetNameLabel_Location.setX(0);
		valuePin_SendSignalActionTargetNameLabel_Location.setY(15);
		Node valuePin_SendSignalActionTargetValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendSigActAsTargetValueEditPart.VISUAL_ID));
		valuePin_SendSignalActionTargetValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendSignalActionTargetValueLabel_Location = (Location) valuePin_SendSignalActionTargetValueLabel.getLayoutConstraint();
		valuePin_SendSignalActionTargetValueLabel_Location.setX(0);
		valuePin_SendSignalActionTargetValueLabel_Location.setY(15);
		Node valuePin_SendSignalActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInSendSigActAsTargetAppliedStereotypeEditPart.VISUAL_ID));
		valuePin_SendSignalActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_SendSignalActionTargetStereotypeLabel_Location = (Location) valuePin_SendSignalActionTargetStereotypeLabel.getLayoutConstraint();
		valuePin_SendSignalActionTargetStereotypeLabel_Location.setX(0);
		valuePin_SendSignalActionTargetStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_SendSignalActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInSendSigActAsTargetEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_SendSignalActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendSigActAsTargetLabelEditPart.VISUAL_ID));
		actionInputPin_SendSignalActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendSignalActionTargetNameLabel_Location = (Location) actionInputPin_SendSignalActionTargetNameLabel.getLayoutConstraint();
		actionInputPin_SendSignalActionTargetNameLabel_Location.setX(0);
		actionInputPin_SendSignalActionTargetNameLabel_Location.setY(15);
		Node actionInputPin_SendSignalActionTargetValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendSigActAsTargetValueEditPart.VISUAL_ID));
		actionInputPin_SendSignalActionTargetValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendSignalActionTargetValueLabel_Location = (Location) actionInputPin_SendSignalActionTargetValueLabel.getLayoutConstraint();
		actionInputPin_SendSignalActionTargetValueLabel_Location.setX(0);
		actionInputPin_SendSignalActionTargetValueLabel_Location.setY(15);
		Node actionInputPin_SendSignalActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInSendSigActAsTargetAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_SendSignalActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_SendSignalActionTargetStereotypeLabel_Location = (Location) actionInputPin_SendSignalActionTargetStereotypeLabel.getLayoutConstraint();
		actionInputPin_SendSignalActionTargetStereotypeLabel_Location.setX(0);
		actionInputPin_SendSignalActionTargetStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_SendSignalActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInSendSigActAsTargetEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_SendSignalActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInSendSigActAsTargetLabelEditPart.VISUAL_ID));
		inputPin_SendSignalActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_SendSignalActionTargetNameLabel_Location = (Location) inputPin_SendSignalActionTargetNameLabel.getLayoutConstraint();
		inputPin_SendSignalActionTargetNameLabel_Location.setX(0);
		inputPin_SendSignalActionTargetNameLabel_Location.setY(15);
		Node inputPin_SendSignalActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInSendSigActAsTargetAppliedStereotypeEditPart.VISUAL_ID));
		inputPin_SendSignalActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_SendSignalActionTargetStereotypeLabel_Location = (Location) inputPin_SendSignalActionTargetStereotypeLabel.getLayoutConstraint();
		inputPin_SendSignalActionTargetStereotypeLabel_Location.setX(0);
		inputPin_SendSignalActionTargetStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActivityParameterNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActivityParameterNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActivityParameterNode"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ParameterNodeNameEditPart.VISUAL_ID));
		Node activityParameterNode_StreamLabel = createLabel(node, UMLVisualIDRegistry.getType(ActivityParameterNodeStreamLabelEditPart.VISUAL_ID));
		activityParameterNode_StreamLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location activityParameterNode_StreamLabel_Location = (Location) activityParameterNode_StreamLabel.getLayoutConstraint();
		activityParameterNode_StreamLabel_Location.setX(0);
		activityParameterNode_StreamLabel_Location.setY(15);
		Node activityParameterNode_ExceptionLabel = createLabel(node, UMLVisualIDRegistry.getType(ActivityParameterNodeExceptionLabelEditPart.VISUAL_ID));
		activityParameterNode_ExceptionLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location activityParameterNode_ExceptionLabel_Location = (Location) activityParameterNode_ExceptionLabel.getLayoutConstraint();
		activityParameterNode_ExceptionLabel_Location.setX(0);
		activityParameterNode_ExceptionLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createAcceptEventAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(AcceptEventActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "AcceptEventAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(AcceptEventActionLabelEditPart.VISUAL_ID));
		Node acceptEventAction_TriggerLabel = createLabel(node, UMLVisualIDRegistry.getType(AcceptTimeEventActionLabelEditPart.VISUAL_ID));
		acceptEventAction_TriggerLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location acceptEventAction_TriggerLabel_Location = (Location) acceptEventAction_TriggerLabel.getLayoutConstraint();
		acceptEventAction_TriggerLabel_Location.setX(0);
		acceptEventAction_TriggerLabel_Location.setY(15);
		Node acceptEventAction_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(AcceptTimeEventActionAppliedStereotypeEditPart.VISUAL_ID));
		acceptEventAction_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location acceptEventAction_StereotypeLabel_Location = (Location) acceptEventAction_StereotypeLabel.getLayoutConstraint();
		acceptEventAction_StereotypeLabel_Location.setX(0);
		acceptEventAction_StereotypeLabel_Location.setY(15);
		Node acceptEventAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(AcceptEventActionFloatingNameEditPart.VISUAL_ID));
		acceptEventAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location acceptEventAction_FloatingNameLabel_Location = (Location) acceptEventAction_FloatingNameLabel.getLayoutConstraint();
		acceptEventAction_FloatingNameLabel_Location.setX(0);
		acceptEventAction_FloatingNameLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "AcceptEventAction"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_AcceptEventActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInAcceptEventActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_AcceptEventActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInAcceptEventActionLabelEditPart.VISUAL_ID));
		outputPin_AcceptEventActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_AcceptEventActionResultNameLabel_Location = (Location) outputPin_AcceptEventActionResultNameLabel.getLayoutConstraint();
		outputPin_AcceptEventActionResultNameLabel_Location.setX(0);
		outputPin_AcceptEventActionResultNameLabel_Location.setY(15);
		Node outputPin_AcceptEventActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInAcceptEventActionAppliedStereotypeEditPart.VISUAL_ID));
		outputPin_AcceptEventActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_AcceptEventActionResultStereotypeLabel_Location = (Location) outputPin_AcceptEventActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_AcceptEventActionResultStereotypeLabel_Location.setX(0);
		outputPin_AcceptEventActionResultStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValueSpecificationAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValueSpecificationActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValueSpecificationAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ValueSpecificationActionNameEditPart.VISUAL_ID));
		Node valueSpecificationAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValueSpecificationActionFloatingNameEditPart.VISUAL_ID));
		valueSpecificationAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valueSpecificationAction_FloatingNameLabel_Location = (Location) valueSpecificationAction_FloatingNameLabel.getLayoutConstraint();
		valueSpecificationAction_FloatingNameLabel_Location.setX(0);
		valueSpecificationAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_ValueSpecificationActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInValSpecActEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_ValueSpecificationActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInValSpecActLabelEditPart.VISUAL_ID));
		outputPin_ValueSpecificationActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ValueSpecificationActionResultNameLabel_Location = (Location) outputPin_ValueSpecificationActionResultNameLabel.getLayoutConstraint();
		outputPin_ValueSpecificationActionResultNameLabel_Location.setX(0);
		outputPin_ValueSpecificationActionResultNameLabel_Location.setY(15);
		Node outputPin_ValueSpecificationActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInValSpecActAppliedStereotypeEditPart.VISUAL_ID));
		outputPin_ValueSpecificationActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ValueSpecificationActionResultStereotypeLabel_Location = (Location) outputPin_ValueSpecificationActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_ValueSpecificationActionResultStereotypeLabel_Location.setX(0);
		outputPin_ValueSpecificationActionResultStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConditionalNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConditionalNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ConditionalNode"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConditionalNodeKeywordEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(ConditionalNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "ConditionalNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createExpansionRegion_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ExpansionRegionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ExpansionRegion"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ExpansionRegionKeywordEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(ExpansionRegionStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "ExpansionRegion"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createExpansionNode_InputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ExpansionNodeAsInEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ExpansionNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createExpansionNode_OutputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ExpansionNodeAsOutEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ExpansionNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createLoopNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(LoopNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "LoopNode"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(LoopNodeKeywordEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(LoopNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "LoopNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_LoopNodeVariableInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInLoopNodeAsVariableEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_LoopNodeVariableInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInLoopNodeAsVariableLabelEditPart.VISUAL_ID));
		inputPin_LoopNodeVariableInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_LoopNodeVariableInputNameLabel_Location = (Location) inputPin_LoopNodeVariableInputNameLabel.getLayoutConstraint();
		inputPin_LoopNodeVariableInputNameLabel_Location.setX(0);
		inputPin_LoopNodeVariableInputNameLabel_Location.setY(15);
		Node inputPin_LoopNodeVariableInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInStructuredActivityNodeAppliedStereotypeEditPart.VISUAL_ID));
		inputPin_LoopNodeVariableInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_LoopNodeVariableInputStereotypeLabel_Location = (Location) inputPin_LoopNodeVariableInputStereotypeLabel.getLayoutConstraint();
		inputPin_LoopNodeVariableInputStereotypeLabel_Location.setX(0);
		inputPin_LoopNodeVariableInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_LoopNodeVariableInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInLoopNodeAsVariableEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_LoopNodeVariableInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInLoopNodeAsVariableLabelEditPart.VISUAL_ID));
		valuePin_LoopNodeVariableInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_LoopNodeVariableInputNameLabel_Location = (Location) valuePin_LoopNodeVariableInputNameLabel.getLayoutConstraint();
		valuePin_LoopNodeVariableInputNameLabel_Location.setX(0);
		valuePin_LoopNodeVariableInputNameLabel_Location.setY(15);
		Node valuePin_LoopNodeVariableInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInLoopNodeAsVariableValueEditPart.VISUAL_ID));
		valuePin_LoopNodeVariableInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_LoopNodeVariableInputValueLabel_Location = (Location) valuePin_LoopNodeVariableInputValueLabel.getLayoutConstraint();
		valuePin_LoopNodeVariableInputValueLabel_Location.setX(0);
		valuePin_LoopNodeVariableInputValueLabel_Location.setY(15);
		Node valuePin_LoopNodeVariableInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInLoopNodeAsVariableAppliedStereotypeEditPart.VISUAL_ID));
		valuePin_LoopNodeVariableInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_LoopNodeVariableInputStereotypeLabel_Location = (Location) valuePin_LoopNodeVariableInputStereotypeLabel.getLayoutConstraint();
		valuePin_LoopNodeVariableInputStereotypeLabel_Location.setX(0);
		valuePin_LoopNodeVariableInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_LoopNodeVariableInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInLoopNodeAsVariableEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_LoopNodeVariableInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInLoopNodeAsVariableLabelEditPart.VISUAL_ID));
		actionInputPin_LoopNodeVariableInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_LoopNodeVariableInputNameLabel_Location = (Location) actionInputPin_LoopNodeVariableInputNameLabel.getLayoutConstraint();
		actionInputPin_LoopNodeVariableInputNameLabel_Location.setX(0);
		actionInputPin_LoopNodeVariableInputNameLabel_Location.setY(15);
		Node actionInputPin_LoopNodeVariableInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInLoopNodeAsVariableValueEditPart.VISUAL_ID));
		actionInputPin_LoopNodeVariableInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_LoopNodeVariableInputValueLabel_Location = (Location) actionInputPin_LoopNodeVariableInputValueLabel.getLayoutConstraint();
		actionInputPin_LoopNodeVariableInputValueLabel_Location.setX(0);
		actionInputPin_LoopNodeVariableInputValueLabel_Location.setY(15);
		Node actionInputPin_LoopNodeVariableInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInLoopNodeAsVariableAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_LoopNodeVariableInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_LoopNodeVariableInputStereotypeLabel_Location = (Location) actionInputPin_LoopNodeVariableInputStereotypeLabel.getLayoutConstraint();
		actionInputPin_LoopNodeVariableInputStereotypeLabel_Location.setX(0);
		actionInputPin_LoopNodeVariableInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_LoopNodeBodyOutputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInLoopNodeAsBodyOutputEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_LoopNodeBodyOutputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInLoopNodeAsBodyOutputLabelEditPart.VISUAL_ID));
		outputPin_LoopNodeBodyOutputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_LoopNodeBodyOutputNameLabel_Location = (Location) outputPin_LoopNodeBodyOutputNameLabel.getLayoutConstraint();
		outputPin_LoopNodeBodyOutputNameLabel_Location.setX(0);
		outputPin_LoopNodeBodyOutputNameLabel_Location.setY(15);
		Node outputPin_LoopNodeBodyOutputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInLoopNodeAsBodyOutputAppliedStereotypeEditPart.VISUAL_ID));
		outputPin_LoopNodeBodyOutputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_LoopNodeBodyOutputStereotypeLabel_Location = (Location) outputPin_LoopNodeBodyOutputStereotypeLabel.getLayoutConstraint();
		outputPin_LoopNodeBodyOutputStereotypeLabel_Location.setX(0);
		outputPin_LoopNodeBodyOutputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_LoopNodeVariableShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInLoopNodeAsLoopVariableEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_LoopNodeVariableNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInLoopNodeAsLoopVariableLabelEditPart.VISUAL_ID));
		outputPin_LoopNodeVariableNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_LoopNodeVariableNameLabel_Location = (Location) outputPin_LoopNodeVariableNameLabel.getLayoutConstraint();
		outputPin_LoopNodeVariableNameLabel_Location.setX(0);
		outputPin_LoopNodeVariableNameLabel_Location.setY(15);
		Node outputPin_LoopNodeVariableStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInLoopNodeAsLoopVariableAppliedStereotypeEditPart.VISUAL_ID));
		outputPin_LoopNodeVariableStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_LoopNodeVariableStereotypeLabel_Location = (Location) outputPin_LoopNodeVariableStereotypeLabel.getLayoutConstraint();
		outputPin_LoopNodeVariableStereotypeLabel_Location.setX(0);
		outputPin_LoopNodeVariableStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_LoopNodeResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInLoopNodeAsResultEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_LoopNodeResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInLoopNodeAsResultLabelEditPart.VISUAL_ID));
		outputPin_LoopNodeResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_LoopNodeResultNameLabel_Location = (Location) outputPin_LoopNodeResultNameLabel.getLayoutConstraint();
		outputPin_LoopNodeResultNameLabel_Location.setX(0);
		outputPin_LoopNodeResultNameLabel_Location.setY(15);
		Node outputPin_LoopNodeResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInLoopNodeAsResultAppliedStereotypeEditPart.VISUAL_ID));
		outputPin_LoopNodeResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_LoopNodeResultStereotypeLabel_Location = (Location) outputPin_LoopNodeResultStereotypeLabel.getLayoutConstraint();
		outputPin_LoopNodeResultStereotypeLabel_Location.setX(0);
		outputPin_LoopNodeResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSequenceNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(SequenceNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "SequenceNode"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(SequenceNodeKeywordEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(SequenceNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "SequenceNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createStructuredActivityNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(StructuredActivityNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "StructuredActivityNode"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(StructuredActivityNodeKeywordEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "StructuredActivityNode"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_StructuredActivityNodeInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_StructuredActivityNodeInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart.VISUAL_ID));
		inputPin_StructuredActivityNodeInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_StructuredActivityNodeInputNameLabel_Location = (Location) inputPin_StructuredActivityNodeInputNameLabel.getLayoutConstraint();
		inputPin_StructuredActivityNodeInputNameLabel_Location.setX(0);
		inputPin_StructuredActivityNodeInputNameLabel_Location.setY(15);
		Node inputPin_StructuredActivityNodeInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart.VISUAL_ID));
		inputPin_StructuredActivityNodeInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_StructuredActivityNodeInputStereotypeLabel_Location = (Location) inputPin_StructuredActivityNodeInputStereotypeLabel.getLayoutConstraint();
		inputPin_StructuredActivityNodeInputStereotypeLabel_Location.setX(0);
		inputPin_StructuredActivityNodeInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_StructuredActivityNodeInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_StructuredActivityNodeInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart.VISUAL_ID));
		valuePin_StructuredActivityNodeInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StructuredActivityNodeInputNameLabel_Location = (Location) valuePin_StructuredActivityNodeInputNameLabel.getLayoutConstraint();
		valuePin_StructuredActivityNodeInputNameLabel_Location.setX(0);
		valuePin_StructuredActivityNodeInputNameLabel_Location.setY(15);
		Node valuePin_StructuredActivityNodeInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStructuredActivityNodeAsStructuredNodeInputsValueEditPart.VISUAL_ID));
		valuePin_StructuredActivityNodeInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StructuredActivityNodeInputValueLabel_Location = (Location) valuePin_StructuredActivityNodeInputValueLabel.getLayoutConstraint();
		valuePin_StructuredActivityNodeInputValueLabel_Location.setX(0);
		valuePin_StructuredActivityNodeInputValueLabel_Location.setY(15);
		Node valuePin_StructuredActivityNodeInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart.VISUAL_ID));
		valuePin_StructuredActivityNodeInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StructuredActivityNodeInputStereotypeLabel_Location = (Location) valuePin_StructuredActivityNodeInputStereotypeLabel.getLayoutConstraint();
		valuePin_StructuredActivityNodeInputStereotypeLabel_Location.setX(0);
		valuePin_StructuredActivityNodeInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_StructuredActivityNodeInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_StructuredActivityNodeInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart.VISUAL_ID));
		actionInputPin_StructuredActivityNodeInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StructuredActivityNodeInputNameLabel_Location = (Location) actionInputPin_StructuredActivityNodeInputNameLabel.getLayoutConstraint();
		actionInputPin_StructuredActivityNodeInputNameLabel_Location.setX(0);
		actionInputPin_StructuredActivityNodeInputNameLabel_Location.setY(15);
		Node actionInputPin_StructuredActivityNodeInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStructuredActivityNodeAsStructuredNodeInputsValueEditPart.VISUAL_ID));
		actionInputPin_StructuredActivityNodeInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StructuredActivityNodeInputValueLabel_Location = (Location) actionInputPin_StructuredActivityNodeInputValueLabel.getLayoutConstraint();
		actionInputPin_StructuredActivityNodeInputValueLabel_Location.setX(0);
		actionInputPin_StructuredActivityNodeInputValueLabel_Location.setY(15);
		Node actionInputPin_StructuredActivityNodeInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_StructuredActivityNodeInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StructuredActivityNodeInputStereotypeLabel_Location = (Location) actionInputPin_StructuredActivityNodeInputStereotypeLabel.getLayoutConstraint();
		actionInputPin_StructuredActivityNodeInputStereotypeLabel_Location.setX(0);
		actionInputPin_StructuredActivityNodeInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_StructuredActivityNodeOutputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_StructuredActivityNodeOutputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInStructuredActivityNodeAsStructuredNodeInputsLabelEditPart.VISUAL_ID));
		outputPin_StructuredActivityNodeOutputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_StructuredActivityNodeOutputNameLabel_Location = (Location) outputPin_StructuredActivityNodeOutputNameLabel.getLayoutConstraint();
		outputPin_StructuredActivityNodeOutputNameLabel_Location.setX(0);
		outputPin_StructuredActivityNodeOutputNameLabel_Location.setY(15);
		Node outputPin_StructuredActivityNodeOutputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInStructuredActivityNodeAsStructuredNodeInputsAppliedStereotypeEditPart.VISUAL_ID));
		outputPin_StructuredActivityNodeOutputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_StructuredActivityNodeOutputStereotypeLabel_Location = (Location) outputPin_StructuredActivityNodeOutputStereotypeLabel.getLayoutConstraint();
		outputPin_StructuredActivityNodeOutputStereotypeLabel_Location.setX(0);
		outputPin_StructuredActivityNodeOutputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActivityPartition_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActivityPartitionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActivityPartition"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ActivityPartitionNameEditPart.VISUAL_ID));
		Node activityPartition_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActivityPartitionFloatingNameEditPart.VISUAL_ID));
		activityPartition_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location activityPartition_FloatingNameLabel_Location = (Location) activityPartition_FloatingNameLabel.getLayoutConstraint();
		activityPartition_FloatingNameLabel_Location.setX(0);
		activityPartition_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "ActivityPartition"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInterruptibleActivityRegion_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InterruptibleActivityRegionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InterruptibleActivityRegion"); //$NON-NLS-1$
		createCompartment(node, UMLVisualIDRegistry.getType(InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "InterruptibleActivityRegion"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComment_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CommentEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Comment"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CommentBodyLabelEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createReadSelfAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ReadSelfActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ReadSelfAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ReadSelfActionNameEditPart.VISUAL_ID));
		Node readSelfAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ReadSelfActionFloatingNameEditPart.VISUAL_ID));
		readSelfAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location readSelfAction_FloatingNameLabel_Location = (Location) readSelfAction_FloatingNameLabel.getLayoutConstraint();
		readSelfAction_FloatingNameLabel_Location.setX(0);
		readSelfAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_ReadSelfActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ReadSelfActionOutputPinEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_ReadSelfActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadSelfActionLabelEditPart.VISUAL_ID));
		outputPin_ReadSelfActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadSelfActionResultNameLabel_Location = (Location) outputPin_ReadSelfActionResultNameLabel.getLayoutConstraint();
		outputPin_ReadSelfActionResultNameLabel_Location.setX(0);
		outputPin_ReadSelfActionResultNameLabel_Location.setY(15);
		Node outputPin_ReadSelfActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadSelfActionAppliedStereotypeEditPart.VISUAL_ID));
		outputPin_ReadSelfActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadSelfActionResultStereotypeLabel_Location = (Location) outputPin_ReadSelfActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_ReadSelfActionResultStereotypeLabel_Location.setX(0);
		outputPin_ReadSelfActionResultStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActivity_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActivityEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Activity"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ActivityNameEditPartCN.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ActivityIsSingleExecutionCNEditPart.VISUAL_ID));
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityCNParametersCompartmentEditPart.VISUAL_ID), false, false, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityCNPreConditionsCompartmentEditPart.VISUAL_ID), false, false, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityCNPostConditionsCompartmentEditPart.VISUAL_ID), false, false, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityCNContentCompartmentEditPart.VISUAL_ID), false, false, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Activity"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createCreateObjectAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CreateObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CreateObjectAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CreateObjectActionNameEditPart.VISUAL_ID));
		Node createObjectAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(CreateObjectActionFloatingNameEditPart.VISUAL_ID));
		createObjectAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location createObjectAction_FloatingNameLabel_Location = (Location) createObjectAction_FloatingNameLabel.getLayoutConstraint();
		createObjectAction_FloatingNameLabel_Location.setX(0);
		createObjectAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_CreateObjectActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInCreateObjectActionAsResultEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_CreateObjectActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInCreateObjectActionAsResultLabelEditPart.VISUAL_ID));
		outputPin_CreateObjectActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_CreateObjectActionResultNameLabel_Location = (Location) outputPin_CreateObjectActionResultNameLabel.getLayoutConstraint();
		outputPin_CreateObjectActionResultNameLabel_Location.setX(0);
		outputPin_CreateObjectActionResultNameLabel_Location.setY(15);
		Node outputPin_CreateObjectActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInCreateObjectActionAsResultAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		outputPin_CreateObjectActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_CreateObjectActionResultStereotypeLabel_Location = (Location) outputPin_CreateObjectActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_CreateObjectActionResultStereotypeLabel_Location.setX(0);
		outputPin_CreateObjectActionResultStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createNamedElement_DefaultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ShapeNamedElementEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ShapeNamedElement"); //$NON-NLS-1$
		Node namedElement_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(ShapeNamedElementNameEditPart.VISUAL_ID));
		namedElement_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location namedElement_NameLabel_Location = (Location) namedElement_NameLabel.getLayoutConstraint();
		namedElement_NameLabel_Location.setX(25);
		namedElement_NameLabel_Location.setY(3);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "ShapeNamedElement"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createReadStructuralFeatureAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ReadStructuralFeatureActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ReadStructuralFeatureAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ReadStructuralFeatureActionNameEditPart.VISUAL_ID));
		Node readStructuralFeatureAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ReadStructuralFeatureActionFloatingNameEditPart.VISUAL_ID));
		readStructuralFeatureAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location readStructuralFeatureAction_FloatingNameLabel_Location = (Location) readStructuralFeatureAction_FloatingNameLabel.getLayoutConstraint();
		readStructuralFeatureAction_FloatingNameLabel_Location.setX(0);
		readStructuralFeatureAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_ReadStructuralFeatureActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_ReadStructuralFeatureActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReadStructuralFeatureAsObjectLabelEditPart.VISUAL_ID));
		inputPin_ReadStructuralFeatureActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ReadStructuralFeatureActionObjectNameLabel_Location = (Location) inputPin_ReadStructuralFeatureActionObjectNameLabel.getLayoutConstraint();
		inputPin_ReadStructuralFeatureActionObjectNameLabel_Location.setX(0);
		inputPin_ReadStructuralFeatureActionObjectNameLabel_Location.setY(15);
		Node inputPin_ReadStructuralFeatureActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReadStructuralFeatureAsObjectAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		inputPin_ReadStructuralFeatureActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Location = (Location) inputPin_ReadStructuralFeatureActionObjectStereotypeLabel.getLayoutConstraint();
		inputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Location.setX(0);
		inputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_ReadStructuralFeatureActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_ReadStructuralFeatureActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReadStructuralFeatureAsObjectNameLabelEditPart.VISUAL_ID));
		valuePin_ReadStructuralFeatureActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReadStructuralFeatureActionObjectNameLabel_Location = (Location) valuePin_ReadStructuralFeatureActionObjectNameLabel.getLayoutConstraint();
		valuePin_ReadStructuralFeatureActionObjectNameLabel_Location.setX(0);
		valuePin_ReadStructuralFeatureActionObjectNameLabel_Location.setY(15);
		Node valuePin_ReadStructuralFeatureActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReadStructuralFeatureAsObjectValueEditPart.VISUAL_ID));
		valuePin_ReadStructuralFeatureActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReadStructuralFeatureActionObjectValueLabel_Location = (Location) valuePin_ReadStructuralFeatureActionObjectValueLabel.getLayoutConstraint();
		valuePin_ReadStructuralFeatureActionObjectValueLabel_Location.setX(0);
		valuePin_ReadStructuralFeatureActionObjectValueLabel_Location.setY(15);
		Node valuePin_ReadStructuralFeatureActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReadStructuralFeatureAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_ReadStructuralFeatureActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReadStructuralFeatureActionObjectStereotypeLabel_Location = (Location) valuePin_ReadStructuralFeatureActionObjectStereotypeLabel.getLayoutConstraint();
		valuePin_ReadStructuralFeatureActionObjectStereotypeLabel_Location.setX(0);
		valuePin_ReadStructuralFeatureActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_ReadStructuralFeatureActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionPin"); //$NON-NLS-1$
		Node actionInputPin_ReadStructuralFeatureActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReadStructuralFeatureAsObjectNameLabelEditPart.VISUAL_ID));
		actionInputPin_ReadStructuralFeatureActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReadStructuralFeatureActionObjectNameLabel_Location = (Location) actionInputPin_ReadStructuralFeatureActionObjectNameLabel.getLayoutConstraint();
		actionInputPin_ReadStructuralFeatureActionObjectNameLabel_Location.setX(0);
		actionInputPin_ReadStructuralFeatureActionObjectNameLabel_Location.setY(15);
		Node actionInputPin_ReadStructuralFeatureActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReadStructuralFeatureAsObjectValueEditPart.VISUAL_ID));
		actionInputPin_ReadStructuralFeatureActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReadStructuralFeatureActionObjectValueLabel_Location = (Location) actionInputPin_ReadStructuralFeatureActionObjectValueLabel.getLayoutConstraint();
		actionInputPin_ReadStructuralFeatureActionObjectValueLabel_Location.setX(0);
		actionInputPin_ReadStructuralFeatureActionObjectValueLabel_Location.setY(15);
		Node actionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReadStructuralFeatureAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Location = (Location) actionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel.getLayoutConstraint();
		actionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Location.setX(0);
		actionInputPin_ReadStructuralFeatureActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_ReadStructuralFeatureActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInReadStructuralFeatureAsResultEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_ReadStructuralFeatureActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadStructuralFeatureAsResultLabelEditPart.VISUAL_ID));
		outputPin_ReadStructuralFeatureActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadStructuralFeatureActionResultNameLabel_Location = (Location) outputPin_ReadStructuralFeatureActionResultNameLabel.getLayoutConstraint();
		outputPin_ReadStructuralFeatureActionResultNameLabel_Location.setX(0);
		outputPin_ReadStructuralFeatureActionResultNameLabel_Location.setY(15);
		Node outputPin_ReadStructuralFeatureActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReadStructuralFeatureAsResultWrappingLabelEditPart.VISUAL_ID));
		outputPin_ReadStructuralFeatureActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadStructuralFeatureActionResultStereotypeLabel_Location = (Location) outputPin_ReadStructuralFeatureActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_ReadStructuralFeatureActionResultStereotypeLabel_Location.setX(0);
		outputPin_ReadStructuralFeatureActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createAddStructuralFeatureValueAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(AddStructuralFeatureValueActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "AddStructuralFeatureValueAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(AddStructuralFeatureValueActionNameEditPart.VISUAL_ID));
		Node addStructuralFeatureValueAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(AddStructuralFeatureValueActionFloatingNameEditPart.VISUAL_ID));
		addStructuralFeatureValueAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location addStructuralFeatureValueAction_FloatingNameLabel_Location = (Location) addStructuralFeatureValueAction_FloatingNameLabel.getLayoutConstraint();
		addStructuralFeatureValueAction_FloatingNameLabel_Location.setX(0);
		addStructuralFeatureValueAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_AddStructuralFeatureValueActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_AddStructuralFeatureValueActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInAddStructuralFeatureValueActionAsObjectLabelEditPart.VISUAL_ID));
		inputPin_AddStructuralFeatureValueActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_AddStructuralFeatureValueActionObjectNameLabel_Location = (Location) inputPin_AddStructuralFeatureValueActionObjectNameLabel.getLayoutConstraint();
		inputPin_AddStructuralFeatureValueActionObjectNameLabel_Location.setX(0);
		inputPin_AddStructuralFeatureValueActionObjectNameLabel_Location.setY(15);
		Node inputPin_AddStructuralFeatureValueActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInAddStructuralFeatureValueActionAsObjectAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		inputPin_AddStructuralFeatureValueActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Location = (Location) inputPin_AddStructuralFeatureValueActionObjectStereotypeLabel.getLayoutConstraint();
		inputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Location.setX(0);
		inputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_AddStructuralFeatureValueActionValueShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_AddStructuralFeatureValueActionValueNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInAddStructuralFeatureValueActionAsValueLabel2EditPart.VISUAL_ID));
		inputPin_AddStructuralFeatureValueActionValueNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_AddStructuralFeatureValueActionValueNameLabel_Location = (Location) inputPin_AddStructuralFeatureValueActionValueNameLabel.getLayoutConstraint();
		inputPin_AddStructuralFeatureValueActionValueNameLabel_Location.setX(0);
		inputPin_AddStructuralFeatureValueActionValueNameLabel_Location.setY(15);
		Node inputPin_AddStructuralFeatureValueActionValueStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInAddStructuralFeatureValueActionAsValueAppliedStereotypeWrappingLabel2EditPart.VISUAL_ID));
		inputPin_AddStructuralFeatureValueActionValueStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Location = (Location) inputPin_AddStructuralFeatureValueActionValueStereotypeLabel.getLayoutConstraint();
		inputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Location.setX(0);
		inputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_AddStructuralFeatureValueActionInsertAtShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_AddStructuralFeatureValueActionInsertAtNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInAddStructuralFeatureValueActionAsInserAtLabelEditPart.VISUAL_ID));
		inputPin_AddStructuralFeatureValueActionInsertAtNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Location = (Location) inputPin_AddStructuralFeatureValueActionInsertAtNameLabel.getLayoutConstraint();
		inputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Location.setX(0);
		inputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Location.setY(15);
		Node inputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInAddStructuralFeatureValueActionAsInserAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		inputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Location = (Location) inputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel.getLayoutConstraint();
		inputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Location.setX(0);
		inputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_AddStructuralFeatureValueActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_AddStructuralFeatureValueActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsObjectLabelEditPart.VISUAL_ID));
		valuePin_AddStructuralFeatureValueActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddStructuralFeatureValueActionObjectNameLabel_Location = (Location) valuePin_AddStructuralFeatureValueActionObjectNameLabel.getLayoutConstraint();
		valuePin_AddStructuralFeatureValueActionObjectNameLabel_Location.setX(0);
		valuePin_AddStructuralFeatureValueActionObjectNameLabel_Location.setY(15);
		Node valuePin_AddStructuralFeatureValueActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsObjectValueEditPart.VISUAL_ID));
		valuePin_AddStructuralFeatureValueActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddStructuralFeatureValueActionObjectValueLabel_Location = (Location) valuePin_AddStructuralFeatureValueActionObjectValueLabel.getLayoutConstraint();
		valuePin_AddStructuralFeatureValueActionObjectValueLabel_Location.setX(0);
		valuePin_AddStructuralFeatureValueActionObjectValueLabel_Location.setY(15);
		Node valuePin_AddStructuralFeatureValueActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsObjectAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		valuePin_AddStructuralFeatureValueActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddStructuralFeatureValueActionObjectStereotypeLabel_Location = (Location) valuePin_AddStructuralFeatureValueActionObjectStereotypeLabel.getLayoutConstraint();
		valuePin_AddStructuralFeatureValueActionObjectStereotypeLabel_Location.setX(0);
		valuePin_AddStructuralFeatureValueActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_AddStructuralFeatureValueActionValueShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_AddStructuralFeatureValueActionValueNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsValueLabelEditPart.VISUAL_ID));
		valuePin_AddStructuralFeatureValueActionValueNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddStructuralFeatureValueActionValueNameLabel_Location = (Location) valuePin_AddStructuralFeatureValueActionValueNameLabel.getLayoutConstraint();
		valuePin_AddStructuralFeatureValueActionValueNameLabel_Location.setX(0);
		valuePin_AddStructuralFeatureValueActionValueNameLabel_Location.setY(15);
		Node valuePin_AddStructuralFeatureValueActionValueValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsValueValueEditPart.VISUAL_ID));
		valuePin_AddStructuralFeatureValueActionValueValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddStructuralFeatureValueActionValueValueLabel_Location = (Location) valuePin_AddStructuralFeatureValueActionValueValueLabel.getLayoutConstraint();
		valuePin_AddStructuralFeatureValueActionValueValueLabel_Location.setX(0);
		valuePin_AddStructuralFeatureValueActionValueValueLabel_Location.setY(15);
		Node valuePin_AddStructuralFeatureValueActionValueStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsValueAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		valuePin_AddStructuralFeatureValueActionValueStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddStructuralFeatureValueActionValueStereotypeLabel_Location = (Location) valuePin_AddStructuralFeatureValueActionValueStereotypeLabel.getLayoutConstraint();
		valuePin_AddStructuralFeatureValueActionValueStereotypeLabel_Location.setX(0);
		valuePin_AddStructuralFeatureValueActionValueStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_AddStructuralFeatureValueActionInsertAtShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_AddStructuralFeatureValueActionInsertAtNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsInserAtLabelEditPart.VISUAL_ID));
		valuePin_AddStructuralFeatureValueActionInsertAtNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddStructuralFeatureValueActionInsertAtNameLabel_Location = (Location) valuePin_AddStructuralFeatureValueActionInsertAtNameLabel.getLayoutConstraint();
		valuePin_AddStructuralFeatureValueActionInsertAtNameLabel_Location.setX(0);
		valuePin_AddStructuralFeatureValueActionInsertAtNameLabel_Location.setY(15);
		Node valuePin_AddStructuralFeatureValueActionInsertAtValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsInserAtValueEditPart.VISUAL_ID));
		valuePin_AddStructuralFeatureValueActionInsertAtValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddStructuralFeatureValueActionInsertAtValueLabel_Location = (Location) valuePin_AddStructuralFeatureValueActionInsertAtValueLabel.getLayoutConstraint();
		valuePin_AddStructuralFeatureValueActionInsertAtValueLabel_Location.setX(0);
		valuePin_AddStructuralFeatureValueActionInsertAtValueLabel_Location.setY(15);
		Node valuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddStructuralFeatureValueActionAsInserAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		valuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Location = (Location) valuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel.getLayoutConstraint();
		valuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Location.setX(0);
		valuePin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_AddStructuralFeatureValueActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_AddStructuralFeatureValueActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsObjectLabelEditPart.VISUAL_ID));
		actionInputPin_AddStructuralFeatureValueActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddStructuralFeatureValueActionObjectNameLabel_Location = (Location) actionInputPin_AddStructuralFeatureValueActionObjectNameLabel.getLayoutConstraint();
		actionInputPin_AddStructuralFeatureValueActionObjectNameLabel_Location.setX(0);
		actionInputPin_AddStructuralFeatureValueActionObjectNameLabel_Location.setY(15);
		Node actionInputPin_AddStructuralFeatureValueActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsObjectValueEditPart.VISUAL_ID));
		actionInputPin_AddStructuralFeatureValueActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddStructuralFeatureValueActionObjectValueLabel_Location = (Location) actionInputPin_AddStructuralFeatureValueActionObjectValueLabel.getLayoutConstraint();
		actionInputPin_AddStructuralFeatureValueActionObjectValueLabel_Location.setX(0);
		actionInputPin_AddStructuralFeatureValueActionObjectValueLabel_Location.setY(15);
		Node actionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsObjectAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		actionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Location = (Location) actionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel.getLayoutConstraint();
		actionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Location.setX(0);
		actionInputPin_AddStructuralFeatureValueActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_AddStructuralFeatureValueActionValueShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_AddStructuralFeatureValueActionValueNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsValueLabelEditPart.VISUAL_ID));
		actionInputPin_AddStructuralFeatureValueActionValueNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddStructuralFeatureValueActionValueNameLabel_Location = (Location) actionInputPin_AddStructuralFeatureValueActionValueNameLabel.getLayoutConstraint();
		actionInputPin_AddStructuralFeatureValueActionValueNameLabel_Location.setX(0);
		actionInputPin_AddStructuralFeatureValueActionValueNameLabel_Location.setY(15);
		Node actionInputPin_AddStructuralFeatureValueActionValueValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsValueValueEditPart.VISUAL_ID));
		actionInputPin_AddStructuralFeatureValueActionValueValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddStructuralFeatureValueActionValueValueLabel_Location = (Location) actionInputPin_AddStructuralFeatureValueActionValueValueLabel.getLayoutConstraint();
		actionInputPin_AddStructuralFeatureValueActionValueValueLabel_Location.setX(0);
		actionInputPin_AddStructuralFeatureValueActionValueValueLabel_Location.setY(15);
		Node actionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsValueAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		actionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Location = (Location) actionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel.getLayoutConstraint();
		actionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Location.setX(0);
		actionInputPin_AddStructuralFeatureValueActionValueStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_AddStructuralFeatureValueActionInsertAtShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsInserAtLabelEditPart.VISUAL_ID));
		actionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Location = (Location) actionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel.getLayoutConstraint();
		actionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Location.setX(0);
		actionInputPin_AddStructuralFeatureValueActionInsertAtNameLabel_Location.setY(15);
		Node actionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsInserAtValueEditPart.VISUAL_ID));
		actionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel_Location = (Location) actionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel.getLayoutConstraint();
		actionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel_Location.setX(0);
		actionInputPin_AddStructuralFeatureValueActionInsertAtValueLabel_Location.setY(15);
		Node actionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddStructuralFeatureValueActionAsInserAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		actionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Location = (Location) actionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel.getLayoutConstraint();
		actionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Location.setX(0);
		actionInputPin_AddStructuralFeatureValueActionInsertAtStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_AddStructuralFeatureValueActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInAddStructuralFeatureValueActionAsResultEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_AddStructuralFeatureValueActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInAddStructuralFeatureValueActionAsResultLabel3EditPart.VISUAL_ID));
		outputPin_AddStructuralFeatureValueActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_AddStructuralFeatureValueActionResultNameLabel_Location = (Location) outputPin_AddStructuralFeatureValueActionResultNameLabel.getLayoutConstraint();
		outputPin_AddStructuralFeatureValueActionResultNameLabel_Location.setX(0);
		outputPin_AddStructuralFeatureValueActionResultNameLabel_Location.setY(15);
		Node outputPin_AddStructuralFeatureValueActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInAddStructuralFeatureValueActionAsResultAppliedStereotypeWrappingLabel3EditPart.VISUAL_ID));
		outputPin_AddStructuralFeatureValueActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_AddStructuralFeatureValueActionResultStereotypeLabel_Location = (Location) outputPin_AddStructuralFeatureValueActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_AddStructuralFeatureValueActionResultStereotypeLabel_Location.setX(0);
		outputPin_AddStructuralFeatureValueActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDestroyObjectAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DestroyObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DestroyObjectAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DestroyObjectActionNameEditPart.VISUAL_ID));
		Node destroyObjectAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DestroyObjectActionFloatingNameEditPart.VISUAL_ID));
		destroyObjectAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location destroyObjectAction_FloatingNameLabel_Location = (Location) destroyObjectAction_FloatingNameLabel.getLayoutConstraint();
		destroyObjectAction_FloatingNameLabel_Location.setX(0);
		destroyObjectAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_DestroyObjectActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInDestroyObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_DestroyObjectActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInDestroyObjectActionLabelEditPart.VISUAL_ID));
		inputPin_DestroyObjectActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_DestroyObjectActionTargetNameLabel_Location = (Location) inputPin_DestroyObjectActionTargetNameLabel.getLayoutConstraint();
		inputPin_DestroyObjectActionTargetNameLabel_Location.setX(0);
		inputPin_DestroyObjectActionTargetNameLabel_Location.setY(15);
		Node inputPin_DestroyObjectActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInDestroyObjectActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		inputPin_DestroyObjectActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_DestroyObjectActionTargetStereotypeLabel_Location = (Location) inputPin_DestroyObjectActionTargetStereotypeLabel.getLayoutConstraint();
		inputPin_DestroyObjectActionTargetStereotypeLabel_Location.setX(0);
		inputPin_DestroyObjectActionTargetStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_DestroyObjectActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInDestroyObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_DestroyObjectActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInDestroyObjectActionLabelEditPart.VISUAL_ID));
		valuePin_DestroyObjectActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_DestroyObjectActionTargetNameLabel_Location = (Location) valuePin_DestroyObjectActionTargetNameLabel.getLayoutConstraint();
		valuePin_DestroyObjectActionTargetNameLabel_Location.setX(0);
		valuePin_DestroyObjectActionTargetNameLabel_Location.setY(15);
		Node valuePin_DestroyObjectActionTargetValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInDestroyObjectActionValueEditPart.VISUAL_ID));
		valuePin_DestroyObjectActionTargetValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_DestroyObjectActionTargetValueLabel_Location = (Location) valuePin_DestroyObjectActionTargetValueLabel.getLayoutConstraint();
		valuePin_DestroyObjectActionTargetValueLabel_Location.setX(0);
		valuePin_DestroyObjectActionTargetValueLabel_Location.setY(15);
		Node valuePin_DestroyObjectActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInDestroyObjectActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		valuePin_DestroyObjectActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_DestroyObjectActionTargetStereotypeLabel_Location = (Location) valuePin_DestroyObjectActionTargetStereotypeLabel.getLayoutConstraint();
		valuePin_DestroyObjectActionTargetStereotypeLabel_Location.setX(0);
		valuePin_DestroyObjectActionTargetStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_DestroyObjectActionTargetShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInDestroyObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_DestroyObjectActionTargetNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInDestroyObjectActionLabelEditPart.VISUAL_ID));
		actionInputPin_DestroyObjectActionTargetNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_DestroyObjectActionTargetNameLabel_Location = (Location) actionInputPin_DestroyObjectActionTargetNameLabel.getLayoutConstraint();
		actionInputPin_DestroyObjectActionTargetNameLabel_Location.setX(0);
		actionInputPin_DestroyObjectActionTargetNameLabel_Location.setY(15);
		Node actionInputPin_DestroyObjectActionTargetValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInDestroyObjectActionValueEditPart.VISUAL_ID));
		actionInputPin_DestroyObjectActionTargetValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_DestroyObjectActionTargetValueLabel_Location = (Location) actionInputPin_DestroyObjectActionTargetValueLabel.getLayoutConstraint();
		actionInputPin_DestroyObjectActionTargetValueLabel_Location.setX(0);
		actionInputPin_DestroyObjectActionTargetValueLabel_Location.setY(15);
		Node actionInputPin_DestroyObjectActionTargetStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInDestroyObjectActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		actionInputPin_DestroyObjectActionTargetStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_DestroyObjectActionTargetStereotypeLabel_Location = (Location) actionInputPin_DestroyObjectActionTargetStereotypeLabel.getLayoutConstraint();
		actionInputPin_DestroyObjectActionTargetStereotypeLabel_Location.setX(0);
		actionInputPin_DestroyObjectActionTargetStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createReadVariableAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ReadVariableActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ReadVariableAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ReadVariableActionNameEditPart.VISUAL_ID));
		Node readVariableAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ReadVariableActionFloatingNameEditPart.VISUAL_ID));
		readVariableAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location readVariableAction_FloatingNameLabel_Location = (Location) readVariableAction_FloatingNameLabel.getLayoutConstraint();
		readVariableAction_FloatingNameLabel_Location.setX(0);
		readVariableAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_ReadVariableActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInReadVariableActionAsResultEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_ReadVariableActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadVariableActionAsResultLabelEditPart.VISUAL_ID));
		outputPin_ReadVariableActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadVariableActionResultNameLabel_Location = (Location) outputPin_ReadVariableActionResultNameLabel.getLayoutConstraint();
		outputPin_ReadVariableActionResultNameLabel_Location.setX(0);
		outputPin_ReadVariableActionResultNameLabel_Location.setY(15);
		Node outputPin_ReadVariableActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadVariableActionAsResultAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		outputPin_ReadVariableActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadVariableActionResultStereotypeLabel_Location = (Location) outputPin_ReadVariableActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_ReadVariableActionResultStereotypeLabel_Location.setX(0);
		outputPin_ReadVariableActionResultStereotypeLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createAddVariableValueAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(AddVariableValueActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "AddVariableValueAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(AddVariableValueActionNameEditPart.VISUAL_ID));
		Node addVariableValueAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(AddVariableValueActionFloatingNameEditPart.VISUAL_ID));
		addVariableValueAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location addVariableValueAction_FloatingNameLabel_Location = (Location) addVariableValueAction_FloatingNameLabel.getLayoutConstraint();
		addVariableValueAction_FloatingNameLabel_Location.setX(0);
		addVariableValueAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_AddVariableValueActionInsertAtShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_AddVariableValueActionInsertAtNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInAddVariableValueActionAsInsertAtLabelEditPart.VISUAL_ID));
		inputPin_AddVariableValueActionInsertAtNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_AddVariableValueActionInsertAtNameLabel_Location = (Location) inputPin_AddVariableValueActionInsertAtNameLabel.getLayoutConstraint();
		inputPin_AddVariableValueActionInsertAtNameLabel_Location.setX(0);
		inputPin_AddVariableValueActionInsertAtNameLabel_Location.setY(15);
		Node inputPin_AddVariableValueActionInsertAtStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInAddVariableValueActionAsInsertAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		inputPin_AddVariableValueActionInsertAtStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_AddVariableValueActionInsertAtStereotypeLabel_Location = (Location) inputPin_AddVariableValueActionInsertAtStereotypeLabel.getLayoutConstraint();
		inputPin_AddVariableValueActionInsertAtStereotypeLabel_Location.setX(0);
		inputPin_AddVariableValueActionInsertAtStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_AddVariableValueActionValueShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInAddVariableValueActionAsValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_AddVariableValueActionValueNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInAddVariableValueActionAsValueLabelEditPart.VISUAL_ID));
		inputPin_AddVariableValueActionValueNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_AddVariableValueActionValueNameLabel_Location = (Location) inputPin_AddVariableValueActionValueNameLabel.getLayoutConstraint();
		inputPin_AddVariableValueActionValueNameLabel_Location.setX(0);
		inputPin_AddVariableValueActionValueNameLabel_Location.setY(15);
		Node inputPin_AddVariableValueActionValueStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInAddVariableValueActionAsValueAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		inputPin_AddVariableValueActionValueStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_AddVariableValueActionValueStereotypeLabel_Location = (Location) inputPin_AddVariableValueActionValueStereotypeLabel.getLayoutConstraint();
		inputPin_AddVariableValueActionValueStereotypeLabel_Location.setX(0);
		inputPin_AddVariableValueActionValueStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_AddVariableValueActionInsertAtShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_AddVariableValueActionInsertAtNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddVariableValueActionAsInsertAtLabelEditPart.VISUAL_ID));
		valuePin_AddVariableValueActionInsertAtNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddVariableValueActionInsertAtNameLabel_Location = (Location) valuePin_AddVariableValueActionInsertAtNameLabel.getLayoutConstraint();
		valuePin_AddVariableValueActionInsertAtNameLabel_Location.setX(0);
		valuePin_AddVariableValueActionInsertAtNameLabel_Location.setY(15);
		Node valuePin_AddVariableValueActionInsertAtValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddVariableValueActionAsInsertAtValueEditPart.VISUAL_ID));
		valuePin_AddVariableValueActionInsertAtValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddVariableValueActionInsertAtValueLabel_Location = (Location) valuePin_AddVariableValueActionInsertAtValueLabel.getLayoutConstraint();
		valuePin_AddVariableValueActionInsertAtValueLabel_Location.setX(0);
		valuePin_AddVariableValueActionInsertAtValueLabel_Location.setY(15);
		Node valuePin_AddVariableValueActionInsertAtStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddVariableValueActionAsInsertAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		valuePin_AddVariableValueActionInsertAtStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddVariableValueActionInsertAtStereotypeLabel_Location = (Location) valuePin_AddVariableValueActionInsertAtStereotypeLabel.getLayoutConstraint();
		valuePin_AddVariableValueActionInsertAtStereotypeLabel_Location.setX(0);
		valuePin_AddVariableValueActionInsertAtStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_AddVariableValueActionValueShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInAddVariableValueActionAsValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_AddVariableValueActionValueNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddVariableValueActionAsValueLabelEditPart.VISUAL_ID));
		valuePin_AddVariableValueActionValueNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddVariableValueActionValueNameLabel_Location = (Location) valuePin_AddVariableValueActionValueNameLabel.getLayoutConstraint();
		valuePin_AddVariableValueActionValueNameLabel_Location.setX(0);
		valuePin_AddVariableValueActionValueNameLabel_Location.setY(15);
		Node valuePin_AddVariableValueActionValueValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddVariableValueActionAsValueValueEditPart.VISUAL_ID));
		valuePin_AddVariableValueActionValueValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddVariableValueActionValueValueLabel_Location = (Location) valuePin_AddVariableValueActionValueValueLabel.getLayoutConstraint();
		valuePin_AddVariableValueActionValueValueLabel_Location.setX(0);
		valuePin_AddVariableValueActionValueValueLabel_Location.setY(15);
		Node valuePin_AddVariableValueActionValueStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInAddVariableValueActionAsValueAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		valuePin_AddVariableValueActionValueStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_AddVariableValueActionValueStereotypeLabel_Location = (Location) valuePin_AddVariableValueActionValueStereotypeLabel.getLayoutConstraint();
		valuePin_AddVariableValueActionValueStereotypeLabel_Location.setX(0);
		valuePin_AddVariableValueActionValueStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_AddVariableValueActionInsertAtShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActoinInputPin"); //$NON-NLS-1$
		Node actionInputPin_AddVariableValueActionInsertAtNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddVariableValueActionAsInsertAtLabelEditPart.VISUAL_ID));
		actionInputPin_AddVariableValueActionInsertAtNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddVariableValueActionInsertAtNameLabel_Location = (Location) actionInputPin_AddVariableValueActionInsertAtNameLabel.getLayoutConstraint();
		actionInputPin_AddVariableValueActionInsertAtNameLabel_Location.setX(0);
		actionInputPin_AddVariableValueActionInsertAtNameLabel_Location.setY(15);
		Node actionInputPin_AddVariableValueActionInsertAtValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddVariableValueActionAsInsertAtValueEditPart.VISUAL_ID));
		actionInputPin_AddVariableValueActionInsertAtValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddVariableValueActionInsertAtValueLabel_Location = (Location) actionInputPin_AddVariableValueActionInsertAtValueLabel.getLayoutConstraint();
		actionInputPin_AddVariableValueActionInsertAtValueLabel_Location.setX(0);
		actionInputPin_AddVariableValueActionInsertAtValueLabel_Location.setY(15);
		Node actionInputPin_AddVariableValueActionInsertAtStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddVariableValueActionAsInsertAtAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		actionInputPin_AddVariableValueActionInsertAtStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddVariableValueActionInsertAtStereotypeLabel_Location = (Location) actionInputPin_AddVariableValueActionInsertAtStereotypeLabel.getLayoutConstraint();
		actionInputPin_AddVariableValueActionInsertAtStereotypeLabel_Location.setX(0);
		actionInputPin_AddVariableValueActionInsertAtStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_AddVariableValueActionValueShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInAddVariableValueActionAsValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_AddVariableValueActionValueNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddVariableValueActionAsValueLabelEditPart.VISUAL_ID));
		actionInputPin_AddVariableValueActionValueNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddVariableValueActionValueNameLabel_Location = (Location) actionInputPin_AddVariableValueActionValueNameLabel.getLayoutConstraint();
		actionInputPin_AddVariableValueActionValueNameLabel_Location.setX(0);
		actionInputPin_AddVariableValueActionValueNameLabel_Location.setY(15);
		Node actionInputPin_AddVariableValueActionValueValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddVariableValueActionAsValueValueEditPart.VISUAL_ID));
		actionInputPin_AddVariableValueActionValueValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddVariableValueActionValueValueLabel_Location = (Location) actionInputPin_AddVariableValueActionValueValueLabel.getLayoutConstraint();
		actionInputPin_AddVariableValueActionValueValueLabel_Location.setX(0);
		actionInputPin_AddVariableValueActionValueValueLabel_Location.setY(15);
		Node actionInputPin_AddVariableValueActionValueStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInAddVariableValueActionAsValueAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		actionInputPin_AddVariableValueActionValueStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_AddVariableValueActionValueStereotypeLabel_Location = (Location) actionInputPin_AddVariableValueActionValueStereotypeLabel.getLayoutConstraint();
		actionInputPin_AddVariableValueActionValueStereotypeLabel_Location.setX(0);
		actionInputPin_AddVariableValueActionValueStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createBroadcastSignalAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(BroadcastSignalActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "BroadcastSignalAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(BroadcastSignalActionNameEditPart.VISUAL_ID));
		Node broadcastSignalAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(BroadcastSignalActionFloatingNameEditPart.VISUAL_ID));
		broadcastSignalAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location broadcastSignalAction_FloatingNameLabel_Location = (Location) broadcastSignalAction_FloatingNameLabel.getLayoutConstraint();
		broadcastSignalAction_FloatingNameLabel_Location.setX(0);
		broadcastSignalAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_BroadcastSignalActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInBroadcastSignalActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_BroadcastSignalActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInBroadcastSignalActionLabelEditPart.VISUAL_ID));
		inputPin_BroadcastSignalActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_BroadcastSignalActionArgumentNameLabel_Location = (Location) inputPin_BroadcastSignalActionArgumentNameLabel.getLayoutConstraint();
		inputPin_BroadcastSignalActionArgumentNameLabel_Location.setX(0);
		inputPin_BroadcastSignalActionArgumentNameLabel_Location.setY(15);
		Node inputPin_BroadcastSignalActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInBroadcastSignalActionValueLabelEditPart.VISUAL_ID));
		inputPin_BroadcastSignalActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_BroadcastSignalActionArgumentValueLabel_Location = (Location) inputPin_BroadcastSignalActionArgumentValueLabel.getLayoutConstraint();
		inputPin_BroadcastSignalActionArgumentValueLabel_Location.setX(0);
		inputPin_BroadcastSignalActionArgumentValueLabel_Location.setY(15);
		Node inputPin_BroadcastSignalActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInBroadcastSignalActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		inputPin_BroadcastSignalActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_BroadcastSignalActionArgumentStereotypeLabel_Location = (Location) inputPin_BroadcastSignalActionArgumentStereotypeLabel.getLayoutConstraint();
		inputPin_BroadcastSignalActionArgumentStereotypeLabel_Location.setX(0);
		inputPin_BroadcastSignalActionArgumentStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_BroadcastSignalActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInBroadcastSignalActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_BroadcastSignalActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInBroadcastSignalActionLabelEditPart.VISUAL_ID));
		valuePin_BroadcastSignalActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_BroadcastSignalActionArgumentNameLabel_Location = (Location) valuePin_BroadcastSignalActionArgumentNameLabel.getLayoutConstraint();
		valuePin_BroadcastSignalActionArgumentNameLabel_Location.setX(0);
		valuePin_BroadcastSignalActionArgumentNameLabel_Location.setY(15);
		Node valuePin_BroadcastSignalActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInBroadcastSignalActionValueLabelEditPart.VISUAL_ID));
		valuePin_BroadcastSignalActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_BroadcastSignalActionArgumentValueLabel_Location = (Location) valuePin_BroadcastSignalActionArgumentValueLabel.getLayoutConstraint();
		valuePin_BroadcastSignalActionArgumentValueLabel_Location.setX(0);
		valuePin_BroadcastSignalActionArgumentValueLabel_Location.setY(15);
		Node valuePin_BroadcastSignalActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInBroadcastSignalActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		valuePin_BroadcastSignalActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_BroadcastSignalActionArgumentStereotypeLabel_Location = (Location) valuePin_BroadcastSignalActionArgumentStereotypeLabel.getLayoutConstraint();
		valuePin_BroadcastSignalActionArgumentStereotypeLabel_Location.setX(0);
		valuePin_BroadcastSignalActionArgumentStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_BroadcastSignalActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInBroadcastSignalActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_BroadcastSignalActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInBroadcastSignalActionLabelEditPart.VISUAL_ID));
		actionInputPin_BroadcastSignalActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_BroadcastSignalActionArgumentNameLabel_Location = (Location) actionInputPin_BroadcastSignalActionArgumentNameLabel.getLayoutConstraint();
		actionInputPin_BroadcastSignalActionArgumentNameLabel_Location.setX(0);
		actionInputPin_BroadcastSignalActionArgumentNameLabel_Location.setY(15);
		Node actionInputPin_BroadcastSignalActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInBroadcastSignalActionValueLabelEditPart.VISUAL_ID));
		actionInputPin_BroadcastSignalActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_BroadcastSignalActionArgumentValueLabel_Location = (Location) actionInputPin_BroadcastSignalActionArgumentValueLabel.getLayoutConstraint();
		actionInputPin_BroadcastSignalActionArgumentValueLabel_Location.setX(0);
		actionInputPin_BroadcastSignalActionArgumentValueLabel_Location.setY(15);
		Node actionInputPin_BroadcastSignalActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInBroadcastSignalActionAppliedStereotypeWrappingLabelEditPart.VISUAL_ID));
		actionInputPin_BroadcastSignalActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_BroadcastSignalActionArgumentStereotypeLabel_Location = (Location) actionInputPin_BroadcastSignalActionArgumentStereotypeLabel.getLayoutConstraint();
		actionInputPin_BroadcastSignalActionArgumentStereotypeLabel_Location.setX(0);
		actionInputPin_BroadcastSignalActionArgumentStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createCentralBufferNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CentralBufferNodeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CentralBufferNode"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CentralBufferNodeLabelEditPart.VISUAL_ID));
		Node centralBufferNode_SelectionLabel = createLabel(node, UMLVisualIDRegistry.getType(CentralBufferNodeSelectionEditPart.VISUAL_ID));
		centralBufferNode_SelectionLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location centralBufferNode_SelectionLabel_Location = (Location) centralBufferNode_SelectionLabel.getLayoutConstraint();
		centralBufferNode_SelectionLabel_Location.setX(0);
		centralBufferNode_SelectionLabel_Location.setY(15);
		Node centralBufferNode_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(CentralBufferNodeFloatingNameEditPart.VISUAL_ID));
		centralBufferNode_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location centralBufferNode_FloatingNameLabel_Location = (Location) centralBufferNode_FloatingNameLabel.getLayoutConstraint();
		centralBufferNode_FloatingNameLabel_Location.setX(0);
		centralBufferNode_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConstraintEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintNameEditPartCN.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintBodyEditPartCN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createStartObjectBehaviorAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(StartObjectBehavoiurActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "StartObjectBehaviourAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(StartObjectBehaviorActionNameEditPart.VISUAL_ID));
		Node startObjectBehaviorAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(StartObjectBehaviorActionFloatingNameEditPart.VISUAL_ID));
		startObjectBehaviorAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location startObjectBehaviorAction_FloatingNameLabel_Location = (Location) startObjectBehaviorAction_FloatingNameLabel.getLayoutConstraint();
		startObjectBehaviorAction_FloatingNameLabel_Location.setX(0);
		startObjectBehaviorAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_StartObjectBehaviorActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInStartObjectBehaviorActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_StartObjectBehaviorActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInStartObjectBehaviorActionLabelEditPart.VISUAL_ID));
		outputPin_StartObjectBehaviorActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_StartObjectBehaviorActionResultNameLabel_Location = (Location) outputPin_StartObjectBehaviorActionResultNameLabel.getLayoutConstraint();
		outputPin_StartObjectBehaviorActionResultNameLabel_Location.setX(0);
		outputPin_StartObjectBehaviorActionResultNameLabel_Location.setY(15);
		Node outputPin_StartObjectBehaviorActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInStartObjectBehaviorActionAppliedStereotypeLabelEditPart.VISUAL_ID));
		outputPin_StartObjectBehaviorActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_StartObjectBehaviorActionResultStereotypeLabel_Location = (Location) outputPin_StartObjectBehaviorActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_StartObjectBehaviorActionResultStereotypeLabel_Location.setX(0);
		outputPin_StartObjectBehaviorActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_StartObjectBehaviorActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_StartObjectBehaviorActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInStartObjectBehaviorActionAsObjectLabelEditPart.VISUAL_ID));
		inputPin_StartObjectBehaviorActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_StartObjectBehaviorActionObjectNameLabel_Location = (Location) inputPin_StartObjectBehaviorActionObjectNameLabel.getLayoutConstraint();
		inputPin_StartObjectBehaviorActionObjectNameLabel_Location.setX(0);
		inputPin_StartObjectBehaviorActionObjectNameLabel_Location.setY(15);
		Node inputPin_StartObjectBehaviorActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInStartObjectBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_StartObjectBehaviorActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_StartObjectBehaviorActionObjectStereotypeLabel_Location = (Location) inputPin_StartObjectBehaviorActionObjectStereotypeLabel.getLayoutConstraint();
		inputPin_StartObjectBehaviorActionObjectStereotypeLabel_Location.setX(0);
		inputPin_StartObjectBehaviorActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_StartObjectBehaviorActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_StartObjectBehaviorActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStartObjectBehaviorActionAsObjectLabelEditPart.VISUAL_ID));
		valuePin_StartObjectBehaviorActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StartObjectBehaviorActionObjectNameLabel_Location = (Location) valuePin_StartObjectBehaviorActionObjectNameLabel.getLayoutConstraint();
		valuePin_StartObjectBehaviorActionObjectNameLabel_Location.setX(0);
		valuePin_StartObjectBehaviorActionObjectNameLabel_Location.setY(15);
		Node valuePin_StartObjectBehaviorActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStartObjectBehaviorActionAsObjectValueEditPart.VISUAL_ID));
		valuePin_StartObjectBehaviorActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StartObjectBehaviorActionObjectValueLabel_Location = (Location) valuePin_StartObjectBehaviorActionObjectValueLabel.getLayoutConstraint();
		valuePin_StartObjectBehaviorActionObjectValueLabel_Location.setX(0);
		valuePin_StartObjectBehaviorActionObjectValueLabel_Location.setY(15);
		Node valuePin_StartObjectBehaviorActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStartObjectBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_StartObjectBehaviorActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StartObjectBehaviorActionObjectStereotypeLabel_Location = (Location) valuePin_StartObjectBehaviorActionObjectStereotypeLabel.getLayoutConstraint();
		valuePin_StartObjectBehaviorActionObjectStereotypeLabel_Location.setX(0);
		valuePin_StartObjectBehaviorActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_StartObjectBehaviorActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_StartObjectBehaviorActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStartObjectBehaviorActionAsObjectLabelEditPart.VISUAL_ID));
		actionInputPin_StartObjectBehaviorActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StartObjectBehaviorActionObjectNameLabel_Location = (Location) actionInputPin_StartObjectBehaviorActionObjectNameLabel.getLayoutConstraint();
		actionInputPin_StartObjectBehaviorActionObjectNameLabel_Location.setX(0);
		actionInputPin_StartObjectBehaviorActionObjectNameLabel_Location.setY(15);
		Node actionInputPin_StartObjectBehaviorActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStartObjectBehaviorActionAsObjectValueEditPart.VISUAL_ID));
		actionInputPin_StartObjectBehaviorActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StartObjectBehaviorActionObjectValueLabel_Location = (Location) actionInputPin_StartObjectBehaviorActionObjectValueLabel.getLayoutConstraint();
		actionInputPin_StartObjectBehaviorActionObjectValueLabel_Location.setX(0);
		actionInputPin_StartObjectBehaviorActionObjectValueLabel_Location.setY(15);
		Node actionInputPin_StartObjectBehaviorActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStartObjectBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_StartObjectBehaviorActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Location = (Location) actionInputPin_StartObjectBehaviorActionObjectStereotypeLabel.getLayoutConstraint();
		actionInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Location.setX(0);
		actionInputPin_StartObjectBehaviorActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_StartObjectBehaviorActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_StartObjectBehaviorActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInStartObjectBehaviorActionAsArgumentLabelEditPart.VISUAL_ID));
		inputPin_StartObjectBehaviorActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_StartObjectBehaviorActionArgumentNameLabel_Location = (Location) inputPin_StartObjectBehaviorActionArgumentNameLabel.getLayoutConstraint();
		inputPin_StartObjectBehaviorActionArgumentNameLabel_Location.setX(0);
		inputPin_StartObjectBehaviorActionArgumentNameLabel_Location.setY(15);
		Node inputPin_StartObjectBehaviorActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInStartObjectBehaviorActionAsArgumentAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_StartObjectBehaviorActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Location = (Location) inputPin_StartObjectBehaviorActionArgumentStereotypeLabel.getLayoutConstraint();
		inputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Location.setX(0);
		inputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_StartObjectBehaviorActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_StartObjectBehaviorActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStartObjectBehaviorActionAsArgumentLabelEditPart.VISUAL_ID));
		valuePin_StartObjectBehaviorActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StartObjectBehaviorActionArgumentNameLabel_Location = (Location) valuePin_StartObjectBehaviorActionArgumentNameLabel.getLayoutConstraint();
		valuePin_StartObjectBehaviorActionArgumentNameLabel_Location.setX(0);
		valuePin_StartObjectBehaviorActionArgumentNameLabel_Location.setY(15);
		Node valuePin_StartObjectBehaviorActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStartObjectBehaviorActionAsArgumentValueEditPart.VISUAL_ID));
		valuePin_StartObjectBehaviorActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StartObjectBehaviorActionArgumentValueLabel_Location = (Location) valuePin_StartObjectBehaviorActionArgumentValueLabel.getLayoutConstraint();
		valuePin_StartObjectBehaviorActionArgumentValueLabel_Location.setX(0);
		valuePin_StartObjectBehaviorActionArgumentValueLabel_Location.setY(15);
		Node valuePin_StartObjectBehaviorActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStartObjectBehaviorActionAsArgumentAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_StartObjectBehaviorActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StartObjectBehaviorActionArgumentStereotypeLabel_Location = (Location) valuePin_StartObjectBehaviorActionArgumentStereotypeLabel.getLayoutConstraint();
		valuePin_StartObjectBehaviorActionArgumentStereotypeLabel_Location.setX(0);
		valuePin_StartObjectBehaviorActionArgumentStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_StartObjectBehaviorActionArgumentShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_StartObjectBehaviorActionArgumentNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStartObjectBehaviorActionAsArgumentLabelEditPart.VISUAL_ID));
		actionInputPin_StartObjectBehaviorActionArgumentNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StartObjectBehaviorActionArgumentNameLabel_Location = (Location) actionInputPin_StartObjectBehaviorActionArgumentNameLabel.getLayoutConstraint();
		actionInputPin_StartObjectBehaviorActionArgumentNameLabel_Location.setX(0);
		actionInputPin_StartObjectBehaviorActionArgumentNameLabel_Location.setY(15);
		Node actionInputPin_StartObjectBehaviorActionArgumentValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStartObjectBehaviorActionAsArgumentValueEditPart.VISUAL_ID));
		actionInputPin_StartObjectBehaviorActionArgumentValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StartObjectBehaviorActionArgumentValueLabel_Location = (Location) actionInputPin_StartObjectBehaviorActionArgumentValueLabel.getLayoutConstraint();
		actionInputPin_StartObjectBehaviorActionArgumentValueLabel_Location.setX(0);
		actionInputPin_StartObjectBehaviorActionArgumentValueLabel_Location.setY(15);
		Node actionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStartObjectBehaviorActionAsArgumentAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Location = (Location) actionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel.getLayoutConstraint();
		actionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Location.setX(0);
		actionInputPin_StartObjectBehaviorActionArgumentStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTestIdentityAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TestIdentityActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TestIdentityAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(TestIdentityActionNameEditPart.VISUAL_ID));
		Node testIdentityAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(TestIdentityActionFloatingNameEditPart.VISUAL_ID));
		testIdentityAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location testIdentityAction_FloatingNameLabel_Location = (Location) testIdentityAction_FloatingNameLabel.getLayoutConstraint();
		testIdentityAction_FloatingNameLabel_Location.setX(0);
		testIdentityAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_TestIdentityActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInTestIdentityActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_TestIdentityActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInTestIdentityActionItemLabelEditPart.VISUAL_ID));
		outputPin_TestIdentityActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_TestIdentityActionResultNameLabel_Location = (Location) outputPin_TestIdentityActionResultNameLabel.getLayoutConstraint();
		outputPin_TestIdentityActionResultNameLabel_Location.setX(0);
		outputPin_TestIdentityActionResultNameLabel_Location.setY(15);
		Node outputPin_TestIdentityActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInTestIdentityActionItemAppliedStereotypeLabelEditPart.VISUAL_ID));
		outputPin_TestIdentityActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_TestIdentityActionResultStereotypeLabel_Location = (Location) outputPin_TestIdentityActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_TestIdentityActionResultStereotypeLabel_Location.setX(0);
		outputPin_TestIdentityActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_TestIdentityActionFirstShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInTestIdentityActionAsFirstEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_TestIdentityActionFirstNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInTestIdentityActionAsFirstLabelEditPart.VISUAL_ID));
		inputPin_TestIdentityActionFirstNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_TestIdentityActionFirstNameLabel_Location = (Location) inputPin_TestIdentityActionFirstNameLabel.getLayoutConstraint();
		inputPin_TestIdentityActionFirstNameLabel_Location.setX(0);
		inputPin_TestIdentityActionFirstNameLabel_Location.setY(15);
		Node inputPin_TestIdentityActionFirstStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInTestIdentityActionAsFirstAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_TestIdentityActionFirstStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_TestIdentityActionFirstStereotypeLabel_Location = (Location) inputPin_TestIdentityActionFirstStereotypeLabel.getLayoutConstraint();
		inputPin_TestIdentityActionFirstStereotypeLabel_Location.setX(0);
		inputPin_TestIdentityActionFirstStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_TestIdentityActionSecondShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInTestIdentityActionAsSecondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_TestIdentityActionSecondNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInTestIdentityActionAsSecondLabelEditPart.VISUAL_ID));
		inputPin_TestIdentityActionSecondNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_TestIdentityActionSecondNameLabel_Location = (Location) inputPin_TestIdentityActionSecondNameLabel.getLayoutConstraint();
		inputPin_TestIdentityActionSecondNameLabel_Location.setX(0);
		inputPin_TestIdentityActionSecondNameLabel_Location.setY(15);
		Node inputPin_TestIdentityActionSecondStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInTestIdentityActionAsSecondAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_TestIdentityActionSecondStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_TestIdentityActionSecondStereotypeLabel_Location = (Location) inputPin_TestIdentityActionSecondStereotypeLabel.getLayoutConstraint();
		inputPin_TestIdentityActionSecondStereotypeLabel_Location.setX(0);
		inputPin_TestIdentityActionSecondStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_TestIdentityActionFirstShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInTestIdentityActionAsFirstEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_TestIdentityActionFirstNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInTestIdentityActionAsFirstLabelEditPart.VISUAL_ID));
		valuePin_TestIdentityActionFirstNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_TestIdentityActionFirstNameLabel_Location = (Location) valuePin_TestIdentityActionFirstNameLabel.getLayoutConstraint();
		valuePin_TestIdentityActionFirstNameLabel_Location.setX(0);
		valuePin_TestIdentityActionFirstNameLabel_Location.setY(15);
		Node valuePin_TestIdentityActionFirstValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInTestIdentityActionAsFirstValueEditPart.VISUAL_ID));
		valuePin_TestIdentityActionFirstValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_TestIdentityActionFirstValueLabel_Location = (Location) valuePin_TestIdentityActionFirstValueLabel.getLayoutConstraint();
		valuePin_TestIdentityActionFirstValueLabel_Location.setX(0);
		valuePin_TestIdentityActionFirstValueLabel_Location.setY(15);
		Node valuePin_TestIdentityActionFirstStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInTestIdentityActionAsFirstAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_TestIdentityActionFirstStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_TestIdentityActionFirstStereotypeLabel_Location = (Location) valuePin_TestIdentityActionFirstStereotypeLabel.getLayoutConstraint();
		valuePin_TestIdentityActionFirstStereotypeLabel_Location.setX(0);
		valuePin_TestIdentityActionFirstStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_TestIdentityActionSecondShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInTestIdentityActionAsSecondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_TestIdentityActionSecondNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInTestIdentityActionAsSecondLabelEditPart.VISUAL_ID));
		valuePin_TestIdentityActionSecondNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_TestIdentityActionSecondNameLabel_Location = (Location) valuePin_TestIdentityActionSecondNameLabel.getLayoutConstraint();
		valuePin_TestIdentityActionSecondNameLabel_Location.setX(0);
		valuePin_TestIdentityActionSecondNameLabel_Location.setY(15);
		Node valuePin_TestIdentityActionSecondValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInTestIdentityActionAsSecondValueEditPart.VISUAL_ID));
		valuePin_TestIdentityActionSecondValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_TestIdentityActionSecondValueLabel_Location = (Location) valuePin_TestIdentityActionSecondValueLabel.getLayoutConstraint();
		valuePin_TestIdentityActionSecondValueLabel_Location.setX(0);
		valuePin_TestIdentityActionSecondValueLabel_Location.setY(15);
		Node valuePin_TestIdentityActionSecondStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInTestIdentityActionAsSecondAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_TestIdentityActionSecondStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_TestIdentityActionSecondStereotypeLabel_Location = (Location) valuePin_TestIdentityActionSecondStereotypeLabel.getLayoutConstraint();
		valuePin_TestIdentityActionSecondStereotypeLabel_Location.setX(0);
		valuePin_TestIdentityActionSecondStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_TestIdentityActionFirstShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInTestIdentityActionAsFirstEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_TestIdentityActionFirstNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInTestIdentityActionAsFirstLabelEditPart.VISUAL_ID));
		actionInputPin_TestIdentityActionFirstNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_TestIdentityActionFirstNameLabel_Location = (Location) actionInputPin_TestIdentityActionFirstNameLabel.getLayoutConstraint();
		actionInputPin_TestIdentityActionFirstNameLabel_Location.setX(0);
		actionInputPin_TestIdentityActionFirstNameLabel_Location.setY(15);
		Node actionInputPin_TestIdentityActionFirstValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInTestIdentityActionAsFirstValueEditPart.VISUAL_ID));
		actionInputPin_TestIdentityActionFirstValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_TestIdentityActionFirstValueLabel_Location = (Location) actionInputPin_TestIdentityActionFirstValueLabel.getLayoutConstraint();
		actionInputPin_TestIdentityActionFirstValueLabel_Location.setX(0);
		actionInputPin_TestIdentityActionFirstValueLabel_Location.setY(15);
		Node actionInputPin_TestIdentityActionFirstStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInTestIdentityActionAsFirstAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_TestIdentityActionFirstStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_TestIdentityActionFirstStereotypeLabel_Location = (Location) actionInputPin_TestIdentityActionFirstStereotypeLabel.getLayoutConstraint();
		actionInputPin_TestIdentityActionFirstStereotypeLabel_Location.setX(0);
		actionInputPin_TestIdentityActionFirstStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_TestIdentityActionSecondShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInTestIdentityActionAsSecondEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_TestIdentityActionSecondNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInTestIdentityActionAsSecondLabelEditPart.VISUAL_ID));
		actionInputPin_TestIdentityActionSecondNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_TestIdentityActionSecondNameLabel_Location = (Location) actionInputPin_TestIdentityActionSecondNameLabel.getLayoutConstraint();
		actionInputPin_TestIdentityActionSecondNameLabel_Location.setX(0);
		actionInputPin_TestIdentityActionSecondNameLabel_Location.setY(15);
		Node actionInputPin_TestIdentityActionSecondValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInTestIdentityActionAsSecondValueEditPart.VISUAL_ID));
		actionInputPin_TestIdentityActionSecondValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_TestIdentityActionSecondValueLabel_Location = (Location) actionInputPin_TestIdentityActionSecondValueLabel.getLayoutConstraint();
		actionInputPin_TestIdentityActionSecondValueLabel_Location.setX(0);
		actionInputPin_TestIdentityActionSecondValueLabel_Location.setY(15);
		Node actionInputPin_TestIdentityActionSecondStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInTestIdentityActionAsSecondAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_TestIdentityActionSecondStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_TestIdentityActionSecondStereotypeLabel_Location = (Location) actionInputPin_TestIdentityActionSecondStereotypeLabel.getLayoutConstraint();
		actionInputPin_TestIdentityActionSecondStereotypeLabel_Location.setX(0);
		actionInputPin_TestIdentityActionSecondStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClearStructuralFeatureAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ClearStructuralFeatureActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ClearStructuralFeatureAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ClearStructuralFeatureActionNameEditPart.VISUAL_ID));
		Node clearStructuralFeatureAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ClearStructuralFeatureActionFloatingNameEditPart.VISUAL_ID));
		clearStructuralFeatureAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location clearStructuralFeatureAction_FloatingNameLabel_Location = (Location) clearStructuralFeatureAction_FloatingNameLabel.getLayoutConstraint();
		clearStructuralFeatureAction_FloatingNameLabel_Location.setX(0);
		clearStructuralFeatureAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_ClearStructuralFeatureActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInClearStructuralFeatureActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_ClearStructuralFeatureActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInClearStructuralFeatureActionLabelEditPart.VISUAL_ID));
		outputPin_ClearStructuralFeatureActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ClearStructuralFeatureActionResultNameLabel_Location = (Location) outputPin_ClearStructuralFeatureActionResultNameLabel.getLayoutConstraint();
		outputPin_ClearStructuralFeatureActionResultNameLabel_Location.setX(0);
		outputPin_ClearStructuralFeatureActionResultNameLabel_Location.setY(15);
		Node outputPin_ClearStructuralFeatureActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInClearStructuralFeatureActionAppliedStereotypeLabelEditPart.VISUAL_ID));
		outputPin_ClearStructuralFeatureActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ClearStructuralFeatureActionResultStereotypeLabel_Location = (Location) outputPin_ClearStructuralFeatureActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_ClearStructuralFeatureActionResultStereotypeLabel_Location.setX(0);
		outputPin_ClearStructuralFeatureActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_ClearStructuralFeatureActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_ClearStructuralFeatureActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInClearStructuralFeatureActionAsObjectLabelEditPart.VISUAL_ID));
		inputPin_ClearStructuralFeatureActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ClearStructuralFeatureActionObjectNameLabel_Location = (Location) inputPin_ClearStructuralFeatureActionObjectNameLabel.getLayoutConstraint();
		inputPin_ClearStructuralFeatureActionObjectNameLabel_Location.setX(0);
		inputPin_ClearStructuralFeatureActionObjectNameLabel_Location.setY(15);
		Node inputPin_ClearStructuralFeatureActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInClearStructuralFeatureActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_ClearStructuralFeatureActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Location = (Location) inputPin_ClearStructuralFeatureActionObjectStereotypeLabel.getLayoutConstraint();
		inputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Location.setX(0);
		inputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_ClearStructuralFeatureActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_ClearStructuralFeatureActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInClearStructuralFeatureActionAsObjectLabelEditPart.VISUAL_ID));
		valuePin_ClearStructuralFeatureActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ClearStructuralFeatureActionObjectNameLabel_Location = (Location) valuePin_ClearStructuralFeatureActionObjectNameLabel.getLayoutConstraint();
		valuePin_ClearStructuralFeatureActionObjectNameLabel_Location.setX(0);
		valuePin_ClearStructuralFeatureActionObjectNameLabel_Location.setY(15);
		Node valuePin_ClearStructuralFeatureActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInClearStructuralFeatureActionAsObjectValueEditPart.VISUAL_ID));
		valuePin_ClearStructuralFeatureActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ClearStructuralFeatureActionObjectValueLabel_Location = (Location) valuePin_ClearStructuralFeatureActionObjectValueLabel.getLayoutConstraint();
		valuePin_ClearStructuralFeatureActionObjectValueLabel_Location.setX(0);
		valuePin_ClearStructuralFeatureActionObjectValueLabel_Location.setY(15);
		Node valuePin_ClearStructuralFeatureActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInClearStructuralFeatureActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_ClearStructuralFeatureActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ClearStructuralFeatureActionObjectStereotypeLabel_Location = (Location) valuePin_ClearStructuralFeatureActionObjectStereotypeLabel.getLayoutConstraint();
		valuePin_ClearStructuralFeatureActionObjectStereotypeLabel_Location.setX(0);
		valuePin_ClearStructuralFeatureActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_ClearStructuralFeatureActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_ClearStructuralFeatureActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInClearStructuralFeatureActionAsObjectLabelEditPart.VISUAL_ID));
		actionInputPin_ClearStructuralFeatureActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ClearStructuralFeatureActionObjectNameLabel_Location = (Location) actionInputPin_ClearStructuralFeatureActionObjectNameLabel.getLayoutConstraint();
		actionInputPin_ClearStructuralFeatureActionObjectNameLabel_Location.setX(0);
		actionInputPin_ClearStructuralFeatureActionObjectNameLabel_Location.setY(15);
		Node actionInputPin_ClearStructuralFeatureActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInClearStructuralFeatureActionAsObjectValueEditPart.VISUAL_ID));
		actionInputPin_ClearStructuralFeatureActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ClearStructuralFeatureActionObjectValueLabel_Location = (Location) actionInputPin_ClearStructuralFeatureActionObjectValueLabel.getLayoutConstraint();
		actionInputPin_ClearStructuralFeatureActionObjectValueLabel_Location.setX(0);
		actionInputPin_ClearStructuralFeatureActionObjectValueLabel_Location.setY(15);
		Node actionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInClearStructFeatActAsObjectAppliedStereotypeEditPart.VISUAL_ID));
		actionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Location = (Location) actionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel.getLayoutConstraint();
		actionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Location.setX(0);
		actionInputPin_ClearStructuralFeatureActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createCreateLinkAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CreateLinkActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CreateLinkAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CreateLinkActionNameEditPart.VISUAL_ID));
		Node createLinkAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(CreateLinkActionFloatingNameEditPart.VISUAL_ID));
		createLinkAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location createLinkAction_FloatingNameLabel_Location = (Location) createLinkAction_FloatingNameLabel.getLayoutConstraint();
		createLinkAction_FloatingNameLabel_Location.setX(0);
		createLinkAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_CreateLinkActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_CreateLinkActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInCreateLinkActionAsInputValueLabelEditPart.VISUAL_ID));
		inputPin_CreateLinkActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_CreateLinkActionInputNameLabel_Location = (Location) inputPin_CreateLinkActionInputNameLabel.getLayoutConstraint();
		inputPin_CreateLinkActionInputNameLabel_Location.setX(0);
		inputPin_CreateLinkActionInputNameLabel_Location.setY(15);
		Node inputPin_CreateLinkActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_CreateLinkActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_CreateLinkActionInputStereotypeLabel_Location = (Location) inputPin_CreateLinkActionInputStereotypeLabel.getLayoutConstraint();
		inputPin_CreateLinkActionInputStereotypeLabel_Location.setX(0);
		inputPin_CreateLinkActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_CreateLinkActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInCreateLinkActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_CreateLinkActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCreateLinkActionAsInputValueLabelEditPart.VISUAL_ID));
		valuePin_CreateLinkActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CreateLinkActionInputNameLabel_Location = (Location) valuePin_CreateLinkActionInputNameLabel.getLayoutConstraint();
		valuePin_CreateLinkActionInputNameLabel_Location.setX(0);
		valuePin_CreateLinkActionInputNameLabel_Location.setY(15);
		Node valuePin_CreateLinkActionInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCreateLinkActionAsInputValueValueEditPart.VISUAL_ID));
		valuePin_CreateLinkActionInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CreateLinkActionInputValueLabel_Location = (Location) valuePin_CreateLinkActionInputValueLabel.getLayoutConstraint();
		valuePin_CreateLinkActionInputValueLabel_Location.setX(0);
		valuePin_CreateLinkActionInputValueLabel_Location.setY(15);
		Node valuePin_CreateLinkActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_CreateLinkActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CreateLinkActionInputStereotypeLabel_Location = (Location) valuePin_CreateLinkActionInputStereotypeLabel.getLayoutConstraint();
		valuePin_CreateLinkActionInputStereotypeLabel_Location.setX(0);
		valuePin_CreateLinkActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_CreateLinkActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_CreateLinkActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCreateLinkActionAsInputValueLabelEditPart.VISUAL_ID));
		actionInputPin_CreateLinkActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CreateLinkActionInputNameLabel_Location = (Location) actionInputPin_CreateLinkActionInputNameLabel.getLayoutConstraint();
		actionInputPin_CreateLinkActionInputNameLabel_Location.setX(0);
		actionInputPin_CreateLinkActionInputNameLabel_Location.setY(15);
		Node actionInputPin_CreateLinkActionInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCreateLinkActionAsInputValueValueEditPart.VISUAL_ID));
		actionInputPin_CreateLinkActionInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CreateLinkActionInputValueLabel_Location = (Location) actionInputPin_CreateLinkActionInputValueLabel.getLayoutConstraint();
		actionInputPin_CreateLinkActionInputValueLabel_Location.setX(0);
		actionInputPin_CreateLinkActionInputValueLabel_Location.setY(15);
		Node actionInputPin_CreateLinkActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInCreateLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_CreateLinkActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CreateLinkActionInputStereotypeLabel_Location = (Location) actionInputPin_CreateLinkActionInputStereotypeLabel.getLayoutConstraint();
		actionInputPin_CreateLinkActionInputStereotypeLabel_Location.setX(0);
		actionInputPin_CreateLinkActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createReadLinkAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ReadLinkActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ReadLinkAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ReadLinkActionNameEditPart.VISUAL_ID));
		Node readLinkAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ReadLinkActionFloatingNameEditPart.VISUAL_ID));
		readLinkAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location readLinkAction_FloatingNameLabel_Location = (Location) readLinkAction_FloatingNameLabel.getLayoutConstraint();
		readLinkAction_FloatingNameLabel_Location.setX(0);
		readLinkAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_ReadLinkActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInReadLinkActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_ReadLinkActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadLinkActionLabelEditPart.VISUAL_ID));
		outputPin_ReadLinkActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadLinkActionResultNameLabel_Location = (Location) outputPin_ReadLinkActionResultNameLabel.getLayoutConstraint();
		outputPin_ReadLinkActionResultNameLabel_Location.setX(0);
		outputPin_ReadLinkActionResultNameLabel_Location.setY(15);
		Node outputPin_ReadLinkActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadLinkActionAppliedStereotypeLabelEditPart.VISUAL_ID));
		outputPin_ReadLinkActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadLinkActionResultStereotypeLabel_Location = (Location) outputPin_ReadLinkActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_ReadLinkActionResultStereotypeLabel_Location.setX(0);
		outputPin_ReadLinkActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_ReadLinkActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_ReadLinkActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReadLinkActionAsInputValueLabelEditPart.VISUAL_ID));
		inputPin_ReadLinkActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ReadLinkActionInputNameLabel_Location = (Location) inputPin_ReadLinkActionInputNameLabel.getLayoutConstraint();
		inputPin_ReadLinkActionInputNameLabel_Location.setX(0);
		inputPin_ReadLinkActionInputNameLabel_Location.setY(15);
		Node inputPin_ReadLinkActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReadLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_ReadLinkActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ReadLinkActionInputStereotypeLabel_Location = (Location) inputPin_ReadLinkActionInputStereotypeLabel.getLayoutConstraint();
		inputPin_ReadLinkActionInputStereotypeLabel_Location.setX(0);
		inputPin_ReadLinkActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_ReadLinkActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInReadLinkActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_ReadLinkActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReadLinkActionAsInputValueLabelEditPart.VISUAL_ID));
		valuePin_ReadLinkActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReadLinkActionInputNameLabel_Location = (Location) valuePin_ReadLinkActionInputNameLabel.getLayoutConstraint();
		valuePin_ReadLinkActionInputNameLabel_Location.setX(0);
		valuePin_ReadLinkActionInputNameLabel_Location.setY(15);
		Node valuePin_ReadLinkActionInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReadLinkActionAsInputValueValueEditPart.VISUAL_ID));
		valuePin_ReadLinkActionInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReadLinkActionInputValueLabel_Location = (Location) valuePin_ReadLinkActionInputValueLabel.getLayoutConstraint();
		valuePin_ReadLinkActionInputValueLabel_Location.setX(0);
		valuePin_ReadLinkActionInputValueLabel_Location.setY(15);
		Node valuePin_ReadLinkActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReadLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_ReadLinkActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReadLinkActionInputStereotypeLabel_Location = (Location) valuePin_ReadLinkActionInputStereotypeLabel.getLayoutConstraint();
		valuePin_ReadLinkActionInputStereotypeLabel_Location.setX(0);
		valuePin_ReadLinkActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_ReadLinkActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_ReadLinkActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInReadLinkActionAsInputValueLabelEditPart.VISUAL_ID));
		actionInputPin_ReadLinkActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReadLinkActionInputNameLabel_Location = (Location) actionInputPin_ReadLinkActionInputNameLabel.getLayoutConstraint();
		actionInputPin_ReadLinkActionInputNameLabel_Location.setX(0);
		actionInputPin_ReadLinkActionInputNameLabel_Location.setY(15);
		Node actionInputPin_ReadLinkActionInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInReadLinkActionAsInputValueValueEditPart.VISUAL_ID));
		actionInputPin_ReadLinkActionInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReadLinkActionInputValueLabel_Location = (Location) actionInputPin_ReadLinkActionInputValueLabel.getLayoutConstraint();
		actionInputPin_ReadLinkActionInputValueLabel_Location.setX(0);
		actionInputPin_ReadLinkActionInputValueLabel_Location.setY(15);
		Node actionInputPin_ReadLinkActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInReadLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_ReadLinkActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReadLinkActionInputStereotypeLabel_Location = (Location) actionInputPin_ReadLinkActionInputStereotypeLabel.getLayoutConstraint();
		actionInputPin_ReadLinkActionInputStereotypeLabel_Location.setX(0);
		actionInputPin_ReadLinkActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDestroyLinkAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DestroyLinkActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DestroyLinkAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DestroyLinkActionNameEditPart.VISUAL_ID));
		Node destroyLinkAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DestroyLinkActionFloatingNameEditPart.VISUAL_ID));
		destroyLinkAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location destroyLinkAction_FloatingNameLabel_Location = (Location) destroyLinkAction_FloatingNameLabel.getLayoutConstraint();
		destroyLinkAction_FloatingNameLabel_Location.setX(0);
		destroyLinkAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_DestroyLinkActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_DestroyLinkActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInDestroyLinkActionAsInputValueLabelEditPart.VISUAL_ID));
		inputPin_DestroyLinkActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_DestroyLinkActionInputNameLabel_Location = (Location) inputPin_DestroyLinkActionInputNameLabel.getLayoutConstraint();
		inputPin_DestroyLinkActionInputNameLabel_Location.setX(0);
		inputPin_DestroyLinkActionInputNameLabel_Location.setY(15);
		Node inputPin_DestroyLinkActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInDestroyLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_DestroyLinkActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_DestroyLinkActionInputStereotypeLabel_Location = (Location) inputPin_DestroyLinkActionInputStereotypeLabel.getLayoutConstraint();
		inputPin_DestroyLinkActionInputStereotypeLabel_Location.setX(0);
		inputPin_DestroyLinkActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_DestroyLinkActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_DestroyLinkActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInDestroyLinkActionAsInputValueLabelEditPart.VISUAL_ID));
		valuePin_DestroyLinkActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_DestroyLinkActionInputNameLabel_Location = (Location) valuePin_DestroyLinkActionInputNameLabel.getLayoutConstraint();
		valuePin_DestroyLinkActionInputNameLabel_Location.setX(0);
		valuePin_DestroyLinkActionInputNameLabel_Location.setY(15);
		Node valuePin_DestroyLinkActionInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInDestroyLinkActionAsInputValueValueEditPart.VISUAL_ID));
		valuePin_DestroyLinkActionInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_DestroyLinkActionInputValueLabel_Location = (Location) valuePin_DestroyLinkActionInputValueLabel.getLayoutConstraint();
		valuePin_DestroyLinkActionInputValueLabel_Location.setX(0);
		valuePin_DestroyLinkActionInputValueLabel_Location.setY(15);
		Node valuePin_DestroyLinkActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInDestroyLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_DestroyLinkActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_DestroyLinkActionInputStereotypeLabel_Location = (Location) valuePin_DestroyLinkActionInputStereotypeLabel.getLayoutConstraint();
		valuePin_DestroyLinkActionInputStereotypeLabel_Location.setX(0);
		valuePin_DestroyLinkActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_DestroyLinkActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionInputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_DestroyLinkActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInDestroyLinkActionAsInputValueLabelEditPart.VISUAL_ID));
		actionInputPin_DestroyLinkActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_DestroyLinkActionInputNameLabel_Location = (Location) actionInputPin_DestroyLinkActionInputNameLabel.getLayoutConstraint();
		actionInputPin_DestroyLinkActionInputNameLabel_Location.setX(0);
		actionInputPin_DestroyLinkActionInputNameLabel_Location.setY(15);
		Node actionInputPin_DestroyLinkActionInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInDestroyLinkActionAsInputValueValueEditPart.VISUAL_ID));
		actionInputPin_DestroyLinkActionInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_DestroyLinkActionInputValueLabel_Location = (Location) actionInputPin_DestroyLinkActionInputValueLabel.getLayoutConstraint();
		actionInputPin_DestroyLinkActionInputValueLabel_Location.setX(0);
		actionInputPin_DestroyLinkActionInputValueLabel_Location.setY(15);
		Node actionInputPin_DestroyLinkActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionInputPinInDestroyLinkActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_DestroyLinkActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_DestroyLinkActionInputStereotypeLabel_Location = (Location) actionInputPin_DestroyLinkActionInputStereotypeLabel.getLayoutConstraint();
		actionInputPin_DestroyLinkActionInputStereotypeLabel_Location.setX(0);
		actionInputPin_DestroyLinkActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClearAssociationAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ClearAssociationActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ClearAssotiationAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ClearAssociationActionNameEditPart.VISUAL_ID));
		Node clearAssociationAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ClearAssociationActionFloatingNameEditPart.VISUAL_ID));
		clearAssociationAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location clearAssociationAction_FloatingNameLabel_Location = (Location) clearAssociationAction_FloatingNameLabel.getLayoutConstraint();
		clearAssociationAction_FloatingNameLabel_Location.setX(0);
		clearAssociationAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_ClearAssociationActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInClearAssociationActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_ClearAssociationActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInClearAssociationActionAsObjectLabelEditPart.VISUAL_ID));
		inputPin_ClearAssociationActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ClearAssociationActionObjectNameLabel_Location = (Location) inputPin_ClearAssociationActionObjectNameLabel.getLayoutConstraint();
		inputPin_ClearAssociationActionObjectNameLabel_Location.setX(0);
		inputPin_ClearAssociationActionObjectNameLabel_Location.setY(15);
		Node inputPin_ClearAssociationActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInClearAssociationActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_ClearAssociationActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ClearAssociationActionObjectStereotypeLabel_Location = (Location) inputPin_ClearAssociationActionObjectStereotypeLabel.getLayoutConstraint();
		inputPin_ClearAssociationActionObjectStereotypeLabel_Location.setX(0);
		inputPin_ClearAssociationActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_ClearAssociationActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInClearAssociationActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_ClearAssociationActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInClearAssociationActionAsObjectLabelEditPart.VISUAL_ID));
		valuePin_ClearAssociationActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ClearAssociationActionObjectNameLabel_Location = (Location) valuePin_ClearAssociationActionObjectNameLabel.getLayoutConstraint();
		valuePin_ClearAssociationActionObjectNameLabel_Location.setX(0);
		valuePin_ClearAssociationActionObjectNameLabel_Location.setY(15);
		Node valuePin_ClearAssociationActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInClearAssociationActionAsObjectValueEditPart.VISUAL_ID));
		valuePin_ClearAssociationActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ClearAssociationActionObjectValueLabel_Location = (Location) valuePin_ClearAssociationActionObjectValueLabel.getLayoutConstraint();
		valuePin_ClearAssociationActionObjectValueLabel_Location.setX(0);
		valuePin_ClearAssociationActionObjectValueLabel_Location.setY(15);
		Node valuePin_ClearAssociationActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInClearAssociationActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_ClearAssociationActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ClearAssociationActionObjectStereotypeLabel_Location = (Location) valuePin_ClearAssociationActionObjectStereotypeLabel.getLayoutConstraint();
		valuePin_ClearAssociationActionObjectStereotypeLabel_Location.setX(0);
		valuePin_ClearAssociationActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_ClearAssociationActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInClearAssociationActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_ClearAssociationActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInClearAssociationActionAsObjectLabelEditPart.VISUAL_ID));
		actionInputPin_ClearAssociationActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ClearAssociationActionObjectNameLabel_Location = (Location) actionInputPin_ClearAssociationActionObjectNameLabel.getLayoutConstraint();
		actionInputPin_ClearAssociationActionObjectNameLabel_Location.setX(0);
		actionInputPin_ClearAssociationActionObjectNameLabel_Location.setY(15);
		Node actionInputPin_ClearAssociationActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInClearAssociationActionAsObjectValueEditPart.VISUAL_ID));
		actionInputPin_ClearAssociationActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ClearAssociationActionObjectValueLabel_Location = (Location) actionInputPin_ClearAssociationActionObjectValueLabel.getLayoutConstraint();
		actionInputPin_ClearAssociationActionObjectValueLabel_Location.setX(0);
		actionInputPin_ClearAssociationActionObjectValueLabel_Location.setY(15);
		Node actionInputPin_ClearAssociationActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInClearAssociationActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_ClearAssociationActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ClearAssociationActionObjectStereotypeLabel_Location = (Location) actionInputPin_ClearAssociationActionObjectStereotypeLabel.getLayoutConstraint();
		actionInputPin_ClearAssociationActionObjectStereotypeLabel_Location.setX(0);
		actionInputPin_ClearAssociationActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createReadExtentAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ReadExtentActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ReadExtentAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ReadExtentActionNameEditPart.VISUAL_ID));
		Node readExtentAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ReadExtentActionFloatingNameEditPart.VISUAL_ID));
		readExtentAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location readExtentAction_FloatingNameLabel_Location = (Location) readExtentAction_FloatingNameLabel.getLayoutConstraint();
		readExtentAction_FloatingNameLabel_Location.setX(0);
		readExtentAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_ReadExtentActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInReadExtentActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_ReadExtentActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadExtentActionLabelEditPart.VISUAL_ID));
		outputPin_ReadExtentActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadExtentActionResultNameLabel_Location = (Location) outputPin_ReadExtentActionResultNameLabel.getLayoutConstraint();
		outputPin_ReadExtentActionResultNameLabel_Location.setX(0);
		outputPin_ReadExtentActionResultNameLabel_Location.setY(15);
		Node outputPin_ReadExtentActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadExtentActionAppliedStereotypeLabelEditPart.VISUAL_ID));
		outputPin_ReadExtentActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadExtentActionResultStereotypeLabel_Location = (Location) outputPin_ReadExtentActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_ReadExtentActionResultStereotypeLabel_Location.setX(0);
		outputPin_ReadExtentActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createReclassifyObjectAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ReclassifyObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ReclassifyObjectAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ReclassifyObjectActionNameEditPart.VISUAL_ID));
		Node reclassifyObjectAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ReclassifyObjectActionFloatingNameEditPart.VISUAL_ID));
		reclassifyObjectAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location reclassifyObjectAction_FloatingNameLabel_Location = (Location) reclassifyObjectAction_FloatingNameLabel.getLayoutConstraint();
		reclassifyObjectAction_FloatingNameLabel_Location.setX(0);
		reclassifyObjectAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_ReclassifyObjectActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_ReclassifyObjectActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReclassifyObjectActionAsObjectLabelEditPart.VISUAL_ID));
		inputPin_ReclassifyObjectActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ReclassifyObjectActionObjectNameLabel_Location = (Location) inputPin_ReclassifyObjectActionObjectNameLabel.getLayoutConstraint();
		inputPin_ReclassifyObjectActionObjectNameLabel_Location.setX(0);
		inputPin_ReclassifyObjectActionObjectNameLabel_Location.setY(15);
		Node inputPin_ReclassifyObjectActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReclassifyObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_ReclassifyObjectActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ReclassifyObjectActionObjectStereotypeLabel_Location = (Location) inputPin_ReclassifyObjectActionObjectStereotypeLabel.getLayoutConstraint();
		inputPin_ReclassifyObjectActionObjectStereotypeLabel_Location.setX(0);
		inputPin_ReclassifyObjectActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_ReclassifyObjectActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_ReclassifyObjectActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReclassifyObjectActionAsObjectLabelEditPart.VISUAL_ID));
		valuePin_ReclassifyObjectActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReclassifyObjectActionObjectNameLabel_Location = (Location) valuePin_ReclassifyObjectActionObjectNameLabel.getLayoutConstraint();
		valuePin_ReclassifyObjectActionObjectNameLabel_Location.setX(0);
		valuePin_ReclassifyObjectActionObjectNameLabel_Location.setY(15);
		Node valuePin_ReclassifyObjectActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReclassifyObjectActionAsObjectValueEditPart.VISUAL_ID));
		valuePin_ReclassifyObjectActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReclassifyObjectActionObjectValueLabel_Location = (Location) valuePin_ReclassifyObjectActionObjectValueLabel.getLayoutConstraint();
		valuePin_ReclassifyObjectActionObjectValueLabel_Location.setX(0);
		valuePin_ReclassifyObjectActionObjectValueLabel_Location.setY(15);
		Node valuePin_ReclassifyObjectActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReclassifyObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_ReclassifyObjectActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReclassifyObjectActionObjectStereotypeLabel_Location = (Location) valuePin_ReclassifyObjectActionObjectStereotypeLabel.getLayoutConstraint();
		valuePin_ReclassifyObjectActionObjectStereotypeLabel_Location.setX(0);
		valuePin_ReclassifyObjectActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_ReclassifyObjectActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_ReclassifyObjectActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReclassifyObjectActionAsObjectLabelEditPart.VISUAL_ID));
		actionInputPin_ReclassifyObjectActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReclassifyObjectActionObjectNameLabel_Location = (Location) actionInputPin_ReclassifyObjectActionObjectNameLabel.getLayoutConstraint();
		actionInputPin_ReclassifyObjectActionObjectNameLabel_Location.setX(0);
		actionInputPin_ReclassifyObjectActionObjectNameLabel_Location.setY(15);
		Node actionInputPin_ReclassifyObjectActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReclassifyObjectActionAsObjectValueEditPart.VISUAL_ID));
		actionInputPin_ReclassifyObjectActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReclassifyObjectActionObjectValueLabel_Location = (Location) actionInputPin_ReclassifyObjectActionObjectValueLabel.getLayoutConstraint();
		actionInputPin_ReclassifyObjectActionObjectValueLabel_Location.setX(0);
		actionInputPin_ReclassifyObjectActionObjectValueLabel_Location.setY(15);
		Node actionInputPin_ReclassifyObjectActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReclassifyObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_ReclassifyObjectActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReclassifyObjectActionObjectStereotypeLabel_Location = (Location) actionInputPin_ReclassifyObjectActionObjectStereotypeLabel.getLayoutConstraint();
		actionInputPin_ReclassifyObjectActionObjectStereotypeLabel_Location.setX(0);
		actionInputPin_ReclassifyObjectActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createReadIsClassifiedObjectAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ReadIsClassifiedObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ReadIsClassifiedObjectAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ReadIsClassifiedObjectActionNameEditPart.VISUAL_ID));
		Node readIsClassifiedObjectAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ReadIsClassifiedObjectActionFloatingNameEditPart.VISUAL_ID));
		readIsClassifiedObjectAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location readIsClassifiedObjectAction_FloatingNameLabel_Location = (Location) readIsClassifiedObjectAction_FloatingNameLabel.getLayoutConstraint();
		readIsClassifiedObjectAction_FloatingNameLabel_Location.setX(0);
		readIsClassifiedObjectAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_ReadIsClassifiedObjectActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInReadIsClassifiedObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_ReadIsClassifiedObjectActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadIsClassifiedObjectActionLabelEditPart.VISUAL_ID));
		outputPin_ReadIsClassifiedObjectActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadIsClassifiedObjectActionResultNameLabel_Location = (Location) outputPin_ReadIsClassifiedObjectActionResultNameLabel.getLayoutConstraint();
		outputPin_ReadIsClassifiedObjectActionResultNameLabel_Location.setX(0);
		outputPin_ReadIsClassifiedObjectActionResultNameLabel_Location.setY(15);
		Node outputPin_ReadIsClassifiedObjectActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReadIsClassifiedObjectActionAppliedStereotypeLabelEditPart.VISUAL_ID));
		outputPin_ReadIsClassifiedObjectActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReadIsClassifiedObjectActionResultStereotypeLabel_Location = (Location) outputPin_ReadIsClassifiedObjectActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_ReadIsClassifiedObjectActionResultStereotypeLabel_Location.setX(0);
		outputPin_ReadIsClassifiedObjectActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_ReadIsClassifiedObjectActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_ReadIsClassifiedObjectActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReadIsClassifiedObjectActionAsObjectLabelEditPart.VISUAL_ID));
		inputPin_ReadIsClassifiedObjectActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ReadIsClassifiedObjectActionObjectNameLabel_Location = (Location) inputPin_ReadIsClassifiedObjectActionObjectNameLabel.getLayoutConstraint();
		inputPin_ReadIsClassifiedObjectActionObjectNameLabel_Location.setX(0);
		inputPin_ReadIsClassifiedObjectActionObjectNameLabel_Location.setY(15);
		Node inputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReadIsClassifiedObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Location = (Location) inputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel.getLayoutConstraint();
		inputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Location.setX(0);
		inputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_ReadIsClassifiedObjectActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_ReadIsClassifiedObjectActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReadIsClassifiedObjectActionAsObjectLabelEditPart.VISUAL_ID));
		valuePin_ReadIsClassifiedObjectActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReadIsClassifiedObjectActionObjectNameLabel_Location = (Location) valuePin_ReadIsClassifiedObjectActionObjectNameLabel.getLayoutConstraint();
		valuePin_ReadIsClassifiedObjectActionObjectNameLabel_Location.setX(0);
		valuePin_ReadIsClassifiedObjectActionObjectNameLabel_Location.setY(15);
		Node valuePin_ReadIsClassifiedObjectActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReadIsClassifiedObjectActionAsObjectValueEditPart.VISUAL_ID));
		valuePin_ReadIsClassifiedObjectActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReadIsClassifiedObjectActionObjectValueLabel_Location = (Location) valuePin_ReadIsClassifiedObjectActionObjectValueLabel.getLayoutConstraint();
		valuePin_ReadIsClassifiedObjectActionObjectValueLabel_Location.setX(0);
		valuePin_ReadIsClassifiedObjectActionObjectValueLabel_Location.setY(15);
		Node valuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReadIsClassifiedObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Location = (Location) valuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel.getLayoutConstraint();
		valuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Location.setX(0);
		valuePin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_ReadIsClassifiedObjectActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_ReadIsClassifiedObjectActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReadIsClassifiedObjectActionAsObjectLabelEditPart.VISUAL_ID));
		actionInputPin_ReadIsClassifiedObjectActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Location = (Location) actionInputPin_ReadIsClassifiedObjectActionObjectNameLabel.getLayoutConstraint();
		actionInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Location.setX(0);
		actionInputPin_ReadIsClassifiedObjectActionObjectNameLabel_Location.setY(15);
		Node actionInputPin_ReadIsClassifiedObjectActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReadIsClassifiedObjectActionAsObjectValueEditPart.VISUAL_ID));
		actionInputPin_ReadIsClassifiedObjectActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReadIsClassifiedObjectActionObjectValueLabel_Location = (Location) actionInputPin_ReadIsClassifiedObjectActionObjectValueLabel.getLayoutConstraint();
		actionInputPin_ReadIsClassifiedObjectActionObjectValueLabel_Location.setX(0);
		actionInputPin_ReadIsClassifiedObjectActionObjectValueLabel_Location.setY(15);
		Node actionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReadIsClassifiedObjectActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Location = (Location) actionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel.getLayoutConstraint();
		actionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Location.setX(0);
		actionInputPin_ReadIsClassifiedObjectActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createReduceAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ReduceActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ReduceAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ReduceActionNameEditPart.VISUAL_ID));
		Node reduceAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ReduceActionFloatingNameEditPart.VISUAL_ID));
		reduceAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location reduceAction_FloatingNameLabel_Location = (Location) reduceAction_FloatingNameLabel.getLayoutConstraint();
		reduceAction_FloatingNameLabel_Location.setX(0);
		reduceAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_ReduceActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInReduceActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_ReduceActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReduceActionLabelEditPart.VISUAL_ID));
		outputPin_ReduceActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReduceActionResultNameLabel_Location = (Location) outputPin_ReduceActionResultNameLabel.getLayoutConstraint();
		outputPin_ReduceActionResultNameLabel_Location.setX(0);
		outputPin_ReduceActionResultNameLabel_Location.setY(15);
		Node outputPin_ReduceActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInReduceActionAppliedStereotypeLabelEditPart.VISUAL_ID));
		outputPin_ReduceActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_ReduceActionResultStereotypeLabel_Location = (Location) outputPin_ReduceActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_ReduceActionResultStereotypeLabel_Location.setX(0);
		outputPin_ReduceActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_ReduceActionCollectionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInReduceActionAsCollectionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_ReduceActionCollectionNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReduceActionAsCollectionLabelEditPart.VISUAL_ID));
		inputPin_ReduceActionCollectionNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ReduceActionCollectionNameLabel_Location = (Location) inputPin_ReduceActionCollectionNameLabel.getLayoutConstraint();
		inputPin_ReduceActionCollectionNameLabel_Location.setX(0);
		inputPin_ReduceActionCollectionNameLabel_Location.setY(15);
		Node inputPin_ReduceActionCollectionStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInReduceActionAsCollectionAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_ReduceActionCollectionStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_ReduceActionCollectionStereotypeLabel_Location = (Location) inputPin_ReduceActionCollectionStereotypeLabel.getLayoutConstraint();
		inputPin_ReduceActionCollectionStereotypeLabel_Location.setX(0);
		inputPin_ReduceActionCollectionStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_ReduceActionCollectionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInReduceActionAsCollectionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_ReduceActionCollectionNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReduceActionAsCollectionLabelEditPart.VISUAL_ID));
		valuePin_ReduceActionCollectionNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReduceActionCollectionNameLabel_Location = (Location) valuePin_ReduceActionCollectionNameLabel.getLayoutConstraint();
		valuePin_ReduceActionCollectionNameLabel_Location.setX(0);
		valuePin_ReduceActionCollectionNameLabel_Location.setY(15);
		Node valuePin_ReduceActionCollectionValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReduceActionAsCollectionValueEditPart.VISUAL_ID));
		valuePin_ReduceActionCollectionValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReduceActionCollectionValueLabel_Location = (Location) valuePin_ReduceActionCollectionValueLabel.getLayoutConstraint();
		valuePin_ReduceActionCollectionValueLabel_Location.setX(0);
		valuePin_ReduceActionCollectionValueLabel_Location.setY(15);
		Node valuePin_ReduceActionCollectionStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInReduceActionAsCollectionAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_ReduceActionCollectionStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_ReduceActionCollectionStereotypeLabel_Location = (Location) valuePin_ReduceActionCollectionStereotypeLabel.getLayoutConstraint();
		valuePin_ReduceActionCollectionStereotypeLabel_Location.setX(0);
		valuePin_ReduceActionCollectionStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_ReduceActionCollectionShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInReduceActionAsCollectionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_ReduceActionCollectionNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReduceActionAsCollectionLabelEditPart.VISUAL_ID));
		actionInputPin_ReduceActionCollectionNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReduceActionCollectionNameLabel_Location = (Location) actionInputPin_ReduceActionCollectionNameLabel.getLayoutConstraint();
		actionInputPin_ReduceActionCollectionNameLabel_Location.setX(0);
		actionInputPin_ReduceActionCollectionNameLabel_Location.setY(15);
		Node actionInputPin_ReduceActionCollectionValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReduceActionAsCollectionValueEditPart.VISUAL_ID));
		actionInputPin_ReduceActionCollectionValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReduceActionCollectionValueLabel_Location = (Location) actionInputPin_ReduceActionCollectionValueLabel.getLayoutConstraint();
		actionInputPin_ReduceActionCollectionValueLabel_Location.setX(0);
		actionInputPin_ReduceActionCollectionValueLabel_Location.setY(15);
		Node actionInputPin_ReduceActionCollectionStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInReduceActionAsCollectionAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_ReduceActionCollectionStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_ReduceActionCollectionStereotypeLabel_Location = (Location) actionInputPin_ReduceActionCollectionStereotypeLabel.getLayoutConstraint();
		actionInputPin_ReduceActionCollectionStereotypeLabel_Location.setX(0);
		actionInputPin_ReduceActionCollectionStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createStartClassifierBehaviorAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(StartClassifierBehaviorActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "StartClassifierBehaviourAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(StartClassifierBehaviorActionNameEditPart.VISUAL_ID));
		Node startClassifierBehaviorAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(StartClassifierBehaviorActionFloatingNameEditPart.VISUAL_ID));
		startClassifierBehaviorAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location startClassifierBehaviorAction_FloatingNameLabel_Location = (Location) startClassifierBehaviorAction_FloatingNameLabel.getLayoutConstraint();
		startClassifierBehaviorAction_FloatingNameLabel_Location.setX(0);
		startClassifierBehaviorAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_StartClassifierBehaviorActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_StartClassifierBehaviorActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInStartClassifierBehaviorActionAsObjectLabelEditPart.VISUAL_ID));
		inputPin_StartClassifierBehaviorActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_StartClassifierBehaviorActionObjectNameLabel_Location = (Location) inputPin_StartClassifierBehaviorActionObjectNameLabel.getLayoutConstraint();
		inputPin_StartClassifierBehaviorActionObjectNameLabel_Location.setX(0);
		inputPin_StartClassifierBehaviorActionObjectNameLabel_Location.setY(15);
		Node inputPin_StartClassifierBehaviorActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInStartClassifierBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_StartClassifierBehaviorActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Location = (Location) inputPin_StartClassifierBehaviorActionObjectStereotypeLabel.getLayoutConstraint();
		inputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Location.setX(0);
		inputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_StartClassifierBehaviorActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_StartClassifierBehaviorActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStartClassifierBehaviorActionAsObjectLabelEditPart.VISUAL_ID));
		valuePin_StartClassifierBehaviorActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StartClassifierBehaviorActionObjectNameLabel_Location = (Location) valuePin_StartClassifierBehaviorActionObjectNameLabel.getLayoutConstraint();
		valuePin_StartClassifierBehaviorActionObjectNameLabel_Location.setX(0);
		valuePin_StartClassifierBehaviorActionObjectNameLabel_Location.setY(15);
		Node valuePin_StartClassifierBehaviorActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStartClassifierBehaviorActionAsObjectValueEditPart.VISUAL_ID));
		valuePin_StartClassifierBehaviorActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StartClassifierBehaviorActionObjectValueLabel_Location = (Location) valuePin_StartClassifierBehaviorActionObjectValueLabel.getLayoutConstraint();
		valuePin_StartClassifierBehaviorActionObjectValueLabel_Location.setX(0);
		valuePin_StartClassifierBehaviorActionObjectValueLabel_Location.setY(15);
		Node valuePin_StartClassifierBehaviorActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInStartClassifierBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_StartClassifierBehaviorActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_StartClassifierBehaviorActionObjectStereotypeLabel_Location = (Location) valuePin_StartClassifierBehaviorActionObjectStereotypeLabel.getLayoutConstraint();
		valuePin_StartClassifierBehaviorActionObjectStereotypeLabel_Location.setX(0);
		valuePin_StartClassifierBehaviorActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_StartClassifierBehaviorActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_StartClassifierBehaviorActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStartClassifierBehaviorActionAsObjectLabelEditPart.VISUAL_ID));
		actionInputPin_StartClassifierBehaviorActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StartClassifierBehaviorActionObjectNameLabel_Location = (Location) actionInputPin_StartClassifierBehaviorActionObjectNameLabel.getLayoutConstraint();
		actionInputPin_StartClassifierBehaviorActionObjectNameLabel_Location.setX(0);
		actionInputPin_StartClassifierBehaviorActionObjectNameLabel_Location.setY(15);
		Node actionInputPin_StartClassifierBehaviorActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStartClassifierBehaviorActionAsObjectValueEditPart.VISUAL_ID));
		actionInputPin_StartClassifierBehaviorActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StartClassifierBehaviorActionObjectValueLabel_Location = (Location) actionInputPin_StartClassifierBehaviorActionObjectValueLabel.getLayoutConstraint();
		actionInputPin_StartClassifierBehaviorActionObjectValueLabel_Location.setX(0);
		actionInputPin_StartClassifierBehaviorActionObjectValueLabel_Location.setY(15);
		Node actionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInStartClassifierBehaviorActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Location = (Location) actionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel.getLayoutConstraint();
		actionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Location.setX(0);
		actionInputPin_StartClassifierBehaviorActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createCreateLinkObjectAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CreateLinkObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CreateLinkObjectAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CreateLinkObjectActionNameEditPart.VISUAL_ID));
		Node createLinkObjectAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(CreateLinkObjectActionFloatingNameEditPart.VISUAL_ID));
		createLinkObjectAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location createLinkObjectAction_FloatingNameLabel_Location = (Location) createLinkObjectAction_FloatingNameLabel.getLayoutConstraint();
		createLinkObjectAction_FloatingNameLabel_Location.setX(0);
		createLinkObjectAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_CreateLinkObjectActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_CreateLinkObjectActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInCreateLinkObjectActionAsInputValueLabelEditPart.VISUAL_ID));
		inputPin_CreateLinkObjectActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_CreateLinkObjectActionInputNameLabel_Location = (Location) inputPin_CreateLinkObjectActionInputNameLabel.getLayoutConstraint();
		inputPin_CreateLinkObjectActionInputNameLabel_Location.setX(0);
		inputPin_CreateLinkObjectActionInputNameLabel_Location.setY(15);
		Node inputPin_CreateLinkObjectActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInCreateLinkObjectActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_CreateLinkObjectActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_CreateLinkObjectActionInputStereotypeLabel_Location = (Location) inputPin_CreateLinkObjectActionInputStereotypeLabel.getLayoutConstraint();
		inputPin_CreateLinkObjectActionInputStereotypeLabel_Location.setX(0);
		inputPin_CreateLinkObjectActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_CreateLinkObjectActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_CreateLinkObjectActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCreateLinkObjectActionAsInputValueLabelEditPart.VISUAL_ID));
		valuePin_CreateLinkObjectActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CreateLinkObjectActionInputNameLabel_Location = (Location) valuePin_CreateLinkObjectActionInputNameLabel.getLayoutConstraint();
		valuePin_CreateLinkObjectActionInputNameLabel_Location.setX(0);
		valuePin_CreateLinkObjectActionInputNameLabel_Location.setY(15);
		Node valuePin_CreateLinkObjectActionInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCreateLinkObjectActionAsInputValueValueEditPart.VISUAL_ID));
		valuePin_CreateLinkObjectActionInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CreateLinkObjectActionInputValueLabel_Location = (Location) valuePin_CreateLinkObjectActionInputValueLabel.getLayoutConstraint();
		valuePin_CreateLinkObjectActionInputValueLabel_Location.setX(0);
		valuePin_CreateLinkObjectActionInputValueLabel_Location.setY(15);
		Node valuePin_CreateLinkObjectActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInCreateLinkObjectActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_CreateLinkObjectActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_CreateLinkObjectActionInputStereotypeLabel_Location = (Location) valuePin_CreateLinkObjectActionInputStereotypeLabel.getLayoutConstraint();
		valuePin_CreateLinkObjectActionInputStereotypeLabel_Location.setX(0);
		valuePin_CreateLinkObjectActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_CreateLinkObjectActionInputShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_CreateLinkObjectActionInputNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInCreateLinkObjectActionAsInputValueLabelEditPart.VISUAL_ID));
		actionInputPin_CreateLinkObjectActionInputNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CreateLinkObjectActionInputNameLabel_Location = (Location) actionInputPin_CreateLinkObjectActionInputNameLabel.getLayoutConstraint();
		actionInputPin_CreateLinkObjectActionInputNameLabel_Location.setX(0);
		actionInputPin_CreateLinkObjectActionInputNameLabel_Location.setY(15);
		Node actionInputPin_CreateLinkObjectActionInputValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInCreateLinkObjectActionAsInputValueValueEditPart.VISUAL_ID));
		actionInputPin_CreateLinkObjectActionInputValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CreateLinkObjectActionInputValueLabel_Location = (Location) actionInputPin_CreateLinkObjectActionInputValueLabel.getLayoutConstraint();
		actionInputPin_CreateLinkObjectActionInputValueLabel_Location.setX(0);
		actionInputPin_CreateLinkObjectActionInputValueLabel_Location.setY(15);
		Node actionInputPin_CreateLinkObjectActionInputStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInCreateLinkObjectActionAsInputValueAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_CreateLinkObjectActionInputStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_CreateLinkObjectActionInputStereotypeLabel_Location = (Location) actionInputPin_CreateLinkObjectActionInputStereotypeLabel.getLayoutConstraint();
		actionInputPin_CreateLinkObjectActionInputStereotypeLabel_Location.setX(0);
		actionInputPin_CreateLinkObjectActionInputStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_CreateLinkObjectActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInCreateLinkObjectActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_CreateLinkObjectActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInCreateLinkObjectActionLabelEditPart.VISUAL_ID));
		outputPin_CreateLinkObjectActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_CreateLinkObjectActionResultNameLabel_Location = (Location) outputPin_CreateLinkObjectActionResultNameLabel.getLayoutConstraint();
		outputPin_CreateLinkObjectActionResultNameLabel_Location.setX(0);
		outputPin_CreateLinkObjectActionResultNameLabel_Location.setY(15);
		Node outputPin_CreateLinkObjectActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInCreateLinkObjectActionAppliedStereotypeLabelEditPart.VISUAL_ID));
		outputPin_CreateLinkObjectActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_CreateLinkObjectActionResultStereotypeLabel_Location = (Location) outputPin_CreateLinkObjectActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_CreateLinkObjectActionResultStereotypeLabel_Location.setX(0);
		outputPin_CreateLinkObjectActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createUnmarshallAction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(UnmarshallActionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "UnmarshallAction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(UnmarshallActionNameEditPart.VISUAL_ID));
		Node unmarshallAction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(UnmarshallActionFloatingNameEditPart.VISUAL_ID));
		unmarshallAction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location unmarshallAction_FloatingNameLabel_Location = (Location) unmarshallAction_FloatingNameLabel.getLayoutConstraint();
		unmarshallAction_FloatingNameLabel_Location.setX(0);
		unmarshallAction_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInputPin_UnmarshallActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InputPinInUnmarshallActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InputPin"); //$NON-NLS-1$
		Node inputPin_UnmarshallActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInUnmarshallActionAsObjectLabelEditPart.VISUAL_ID));
		inputPin_UnmarshallActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_UnmarshallActionObjectNameLabel_Location = (Location) inputPin_UnmarshallActionObjectNameLabel.getLayoutConstraint();
		inputPin_UnmarshallActionObjectNameLabel_Location.setX(0);
		inputPin_UnmarshallActionObjectNameLabel_Location.setY(15);
		Node inputPin_UnmarshallActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(InputPinInUnmarshallActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		inputPin_UnmarshallActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location inputPin_UnmarshallActionObjectStereotypeLabel_Location = (Location) inputPin_UnmarshallActionObjectStereotypeLabel.getLayoutConstraint();
		inputPin_UnmarshallActionObjectStereotypeLabel_Location.setX(0);
		inputPin_UnmarshallActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createValuePin_UnmarshallActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ValuePinInUnmarshallActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ValuePin"); //$NON-NLS-1$
		Node valuePin_UnmarshallActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInUnmarshallActionAsObjectLabelEditPart.VISUAL_ID));
		valuePin_UnmarshallActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_UnmarshallActionObjectNameLabel_Location = (Location) valuePin_UnmarshallActionObjectNameLabel.getLayoutConstraint();
		valuePin_UnmarshallActionObjectNameLabel_Location.setX(0);
		valuePin_UnmarshallActionObjectNameLabel_Location.setY(15);
		Node valuePin_UnmarshallActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInUnmarshallActionAsObjectValueEditPart.VISUAL_ID));
		valuePin_UnmarshallActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_UnmarshallActionObjectValueLabel_Location = (Location) valuePin_UnmarshallActionObjectValueLabel.getLayoutConstraint();
		valuePin_UnmarshallActionObjectValueLabel_Location.setX(0);
		valuePin_UnmarshallActionObjectValueLabel_Location.setY(15);
		Node valuePin_UnmarshallActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ValuePinInUnmarshallActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		valuePin_UnmarshallActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location valuePin_UnmarshallActionObjectStereotypeLabel_Location = (Location) valuePin_UnmarshallActionObjectStereotypeLabel.getLayoutConstraint();
		valuePin_UnmarshallActionObjectStereotypeLabel_Location.setX(0);
		valuePin_UnmarshallActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActionInputPin_UnmarshallActionObjectShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActionPinInUnmarshallActionAsObjectEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ActionInputPin"); //$NON-NLS-1$
		Node actionInputPin_UnmarshallActionObjectNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInUnmarshallActionAsObjectLabelEditPart.VISUAL_ID));
		actionInputPin_UnmarshallActionObjectNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_UnmarshallActionObjectNameLabel_Location = (Location) actionInputPin_UnmarshallActionObjectNameLabel.getLayoutConstraint();
		actionInputPin_UnmarshallActionObjectNameLabel_Location.setX(0);
		actionInputPin_UnmarshallActionObjectNameLabel_Location.setY(15);
		Node actionInputPin_UnmarshallActionObjectValueLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInUnmarshallActionAsObjectValueEditPart.VISUAL_ID));
		actionInputPin_UnmarshallActionObjectValueLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_UnmarshallActionObjectValueLabel_Location = (Location) actionInputPin_UnmarshallActionObjectValueLabel.getLayoutConstraint();
		actionInputPin_UnmarshallActionObjectValueLabel_Location.setX(0);
		actionInputPin_UnmarshallActionObjectValueLabel_Location.setY(15);
		Node actionInputPin_UnmarshallActionObjectStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ActionPinInUnmarshallActionAsObjectAppliedStereotypeLabelEditPart.VISUAL_ID));
		actionInputPin_UnmarshallActionObjectStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actionInputPin_UnmarshallActionObjectStereotypeLabel_Location = (Location) actionInputPin_UnmarshallActionObjectStereotypeLabel.getLayoutConstraint();
		actionInputPin_UnmarshallActionObjectStereotypeLabel_Location.setX(0);
		actionInputPin_UnmarshallActionObjectStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOutputPin_UnmarshallActionResultShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OutputPinInUnmarshallActionAsResultEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OutputPin"); //$NON-NLS-1$
		Node outputPin_UnmarshallActionResultNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInUnmarshallActionAsResultLabelEditPart.VISUAL_ID));
		outputPin_UnmarshallActionResultNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_UnmarshallActionResultNameLabel_Location = (Location) outputPin_UnmarshallActionResultNameLabel.getLayoutConstraint();
		outputPin_UnmarshallActionResultNameLabel_Location.setX(0);
		outputPin_UnmarshallActionResultNameLabel_Location.setY(15);
		Node outputPin_UnmarshallActionResultStereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(OutputPinInUnmarshallActionAsResultAppliedStereotypeLabelEditPart.VISUAL_ID));
		outputPin_UnmarshallActionResultStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location outputPin_UnmarshallActionResultStereotypeLabel_Location = (Location) outputPin_UnmarshallActionResultStereotypeLabel.getLayoutConstraint();
		outputPin_UnmarshallActionResultStereotypeLabel_Location.setX(0);
		outputPin_UnmarshallActionResultStereotypeLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createAction_LocalPreconditionEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ActionLocalPreconditionEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createAction_LocalPostconditionEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ActionLocalPostconditionEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createObjectFlow_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ObjectFlowEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "ObjectFlow"); //$NON-NLS-1$
		Node objectFlow_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(ObjectFlowNameEditPart.VISUAL_ID));
		objectFlow_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location objectFlow_NameLabel_Location = (Location) objectFlow_NameLabel.getLayoutConstraint();
		objectFlow_NameLabel_Location.setX(0);
		objectFlow_NameLabel_Location.setY(20);
		Node objectFlow_WeightLabel = createLabel(edge, UMLVisualIDRegistry.getType(ObjectFlowWeightEditPart.VISUAL_ID));
		objectFlow_WeightLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location objectFlow_WeightLabel_Location = (Location) objectFlow_WeightLabel.getLayoutConstraint();
		objectFlow_WeightLabel_Location.setX(0);
		objectFlow_WeightLabel_Location.setY(20);
		Node objectFlow_SelectionLabel = createLabel(edge, UMLVisualIDRegistry.getType(ObjectFlowSelectionEditPart.VISUAL_ID));
		objectFlow_SelectionLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location objectFlow_SelectionLabel_Location = (Location) objectFlow_SelectionLabel.getLayoutConstraint();
		objectFlow_SelectionLabel_Location.setX(20);
		objectFlow_SelectionLabel_Location.setY(40);
		Node objectFlow_TransformationLabel = createLabel(edge, UMLVisualIDRegistry.getType(ObjectFlowTransformationEditPart.VISUAL_ID));
		objectFlow_TransformationLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location objectFlow_TransformationLabel_Location = (Location) objectFlow_TransformationLabel.getLayoutConstraint();
		objectFlow_TransformationLabel_Location.setX(-20);
		objectFlow_TransformationLabel_Location.setY(-60);
		Node objectFlow_KeywordLabel = createLabel(edge, UMLVisualIDRegistry.getType(DecisionInputFlowEditPart.VISUAL_ID));
		objectFlow_KeywordLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location objectFlow_KeywordLabel_Location = (Location) objectFlow_KeywordLabel.getLayoutConstraint();
		objectFlow_KeywordLabel_Location.setX(0);
		objectFlow_KeywordLabel_Location.setY(-20);
		Node objectFlow_GuardLabel = createLabel(edge, UMLVisualIDRegistry.getType(ObjectFlowGuardEditPart.VISUAL_ID));
		objectFlow_GuardLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location objectFlow_GuardLabel_Location = (Location) objectFlow_GuardLabel.getLayoutConstraint();
		objectFlow_GuardLabel_Location.setX(0);
		objectFlow_GuardLabel_Location.setY(20);
		Node objectFlow_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(ObjectFlowAppliedStereotypeEditPart.VISUAL_ID));
		objectFlow_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location objectFlow_StereotypeLabel_Location = (Location) objectFlow_StereotypeLabel.getLayoutConstraint();
		objectFlow_StereotypeLabel_Location.setX(0);
		objectFlow_StereotypeLabel_Location.setY(-20);
		Node objectFlow_IconLabel = createLabel(edge, UMLVisualIDRegistry.getType(ObjectFlowInterruptibleIconEditPart.VISUAL_ID));
		objectFlow_IconLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "ObjectFlow"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createControlFlow_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ControlFlowEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "ControlFlow"); //$NON-NLS-1$
		Node controlFlow_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(ControlFlowNameEditPart.VISUAL_ID));
		controlFlow_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location controlFlow_NameLabel_Location = (Location) controlFlow_NameLabel.getLayoutConstraint();
		controlFlow_NameLabel_Location.setX(0);
		controlFlow_NameLabel_Location.setY(20);
		Node controlFlow_WeightLabel = createLabel(edge, UMLVisualIDRegistry.getType(ControlFlowWeightEditPart.VISUAL_ID));
		controlFlow_WeightLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location controlFlow_WeightLabel_Location = (Location) controlFlow_WeightLabel.getLayoutConstraint();
		controlFlow_WeightLabel_Location.setX(0);
		controlFlow_WeightLabel_Location.setY(20);
		Node controlFlow_GuardLabel = createLabel(edge, UMLVisualIDRegistry.getType(ControlFlowGuardEditPart.VISUAL_ID));
		controlFlow_GuardLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location controlFlow_GuardLabel_Location = (Location) controlFlow_GuardLabel.getLayoutConstraint();
		controlFlow_GuardLabel_Location.setX(0);
		controlFlow_GuardLabel_Location.setY(20);
		Node controlFlow_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(ControlFlowAppliedStereotypeEditPart.VISUAL_ID));
		controlFlow_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location controlFlow_StereotypeLabel_Location = (Location) controlFlow_StereotypeLabel.getLayoutConstraint();
		controlFlow_StereotypeLabel_Location.setX(0);
		controlFlow_StereotypeLabel_Location.setY(-20);
		Node controlFlow_IconLabel = createLabel(edge, UMLVisualIDRegistry.getType(ControlFlowInterruptibleIconEditPart.VISUAL_ID));
		controlFlow_IconLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "ControlFlow"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createExceptionHandler_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ExceptionHandlerEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "ExceptionHandler"); //$NON-NLS-1$
		Node exceptionHandler_TypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(ExceptionHandlerTypeEditPart.VISUAL_ID));
		exceptionHandler_TypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location exceptionHandler_TypeLabel_Location = (Location) exceptionHandler_TypeLabel.getLayoutConstraint();
		exceptionHandler_TypeLabel_Location.setX(0);
		exceptionHandler_TypeLabel_Location.setY(40);
		Node exceptionHandler_IconLabel = createLabel(edge, UMLVisualIDRegistry.getType(ExceptionHandlerIconEditPart.VISUAL_ID));
		exceptionHandler_IconLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
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
		edge.setType(UMLVisualIDRegistry.getType(CommentLinkEditPart.VISUAL_ID));
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
	protected void stampShortcut(View containerView, Node target) {
		if (!ActivityDiagramEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(containerView))) {
			EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
			shortcutAnnotation.getDetails().put("modelID", ActivityDiagramEditPart.MODEL_ID); //$NON-NLS-1$
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
