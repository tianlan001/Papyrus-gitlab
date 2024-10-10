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
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.DeploymentSpecification;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all the tests related to node deletion on the root of a Deployment diagram.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com">Gwendal Daniel</a>
 */
@PluginResource("resources/deletion/nodes/TopNodeDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class TopNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {

	private static final String ELEMENT_NAME_SUFFIX = "1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "TopNodeDeleteDeploymentDiagramSirius"; //$NON-NLS-1$

	private final String nodeMappingType;

	private final String nodeName;

	public TopNodeDeleteSemanticTest(Class<? extends Element> nodeType) {
		this.nodeName = nodeType.getSimpleName() + ELEMENT_NAME_SUFFIX;
		this.nodeMappingType = MappingTypes.getMappingType(nodeType.getSimpleName());
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteTopDiagramElementTest() {
		this.deleteNode(this.nodeName, this.nodeMappingType, false);
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ Artifact.class },
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
