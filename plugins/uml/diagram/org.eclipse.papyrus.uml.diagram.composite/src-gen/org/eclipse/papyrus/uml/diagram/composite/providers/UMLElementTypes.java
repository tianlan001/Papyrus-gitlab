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

import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.gmfdiag.common.providers.DiagramElementTypes;
import org.eclipse.papyrus.infra.gmfdiag.tooling.runtime.providers.DiagramElementTypeImages;
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.*;
import org.eclipse.papyrus.uml.diagram.composite.part.UMLDiagramEditorPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class UMLElementTypes {

	/**
	 * @generated
	 */
	private UMLElementTypes() {
	}

	/**
	 * @generated
	 */
	private static Map<IElementType, ENamedElement> elements;

	/**
	 * @generated
	 */
	private static DiagramElementTypeImages elementTypeImages = new DiagramElementTypeImages(UMLDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());

	/**
	 * @generated
	 */
	private static Set<IElementType> KNOWN_ELEMENT_TYPES;

	/**
	 * @generated
	 */
	public static final IElementType Package_CompositeStructureDiagram = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Package_CompositeStructureDiagram"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Activity_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Activity_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interaction_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interaction_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ProtocolStateMachine_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ProtocolStateMachine_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType StateMachine_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.StateMachine_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType FunctionBehavior_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.FunctionBehavior_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OpaqueBehavior_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OpaqueBehavior_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Device_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Device_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExecutionEnvironment_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExecutionEnvironment_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Node_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Collaboration_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Collaboration_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PrimitiveType_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PrimitiveType_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Enumeration_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Enumeration_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DataType_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DataType_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Actor_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Actor_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DeploymentSpecification_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DeploymentSpecification_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Artifact_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Artifact_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InformationItem_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InformationItem_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Signal_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Signal_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType UseCase_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.UseCase_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType SignalEvent_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.SignalEvent_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CallEvent_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.CallEvent_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType AnyReceiveEvent_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.AnyReceiveEvent_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ChangeEvent_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ChangeEvent_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeEvent_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeEvent_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationObservation_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationObservation_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeObservation_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeObservation_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType LiteralBoolean_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.LiteralBoolean_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType LiteralInteger_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.LiteralInteger_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType LiteralNull_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.LiteralNull_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType LiteralString_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.LiteralString_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType LiteralUnlimitedNatural_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.LiteralUnlimitedNatural_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType StringExpression_PackagedElementShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.StringExpression_PackagedElementShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OpaqueExpression_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OpaqueExpression_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeExpression_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeExpression_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Expression_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Expression_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Duration_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Duration_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeInterval_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeInterval_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationInterval_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationInterval_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interval_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interval_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InstanceValue_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InstanceValue_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationConstraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationConstraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeConstraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeConstraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType IntervalConstraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.IntervalConstraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InteractionConstraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InteractionConstraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Port_BehaviorShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Port_BehaviorShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Port_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Port_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Parameter_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Parameter_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ConnectableElement_CollaborationRoleShape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ConnectableElement_CollaborationRoleShape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType CollaborationUse_Shape = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.CollaborationUse_Shape"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Activity_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Activity_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interaction_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interaction_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ProtocolStateMachine_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ProtocolStateMachine_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType StateMachine_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.StateMachine_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType FunctionBehavior_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.FunctionBehavior_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType OpaqueBehavior_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.OpaqueBehavior_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Component_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Component_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Device_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Device_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ExecutionEnvironment_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ExecutionEnvironment_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Node_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Node_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Class_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Class_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Collaboration_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Collaboration_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Interface_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Interface_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType PrimitiveType_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.PrimitiveType_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Enumeration_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Enumeration_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DataType_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DataType_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Actor_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Actor_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DeploymentSpecification_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DeploymentSpecification_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Artifact_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Artifact_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InformationItem_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InformationItem_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Signal_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Signal_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType UseCase_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.UseCase_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationConstraint_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationConstraint_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeConstraint_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeConstraint_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType IntervalConstraint_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.IntervalConstraint_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InteractionConstraint_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InteractionConstraint_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_Shape_CN = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_Shape_CN"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Property_AttributeLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Property_AttributeLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Operation_OperationLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Operation_OperationLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType EnumerationLiteral_LiteralLabel = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.EnumerationLiteral_LiteralLabel"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Port_BehaviorEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Port_BehaviorEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Link_DescriptorEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Link_DescriptorEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Comment_AnnotatedElementEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Comment_AnnotatedElementEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Constraint_ConstrainedElementEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Constraint_ConstrainedElementEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType ComponentRealization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.ComponentRealization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InterfaceRealization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InterfaceRealization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Substitution_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Substitution_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Realization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Realization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Manifestation_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Manifestation_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Abstraction_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Abstraction_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Usage_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Usage_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Deployment_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Deployment_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_RoleBindingEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_RoleBindingEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Dependency_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Dependency_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Connector_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Connector_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Generalization_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Generalization_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType TimeObservation_EventEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.TimeObservation_EventEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType DurationObservation_EventEdge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.DurationObservation_EventEdge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType Representation_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.Representation_Edge"); //$NON-NLS-1$
	/**
	 * @generated
	 */
	public static final IElementType InformationFlow_Edge = getElementTypeByUniqueId("org.eclipse.papyrus.umldi.InformationFlow_Edge"); //$NON-NLS-1$

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(ENamedElement element) {
		return elementTypeImages.getImageDescriptor(element);
	}

	/**
	 * @generated
	 */
	public static Image getImage(ENamedElement element) {
		return elementTypeImages.getImage(element);
	}

	/**
	 * @generated
	 */
	public static ImageDescriptor getImageDescriptor(IAdaptable hint) {
		return getImageDescriptor(getElement(hint));
	}

	/**
	 * @generated
	 */
	public static Image getImage(IAdaptable hint) {
		return getImage(getElement(hint));
	}

	/**
	 * Returns 'type' of the ecore object associated with the hint.
	 *
	 * @generated
	 */
	public static synchronized ENamedElement getElement(IAdaptable hint) {
		Object type = hint.getAdapter(IElementType.class);
		if (elements == null) {
			elements = new IdentityHashMap<>();
			elements.put(Package_CompositeStructureDiagram, UMLPackage.eINSTANCE.getPackage());
			elements.put(Activity_Shape, UMLPackage.eINSTANCE.getActivity());
			elements.put(Interaction_Shape, UMLPackage.eINSTANCE.getInteraction());
			elements.put(ProtocolStateMachine_Shape, UMLPackage.eINSTANCE.getProtocolStateMachine());
			elements.put(StateMachine_Shape, UMLPackage.eINSTANCE.getStateMachine());
			elements.put(FunctionBehavior_Shape, UMLPackage.eINSTANCE.getFunctionBehavior());
			elements.put(OpaqueBehavior_Shape, UMLPackage.eINSTANCE.getOpaqueBehavior());
			elements.put(Component_Shape, UMLPackage.eINSTANCE.getComponent());
			elements.put(Device_Shape, UMLPackage.eINSTANCE.getDevice());
			elements.put(ExecutionEnvironment_Shape, UMLPackage.eINSTANCE.getExecutionEnvironment());
			elements.put(Node_Shape, UMLPackage.eINSTANCE.getNode());
			elements.put(Class_Shape, UMLPackage.eINSTANCE.getClass_());
			elements.put(Collaboration_Shape, UMLPackage.eINSTANCE.getCollaboration());
			elements.put(Interface_Shape, UMLPackage.eINSTANCE.getInterface());
			elements.put(PrimitiveType_Shape, UMLPackage.eINSTANCE.getPrimitiveType());
			elements.put(Enumeration_Shape, UMLPackage.eINSTANCE.getEnumeration());
			elements.put(DataType_Shape, UMLPackage.eINSTANCE.getDataType());
			elements.put(Actor_Shape, UMLPackage.eINSTANCE.getActor());
			elements.put(DeploymentSpecification_Shape, UMLPackage.eINSTANCE.getDeploymentSpecification());
			elements.put(Artifact_Shape, UMLPackage.eINSTANCE.getArtifact());
			elements.put(InformationItem_Shape, UMLPackage.eINSTANCE.getInformationItem());
			elements.put(Signal_Shape, UMLPackage.eINSTANCE.getSignal());
			elements.put(UseCase_Shape, UMLPackage.eINSTANCE.getUseCase());
			elements.put(SignalEvent_Shape, UMLPackage.eINSTANCE.getSignalEvent());
			elements.put(CallEvent_Shape, UMLPackage.eINSTANCE.getCallEvent());
			elements.put(AnyReceiveEvent_Shape, UMLPackage.eINSTANCE.getAnyReceiveEvent());
			elements.put(ChangeEvent_Shape, UMLPackage.eINSTANCE.getChangeEvent());
			elements.put(TimeEvent_Shape, UMLPackage.eINSTANCE.getTimeEvent());
			elements.put(DurationObservation_Shape, UMLPackage.eINSTANCE.getDurationObservation());
			elements.put(TimeObservation_Shape, UMLPackage.eINSTANCE.getTimeObservation());
			elements.put(LiteralBoolean_Shape, UMLPackage.eINSTANCE.getLiteralBoolean());
			elements.put(LiteralInteger_Shape, UMLPackage.eINSTANCE.getLiteralInteger());
			elements.put(LiteralNull_Shape, UMLPackage.eINSTANCE.getLiteralNull());
			elements.put(LiteralString_Shape, UMLPackage.eINSTANCE.getLiteralString());
			elements.put(LiteralUnlimitedNatural_Shape, UMLPackage.eINSTANCE.getLiteralUnlimitedNatural());
			elements.put(StringExpression_PackagedElementShape, UMLPackage.eINSTANCE.getStringExpression());
			elements.put(OpaqueExpression_Shape, UMLPackage.eINSTANCE.getOpaqueExpression());
			elements.put(TimeExpression_Shape, UMLPackage.eINSTANCE.getTimeExpression());
			elements.put(Expression_Shape, UMLPackage.eINSTANCE.getExpression());
			elements.put(Duration_Shape, UMLPackage.eINSTANCE.getDuration());
			elements.put(TimeInterval_Shape, UMLPackage.eINSTANCE.getTimeInterval());
			elements.put(DurationInterval_Shape, UMLPackage.eINSTANCE.getDurationInterval());
			elements.put(Interval_Shape, UMLPackage.eINSTANCE.getInterval());
			elements.put(InstanceValue_Shape, UMLPackage.eINSTANCE.getInstanceValue());
			elements.put(Comment_Shape, UMLPackage.eINSTANCE.getComment());
			elements.put(DurationConstraint_Shape, UMLPackage.eINSTANCE.getDurationConstraint());
			elements.put(TimeConstraint_Shape, UMLPackage.eINSTANCE.getTimeConstraint());
			elements.put(IntervalConstraint_Shape, UMLPackage.eINSTANCE.getIntervalConstraint());
			elements.put(InteractionConstraint_Shape, UMLPackage.eINSTANCE.getInteractionConstraint());
			elements.put(Constraint_Shape, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Port_BehaviorShape, UMLPackage.eINSTANCE.getPort());
			elements.put(Port_Shape, UMLPackage.eINSTANCE.getPort());
			elements.put(Parameter_Shape, UMLPackage.eINSTANCE.getParameter());
			elements.put(Property_Shape, UMLPackage.eINSTANCE.getProperty());
			elements.put(ConnectableElement_CollaborationRoleShape, UMLPackage.eINSTANCE.getConnectableElement());
			elements.put(CollaborationUse_Shape, UMLPackage.eINSTANCE.getCollaborationUse());
			elements.put(Activity_Shape_CN, UMLPackage.eINSTANCE.getActivity());
			elements.put(Interaction_Shape_CN, UMLPackage.eINSTANCE.getInteraction());
			elements.put(ProtocolStateMachine_Shape_CN, UMLPackage.eINSTANCE.getProtocolStateMachine());
			elements.put(StateMachine_Shape_CN, UMLPackage.eINSTANCE.getStateMachine());
			elements.put(FunctionBehavior_Shape_CN, UMLPackage.eINSTANCE.getFunctionBehavior());
			elements.put(OpaqueBehavior_Shape_CN, UMLPackage.eINSTANCE.getOpaqueBehavior());
			elements.put(Component_Shape_CN, UMLPackage.eINSTANCE.getComponent());
			elements.put(Device_Shape_CN, UMLPackage.eINSTANCE.getDevice());
			elements.put(ExecutionEnvironment_Shape_CN, UMLPackage.eINSTANCE.getExecutionEnvironment());
			elements.put(Node_Shape_CN, UMLPackage.eINSTANCE.getNode());
			elements.put(Class_Shape_CN, UMLPackage.eINSTANCE.getClass_());
			elements.put(Collaboration_Shape_CN, UMLPackage.eINSTANCE.getCollaboration());
			elements.put(Interface_Shape_CN, UMLPackage.eINSTANCE.getInterface());
			elements.put(PrimitiveType_Shape_CN, UMLPackage.eINSTANCE.getPrimitiveType());
			elements.put(Enumeration_Shape_CN, UMLPackage.eINSTANCE.getEnumeration());
			elements.put(DataType_Shape_CN, UMLPackage.eINSTANCE.getDataType());
			elements.put(Actor_Shape_CN, UMLPackage.eINSTANCE.getActor());
			elements.put(DeploymentSpecification_Shape_CN, UMLPackage.eINSTANCE.getDeploymentSpecification());
			elements.put(Artifact_Shape_CN, UMLPackage.eINSTANCE.getArtifact());
			elements.put(InformationItem_Shape_CN, UMLPackage.eINSTANCE.getInformationItem());
			elements.put(Signal_Shape_CN, UMLPackage.eINSTANCE.getSignal());
			elements.put(UseCase_Shape_CN, UMLPackage.eINSTANCE.getUseCase());
			elements.put(Comment_Shape_CN, UMLPackage.eINSTANCE.getComment());
			elements.put(DurationConstraint_Shape_CN, UMLPackage.eINSTANCE.getDurationConstraint());
			elements.put(TimeConstraint_Shape_CN, UMLPackage.eINSTANCE.getTimeConstraint());
			elements.put(IntervalConstraint_Shape_CN, UMLPackage.eINSTANCE.getIntervalConstraint());
			elements.put(InteractionConstraint_Shape_CN, UMLPackage.eINSTANCE.getInteractionConstraint());
			elements.put(Constraint_Shape_CN, UMLPackage.eINSTANCE.getConstraint());
			elements.put(Property_AttributeLabel, UMLPackage.eINSTANCE.getProperty());
			elements.put(Operation_OperationLabel, UMLPackage.eINSTANCE.getOperation());
			elements.put(EnumerationLiteral_LiteralLabel, UMLPackage.eINSTANCE.getEnumerationLiteral());
			elements.put(Comment_AnnotatedElementEdge, UMLPackage.eINSTANCE.getComment_AnnotatedElement());
			elements.put(Constraint_ConstrainedElementEdge, UMLPackage.eINSTANCE.getConstraint_ConstrainedElement());
			elements.put(ComponentRealization_Edge, UMLPackage.eINSTANCE.getComponentRealization());
			elements.put(InterfaceRealization_Edge, UMLPackage.eINSTANCE.getInterfaceRealization());
			elements.put(Substitution_Edge, UMLPackage.eINSTANCE.getSubstitution());
			elements.put(Realization_Edge, UMLPackage.eINSTANCE.getRealization());
			elements.put(Manifestation_Edge, UMLPackage.eINSTANCE.getManifestation());
			elements.put(Abstraction_Edge, UMLPackage.eINSTANCE.getAbstraction());
			elements.put(Usage_Edge, UMLPackage.eINSTANCE.getUsage());
			elements.put(Deployment_Edge, UMLPackage.eINSTANCE.getDeployment());
			elements.put(Dependency_RoleBindingEdge, UMLPackage.eINSTANCE.getDependency());
			elements.put(Dependency_Edge, UMLPackage.eINSTANCE.getDependency());
			elements.put(Connector_Edge, UMLPackage.eINSTANCE.getConnector());
			elements.put(Generalization_Edge, UMLPackage.eINSTANCE.getGeneralization());
			elements.put(TimeObservation_EventEdge, UMLPackage.eINSTANCE.getTimeObservation_Event());
			elements.put(DurationObservation_EventEdge, UMLPackage.eINSTANCE.getDurationObservation_Event());
			elements.put(Representation_Edge, UMLPackage.eINSTANCE.getInformationItem_Represented());
			elements.put(InformationFlow_Edge, UMLPackage.eINSTANCE.getInformationFlow());
		}
		return elements.get(type);
	}

	/**
	 * @generated
	 */
	private static IElementType getElementTypeByUniqueId(String id) {
		return ElementTypeRegistry.getInstance().getType(id);
	}

	/**
	 * @generated
	 */
	public static synchronized boolean isKnownElementType(IElementType elementType) {
		if (KNOWN_ELEMENT_TYPES == null) {
			KNOWN_ELEMENT_TYPES = new HashSet<>();
			KNOWN_ELEMENT_TYPES.add(Package_CompositeStructureDiagram);
			KNOWN_ELEMENT_TYPES.add(Activity_Shape);
			KNOWN_ELEMENT_TYPES.add(Interaction_Shape);
			KNOWN_ELEMENT_TYPES.add(ProtocolStateMachine_Shape);
			KNOWN_ELEMENT_TYPES.add(StateMachine_Shape);
			KNOWN_ELEMENT_TYPES.add(FunctionBehavior_Shape);
			KNOWN_ELEMENT_TYPES.add(OpaqueBehavior_Shape);
			KNOWN_ELEMENT_TYPES.add(Component_Shape);
			KNOWN_ELEMENT_TYPES.add(Device_Shape);
			KNOWN_ELEMENT_TYPES.add(ExecutionEnvironment_Shape);
			KNOWN_ELEMENT_TYPES.add(Node_Shape);
			KNOWN_ELEMENT_TYPES.add(Class_Shape);
			KNOWN_ELEMENT_TYPES.add(Collaboration_Shape);
			KNOWN_ELEMENT_TYPES.add(Interface_Shape);
			KNOWN_ELEMENT_TYPES.add(PrimitiveType_Shape);
			KNOWN_ELEMENT_TYPES.add(Enumeration_Shape);
			KNOWN_ELEMENT_TYPES.add(DataType_Shape);
			KNOWN_ELEMENT_TYPES.add(Actor_Shape);
			KNOWN_ELEMENT_TYPES.add(DeploymentSpecification_Shape);
			KNOWN_ELEMENT_TYPES.add(Artifact_Shape);
			KNOWN_ELEMENT_TYPES.add(InformationItem_Shape);
			KNOWN_ELEMENT_TYPES.add(Signal_Shape);
			KNOWN_ELEMENT_TYPES.add(UseCase_Shape);
			KNOWN_ELEMENT_TYPES.add(SignalEvent_Shape);
			KNOWN_ELEMENT_TYPES.add(CallEvent_Shape);
			KNOWN_ELEMENT_TYPES.add(AnyReceiveEvent_Shape);
			KNOWN_ELEMENT_TYPES.add(ChangeEvent_Shape);
			KNOWN_ELEMENT_TYPES.add(TimeEvent_Shape);
			KNOWN_ELEMENT_TYPES.add(DurationObservation_Shape);
			KNOWN_ELEMENT_TYPES.add(TimeObservation_Shape);
			KNOWN_ELEMENT_TYPES.add(LiteralBoolean_Shape);
			KNOWN_ELEMENT_TYPES.add(LiteralInteger_Shape);
			KNOWN_ELEMENT_TYPES.add(LiteralNull_Shape);
			KNOWN_ELEMENT_TYPES.add(LiteralString_Shape);
			KNOWN_ELEMENT_TYPES.add(LiteralUnlimitedNatural_Shape);
			KNOWN_ELEMENT_TYPES.add(StringExpression_PackagedElementShape);
			KNOWN_ELEMENT_TYPES.add(OpaqueExpression_Shape);
			KNOWN_ELEMENT_TYPES.add(TimeExpression_Shape);
			KNOWN_ELEMENT_TYPES.add(Expression_Shape);
			KNOWN_ELEMENT_TYPES.add(Duration_Shape);
			KNOWN_ELEMENT_TYPES.add(TimeInterval_Shape);
			KNOWN_ELEMENT_TYPES.add(DurationInterval_Shape);
			KNOWN_ELEMENT_TYPES.add(Interval_Shape);
			KNOWN_ELEMENT_TYPES.add(InstanceValue_Shape);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape);
			KNOWN_ELEMENT_TYPES.add(DurationConstraint_Shape);
			KNOWN_ELEMENT_TYPES.add(TimeConstraint_Shape);
			KNOWN_ELEMENT_TYPES.add(IntervalConstraint_Shape);
			KNOWN_ELEMENT_TYPES.add(InteractionConstraint_Shape);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape);
			KNOWN_ELEMENT_TYPES.add(Port_BehaviorShape);
			KNOWN_ELEMENT_TYPES.add(Port_Shape);
			KNOWN_ELEMENT_TYPES.add(Parameter_Shape);
			KNOWN_ELEMENT_TYPES.add(Property_Shape);
			KNOWN_ELEMENT_TYPES.add(ConnectableElement_CollaborationRoleShape);
			KNOWN_ELEMENT_TYPES.add(CollaborationUse_Shape);
			KNOWN_ELEMENT_TYPES.add(Activity_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Interaction_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(ProtocolStateMachine_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(StateMachine_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(FunctionBehavior_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(OpaqueBehavior_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Component_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Device_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(ExecutionEnvironment_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Node_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Class_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Collaboration_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Interface_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(PrimitiveType_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Enumeration_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(DataType_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Actor_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(DeploymentSpecification_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Artifact_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(InformationItem_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Signal_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(UseCase_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Comment_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(DurationConstraint_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(TimeConstraint_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(IntervalConstraint_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(InteractionConstraint_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Constraint_Shape_CN);
			KNOWN_ELEMENT_TYPES.add(Property_AttributeLabel);
			KNOWN_ELEMENT_TYPES.add(Operation_OperationLabel);
			KNOWN_ELEMENT_TYPES.add(EnumerationLiteral_LiteralLabel);
			KNOWN_ELEMENT_TYPES.add(Port_BehaviorEdge);
			KNOWN_ELEMENT_TYPES.add(Link_DescriptorEdge);
			KNOWN_ELEMENT_TYPES.add(Comment_AnnotatedElementEdge);
			KNOWN_ELEMENT_TYPES.add(Constraint_ConstrainedElementEdge);
			KNOWN_ELEMENT_TYPES.add(ComponentRealization_Edge);
			KNOWN_ELEMENT_TYPES.add(InterfaceRealization_Edge);
			KNOWN_ELEMENT_TYPES.add(Substitution_Edge);
			KNOWN_ELEMENT_TYPES.add(Realization_Edge);
			KNOWN_ELEMENT_TYPES.add(Manifestation_Edge);
			KNOWN_ELEMENT_TYPES.add(Abstraction_Edge);
			KNOWN_ELEMENT_TYPES.add(Usage_Edge);
			KNOWN_ELEMENT_TYPES.add(Deployment_Edge);
			KNOWN_ELEMENT_TYPES.add(Dependency_RoleBindingEdge);
			KNOWN_ELEMENT_TYPES.add(Dependency_Edge);
			KNOWN_ELEMENT_TYPES.add(Connector_Edge);
			KNOWN_ELEMENT_TYPES.add(Generalization_Edge);
			KNOWN_ELEMENT_TYPES.add(TimeObservation_EventEdge);
			KNOWN_ELEMENT_TYPES.add(DurationObservation_EventEdge);
			KNOWN_ELEMENT_TYPES.add(Representation_Edge);
			KNOWN_ELEMENT_TYPES.add(InformationFlow_Edge);
		}

		boolean result = KNOWN_ELEMENT_TYPES.contains(elementType);

		if (!result) {
			IElementType[] supertypes = elementType.getAllSuperTypes();
			for (int i = 0; !result && (i < supertypes.length); i++) {
				result = KNOWN_ELEMENT_TYPES.contains(supertypes[i]);
			}
		}

		return result;
	}

	/**
	 * @generated
	 */
	public static IElementType getElementType(String visualID) {
		if (visualID != null) {
			switch (visualID) {
			case CompositeStructureDiagramEditPart.VISUAL_ID:
				return Package_CompositeStructureDiagram;
			case ActivityCompositeEditPart.VISUAL_ID:
				return Activity_Shape;
			case InteractionCompositeEditPart.VISUAL_ID:
				return Interaction_Shape;
			case ProtocolStateMachineCompositeEditPart.VISUAL_ID:
				return ProtocolStateMachine_Shape;
			case StateMachineCompositeEditPart.VISUAL_ID:
				return StateMachine_Shape;
			case FunctionBehaviorCompositeEditPart.VISUAL_ID:
				return FunctionBehavior_Shape;
			case OpaqueBehaviorCompositeEditPart.VISUAL_ID:
				return OpaqueBehavior_Shape;
			case ComponentCompositeEditPart.VISUAL_ID:
				return Component_Shape;
			case DeviceCompositeEditPart.VISUAL_ID:
				return Device_Shape;
			case ExecutionEnvironmentCompositeEditPart.VISUAL_ID:
				return ExecutionEnvironment_Shape;
			case NodeCompositeEditPart.VISUAL_ID:
				return Node_Shape;
			case ClassCompositeEditPart.VISUAL_ID:
				return Class_Shape;
			case CollaborationCompositeEditPart.VISUAL_ID:
				return Collaboration_Shape;
			case InterfaceEditPart.VISUAL_ID:
				return Interface_Shape;
			case PrimitiveTypeEditPart.VISUAL_ID:
				return PrimitiveType_Shape;
			case EnumerationEditPart.VISUAL_ID:
				return Enumeration_Shape;
			case DataTypeEditPart.VISUAL_ID:
				return DataType_Shape;
			case ActorEditPart.VISUAL_ID:
				return Actor_Shape;
			case DeploymentSpecificationEditPart.VISUAL_ID:
				return DeploymentSpecification_Shape;
			case ArtifactEditPart.VISUAL_ID:
				return Artifact_Shape;
			case InformationItemEditPart.VISUAL_ID:
				return InformationItem_Shape;
			case SignalEditPart.VISUAL_ID:
				return Signal_Shape;
			case UseCaseEditPart.VISUAL_ID:
				return UseCase_Shape;
			case SignalEventEditPart.VISUAL_ID:
				return SignalEvent_Shape;
			case CallEventEditPart.VISUAL_ID:
				return CallEvent_Shape;
			case AnyReceiveEventEditPart.VISUAL_ID:
				return AnyReceiveEvent_Shape;
			case ChangeEventEditPart.VISUAL_ID:
				return ChangeEvent_Shape;
			case TimeEventEditPart.VISUAL_ID:
				return TimeEvent_Shape;
			case DurationObservationEditPart.VISUAL_ID:
				return DurationObservation_Shape;
			case TimeObservationEditPart.VISUAL_ID:
				return TimeObservation_Shape;
			case LiteralBooleanEditPart.VISUAL_ID:
				return LiteralBoolean_Shape;
			case LiteralIntegerEditPart.VISUAL_ID:
				return LiteralInteger_Shape;
			case LiteralNullEditPart.VISUAL_ID:
				return LiteralNull_Shape;
			case LiteralStringEditPart.VISUAL_ID:
				return LiteralString_Shape;
			case LiteralUnlimitedNaturalEditPart.VISUAL_ID:
				return LiteralUnlimitedNatural_Shape;
			case StringExpressionEditPart.VISUAL_ID:
				return StringExpression_PackagedElementShape;
			case OpaqueExpressionEditPart.VISUAL_ID:
				return OpaqueExpression_Shape;
			case TimeExpressionEditPart.VISUAL_ID:
				return TimeExpression_Shape;
			case ExpressionEditPart.VISUAL_ID:
				return Expression_Shape;
			case DurationEditPart.VISUAL_ID:
				return Duration_Shape;
			case TimeIntervalEditPart.VISUAL_ID:
				return TimeInterval_Shape;
			case DurationIntervalEditPart.VISUAL_ID:
				return DurationInterval_Shape;
			case IntervalEditPart.VISUAL_ID:
				return Interval_Shape;
			case InstanceValueEditPart.VISUAL_ID:
				return InstanceValue_Shape;
			case CommentEditPart.VISUAL_ID:
				return Comment_Shape;
			case DurationConstraintEditPart.VISUAL_ID:
				return DurationConstraint_Shape;
			case TimeConstraintEditPart.VISUAL_ID:
				return TimeConstraint_Shape;
			case IntervalConstraintEditPart.VISUAL_ID:
				return IntervalConstraint_Shape;
			case InteractionConstraintEditPart.VISUAL_ID:
				return InteractionConstraint_Shape;
			case ConstraintEditPart.VISUAL_ID:
				return Constraint_Shape;
			case BehaviorPortEditPart.VISUAL_ID:
				return Port_BehaviorShape;
			case PortEditPart.VISUAL_ID:
				return Port_Shape;
			case ParameterEditPart.VISUAL_ID:
				return Parameter_Shape;
			case PropertyPartEditPartCN.VISUAL_ID:
				return Property_Shape;
			case CollaborationRoleEditPartCN.VISUAL_ID:
				return ConnectableElement_CollaborationRoleShape;
			case CollaborationUseEditPartCN.VISUAL_ID:
				return CollaborationUse_Shape;
			case ActivityCompositeEditPartCN.VISUAL_ID:
				return Activity_Shape_CN;
			case InteractionCompositeEditPartCN.VISUAL_ID:
				return Interaction_Shape_CN;
			case ProtocolStateMachineCompositeEditPartCN.VISUAL_ID:
				return ProtocolStateMachine_Shape_CN;
			case StateMachineCompositeEditPartCN.VISUAL_ID:
				return StateMachine_Shape_CN;
			case FunctionBehaviorCompositeEditPartCN.VISUAL_ID:
				return FunctionBehavior_Shape_CN;
			case OpaqueBehaviorCompositeEditPartCN.VISUAL_ID:
				return OpaqueBehavior_Shape_CN;
			case ComponentCompositeEditPartCN.VISUAL_ID:
				return Component_Shape_CN;
			case DeviceCompositeEditPartCN.VISUAL_ID:
				return Device_Shape_CN;
			case ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID:
				return ExecutionEnvironment_Shape_CN;
			case NodeCompositeEditPartCN.VISUAL_ID:
				return Node_Shape_CN;
			case ClassCompositeEditPartCN.VISUAL_ID:
				return Class_Shape_CN;
			case CollaborationCompositeEditPartCN.VISUAL_ID:
				return Collaboration_Shape_CN;
			case InterfaceEditPartCN.VISUAL_ID:
				return Interface_Shape_CN;
			case PrimitiveTypeEditPartCN.VISUAL_ID:
				return PrimitiveType_Shape_CN;
			case EnumerationEditPartCN.VISUAL_ID:
				return Enumeration_Shape_CN;
			case DataTypeEditPartCN.VISUAL_ID:
				return DataType_Shape_CN;
			case ActorEditPartCN.VISUAL_ID:
				return Actor_Shape_CN;
			case DeploymentSpecificationEditPartCN.VISUAL_ID:
				return DeploymentSpecification_Shape_CN;
			case ArtifactEditPartCN.VISUAL_ID:
				return Artifact_Shape_CN;
			case InformationItemEditPartCN.VISUAL_ID:
				return InformationItem_Shape_CN;
			case SignalEditPartCN.VISUAL_ID:
				return Signal_Shape_CN;
			case UseCaseEditPartCN.VISUAL_ID:
				return UseCase_Shape_CN;
			case CommentEditPartCN.VISUAL_ID:
				return Comment_Shape_CN;
			case DurationConstraintEditPartCN.VISUAL_ID:
				return DurationConstraint_Shape_CN;
			case TimeConstraintEditPartCN.VISUAL_ID:
				return TimeConstraint_Shape_CN;
			case IntervalConstraintEditPartCN.VISUAL_ID:
				return IntervalConstraint_Shape_CN;
			case InteractionConstraintEditPartCN.VISUAL_ID:
				return InteractionConstraint_Shape_CN;
			case ConstraintEditPartCN.VISUAL_ID:
				return Constraint_Shape_CN;
			case PropertyEditPartCLN.VISUAL_ID:
				return Property_AttributeLabel;
			case OperationEditPartCLN.VISUAL_ID:
				return Operation_OperationLabel;
			case EnumerationLiteralEditPartCLN.VISUAL_ID:
				return EnumerationLiteral_LiteralLabel;
			case BehaviorPortLinkEditPart.VISUAL_ID:
				return Port_BehaviorEdge;
			case LinkDescriptorEditPart.VISUAL_ID:
				return Link_DescriptorEdge;
			case CommentAnnotatedElementEditPart.VISUAL_ID:
				return Comment_AnnotatedElementEdge;
			case ConstraintConstrainedElementEditPart.VISUAL_ID:
				return Constraint_ConstrainedElementEdge;
			case ComponentRealizationEditPart.VISUAL_ID:
				return ComponentRealization_Edge;
			case InterfaceRealizationEditPart.VISUAL_ID:
				return InterfaceRealization_Edge;
			case SubstitutionEditPart.VISUAL_ID:
				return Substitution_Edge;
			case RealizationEditPart.VISUAL_ID:
				return Realization_Edge;
			case ManifestationEditPart.VISUAL_ID:
				return Manifestation_Edge;
			case AbstractionEditPart.VISUAL_ID:
				return Abstraction_Edge;
			case UsageEditPart.VISUAL_ID:
				return Usage_Edge;
			case DeploymentEditPart.VISUAL_ID:
				return Deployment_Edge;
			case RoleBindingEditPart.VISUAL_ID:
				return Dependency_RoleBindingEdge;
			case DependencyEditPart.VISUAL_ID:
				return Dependency_Edge;
			case ConnectorEditPart.VISUAL_ID:
				return Connector_Edge;
			case GeneralizationEditPart.VISUAL_ID:
				return Generalization_Edge;
			case TimeObservationEventEditPart.VISUAL_ID:
				return TimeObservation_EventEdge;
			case DurationObservationEventEditPart.VISUAL_ID:
				return DurationObservation_EventEdge;
			case RepresentationEditPart.VISUAL_ID:
				return Representation_Edge;
			case InformationFlowEditPart.VISUAL_ID:
				return InformationFlow_Edge;
			}
		}
		return null;
	}

	/**
	 * @generated
	 */
	public static final DiagramElementTypes TYPED_INSTANCE = new DiagramElementTypes(elementTypeImages) {

		/**
		 * @generated
		 */
		@Override
		public boolean isKnownElementType(IElementType elementType) {
			return org.eclipse.papyrus.uml.diagram.composite.providers.UMLElementTypes.isKnownElementType(elementType);
		}

		/**
		 * @generated
		 */
		@Override
		public IElementType getElementTypeForVisualId(String visualID) {
			return org.eclipse.papyrus.uml.diagram.composite.providers.UMLElementTypes.getElementType(visualID);
		}

		/**
		 * @generated
		 */
		@Override
		public ENamedElement getDefiningNamedElement(IAdaptable elementTypeAdapter) {
			return org.eclipse.papyrus.uml.diagram.composite.providers.UMLElementTypes.getElement(elementTypeAdapter);
		}
	};

	/**
	 * @generated
	 */
	public static boolean isKindOf(IElementType subtype, IElementType supertype) {
		boolean result = subtype == supertype;

		if (!result) {
			IElementType[] supertypes = subtype.getAllSuperTypes();
			for (int i = 0; !result && (i < supertypes.length); i++) {
				result = supertype == supertypes[i];
			}
		}

		return result;
	}
}
