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
 * This class tests the graphical drop from a Classifier on the StrcutureComposite diagram .
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/drop/graphical/graphicalDrop.di")
@RunWith(value = Parameterized.class)
public class GraphicalDropIntoClassifierTest extends AbstractGraphicalDropNodeTests {

	private static final String ACTIVITY = "Activity_FromClass"; //$NON-NLS-1$

	private static final String CLASS = "Class_FromClass"; //$NON-NLS-1$

	private static final String COLLABORATION = "Collaboration_FromClass"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint_FromClass"; //$NON-NLS-1$

	private static final String FUNCTIONBEHAVIOR = "FunctionBehavior_FromClass"; //$NON-NLS-1$

	private static final String INFORMATIONITEM = "InformationItem_FromClass"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction_FromClass"; //$NON-NLS-1$

	private static final String OPAQUEBEHAVIOR = "OpaqueBehavior_FromClass"; //$NON-NLS-1$

	private static final String STATEMACHINE = "StateMachine_FromClass"; //$NON-NLS-1$

	private static final String PROTOCOLSTATEMACHINE = "ProtocolStateMachine_FromClass"; //$NON-NLS-1$

	private static final String ACTIVITY_NEWCONTAINER = "Activity_NewContainer"; //$NON-NLS-1$

	private static final String CLASS_NEWCONTAINER = "Class_NewContainer"; //$NON-NLS-1$

	private static final String FUNCTIONBEHAVIOR_NEWCONTAINER = "FunctionBehavior_NewContainer"; //$NON-NLS-1$

	private static final String INTERACTION_NEWCONTAINER = "Interaction_NewContainer"; //$NON-NLS-1$

	private static final String OPAQUEBEHAVIOR_NEWCONTAINER = "OpaqueBehavior_NewContainer"; //$NON-NLS-1$

	private static final String STATEMACHINE_NEWCONTAINER = "StateMachine_NewContainer"; //$NON-NLS-1$

	private static final String PROTOCOLSTATEMACHINE_NEWCONTAINER = "ProtocolStateMachine_NewContainer"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "GraphicalDropCompositeStructureDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	/**
	 * Constructor.
	 *
	 */
	public GraphicalDropIntoClassifierTest(String elementToDropName, String dropToolId, String nodeMappingType) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropIntoActivity() {
		dropElementIntoContainer(this.elementToDropName, ACTIVITY_NEWCONTAINER, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_NODE_CSD_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropIntoClass() {
		dropElementIntoContainer(this.elementToDropName, CLASS_NEWCONTAINER, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_CSD_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropIntoFunctionBehavior() {
		dropElementIntoContainer(this.elementToDropName, FUNCTIONBEHAVIOR_NEWCONTAINER, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE, MappingTypes.FUNCTION_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropIntoInteraction() {
		dropElementIntoContainer(this.elementToDropName, INTERACTION_NEWCONTAINER, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_CSD_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropIntoOpaqueBehavior() {
		dropElementIntoContainer(this.elementToDropName, OPAQUEBEHAVIOR_NEWCONTAINER, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE, MappingTypes.OPAQUE_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropIntoProtocolStateMachine() {
		dropElementIntoContainer(this.elementToDropName, PROTOCOLSTATEMACHINE_NEWCONTAINER, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testGraphicalDropIntoStateMachine() {
		dropElementIntoContainer(this.elementToDropName, STATEMACHINE_NEWCONTAINER, MappingTypes.STATE_MACHINE_NODE_TYPE, MappingTypes.STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE, this.dropToolId, this.nodeMappingType);
	}


	@Parameters(name = "{index} drop graphical element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY, GraphicalDropToolsIds.DROP__ACTIVITY__TOOL, MappingTypes.ACTIVITY_NODE_TYPE },
				{ CLASS, GraphicalDropToolsIds.DROP__CLASS__TOOL, MappingTypes.CLASS_NODE_TYPE },
				{ COLLABORATION, GraphicalDropToolsIds.DROP__COLLABORATION__TOOL, MappingTypes.COLLABORATION_NODE_TYPE },
				{ CONSTRAINT, GraphicalDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ FUNCTIONBEHAVIOR, GraphicalDropToolsIds.DROP__FUNCTION_BEHAVIOR__TOOL, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE },
				{ INFORMATIONITEM, GraphicalDropToolsIds.DROP__INFORMATION_ITEM__TOOL, MappingTypes.INFORMATION_ITEM_NODE_TYPE },
				{ INTERACTION, GraphicalDropToolsIds.DROP__INTERACTION__TOOL, MappingTypes.INTERACTION_NODE_TYPE },
				{ OPAQUEBEHAVIOR, GraphicalDropToolsIds.DROP__OPAQUE_BEHAVIOR__TOOL, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE },
				{ PROTOCOLSTATEMACHINE, GraphicalDropToolsIds.DROP__PROTOCOL_STATE_MACHINE__TOOL, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE },
				{ STATEMACHINE, GraphicalDropToolsIds.DROP__STATE_MACHINE__TOOL, MappingTypes.STATE_MACHINE_NODE_TYPE },
		});
	}
}
