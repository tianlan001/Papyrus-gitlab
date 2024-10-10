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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.creation.topnodes;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateTopNodeOnDiagramTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.MappingTypes;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all the tests related to node creation on the root of a Deployment diagram.
 *
 * @author <a href="mailto:florian.barbin@obeosoft.com">Florian Barbin</a>
 */
@PluginResource("resources/creation/topNodes/TopNode_CreationTest.di")
@RunWith(value = Parameterized.class)
public class CreateTopNodeContainerOnDeploymentTest extends AbstractCreateTopNodeOnDiagramTests {

	/**
	 * The name of the diagram to open.
	 */
	protected static final String DIAGRAM_NAME = "TopNode_DeploymentDiagramSirius"; //$NON-NLS-1$

	private final String creationToolId;

	private final String nodeMappingType;

	private final String nodeCompartmentTypes;


	private final Class<? extends Element> expectedType;

	public CreateTopNodeContainerOnDeploymentTest(Class<? extends Element> expectedType) {
		this.creationToolId = CreationToolsIds.getToolId(expectedType.getSimpleName());
		this.nodeMappingType = MappingTypes.getMappingType(expectedType.getSimpleName());
		this.nodeCompartmentTypes = MappingTypes.getCompartmentMappingType(expectedType.getSimpleName());
		this.expectedType = expectedType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createTopNodeContainerTest() {
		final Diagram diagram = this.getDiagram();
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(this.getSemanticOwner(), UMLPackage.eINSTANCE.getPackage_PackagedElement(), this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeContainerCreationChecker(diagram, this.getTopGraphicalContainer(), this.nodeMappingType, this.nodeCompartmentTypes);
		this.createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker));
	}

	@Parameters(name = "{index} Test {0} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{ Artifact.class },
			{ Device.class },
			{ ExecutionEnvironment.class },
			{ Model.class },
			{ Node.class },
			{ Package.class }
		});
	}
}
