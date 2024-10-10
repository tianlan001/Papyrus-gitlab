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
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests.reconnection;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.ReconnectionToolsIds;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.uml2.uml.Activity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the target reconnection of edges in Activity diagram.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/reconnection/Edge_ReconnectionTest.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class EdgeReconnectionTargetControlFlowTest extends AbstractEdgeReconnectionTargetActivityEdgeTest {

	private static final String DIAGRAM_NAME = "ReconnectionActivityDiagramSirius"; //$NON-NLS-1$

	private static final String ACCEPT_CALL_ACTION = "AcceptCallAction"; //$NON-NLS-1$

	private static final String ACCEPT_EVENT_ACTION = "AcceptEventAction"; //$NON-NLS-1$

	private static final String ACTIVITY_FINAL_NODE = "ActivityFinalNode"; //$NON-NLS-1$

	private static final String ADD_STRUCTURAL_FEATURE_VALUE_ACTION = "AddStructuralFeatureValueAction"; //$NON-NLS-1$

	private static final String BROADCAST_SIGNAL_ACTION = "BroadcastSignalAction"; //$NON-NLS-1$

	private static final String CALL_OPERATION_ACTION = "CallOperationAction"; //$NON-NLS-1$

	private static final String CLEAR_ASSOCIATION_ACTION = "ClearAssociationAction"; //$NON-NLS-1$

	private static final String CLEAR_STRUCTURAL_FEATURE_ACTION = "ClearStructuralFeatureAction"; //$NON-NLS-1$

	private static final String CONDITIONAL_NODE = "ConditionalNode"; //$NON-NLS-1$

	private static final String CREATE_OBJECT_ACTION = "CreateObjectAction"; //$NON-NLS-1$

	private static final String DECISION_NODE = "DecisionNode"; //$NON-NLS-1$

	private static final String DESTROY_OBJECT_ACTION = "DestroyObjectAction"; //$NON-NLS-1$

	private static final String FLOW_FINAL_NODE = "FlowFinalNode"; //$NON-NLS-1$

	private static final String FORK_NODE = "ForkNode"; //$NON-NLS-1$

	private static final String INPUT_PIN = "InputPin"; //$NON-NLS-1$

	private static final String JOIN_NODE = "JoinNode"; //$NON-NLS-1$

	private static final String MERGE_NODE = "MergeNode"; //$NON-NLS-1$

	private static final String OPAQUE_ACTION = "OpaqueAction"; //$NON-NLS-1$

	private static final String OUTPUT_PIN = "OutputPin"; //$NON-NLS-1$

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

	/**
	 * Default constructor.
	 *
	 */
	public EdgeReconnectionTargetControlFlowTest(String newTargetName) {
		super(newTargetName);
	}

	@Override
	public void setUp() {
		super.setUp();
		this.rootActivity = (Activity) this.root.getMember(ROOT_ACTIVITY);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetControlFlowTest() {
		initializeGraphicalAndSemanticContext(TEST_IDENTITY_ACTION, VALUE_SPECIFICATION_ACTION, MappingTypes.TEST_IDENTITY_ACTION_NODE_TYPE, MappingTypes.VALUE_SPECIFICATION_ACTION_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.CONTROL_FLOW_EDGE_TYPE);
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__CONTROL_FLOW__TOOL, edgeToReconnect);
	}

	@Parameters(name = "{index} reconnect target ControlFlow on {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY_FINAL_NODE },
				{ FLOW_FINAL_NODE },
				{ DECISION_NODE },
				{ MERGE_NODE },
				{ FORK_NODE },
				{ JOIN_NODE },
				{ EXPANSION_REGION },
				{ OPAQUE_ACTION },
				{ INPUT_PIN },
				{ OUTPUT_PIN },
				{ CALL_BEHAVIOR_ACTION },
				{ ADD_STRUCTURAL_FEATURE_VALUE_ACTION },
				{ ACCEPT_CALL_ACTION },
				{ ACCEPT_EVENT_ACTION },
				{ BROADCAST_SIGNAL_ACTION },
				{ CALL_OPERATION_ACTION },
				{ CLEAR_ASSOCIATION_ACTION },
				{ CLEAR_STRUCTURAL_FEATURE_ACTION },
				{ CONDITIONAL_NODE },
				{ CREATE_OBJECT_ACTION },
				{ DESTROY_OBJECT_ACTION },
				{ READ_EXTENT_ACTION },
				{ READ_IS_CLASSIFIED_OBJECT_ACTION },
				{ READ_STRUCTURAL_FEATURE_ACTION },
				{ READ_SELF_ACTION },
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
