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
package org.eclipse.papyrus.sirius.uml.diagram.component.tests.reconnection;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractReconnectTargetEdgeTests;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.ReconnectionToolsIds;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.eclipse.uml2.uml.Component;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all the tests related to target reconnection of edges.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/reconnection/Edge_ReconnectionTest.di")
@RunWith(value = Parameterized.class)
public class EdgeReconnectionTargetTest extends AbstractReconnectTargetEdgeTests {

	private static final String COMPONENT = "Component1"; //$NON-NLS-1$

	private static final String COMPONENT_NEW_TARGET = "Component2"; //$NON-NLS-1$

	private static final String CONSTRAINT_NEW_TARGET = "Constraint1"; //$NON-NLS-1$

	private static final String INTERFACE = "Interface1"; //$NON-NLS-1$

	private static final String INTERFACE_NEW_TARGET = "Interface2"; //$NON-NLS-1$

	private static final String MODEL_NEW_TARGET = "Model1"; //$NON-NLS-1$

	private static final String PACKAGE_NEW_TARGET = "Package1"; //$NON-NLS-1$

	private static final String PROPERTY = "Property1"; //$NON-NLS-1$

	private static final String PROPERTY_NEW_TARGET = "Property2"; //$NON-NLS-1$

	private static final String PORT = "Port1"; //$NON-NLS-1$

	private static final String PORT_NEW_TARGET = "Port2"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "ReconnectionComponentDiagramSirius"; //$NON-NLS-1$

	private final String newTargetName;

	private final String mappingNewTargetType;

	public EdgeReconnectionTargetTest(String newTargetName, String mappingNewTargetType) {
		this.newTargetName = newTargetName;
		this.mappingNewTargetType = mappingNewTargetType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetAbstractionTest() {
		this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.ABSTRACTION_EDGE_TYPE);
		this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__ABSTRACTION__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetComponentRealizationTest() {
		if (MappingTypes.COMPONENT_NODE_TYPE.equals(this.mappingNewTargetType)) {
			this.initializeGraphicalAndSemanticContext(INTERFACE, COMPONENT, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.COMPONENT_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.COMPONENT_REALIZATION_EDGE_TYPE);
			this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__COMPONENT_REALIZATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetConnectorTest() {
		List<String> allowedTargetTypes = List.of(MappingTypes.PORT_NODE_TYPE, MappingTypes.PROPERTY_NODE_TYPE);
		if (allowedTargetTypes.contains(this.mappingNewTargetType)) {
			this.initializeGraphicalAndSemanticContext(PROPERTY, PORT, MappingTypes.PROPERTY_NODE_TYPE, MappingTypes.PORT_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.CONNECTOR_EDGE_TYPE);
			this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__CONNECTOR__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetDependencyTest() {
		this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.DEPENDENCY_EDGE_TYPE);
		this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__DEPENDENCY__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetGeneralization() {
		List<String> allowedTargetTypes = List.of(MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		if (allowedTargetTypes.contains(this.mappingNewTargetType)) {
			this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.GENERALIZATION_EDGE_TYPE);
			this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__GENERALIZATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetInterfaceRealization() {
		if (MappingTypes.INTERFACE_NODE_TYPE.equals(this.mappingNewTargetType)) {
			this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.INTERFACE_REALIZATION_EDGE_TYPE);
			this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__INTERFACE_REALIZATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetManifestationTest() {
		List<String> forbiddenTargetTypes = List.of(MappingTypes.PORT_NODE_TYPE, MappingTypes.PROPERTY_NODE_TYPE);
		if (!forbiddenTargetTypes.contains(this.mappingNewTargetType)) {
			this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.MANIFESTATION_EDGE_TYPE);
			this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__MANIFESTATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetSubstitutionTest() {
		List<String> allowedTargetTypes = List.of(MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		if (allowedTargetTypes.contains(this.mappingNewTargetType)) {
			this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.SUBSTITUTION_EDGE_TYPE);
			this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__SUBSTITUTION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetUsageTest() {
		this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.USAGE_EDGE_TYPE);
		this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__USAGE__TOOL, edgeToReconnect);
	}

	private void initializeGraphicalAndSemanticContext(String sourceName, String targetName, String mappingSourceType, String mappingTargetType) {
		// initialize semantic context for test
		this.setSemanticSource(this.getSemanticElement(sourceName));
		this.setSemanticTarget(this.getSemanticElement(targetName));
		this.setSemanticNewTarget(this.getSemanticElement(this.newTargetName));
		// initialize graphical context for test
		this.setEdgeSource(this.getElementsFromDiagramBySemanticName(sourceName, mappingSourceType).get(0));
		this.setEdgeTarget(this.getElementsFromDiagramBySemanticName(targetName, mappingTargetType).get(0));
		this.setEdgeNewTarget(this.getElementsFromDiagramBySemanticName(this.newTargetName, this.mappingNewTargetType).get(0));
	}

	private EObject getSemanticElement(String name) {
		final EObject result;
		if (List.of(PORT, PORT_NEW_TARGET, PROPERTY, PROPERTY_NEW_TARGET).contains(name)) {
			result = ((Component) this.root.getMember(COMPONENT)).getMember(name);
		} else {
			result = this.root.getMember(name);
		}
		return result;
	}

	@Parameters(name = "{index} reconnect target edge on {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ COMPONENT_NEW_TARGET, MappingTypes.COMPONENT_NODE_TYPE },
				{ CONSTRAINT_NEW_TARGET, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ INTERFACE_NEW_TARGET, MappingTypes.INTERFACE_NODE_TYPE },
				{ MODEL_NEW_TARGET, MappingTypes.MODEL_NODE_TYPE },
				{ PACKAGE_NEW_TARGET, MappingTypes.PACKAGE_NODE_TYPE },
				{ PROPERTY_NEW_TARGET, MappingTypes.PROPERTY_NODE_TYPE },
				{ PORT_NEW_TARGET, MappingTypes.PORT_NODE_TYPE }
		});
	}

}
