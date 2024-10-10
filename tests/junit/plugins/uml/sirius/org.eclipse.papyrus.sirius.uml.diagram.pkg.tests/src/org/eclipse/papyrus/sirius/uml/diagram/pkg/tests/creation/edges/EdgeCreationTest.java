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
package org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.creation.edges;

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
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.MappingTypes;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to edge creation for the Package Diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/creation/edges/Edge_CreationTest.di")
@RunWith(value = Parameterized.class)
public class EdgeCreationTest extends AbstractCreateEdgeTests {

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String MODEL = "Model"; //$NON-NLS-1$

	private static final String PACKAGE = "Package"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "EdgePackageDiagramSirius"; //$NON-NLS-1$

	private static final Map<String, String> elementTypeToMapping = Map.of(
			CONSTRAINT, MappingTypes.CONSTRAINT_NODE_TYPE,
			MODEL, MappingTypes.MODEL_NODE_TYPE,
			PACKAGE, MappingTypes.PACKAGE_NODE_TYPE);

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
		this.testEdgeCreation(CreationToolsIds.CREATE__ABSTRACTION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Abstraction.class, this.getExpectedOwner(), MappingTypes.ABSTRACTION_EDGE_TYPE);
	}

	private EObject getExpectedOwner() {
		final EObject expectedOwner;
		if (List.of(MappingTypes.CONSTRAINT_NODE_TYPE).contains(this.mappingSourceTypeName)) {
			expectedOwner = this.root;
		} else {
			expectedOwner = this.root.getMember(this.sourceName);
		}
		return expectedOwner;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createDependencyTest() {
		this.testEdgeCreation(CreationToolsIds.CREATE__DEPENDENCY__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Dependency.class, this.getExpectedOwner(), MappingTypes.DEPENDENCY_EDGE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createPackageImportTest() {
		List<String> forbiddenSourceTargetMappingList = List.of(MappingTypes.CONSTRAINT_NODE_TYPE);
		if (!forbiddenSourceTargetMappingList.contains(this.mappingSourceTypeName) && !forbiddenSourceTargetMappingList.contains(this.mappingTargetTypeName)) {
			this.testEdgeCreation(CreationToolsIds.CREATE__PACKAGE_IMPORT__TOOL, UMLPackage.eINSTANCE.getNamespace_PackageImport(), PackageImport.class, this.getExpectedOwner(), MappingTypes.PACKAGE_IMPORT_EDGE_TYPE);
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
