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
package org.eclipse.papyrus.sirius.uml.diagram.component.tests.creation.edges;

import java.util.ArrayList;
import java.util.Collection;
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
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.component.tests.MappingTypes;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.ComponentRealization;
import org.eclipse.uml2.uml.Connector;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Substitution;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to edge creation for the Component Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/creation/edges/Edge_CreationTest.di")
@RunWith(value = Parameterized.class)
public class EdgeCreationTest extends AbstractCreateEdgeTests {

	private static final String COMPONENT = "Component"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String INTERFACE = "Interface"; //$NON-NLS-1$

	private static final String MODEL = "Model"; //$NON-NLS-1$

	private static final String PACKAGE = "Package"; //$NON-NLS-1$

	private static final String PORT = "Port"; //$NON-NLS-1$

	private static final String PROPERTY = "Property"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "EdgeComponentDiagramSirius"; //$NON-NLS-1$

	private static final Map<String, String> elementTypeToMapping = Map.of(
			// Comment, Operation, and Reception aren't in the map because they aren't used
			// in any test.
			COMPONENT, MappingTypes.COMPONENT_NODE_TYPE,
			CONSTRAINT, MappingTypes.CONSTRAINT_NODE_TYPE,
			INTERFACE, MappingTypes.INTERFACE_NODE_TYPE,
			MODEL, MappingTypes.MODEL_NODE_TYPE,
			PACKAGE, MappingTypes.PACKAGE_NODE_TYPE,
			PORT, MappingTypes.PORT_NODE_TYPE,
			PROPERTY, MappingTypes.PROPERTY_NODE_TYPE);

	private final String sourceName;

	private final String targetName;

	private final String mappingSourceTypeName;

	private final String mappingTargetTypeName;

	public EdgeCreationTest(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		this.sourceName = sourceName;
		this.targetName = targetName;
		this.mappingSourceTypeName = mappingSourceTypeName;
		this.mappingTargetTypeName = mappingTargetTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createAbstractionTest() {
		List<String> forbiddenSourceTargetTypes = List.of(MappingTypes.COMMENT_NODE_TYPE, MappingTypes.OPERATION_NODE_TYPE, MappingTypes.RECEPTION_NODE_TYPE);
		if (!forbiddenSourceTargetTypes.contains(this.mappingSourceTypeName) && !forbiddenSourceTargetTypes.contains(this.mappingTargetTypeName)) {
			final EObject expectedOwner;
			final EReference containingFeature;
			if (List.of(MappingTypes.CONSTRAINT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.PORT_NODE_TYPE, MappingTypes.PROPERTY_NODE_TYPE).contains(this.mappingSourceTypeName)) {
				expectedOwner = this.root;
				containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
			} else {
				expectedOwner = this.root.getMember(this.sourceName);
				if (MappingTypes.COMPONENT_NODE_TYPE.equals(this.mappingSourceTypeName)) {
					containingFeature = UMLPackage.eINSTANCE.getComponent_PackagedElement();
				} else {
					containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
				}
			}
			this.testEdgeCreation(CreationToolsIds.CREATE__ABSTRACTION__TOOL, containingFeature, Abstraction.class, expectedOwner, MappingTypes.ABSTRACTION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createComponentRealizationTest() {
		List<String> allowedSourceTypes = List.of(MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		if (allowedSourceTypes.contains(this.mappingSourceTypeName) && MappingTypes.COMPONENT_NODE_TYPE.equals(this.mappingTargetTypeName)) {
			// ComponentRealizations are contained in their target
			EObject expectedOwner = this.root.getMember(this.targetName);
			EReference containingFeature = UMLPackage.eINSTANCE.getComponent_Realization();
			this.testEdgeCreation(CreationToolsIds.CREATE__COMPONENT_REALIZATION__TOOL, containingFeature, ComponentRealization.class, expectedOwner, MappingTypes.COMPONENT_REALIZATION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createConnectorTest() {
		List<String> allowedSourceTargetTypes = List.of(MappingTypes.PORT_NODE_TYPE, MappingTypes.PROPERTY_NODE_TYPE);
		if (allowedSourceTargetTypes.contains(this.mappingSourceTypeName) && allowedSourceTargetTypes.contains(this.mappingTargetTypeName)) {
			// Test port and properties are defined in Component1, which contains the connector references.
			EObject expectedOwner = this.root.getMember("Component1"); //$NON-NLS-1$
			EReference containingFeature = UMLPackage.eINSTANCE.getStructuredClassifier_OwnedConnector();
			this.testEdgeCreation(CreationToolsIds.CREATE__CONNECTOR__TOOL, containingFeature, Connector.class, expectedOwner, MappingTypes.CONNECTOR_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createDependencyTest() {
		List<String> forbiddenSourceTargetTypes = List.of(MappingTypes.COMMENT_NODE_TYPE, MappingTypes.OPERATION_NODE_TYPE, MappingTypes.RECEPTION_NODE_TYPE);
		if (!forbiddenSourceTargetTypes.contains(this.mappingSourceTypeName) && !forbiddenSourceTargetTypes.contains(this.mappingTargetTypeName)) {
			final EObject expectedOwner;
			final EReference containingFeature;
			if (List.of(MappingTypes.CONSTRAINT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.PORT_NODE_TYPE, MappingTypes.PROPERTY_NODE_TYPE).contains(this.mappingSourceTypeName)) {
				expectedOwner = this.root;
				containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
			} else {
				expectedOwner = this.root.getMember(this.sourceName);
				if (MappingTypes.COMPONENT_NODE_TYPE.equals(this.mappingSourceTypeName)) {
					containingFeature = UMLPackage.eINSTANCE.getComponent_PackagedElement();
				} else {
					containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
				}
			}
			this.testEdgeCreation(CreationToolsIds.CREATE__DEPENDENCY__TOOL, containingFeature, Dependency.class, expectedOwner, MappingTypes.DEPENDENCY_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createGeneralizationTest() {
		List<String> allowedSourceTargetTypes = List.of(MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		if (allowedSourceTargetTypes.contains(this.mappingSourceTypeName) && allowedSourceTargetTypes.contains(this.mappingTargetTypeName)) {
			final EObject expectedOwner = this.root.getMember(this.sourceName);
			final EReference containingFeature = UMLPackage.eINSTANCE.getClassifier_Generalization();
			this.testEdgeCreation(CreationToolsIds.CREATE__GENERALIZATION__TOOL, containingFeature, Generalization.class, expectedOwner, MappingTypes.GENERALIZATION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createInterfaceRealizationTest() {
		if (MappingTypes.COMPONENT_NODE_TYPE.equals(this.mappingSourceTypeName) && MappingTypes.INTERFACE_NODE_TYPE.equals(this.mappingTargetTypeName)) {
			final EObject expectedOwner = this.root.getMember(this.sourceName);
			final EReference containingFeature = UMLPackage.eINSTANCE.getBehavioredClassifier_InterfaceRealization();
			this.testEdgeCreation(CreationToolsIds.CREATE__INTERFACE_REALIZATION__TOOL, containingFeature, InterfaceRealization.class, expectedOwner, MappingTypes.INTERFACE_REALIZATION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createManifestationTest() {
		List<String> forbiddenSourceTypes = List.of(MappingTypes.COMMENT_NODE_TYPE, MappingTypes.OPERATION_NODE_TYPE, MappingTypes.RECEPTION_NODE_TYPE);
		List<String> forbiddenTargetTypes = List.of(MappingTypes.COMMENT_NODE_TYPE, MappingTypes.OPERATION_NODE_TYPE, MappingTypes.PORT_NODE_TYPE, MappingTypes.PROPERTY_NODE_TYPE, MappingTypes.RECEPTION_NODE_TYPE);
		if (!forbiddenSourceTypes.contains(this.mappingSourceTypeName) && !forbiddenTargetTypes.contains(this.mappingTargetTypeName)) {
			final EObject expectedOwner;
			final EReference containingFeature;
			if (List.of(MappingTypes.CONSTRAINT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.PORT_NODE_TYPE, MappingTypes.PROPERTY_NODE_TYPE).contains(this.mappingSourceTypeName)) {
				expectedOwner = this.root;
				containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
			} else {
				expectedOwner = this.root.getMember(this.sourceName);
				if (MappingTypes.COMPONENT_NODE_TYPE.equals(this.mappingSourceTypeName)) {
					containingFeature = UMLPackage.eINSTANCE.getComponent_PackagedElement();
				} else {
					containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
				}
			}
			this.testEdgeCreation(CreationToolsIds.CREATE__MANIFESTATION__TOOL, containingFeature, Manifestation.class, expectedOwner, MappingTypes.MANIFESTATION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createSubstitutionTest() {
		List<String> allowedSourceTargetTypes = List.of(MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE);
		if (allowedSourceTargetTypes.contains(this.mappingSourceTypeName) && allowedSourceTargetTypes.contains(this.mappingTargetTypeName)) {
			final EObject expectedOwner = this.root.getMember(this.sourceName);
			final EReference containingFeature = UMLPackage.eINSTANCE.getClassifier_Substitution();
			this.testEdgeCreation(CreationToolsIds.CREATE__SUBSTITUTION__TOOL, containingFeature, Substitution.class, expectedOwner, MappingTypes.SUBSTITUTION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createUsageTest() {
		List<String> forbiddenSourceTargetTypes = List.of(MappingTypes.COMMENT_NODE_TYPE, MappingTypes.OPERATION_NODE_TYPE, MappingTypes.RECEPTION_NODE_TYPE);
		if (!forbiddenSourceTargetTypes.contains(this.mappingSourceTypeName) && !forbiddenSourceTargetTypes.contains(this.mappingTargetTypeName)) {
			final EObject expectedOwner;
			final EReference containingFeature;
			if (List.of(MappingTypes.CONSTRAINT_NODE_TYPE, MappingTypes.INTERFACE_NODE_TYPE, MappingTypes.PORT_NODE_TYPE, MappingTypes.PROPERTY_NODE_TYPE).contains(this.mappingSourceTypeName)) {
				expectedOwner = this.root;
				containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
			} else {
				expectedOwner = this.root.getMember(this.sourceName);
				if (MappingTypes.COMPONENT_NODE_TYPE.equals(this.mappingSourceTypeName)) {
					containingFeature = UMLPackage.eINSTANCE.getComponent_PackagedElement();
				} else {
					containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
				}
			}
			this.testEdgeCreation(CreationToolsIds.CREATE__USAGE__TOOL, containingFeature, Usage.class, expectedOwner, MappingTypes.USAGE_EDGE_TYPE);
		}
	}

	private void testEdgeCreation(String toolId, EReference containmentFeature, Class<? extends Element> expectedType, EObject expectedOwner, String expectedEdgeMappingType) {
		Package rootSemanticContainer = this.root;
		// initialize semantic context for test
		this.setSemanticSource(rootSemanticContainer.getMember(this.sourceName));
		this.setSemanticTarget(rootSemanticContainer.getMember(this.targetName));
		// initialize graphical context for test
		this.setEdgeSource(this.getElementsFromDiagramBySemanticName(this.sourceName, this.mappingSourceTypeName).get(0));
		this.setEdgeTarget(this.getElementsFromDiagramBySemanticName(this.targetName, this.mappingTargetTypeName).get(0));
		final ISemanticRepresentationElementCreationChecker semanticChecker = new SemanticEdgeCreationChecker(expectedOwner, containmentFeature, expectedType);
		final IGraphicalRepresentationElementCreationChecker graphicalEdgeCreationChecker = new DEdgeCreationChecker(this.getDiagram(), this.getDDiagram(), expectedEdgeMappingType);
		this.createEdge(toolId, new SemanticAndGraphicalCreationChecker(semanticChecker, graphicalEdgeCreationChecker));
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
