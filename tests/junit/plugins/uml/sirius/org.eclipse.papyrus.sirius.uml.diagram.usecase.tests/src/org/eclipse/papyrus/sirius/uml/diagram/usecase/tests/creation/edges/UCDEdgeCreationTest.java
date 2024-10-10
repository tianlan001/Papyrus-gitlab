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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.creation.edges;

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
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.MappingTypes;
import org.eclipse.uml2.uml.Abstraction;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Extend;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Include;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.PackageImport;
import org.eclipse.uml2.uml.PackageMerge;
import org.eclipse.uml2.uml.Realization;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Usage;
import org.eclipse.uml2.uml.UseCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the creation of Use Case Diagram edges.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/creation/edges/Edge_CreationTest.di") // the resource to import for the test in the workspace
@RunWith(value = Parameterized.class)
public class UCDEdgeCreationTest extends AbstractCreateEdgeTests {

	private static final Collection<Object[]> data = new ArrayList<>();
	private static final String ACTIVITY = "Activity"; //$NON-NLS-1$

	private static final String ACTOR = "Actor"; //$NON-NLS-1$

	private static final String CLASS = "Class"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String COMPONENT = "Component"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction"; //$NON-NLS-1$

	private static final String PACKAGE = "Package"; //$NON-NLS-1$

	private static final String STATEMACHINE = "StateMachine"; //$NON-NLS-1$

	private static final String USECASE = "UseCase"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "Edge_UseCaseDiagramSirius"; //$NON-NLS-1$

	private static final Map<String, String> elementTypeToMapping = Map.of(ACTIVITY, MappingTypes.ACTIVITY_NODE_TYPE, ACTOR, MappingTypes.ACTOR_NODE_TYPE, CLASS, MappingTypes.CLASS_NODE_TYPE, COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, CONSTRAINT,
			MappingTypes.CONSTRAINT_NODE_TYPE, INTERACTION,
			MappingTypes.INTERACTION_NODE_TYPE, STATEMACHINE, MappingTypes.STATE_MACHINE_NODE_TYPE, PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, USECASE, MappingTypes.USE_CASE_NODE_TYPE);


	private final String sourceName;

	private final String targetName;

	private final String mappingSourceTypeName;

	private final String mappingTargetTypeName;


	/**
	 * Default constructor.
	 *
	 */
	public UCDEdgeCreationTest(String sourceName, String targetName, String mappingSourceTypeName, String mappingTargetTypeName) {
		this.sourceName = sourceName;
		this.targetName = targetName;
		this.mappingSourceTypeName = mappingSourceTypeName;
		this.mappingTargetTypeName = mappingTargetTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createAbstractionTest() {
		if (this.root.getMember(this.sourceName) instanceof Package) {
			testEdgeCreation(CreationToolsIds.CREATE__ABSTRACTION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Abstraction.class,
					this.root.getMember(this.sourceName), MappingTypes.ABSTRACTION_EDGE_TYPE);
		} else if (this.root.getMember(this.sourceName) instanceof Component) {
			testEdgeCreation(CreationToolsIds.CREATE__ABSTRACTION__TOOL, UMLPackage.eINSTANCE.getComponent_PackagedElement(), Abstraction.class,
					this.root.getMember(this.sourceName), MappingTypes.ABSTRACTION_EDGE_TYPE);
		} else {
			testEdgeCreation(CreationToolsIds.CREATE__ABSTRACTION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Abstraction.class,
					this.root, MappingTypes.ABSTRACTION_EDGE_TYPE);
		}

	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createAssociationTest() {
		if (!(this.root.getMember(this.sourceName) instanceof Package) && !(this.root.getMember(this.targetName) instanceof Package)
				&& !(this.root.getMember(this.sourceName) instanceof Constraint) && !(this.root.getMember(this.targetName) instanceof Constraint)) {
			testEdgeCreation(CreationToolsIds.CREATE__ASSOCIATION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Association.class,
					this.root, MappingTypes.ASSOCIATION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createDependencyTest() {
		if (this.root.getMember(this.sourceName) instanceof Package) {
			testEdgeCreation(CreationToolsIds.CREATE__DEPENDENCY__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Dependency.class,
					this.root.getMember(this.sourceName), MappingTypes.DEPENDENCY_EDGE_TYPE);
		} else if (this.root.getMember(this.sourceName) instanceof Component) {
			testEdgeCreation(CreationToolsIds.CREATE__DEPENDENCY__TOOL, UMLPackage.eINSTANCE.getComponent_PackagedElement(), Dependency.class,
					this.root.getMember(this.sourceName), MappingTypes.DEPENDENCY_EDGE_TYPE);
		} else {
			testEdgeCreation(CreationToolsIds.CREATE__DEPENDENCY__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Dependency.class,
					this.root, MappingTypes.DEPENDENCY_EDGE_TYPE);

		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createExtendTest() {
		if (this.root.getMember(this.sourceName) instanceof UseCase && this.root.getMember(this.targetName) instanceof UseCase) {
			testEdgeCreation(CreationToolsIds.CREATE__EXTEND__TOOL, UMLPackage.eINSTANCE.getUseCase_Extend(), Extend.class,
					this.root.getMember(this.sourceName), MappingTypes.EXTEND_EDGE_TYPE);
		}
	}


	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createGeneralizationTest() {
		if (!(this.root.getMember(this.sourceName) instanceof Package) && !(this.root.getMember(this.targetName) instanceof Package)
				&& !(this.root.getMember(this.sourceName) instanceof Constraint) && !(this.root.getMember(this.targetName) instanceof Constraint)) {
			testEdgeCreation(CreationToolsIds.CREATE__GENERALIZATION__TOOL, UMLPackage.eINSTANCE.getClassifier_Generalization(), Generalization.class,
					this.root.getMember(this.sourceName), MappingTypes.GENERALIZATION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createIncludeTest() {
		if (this.root.getMember(this.sourceName) instanceof UseCase && this.root.getMember(this.targetName) instanceof UseCase) {
			testEdgeCreation(CreationToolsIds.CREATE__INCLUDE__TOOL, UMLPackage.eINSTANCE.getUseCase_Include(), Include.class,
					this.root.getMember(this.sourceName), MappingTypes.INCLUDE_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createPackageImportTest() {
		if (this.root.getMember(this.sourceName) instanceof Package && this.root.getMember(this.targetName) instanceof Package) {
			testEdgeCreation(CreationToolsIds.CREATE__PACKAGE_IMPORT__TOOL, UMLPackage.eINSTANCE.getNamespace_PackageImport(), PackageImport.class,
					this.root.getMember(this.sourceName), MappingTypes.PACKAGE_IMPORT_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createPackageMergeTest() {
		if (this.root.getMember(this.sourceName) instanceof Package && this.root.getMember(this.targetName) instanceof Package) {
			testEdgeCreation(CreationToolsIds.CREATE__PACKAGE_MERGE__TOOL, UMLPackage.eINSTANCE.getPackage_PackageMerge(), PackageMerge.class,
					this.root.getMember(this.sourceName), MappingTypes.PACKAGE_MERGE_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createRealizationTest() {
		if (this.root.getMember(this.sourceName) instanceof Package) {
			testEdgeCreation(CreationToolsIds.CREATE__REALIZATION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Realization.class,
					this.root.getMember(this.sourceName), MappingTypes.REALIZATION_EDGE_TYPE);
		} else if (this.root.getMember(this.sourceName) instanceof Component) {
			testEdgeCreation(CreationToolsIds.CREATE__REALIZATION__TOOL, UMLPackage.eINSTANCE.getComponent_PackagedElement(), Realization.class,
					this.root.getMember(this.sourceName), MappingTypes.REALIZATION_EDGE_TYPE);
		} else {
			testEdgeCreation(CreationToolsIds.CREATE__REALIZATION__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Realization.class,
					this.root, MappingTypes.REALIZATION_EDGE_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createUsageTest() {
		if (this.root.getMember(this.sourceName) instanceof Package) {
			testEdgeCreation(CreationToolsIds.CREATE__USAGE__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Usage.class,
					this.root.getMember(this.sourceName), MappingTypes.USAGE_EDGE_TYPE);
		} else if (this.root.getMember(this.sourceName) instanceof Component) {
			testEdgeCreation(CreationToolsIds.CREATE__USAGE__TOOL, UMLPackage.eINSTANCE.getComponent_PackagedElement(), Usage.class,
					this.root.getMember(this.sourceName), MappingTypes.USAGE_EDGE_TYPE);
		} else {
			testEdgeCreation(CreationToolsIds.CREATE__USAGE__TOOL, UMLPackage.eINSTANCE.getPackage_PackagedElement(), Usage.class,
					this.root, MappingTypes.USAGE_EDGE_TYPE);
		}
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
		if (data.isEmpty()) {
			for (String sourceName : elementTypeToMapping.keySet()) {
				for (String targetName : elementTypeToMapping.keySet()) {
					Object[] array = { sourceName + "1", targetName + "2", elementTypeToMapping.get(sourceName), elementTypeToMapping.get(targetName) }; //$NON-NLS-1$//$NON-NLS-2$
					data.add(array);
				}
			}
		}
		return data;
	}

}
