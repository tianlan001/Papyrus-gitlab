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
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests.drop.semantic;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticDropSubNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.MappingTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the semantic drop for the Activity Diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/drop/subNodes/subNodeSemanticDrop.di")
@RunWith(value = Parameterized.class)
public class SemanticDropIntoContainerTest extends AbstractSemanticDropSubNodeTests {

	private static final String DIAGRAM_NAME = "SubNodeSemanticDropActivityDiagramSirius"; //$NON-NLS-1$

	private static final String TOP_NODE_DIAGRAM_NAME = "TopNodeSemanticDropActivityDiagramSirius"; //$NON-NLS-1$

	private static final String ACCEPT_CALL_ACTION = "AcceptCallAction"; //$NON-NLS-1$

	private static final String ACCEPT_EVENT_ACTION = "AcceptEventAction"; //$NON-NLS-1$

	private static final String ACTIVITY = "Activity"; //$NON-NLS-1$

	private static final String ACTIVITY_FINAL_NODE = "ActivityFinalNode"; //$NON-NLS-1$

	private static final String ACTIVITY_PARAMETER_NODE = "ActivityParameterNode"; //$NON-NLS-1$

	private static final String ACTIVITY_PARTITION = "ActivityPartition"; //$NON-NLS-1$

	private static final String ADD_STRUCTURAL_FEATURE_VALUE_ACTION = "AddStructuralFeatureValueAction"; //$NON-NLS-1$

	private static final String BROADCAST_SIGNAL_ACTION = "BroadcastSignalAction"; //$NON-NLS-1$

	private static final String CALL_BEHAVIOR_ACTION = "CallBehaviorAction"; //$NON-NLS-1$

	private static final String CALL_OPERATION_ACTION = "CallOperationAction"; //$NON-NLS-1$

	private static final String CLEAR_ASSOCIATION_ACTION = "ClearAssociationAction"; //$NON-NLS-1$

	private static final String CLEAR_STRUCTURAL_FEATURE_ACTION = "ClearStructuralFeatureAction"; //$NON-NLS-1$

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String CONDITIONAL_NODE = "ConditionalNode"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String CREATE_OBJECT_ACTION = "CreateObjectAction"; //$NON-NLS-1$

	private static final String DECISION_NODE = "DecisionNode"; //$NON-NLS-1$

	private static final String DESTROY_OBJECT_ACTION = "DestroyObjectAction"; //$NON-NLS-1$

	private static final String EXPANSION_NODE = "ExpansionNode"; //$NON-NLS-1$

	private static final String EXPANSION_REGION = "ExpansionRegion"; //$NON-NLS-1$

	private static final String FLOW_FINAL_NODE = "FlowFinalNode"; //$NON-NLS-1$

	private static final String FORK_NODE = "ForkNode"; //$NON-NLS-1$

	private static final String INITIAL_NODE = "InitialNode"; //$NON-NLS-1$

	private static final String INTERRUPTIBLE_ACTIVITY_REGION = "InterruptibleActivityRegion"; //$NON-NLS-1$

	private static final String JOIN_NODE = "JoinNode"; //$NON-NLS-1$

	private static final String LOOP_NODE = "LoopNode"; //$NON-NLS-1$

	private static final String MERGE_NODE = "MergeNode"; //$NON-NLS-1$

	private static final String OPAQUE_ACTION = "OpaqueAction"; //$NON-NLS-1$

	private static final String READ_EXTENT_ACTION = "ReadExtentAction"; //$NON-NLS-1$

	private static final String READ_IS_CLASSIFIED_OBJECT_ACTION = "ReadIsClassifiedObjectAction"; //$NON-NLS-1$

	private static final String READ_SELF_ACTION = "ReadSelfAction"; //$NON-NLS-1$

	private static final String READ_STRUCTURAL_FEATURE_ACTION = "ReadStructuralFeatureAction"; //$NON-NLS-1$

	private static final String RECLASSIFY_OBJECT_ACTION = "ReclassifyObjectAction"; //$NON-NLS-1$

	private static final String REDUCE_ACTION = "ReduceAction"; //$NON-NLS-1$

	private static final String SEND_OBJECT_ACTION = "SendObjectAction"; //$NON-NLS-1$

	private static final String SEND_SIGNAL_ACTION = "SendSignalAction"; //$NON-NLS-1$

	private static final String SEQUENCE_NODE = "SequenceNode"; //$NON-NLS-1$

	private static final String START_CLASSIFIER_BEHAVIOR_ACTION = "StartClassifierBehaviorAction"; //$NON-NLS-1$

	private static final String START_OBJECT_BEHAVIOR_ACTION = "StartObjectBehaviorAction"; //$NON-NLS-1$

	private static final String STRUCTURED_ACTIVITY_NODE = "StructuredActivityNode"; //$NON-NLS-1$

	private static final String TEST_IDENTITY_ACTION = "TestIdentityAction"; //$NON-NLS-1$

	private static final String VALUE_SPECIFICATION_ACTION = "ValueSpecificationAction"; //$NON-NLS-1$

	private static final String FROM = "_From"; //$NON-NLS-1$

	private static final String AD = "AD_"; //$NON-NLS-1$

	private static final String DROP = "Drop"; //$NON-NLS-1$

	private static final String SEMANTIC = "Semantic"; //$NON-NLS-1$

	private static final String ROOT_ACTIVITY = "RootActivity"; //$NON-NLS-1$

	private static final String SUB_ACTIVITY = "SubActivity"; //$NON-NLS-1$



	private Activity rootActivity;

	private final String dropToolId;

	private final String elementConceptNameToDrop;

	private final String expectedMappingType;


	/**
	 * Default Constructor.
	 *
	 */
	public SemanticDropIntoContainerTest(String elementConceptNameToDrop) {

		this.elementConceptNameToDrop = elementConceptNameToDrop;
		this.dropToolId = SEMANTIC + elementConceptNameToDrop + DROP;
		this.expectedMappingType = AD + elementConceptNameToDrop;
	}

	@Before
	public void setUp() {
		this.rootActivity = (Activity) this.fixture.getModel().getMember(ROOT_ACTIVITY);
	}

	@Test
	@ActiveDiagram(TOP_NODE_DIAGRAM_NAME)
	public void testIntoRootActivity() {
		String expectedMappingForActivity = this.expectedMappingType;
		if (ACTIVITY.equals(this.elementConceptNameToDrop)) {
			expectedMappingForActivity = MappingTypes.SUB_ACTIVITY_NODE_TYPE;
		}
		if (!EXPANSION_NODE.equals(this.elementConceptNameToDrop)) {
			this.dropElementToContainer(this.elementConceptNameToDrop + FROM + ROOT_ACTIVITY, this.rootActivity, this.rootActivity, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_AD_COMPARTMENTS_TYPE, expectedMappingForActivity);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testIntoSubActivity() {
		NamedElement semanticOwner = this.rootActivity.getMember(SUB_ACTIVITY);
		String expectedMappingForActivity = this.expectedMappingType;
		if (ACTIVITY.equals(this.elementConceptNameToDrop)) {
			expectedMappingForActivity = MappingTypes.SUB_ACTIVITY_NODE_TYPE;
		}
		if (!EXPANSION_NODE.equals(this.elementConceptNameToDrop)) {
			this.dropElementToContainer(this.elementConceptNameToDrop + FROM + SUB_ACTIVITY, semanticOwner, semanticOwner, MappingTypes.SUB_ACTIVITY_NODE_TYPE, MappingTypes.SUB_ACTIVITY_AD_COMPARTMENTS_TYPE, expectedMappingForActivity);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testIntoActivityPartition() {
		List<String> forbiddenConceptList = List.of(EXPANSION_NODE, ACTIVITY_PARAMETER_NODE, ACTIVITY, CONSTRAINT, INTERRUPTIBLE_ACTIVITY_REGION);
		NamedElement semanticOwner = this.rootActivity;
		NamedElement graphicalOwnerSemanticTarget = this.getElementByName(semanticOwner, ACTIVITY_PARTITION).get();
		if (ACTIVITY_PARTITION.equals(this.elementConceptNameToDrop)) {
			semanticOwner = graphicalOwnerSemanticTarget;
		}
		if (!forbiddenConceptList.contains(this.elementConceptNameToDrop)) {
			this.dropElementToContainer(this.elementConceptNameToDrop + FROM + ACTIVITY_PARTITION, semanticOwner, graphicalOwnerSemanticTarget, MappingTypes.ACTIVITY_PARTITION_NODE_TYPE, MappingTypes.ACTIVITY_PARTITION_AD_COMPARTMENTS_TYPE,
					this.expectedMappingType);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testIntoConditionalNode() {
		List<String> forbiddenConceptList = List.of(ACTIVITY_PARAMETER_NODE, ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION, EXPANSION_NODE);
		NamedElement semanticOwner = this.getElementByNameFromRoot(CONDITIONAL_NODE).get();
		if (!forbiddenConceptList.contains(this.elementConceptNameToDrop)) {
			this.dropElementToContainer(this.elementConceptNameToDrop + FROM + CONDITIONAL_NODE, semanticOwner, semanticOwner, MappingTypes.CONDITIONAL_NODE_NODE_TYPE, MappingTypes.CONDITIONAL_NODE_AD_COMPARTMENTS_TYPE,
					this.expectedMappingType);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testIntoExpansionRegion() {
		List<String> forbiddenConceptList = List.of(ACTIVITY_PARAMETER_NODE, ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION);
		NamedElement semanticOwner = this.getElementByNameFromRoot(EXPANSION_REGION).get();
		if (!forbiddenConceptList.contains(this.elementConceptNameToDrop)) {
			this.dropElementToContainer(this.elementConceptNameToDrop + FROM + EXPANSION_REGION, semanticOwner, semanticOwner, MappingTypes.EXPANSION_REGION_NODE_TYPE, MappingTypes.EXPANSION_REGION_AD_COMPARTMENTS_TYPE,
					this.expectedMappingType);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testIntoInterruptibleActivityRegion() {
		List<String> forbiddenConceptList = List.of(EXPANSION_NODE, ACTIVITY_PARAMETER_NODE, ACTIVITY, CONSTRAINT, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION);
		NamedElement semanticOwner = this.rootActivity;
		NamedElement graphicalOwnerSemanticTarget = this.getElementByName(semanticOwner, INTERRUPTIBLE_ACTIVITY_REGION).get();
		if (!forbiddenConceptList.contains(this.elementConceptNameToDrop)) {
			this.dropElementToContainer(this.elementConceptNameToDrop + FROM + INTERRUPTIBLE_ACTIVITY_REGION, semanticOwner, graphicalOwnerSemanticTarget, MappingTypes.INTERRUPTIBLE_ACTIVITY_REGION_NODE_TYPE,
					MappingTypes.INTERRUPTIBLE_ACTIVITY_REGION_AD_COMPARTMENTS_TYPE,
					this.expectedMappingType);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testIntoLoopNode() {
		List<String> forbiddenConceptList = List.of(ACTIVITY_PARAMETER_NODE, ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION, EXPANSION_NODE);
		NamedElement semanticOwner = this.getElementByNameFromRoot(LOOP_NODE).get();
		if (!forbiddenConceptList.contains(this.elementConceptNameToDrop)) {
			this.dropElementToContainer(this.elementConceptNameToDrop + FROM + LOOP_NODE, semanticOwner, semanticOwner, MappingTypes.LOOP_NODE_NODE_TYPE, MappingTypes.LOOP_NODE_AD_COMPARTMENTS_TYPE,
					this.expectedMappingType);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testIntoSequenceNode() {
		List<String> forbiddenConceptList = List.of(ACTIVITY_PARAMETER_NODE, ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION, EXPANSION_NODE, INITIAL_NODE, ACTIVITY_FINAL_NODE, FLOW_FINAL_NODE, DECISION_NODE, MERGE_NODE, JOIN_NODE,
				FORK_NODE);
		NamedElement semanticOwner = this.getElementByNameFromRoot(SEQUENCE_NODE).get();
		if (!forbiddenConceptList.contains(this.elementConceptNameToDrop)) {
			this.dropElementToContainer(this.elementConceptNameToDrop + FROM + SEQUENCE_NODE, semanticOwner, semanticOwner, MappingTypes.SEQUENCE_NODE_NODE_TYPE, MappingTypes.SEQUENCE_NODE_AD_COMPARTMENTS_TYPE,
					this.expectedMappingType);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testIntoStructuredActivityNode() {
		List<String> forbiddenConceptList = List.of(ACTIVITY_PARAMETER_NODE, ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION, EXPANSION_NODE);
		NamedElement semanticOwner = this.getElementByNameFromRoot(STRUCTURED_ACTIVITY_NODE).get();
		if (!forbiddenConceptList.contains(this.elementConceptNameToDrop)) {
			this.dropElementToContainer(this.elementConceptNameToDrop + FROM + STRUCTURED_ACTIVITY_NODE, semanticOwner, semanticOwner, MappingTypes.STRUCTURED_ACTIVITY_NODE_NODE_TYPE, MappingTypes.STRUCTURED_ACTIVITY_NODE_AD_COMPARTMENTS_TYPE,
					this.expectedMappingType);
		}
	}

	/**
	 * Drop the element to a container.
	 *
	 * @param elementNameToDrop
	 *            the semantic name of the element to drop.
	 * @param semanticOwner
	 *            the semantic parent containing the element to drop.
	 * @param semanticTargetOfGraphicalOwner
	 *            the semantic target of the graphical element that will graphically own the dropped element (can be different from the semantic parent).
	 * @param containerMappingType
	 *            the mapping of the graphical container where the element is dropped.
	 * @param compartmentMappingType
	 *            the type of the compartment in which we want to drop the element.
	 * @param dropToolId
	 *            the drop tool to test.
	 * @param expectedMapping
	 *            the expected mapping once the element is dropped.
	 */
	private void dropElementToContainer(String elementNameToDrop, NamedElement semanticOwner, NamedElement semanticTargetOfGraphicalOwner, String containerMappingType, String compartmentMappingType, String expectedMapping) {
		final Element elementToDrop;
		if (COMMENT.equals(elementNameToDrop)) {
			elementToDrop = semanticOwner.getOwnedComments().get(0);
		} else {
			elementToDrop = this.getElementByName(semanticOwner, elementNameToDrop).get();
		}
		this.dropElementToSubContainers(semanticTargetOfGraphicalOwner, elementToDrop, containerMappingType, compartmentMappingType, this.dropToolId, expectedMapping);
	}

	@Override
	protected Element getRootElement() {
		return this.rootActivity;
	}

	@Override
	protected boolean isBorderNode(String elementToDropMappingType) {
		return MappingTypes.isBorderNode(elementToDropMappingType);
	}

	@Parameters(name = "{index} drop semantic element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY_PARTITION },
				{ ACCEPT_CALL_ACTION },
				{ ACCEPT_EVENT_ACTION },
				{ ACTIVITY },
				{ ACTIVITY_FINAL_NODE },
				{ ACTIVITY_PARAMETER_NODE },
				{ ACTIVITY_PARTITION },
				{ ADD_STRUCTURAL_FEATURE_VALUE_ACTION },
				{ BROADCAST_SIGNAL_ACTION },
				{ CALL_BEHAVIOR_ACTION },
				{ CALL_OPERATION_ACTION },
				{ CLEAR_ASSOCIATION_ACTION },
				{ CLEAR_STRUCTURAL_FEATURE_ACTION },
				{ CONDITIONAL_NODE },
				{ CONSTRAINT },
				{ CREATE_OBJECT_ACTION },
				{ DECISION_NODE },
				{ DESTROY_OBJECT_ACTION },
				{ EXPANSION_NODE },
				{ EXPANSION_REGION },
				{ FLOW_FINAL_NODE },
				{ FORK_NODE },
				{ JOIN_NODE },
				{ LOOP_NODE },
				{ INTERRUPTIBLE_ACTIVITY_REGION },
				{ INITIAL_NODE },
				{ MERGE_NODE },
				{ OPAQUE_ACTION },
				{ READ_EXTENT_ACTION },
				{ READ_IS_CLASSIFIED_OBJECT_ACTION },
				{ READ_SELF_ACTION },
				{ READ_STRUCTURAL_FEATURE_ACTION },
				{ RECLASSIFY_OBJECT_ACTION },
				{ REDUCE_ACTION },
				{ SEND_OBJECT_ACTION },
				{ SEND_SIGNAL_ACTION },
				{ SEQUENCE_NODE },
				{ START_CLASSIFIER_BEHAVIOR_ACTION },
				{ START_OBJECT_BEHAVIOR_ACTION },
				{ STRUCTURED_ACTIVITY_NODE },
				{ TEST_IDENTITY_ACTION },
				{ VALUE_SPECIFICATION_ACTION },
		});
	}

	@After
	public void tearDown() {
		this.rootActivity = null;
	}
}
