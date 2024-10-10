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
package org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.drop.semantic;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticDropSubNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.SemanticDropToolsIds;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Namespace;
import org.eclipse.uml2.uml.Node;
import org.eclipse.uml2.uml.Package;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to semantic drop into containers of the Deployment diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/drop/subNodes/subNodeSemanticDrop.di")
@RunWith(Parameterized.class)
public class SemanticDropIntoContainerTest extends AbstractSemanticDropSubNodeTests {

	private static final String ELEMENT_NAME_SUFFIX = "1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SubNodeSemanticDropDeploymentDiagramSirius"; //$NON-NLS-1$

	private String elementName;

	private String elementMappingType;

	private String containerName;

	private String containerMappingType;

	private String containerCompartmentType;

	private String semanticDropToolId;

	public SemanticDropIntoContainerTest(Class<? extends Element> elementType, Class<? extends Element> containerType) {
		this.elementName = elementType.getSimpleName() + ELEMENT_NAME_SUFFIX;
		this.elementMappingType = MappingTypes.getMappingType(elementType.getSimpleName());
		this.containerName = containerType.getSimpleName() + ELEMENT_NAME_SUFFIX;
		this.containerMappingType = MappingTypes.getMappingType(containerType.getSimpleName());
		this.containerCompartmentType = MappingTypes.getCompartmentMappingType(containerType.getSimpleName());
		this.semanticDropToolId = SemanticDropToolsIds.getToolId(elementType.getSimpleName());
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropElementIntoContainer() {
		// Get semantic element representing the graphicalOwner
		Element rootElement = this.getRootElement();
		assertTrue(rootElement instanceof Namespace);
		Namespace semanticTargetOfGraphicalOwner = (Namespace) ((Namespace) rootElement).getMember(this.containerName);
		// Get the semantic element to drop
		final Element elementToDrop;
		if (MappingTypes.getMappingType(Comment.class.getSimpleName()).equals(this.elementMappingType)) {
			elementToDrop = semanticTargetOfGraphicalOwner.getOwnedComments().get(0);
		} else {
			elementToDrop = semanticTargetOfGraphicalOwner.getMember(this.elementName);
		}
		this.dropElementToContainer(semanticTargetOfGraphicalOwner, elementToDrop, this.containerMappingType, this.containerCompartmentType, this.semanticDropToolId, this.elementMappingType);
	}

	@Parameters(name = "{index} drop semantic element {0} on {1}")
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
				{ Comment.class, Model.class },
				{ Constraint.class, Model.class },
				{ Artifact.class, Model.class },
				{ Device.class, Model.class },
				{ DeploymentSpecification.class, Model.class },
				{ ExecutionEnvironment.class, Model.class },
				{ Model.class, Model.class },
				{ Node.class, Model.class },
				{ Package.class, Model.class },
				{ Artifact.class, Node.class },
				{ DeploymentSpecification.class, Node.class },
				{ Device.class, Node.class },
				{ ExecutionEnvironment.class, Node.class },
				{ Node.class, Node.class },
				{ Comment.class, Package.class },
				{ Constraint.class, Package.class },
				{ Artifact.class, Package.class },
				{ Device.class, Package.class },
				{ DeploymentSpecification.class, Package.class },
				{ ExecutionEnvironment.class, Package.class },
				{ Model.class, Package.class },
				{ Node.class, Package.class },
				{ Package.class, Package.class }
		});
	}
}
