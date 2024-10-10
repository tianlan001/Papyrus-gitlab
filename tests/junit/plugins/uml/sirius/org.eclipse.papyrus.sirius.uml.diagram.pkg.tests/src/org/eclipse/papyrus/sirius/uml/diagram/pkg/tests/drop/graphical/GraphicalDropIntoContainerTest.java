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
package org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.drop.graphical;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractGraphicalDropNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.GraphicalDropToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.pkg.tests.MappingTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the graphical drop into new containers within the Package diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
@RunWith(value = Parameterized.class)
public class GraphicalDropIntoContainerTest extends AbstractGraphicalDropNodeTests {

	private static final String CONSTRAINT_FROM_DIAGRAM = "Constraint_FromDiagram"; //$NON-NLS-1$

	private static final String COMMENT_FROM_DIAGRAM = "Comment_FromDiagram"; //$NON-NLS-1$

	private static final String MODEL_FROM_DIAGRAM = "Model_FromDiagram"; //$NON-NLS-1$

	private static final String PACKAGE_FROM_DIAGRAM = "Package_FromDiagram"; //$NON-NLS-1$

	private static final String CONSTRAINT_FROM_PACKAGE = "Constraint_FromPackage"; //$NON-NLS-1$

	private static final String COMMENT_FROM_PACKAGE = "Comment_FromPackage"; //$NON-NLS-1$

	private static final String MODEL_FROM_PACKAGE = "Model_FromPackage"; //$NON-NLS-1$

	private static final String PACKAGE_FROM_PACKAGE = "Package_FromPackage"; //$NON-NLS-1$

	private static final String MODEL_NEWCONTAINER = "Model_NewContainer"; //$NON-NLS-1$

	private static final String PACKAGE_NEWCONTAINER = "Package_NewContainer"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "GraphicalDropPackageDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	/**
	 * Constructor.
	 *
	 */
	public GraphicalDropIntoContainerTest(String elementToDropName, String dropToolId, String nodeMappingType) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropToModelContainer() {
		this.dropElementIntoContainer(this.elementToDropName, MODEL_NEWCONTAINER, MappingTypes.MODEL_NODE_TYPE, MappingTypes.MODEL_NODE_PAD_PACKAGED_ELEMENTS_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropToPackageContainer() {
		this.dropElementIntoContainer(this.elementToDropName, PACKAGE_NEWCONTAINER, MappingTypes.PACKAGE_NODE_TYPE, MappingTypes.PACKAGE_NODE_PAD_PACKAGED_ELEMENTS_COMPARTMENT_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Parameters(name = "{index} drop graphical element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CONSTRAINT_FROM_DIAGRAM, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ CONSTRAINT_FROM_PACKAGE, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ COMMENT_FROM_DIAGRAM, GraphicalDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE },
				{ COMMENT_FROM_PACKAGE, GraphicalDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE },
				{ MODEL_FROM_DIAGRAM, GraphicalDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE },
				{ MODEL_FROM_PACKAGE, GraphicalDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE },
				{ PACKAGE_FROM_DIAGRAM, GraphicalDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE },
				{ PACKAGE_FROM_PACKAGE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE },
		});
	}
}
