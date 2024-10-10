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

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticTopNodeDropTests;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeContainerCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.DNodeCreationChecker;
import org.eclipse.papyrus.sirius.junit.utils.diagram.creation.graphical.checker.IGraphicalRepresentationElementCreationChecker;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.deployment.tests.SemanticDropToolsIds;
import org.eclipse.sirius.diagram.DSemanticDiagram;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Node;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all tests related to semantic drop into the root of the Deployment diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/drop/topNodes/topNodeSemanticDrop.di")
@RunWith(value = Parameterized.class)
public class SemanticDropIntoDiagramTest extends AbstractSemanticTopNodeDropTests {

	private static final String ELEMENT_NAME_SUFFIX = "1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "TopNodeSemanticDropDeploymentDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	private final String nodeCompartmentType;

	public SemanticDropIntoDiagramTest(Class<? extends Element> elementType) {
		this.elementToDropName = elementType.getSimpleName() + ELEMENT_NAME_SUFFIX;
		this.dropToolId = SemanticDropToolsIds.getToolId(elementType.getSimpleName());
		this.nodeMappingType = MappingTypes.getMappingType(elementType.getSimpleName());
		if (MappingTypes.isNode(this.nodeMappingType)) {
			// nodeCompartmentType shouldn't be used if the nodeMappingType isn't a node.
			this.nodeCompartmentType = null;
		} else {
			this.nodeCompartmentType = MappingTypes.getCompartmentMappingType(elementType.getSimpleName());
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void dropElementIntoDiagram() {
		this.checkSiriusDiagramSynchronization(false);
		final DSemanticDiagram siriusDiagram = this.fixture.getActiveSiriusDiagram();
		Assert.assertNotNull(siriusDiagram);
		Assert.assertFalse("The diagram must be unsynchronized to test drop", siriusDiagram.isSynchronized()); //$NON-NLS-1$
		final Element toDrop;
		if (MappingTypes.getMappingType(Comment.class.getSimpleName()).equals(this.nodeMappingType)) {
			toDrop = this.root.getOwnedComments().get(0);
		} else {
			toDrop = this.root.getMember(this.elementToDropName);
		}
		Diagram diagram = this.getDiagram();
		IGraphicalRepresentationElementCreationChecker checker;
		if (MappingTypes.isNode(this.nodeMappingType)) {
			checker = new DNodeCreationChecker(diagram, siriusDiagram, this.nodeMappingType);
		} else {
			checker = new DNodeContainerCreationChecker(diagram, siriusDiagram, this.nodeMappingType, this.nodeCompartmentType);
		}
		this.dropNode(this.dropToolId, checker, toDrop, siriusDiagram);
	}

	@Parameters(name = "{index} drop semantic element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ Artifact.class },
				{ Comment.class },
				{ Constraint.class },
				{ DeploymentSpecification.class },
				{ Device.class },
				{ ExecutionEnvironment.class },
				{ Model.class },
				{ Node.class },
				{ Package.class }
		});
	}
}
