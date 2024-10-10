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
package org.eclipse.papyrus.sirius.uml.diagram.activity.tests.creation.edges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractCreateEdgeTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.checker.SemanticAndGraphicalCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DEdgeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.ISemanticRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.semantic.checker.SemanticEdgeCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.activity.tests.MappingTypes;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the creation of ControlFlow edges in the Activity Diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/creation/edges/Edge_CreationTest.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class ControlFlowEdgeCreationTest extends AbstractCreateEdgeTests {

	private static final String AD_PREFIX = "AD_"; //$NON-NLS-1$

	private static final String FIRST_ELEMENT_SUFFIX = "1"; //$NON-NLS-1$

	private static final String SECOND_ELEMENT_SUFFIX = "2"; //$NON-NLS-1$

	private static final String ACTIVITY_EDGE_CONTAINMENT_FEATURE_NAME = "edge"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "EdgeActivityDiagramSirius_ControlFlow"; //$NON-NLS-1$

	private static final String ROOT_ACTIVITY = "RootActivity"; //$NON-NLS-1$

	private static final String ACTIVITY = "Activity"; //$NON-NLS-1$

	private static final String ACTIVITY_FINAL_NODE = "ActivityFinalNode"; //$NON-NLS-1$

	private static final String ACTIVITY_PARAMETER_NODE = "ActivityParameterNode"; //$NON-NLS-1$

	private static final String CALL_BEHAVIOR_ACTION = "CallBehaviorAction"; //$NON-NLS-1$

	private static final String DECISION_NODE = "DecisionNode"; //$NON-NLS-1$

	private static final String EXPANSION_NODE = "ExpansionNode"; //$NON-NLS-1$

	private static final String FLOW_FINAL_NODE = "FlowFinalNode"; //$NON-NLS-1$

	private static final String FORK_NODE = "ForkNode"; //$NON-NLS-1$

	private static final String INITIAL_NODE = "InitialNode"; //$NON-NLS-1$

	private static final String INPUT_PIN = "InputPin"; //$NON-NLS-1$

	private static final String JOIN_NODE = "JoinNode"; //$NON-NLS-1$

	private static final String MERGE_NODE = "MergeNode"; //$NON-NLS-1$

	private static final String OPAQUE_ACTION = "OpaqueAction"; //$NON-NLS-1$

	private static final String OUTPUT_PIN = "OutputPin"; //$NON-NLS-1$

	private static final String STRUCTURED_ACTIVITY_NODE = "StructuredActivityNode"; //$NON-NLS-1$

	private static final Map<String, String> ELEMENT_TYPE_TO_MAPPING = getElementTypeToMapping();

	private final String sourceName;

	private final String targetName;

	private final String mappingSourceTypeName;

	private final String mappingTargetTypeName;

	private Activity rootActivity;

	/**
	 * Default constructor.
	 *
	 */
	public ControlFlowEdgeCreationTest(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		this.sourceName = sourceName;
		this.targetName = targetName;
		this.mappingSourceTypeName = mappingSourceTypeName;
		this.mappingTargetTypeName = mappingTargetTypeName;
	}

	@Override
	public void setUp() {
		super.setUp();
		this.rootActivity = (Activity) this.root.getMember(ROOT_ACTIVITY);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createControlFlowTest() {
		List<String> forbiddenSourceMappingList = List.of(MappingTypes.ACTIVITY_FINAL_NODE_NODE_TYPE, MappingTypes.FLOW_FINAL_NODE_NODE_TYPE);
		List<String> forbiddenTargetMappingList = List.of(MappingTypes.INITIAL_NODE_NODE_TYPE);
		if (!forbiddenSourceMappingList.contains(this.mappingSourceTypeName) && !forbiddenTargetMappingList.contains(this.mappingTargetTypeName)) {
			Activity expectedOwner = (Activity) this.root.getMember(ROOT_ACTIVITY);
			if (MappingTypes.ACTIVITY_PARAMETER_NODE_NODE_TYPE.equals(this.mappingSourceTypeName)) {
				expectedOwner = (Activity) this.getElementByNameFromRoot(ACTIVITY + FIRST_ELEMENT_SUFFIX).get();
			}
			this.testEdgeCreation(CreationToolsIds.CREATE__CONTROL_FLOW__TOOL, ControlFlow.class,
					expectedOwner, MappingTypes.CONTROL_FLOW_EDGE_TYPE);
		}
	}

	private void testEdgeCreation(String toolId, Class<? extends Element> expectedType, EObject expectedOwner, String expectedEdgeMappingType) {
		Activity activitySemanticContainer = (Activity) this.root.getMember(ROOT_ACTIVITY);
		EReference containmentFeature = (EReference) expectedOwner.eClass().getEStructuralFeature(ACTIVITY_EDGE_CONTAINMENT_FEATURE_NAME);
		// initialize semantic context for test
		EObject semanticSource = activitySemanticContainer.getMember(this.sourceName);
		if (semanticSource == null) {
			semanticSource = this.getElementByNameFromRoot(this.sourceName).get();
		}
		EObject semanticTarget = activitySemanticContainer.getMember(this.targetName);
		if (semanticTarget == null) {
			semanticTarget = this.getElementByNameFromRoot(this.targetName).get();
		}
		this.setSemanticSource(semanticSource);
		this.setSemanticTarget(semanticTarget);
		// initialize graphical context for test
		this.setEdgeSource(this.getElementsFromDiagramBySemanticName(this.sourceName, this.mappingSourceTypeName).get(0));
		this.setEdgeTarget(this.getElementsFromDiagramBySemanticName(this.targetName, this.mappingTargetTypeName).get(0));
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticEdgeCreationChecker(expectedOwner, containmentFeature, expectedType);

		final IGraphicalRepresentationElementCreationChecker graphicalEdgeCreationChecker = new DEdgeCreationChecker(this.getDiagram(), this.getDDiagram(), expectedEdgeMappingType);
		this.createEdge(toolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalEdgeCreationChecker));
	}

	@Override
	protected Element getRootElement() {
		return this.rootActivity;
	}

	/**
	 * Computes the map of element types associated to their mapping that will be used for the edge creation.
	 *
	 * @return the map of element types associated to their mapping
	 */
	private static Map<String, String> getElementTypeToMapping() {
		List<String> conceptsList = Arrays.asList(new String[] {
				ACTIVITY_PARAMETER_NODE,
				ACTIVITY_FINAL_NODE,
				DECISION_NODE,
				FLOW_FINAL_NODE,
				FORK_NODE,
				JOIN_NODE,
				MERGE_NODE,
				OPAQUE_ACTION,
				EXPANSION_NODE,
				INPUT_PIN,
				OUTPUT_PIN,
				CALL_BEHAVIOR_ACTION,
				STRUCTURED_ACTIVITY_NODE,
				INITIAL_NODE,
		});
		Map<String, String> elementTypeToMapping = new HashMap<>();
		for (String concept : conceptsList) {
			elementTypeToMapping.put(concept, AD_PREFIX + concept);
		}
		return elementTypeToMapping;
	}

	@Parameters(name = "{index} create ControlFlow edge between {0} and {1}")
	public static Collection<Object[]> data() {
		Collection<Object[]> data = new ArrayList<>();
		for (String sourceName : ELEMENT_TYPE_TO_MAPPING.keySet()) {
			for (String targetName : ELEMENT_TYPE_TO_MAPPING.keySet()) {
				Object[] array = { sourceName + FIRST_ELEMENT_SUFFIX, targetName + SECOND_ELEMENT_SUFFIX, ELEMENT_TYPE_TO_MAPPING.get(sourceName), ELEMENT_TYPE_TO_MAPPING.get(targetName) };
				data.add(array);
			}
		}
		return data;
	}
}
