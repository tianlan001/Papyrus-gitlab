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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.delete.semantic.nodes;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all the tests related to sub-node deletion on the root of a Deployment diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/deletion/nodes/SubNodeDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class SubNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {

	private static final String ELEMENT_NAME_SUFFIX = "1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SubNodeDeleteDeploymentDiagramSirius"; //$NON-NLS-1$

	private final String subNodeName;

	private final String subNodeMappingType;

	private final String containerName;

	private final String containerMappingType;

	private final String compartmentMappingType;

	public SubNodeDeleteSemanticTest(Class<? extends Element> subNodeType, Class<? extends Element> containerNodeType) {
		this.subNodeName = subNodeType.getSimpleName() + ELEMENT_NAME_SUFFIX;
		this.subNodeMappingType = MappingTypes.getMappingType(subNodeType.getSimpleName());
		this.containerName = containerNodeType.getSimpleName() + ELEMENT_NAME_SUFFIX;
		this.containerMappingType = MappingTypes.getMappingType(containerNodeType.getSimpleName());
		this.compartmentMappingType = MappingTypes.getCompartmentMappingType(containerNodeType.getSimpleName());
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteSubDiagramElementTest() {
		DDiagramElement compartment = this.getContainer();
		this.deleteNode(this.subNodeName, this.subNodeMappingType, compartment, false);
	}

	private DDiagramElement getContainer() {
		DDiagramElement result;
		DNodeContainer container = (DNodeContainer) this.getNodeFromDiagram(this.containerName, this.containerMappingType);
		if (this.compartmentMappingType != null) {
			result = container.getOwnedDiagramElements().stream().filter(element -> this.compartmentMappingType.equals(element.getDiagramElementMapping().getName())).findFirst().orElse(null);
		} else {
			result = container.getOwnedDiagramElements().get(0);
		}
		return result;
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ Artifact.class, Artifact.class },
				{ Artifact.class, ExecutionEnvironment.class },
				{ Artifact.class, Model.class },
				{ Artifact.class, Node.class },
				{ Artifact.class, Package.class },
				{ Constraint.class, Model.class },
				{ Constraint.class, Package.class },
				{ DeploymentSpecification.class, Artifact.class },
				{ DeploymentSpecification.class, Device.class },
				{ DeploymentSpecification.class, ExecutionEnvironment.class },
				{ DeploymentSpecification.class, Model.class },
				{ DeploymentSpecification.class, Node.class },
				{ DeploymentSpecification.class, Package.class },
				{ Device.class, Device.class },
				{ Device.class, Model.class },
				{ Device.class, Node.class },
				{ Device.class, Package.class },
				{ ExecutionEnvironment.class, Device.class },
				{ ExecutionEnvironment.class, ExecutionEnvironment.class },
				{ ExecutionEnvironment.class, Model.class },
				{ ExecutionEnvironment.class, Node.class },
				{ ExecutionEnvironment.class, Package.class },
				{ Model.class, Model.class },
				{ Model.class, Package.class },
				{ Node.class, Device.class },
				{ Node.class, Model.class },
				{ Node.class, Node.class },
				{ Node.class, Package.class },
				{ Package.class, Model.class },
				{ Package.class, Package.class }
		});
	}
}
