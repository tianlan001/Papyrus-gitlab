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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.creation.edges;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EModelElement;
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
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.CreationToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.MappingTypes;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.CommunicationPath;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Deployment;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Manifestation;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to edge creation for the Deployment Diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/creation/edges/Edge_CreationTest.di")
@RunWith(value = Parameterized.class)
public class EdgeCreationTest extends AbstractCreateEdgeTests {

	private static final String DIAGRAM_NAME = "EdgeDeploymentDiagramSirius"; //$NON-NLS-1$

	private static final List<Class<? extends EModelElement>> elementTypes = List.of(
			Artifact.class,
			Constraint.class,
			DeploymentSpecification.class,
			Device.class,
			ExecutionEnvironment.class,
			Model.class,
			Node.class,
			Package.class);

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
	public void createCommunicationPathTest() {
		List<String> allowedSourceTargetTypes = List.of(MappingTypes.getMappingType(Device.class.getSimpleName()), MappingTypes.getMappingType(ExecutionEnvironment.class.getSimpleName()), MappingTypes.getMappingType(Node.class.getSimpleName()));
		if (allowedSourceTargetTypes.contains(this.mappingSourceTypeName) && allowedSourceTargetTypes.contains(this.mappingTargetTypeName)) {
			final EObject expectedOwner = this.root;
			final EReference containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
			this.testEdgeCreation(CreationToolsIds.getToolId(CommunicationPath.class.getSimpleName()), containingFeature, CommunicationPath.class, expectedOwner, MappingTypes.getMappingType(CommunicationPath.class.getSimpleName()));
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createDependencyTest() {
		final EObject expectedOwner;
		List<String> packageSourceTypes = List.of(MappingTypes.getMappingType(Package.class.getSimpleName()), MappingTypes.getMappingType(Model.class.getSimpleName()));
		if (packageSourceTypes.contains(this.mappingSourceTypeName)) {
			// Dependencies with a Package source are contained in the Package.
			expectedOwner = this.root.getMember(this.sourceName);
		} else {
			expectedOwner = this.root;
		}
		final EReference containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
		this.testEdgeCreation(CreationToolsIds.getToolId(Dependency.class.getSimpleName()), containingFeature, Dependency.class, expectedOwner, MappingTypes.getMappingType(Dependency.class.getSimpleName()));
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createDeploymentTest() {
		List<String> allowedSourceTypes = List.of(MappingTypes.getMappingType(Artifact.class.getSimpleName()), MappingTypes.getMappingType(DeploymentSpecification.class.getSimpleName()));
		List<String> allowedTargetTypes = List.of(MappingTypes.getMappingType(Device.class.getSimpleName()), MappingTypes.getMappingType(ExecutionEnvironment.class.getSimpleName()), MappingTypes.getMappingType(Node.class.getSimpleName()));
		if (allowedSourceTypes.contains(this.mappingSourceTypeName) && allowedTargetTypes.contains(this.mappingTargetTypeName)) {
			final EObject expectedOwner = this.root.getMember(this.targetName);
			final EReference containingFeature = UMLPackage.eINSTANCE.getDeploymentTarget_Deployment();
			this.testEdgeCreation(CreationToolsIds.getToolId(Deployment.class.getSimpleName()), containingFeature, Deployment.class, expectedOwner, MappingTypes.getMappingType(Deployment.class.getSimpleName()));
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createGeneralizationTest() {
		List<String> forbiddenSourceTargetTypes = List.of(MappingTypes.getMappingType(Constraint.class.getSimpleName()), MappingTypes.getMappingType(Model.class.getSimpleName()), MappingTypes.getMappingType(Package.class.getSimpleName()));
		if (!forbiddenSourceTargetTypes.contains(this.mappingSourceTypeName) && !forbiddenSourceTargetTypes.contains(this.mappingTargetTypeName)) {
			final EObject expectedOwner = this.root.getMember(this.sourceName);
			final EReference containingFeature = UMLPackage.eINSTANCE.getClassifier_Generalization();
			this.testEdgeCreation(CreationToolsIds.getToolId(Generalization.class.getSimpleName()), containingFeature, Generalization.class, expectedOwner, MappingTypes.getMappingType(Generalization.class.getSimpleName()));
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void createManifestationTest() {
		final EObject expectedOwner;
		final EReference containingFeature;
		if (MappingTypes.getMappingType(Artifact.class.getSimpleName()).equals(this.mappingSourceTypeName)
				|| MappingTypes.getMappingType(DeploymentSpecification.class.getSimpleName()).equals(this.mappingSourceTypeName)) {
			// Manifestations with an Artifact source are contained in a specific EReference.
			expectedOwner = this.root.getMember(this.sourceName);
			containingFeature = UMLPackage.eINSTANCE.getArtifact_Manifestation();
		} else {
			if (MappingTypes.getMappingType(Package.class.getSimpleName()).equals(this.mappingSourceTypeName)
					|| MappingTypes.getMappingType(Model.class.getSimpleName()).equals(this.mappingSourceTypeName)) {
				// Manifestations with a Package source are contained in the source Package.
				expectedOwner = this.root.getMember(this.sourceName);
			} else {
				expectedOwner = this.root;
			}
			containingFeature = UMLPackage.eINSTANCE.getPackage_PackagedElement();
		}
		this.testEdgeCreation(CreationToolsIds.getToolId(Manifestation.class.getSimpleName()), containingFeature, Manifestation.class, expectedOwner, MappingTypes.getMappingType(Manifestation.class.getSimpleName()));
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
		for (Class<? extends EModelElement> sourceType : elementTypes) {
			for (Class<? extends EModelElement> targetType : elementTypes) {
				Object[] array = { sourceType.getSimpleName() + "1", targetType.getSimpleName() + "2", MappingTypes.getMappingType(sourceType.getSimpleName()), MappingTypes.getMappingType(targetType.getSimpleName()) }; //$NON-NLS-1$//$NON-NLS-2$
				data.add(array);
			}
		}
		return data;
	}

}
