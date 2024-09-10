/*****************************************************************************
 * Copyright (c) 2009, 2015 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 433206
 *  Christian W. Damus - bug 470823
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.composite.custom.edit.policies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.CommonDiagramDragDropEditPolicy;
import org.eclipse.papyrus.uml.diagram.composite.custom.helper.CollaborationHelper;
import org.eclipse.papyrus.uml.diagram.composite.custom.helper.CompositeLinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.composite.custom.helper.ConnectorHelper;
import org.eclipse.papyrus.uml.diagram.composite.custom.helper.DurationObservationHelper;
import org.eclipse.papyrus.uml.diagram.composite.custom.helper.TimeObservationHelper;
import org.eclipse.papyrus.uml.diagram.composite.custom.helper.TypeHelper;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ActivityCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ActivityCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ActorEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.AnyReceiveEventEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ArtifactEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CallEventEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ChangeEventEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ClassCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CollaborationCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CollaborationCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CommentEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ComponentCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ComponentCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ConnectorEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ConstraintEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DataTypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DependencyEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DeploymentSpecificationEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DeviceCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DeviceCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DurationConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DurationEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.DurationObservationEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.EnumerationEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ExecutionEnvironmentCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ExecutionEnvironmentCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ExpressionEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.FunctionBehaviorCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.FunctionBehaviorCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InformationItemEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InstanceValueEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InteractionCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InteractionCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InteractionConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.InterfaceEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.IntervalConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.IntervalEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.LiteralBooleanEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.LiteralIntegerEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.LiteralNullEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.LiteralStringEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.LiteralUnlimitedNaturalEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.NodeCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.NodeCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.OpaqueBehaviorCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.OpaqueBehaviorCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.OpaqueExpressionEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ParameterEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PortEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.PrimitiveTypeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ProtocolStateMachineCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.ProtocolStateMachineCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.RoleBindingEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.SignalEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.SignalEventEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.StateMachineCompositeEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.StateMachineCompositeEditPartCN;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.StringExpressionEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.TimeConstraintEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.TimeEventEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.TimeExpressionEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.TimeObservationEditPart;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.UseCaseEditPart;
import org.eclipse.papyrus.uml.diagram.composite.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.composite.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.TypedElement;

/**
 * This class provides an implementation for specific behavior of Drag and Drop
 * in the Composite Diagram.
 */
public class CustomDiagramDragDropEditPolicy extends CommonDiagramDragDropEditPolicy {

	/**
	 * Default constructor
	 */
	public CustomDiagramDragDropEditPolicy() {
		super(CompositeLinkMappingHelper.getInstance());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Set<String> getDroppableElementVisualId() {
		// TopLevelNodes
		Set<String> droppableElementsVisualId = new HashSet<String>();
		droppableElementsVisualId.add(ActivityCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(InteractionCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ProtocolStateMachineCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(StateMachineCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(FunctionBehaviorCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(OpaqueBehaviorCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ComponentCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(DeviceCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ExecutionEnvironmentCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(NodeCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ClassCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(CollaborationCompositeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(InterfaceEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PrimitiveTypeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(EnumerationEditPart.VISUAL_ID);
		droppableElementsVisualId.add(DataTypeEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ActorEditPart.VISUAL_ID);
		droppableElementsVisualId.add(DeploymentSpecificationEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ArtifactEditPart.VISUAL_ID);
		droppableElementsVisualId.add(InformationItemEditPart.VISUAL_ID);
		droppableElementsVisualId.add(SignalEditPart.VISUAL_ID);
		droppableElementsVisualId.add(UseCaseEditPart.VISUAL_ID);
		droppableElementsVisualId.add(SignalEventEditPart.VISUAL_ID);
		droppableElementsVisualId.add(CallEventEditPart.VISUAL_ID);
		droppableElementsVisualId.add(AnyReceiveEventEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ChangeEventEditPart.VISUAL_ID);
		droppableElementsVisualId.add(TimeEventEditPart.VISUAL_ID);
		droppableElementsVisualId.add(LiteralBooleanEditPart.VISUAL_ID);
		droppableElementsVisualId.add(LiteralIntegerEditPart.VISUAL_ID);
		droppableElementsVisualId.add(LiteralNullEditPart.VISUAL_ID);
		droppableElementsVisualId.add(LiteralStringEditPart.VISUAL_ID);
		droppableElementsVisualId.add(LiteralUnlimitedNaturalEditPart.VISUAL_ID);
		droppableElementsVisualId.add(StringExpressionEditPart.VISUAL_ID);
		droppableElementsVisualId.add(OpaqueExpressionEditPart.VISUAL_ID);
		droppableElementsVisualId.add(TimeExpressionEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ExpressionEditPart.VISUAL_ID);
		droppableElementsVisualId.add(DurationEditPart.VISUAL_ID);
		droppableElementsVisualId.add(IntervalEditPart.VISUAL_ID);
		droppableElementsVisualId.add(InstanceValueEditPart.VISUAL_ID);
		droppableElementsVisualId.add(CommentEditPart.VISUAL_ID);
		droppableElementsVisualId.add(DurationConstraintEditPart.VISUAL_ID);
		droppableElementsVisualId.add(TimeConstraintEditPart.VISUAL_ID);
		droppableElementsVisualId.add(IntervalConstraintEditPart.VISUAL_ID);
		droppableElementsVisualId.add(InteractionConstraintEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ConstraintEditPart.VISUAL_ID);

		// Class CN
		droppableElementsVisualId.add(ActivityCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(InteractionCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(ProtocolStateMachineCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(StateMachineCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(FunctionBehaviorCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(OpaqueBehaviorCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(ComponentCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(DeviceCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(NodeCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(ClassCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(CollaborationCompositeEditPartCN.VISUAL_ID);

		droppableElementsVisualId.add(CollaborationCompositeEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(DependencyEditPart.VISUAL_ID);
		droppableElementsVisualId.add(RoleBindingEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ConnectorEditPart.VISUAL_ID);
		droppableElementsVisualId.add(PortEditPart.VISUAL_ID);
		droppableElementsVisualId.add(ParameterEditPart.VISUAL_ID);
		droppableElementsVisualId.add(org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(TimeObservationEditPart.VISUAL_ID);
		droppableElementsVisualId.add(DurationObservationEditPart.VISUAL_ID);

		droppableElementsVisualId.add(CommentEditPartCN.VISUAL_ID);
		droppableElementsVisualId.add(ConstraintEditPartCN.VISUAL_ID);
		return droppableElementsVisualId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IElementType getUMLElementType(String elementID) {
		return UMLElementTypes.getElementType(elementID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeVisualID(View containerView, EObject domainElement) {
		return UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLinkWithClassVisualID(EObject domainElement) {
		return UMLVisualIDRegistry.getLinkWithClassVisualID(domainElement);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Command getSpecificDropCommand(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID, String linkVISUALID) {
		if (linkVISUALID != null) {
			// Switch test over linkVisualID
			switch (linkVISUALID) {
			case DependencyEditPart.VISUAL_ID:
				return dropDependency(dropRequest, semanticElement, linkVISUALID);
			case RoleBindingEditPart.VISUAL_ID:
				return dropRoleBinding(dropRequest, semanticElement, linkVISUALID);
			case ConnectorEditPart.VISUAL_ID:
				return dropConnector(dropRequest, semanticElement, linkVISUALID);
			}
		}
		if (nodeVISUALID != null) {
			// Switch test over nodeVISUALID
			switch (nodeVISUALID) {
			// Test TopLevelNode... Start
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
			case CollaborationCompositeEditPart.VISUAL_ID:
			case InterfaceEditPart.VISUAL_ID:
			case PrimitiveTypeEditPart.VISUAL_ID:
			case EnumerationEditPart.VISUAL_ID:
			case DataTypeEditPart.VISUAL_ID:
			case ActorEditPart.VISUAL_ID:
			case DeploymentSpecificationEditPart.VISUAL_ID:
			case ArtifactEditPart.VISUAL_ID:
			case InformationItemEditPart.VISUAL_ID:
			case SignalEditPart.VISUAL_ID:
			case UseCaseEditPart.VISUAL_ID:
			case SignalEventEditPart.VISUAL_ID:
			case CallEventEditPart.VISUAL_ID:
			case AnyReceiveEventEditPart.VISUAL_ID:
			case ChangeEventEditPart.VISUAL_ID:
			case TimeEventEditPart.VISUAL_ID:
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
			case IntervalEditPart.VISUAL_ID:
			case InstanceValueEditPart.VISUAL_ID:
			case DurationConstraintEditPart.VISUAL_ID:
			case TimeConstraintEditPart.VISUAL_ID:
			case IntervalConstraintEditPart.VISUAL_ID:
			case InteractionConstraintEditPart.VISUAL_ID:

				return dropTopLevelNode(dropRequest, semanticElement, nodeVISUALID, linkVISUALID);
			// Test TopLevelNode... End
			case PortEditPart.VISUAL_ID:
				return dropAffixedNode(dropRequest, semanticElement, nodeVISUALID);
			case ParameterEditPart.VISUAL_ID:
				return dropAffixedNode(dropRequest, semanticElement, nodeVISUALID);
			case org.eclipse.papyrus.uml.diagram.composite.edit.parts.PropertyPartEditPartCN.VISUAL_ID:
				return dropProperty(dropRequest, (Property) semanticElement, nodeVISUALID);
			case TimeObservationEditPart.VISUAL_ID:
				return dropTimeObservation(dropRequest, (TimeObservation) semanticElement, nodeVISUALID);
			case DurationObservationEditPart.VISUAL_ID:
				return dropDurationObservation(dropRequest, (DurationObservation) semanticElement, nodeVISUALID);

			case CommentEditPart.VISUAL_ID:
			case CommentEditPartCN.VISUAL_ID:
				return dropComment(dropRequest, semanticElement, nodeVISUALID);

			case ConstraintEditPart.VISUAL_ID:
			case ConstraintEditPartCN.VISUAL_ID:
				return dropConstraint(dropRequest, semanticElement, nodeVISUALID);
			}
		}
		return super.getSpecificDropCommand(dropRequest, semanticElement, nodeVISUALID, linkVISUALID);
	}

	/**
	 * Returns the command to drop the Comment + the link to attach it to its annotated elements
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the semantic link
	 * @param nodeVISUALID
	 *            the node visual id
	 *
	 * @return the command
	 */
	protected Command dropComment(DropObjectsRequest dropRequest, Element semanticLink, String nodeVISUALID) {
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		if (CommentEditPart.VISUAL_ID.equals(nodeVISUALID)) {
			return getDropCommentCommand((Comment) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), graphicalParentEditPart.getNotationView(), (IHintedType) UMLElementTypes.Comment_Shape,
					(IHintedType) UMLElementTypes.Comment_AnnotatedElementEdge);
		} else if (CommentEditPartCN.VISUAL_ID.equals(nodeVISUALID)) {
			return getDropCommentCommand((Comment) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), graphicalParentEditPart.getNotationView(), (IHintedType) UMLElementTypes.Comment_Shape_CN,
					(IHintedType) UMLElementTypes.Comment_AnnotatedElementEdge);
		}
		return UnexecutableCommand.INSTANCE;
	}



	/**
	 * Returns the command to drop the Constraint + the link to attach it to its contrainted elements
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the semantic link
	 * @param nodeVISUALID
	 *            the node visual id
	 *
	 * @return the command
	 */
	protected Command dropConstraint(DropObjectsRequest dropRequest, Element semanticLink, String nodeVISUALID) {
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		if (ConstraintEditPart.VISUAL_ID.equals(nodeVISUALID)) {
			return getDropConstraintCommand((Constraint) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), graphicalParentEditPart.getNotationView(), (IHintedType) UMLElementTypes.Constraint_Shape,
					(IHintedType) UMLElementTypes.Constraint_ConstrainedElementEdge);
		} else if (ConstraintEditPartCN.VISUAL_ID.equals(nodeVISUALID)) {
			return getDropConstraintCommand((Constraint) semanticLink, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), graphicalParentEditPart.getNotationView(), (IHintedType) UMLElementTypes.Constraint_Shape_CN,
					(IHintedType) UMLElementTypes.Constraint_ConstrainedElementEdge);
		}
		return UnexecutableCommand.INSTANCE;
	}


	/**
	 * Returns the drop command for Dependency links.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the element to drop
	 * @param linkVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropDependency(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		Collection<?> sourceEnds = CompositeLinkMappingHelper.getInstance().getSource(semanticLink);
		Collection<?> targetEnds = CompositeLinkMappingHelper.getInstance().getTarget(semanticLink);

		// Dependency with Unary ends
		if ((sourceEnds != null) && (targetEnds != null) && (sourceEnds.size() == 1) && (targetEnds.size() == 1)) {

			Element source = (Element) sourceEnds.toArray()[0];
			Element target = (Element) targetEnds.toArray()[0];
			return new ICommandProxy(dropBinaryLink(new CompositeCommand("Drop Dependency"), source, target, //$NON-NLS-1$
					linkVISUALID, dropRequest.getLocation(), semanticLink));
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * Returns the drop command for RoleBinding links.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the element to drop
	 * @param linkVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropRoleBinding(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		Collection<?> sourceEnds = CompositeLinkMappingHelper.getInstance().getSource(semanticLink);
		Collection<?> targetEnds = CompositeLinkMappingHelper.getInstance().getTarget(semanticLink);

		// Dependency with Unary ends
		if ((sourceEnds != null) && (targetEnds != null) && (sourceEnds.size() == 1) && (targetEnds.size() == 1)) {

			Element source = semanticLink.getOwner();
			Element target = (Element) targetEnds.toArray()[0];
			return new ICommandProxy(dropBinaryLink(new CompositeCommand("drop RoleBinding"), source, target, //$NON-NLS-1$
					linkVISUALID, dropRequest.getLocation(), semanticLink));
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * Returns the drop command for Connector links.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the element to drop
	 * @param linkVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropConnector(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		Collection<?> connectorEnds = CompositeLinkMappingHelper.getInstance().getSource(semanticLink);

		if ((connectorEnds != null) && (connectorEnds.size() == 2)) {

			ConnectorHelper helper = new ConnectorHelper(getEditingDomain());
			return new ICommandProxy(helper.dropConnector((Connector) semanticLink, linkVISUALID, getViewer(), getHost(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView()));
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * Returns the drop command for Property nodes.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param location
	 *            the location to drop the element
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropProperty(DropObjectsRequest dropRequest, Property droppedElement, String nodeVISUALID) {

		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();

		// Default drop location
		Point location = dropRequest.getLocation().getCopy();

		// Port inherits from Property this case should be excluded and treated
		// separately
		if (!(droppedElement instanceof Port)) {

			if ((graphicalParentObject instanceof Classifier) && (((Classifier) graphicalParentObject).getAllAttributes().contains(droppedElement))) {
				// The graphical parent is the real owner of the dropped
				// property.
				// The dropped property may also be an inherited attribute of
				// the graphical parent.
				return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, location, droppedElement));

			} else if (graphicalParentObject instanceof ConnectableElement) {
				Type type = ((ConnectableElement) graphicalParentObject).getType();
				if ((type != null) && (type instanceof Classifier) && (((Classifier) type).getAllAttributes().contains(droppedElement))) {
					// The graphical parent is a Property typed by a Classifier
					// that owns or inherits the
					// dropped property.
					return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, location, droppedElement));
				}
			}
		}

		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * Returns the drop command for DurationObservation nodes.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param location
	 *            the location to drop the element
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 *
	 */
	protected Command dropDurationObservation(DropObjectsRequest dropRequest, DurationObservation droppedElement, String nodeVISUALID) {

		// Test canvas element
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		if (!(graphicalParentObject instanceof org.eclipse.uml2.uml.Package)) {
			return UnexecutableCommand.INSTANCE;
		}

		DurationObservationHelper durationObservationHelper = new DurationObservationHelper(getEditingDomain());
		return durationObservationHelper.dropDurationObservation(droppedElement, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView());

	}

	/**
	 * Returns the drop command for TimeObservation nodes.
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param location
	 *            the location to drop the element
	 * @param droppedElement
	 *            the element to drop
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 *
	 */
	protected Command dropTimeObservation(DropObjectsRequest dropRequest, TimeObservation droppedElement, String nodeVISUALID) {

		// Test canvas element
		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		if (!(graphicalParentObject instanceof org.eclipse.uml2.uml.Package)) {
			return UnexecutableCommand.INSTANCE;
		}

		TimeObservationHelper timeObservationHelper = new TimeObservationHelper(getEditingDomain());
		return timeObservationHelper.dropTimeObservation(droppedElement, getViewer(), getDiagramPreferencesHint(), dropRequest.getLocation(), ((GraphicalEditPart) getHost()).getNotationView());
	}

	/**
	 * <pre>
	 * This method return a drop command for TopLevelNode.
	 * It returns an {@link org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand} in
	 * case the element is dropped on a canvas referencing a domain element that is not a Package.
	 * </pre>
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticElement
	 *            the semantic element
	 * @param nodeVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @param linkVISUALID
	 *            the visual identifier of the EditPart of the dropped element
	 * @return the drop command
	 */
	protected Command dropTopLevelNode(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID, String linkVISUALID) {

		GraphicalEditPart graphicalParentEditPart = (GraphicalEditPart) getHost();
		EObject graphicalParentObject = graphicalParentEditPart.resolveSemanticElement();
		if (graphicalParentObject instanceof org.eclipse.uml2.uml.Package) {
			return new ICommandProxy(getDefaultDropNodeCommand(nodeVISUALID, dropRequest.getLocation(), semanticElement));
		}

		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * <pre>
	 * This method is overridden to provide DND support to element that are not
	 * expected relatively to GMF declarations (e.g. : Drop Class on Collaboration).
	 * In such a case, the VisualID of the dropped element cannot be retrieved, because
	 * not expected to be created on the underlying graphical node.
	 * Such behavior should be externalized (with extension point) in order to be easily
	 * modified in derived diagrams.
	 * </pre>
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.OldCommonDiagramDragDropEditPolicy#getDropObjectsCommand(org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest)
	 *
	 * @param dropRequest
	 *            the drop request
	 * @return the drop command
	 */
	@Override
	public Command getDropObjectsCommand(DropObjectsRequest dropRequest) {

		CompoundCommand cc = new CompoundCommand("Drop");

		Iterator<?> iter = dropRequest.getObjects().iterator();
		if ((dropRequest.getObjects().size() > 0) && (dropRequest.getObjects().get(0) instanceof String)) {
			return getDropFileCommand(dropRequest);
		}

		Point location = dropRequest.getLocation().getCopy();
		((GraphicalEditPart) getHost()).getContentPane().translateToRelative(location);
		((GraphicalEditPart) getHost()).getContentPane().translateFromParent(location);
		location.translate(((GraphicalEditPart) getHost()).getContentPane().getClientArea().getLocation().getNegated());

		EObject graphicalParentObject = ((GraphicalEditPart) getHost()).resolveSemanticElement();
		View graphicalParentView = (View) ((GraphicalEditPart) getHost()).getModel();

		// Warning : in case the diagram refers to a Class (instead of Package), the canvas is a StructuredClassifier
		// but should not accept drop.
		while (!(graphicalParentView instanceof Diagram) && (graphicalParentObject != null) && (iter.hasNext())) {

			EObject droppedObject = (EObject) iter.next();
			if (graphicalParentObject instanceof Collaboration) {

				if (droppedObject instanceof Collaboration) {
					CollaborationHelper helper = new CollaborationHelper(getEditingDomain());
					cc.add(helper.dropCollaborationAsCollaborationUse((GraphicalEditPart) getHost(), (Collaboration) droppedObject, location));

				}

			} else if (graphicalParentObject instanceof StructuredClassifier) {

				if (droppedObject instanceof Collaboration) {
					CollaborationHelper helper = new CollaborationHelper(getEditingDomain());
					cc.add(helper.dropCollaborationAsCollaborationUse((GraphicalEditPart) getHost(), (Collaboration) droppedObject, location));

				}

			} else if (graphicalParentObject instanceof TypedElement) {

				if (droppedObject instanceof Type && (!(droppedObject instanceof Association) || !(graphicalParentObject instanceof ConnectableElement))) { // Properties of association type are nonsense

					TypeHelper helper = new TypeHelper(getEditingDomain());
					cc.add(helper.dropTypeOnTypedElement((GraphicalEditPart) getHost(), (Type) droppedObject, location));
				}

			} else if (graphicalParentObject instanceof CollaborationUse) {

				if (droppedObject instanceof Collaboration) {

					CollaborationHelper helper = new CollaborationHelper(getEditingDomain());
					cc.add(helper.dropCollaborationOnCollaborationUse((GraphicalEditPart) getHost(), (Collaboration) droppedObject, location));
				}
			}
		}

		if (!cc.isEmpty()) {
			return cc;
		}
		return super.getDropObjectsCommand(dropRequest);
	}

	/**
	 * Avoid dropped element to get orphaned for DND action resulting in a
	 * specific action (not a move)
	 */
	@Override
	protected Command getDragCommand(ChangeBoundsRequest request) {

		Boolean isSpecificDrag = false;

		Iterator<?> iter = request.getEditParts().iterator();
		EObject graphicalParentObject = ((GraphicalEditPart) getHost()).resolveSemanticElement();
		while ((graphicalParentObject != null) && (iter.hasNext())) {

			EObject droppedObject = null;
			EditPart droppedEditPart = (EditPart) iter.next();
			if (droppedEditPart instanceof GraphicalEditPart) {
				droppedObject = ((GraphicalEditPart) droppedEditPart).resolveSemanticElement();
			}

			isSpecificDrag = isSpecificDropActionExpected((GraphicalEditPart) getHost(), droppedObject);
		}

		if (isSpecificDrag) {
			return null;
		}

		return super.getDragCommand(request);
	}

	@Override
	protected Command getDropCommand(ChangeBoundsRequest request) {
		Boolean isSpecificDrop = false;

		Iterator<?> iter = request.getEditParts().iterator();
		EObject graphicalParentObject = ((GraphicalEditPart) getHost()).resolveSemanticElement();
		while ((graphicalParentObject != null) && (iter.hasNext())) {
			EObject droppedObject = null;
			EditPart droppedEditPart = (EditPart) iter.next();
			if (droppedEditPart instanceof GraphicalEditPart) {
				droppedObject = ((GraphicalEditPart) droppedEditPart).resolveSemanticElement();
			}

			isSpecificDrop = isSpecificDropActionExpected((GraphicalEditPart) getHost(), droppedObject);
		}

		if (isSpecificDrop) {
			return getDropObjectsCommand(castToDropObjectsRequest(request));
		}

		return super.getDropCommand(request);
	}

	/** Test if a specific drop action shall is expected */
	protected boolean isSpecificDropActionExpected(GraphicalEditPart graphicalParent, EObject droppedObject) {
		boolean isSpecificDropActionExpected = false;

		EObject graphicalParentObject = graphicalParent.resolveSemanticElement();
		if (graphicalParentObject != null) {

			if (graphicalParentObject instanceof Collaboration) {

				if ((droppedObject instanceof Collaboration) || (droppedObject instanceof Class)) {
					isSpecificDropActionExpected = true;
				}

			} else if (graphicalParentObject instanceof StructuredClassifier) {

				if ((droppedObject instanceof Collaboration) || (droppedObject instanceof Class)) {
					isSpecificDropActionExpected = true;
				}

			} else if (graphicalParentObject instanceof TypedElement) {

				if (droppedObject instanceof Type) {
					isSpecificDropActionExpected = true;
				}

			} else if (graphicalParentObject instanceof CollaborationUse) {

				if (droppedObject instanceof Collaboration) {
					isSpecificDropActionExpected = true;
				}
			}
		}

		return isSpecificDropActionExpected;
	}
}
