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
package org.eclipse.papyrus.sirius.uml.diagram.communication.tests.creation.edges;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateEdgeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DEdgeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticEdgeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.communication.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.communication.tests.MappingTypes;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the creation of Communication Diagram edges.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/creation/edges/Edge_CreationTest.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class EdgeCreationTest extends AbstractCreateEdgeTests {

	private static final String INTERACTION1 = "Interaction1"; //$NON-NLS-1$

	private static final String LIFELINE = "Lifeline"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "EdgeCommunicationDiagramSirius"; //$NON-NLS-1$

	private static final Map<String, String> elementTypeToMapping = Map.of(LIFELINE, MappingTypes.LIFELINE_NODE_TYPE);

	private final String sourceName;

	private final String targetName;

	private final String mappingSourceTypeName;

	private final String mappingTargetTypeName;


	/**
	 * Default constructor.
	 *
	 */
	public EdgeCreationTest(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		this.sourceName = sourceName;
		this.targetName = targetName;
		this.mappingSourceTypeName = mappingSourceTypeName;
		this.mappingTargetTypeName = mappingTargetTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createMessageTest() {
		testEdgeCreation(CreationToolsIds.CREATE__MESSAGE__TOOL, UMLPackage.eINSTANCE.getInteraction_Message(), Message.class,
				(Interaction) this.root.getMember(INTERACTION1), MappingTypes.MESSAGE_EDGE_TYPE);
	}

	private void testEdgeCreation(String toolId, EReference containmentFeature, Class<? extends Element> expectedType, EObject expectedOwner, String expectedEdgeMappingType) {
		Interaction interactionSemanticContainer = (Interaction) this.root.getMember(INTERACTION1);
		// initialize semantic context for test
		this.setSemanticSource(interactionSemanticContainer.getMember(sourceName));
		this.setSemanticTarget(interactionSemanticContainer.getMember(targetName));
		// initialize graphical context for test
		this.setEdgeSource(getElementsFromDiagram(sourceName, mappingSourceTypeName).get(0));
		this.setEdgeTarget(getElementsFromDiagram(targetName, mappingTargetTypeName).get(0));
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticEdgeCreationChecker(expectedOwner, containmentFeature, expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalEdgeCreationChecker = new DEdgeCreationChecker(getDiagram(), getDDiagram(), expectedEdgeMappingType);
		createEdge(toolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalEdgeCreationChecker));
	}

	@Parameters(name = "{index} create edge between {0} and {1}")
	public static Collection<Object[]> data() {
		Collection<Object[]> data = new ArrayList<>();
		for (String sourceName : elementTypeToMapping.keySet()) {
			for (String targetName : elementTypeToMapping.keySet()) {
				Object[] array = { sourceName + "1", targetName + "2", elementTypeToMapping.get(sourceName), elementTypeToMapping.get(targetName) }; //$NON-NLS-1$//$NON-NLS-2$
				data.add(array);
			}
		}
		return data;
	}

}
