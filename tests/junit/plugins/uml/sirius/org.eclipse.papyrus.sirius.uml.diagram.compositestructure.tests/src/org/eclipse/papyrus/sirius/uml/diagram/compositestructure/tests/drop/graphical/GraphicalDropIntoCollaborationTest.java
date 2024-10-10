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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.drop.graphical;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractGraphicalDropNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.GraphicalDropToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.MappingTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the graphical drop from a Collaboration into an other Collaboration on the StrcutureComposite diagram .
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
@RunWith(value = Parameterized.class)
public class GraphicalDropIntoCollaborationTest extends AbstractGraphicalDropNodeTests {

	private static final String COLLABORATIONUSE = "CollaborationUse_FromCollaboration"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint_FromCollaboration"; //$NON-NLS-1$

	private static final String COLLABORATION_NEWCONTAINER = "Collaboration_NewContainer"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "GraphicalDropCompositeStructureDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	/**
	 * Constructor.
	 *
	 */
	public GraphicalDropIntoCollaborationTest(String elementToDropName, String dropToolId, String nodeMappingType) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropIntoCollaboration() {
		dropElementIntoContainer(this.elementToDropName, COLLABORATION_NEWCONTAINER, MappingTypes.COLLABORATION_NODE_TYPE, MappingTypes.COLLABORATION_NODE_CSD_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}


	@Parameters(name = "{index} drop graphical element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ COLLABORATIONUSE, GraphicalDropToolsIds.DROP__COLLABORATION_USE__TOOL, MappingTypes.COLLABORATION_USE_NODE_TYPE },
				{ CONSTRAINT, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
		});
	}
}
