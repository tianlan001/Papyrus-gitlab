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
package org.eclipse.papyrus.sirius.uml.diagram.communication.tests.delete.semantic.nodes;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.communication.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Delete sub-nodes from model and from container of Communication diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/deletion/nodes/NodeDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class SubNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$

	private static final String DURATION_OBSERVATION = "DurationObservation1"; //$NON-NLS-1$

	private static final String LIFELINE = "Lifeline1"; //$NON-NLS-1$

	private static final String TIME_OBSERVATION = "TimeObservation1"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "NodeDeleteCommunicationDiagramSirius"; //$NON-NLS-1$

	private final String subNodeName;

	private final String subNodeMappingType;

	private final String containerName;

	private final String containerMappingTypeName;

	/**
	 * Constructor.
	 *
	 */
	public SubNodeDeleteSemanticTest(String subNodeName, String subNodeMappingType, String containerName, String containerMappingTypeName) {
		this.subNodeName = subNodeName;
		this.subNodeMappingType = subNodeMappingType;
		this.containerName = containerName;
		this.containerMappingTypeName = containerMappingTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteSubDiagramElementTest() {
		DDiagramElement compartment = getContainer();
		if (this.subNodeName.equals(DURATION_OBSERVATION) || this.subNodeName.equals(TIME_OBSERVATION)) {
			// Observations are stored at the Model root, not in Interaction
			deleteNode(this.subNodeName, this.subNodeMappingType, compartment, getModel(), false);
		} else {
			deleteNode(this.subNodeName, this.subNodeMappingType, compartment, false);
		}
	}

	private DDiagramElement getContainer() {
		DNodeContainer container = (DNodeContainer) getNodeFromDiagram(this.containerName, this.containerMappingTypeName);
		final DDiagramElement compartment = container.getOwnedDiagramElements().get(0);
		return compartment;
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ LIFELINE, MappingTypes.LIFELINE_NODE_TYPE, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE },
				{ DURATION_OBSERVATION, MappingTypes.DURATION_OBSERVATION_NODE_TYPE, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE },
				{ TIME_OBSERVATION, MappingTypes.TIME_OBSERVATION_NODE_TYPE, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE },
				{ CONSTRAINT, MappingTypes.CONSTRAINT_NODE_TYPE, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE }
		});
	}
}
