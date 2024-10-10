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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.creation.edges;

import java.util.ArrayList;
import java.util.Collection;
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
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.MappingTypes;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InformationFlow;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the creation of Composite Structure Diagram edges.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/creation/edges/Edge_CreationTest.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class CSDEdgeCreationTest extends AbstractCreateEdgeTests {

	private static final String ACTIVITY = "Activity"; //$NON-NLS-1$

	private static final String CLASS = "Class"; //$NON-NLS-1$

	private static final String COLLABORATION = "Collaboration"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String FUNCTIONBEHAVIOR = "FunctionBehavior"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction"; //$NON-NLS-1$

	private static final String INFORMATIONITEM = "InformationItem"; //$NON-NLS-1$

	private static final String OPAQUEBEHAVIOR = "OpaqueBehavior"; //$NON-NLS-1$

	private static final String STATEMACHINE = "StateMachine"; //$NON-NLS-1$

	private static final String PROTOCOLSTATEMACHINE = "ProtocolStateMachine"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "EdgeCompositeStructureDiagramSirius"; //$NON-NLS-1$

	private static final Map<String, String> elementTypeToMapping = Map.of(ACTIVITY, MappingTypes.ACTIVITY_NODE_TYPE, CLASS, MappingTypes.CLASS_NODE_TYPE, COLLABORATION, MappingTypes.COLLABORATION_NODE_TYPE, CONSTRAINT, MappingTypes.CONSTRAINT_NODE_TYPE,
			FUNCTIONBEHAVIOR, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE, INFORMATIONITEM, MappingTypes.INFORMATION_ITEM_NODE_TYPE, OPAQUEBEHAVIOR, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE, STATEMACHINE,
			MappingTypes.STATE_MACHINE_NODE_TYPE, PROTOCOLSTATEMACHINE, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE);

	private final String sourceName;

	private final String targetName;

	private final String mappingSourceTypeName;

	private final String mappingTargetTypeName;


	/**
	 * Default constructor.
	 *
	 */
	public CSDEdgeCreationTest(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		this.sourceName = sourceName;
		this.targetName = targetName;
		this.mappingSourceTypeName = mappingSourceTypeName;
		this.mappingTargetTypeName = mappingTargetTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createGeneralizationTest() {
		if (this.mappingSourceTypeName != MappingTypes.CONSTRAINT_NODE_TYPE && this.mappingTargetTypeName != MappingTypes.CONSTRAINT_NODE_TYPE) {
			testEdgeCreation(CreationToolsIds.CREATE__GENERALIZATION__TOOL, UMLPackage.eINSTANCE.getClassifier_Generalization(), Generalization.class,
					this.root.getMember(this.sourceName), MappingTypes.GENERALIZATION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createRealizationTest() {
		testEdgeCreation(CreationToolsIds.CREATE__REALIZATION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Realization.class,
				this.root, MappingTypes.REALIZATION_EDGE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createSubstitutionTest() {
		if (this.mappingSourceTypeName != MappingTypes.CONSTRAINT_NODE_TYPE && this.mappingTargetTypeName != MappingTypes.CONSTRAINT_NODE_TYPE) {
			testEdgeCreation(CreationToolsIds.CREATE__SUBSTITUTION__TOOL, UMLPackage.eINSTANCE.getClassifier_Substitution(), Substitution.class,
					this.root.getMember(this.sourceName), MappingTypes.SUBSTITUTION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createAbstractionTest() {
		testEdgeCreation(CreationToolsIds.CREATE__ABSTRACTION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Abstraction.class,
				this.root, MappingTypes.ABSTRACTION_EDGE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createUsageTest() {
		testEdgeCreation(CreationToolsIds.CREATE__USAGE__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Usage.class,
				this.root, MappingTypes.USAGE_EDGE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createDependencyTest() {
		testEdgeCreation(CreationToolsIds.CREATE__DEPENDENCY__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Dependency.class,
				this.root, MappingTypes.DEPENDENCY_EDGE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createInformationFlowTest() {
		testEdgeCreation(CreationToolsIds.CREATE__INFORMATION_FLOW__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), InformationFlow.class,
				this.root, MappingTypes.INFORMATION_FLOW_EDGE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createManifestationTest() {
		testEdgeCreation(CreationToolsIds.CREATE__MANIFESTATION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Manifestation.class,
				this.root, MappingTypes.MANIFESTATION_EDGE_TYPE);
	}

	private void testEdgeCreation(String toolId, EReference containmentFeature, Class<? extends Element> expectedType, EObject expectedOwner, String expectedEdgeMappingType) {
		this.setSemanticSource(this.root.getMember(sourceName));
		this.setSemanticTarget(this.root.getMember(targetName));
		this.setEdgeSource(getNodeFromDiagram(sourceName, mappingSourceTypeName)); // $NON-NLS-1$
		this.setEdgeTarget(getNodeFromDiagram(targetName, mappingTargetTypeName)); // $NON-NLS-1$
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticEdgeCreationChecker(expectedOwner, containmentFeature, expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalEdgeCreationChecker = new DEdgeCreationChecker(getDiagram(), getDDiagram(), expectedEdgeMappingType);
		createEdge(toolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalEdgeCreationChecker));
	}

	@Parameters(name = "{index} create edge between {0} and {1}")
	public static Collection<Object[]> data() {
		Collection<Object[]> data = new ArrayList<>();
		for (String sourceName : elementTypeToMapping.keySet()) {
			for (String targetName : elementTypeToMapping.keySet()) {
				Object[] array = { sourceName + "1", targetName + "2", elementTypeToMapping.get(sourceName), elementTypeToMapping.get(targetName) }; //$NON-NLS-1$//$NON-NLS-2$
				data.add(array);
			}
		}
		return data;
	}

}
