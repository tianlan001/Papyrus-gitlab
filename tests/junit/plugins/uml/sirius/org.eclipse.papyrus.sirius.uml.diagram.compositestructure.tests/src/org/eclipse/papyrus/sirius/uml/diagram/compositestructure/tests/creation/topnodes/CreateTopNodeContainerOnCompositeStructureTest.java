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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.creation.topnodes;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateTopNodeOnDiagramTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.MappingTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.Collaboration;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.FunctionBehavior;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.OpaqueBehavior;
import org.eclipse.uml2.uml.ProtocolStateMachine;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class groups all Tests about allowed creations on the root of the Diagram represented by a {@link Model}.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/creation/topNodes/TopNode_CreationTest.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class CreateTopNodeContainerOnCompositeStructureTest extends AbstractCreateTopNodeOnDiagramTests {

	/**
	 * The name of the diagram to open
	 */
	protected static final String DIAGRAM_NAME = "TopNode_CompositeStructureDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private final String nodeCompartmentType;

	private final EReference containmentFeature;

	private final Class<? extends Element> expectedType;


	/**
	 * Default Constructor.
	 *
	 */
	public CreateTopNodeContainerOnCompositeStructureTest(String creationToolId, String nodeMappingType, String nodeCompartmentType, EReference containmentFeature, Class<? extends Element> expectedType) {
		this.creationToolId = creationToolId;
		this.nodeMappingType = nodeMappingType;
		this.nodeCompartmentType = nodeCompartmentType;
		this.containmentFeature = containmentFeature;
		this.expectedType = expectedType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createTopNodeContainerTest() {
		final Diagram diagram = getDiagram();
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(getSemanticOwner(), this.containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeContainerCreationChecker(diagram, getTopGraphicalContainer(), nodeMappingType, nodeCompartmentType);
		createNode(creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker));
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CreationToolsIds.CREATE__ACTIVITY__TOOL, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_NODE_CSD_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Activity.class },
				{ CreationToolsIds.CREATE__CLASS__TOOL, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_CSD_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), org.eclipse.uml2.uml.Class.class },
				{ CreationToolsIds.CREATE__COLABORATION__TOOL, MappingTypes.COLLABORATION_NODE_TYPE, MappingTypes.COLLABORATION_NODE_CSD_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Collaboration.class },
				{ CreationToolsIds.CREATE__FUNCTION_BEHAVIOR__TOOL, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE, MappingTypes.FUNCTION_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), FunctionBehavior.class },
				{ CreationToolsIds.CREATE__OPAQUE_BEHAVIOR__TOOL, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE, MappingTypes.OPAQUE_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), OpaqueBehavior.class },
				{ CreationToolsIds.CREATE__STATE_MACHINE__TOOL, MappingTypes.STATE_MACHINE_NODE_TYPE, MappingTypes.STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), StateMachine.class },
				{ CreationToolsIds.CREATE__PROTOCOL_STATE_MACHINE__TOOL, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(),
						ProtocolStateMachine.class },
				{ CreationToolsIds.CREATE__INTERACTION__TOOL, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_CSD_COMPARTMENTS_TYPE, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Interaction.class }
		});
	}

}
