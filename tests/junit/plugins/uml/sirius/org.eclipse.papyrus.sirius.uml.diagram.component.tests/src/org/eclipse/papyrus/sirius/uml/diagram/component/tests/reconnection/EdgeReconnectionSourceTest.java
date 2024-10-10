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
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractReconnectSourceEdgeTests;
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
 * Groups all the tests related to source reconnection of edges.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/reconnection/Edge_ReconnectionTest.di")
@RunWith(value = Parameterized.class)
public class EdgeReconnectionSourceTest extends AbstractReconnectSourceEdgeTests {

	private static final String COMPONENT = "Component1"; //$NON-NLS-1$

	private static final String COMPONENT_NEW_SOURCE = "Component2"; //$NON-NLS-1$

	private static final String CONSTRAINT_NEW_SOURCE = "Constraint1"; //$NON-NLS-1$

	private static final String INTERFACE = "Interface1"; //$NON-NLS-1$

	private static final String INTERFACE_NEW_SOURCE = "Interface2"; //$NON-NLS-1$

	private static final String MODEL_NEW_SOURCE = "Model1"; //$NON-NLS-1$

	private static final String PACKAGE_NEW_SOURCE = "Package1"; //$NON-NLS-1$

	private static final String PROPERTY = "Property1"; //$NON-NLS-1$

	private static final String PROPERTY_NEW_SOURCE = "Property2"; //$NON-NLS-1$

	private static final String PORT = "Port1"; //$NON-NLS-1$

	private static final String PORT_NEW_SOURCE = "Port2"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "ReconnectionComponentDiagramSirius"; //$NON-NLS-1$

	private final String newSourceName;

	private final String mappingNewSourceType;

	public EdgeReconnectionSourceTest(String newSourceName, String mappingNewSourceType) {
		this.newSourceName = newSourceName;
		this.mappingNewSourceType = mappingNewSourceType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceAbstractionTest() {
		this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.ABSTRACTION_EDGE_TYPE);
		this.reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__ABSTRACTION__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceComponentRealizationTest() {
		List<String> allowedSourceTypes = List.of(MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		if (allowedSourceTypes.contains(this.mappingNewSourceType)) {
			this.initializeGraphicalAndSemanticContext(INTERFACE, COMPONENT, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.COMPONENT_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.COMPONENT_REALIZATION_EDGE_TYPE);
			this.reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__COMPONENT_REALIZATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceConnectorTest() {
		List<String> allowedSourceTypes = List.of(MappingTypes.PORT_NODE_TYPE, MappingTypes.PROPERTY_NODE_TYPE);
		if (allowedSourceTypes.contains(this.mappingNewSourceType)) {
			this.initializeGraphicalAndSemanticContext(PROPERTY, PORT, MappingTypes.PROPERTY_NODE_TYPE, MappingTypes.PORT_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.CONNECTOR_EDGE_TYPE);
			this.reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__CONNECTOR__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceDependencyTest() {
		this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.DEPENDENCY_EDGE_TYPE);
		this.reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__DEPENDENCY__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceGeneralization() {
		List<String> allowedSourceTypes = List.of(MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		if (allowedSourceTypes.contains(this.mappingNewSourceType)) {
			this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.GENERALIZATION_EDGE_TYPE);
			this.reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__GENERALIZATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceInterfaceRealization() {
		if (MappingTypes.COMPONENT_NODE_TYPE.equals(this.mappingNewSourceType)) {
			this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.INTERFACE_REALIZATION_EDGE_TYPE);
			this.reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__INTERFACE_REALIZATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceManifestationTest() {
		this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.MANIFESTATION_EDGE_TYPE);
		this.reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__MANIFESTATION__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceSubstitutionTest() {
		List<String> allowedSourceTypes = List.of(MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		if (allowedSourceTypes.contains(this.mappingNewSourceType)) {
			this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.SUBSTITUTION_EDGE_TYPE);
			this.reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__SUBSTITUTION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceUsageTest() {
		this.initializeGraphicalAndSemanticContext(COMPONENT, INTERFACE, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.USAGE_EDGE_TYPE);
		this.reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__USAGE__TOOL, edgeToReconnect);
	}

	private void initializeGraphicalAndSemanticContext(String sourceName, String targetName, String mappingSourceType, String mappingTargetType) {
		// initialize semantic context for test
		this.setSemanticSource(this.getSemanticElement(sourceName));
		this.setSemanticTarget(this.getSemanticElement(targetName));
		this.setSemanticNewSource(this.getSemanticElement(this.newSourceName));
		// initialize graphical context for test
		this.setEdgeSource(this.getElementsFromDiagramBySemanticName(sourceName, mappingSourceType).get(0));
		this.setEdgeTarget(this.getElementsFromDiagramBySemanticName(targetName, mappingTargetType).get(0));
		this.setEdgeNewSource(this.getElementsFromDiagramBySemanticName(this.newSourceName, this.mappingNewSourceType).get(0));
	}

	private EObject getSemanticElement(String name) {
		final EObject result;
		if (List.of(PORT, PORT_NEW_SOURCE, PROPERTY, PROPERTY_NEW_SOURCE).contains(name)) {
			result = ((Component) this.root.getMember(COMPONENT)).getMember(name);
		} else {
			result = this.root.getMember(name);
		}
		return result;
	}

	@Parameters(name = "{index} reconnect source edge on {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ COMPONENT_NEW_SOURCE, MappingTypes.COMPONENT_NODE_TYPE },
				{ CONSTRAINT_NEW_SOURCE, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ INTERFACE_NEW_SOURCE, MappingTypes.INTERFACE_NODE_TYPE },
				{ MODEL_NEW_SOURCE, MappingTypes.MODEL_NODE_TYPE },
				{ PACKAGE_NEW_SOURCE, MappingTypes.PACKAGE_NODE_TYPE },
				{ PROPERTY_NEW_SOURCE, MappingTypes.PROPERTY_NODE_TYPE },
				{ PORT_NEW_SOURCE, MappingTypes.PORT_NODE_TYPE }
		});
	}

}
