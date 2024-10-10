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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.reconnection.edges;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractReconnectTargetEdgeTests;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.ReconnectionToolsIds;
import org.eclipse.sirius.diagram.DEdge;
import org.eclipse.sirius.diagram.EdgeTarget;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the target reconnection of edges.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/reconnection/Edge_ReconnectionTest.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class EdgeReconnexionTargetTest extends AbstractReconnectTargetEdgeTests {

	/**
	 * Name of the semantic edge target called Activity2.
	 */
	private static final String ACTIVITY2 = "Activity2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Activity1.
	 */
	private static final String ACTIVITY1 = "Activity1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Class4.
	 */
	private static final String CLASS4 = "Class4"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Class3.
	 */
	private static final String CLASS3 = "Class3"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Class2.
	 */
	private static final String CLASS2 = "Class2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Class1.
	 */
	private static final String CLASS1 = "Class1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Component2.
	 */
	private static final String COMPONENT2 = "Component2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Component1.
	 */
	private static final String COMPONENT1 = "Component1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Interaction2.
	 */
	private static final String INTERACTION2 = "Interaction2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Interaction1.
	 */
	private static final String INTERACTION1 = "Interaction1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called Package2.
	 */
	private static final String PACKAGE2 = "Package2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called Package1.
	 */
	private static final String PACKAGE1 = "Package1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called StateMachine2.
	 */
	private static final String STATEMACHINE2 = "StateMachine2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called StateMachine1.
	 */
	private static final String STATEMACHINE1 = "StateMachine1"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge target called UseCase2.
	 */
	private static final String USECASE2 = "UseCase2"; //$NON-NLS-1$

	/**
	 * Name of the semantic edge source called UseCase1.
	 */
	private static final String USECASE1 = "UseCase1"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called ActivityNewEnd.
	 */
	private static final String ACTIVITY_NEW_TARGET = "ActivityNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called Actor1.
	 */
	private static final String ACTOR_NEW_TARGET = "ActorNewEnd"; //$NON-NLS-1$
	/**
	 * Name of the new semantic edge target called ClassNewEnd.
	 */
	private static final String CLASS_NEW_TARGET = "ClassNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called ComponentNewEnd.
	 */
	private static final String COMPONENT_NEW_TARGET = "ComponentNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called InteractionNewEnd.
	 */
	private static final String INTERACTION_NEW_TARGET = "InteractionNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called PackageNewEnd.
	 */
	private static final String PACKAGE_NEW_TARGET = "PackageNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called StateMachineNewEnd.
	 */
	private static final String STATEMACHINE_NEW_TARGET = "StateMachineNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the new semantic edge target called UseCaseNewEnd.
	 */
	private static final String USECASE_NEW_TARGET = "UseCaseNewEnd"; //$NON-NLS-1$

	/**
	 * Name of the diagram.
	 */
	private static final String DIAGRAM_NAME = "Edge_UseCaseDiagramSirius"; //$NON-NLS-1$

	private final String newTargetName;

	private final String mappingNewTargetTypeName;

	/**
	 * Default constructor.
	 *
	 */
	public EdgeReconnexionTargetTest(String newTargetName, String mappingNewTargetTypeName) {
		this.newTargetName = newTargetName;
		this.mappingNewTargetTypeName = mappingNewTargetTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetAbstractionTest() {
		initializeGraphicalAndSemanticContext(ACTIVITY1, ACTIVITY2, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.ABSTRACTION_EDGE_TYPE);
		// test target reconnection
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__ABSTRACTION__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetAssociationTest() {
		if (!PACKAGE_NEW_TARGET.equals(newTargetName)) {
			initializeGraphicalAndSemanticContext(STATEMACHINE1, STATEMACHINE2, MappingTypes.STATE_MACHINE_NODE_TYPE, MappingTypes.STATE_MACHINE_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.ASSOCIATION_EDGE_TYPE);
			// test target reconnection
			reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__ASSOCIATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetDependencyTest() {
		initializeGraphicalAndSemanticContext(CLASS3, CLASS4, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.DEPENDENCY_EDGE_TYPE);
		// test target reconnection
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__DEPENDENCY__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetExtendTest() {
		if (USECASE_NEW_TARGET.equals(newTargetName)) {
			initializeGraphicalAndSemanticContext(USECASE1, USECASE2, MappingTypes.USE_CASE_NODE_TYPE, MappingTypes.USE_CASE_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.EXTEND_EDGE_TYPE);
			// test target reconnection
			reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__EXTEND__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetIncludeTest() {
		if (USECASE_NEW_TARGET.equals(newTargetName)) {
			initializeGraphicalAndSemanticContext(USECASE1, USECASE2, MappingTypes.USE_CASE_NODE_TYPE, MappingTypes.USE_CASE_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.INCLUDE_EDGE_TYPE);
			// test target reconnection
			reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__INCLUDE__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetRealizationTest() {
		initializeGraphicalAndSemanticContext(COMPONENT1, COMPONENT2, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.REALIZATION_EDGE_TYPE);
		// test target reconnection
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__REALIZATION__TOOL, edgeToReconnect);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetGeneralizationTest() {
		if (!PACKAGE_NEW_TARGET.equals(newTargetName)) {
			initializeGraphicalAndSemanticContext(CLASS1, CLASS2, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.GENERALIZATION_EDGE_TYPE);
			// test target reconnection
			reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__GENERALIZATION__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetPackageImportTest() {
		if (PACKAGE_NEW_TARGET.equals(newTargetName)) {
			initializeGraphicalAndSemanticContext(PACKAGE1, PACKAGE2, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.PACKAGE_IMPORT_EDGE_TYPE);
			// test target reconnection
			reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__PACKAGE_IMPORT__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetPackageMergeTest() {
		if (PACKAGE_NEW_TARGET.equals(newTargetName)) {
			initializeGraphicalAndSemanticContext(PACKAGE1, PACKAGE2, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_TYPE);
			DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.PACKAGE_MERGE_EDGE_TYPE);
			// test target reconnection
			reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__PACKAGE_MERGE__TOOL, edgeToReconnect);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void reconnectTargetUsageTest() {
		initializeGraphicalAndSemanticContext(INTERACTION1, INTERACTION2, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_TYPE);
		DEdge edgeToReconnect = getEdgeFromDiagram((EdgeTarget) this.getEdgeSource(), (EdgeTarget) this.getEdgeTarget(), MappingTypes.USAGE_EDGE_TYPE);
		// test target reconnection
		reconnectTargetEdge(ReconnectionToolsIds.RECONNECT_TARGET__USAGE__TOOL, edgeToReconnect);
	}

	private void initializeGraphicalAndSemanticContext(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		// initialize semantic context for test
		this.setSemanticSource(this.root.getMember(sourceName));
		this.setSemanticTarget(this.root.getMember(targetName));
		this.setSemanticNewTarget(this.root.getMember(newTargetName));
		// initialize graphical context for test
		this.setEdgeSource(getNodeFromDiagram(sourceName, mappingSourceTypeName));
		this.setEdgeTarget(getNodeFromDiagram(targetName, mappingTargetTypeName));
		this.setEdgeNewTarget(getNodeFromDiagram(newTargetName, mappingNewTargetTypeName));
	}

	@Parameters(name = "{index} reconnect target edge on {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY_NEW_TARGET, MappingTypes.ACTIVITY_NODE_TYPE },
				{ ACTOR_NEW_TARGET, MappingTypes.ACTOR_NODE_TYPE },
				{ CLASS_NEW_TARGET, MappingTypes.CLASS_NODE_TYPE },
				{ COMPONENT_NEW_TARGET, MappingTypes.COMPONENT_NODE_TYPE },
				{ INTERACTION_NEW_TARGET, MappingTypes.INTERACTION_NODE_TYPE },
				{ PACKAGE_NEW_TARGET, MappingTypes.PACKAGE_NODE_TYPE },
				{ STATEMACHINE_NEW_TARGET, MappingTypes.STATE_MACHINE_NODE_TYPE },
				{ USECASE_NEW_TARGET, MappingTypes.USE_CASE_NODE_TYPE },
		});
	}

}
