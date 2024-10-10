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
 * This class tests the graphical drop from Subject into new Subject container within the UseCase diagram .
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
@RunWith(value = Parameterized.class)
public class GraphicalDropIntoSubjectTest extends AbstractGraphicalDropNodeTests {

	private static final String CONSTRAINT = "Constraint_FromSubject"; //$NON-NLS-1$

	private static final String USECASE = "UseCase_FromSubject"; //$NON-NLS-1$

	private static final String ACTIVITY_NEWCONTAINER = "Activity_NewContainer"; //$NON-NLS-1$

	private static final String CLASS_NEWCONTAINER = "Class_NewContainer"; //$NON-NLS-1$

	private static final String COMPONENT_NEWCONTAINER = "Component_NewContainer"; //$NON-NLS-1$

	private static final String INTERACTION_NEWCONTAINER = "Interaction_NewContainer"; //$NON-NLS-1$

	private static final String STATEMACHINE_NEWCONTAINER = "StateMachine_NewContainer"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "GraphicalDropUseCaseDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	/**
	 * Constructor.
	 *
	 */
	public GraphicalDropIntoSubjectTest(String elementToDropName, String dropToolId, String nodeMappingType) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropToActivitySubject() {
		dropElementIntoContainer(this.elementToDropName, ACTIVITY_NEWCONTAINER, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_NODE_USECASES_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropToClassSubject() {
		dropElementIntoContainer(this.elementToDropName, CLASS_NEWCONTAINER, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_USECASES_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropToComponentSubject() {
		dropElementIntoContainer(this.elementToDropName, COMPONENT_NEWCONTAINER, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_USECASES_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropToInteractionSubject() {
		dropElementIntoContainer(this.elementToDropName, INTERACTION_NEWCONTAINER, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_USECASES_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDropToStateMachineSubject() {
		dropElementIntoContainer(this.elementToDropName, STATEMACHINE_NEWCONTAINER, MappingTypes.STATE_MACHINE_NODE_TYPE, MappingTypes.STATE_MACHINE_NODE_USECASES_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}


	@Parameters(name = "{index} drop graphical element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ CONSTRAINT, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ USECASE, GraphicalDropToolsIds.DROP__USE_CASE__TOOL, MappingTypes.USE_CASE_NODE_TYPE },
		});
	}
}
