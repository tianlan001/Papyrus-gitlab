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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.services.DeploymentDiagramServices;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.InputPin;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Pin;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;

/**
 * JUnit tests for the {@link DeploymentDiagramServices}
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
public class DeploymentDiagramServicesTests extends AbstractServicesTest {

	private final DeploymentDiagramServices deploymentDiagramServices = new DeploymentDiagramServices();


	/**
	 * Verify the getArtifactCandidates service.
	 */
	@Test
	public void testGetArtifactCandidates() {
		Model model = this.create(Model.class);
		Artifact rootArtifact = this.create(Artifact.class);
		Node rootNode = this.create(Node.class);
		Artifact artifactInPackage = this.create(Artifact.class);
		DeploymentSpecification deploymentSpecificationInPackage = this.create(DeploymentSpecification.class);
		model.getPackagedElements().addAll(List.of(artifactInPackage, deploymentSpecificationInPackage));

		//We check Artifact within a Package type (a Model here)
		List<? extends Artifact> artifactCandidates = this.deploymentDiagramServices.getArtifactCandidates(model);
		assertEquals(2, artifactCandidates.size());
		assertTrue(List.of(artifactInPackage, deploymentSpecificationInPackage).containsAll(artifactCandidates));

		Artifact artifactInArtifact = this.create(Artifact.class);
		DeploymentSpecification deploymentSpecificationInArtifact = this.create(DeploymentSpecification.class);
		rootArtifact.getNestedArtifacts().addAll(List.of(artifactInArtifact, deploymentSpecificationInArtifact));


		//We check Artifact within a Artifact type
		artifactCandidates = this.deploymentDiagramServices.getArtifactCandidates(rootArtifact);
		assertEquals(2, artifactCandidates.size());
		assertTrue(List.of(artifactInArtifact, deploymentSpecificationInArtifact).containsAll(artifactCandidates));

		Artifact artifactInNode = this.create(Artifact.class);
		DeploymentSpecification deploymentSpecificationInNode = this.create(DeploymentSpecification.class);
		rootNode.getNestedClassifiers().addAll(List.of(artifactInNode, deploymentSpecificationInNode));

		//We check Artifact within a Node type
		artifactCandidates = this.deploymentDiagramServices.getArtifactCandidates(rootNode);
		assertEquals(2, artifactCandidates.size());
		assertTrue(List.of(artifactInNode, deploymentSpecificationInNode).containsAll(artifactCandidates));


		// Check a type that is not supported
		Pin pin = this.create(InputPin.class);
		artifactCandidates = this.deploymentDiagramServices.getArtifactCandidates(pin);
		assertEquals(0, artifactCandidates.size());
	}


	/**
	 * Verify the getNodeCandidates service.
	 */
	@Test
	public void testGetNodeCandidates() {
		Model model = this.create(Model.class);
		Artifact rootArtifact = this.create(Artifact.class);
		Node rootNode = this.create(Node.class);
		Node nodeInPackage = this.create(Node.class);
		Node node2InPackage = this.create(Node.class);
		model.getPackagedElements().addAll(List.of(nodeInPackage, node2InPackage));

		// Check Node in Package
		List<? extends Node> nodeCandidates = this.deploymentDiagramServices.getNodeCandidates(model);
		assertEquals(2, nodeCandidates.size());
		assertTrue(List.of(nodeInPackage, node2InPackage).containsAll(nodeCandidates));

		Node nodeInNode = this.create(Node.class);
		Node node2InNode = this.create(Node.class);
		rootNode.getNestedNodes().addAll(List.of(nodeInNode, node2InNode));

		// Check Node in Node
		nodeCandidates = this.deploymentDiagramServices.getNodeCandidates(rootNode);
		assertEquals(2, nodeCandidates.size());
		assertTrue(List.of(nodeInNode, node2InNode).containsAll(nodeCandidates));

		// Check a type that is not supported
		nodeCandidates = this.deploymentDiagramServices.getNodeCandidates(rootArtifact);
		assertEquals(0, nodeCandidates.size());
	}

	/**
	 * Verify the getPackageCandidates service.
	 */
	@Test
	public void testGetPackageCandidates() {
		Model model = this.create(Model.class);
		Artifact rootArtifact = this.create(Artifact.class);
		Package packageInPackage = this.create(Package.class);
		Model modelInPackage = this.create(Model.class);
		model.getPackagedElements().addAll(List.of(packageInPackage, modelInPackage));

		List<? extends Package> packageCandidates = this.deploymentDiagramServices.getPackageCandidates(model);
		assertEquals(2, packageCandidates.size());
		assertTrue(List.of(packageInPackage, modelInPackage).containsAll(packageCandidates));

		// Check a type that is not supported
		packageCandidates = this.deploymentDiagramServices.getPackageCandidates(rootArtifact);
		assertEquals(0, packageCandidates.size());
	}

	/**
	 * Verify the canCreateNode service.
	 */
	@Test
	public void testCanCreateNode() {
		Model model = this.create(Model.class);
		Artifact artifact = this.create(Artifact.class);
		Node node = this.create(Node.class);
		assertTrue(this.deploymentDiagramServices.canCreateNodeDD(model, UMLPackage.eINSTANCE.getDevice()));
		assertTrue(this.deploymentDiagramServices.canCreateNodeDD(node, UMLPackage.eINSTANCE.getDevice()));
		assertFalse(this.deploymentDiagramServices.canCreateNodeDD(artifact, UMLPackage.eINSTANCE.getDevice()));
		assertFalse(this.deploymentDiagramServices.canCreateNodeDD(null, UMLPackage.eINSTANCE.getDevice()));
	}

	/**
	 * Verify the canCreateArtifact service.
	 */
	@Test
	public void testCanCreateArtifact() {
		Model model = this.create(Model.class);
		Artifact artifact = this.create(Artifact.class);
		Node node = this.create(Node.class);
		assertTrue(this.deploymentDiagramServices.canCreateArtifactDD(model, UMLPackage.eINSTANCE.getDeploymentSpecification()));
		assertTrue(this.deploymentDiagramServices.canCreateArtifactDD(node, UMLPackage.eINSTANCE.getDeploymentSpecification()));
		assertTrue(this.deploymentDiagramServices.canCreateArtifactDD(artifact, UMLPackage.eINSTANCE.getDeploymentSpecification()));
		assertFalse(this.deploymentDiagramServices.canCreateArtifactDD(null, UMLPackage.eINSTANCE.getDeploymentSpecification()));
	}
}
