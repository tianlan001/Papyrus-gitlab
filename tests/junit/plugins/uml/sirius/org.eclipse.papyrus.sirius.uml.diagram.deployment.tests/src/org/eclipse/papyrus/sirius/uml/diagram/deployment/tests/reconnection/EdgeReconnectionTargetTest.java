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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.reconnection;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractReconnectTargetEdgeTests;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.ReconnectionToolsIds;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.CommunicationPath;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Deployment;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all the tests related to target reconnection of edges on Deployment diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/reconnection/Edge_ReconnectionTest.di")
@RunWith(value = Parameterized.class)
public class EdgeReconnectionTargetTest extends AbstractReconnectTargetEdgeTests {

	private static final String NEW_TARGET_SUFFIX = "1"; //$NON-NLS-1$

	private static final String ARTIFACT = "Artifact2"; //$NON-NLS-1$

	private static final String NODE = "Node2"; //$NON-NLS-1$

	private static final String DEVICE = "Device2"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "ReconnectionDeploymentDiagramSirius"; //$NON-NLS-1$

	private final String newTargetName;

	private final String mappingNewTargetType;

	public EdgeReconnectionTargetTest(Class<? extends Element> newTargetType) {
		// Already connected nodes in the test model are suffixed with "2". Nodes to use as new targets are suffixed with "1".
		this.newTargetName = newTargetType.getSimpleName() + NEW_TARGET_SUFFIX;
		this.mappingNewTargetType = MappingTypes.getMappingType(newTargetType.getSimpleName());
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetCommunicationPathTest() {
		List<String> allowedTargetTypes = List.of(MappingTypes.getMappingType(Device.class.getSimpleName()), MappingTypes.getMappingType(ExecutionEnvironment.class.getSimpleName()), MappingTypes.getMappingType(Node.class.getSimpleName()),
				MappingTypes.getMappingType(Node.class.getSimpleName()));
		if (allowedTargetTypes.contains(this.mappingNewTargetType)) {
			this.initializeGraphicalAndSemanticContext(NODE, DEVICE, MappingTypes.getMappingType(Node.class.getSimpleName()), MappingTypes.getMappingType(Device.class.getSimpleName()));
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.getMappingType(CommunicationPath.class.getSimpleName()));
			this.reconnectTargetEdge(ReconnectionToolsIds.getReconnectTargetToolId(CommunicationPath.class.getSimpleName()), edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetDependencyTest() {
		this.initializeGraphicalAndSemanticContext(ARTIFACT, NODE, MappingTypes.getMappingType(Artifact.class.getSimpleName()), MappingTypes.getMappingType(Node.class.getSimpleName()));
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.getMappingType(Dependency.class.getSimpleName()));
		this.reconnectTargetEdge(ReconnectionToolsIds.getReconnectTargetToolId(Dependency.class.getSimpleName()), edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetDeploymentTest() {
		List<String> allowedTargetTypes = List.of(MappingTypes.getMappingType(Device.class.getSimpleName()), MappingTypes.getMappingType(ExecutionEnvironment.class.getSimpleName()), MappingTypes.getMappingType(Node.class.getSimpleName()));
		if (allowedTargetTypes.contains(this.mappingNewTargetType)) {
			this.initializeGraphicalAndSemanticContext(ARTIFACT, NODE, MappingTypes.getMappingType(Artifact.class.getSimpleName()), MappingTypes.getMappingType(Node.class.getSimpleName()));
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.getMappingType(Deployment.class.getSimpleName()));
			this.reconnectTargetEdge(ReconnectionToolsIds.getReconnectTargetToolId(Deployment.class.getSimpleName()), edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetGeneralizationTest() {
		List<String> forbiddenTargetTypes = List.of(MappingTypes.getMappingType(Constraint.class.getSimpleName()), MappingTypes.getMappingType(Model.class.getSimpleName()), MappingTypes.getMappingType(Package.class.getSimpleName()));
		if (!forbiddenTargetTypes.contains(this.mappingNewTargetType)) {
			this.initializeGraphicalAndSemanticContext(ARTIFACT, NODE, MappingTypes.getMappingType(Artifact.class.getSimpleName()), MappingTypes.getMappingType(Node.class.getSimpleName()));
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.getMappingType(Generalization.class.getSimpleName()));
			this.reconnectTargetEdge(ReconnectionToolsIds.getReconnectTargetToolId(Generalization.class.getSimpleName()), edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetManifestationTest() {
		this.initializeGraphicalAndSemanticContext(ARTIFACT, NODE, MappingTypes.getMappingType(Artifact.class.getSimpleName()), MappingTypes.getMappingType(Node.class.getSimpleName()));
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.getMappingType(Manifestation.class.getSimpleName()));
		this.reconnectTargetEdge(ReconnectionToolsIds.getReconnectTargetToolId(Manifestation.class.getSimpleName()), edgeToReconnect);
	}

	private void initializeGraphicalAndSemanticContext(String sourceName, String targetName, String mappingSourceType, String mappingTargetType) {
		// initialize semantic context for test
		this.setSemanticSource(this.root.getMember(sourceName));
		this.setSemanticTarget(this.root.getMember(targetName));
		this.setSemanticNewTarget(this.root.getMember(this.newTargetName));
		// initialize graphical context for test
		this.setEdgeSource(this.getElementsFromDiagramBySemanticName(sourceName, mappingSourceType).get(0));
		this.setEdgeTarget(this.getElementsFromDiagramBySemanticName(targetName, mappingTargetType).get(0));
		this.setEdgeNewTarget(this.getElementsFromDiagramBySemanticName(this.newTargetName, this.mappingNewTargetType).get(0));
	}

	@Parameters(name = "{index} reconnect source edge on {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ Artifact.class },
				{ Constraint.class },
				{ DeploymentSpecification.class },
				{ Device.class },
				{ ExecutionEnvironment.class },
				{ Model.class },
				{ Node.class },
				{ Package.class }
		});
	}

}
