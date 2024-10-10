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
 * This class tests the graphical drop from a Package on the Package diagram.
 *
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
@RunWith(value = Parameterized.class)
public class GraphicalDropIntoDiagramTest extends AbstractGraphicalDropNodeTests {

	private static final String CONSTRAINT = "Constraint_FromPackage"; //$NON-NLS-1$

	private static final String COMMENT = "Comment_FromPackage"; //$NON-NLS-1$

	private static final String MODEL = "Model_FromPackage"; //$NON-NLS-1$

	private static final String PACKAGE = "Package_FromPackage"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "GraphicalDropPackageDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	/**
	 * Constructor.
	 *
	 */
	public GraphicalDropIntoDiagramTest(String elementToDropName, String dropToolId, String nodeMappingType) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropToPackage() {
		this.dropElementToDiagram(this.elementToDropName, this.dropToolId, this.nodeMappingType);
	}


	@Parameters(name = "{index} drop graphical element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CONSTRAINT, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ COMMENT, GraphicalDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE },
				{ MODEL, GraphicalDropToolsIds.DROP__MODEL__TOOL, MappingTypes.MODEL_NODE_TYPE },
				{ PACKAGE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE },
		});
	}
}
