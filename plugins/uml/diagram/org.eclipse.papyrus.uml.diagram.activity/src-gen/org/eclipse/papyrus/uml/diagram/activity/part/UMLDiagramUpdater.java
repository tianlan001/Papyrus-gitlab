/*****************************************************************************
 * Copyright (c) 2009, 2014 Atos Origin, CEA, and others.
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
 *   Christian W. Damus (CEA) - bug 410909
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.part;

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
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.activity.providers.UMLElementTypes;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActionInputPin;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityGroup;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ActivityParameterNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.AddVariableValueAction;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.BroadcastSignalAction;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.CentralBufferNode;
import org.eclipse.uml2.uml.ClearAssociationAction;
import org.eclipse.uml2.uml.ClearStructuralFeatureAction;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.ConditionalNode;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.CreateLinkAction;
import org.eclipse.uml2.uml.CreateLinkObjectAction;
import org.eclipse.uml2.uml.CreateObjectAction;
import org.eclipse.uml2.uml.DataStoreNode;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.DestroyLinkAction;
import org.eclipse.uml2.uml.DestroyObjectAction;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExceptionHandler;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.ExpansionNode;
import org.eclipse.uml2.uml.ExpansionRegion;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.IntervalConstraint;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.ObjectFlow;
import org.eclipse.uml2.uml.ObjectNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.ReadExtentAction;
import org.eclipse.uml2.uml.ReadIsClassifiedObjectAction;
import org.eclipse.uml2.uml.ReadLinkAction;
import org.eclipse.uml2.uml.ReadSelfAction;
import org.eclipse.uml2.uml.ReadStructuralFeatureAction;
import org.eclipse.uml2.uml.ReadVariableAction;
import org.eclipse.uml2.uml.ReclassifyObjectAction;
import org.eclipse.uml2.uml.ReduceAction;
import org.eclipse.uml2.uml.SendObjectAction;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.SequenceNode;
import org.eclipse.uml2.uml.StartClassifierBehaviorAction;
import org.eclipse.uml2.uml.StartObjectBehaviorAction;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.TestIdentityAction;
import org.eclipse.uml2.uml.TimeConstraint;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UnmarshallAction;
import org.eclipse.uml2.uml.ValuePin;
import org.eclipse.uml2.uml.ValueSpecificationAction;

/**
 * @generated
 */
public class UMLDiagramUpdater implements DiagramUpdater {

	/**
	 * @generated
	 */
	public static final org.eclipse.papyrus.uml.diagram.activity.part.UMLDiagramUpdater INSTANCE = new UMLDiagramUpdater();

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
			case ActivityDiagramEditPart.VISUAL_ID:
				return getPackage_ActivityDiagram_SemanticChildren(view);
			case ActivityEditPart.VISUAL_ID:
				return getActivity_Shape_SemanticChildren(view);
			case OpaqueActionEditPart.VISUAL_ID:
				return getOpaqueAction_Shape_SemanticChildren(view);
			case CallBehaviorActionEditPart.VISUAL_ID:
				return getCallBehaviorAction_Shape_SemanticChildren(view);
			case CallOperationActionEditPart.VISUAL_ID:
				return getCallOperationAction_Shape_SemanticChildren(view);
			case SendObjectActionEditPart.VISUAL_ID:
				return getSendObjectAction_Shape_SemanticChildren(view);
			case SendSignalActionEditPart.VISUAL_ID:
				return getSendSignalAction_Shape_SemanticChildren(view);
			case AcceptEventActionEditPart.VISUAL_ID:
				return getAcceptEventAction_Shape_SemanticChildren(view);
			case ValueSpecificationActionEditPart.VISUAL_ID:
				return getValueSpecificationAction_Shape_SemanticChildren(view);
			case ConditionalNodeEditPart.VISUAL_ID:
				return getConditionalNode_Shape_SemanticChildren(view);
			case ExpansionRegionEditPart.VISUAL_ID:
				return getExpansionRegion_Shape_SemanticChildren(view);
			case LoopNodeEditPart.VISUAL_ID:
				return getLoopNode_Shape_SemanticChildren(view);
			case SequenceNodeEditPart.VISUAL_ID:
				return getSequenceNode_Shape_SemanticChildren(view);
			case StructuredActivityNodeEditPart.VISUAL_ID:
				return getStructuredActivityNode_Shape_SemanticChildren(view);
			case ReadSelfActionEditPart.VISUAL_ID:
				return getReadSelfAction_Shape_SemanticChildren(view);
			case ActivityEditPartCN.VISUAL_ID:
				return getActivity_Shape_CN_SemanticChildren(view);
			case CreateObjectActionEditPart.VISUAL_ID:
				return getCreateObjectAction_Shape_SemanticChildren(view);
			case ReadStructuralFeatureActionEditPart.VISUAL_ID:
				return getReadStructuralFeatureAction_Shape_SemanticChildren(view);
			case AddStructuralFeatureValueActionEditPart.VISUAL_ID:
				return getAddStructuralFeatureValueAction_Shape_SemanticChildren(view);
			case DestroyObjectActionEditPart.VISUAL_ID:
				return getDestroyObjectAction_Shape_SemanticChildren(view);
			case ReadVariableActionEditPart.VISUAL_ID:
				return getReadVariableAction_Shape_SemanticChildren(view);
			case AddVariableValueActionEditPart.VISUAL_ID:
				return getAddVariableValueAction_Shape_SemanticChildren(view);
			case BroadcastSignalActionEditPart.VISUAL_ID:
				return getBroadcastSignalAction_Shape_SemanticChildren(view);
			case StartObjectBehavoiurActionEditPart.VISUAL_ID:
				return getStartObjectBehaviorAction_Shape_SemanticChildren(view);
			case TestIdentityActionEditPart.VISUAL_ID:
				return getTestIdentityAction_Shape_SemanticChildren(view);
			case ClearStructuralFeatureActionEditPart.VISUAL_ID:
				return getClearStructuralFeatureAction_Shape_SemanticChildren(view);
			case CreateLinkActionEditPart.VISUAL_ID:
				return getCreateLinkAction_Shape_SemanticChildren(view);
			case ReadLinkActionEditPart.VISUAL_ID:
				return getReadLinkAction_Shape_SemanticChildren(view);
			case DestroyLinkActionEditPart.VISUAL_ID:
				return getDestroyLinkAction_Shape_SemanticChildren(view);
			case ClearAssociationActionEditPart.VISUAL_ID:
				return getClearAssociationAction_Shape_SemanticChildren(view);
			case ReadExtentActionEditPart.VISUAL_ID:
				return getReadExtentAction_Shape_SemanticChildren(view);
			case ReclassifyObjectActionEditPart.VISUAL_ID:
				return getReclassifyObjectAction_Shape_SemanticChildren(view);
			case ReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return getReadIsClassifiedObjectAction_Shape_SemanticChildren(view);
			case ReduceActionEditPart.VISUAL_ID:
				return getReduceAction_Shape_SemanticChildren(view);
			case StartClassifierBehaviorActionEditPart.VISUAL_ID:
				return getStartClassifierBehaviorAction_Shape_SemanticChildren(view);
			case CreateLinkObjectActionEditPart.VISUAL_ID:
				return getCreateLinkObjectAction_Shape_SemanticChildren(view);
			case UnmarshallActionEditPart.VISUAL_ID:
				return getUnmarshallAction_Shape_SemanticChildren(view);
			case ActivityActivityParametersCompartmentEditPart.VISUAL_ID:
				return getActivity_ParameterCompartment_SemanticChildren(view);
			case ActivityActivityPreConditionsCompartmentEditPart.VISUAL_ID:
				return getActivity_PreconditionCompartment_SemanticChildren(view);
			case ActivityActivityPostConditionsCompartmentEditPart.VISUAL_ID:
				return getActivity_PostconditionCompartment_SemanticChildren(view);
			case ActivityActivityContentCompartmentEditPart.VISUAL_ID:
				return getActivity_ActivityNodeCompartment_SemanticChildren(view);
			case ConditionalNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID:
				return getConditionalNode_ActivityNodeCompartment_SemanticChildren(view);
			case ExpansionRegionStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID:
				return getExpansionRegion_ActivityNodeCompartment_SemanticChildren(view);
			case LoopNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID:
				return getLoopNode_ActivityNodeCompartment_SemanticChildren(view);
			case SequenceNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID:
				return getSequenceNode_ActivityNodeCompartment_SemanticChildren(view);
			case StructuredActivityNodeStructuredActivityNodeContentCompartmentEditPart.VISUAL_ID:
				return getStructuredActivityNode_ActivityNodeCompartment_SemanticChildren(view);
			case ActivityPartitionActivityPartitionContentCompartmentEditPart.VISUAL_ID:
				return getActivityPartition_ActivityNodeCompartment_SemanticChildren(view);
			case InterruptibleActivityRegionInterruptibleActivityRegionContentCompartmentEditPart.VISUAL_ID:
				return getInterruptibleActivityRegion_ActivityNodeCompartment_SemanticChildren(view);
			case ActivityCNParametersCompartmentEditPart.VISUAL_ID:
				return getActivity_ParameterCompartment_CN_SemanticChildren(view);
			case ActivityCNPreConditionsCompartmentEditPart.VISUAL_ID:
				return getActivity_PreconditionCompartment_CN_SemanticChildren(view);
			case ActivityCNPostConditionsCompartmentEditPart.VISUAL_ID:
				return getActivity_PostconditionCompartment_CN_SemanticChildren(view);
			case ActivityCNContentCompartmentEditPart.VISUAL_ID:
				return getActivity_ActivityNodeCompartment_CN_SemanticChildren(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_ActivityDiagram_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getActivity_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Activity modelElement = (Activity) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedNodes()
				.iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityParameterNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getOpaqueAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		OpaqueAction modelElement = (OpaqueAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getInputValues()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ValuePinInOpaqueActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionInputPinInOpaqueActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InputPinInOpaqueActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOutputValues()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInOpaqueActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getCallBehaviorAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		CallBehaviorAction modelElement = (CallBehaviorAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getArguments()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ValuePinInCallBeActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionInputPinInCallBeActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InputPinInCallBeActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getResults()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInCallBeActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getCallOperationAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		CallOperationAction modelElement = (CallOperationAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getArguments()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActionInputPinInCallOpActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInCallOpActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InputPinInCallOpActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getResults()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInCallOpActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		{
			InputPin childElement = modelElement.getTarget();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ValuePinInCallOpActAsTargetEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionInputPinInCallOpActAsTargetEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (InputPinInCallOpActAsTargetEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getSendObjectAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		SendObjectAction modelElement = (SendObjectAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			InputPin childElement = modelElement.getRequest();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ValuePinInSendObjActAsReqEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionInputPinInSendObjActAsReqEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (InputPinInSendObjActAsReqEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			InputPin childElement = modelElement.getTarget();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ValuePinInSendObjActAsTargetEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionInputPinInSendObjActAsTargetEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (InputPinInSendObjActAsTargetEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getSendSignalAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		SendSignalAction modelElement = (SendSignalAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getArguments()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActionInputPinInSendSigActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInSendSigActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InputPinInSendSigActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		{
			InputPin childElement = modelElement.getTarget();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ValuePinInSendSigActAsTargetEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionInputPinInSendSigActAsTargetEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (InputPinInSendSigActAsTargetEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getAcceptEventAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		AcceptEventAction modelElement = (AcceptEventAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getResults()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInAcceptEventActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getValueSpecificationAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ValueSpecificationAction modelElement = (ValueSpecificationAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInValSpecActEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getConditionalNode_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ConditionalNode modelElement = (ConditionalNode) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getStructuredNodeInputs()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getStructuredNodeOutputs()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getExpansionRegion_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ExpansionRegion modelElement = (ExpansionRegion) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getInputElements()
				.iterator(); it.hasNext();) {
			ExpansionNode childElement = (ExpansionNode) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ExpansionNodeAsInEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOutputElements()
				.iterator(); it.hasNext();) {
			ExpansionNode childElement = (ExpansionNode) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ExpansionNodeAsOutEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getStructuredNodeInputs()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getStructuredNodeOutputs()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getLoopNode_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		LoopNode modelElement = (LoopNode) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getLoopVariableInputs()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInLoopNodeAsVariableEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInLoopNodeAsVariableEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionPinInLoopNodeAsVariableEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getBodyOutputs()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInLoopNodeAsBodyOutputEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getLoopVariables()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInLoopNodeAsLoopVariableEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getResults()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInLoopNodeAsResultEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getSequenceNode_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		SequenceNode modelElement = (SequenceNode) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getStructuredNodeInputs()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getStructuredNodeOutputs()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getStructuredActivityNode_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		StructuredActivityNode modelElement = (StructuredActivityNode) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getStructuredNodeInputs()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getStructuredNodeOutputs()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getReadSelfAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ReadSelfAction modelElement = (ReadSelfAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ReadSelfActionOutputPinEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getActivity_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Activity modelElement = (Activity) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedNodes()
				.iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityParameterNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getCreateObjectAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		CreateObjectAction modelElement = (CreateObjectAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInCreateObjectActionAsResultEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getReadStructuralFeatureAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ReadStructuralFeatureAction modelElement = (ReadStructuralFeatureAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			InputPin childElement = modelElement.getObject();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInReadStructuralFeatureAsResultEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getAddStructuralFeatureValueAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		AddStructuralFeatureValueAction modelElement = (AddStructuralFeatureValueAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			InputPin childElement = modelElement.getObject();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			InputPin childElement = modelElement.getValue();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			InputPin childElement = modelElement.getInsertAt();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInAddStructuralFeatureValueActionAsResultEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDestroyObjectAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		DestroyObjectAction modelElement = (DestroyObjectAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			InputPin childElement = modelElement.getTarget();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInDestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInDestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInDestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getReadVariableAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ReadVariableAction modelElement = (ReadVariableAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInReadVariableActionAsResultEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getAddVariableValueAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		AddVariableValueAction modelElement = (AddVariableValueAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			InputPin childElement = modelElement.getInsertAt();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			InputPin childElement = modelElement.getValue();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInAddVariableValueActionAsValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInAddVariableValueActionAsValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInAddVariableValueActionAsValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getBroadcastSignalAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		BroadcastSignalAction modelElement = (BroadcastSignalAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getArguments()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInBroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInBroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionPinInBroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getStartObjectBehaviorAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		StartObjectBehaviorAction modelElement = (StartObjectBehaviorAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getResults()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInStartObjectBehaviorActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		{
			InputPin childElement = modelElement.getObject();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		for (Iterator<?> it = modelElement.getArguments()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getTestIdentityAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		TestIdentityAction modelElement = (TestIdentityAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInTestIdentityActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			InputPin childElement = modelElement.getFirst();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInTestIdentityActionAsFirstEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInTestIdentityActionAsFirstEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInTestIdentityActionAsFirstEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			InputPin childElement = modelElement.getSecond();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInTestIdentityActionAsSecondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInTestIdentityActionAsSecondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInTestIdentityActionAsSecondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClearStructuralFeatureAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ClearStructuralFeatureAction modelElement = (ClearStructuralFeatureAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInClearStructuralFeatureActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			InputPin childElement = modelElement.getObject();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionInputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getCreateLinkAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		CreateLinkAction modelElement = (CreateLinkAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getInputValues()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInCreateLinkActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionInputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getReadLinkAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ReadLinkAction modelElement = (ReadLinkAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInReadLinkActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		for (Iterator<?> it = modelElement.getInputValues()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInReadLinkActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionInputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDestroyLinkAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		DestroyLinkAction modelElement = (DestroyLinkAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getInputValues()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionInputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClearAssociationAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ClearAssociationAction modelElement = (ClearAssociationAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			InputPin childElement = modelElement.getObject();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInClearAssociationActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInClearAssociationActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInClearAssociationActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getReadExtentAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ReadExtentAction modelElement = (ReadExtentAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInReadExtentActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getReclassifyObjectAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ReclassifyObjectAction modelElement = (ReclassifyObjectAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			InputPin childElement = modelElement.getObject();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getReadIsClassifiedObjectAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ReadIsClassifiedObjectAction modelElement = (ReadIsClassifiedObjectAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInReadIsClassifiedObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			InputPin childElement = modelElement.getObject();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getReduceAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ReduceAction modelElement = (ReduceAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInReduceActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			InputPin childElement = modelElement.getCollection();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInReduceActionAsCollectionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInReduceActionAsCollectionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInReduceActionAsCollectionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getStartClassifierBehaviorAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		StartClassifierBehaviorAction modelElement = (StartClassifierBehaviorAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			InputPin childElement = modelElement.getObject();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getCreateLinkObjectAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		CreateLinkObjectAction modelElement = (CreateLinkObjectAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getInputValues()
				.iterator(); it.hasNext();) {
			InputPin childElement = (InputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValuePinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActionPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		{
			OutputPin childElement = modelElement.getResult();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInCreateLinkObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getUnmarshallAction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		UnmarshallAction modelElement = (UnmarshallAction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		{
			InputPin childElement = modelElement.getObject();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InputPinInUnmarshallActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ValuePinInUnmarshallActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
			if (ActionPinInUnmarshallActionAsObjectEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		for (Iterator<?> it = modelElement.getResults()
				.iterator(); it.hasNext();) {
			OutputPin childElement = (OutputPin) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OutputPinInUnmarshallActionAsResultEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getActivity_ParameterCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Activity modelElement = (Activity) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedParameters()
				.iterator(); it.hasNext();) {
			Parameter childElement = (Parameter) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ParameterEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getActivity_PreconditionCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Activity modelElement = (Activity) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPreconditions()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintInActivityAsPrecondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getActivity_PostconditionCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Activity modelElement = (Activity) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPostconditions()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintInActivityAsPostcondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * (update at each gmf change) add children actions' local conditions
	 *
	 * @generated NOT
	 */
	public List<UMLNodeDescriptor> getActivity_ActivityNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Activity modelElement = (Activity) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNodes().iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InitialNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FlowFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallBehaviorActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallOperationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DecisionNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MergeNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ForkNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (JoinNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataStoreNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CreateObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadStructuralFeatureActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddStructuralFeatureValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddVariableValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadVariableActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (BroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AcceptEventActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValueSpecificationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadSelfActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CentralBufferNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		// for (Iterator<?> it = modelElement.getLocalPreconditions().iterator(); it
		// .hasNext();) {
		// Constraint childElement = (Constraint) it.next();
		// String visualID = UMLVisualIDRegistry.getNodeVisualID(view,
		// childElement);
		// if (DurationConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (TimeConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (ConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// }
		// for (Iterator<?> it = modelElement.getLocalPostconditions().iterator(); it
		// .hasNext();) {
		// Constraint childElement = (Constraint) it.next();
		// String visualID = UMLVisualIDRegistry.getNodeVisualID(view,
		// childElement);
		// if (DurationConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (TimeConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (ConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// }
		for (Iterator<?> it = modelElement.getGroups().iterator(); it.hasNext();) {
			ActivityGroup childElement = (ActivityGroup) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConditionalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExpansionRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LoopNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SequenceNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StructuredActivityNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityPartitionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterruptibleActivityRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments().iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedBehaviors().iterator(); it.hasNext();) {
			Behavior childElement = (Behavior) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated NOT (update at each gmf change) add children actions' local conditions, add structured activity group as nodes
	 */
	public List<UMLNodeDescriptor> getConditionalNode_ActivityNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ConditionalNode modelElement = (ConditionalNode) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNodes().iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			// add children actions' local conditions
			if (childElement instanceof Action) {
				result.addAll(getActionLocalConditionsDescriptors((Action) childElement, view));
			}
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InitialNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FlowFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallBehaviorActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallOperationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DecisionNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MergeNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ForkNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (JoinNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataStoreNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CreateObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadStructuralFeatureActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddStructuralFeatureValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddVariableValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadVariableActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (BroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AcceptEventActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValueSpecificationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CentralBufferNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			// add children actions' local conditions (2 blocks removed)
			// add structured activity group as nodes
			// }
			// for(Iterator<?> it = modelElement.getGroups().iterator(); it.hasNext();) {
			// ActivityGroup childElement = (ActivityGroup)it.next();
			// String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConditionalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExpansionRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LoopNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SequenceNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StructuredActivityNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated NOT (update at each gmf change) add children actions' local conditions
	 */
	public List<UMLNodeDescriptor> getExpansionRegion_ActivityNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ExpansionRegion modelElement = (ExpansionRegion) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNodes().iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			// add children actions' local conditions
			if (childElement instanceof Action) {
				result.addAll(getActionLocalConditionsDescriptors((Action) childElement, view));
			}
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InitialNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FlowFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallBehaviorActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallOperationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DecisionNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MergeNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ForkNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (JoinNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataStoreNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CreateObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadStructuralFeatureActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddStructuralFeatureValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddVariableValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadVariableActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (BroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AcceptEventActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValueSpecificationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CentralBufferNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			// add children actions' local conditions (2 blocks removed)
			// add structured activity group as nodes
			// }
			// for(Iterator<?> it = modelElement.getGroups().iterator(); it.hasNext();) {
			// ActivityGroup childElement = (ActivityGroup)it.next();
			// String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConditionalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExpansionRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LoopNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SequenceNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StructuredActivityNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated NOT (update at each gmf change) add children actions' local conditions, add structured activity group as nodes
	 */
	public List<UMLNodeDescriptor> getLoopNode_ActivityNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		LoopNode modelElement = (LoopNode) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNodes().iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			// add children actions' local conditions
			if (childElement instanceof Action) {
				result.addAll(getActionLocalConditionsDescriptors((Action) childElement, view));
			}
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InitialNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FlowFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallBehaviorActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallOperationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DecisionNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MergeNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ForkNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (JoinNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataStoreNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CreateObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadStructuralFeatureActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddStructuralFeatureValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddVariableValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadVariableActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (BroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AcceptEventActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValueSpecificationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CentralBufferNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			// add children actions' local conditions (2 blocks removed)
			// add structured activity group as nodes
			// }
			// for(Iterator<?> it = modelElement.getGroups().iterator(); it.hasNext();) {
			// ActivityGroup childElement = (ActivityGroup)it.next();
			// String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConditionalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExpansionRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LoopNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SequenceNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StructuredActivityNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated NOT (update at each gmf change) add children actions' local conditions, add structured activity group as nodes
	 */
	public List<UMLNodeDescriptor> getSequenceNode_ActivityNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		SequenceNode modelElement = (SequenceNode) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNodes().iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			// add children actions' local conditions
			if (childElement instanceof Action) {
				result.addAll(getActionLocalConditionsDescriptors((Action) childElement, view));
			}
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InitialNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FlowFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallBehaviorActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallOperationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DecisionNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MergeNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ForkNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (JoinNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataStoreNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CreateObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadStructuralFeatureActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddStructuralFeatureValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddVariableValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadVariableActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (BroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AcceptEventActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValueSpecificationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CentralBufferNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			// add children actions' local conditions (2 blocks removed)
			// add structured activity group as nodes
			// }
			// for(Iterator<?> it = modelElement.getGroups().iterator(); it.hasNext();) {
			// ActivityGroup childElement = (ActivityGroup)it.next();
			// String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConditionalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExpansionRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LoopNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SequenceNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StructuredActivityNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated NOT (update at each gmf change) add children actions' local conditions, add structured activity group as nodes
	 */
	public List<UMLNodeDescriptor> getStructuredActivityNode_ActivityNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		StructuredActivityNode modelElement = (StructuredActivityNode) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNodes().iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			// add children actions' local conditions
			if (childElement instanceof Action) {
				result.addAll(getActionLocalConditionsDescriptors((Action) childElement, view));
			}
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InitialNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FlowFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallBehaviorActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallOperationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DecisionNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MergeNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ForkNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (JoinNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataStoreNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CreateObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadStructuralFeatureActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddStructuralFeatureValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddVariableValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadVariableActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (BroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AcceptEventActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValueSpecificationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CentralBufferNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			// add children actions' local conditions (2 blocks removed)
			// add structured activity group as nodes
			// }
			// for(Iterator<?> it = modelElement.getGroups().iterator(); it.hasNext();) {
			// ActivityGroup childElement = (ActivityGroup)it.next();
			// String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConditionalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExpansionRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LoopNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SequenceNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StructuredActivityNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated NOT (update at each gmf change) add children actions' local conditions, add structured activity group as nodes
	 */
	public List<UMLNodeDescriptor> getActivityPartition_ActivityNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ActivityPartition modelElement = (ActivityPartition) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNodes().iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			// add children actions' local conditions
			if (childElement instanceof Action) {
				result.addAll(getActionLocalConditionsDescriptors((Action) childElement, view));
			}
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InitialNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FlowFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallBehaviorActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallOperationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DecisionNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MergeNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ForkNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (JoinNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataStoreNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CreateObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadStructuralFeatureActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddStructuralFeatureValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddVariableValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadVariableActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (BroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AcceptEventActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValueSpecificationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CentralBufferNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			// add children actions' local conditions (2 blocks removed)
			// add structured activity group as nodes
			// }
			// for(Iterator<?> it = modelElement.getGroups().iterator(); it.hasNext();) {
			// ActivityGroup childElement = (ActivityGroup)it.next();
			// String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConditionalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExpansionRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LoopNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SequenceNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StructuredActivityNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityPartitionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated NOT (update at each gmf change) add children actions' local conditions, add structured activity group as nodes
	 */
	public List<UMLNodeDescriptor> getInterruptibleActivityRegion_ActivityNodeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		InterruptibleActivityRegion modelElement = (InterruptibleActivityRegion) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNodes().iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			// add children actions' local conditions
			if (childElement instanceof Action) {
				result.addAll(getActionLocalConditionsDescriptors((Action) childElement, view));
			}
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InitialNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FlowFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallBehaviorActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallOperationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DecisionNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MergeNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ForkNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (JoinNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataStoreNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CreateObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadStructuralFeatureActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddStructuralFeatureValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddVariableValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadVariableActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (BroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AcceptEventActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValueSpecificationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CentralBufferNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			// add children actions' local conditions (2 blocks removed)
			// add structured activity group as nodes
			// }
			// for(Iterator<?> it = modelElement.getGroups().iterator(); it.hasNext();) {
			// ActivityGroup childElement = (ActivityGroup)it.next();
			// String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConditionalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExpansionRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LoopNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SequenceNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StructuredActivityNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getActivity_ParameterCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Activity modelElement = (Activity) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedParameters()
				.iterator(); it.hasNext();) {
			Parameter childElement = (Parameter) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ParameterEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getActivity_PreconditionCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Activity modelElement = (Activity) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPreconditions()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintInActivityAsPrecondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getActivity_PostconditionCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Activity modelElement = (Activity) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPostconditions()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintInActivityAsPostcondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * (update at each gmf change) add children actions' local conditions
	 *
	 * @generated NOT
	 */
	public List<UMLNodeDescriptor> getActivity_ActivityNodeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Activity modelElement = (Activity) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getNodes().iterator(); it.hasNext();) {
			ActivityNode childElement = (ActivityNode) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (InitialNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FlowFinalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallBehaviorActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallOperationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DecisionNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (MergeNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ForkNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (JoinNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataStoreNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CreateObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadStructuralFeatureActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddStructuralFeatureValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DestroyObjectActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AddVariableValueActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadVariableActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (BroadcastSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SendSignalActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AcceptEventActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ValueSpecificationActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ReadSelfActionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CentralBufferNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		// for (Iterator<?> it = modelElement.getLocalPreconditions().iterator(); it
		// .hasNext();) {
		// Constraint childElement = (Constraint) it.next();
		// String visualID = UMLVisualIDRegistry.getNodeVisualID(view,
		// childElement);
		// if (DurationConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (TimeConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (ConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// }
		// for (Iterator<?> it = modelElement.getLocalPostconditions().iterator(); it
		// .hasNext();) {
		// Constraint childElement = (Constraint) it.next();
		// String visualID = UMLVisualIDRegistry.getNodeVisualID(view,
		// childElement);
		// if (DurationConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (TimeConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// if (ConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
		// result.add(new UMLNodeDescriptor(childElement, visualID));
		// continue;
		// }
		// }
		for (Iterator<?> it = modelElement.getGroups().iterator(); it.hasNext();) {
			ActivityGroup childElement = (ActivityGroup) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConditionalNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExpansionRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LoopNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SequenceNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StructuredActivityNodeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActivityPartitionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterruptibleActivityRegionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments().iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedBehaviors().iterator(); it.hasNext();) {
			Behavior childElement = (Behavior) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedElements().iterator(); it.hasNext();) {
			Element childElement = (Element) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ShapeNamedElementEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * Get the node descriptors corresponding to local pre and post conditions of an action.
	 * These nodes are children of ActivityContentCompartment_7004.
	 *
	 * @param actionElement
	 *            the action containing conditions
	 * @param parentView
	 *            the parent activity content compartment view
	 * @return the list of semantic children (node descriptors)
	 * @generated NOT
	 */
	private List<UMLNodeDescriptor> getActionLocalConditionsDescriptors(Action actionElement, View parentView) {
		/*
		 * Called above by customized code (instead of generated code with errors) :
		 * //add children actions' local conditions
		 * if(childElement instanceof Action) {
		 * result.addAll(getActionLocalConditionsDescriptors((Action)childElement, view));
		 * }
		 */
		List<UMLNodeDescriptor> result = new LinkedList<>();
		for (Constraint childElement : actionElement.getLocalPreconditions()) {
			String visualID = UMLVisualIDRegistry.getNodeVisualID(parentView, childElement);
			if (ConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DurationConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintAsLocalPrecondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Constraint childElement : actionElement.getLocalPostconditions()) {
			String visualID = UMLVisualIDRegistry.getNodeVisualID(parentView, childElement);
			if (ConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DurationConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintAsLocalPostcondEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getContainedLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case ActivityDiagramEditPart.VISUAL_ID:
				return getPackage_ActivityDiagram_ContainedLinks(view);
			case ActivityEditPart.VISUAL_ID:
				return getActivity_Shape_ContainedLinks(view);
			case ParameterEditPart.VISUAL_ID:
				return getParameter_ParameterLabel_ContainedLinks(view);
			case ConstraintInActivityAsPrecondEditPart.VISUAL_ID:
				return getConstraint_PreconditionLabel_ContainedLinks(view);
			case ConstraintInActivityAsPostcondEditPart.VISUAL_ID:
				return getConstraint_PostconditionLabel_ContainedLinks(view);
			case InitialNodeEditPart.VISUAL_ID:
				return getInitialNode_Shape_ContainedLinks(view);
			case ActivityFinalNodeEditPart.VISUAL_ID:
				return getActivityFinalNode_Shape_ContainedLinks(view);
			case FlowFinalNodeEditPart.VISUAL_ID:
				return getFlowFinalNode_Shape_ContainedLinks(view);
			case OpaqueActionEditPart.VISUAL_ID:
				return getOpaqueAction_Shape_ContainedLinks(view);
			case ValuePinInOpaqueActEditPart.VISUAL_ID:
				return getValuePin_OpaqueActionInputShape_ContainedLinks(view);
			case ActionInputPinInOpaqueActEditPart.VISUAL_ID:
				return getActionInputPin_OpaqueActionInputShape_ContainedLinks(view);
			case InputPinInOpaqueActEditPart.VISUAL_ID:
				return getInputPin_OpaqueActionInputShape_ContainedLinks(view);
			case OutputPinInOpaqueActEditPart.VISUAL_ID:
				return getOutputPin_OpaqueActionOutputShape_ContainedLinks(view);
			case CallBehaviorActionEditPart.VISUAL_ID:
				return getCallBehaviorAction_Shape_ContainedLinks(view);
			case ValuePinInCallBeActEditPart.VISUAL_ID:
				return getValuePin_CallBehaviorActionArgumentShape_ContainedLinks(view);
			case ActionInputPinInCallBeActEditPart.VISUAL_ID:
				return getActionInputPin_CallBehaviorActionArgumentShape_ContainedLinks(view);
			case InputPinInCallBeActEditPart.VISUAL_ID:
				return getInputPin_CallBehaviorActionArgumentShape_ContainedLinks(view);
			case OutputPinInCallBeActEditPart.VISUAL_ID:
				return getOutputPin_CallBehaviorActionResultShape_ContainedLinks(view);
			case CallOperationActionEditPart.VISUAL_ID:
				return getCallOperationAction_Shape_ContainedLinks(view);
			case ActionInputPinInCallOpActEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionArgumentShape_ContainedLinks(view);
			case ValuePinInCallOpActEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionArgumentShape_ContainedLinks(view);
			case InputPinInCallOpActEditPart.VISUAL_ID:
				return getInputPin_CallOperationActionArgumentShape_ContainedLinks(view);
			case OutputPinInCallOpActEditPart.VISUAL_ID:
				return getOutputPin_CallOperationActionResultShape_ContainedLinks(view);
			case ValuePinInCallOpActAsTargetEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionTargetShape_ContainedLinks(view);
			case ActionInputPinInCallOpActAsTargetEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionTargetShape_ContainedLinks(view);
			case InputPinInCallOpActAsTargetEditPart.VISUAL_ID:
				return getInputPin_CallOperationActionTargetShape_ContainedLinks(view);
			case DurationConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getDurationConstraint_LocalPreconditionShape_ContainedLinks(view);
			case DurationConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getDurationConstraint_LocalPostconditionShape_ContainedLinks(view);
			case TimeConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getTimeConstraint_LocalPreconditionShape_ContainedLinks(view);
			case TimeConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getTimeConstraint_LocalPostconditionShape_ContainedLinks(view);
			case IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getIntervalConstraint_LocalPreconditionShape_ContainedLinks(view);
			case IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getIntervalConstraint_LocalPostconditionShape_ContainedLinks(view);
			case ConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getConstraint_LocalPreconditionShape_ContainedLinks(view);
			case ConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getConstraint_LocalPostconditionShape_ContainedLinks(view);
			case DecisionNodeEditPart.VISUAL_ID:
				return getDecisionNode_Shape_ContainedLinks(view);
			case MergeNodeEditPart.VISUAL_ID:
				return getMergeNode_Shape_ContainedLinks(view);
			case ForkNodeEditPart.VISUAL_ID:
				return getForkNode_Shape_ContainedLinks(view);
			case JoinNodeEditPart.VISUAL_ID:
				return getJoinNode_Shape_ContainedLinks(view);
			case DataStoreNodeEditPart.VISUAL_ID:
				return getDataStoreNode_Shape_ContainedLinks(view);
			case SendObjectActionEditPart.VISUAL_ID:
				return getSendObjectAction_Shape_ContainedLinks(view);
			case ValuePinInSendObjActAsReqEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionRequestShape_ContainedLinks(view);
			case ActionInputPinInSendObjActAsReqEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionRequestShape_ContainedLinks(view);
			case InputPinInSendObjActAsReqEditPart.VISUAL_ID:
				return getInputPin_SendObjectActionRequestShape_ContainedLinks(view);
			case ValuePinInSendObjActAsTargetEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionTargetShape_ContainedLinks(view);
			case ActionInputPinInSendObjActAsTargetEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionTargetShape_ContainedLinks(view);
			case InputPinInSendObjActAsTargetEditPart.VISUAL_ID:
				return getInputPin_SendObjectActionTargetShape_ContainedLinks(view);
			case SendSignalActionEditPart.VISUAL_ID:
				return getSendSignalAction_Shape_ContainedLinks(view);
			case ActionInputPinInSendSigActEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionArgumentShape_ContainedLinks(view);
			case ValuePinInSendSigActEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionArgumentShape_ContainedLinks(view);
			case InputPinInSendSigActEditPart.VISUAL_ID:
				return getInputPin_SendSignalActionArgumentShape_ContainedLinks(view);
			case ValuePinInSendSigActAsTargetEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionTargetShape_ContainedLinks(view);
			case ActionInputPinInSendSigActAsTargetEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionTargetShape_ContainedLinks(view);
			case InputPinInSendSigActAsTargetEditPart.VISUAL_ID:
				return getInputPin_SendSignalActionTargetShape_ContainedLinks(view);
			case ActivityParameterNodeEditPart.VISUAL_ID:
				return getActivityParameterNode_Shape_ContainedLinks(view);
			case AcceptEventActionEditPart.VISUAL_ID:
				return getAcceptEventAction_Shape_ContainedLinks(view);
			case OutputPinInAcceptEventActionEditPart.VISUAL_ID:
				return getOutputPin_AcceptEventActionResultShape_ContainedLinks(view);
			case ValueSpecificationActionEditPart.VISUAL_ID:
				return getValueSpecificationAction_Shape_ContainedLinks(view);
			case OutputPinInValSpecActEditPart.VISUAL_ID:
				return getOutputPin_ValueSpecificationActionResultShape_ContainedLinks(view);
			case ConditionalNodeEditPart.VISUAL_ID:
				return getConditionalNode_Shape_ContainedLinks(view);
			case ExpansionRegionEditPart.VISUAL_ID:
				return getExpansionRegion_Shape_ContainedLinks(view);
			case ExpansionNodeAsInEditPart.VISUAL_ID:
				return getExpansionNode_InputShape_ContainedLinks(view);
			case ExpansionNodeAsOutEditPart.VISUAL_ID:
				return getExpansionNode_OutputShape_ContainedLinks(view);
			case LoopNodeEditPart.VISUAL_ID:
				return getLoopNode_Shape_ContainedLinks(view);
			case InputPinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return getInputPin_LoopNodeVariableInputShape_ContainedLinks(view);
			case ValuePinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return getValuePin_LoopNodeVariableInputShape_ContainedLinks(view);
			case ActionPinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return getActionInputPin_LoopNodeVariableInputShape_ContainedLinks(view);
			case OutputPinInLoopNodeAsBodyOutputEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeBodyOutputShape_ContainedLinks(view);
			case OutputPinInLoopNodeAsLoopVariableEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeVariableShape_ContainedLinks(view);
			case OutputPinInLoopNodeAsResultEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeResultShape_ContainedLinks(view);
			case SequenceNodeEditPart.VISUAL_ID:
				return getSequenceNode_Shape_ContainedLinks(view);
			case StructuredActivityNodeEditPart.VISUAL_ID:
				return getStructuredActivityNode_Shape_ContainedLinks(view);
			case InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getInputPin_StructuredActivityNodeInputShape_ContainedLinks(view);
			case ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getValuePin_StructuredActivityNodeInputShape_ContainedLinks(view);
			case ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getActionInputPin_StructuredActivityNodeInputShape_ContainedLinks(view);
			case OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getOutputPin_StructuredActivityNodeOutputShape_ContainedLinks(view);
			case ActivityPartitionEditPart.VISUAL_ID:
				return getActivityPartition_Shape_ContainedLinks(view);
			case InterruptibleActivityRegionEditPart.VISUAL_ID:
				return getInterruptibleActivityRegion_Shape_ContainedLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_ContainedLinks(view);
			case ReadSelfActionEditPart.VISUAL_ID:
				return getReadSelfAction_Shape_ContainedLinks(view);
			case ReadSelfActionOutputPinEditPart.VISUAL_ID:
				return getOutputPin_ReadSelfActionResultShape_ContainedLinks(view);
			case ActivityEditPartCN.VISUAL_ID:
				return getActivity_Shape_CN_ContainedLinks(view);
			case CreateObjectActionEditPart.VISUAL_ID:
				return getCreateObjectAction_Shape_ContainedLinks(view);
			case OutputPinInCreateObjectActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_CreateObjectActionResultShape_ContainedLinks(view);
			case ShapeNamedElementEditPart.VISUAL_ID:
				return getNamedElement_DefaultShape_ContainedLinks(view);
			case ReadStructuralFeatureActionEditPart.VISUAL_ID:
				return getReadStructuralFeatureAction_Shape_ContainedLinks(view);
			case InputPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return getInputPin_ReadStructuralFeatureActionObjectShape_ContainedLinks(view);
			case ValuePinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return getValuePin_ReadStructuralFeatureActionObjectShape_ContainedLinks(view);
			case ActionPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ReadStructuralFeatureActionObjectShape_ContainedLinks(view);
			case OutputPinInReadStructuralFeatureAsResultEditPart.VISUAL_ID:
				return getOutputPin_ReadStructuralFeatureActionResultShape_ContainedLinks(view);
			case AddStructuralFeatureValueActionEditPart.VISUAL_ID:
				return getAddStructuralFeatureValueAction_Shape_ContainedLinks(view);
			case InputPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionObjectShape_ContainedLinks(view);
			case InputPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionValueShape_ContainedLinks(view);
			case InputPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionInsertAtShape_ContainedLinks(view);
			case ValuePinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionObjectShape_ContainedLinks(view);
			case ValuePinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionValueShape_ContainedLinks(view);
			case ValuePinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionInsertAtShape_ContainedLinks(view);
			case ActionPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionObjectShape_ContainedLinks(view);
			case ActionPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionValueShape_ContainedLinks(view);
			case ActionPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionInsertAtShape_ContainedLinks(view);
			case OutputPinInAddStructuralFeatureValueActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_AddStructuralFeatureValueActionResultShape_ContainedLinks(view);
			case DestroyObjectActionEditPart.VISUAL_ID:
				return getDestroyObjectAction_Shape_ContainedLinks(view);
			case InputPinInDestroyObjectActionEditPart.VISUAL_ID:
				return getInputPin_DestroyObjectActionTargetShape_ContainedLinks(view);
			case ValuePinInDestroyObjectActionEditPart.VISUAL_ID:
				return getValuePin_DestroyObjectActionTargetShape_ContainedLinks(view);
			case ActionPinInDestroyObjectActionEditPart.VISUAL_ID:
				return getActionInputPin_DestroyObjectActionTargetShape_ContainedLinks(view);
			case ReadVariableActionEditPart.VISUAL_ID:
				return getReadVariableAction_Shape_ContainedLinks(view);
			case OutputPinInReadVariableActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_ReadVariableActionResultShape_ContainedLinks(view);
			case AddVariableValueActionEditPart.VISUAL_ID:
				return getAddVariableValueAction_Shape_ContainedLinks(view);
			case InputPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return getInputPin_AddVariableValueActionInsertAtShape_ContainedLinks(view);
			case InputPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return getInputPin_AddVariableValueActionValueShape_ContainedLinks(view);
			case ValuePinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionInsertAtShape_ContainedLinks(view);
			case ValuePinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionValueShape_ContainedLinks(view);
			case ActionPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionInsertAtShape_ContainedLinks(view);
			case ActionPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionValueShape_ContainedLinks(view);
			case BroadcastSignalActionEditPart.VISUAL_ID:
				return getBroadcastSignalAction_Shape_ContainedLinks(view);
			case InputPinInBroadcastSignalActionEditPart.VISUAL_ID:
				return getInputPin_BroadcastSignalActionArgumentShape_ContainedLinks(view);
			case ValuePinInBroadcastSignalActionEditPart.VISUAL_ID:
				return getValuePin_BroadcastSignalActionArgumentShape_ContainedLinks(view);
			case ActionPinInBroadcastSignalActionEditPart.VISUAL_ID:
				return getActionInputPin_BroadcastSignalActionArgumentShape_ContainedLinks(view);
			case CentralBufferNodeEditPart.VISUAL_ID:
				return getCentralBufferNode_Shape_ContainedLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_Shape_ContainedLinks(view);
			case StartObjectBehavoiurActionEditPart.VISUAL_ID:
				return getStartObjectBehaviorAction_Shape_ContainedLinks(view);
			case OutputPinInStartObjectBehaviorActionEditPart.VISUAL_ID:
				return getOutputPin_StartObjectBehaviorActionResultShape_ContainedLinks(view);
			case InputPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_StartObjectBehaviorActionObjectShape_ContainedLinks(view);
			case ValuePinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionObjectShape_ContainedLinks(view);
			case ActionPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionObjectShape_ContainedLinks(view);
			case InputPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return getInputPin_StartObjectBehaviorActionArgumentShape_ContainedLinks(view);
			case ValuePinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionArgumentShape_ContainedLinks(view);
			case ActionPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionArgumentShape_ContainedLinks(view);
			case TestIdentityActionEditPart.VISUAL_ID:
				return getTestIdentityAction_Shape_ContainedLinks(view);
			case OutputPinInTestIdentityActionEditPart.VISUAL_ID:
				return getOutputPin_TestIdentityActionResultShape_ContainedLinks(view);
			case InputPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return getInputPin_TestIdentityActionFirstShape_ContainedLinks(view);
			case InputPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return getInputPin_TestIdentityActionSecondShape_ContainedLinks(view);
			case ValuePinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionFirstShape_ContainedLinks(view);
			case ValuePinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionSecondShape_ContainedLinks(view);
			case ActionPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionFirstShape_ContainedLinks(view);
			case ActionPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionSecondShape_ContainedLinks(view);
			case ClearStructuralFeatureActionEditPart.VISUAL_ID:
				return getClearStructuralFeatureAction_Shape_ContainedLinks(view);
			case OutputPinInClearStructuralFeatureActionEditPart.VISUAL_ID:
				return getOutputPin_ClearStructuralFeatureActionResultShape_ContainedLinks(view);
			case InputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ClearStructuralFeatureActionObjectShape_ContainedLinks(view);
			case ValuePinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ClearStructuralFeatureActionObjectShape_ContainedLinks(view);
			case ActionInputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ClearStructuralFeatureActionObjectShape_ContainedLinks(view);
			case CreateLinkActionEditPart.VISUAL_ID:
				return getCreateLinkAction_Shape_ContainedLinks(view);
			case InputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_CreateLinkActionInputShape_ContainedLinks(view);
			case ValuePinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_CreateLinkActionInputShape_ContainedLinks(view);
			case ActionInputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkActionInputShape_ContainedLinks(view);
			case ReadLinkActionEditPart.VISUAL_ID:
				return getReadLinkAction_Shape_ContainedLinks(view);
			case OutputPinInReadLinkActionEditPart.VISUAL_ID:
				return getOutputPin_ReadLinkActionResultShape_ContainedLinks(view);
			case InputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_ReadLinkActionInputShape_ContainedLinks(view);
			case ValuePinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_ReadLinkActionInputShape_ContainedLinks(view);
			case ActionInputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_ReadLinkActionInputShape_ContainedLinks(view);
			case DestroyLinkActionEditPart.VISUAL_ID:
				return getDestroyLinkAction_Shape_ContainedLinks(view);
			case InputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_DestroyLinkActionInputShape_ContainedLinks(view);
			case ValuePinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_DestroyLinkActionInputShape_ContainedLinks(view);
			case ActionInputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_DestroyLinkActionInputShape_ContainedLinks(view);
			case ClearAssociationActionEditPart.VISUAL_ID:
				return getClearAssociationAction_Shape_ContainedLinks(view);
			case InputPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ClearAssociationActionObjectShape_ContainedLinks(view);
			case ValuePinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ClearAssociationActionObjectShape_ContainedLinks(view);
			case ActionPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ClearAssociationActionObjectShape_ContainedLinks(view);
			case ReadExtentActionEditPart.VISUAL_ID:
				return getReadExtentAction_Shape_ContainedLinks(view);
			case OutputPinInReadExtentActionEditPart.VISUAL_ID:
				return getOutputPin_ReadExtentActionResultShape_ContainedLinks(view);
			case ReclassifyObjectActionEditPart.VISUAL_ID:
				return getReclassifyObjectAction_Shape_ContainedLinks(view);
			case InputPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ReclassifyObjectActionObjectShape_ContainedLinks(view);
			case ValuePinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ReclassifyObjectActionObjectShape_ContainedLinks(view);
			case ActionPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ReclassifyObjectActionObjectShape_ContainedLinks(view);
			case ReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return getReadIsClassifiedObjectAction_Shape_ContainedLinks(view);
			case OutputPinInReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return getOutputPin_ReadIsClassifiedObjectActionResultShape_ContainedLinks(view);
			case InputPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ReadIsClassifiedObjectActionObjectShape_ContainedLinks(view);
			case ValuePinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ReadIsClassifiedObjectActionObjectShape_ContainedLinks(view);
			case ActionPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ReadIsClassifiedObjectActionObjectShape_ContainedLinks(view);
			case ReduceActionEditPart.VISUAL_ID:
				return getReduceAction_Shape_ContainedLinks(view);
			case OutputPinInReduceActionEditPart.VISUAL_ID:
				return getOutputPin_ReduceActionResultShape_ContainedLinks(view);
			case InputPinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return getInputPin_ReduceActionCollectionShape_ContainedLinks(view);
			case ValuePinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return getValuePin_ReduceActionCollectionShape_ContainedLinks(view);
			case ActionPinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return getActionInputPin_ReduceActionCollectionShape_ContainedLinks(view);
			case StartClassifierBehaviorActionEditPart.VISUAL_ID:
				return getStartClassifierBehaviorAction_Shape_ContainedLinks(view);
			case InputPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_StartClassifierBehaviorActionObjectShape_ContainedLinks(view);
			case ValuePinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_StartClassifierBehaviorActionObjectShape_ContainedLinks(view);
			case ActionPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_StartClassifierBehaviorActionObjectShape_ContainedLinks(view);
			case CreateLinkObjectActionEditPart.VISUAL_ID:
				return getCreateLinkObjectAction_Shape_ContainedLinks(view);
			case InputPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_CreateLinkObjectActionInputShape_ContainedLinks(view);
			case ValuePinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_CreateLinkObjectActionInputShape_ContainedLinks(view);
			case ActionPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkObjectActionInputShape_ContainedLinks(view);
			case OutputPinInCreateLinkObjectActionEditPart.VISUAL_ID:
				return getOutputPin_CreateLinkObjectActionResultShape_ContainedLinks(view);
			case UnmarshallActionEditPart.VISUAL_ID:
				return getUnmarshallAction_Shape_ContainedLinks(view);
			case InputPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_UnmarshallActionObjectShape_ContainedLinks(view);
			case ValuePinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_UnmarshallActionObjectShape_ContainedLinks(view);
			case ActionPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_UnmarshallActionObjectShape_ContainedLinks(view);
			case OutputPinInUnmarshallActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_UnmarshallActionResultShape_ContainedLinks(view);
			case ObjectFlowEditPart.VISUAL_ID:
				return getObjectFlow_Edge_ContainedLinks(view);
			case ControlFlowEditPart.VISUAL_ID:
				return getControlFlow_Edge_ContainedLinks(view);
			case ExceptionHandlerEditPart.VISUAL_ID:
				return getExceptionHandler_Edge_ContainedLinks(view);
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
			case ActivityEditPart.VISUAL_ID:
				return getActivity_Shape_IncomingLinks(view);
			case ParameterEditPart.VISUAL_ID:
				return getParameter_ParameterLabel_IncomingLinks(view);
			case ConstraintInActivityAsPrecondEditPart.VISUAL_ID:
				return getConstraint_PreconditionLabel_IncomingLinks(view);
			case ConstraintInActivityAsPostcondEditPart.VISUAL_ID:
				return getConstraint_PostconditionLabel_IncomingLinks(view);
			case InitialNodeEditPart.VISUAL_ID:
				return getInitialNode_Shape_IncomingLinks(view);
			case ActivityFinalNodeEditPart.VISUAL_ID:
				return getActivityFinalNode_Shape_IncomingLinks(view);
			case FlowFinalNodeEditPart.VISUAL_ID:
				return getFlowFinalNode_Shape_IncomingLinks(view);
			case OpaqueActionEditPart.VISUAL_ID:
				return getOpaqueAction_Shape_IncomingLinks(view);
			case ValuePinInOpaqueActEditPart.VISUAL_ID:
				return getValuePin_OpaqueActionInputShape_IncomingLinks(view);
			case ActionInputPinInOpaqueActEditPart.VISUAL_ID:
				return getActionInputPin_OpaqueActionInputShape_IncomingLinks(view);
			case InputPinInOpaqueActEditPart.VISUAL_ID:
				return getInputPin_OpaqueActionInputShape_IncomingLinks(view);
			case OutputPinInOpaqueActEditPart.VISUAL_ID:
				return getOutputPin_OpaqueActionOutputShape_IncomingLinks(view);
			case CallBehaviorActionEditPart.VISUAL_ID:
				return getCallBehaviorAction_Shape_IncomingLinks(view);
			case ValuePinInCallBeActEditPart.VISUAL_ID:
				return getValuePin_CallBehaviorActionArgumentShape_IncomingLinks(view);
			case ActionInputPinInCallBeActEditPart.VISUAL_ID:
				return getActionInputPin_CallBehaviorActionArgumentShape_IncomingLinks(view);
			case InputPinInCallBeActEditPart.VISUAL_ID:
				return getInputPin_CallBehaviorActionArgumentShape_IncomingLinks(view);
			case OutputPinInCallBeActEditPart.VISUAL_ID:
				return getOutputPin_CallBehaviorActionResultShape_IncomingLinks(view);
			case CallOperationActionEditPart.VISUAL_ID:
				return getCallOperationAction_Shape_IncomingLinks(view);
			case ActionInputPinInCallOpActEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionArgumentShape_IncomingLinks(view);
			case ValuePinInCallOpActEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionArgumentShape_IncomingLinks(view);
			case InputPinInCallOpActEditPart.VISUAL_ID:
				return getInputPin_CallOperationActionArgumentShape_IncomingLinks(view);
			case OutputPinInCallOpActEditPart.VISUAL_ID:
				return getOutputPin_CallOperationActionResultShape_IncomingLinks(view);
			case ValuePinInCallOpActAsTargetEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionTargetShape_IncomingLinks(view);
			case ActionInputPinInCallOpActAsTargetEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionTargetShape_IncomingLinks(view);
			case InputPinInCallOpActAsTargetEditPart.VISUAL_ID:
				return getInputPin_CallOperationActionTargetShape_IncomingLinks(view);
			case DurationConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getDurationConstraint_LocalPreconditionShape_IncomingLinks(view);
			case DurationConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getDurationConstraint_LocalPostconditionShape_IncomingLinks(view);
			case TimeConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getTimeConstraint_LocalPreconditionShape_IncomingLinks(view);
			case TimeConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getTimeConstraint_LocalPostconditionShape_IncomingLinks(view);
			case IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getIntervalConstraint_LocalPreconditionShape_IncomingLinks(view);
			case IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getIntervalConstraint_LocalPostconditionShape_IncomingLinks(view);
			case ConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getConstraint_LocalPreconditionShape_IncomingLinks(view);
			case ConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getConstraint_LocalPostconditionShape_IncomingLinks(view);
			case DecisionNodeEditPart.VISUAL_ID:
				return getDecisionNode_Shape_IncomingLinks(view);
			case MergeNodeEditPart.VISUAL_ID:
				return getMergeNode_Shape_IncomingLinks(view);
			case ForkNodeEditPart.VISUAL_ID:
				return getForkNode_Shape_IncomingLinks(view);
			case JoinNodeEditPart.VISUAL_ID:
				return getJoinNode_Shape_IncomingLinks(view);
			case DataStoreNodeEditPart.VISUAL_ID:
				return getDataStoreNode_Shape_IncomingLinks(view);
			case SendObjectActionEditPart.VISUAL_ID:
				return getSendObjectAction_Shape_IncomingLinks(view);
			case ValuePinInSendObjActAsReqEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionRequestShape_IncomingLinks(view);
			case ActionInputPinInSendObjActAsReqEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionRequestShape_IncomingLinks(view);
			case InputPinInSendObjActAsReqEditPart.VISUAL_ID:
				return getInputPin_SendObjectActionRequestShape_IncomingLinks(view);
			case ValuePinInSendObjActAsTargetEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionTargetShape_IncomingLinks(view);
			case ActionInputPinInSendObjActAsTargetEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionTargetShape_IncomingLinks(view);
			case InputPinInSendObjActAsTargetEditPart.VISUAL_ID:
				return getInputPin_SendObjectActionTargetShape_IncomingLinks(view);
			case SendSignalActionEditPart.VISUAL_ID:
				return getSendSignalAction_Shape_IncomingLinks(view);
			case ActionInputPinInSendSigActEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionArgumentShape_IncomingLinks(view);
			case ValuePinInSendSigActEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionArgumentShape_IncomingLinks(view);
			case InputPinInSendSigActEditPart.VISUAL_ID:
				return getInputPin_SendSignalActionArgumentShape_IncomingLinks(view);
			case ValuePinInSendSigActAsTargetEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionTargetShape_IncomingLinks(view);
			case ActionInputPinInSendSigActAsTargetEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionTargetShape_IncomingLinks(view);
			case InputPinInSendSigActAsTargetEditPart.VISUAL_ID:
				return getInputPin_SendSignalActionTargetShape_IncomingLinks(view);
			case ActivityParameterNodeEditPart.VISUAL_ID:
				return getActivityParameterNode_Shape_IncomingLinks(view);
			case AcceptEventActionEditPart.VISUAL_ID:
				return getAcceptEventAction_Shape_IncomingLinks(view);
			case OutputPinInAcceptEventActionEditPart.VISUAL_ID:
				return getOutputPin_AcceptEventActionResultShape_IncomingLinks(view);
			case ValueSpecificationActionEditPart.VISUAL_ID:
				return getValueSpecificationAction_Shape_IncomingLinks(view);
			case OutputPinInValSpecActEditPart.VISUAL_ID:
				return getOutputPin_ValueSpecificationActionResultShape_IncomingLinks(view);
			case ConditionalNodeEditPart.VISUAL_ID:
				return getConditionalNode_Shape_IncomingLinks(view);
			case ExpansionRegionEditPart.VISUAL_ID:
				return getExpansionRegion_Shape_IncomingLinks(view);
			case ExpansionNodeAsInEditPart.VISUAL_ID:
				return getExpansionNode_InputShape_IncomingLinks(view);
			case ExpansionNodeAsOutEditPart.VISUAL_ID:
				return getExpansionNode_OutputShape_IncomingLinks(view);
			case LoopNodeEditPart.VISUAL_ID:
				return getLoopNode_Shape_IncomingLinks(view);
			case InputPinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return getInputPin_LoopNodeVariableInputShape_IncomingLinks(view);
			case ValuePinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return getValuePin_LoopNodeVariableInputShape_IncomingLinks(view);
			case ActionPinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return getActionInputPin_LoopNodeVariableInputShape_IncomingLinks(view);
			case OutputPinInLoopNodeAsBodyOutputEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeBodyOutputShape_IncomingLinks(view);
			case OutputPinInLoopNodeAsLoopVariableEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeVariableShape_IncomingLinks(view);
			case OutputPinInLoopNodeAsResultEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeResultShape_IncomingLinks(view);
			case SequenceNodeEditPart.VISUAL_ID:
				return getSequenceNode_Shape_IncomingLinks(view);
			case StructuredActivityNodeEditPart.VISUAL_ID:
				return getStructuredActivityNode_Shape_IncomingLinks(view);
			case InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getInputPin_StructuredActivityNodeInputShape_IncomingLinks(view);
			case ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getValuePin_StructuredActivityNodeInputShape_IncomingLinks(view);
			case ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getActionInputPin_StructuredActivityNodeInputShape_IncomingLinks(view);
			case OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getOutputPin_StructuredActivityNodeOutputShape_IncomingLinks(view);
			case ActivityPartitionEditPart.VISUAL_ID:
				return getActivityPartition_Shape_IncomingLinks(view);
			case InterruptibleActivityRegionEditPart.VISUAL_ID:
				return getInterruptibleActivityRegion_Shape_IncomingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_IncomingLinks(view);
			case ReadSelfActionEditPart.VISUAL_ID:
				return getReadSelfAction_Shape_IncomingLinks(view);
			case ReadSelfActionOutputPinEditPart.VISUAL_ID:
				return getOutputPin_ReadSelfActionResultShape_IncomingLinks(view);
			case ActivityEditPartCN.VISUAL_ID:
				return getActivity_Shape_CN_IncomingLinks(view);
			case CreateObjectActionEditPart.VISUAL_ID:
				return getCreateObjectAction_Shape_IncomingLinks(view);
			case OutputPinInCreateObjectActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_CreateObjectActionResultShape_IncomingLinks(view);
			case ShapeNamedElementEditPart.VISUAL_ID:
				return getNamedElement_DefaultShape_IncomingLinks(view);
			case ReadStructuralFeatureActionEditPart.VISUAL_ID:
				return getReadStructuralFeatureAction_Shape_IncomingLinks(view);
			case InputPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return getInputPin_ReadStructuralFeatureActionObjectShape_IncomingLinks(view);
			case ValuePinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return getValuePin_ReadStructuralFeatureActionObjectShape_IncomingLinks(view);
			case ActionPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ReadStructuralFeatureActionObjectShape_IncomingLinks(view);
			case OutputPinInReadStructuralFeatureAsResultEditPart.VISUAL_ID:
				return getOutputPin_ReadStructuralFeatureActionResultShape_IncomingLinks(view);
			case AddStructuralFeatureValueActionEditPart.VISUAL_ID:
				return getAddStructuralFeatureValueAction_Shape_IncomingLinks(view);
			case InputPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionObjectShape_IncomingLinks(view);
			case InputPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionValueShape_IncomingLinks(view);
			case InputPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionInsertAtShape_IncomingLinks(view);
			case ValuePinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionObjectShape_IncomingLinks(view);
			case ValuePinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionValueShape_IncomingLinks(view);
			case ValuePinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionInsertAtShape_IncomingLinks(view);
			case ActionPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionObjectShape_IncomingLinks(view);
			case ActionPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionValueShape_IncomingLinks(view);
			case ActionPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionInsertAtShape_IncomingLinks(view);
			case OutputPinInAddStructuralFeatureValueActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_AddStructuralFeatureValueActionResultShape_IncomingLinks(view);
			case DestroyObjectActionEditPart.VISUAL_ID:
				return getDestroyObjectAction_Shape_IncomingLinks(view);
			case InputPinInDestroyObjectActionEditPart.VISUAL_ID:
				return getInputPin_DestroyObjectActionTargetShape_IncomingLinks(view);
			case ValuePinInDestroyObjectActionEditPart.VISUAL_ID:
				return getValuePin_DestroyObjectActionTargetShape_IncomingLinks(view);
			case ActionPinInDestroyObjectActionEditPart.VISUAL_ID:
				return getActionInputPin_DestroyObjectActionTargetShape_IncomingLinks(view);
			case ReadVariableActionEditPart.VISUAL_ID:
				return getReadVariableAction_Shape_IncomingLinks(view);
			case OutputPinInReadVariableActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_ReadVariableActionResultShape_IncomingLinks(view);
			case AddVariableValueActionEditPart.VISUAL_ID:
				return getAddVariableValueAction_Shape_IncomingLinks(view);
			case InputPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return getInputPin_AddVariableValueActionInsertAtShape_IncomingLinks(view);
			case InputPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return getInputPin_AddVariableValueActionValueShape_IncomingLinks(view);
			case ValuePinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionInsertAtShape_IncomingLinks(view);
			case ValuePinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionValueShape_IncomingLinks(view);
			case ActionPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionInsertAtShape_IncomingLinks(view);
			case ActionPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionValueShape_IncomingLinks(view);
			case BroadcastSignalActionEditPart.VISUAL_ID:
				return getBroadcastSignalAction_Shape_IncomingLinks(view);
			case InputPinInBroadcastSignalActionEditPart.VISUAL_ID:
				return getInputPin_BroadcastSignalActionArgumentShape_IncomingLinks(view);
			case ValuePinInBroadcastSignalActionEditPart.VISUAL_ID:
				return getValuePin_BroadcastSignalActionArgumentShape_IncomingLinks(view);
			case ActionPinInBroadcastSignalActionEditPart.VISUAL_ID:
				return getActionInputPin_BroadcastSignalActionArgumentShape_IncomingLinks(view);
			case CentralBufferNodeEditPart.VISUAL_ID:
				return getCentralBufferNode_Shape_IncomingLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_Shape_IncomingLinks(view);
			case StartObjectBehavoiurActionEditPart.VISUAL_ID:
				return getStartObjectBehaviorAction_Shape_IncomingLinks(view);
			case OutputPinInStartObjectBehaviorActionEditPart.VISUAL_ID:
				return getOutputPin_StartObjectBehaviorActionResultShape_IncomingLinks(view);
			case InputPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_StartObjectBehaviorActionObjectShape_IncomingLinks(view);
			case ValuePinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionObjectShape_IncomingLinks(view);
			case ActionPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionObjectShape_IncomingLinks(view);
			case InputPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return getInputPin_StartObjectBehaviorActionArgumentShape_IncomingLinks(view);
			case ValuePinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionArgumentShape_IncomingLinks(view);
			case ActionPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionArgumentShape_IncomingLinks(view);
			case TestIdentityActionEditPart.VISUAL_ID:
				return getTestIdentityAction_Shape_IncomingLinks(view);
			case OutputPinInTestIdentityActionEditPart.VISUAL_ID:
				return getOutputPin_TestIdentityActionResultShape_IncomingLinks(view);
			case InputPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return getInputPin_TestIdentityActionFirstShape_IncomingLinks(view);
			case InputPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return getInputPin_TestIdentityActionSecondShape_IncomingLinks(view);
			case ValuePinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionFirstShape_IncomingLinks(view);
			case ValuePinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionSecondShape_IncomingLinks(view);
			case ActionPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionFirstShape_IncomingLinks(view);
			case ActionPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionSecondShape_IncomingLinks(view);
			case ClearStructuralFeatureActionEditPart.VISUAL_ID:
				return getClearStructuralFeatureAction_Shape_IncomingLinks(view);
			case OutputPinInClearStructuralFeatureActionEditPart.VISUAL_ID:
				return getOutputPin_ClearStructuralFeatureActionResultShape_IncomingLinks(view);
			case InputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ClearStructuralFeatureActionObjectShape_IncomingLinks(view);
			case ValuePinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ClearStructuralFeatureActionObjectShape_IncomingLinks(view);
			case ActionInputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ClearStructuralFeatureActionObjectShape_IncomingLinks(view);
			case CreateLinkActionEditPart.VISUAL_ID:
				return getCreateLinkAction_Shape_IncomingLinks(view);
			case InputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_CreateLinkActionInputShape_IncomingLinks(view);
			case ValuePinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_CreateLinkActionInputShape_IncomingLinks(view);
			case ActionInputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkActionInputShape_IncomingLinks(view);
			case ReadLinkActionEditPart.VISUAL_ID:
				return getReadLinkAction_Shape_IncomingLinks(view);
			case OutputPinInReadLinkActionEditPart.VISUAL_ID:
				return getOutputPin_ReadLinkActionResultShape_IncomingLinks(view);
			case InputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_ReadLinkActionInputShape_IncomingLinks(view);
			case ValuePinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_ReadLinkActionInputShape_IncomingLinks(view);
			case ActionInputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_ReadLinkActionInputShape_IncomingLinks(view);
			case DestroyLinkActionEditPart.VISUAL_ID:
				return getDestroyLinkAction_Shape_IncomingLinks(view);
			case InputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_DestroyLinkActionInputShape_IncomingLinks(view);
			case ValuePinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_DestroyLinkActionInputShape_IncomingLinks(view);
			case ActionInputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_DestroyLinkActionInputShape_IncomingLinks(view);
			case ClearAssociationActionEditPart.VISUAL_ID:
				return getClearAssociationAction_Shape_IncomingLinks(view);
			case InputPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ClearAssociationActionObjectShape_IncomingLinks(view);
			case ValuePinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ClearAssociationActionObjectShape_IncomingLinks(view);
			case ActionPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ClearAssociationActionObjectShape_IncomingLinks(view);
			case ReadExtentActionEditPart.VISUAL_ID:
				return getReadExtentAction_Shape_IncomingLinks(view);
			case OutputPinInReadExtentActionEditPart.VISUAL_ID:
				return getOutputPin_ReadExtentActionResultShape_IncomingLinks(view);
			case ReclassifyObjectActionEditPart.VISUAL_ID:
				return getReclassifyObjectAction_Shape_IncomingLinks(view);
			case InputPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ReclassifyObjectActionObjectShape_IncomingLinks(view);
			case ValuePinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ReclassifyObjectActionObjectShape_IncomingLinks(view);
			case ActionPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ReclassifyObjectActionObjectShape_IncomingLinks(view);
			case ReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return getReadIsClassifiedObjectAction_Shape_IncomingLinks(view);
			case OutputPinInReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return getOutputPin_ReadIsClassifiedObjectActionResultShape_IncomingLinks(view);
			case InputPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ReadIsClassifiedObjectActionObjectShape_IncomingLinks(view);
			case ValuePinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ReadIsClassifiedObjectActionObjectShape_IncomingLinks(view);
			case ActionPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ReadIsClassifiedObjectActionObjectShape_IncomingLinks(view);
			case ReduceActionEditPart.VISUAL_ID:
				return getReduceAction_Shape_IncomingLinks(view);
			case OutputPinInReduceActionEditPart.VISUAL_ID:
				return getOutputPin_ReduceActionResultShape_IncomingLinks(view);
			case InputPinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return getInputPin_ReduceActionCollectionShape_IncomingLinks(view);
			case ValuePinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return getValuePin_ReduceActionCollectionShape_IncomingLinks(view);
			case ActionPinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return getActionInputPin_ReduceActionCollectionShape_IncomingLinks(view);
			case StartClassifierBehaviorActionEditPart.VISUAL_ID:
				return getStartClassifierBehaviorAction_Shape_IncomingLinks(view);
			case InputPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_StartClassifierBehaviorActionObjectShape_IncomingLinks(view);
			case ValuePinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_StartClassifierBehaviorActionObjectShape_IncomingLinks(view);
			case ActionPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_StartClassifierBehaviorActionObjectShape_IncomingLinks(view);
			case CreateLinkObjectActionEditPart.VISUAL_ID:
				return getCreateLinkObjectAction_Shape_IncomingLinks(view);
			case InputPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_CreateLinkObjectActionInputShape_IncomingLinks(view);
			case ValuePinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_CreateLinkObjectActionInputShape_IncomingLinks(view);
			case ActionPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkObjectActionInputShape_IncomingLinks(view);
			case OutputPinInCreateLinkObjectActionEditPart.VISUAL_ID:
				return getOutputPin_CreateLinkObjectActionResultShape_IncomingLinks(view);
			case UnmarshallActionEditPart.VISUAL_ID:
				return getUnmarshallAction_Shape_IncomingLinks(view);
			case InputPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_UnmarshallActionObjectShape_IncomingLinks(view);
			case ValuePinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_UnmarshallActionObjectShape_IncomingLinks(view);
			case ActionPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_UnmarshallActionObjectShape_IncomingLinks(view);
			case OutputPinInUnmarshallActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_UnmarshallActionResultShape_IncomingLinks(view);
			case ObjectFlowEditPart.VISUAL_ID:
				return getObjectFlow_Edge_IncomingLinks(view);
			case ControlFlowEditPart.VISUAL_ID:
				return getControlFlow_Edge_IncomingLinks(view);
			case ExceptionHandlerEditPart.VISUAL_ID:
				return getExceptionHandler_Edge_IncomingLinks(view);
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
			case ActivityEditPart.VISUAL_ID:
				return getActivity_Shape_OutgoingLinks(view);
			case ParameterEditPart.VISUAL_ID:
				return getParameter_ParameterLabel_OutgoingLinks(view);
			case ConstraintInActivityAsPrecondEditPart.VISUAL_ID:
				return getConstraint_PreconditionLabel_OutgoingLinks(view);
			case ConstraintInActivityAsPostcondEditPart.VISUAL_ID:
				return getConstraint_PostconditionLabel_OutgoingLinks(view);
			case InitialNodeEditPart.VISUAL_ID:
				return getInitialNode_Shape_OutgoingLinks(view);
			case ActivityFinalNodeEditPart.VISUAL_ID:
				return getActivityFinalNode_Shape_OutgoingLinks(view);
			case FlowFinalNodeEditPart.VISUAL_ID:
				return getFlowFinalNode_Shape_OutgoingLinks(view);
			case OpaqueActionEditPart.VISUAL_ID:
				return getOpaqueAction_Shape_OutgoingLinks(view);
			case ValuePinInOpaqueActEditPart.VISUAL_ID:
				return getValuePin_OpaqueActionInputShape_OutgoingLinks(view);
			case ActionInputPinInOpaqueActEditPart.VISUAL_ID:
				return getActionInputPin_OpaqueActionInputShape_OutgoingLinks(view);
			case InputPinInOpaqueActEditPart.VISUAL_ID:
				return getInputPin_OpaqueActionInputShape_OutgoingLinks(view);
			case OutputPinInOpaqueActEditPart.VISUAL_ID:
				return getOutputPin_OpaqueActionOutputShape_OutgoingLinks(view);
			case CallBehaviorActionEditPart.VISUAL_ID:
				return getCallBehaviorAction_Shape_OutgoingLinks(view);
			case ValuePinInCallBeActEditPart.VISUAL_ID:
				return getValuePin_CallBehaviorActionArgumentShape_OutgoingLinks(view);
			case ActionInputPinInCallBeActEditPart.VISUAL_ID:
				return getActionInputPin_CallBehaviorActionArgumentShape_OutgoingLinks(view);
			case InputPinInCallBeActEditPart.VISUAL_ID:
				return getInputPin_CallBehaviorActionArgumentShape_OutgoingLinks(view);
			case OutputPinInCallBeActEditPart.VISUAL_ID:
				return getOutputPin_CallBehaviorActionResultShape_OutgoingLinks(view);
			case CallOperationActionEditPart.VISUAL_ID:
				return getCallOperationAction_Shape_OutgoingLinks(view);
			case ActionInputPinInCallOpActEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionArgumentShape_OutgoingLinks(view);
			case ValuePinInCallOpActEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionArgumentShape_OutgoingLinks(view);
			case InputPinInCallOpActEditPart.VISUAL_ID:
				return getInputPin_CallOperationActionArgumentShape_OutgoingLinks(view);
			case OutputPinInCallOpActEditPart.VISUAL_ID:
				return getOutputPin_CallOperationActionResultShape_OutgoingLinks(view);
			case ValuePinInCallOpActAsTargetEditPart.VISUAL_ID:
				return getValuePin_CallOperationActionTargetShape_OutgoingLinks(view);
			case ActionInputPinInCallOpActAsTargetEditPart.VISUAL_ID:
				return getActionInputPin_CallOperationActionTargetShape_OutgoingLinks(view);
			case InputPinInCallOpActAsTargetEditPart.VISUAL_ID:
				return getInputPin_CallOperationActionTargetShape_OutgoingLinks(view);
			case DurationConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getDurationConstraint_LocalPreconditionShape_OutgoingLinks(view);
			case DurationConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getDurationConstraint_LocalPostconditionShape_OutgoingLinks(view);
			case TimeConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getTimeConstraint_LocalPreconditionShape_OutgoingLinks(view);
			case TimeConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getTimeConstraint_LocalPostconditionShape_OutgoingLinks(view);
			case IntervalConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getIntervalConstraint_LocalPreconditionShape_OutgoingLinks(view);
			case IntervalConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getIntervalConstraint_LocalPostconditionShape_OutgoingLinks(view);
			case ConstraintAsLocalPrecondEditPart.VISUAL_ID:
				return getConstraint_LocalPreconditionShape_OutgoingLinks(view);
			case ConstraintAsLocalPostcondEditPart.VISUAL_ID:
				return getConstraint_LocalPostconditionShape_OutgoingLinks(view);
			case DecisionNodeEditPart.VISUAL_ID:
				return getDecisionNode_Shape_OutgoingLinks(view);
			case MergeNodeEditPart.VISUAL_ID:
				return getMergeNode_Shape_OutgoingLinks(view);
			case ForkNodeEditPart.VISUAL_ID:
				return getForkNode_Shape_OutgoingLinks(view);
			case JoinNodeEditPart.VISUAL_ID:
				return getJoinNode_Shape_OutgoingLinks(view);
			case DataStoreNodeEditPart.VISUAL_ID:
				return getDataStoreNode_Shape_OutgoingLinks(view);
			case SendObjectActionEditPart.VISUAL_ID:
				return getSendObjectAction_Shape_OutgoingLinks(view);
			case ValuePinInSendObjActAsReqEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionRequestShape_OutgoingLinks(view);
			case ActionInputPinInSendObjActAsReqEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionRequestShape_OutgoingLinks(view);
			case InputPinInSendObjActAsReqEditPart.VISUAL_ID:
				return getInputPin_SendObjectActionRequestShape_OutgoingLinks(view);
			case ValuePinInSendObjActAsTargetEditPart.VISUAL_ID:
				return getValuePin_SendObjectActionTargetShape_OutgoingLinks(view);
			case ActionInputPinInSendObjActAsTargetEditPart.VISUAL_ID:
				return getActionInputPin_SendObjectActionTargetShape_OutgoingLinks(view);
			case InputPinInSendObjActAsTargetEditPart.VISUAL_ID:
				return getInputPin_SendObjectActionTargetShape_OutgoingLinks(view);
			case SendSignalActionEditPart.VISUAL_ID:
				return getSendSignalAction_Shape_OutgoingLinks(view);
			case ActionInputPinInSendSigActEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionArgumentShape_OutgoingLinks(view);
			case ValuePinInSendSigActEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionArgumentShape_OutgoingLinks(view);
			case InputPinInSendSigActEditPart.VISUAL_ID:
				return getInputPin_SendSignalActionArgumentShape_OutgoingLinks(view);
			case ValuePinInSendSigActAsTargetEditPart.VISUAL_ID:
				return getValuePin_SendSignalActionTargetShape_OutgoingLinks(view);
			case ActionInputPinInSendSigActAsTargetEditPart.VISUAL_ID:
				return getActionInputPin_SendSignalActionTargetShape_OutgoingLinks(view);
			case InputPinInSendSigActAsTargetEditPart.VISUAL_ID:
				return getInputPin_SendSignalActionTargetShape_OutgoingLinks(view);
			case ActivityParameterNodeEditPart.VISUAL_ID:
				return getActivityParameterNode_Shape_OutgoingLinks(view);
			case AcceptEventActionEditPart.VISUAL_ID:
				return getAcceptEventAction_Shape_OutgoingLinks(view);
			case OutputPinInAcceptEventActionEditPart.VISUAL_ID:
				return getOutputPin_AcceptEventActionResultShape_OutgoingLinks(view);
			case ValueSpecificationActionEditPart.VISUAL_ID:
				return getValueSpecificationAction_Shape_OutgoingLinks(view);
			case OutputPinInValSpecActEditPart.VISUAL_ID:
				return getOutputPin_ValueSpecificationActionResultShape_OutgoingLinks(view);
			case ConditionalNodeEditPart.VISUAL_ID:
				return getConditionalNode_Shape_OutgoingLinks(view);
			case ExpansionRegionEditPart.VISUAL_ID:
				return getExpansionRegion_Shape_OutgoingLinks(view);
			case ExpansionNodeAsInEditPart.VISUAL_ID:
				return getExpansionNode_InputShape_OutgoingLinks(view);
			case ExpansionNodeAsOutEditPart.VISUAL_ID:
				return getExpansionNode_OutputShape_OutgoingLinks(view);
			case LoopNodeEditPart.VISUAL_ID:
				return getLoopNode_Shape_OutgoingLinks(view);
			case InputPinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return getInputPin_LoopNodeVariableInputShape_OutgoingLinks(view);
			case ValuePinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return getValuePin_LoopNodeVariableInputShape_OutgoingLinks(view);
			case ActionPinInLoopNodeAsVariableEditPart.VISUAL_ID:
				return getActionInputPin_LoopNodeVariableInputShape_OutgoingLinks(view);
			case OutputPinInLoopNodeAsBodyOutputEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeBodyOutputShape_OutgoingLinks(view);
			case OutputPinInLoopNodeAsLoopVariableEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeVariableShape_OutgoingLinks(view);
			case OutputPinInLoopNodeAsResultEditPart.VISUAL_ID:
				return getOutputPin_LoopNodeResultShape_OutgoingLinks(view);
			case SequenceNodeEditPart.VISUAL_ID:
				return getSequenceNode_Shape_OutgoingLinks(view);
			case StructuredActivityNodeEditPart.VISUAL_ID:
				return getStructuredActivityNode_Shape_OutgoingLinks(view);
			case InputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getInputPin_StructuredActivityNodeInputShape_OutgoingLinks(view);
			case ValuePinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getValuePin_StructuredActivityNodeInputShape_OutgoingLinks(view);
			case ActionPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getActionInputPin_StructuredActivityNodeInputShape_OutgoingLinks(view);
			case OutputPinInStructuredActivityNodeAsStructuredNodeInputsEditPart.VISUAL_ID:
				return getOutputPin_StructuredActivityNodeOutputShape_OutgoingLinks(view);
			case ActivityPartitionEditPart.VISUAL_ID:
				return getActivityPartition_Shape_OutgoingLinks(view);
			case InterruptibleActivityRegionEditPart.VISUAL_ID:
				return getInterruptibleActivityRegion_Shape_OutgoingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_OutgoingLinks(view);
			case ReadSelfActionEditPart.VISUAL_ID:
				return getReadSelfAction_Shape_OutgoingLinks(view);
			case ReadSelfActionOutputPinEditPart.VISUAL_ID:
				return getOutputPin_ReadSelfActionResultShape_OutgoingLinks(view);
			case ActivityEditPartCN.VISUAL_ID:
				return getActivity_Shape_CN_OutgoingLinks(view);
			case CreateObjectActionEditPart.VISUAL_ID:
				return getCreateObjectAction_Shape_OutgoingLinks(view);
			case OutputPinInCreateObjectActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_CreateObjectActionResultShape_OutgoingLinks(view);
			case ShapeNamedElementEditPart.VISUAL_ID:
				return getNamedElement_DefaultShape_OutgoingLinks(view);
			case ReadStructuralFeatureActionEditPart.VISUAL_ID:
				return getReadStructuralFeatureAction_Shape_OutgoingLinks(view);
			case InputPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return getInputPin_ReadStructuralFeatureActionObjectShape_OutgoingLinks(view);
			case ValuePinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return getValuePin_ReadStructuralFeatureActionObjectShape_OutgoingLinks(view);
			case ActionPinInReadStructuralFeatureAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ReadStructuralFeatureActionObjectShape_OutgoingLinks(view);
			case OutputPinInReadStructuralFeatureAsResultEditPart.VISUAL_ID:
				return getOutputPin_ReadStructuralFeatureActionResultShape_OutgoingLinks(view);
			case AddStructuralFeatureValueActionEditPart.VISUAL_ID:
				return getAddStructuralFeatureValueAction_Shape_OutgoingLinks(view);
			case InputPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionObjectShape_OutgoingLinks(view);
			case InputPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionValueShape_OutgoingLinks(view);
			case InputPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return getInputPin_AddStructuralFeatureValueActionInsertAtShape_OutgoingLinks(view);
			case ValuePinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionObjectShape_OutgoingLinks(view);
			case ValuePinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionValueShape_OutgoingLinks(view);
			case ValuePinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return getValuePin_AddStructuralFeatureValueActionInsertAtShape_OutgoingLinks(view);
			case ActionPinInAddStructuralFeatureValueActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionObjectShape_OutgoingLinks(view);
			case ActionPinInAddStructuralFeatureValueActionAsValueEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionValueShape_OutgoingLinks(view);
			case ActionPinInAddStructuralFeatureValueActionAsInserAtEditPart.VISUAL_ID:
				return getActionInputPin_AddStructuralFeatureValueActionInsertAtShape_OutgoingLinks(view);
			case OutputPinInAddStructuralFeatureValueActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_AddStructuralFeatureValueActionResultShape_OutgoingLinks(view);
			case DestroyObjectActionEditPart.VISUAL_ID:
				return getDestroyObjectAction_Shape_OutgoingLinks(view);
			case InputPinInDestroyObjectActionEditPart.VISUAL_ID:
				return getInputPin_DestroyObjectActionTargetShape_OutgoingLinks(view);
			case ValuePinInDestroyObjectActionEditPart.VISUAL_ID:
				return getValuePin_DestroyObjectActionTargetShape_OutgoingLinks(view);
			case ActionPinInDestroyObjectActionEditPart.VISUAL_ID:
				return getActionInputPin_DestroyObjectActionTargetShape_OutgoingLinks(view);
			case ReadVariableActionEditPart.VISUAL_ID:
				return getReadVariableAction_Shape_OutgoingLinks(view);
			case OutputPinInReadVariableActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_ReadVariableActionResultShape_OutgoingLinks(view);
			case AddVariableValueActionEditPart.VISUAL_ID:
				return getAddVariableValueAction_Shape_OutgoingLinks(view);
			case InputPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return getInputPin_AddVariableValueActionInsertAtShape_OutgoingLinks(view);
			case InputPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return getInputPin_AddVariableValueActionValueShape_OutgoingLinks(view);
			case ValuePinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionInsertAtShape_OutgoingLinks(view);
			case ValuePinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return getValuePin_AddVariableValueActionValueShape_OutgoingLinks(view);
			case ActionPinInAddVariableValueActionAsInsertAtEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionInsertAtShape_OutgoingLinks(view);
			case ActionPinInAddVariableValueActionAsValueEditPart.VISUAL_ID:
				return getActionInputPin_AddVariableValueActionValueShape_OutgoingLinks(view);
			case BroadcastSignalActionEditPart.VISUAL_ID:
				return getBroadcastSignalAction_Shape_OutgoingLinks(view);
			case InputPinInBroadcastSignalActionEditPart.VISUAL_ID:
				return getInputPin_BroadcastSignalActionArgumentShape_OutgoingLinks(view);
			case ValuePinInBroadcastSignalActionEditPart.VISUAL_ID:
				return getValuePin_BroadcastSignalActionArgumentShape_OutgoingLinks(view);
			case ActionPinInBroadcastSignalActionEditPart.VISUAL_ID:
				return getActionInputPin_BroadcastSignalActionArgumentShape_OutgoingLinks(view);
			case CentralBufferNodeEditPart.VISUAL_ID:
				return getCentralBufferNode_Shape_OutgoingLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_Shape_OutgoingLinks(view);
			case StartObjectBehavoiurActionEditPart.VISUAL_ID:
				return getStartObjectBehaviorAction_Shape_OutgoingLinks(view);
			case OutputPinInStartObjectBehaviorActionEditPart.VISUAL_ID:
				return getOutputPin_StartObjectBehaviorActionResultShape_OutgoingLinks(view);
			case InputPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_StartObjectBehaviorActionObjectShape_OutgoingLinks(view);
			case ValuePinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionObjectShape_OutgoingLinks(view);
			case ActionPinInStartObjectBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionObjectShape_OutgoingLinks(view);
			case InputPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return getInputPin_StartObjectBehaviorActionArgumentShape_OutgoingLinks(view);
			case ValuePinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return getValuePin_StartObjectBehaviorActionArgumentShape_OutgoingLinks(view);
			case ActionPinInStartObjectBehaviorActionAsArgumentEditPart.VISUAL_ID:
				return getActionInputPin_StartObjectBehaviorActionArgumentShape_OutgoingLinks(view);
			case TestIdentityActionEditPart.VISUAL_ID:
				return getTestIdentityAction_Shape_OutgoingLinks(view);
			case OutputPinInTestIdentityActionEditPart.VISUAL_ID:
				return getOutputPin_TestIdentityActionResultShape_OutgoingLinks(view);
			case InputPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return getInputPin_TestIdentityActionFirstShape_OutgoingLinks(view);
			case InputPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return getInputPin_TestIdentityActionSecondShape_OutgoingLinks(view);
			case ValuePinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionFirstShape_OutgoingLinks(view);
			case ValuePinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return getValuePin_TestIdentityActionSecondShape_OutgoingLinks(view);
			case ActionPinInTestIdentityActionAsFirstEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionFirstShape_OutgoingLinks(view);
			case ActionPinInTestIdentityActionAsSecondEditPart.VISUAL_ID:
				return getActionInputPin_TestIdentityActionSecondShape_OutgoingLinks(view);
			case ClearStructuralFeatureActionEditPart.VISUAL_ID:
				return getClearStructuralFeatureAction_Shape_OutgoingLinks(view);
			case OutputPinInClearStructuralFeatureActionEditPart.VISUAL_ID:
				return getOutputPin_ClearStructuralFeatureActionResultShape_OutgoingLinks(view);
			case InputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ClearStructuralFeatureActionObjectShape_OutgoingLinks(view);
			case ValuePinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ClearStructuralFeatureActionObjectShape_OutgoingLinks(view);
			case ActionInputPinInClearStructuralFeatureActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ClearStructuralFeatureActionObjectShape_OutgoingLinks(view);
			case CreateLinkActionEditPart.VISUAL_ID:
				return getCreateLinkAction_Shape_OutgoingLinks(view);
			case InputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_CreateLinkActionInputShape_OutgoingLinks(view);
			case ValuePinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_CreateLinkActionInputShape_OutgoingLinks(view);
			case ActionInputPinInCreateLinkActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkActionInputShape_OutgoingLinks(view);
			case ReadLinkActionEditPart.VISUAL_ID:
				return getReadLinkAction_Shape_OutgoingLinks(view);
			case OutputPinInReadLinkActionEditPart.VISUAL_ID:
				return getOutputPin_ReadLinkActionResultShape_OutgoingLinks(view);
			case InputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_ReadLinkActionInputShape_OutgoingLinks(view);
			case ValuePinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_ReadLinkActionInputShape_OutgoingLinks(view);
			case ActionInputPinInReadLinkActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_ReadLinkActionInputShape_OutgoingLinks(view);
			case DestroyLinkActionEditPart.VISUAL_ID:
				return getDestroyLinkAction_Shape_OutgoingLinks(view);
			case InputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_DestroyLinkActionInputShape_OutgoingLinks(view);
			case ValuePinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_DestroyLinkActionInputShape_OutgoingLinks(view);
			case ActionInputPinInDestroyLinkActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_DestroyLinkActionInputShape_OutgoingLinks(view);
			case ClearAssociationActionEditPart.VISUAL_ID:
				return getClearAssociationAction_Shape_OutgoingLinks(view);
			case InputPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ClearAssociationActionObjectShape_OutgoingLinks(view);
			case ValuePinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ClearAssociationActionObjectShape_OutgoingLinks(view);
			case ActionPinInClearAssociationActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ClearAssociationActionObjectShape_OutgoingLinks(view);
			case ReadExtentActionEditPart.VISUAL_ID:
				return getReadExtentAction_Shape_OutgoingLinks(view);
			case OutputPinInReadExtentActionEditPart.VISUAL_ID:
				return getOutputPin_ReadExtentActionResultShape_OutgoingLinks(view);
			case ReclassifyObjectActionEditPart.VISUAL_ID:
				return getReclassifyObjectAction_Shape_OutgoingLinks(view);
			case InputPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ReclassifyObjectActionObjectShape_OutgoingLinks(view);
			case ValuePinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ReclassifyObjectActionObjectShape_OutgoingLinks(view);
			case ActionPinInReclassifyObjectActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ReclassifyObjectActionObjectShape_OutgoingLinks(view);
			case ReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return getReadIsClassifiedObjectAction_Shape_OutgoingLinks(view);
			case OutputPinInReadIsClassifiedObjectActionEditPart.VISUAL_ID:
				return getOutputPin_ReadIsClassifiedObjectActionResultShape_OutgoingLinks(view);
			case InputPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_ReadIsClassifiedObjectActionObjectShape_OutgoingLinks(view);
			case ValuePinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_ReadIsClassifiedObjectActionObjectShape_OutgoingLinks(view);
			case ActionPinInReadIsClassifiedObjectActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_ReadIsClassifiedObjectActionObjectShape_OutgoingLinks(view);
			case ReduceActionEditPart.VISUAL_ID:
				return getReduceAction_Shape_OutgoingLinks(view);
			case OutputPinInReduceActionEditPart.VISUAL_ID:
				return getOutputPin_ReduceActionResultShape_OutgoingLinks(view);
			case InputPinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return getInputPin_ReduceActionCollectionShape_OutgoingLinks(view);
			case ValuePinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return getValuePin_ReduceActionCollectionShape_OutgoingLinks(view);
			case ActionPinInReduceActionAsCollectionEditPart.VISUAL_ID:
				return getActionInputPin_ReduceActionCollectionShape_OutgoingLinks(view);
			case StartClassifierBehaviorActionEditPart.VISUAL_ID:
				return getStartClassifierBehaviorAction_Shape_OutgoingLinks(view);
			case InputPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_StartClassifierBehaviorActionObjectShape_OutgoingLinks(view);
			case ValuePinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_StartClassifierBehaviorActionObjectShape_OutgoingLinks(view);
			case ActionPinInStartClassifierBehaviorActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_StartClassifierBehaviorActionObjectShape_OutgoingLinks(view);
			case CreateLinkObjectActionEditPart.VISUAL_ID:
				return getCreateLinkObjectAction_Shape_OutgoingLinks(view);
			case InputPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return getInputPin_CreateLinkObjectActionInputShape_OutgoingLinks(view);
			case ValuePinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return getValuePin_CreateLinkObjectActionInputShape_OutgoingLinks(view);
			case ActionPinInCreateLinkObjectActionAsInputValueEditPart.VISUAL_ID:
				return getActionInputPin_CreateLinkObjectActionInputShape_OutgoingLinks(view);
			case OutputPinInCreateLinkObjectActionEditPart.VISUAL_ID:
				return getOutputPin_CreateLinkObjectActionResultShape_OutgoingLinks(view);
			case UnmarshallActionEditPart.VISUAL_ID:
				return getUnmarshallAction_Shape_OutgoingLinks(view);
			case InputPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return getInputPin_UnmarshallActionObjectShape_OutgoingLinks(view);
			case ValuePinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return getValuePin_UnmarshallActionObjectShape_OutgoingLinks(view);
			case ActionPinInUnmarshallActionAsObjectEditPart.VISUAL_ID:
				return getActionInputPin_UnmarshallActionObjectShape_OutgoingLinks(view);
			case OutputPinInUnmarshallActionAsResultEditPart.VISUAL_ID:
				return getOutputPin_UnmarshallActionResultShape_OutgoingLinks(view);
			case ObjectFlowEditPart.VISUAL_ID:
				return getObjectFlow_Edge_OutgoingLinks(view);
			case ControlFlowEditPart.VISUAL_ID:
				return getControlFlow_Edge_OutgoingLinks(view);
			case ExceptionHandlerEditPart.VISUAL_ID:
				return getExceptionHandler_Edge_OutgoingLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_ActivityDiagram_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivity_Shape_ContainedLinks(View view) {
		Activity modelElement = (Activity) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getParameter_ParameterLabel_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PreconditionLabel_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PostconditionLabel_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInitialNode_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivityFinalNode_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getFlowFinalNode_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueAction_Shape_ContainedLinks(View view) {
		OpaqueAction modelElement = (OpaqueAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_OpaqueActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_OpaqueActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_OpaqueActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_OpaqueActionOutputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCallBehaviorAction_Shape_ContainedLinks(View view) {
		CallBehaviorAction modelElement = (CallBehaviorAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CallBehaviorActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CallBehaviorActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CallBehaviorActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CallBehaviorActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCallOperationAction_Shape_ContainedLinks(View view) {
		CallOperationAction modelElement = (CallOperationAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CallOperationActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CallOperationActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CallOperationActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CallOperationActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CallOperationActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CallOperationActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CallOperationActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_LocalPreconditionShape_ContainedLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_LocalPostconditionShape_ContainedLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_LocalPreconditionShape_ContainedLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_LocalPostconditionShape_ContainedLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_LocalPreconditionShape_ContainedLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_LocalPostconditionShape_ContainedLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_LocalPreconditionShape_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_LocalPostconditionShape_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDecisionNode_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMergeNode_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getForkNode_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getJoinNode_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataStoreNode_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSendObjectAction_Shape_ContainedLinks(View view) {
		SendObjectAction modelElement = (SendObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendObjectActionRequestShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendObjectActionRequestShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendObjectActionRequestShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendObjectActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendObjectActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendObjectActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSendSignalAction_Shape_ContainedLinks(View view) {
		SendSignalAction modelElement = (SendSignalAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendSignalActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendSignalActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendSignalActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendSignalActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendSignalActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendSignalActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivityParameterNode_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAcceptEventAction_Shape_ContainedLinks(View view) {
		AcceptEventAction modelElement = (AcceptEventAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_AcceptEventActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValueSpecificationAction_Shape_ContainedLinks(View view) {
		ValueSpecificationAction modelElement = (ValueSpecificationAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ValueSpecificationActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConditionalNode_Shape_ContainedLinks(View view) {
		ConditionalNode modelElement = (ConditionalNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpansionRegion_Shape_ContainedLinks(View view) {
		ExpansionRegion modelElement = (ExpansionRegion) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpansionNode_InputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpansionNode_OutputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLoopNode_Shape_ContainedLinks(View view) {
		LoopNode modelElement = (LoopNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_LoopNodeVariableInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_LoopNodeVariableInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_LoopNodeVariableInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_LoopNodeBodyOutputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_LoopNodeVariableShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_LoopNodeResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSequenceNode_Shape_ContainedLinks(View view) {
		SequenceNode modelElement = (SequenceNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStructuredActivityNode_Shape_ContainedLinks(View view) {
		StructuredActivityNode modelElement = (StructuredActivityNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StructuredActivityNodeInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StructuredActivityNodeInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StructuredActivityNodeInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_StructuredActivityNodeOutputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivityPartition_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterruptibleActivityRegion_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_ContainedLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadSelfAction_Shape_ContainedLinks(View view) {
		ReadSelfAction modelElement = (ReadSelfAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadSelfActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivity_Shape_CN_ContainedLinks(View view) {
		Activity modelElement = (Activity) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCreateObjectAction_Shape_ContainedLinks(View view) {
		CreateObjectAction modelElement = (CreateObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CreateObjectActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadStructuralFeatureAction_Shape_ContainedLinks(View view) {
		ReadStructuralFeatureAction modelElement = (ReadStructuralFeatureAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReadStructuralFeatureActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReadStructuralFeatureActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReadStructuralFeatureActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadStructuralFeatureActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAddStructuralFeatureValueAction_Shape_ContainedLinks(View view) {
		AddStructuralFeatureValueAction modelElement = (AddStructuralFeatureValueAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddStructuralFeatureValueActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddStructuralFeatureValueActionValueShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddStructuralFeatureValueActionInsertAtShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddStructuralFeatureValueActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddStructuralFeatureValueActionValueShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddStructuralFeatureValueActionInsertAtShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddStructuralFeatureValueActionObjectShape_ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddStructuralFeatureValueActionValueShape_ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddStructuralFeatureValueActionInsertAtShape_ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_AddStructuralFeatureValueActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDestroyObjectAction_Shape_ContainedLinks(View view) {
		DestroyObjectAction modelElement = (DestroyObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_DestroyObjectActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_DestroyObjectActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_DestroyObjectActionTargetShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadVariableAction_Shape_ContainedLinks(View view) {
		ReadVariableAction modelElement = (ReadVariableAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadVariableActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAddVariableValueAction_Shape_ContainedLinks(View view) {
		AddVariableValueAction modelElement = (AddVariableValueAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddVariableValueActionInsertAtShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddVariableValueActionValueShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddVariableValueActionInsertAtShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddVariableValueActionValueShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddVariableValueActionInsertAtShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddVariableValueActionValueShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getBroadcastSignalAction_Shape_ContainedLinks(View view) {
		BroadcastSignalAction modelElement = (BroadcastSignalAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_BroadcastSignalActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_BroadcastSignalActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_BroadcastSignalActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCentralBufferNode_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStartObjectBehaviorAction_Shape_ContainedLinks(View view) {
		StartObjectBehaviorAction modelElement = (StartObjectBehaviorAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_StartObjectBehaviorActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StartObjectBehaviorActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StartObjectBehaviorActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StartObjectBehaviorActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StartObjectBehaviorActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StartObjectBehaviorActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StartObjectBehaviorActionArgumentShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTestIdentityAction_Shape_ContainedLinks(View view) {
		TestIdentityAction modelElement = (TestIdentityAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_TestIdentityActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_TestIdentityActionFirstShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_TestIdentityActionSecondShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_TestIdentityActionFirstShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_TestIdentityActionSecondShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_TestIdentityActionFirstShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_TestIdentityActionSecondShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClearStructuralFeatureAction_Shape_ContainedLinks(View view) {
		ClearStructuralFeatureAction modelElement = (ClearStructuralFeatureAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ClearStructuralFeatureActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ClearStructuralFeatureActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ClearStructuralFeatureActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ClearStructuralFeatureActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCreateLinkAction_Shape_ContainedLinks(View view) {
		CreateLinkAction modelElement = (CreateLinkAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CreateLinkActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CreateLinkActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CreateLinkActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadLinkAction_Shape_ContainedLinks(View view) {
		ReadLinkAction modelElement = (ReadLinkAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadLinkActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReadLinkActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReadLinkActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReadLinkActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDestroyLinkAction_Shape_ContainedLinks(View view) {
		DestroyLinkAction modelElement = (DestroyLinkAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_DestroyLinkActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_DestroyLinkActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_DestroyLinkActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClearAssociationAction_Shape_ContainedLinks(View view) {
		ClearAssociationAction modelElement = (ClearAssociationAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ClearAssociationActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ClearAssociationActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ClearAssociationActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadExtentAction_Shape_ContainedLinks(View view) {
		ReadExtentAction modelElement = (ReadExtentAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadExtentActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReclassifyObjectAction_Shape_ContainedLinks(View view) {
		ReclassifyObjectAction modelElement = (ReclassifyObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReclassifyObjectActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReclassifyObjectActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReclassifyObjectActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadIsClassifiedObjectAction_Shape_ContainedLinks(View view) {
		ReadIsClassifiedObjectAction modelElement = (ReadIsClassifiedObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadIsClassifiedObjectActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReadIsClassifiedObjectActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReadIsClassifiedObjectActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReadIsClassifiedObjectActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReduceAction_Shape_ContainedLinks(View view) {
		ReduceAction modelElement = (ReduceAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReduceActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReduceActionCollectionShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReduceActionCollectionShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReduceActionCollectionShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStartClassifierBehaviorAction_Shape_ContainedLinks(View view) {
		StartClassifierBehaviorAction modelElement = (StartClassifierBehaviorAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StartClassifierBehaviorActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StartClassifierBehaviorActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StartClassifierBehaviorActionObjectShape_ContainedLinks(
			View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCreateLinkObjectAction_Shape_ContainedLinks(View view) {
		CreateLinkObjectAction modelElement = (CreateLinkObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CreateLinkObjectActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CreateLinkObjectActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CreateLinkObjectActionInputShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CreateLinkObjectActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUnmarshallAction_Shape_ContainedLinks(View view) {
		UnmarshallAction modelElement = (UnmarshallAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_UnmarshallActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_UnmarshallActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_UnmarshallActionObjectShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_UnmarshallActionResultShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getObjectFlow_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getControlFlow_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExceptionHandler_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivity_Shape_IncomingLinks(View view) {
		Activity modelElement = (Activity) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getParameter_ParameterLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PreconditionLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PostconditionLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInitialNode_Shape_IncomingLinks(View view) {
		InitialNode modelElement = (InitialNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivityFinalNode_Shape_IncomingLinks(View view) {
		ActivityFinalNode modelElement = (ActivityFinalNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getFlowFinalNode_Shape_IncomingLinks(View view) {
		FlowFinalNode modelElement = (FlowFinalNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueAction_Shape_IncomingLinks(View view) {
		OpaqueAction modelElement = (OpaqueAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_OpaqueActionInputShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_OpaqueActionInputShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_OpaqueActionInputShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_OpaqueActionOutputShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCallBehaviorAction_Shape_IncomingLinks(View view) {
		CallBehaviorAction modelElement = (CallBehaviorAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CallBehaviorActionArgumentShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CallBehaviorActionArgumentShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CallBehaviorActionArgumentShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CallBehaviorActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCallOperationAction_Shape_IncomingLinks(View view) {
		CallOperationAction modelElement = (CallOperationAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CallOperationActionArgumentShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CallOperationActionArgumentShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CallOperationActionArgumentShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CallOperationActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CallOperationActionTargetShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CallOperationActionTargetShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CallOperationActionTargetShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_LocalPreconditionShape_IncomingLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_LocalPostconditionShape_IncomingLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_LocalPreconditionShape_IncomingLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_LocalPostconditionShape_IncomingLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_LocalPreconditionShape_IncomingLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_LocalPostconditionShape_IncomingLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_LocalPreconditionShape_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_LocalPostconditionShape_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDecisionNode_Shape_IncomingLinks(View view) {
		DecisionNode modelElement = (DecisionNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMergeNode_Shape_IncomingLinks(View view) {
		MergeNode modelElement = (MergeNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getForkNode_Shape_IncomingLinks(View view) {
		ForkNode modelElement = (ForkNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getJoinNode_Shape_IncomingLinks(View view) {
		JoinNode modelElement = (JoinNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataStoreNode_Shape_IncomingLinks(View view) {
		DataStoreNode modelElement = (DataStoreNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSendObjectAction_Shape_IncomingLinks(View view) {
		SendObjectAction modelElement = (SendObjectAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendObjectActionRequestShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendObjectActionRequestShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendObjectActionRequestShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendObjectActionTargetShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendObjectActionTargetShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendObjectActionTargetShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSendSignalAction_Shape_IncomingLinks(View view) {
		SendSignalAction modelElement = (SendSignalAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendSignalActionArgumentShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendSignalActionArgumentShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendSignalActionArgumentShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendSignalActionTargetShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendSignalActionTargetShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendSignalActionTargetShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivityParameterNode_Shape_IncomingLinks(View view) {
		ActivityParameterNode modelElement = (ActivityParameterNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAcceptEventAction_Shape_IncomingLinks(View view) {
		AcceptEventAction modelElement = (AcceptEventAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_AcceptEventActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValueSpecificationAction_Shape_IncomingLinks(View view) {
		ValueSpecificationAction modelElement = (ValueSpecificationAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ValueSpecificationActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConditionalNode_Shape_IncomingLinks(View view) {
		ConditionalNode modelElement = (ConditionalNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpansionRegion_Shape_IncomingLinks(View view) {
		ExpansionRegion modelElement = (ExpansionRegion) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpansionNode_InputShape_IncomingLinks(View view) {
		ExpansionNode modelElement = (ExpansionNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpansionNode_OutputShape_IncomingLinks(View view) {
		ExpansionNode modelElement = (ExpansionNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLoopNode_Shape_IncomingLinks(View view) {
		LoopNode modelElement = (LoopNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_LoopNodeVariableInputShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_LoopNodeVariableInputShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_LoopNodeVariableInputShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_LoopNodeBodyOutputShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_LoopNodeVariableShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_LoopNodeResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSequenceNode_Shape_IncomingLinks(View view) {
		SequenceNode modelElement = (SequenceNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStructuredActivityNode_Shape_IncomingLinks(View view) {
		StructuredActivityNode modelElement = (StructuredActivityNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StructuredActivityNodeInputShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StructuredActivityNodeInputShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StructuredActivityNodeInputShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_StructuredActivityNodeOutputShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivityPartition_Shape_IncomingLinks(View view) {
		ActivityPartition modelElement = (ActivityPartition) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterruptibleActivityRegion_Shape_IncomingLinks(View view) {
		InterruptibleActivityRegion modelElement = (InterruptibleActivityRegion) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_IncomingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadSelfAction_Shape_IncomingLinks(View view) {
		ReadSelfAction modelElement = (ReadSelfAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadSelfActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivity_Shape_CN_IncomingLinks(View view) {
		Activity modelElement = (Activity) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCreateObjectAction_Shape_IncomingLinks(View view) {
		CreateObjectAction modelElement = (CreateObjectAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CreateObjectActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_IncomingLinks(View view) {
		NamedElement modelElement = (NamedElement) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadStructuralFeatureAction_Shape_IncomingLinks(View view) {
		ReadStructuralFeatureAction modelElement = (ReadStructuralFeatureAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReadStructuralFeatureActionObjectShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReadStructuralFeatureActionObjectShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReadStructuralFeatureActionObjectShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadStructuralFeatureActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAddStructuralFeatureValueAction_Shape_IncomingLinks(View view) {
		AddStructuralFeatureValueAction modelElement = (AddStructuralFeatureValueAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddStructuralFeatureValueActionObjectShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddStructuralFeatureValueActionValueShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddStructuralFeatureValueActionInsertAtShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddStructuralFeatureValueActionObjectShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddStructuralFeatureValueActionValueShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddStructuralFeatureValueActionInsertAtShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddStructuralFeatureValueActionObjectShape_IncomingLinks(
			View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddStructuralFeatureValueActionValueShape_IncomingLinks(
			View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddStructuralFeatureValueActionInsertAtShape_IncomingLinks(
			View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_AddStructuralFeatureValueActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDestroyObjectAction_Shape_IncomingLinks(View view) {
		DestroyObjectAction modelElement = (DestroyObjectAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_DestroyObjectActionTargetShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_DestroyObjectActionTargetShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_DestroyObjectActionTargetShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadVariableAction_Shape_IncomingLinks(View view) {
		ReadVariableAction modelElement = (ReadVariableAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadVariableActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAddVariableValueAction_Shape_IncomingLinks(View view) {
		AddVariableValueAction modelElement = (AddVariableValueAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddVariableValueActionInsertAtShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddVariableValueActionValueShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddVariableValueActionInsertAtShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddVariableValueActionValueShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddVariableValueActionInsertAtShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddVariableValueActionValueShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getBroadcastSignalAction_Shape_IncomingLinks(View view) {
		BroadcastSignalAction modelElement = (BroadcastSignalAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_BroadcastSignalActionArgumentShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_BroadcastSignalActionArgumentShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_BroadcastSignalActionArgumentShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCentralBufferNode_Shape_IncomingLinks(View view) {
		CentralBufferNode modelElement = (CentralBufferNode) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStartObjectBehaviorAction_Shape_IncomingLinks(View view) {
		StartObjectBehaviorAction modelElement = (StartObjectBehaviorAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_StartObjectBehaviorActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StartObjectBehaviorActionObjectShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StartObjectBehaviorActionObjectShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StartObjectBehaviorActionObjectShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StartObjectBehaviorActionArgumentShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StartObjectBehaviorActionArgumentShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StartObjectBehaviorActionArgumentShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTestIdentityAction_Shape_IncomingLinks(View view) {
		TestIdentityAction modelElement = (TestIdentityAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_TestIdentityActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_TestIdentityActionFirstShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_TestIdentityActionSecondShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_TestIdentityActionFirstShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_TestIdentityActionSecondShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_TestIdentityActionFirstShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_TestIdentityActionSecondShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClearStructuralFeatureAction_Shape_IncomingLinks(View view) {
		ClearStructuralFeatureAction modelElement = (ClearStructuralFeatureAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ClearStructuralFeatureActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ClearStructuralFeatureActionObjectShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ClearStructuralFeatureActionObjectShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ClearStructuralFeatureActionObjectShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCreateLinkAction_Shape_IncomingLinks(View view) {
		CreateLinkAction modelElement = (CreateLinkAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CreateLinkActionInputShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CreateLinkActionInputShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CreateLinkActionInputShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadLinkAction_Shape_IncomingLinks(View view) {
		ReadLinkAction modelElement = (ReadLinkAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadLinkActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReadLinkActionInputShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReadLinkActionInputShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReadLinkActionInputShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDestroyLinkAction_Shape_IncomingLinks(View view) {
		DestroyLinkAction modelElement = (DestroyLinkAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_DestroyLinkActionInputShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_DestroyLinkActionInputShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_DestroyLinkActionInputShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClearAssociationAction_Shape_IncomingLinks(View view) {
		ClearAssociationAction modelElement = (ClearAssociationAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ClearAssociationActionObjectShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ClearAssociationActionObjectShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ClearAssociationActionObjectShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadExtentAction_Shape_IncomingLinks(View view) {
		ReadExtentAction modelElement = (ReadExtentAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadExtentActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReclassifyObjectAction_Shape_IncomingLinks(View view) {
		ReclassifyObjectAction modelElement = (ReclassifyObjectAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReclassifyObjectActionObjectShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReclassifyObjectActionObjectShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReclassifyObjectActionObjectShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadIsClassifiedObjectAction_Shape_IncomingLinks(View view) {
		ReadIsClassifiedObjectAction modelElement = (ReadIsClassifiedObjectAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadIsClassifiedObjectActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReadIsClassifiedObjectActionObjectShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReadIsClassifiedObjectActionObjectShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReadIsClassifiedObjectActionObjectShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReduceAction_Shape_IncomingLinks(View view) {
		ReduceAction modelElement = (ReduceAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReduceActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReduceActionCollectionShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReduceActionCollectionShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReduceActionCollectionShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStartClassifierBehaviorAction_Shape_IncomingLinks(View view) {
		StartClassifierBehaviorAction modelElement = (StartClassifierBehaviorAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StartClassifierBehaviorActionObjectShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StartClassifierBehaviorActionObjectShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StartClassifierBehaviorActionObjectShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCreateLinkObjectAction_Shape_IncomingLinks(View view) {
		CreateLinkObjectAction modelElement = (CreateLinkObjectAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CreateLinkObjectActionInputShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUnmarshallAction_Shape_IncomingLinks(View view) {
		UnmarshallAction modelElement = (UnmarshallAction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_UnmarshallActionObjectShape_IncomingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_UnmarshallActionObjectShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_UnmarshallActionObjectShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_UnmarshallActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CreateLinkObjectActionInputShape_IncomingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CreateLinkObjectActionInputShape_IncomingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CreateLinkObjectActionResultShape_IncomingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingTypeModelFacetLinks_ObjectFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ControlFlow_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getObjectFlow_Edge_IncomingLinks(View view) {
		ObjectFlow modelElement = (ObjectFlow) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getControlFlow_Edge_IncomingLinks(View view) {
		ControlFlow modelElement = (ControlFlow) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExceptionHandler_Edge_IncomingLinks(View view) {
		ExceptionHandler modelElement = (ExceptionHandler) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivity_Shape_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getParameter_ParameterLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PreconditionLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_PostconditionLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInitialNode_Shape_OutgoingLinks(View view) {
		InitialNode modelElement = (InitialNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivityFinalNode_Shape_OutgoingLinks(View view) {
		ActivityFinalNode modelElement = (ActivityFinalNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getFlowFinalNode_Shape_OutgoingLinks(View view) {
		FlowFinalNode modelElement = (FlowFinalNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueAction_Shape_OutgoingLinks(View view) {
		OpaqueAction modelElement = (OpaqueAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_OpaqueActionInputShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_OpaqueActionInputShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_OpaqueActionInputShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_OpaqueActionOutputShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCallBehaviorAction_Shape_OutgoingLinks(View view) {
		CallBehaviorAction modelElement = (CallBehaviorAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CallBehaviorActionArgumentShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CallBehaviorActionArgumentShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CallBehaviorActionArgumentShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CallBehaviorActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCallOperationAction_Shape_OutgoingLinks(View view) {
		CallOperationAction modelElement = (CallOperationAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CallOperationActionArgumentShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CallOperationActionArgumentShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CallOperationActionArgumentShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CallOperationActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CallOperationActionTargetShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CallOperationActionTargetShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CallOperationActionTargetShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_LocalPreconditionShape_OutgoingLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_LocalPostconditionShape_OutgoingLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_LocalPreconditionShape_OutgoingLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_LocalPostconditionShape_OutgoingLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_LocalPreconditionShape_OutgoingLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_LocalPostconditionShape_OutgoingLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_LocalPreconditionShape_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_LocalPostconditionShape_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDecisionNode_Shape_OutgoingLinks(View view) {
		DecisionNode modelElement = (DecisionNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getMergeNode_Shape_OutgoingLinks(View view) {
		MergeNode modelElement = (MergeNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getForkNode_Shape_OutgoingLinks(View view) {
		ForkNode modelElement = (ForkNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getJoinNode_Shape_OutgoingLinks(View view) {
		JoinNode modelElement = (JoinNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataStoreNode_Shape_OutgoingLinks(View view) {
		DataStoreNode modelElement = (DataStoreNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSendObjectAction_Shape_OutgoingLinks(View view) {
		SendObjectAction modelElement = (SendObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendObjectActionRequestShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendObjectActionRequestShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendObjectActionRequestShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendObjectActionTargetShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendObjectActionTargetShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendObjectActionTargetShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSendSignalAction_Shape_OutgoingLinks(View view) {
		SendSignalAction modelElement = (SendSignalAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendSignalActionArgumentShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendSignalActionArgumentShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendSignalActionArgumentShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_SendSignalActionTargetShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_SendSignalActionTargetShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_SendSignalActionTargetShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivityParameterNode_Shape_OutgoingLinks(View view) {
		ActivityParameterNode modelElement = (ActivityParameterNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAcceptEventAction_Shape_OutgoingLinks(View view) {
		AcceptEventAction modelElement = (AcceptEventAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_AcceptEventActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValueSpecificationAction_Shape_OutgoingLinks(View view) {
		ValueSpecificationAction modelElement = (ValueSpecificationAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ValueSpecificationActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConditionalNode_Shape_OutgoingLinks(View view) {
		ConditionalNode modelElement = (ConditionalNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpansionRegion_Shape_OutgoingLinks(View view) {
		ExpansionRegion modelElement = (ExpansionRegion) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpansionNode_InputShape_OutgoingLinks(View view) {
		ExpansionNode modelElement = (ExpansionNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpansionNode_OutputShape_OutgoingLinks(View view) {
		ExpansionNode modelElement = (ExpansionNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLoopNode_Shape_OutgoingLinks(View view) {
		LoopNode modelElement = (LoopNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_LoopNodeVariableInputShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_LoopNodeVariableInputShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_LoopNodeVariableInputShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_LoopNodeBodyOutputShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_LoopNodeVariableShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_LoopNodeResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSequenceNode_Shape_OutgoingLinks(View view) {
		SequenceNode modelElement = (SequenceNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStructuredActivityNode_Shape_OutgoingLinks(View view) {
		StructuredActivityNode modelElement = (StructuredActivityNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StructuredActivityNodeInputShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StructuredActivityNodeInputShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StructuredActivityNodeInputShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_StructuredActivityNodeOutputShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivityPartition_Shape_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterruptibleActivityRegion_Shape_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_OutgoingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadSelfAction_Shape_OutgoingLinks(View view) {
		ReadSelfAction modelElement = (ReadSelfAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadSelfActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivity_Shape_CN_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCreateObjectAction_Shape_OutgoingLinks(View view) {
		CreateObjectAction modelElement = (CreateObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CreateObjectActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNamedElement_DefaultShape_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadStructuralFeatureAction_Shape_OutgoingLinks(View view) {
		ReadStructuralFeatureAction modelElement = (ReadStructuralFeatureAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReadStructuralFeatureActionObjectShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReadStructuralFeatureActionObjectShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReadStructuralFeatureActionObjectShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadStructuralFeatureActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAddStructuralFeatureValueAction_Shape_OutgoingLinks(View view) {
		AddStructuralFeatureValueAction modelElement = (AddStructuralFeatureValueAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddStructuralFeatureValueActionObjectShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddStructuralFeatureValueActionValueShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddStructuralFeatureValueActionInsertAtShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddStructuralFeatureValueActionObjectShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddStructuralFeatureValueActionValueShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddStructuralFeatureValueActionInsertAtShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddStructuralFeatureValueActionObjectShape_OutgoingLinks(
			View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddStructuralFeatureValueActionValueShape_OutgoingLinks(
			View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddStructuralFeatureValueActionInsertAtShape_OutgoingLinks(
			View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_AddStructuralFeatureValueActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDestroyObjectAction_Shape_OutgoingLinks(View view) {
		DestroyObjectAction modelElement = (DestroyObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_DestroyObjectActionTargetShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_DestroyObjectActionTargetShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_DestroyObjectActionTargetShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadVariableAction_Shape_OutgoingLinks(View view) {
		ReadVariableAction modelElement = (ReadVariableAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadVariableActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAddVariableValueAction_Shape_OutgoingLinks(View view) {
		AddVariableValueAction modelElement = (AddVariableValueAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddVariableValueActionInsertAtShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_AddVariableValueActionValueShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddVariableValueActionInsertAtShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_AddVariableValueActionValueShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddVariableValueActionInsertAtShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_AddVariableValueActionValueShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getBroadcastSignalAction_Shape_OutgoingLinks(View view) {
		BroadcastSignalAction modelElement = (BroadcastSignalAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_BroadcastSignalActionArgumentShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_BroadcastSignalActionArgumentShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_BroadcastSignalActionArgumentShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCentralBufferNode_Shape_OutgoingLinks(View view) {
		CentralBufferNode modelElement = (CentralBufferNode) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStartObjectBehaviorAction_Shape_OutgoingLinks(View view) {
		StartObjectBehaviorAction modelElement = (StartObjectBehaviorAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_StartObjectBehaviorActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StartObjectBehaviorActionObjectShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StartObjectBehaviorActionObjectShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StartObjectBehaviorActionObjectShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StartObjectBehaviorActionArgumentShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StartObjectBehaviorActionArgumentShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StartObjectBehaviorActionArgumentShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTestIdentityAction_Shape_OutgoingLinks(View view) {
		TestIdentityAction modelElement = (TestIdentityAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_TestIdentityActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_TestIdentityActionFirstShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_TestIdentityActionSecondShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_TestIdentityActionFirstShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_TestIdentityActionSecondShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_TestIdentityActionFirstShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_TestIdentityActionSecondShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClearStructuralFeatureAction_Shape_OutgoingLinks(View view) {
		ClearStructuralFeatureAction modelElement = (ClearStructuralFeatureAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ClearStructuralFeatureActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ClearStructuralFeatureActionObjectShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ClearStructuralFeatureActionObjectShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ClearStructuralFeatureActionObjectShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCreateLinkAction_Shape_OutgoingLinks(View view) {
		CreateLinkAction modelElement = (CreateLinkAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CreateLinkActionInputShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CreateLinkActionInputShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CreateLinkActionInputShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadLinkAction_Shape_OutgoingLinks(View view) {
		ReadLinkAction modelElement = (ReadLinkAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadLinkActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReadLinkActionInputShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReadLinkActionInputShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReadLinkActionInputShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDestroyLinkAction_Shape_OutgoingLinks(View view) {
		DestroyLinkAction modelElement = (DestroyLinkAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_DestroyLinkActionInputShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_DestroyLinkActionInputShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_DestroyLinkActionInputShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClearAssociationAction_Shape_OutgoingLinks(View view) {
		ClearAssociationAction modelElement = (ClearAssociationAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ClearAssociationActionObjectShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ClearAssociationActionObjectShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ClearAssociationActionObjectShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadExtentAction_Shape_OutgoingLinks(View view) {
		ReadExtentAction modelElement = (ReadExtentAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadExtentActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReclassifyObjectAction_Shape_OutgoingLinks(View view) {
		ReclassifyObjectAction modelElement = (ReclassifyObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReclassifyObjectActionObjectShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReclassifyObjectActionObjectShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReclassifyObjectActionObjectShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReadIsClassifiedObjectAction_Shape_OutgoingLinks(View view) {
		ReadIsClassifiedObjectAction modelElement = (ReadIsClassifiedObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReadIsClassifiedObjectActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReadIsClassifiedObjectActionObjectShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReadIsClassifiedObjectActionObjectShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReadIsClassifiedObjectActionObjectShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getReduceAction_Shape_OutgoingLinks(View view) {
		ReduceAction modelElement = (ReduceAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_ReduceActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_ReduceActionCollectionShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_ReduceActionCollectionShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_ReduceActionCollectionShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStartClassifierBehaviorAction_Shape_OutgoingLinks(View view) {
		StartClassifierBehaviorAction modelElement = (StartClassifierBehaviorAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_StartClassifierBehaviorActionObjectShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_StartClassifierBehaviorActionObjectShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_StartClassifierBehaviorActionObjectShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUnmarshallAction_Shape_OutgoingLinks(View view) {
		UnmarshallAction modelElement = (UnmarshallAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_UnmarshallActionObjectShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_UnmarshallActionObjectShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_UnmarshallActionObjectShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_UnmarshallActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCreateLinkObjectAction_Shape_OutgoingLinks(View view) {
		CreateLinkObjectAction modelElement = (CreateLinkObjectAction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInputPin_CreateLinkObjectActionInputShape_OutgoingLinks(View view) {
		InputPin modelElement = (InputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getValuePin_CreateLinkObjectActionInputShape_OutgoingLinks(View view) {
		ValuePin modelElement = (ValuePin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActionInputPin_CreateLinkObjectActionInputShape_OutgoingLinks(View view) {
		ActionInputPin modelElement = (ActionInputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOutputPin_CreateLinkObjectActionResultShape_OutgoingLinks(View view) {
		OutputPin modelElement = (OutputPin) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ControlFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getObjectFlow_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getControlFlow_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExceptionHandler_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_ObjectFlow_Edge(Activity container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getEdges()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ObjectFlow) {
				continue;
			}
			ObjectFlow link = (ObjectFlow) linkObject;
			if (!ObjectFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			ActivityNode dst = link.getTarget();
			ActivityNode src = link.getSource();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ObjectFlow_Edge, ObjectFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_ControlFlow_Edge(Activity container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getEdges()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ControlFlow) {
				continue;
			}
			ControlFlow link = (ControlFlow) linkObject;
			if (!ControlFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			ActivityNode dst = link.getTarget();
			ActivityNode src = link.getSource();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ControlFlow_Edge, ControlFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_ExceptionHandler_Edge(
			ExecutableNode container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getHandlers()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ExceptionHandler) {
				continue;
			}
			ExceptionHandler link = (ExceptionHandler) linkObject;
			if (!ExceptionHandlerEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			ObjectNode dst = link.getExceptionInput();
			ExecutableNode src = link.getProtectedNode();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ExceptionHandler_Edge, ExceptionHandlerEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Action_LocalPreconditionEdge(
			Constraint target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getAction_LocalPrecondition()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Action_LocalPreconditionEdge, ActionLocalPreconditionEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Action_LocalPostconditionEdge(
			Constraint target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getAction_LocalPostcondition()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Action_LocalPostconditionEdge, ActionLocalPostconditionEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_ObjectFlow_Edge(ActivityNode target,
			CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getActivityEdge_Target() || false == setting.getEObject() instanceof ObjectFlow) {
				continue;
			}
			ObjectFlow link = (ObjectFlow) setting.getEObject();
			if (!ObjectFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			ActivityNode src = link.getSource();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.ObjectFlow_Edge, ObjectFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_ControlFlow_Edge(ActivityNode target,
			CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getActivityEdge_Target() || false == setting.getEObject() instanceof ControlFlow) {
				continue;
			}
			ControlFlow link = (ControlFlow) setting.getEObject();
			if (!ControlFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			ActivityNode src = link.getSource();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.ControlFlow_Edge, ControlFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_ExceptionHandler_Edge(ObjectNode target,
			CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getExceptionHandler_ExceptionInput() || false == setting.getEObject() instanceof ExceptionHandler) {
				continue;
			}
			ExceptionHandler link = (ExceptionHandler) setting.getEObject();
			if (!ExceptionHandlerEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			ExecutableNode src = link.getProtectedNode();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.ExceptionHandler_Edge, ExceptionHandlerEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(
			Element target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getComment_AnnotatedElement()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Comment_AnnotatedElementEdge, CommentLinkEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(
			Element target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getConstraint_ConstrainedElement()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Constraint_ConstrainedElementEdge, ConstraintConstrainedElementEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Action_LocalPreconditionEdge(
			Action source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getLocalPreconditions()
				.iterator(); destinations.hasNext();) {
			Constraint destination = (Constraint) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Action_LocalPreconditionEdge, ActionLocalPreconditionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Action_LocalPostconditionEdge(
			Action source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getLocalPostconditions()
				.iterator(); destinations.hasNext();) {
			Constraint destination = (Constraint) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Action_LocalPostconditionEdge, ActionLocalPostconditionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_ObjectFlow_Edge(ActivityNode source) {
		Activity container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Activity) {
				container = (Activity) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getEdges()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ObjectFlow) {
				continue;
			}
			ObjectFlow link = (ObjectFlow) linkObject;
			if (!ObjectFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			ActivityNode dst = link.getTarget();
			ActivityNode src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ObjectFlow_Edge, ObjectFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_ControlFlow_Edge(ActivityNode source) {
		Activity container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Activity) {
				container = (Activity) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getEdges()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ControlFlow) {
				continue;
			}
			ControlFlow link = (ControlFlow) linkObject;
			if (!ControlFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			ActivityNode dst = link.getTarget();
			ActivityNode src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ControlFlow_Edge, ControlFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_ExceptionHandler_Edge(
			ExecutableNode source) {
		ExecutableNode container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof ExecutableNode) {
				container = (ExecutableNode) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getHandlers()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ExceptionHandler) {
				continue;
			}
			ExceptionHandler link = (ExceptionHandler) linkObject;
			if (!ExceptionHandlerEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			ObjectNode dst = link.getExceptionInput();
			ExecutableNode src = link.getProtectedNode();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ExceptionHandler_Edge, ExceptionHandlerEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(
			Comment source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getAnnotatedElements()
				.iterator(); destinations.hasNext();) {
			Element destination = (Element) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Comment_AnnotatedElementEdge, CommentLinkEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(
			Constraint source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getConstrainedElements()
				.iterator(); destinations.hasNext();) {
			Element destination = (Element) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Constraint_ConstrainedElementEdge, ConstraintConstrainedElementEditPart.VISUAL_ID));
		}
		return result;
	}
}
