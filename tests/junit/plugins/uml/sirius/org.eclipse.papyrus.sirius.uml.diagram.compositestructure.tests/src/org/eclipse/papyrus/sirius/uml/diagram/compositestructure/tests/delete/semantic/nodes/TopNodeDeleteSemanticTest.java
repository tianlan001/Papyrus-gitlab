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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.delete.semantic.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.MappingTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Delete nodes from model and from the root of diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/deletion/nodes/NodeDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class TopNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {
	private static final String ACTIVITY = "Activity1"; //$NON-NLS-1$

	private static final String CLASS = "Class1"; //$NON-NLS-1$

	private static final String COLLABORATION = "Collaboration1"; //$NON-NLS-1$

	private static final String FUNCTIONBEHAVIOR = "FunctionBehavior1"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction1"; //$NON-NLS-1$

	private static final String INFORMATIONITEM = "InformationItem1"; //$NON-NLS-1$

	private static final String OPAQUEBEHAVIOR = "OpaqueBehavior1"; //$NON-NLS-1$

	private static final String STATEMACHINE = "StateMachine1"; //$NON-NLS-1$

	private static final String PROTOCOLSTATEMACHINE = "ProtocolStateMachine1"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$


	private static final Map<String, String> elementTypeToMapping = Map.of(ACTIVITY, MappingTypes.ACTIVITY_NODE_TYPE, CLASS, MappingTypes.CLASS_NODE_TYPE, COLLABORATION, MappingTypes.COLLABORATION_NODE_TYPE, FUNCTIONBEHAVIOR,
			MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE, INFORMATIONITEM, MappingTypes.INFORMATION_ITEM_NODE_TYPE, OPAQUEBEHAVIOR, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE, STATEMACHINE,
			MappingTypes.STATE_MACHINE_NODE_TYPE, PROTOCOLSTATEMACHINE, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE, CONSTRAINT, MappingTypes.CONSTRAINT_NODE_TYPE);

	private static final String DIAGRAM_NAME = "NodeDeleteCompositeStructureDiagramSirius"; //$NON-NLS-1$

	private final String nodeMappingType;

	private final String nodeName;

	/**
	 * Default Constructor.
	 *
	 */
	public TopNodeDeleteSemanticTest(String nodeName, String nodeMappingType) {
		this.nodeName = nodeName;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteTopDiagramElementTest() {
		deleteNode(this.nodeName, this.nodeMappingType, false);
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		Collection<Object[]> data = new ArrayList<>();
		for (String sourceName : elementTypeToMapping.keySet()) {
			Object[] array = { sourceName, elementTypeToMapping.get(sourceName) };
			data.add(array);
		}
		return data;
	}
}
