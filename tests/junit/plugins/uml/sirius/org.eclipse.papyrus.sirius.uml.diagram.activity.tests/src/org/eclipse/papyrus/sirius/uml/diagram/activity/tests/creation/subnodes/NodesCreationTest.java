/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests.creation.subnodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.activity.services.ActivityFeatureProvider;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.MappingTypes;
import org.eclipse.papyrus.uml.domain.services.UMLHelper;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.viewpoint.DMappingBased;
import org.eclipse.uml2.uml.AcceptCallAction;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.ActionInputPin;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityGroup;
import org.eclipse.uml2.uml.ActivityParameterNode;
import org.eclipse.uml2.uml.ActivityPartition;
import org.eclipse.uml2.uml.AddStructuralFeatureValueAction;
import org.eclipse.uml2.uml.BroadcastSignalAction;
import org.eclipse.uml2.uml.CallBehaviorAction;
import org.eclipse.uml2.uml.CallOperationAction;
import org.eclipse.uml2.uml.ClearAssociationAction;
import org.eclipse.uml2.uml.ClearStructuralFeatureAction;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.ConditionalNode;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.CreateObjectAction;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.DestroyObjectAction;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExpansionNode;
import org.eclipse.uml2.uml.ExpansionRegion;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.InterruptibleActivityRegion;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.LoopNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.OpaqueAction;
import org.eclipse.uml2.uml.OutputPin;
import org.eclipse.uml2.uml.ReadExtentAction;
import org.eclipse.uml2.uml.ReadIsClassifiedObjectAction;
import org.eclipse.uml2.uml.ReadSelfAction;
import org.eclipse.uml2.uml.ReadStructuralFeatureAction;
import org.eclipse.uml2.uml.ReclassifyObjectAction;
import org.eclipse.uml2.uml.ReduceAction;
import org.eclipse.uml2.uml.SendObjectAction;
import org.eclipse.uml2.uml.SendSignalAction;
import org.eclipse.uml2.uml.SequenceNode;
import org.eclipse.uml2.uml.StartClassifierBehaviorAction;
import org.eclipse.uml2.uml.StartObjectBehaviorAction;
import org.eclipse.uml2.uml.StructuredActivityNode;
import org.eclipse.uml2.uml.TestIdentityAction;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValuePin;
import org.eclipse.uml2.uml.ValueSpecificationAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the creation of sub-nodes for Activity diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/creation/nodes/subNodes.di")
@RunWith(value = Parameterized.class)
public class NodesCreationTest extends AbstractCreateNodeTests {

	private static final String ROOT_ACTIVITY = "RootActivity"; //$NON-NLS-1$

	private static final String SUB_ACTIVITY = "SubActivity"; //$NON-NLS-1$

	private static final String ACTIVITY_PARTITION = "ActivityPartition"; //$NON-NLS-1$

	private static final String EXPANSION_REGION = "ExpansionRegion"; //$NON-NLS-1$

	private static final String STRUCTURED_ACTIVITY_NODE = "StructuredActivityNode"; //$NON-NLS-1$

	private static final String SUB_NODE_DIAGRAM_NAME = "SubNodeActivityDiagramSirius"; //$NON-NLS-1$

	private static final String TOP_NODE_DIAGRAM_NAME = "TopNodeActivityDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private EReference containmentFeature;

	private String semanticOwnerName;

	private List<String> nodeCompartmentTypes;

	private List<String> synchronizedBorderNodes;

	private final Class<? extends Element> expectedType;

	private Activity rootActivity;

	/**
	 * Default Constructor.
	 *
	 */
	public NodesCreationTest(String creationToolId, String nodeMappingType, List<String> nodeCompartmentTypes, List<String> synchronizedBorderNodes, Class<? extends Element> expectedType) {
		this.creationToolId = creationToolId;
		this.nodeMappingType = nodeMappingType;
		this.containmentFeature = null;
		this.nodeCompartmentTypes = List.copyOf(nodeCompartmentTypes);
		this.synchronizedBorderNodes = List.copyOf(synchronizedBorderNodes);
		this.expectedType = expectedType;
	}

	@Override
	public void setUp() {
		super.setUp();
		this.rootActivity = (Activity) this.root.getMember(ROOT_ACTIVITY);
	}

	@Test
	@ActiveDiagram(TOP_NODE_DIAGRAM_NAME)
	public void createNodeIntoRootActivity() {
		List<String> forbiddenConceptList = List.of(MappingTypes.EXPANSION_NODE_NODE_TYPE, MappingTypes.ACTION_INPUT_PIN_NODE_TYPE, MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.OUTPUT_PIN_NODE_TYPE, MappingTypes.VALUE_PIN_NODE_TYPE);
		if (!forbiddenConceptList.contains(this.nodeMappingType)) {
			this.semanticOwnerName = ROOT_ACTIVITY;
			Element semanticOwner = this.getSemanticOwner();
			this.createNodeIntoContainer(semanticOwner, semanticOwner, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_AD_COMPARTMENTS_TYPE); // $NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(SUB_NODE_DIAGRAM_NAME)
	public void createNodeIntoSubActivity() {
		List<String> forbiddenConceptList = List.of(MappingTypes.EXPANSION_NODE_NODE_TYPE, MappingTypes.ACTION_INPUT_PIN_NODE_TYPE, MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.OUTPUT_PIN_NODE_TYPE, MappingTypes.VALUE_PIN_NODE_TYPE);
		if (!forbiddenConceptList.contains(this.nodeMappingType)) {
			this.semanticOwnerName = SUB_ACTIVITY;
			Element semanticOwner = this.getElementByNameFromRoot(this.semanticOwnerName).get();
			this.createNodeIntoContainer(semanticOwner, semanticOwner, MappingTypes.SUB_ACTIVITY_NODE_TYPE, MappingTypes.SUB_ACTIVITY_AD_COMPARTMENTS_TYPE); // $NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(SUB_NODE_DIAGRAM_NAME)
	public void createNodeIntoStructuredActivityNode() {
		List<String> forbiddenConceptList = List.of(MappingTypes.ACTIVITY_PARTITION_NODE_TYPE, MappingTypes.SUB_ACTIVITY_NODE_TYPE, MappingTypes.INTERRUPTIBLE_ACTIVITY_REGION_NODE_TYPE, MappingTypes.ACTIVITY_PARAMETER_NODE_NODE_TYPE,
				MappingTypes.EXPANSION_NODE_NODE_TYPE);
		if (!forbiddenConceptList.contains(this.nodeMappingType)) {
			this.semanticOwnerName = STRUCTURED_ACTIVITY_NODE;
			Element semanticOwner = this.getElementByNameFromRoot(this.semanticOwnerName).get();
			this.createNodeIntoContainer(semanticOwner, semanticOwner, MappingTypes.STRUCTURED_ACTIVITY_NODE_NODE_TYPE, MappingTypes.STRUCTURED_ACTIVITY_NODE_AD_COMPARTMENTS_TYPE); // $NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(SUB_NODE_DIAGRAM_NAME)
	public void createNodeIntoExpansionRegion() {
		List<String> forbiddenConceptList = List.of(MappingTypes.ACTIVITY_PARTITION_NODE_TYPE, MappingTypes.SUB_ACTIVITY_NODE_TYPE, MappingTypes.INTERRUPTIBLE_ACTIVITY_REGION_NODE_TYPE, MappingTypes.ACTIVITY_PARAMETER_NODE_NODE_TYPE);
		if (!forbiddenConceptList.contains(this.nodeMappingType)) {
			this.semanticOwnerName = EXPANSION_REGION;
			Element semanticOwner = this.getElementByNameFromRoot(this.semanticOwnerName).get();
			this.createNodeIntoContainer(semanticOwner, semanticOwner, MappingTypes.EXPANSION_REGION_NODE_TYPE, MappingTypes.EXPANSION_REGION_AD_COMPARTMENTS_TYPE); // $NON-NLS-1$
		}
	}

	@Test
	@ActiveDiagram(SUB_NODE_DIAGRAM_NAME)
	public void createNodeIntoActivityPartition() {
		List<String> forbiddenConceptList = List.of(MappingTypes.SUB_ACTIVITY_NODE_TYPE, MappingTypes.INTERRUPTIBLE_ACTIVITY_REGION_NODE_TYPE, MappingTypes.ACTIVITY_PARAMETER_NODE_NODE_TYPE, MappingTypes.ACTION_INPUT_PIN_NODE_TYPE,
				MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.OUTPUT_PIN_NODE_TYPE, MappingTypes.VALUE_PIN_NODE_TYPE, MappingTypes.EXPANSION_NODE_NODE_TYPE, MappingTypes.CONSTRAINT_NODE_TYPE);
		if (!forbiddenConceptList.contains(this.nodeMappingType)) {
			this.semanticOwnerName = ACTIVITY_PARTITION;
			Element semanticOwner = this.rootActivity;
			Element graphicalOwnerSemanticTarget = this.getElementByNameFromRoot(this.semanticOwnerName).get();
			if (MappingTypes.ACTIVITY_PARTITION_NODE_TYPE.equals(this.nodeMappingType) || MappingTypes.COMMENT_NODE_TYPE.equals(this.nodeMappingType)) {
				semanticOwner = graphicalOwnerSemanticTarget;
			}
			this.createNodeIntoContainer(semanticOwner, graphicalOwnerSemanticTarget, MappingTypes.ACTIVITY_PARTITION_NODE_TYPE, MappingTypes.ACTIVITY_PARTITION_AD_COMPARTMENTS_TYPE); // $NON-NLS-1$
		}
	}

	/**
	 * Create a node in a container.
	 *
	 * @param semanticOwner
	 *            the semantic parent that will contain the created object
	 * @param targetedContainer
	 *            the container targeted by the creation tool
	 * @param nodeContainerType
	 *            the mapping of the graphical container where the element will be created
	 * @param nodeCompartmentContainerType
	 *            the type of the compartment in which we want to cretate the element
	 */
	private void createNodeIntoContainer(Element semanticOwner, Element targetedContainer, final String nodeContainerType, final String nodeCompartmentContainerType) {
		final String containerType;
		if (MappingTypes.isBorderNode(this.nodeMappingType)) {
			containerType = nodeContainerType;
		} else {
			containerType = nodeCompartmentContainerType;
		}
		final EObject graphicalContainer = this.getSubNodeOfGraphicalContainer(this.getTopGraphicalContainer(), containerType);
		this.containmentFeature = this.getContainmentFeature(semanticOwner);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(semanticOwner, this.containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker;
		if (MappingTypes.isNode(this.nodeMappingType) || MappingTypes.isBorderNode(this.nodeMappingType)) {
			graphicalNodeCreationChecker = new DNodeCreationChecker(this.getDiagram(), graphicalContainer, this.nodeMappingType, this.synchronizedBorderNodes);
		} else {
			graphicalNodeCreationChecker = new DNodeContainerCreationChecker(this.getDiagram(), graphicalContainer, this.nodeMappingType, this.nodeCompartmentTypes, this.synchronizedBorderNodes);
		}

		this.createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	/**
	 * Similar to org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests#getSubNodeOfGraphicalContainer(String), but we need to browse
	 * containers recursively to find the appropriate mapping.
	 *
	 * @param container
	 *            the container we will browse
	 * @param mappingID
	 *            the mapping we are looking for
	 * @return the node with the expected mappingID
	 */
	private EObject getSubNodeOfGraphicalContainer(EObject container, String mappingID) {
		EObject result = null;
		if (container instanceof DMappingBased && ((DMappingBased) container).getMapping().getName().equals(mappingID)) {
			result = container;
		} else if (container instanceof DNodeContainer) {
			for (final DDiagramElement diagramElement : ((DNodeContainer) container).getOwnedDiagramElements()) {
				if (result != null) {
					break;
				}
				if (mappingID.equals(diagramElement.getMapping().getName())) {
					result = diagramElement;
				} else if (diagramElement instanceof DNodeContainer) {
					result = this.getSubNodeOfGraphicalContainer(diagramElement, mappingID);
				}
			}
		}
		return result;
	}

	/**
	 * This method is used to get the containment feature to use to create the expected type element depending on the semanticOwner.
	 *
	 * @param targetedContainer
	 *            the container targeted by the creation tool
	 * @return the containment reference to use
	 */
	private EReference getContainmentFeature(Element targetedContainer) {
		EReference feature = null;
		EClass expectedEClass = UMLHelper.toEClass(this.expectedType.getSimpleName());
		if (UMLPackage.eINSTANCE.getActivityGroup().isSuperTypeOf(expectedEClass) && !UMLPackage.eINSTANCE.getStructuredActivityNode().isSuperTypeOf(expectedEClass)) {
			if (UMLPackage.eINSTANCE.getActivityPartition().isSuperTypeOf(expectedEClass)) {
				if (targetedContainer instanceof Activity) {
					feature = UMLPackage.eINSTANCE.getActivity_OwnedGroup();
				} else if (targetedContainer instanceof ActivityPartition) {
					feature = UMLPackage.eINSTANCE.getActivityPartition_Subpartition();
				}
			} else if (UMLPackage.eINSTANCE.getInterruptibleActivityRegion().isSuperTypeOf(expectedEClass)) {
				feature = UMLPackage.eINSTANCE.getActivity_OwnedGroup();
			}
		} else if (UMLPackage.eINSTANCE.getActivityNode().isSuperTypeOf(expectedEClass)) {
			if (UMLPackage.eINSTANCE.getStructuredActivityNode().isSuperTypeOf(expectedEClass)) {
				if (targetedContainer instanceof Activity || targetedContainer instanceof ActivityGroup && !(targetedContainer instanceof StructuredActivityNode)) {
					feature = UMLPackage.eINSTANCE.getActivity_StructuredNode();
				} else if (targetedContainer instanceof StructuredActivityNode) {
					feature = UMLPackage.eINSTANCE.getStructuredActivityNode_Node();
				}
			} else if (UMLPackage.eINSTANCE.getPin().isSuperTypeOf(expectedEClass)) {
				feature = (EReference) new ActivityFeatureProvider().getActivityFeature(targetedContainer, this.expectedType.getSimpleName());
			} else {
				if (targetedContainer instanceof Activity) {
					feature = UMLPackage.eINSTANCE.getActivity_OwnedNode();
				} else if (targetedContainer instanceof ActivityGroup && !(targetedContainer instanceof StructuredActivityNode)) {
					feature = UMLPackage.eINSTANCE.getActivity_OwnedNode();
				} else if (targetedContainer instanceof StructuredActivityNode) {
					feature = UMLPackage.eINSTANCE.getStructuredActivityNode_Node();
				}
			}
		} else if (UMLPackage.eINSTANCE.getActivity().isSuperTypeOf(expectedEClass)) {
			feature = UMLPackage.eINSTANCE.getClass_NestedClassifier();
		} else if (UMLPackage.eINSTANCE.getComment().isSuperTypeOf(expectedEClass)) {
			feature = UMLPackage.eINSTANCE.getElement_OwnedComment();
		} else if (UMLPackage.eINSTANCE.getConstraint().isSuperTypeOf(expectedEClass)) {
			feature = UMLPackage.eINSTANCE.getNamespace_OwnedRule();
		}
		return feature;
	}

	@Override
	protected Element getSemanticOwner() {
		return this.root.getMember(this.semanticOwnerName);
	}

	@Override
	protected Element getRootElement() {
		return this.rootActivity;
	}

	@Override
	protected EObject getTopGraphicalContainer() {
		return this.getNodeFromDiagram(ROOT_ACTIVITY, MappingTypes.ACTIVITY_NODE_TYPE);
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__ACCEPT_CALL_ACTION__TOOL, MappingTypes.ACCEPT_CALL_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.OUTPUT_PIN_NODE_TYPE), AcceptCallAction.class },
				{ CreationToolsIds.CREATE__ACCEPT_EVENT_ACTION__TOOL, MappingTypes.ACCEPT_EVENT_ACTION_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), AcceptEventAction.class },
				{ CreationToolsIds.CREATE__ACTION_INPUT_PIN__TOOL, MappingTypes.ACTION_INPUT_PIN_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), ActionInputPin.class },
				{ CreationToolsIds.CREATE__ACTIVITY_FINAL_NODE__TOOL, MappingTypes.ACTIVITY_FINAL_NODE_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), ActivityFinalNode.class },
				{ CreationToolsIds.CREATE__ACTIVITY_PARAMETER_NODE__TOOL, MappingTypes.ACTIVITY_PARAMETER_NODE_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), ActivityParameterNode.class },
				{ CreationToolsIds.CREATE__ACTIVITY_PARTITION__TOOL, MappingTypes.ACTIVITY_PARTITION_NODE_TYPE, List.of(MappingTypes.ACTIVITY_PARTITION_AD_COMPARTMENTS_TYPE), Collections.emptyList(), ActivityPartition.class },
				{ CreationToolsIds.CREATE__ACTIVITY__TOOL, MappingTypes.SUB_ACTIVITY_NODE_TYPE, List.of(MappingTypes.SUB_ACTIVITY_AD_COMPARTMENTS_TYPE), Collections.emptyList(), Activity.class },
				{ CreationToolsIds.CREATE__ADD_STRUCTURAL_FEATURE_VALUE_ACTION__TOOL, MappingTypes.ADD_STRUCTURAL_FEATURE_VALUE_ACTION_NODE_TYPE, Collections.emptyList(),
						List.of(MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.OUTPUT_PIN_NODE_TYPE, MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.INPUT_PIN_NODE_TYPE), AddStructuralFeatureValueAction.class },
				{ CreationToolsIds.CREATE__BROADCAST_SIGNAL_ACTION__TOOL, MappingTypes.BROADCAST_SIGNAL_ACTION_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), BroadcastSignalAction.class },
				{ CreationToolsIds.CREATE__CALL_BEHAVIOR_ACTION__TOOL, MappingTypes.CALL_BEHAVIOR_ACTION_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), CallBehaviorAction.class },
				{ CreationToolsIds.CREATE__CALL_OPERATION_ACTION__TOOL, MappingTypes.CALL_OPERATION_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE), CallOperationAction.class },
				{ CreationToolsIds.CREATE__CLEAR_ASSOCIATION_ACTION__TOOL, MappingTypes.CLEAR_ASSOCIATION_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE), ClearAssociationAction.class },
				{ CreationToolsIds.CREATE__CLEAR_STRUCTURAL_FEATURE_ACTION__TOOL, MappingTypes.CLEAR_STRUCTURAL_FEATURE_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.OUTPUT_PIN_NODE_TYPE),
						ClearStructuralFeatureAction.class },
				{ CreationToolsIds.CREATE__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), Comment.class },
				{ CreationToolsIds.CREATE__CONDITIONAL_NODE__TOOL, MappingTypes.CONDITIONAL_NODE_NODE_TYPE, List.of(MappingTypes.CONDITIONAL_NODE_AD_COMPARTMENTS_TYPE), Collections.emptyList(), ConditionalNode.class },
				{ CreationToolsIds.CREATE__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), Constraint.class },
				{ CreationToolsIds.CREATE__CREATE_OBJECT_ACTION__TOOL, MappingTypes.CREATE_OBJECT_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.OUTPUT_PIN_NODE_TYPE), CreateObjectAction.class },
				{ CreationToolsIds.CREATE__DECISION_NODE__TOOL, MappingTypes.DECISION_NODE_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), DecisionNode.class },
				{ CreationToolsIds.CREATE__DESTROY_OBJECT_ACTION__TOOL, MappingTypes.DESTROY_OBJECT_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE), DestroyObjectAction.class },
				{ CreationToolsIds.CREATE__EXPANSION_REGION__TOOL, MappingTypes.EXPANSION_REGION_NODE_TYPE, List.of(MappingTypes.EXPANSION_REGION_AD_COMPARTMENTS_TYPE), Collections.emptyList(), ExpansionRegion.class },
				{ CreationToolsIds.CREATE__FLOW_FINAL_NODE__TOOL, MappingTypes.FLOW_FINAL_NODE_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), FlowFinalNode.class },
				{ CreationToolsIds.CREATE__FORK_NODE__TOOL, MappingTypes.FORK_NODE_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), ForkNode.class },
				{ CreationToolsIds.CREATE__INITIAL_NODE__TOOL, MappingTypes.INITIAL_NODE_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), InitialNode.class },
				{ CreationToolsIds.CREATE__INPUT_EXPANSION_NODE__TOOL, MappingTypes.EXPANSION_NODE_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), ExpansionNode.class },
				{ CreationToolsIds.CREATE__INPUT_PIN__TOOL, MappingTypes.INPUT_PIN_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), InputPin.class },
				{ CreationToolsIds.CREATE__INTERRUPTIBLE_ACTIVITY_REGION__TOOL, MappingTypes.INTERRUPTIBLE_ACTIVITY_REGION_NODE_TYPE, List.of(MappingTypes.INTERRUPTIBLE_ACTIVITY_REGION_AD_COMPARTMENTS_TYPE), Collections.emptyList(),
						InterruptibleActivityRegion.class },
				{ CreationToolsIds.CREATE__JOIN_NODE__TOOL, MappingTypes.JOIN_NODE_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), JoinNode.class },
				{ CreationToolsIds.CREATE__LOOP_NODE__TOOL, MappingTypes.LOOP_NODE_NODE_TYPE, List.of(MappingTypes.LOOP_NODE_AD_COMPARTMENTS_TYPE), Collections.emptyList(), LoopNode.class },
				{ CreationToolsIds.CREATE__MERGE_NODE__TOOL, MappingTypes.MERGE_NODE_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), MergeNode.class },
				{ CreationToolsIds.CREATE__OPAQUE_ACTION__TOOL, MappingTypes.OPAQUE_ACTION_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), OpaqueAction.class },
				{ CreationToolsIds.CREATE__OUTPUT_EXPANSION_NODE__TOOL, MappingTypes.EXPANSION_NODE_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), ExpansionNode.class },
				{ CreationToolsIds.CREATE__OUTPUT_PIN__TOOL, MappingTypes.OUTPUT_PIN_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), OutputPin.class },
				{ CreationToolsIds.CREATE__READ_EXTENT_ACTION__TOOL, MappingTypes.READ_EXTENT_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.OUTPUT_PIN_NODE_TYPE), ReadExtentAction.class },
				{ CreationToolsIds.CREATE__READ_IS_CLASSIFIED_OBJECT_ACTION__TOOL, MappingTypes.READ_IS_CLASSIFIED_OBJECT_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.OUTPUT_PIN_NODE_TYPE),
						ReadIsClassifiedObjectAction.class },
				{ CreationToolsIds.CREATE__READ_SELF_ACTION__TOOL, MappingTypes.READ_SELF_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.OUTPUT_PIN_NODE_TYPE), ReadSelfAction.class },
				{ CreationToolsIds.CREATE__READ_STRUCTURAL_FEATURE_ACTION__TOOL, MappingTypes.READ_STRUCTURAL_FEATURE_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.OUTPUT_PIN_NODE_TYPE),
						ReadStructuralFeatureAction.class },
				{ CreationToolsIds.CREATE__RECLASSIFY_OBJECT_ACTION__TOOL, MappingTypes.RECLASSIFY_OBJECT_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE), ReclassifyObjectAction.class },
				{ CreationToolsIds.CREATE__REDUCE_ACTION__TOOL, MappingTypes.REDUCE_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.OUTPUT_PIN_NODE_TYPE), ReduceAction.class },
				{ CreationToolsIds.CREATE__SEND_OBJECT_ACTION__TOOL, MappingTypes.SEND_OBJECT_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.INPUT_PIN_NODE_TYPE), SendObjectAction.class },
				{ CreationToolsIds.CREATE__SEND_SIGNAL_ACTION__TOOL, MappingTypes.SEND_SIGNAL_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE), SendSignalAction.class },
				{ CreationToolsIds.CREATE__SEQUENCE_NODE__TOOL, MappingTypes.SEQUENCE_NODE_NODE_TYPE, List.of(MappingTypes.SEQUENCE_NODE_AD_COMPARTMENTS_TYPE), Collections.emptyList(), SequenceNode.class },
				{ CreationToolsIds.CREATE__START_CLASSIFIER_BEHAVIOR_ACTION__TOOL, MappingTypes.START_CLASSIFIER_BEHAVIOR_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE), StartClassifierBehaviorAction.class },
				{ CreationToolsIds.CREATE__START_OBJECT_BEHAVIOR_ACTION__TOOL, MappingTypes.START_OBJECT_BEHAVIOR_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE), StartObjectBehaviorAction.class },
				{ CreationToolsIds.CREATE__STRUCTURED_ACTIVITY_NODE__TOOL, MappingTypes.STRUCTURED_ACTIVITY_NODE_NODE_TYPE, List.of(MappingTypes.STRUCTURED_ACTIVITY_NODE_AD_COMPARTMENTS_TYPE), Collections.emptyList(), StructuredActivityNode.class },
				{ CreationToolsIds.CREATE__TEST_IDENTITY_ACTION__TOOL, MappingTypes.TEST_IDENTITY_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.INPUT_PIN_NODE_TYPE, MappingTypes.OUTPUT_PIN_NODE_TYPE, MappingTypes.INPUT_PIN_NODE_TYPE),
						TestIdentityAction.class },
				{ CreationToolsIds.CREATE__VALUE_PIN__TOOL, MappingTypes.VALUE_PIN_NODE_TYPE, Collections.emptyList(), Collections.emptyList(), ValuePin.class },
				{ CreationToolsIds.CREATE__VALUE_SPECIFICATION_ACTION__TOOL, MappingTypes.VALUE_SPECIFICATION_ACTION_NODE_TYPE, Collections.emptyList(), List.of(MappingTypes.OUTPUT_PIN_NODE_TYPE), ValueSpecificationAction.class },
		});
	}
}
