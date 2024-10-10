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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.creation.subnodes;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to sub-node container creation in Classifiers for the Deployment diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/creation/subNodes/subNodes.di")
@RunWith(value = Parameterized.class)
public class SubNodeContainersCreationIntoClassifierContainerTest extends AbstractCreateNodeTests {

	private static final String ARTIFACT1 = "Artifact1"; //$NON-NLS-1$

	private static final String DEVICE1 = "Device1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SubNodes_DeploymentDiagramSirius"; //$NON-NLS-1$

	private static final String EXECUTION_ENVIRONMENT1 = "ExecutionEnvironment1"; //$NON-NLS-1$

	private static final String NODE1 = "Node1"; //$NON-NLS-1$

	private EReference containmentFeature;

	private final String creationToolId;

	private final Class<? extends Element> expectedType;

	private final String nodeCompartmentType;

	private final String nodeMappingType;

	private String semanticOwnerName;


	public SubNodeContainersCreationIntoClassifierContainerTest(EReference containmentFeature, Class<? extends Element> expectedType) {
		this.containmentFeature = containmentFeature;
		this.creationToolId = CreationToolsIds.getToolId(expectedType.getSimpleName());
		this.nodeMappingType = MappingTypes.getMappingType(expectedType.getSimpleName());
		this.nodeCompartmentType = MappingTypes.getCompartmentMappingType(expectedType.getSimpleName());
		this.expectedType = expectedType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeContainerIntoArtifact() {
		String artifactName = Artifact.class.getSimpleName();
		if ((Artifact.class == this.expectedType)) {
			this.createNodeContainer(ARTIFACT1, MappingTypes.getCompartmentMappingType(artifactName), UMLPackage.eINSTANCE.getArtifact_NestedArtifact());
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeContainerIntoDevice() {
		String deviceName = Device.class.getSimpleName();
		if (!(Artifact.class == this.expectedType)) {
			this.createNodeContainer(DEVICE1, MappingTypes.getCompartmentMappingType(deviceName), this.containmentFeature);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeContainerIntoExecutionEnvironment() {
		if (!(Device.class == this.expectedType) && !(Node.class == this.expectedType)) {
			String execEnvName = ExecutionEnvironment.class.getSimpleName();
			this.createNodeContainer(EXECUTION_ENVIRONMENT1, MappingTypes.getCompartmentMappingType(execEnvName), this.containmentFeature);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createNodeContainerIntoNode() {
		String nodeName = Node.class.getSimpleName();
		this.createNodeContainer(NODE1, MappingTypes.getCompartmentMappingType(nodeName), this.containmentFeature);
	}

	@Override
	protected Element getSemanticOwner() {
		return this.root.getMember(this.semanticOwnerName);
	}

	@Override
	protected EObject getTopGraphicalContainer() {
		final DDiagram ddiagram = this.getDDiagram();
		Element semanticOwner = this.getSemanticOwner();
		// @formatter:off
		Optional<DSemanticDecorator> optionalDSemanticDecorator = ddiagram.getOwnedDiagramElements().stream()
				.filter(DSemanticDecorator.class::isInstance)
				.map(DSemanticDecorator.class::cast)
				.filter(semanticDecorator -> semanticDecorator.getTarget().equals(semanticOwner))
				.findFirst();
		// @formatter:on
		Assert.assertTrue(optionalDSemanticDecorator.isPresent());
		final DSemanticDecorator element = optionalDSemanticDecorator.get();
		Assert.assertEquals(semanticOwner, element.getTarget());
		return element;
	}

	private void createNodeContainer(final String semanticOwnerName, final String containerMappingTypeName, final EReference containmentFeature) {
		this.semanticOwnerName = semanticOwnerName;
		final EObject graphicalContainer = this.getSubNodeOfGraphicalContainer(containerMappingTypeName);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(this.getSemanticOwner(), containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeContainerCreationChecker(this.getDiagram(), graphicalContainer, this.nodeMappingType, this.nodeCompartmentType);
		this.createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	@Parameters(name = "{index} Test {1} tool")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ UMLPackage.eINSTANCE.getClass_NestedClassifier(), Artifact.class },
				{ UMLPackage.eINSTANCE.getNode_NestedNode(), Device.class },
				{ UMLPackage.eINSTANCE.getNode_NestedNode(), ExecutionEnvironment.class },
				{ UMLPackage.eINSTANCE.getNode_NestedNode(), Node.class },
		});
	}
}
