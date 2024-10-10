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
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests.delete.semantic;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
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
 * Check the delete tool on ActivityDiagram. We do not test each element in each container but only in Activity root, LoopNode
 * (same graphical and semantic container) and ActivityPartition (semantic container different from the graphical one)
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/deletion/nodes/nodeDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class NodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {


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

	private static final String ROOT_ACTIVITY = "RootActivity"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "NodeDeleteActivityDiagramSirius"; //$NON-NLS-1$

	private Activity rootActivity;

	private final String subNodeName;

	private final String subNodeMappingType;


	/**
	 * Constructor.
	 *
	 */
	public NodeDeleteSemanticTest(String subNodeName) {
		this.subNodeName = subNodeName;
		if (ACTIVITY.equals(subNodeName)) {
			this.subNodeMappingType = MappingTypes.SUB_ACTIVITY_NODE_TYPE;
		} else {
			this.subNodeMappingType = AD + subNodeName;
		}
	}

	@Before
	public void setUp() {
		this.rootActivity = (Activity) this.fixture.getModel().getMember(ROOT_ACTIVITY);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteElementFromRootActivity() {
		DDiagramElement compartment = getContainer(ROOT_ACTIVITY, MappingTypes.ACTIVITY_NODE_TYPE);
		deleteNode(this.subNodeName + FROM + ROOT_ACTIVITY, this.subNodeMappingType, compartment, false);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteElementFromActivityPartition() {
		List<String> forbiddenElements = List.of(ACTIVITY, ACTIVITY_PARAMETER_NODE, CONSTRAINT, INTERRUPTIBLE_ACTIVITY_REGION);
		if (!forbiddenElements.contains(this.subNodeName)) {
			DDiagramElement compartment = getContainer(ACTIVITY_PARTITION, MappingTypes.ACTIVITY_PARTITION_NODE_TYPE);
			NamedElement semanticContainer = null;
			if (!ACTIVITY_PARTITION.equals(this.subNodeName)) {
				semanticContainer = (NamedElement) getRootElement();
			}
			deleteNode(this.subNodeName + FROM + ACTIVITY_PARTITION, this.subNodeMappingType, compartment, semanticContainer, false);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteElementFromLoopNode() {
		List<String> forbiddenElements = List.of(ACTIVITY_PARAMETER_NODE, ACTIVITY, INTERRUPTIBLE_ACTIVITY_REGION, ACTIVITY_PARTITION);
		if (!forbiddenElements.contains(this.subNodeName)) {
			DDiagramElement compartment = getContainer(LOOP_NODE, MappingTypes.LOOP_NODE_NODE_TYPE);
			deleteNode(this.subNodeName + FROM + LOOP_NODE, this.subNodeMappingType, compartment, false);
		}
	}

	@Override
	protected Element getRootElement() {
		return this.rootActivity;
	}

	private DDiagramElement getContainer(String containerName, String mappingType) {
		DNodeContainer container = (DNodeContainer) getElementsFromDiagramBySemanticName(containerName, mappingType).get(0);
		final DDiagramElement compartment;
		if (MappingTypes.isBorderNode(this.subNodeMappingType)) {
			compartment = container;
		} else {
			compartment = container.getOwnedDiagramElements().get(0);
		}
		return compartment;
	}

	@After
	public void tearDown() {
		this.rootActivity = null;
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY_PARTITION },
				{ ACCEPT_CALL_ACTION },
				{ ACCEPT_EVENT_ACTION },
				{ ACTIVITY },
				{ ACTIVITY_FINAL_NODE },
				{ ACTIVITY_PARAMETER_NODE },
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
