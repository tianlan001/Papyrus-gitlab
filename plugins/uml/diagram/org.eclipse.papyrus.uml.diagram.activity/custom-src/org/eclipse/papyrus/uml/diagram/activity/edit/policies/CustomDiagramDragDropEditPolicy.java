/*****************************************************************************
 * Copyright (c) 2009 Atos Origin.
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
 *   Atos Origin - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.commands.UnexecutableCommand;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.core.util.ViewUtil;
import org.eclipse.gmf.runtime.diagram.ui.commands.CreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SetBoundsCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.LabelEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateConnectionViewRequest;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewRequest.ViewDescriptor;
import org.eclipse.gmf.runtime.diagram.ui.requests.DropObjectsRequest;
import org.eclipse.gmf.runtime.emf.core.util.EObjectAdapter;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.Edge;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.adapter.SemanticAdapter;
import org.eclipse.papyrus.infra.gmfdiag.common.commands.CommonDeferredCreateConnectionViewCommand;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.AcceptEventActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActionLocalPostconditionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActionLocalPreconditionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityFinalNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityParameterNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityPartitionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.AddStructuralFeatureValueActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.AddVariableValueActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.BroadcastSignalActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallBehaviorActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CallOperationActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CentralBufferNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ClearAssociationActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ClearStructuralFeatureActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CommentEditPartCN;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CommentLinkEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ConditionalNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ConstraintAsLocalPostcondEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ConstraintAsLocalPrecondEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ControlFlowEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CreateLinkActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CreateLinkObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.CreateObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DataStoreNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DecisionInputEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DecisionInputFlowEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DecisionNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DestroyLinkActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DestroyObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DurationConstraintAsLocalPostcondEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.DurationConstraintAsLocalPrecondEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ExceptionHandlerEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ExpansionRegionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.FlowFinalNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ForkNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.InitialNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.InterruptibleActivityRegionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.IntervalConstraintAsLocalPostcondEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.IntervalConstraintAsLocalPrecondEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.JoinNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.JoinSpecEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.LoopNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.MergeNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ObjectFlowEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.OpaqueActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadExtentActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadIsClassifiedObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadLinkActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadSelfActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadStructuralFeatureActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReadVariableActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReclassifyObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ReduceActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.SendObjectActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.SendSignalActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.SequenceNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StartClassifierBehaviorActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StartObjectBehavoiurActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.StructuredActivityNodeEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.TestIdentityActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.TimeConstraintAsLocalPostcondEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.TimeConstraintAsLocalPrecondEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.UnmarshallActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ValueSpecificationActionEditPart;
import org.eclipse.papyrus.uml.diagram.activity.helper.ActivityLinkMappingHelper;
import org.eclipse.papyrus.uml.diagram.activity.part.UMLVisualIDRegistry;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.CommonDiagramDragDropEditPolicy;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ObjectFlow;
import org.eclipse.uml2.uml.StructuredActivityNode;

/**
 * This class is used to execute the drag and drop from the outline. It can
 * manage the drop of nodes and binary links. To manage specific drop the method
 * CommonDiagramDragDropEditPolicy.getSpecificDropCommand has to be implemented
 */
public class CustomDiagramDragDropEditPolicy extends CommonDiagramDragDropEditPolicy {

	/** DropActionLocalConditionsAfterActionCommand label */
	private static final String LABEL = "DropLocalConditions";

	/** Point to translate successive local conditions to avoid overlapping */
	private static final Point LOCAL_CONDITIONS_TRANSLATION_POINT = new Point(160, 0);

	/**
	 * Instantiates a new custom diagram drag drop edit policy with the right
	 * link mapping helper
	 */
	public CustomDiagramDragDropEditPolicy() {
		super(ActivityLinkMappingHelper.getInstance());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Set<String> getDroppableElementVisualId() {
		Set<String> droppableElementsVisualID = new HashSet<>();
		droppableElementsVisualID.add(InitialNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ActivityFinalNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(FlowFinalNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(OpaqueActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(CallBehaviorActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(CallOperationActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(DecisionNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(DecisionInputEditPart.VISUAL_ID);
		droppableElementsVisualID.add(MergeNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ForkNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(JoinNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(JoinSpecEditPart.VISUAL_ID);
		droppableElementsVisualID.add(DataStoreNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(SendObjectActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(SendSignalActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ActivityParameterNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(AcceptEventActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ValueSpecificationActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ConditionalNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ExpansionRegionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(LoopNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(SequenceNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(StructuredActivityNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ActivityPartitionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(InterruptibleActivityRegionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(CommentEditPartCN.VISUAL_ID);
		droppableElementsVisualID.add(ReadSelfActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(CreateObjectActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ReadStructuralFeatureActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(AddStructuralFeatureValueActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(DestroyObjectActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ReadVariableActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(AddVariableValueActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(BroadcastSignalActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(CentralBufferNodeEditPart.VISUAL_ID);
		droppableElementsVisualID.add(StartObjectBehavoiurActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(TestIdentityActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ClearStructuralFeatureActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(CreateLinkActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ReadLinkActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(DestroyLinkActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ClearAssociationActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ReadExtentActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ReclassifyObjectActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ReadIsClassifiedObjectActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ReduceActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(StartClassifierBehaviorActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(CreateLinkObjectActionEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ObjectFlowEditPart.VISUAL_ID);
		droppableElementsVisualID.add(DecisionInputFlowEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ControlFlowEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ExceptionHandlerEditPart.VISUAL_ID);
		droppableElementsVisualID.add(CommentLinkEditPart.VISUAL_ID);
		droppableElementsVisualID.add(TimeConstraintAsLocalPrecondEditPart.VISUAL_ID);
		droppableElementsVisualID.add(TimeConstraintAsLocalPostcondEditPart.VISUAL_ID);
		droppableElementsVisualID.add(DurationConstraintAsLocalPrecondEditPart.VISUAL_ID);
		droppableElementsVisualID.add(DurationConstraintAsLocalPostcondEditPart.VISUAL_ID);
		droppableElementsVisualID.add(IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID);
		droppableElementsVisualID.add(IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ConstraintAsLocalPrecondEditPart.VISUAL_ID);
		droppableElementsVisualID.add(ConstraintAsLocalPostcondEditPart.VISUAL_ID);
		droppableElementsVisualID.add(UnmarshallActionEditPart.VISUAL_ID);
		return droppableElementsVisualID;
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
		if (nodeVISUALID != null) {
			switch (nodeVISUALID) {
			case InitialNodeEditPart.VISUAL_ID:
			case ActivityFinalNodeEditPart.VISUAL_ID:
			case FlowFinalNodeEditPart.VISUAL_ID:
			case OpaqueActionEditPart.VISUAL_ID:
			case CallBehaviorActionEditPart.VISUAL_ID:
			case CallOperationActionEditPart.VISUAL_ID:
			case DecisionNodeEditPart.VISUAL_ID:
			case DecisionInputEditPart.VISUAL_ID:
			case MergeNodeEditPart.VISUAL_ID:
			case ForkNodeEditPart.VISUAL_ID:
			case JoinNodeEditPart.VISUAL_ID:
			case JoinSpecEditPart.VISUAL_ID:
			case DataStoreNodeEditPart.VISUAL_ID:
			case SendObjectActionEditPart.VISUAL_ID:
			case SendSignalActionEditPart.VISUAL_ID:
			case ActivityParameterNodeEditPart.VISUAL_ID:
			case AcceptEventActionEditPart.VISUAL_ID:
			case ValueSpecificationActionEditPart.VISUAL_ID:
			case ConditionalNodeEditPart.VISUAL_ID:
			case ExpansionRegionEditPart.VISUAL_ID:
			case LoopNodeEditPart.VISUAL_ID:
			case SequenceNodeEditPart.VISUAL_ID:
			case StructuredActivityNodeEditPart.VISUAL_ID:
			case ActivityPartitionEditPart.VISUAL_ID:
			case InterruptibleActivityRegionEditPart.VISUAL_ID:
			case CommentEditPartCN.VISUAL_ID:
			case ReadSelfActionEditPart.VISUAL_ID:
			case CreateObjectActionEditPart.VISUAL_ID:
			case ReadStructuralFeatureActionEditPart.VISUAL_ID:
			case AddStructuralFeatureValueActionEditPart.VISUAL_ID:
			case DestroyObjectActionEditPart.VISUAL_ID:
			case ReadVariableActionEditPart.VISUAL_ID:
			case AddVariableValueActionEditPart.VISUAL_ID:
			case BroadcastSignalActionEditPart.VISUAL_ID:
			case CentralBufferNodeEditPart.VISUAL_ID:
			case StartObjectBehavoiurActionEditPart.VISUAL_ID:
			case TestIdentityActionEditPart.VISUAL_ID:
			case ClearStructuralFeatureActionEditPart.VISUAL_ID:
			case CreateLinkActionEditPart.VISUAL_ID:
			case ReadLinkActionEditPart.VISUAL_ID:
			case DestroyLinkActionEditPart.VISUAL_ID:
			case ClearAssociationActionEditPart.VISUAL_ID:
			case ReadExtentActionEditPart.VISUAL_ID:
			case ReclassifyObjectActionEditPart.VISUAL_ID:
			case ReadIsClassifiedObjectActionEditPart.VISUAL_ID:
			case ReduceActionEditPart.VISUAL_ID:
			case StartClassifierBehaviorActionEditPart.VISUAL_ID:
			case CreateLinkObjectActionEditPart.VISUAL_ID:
			case ObjectFlowEditPart.VISUAL_ID:
			case DecisionInputFlowEditPart.VISUAL_ID:
			case ControlFlowEditPart.VISUAL_ID:
			case ExceptionHandlerEditPart.VISUAL_ID:
			case CommentLinkEditPart.VISUAL_ID:
			case UnmarshallActionEditPart.VISUAL_ID:
				return dropAction(dropRequest, semanticElement, nodeVISUALID);
			case TimeConstraintAsLocalPrecondEditPart.VISUAL_ID:
			case TimeConstraintAsLocalPostcondEditPart.VISUAL_ID:
			case DurationConstraintAsLocalPrecondEditPart.VISUAL_ID:
			case DurationConstraintAsLocalPostcondEditPart.VISUAL_ID:
			case IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID:
			case IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID:
			case ConstraintAsLocalPrecondEditPart.VISUAL_ID:
			case ConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return dropActionLocalCondition(dropRequest, semanticElement, nodeVISUALID);
			}
		}
		if (linkVISUALID != null) {
			switch (linkVISUALID) {
			case ObjectFlowEditPart.VISUAL_ID:
			case ControlFlowEditPart.VISUAL_ID:
				return dropActivityEdge(dropRequest, semanticElement, linkVISUALID);
			}
		}
		return super.getSpecificDropCommand(dropRequest, semanticElement, nodeVISUALID, linkVISUALID);
	}

	/**
	 * Specific drop action for an action (with its local conditions)
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticElement
	 *            the semantic link
	 * @param nodeVISUALID
	 *            the node visual id
	 *
	 * @return the command for action
	 */
	protected Command dropAction(final DropObjectsRequest dropRequest, final Element semanticElement, String nodeVISUALID) {
		// The element to drop is a node
		/*
		 * Check if the element is contained in this new container.
		 * A special case as to be handle for structured element as contained node are not contained by the reference OwnedElement
		 */
		// Retrieve it's expected graphical parent
		EObject graphicalParent = ((GraphicalEditPart) getHost()).resolveSemanticElement();
		if (graphicalParent instanceof StructuredActivityNode) {
			if (!((StructuredActivityNode) graphicalParent).getNodes().contains(semanticElement)) {
				return UnexecutableCommand.INSTANCE;
			}
		} else if (graphicalParent instanceof ActivityPartition) {
			if (false == semanticElement.eContainer() instanceof Activity) {
				return UnexecutableCommand.INSTANCE;
			}
		} else if (graphicalParent instanceof Element) {
			if (!((Element) graphicalParent).getOwnedElements().contains(semanticElement)) {
				return UnexecutableCommand.INSTANCE;
			}
		}
		CompoundCommand globalCmd = new CompoundCommand();
		if (globalCmd.isEmpty()) {
			ICommand cmd = getDefaultDropNodeCommand(nodeVISUALID, dropRequest.getLocation(), semanticElement);
			globalCmd.add(new ICommandProxy(cmd));
		}
		// also drop local conditions
		DropActionLocalConditionsAfterActionCommand cmd = new DropActionLocalConditionsAfterActionCommand(dropRequest, semanticElement);
		globalCmd.add(cmd);
		return globalCmd;
	}

	/**
	 * Specific drop action for an action's local condition
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticElement
	 *            the semantic link
	 * @param nodeVISUALID
	 *            the node visual id
	 *
	 * @return the command for local condition
	 */
	protected Command dropActionLocalCondition(DropObjectsRequest dropRequest, Element semanticElement, String nodeVISUALID) {
		if (getHost() instanceof GraphicalEditPart) {
			// Adapt the location
			Point location = dropRequest.getLocation().getCopy();
			((GraphicalEditPart) getHost()).getContentPane().translateToRelative(location);
			((GraphicalEditPart) getHost()).getContentPane().translateFromParent(location);
			location.translate(((GraphicalEditPart) getHost()).getContentPane().getClientArea().getLocation().getNegated());
			location.y += 100;
			// Retrieve expected graphical parent
			EObject graphicalParent = ((GraphicalEditPart) getHost()).resolveSemanticElement();
			// verification of container differs from usually, condition is
			// graphically contained by the activity
			if (graphicalParent instanceof Activity) {
				// drop the constraint and its link to the action
				Element linkSource = semanticElement.getOwner();
				Element linkTarget = semanticElement;
				// check for existing link part
				for (Object targetView : DiagramEditPartsUtil.getEObjectViews(linkTarget)) {
					if (targetView instanceof View) {
						EditPart targetEditpart = DiagramEditPartsUtil.getEditPartFromView((View) targetView, getHost());
						if (targetEditpart instanceof ActionLocalPreconditionEditPart || targetEditpart instanceof ActionLocalPostconditionEditPart) {
							// condition link is already drawn.
							return UnexecutableCommand.INSTANCE;
						}
					}
				}
				if (TimeConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(nodeVISUALID) || DurationConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(nodeVISUALID) || IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(nodeVISUALID)
						|| ConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(nodeVISUALID)) {
					return new ICommandProxy(dropBinaryLink(new CompositeCommand("drop Local Precondition link"), linkSource, linkTarget, ActionLocalPreconditionEditPart.VISUAL_ID, location, semanticElement));
				} else if (TimeConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(nodeVISUALID) || DurationConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(nodeVISUALID) || IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(nodeVISUALID)
						|| ConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(nodeVISUALID)) {
					return new ICommandProxy(dropBinaryLink(new CompositeCommand("drop Local Postcondition link"), linkSource, linkTarget, ActionLocalPostconditionEditPart.VISUAL_ID, location, semanticElement));
				}
			}
		}
		return UnexecutableCommand.INSTANCE;
	}

	/**
	 * the method provides command to create the binary link into the diagram.
	 * If the source and the target views do not exist, these views will be
	 * created.
	 *
	 * @param cc
	 *            the composite command that will contain the set of command to
	 *            create the binary link
	 * @param source
	 *            the source the element source of the link
	 * @param target
	 *            the target the element target of the link
	 * @param linkVISUALID
	 *            the link VISUALID used to create the view
	 * @param location
	 *            the location the location where the view will be be created
	 * @param semanticLink
	 *            the semantic link that will be attached to the view
	 *
	 * @return the composite command
	 */
	public CompositeCommand dropObjectFlowSpecification(CompositeCommand cc, ObjectFlow source, Behavior target, String linkVISUALID, String specificationVISUALID, Point location, Element semanticLink) {
		// look for editpart
		ObjectFlowEditPart sourceEditPart = lookForEdgePart(source);
		// look for editpart linked with the object flow
		GraphicalEditPart targetEditPart = null;
		if (sourceEditPart != null) {
			// TODO check that there is not already a representation linked to
			// the source object flow
		}
		// descriptor of the link
		CreateConnectionViewRequest.ConnectionViewDescriptor linkdescriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(getUMLElementType(linkVISUALID), ((IHintedType) getUMLElementType(linkVISUALID)).getSemanticHint(),
				getDiagramPreferencesHint());
		IAdaptable sourceAdapter = null;
		IAdaptable targetAdapter = null;
		if (sourceEditPart == null) {
			// creation of the node
			ViewDescriptor descriptor = new ViewDescriptor(new EObjectAdapter(source), Edge.class, null, ViewUtil.APPEND, true, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
			// get the command and execute it.
			CreateCommand nodeCreationCommand = new CreateCommand(((IGraphicalEditPart) getHost()).getEditingDomain(), descriptor, ((View) getHost().getModel()));
			cc.compose(nodeCreationCommand);
			SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y + 100));
			cc.compose(setBoundsCommand);
			sourceAdapter = (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue();
		} else {
			sourceAdapter = new SemanticAdapter(null, sourceEditPart.getModel());
		}
		if (targetEditPart == null) {
			// creation of the node
			String nodeSemanticHint = ((IHintedType) getUMLElementType(specificationVISUALID)).getSemanticHint();
			ViewDescriptor descriptor = new ViewDescriptor(new EObjectAdapter(target), Node.class, nodeSemanticHint, ViewUtil.APPEND, true, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
			// get the command and execute it.
			CreateCommand nodeCreationCommand = new CreateCommand(((IGraphicalEditPart) getHost()).getEditingDomain(), descriptor, ((View) getHost().getModel()));
			cc.compose(nodeCreationCommand);
			SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y - 100));
			cc.compose(setBoundsCommand);
			targetAdapter = (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue();
		} else {
			targetAdapter = new SemanticAdapter(null, targetEditPart.getModel());
		}
		CommonDeferredCreateConnectionViewCommand aLinkCommand = new CommonDeferredCreateConnectionViewCommand(getEditingDomain(), ((IHintedType) getUMLElementType(linkVISUALID)).getSemanticHint(), sourceAdapter, targetAdapter, getViewer(),
				getDiagramPreferencesHint(), linkdescriptor, null);
		aLinkCommand.setElement(semanticLink);
		cc.compose(aLinkCommand);
		return cc;
	}

	/**
	 * Look for editPart from its semantic.
	 *
	 * @param semantic
	 *            the semantic
	 *
	 * @return the edits the part or null if not found
	 */
	private ObjectFlowEditPart lookForEdgePart(EObject semantic) {
		Collection<EditPart> editPartSet = getHost().getViewer().getEditPartRegistry().values();
		Iterator<EditPart> editPartIterator = editPartSet.iterator();
		ObjectFlowEditPart existedEditPart = null;
		while (editPartIterator.hasNext() && existedEditPart == null) {
			EditPart currentEditPart = editPartIterator.next();
			if (currentEditPart instanceof ObjectFlowEditPart && semantic.equals(((ObjectFlowEditPart) currentEditPart).resolveSemanticElement())) {
				existedEditPart = (ObjectFlowEditPart) currentEditPart;
			}
		}
		return existedEditPart;
	}

	/**
	 * Specific drop action for an activity edge
	 *
	 * @param dropRequest
	 *            the drop request
	 * @param semanticLink
	 *            the semantic link
	 * @param linkVISUALID
	 *            the link visual id
	 *
	 * @return the command for association
	 */
	protected Command dropActivityEdge(DropObjectsRequest dropRequest, Element semanticLink, String linkVISUALID) {
		Collection<?> sources = ActivityLinkMappingHelper.getInstance().getSource(semanticLink);
		Collection<?> targets = ActivityLinkMappingHelper.getInstance().getTarget(semanticLink);
		if (sources.size() == 1 && targets.size() == 1) {
			ActivityNode source = (ActivityNode) sources.toArray()[0];
			ActivityNode target = (ActivityNode) targets.toArray()[0];
			CompositeCommand dropBinaryLink = dropBinaryLink(new CompositeCommand("drop Activity Edge"), source, target, linkVISUALID, dropRequest.getLocation(), semanticLink);
			// If the activity edge is interruptible edge we forbib to drag it outside the interuptible edge
			if (dropBinaryLink != null && semanticLink instanceof ActivityEdge && ((ActivityEdge) semanticLink).getInterrupts() != null) {
				if (!((ActivityEdge) semanticLink).getInterrupts().equals(((IGraphicalEditPart) getHost()).resolveSemanticElement())) {
					return UnexecutableCommand.INSTANCE;
				} else {
					return new ICommandProxy(getInterruptbleEdgeCommand(new CompositeCommand("drop Interruptible Activity Edge"), source, target, linkVISUALID, dropRequest.getLocation(), semanticLink));//
				}
			}
			return new ICommandProxy(dropBinaryLink);
		} else {
			return UnexecutableCommand.INSTANCE;
		}
	}

	/**
	 * Get the command to drag and drop an interruptible Edge
	 * Set the source inside the Interruptible Edge and set the target outside the interruptible edge
	 *
	 * @param dropBinaryLink
	 *            {@link CompositeCommand} to compose the newly created command
	 * @param semanticLink
	 * @param point
	 * @param linkVISUALID
	 * @param target
	 * @param source
	 */
	protected CompositeCommand getInterruptbleEdgeCommand(CompositeCommand cc, Element source, Element target, String linkVISUALID, Point location, Element semanticLink) {
		// look for editpart
		GraphicalEditPart sourceEditPart = (GraphicalEditPart) lookForEditPart(source);
		GraphicalEditPart targetEditPart = (GraphicalEditPart) lookForEditPart(target);
		// descriptor of the link
		CreateConnectionViewRequest.ConnectionViewDescriptor linkdescriptor = new CreateConnectionViewRequest.ConnectionViewDescriptor(getUMLElementType(linkVISUALID), ((IHintedType) getUMLElementType(linkVISUALID)).getSemanticHint(),
				getDiagramPreferencesHint());
		IAdaptable sourceAdapter = null;
		IAdaptable targetAdapter = null;
		if (sourceEditPart == null) {
			// creation of the node
			ViewDescriptor descriptor = new ViewDescriptor(new EObjectAdapter(source), Node.class, null, ViewUtil.APPEND, true, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
			// get the command and execute it.
			CreateCommand nodeCreationCommand = new CreateCommand(((IGraphicalEditPart) getHost()).getEditingDomain(), descriptor, ((View) getHost().getModel()));
			cc.compose(nodeCreationCommand);
			SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), new Point(location.x, location.y)); //$NON-NLS-1$
			cc.compose(setBoundsCommand);
			sourceAdapter = (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue();
		} else {
			sourceAdapter = new SemanticAdapter(null, sourceEditPart.getModel());
		}
		if (targetEditPart == null) {
			// creation of the node
			ViewDescriptor descriptor = new ViewDescriptor(new EObjectAdapter(target), Node.class, null, ViewUtil.APPEND, true, ((IGraphicalEditPart) getHost()).getDiagramPreferencesHint());
			// get the command and execute it.
			CreateCommand nodeCreationCommand = new CreateCommand(((IGraphicalEditPart) getHost()).getEditingDomain(), descriptor, ((View) ((View) ((IGraphicalEditPart) getHost()).getTopGraphicEditPart().getModel()).eContainer()));
			cc.compose(nodeCreationCommand);
			IFigure interruptibleActivityRegionFigure = ((IGraphicalEditPart) getHost()).getFigure();
			interruptibleActivityRegionFigure.getBounds();
			Point targetPoint = location.getCopy();
			targetPoint.setX(targetPoint.x() + 50);
			interruptibleActivityRegionFigure.translateToAbsolute(targetPoint);
			while (interruptibleActivityRegionFigure.containsPoint(targetPoint)) {
				targetPoint.setX(targetPoint.x() + 50);
			}
			SetBoundsCommand setBoundsCommand = new SetBoundsCommand(getEditingDomain(), "move", (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue(), targetPoint); //$NON-NLS-1$
			cc.compose(setBoundsCommand);
			targetAdapter = (IAdaptable) nodeCreationCommand.getCommandResult().getReturnValue();
		} else {
			targetAdapter = new SemanticAdapter(null, targetEditPart.getModel());
		}
		CommonDeferredCreateConnectionViewCommand aLinkCommand = new CommonDeferredCreateConnectionViewCommand(getEditingDomain(), ((IHintedType) getUMLElementType(linkVISUALID)).getSemanticHint(), sourceAdapter, targetAdapter, getViewer(),
				getDiagramPreferencesHint(), linkdescriptor, null);
		aLinkCommand.setElement(semanticLink);
		cc.compose(aLinkCommand);
		return cc;
	}

	/**
	 * Avoid selection of label edit parts
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.OldCommonDiagramDragDropEditPolicy#isEditPartTypeSuitableForEClass(java.lang.Class, org.eclipse.emf.ecore.EClass)
	 */
	@Override
	protected boolean isEditPartTypeSuitableForEClass(Class<? extends GraphicalEditPart> editPartClass, EClass eClass) {
		// avoid selecting a label instead of the activity node
		return !LabelEditPart.class.isAssignableFrom(editPartClass);
	}

	/**
	 * This action enables to drop an action's local conditions when an action
	 * is dropped. Check of the existing action view is run at execution time to
	 * avoid duplication of the action's view. This action is an intern class
	 * since it is narrowly linked with this edit policy properties and
	 * operations
	 */
	private class DropActionLocalConditionsAfterActionCommand extends Command {

		/** The drop request */
		private DropObjectsRequest request;

		/** The dropped action */
		private Element droppedAction;

		/** The command which have been executed (known at execution time) */
		private List<Command> executedCommands = new LinkedList<>();

		/**
		 * Constructor.
		 *
		 * @param dropRequest
		 *            the initial request to drop elements
		 * @param action
		 *            the dropped action (whose local conditions must follow)
		 */
		public DropActionLocalConditionsAfterActionCommand(DropObjectsRequest dropRequest, Element action) {
			super(LABEL);
			request = dropRequest;
			droppedAction = action;
		}

		/**
		 * This command can always execute, though it may have no effect
		 *
		 * @see org.eclipse.gef.commands.Command#canExecute()
		 * @return true
		 */
		@Override
		public boolean canExecute() {
			return true;
		}

		/**
		 * Execute the command and drop each local condition view
		 *
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			// update request to unset the position, so that position is
			// automatically recomputed each time (to avoid superposition).
			Object hostView = getHost().getModel();
			if (hostView instanceof View) {
				if (droppedAction instanceof Action) {
					Point initialLocation = request.getLocation().getCopy();
					for (Constraint pre : ((Action) droppedAction).getLocalPreconditions()) {
						String visual = UMLVisualIDRegistry.getNodeVisualID((View) hostView, pre);
						Command localCmd = dropActionLocalCondition(request, pre, visual);
						if (localCmd != null && localCmd.canExecute()) {
							localCmd.execute();
							executedCommands.add(localCmd);
							// update the request's position to avoid conditions
							// superposition
							request.getLocation().translate(LOCAL_CONDITIONS_TRANSLATION_POINT);
						}
					}
					for (Constraint post : ((Action) droppedAction).getLocalPostconditions()) {
						String visual = UMLVisualIDRegistry.getNodeVisualID((View) hostView, post);
						Command localCmd = dropActionLocalCondition(request, post, visual);
						if (localCmd != null && localCmd.canExecute()) {
							localCmd.execute();
							executedCommands.add(localCmd);
							// update the request's position to avoid conditions
							// superposition
							request.getLocation().translate(LOCAL_CONDITIONS_TRANSLATION_POINT);
						}
					}
					// restore initial location
					request.getLocation().setLocation(initialLocation);
				}
			}
		}

		/**
		 * Undo executed commands
		 *
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			// undo commands in the inverse order
			for (int i = executedCommands.size() - 1; i >= 0; i--) {
				Command cmd = executedCommands.get(i);
				cmd.undo();
			}
			executedCommands.clear();
		}
	}

	@Override
	protected Command getDropCommand(ChangeBoundsRequest request) {
		return super.getDropCommand(request);
	}
}
