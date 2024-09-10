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
import org.eclipse.papyrus.uml.diagram.composite.edit.parts.*;
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
		if (!CompositeStructureDiagramEditPart.MODEL_ID.equals(diagram.getType())) {
			UMLDiagramEditorPlugin.getInstance().logError("Incorrect diagram passed as a parameter: " + diagram.getType());
			return;
		}
		if (false == diagram.getElement() instanceof Package) {
			UMLDiagramEditorPlugin.getInstance().logError("Incorrect diagram element specified: " + diagram.getElement() + " instead of Package");
			return;
		}
		createPackage_CompositeStructureDiagram_Children(diagram);
		createLinks(diagram);
	}

	/**
	 * @generated
	 */
	private void createPackage_CompositeStructureDiagram_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getPackage_CompositeStructureDiagram_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createActivity_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getActivity_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getActivity_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createActivity_StructureCompartment_Children(getCompartment(view, ActivityCompositeCompartmentEditPart.VISUAL_ID));
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
		createInteraction_StructureCompartment_Children(getCompartment(view, InteractionCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createProtocolStateMachine_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getProtocolStateMachine_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getProtocolStateMachine_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createProtocolStateMachine_StructureCompartment_Children(getCompartment(view, ProtocolStateMachineCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createStateMachine_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getStateMachine_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getStateMachine_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createStateMachine_StructureCompartment_Children(getCompartment(view, StateMachineCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createFunctionBehavior_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getFunctionBehavior_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getFunctionBehavior_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createFunctionBehavior_StructureCompartment_Children(getCompartment(view, FunctionBehaviorCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createOpaqueBehavior_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getOpaqueBehavior_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getOpaqueBehavior_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createOpaqueBehavior_StructureCompartment_Children(getCompartment(view, OpaqueBehaviorCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createComponent_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getComponent_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getComponent_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createComponent_StructureCompartment_Children(getCompartment(view, ComponentCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createDevice_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDevice_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDevice_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createDevice_StructureCompartment_Children(getCompartment(view, DeviceCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createExecutionEnvironment_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getExecutionEnvironment_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getExecutionEnvironment_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createExecutionEnvironment_StructureCompartment_Children(getCompartment(view, ExecutionEnvironmentCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createNode_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getNode_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getNode_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createNode_StructureCompartment_Children(getCompartment(view, NodeCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createClass_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getClass_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getClass_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createClass_StructureCompartment_Children(getCompartment(view, ClassCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createCollaboration_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getCollaboration_Shape_OutgoingLinks(view));
		createCollaboration_StructureCompartment_Children(getCompartment(view, CollaborationCompositeCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createInterface_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getInterface_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createPrimitiveType_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getPrimitiveType_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createEnumeration_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getEnumeration_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getEnumeration_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createEnumeration_LiteralCompartment_Children(getCompartment(view, EnumerationEnumerationLiteralCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createDataType_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDataType_Shape_OutgoingLinks(view));
		createDataType_AttributeCompartment_Children(getCompartment(view, DataTypeAttributeCompartmentEditPart.VISUAL_ID));
		createDataType_OperationCompartment_Children(getCompartment(view, DataTypeOperationCompartmentEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createActor_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getActor_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createDeploymentSpecification_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDeploymentSpecification_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createArtifact_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getArtifact_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createInformationItem_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getInformationItem_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createSignal_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getSignal_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createUseCase_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getUseCase_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createSignalEvent_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getSignalEvent_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createCallEvent_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getCallEvent_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createAnyReceiveEvent_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getAnyReceiveEvent_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createChangeEvent_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getChangeEvent_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createTimeEvent_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getTimeEvent_Shape_OutgoingLinks(view));
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
	private void createTimeObservation_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getTimeObservation_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createLiteralBoolean_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getLiteralBoolean_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createLiteralInteger_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getLiteralInteger_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createLiteralNull_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getLiteralNull_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createLiteralString_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getLiteralString_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createLiteralUnlimitedNatural_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getLiteralUnlimitedNatural_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createStringExpression_PackagedElementShape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getStringExpression_PackagedElementShape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createOpaqueExpression_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getOpaqueExpression_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createTimeExpression_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getTimeExpression_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createExpression_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getExpression_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createDuration_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDuration_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createTimeInterval_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getTimeInterval_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createDurationInterval_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDurationInterval_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createInterval_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getInterval_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createInstanceValue_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getInstanceValue_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createComment_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getComment_Shape_OutgoingLinks(view));
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
	private void createTimeConstraint_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getTimeConstraint_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createIntervalConstraint_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getIntervalConstraint_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createInteractionConstraint_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getInteractionConstraint_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createConstraint_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getConstraint_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createPort_BehaviorShape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getPort_BehaviorShape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createPort_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getPort_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createParameter_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getParameter_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createProperty_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getProperty_Shape_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getProperty_Shape_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createProperty_StructureCompartment_Children(getCompartment(view, PropertyPartCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createConnectableElement_CollaborationRoleShape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getConnectableElement_CollaborationRoleShape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createCollaborationUse_Shape_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getCollaborationUse_Shape_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createActivity_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getActivity_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getActivity_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createActivity_StructureCompartment_CN_Children(getCompartment(view, ActivityCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createInteraction_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getInteraction_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getInteraction_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createInteraction_StructureCompartment_CN_Children(getCompartment(view, InteractionCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createProtocolStateMachine_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getProtocolStateMachine_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getProtocolStateMachine_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createProtocolStateMachine_StructureCompartment_CN_Children(getCompartment(view, ProtocolStateMachineCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createStateMachine_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getStateMachine_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getStateMachine_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createStateMachine_StructureCompartment_CN_Children(getCompartment(view, StateMachineCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createFunctionBehavior_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getFunctionBehavior_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getFunctionBehavior_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createFunctionBehavior_StructureCompartment_CN_Children(getCompartment(view, FunctionBehaviorCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createOpaqueBehavior_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getOpaqueBehavior_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getOpaqueBehavior_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createOpaqueBehavior_StructureCompartment_CN_Children(getCompartment(view, OpaqueBehaviorCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createComponent_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getComponent_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getComponent_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createComponent_StructureCompartment_CN_Children(getCompartment(view, ComponentCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createDevice_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDevice_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDevice_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createDevice_StructureCompartment_CN_Children(getCompartment(view, DeviceCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createExecutionEnvironment_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getExecutionEnvironment_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getExecutionEnvironment_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createExecutionEnvironment_StructureCompartment_CN_Children(getCompartment(view, ExecutionEnvironmentCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createNode_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getNode_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getNode_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createNode_StructureCompartment_CN_Children(getCompartment(view, NodeCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createClass_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getClass_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getClass_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createClass_StructureCompartment_CN_Children(getCompartment(view, ClassCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createCollaboration_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getCollaboration_Shape_CN_OutgoingLinks(view));
		createCollaboration_StructureCompartment_CN_Children(getCompartment(view, CollaborationCompositeCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createInterface_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getInterface_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createPrimitiveType_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getPrimitiveType_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createEnumeration_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getEnumeration_Shape_CN_OutgoingLinks(view));
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getEnumeration_Shape_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
		createEnumeration_LiteralCompartment_CN_Children(getCompartment(view, EnumerationEnumerationLiteralCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createDataType_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDataType_Shape_CN_OutgoingLinks(view));
		createDataType_AttributeCompartment_CN_Children(getCompartment(view, DataTypeAttributeCompartmentEditPartCN.VISUAL_ID));
		createDataType_OperationCompartment_CN_Children(getCompartment(view, DataTypeOperationCompartmentEditPartCN.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	private void createActor_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getActor_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createDeploymentSpecification_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDeploymentSpecification_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createArtifact_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getArtifact_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createInformationItem_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getInformationItem_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createSignal_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getSignal_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createUseCase_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getUseCase_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createComment_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getComment_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createDurationConstraint_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getDurationConstraint_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createTimeConstraint_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getTimeConstraint_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createIntervalConstraint_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getIntervalConstraint_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createInteractionConstraint_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getInteractionConstraint_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createConstraint_Shape_CN_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getConstraint_Shape_CN_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createProperty_AttributeLabel_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getProperty_AttributeLabel_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createOperation_OperationLabel_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getOperation_OperationLabel_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createEnumerationLiteral_LiteralLabel_Children(View view) {
		myDomain2NotationMap.put(view.getElement(), view);
		myLinkDescriptors.addAll(UMLDiagramUpdater.INSTANCE
				.getEnumerationLiteral_LiteralLabel_OutgoingLinks(view));
	}

	/**
	 * @generated
	 */
	private void createDataType_AttributeCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDataType_AttributeCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createDataType_OperationCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDataType_OperationCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createDataType_AttributeCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDataType_AttributeCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createDataType_OperationCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDataType_OperationCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createEnumeration_LiteralCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getEnumeration_LiteralCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createEnumeration_LiteralCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getEnumeration_LiteralCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createActivity_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getActivity_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createInteraction_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getInteraction_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createProtocolStateMachine_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getProtocolStateMachine_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createStateMachine_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getStateMachine_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createFunctionBehavior_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getFunctionBehavior_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createOpaqueBehavior_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getOpaqueBehavior_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createComponent_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getComponent_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createDevice_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDevice_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createExecutionEnvironment_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getExecutionEnvironment_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createNode_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getNode_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createClass_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getClass_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createCollaboration_StructureCompartment_CN_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getCollaboration_StructureCompartment_CN_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createActivity_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getActivity_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createInteraction_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getInteraction_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createProtocolStateMachine_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getProtocolStateMachine_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createStateMachine_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getStateMachine_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createFunctionBehavior_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getFunctionBehavior_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createOpaqueBehavior_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getOpaqueBehavior_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createComponent_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getComponent_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createDevice_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getDevice_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createExecutionEnvironment_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getExecutionEnvironment_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createNode_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getNode_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createClass_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getClass_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createCollaboration_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getCollaboration_StructureCompartment_SemanticChildren(view);
		for (Iterator it = childNodeDescriptors.iterator(); it.hasNext();) {
			createNode(view, (UMLNodeDescriptor) it.next());
		}
	}

	/**
	 * @generated
	 */
	private void createProperty_StructureCompartment_Children(View view) {
		Collection childNodeDescriptors = UMLDiagramUpdater.INSTANCE
				.getProperty_StructureCompartment_SemanticChildren(view);
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
		case ActivityCompositeEditPart.VISUAL_ID:
			createActivity_Shape_Children(node);
			return;
		case InteractionCompositeEditPart.VISUAL_ID:
			createInteraction_Shape_Children(node);
			return;
		case ProtocolStateMachineCompositeEditPart.VISUAL_ID:
			createProtocolStateMachine_Shape_Children(node);
			return;
		case StateMachineCompositeEditPart.VISUAL_ID:
			createStateMachine_Shape_Children(node);
			return;
		case FunctionBehaviorCompositeEditPart.VISUAL_ID:
			createFunctionBehavior_Shape_Children(node);
			return;
		case OpaqueBehaviorCompositeEditPart.VISUAL_ID:
			createOpaqueBehavior_Shape_Children(node);
			return;
		case ComponentCompositeEditPart.VISUAL_ID:
			createComponent_Shape_Children(node);
			return;
		case DeviceCompositeEditPart.VISUAL_ID:
			createDevice_Shape_Children(node);
			return;
		case ExecutionEnvironmentCompositeEditPart.VISUAL_ID:
			createExecutionEnvironment_Shape_Children(node);
			return;
		case NodeCompositeEditPart.VISUAL_ID:
			createNode_Shape_Children(node);
			return;
		case ClassCompositeEditPart.VISUAL_ID:
			createClass_Shape_Children(node);
			return;
		case CollaborationCompositeEditPart.VISUAL_ID:
			createCollaboration_Shape_Children(node);
			return;
		case InterfaceEditPart.VISUAL_ID:
			createInterface_Shape_Children(node);
			return;
		case PrimitiveTypeEditPart.VISUAL_ID:
			createPrimitiveType_Shape_Children(node);
			return;
		case EnumerationEditPart.VISUAL_ID:
			createEnumeration_Shape_Children(node);
			return;
		case DataTypeEditPart.VISUAL_ID:
			createDataType_Shape_Children(node);
			return;
		case ActorEditPart.VISUAL_ID:
			createActor_Shape_Children(node);
			return;
		case DeploymentSpecificationEditPart.VISUAL_ID:
			createDeploymentSpecification_Shape_Children(node);
			return;
		case ArtifactEditPart.VISUAL_ID:
			createArtifact_Shape_Children(node);
			return;
		case InformationItemEditPart.VISUAL_ID:
			createInformationItem_Shape_Children(node);
			return;
		case SignalEditPart.VISUAL_ID:
			createSignal_Shape_Children(node);
			return;
		case UseCaseEditPart.VISUAL_ID:
			createUseCase_Shape_Children(node);
			return;
		case SignalEventEditPart.VISUAL_ID:
			createSignalEvent_Shape_Children(node);
			return;
		case CallEventEditPart.VISUAL_ID:
			createCallEvent_Shape_Children(node);
			return;
		case AnyReceiveEventEditPart.VISUAL_ID:
			createAnyReceiveEvent_Shape_Children(node);
			return;
		case ChangeEventEditPart.VISUAL_ID:
			createChangeEvent_Shape_Children(node);
			return;
		case TimeEventEditPart.VISUAL_ID:
			createTimeEvent_Shape_Children(node);
			return;
		case DurationObservationEditPart.VISUAL_ID:
			createDurationObservation_Shape_Children(node);
			return;
		case TimeObservationEditPart.VISUAL_ID:
			createTimeObservation_Shape_Children(node);
			return;
		case LiteralBooleanEditPart.VISUAL_ID:
			createLiteralBoolean_Shape_Children(node);
			return;
		case LiteralIntegerEditPart.VISUAL_ID:
			createLiteralInteger_Shape_Children(node);
			return;
		case LiteralNullEditPart.VISUAL_ID:
			createLiteralNull_Shape_Children(node);
			return;
		case LiteralStringEditPart.VISUAL_ID:
			createLiteralString_Shape_Children(node);
			return;
		case LiteralUnlimitedNaturalEditPart.VISUAL_ID:
			createLiteralUnlimitedNatural_Shape_Children(node);
			return;
		case StringExpressionEditPart.VISUAL_ID:
			createStringExpression_PackagedElementShape_Children(node);
			return;
		case OpaqueExpressionEditPart.VISUAL_ID:
			createOpaqueExpression_Shape_Children(node);
			return;
		case TimeExpressionEditPart.VISUAL_ID:
			createTimeExpression_Shape_Children(node);
			return;
		case ExpressionEditPart.VISUAL_ID:
			createExpression_Shape_Children(node);
			return;
		case DurationEditPart.VISUAL_ID:
			createDuration_Shape_Children(node);
			return;
		case TimeIntervalEditPart.VISUAL_ID:
			createTimeInterval_Shape_Children(node);
			return;
		case DurationIntervalEditPart.VISUAL_ID:
			createDurationInterval_Shape_Children(node);
			return;
		case IntervalEditPart.VISUAL_ID:
			createInterval_Shape_Children(node);
			return;
		case InstanceValueEditPart.VISUAL_ID:
			createInstanceValue_Shape_Children(node);
			return;
		case CommentEditPart.VISUAL_ID:
			createComment_Shape_Children(node);
			return;
		case DurationConstraintEditPart.VISUAL_ID:
			createDurationConstraint_Shape_Children(node);
			return;
		case TimeConstraintEditPart.VISUAL_ID:
			createTimeConstraint_Shape_Children(node);
			return;
		case IntervalConstraintEditPart.VISUAL_ID:
			createIntervalConstraint_Shape_Children(node);
			return;
		case InteractionConstraintEditPart.VISUAL_ID:
			createInteractionConstraint_Shape_Children(node);
			return;
		case ConstraintEditPart.VISUAL_ID:
			createConstraint_Shape_Children(node);
			return;
		case BehaviorPortEditPart.VISUAL_ID:
			createPort_BehaviorShape_Children(node);
			return;
		case PortEditPart.VISUAL_ID:
			createPort_Shape_Children(node);
			return;
		case ParameterEditPart.VISUAL_ID:
			createParameter_Shape_Children(node);
			return;
		case PropertyPartEditPartCN.VISUAL_ID:
			createProperty_Shape_Children(node);
			return;
		case CollaborationRoleEditPartCN.VISUAL_ID:
			createConnectableElement_CollaborationRoleShape_Children(node);
			return;
		case CollaborationUseEditPartCN.VISUAL_ID:
			createCollaborationUse_Shape_Children(node);
			return;
		case ActivityCompositeEditPartCN.VISUAL_ID:
			createActivity_Shape_CN_Children(node);
			return;
		case InteractionCompositeEditPartCN.VISUAL_ID:
			createInteraction_Shape_CN_Children(node);
			return;
		case ProtocolStateMachineCompositeEditPartCN.VISUAL_ID:
			createProtocolStateMachine_Shape_CN_Children(node);
			return;
		case StateMachineCompositeEditPartCN.VISUAL_ID:
			createStateMachine_Shape_CN_Children(node);
			return;
		case FunctionBehaviorCompositeEditPartCN.VISUAL_ID:
			createFunctionBehavior_Shape_CN_Children(node);
			return;
		case OpaqueBehaviorCompositeEditPartCN.VISUAL_ID:
			createOpaqueBehavior_Shape_CN_Children(node);
			return;
		case ComponentCompositeEditPartCN.VISUAL_ID:
			createComponent_Shape_CN_Children(node);
			return;
		case DeviceCompositeEditPartCN.VISUAL_ID:
			createDevice_Shape_CN_Children(node);
			return;
		case ExecutionEnvironmentCompositeEditPartCN.VISUAL_ID:
			createExecutionEnvironment_Shape_CN_Children(node);
			return;
		case NodeCompositeEditPartCN.VISUAL_ID:
			createNode_Shape_CN_Children(node);
			return;
		case ClassCompositeEditPartCN.VISUAL_ID:
			createClass_Shape_CN_Children(node);
			return;
		case CollaborationCompositeEditPartCN.VISUAL_ID:
			createCollaboration_Shape_CN_Children(node);
			return;
		case InterfaceEditPartCN.VISUAL_ID:
			createInterface_Shape_CN_Children(node);
			return;
		case PrimitiveTypeEditPartCN.VISUAL_ID:
			createPrimitiveType_Shape_CN_Children(node);
			return;
		case EnumerationEditPartCN.VISUAL_ID:
			createEnumeration_Shape_CN_Children(node);
			return;
		case DataTypeEditPartCN.VISUAL_ID:
			createDataType_Shape_CN_Children(node);
			return;
		case ActorEditPartCN.VISUAL_ID:
			createActor_Shape_CN_Children(node);
			return;
		case DeploymentSpecificationEditPartCN.VISUAL_ID:
			createDeploymentSpecification_Shape_CN_Children(node);
			return;
		case ArtifactEditPartCN.VISUAL_ID:
			createArtifact_Shape_CN_Children(node);
			return;
		case InformationItemEditPartCN.VISUAL_ID:
			createInformationItem_Shape_CN_Children(node);
			return;
		case SignalEditPartCN.VISUAL_ID:
			createSignal_Shape_CN_Children(node);
			return;
		case UseCaseEditPartCN.VISUAL_ID:
			createUseCase_Shape_CN_Children(node);
			return;
		case CommentEditPartCN.VISUAL_ID:
			createComment_Shape_CN_Children(node);
			return;
		case DurationConstraintEditPartCN.VISUAL_ID:
			createDurationConstraint_Shape_CN_Children(node);
			return;
		case TimeConstraintEditPartCN.VISUAL_ID:
			createTimeConstraint_Shape_CN_Children(node);
			return;
		case IntervalConstraintEditPartCN.VISUAL_ID:
			createIntervalConstraint_Shape_CN_Children(node);
			return;
		case InteractionConstraintEditPartCN.VISUAL_ID:
			createInteractionConstraint_Shape_CN_Children(node);
			return;
		case ConstraintEditPartCN.VISUAL_ID:
			createConstraint_Shape_CN_Children(node);
			return;
		case PropertyEditPartCLN.VISUAL_ID:
			createProperty_AttributeLabel_Children(node);
			return;
		case OperationEditPartCLN.VISUAL_ID:
			createOperation_OperationLabel_Children(node);
			return;
		case EnumerationLiteralEditPartCLN.VISUAL_ID:
			createEnumerationLiteral_LiteralLabel_Children(node);
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
					case ComponentRealizationEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getComponentRealization_Edge_OutgoingLinks(edge));
						break;
					case InterfaceRealizationEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getInterfaceRealization_Edge_OutgoingLinks(edge));
						break;
					case SubstitutionEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getSubstitution_Edge_OutgoingLinks(edge));
						break;
					case RealizationEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getRealization_Edge_OutgoingLinks(edge));
						break;
					case ManifestationEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getManifestation_Edge_OutgoingLinks(edge));
						break;
					case AbstractionEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getAbstraction_Edge_OutgoingLinks(edge));
						break;
					case UsageEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getUsage_Edge_OutgoingLinks(edge));
						break;
					case DeploymentEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getDeployment_Edge_OutgoingLinks(edge));
						break;
					case RoleBindingEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getDependency_RoleBindingEdge_OutgoingLinks(edge));
						break;
					case DependencyEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getDependency_Edge_OutgoingLinks(edge));
						break;
					case ConnectorEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getConnector_Edge_OutgoingLinks(edge));
						break;
					case GeneralizationEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getGeneralization_Edge_OutgoingLinks(edge));
						break;
					case InformationFlowEditPart.VISUAL_ID:
						additionalDescriptors.addAll(UMLDiagramUpdater.INSTANCE
								.getInformationFlow_Edge_OutgoingLinks(edge));
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
