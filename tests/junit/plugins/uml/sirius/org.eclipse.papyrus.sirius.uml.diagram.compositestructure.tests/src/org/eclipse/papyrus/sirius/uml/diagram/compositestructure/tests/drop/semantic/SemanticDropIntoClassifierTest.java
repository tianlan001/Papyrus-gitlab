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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.drop.semantic;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticDropSubNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.SemanticDropToolsIds;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the semantic drop into Classifier container from the CompositeStructure diagram.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/drop/semanticSubNodes/semanticSubNodesDrop.di")
@RunWith(value = Parameterized.class)
public class SemanticDropIntoClassifierTest extends AbstractSemanticDropSubNodeTests {

	private static final String ACTIVITY = "Activity"; //$NON-NLS-1$

	private static final String CLASS = "Class"; //$NON-NLS-1$

	private static final String COLLABORATION = "Collaboration"; //$NON-NLS-1$

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String FUNCTIONBEHAVIOR = "FunctionBehavior"; //$NON-NLS-1$

	private static final String INFORMATIONITEM = "InformationItem"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction"; //$NON-NLS-1$

	private static final String OPAQUEBEHAVIOR = "OpaqueBehavior"; //$NON-NLS-1$

	private static final String PARAMETER = "Parameter"; //$NON-NLS-1$

	private static final String PORT = "Port"; //$NON-NLS-1$

	private static final String PROPERTY = "Property"; //$NON-NLS-1$

	private static final String STATEMACHINE = "StateMachine"; //$NON-NLS-1$

	private static final String PROTOCOLSTATEMACHINE = "ProtocolStateMachine"; //$NON-NLS-1$

	private static final String ACTIVITY_CONTAINER = "Activity_Container"; //$NON-NLS-1$

	private static final String CLASS_CONTAINER = "Class_Container"; //$NON-NLS-1$

	private static final String FUNCTIONBEHAVIOR_CONTAINER = "FunctionBehavior_Container"; //$NON-NLS-1$

	private static final String INTERACTION_CONTAINER = "Interaction_Container"; //$NON-NLS-1$

	private static final String OPAQUEBEHAVIOR_CONTAINER = "OpaqueBehavior_Container"; //$NON-NLS-1$

	private static final String STATEMACHINE_CONTAINER = "StateMachine_Container"; //$NON-NLS-1$

	private static final String PROTOCOLSTATEMACHINE_CONTAINER = "ProtocolStateMachine_Container"; //$NON-NLS-1$

	private static final String FROM_ACTIVITY = "_FromActivity"; //$NON-NLS-1$

	private static final String FROM_CLASS = "_FromClass"; //$NON-NLS-1$

	private static final String FROM_FUNCTIONBEHAVIOR = "_FromFunctionBehavior"; //$NON-NLS-1$

	private static final String FROM_INTERACTION = "_FromInteraction"; //$NON-NLS-1$

	private static final String FROM_OPAQUEBEHAVIOR = "_FromOpaqueBehavior"; //$NON-NLS-1$

	private static final String FROM_PROTOCOLSTATEMACHINE = "_FromProtocolStateMachine"; //$NON-NLS-1$

	private static final String FROM_STATEMACHINE = "_FromStateMachine"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SemanticSubNodeDropCompositeStructureDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	/**
	 * Constructor.
	 *
	 */
	public SemanticDropIntoClassifierTest(String elementToDropName, String dropToolId, String nodeMappingType) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoActivity() {
		dropElementToClassifierContainer(ACTIVITY_CONTAINER, FROM_ACTIVITY, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_NODE_CSD_COMPARTMENTS_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoClass() {
		if (!PARAMETER.equals(this.elementToDropName)) {
			dropElementToClassifierContainer(CLASS_CONTAINER, FROM_CLASS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_CSD_COMPARTMENTS_TYPE);
		}
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoFunctionBehavior() {
		dropElementToClassifierContainer(FUNCTIONBEHAVIOR_CONTAINER, FROM_FUNCTIONBEHAVIOR, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE, MappingTypes.FUNCTION_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoInteraction() {
		dropElementToClassifierContainer(INTERACTION_CONTAINER, FROM_INTERACTION, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_CSD_COMPARTMENTS_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoOpaqueBehavior() {
		dropElementToClassifierContainer(OPAQUEBEHAVIOR_CONTAINER, FROM_OPAQUEBEHAVIOR, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE, MappingTypes.OPAQUE_BEHAVIOR_NODE_CSD_COMPARTMENTS_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoProtocolStateMachine() {
		dropElementToClassifierContainer(PROTOCOLSTATEMACHINE_CONTAINER, FROM_PROTOCOLSTATEMACHINE, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoStateMachine() {
		dropElementToClassifierContainer(STATEMACHINE_CONTAINER, FROM_STATEMACHINE, MappingTypes.STATE_MACHINE_NODE_TYPE, MappingTypes.STATE_MACHINE_NODE_CSD_COMPARTMENTS_TYPE);
	}

	/**
	 * Drop the element to a Classifier container.
	 * 
	 * @param semanticOwnerName
	 *            the name of the semantic owner
	 * @param sourceSuffix
	 *            suffix of the element to DnD
	 * @param containerMappingType
	 *            the mapping of the graphical container where the element is dropped.
	 * @param compartmentMappingType
	 *            the type of the compartment in which we want to drop the element.
	 */
	private void dropElementToClassifierContainer(String semanticOwnerName, String sourceSuffix, String containerMappingType, String compartmentMappingType) {
		Element rootElement = this.getRootElement();
		assertTrue(rootElement instanceof Namespace);
		Namespace semanticOwner = (Namespace) ((Namespace) rootElement).getMember(semanticOwnerName);
		final Element elementToDrop;
		if (COMMENT.equals(this.elementToDropName)) {
			elementToDrop = semanticOwner.getOwnedComments().get(0);
		} else {
			elementToDrop = semanticOwner.getMember(this.elementToDropName + sourceSuffix);
		}
		this.dropElementToContainer(semanticOwner, elementToDrop, containerMappingType, compartmentMappingType, this.dropToolId, this.nodeMappingType);
	}

	@Override
	protected boolean isBorderNode(String elementToDropType) {
		return MappingTypes.isBorderNode(elementToDropType);
	}


	@Parameters(name = "{index} drop semantic element {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY, SemanticDropToolsIds.DROP__ACTIVITY__TOOL, MappingTypes.ACTIVITY_NODE_TYPE },
				{ CLASS, SemanticDropToolsIds.DROP__CLASS__TOOL, MappingTypes.CLASS_NODE_TYPE },
				{ COLLABORATION, SemanticDropToolsIds.DROP__COLLABORATION__TOOL, MappingTypes.COLLABORATION_NODE_TYPE },
				{ COMMENT, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE },
				{ CONSTRAINT, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ FUNCTIONBEHAVIOR, SemanticDropToolsIds.DROP__FUNCTION_BEHAVIOR__TOOL, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE },
				{ INFORMATIONITEM, SemanticDropToolsIds.DROP__INFORMATION_ITEM__TOOL, MappingTypes.INFORMATION_ITEM_NODE_TYPE },
				{ INTERACTION, SemanticDropToolsIds.DROP__INTERACTION__TOOL, MappingTypes.INTERACTION_NODE_TYPE },
				{ OPAQUEBEHAVIOR, SemanticDropToolsIds.DROP__OPAQUE_BEHAVIOR__TOOL, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE },
				{ PARAMETER, SemanticDropToolsIds.DROP__PARAMETER__TOOL, MappingTypes.PARAMETER_NODE_TYPE },
				{ PORT, SemanticDropToolsIds.DROP__PORT__TOOL, MappingTypes.PORT_NODE_TYPE },
				{ PROPERTY, SemanticDropToolsIds.DROP__PROPERTY__TOOL, MappingTypes.PROPERTY_NODE_TYPE },
				{ PROTOCOLSTATEMACHINE, SemanticDropToolsIds.DROP__PROTOCOL_STATE_MACHINE__TOOL, MappingTypes.PROTOCOL_STATE_MACHINE_NODE_TYPE },
				{ STATEMACHINE, SemanticDropToolsIds.DROP__STATE_MACHINE__TOOL, MappingTypes.STATE_MACHINE_NODE_TYPE },
		});
	}

}
