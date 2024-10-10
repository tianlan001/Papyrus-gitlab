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
package org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.delete.semantic.nodes;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Groups all the tests related to sub-node deletion on the root of a Package diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/deletion/subNodes/SubNodeDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class SubNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {

	private static final String CONSTRAINT1 = "Constraint1"; //$NON-NLS-1$

	private static final String MODEL0 = "Model0"; //$NON-NLS-1$

	private static final String PACKAGE0 = "Package0"; //$NON-NLS-1$

	private static final String MODEL1 = "Model1"; //$NON-NLS-1$

	private static final String PACKAGE1 = "Package1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SubNodeDeletePackageDiagramSirius"; //$NON-NLS-1$

	private final String subNodeName;

	private final String subNodeMappingType;

	private final String containerName;

	private final String containerMappingTypeName;

	/**
	 * Constructor.
	 *
	 */
	public SubNodeDeleteSemanticTest(String subNodeName, String subNodeMappingType, String containerName, String containerMappingTypeName) {
		this.subNodeName = subNodeName;
		this.subNodeMappingType = subNodeMappingType;
		this.containerName = containerName;
		this.containerMappingTypeName = containerMappingTypeName;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void deleteSubDiagramElementTest() {
		DDiagramElement compartment = this.getContainer();
		this.deleteNode(this.subNodeName, this.subNodeMappingType, compartment, false);
	}

	private DDiagramElement getContainer() {
		DNodeContainer container = (DNodeContainer) this.getNodeFromDiagram(this.containerName, this.containerMappingTypeName);
		return container.getOwnedDiagramElements().get(0);
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ PACKAGE1, MappingTypes.PACKAGE_NODE_TYPE, MODEL0, MappingTypes.MODEL_NODE_TYPE },
				{ PACKAGE1, MappingTypes.PACKAGE_NODE_TYPE, PACKAGE0, MappingTypes.PACKAGE_NODE_TYPE },
				{ MODEL1, MappingTypes.MODEL_NODE_TYPE, MODEL0, MappingTypes.MODEL_NODE_TYPE },
				{ MODEL1, MappingTypes.MODEL_NODE_TYPE, PACKAGE0, MappingTypes.PACKAGE_NODE_TYPE },
				{ CONSTRAINT1, MappingTypes.CONSTRAINT_NODE_TYPE, MODEL0, MappingTypes.MODEL_NODE_TYPE },
				{ CONSTRAINT1, MappingTypes.CONSTRAINT_NODE_TYPE, PACKAGE0, MappingTypes.PACKAGE_NODE_TYPE },
		});
	}
}
