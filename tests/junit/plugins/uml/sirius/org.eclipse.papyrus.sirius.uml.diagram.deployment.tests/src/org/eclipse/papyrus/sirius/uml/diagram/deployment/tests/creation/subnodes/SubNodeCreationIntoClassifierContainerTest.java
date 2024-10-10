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

import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateNodeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticNodeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagram;
import org.eclipse.sirius.viewpoint.DSemanticDecorator;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Assert;
import org.junit.Test;

/**
 * Groups all tests related to sub-node creation into container (except Packages) for the Deployment diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/creation/subNodes/subNodes.di")
public class SubNodeCreationIntoClassifierContainerTest extends AbstractCreateNodeTests {

	private static final String ARTIFACT1 = "Artifact1"; //$NON-NLS-1$

	private static final String DEVICE1 = "Device1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SubNodes_DeploymentDiagramSirius"; //$NON-NLS-1$

	private static final String EXECUTION_ENVIRONMENT1 = "ExecutionEnvironment1"; //$NON-NLS-1$

	private static final String NODE1 = "Node1"; //$NON-NLS-1$

	private String creationToolId;

	private Class<? extends Element> expectedType;

	private String nodeMappingType;

	private String semanticOwnerName;

	/**
	 * Default constructor.
	 *
	 */
	public SubNodeCreationIntoClassifierContainerTest() {
		this.expectedType = DeploymentSpecification.class;
		String deploymentSpec = DeploymentSpecification.class.getSimpleName();
		this.creationToolId = CreationToolsIds.getToolId(deploymentSpec);
		this.nodeMappingType = MappingTypes.getMappingType(deploymentSpec);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createDeploymentSpecificationIntoArtifact() {
		String artifactName = Artifact.class.getSimpleName();
		this.createDeploymentSpecificationIntoContainer(ARTIFACT1, MappingTypes.getMappingType(artifactName), MappingTypes.getCompartmentMappingType(artifactName), UMLPackage.eINSTANCE.getArtifact_NestedArtifact());
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createDeploymentSpecificationIntoDevice() {
		String deviceName = Device.class.getSimpleName();
		this.createDeploymentSpecificationIntoContainer(DEVICE1, MappingTypes.getMappingType(deviceName), MappingTypes.getCompartmentMappingType(deviceName), UMLPackage.eINSTANCE.getClass_NestedClassifier());
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createDeploymentSpecificationIntoExecutionEnvironment() {
		String execEnvName = ExecutionEnvironment.class.getSimpleName();
		this.createDeploymentSpecificationIntoContainer(EXECUTION_ENVIRONMENT1, MappingTypes.getMappingType(execEnvName), MappingTypes.getCompartmentMappingType(execEnvName), UMLPackage.eINSTANCE.getClass_NestedClassifier());
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createDeploymentSpecificationIntoNode() {
		String nodeName = Node.class.getSimpleName();
		this.createDeploymentSpecificationIntoContainer(NODE1, MappingTypes.getMappingType(nodeName), MappingTypes.getCompartmentMappingType(nodeName), UMLPackage.eINSTANCE.getClass_NestedClassifier());
	}


	private void createDeploymentSpecificationIntoContainer(final String semanticOwnerName, final String nodeContainerType, final String nodeCompartmentContainerType, final EReference containmentFeature) {
		this.semanticOwnerName = semanticOwnerName;
		final EObject graphicalContainer = this.getSubNodeOfGraphicalContainer(nodeCompartmentContainerType);
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticNodeCreationChecker(this.getSemanticOwner(), containmentFeature, this.expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalNodeCreationChecker = new DNodeCreationChecker(this.getDiagram(), graphicalContainer, this.nodeMappingType);
		this.createNode(this.creationToolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalNodeCreationChecker), graphicalContainer);
	}

	@Override
	protected Element getSemanticOwner() {
		return this.root.getMember(this.semanticOwnerName);
	}

	@Override
	protected EObject getTopGraphicalContainer() {
		final DDiagram ddiagram = this.getDDiagram();

		final DSemanticDecorator result;
		Element semanticOwner = this.getSemanticOwner();
		// @formatter:off
		Optional<DSemanticDecorator> optionalDSemanticDecorator = ddiagram.getOwnedDiagramElements().stream()
				.filter(DSemanticDecorator.class::isInstance)
				.map(DSemanticDecorator.class::cast)
				.filter(semanticDecorator -> semanticDecorator.getTarget().equals(semanticOwner))
				.findFirst();
		// @formatter:on
		Assert.assertTrue(optionalDSemanticDecorator.isPresent());
		result = optionalDSemanticDecorator.get();
		Assert.assertEquals(semanticOwner, result.getTarget());
		return result;
	}

}
