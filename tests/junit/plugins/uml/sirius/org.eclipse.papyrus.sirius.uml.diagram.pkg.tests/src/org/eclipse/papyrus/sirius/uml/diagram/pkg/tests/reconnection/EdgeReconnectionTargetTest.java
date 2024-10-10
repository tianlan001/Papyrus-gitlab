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
package org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.reconnection;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractReconnectTargetEdgeTests;
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.ReconnectionToolsIds;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all the tests related to target reconnection of edges.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/reconnection/Edge_ReconnectionTest.di")
@RunWith(value = Parameterized.class)
public class EdgeReconnectionTargetTest extends AbstractReconnectTargetEdgeTests {

	protected static final String NEW_END = "NewEnd"; //$NON-NLS-1$

	protected static final String ABSTRACTION_SUFFIX = "_WithAbstraction"; //$NON-NLS-1$

	protected static final String DEPENDENCY_SUFFIX = "_WithDependency"; //$NON-NLS-1$

	protected static final String PACKAGE_IMPORT_SUFFIX = "_WithPackageImport"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String MODEL = "Model"; //$NON-NLS-1$

	private static final String PACKAGE = "Package"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "ReconnectionPackageDiagramSirius"; //$NON-NLS-1$

	private final String newTargetName;

	private final String mappingNewTargetTypeName;

	public EdgeReconnectionTargetTest(String newTargetName, String mappingNewTargetTypeName) {
		this.newTargetName = newTargetName + NEW_END;
		this.mappingNewTargetTypeName = mappingNewTargetTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetAbstractionTest() {
		this.initializeGraphicalAndSemanticContext(MODEL + ABSTRACTION_SUFFIX, PACKAGE + ABSTRACTION_SUFFIX, MappingTypes.MODEL_NODE_TYPE, MappingTypes.PACKAGE_NODE_TYPE);
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.ABSTRACTION_EDGE_TYPE);
		this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__ABSTRACTION__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetDependencyTest() {
		this.initializeGraphicalAndSemanticContext(PACKAGE + DEPENDENCY_SUFFIX, CONSTRAINT + DEPENDENCY_SUFFIX, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.CONSTRAINT_NODE_TYPE);
		DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.DEPENDENCY_EDGE_TYPE);
		this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__DEPENDENCY__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetPackageImportTest() {
		List<String> allowedTargetTypes = List.of(MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.MODEL_NODE_TYPE);
		if (allowedTargetTypes.contains(this.mappingNewTargetTypeName)) {
			this.initializeGraphicalAndSemanticContext(PACKAGE + PACKAGE_IMPORT_SUFFIX, MODEL + PACKAGE_IMPORT_SUFFIX, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.MODEL_NODE_TYPE);
			DEdge edgeToReconnect = this.getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.PACKAGE_IMPORT_EDGE_TYPE);
			this.reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__PACKAGE_IMPORT__TOOL, edgeToReconnect);
		}
	}

	private void initializeGraphicalAndSemanticContext(String sourceName, String targetName, String mappingSourceType, String mappingTargetType) {
		// initialize semantic context for test
		this.setSemanticSource(this.getElementByNameFromRoot(sourceName).get());
		this.setSemanticTarget(this.getElementByNameFromRoot(targetName).get());
		// initialize graphical context for test
		this.setEdgeSource(this.getElementsFromDiagramBySemanticName(sourceName, mappingSourceType).get(0));
		this.setEdgeTarget(this.getElementsFromDiagramBySemanticName(targetName, mappingTargetType).get(0));
		this.setSemanticNewTarget(this.getElementByNameFromRoot(this.newTargetName).get());
		this.setEdgeNewTarget(this.getElementsFromDiagramBySemanticName(this.newTargetName, this.mappingNewTargetTypeName).get(0));
	}

	@Parameters(name = "{index} reconnect target edge on {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CONSTRAINT, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ MODEL, MappingTypes.MODEL_NODE_TYPE },
				{ PACKAGE, MappingTypes.PACKAGE_NODE_TYPE },
		});
	}

}
