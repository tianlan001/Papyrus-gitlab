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

import java.util.Arrays;
import java.util.Collection;

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
public class GraphicalDropIntoDiagramTest extends AbstractGraphicalDropNodeTests {

	/**
	 * The separator used to identify elements inside other elements (e.g. {@code NodeInNode}).
	 */
	private static final String ELEMENT_IN_ELEMENT_SEPARATOR = "In"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "GraphicalDropDeploymentDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String elementToDropMappingType;

	private final String graphicalToolId;

	private final String newContainmentFeatureName;

	/**
	 * Initializes the test with the provided parameters.
	 * <p>
	 * If the provided {@code newContainmentFeature} is {@code null} the
	 * test assumes that the containment feature is the same as the one initially containing the element.
	 * </p>
	 *
	 * @param elementToDropType
	 *            the type of the element to drop
	 * @param initialContainerType
	 *            the type of the container initially containing the element to drop
	 */
	public GraphicalDropIntoDiagramTest(Class<? extends Element> elementToDropType, Class<? extends Element> initialContainerType) {
		assertNotNull(elementToDropType);
		assertNotNull(initialContainerType);
		this.elementToDropName = elementToDropType.getSimpleName() + ELEMENT_IN_ELEMENT_SEPARATOR + initialContainerType.getSimpleName();
		this.graphicalToolId = GraphicalDropToolsIds.getToolId(elementToDropType.getSimpleName());
		this.elementToDropMappingType = MappingTypes.getMappingType(elementToDropType.getSimpleName());
		if (MappingTypes.getMappingType(Constraint.class.getSimpleName()).equals(this.elementToDropMappingType)) {
			this.newContainmentFeatureName = UMLPackage.eINSTANCE.getNamespace_OwnedRule().getName();
		} else {
			this.newContainmentFeatureName = UMLPackage.eINSTANCE.getPackage_PackagedElement().getName();
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropElementIntoDiagram() {
		this.dropElementToDiagram(this.elementToDropName, this.graphicalToolId, this.elementToDropMappingType, this.newContainmentFeatureName);
	}

	@Parameters(name = "{index} drop graphical element {0} from {1} to Diagram")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ Artifact.class, Artifact.class },
				{ DeploymentSpecification.class, Artifact.class },
				{ DeploymentSpecification.class, Device.class },
				{ Device.class, Device.class },
				{ ExecutionEnvironment.class, Device.class },
				{ Node.class, Device.class },
				{ Artifact.class, ExecutionEnvironment.class },
				{ DeploymentSpecification.class, ExecutionEnvironment.class },
				{ ExecutionEnvironment.class, ExecutionEnvironment.class },
				{ Artifact.class, Model.class },
				{ Constraint.class, Model.class },
				{ DeploymentSpecification.class, Model.class },
				{ Device.class, Model.class },
				{ ExecutionEnvironment.class, Model.class },
				{ Model.class, Model.class },
				{ Node.class, Model.class },
				{ Package.class, Model.class },
				{ Artifact.class, Node.class },
				{ DeploymentSpecification.class, Node.class },
				{ Device.class, Node.class },
				{ ExecutionEnvironment.class, Node.class },
				{ Node.class, Node.class },
				{ Artifact.class, Package.class },
				{ Constraint.class, Package.class },
				{ DeploymentSpecification.class, Package.class },
				{ Device.class, Package.class },
				{ ExecutionEnvironment.class, Package.class },
				{ Model.class, Package.class },
				{ Node.class, Package.class },
				{ Package.class, Package.class }
		});
	}
}
