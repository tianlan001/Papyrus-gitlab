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
package org.eclipse.papyrus.sirius.uml.diagram.communication.tests.reconnection;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractReconnectTargetEdgeTests;
import org.eclipse.papyrus.sirius.uml.diagram.communication.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.communication.tests.ReconnectionToolsIds;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.uml2.uml.Interaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the target reconnection of edges in Communication diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/reconnection/ReconnectionTest.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class EdgeReconnectionTargetTest extends AbstractReconnectTargetEdgeTests {

	private static final String INTERACTION1 = "Interaction1"; //$NON-NLS-1$

	private static final String SOURCE_LIFELINE = "Lifeline1"; //$NON-NLS-1$

	private static final String TARGET_LIFELINE = "Lifeline2"; //$NON-NLS-1$

	private static final String NEW_TARGET_LIFELINE = "Lifeline3"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "ReconnectionCommunicationDiagramSirius"; //$NON-NLS-1$

	private final String newTargetName;

	private final String mappingNewTargetTypeName;

	/**
	 * Default constructor.
	 *
	 */
	public EdgeReconnectionTargetTest(String newTargetName, String mappingNewTargetTypeName) {
		this.newTargetName = newTargetName;
		this.mappingNewTargetTypeName = mappingNewTargetTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetMessageTest() {
		initializeGraphicalAndSemanticContext(SOURCE_LIFELINE, TARGET_LIFELINE, MappingTypes.LIFELINE_NODE_TYPE, MappingTypes.LIFELINE_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.MESSAGE_EDGE_TYPE);
		// test target reconnection
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__MESSAGE__TOOL, edgeToReconnect);
	}

	private void initializeGraphicalAndSemanticContext(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		Interaction interactionSemanticContainer = (Interaction) this.root.getMember(INTERACTION1);
		DDiagramElement interactionGraphicalContainer = getInteractionGraphicalContainer();
		// initialize semantic context for test
		this.setSemanticSource(interactionSemanticContainer.getMember(sourceName));
		this.setSemanticTarget(interactionSemanticContainer.getMember(targetName));
		this.setSemanticNewTarget(interactionSemanticContainer.getMember(this.newTargetName));
		// initialize graphical context for test
		this.setEdgeSource(getNodeFromContainer(sourceName, mappingSourceTypeName, interactionGraphicalContainer));
		this.setEdgeTarget(getNodeFromContainer(targetName, mappingTargetTypeName, interactionGraphicalContainer));
		this.setEdgeNewTarget(getNodeFromContainer(this.newTargetName, this.mappingNewTargetTypeName, interactionGraphicalContainer));
	}

	/**
	 * Get the {@link Interaction} graphical container which is the root of the diagram.
	 * 
	 * @return the {@link Interaction} graphical container.
	 */
	private DDiagramElement getInteractionGraphicalContainer() {
		DNodeContainer container = (DNodeContainer) getNodeFromDiagram(INTERACTION1, MappingTypes.INTERACTION_NODE_TYPE);
		final DDiagramElement compartment = container.getOwnedDiagramElements().get(0);
		return compartment;
	}

	@Parameters(name = "{index} reconnect target edge on {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ NEW_TARGET_LIFELINE, MappingTypes.LIFELINE_NODE_TYPE }
		});
	}

}
