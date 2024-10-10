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
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests.drop.graphical;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractGraphicalDropNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.MappingTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the graphical drop from and within the diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
@RunWith(value = Parameterized.class)
public class GraphicalDropTest extends AbstractGraphicalDropNodeTests {

	private static final String DIAGRAM_NAME = "GraphicalDropActivityDiagramSirius"; //$NON-NLS-1$

	private static final String ACCEPT_CALL_ACTION = "AcceptCallAction"; //$NON-NLS-1$

	private static final String ACCEPT_EVENT_ACTION = "AcceptEventAction"; //$NON-NLS-1$

	private static final String ACTIVITY = "Activity"; //$NON-NLS-1$

	private static final String ACTIVITY_FINAL_NODE = "ActivityFinalNode"; //$NON-NLS-1$

	private static final String ACTIVITY_PARTITION = "ActivityPartition"; //$NON-NLS-1$

	private static final String ADD_STRUCTURAL_FEATURE_VALUE_ACTION = "AddStructuralFeatureValueAction"; //$NON-NLS-1$

	private static final String BROADCAST_SIGNAL_ACTION = "BroadcastSignalAction"; //$NON-NLS-1$

	private static final String CALL_BEHAVIOR_ACTION = "CallBehaviorAction"; //$NON-NLS-1$

	private static final String CALL_OPERATION_ACTION = "CallOperationAction"; //$NON-NLS-1$

	private static final String CLEAR_ASSOCIATION_ACTION = "ClearAssociationAction"; //$NON-NLS-1$

	private static final String CLEAR_STRUCTURAL_FEATURE_ACTION = "ClearStructuralFeatureAction"; //$NON-NLS-1$

	private static final String CONDITIONAL_NODE = "ConditionalNode"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String CREATE_OBJECT_ACTION = "CreateObjectAction"; //$NON-NLS-1$

	private static final String DECISION_NODE = "DecisionNode"; //$NON-NLS-1$

	private static final String DESTROY_OBJECT_ACTION = "DestroyObjectAction"; //$NON-NLS-1$

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

	private static final String GRAPHICAL = "Graphical"; //$NON-NLS-1$

	private static final String ROOT_ACTIVITY = "RootActivity"; //$NON-NLS-1$

	private static final String SUB_ACTIVITY = "SubActivity"; //$NON-NLS-1$

	private Activity rootActivity;

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;


	/**
	 * Default constructor.
	 *
	 */
	public GraphicalDropTest(String elementToDropName) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = GRAPHICAL + elementToDropName + DROP;
		if (elementToDropName.equals(ACTIVITY)) {
			this.nodeMappingType = MappingTypes.SUB_ACTIVITY_NODE_TYPE;
		} else {
			this.nodeMappingType = AD + elementToDropName;
		}
	}

	@Before
	public void setUp() {
		this.rootActivity = (Activity) this.fixture.getModel().getMember(ROOT_ACTIVITY);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropElementIntoActivityPartition() {
		List<String> forbiddenDrop = List.of(ACTIVITY, CONSTRAINT, INTERRUPTIBLE_ACTIVITY_REGION);
		String expectedSemanticContainer = ROOT_ACTIVITY;
		String newContainmentFeatureName = null;
		if (this.elementToDropName.equals(ACTIVITY_PARTITION)) {
			expectedSemanticContainer = ACTIVITY_PARTITION;
			newContainmentFeatureName = UMLPackage.eINSTANCE.getActivityPartition_Subpartition().getName();
		}
		if (!forbiddenDrop.contains(this.elementToDropName)) {
			dropElementIntoContainer(this.elementToDropName + FROM + SUB_ACTIVITY, ACTIVITY_PARTITION, MappingTypes.ACTIVITY_PARTITION_NODE_TYPE, MappingTypes.ACTIVITY_PARTITION_AD_COMPARTMENTS_TYPE, this.dropToolId,
					this.nodeMappingType, expectedSemanticContainer, newContainmentFeatureName);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropElementIntoSubActivity() {
		dropElementIntoContainer(this.elementToDropName + FROM + SUB_ACTIVITY, ACTIVITY + FROM + ROOT_ACTIVITY, MappingTypes.SUB_ACTIVITY_NODE_TYPE, MappingTypes.SUB_ACTIVITY_AD_COMPARTMENTS_TYPE, this.dropToolId,
				this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropElementIntoRootActivity() {
		dropElementIntoContainer(this.elementToDropName + FROM + SUB_ACTIVITY, ROOT_ACTIVITY, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_AD_COMPARTMENTS_TYPE, this.dropToolId,
				this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropElementIntoConditionalNode() {
		List<String> forbiddenDrop = List.of(ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION);
		if (!forbiddenDrop.contains(this.elementToDropName)) {
			String containmentFeatureName = getStructuredActivityNodeContainmentFeatureName();
			dropElementIntoContainer(this.elementToDropName + FROM + SUB_ACTIVITY, CONDITIONAL_NODE, MappingTypes.CONDITIONAL_NODE_NODE_TYPE, MappingTypes.CONDITIONAL_NODE_AD_COMPARTMENTS_TYPE, this.dropToolId,
					this.nodeMappingType, CONDITIONAL_NODE, containmentFeatureName);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropElementIntoExpansionRegion() {
		List<String> forbiddenDrop = List.of(ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION);
		if (!forbiddenDrop.contains(this.elementToDropName)) {
			String containmentFeatureName = getStructuredActivityNodeContainmentFeatureName();
			dropElementIntoContainer(this.elementToDropName + FROM + SUB_ACTIVITY, EXPANSION_REGION, MappingTypes.EXPANSION_REGION_NODE_TYPE, MappingTypes.EXPANSION_REGION_AD_COMPARTMENTS_TYPE, this.dropToolId,
					this.nodeMappingType, EXPANSION_REGION, containmentFeatureName);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropElementIntoInterruptibleActivityRegion() {
		List<String> forbiddenDrop = List.of(ACTIVITY, CONSTRAINT, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION);
		if (!forbiddenDrop.contains(this.elementToDropName)) {
			dropElementIntoContainer(this.elementToDropName + FROM + SUB_ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, MappingTypes.INTERRUPTIBLE_ACTIVITY_REGION_NODE_TYPE, MappingTypes.INTERRUPTIBLE_ACTIVITY_REGION_AD_COMPARTMENTS_TYPE, this.dropToolId,
					this.nodeMappingType, ROOT_ACTIVITY, null);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropElementIntoLoopNode() {
		List<String> forbiddenDrop = List.of(ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION);
		if (!forbiddenDrop.contains(this.elementToDropName)) {
			String containmentFeatureName = getStructuredActivityNodeContainmentFeatureName();
			dropElementIntoContainer(this.elementToDropName + FROM + SUB_ACTIVITY, LOOP_NODE, MappingTypes.LOOP_NODE_NODE_TYPE, MappingTypes.LOOP_NODE_AD_COMPARTMENTS_TYPE, this.dropToolId,
					this.nodeMappingType, LOOP_NODE, containmentFeatureName);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropElementIntoSequenceNode() {
		List<String> forbiddenDrop = List.of(ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION, INITIAL_NODE, ACTIVITY_FINAL_NODE, FLOW_FINAL_NODE, DECISION_NODE, MERGE_NODE, JOIN_NODE,
				FORK_NODE);
		if (!forbiddenDrop.contains(this.elementToDropName)) {
			String containmentFeatureName = getStructuredActivityNodeContainmentFeatureName();
			dropElementIntoContainer(this.elementToDropName + FROM + SUB_ACTIVITY, SEQUENCE_NODE, MappingTypes.SEQUENCE_NODE_NODE_TYPE, MappingTypes.SEQUENCE_NODE_AD_COMPARTMENTS_TYPE, this.dropToolId,
					this.nodeMappingType, SEQUENCE_NODE, containmentFeatureName);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropElementIntoStructuredActivityNode() {
		List<String> forbiddenDrop = List.of(ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION);
		if (!forbiddenDrop.contains(this.elementToDropName)) {
			String containmentFeatureName = getStructuredActivityNodeContainmentFeatureName();
			dropElementIntoContainer(this.elementToDropName + FROM + SUB_ACTIVITY, STRUCTURED_ACTIVITY_NODE, MappingTypes.STRUCTURED_ACTIVITY_NODE_NODE_TYPE, MappingTypes.STRUCTURED_ACTIVITY_NODE_AD_COMPARTMENTS_TYPE, this.dropToolId,
					this.nodeMappingType, STRUCTURED_ACTIVITY_NODE, containmentFeatureName);
		}
	}

	@Override
	protected Element getRootElement() {
		return this.rootActivity;
	}

	@Override
	protected boolean isBorderNode(String elementToDropMappingType) {
		return MappingTypes.isBorderNode(elementToDropMappingType);
	}

	private String getStructuredActivityNodeContainmentFeatureName() {
		String containmentFeatureName = null;
		if (!CONSTRAINT.equals(this.elementToDropName)) {
			containmentFeatureName = UMLPackage.eINSTANCE.getStructuredActivityNode_Node().getName();
		}
		return containmentFeatureName;
	}

	@After
	public void tearDown() {
		this.rootActivity = null;
	}

	@Parameters(name = "{index} drop the graphical element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY_PARTITION },
				{ ACCEPT_CALL_ACTION },
				{ ACCEPT_EVENT_ACTION },
				{ ACTIVITY },
				{ ACTIVITY_FINAL_NODE },
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
}
