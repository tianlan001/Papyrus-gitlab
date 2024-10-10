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
public class EdgeReconnectionTargetObjectFlowTest extends AbstractEdgeReconnectionTargetActivityEdgeTest {

	private static final String DIAGRAM_NAME = "ReconnectionActivityDiagramSirius"; //$NON-NLS-1$

	private static final String ACTIVITY_FINAL_NODE = "ActivityFinalNode"; //$NON-NLS-1$

	private static final String ACTIVITY_PARAMETER_NODE = "ActivityParameterNode"; //$NON-NLS-1$

	private static final String DECISION_NODE = "DecisionNode"; //$NON-NLS-1$

	private static final String EXPANSION_NODE = "ExpansionNode"; //$NON-NLS-1$

	private static final String FLOW_FINAL_NODE = "FlowFinalNode"; //$NON-NLS-1$

	private static final String FORK_NODE = "ForkNode"; //$NON-NLS-1$

	private static final String INPUT_PIN = "InputPin"; //$NON-NLS-1$

	private static final String JOIN_NODE = "JoinNode"; //$NON-NLS-1$

	private static final String MERGE_NODE = "MergeNode"; //$NON-NLS-1$

	private static final String OPAQUE_ACTION = "OpaqueAction"; //$NON-NLS-1$

	/**
	 * Default constructor.
	 *
	 */
	public EdgeReconnectionTargetObjectFlowTest(String newTargetName) {
		super(newTargetName);
	}

	@Override
	public void setUp() {
		super.setUp();
		this.rootActivity = (Activity) this.root.getMember(ROOT_ACTIVITY);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetObjectFlowTest() {
		this.initializeGraphicalAndSemanticContext(DECISION_NODE, FORK_NODE, MappingTypes.DECISION_NODE_NODE_TYPE, MappingTypes.FORK_NODE_NODE_TYPE);
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.OBJECT_FLOW_EDGE_TYPE);
		this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__OBJECT_FLOW__TOOL, edgeToReconnect);
	}

	@Parameters(name = "{index} reconnect target ObjectFlow on {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY_PARAMETER_NODE },
				{ ACTIVITY_FINAL_NODE },
				{ DECISION_NODE },
				{ FLOW_FINAL_NODE },
				{ MERGE_NODE },
				{ FORK_NODE },
				{ JOIN_NODE },
				{ EXPANSION_NODE },
				{ INPUT_PIN }
		});
	}
}
