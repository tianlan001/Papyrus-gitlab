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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.drop.graphical;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractGraphicalDropNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.GraphicalDropToolsIds;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.MappingTypes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the graphical drop from a Package on the UseCase diagram .
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
@RunWith(value = Parameterized.class)
public class GraphicalDropIntoDiagramTest extends AbstractGraphicalDropNodeTests {

	private static final String ACTIVITY = "Activity_FromPackage"; //$NON-NLS-1$

	private static final String ACTOR = "Actor_FromPackage"; //$NON-NLS-1$

	private static final String CLASS = "Class_FromPackage"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint_FromPackage"; //$NON-NLS-1$

	private static final String COMPONENT = "Component_FromPackage"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction_FromPackage"; //$NON-NLS-1$

	private static final String PACKAGE = "Package_FromPackage"; //$NON-NLS-1$

	private static final String STATEMACHINE = "StateMachine_FromPackage"; //$NON-NLS-1$

	private static final String USECASE = "UseCase_FromPackage"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "GraphicalDropUseCaseDiagramSirius"; //$NON-NLS-1$

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
		dropElementToDiagram(this.elementToDropName, this.dropToolId, this.nodeMappingType);
	}


	@Parameters(name = "{index} drop graphical element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY, GraphicalDropToolsIds.DROP__ACTIVITY__TOOL, MappingTypes.ACTIVITY_NODE_TYPE },
				{ ACTOR, GraphicalDropToolsIds.DROP__ACTOR__TOOL, MappingTypes.ACTOR_NODE_TYPE },
				{ CLASS, GraphicalDropToolsIds.DROP__CLASS__TOOL, MappingTypes.CLASS_NODE_TYPE },
				{ COMPONENT, GraphicalDropToolsIds.DROP__COMPONENT__TOOL, MappingTypes.COMPONENT_NODE_TYPE },
				{ CONSTRAINT, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ INTERACTION, GraphicalDropToolsIds.DROP__INTERACTION__TOOL, MappingTypes.INTERACTION_NODE_TYPE },
				{ PACKAGE, GraphicalDropToolsIds.DROP__PACKAGE__TOOL, MappingTypes.PACKAGE_NODE_TYPE },
				{ STATEMACHINE, GraphicalDropToolsIds.DROP__STATE_MACHINE__TOOL, MappingTypes.STATE_MACHINE_NODE_TYPE },
				{ USECASE, GraphicalDropToolsIds.DROP__USE_CASE__TOOL, MappingTypes.USE_CASE_NODE_TYPE },
		});
	}
}
