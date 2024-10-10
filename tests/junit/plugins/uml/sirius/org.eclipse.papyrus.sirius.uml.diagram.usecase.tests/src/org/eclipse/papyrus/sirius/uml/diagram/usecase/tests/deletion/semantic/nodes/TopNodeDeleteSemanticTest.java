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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.deletion.semantic.nodes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.MappingTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Delete nodes from model and from the root of UseCase diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/deletion/nodes/Node_DeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class TopNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {
	private static final String ACTIVITY = "ActivityToDelete"; //$NON-NLS-1$

	private static final String ACTOR = "ActorToDelete"; //$NON-NLS-1$

	private static final String CLASS = "ClassToDelete"; //$NON-NLS-1$

	private static final String COMPONENT = "ComponentToDelete"; //$NON-NLS-1$

	private static final String CONSTRAINT = "ConstraintToDelete"; //$NON-NLS-1$

	private static final String INTERACTION = "InteractionToDelete"; //$NON-NLS-1$

	private static final String PACKAGE = "PackageToDelete"; //$NON-NLS-1$

	private static final String STATEMACHINE = "StateMachineToDelete"; //$NON-NLS-1$

	private static final String USECASE = "UseCaseToDelete"; //$NON-NLS-1$


	private static final Map<String, String> elementTypeToMapping = Map.of(ACTIVITY, MappingTypes.ACTIVITY_NODE_TYPE, ACTOR, MappingTypes.ACTOR_NODE_TYPE, CLASS, MappingTypes.CLASS_NODE_TYPE, COMPONENT, MappingTypes.COMPONENT_NODE_TYPE,
			CONSTRAINT, MappingTypes.CONSTRAINT_NODE_TYPE, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, STATEMACHINE,
			MappingTypes.STATE_MACHINE_NODE_TYPE, USECASE, MappingTypes.USE_CASE_NODE_TYPE);

	private static final String DIAGRAM_NAME = "Node_Delete_UseCaseDiagramSirius"; //$NON-NLS-1$

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
