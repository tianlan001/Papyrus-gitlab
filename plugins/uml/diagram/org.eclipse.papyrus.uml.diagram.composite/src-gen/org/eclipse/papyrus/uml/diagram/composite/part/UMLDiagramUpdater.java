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
package org.eclipse.papyrus.uml.diagram.composite.part;

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
import org.eclipse.papyrus.uml.diagram.composite.custom.parts.PropertyDiagramUpdater;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.composite.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Actor;
import org.eclipse.uml2.uml.AnyReceiveEvent;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.CallEvent;
import org.eclipse.uml2.uml.ChangeEvent;
import org.eclipse.uml2.uml.Class;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.CollaborationUse;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.ComponentRealization;
import org.eclipse.uml2.uml.ConnectableElement;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.ConnectorEnd;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DataType;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Deployment;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Duration;
import org.eclipse.uml2.uml.DurationConstraint;
import org.eclipse.uml2.uml.DurationInterval;
import org.eclipse.uml2.uml.DurationObservation;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Expression;
import org.eclipse.uml2.uml.FunctionBehavior;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InformationFlow;
import org.eclipse.uml2.uml.InformationItem;
import org.eclipse.uml2.uml.InstanceValue;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionConstraint;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Interval;
import org.eclipse.uml2.uml.IntervalConstraint;
import org.eclipse.uml2.uml.LiteralBoolean;
import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralNull;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.Operation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageableElement;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.Port;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.ProtocolStateMachine;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Signal;
import org.eclipse.uml2.uml.SignalEvent;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.StringExpression;
import org.eclipse.uml2.uml.StructuredClassifier;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.TimeConstraint;
import org.eclipse.uml2.uml.TimeEvent;
import org.eclipse.uml2.uml.TimeExpression;
import org.eclipse.uml2.uml.TimeInterval;
import org.eclipse.uml2.uml.TimeObservation;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.UseCase;

/**
 * @generated
 */
public class UMLDiagramUpdater implements DiagramUpdater {

	/**
	 * @generated
	 */
	public static final org.eclipse.papyrus.uml.diagram.composite.part.UMLDiagramUpdater INSTANCE = new UMLDiagramUpdater();

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
			case CompositeStructureDiagramEditPart.VISUAL_ID:
				return getPackage_CompositeStructureDiagram_SemanticChildren(view);
			case ActivityCompositeEditPart.VISUAL_ID:
				return getActivity_Shape_SemanticChildren(view);
			case InteractionCompositeEditPart.VISUAL_ID:
				return getInteraction_Shape_SemanticChildren(view);
			case ProtocolStateMachineCompositeEditPart.VISUAL_ID:
				return getProtocolStateMachine_Shape_SemanticChildren(view);
			case StateMachineCompositeEditPart.VISUAL_ID:
				return getStateMachine_Shape_SemanticChildren(view);
			case FunctionBehaviorCompositeEditPart.VISUAL_ID:
				return getFunctionBehavior_Shape_SemanticChildren(view);
			case OpaqueBehaviorCompositeEditPart.VISUAL_ID:
				return getOpaqueBehavior_Shape_SemanticChildren(view);
			case ComponentCompositeEditPart.VISUAL_ID:
				return getComponent_Shape_SemanticChildren(view);
			case DeviceCompositeEditPart.VISUAL_ID:
				return getDevice_Shape_SemanticChildren(view);
			case ExecutionEnvironmentCompositeEditPart.VISUAL_ID:
				return getExecutionEnvironment_Shape_SemanticChildren(view);
			case NodeCompositeEditPart.VISUAL_ID:
				return getNode_Shape_SemanticChildren(view);
			case ClassCompositeEditPart.VISUAL_ID:
				return getClass_Shape_SemanticChildren(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_SemanticChildren(view);
			case PropertyPartEditPartCN.VISUAL_ID:
				return getProperty_Shape_SemanticChildren(view);
			case ActivityCompositeEditPartCN.VISUAL_ID:
				return getActivity_Shape_CN_SemanticChildren(view);
			case InteractionCompositeEditPartCN.VISUAL_ID:
				return getInteraction_Shape_CN_SemanticChildren(view);
			case ProtocolStateMachineCompositeEditPartCN.VISUAL_ID:
				return getProtocolStateMachine_Shape_CN_SemanticChildren(view);
			case StateMachineCompositeEditPartCN.VISUAL_ID:
				return getStateMachine_Shape_CN_SemanticChildren(view);
			case FunctionBehaviorCompositeEditPartCN.VISUAL_ID:
				return getFunctionBehavior_Shape_CN_SemanticChildren(view);
			case OpaqueBehaviorCompositeEditPartCN.VISUAL_ID:
				return getOpaqueBehavior_Shape_CN_SemanticChildren(view);
			case ComponentCompositeEditPartCN.VISUAL_ID:
				return getComponent_Shape_CN_SemanticChildren(view);
			case DeviceCompositeEditPartCN.VISUAL_ID:
				return getDevice_Shape_CN_SemanticChildren(view);
			case ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_Shape_CN_SemanticChildren(view);
			case NodeCompositeEditPartCN.VISUAL_ID:
				return getNode_Shape_CN_SemanticChildren(view);
			case ClassCompositeEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_SemanticChildren(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_SemanticChildren(view);
			case DataTypeAttributeCompartmentEditPart.VISUAL_ID:
				return getDataType_AttributeCompartment_SemanticChildren(view);
			case DataTypeOperationCompartmentEditPart.VISUAL_ID:
				return getDataType_OperationCompartment_SemanticChildren(view);
			case DataTypeAttributeCompartmentEditPartCN.VISUAL_ID:
				return getDataType_AttributeCompartment_CN_SemanticChildren(view);
			case DataTypeOperationCompartmentEditPartCN.VISUAL_ID:
				return getDataType_OperationCompartment_CN_SemanticChildren(view);
			case EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID:
				return getEnumeration_LiteralCompartment_SemanticChildren(view);
			case EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID:
				return getEnumeration_LiteralCompartment_CN_SemanticChildren(view);
			case ActivityCompositeCompartmentEditPartCN.VISUAL_ID:
				return getActivity_StructureCompartment_CN_SemanticChildren(view);
			case InteractionCompositeCompartmentEditPartCN.VISUAL_ID:
				return getInteraction_StructureCompartment_CN_SemanticChildren(view);
			case ProtocolStateMachineCompositeCompartmentEditPartCN.VISUAL_ID:
				return getProtocolStateMachine_StructureCompartment_CN_SemanticChildren(view);
			case StateMachineCompositeCompartmentEditPartCN.VISUAL_ID:
				return getStateMachine_StructureCompartment_CN_SemanticChildren(view);
			case FunctionBehaviorCompositeCompartmentEditPartCN.VISUAL_ID:
				return getFunctionBehavior_StructureCompartment_CN_SemanticChildren(view);
			case OpaqueBehaviorCompositeCompartmentEditPartCN.VISUAL_ID:
				return getOpaqueBehavior_StructureCompartment_CN_SemanticChildren(view);
			case ComponentCompositeCompartmentEditPartCN.VISUAL_ID:
				return getComponent_StructureCompartment_CN_SemanticChildren(view);
			case DeviceCompositeCompartmentEditPartCN.VISUAL_ID:
				return getDevice_StructureCompartment_CN_SemanticChildren(view);
			case ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_StructureCompartment_CN_SemanticChildren(view);
			case NodeCompositeCompartmentEditPartCN.VISUAL_ID:
				return getNode_StructureCompartment_CN_SemanticChildren(view);
			case ClassCompositeCompartmentEditPartCN.VISUAL_ID:
				return getClass_StructureCompartment_CN_SemanticChildren(view);
			case CollaborationCompositeCompartmentEditPartCN.VISUAL_ID:
				return getCollaboration_StructureCompartment_CN_SemanticChildren(view);
			case ActivityCompositeCompartmentEditPart.VISUAL_ID:
				return getActivity_StructureCompartment_SemanticChildren(view);
			case InteractionCompositeCompartmentEditPart.VISUAL_ID:
				return getInteraction_StructureCompartment_SemanticChildren(view);
			case ProtocolStateMachineCompositeCompartmentEditPart.VISUAL_ID:
				return getProtocolStateMachine_StructureCompartment_SemanticChildren(view);
			case StateMachineCompositeCompartmentEditPart.VISUAL_ID:
				return getStateMachine_StructureCompartment_SemanticChildren(view);
			case FunctionBehaviorCompositeCompartmentEditPart.VISUAL_ID:
				return getFunctionBehavior_StructureCompartment_SemanticChildren(view);
			case OpaqueBehaviorCompositeCompartmentEditPart.VISUAL_ID:
				return getOpaqueBehavior_StructureCompartment_SemanticChildren(view);
			case ComponentCompositeCompartmentEditPart.VISUAL_ID:
				return getComponent_StructureCompartment_SemanticChildren(view);
			case DeviceCompositeCompartmentEditPart.VISUAL_ID:
				return getDevice_StructureCompartment_SemanticChildren(view);
			case ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID:
				return getExecutionEnvironment_StructureCompartment_SemanticChildren(view);
			case NodeCompositeCompartmentEditPart.VISUAL_ID:
				return getNode_StructureCompartment_SemanticChildren(view);
			case ClassCompositeCompartmentEditPart.VISUAL_ID:
				return getClass_StructureCompartment_SemanticChildren(view);
			case CollaborationCompositeCompartmentEditPart.VISUAL_ID:
				return getCollaboration_StructureCompartment_SemanticChildren(view);
			case PropertyPartCompartmentEditPartCN.VISUAL_ID:
				return getProperty_StructureCompartment_SemanticChildren(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getPackage_CompositeStructureDiagram_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getPackagedElements()
				.iterator(); it.hasNext();) {
			PackageableElement childElement = (PackageableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEventEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CallEventEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (AnyReceiveEventEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ChangeEventEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeEventEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DurationObservationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeObservationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LiteralBooleanEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LiteralIntegerEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LiteralNullEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LiteralStringEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (LiteralUnlimitedNaturalEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StringExpressionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueExpressionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeExpressionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExpressionEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DurationEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeIntervalEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DurationIntervalEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InstanceValueEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPart.VISUAL_ID.equals(visualID)) {
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getInteraction_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Interaction modelElement = (Interaction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getProtocolStateMachine_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ProtocolStateMachine modelElement = (ProtocolStateMachine) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getStateMachine_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		StateMachine modelElement = (StateMachine) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getFunctionBehavior_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		FunctionBehavior modelElement = (FunctionBehavior) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getOpaqueBehavior_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		OpaqueBehavior modelElement = (OpaqueBehavior) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getComponent_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDevice_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getExecutionEnvironment_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getNode_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getEnumeration_Shape_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedLiterals()
				.iterator(); it.hasNext();) {
			EnumerationLiteral childElement = (EnumerationLiteral) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (EnumerationLiteralEditPartCLN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getProperty_Shape_SemanticChildren(View view) {
		ICustomDiagramUpdater<UMLNodeDescriptor> customUpdater = new PropertyDiagramUpdater();
		return customUpdater.getSemanticChildren(view);
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getInteraction_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Interaction modelElement = (Interaction) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getProtocolStateMachine_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ProtocolStateMachine modelElement = (ProtocolStateMachine) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getStateMachine_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		StateMachine modelElement = (StateMachine) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getFunctionBehavior_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		FunctionBehavior modelElement = (FunctionBehavior) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getOpaqueBehavior_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		OpaqueBehavior modelElement = (OpaqueBehavior) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
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
	public List<UMLNodeDescriptor> getComponent_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDevice_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getExecutionEnvironment_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getNode_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getEnumeration_Shape_CN_SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.emptyList();
		}
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedLiterals()
				.iterator(); it.hasNext();) {
			EnumerationLiteral childElement = (EnumerationLiteral) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (EnumerationLiteralEditPartCLN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_AttributeCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyEditPartCLN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_OperationCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationEditPartCLN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_AttributeCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyEditPartCLN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDataType_OperationCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		DataType modelElement = (DataType) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedOperations()
				.iterator(); it.hasNext();) {
			Operation childElement = (Operation) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (OperationEditPartCLN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getEnumeration_LiteralCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Enumeration modelElement = (Enumeration) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedLiterals()
				.iterator(); it.hasNext();) {
			EnumerationLiteral childElement = (EnumerationLiteral) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (EnumerationLiteralEditPartCLN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getEnumeration_LiteralCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Enumeration modelElement = (Enumeration) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedLiterals()
				.iterator(); it.hasNext();) {
			EnumerationLiteral childElement = (EnumerationLiteral) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (EnumerationLiteralEditPartCLN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getActivity_StructureCompartment_CN_SemanticChildren(View view) {
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInteraction_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Interaction modelElement = (Interaction) containerView.getElement();
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getProtocolStateMachine_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ProtocolStateMachine modelElement = (ProtocolStateMachine) containerView.getElement();
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getStateMachine_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		StateMachine modelElement = (StateMachine) containerView.getElement();
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getFunctionBehavior_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		FunctionBehavior modelElement = (FunctionBehavior) containerView.getElement();
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getOpaqueBehavior_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		OpaqueBehavior modelElement = (OpaqueBehavior) containerView.getElement();
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDevice_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Device modelElement = (Device) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getExecutionEnvironment_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ExecutionEnvironment modelElement = (ExecutionEnvironment) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getNode_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Node modelElement = (Node) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (BehaviorPortEditPart.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getCollaboration_StructureCompartment_CN_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Collaboration modelElement = (Collaboration) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getRoles()
				.iterator(); it.hasNext();) {
			ConnectableElement childElement = (ConnectableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationRoleEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getActivity_StructureCompartment_SemanticChildren(View view) {
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getInteraction_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Interaction modelElement = (Interaction) containerView.getElement();
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getProtocolStateMachine_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ProtocolStateMachine modelElement = (ProtocolStateMachine) containerView.getElement();
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getStateMachine_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		StateMachine modelElement = (StateMachine) containerView.getElement();
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getFunctionBehavior_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		FunctionBehavior modelElement = (FunctionBehavior) containerView.getElement();
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getOpaqueBehavior_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		OpaqueBehavior modelElement = (OpaqueBehavior) containerView.getElement();
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
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getComponent_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Component modelElement = (Component) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getDevice_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Device modelElement = (Device) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getExecutionEnvironment_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		ExecutionEnvironment modelElement = (ExecutionEnvironment) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getNode_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Node modelElement = (Node) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getClass_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Class modelElement = (Class) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getNestedClassifiers()
				.iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ActivityCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ProtocolStateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (StateMachineCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (FunctionBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (OpaqueBehaviorCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ComponentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeviceCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (NodeCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ClassCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (CollaborationCompositeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InterfaceEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (PrimitiveTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (EnumerationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DataTypeEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ActorEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DeploymentSpecificationEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ArtifactEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InformationItemEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (SignalEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (UseCaseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getCollaboration_StructureCompartment_SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.emptyList();
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.emptyList();
		}
		Collaboration modelElement = (Collaboration) containerView.getElement();
		LinkedList<UMLNodeDescriptor> result = new LinkedList<>();
		for (Iterator<?> it = modelElement.getOwnedAttributes()
				.iterator(); it.hasNext();) {
			Property childElement = (Property) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (PropertyPartEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getCollaborationUses()
				.iterator(); it.hasNext();) {
			CollaborationUse childElement = (CollaborationUse) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationUseEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedComments()
				.iterator(); it.hasNext();) {
			Comment childElement = (Comment) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CommentEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getRoles()
				.iterator(); it.hasNext();) {
			ConnectableElement childElement = (ConnectableElement) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (CollaborationRoleEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator<?> it = modelElement.getOwnedRules()
				.iterator(); it.hasNext();) {
			Constraint childElement = (Constraint) it.next();
			String visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (DurationConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (TimeConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (IntervalConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (InteractionConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (ConstraintEditPartCN.VISUAL_ID.equals(visualID)) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLNodeDescriptor> getProperty_StructureCompartment_SemanticChildren(View view) {
		ICustomDiagramUpdater<UMLNodeDescriptor> customUpdater = new PropertyDiagramUpdater();
		return customUpdater.getSemanticChildren(view);
	}

	/**
	 * @generated
	 */
	@Override
	public List<UMLLinkDescriptor> getContainedLinks(View view) {
		String vid = UMLVisualIDRegistry.getVisualID(view);
		if (vid != null) {
			switch (vid) {
			case CompositeStructureDiagramEditPart.VISUAL_ID:
				return getPackage_CompositeStructureDiagram_ContainedLinks(view);
			case ActivityCompositeEditPart.VISUAL_ID:
				return getActivity_Shape_ContainedLinks(view);
			case InteractionCompositeEditPart.VISUAL_ID:
				return getInteraction_Shape_ContainedLinks(view);
			case ProtocolStateMachineCompositeEditPart.VISUAL_ID:
				return getProtocolStateMachine_Shape_ContainedLinks(view);
			case StateMachineCompositeEditPart.VISUAL_ID:
				return getStateMachine_Shape_ContainedLinks(view);
			case FunctionBehaviorCompositeEditPart.VISUAL_ID:
				return getFunctionBehavior_Shape_ContainedLinks(view);
			case OpaqueBehaviorCompositeEditPart.VISUAL_ID:
				return getOpaqueBehavior_Shape_ContainedLinks(view);
			case ComponentCompositeEditPart.VISUAL_ID:
				return getComponent_Shape_ContainedLinks(view);
			case DeviceCompositeEditPart.VISUAL_ID:
				return getDevice_Shape_ContainedLinks(view);
			case ExecutionEnvironmentCompositeEditPart.VISUAL_ID:
				return getExecutionEnvironment_Shape_ContainedLinks(view);
			case NodeCompositeEditPart.VISUAL_ID:
				return getNode_Shape_ContainedLinks(view);
			case ClassCompositeEditPart.VISUAL_ID:
				return getClass_Shape_ContainedLinks(view);
			case CollaborationCompositeEditPart.VISUAL_ID:
				return getCollaboration_Shape_ContainedLinks(view);
			case InterfaceEditPart.VISUAL_ID:
				return getInterface_Shape_ContainedLinks(view);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return getPrimitiveType_Shape_ContainedLinks(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_ContainedLinks(view);
			case DataTypeEditPart.VISUAL_ID:
				return getDataType_Shape_ContainedLinks(view);
			case ActorEditPart.VISUAL_ID:
				return getActor_Shape_ContainedLinks(view);
			case DeploymentSpecificationEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_ContainedLinks(view);
			case ArtifactEditPart.VISUAL_ID:
				return getArtifact_Shape_ContainedLinks(view);
			case InformationItemEditPart.VISUAL_ID:
				return getInformationItem_Shape_ContainedLinks(view);
			case SignalEditPart.VISUAL_ID:
				return getSignal_Shape_ContainedLinks(view);
			case UseCaseEditPart.VISUAL_ID:
				return getUseCase_Shape_ContainedLinks(view);
			case SignalEventEditPart.VISUAL_ID:
				return getSignalEvent_Shape_ContainedLinks(view);
			case CallEventEditPart.VISUAL_ID:
				return getCallEvent_Shape_ContainedLinks(view);
			case AnyReceiveEventEditPart.VISUAL_ID:
				return getAnyReceiveEvent_Shape_ContainedLinks(view);
			case ChangeEventEditPart.VISUAL_ID:
				return getChangeEvent_Shape_ContainedLinks(view);
			case TimeEventEditPart.VISUAL_ID:
				return getTimeEvent_Shape_ContainedLinks(view);
			case DurationObservationEditPart.VISUAL_ID:
				return getDurationObservation_Shape_ContainedLinks(view);
			case TimeObservationEditPart.VISUAL_ID:
				return getTimeObservation_Shape_ContainedLinks(view);
			case LiteralBooleanEditPart.VISUAL_ID:
				return getLiteralBoolean_Shape_ContainedLinks(view);
			case LiteralIntegerEditPart.VISUAL_ID:
				return getLiteralInteger_Shape_ContainedLinks(view);
			case LiteralNullEditPart.VISUAL_ID:
				return getLiteralNull_Shape_ContainedLinks(view);
			case LiteralStringEditPart.VISUAL_ID:
				return getLiteralString_Shape_ContainedLinks(view);
			case LiteralUnlimitedNaturalEditPart.VISUAL_ID:
				return getLiteralUnlimitedNatural_Shape_ContainedLinks(view);
			case StringExpressionEditPart.VISUAL_ID:
				return getStringExpression_PackagedElementShape_ContainedLinks(view);
			case OpaqueExpressionEditPart.VISUAL_ID:
				return getOpaqueExpression_Shape_ContainedLinks(view);
			case TimeExpressionEditPart.VISUAL_ID:
				return getTimeExpression_Shape_ContainedLinks(view);
			case ExpressionEditPart.VISUAL_ID:
				return getExpression_Shape_ContainedLinks(view);
			case DurationEditPart.VISUAL_ID:
				return getDuration_Shape_ContainedLinks(view);
			case TimeIntervalEditPart.VISUAL_ID:
				return getTimeInterval_Shape_ContainedLinks(view);
			case DurationIntervalEditPart.VISUAL_ID:
				return getDurationInterval_Shape_ContainedLinks(view);
			case IntervalEditPart.VISUAL_ID:
				return getInterval_Shape_ContainedLinks(view);
			case InstanceValueEditPart.VISUAL_ID:
				return getInstanceValue_Shape_ContainedLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_ContainedLinks(view);
			case DurationConstraintEditPart.VISUAL_ID:
				return getDurationConstraint_Shape_ContainedLinks(view);
			case TimeConstraintEditPart.VISUAL_ID:
				return getTimeConstraint_Shape_ContainedLinks(view);
			case IntervalConstraintEditPart.VISUAL_ID:
				return getIntervalConstraint_Shape_ContainedLinks(view);
			case InteractionConstraintEditPart.VISUAL_ID:
				return getInteractionConstraint_Shape_ContainedLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_Shape_ContainedLinks(view);
			case BehaviorPortEditPart.VISUAL_ID:
				return getPort_BehaviorShape_ContainedLinks(view);
			case PortEditPart.VISUAL_ID:
				return getPort_Shape_ContainedLinks(view);
			case ParameterEditPart.VISUAL_ID:
				return getParameter_Shape_ContainedLinks(view);
			case PropertyPartEditPartCN.VISUAL_ID:
				return getProperty_Shape_ContainedLinks(view);
			case CollaborationRoleEditPartCN.VISUAL_ID:
				return getConnectableElement_CollaborationRoleShape_ContainedLinks(view);
			case CollaborationUseEditPartCN.VISUAL_ID:
				return getCollaborationUse_Shape_ContainedLinks(view);
			case ActivityCompositeEditPartCN.VISUAL_ID:
				return getActivity_Shape_CN_ContainedLinks(view);
			case InteractionCompositeEditPartCN.VISUAL_ID:
				return getInteraction_Shape_CN_ContainedLinks(view);
			case ProtocolStateMachineCompositeEditPartCN.VISUAL_ID:
				return getProtocolStateMachine_Shape_CN_ContainedLinks(view);
			case StateMachineCompositeEditPartCN.VISUAL_ID:
				return getStateMachine_Shape_CN_ContainedLinks(view);
			case FunctionBehaviorCompositeEditPartCN.VISUAL_ID:
				return getFunctionBehavior_Shape_CN_ContainedLinks(view);
			case OpaqueBehaviorCompositeEditPartCN.VISUAL_ID:
				return getOpaqueBehavior_Shape_CN_ContainedLinks(view);
			case ComponentCompositeEditPartCN.VISUAL_ID:
				return getComponent_Shape_CN_ContainedLinks(view);
			case DeviceCompositeEditPartCN.VISUAL_ID:
				return getDevice_Shape_CN_ContainedLinks(view);
			case ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_Shape_CN_ContainedLinks(view);
			case NodeCompositeEditPartCN.VISUAL_ID:
				return getNode_Shape_CN_ContainedLinks(view);
			case ClassCompositeEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_ContainedLinks(view);
			case CollaborationCompositeEditPartCN.VISUAL_ID:
				return getCollaboration_Shape_CN_ContainedLinks(view);
			case InterfaceEditPartCN.VISUAL_ID:
				return getInterface_Shape_CN_ContainedLinks(view);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return getPrimitiveType_Shape_CN_ContainedLinks(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_ContainedLinks(view);
			case DataTypeEditPartCN.VISUAL_ID:
				return getDataType_Shape_CN_ContainedLinks(view);
			case ActorEditPartCN.VISUAL_ID:
				return getActor_Shape_CN_ContainedLinks(view);
			case DeploymentSpecificationEditPartCN.VISUAL_ID:
				return getDeploymentSpecification_Shape_CN_ContainedLinks(view);
			case ArtifactEditPartCN.VISUAL_ID:
				return getArtifact_Shape_CN_ContainedLinks(view);
			case InformationItemEditPartCN.VISUAL_ID:
				return getInformationItem_Shape_CN_ContainedLinks(view);
			case SignalEditPartCN.VISUAL_ID:
				return getSignal_Shape_CN_ContainedLinks(view);
			case UseCaseEditPartCN.VISUAL_ID:
				return getUseCase_Shape_CN_ContainedLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_ContainedLinks(view);
			case DurationConstraintEditPartCN.VISUAL_ID:
				return getDurationConstraint_Shape_CN_ContainedLinks(view);
			case TimeConstraintEditPartCN.VISUAL_ID:
				return getTimeConstraint_Shape_CN_ContainedLinks(view);
			case IntervalConstraintEditPartCN.VISUAL_ID:
				return getIntervalConstraint_Shape_CN_ContainedLinks(view);
			case InteractionConstraintEditPartCN.VISUAL_ID:
				return getInteractionConstraint_Shape_CN_ContainedLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_Shape_CN_ContainedLinks(view);
			case PropertyEditPartCLN.VISUAL_ID:
				return getProperty_AttributeLabel_ContainedLinks(view);
			case OperationEditPartCLN.VISUAL_ID:
				return getOperation_OperationLabel_ContainedLinks(view);
			case EnumerationLiteralEditPartCLN.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_ContainedLinks(view);
			case ComponentRealizationEditPart.VISUAL_ID:
				return getComponentRealization_Edge_ContainedLinks(view);
			case InterfaceRealizationEditPart.VISUAL_ID:
				return getInterfaceRealization_Edge_ContainedLinks(view);
			case SubstitutionEditPart.VISUAL_ID:
				return getSubstitution_Edge_ContainedLinks(view);
			case RealizationEditPart.VISUAL_ID:
				return getRealization_Edge_ContainedLinks(view);
			case ManifestationEditPart.VISUAL_ID:
				return getManifestation_Edge_ContainedLinks(view);
			case AbstractionEditPart.VISUAL_ID:
				return getAbstraction_Edge_ContainedLinks(view);
			case UsageEditPart.VISUAL_ID:
				return getUsage_Edge_ContainedLinks(view);
			case DeploymentEditPart.VISUAL_ID:
				return getDeployment_Edge_ContainedLinks(view);
			case RoleBindingEditPart.VISUAL_ID:
				return getDependency_RoleBindingEdge_ContainedLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_ContainedLinks(view);
			case ConnectorEditPart.VISUAL_ID:
				return getConnector_Edge_ContainedLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_ContainedLinks(view);
			case InformationFlowEditPart.VISUAL_ID:
				return getInformationFlow_Edge_ContainedLinks(view);
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
			case ActivityCompositeEditPart.VISUAL_ID:
				return getActivity_Shape_IncomingLinks(view);
			case InteractionCompositeEditPart.VISUAL_ID:
				return getInteraction_Shape_IncomingLinks(view);
			case ProtocolStateMachineCompositeEditPart.VISUAL_ID:
				return getProtocolStateMachine_Shape_IncomingLinks(view);
			case StateMachineCompositeEditPart.VISUAL_ID:
				return getStateMachine_Shape_IncomingLinks(view);
			case FunctionBehaviorCompositeEditPart.VISUAL_ID:
				return getFunctionBehavior_Shape_IncomingLinks(view);
			case OpaqueBehaviorCompositeEditPart.VISUAL_ID:
				return getOpaqueBehavior_Shape_IncomingLinks(view);
			case ComponentCompositeEditPart.VISUAL_ID:
				return getComponent_Shape_IncomingLinks(view);
			case DeviceCompositeEditPart.VISUAL_ID:
				return getDevice_Shape_IncomingLinks(view);
			case ExecutionEnvironmentCompositeEditPart.VISUAL_ID:
				return getExecutionEnvironment_Shape_IncomingLinks(view);
			case NodeCompositeEditPart.VISUAL_ID:
				return getNode_Shape_IncomingLinks(view);
			case ClassCompositeEditPart.VISUAL_ID:
				return getClass_Shape_IncomingLinks(view);
			case CollaborationCompositeEditPart.VISUAL_ID:
				return getCollaboration_Shape_IncomingLinks(view);
			case InterfaceEditPart.VISUAL_ID:
				return getInterface_Shape_IncomingLinks(view);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return getPrimitiveType_Shape_IncomingLinks(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_IncomingLinks(view);
			case DataTypeEditPart.VISUAL_ID:
				return getDataType_Shape_IncomingLinks(view);
			case ActorEditPart.VISUAL_ID:
				return getActor_Shape_IncomingLinks(view);
			case DeploymentSpecificationEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_IncomingLinks(view);
			case ArtifactEditPart.VISUAL_ID:
				return getArtifact_Shape_IncomingLinks(view);
			case InformationItemEditPart.VISUAL_ID:
				return getInformationItem_Shape_IncomingLinks(view);
			case SignalEditPart.VISUAL_ID:
				return getSignal_Shape_IncomingLinks(view);
			case UseCaseEditPart.VISUAL_ID:
				return getUseCase_Shape_IncomingLinks(view);
			case SignalEventEditPart.VISUAL_ID:
				return getSignalEvent_Shape_IncomingLinks(view);
			case CallEventEditPart.VISUAL_ID:
				return getCallEvent_Shape_IncomingLinks(view);
			case AnyReceiveEventEditPart.VISUAL_ID:
				return getAnyReceiveEvent_Shape_IncomingLinks(view);
			case ChangeEventEditPart.VISUAL_ID:
				return getChangeEvent_Shape_IncomingLinks(view);
			case TimeEventEditPart.VISUAL_ID:
				return getTimeEvent_Shape_IncomingLinks(view);
			case DurationObservationEditPart.VISUAL_ID:
				return getDurationObservation_Shape_IncomingLinks(view);
			case TimeObservationEditPart.VISUAL_ID:
				return getTimeObservation_Shape_IncomingLinks(view);
			case LiteralBooleanEditPart.VISUAL_ID:
				return getLiteralBoolean_Shape_IncomingLinks(view);
			case LiteralIntegerEditPart.VISUAL_ID:
				return getLiteralInteger_Shape_IncomingLinks(view);
			case LiteralNullEditPart.VISUAL_ID:
				return getLiteralNull_Shape_IncomingLinks(view);
			case LiteralStringEditPart.VISUAL_ID:
				return getLiteralString_Shape_IncomingLinks(view);
			case LiteralUnlimitedNaturalEditPart.VISUAL_ID:
				return getLiteralUnlimitedNatural_Shape_IncomingLinks(view);
			case StringExpressionEditPart.VISUAL_ID:
				return getStringExpression_PackagedElementShape_IncomingLinks(view);
			case OpaqueExpressionEditPart.VISUAL_ID:
				return getOpaqueExpression_Shape_IncomingLinks(view);
			case TimeExpressionEditPart.VISUAL_ID:
				return getTimeExpression_Shape_IncomingLinks(view);
			case ExpressionEditPart.VISUAL_ID:
				return getExpression_Shape_IncomingLinks(view);
			case DurationEditPart.VISUAL_ID:
				return getDuration_Shape_IncomingLinks(view);
			case TimeIntervalEditPart.VISUAL_ID:
				return getTimeInterval_Shape_IncomingLinks(view);
			case DurationIntervalEditPart.VISUAL_ID:
				return getDurationInterval_Shape_IncomingLinks(view);
			case IntervalEditPart.VISUAL_ID:
				return getInterval_Shape_IncomingLinks(view);
			case InstanceValueEditPart.VISUAL_ID:
				return getInstanceValue_Shape_IncomingLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_IncomingLinks(view);
			case DurationConstraintEditPart.VISUAL_ID:
				return getDurationConstraint_Shape_IncomingLinks(view);
			case TimeConstraintEditPart.VISUAL_ID:
				return getTimeConstraint_Shape_IncomingLinks(view);
			case IntervalConstraintEditPart.VISUAL_ID:
				return getIntervalConstraint_Shape_IncomingLinks(view);
			case InteractionConstraintEditPart.VISUAL_ID:
				return getInteractionConstraint_Shape_IncomingLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_Shape_IncomingLinks(view);
			case BehaviorPortEditPart.VISUAL_ID:
				return getPort_BehaviorShape_IncomingLinks(view);
			case PortEditPart.VISUAL_ID:
				return getPort_Shape_IncomingLinks(view);
			case ParameterEditPart.VISUAL_ID:
				return getParameter_Shape_IncomingLinks(view);
			case PropertyPartEditPartCN.VISUAL_ID:
				return getProperty_Shape_IncomingLinks(view);
			case CollaborationRoleEditPartCN.VISUAL_ID:
				return getConnectableElement_CollaborationRoleShape_IncomingLinks(view);
			case CollaborationUseEditPartCN.VISUAL_ID:
				return getCollaborationUse_Shape_IncomingLinks(view);
			case ActivityCompositeEditPartCN.VISUAL_ID:
				return getActivity_Shape_CN_IncomingLinks(view);
			case InteractionCompositeEditPartCN.VISUAL_ID:
				return getInteraction_Shape_CN_IncomingLinks(view);
			case ProtocolStateMachineCompositeEditPartCN.VISUAL_ID:
				return getProtocolStateMachine_Shape_CN_IncomingLinks(view);
			case StateMachineCompositeEditPartCN.VISUAL_ID:
				return getStateMachine_Shape_CN_IncomingLinks(view);
			case FunctionBehaviorCompositeEditPartCN.VISUAL_ID:
				return getFunctionBehavior_Shape_CN_IncomingLinks(view);
			case OpaqueBehaviorCompositeEditPartCN.VISUAL_ID:
				return getOpaqueBehavior_Shape_CN_IncomingLinks(view);
			case ComponentCompositeEditPartCN.VISUAL_ID:
				return getComponent_Shape_CN_IncomingLinks(view);
			case DeviceCompositeEditPartCN.VISUAL_ID:
				return getDevice_Shape_CN_IncomingLinks(view);
			case ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_Shape_CN_IncomingLinks(view);
			case NodeCompositeEditPartCN.VISUAL_ID:
				return getNode_Shape_CN_IncomingLinks(view);
			case ClassCompositeEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_IncomingLinks(view);
			case CollaborationCompositeEditPartCN.VISUAL_ID:
				return getCollaboration_Shape_CN_IncomingLinks(view);
			case InterfaceEditPartCN.VISUAL_ID:
				return getInterface_Shape_CN_IncomingLinks(view);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return getPrimitiveType_Shape_CN_IncomingLinks(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_IncomingLinks(view);
			case DataTypeEditPartCN.VISUAL_ID:
				return getDataType_Shape_CN_IncomingLinks(view);
			case ActorEditPartCN.VISUAL_ID:
				return getActor_Shape_CN_IncomingLinks(view);
			case DeploymentSpecificationEditPartCN.VISUAL_ID:
				return getDeploymentSpecification_Shape_CN_IncomingLinks(view);
			case ArtifactEditPartCN.VISUAL_ID:
				return getArtifact_Shape_CN_IncomingLinks(view);
			case InformationItemEditPartCN.VISUAL_ID:
				return getInformationItem_Shape_CN_IncomingLinks(view);
			case SignalEditPartCN.VISUAL_ID:
				return getSignal_Shape_CN_IncomingLinks(view);
			case UseCaseEditPartCN.VISUAL_ID:
				return getUseCase_Shape_CN_IncomingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_IncomingLinks(view);
			case DurationConstraintEditPartCN.VISUAL_ID:
				return getDurationConstraint_Shape_CN_IncomingLinks(view);
			case TimeConstraintEditPartCN.VISUAL_ID:
				return getTimeConstraint_Shape_CN_IncomingLinks(view);
			case IntervalConstraintEditPartCN.VISUAL_ID:
				return getIntervalConstraint_Shape_CN_IncomingLinks(view);
			case InteractionConstraintEditPartCN.VISUAL_ID:
				return getInteractionConstraint_Shape_CN_IncomingLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_Shape_CN_IncomingLinks(view);
			case PropertyEditPartCLN.VISUAL_ID:
				return getProperty_AttributeLabel_IncomingLinks(view);
			case OperationEditPartCLN.VISUAL_ID:
				return getOperation_OperationLabel_IncomingLinks(view);
			case EnumerationLiteralEditPartCLN.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_IncomingLinks(view);
			case ComponentRealizationEditPart.VISUAL_ID:
				return getComponentRealization_Edge_IncomingLinks(view);
			case InterfaceRealizationEditPart.VISUAL_ID:
				return getInterfaceRealization_Edge_IncomingLinks(view);
			case SubstitutionEditPart.VISUAL_ID:
				return getSubstitution_Edge_IncomingLinks(view);
			case RealizationEditPart.VISUAL_ID:
				return getRealization_Edge_IncomingLinks(view);
			case ManifestationEditPart.VISUAL_ID:
				return getManifestation_Edge_IncomingLinks(view);
			case AbstractionEditPart.VISUAL_ID:
				return getAbstraction_Edge_IncomingLinks(view);
			case UsageEditPart.VISUAL_ID:
				return getUsage_Edge_IncomingLinks(view);
			case DeploymentEditPart.VISUAL_ID:
				return getDeployment_Edge_IncomingLinks(view);
			case RoleBindingEditPart.VISUAL_ID:
				return getDependency_RoleBindingEdge_IncomingLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_IncomingLinks(view);
			case ConnectorEditPart.VISUAL_ID:
				return getConnector_Edge_IncomingLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_IncomingLinks(view);
			case InformationFlowEditPart.VISUAL_ID:
				return getInformationFlow_Edge_IncomingLinks(view);
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
			case ActivityCompositeEditPart.VISUAL_ID:
				return getActivity_Shape_OutgoingLinks(view);
			case InteractionCompositeEditPart.VISUAL_ID:
				return getInteraction_Shape_OutgoingLinks(view);
			case ProtocolStateMachineCompositeEditPart.VISUAL_ID:
				return getProtocolStateMachine_Shape_OutgoingLinks(view);
			case StateMachineCompositeEditPart.VISUAL_ID:
				return getStateMachine_Shape_OutgoingLinks(view);
			case FunctionBehaviorCompositeEditPart.VISUAL_ID:
				return getFunctionBehavior_Shape_OutgoingLinks(view);
			case OpaqueBehaviorCompositeEditPart.VISUAL_ID:
				return getOpaqueBehavior_Shape_OutgoingLinks(view);
			case ComponentCompositeEditPart.VISUAL_ID:
				return getComponent_Shape_OutgoingLinks(view);
			case DeviceCompositeEditPart.VISUAL_ID:
				return getDevice_Shape_OutgoingLinks(view);
			case ExecutionEnvironmentCompositeEditPart.VISUAL_ID:
				return getExecutionEnvironment_Shape_OutgoingLinks(view);
			case NodeCompositeEditPart.VISUAL_ID:
				return getNode_Shape_OutgoingLinks(view);
			case ClassCompositeEditPart.VISUAL_ID:
				return getClass_Shape_OutgoingLinks(view);
			case CollaborationCompositeEditPart.VISUAL_ID:
				return getCollaboration_Shape_OutgoingLinks(view);
			case InterfaceEditPart.VISUAL_ID:
				return getInterface_Shape_OutgoingLinks(view);
			case PrimitiveTypeEditPart.VISUAL_ID:
				return getPrimitiveType_Shape_OutgoingLinks(view);
			case EnumerationEditPart.VISUAL_ID:
				return getEnumeration_Shape_OutgoingLinks(view);
			case DataTypeEditPart.VISUAL_ID:
				return getDataType_Shape_OutgoingLinks(view);
			case ActorEditPart.VISUAL_ID:
				return getActor_Shape_OutgoingLinks(view);
			case DeploymentSpecificationEditPart.VISUAL_ID:
				return getDeploymentSpecification_Shape_OutgoingLinks(view);
			case ArtifactEditPart.VISUAL_ID:
				return getArtifact_Shape_OutgoingLinks(view);
			case InformationItemEditPart.VISUAL_ID:
				return getInformationItem_Shape_OutgoingLinks(view);
			case SignalEditPart.VISUAL_ID:
				return getSignal_Shape_OutgoingLinks(view);
			case UseCaseEditPart.VISUAL_ID:
				return getUseCase_Shape_OutgoingLinks(view);
			case SignalEventEditPart.VISUAL_ID:
				return getSignalEvent_Shape_OutgoingLinks(view);
			case CallEventEditPart.VISUAL_ID:
				return getCallEvent_Shape_OutgoingLinks(view);
			case AnyReceiveEventEditPart.VISUAL_ID:
				return getAnyReceiveEvent_Shape_OutgoingLinks(view);
			case ChangeEventEditPart.VISUAL_ID:
				return getChangeEvent_Shape_OutgoingLinks(view);
			case TimeEventEditPart.VISUAL_ID:
				return getTimeEvent_Shape_OutgoingLinks(view);
			case DurationObservationEditPart.VISUAL_ID:
				return getDurationObservation_Shape_OutgoingLinks(view);
			case TimeObservationEditPart.VISUAL_ID:
				return getTimeObservation_Shape_OutgoingLinks(view);
			case LiteralBooleanEditPart.VISUAL_ID:
				return getLiteralBoolean_Shape_OutgoingLinks(view);
			case LiteralIntegerEditPart.VISUAL_ID:
				return getLiteralInteger_Shape_OutgoingLinks(view);
			case LiteralNullEditPart.VISUAL_ID:
				return getLiteralNull_Shape_OutgoingLinks(view);
			case LiteralStringEditPart.VISUAL_ID:
				return getLiteralString_Shape_OutgoingLinks(view);
			case LiteralUnlimitedNaturalEditPart.VISUAL_ID:
				return getLiteralUnlimitedNatural_Shape_OutgoingLinks(view);
			case StringExpressionEditPart.VISUAL_ID:
				return getStringExpression_PackagedElementShape_OutgoingLinks(view);
			case OpaqueExpressionEditPart.VISUAL_ID:
				return getOpaqueExpression_Shape_OutgoingLinks(view);
			case TimeExpressionEditPart.VISUAL_ID:
				return getTimeExpression_Shape_OutgoingLinks(view);
			case ExpressionEditPart.VISUAL_ID:
				return getExpression_Shape_OutgoingLinks(view);
			case DurationEditPart.VISUAL_ID:
				return getDuration_Shape_OutgoingLinks(view);
			case TimeIntervalEditPart.VISUAL_ID:
				return getTimeInterval_Shape_OutgoingLinks(view);
			case DurationIntervalEditPart.VISUAL_ID:
				return getDurationInterval_Shape_OutgoingLinks(view);
			case IntervalEditPart.VISUAL_ID:
				return getInterval_Shape_OutgoingLinks(view);
			case InstanceValueEditPart.VISUAL_ID:
				return getInstanceValue_Shape_OutgoingLinks(view);
			case CommentEditPart.VISUAL_ID:
				return getComment_Shape_OutgoingLinks(view);
			case DurationConstraintEditPart.VISUAL_ID:
				return getDurationConstraint_Shape_OutgoingLinks(view);
			case TimeConstraintEditPart.VISUAL_ID:
				return getTimeConstraint_Shape_OutgoingLinks(view);
			case IntervalConstraintEditPart.VISUAL_ID:
				return getIntervalConstraint_Shape_OutgoingLinks(view);
			case InteractionConstraintEditPart.VISUAL_ID:
				return getInteractionConstraint_Shape_OutgoingLinks(view);
			case ConstraintEditPart.VISUAL_ID:
				return getConstraint_Shape_OutgoingLinks(view);
			case BehaviorPortEditPart.VISUAL_ID:
				return getPort_BehaviorShape_OutgoingLinks(view);
			case PortEditPart.VISUAL_ID:
				return getPort_Shape_OutgoingLinks(view);
			case ParameterEditPart.VISUAL_ID:
				return getParameter_Shape_OutgoingLinks(view);
			case PropertyPartEditPartCN.VISUAL_ID:
				return getProperty_Shape_OutgoingLinks(view);
			case CollaborationRoleEditPartCN.VISUAL_ID:
				return getConnectableElement_CollaborationRoleShape_OutgoingLinks(view);
			case CollaborationUseEditPartCN.VISUAL_ID:
				return getCollaborationUse_Shape_OutgoingLinks(view);
			case ActivityCompositeEditPartCN.VISUAL_ID:
				return getActivity_Shape_CN_OutgoingLinks(view);
			case InteractionCompositeEditPartCN.VISUAL_ID:
				return getInteraction_Shape_CN_OutgoingLinks(view);
			case ProtocolStateMachineCompositeEditPartCN.VISUAL_ID:
				return getProtocolStateMachine_Shape_CN_OutgoingLinks(view);
			case StateMachineCompositeEditPartCN.VISUAL_ID:
				return getStateMachine_Shape_CN_OutgoingLinks(view);
			case FunctionBehaviorCompositeEditPartCN.VISUAL_ID:
				return getFunctionBehavior_Shape_CN_OutgoingLinks(view);
			case OpaqueBehaviorCompositeEditPartCN.VISUAL_ID:
				return getOpaqueBehavior_Shape_CN_OutgoingLinks(view);
			case ComponentCompositeEditPartCN.VISUAL_ID:
				return getComponent_Shape_CN_OutgoingLinks(view);
			case DeviceCompositeEditPartCN.VISUAL_ID:
				return getDevice_Shape_CN_OutgoingLinks(view);
			case ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID:
				return getExecutionEnvironment_Shape_CN_OutgoingLinks(view);
			case NodeCompositeEditPartCN.VISUAL_ID:
				return getNode_Shape_CN_OutgoingLinks(view);
			case ClassCompositeEditPartCN.VISUAL_ID:
				return getClass_Shape_CN_OutgoingLinks(view);
			case CollaborationCompositeEditPartCN.VISUAL_ID:
				return getCollaboration_Shape_CN_OutgoingLinks(view);
			case InterfaceEditPartCN.VISUAL_ID:
				return getInterface_Shape_CN_OutgoingLinks(view);
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return getPrimitiveType_Shape_CN_OutgoingLinks(view);
			case EnumerationEditPartCN.VISUAL_ID:
				return getEnumeration_Shape_CN_OutgoingLinks(view);
			case DataTypeEditPartCN.VISUAL_ID:
				return getDataType_Shape_CN_OutgoingLinks(view);
			case ActorEditPartCN.VISUAL_ID:
				return getActor_Shape_CN_OutgoingLinks(view);
			case DeploymentSpecificationEditPartCN.VISUAL_ID:
				return getDeploymentSpecification_Shape_CN_OutgoingLinks(view);
			case ArtifactEditPartCN.VISUAL_ID:
				return getArtifact_Shape_CN_OutgoingLinks(view);
			case InformationItemEditPartCN.VISUAL_ID:
				return getInformationItem_Shape_CN_OutgoingLinks(view);
			case SignalEditPartCN.VISUAL_ID:
				return getSignal_Shape_CN_OutgoingLinks(view);
			case UseCaseEditPartCN.VISUAL_ID:
				return getUseCase_Shape_CN_OutgoingLinks(view);
			case CommentEditPartCN.VISUAL_ID:
				return getComment_Shape_CN_OutgoingLinks(view);
			case DurationConstraintEditPartCN.VISUAL_ID:
				return getDurationConstraint_Shape_CN_OutgoingLinks(view);
			case TimeConstraintEditPartCN.VISUAL_ID:
				return getTimeConstraint_Shape_CN_OutgoingLinks(view);
			case IntervalConstraintEditPartCN.VISUAL_ID:
				return getIntervalConstraint_Shape_CN_OutgoingLinks(view);
			case InteractionConstraintEditPartCN.VISUAL_ID:
				return getInteractionConstraint_Shape_CN_OutgoingLinks(view);
			case ConstraintEditPartCN.VISUAL_ID:
				return getConstraint_Shape_CN_OutgoingLinks(view);
			case PropertyEditPartCLN.VISUAL_ID:
				return getProperty_AttributeLabel_OutgoingLinks(view);
			case OperationEditPartCLN.VISUAL_ID:
				return getOperation_OperationLabel_OutgoingLinks(view);
			case EnumerationLiteralEditPartCLN.VISUAL_ID:
				return getEnumerationLiteral_LiteralLabel_OutgoingLinks(view);
			case ComponentRealizationEditPart.VISUAL_ID:
				return getComponentRealization_Edge_OutgoingLinks(view);
			case InterfaceRealizationEditPart.VISUAL_ID:
				return getInterfaceRealization_Edge_OutgoingLinks(view);
			case SubstitutionEditPart.VISUAL_ID:
				return getSubstitution_Edge_OutgoingLinks(view);
			case RealizationEditPart.VISUAL_ID:
				return getRealization_Edge_OutgoingLinks(view);
			case ManifestationEditPart.VISUAL_ID:
				return getManifestation_Edge_OutgoingLinks(view);
			case AbstractionEditPart.VISUAL_ID:
				return getAbstraction_Edge_OutgoingLinks(view);
			case UsageEditPart.VISUAL_ID:
				return getUsage_Edge_OutgoingLinks(view);
			case DeploymentEditPart.VISUAL_ID:
				return getDeployment_Edge_OutgoingLinks(view);
			case RoleBindingEditPart.VISUAL_ID:
				return getDependency_RoleBindingEdge_OutgoingLinks(view);
			case DependencyEditPart.VISUAL_ID:
				return getDependency_Edge_OutgoingLinks(view);
			case ConnectorEditPart.VISUAL_ID:
				return getConnector_Edge_OutgoingLinks(view);
			case GeneralizationEditPart.VISUAL_ID:
				return getGeneralization_Edge_OutgoingLinks(view);
			case InformationFlowEditPart.VISUAL_ID:
				return getInformationFlow_Edge_OutgoingLinks(view);
			}
		}
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPackage_CompositeStructureDiagram_ContainedLinks(View view) {
		Package modelElement = (Package) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivity_Shape_ContainedLinks(View view) {
		Activity modelElement = (Activity) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteraction_Shape_ContainedLinks(View view) {
		Interaction modelElement = (Interaction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProtocolStateMachine_Shape_ContainedLinks(View view) {
		ProtocolStateMachine modelElement = (ProtocolStateMachine) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateMachine_Shape_ContainedLinks(View view) {
		StateMachine modelElement = (StateMachine) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getFunctionBehavior_Shape_ContainedLinks(View view) {
		FunctionBehavior modelElement = (FunctionBehavior) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueBehavior_Shape_ContainedLinks(View view) {
		OpaqueBehavior modelElement = (OpaqueBehavior) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_ContainedLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_ContainedLinks(View view) {
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_ContainedLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_ContainedLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCollaboration_Shape_ContainedLinks(View view) {
		Collaboration modelElement = (Collaboration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_ContainedLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_ContainedLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_ContainedLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_ContainedLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_ContainedLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_ContainedLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_ContainedLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_ContainedLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Representation_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_ContainedLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_ContainedLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignalEvent_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCallEvent_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAnyReceiveEvent_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getChangeEvent_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeEvent_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationObservation_Shape_ContainedLinks(View view) {
		DurationObservation modelElement = (DurationObservation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeObservation_Shape_ContainedLinks(View view) {
		TimeObservation modelElement = (TimeObservation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralBoolean_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralInteger_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralNull_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralString_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralUnlimitedNatural_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStringExpression_PackagedElementShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueExpression_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeExpression_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpression_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDuration_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeInterval_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationInterval_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterval_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceValue_Shape_ContainedLinks(View view) {
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
	public List<UMLLinkDescriptor> getDurationConstraint_Shape_ContainedLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_Shape_ContainedLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_Shape_ContainedLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteractionConstraint_Shape_ContainedLinks(View view) {
		InteractionConstraint modelElement = (InteractionConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
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
	public List<UMLLinkDescriptor> getPort_BehaviorShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPort_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getParameter_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConnectableElement_CollaborationRoleShape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCollaborationUse_Shape_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivity_Shape_CN_ContainedLinks(View view) {
		Activity modelElement = (Activity) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteraction_Shape_CN_ContainedLinks(View view) {
		Interaction modelElement = (Interaction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProtocolStateMachine_Shape_CN_ContainedLinks(View view) {
		ProtocolStateMachine modelElement = (ProtocolStateMachine) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateMachine_Shape_CN_ContainedLinks(View view) {
		StateMachine modelElement = (StateMachine) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getFunctionBehavior_Shape_CN_ContainedLinks(View view) {
		FunctionBehavior modelElement = (FunctionBehavior) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueBehavior_Shape_CN_ContainedLinks(View view) {
		OpaqueBehavior modelElement = (OpaqueBehavior) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CN_ContainedLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_CN_ContainedLinks(View view) {
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_CN_ContainedLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_CN_ContainedLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_CN_ContainedLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCollaboration_Shape_CN_ContainedLinks(View view) {
		Collaboration modelElement = (Collaboration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Connector_Edge(modelElement));
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_CN_ContainedLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_CN_ContainedLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_CN_ContainedLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_CN_ContainedLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_CN_ContainedLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_CN_ContainedLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_CN_ContainedLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_CN_ContainedLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Representation_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_CN_ContainedLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_CN_ContainedLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getContainedTypeModelFacetLinks_Generalization_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_ContainedLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_Shape_CN_ContainedLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_Shape_CN_ContainedLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_Shape_CN_ContainedLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteractionConstraint_Shape_CN_ContainedLinks(View view) {
		InteractionConstraint modelElement = (InteractionConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_CN_ContainedLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_AttributeLabel_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_OperationLabel_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumerationLiteral_LiteralLabel_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponentRealization_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterfaceRealization_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSubstitution_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRealization_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getManifestation_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAbstraction_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUsage_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeployment_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_RoleBindingEdge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConnector_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_ContainedLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationFlow_Edge_ContainedLinks(View view) {
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
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteraction_Shape_IncomingLinks(View view) {
		Interaction modelElement = (Interaction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProtocolStateMachine_Shape_IncomingLinks(View view) {
		ProtocolStateMachine modelElement = (ProtocolStateMachine) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateMachine_Shape_IncomingLinks(View view) {
		StateMachine modelElement = (StateMachine) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getFunctionBehavior_Shape_IncomingLinks(View view) {
		FunctionBehavior modelElement = (FunctionBehavior) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueBehavior_Shape_IncomingLinks(View view) {
		OpaqueBehavior modelElement = (OpaqueBehavior) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_IncomingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_IncomingLinks(View view) {
		Device modelElement = (Device) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_IncomingLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_IncomingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_IncomingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCollaboration_Shape_IncomingLinks(View view) {
		Collaboration modelElement = (Collaboration) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_IncomingLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_IncomingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_IncomingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_IncomingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_IncomingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_IncomingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_IncomingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_IncomingLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_IncomingLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_IncomingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignalEvent_Shape_IncomingLinks(View view) {
		SignalEvent modelElement = (SignalEvent) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCallEvent_Shape_IncomingLinks(View view) {
		CallEvent modelElement = (CallEvent) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAnyReceiveEvent_Shape_IncomingLinks(View view) {
		AnyReceiveEvent modelElement = (AnyReceiveEvent) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getChangeEvent_Shape_IncomingLinks(View view) {
		ChangeEvent modelElement = (ChangeEvent) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeEvent_Shape_IncomingLinks(View view) {
		TimeEvent modelElement = (TimeEvent) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationObservation_Shape_IncomingLinks(View view) {
		DurationObservation modelElement = (DurationObservation) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeObservation_Shape_IncomingLinks(View view) {
		TimeObservation modelElement = (TimeObservation) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralBoolean_Shape_IncomingLinks(View view) {
		LiteralBoolean modelElement = (LiteralBoolean) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralInteger_Shape_IncomingLinks(View view) {
		LiteralInteger modelElement = (LiteralInteger) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralNull_Shape_IncomingLinks(View view) {
		LiteralNull modelElement = (LiteralNull) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralString_Shape_IncomingLinks(View view) {
		LiteralString modelElement = (LiteralString) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralUnlimitedNatural_Shape_IncomingLinks(View view) {
		LiteralUnlimitedNatural modelElement = (LiteralUnlimitedNatural) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStringExpression_PackagedElementShape_IncomingLinks(View view) {
		StringExpression modelElement = (StringExpression) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueExpression_Shape_IncomingLinks(View view) {
		OpaqueExpression modelElement = (OpaqueExpression) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeExpression_Shape_IncomingLinks(View view) {
		TimeExpression modelElement = (TimeExpression) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpression_Shape_IncomingLinks(View view) {
		Expression modelElement = (Expression) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDuration_Shape_IncomingLinks(View view) {
		Duration modelElement = (Duration) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeInterval_Shape_IncomingLinks(View view) {
		TimeInterval modelElement = (TimeInterval) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationInterval_Shape_IncomingLinks(View view) {
		DurationInterval modelElement = (DurationInterval) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterval_Shape_IncomingLinks(View view) {
		Interval modelElement = (Interval) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceValue_Shape_IncomingLinks(View view) {
		InstanceValue modelElement = (InstanceValue) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
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
	public List<UMLLinkDescriptor> getDurationConstraint_Shape_IncomingLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_Shape_IncomingLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_Shape_IncomingLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteractionConstraint_Shape_IncomingLinks(View view) {
		InteractionConstraint modelElement = (InteractionConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPort_BehaviorShape_IncomingLinks(View view) {
		Port modelElement = (Port) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPort_Shape_IncomingLinks(View view) {
		Port modelElement = (Port) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getParameter_Shape_IncomingLinks(View view) {
		Parameter modelElement = (Parameter) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_Shape_IncomingLinks(View view) {
		Property modelElement = (Property) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConnectableElement_CollaborationRoleShape_IncomingLinks(View view) {
		ConnectableElement modelElement = (ConnectableElement) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCollaborationUse_Shape_IncomingLinks(View view) {
		CollaborationUse modelElement = (CollaborationUse) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
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
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteraction_Shape_CN_IncomingLinks(View view) {
		Interaction modelElement = (Interaction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProtocolStateMachine_Shape_CN_IncomingLinks(View view) {
		ProtocolStateMachine modelElement = (ProtocolStateMachine) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateMachine_Shape_CN_IncomingLinks(View view) {
		StateMachine modelElement = (StateMachine) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getFunctionBehavior_Shape_CN_IncomingLinks(View view) {
		FunctionBehavior modelElement = (FunctionBehavior) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueBehavior_Shape_CN_IncomingLinks(View view) {
		OpaqueBehavior modelElement = (OpaqueBehavior) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CN_IncomingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_CN_IncomingLinks(View view) {
		Device modelElement = (Device) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_CN_IncomingLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_CN_IncomingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_CN_IncomingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCollaboration_Shape_CN_IncomingLinks(View view) {
		Collaboration modelElement = (Collaboration) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_CN_IncomingLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_CN_IncomingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_CN_IncomingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_CN_IncomingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_CN_IncomingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_CN_IncomingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_CN_IncomingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_CN_IncomingLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_CN_IncomingLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_CN_IncomingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Generalization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Representation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_IncomingLinks(View view) {
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
	public List<UMLLinkDescriptor> getDurationConstraint_Shape_CN_IncomingLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_Shape_CN_IncomingLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_Shape_CN_IncomingLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteractionConstraint_Shape_CN_IncomingLinks(View view) {
		InteractionConstraint modelElement = (InteractionConstraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_CN_IncomingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_AttributeLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_OperationLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumerationLiteral_LiteralLabel_IncomingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponentRealization_Edge_IncomingLinks(View view) {
		ComponentRealization modelElement = (ComponentRealization) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterfaceRealization_Edge_IncomingLinks(View view) {
		InterfaceRealization modelElement = (InterfaceRealization) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSubstitution_Edge_IncomingLinks(View view) {
		Substitution modelElement = (Substitution) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRealization_Edge_IncomingLinks(View view) {
		Realization modelElement = (Realization) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getManifestation_Edge_IncomingLinks(View view) {
		Manifestation modelElement = (Manifestation) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAbstraction_Edge_IncomingLinks(View view) {
		Abstraction modelElement = (Abstraction) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUsage_Edge_IncomingLinks(View view) {
		Usage modelElement = (Usage) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeployment_Edge_IncomingLinks(View view) {
		Deployment modelElement = (Deployment) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_RoleBindingEdge_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_IncomingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConnector_Edge_IncomingLinks(View view) {
		Connector modelElement = (Connector) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_IncomingLinks(View view) {
		Generalization modelElement = (Generalization) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationFlow_Edge_IncomingLinks(View view) {
		InformationFlow modelElement = (InformationFlow) view.getElement();
		CrossReferenceAdapter crossReferencer = CrossReferenceAdapter.getCrossReferenceAdapter(view.eResource().getResourceSet());
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_ComponentRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Substitution_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Realization_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Manifestation_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Abstraction_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Usage_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Deployment_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_Dependency_Edge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement, crossReferencer));
		result.addAll(getIncomingTypeModelFacetLinks_InformationFlow_Edge(modelElement, crossReferencer));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivity_Shape_OutgoingLinks(View view) {
		Activity modelElement = (Activity) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteraction_Shape_OutgoingLinks(View view) {
		Interaction modelElement = (Interaction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProtocolStateMachine_Shape_OutgoingLinks(View view) {
		ProtocolStateMachine modelElement = (ProtocolStateMachine) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateMachine_Shape_OutgoingLinks(View view) {
		StateMachine modelElement = (StateMachine) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getFunctionBehavior_Shape_OutgoingLinks(View view) {
		FunctionBehavior modelElement = (FunctionBehavior) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueBehavior_Shape_OutgoingLinks(View view) {
		OpaqueBehavior modelElement = (OpaqueBehavior) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_OutgoingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_OutgoingLinks(View view) {
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_OutgoingLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_OutgoingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_OutgoingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCollaboration_Shape_OutgoingLinks(View view) {
		Collaboration modelElement = (Collaboration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_OutgoingLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_OutgoingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_OutgoingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_OutgoingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_OutgoingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_OutgoingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_OutgoingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_OutgoingLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Representation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_OutgoingLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_OutgoingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignalEvent_Shape_OutgoingLinks(View view) {
		SignalEvent modelElement = (SignalEvent) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCallEvent_Shape_OutgoingLinks(View view) {
		CallEvent modelElement = (CallEvent) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAnyReceiveEvent_Shape_OutgoingLinks(View view) {
		AnyReceiveEvent modelElement = (AnyReceiveEvent) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getChangeEvent_Shape_OutgoingLinks(View view) {
		ChangeEvent modelElement = (ChangeEvent) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeEvent_Shape_OutgoingLinks(View view) {
		TimeEvent modelElement = (TimeEvent) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationObservation_Shape_OutgoingLinks(View view) {
		DurationObservation modelElement = (DurationObservation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_DurationObservation_EventEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeObservation_Shape_OutgoingLinks(View view) {
		TimeObservation modelElement = (TimeObservation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_TimeObservation_EventEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralBoolean_Shape_OutgoingLinks(View view) {
		LiteralBoolean modelElement = (LiteralBoolean) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralInteger_Shape_OutgoingLinks(View view) {
		LiteralInteger modelElement = (LiteralInteger) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralNull_Shape_OutgoingLinks(View view) {
		LiteralNull modelElement = (LiteralNull) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralString_Shape_OutgoingLinks(View view) {
		LiteralString modelElement = (LiteralString) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getLiteralUnlimitedNatural_Shape_OutgoingLinks(View view) {
		LiteralUnlimitedNatural modelElement = (LiteralUnlimitedNatural) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStringExpression_PackagedElementShape_OutgoingLinks(View view) {
		StringExpression modelElement = (StringExpression) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueExpression_Shape_OutgoingLinks(View view) {
		OpaqueExpression modelElement = (OpaqueExpression) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeExpression_Shape_OutgoingLinks(View view) {
		TimeExpression modelElement = (TimeExpression) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExpression_Shape_OutgoingLinks(View view) {
		Expression modelElement = (Expression) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDuration_Shape_OutgoingLinks(View view) {
		Duration modelElement = (Duration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeInterval_Shape_OutgoingLinks(View view) {
		TimeInterval modelElement = (TimeInterval) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationInterval_Shape_OutgoingLinks(View view) {
		DurationInterval modelElement = (DurationInterval) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterval_Shape_OutgoingLinks(View view) {
		Interval modelElement = (Interval) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInstanceValue_Shape_OutgoingLinks(View view) {
		InstanceValue modelElement = (InstanceValue) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
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
	public List<UMLLinkDescriptor> getDurationConstraint_Shape_OutgoingLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_Shape_OutgoingLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_Shape_OutgoingLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteractionConstraint_Shape_OutgoingLinks(View view) {
		InteractionConstraint modelElement = (InteractionConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPort_BehaviorShape_OutgoingLinks(View view) {
		Port modelElement = (Port) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPort_Shape_OutgoingLinks(View view) {
		Port modelElement = (Port) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getParameter_Shape_OutgoingLinks(View view) {
		Parameter modelElement = (Parameter) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_Shape_OutgoingLinks(View view) {
		Property modelElement = (Property) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConnectableElement_CollaborationRoleShape_OutgoingLinks(View view) {
		ConnectableElement modelElement = (ConnectableElement) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCollaborationUse_Shape_OutgoingLinks(View view) {
		CollaborationUse modelElement = (CollaborationUse) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActivity_Shape_CN_OutgoingLinks(View view) {
		Activity modelElement = (Activity) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteraction_Shape_CN_OutgoingLinks(View view) {
		Interaction modelElement = (Interaction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProtocolStateMachine_Shape_CN_OutgoingLinks(View view) {
		ProtocolStateMachine modelElement = (ProtocolStateMachine) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getStateMachine_Shape_CN_OutgoingLinks(View view) {
		StateMachine modelElement = (StateMachine) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getFunctionBehavior_Shape_CN_OutgoingLinks(View view) {
		FunctionBehavior modelElement = (FunctionBehavior) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOpaqueBehavior_Shape_CN_OutgoingLinks(View view) {
		OpaqueBehavior modelElement = (OpaqueBehavior) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponent_Shape_CN_OutgoingLinks(View view) {
		Component modelElement = (Component) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDevice_Shape_CN_OutgoingLinks(View view) {
		Device modelElement = (Device) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getExecutionEnvironment_Shape_CN_OutgoingLinks(View view) {
		ExecutionEnvironment modelElement = (ExecutionEnvironment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getNode_Shape_CN_OutgoingLinks(View view) {
		Node modelElement = (Node) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getClass_Shape_CN_OutgoingLinks(View view) {
		Class modelElement = (Class) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getCollaboration_Shape_CN_OutgoingLinks(View view) {
		Collaboration modelElement = (Collaboration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterface_Shape_CN_OutgoingLinks(View view) {
		Interface modelElement = (Interface) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getPrimitiveType_Shape_CN_OutgoingLinks(View view) {
		PrimitiveType modelElement = (PrimitiveType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumeration_Shape_CN_OutgoingLinks(View view) {
		Enumeration modelElement = (Enumeration) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDataType_Shape_CN_OutgoingLinks(View view) {
		DataType modelElement = (DataType) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getActor_Shape_CN_OutgoingLinks(View view) {
		Actor modelElement = (Actor) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeploymentSpecification_Shape_CN_OutgoingLinks(View view) {
		DeploymentSpecification modelElement = (DeploymentSpecification) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getArtifact_Shape_CN_OutgoingLinks(View view) {
		Artifact modelElement = (Artifact) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationItem_Shape_CN_OutgoingLinks(View view) {
		InformationItem modelElement = (InformationItem) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingFeatureModelFacetLinks_Representation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSignal_Shape_CN_OutgoingLinks(View view) {
		Signal modelElement = (Signal) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUseCase_Shape_CN_OutgoingLinks(View view) {
		UseCase modelElement = (UseCase) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Generalization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComment_Shape_CN_OutgoingLinks(View view) {
		Comment modelElement = (Comment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDurationConstraint_Shape_CN_OutgoingLinks(View view) {
		DurationConstraint modelElement = (DurationConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getTimeConstraint_Shape_CN_OutgoingLinks(View view) {
		TimeConstraint modelElement = (TimeConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getIntervalConstraint_Shape_CN_OutgoingLinks(View view) {
		IntervalConstraint modelElement = (IntervalConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInteractionConstraint_Shape_CN_OutgoingLinks(View view) {
		InteractionConstraint modelElement = (InteractionConstraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConstraint_Shape_CN_OutgoingLinks(View view) {
		Constraint modelElement = (Constraint) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getProperty_AttributeLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getOperation_OperationLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getEnumerationLiteral_LiteralLabel_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getComponentRealization_Edge_OutgoingLinks(View view) {
		ComponentRealization modelElement = (ComponentRealization) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInterfaceRealization_Edge_OutgoingLinks(View view) {
		InterfaceRealization modelElement = (InterfaceRealization) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getSubstitution_Edge_OutgoingLinks(View view) {
		Substitution modelElement = (Substitution) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getRealization_Edge_OutgoingLinks(View view) {
		Realization modelElement = (Realization) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getManifestation_Edge_OutgoingLinks(View view) {
		Manifestation modelElement = (Manifestation) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getAbstraction_Edge_OutgoingLinks(View view) {
		Abstraction modelElement = (Abstraction) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getUsage_Edge_OutgoingLinks(View view) {
		Usage modelElement = (Usage) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDeployment_Edge_OutgoingLinks(View view) {
		Deployment modelElement = (Deployment) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_RoleBindingEdge_OutgoingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getDependency_Edge_OutgoingLinks(View view) {
		Dependency modelElement = (Dependency) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getConnector_Edge_OutgoingLinks(View view) {
		Connector modelElement = (Connector) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getGeneralization_Edge_OutgoingLinks(View view) {
		return Collections.emptyList();
	}

	/**
	 * @generated
	 */
	public List<UMLLinkDescriptor> getInformationFlow_Edge_OutgoingLinks(View view) {
		InformationFlow modelElement = (InformationFlow) view.getElement();
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		result.addAll(getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Substitution_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Realization_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Manifestation_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Abstraction_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Usage_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Deployment_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_Dependency_Edge(modelElement));
		result.addAll(getOutgoingTypeModelFacetLinks_InformationFlow_Edge(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_ComponentRealization_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ComponentRealization) {
				continue;
			}
			ComponentRealization link = (ComponentRealization) linkObject;
			if (!ComponentRealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ComponentRealization_Edge, ComponentRealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_InterfaceRealization_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof InterfaceRealization) {
				continue;
			}
			InterfaceRealization link = (InterfaceRealization) linkObject;
			if (!InterfaceRealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.InterfaceRealization_Edge, InterfaceRealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Substitution_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Substitution) {
				continue;
			}
			Substitution link = (Substitution) linkObject;
			if (!SubstitutionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Substitution_Edge, SubstitutionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Realization_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Realization) {
				continue;
			}
			Realization link = (Realization) linkObject;
			if (!RealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Realization_Edge, RealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Manifestation_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Manifestation) {
				continue;
			}
			Manifestation link = (Manifestation) linkObject;
			if (!ManifestationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Manifestation_Edge, ManifestationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Abstraction_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Abstraction) {
				continue;
			}
			Abstraction link = (Abstraction) linkObject;
			if (!AbstractionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Abstraction_Edge, AbstractionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Usage_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Usage) {
				continue;
			}
			Usage link = (Usage) linkObject;
			if (!UsageEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Usage_Edge, UsageEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Deployment_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Deployment) {
				continue;
			}
			Deployment link = (Deployment) linkObject;
			if (!DeploymentEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Deployment_Edge, DeploymentEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Dependency_RoleBindingEdge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!RoleBindingEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_RoleBindingEdge, RoleBindingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Dependency_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!DependencyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_Edge, DependencyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Connector_Edge(StructuredClassifier container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getOwnedConnectors()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Connector) {
				continue;
			}
			Connector link = (Connector) linkObject;
			if (!ConnectorEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getEnds();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof ConnectorEnd) {
				continue;
			}
			ConnectorEnd dst = (ConnectorEnd) theTarget;
			List<?> sources = link.getEnds();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof ConnectorEnd) {
				continue;
			}
			ConnectorEnd src = (ConnectorEnd) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Connector_Edge, ConnectorEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_Generalization_Edge(Classifier container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getGeneralizations()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Generalization) {
				continue;
			}
			Generalization link = (Generalization) linkObject;
			if (!GeneralizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Classifier dst = link.getGeneral();
			Classifier src = link.getSpecific();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getContainedTypeModelFacetLinks_InformationFlow_Edge(Package container) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof InformationFlow) {
				continue;
			}
			InformationFlow link = (InformationFlow) linkObject;
			if (!InformationFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getInformationTargets();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getInformationSources();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.InformationFlow_Edge, InformationFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(Element target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getComment_AnnotatedElement()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Comment_AnnotatedElementEdge, CommentAnnotatedElementEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(Element target, CrossReferenceAdapter crossReferencer) {
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
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_ComponentRealization_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof ComponentRealization) {
				continue;
			}
			ComponentRealization link = (ComponentRealization) setting.getEObject();
			if (!ComponentRealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.ComponentRealization_Edge, ComponentRealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_InterfaceRealization_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof InterfaceRealization) {
				continue;
			}
			InterfaceRealization link = (InterfaceRealization) setting.getEObject();
			if (!InterfaceRealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.InterfaceRealization_Edge, InterfaceRealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Substitution_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Substitution) {
				continue;
			}
			Substitution link = (Substitution) setting.getEObject();
			if (!SubstitutionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Substitution_Edge, SubstitutionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Realization_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Realization) {
				continue;
			}
			Realization link = (Realization) setting.getEObject();
			if (!RealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Realization_Edge, RealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Manifestation_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Manifestation) {
				continue;
			}
			Manifestation link = (Manifestation) setting.getEObject();
			if (!ManifestationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Manifestation_Edge, ManifestationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Abstraction_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Abstraction) {
				continue;
			}
			Abstraction link = (Abstraction) setting.getEObject();
			if (!AbstractionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Abstraction_Edge, AbstractionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Usage_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Usage) {
				continue;
			}
			Usage link = (Usage) setting.getEObject();
			if (!UsageEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Usage_Edge, UsageEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Deployment_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Deployment) {
				continue;
			}
			Deployment link = (Deployment) setting.getEObject();
			if (!DeploymentEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Deployment_Edge, DeploymentEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Dependency_RoleBindingEdge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) setting.getEObject();
			if (!RoleBindingEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Dependency_RoleBindingEdge, RoleBindingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Dependency_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getDependency_Supplier() || false == setting.getEObject() instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) setting.getEObject();
			if (!DependencyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Dependency_Edge, DependencyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_Generalization_Edge(Classifier target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getGeneralization_General() || false == setting.getEObject() instanceof Generalization) {
				continue;
			}
			Generalization link = (Generalization) setting.getEObject();
			if (!GeneralizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Classifier src = link.getSpecific();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_TimeObservation_EventEdge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getTimeObservation_Event()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.TimeObservation_EventEdge, TimeObservationEventEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_DurationObservation_EventEdge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getDurationObservation_Event()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.DurationObservation_EventEdge, DurationObservationEventEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingFeatureModelFacetLinks_Representation_Edge(Classifier target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() == UMLPackage.eINSTANCE.getInformationItem_Represented()) {
				result.add(new UMLLinkDescriptor(setting.getEObject(), target, UMLElementTypes.Representation_Edge, RepresentationEditPart.VISUAL_ID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getIncomingTypeModelFacetLinks_InformationFlow_Edge(NamedElement target, CrossReferenceAdapter crossReferencer) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		Collection<EStructuralFeature.Setting> settings = crossReferencer.getInverseReferences(target);
		for (EStructuralFeature.Setting setting : settings) {
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getInformationFlow_InformationTarget() || false == setting.getEObject() instanceof InformationFlow) {
				continue;
			}
			InformationFlow link = (InformationFlow) setting.getEObject();
			if (!InformationFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> sources = link.getInformationSources();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.InformationFlow_Edge, InformationFlowEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Comment_AnnotatedElementEdge(Comment source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getAnnotatedElements()
				.iterator(); destinations.hasNext();) {
			Element destination = (Element) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Comment_AnnotatedElementEdge, CommentAnnotatedElementEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Constraint_ConstrainedElementEdge(Constraint source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getConstrainedElements()
				.iterator(); destinations.hasNext();) {
			Element destination = (Element) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Constraint_ConstrainedElementEdge, ConstraintConstrainedElementEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_ComponentRealization_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof ComponentRealization) {
				continue;
			}
			ComponentRealization link = (ComponentRealization) linkObject;
			if (!ComponentRealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.ComponentRealization_Edge, ComponentRealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_InterfaceRealization_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof InterfaceRealization) {
				continue;
			}
			InterfaceRealization link = (InterfaceRealization) linkObject;
			if (!InterfaceRealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.InterfaceRealization_Edge, InterfaceRealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Substitution_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Substitution) {
				continue;
			}
			Substitution link = (Substitution) linkObject;
			if (!SubstitutionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Substitution_Edge, SubstitutionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Realization_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Realization) {
				continue;
			}
			Realization link = (Realization) linkObject;
			if (!RealizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Realization_Edge, RealizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Manifestation_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Manifestation) {
				continue;
			}
			Manifestation link = (Manifestation) linkObject;
			if (!ManifestationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Manifestation_Edge, ManifestationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Abstraction_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Abstraction) {
				continue;
			}
			Abstraction link = (Abstraction) linkObject;
			if (!AbstractionEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Abstraction_Edge, AbstractionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Usage_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Usage) {
				continue;
			}
			Usage link = (Usage) linkObject;
			if (!UsageEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Usage_Edge, UsageEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Deployment_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Deployment) {
				continue;
			}
			Deployment link = (Deployment) linkObject;
			if (!DeploymentEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Deployment_Edge, DeploymentEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Dependency_RoleBindingEdge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!RoleBindingEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_RoleBindingEdge, RoleBindingEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Dependency_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Dependency) {
				continue;
			}
			Dependency link = (Dependency) linkObject;
			if (!DependencyEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getSuppliers();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getClients();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Dependency_Edge, DependencyEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_Generalization_Edge(Classifier source) {
		Classifier container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Classifier) {
				container = (Classifier) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getGeneralizations()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Generalization) {
				continue;
			}
			Generalization link = (Generalization) linkObject;
			if (!GeneralizationEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			Classifier dst = link.getGeneral();
			Classifier src = link.getSpecific();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Generalization_Edge, GeneralizationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_TimeObservation_EventEdge(TimeObservation source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		NamedElement destination = source.getEvent();
		if (destination == null) {
			return result;
		}
		result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.TimeObservation_EventEdge, TimeObservationEventEditPart.VISUAL_ID));
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_DurationObservation_EventEdge(DurationObservation source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getEvents()
				.iterator(); destinations.hasNext();) {
			NamedElement destination = (NamedElement) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.DurationObservation_EventEdge, DurationObservationEventEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingFeatureModelFacetLinks_Representation_Edge(InformationItem source) {
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> destinations = source.getRepresenteds()
				.iterator(); destinations.hasNext();) {
			Classifier destination = (Classifier) destinations.next();
			result.add(new UMLLinkDescriptor(source, destination, UMLElementTypes.Representation_Edge, RepresentationEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	protected Collection<UMLLinkDescriptor> getOutgoingTypeModelFacetLinks_InformationFlow_Edge(NamedElement source) {
		Package container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Package) {
				container = (Package) element;
			}
		}
		if (container == null) {
			return Collections.emptyList();
		}
		LinkedList<UMLLinkDescriptor> result = new LinkedList<>();
		for (Iterator<?> links = container.getPackagedElements()
				.iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof InformationFlow) {
				continue;
			}
			InformationFlow link = (InformationFlow) linkObject;
			if (!InformationFlowEditPart.VISUAL_ID.equals(UMLVisualIDRegistry.getLinkWithClassVisualID(link))) {
				continue;
			}
			List<?> targets = link.getInformationTargets();
			Object theTarget = targets.size() == 1 ? targets.get(0) : null;
			if (false == theTarget instanceof NamedElement) {
				continue;
			}
			NamedElement dst = (NamedElement) theTarget;
			List<?> sources = link.getInformationSources();
			Object theSource = sources.size() == 1 ? sources.get(0) : null;
			if (false == theSource instanceof NamedElement) {
				continue;
			}
			NamedElement src = (NamedElement) theSource;
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.InformationFlow_Edge, InformationFlowEditPart.VISUAL_ID));
		}
		return result;
	}
}
