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
package org.eclipse.papyrus.sirius.uml.diagram.profile.tests.reconnection;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractReconnectSourceEdgeTests;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.profile.tests.ReconnectionToolsIds;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the source reconnection of edges.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/reconnection/ReconnectionTest.profile.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class EdgeReconnectionSourceTest extends AbstractReconnectSourceEdgeTests {

	private static final String METACLASS_ACTIVITY = "Activity"; //$NON-NLS-1$

	private static final String METACLASS_ACTOR = "Actor"; //$NON-NLS-1$

	private static final String SOURCE_CLASS = "Class1"; //$NON-NLS-1$

	private static final String TARGET_CLASS = "Class2"; //$NON-NLS-1$

	private static final String NEW_SOURCE_CLASS = "Class3"; //$NON-NLS-1$

	private static final String DATATYPE = "DataType1"; //$NON-NLS-1$

	private static final String ENUMERATION = "Enumeration1"; //$NON-NLS-1$

	private static final String PRIMITIVETYPE = "PrimitiveType1"; //$NON-NLS-1$

	private static final String STEREOTYPE = "Stereotype1"; //$NON-NLS-1$

	private static final String STEREOTYPE2 = "Stereotype2"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "ReconnectionProfileDiagramSirius"; //$NON-NLS-1$

	private final String newSourceName;

	private final String mappingNewSourceTypeName;

	/**
	 * Default constructor.
	 *
	 */
	public EdgeReconnectionSourceTest(String newSourceName, String mappingNewSourceTypeName) {
		this.newSourceName = newSourceName;
		this.mappingNewSourceTypeName = mappingNewSourceTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceAssociationTest() {
		if (!SOURCE_CLASS.equals(this.newSourceName) && !METACLASS_ACTIVITY.equals(this.newSourceName)) {
			initializeGraphicalAndSemanticContext(SOURCE_CLASS, TARGET_CLASS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.ASSOCIATION_EDGE_TYPE);
			// test source reconnection
			reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__ASSOCIATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceExtensionTest() {
		if (STEREOTYPE2.equals(this.newSourceName)) {
			initializeGraphicalAndSemanticContext(STEREOTYPE, METACLASS_ACTOR, MappingTypes.STEREOTYPE_NODE_TYPE, MappingTypes.METACLASS_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.EXTENSION_EDGE_TYPE);
			// test source reconnection
			reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__EXTENSION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectSourceGeneralizationTest() {
		if (!SOURCE_CLASS.equals(this.newSourceName) && !METACLASS_ACTIVITY.equals(this.newSourceName)) {
			initializeGraphicalAndSemanticContext(SOURCE_CLASS, TARGET_CLASS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.GENERALIZATION_EDGE_TYPE);
			// test source reconnection
			reconnectSourceEdge(ReconnectionToolsIds.RECONNECT_SOURCE__GENERALIZATION__TOOL, edgeToReconnect);
		}
	}

	private void initializeGraphicalAndSemanticContext(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		// initialize semantic context for test
		this.setSemanticSource(this.root.getMember(sourceName));
		this.setSemanticTarget(this.root.getMember(targetName));
		this.setSemanticNewSource(this.root.getMember(this.newSourceName));
		// initialize graphical context for test
		this.setEdgeSource(getNodeFromDiagram(sourceName, mappingSourceTypeName));
		this.setEdgeTarget(getNodeFromDiagram(targetName, mappingTargetTypeName));
		this.setEdgeNewSource(getNodeFromDiagram(this.newSourceName, this.mappingNewSourceTypeName));
	}

	@Parameters(name = "{index} reconnect source edge on {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ NEW_SOURCE_CLASS, MappingTypes.CLASS_NODE_TYPE },
				{ DATATYPE, MappingTypes.DATATYPE_NODE_TYPE },
				{ ENUMERATION, MappingTypes.ENUMERATION_NODE_TYPE },
				{ STEREOTYPE, MappingTypes.STEREOTYPE_NODE_TYPE },
				{ STEREOTYPE2, MappingTypes.STEREOTYPE_NODE_TYPE },
				{ PRIMITIVETYPE, MappingTypes.PRIMITIVETYPE_NODE_TYPE },
				{ METACLASS_ACTIVITY, MappingTypes.METACLASS_NODE_TYPE },
		});
	}

}
