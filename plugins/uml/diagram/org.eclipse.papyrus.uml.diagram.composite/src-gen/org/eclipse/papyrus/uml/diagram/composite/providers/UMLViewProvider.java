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
package org.eclipse.papyrus.uml.diagram.composite.providers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.ColorConstants;
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
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
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
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.RelativeBendpoints;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.TitleStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.datatype.RelativeBendpoint;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.papyrus.infra.gmfdiag.common.reconciler.DiagramVersioningUtils;
import org.eclipse.papyrus.uml.diagram.common.helper.PreferenceInitializerForElementHelper;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.composite.part.UMLVisualIDRegistry;

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
		return CompositeStructureDiagramEditPart.MODEL_ID;
	}

	/**
	 * @generated
	 */
	protected boolean provides(CreateDiagramViewOperation op) {
		return CompositeStructureDiagramEditPart.MODEL_ID.equals(op.getSemanticHint()) && UMLVisualIDRegistry.getDiagramVisualID(getSemanticElement(op.getSemanticAdapter())) != null
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
				if (!CompositeStructureDiagramEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(op.getContainerView()))) {
					return false; // foreign diagram
				}
				if (visualID != null) {
					switch (visualID) {
					case BehaviorPortEditPart.VISUAL_ID:
						break; // pure design element
					case ActivityCompositeEditPart.VISUAL_ID:
					case InteractionCompositeEditPart.VISUAL_ID:
					case ProtocolStateMachineCompositeEditPart.VISUAL_ID:
					case StateMachineCompositeEditPart.VISUAL_ID:
					case FunctionBehaviorCompositeEditPart.VISUAL_ID:
					case OpaqueBehaviorCompositeEditPart.VISUAL_ID:
					case ComponentCompositeEditPart.VISUAL_ID:
					case DeviceCompositeEditPart.VISUAL_ID:
					case ExecutionEnvironmentCompositeEditPart.VISUAL_ID:
					case NodeCompositeEditPart.VISUAL_ID:
					case ClassCompositeEditPart.VISUAL_ID:
					case PrimitiveTypeEditPart.VISUAL_ID:
					case EnumerationEditPart.VISUAL_ID:
					case DataTypeEditPart.VISUAL_ID:
					case SignalEventEditPart.VISUAL_ID:
					case CallEventEditPart.VISUAL_ID:
					case AnyReceiveEventEditPart.VISUAL_ID:
					case ChangeEventEditPart.VISUAL_ID:
					case TimeEventEditPart.VISUAL_ID:
					case DurationObservationEditPart.VISUAL_ID:
					case TimeObservationEditPart.VISUAL_ID:
					case LiteralBooleanEditPart.VISUAL_ID:
					case LiteralIntegerEditPart.VISUAL_ID:
					case LiteralNullEditPart.VISUAL_ID:
					case LiteralStringEditPart.VISUAL_ID:
					case LiteralUnlimitedNaturalEditPart.VISUAL_ID:
					case StringExpressionEditPart.VISUAL_ID:
					case OpaqueExpressionEditPart.VISUAL_ID:
					case TimeExpressionEditPart.VISUAL_ID:
					case ExpressionEditPart.VISUAL_ID:
					case DurationEditPart.VISUAL_ID:
					case TimeIntervalEditPart.VISUAL_ID:
					case DurationIntervalEditPart.VISUAL_ID:
					case IntervalEditPart.VISUAL_ID:
					case InstanceValueEditPart.VISUAL_ID:
					case TimeConstraintEditPart.VISUAL_ID:
					case IntervalConstraintEditPart.VISUAL_ID:
					case InteractionConstraintEditPart.VISUAL_ID:
					case ConstraintEditPart.VISUAL_ID:
					case PortEditPart.VISUAL_ID:
					case ParameterEditPart.VISUAL_ID:
					case PropertyPartEditPartCN.VISUAL_ID:
					case CollaborationRoleEditPartCN.VISUAL_ID:
					case CollaborationUseEditPartCN.VISUAL_ID:
					case CollaborationCompositeEditPartCN.VISUAL_ID:
					case InterfaceEditPartCN.VISUAL_ID:
					case ActorEditPartCN.VISUAL_ID:
					case DeploymentSpecificationEditPartCN.VISUAL_ID:
					case ArtifactEditPartCN.VISUAL_ID:
					case InformationItemEditPartCN.VISUAL_ID:
					case SignalEditPartCN.VISUAL_ID:
					case UseCaseEditPartCN.VISUAL_ID:
					case CommentEditPartCN.VISUAL_ID:
					case EnumerationLiteralEditPartCLN.VISUAL_ID:
					case CollaborationCompositeEditPart.VISUAL_ID:
					case InterfaceEditPart.VISUAL_ID:
					case ActorEditPart.VISUAL_ID:
					case DeploymentSpecificationEditPart.VISUAL_ID:
					case ArtifactEditPart.VISUAL_ID:
					case InformationItemEditPart.VISUAL_ID:
					case SignalEditPart.VISUAL_ID:
					case UseCaseEditPart.VISUAL_ID:
					case CommentEditPart.VISUAL_ID:
					case DurationConstraintEditPart.VISUAL_ID:
					case ActivityCompositeEditPartCN.VISUAL_ID:
					case InteractionCompositeEditPartCN.VISUAL_ID:
					case ProtocolStateMachineCompositeEditPartCN.VISUAL_ID:
					case StateMachineCompositeEditPartCN.VISUAL_ID:
					case FunctionBehaviorCompositeEditPartCN.VISUAL_ID:
					case OpaqueBehaviorCompositeEditPartCN.VISUAL_ID:
					case ComponentCompositeEditPartCN.VISUAL_ID:
					case DeviceCompositeEditPartCN.VISUAL_ID:
					case ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID:
					case NodeCompositeEditPartCN.VISUAL_ID:
					case ClassCompositeEditPartCN.VISUAL_ID:
					case PrimitiveTypeEditPartCN.VISUAL_ID:
					case EnumerationEditPartCN.VISUAL_ID:
					case DataTypeEditPartCN.VISUAL_ID:
					case DurationConstraintEditPartCN.VISUAL_ID:
					case TimeConstraintEditPartCN.VISUAL_ID:
					case IntervalConstraintEditPartCN.VISUAL_ID:
					case InteractionConstraintEditPartCN.VISUAL_ID:
					case ConstraintEditPartCN.VISUAL_ID:
					case PropertyEditPartCLN.VISUAL_ID:
					case OperationEditPartCLN.VISUAL_ID:
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
		diagram.setType(CompositeStructureDiagramEditPart.MODEL_ID);
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
			case ActivityCompositeEditPart.VISUAL_ID:
				return createActivity_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InteractionCompositeEditPart.VISUAL_ID:
				return createInteraction_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ProtocolStateMachineCompositeEditPart.VISUAL_ID:
				return createProtocolStateMachine_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case StateMachineCompositeEditPart.VISUAL_ID:
				return createStateMachine_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case FunctionBehaviorCompositeEditPart.VISUAL_ID:
				return createFunctionBehavior_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case OpaqueBehaviorCompositeEditPart.VISUAL_ID:
				return createOpaqueBehavior_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ComponentCompositeEditPart.VISUAL_ID:
				return createComponent_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DeviceCompositeEditPart.VISUAL_ID:
				return createDevice_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ExecutionEnvironmentCompositeEditPart.VISUAL_ID:
				return createExecutionEnvironment_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case NodeCompositeEditPart.VISUAL_ID:
				return createNode_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ClassCompositeEditPart.VISUAL_ID:
				return createClass_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case CollaborationCompositeEditPart.VISUAL_ID:
				return createCollaboration_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InterfaceEditPart.VISUAL_ID:
				return createInterface_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return createPrimitiveType_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case EnumerationEditPart.VISUAL_ID:
				return createEnumeration_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DataTypeEditPart.VISUAL_ID:
				return createDataType_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ActorEditPart.VISUAL_ID:
				return createActor_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DeploymentSpecificationEditPart.VISUAL_ID:
				return createDeploymentSpecification_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ArtifactEditPart.VISUAL_ID:
				return createArtifact_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InformationItemEditPart.VISUAL_ID:
				return createInformationItem_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case SignalEditPart.VISUAL_ID:
				return createSignal_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case UseCaseEditPart.VISUAL_ID:
				return createUseCase_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case SignalEventEditPart.VISUAL_ID:
				return createSignalEvent_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case CallEventEditPart.VISUAL_ID:
				return createCallEvent_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case AnyReceiveEventEditPart.VISUAL_ID:
				return createAnyReceiveEvent_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ChangeEventEditPart.VISUAL_ID:
				return createChangeEvent_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case TimeEventEditPart.VISUAL_ID:
				return createTimeEvent_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DurationObservationEditPart.VISUAL_ID:
				return createDurationObservation_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case TimeObservationEditPart.VISUAL_ID:
				return createTimeObservation_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case LiteralBooleanEditPart.VISUAL_ID:
				return createLiteralBoolean_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case LiteralIntegerEditPart.VISUAL_ID:
				return createLiteralInteger_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case LiteralNullEditPart.VISUAL_ID:
				return createLiteralNull_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case LiteralStringEditPart.VISUAL_ID:
				return createLiteralString_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case LiteralUnlimitedNaturalEditPart.VISUAL_ID:
				return createLiteralUnlimitedNatural_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case StringExpressionEditPart.VISUAL_ID:
				return createStringExpression_PackagedElementShape(domainElement, containerView, index, persisted, preferencesHint);
			case OpaqueExpressionEditPart.VISUAL_ID:
				return createOpaqueExpression_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case TimeExpressionEditPart.VISUAL_ID:
				return createTimeExpression_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ExpressionEditPart.VISUAL_ID:
				return createExpression_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DurationEditPart.VISUAL_ID:
				return createDuration_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case TimeIntervalEditPart.VISUAL_ID:
				return createTimeInterval_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DurationIntervalEditPart.VISUAL_ID:
				return createDurationInterval_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case IntervalEditPart.VISUAL_ID:
				return createInterval_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InstanceValueEditPart.VISUAL_ID:
				return createInstanceValue_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case CommentEditPart.VISUAL_ID:
				return createComment_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case DurationConstraintEditPart.VISUAL_ID:
				return createDurationConstraint_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case TimeConstraintEditPart.VISUAL_ID:
				return createTimeConstraint_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case IntervalConstraintEditPart.VISUAL_ID:
				return createIntervalConstraint_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case InteractionConstraintEditPart.VISUAL_ID:
				return createInteractionConstraint_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintEditPart.VISUAL_ID:
				return createConstraint_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case BehaviorPortEditPart.VISUAL_ID:
				return createPort_BehaviorShape(domainElement, containerView, index, persisted, preferencesHint);
			case PortEditPart.VISUAL_ID:
				return createPort_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ParameterEditPart.VISUAL_ID:
				return createParameter_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case PropertyPartEditPartCN.VISUAL_ID:
				return createProperty_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case CollaborationRoleEditPartCN.VISUAL_ID:
				return createConnectableElement_CollaborationRoleShape(domainElement, containerView, index, persisted, preferencesHint);
			case CollaborationUseEditPartCN.VISUAL_ID:
				return createCollaborationUse_Shape(domainElement, containerView, index, persisted, preferencesHint);
			case ActivityCompositeEditPartCN.VISUAL_ID:
				return createActivity_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case InteractionCompositeEditPartCN.VISUAL_ID:
				return createInteraction_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ProtocolStateMachineCompositeEditPartCN.VISUAL_ID:
				return createProtocolStateMachine_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case StateMachineCompositeEditPartCN.VISUAL_ID:
				return createStateMachine_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case FunctionBehaviorCompositeEditPartCN.VISUAL_ID:
				return createFunctionBehavior_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case OpaqueBehaviorCompositeEditPartCN.VISUAL_ID:
				return createOpaqueBehavior_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ComponentCompositeEditPartCN.VISUAL_ID:
				return createComponent_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case DeviceCompositeEditPartCN.VISUAL_ID:
				return createDevice_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID:
				return createExecutionEnvironment_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case NodeCompositeEditPartCN.VISUAL_ID:
				return createNode_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ClassCompositeEditPartCN.VISUAL_ID:
				return createClass_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case CollaborationCompositeEditPartCN.VISUAL_ID:
				return createCollaboration_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case InterfaceEditPartCN.VISUAL_ID:
				return createInterface_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return createPrimitiveType_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case EnumerationEditPartCN.VISUAL_ID:
				return createEnumeration_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case DataTypeEditPartCN.VISUAL_ID:
				return createDataType_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ActorEditPartCN.VISUAL_ID:
				return createActor_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case DeploymentSpecificationEditPartCN.VISUAL_ID:
				return createDeploymentSpecification_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ArtifactEditPartCN.VISUAL_ID:
				return createArtifact_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case InformationItemEditPartCN.VISUAL_ID:
				return createInformationItem_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case SignalEditPartCN.VISUAL_ID:
				return createSignal_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case UseCaseEditPartCN.VISUAL_ID:
				return createUseCase_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case CommentEditPartCN.VISUAL_ID:
				return createComment_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case DurationConstraintEditPartCN.VISUAL_ID:
				return createDurationConstraint_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case TimeConstraintEditPartCN.VISUAL_ID:
				return createTimeConstraint_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case IntervalConstraintEditPartCN.VISUAL_ID:
				return createIntervalConstraint_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case InteractionConstraintEditPartCN.VISUAL_ID:
				return createInteractionConstraint_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case ConstraintEditPartCN.VISUAL_ID:
				return createConstraint_Shape_CN(domainElement, containerView, index, persisted, preferencesHint);
			case PropertyEditPartCLN.VISUAL_ID:
				return createProperty_AttributeLabel(domainElement, containerView, index, persisted, preferencesHint);
			case OperationEditPartCLN.VISUAL_ID:
				return createOperation_OperationLabel(domainElement, containerView, index, persisted, preferencesHint);
			case EnumerationLiteralEditPartCLN.VISUAL_ID:
				return createEnumerationLiteral_LiteralLabel(domainElement, containerView, index, persisted, preferencesHint);
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
			case BehaviorPortLinkEditPart.VISUAL_ID:
				return createPort_BehaviorEdge(containerView, index, persisted, preferencesHint);
			case LinkDescriptorEditPart.VISUAL_ID:
				return createLink_DescriptorEdge(containerView, index, persisted, preferencesHint);
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return createComment_AnnotatedElementEdge(containerView, index, persisted, preferencesHint);
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return createConstraint_ConstrainedElementEdge(containerView, index, persisted, preferencesHint);
			case ComponentRealizationEditPart.VISUAL_ID:
				return createComponentRealization_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case InterfaceRealizationEditPart.VISUAL_ID:
				return createInterfaceRealization_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case SubstitutionEditPart.VISUAL_ID:
				return createSubstitution_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case RealizationEditPart.VISUAL_ID:
				return createRealization_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case ManifestationEditPart.VISUAL_ID:
				return createManifestation_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case AbstractionEditPart.VISUAL_ID:
				return createAbstraction_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case UsageEditPart.VISUAL_ID:
				return createUsage_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case DeploymentEditPart.VISUAL_ID:
				return createDeployment_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case RoleBindingEditPart.VISUAL_ID:
				return createDependency_RoleBindingEdge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case DependencyEditPart.VISUAL_ID:
				return createDependency_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case ConnectorEditPart.VISUAL_ID:
				return createConnector_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case GeneralizationEditPart.VISUAL_ID:
				return createGeneralization_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
			case TimeObservationEventEditPart.VISUAL_ID:
				return createTimeObservation_EventEdge(containerView, index, persisted, preferencesHint);
			case DurationObservationEventEditPart.VISUAL_ID:
				return createDurationObservation_EventEdge(containerView, index, persisted, preferencesHint);
			case RepresentationEditPart.VISUAL_ID:
				return createRepresentation_Edge(containerView, index, persisted, preferencesHint);
			case InformationFlowEditPart.VISUAL_ID:
				return createInformationFlow_Edge(getSemanticElement(semanticAdapter), containerView, index, persisted, preferencesHint);
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
		node.setType(UMLVisualIDRegistry.getType(ActivityCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Activity"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ActivityCompositeNameEditPart.VISUAL_ID));
		Node activity_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActivityCompositeFloatingLabelEditPart.VISUAL_ID));
		activity_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location activity_FloatingNameLabel_Location = (Location) activity_FloatingNameLabel.getLayoutConstraint();
		activity_FloatingNameLabel_Location.setX(0);
		activity_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Activity"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInteraction_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InteractionCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interaction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InteractionCompositeNameEditPart.VISUAL_ID));
		Node interaction_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InteractionCompositeFloatingLabelEditPart.VISUAL_ID));
		interaction_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interaction_FloatingNameLabel_Location = (Location) interaction_FloatingNameLabel.getLayoutConstraint();
		interaction_FloatingNameLabel_Location.setX(0);
		interaction_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(InteractionCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Interaction"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createProtocolStateMachine_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ProtocolStateMachineCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ProtocolStateMachine"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ProtocolStateMachineCompositeNameEditPart.VISUAL_ID));
		Node protocolStateMachine_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ProtocolStateMachineCompositeFloatingLabelEditPart.VISUAL_ID));
		protocolStateMachine_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location protocolStateMachine_FloatingNameLabel_Location = (Location) protocolStateMachine_FloatingNameLabel.getLayoutConstraint();
		protocolStateMachine_FloatingNameLabel_Location.setX(0);
		protocolStateMachine_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ProtocolStateMachineCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "ProtocolStateMachine"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createStateMachine_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(StateMachineCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "StateMachine"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(StateMachineCompositeNameEditPart.VISUAL_ID));
		Node stateMachine_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(StateMachineCompositeFloatingLabelEditPart.VISUAL_ID));
		stateMachine_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location stateMachine_FloatingNameLabel_Location = (Location) stateMachine_FloatingNameLabel.getLayoutConstraint();
		stateMachine_FloatingNameLabel_Location.setX(0);
		stateMachine_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(StateMachineCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "StateMachine"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createFunctionBehavior_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(FunctionBehaviorCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "FunctionBehavior"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(FunctionBehaviorCompositeNameEditPart.VISUAL_ID));
		Node functionBehavior_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(FunctionBehaviorCompositeFloatingLabelEditPart.VISUAL_ID));
		functionBehavior_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location functionBehavior_FloatingNameLabel_Location = (Location) functionBehavior_FloatingNameLabel.getLayoutConstraint();
		functionBehavior_FloatingNameLabel_Location.setX(0);
		functionBehavior_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(FunctionBehaviorCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "FunctionBehavior"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOpaqueBehavior_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OpaqueBehaviorCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OpaqueBehavior"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(OpaqueBehaviorCompositeNameEditPart.VISUAL_ID));
		Node opaqueBehavior_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OpaqueBehaviorCompositeFloatingLabelEditPart.VISUAL_ID));
		opaqueBehavior_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location opaqueBehavior_FloatingNameLabel_Location = (Location) opaqueBehavior_FloatingNameLabel.getLayoutConstraint();
		opaqueBehavior_FloatingNameLabel_Location.setX(0);
		opaqueBehavior_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(OpaqueBehaviorCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "OpaqueBehavior"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComponent_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ComponentCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ComponentCompositeNameEditPart.VISUAL_ID));
		Node component_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ComponentCompositeFloatingLabelEditPart.VISUAL_ID));
		component_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location component_FloatingNameLabel_Location = (Location) component_FloatingNameLabel.getLayoutConstraint();
		component_FloatingNameLabel_Location.setX(0);
		component_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ComponentCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDevice_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DeviceCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Device"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DeviceCompositeNameEditPart.VISUAL_ID));
		Node device_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DeviceCompositeFloatingLabelEditPart.VISUAL_ID));
		device_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location device_FloatingNameLabel_Location = (Location) device_FloatingNameLabel.getLayoutConstraint();
		device_FloatingNameLabel_Location.setX(0);
		device_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(DeviceCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Device"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createExecutionEnvironment_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ExecutionEnvironmentCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ExecutionEnvironment"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ExecutionEnvironmentCompositeNameEditPart.VISUAL_ID));
		Node executionEnvironment_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ExecutionEnvironmentCompositeFloatingLabelEditPart.VISUAL_ID));
		executionEnvironment_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location executionEnvironment_FloatingNameLabel_Location = (Location) executionEnvironment_FloatingNameLabel.getLayoutConstraint();
		executionEnvironment_FloatingNameLabel_Location.setX(0);
		executionEnvironment_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "ExecutionEnvironment"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createNode_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(NodeCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Node"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(NodeCompositeNameEditPart.VISUAL_ID));
		Node node_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(NodeCompositeFloatingLabelEditPart.VISUAL_ID));
		node_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location node_FloatingNameLabel_Location = (Location) node_FloatingNameLabel.getLayoutConstraint();
		node_FloatingNameLabel_Location.setX(0);
		node_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(NodeCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Node"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClass_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ClassCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ClassCompositeNameEditPart.VISUAL_ID));
		Node class_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ClassCompositeFloatingLabelEditPart.VISUAL_ID));
		class_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location class_FloatingNameLabel_Location = (Location) class_FloatingNameLabel.getLayoutConstraint();
		class_FloatingNameLabel_Location.setX(0);
		class_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ClassCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createCollaboration_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CollaborationCompositeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Collaboration"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CollaborationCompositeNameEditPart.VISUAL_ID));
		Node collaboration_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(CollaborationCompositeFloatingLabelEditPart.VISUAL_ID));
		collaboration_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location collaboration_FloatingNameLabel_Location = (Location) collaboration_FloatingNameLabel.getLayoutConstraint();
		collaboration_FloatingNameLabel_Location.setX(0);
		collaboration_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(CollaborationCompositeCompartmentEditPart.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Collaboration"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInterface_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InterfaceEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interface"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InterfaceNameEditPart.VISUAL_ID));
		Node interface_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InterfaceFloatingLabelEditPart.VISUAL_ID));
		interface_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interface_FloatingNameLabel_Location = (Location) interface_FloatingNameLabel.getLayoutConstraint();
		interface_FloatingNameLabel_Location.setX(0);
		interface_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPrimitiveType_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PrimitiveTypeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "PrimitiveType"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(PrimitiveTypeNameEditPart.VISUAL_ID));
		Node primitiveType_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PrimitiveTypeFloatingLabelEditPart.VISUAL_ID));
		primitiveType_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location primitiveType_FloatingNameLabel_Location = (Location) primitiveType_FloatingNameLabel.getLayoutConstraint();
		primitiveType_FloatingNameLabel_Location.setX(0);
		primitiveType_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createEnumeration_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(EnumerationEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(EnumerationNameEditPart.VISUAL_ID));
		Node enumeration_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(EnumerationFloatingLabelEditPart.VISUAL_ID));
		enumeration_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location enumeration_FloatingNameLabel_Location = (Location) enumeration_FloatingNameLabel.getLayoutConstraint();
		enumeration_FloatingNameLabel_Location.setX(0);
		enumeration_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDataType_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DataTypeEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DataTypeNameEditPart.VISUAL_ID));
		Node dataType_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DataTypeFloatingLabelEditPart.VISUAL_ID));
		dataType_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dataType_FloatingNameLabel_Location = (Location) dataType_FloatingNameLabel.getLayoutConstraint();
		dataType_FloatingNameLabel_Location.setX(0);
		dataType_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(DataTypeAttributeCompartmentEditPart.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(DataTypeOperationCompartmentEditPart.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActor_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActorEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Actor"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ActorNameEditPart.VISUAL_ID));
		Node actor_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ActorFloatingLabelEditPart.VISUAL_ID));
		actor_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_FloatingNameLabel_Location = (Location) actor_FloatingNameLabel.getLayoutConstraint();
		actor_FloatingNameLabel_Location.setX(0);
		actor_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDeploymentSpecification_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DeploymentSpecificationEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DeploymentSpecification"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DeploymentSpecificationNameEditPart.VISUAL_ID));
		Node deploymentSpecification_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DeploymentSpecificationFloatingLabelEditPart.VISUAL_ID));
		deploymentSpecification_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location deploymentSpecification_FloatingNameLabel_Location = (Location) deploymentSpecification_FloatingNameLabel.getLayoutConstraint();
		deploymentSpecification_FloatingNameLabel_Location.setX(0);
		deploymentSpecification_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createArtifact_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ArtifactEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Artifact"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ArtifactNameEditPart.VISUAL_ID));
		Node artifact_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ArtifactFloatingLabelEditPart.VISUAL_ID));
		artifact_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location artifact_FloatingNameLabel_Location = (Location) artifact_FloatingNameLabel.getLayoutConstraint();
		artifact_FloatingNameLabel_Location.setX(0);
		artifact_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInformationItem_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InformationItemEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InformationItem"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InformationItemNameEditPart.VISUAL_ID));
		Node informationItem_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InformationItemFloatingLabelEditPart.VISUAL_ID));
		informationItem_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location informationItem_FloatingNameLabel_Location = (Location) informationItem_FloatingNameLabel.getLayoutConstraint();
		informationItem_FloatingNameLabel_Location.setX(0);
		informationItem_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSignal_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(SignalEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Signal"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(SignalNameEditPart.VISUAL_ID));
		Node signal_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(SignalFloatingLabelEditPart.VISUAL_ID));
		signal_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location signal_FloatingNameLabel_Location = (Location) signal_FloatingNameLabel.getLayoutConstraint();
		signal_FloatingNameLabel_Location.setX(0);
		signal_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createUseCase_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(UseCaseEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "UseCase"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(UseCaseNameEditPart.VISUAL_ID));
		Node useCase_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(UseCaseFloatingLabelEditPart.VISUAL_ID));
		useCase_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location useCase_FloatingNameLabel_Location = (Location) useCase_FloatingNameLabel.getLayoutConstraint();
		useCase_FloatingNameLabel_Location.setX(0);
		useCase_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSignalEvent_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(SignalEventEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "SignalEvent"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(SignalEventNameEditPart.VISUAL_ID));
		Node signalEvent_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(SignalEventFloatingLabelEditPart.VISUAL_ID));
		signalEvent_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location signalEvent_FloatingNameLabel_Location = (Location) signalEvent_FloatingNameLabel.getLayoutConstraint();
		signalEvent_FloatingNameLabel_Location.setX(0);
		signalEvent_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createCallEvent_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CallEventEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CallEvent"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CallEventNameEditPart.VISUAL_ID));
		Node callEvent_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(CallEventFloatingLabelEditPart.VISUAL_ID));
		callEvent_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location callEvent_FloatingNameLabel_Location = (Location) callEvent_FloatingNameLabel.getLayoutConstraint();
		callEvent_FloatingNameLabel_Location.setX(0);
		callEvent_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createAnyReceiveEvent_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(AnyReceiveEventEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "AnyReceiveEvent"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(AnyReceiveEventNameEditPart.VISUAL_ID));
		Node anyReceiveEvent_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(AnyReceiveEventFloatingLabelEditPart.VISUAL_ID));
		anyReceiveEvent_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location anyReceiveEvent_FloatingNameLabel_Location = (Location) anyReceiveEvent_FloatingNameLabel.getLayoutConstraint();
		anyReceiveEvent_FloatingNameLabel_Location.setX(0);
		anyReceiveEvent_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createChangeEvent_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ChangeEventEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ChangeEvent"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ChangeEventNameEditPart.VISUAL_ID));
		Node changeEvent_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ChangeEventFloatingLabelEditPart.VISUAL_ID));
		changeEvent_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location changeEvent_FloatingNameLabel_Location = (Location) changeEvent_FloatingNameLabel.getLayoutConstraint();
		changeEvent_FloatingNameLabel_Location.setX(0);
		changeEvent_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTimeEvent_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TimeEventEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeEvent"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(TimeEventNameEditPart.VISUAL_ID));
		Node timeEvent_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeEventFloatingLabelEditPart.VISUAL_ID));
		timeEvent_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeEvent_FloatingNameLabel_Location = (Location) timeEvent_FloatingNameLabel.getLayoutConstraint();
		timeEvent_FloatingNameLabel_Location.setX(0);
		timeEvent_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDurationObservation_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DurationObservationEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DurationObservation"); //$NON-NLS-1$
		Node durationObservation_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(DurationObservationNameEditPart.VISUAL_ID));
		durationObservation_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location durationObservation_NameLabel_Location = (Location) durationObservation_NameLabel.getLayoutConstraint();
		durationObservation_NameLabel_Location.setX(25);
		durationObservation_NameLabel_Location.setY(3);
		Node durationObservation_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(DurationObservationStereotypeLabelEditPart.VISUAL_ID));
		durationObservation_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location durationObservation_StereotypeLabel_Location = (Location) durationObservation_StereotypeLabel.getLayoutConstraint();
		durationObservation_StereotypeLabel_Location.setX(25);
		durationObservation_StereotypeLabel_Location.setY(-10);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "DurationObservation"); //$NON-NLS-1$
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
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeObservation"); //$NON-NLS-1$
		Node timeObservation_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeObservationNameEditPart.VISUAL_ID));
		timeObservation_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeObservation_NameLabel_Location = (Location) timeObservation_NameLabel.getLayoutConstraint();
		timeObservation_NameLabel_Location.setX(25);
		timeObservation_NameLabel_Location.setY(3);
		Node timeObservation_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeObservationStereotypeLabelEditPart.VISUAL_ID));
		timeObservation_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeObservation_StereotypeLabel_Location = (Location) timeObservation_StereotypeLabel.getLayoutConstraint();
		timeObservation_StereotypeLabel_Location.setX(25);
		timeObservation_StereotypeLabel_Location.setY(-10);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "TimeObservation"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createLiteralBoolean_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(LiteralBooleanEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "LiteralBoolean"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(LiteralBooleanNameEditPart.VISUAL_ID));
		Node literalBoolean_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(LiteralBooleanFloatingLabelEditPart.VISUAL_ID));
		literalBoolean_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location literalBoolean_FloatingNameLabel_Location = (Location) literalBoolean_FloatingNameLabel.getLayoutConstraint();
		literalBoolean_FloatingNameLabel_Location.setX(0);
		literalBoolean_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createLiteralInteger_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(LiteralIntegerEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "LiteralInteger"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(LiteralIntegerNameEditPart.VISUAL_ID));
		Node literalInteger_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(LiteralIntegerFloatingLabelEditPart.VISUAL_ID));
		literalInteger_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location literalInteger_FloatingNameLabel_Location = (Location) literalInteger_FloatingNameLabel.getLayoutConstraint();
		literalInteger_FloatingNameLabel_Location.setX(0);
		literalInteger_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createLiteralNull_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(LiteralNullEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "LiteralNull"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(LiteralNullNameEditPart.VISUAL_ID));
		Node literalNull_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(LiteralNullFloatingLabelEditPart.VISUAL_ID));
		literalNull_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location literalNull_FloatingNameLabel_Location = (Location) literalNull_FloatingNameLabel.getLayoutConstraint();
		literalNull_FloatingNameLabel_Location.setX(0);
		literalNull_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createLiteralString_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(LiteralStringEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "LiteralString"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(LiteralStringNameEditPart.VISUAL_ID));
		Node literalString_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(LiteralStringFloatingLabelEditPart.VISUAL_ID));
		literalString_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location literalString_FloatingNameLabel_Location = (Location) literalString_FloatingNameLabel.getLayoutConstraint();
		literalString_FloatingNameLabel_Location.setX(0);
		literalString_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createLiteralUnlimitedNatural_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(LiteralUnlimitedNaturalEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "LiteralUnlimitedNatural"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(LiteralUnlimitedNaturalNameEditPart.VISUAL_ID));
		Node literalUnlimitedNatural_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(LiteralUnlimitedNaturalFloatingLabelEditPart.VISUAL_ID));
		literalUnlimitedNatural_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location literalUnlimitedNatural_FloatingNameLabel_Location = (Location) literalUnlimitedNatural_FloatingNameLabel.getLayoutConstraint();
		literalUnlimitedNatural_FloatingNameLabel_Location.setX(0);
		literalUnlimitedNatural_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createStringExpression_PackagedElementShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(StringExpressionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "StringExpression"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(StringExpressionNameEditPart.VISUAL_ID));
		Node stringExpression_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(StringExpressionFloatingLabelEditPart.VISUAL_ID));
		stringExpression_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location stringExpression_FloatingNameLabel_Location = (Location) stringExpression_FloatingNameLabel.getLayoutConstraint();
		stringExpression_FloatingNameLabel_Location.setX(0);
		stringExpression_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOpaqueExpression_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OpaqueExpressionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OpaqueExpression"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(OpaqueExpressionNameEditPart.VISUAL_ID));
		Node opaqueExpression_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(OpaqueExpressionFloatingLabelEditPart.VISUAL_ID));
		opaqueExpression_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location opaqueExpression_FloatingNameLabel_Location = (Location) opaqueExpression_FloatingNameLabel.getLayoutConstraint();
		opaqueExpression_FloatingNameLabel_Location.setX(0);
		opaqueExpression_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTimeExpression_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TimeExpressionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeExpression"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(TimeExpressionNameEditPart.VISUAL_ID));
		Node timeExpression_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeExpressionFloatingLabelEditPart.VISUAL_ID));
		timeExpression_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeExpression_FloatingNameLabel_Location = (Location) timeExpression_FloatingNameLabel.getLayoutConstraint();
		timeExpression_FloatingNameLabel_Location.setX(0);
		timeExpression_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createExpression_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ExpressionEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Expression"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ExpressionNameEditPart.VISUAL_ID));
		Node expression_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(ExpressionFloatingLabelEditPart.VISUAL_ID));
		expression_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location expression_FloatingNameLabel_Location = (Location) expression_FloatingNameLabel.getLayoutConstraint();
		expression_FloatingNameLabel_Location.setX(0);
		expression_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDuration_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DurationEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Duration"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DurationNameEditPart.VISUAL_ID));
		Node duration_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DurationFloatingLabelEditPart.VISUAL_ID));
		duration_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location duration_FloatingNameLabel_Location = (Location) duration_FloatingNameLabel.getLayoutConstraint();
		duration_FloatingNameLabel_Location.setX(0);
		duration_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTimeInterval_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TimeIntervalEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeInterval"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(TimeIntervalNameEditPart.VISUAL_ID));
		Node timeInterval_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(TimeIntervalFloatingLabelEditPart.VISUAL_ID));
		timeInterval_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location timeInterval_FloatingNameLabel_Location = (Location) timeInterval_FloatingNameLabel.getLayoutConstraint();
		timeInterval_FloatingNameLabel_Location.setX(0);
		timeInterval_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDurationInterval_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DurationIntervalEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DurationInterval"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DurationIntervalNameEditPart.VISUAL_ID));
		Node durationInterval_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(DurationIntervalFloatingLabelEditPart.VISUAL_ID));
		durationInterval_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location durationInterval_FloatingNameLabel_Location = (Location) durationInterval_FloatingNameLabel.getLayoutConstraint();
		durationInterval_FloatingNameLabel_Location.setX(0);
		durationInterval_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInterval_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(IntervalEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interval"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(IntervalNameEditPart.VISUAL_ID));
		Node interval_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(IntervalFloatingLabelEditPart.VISUAL_ID));
		interval_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interval_FloatingNameLabel_Location = (Location) interval_FloatingNameLabel.getLayoutConstraint();
		interval_FloatingNameLabel_Location.setX(0);
		interval_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInstanceValue_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InstanceValueEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InstanceValue"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InstanceValueNameEditPart.VISUAL_ID));
		Node instanceValue_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(InstanceValueFloatingLabelEditPart.VISUAL_ID));
		instanceValue_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location instanceValue_FloatingNameLabel_Location = (Location) instanceValue_FloatingNameLabel.getLayoutConstraint();
		instanceValue_FloatingNameLabel_Location.setX(0);
		instanceValue_FloatingNameLabel_Location.setY(15);
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
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Comment"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CommentBodyEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDurationConstraint_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DurationConstraintEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DurationConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DurationConstraintNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(DurationConstraintSpecificationEditPart.VISUAL_ID));
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
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(TimeConstraintNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(TimeConstraintSpecificationEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createIntervalConstraint_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(IntervalConstraintEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "IntervalConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(IntervalConstraintNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(IntervalConstraintSpecificationEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInteractionConstraint_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InteractionConstraintEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InteractionConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InteractionConstraintNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(InteractionConstraintSpecificationEditPart.VISUAL_ID));
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
		stampShortcut(containerView, node);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintNameEditPart.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintSpecificationEditPart.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPort_BehaviorShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(BehaviorPortEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Port"); //$NON-NLS-1$
		Node port_BehaviorFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(BehaviorPortFloatingLabelEditPart.VISUAL_ID));
		port_BehaviorFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location port_BehaviorFloatingNameLabel_Location = (Location) port_BehaviorFloatingNameLabel.getLayoutConstraint();
		port_BehaviorFloatingNameLabel_Location.setX(0);
		port_BehaviorFloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPort_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PortEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Port"); //$NON-NLS-1$
		Node port_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(PortNameEditPart.VISUAL_ID));
		port_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location port_NameLabel_Location = (Location) port_NameLabel.getLayoutConstraint();
		port_NameLabel_Location.setX(25);
		port_NameLabel_Location.setY(3);
		Node port_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(PortAppliedStereotypeEditPart.VISUAL_ID));
		port_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location port_StereotypeLabel_Location = (Location) port_StereotypeLabel.getLayoutConstraint();
		port_StereotypeLabel_Location.setX(25);
		port_StereotypeLabel_Location.setY(-10);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "Port"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createParameter_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ParameterEditPart.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Parameter"); //$NON-NLS-1$
		Node parameter_NameLabel = createLabel(node, UMLVisualIDRegistry.getType(ParameterNameEditPart.VISUAL_ID));
		parameter_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location parameter_NameLabel_Location = (Location) parameter_NameLabel.getLayoutConstraint();
		parameter_NameLabel_Location.setX(25);
		parameter_NameLabel_Location.setY(3);
		Node parameter_StereotypeLabel = createLabel(node, UMLVisualIDRegistry.getType(ParameterAppliedStereotypeEditPart.VISUAL_ID));
		parameter_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location parameter_StereotypeLabel_Location = (Location) parameter_StereotypeLabel.getLayoutConstraint();
		parameter_StereotypeLabel_Location.setX(25);
		parameter_StereotypeLabel_Location.setY(-10);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(node, prefStore, "Parameter"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createProperty_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PropertyPartEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Property"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(PropertyPartNameEditPartCN.VISUAL_ID));
		Node property_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(PropertyPartFloatingLabelEditPartCN.VISUAL_ID));
		property_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location property_FloatingNameLabel_Location = (Location) property_FloatingNameLabel.getLayoutConstraint();
		property_FloatingNameLabel_Location.setX(0);
		property_FloatingNameLabel_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(PropertyPartCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Property"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConnectableElement_CollaborationRoleShape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CollaborationRoleEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CollaborationRole"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CollaborationRoleNameEditPartCN.VISUAL_ID));
		Node connectableElement_CollaborationRoleFloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(CollaborationRoleFloatingLabelEditPartCN.VISUAL_ID));
		connectableElement_CollaborationRoleFloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location connectableElement_CollaborationRoleFloatingNameLabel_Location = (Location) connectableElement_CollaborationRoleFloatingNameLabel.getLayoutConstraint();
		connectableElement_CollaborationRoleFloatingNameLabel_Location.setX(0);
		connectableElement_CollaborationRoleFloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createCollaborationUse_Shape(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CollaborationUseEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "CollaborationUse"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CollaborationUseNameEditPart.VISUAL_ID));
		Node collaborationUse_FloatingNameLabel = createLabel(node, UMLVisualIDRegistry.getType(CollaborationUseFloatingLabelEditPartCN.VISUAL_ID));
		collaborationUse_FloatingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location collaborationUse_FloatingNameLabel_Location = (Location) collaborationUse_FloatingNameLabel.getLayoutConstraint();
		collaborationUse_FloatingNameLabel_Location.setX(0);
		collaborationUse_FloatingNameLabel_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActivity_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActivityCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Activity"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ActivityCompositeNameEditPartCN.VISUAL_ID));
		Node activity_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ActivityCompositeFloatingLabelEditPartCN.VISUAL_ID));
		activity_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location activity_FloatingNameLabel_CN_Location = (Location) activity_FloatingNameLabel_CN.getLayoutConstraint();
		activity_FloatingNameLabel_CN_Location.setX(0);
		activity_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ActivityCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Activity"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInteraction_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InteractionCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interaction"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InteractionCompositeNameEditPartCN.VISUAL_ID));
		Node interaction_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(InteractionCompositeFloatingLabelEditPartCN.VISUAL_ID));
		interaction_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interaction_FloatingNameLabel_CN_Location = (Location) interaction_FloatingNameLabel_CN.getLayoutConstraint();
		interaction_FloatingNameLabel_CN_Location.setX(0);
		interaction_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(InteractionCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Interaction"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createProtocolStateMachine_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ProtocolStateMachineCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ProtocolStateMachine"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ProtocolStateMachineCompositeNameEditPartCN.VISUAL_ID));
		Node protocolStateMachine_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ProtocolStateMachineCompositeFloatingLabelEditPartCN.VISUAL_ID));
		protocolStateMachine_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location protocolStateMachine_FloatingNameLabel_CN_Location = (Location) protocolStateMachine_FloatingNameLabel_CN.getLayoutConstraint();
		protocolStateMachine_FloatingNameLabel_CN_Location.setX(0);
		protocolStateMachine_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ProtocolStateMachineCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "ProtocolStateMachine"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createStateMachine_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(StateMachineCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "StateMachine"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(StateMachineCompositeNameEditPartCN.VISUAL_ID));
		Node stateMachine_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(StateMachineCompositeFloatingLabelEditPartCN.VISUAL_ID));
		stateMachine_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location stateMachine_FloatingNameLabel_CN_Location = (Location) stateMachine_FloatingNameLabel_CN.getLayoutConstraint();
		stateMachine_FloatingNameLabel_CN_Location.setX(0);
		stateMachine_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(StateMachineCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "StateMachine"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createFunctionBehavior_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(FunctionBehaviorCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "FunctionBehavior"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(FunctionBehaviorCompositeNameEditPartCN.VISUAL_ID));
		Node functionBehavior_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(FunctionBehaviorCompositeFloatingLabelEditPartCN.VISUAL_ID));
		functionBehavior_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location functionBehavior_FloatingNameLabel_CN_Location = (Location) functionBehavior_FloatingNameLabel_CN.getLayoutConstraint();
		functionBehavior_FloatingNameLabel_CN_Location.setX(0);
		functionBehavior_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(FunctionBehaviorCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "FunctionBehavior"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOpaqueBehavior_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(OpaqueBehaviorCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "OpaqueBehavior"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(OpaqueBehaviorCompositeNameEditPartCN.VISUAL_ID));
		Node opaqueBehavior_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(OpaqueBehaviorCompositeFloatingLabelEditPartCN.VISUAL_ID));
		opaqueBehavior_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location opaqueBehavior_FloatingNameLabel_CN_Location = (Location) opaqueBehavior_FloatingNameLabel_CN.getLayoutConstraint();
		opaqueBehavior_FloatingNameLabel_CN_Location.setX(0);
		opaqueBehavior_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(OpaqueBehaviorCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "OpaqueBehavior"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComponent_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ComponentCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ComponentCompositeNameEditPartCN.VISUAL_ID));
		Node component_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ComponentCompositeFloatingLabelEditPartCN.VISUAL_ID));
		component_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location component_FloatingNameLabel_CN_Location = (Location) component_FloatingNameLabel_CN.getLayoutConstraint();
		component_FloatingNameLabel_CN_Location.setX(0);
		component_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ComponentCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Component"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDevice_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DeviceCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Device"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DeviceCompositeNameEditPartCN.VISUAL_ID));
		Node device_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(DeviceCompositeFloatingLabelEditPartCN.VISUAL_ID));
		device_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location device_FloatingNameLabel_CN_Location = (Location) device_FloatingNameLabel_CN.getLayoutConstraint();
		device_FloatingNameLabel_CN_Location.setX(0);
		device_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(DeviceCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Device"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createExecutionEnvironment_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "ExecutionEnvironment"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ExecutionEnvironmentCompositeNameEditPartCN.VISUAL_ID));
		Node executionEnvironment_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ExecutionEnvironmentCompositeFloatingLabelEditPartCN.VISUAL_ID));
		executionEnvironment_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location executionEnvironment_FloatingNameLabel_CN_Location = (Location) executionEnvironment_FloatingNameLabel_CN.getLayoutConstraint();
		executionEnvironment_FloatingNameLabel_CN_Location.setX(0);
		executionEnvironment_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "ExecutionEnvironment"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createNode_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(NodeCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Node"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(NodeCompositeNameEditPartCN.VISUAL_ID));
		Node node_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(NodeCompositeFloatingLabelEditPartCN.VISUAL_ID));
		node_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location node_FloatingNameLabel_CN_Location = (Location) node_FloatingNameLabel_CN.getLayoutConstraint();
		node_FloatingNameLabel_CN_Location.setX(0);
		node_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(NodeCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Node"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createClass_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ClassCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ClassCompositeNameEditPartCN.VISUAL_ID));
		Node class_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ClassCompositeFloatingLabelEditPartCN.VISUAL_ID));
		class_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location class_FloatingNameLabel_CN_Location = (Location) class_FloatingNameLabel_CN.getLayoutConstraint();
		class_FloatingNameLabel_CN_Location.setX(0);
		class_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(ClassCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Class"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createCollaboration_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CollaborationCompositeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Collaboration"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CollaborationCompositeNameEditPartCN.VISUAL_ID));
		Node collaboration_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(CollaborationCompositeFloatingLabelEditPartCN.VISUAL_ID));
		collaboration_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location collaboration_FloatingNameLabel_CN_Location = (Location) collaboration_FloatingNameLabel_CN.getLayoutConstraint();
		collaboration_FloatingNameLabel_CN_Location.setX(0);
		collaboration_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(CollaborationCompositeCompartmentEditPartCN.VISUAL_ID), true, true, false, false);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Collaboration"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInterface_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InterfaceEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Interface"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InterfaceNameEditPartCN.VISUAL_ID));
		Node interface_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(InterfaceFloatingLabelEditPartCN.VISUAL_ID));
		interface_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interface_FloatingNameLabel_CN_Location = (Location) interface_FloatingNameLabel_CN.getLayoutConstraint();
		interface_FloatingNameLabel_CN_Location.setX(0);
		interface_FloatingNameLabel_CN_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createPrimitiveType_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(PrimitiveTypeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "PrimitiveType"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(PrimitiveTypeNameEditPartCN.VISUAL_ID));
		Node primitiveType_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(PrimitiveTypeFloatingLabelEditPartCN.VISUAL_ID));
		primitiveType_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location primitiveType_FloatingNameLabel_CN_Location = (Location) primitiveType_FloatingNameLabel_CN.getLayoutConstraint();
		primitiveType_FloatingNameLabel_CN_Location.setX(0);
		primitiveType_FloatingNameLabel_CN_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createEnumeration_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(EnumerationEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(EnumerationNameEditPartCN.VISUAL_ID));
		Node enumeration_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(EnumerationFloatingLabelEditPartCN.VISUAL_ID));
		enumeration_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location enumeration_FloatingNameLabel_CN_Location = (Location) enumeration_FloatingNameLabel_CN.getLayoutConstraint();
		enumeration_FloatingNameLabel_CN_Location.setX(0);
		enumeration_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "Enumeration"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDataType_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DataTypeEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DataTypeNameEditPartCN.VISUAL_ID));
		Node dataType_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(DataTypeFloatingLabelEditPartCN.VISUAL_ID));
		dataType_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dataType_FloatingNameLabel_CN_Location = (Location) dataType_FloatingNameLabel_CN.getLayoutConstraint();
		dataType_FloatingNameLabel_CN_Location.setX(0);
		dataType_FloatingNameLabel_CN_Location.setY(15);
		createCompartment(node, UMLVisualIDRegistry.getType(DataTypeAttributeCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		createCompartment(node, UMLVisualIDRegistry.getType(DataTypeOperationCompartmentEditPartCN.VISUAL_ID), true, true, true, true);
		PreferenceInitializerForElementHelper.initCompartmentsStatusFromPrefs(node, prefStore, "DataType"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createActor_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ActorEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Actor"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ActorNameEditPartCN.VISUAL_ID));
		Node actor_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ActorFloatingLabelEditPartCN.VISUAL_ID));
		actor_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location actor_FloatingNameLabel_CN_Location = (Location) actor_FloatingNameLabel_CN.getLayoutConstraint();
		actor_FloatingNameLabel_CN_Location.setX(0);
		actor_FloatingNameLabel_CN_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDeploymentSpecification_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DeploymentSpecificationEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DeploymentSpecification"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DeploymentSpecificationNameEditPartCN.VISUAL_ID));
		Node deploymentSpecification_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(DeploymentSpecificationFloatingLabelEditPartCN.VISUAL_ID));
		deploymentSpecification_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location deploymentSpecification_FloatingNameLabel_CN_Location = (Location) deploymentSpecification_FloatingNameLabel_CN.getLayoutConstraint();
		deploymentSpecification_FloatingNameLabel_CN_Location.setX(0);
		deploymentSpecification_FloatingNameLabel_CN_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createArtifact_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ArtifactEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Artifact"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ArtifactNameEditPartCN.VISUAL_ID));
		Node artifact_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(ArtifactFloatingLabelEditPartCN.VISUAL_ID));
		artifact_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location artifact_FloatingNameLabel_CN_Location = (Location) artifact_FloatingNameLabel_CN.getLayoutConstraint();
		artifact_FloatingNameLabel_CN_Location.setX(0);
		artifact_FloatingNameLabel_CN_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInformationItem_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InformationItemEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InformationItem"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InformationItemNameEditPartCN.VISUAL_ID));
		Node informationItem_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(InformationItemFloatingLabelEditPartCN.VISUAL_ID));
		informationItem_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location informationItem_FloatingNameLabel_CN_Location = (Location) informationItem_FloatingNameLabel_CN.getLayoutConstraint();
		informationItem_FloatingNameLabel_CN_Location.setX(0);
		informationItem_FloatingNameLabel_CN_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createSignal_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(SignalEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Signal"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(SignalNameEditPartCN.VISUAL_ID));
		Node signal_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(SignalFloatingLabelEditPartCN.VISUAL_ID));
		signal_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location signal_FloatingNameLabel_CN_Location = (Location) signal_FloatingNameLabel_CN.getLayoutConstraint();
		signal_FloatingNameLabel_CN_Location.setX(0);
		signal_FloatingNameLabel_CN_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createUseCase_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(UseCaseEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "UseCase"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(UseCaseNameEditPartCN.VISUAL_ID));
		Node useCase_FloatingNameLabel_CN = createLabel(node, UMLVisualIDRegistry.getType(UseCaseFloatingLabelEditPartCN.VISUAL_ID));
		useCase_FloatingNameLabel_CN.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location useCase_FloatingNameLabel_CN_Location = (Location) useCase_FloatingNameLabel_CN.getLayoutConstraint();
		useCase_FloatingNameLabel_CN_Location.setX(0);
		useCase_FloatingNameLabel_CN_Location.setY(15);
		return node;
	}

	/**
	 * @generated
	 */
	public Node createComment_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(CommentEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Comment"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(CommentBodyEditPartCN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createDurationConstraint_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(DurationConstraintEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "DurationConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(DurationConstraintNameEditPartCN.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(DurationConstraintSpecificationEditPartCN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createTimeConstraint_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(TimeConstraintEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "TimeConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(TimeConstraintNameEditPartCN.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(TimeConstraintSpecificationEditPartCN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createIntervalConstraint_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(IntervalConstraintEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "IntervalConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(IntervalConstraintNameEditPartCN.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(IntervalConstraintSpecificationEditPartCN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createInteractionConstraint_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(InteractionConstraintEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "InteractionConstraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(InteractionConstraintNameEditPartCN.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(InteractionConstraintSpecificationEditPartCN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createConstraint_Shape_CN(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Shape node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createBounds());
		node.setType(UMLVisualIDRegistry.getType(ConstraintEditPartCN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		// initializeFromPreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Constraint"); //$NON-NLS-1$
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintNameEditPartCN.VISUAL_ID));
		createLabel(node, UMLVisualIDRegistry.getType(ConstraintSpecificationEditPartCN.VISUAL_ID));
		return node;
	}

	/**
	 * @generated
	 */
	public Node createProperty_AttributeLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(PropertyEditPartCLN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Property"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createOperation_OperationLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(OperationEditPartCLN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "Operation"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	public Node createEnumerationLiteral_LiteralLabel(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Node node = NotationFactory.eINSTANCE.createShape();
		node.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		node.setType(UMLVisualIDRegistry.getType(EnumerationLiteralEditPartCLN.VISUAL_ID));
		ViewUtil.insertChildView(containerView, node, index, persisted);
		node.setElement(domainElement);
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(node, prefStore, "EnumerationLiteral"); //$NON-NLS-1$
		return node;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createPort_BehaviorEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(BehaviorPortLinkEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createLink_DescriptorEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(LinkDescriptorEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
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
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "CommentAnnotatedElement"); //$NON-NLS-1$
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
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "ConstraintConstrainedElement"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createComponentRealization_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ComponentRealizationEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "ComponentRealization"); //$NON-NLS-1$
		Node componentRealization_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(ComponentRealizationNameEditPart.VISUAL_ID));
		componentRealization_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location componentRealization_NameLabel_Location = (Location) componentRealization_NameLabel.getLayoutConstraint();
		componentRealization_NameLabel_Location.setX(0);
		componentRealization_NameLabel_Location.setY(60);
		Node componentRealization_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(ComponentRealizationAppliedStereotypeEditPart.VISUAL_ID));
		componentRealization_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location componentRealization_StereotypeLabel_Location = (Location) componentRealization_StereotypeLabel.getLayoutConstraint();
		componentRealization_StereotypeLabel_Location.setX(0);
		componentRealization_StereotypeLabel_Location.setY(30);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "ComponentRealization"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createInterfaceRealization_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(InterfaceRealizationEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "InterfaceRealization"); //$NON-NLS-1$
		Node interfaceRealization_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(InterfaceRealizationNameEditPart.VISUAL_ID));
		interfaceRealization_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interfaceRealization_NameLabel_Location = (Location) interfaceRealization_NameLabel.getLayoutConstraint();
		interfaceRealization_NameLabel_Location.setX(0);
		interfaceRealization_NameLabel_Location.setY(60);
		Node interfaceRealization_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(InterfaceRealizationAppliedStereotypeEditPart.VISUAL_ID));
		interfaceRealization_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location interfaceRealization_StereotypeLabel_Location = (Location) interfaceRealization_StereotypeLabel.getLayoutConstraint();
		interfaceRealization_StereotypeLabel_Location.setX(0);
		interfaceRealization_StereotypeLabel_Location.setY(30);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "InterfaceRealization"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createSubstitution_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(SubstitutionEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Substitution"); //$NON-NLS-1$
		Node substitution_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(SubstitutionNameEditPart.VISUAL_ID));
		substitution_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location substitution_NameLabel_Location = (Location) substitution_NameLabel.getLayoutConstraint();
		substitution_NameLabel_Location.setX(0);
		substitution_NameLabel_Location.setY(60);
		Node substitution_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(SubstitutionAppliedStereotypeEditPart.VISUAL_ID));
		substitution_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location substitution_StereotypeLabel_Location = (Location) substitution_StereotypeLabel.getLayoutConstraint();
		substitution_StereotypeLabel_Location.setX(0);
		substitution_StereotypeLabel_Location.setY(30);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Substitution"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createRealization_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(RealizationEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Realization"); //$NON-NLS-1$
		Node realization_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(RealizationNameEditPart.VISUAL_ID));
		realization_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location realization_NameLabel_Location = (Location) realization_NameLabel.getLayoutConstraint();
		realization_NameLabel_Location.setX(0);
		realization_NameLabel_Location.setY(60);
		Node realization_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(RealizationAppliedStereotypeEditPart.VISUAL_ID));
		realization_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location realization_StereotypeLabel_Location = (Location) realization_StereotypeLabel.getLayoutConstraint();
		realization_StereotypeLabel_Location.setX(0);
		realization_StereotypeLabel_Location.setY(30);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Realization"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createManifestation_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ManifestationEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Manifestation"); //$NON-NLS-1$
		Node manifestation_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(ManifestationNameEditPart.VISUAL_ID));
		manifestation_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location manifestation_NameLabel_Location = (Location) manifestation_NameLabel.getLayoutConstraint();
		manifestation_NameLabel_Location.setX(0);
		manifestation_NameLabel_Location.setY(60);
		Node manifestation_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(ManifestationAppliedStereotypeEditPart.VISUAL_ID));
		manifestation_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location manifestation_StereotypeLabel_Location = (Location) manifestation_StereotypeLabel.getLayoutConstraint();
		manifestation_StereotypeLabel_Location.setX(0);
		manifestation_StereotypeLabel_Location.setY(30);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Manifestation"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createAbstraction_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(AbstractionEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Abstraction"); //$NON-NLS-1$
		Node abstraction_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(AbstractionNameEditPart.VISUAL_ID));
		abstraction_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location abstraction_NameLabel_Location = (Location) abstraction_NameLabel.getLayoutConstraint();
		abstraction_NameLabel_Location.setX(0);
		abstraction_NameLabel_Location.setY(60);
		Node abstraction_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(AbstractionAppliedStereotypeEditPart.VISUAL_ID));
		abstraction_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location abstraction_StereotypeLabel_Location = (Location) abstraction_StereotypeLabel.getLayoutConstraint();
		abstraction_StereotypeLabel_Location.setX(0);
		abstraction_StereotypeLabel_Location.setY(30);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Abstraction"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createUsage_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(UsageEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Usage"); //$NON-NLS-1$
		Node usage_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(UsageNameEditPart.VISUAL_ID));
		usage_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location usage_NameLabel_Location = (Location) usage_NameLabel.getLayoutConstraint();
		usage_NameLabel_Location.setX(0);
		usage_NameLabel_Location.setY(60);
		Node usage_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(UsageAppliedStereotypeEditPart.VISUAL_ID));
		usage_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location usage_StereotypeLabel_Location = (Location) usage_StereotypeLabel.getLayoutConstraint();
		usage_StereotypeLabel_Location.setX(0);
		usage_StereotypeLabel_Location.setY(30);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Usage"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createDeployment_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(DeploymentEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Deployment"); //$NON-NLS-1$
		Node deployment_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(DeploymentNameEditPart.VISUAL_ID));
		deployment_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location deployment_NameLabel_Location = (Location) deployment_NameLabel.getLayoutConstraint();
		deployment_NameLabel_Location.setX(0);
		deployment_NameLabel_Location.setY(60);
		Node deployment_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(DeploymentAppliedStereotypeEditPart.VISUAL_ID));
		deployment_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location deployment_StereotypeLabel_Location = (Location) deployment_StereotypeLabel.getLayoutConstraint();
		deployment_StereotypeLabel_Location.setX(0);
		deployment_StereotypeLabel_Location.setY(30);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Deployment"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createDependency_RoleBindingEdge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(RoleBindingEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "RoleBinding"); //$NON-NLS-1$
		Node dependency_RoleBindingNameLabel = createLabel(edge, UMLVisualIDRegistry.getType(RoleBindingRoleNameEditPart.VISUAL_ID));
		dependency_RoleBindingNameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dependency_RoleBindingNameLabel_Location = (Location) dependency_RoleBindingNameLabel.getLayoutConstraint();
		dependency_RoleBindingNameLabel_Location.setX(0);
		dependency_RoleBindingNameLabel_Location.setY(20);
		Node dependency_RoleBindingStereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(RoleBindingAppliedStereotypeEditPart.VISUAL_ID));
		dependency_RoleBindingStereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dependency_RoleBindingStereotypeLabel_Location = (Location) dependency_RoleBindingStereotypeLabel.getLayoutConstraint();
		dependency_RoleBindingStereotypeLabel_Location.setX(0);
		dependency_RoleBindingStereotypeLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "RoleBinding"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createDependency_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(DependencyEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Dependency"); //$NON-NLS-1$
		Node dependency_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(DependencyNameEditPart.VISUAL_ID));
		dependency_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dependency_NameLabel_Location = (Location) dependency_NameLabel.getLayoutConstraint();
		dependency_NameLabel_Location.setX(0);
		dependency_NameLabel_Location.setY(60);
		Node dependency_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(DependencyAppliedStereotypeEditPart.VISUAL_ID));
		dependency_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location dependency_StereotypeLabel_Location = (Location) dependency_StereotypeLabel.getLayoutConstraint();
		dependency_StereotypeLabel_Location.setX(0);
		dependency_StereotypeLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Dependency"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createConnector_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(ConnectorEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "Connector"); //$NON-NLS-1$
		Node connector_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(ConnectorAppliedStereotypeEditPart.VISUAL_ID));
		connector_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location connector_StereotypeLabel_Location = (Location) connector_StereotypeLabel.getLayoutConstraint();
		connector_StereotypeLabel_Location.setX(0);
		connector_StereotypeLabel_Location.setY(60);
		Node connector_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(ConnectorNameEditPart.VISUAL_ID));
		connector_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location connector_NameLabel_Location = (Location) connector_NameLabel.getLayoutConstraint();
		connector_NameLabel_Location.setX(0);
		connector_NameLabel_Location.setY(-20);
		Node connector_SourceMultiplicityLabel = createLabel(edge, UMLVisualIDRegistry.getType(ConnectorMultiplicitySourceEditPart.VISUAL_ID));
		connector_SourceMultiplicityLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location connector_SourceMultiplicityLabel_Location = (Location) connector_SourceMultiplicityLabel.getLayoutConstraint();
		connector_SourceMultiplicityLabel_Location.setX(0);
		connector_SourceMultiplicityLabel_Location.setY(20);
		Node connector_TargetMultiplicityLabel = createLabel(edge, UMLVisualIDRegistry.getType(ConnectorMultiplicityTargetEditPart.VISUAL_ID));
		connector_TargetMultiplicityLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location connector_TargetMultiplicityLabel_Location = (Location) connector_TargetMultiplicityLabel.getLayoutConstraint();
		connector_TargetMultiplicityLabel_Location.setX(0);
		connector_TargetMultiplicityLabel_Location.setY(20);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Connector"); //$NON-NLS-1$
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
		Node generalization_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(GeneralizationAppliedStereotypeEditPart.VISUAL_ID));
		generalization_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location generalization_StereotypeLabel_Location = (Location) generalization_StereotypeLabel.getLayoutConstraint();
		generalization_StereotypeLabel_Location.setX(0);
		generalization_StereotypeLabel_Location.setY(60);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Generalization"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createTimeObservation_EventEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(TimeObservationEventEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "TimeObservationEvent"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createDurationObservation_EventEdge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(DurationObservationEventEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "DurationObservationEvent"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createRepresentation_Edge(View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(RepresentationEditPart.VISUAL_ID));
		edge.setElement(null);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		ViewUtil.setStructuralFeatureValue(edge, NotationPackage.eINSTANCE.getLineStyle_LineColor(), FigureUtilities.colorToInteger(ColorConstants.black));
		Node representation_KeywordLabel = createLabel(edge, UMLVisualIDRegistry.getType(RepresentationTagLabelEditPart.VISUAL_ID));
		representation_KeywordLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location representation_KeywordLabel_Location = (Location) representation_KeywordLabel.getLayoutConstraint();
		representation_KeywordLabel_Location.setX(0);
		representation_KeywordLabel_Location.setY(15);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "Undefined"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public Edge createInformationFlow_Edge(EObject domainElement, View containerView, int index, boolean persisted, PreferencesHint preferencesHint) {
		Connector edge = NotationFactory.eINSTANCE.createConnector();
		edge.getStyles().add(NotationFactory.eINSTANCE.createFontStyle());
		RelativeBendpoints bendpoints = NotationFactory.eINSTANCE.createRelativeBendpoints();
		List<RelativeBendpoint> points = new ArrayList<>(2);
		points.add(new RelativeBendpoint());
		points.add(new RelativeBendpoint());
		bendpoints.setPoints(points);
		edge.setBendpoints(bendpoints);
		ViewUtil.insertChildView(containerView, edge, index, persisted);
		edge.setType(UMLVisualIDRegistry.getType(InformationFlowEditPart.VISUAL_ID));
		edge.setElement(domainElement);
		// initializePreferences
		final IPreferenceStore prefStore = (IPreferenceStore) preferencesHint.getPreferenceStore();
		PreferenceInitializerForElementHelper.initFontStyleFromPrefs(edge, prefStore, "InformationFlow"); //$NON-NLS-1$
		Node informationFlow_ConveyedLabel = createLabel(edge, UMLVisualIDRegistry.getType(InformationFlowConveyedLabelEditPart.VISUAL_ID));
		informationFlow_ConveyedLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location informationFlow_ConveyedLabel_Location = (Location) informationFlow_ConveyedLabel.getLayoutConstraint();
		informationFlow_ConveyedLabel_Location.setX(0);
		informationFlow_ConveyedLabel_Location.setY(30);
		Node informationFlow_StereotypeLabel = createLabel(edge, UMLVisualIDRegistry.getType(InformationFlowAppliedStereotypeEditPart.VISUAL_ID));
		informationFlow_StereotypeLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location informationFlow_StereotypeLabel_Location = (Location) informationFlow_StereotypeLabel.getLayoutConstraint();
		informationFlow_StereotypeLabel_Location.setX(0);
		informationFlow_StereotypeLabel_Location.setY(15);
		Node informationFlow_NameLabel = createLabel(edge, UMLVisualIDRegistry.getType(InformationFlowNameEditPart.VISUAL_ID));
		informationFlow_NameLabel.setLayoutConstraint(NotationFactory.eINSTANCE.createLocation());
		Location informationFlow_NameLabel_Location = (Location) informationFlow_NameLabel.getLayoutConstraint();
		informationFlow_NameLabel_Location.setX(0);
		informationFlow_NameLabel_Location.setY(40);
		PreferenceInitializerForElementHelper.initLabelVisibilityFromPrefs(edge, prefStore, "InformationFlow"); //$NON-NLS-1$
		return edge;
	}

	/**
	 * @generated
	 */
	protected void stampShortcut(View containerView, Node target) {
		if (!CompositeStructureDiagramEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(containerView))) {
			EAnnotation shortcutAnnotation = EcoreFactory.eINSTANCE.createEAnnotation();
			shortcutAnnotation.setSource("Shortcut"); //$NON-NLS-1$
			shortcutAnnotation.getDetails().put("modelID", CompositeStructureDiagramEditPart.MODEL_ID); //$NON-NLS-1$
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
