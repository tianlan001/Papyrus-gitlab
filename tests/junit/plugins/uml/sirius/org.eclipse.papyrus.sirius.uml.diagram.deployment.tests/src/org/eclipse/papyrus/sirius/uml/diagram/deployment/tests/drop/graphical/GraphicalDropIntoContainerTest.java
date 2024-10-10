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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.drop.graphical;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractGraphicalDropNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.GraphicalDropToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.MappingTypes;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to graphical drop in the Deployment diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
@RunWith(Parameterized.class)
public class GraphicalDropIntoContainerTest extends AbstractGraphicalDropNodeTests {

	/**
	 * Suffix for elements to drop that are located on the root of the diagram.
	 */
	private static final String ELEMENT_ON_DIAGRAM_SUFFIX = "1"; //$NON-NLS-1$

	/**
	 * The separator used to identify elements inside other elements (e.g. {@code NodeInNode}).
	 */
	private static final String ELEMENT_IN_ELEMENT_SEPARATOR = "In"; //$NON-NLS-1$

	/**
	 * The suffix used to identify elements used to drop other elements.
	 */
	private static final String NEW_CONTAINER_SUFFIX = "ToDropElements"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "GraphicalDropDeploymentDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String elementToDropMappingType;

	private final String newContainerName;

	private final String newContainerMappingType;

	private final String newContainerCompartmentType;

	private final String graphicalToolId;

	private final String newContainmentFeatureName;

	/**
	 * Initializes the test with the provided parameters.
	 * <p>
	 * If the provided {@code initalContainerType} is {@code null} the test assumes the element to drop
	 * is located on the diagram root.
	 * </p>
	 *
	 * @param elementToDropType
	 *            the type of the element to drop
	 * @param initialContainerType
	 *            the type of the container initially containing the element to drop
	 * @param newContainerType
	 *            the type of the new container for the element
	 * @param newContainmentFeature
	 *            the feature containing the element after the drop
	 */
	public GraphicalDropIntoContainerTest(Class<? extends Element> elementToDropType, Class<? extends Element> initialContainerType, Class<? extends Element> newContainerType, EReference newContainmentFeature) {
		assertNotNull(elementToDropType);
		assertNotNull(newContainerType);
		assertNotNull(newContainmentFeature);
		if (initialContainerType == null) {
			this.elementToDropName = elementToDropType.getSimpleName() + ELEMENT_ON_DIAGRAM_SUFFIX;
		} else {
			this.elementToDropName = elementToDropType.getSimpleName() + ELEMENT_IN_ELEMENT_SEPARATOR + initialContainerType.getSimpleName();
		}
		this.newContainerName = newContainerType.getSimpleName() + NEW_CONTAINER_SUFFIX;
		this.newContainerMappingType = MappingTypes.getMappingType(newContainerType.getSimpleName());
		this.newContainerCompartmentType = MappingTypes.getCompartmentMappingType(newContainerType.getSimpleName());
		this.graphicalToolId = GraphicalDropToolsIds.getToolId(elementToDropType.getSimpleName());
		this.elementToDropMappingType = MappingTypes.getMappingType(elementToDropType.getSimpleName());
		this.newContainmentFeatureName = newContainmentFeature.getName();
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropElementInContainer() {
		this.dropElementIntoContainer(this.elementToDropName, this.newContainerName, this.newContainerMappingType, this.newContainerCompartmentType, this.graphicalToolId, this.elementToDropMappingType, null, this.newContainmentFeatureName);
	}

	@Parameters(name = "{index} drop graphical element {0} from {1} to {2}")
	public static Collection<Object[]> data() {
		Collection<Object[]> data = new ArrayList<>();
		for (ContainmentRule sourceContainmentRule : containmentRules) {
			for (ContainmentRule targetContainmentRule : containmentRules) {
				if (sourceContainmentRule.elementType().equals(targetContainmentRule.elementType())) {
					Object[] dropFromContainerData = { sourceContainmentRule.elementType(), sourceContainmentRule.containerType(), targetContainmentRule.containerType(), targetContainmentRule.containmentReference() };
					data.add(dropFromContainerData);
				}
			}
			Object[] dropFromDiagramData = { sourceContainmentRule.elementType(), null, sourceContainmentRule.containerType(), sourceContainmentRule.containmentReference() };
			data.add(dropFromDiagramData);
		}
		return data;
	}

	private static final Collection<ContainmentRule> containmentRules = List.of(
			new ContainmentRule(Artifact.class, Artifact.class, UMLPackage.eINSTANCE.getArtifact_NestedArtifact()),
			new ContainmentRule(Artifact.class, ExecutionEnvironment.class, UMLPackage.eINSTANCE.getClass_NestedClassifier()),
			new ContainmentRule(Artifact.class, Model.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(Artifact.class, Node.class, UMLPackage.eINSTANCE.getClass_NestedClassifier()),
			new ContainmentRule(Artifact.class, Package.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(Constraint.class, Model.class, UMLPackage.eINSTANCE.getNamespace_OwnedRule()),
			new ContainmentRule(Constraint.class, Package.class, UMLPackage.eINSTANCE.getNamespace_OwnedRule()),
			new ContainmentRule(DeploymentSpecification.class, Artifact.class, UMLPackage.eINSTANCE.getArtifact_NestedArtifact()),
			new ContainmentRule(DeploymentSpecification.class, Device.class, UMLPackage.eINSTANCE.getClass_NestedClassifier()),
			new ContainmentRule(DeploymentSpecification.class, ExecutionEnvironment.class, UMLPackage.eINSTANCE.getClass_NestedClassifier()),
			new ContainmentRule(DeploymentSpecification.class, Model.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(DeploymentSpecification.class, Node.class, UMLPackage.eINSTANCE.getClass_NestedClassifier()),
			new ContainmentRule(DeploymentSpecification.class, Package.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(Device.class, Device.class, UMLPackage.eINSTANCE.getNode_NestedNode()),
			new ContainmentRule(Device.class, Model.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(Device.class, Node.class, UMLPackage.eINSTANCE.getNode_NestedNode()),
			new ContainmentRule(Device.class, Package.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(ExecutionEnvironment.class, Device.class, UMLPackage.eINSTANCE.getNode_NestedNode()),
			new ContainmentRule(ExecutionEnvironment.class, ExecutionEnvironment.class, UMLPackage.eINSTANCE.getNode_NestedNode()),
			new ContainmentRule(ExecutionEnvironment.class, Model.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(ExecutionEnvironment.class, Node.class, UMLPackage.eINSTANCE.getNode_NestedNode()),
			new ContainmentRule(ExecutionEnvironment.class, Package.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(Model.class, Model.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(Model.class, Package.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(Node.class, Device.class, UMLPackage.eINSTANCE.getNode_NestedNode()),
			new ContainmentRule(Node.class, Model.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(Node.class, Node.class, UMLPackage.eINSTANCE.getNode_NestedNode()),
			new ContainmentRule(Node.class, Package.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(Package.class, Model.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()),
			new ContainmentRule(Package.class, Package.class, UMLPackage.eINSTANCE.getPackage_PackagedElement()));

	/**
	 * Defines a containment rule for the Deployment diagram.
	 * <p>
	 * A containment rule is specified by an element, a container, and the containing feature used to store the element in the container.
	 * </p>
	 */
	final record ContainmentRule(Class<? extends EModelElement> elementType, Class<? extends EModelElement> containerType, EReference containmentReference) {
	}
}
