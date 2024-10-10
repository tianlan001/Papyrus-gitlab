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
package org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.delete.semantic.nodes;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.compositestructure.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Delete sub-nodes from model and from container of diagram.
 *
 * @author <a href="mailto:florian.barbin@obeo.fr">Florian Barbin</a>
 */
@PluginResource("resources/deletion/nodes/NodeDeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class SubNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {

	private static final String CLASS = "Class1"; //$NON-NLS-1$

	private static final String COLLABORATION = "Collaboration1"; //$NON-NLS-1$

	private static final String COLLABORATIONUSE1 = "CollaborationUse1"; //$NON-NLS-1$

	private static final String FUNCTIONBEHAVIOR = "FunctionBehavior1"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction1"; //$NON-NLS-1$

	private static final String INFORMATIONITEM = "InformationItem1"; //$NON-NLS-1$

	private static final String OPAQUEBEHAVIOR = "OpaqueBehavior1"; //$NON-NLS-1$

	private static final String PARAMETER1 = "Parameter1"; //$NON-NLS-1$

	private static final String PORT1 = "Port1"; //$NON-NLS-1$

	private static final String PROPERTY1 = "Property1"; //$NON-NLS-1$

	private static final String PROPERTY2 = "Property2"; //$NON-NLS-1$

	private static final String STATEMACHINE = "StateMachine1"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "NodeDeleteCompositeStructureDiagramSirius"; //$NON-NLS-1$

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
		DDiagramElement compartment = getContainer();
		deleteNode(this.subNodeName, this.subNodeMappingType, compartment, false);
	}

	private DDiagramElement getContainer() {
		DNodeContainer container = (DNodeContainer) getNodeFromDiagram(this.containerName, this.containerMappingTypeName);
		final DDiagramElement compartment;
		if (MappingTypes.isBorderNode(this.subNodeMappingType)) {
			compartment = container;
		} else {
			compartment = container.getOwnedDiagramElements().get(0);
		}
		return compartment;
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ PORT1, MappingTypes.PORT_NODE_TYPE, FUNCTIONBEHAVIOR, MappingTypes.FUNCTION_BEHAVIOR_NODE_TYPE },
				{ PROPERTY1, MappingTypes.PROPERTY_NODE_TYPE, COLLABORATION, MappingTypes.COLLABORATION_NODE_TYPE },
				{ PROPERTY2, MappingTypes.PROPERTY_NODE_TYPE, CLASS, MappingTypes.CLASS_NODE_TYPE },
				{ PARAMETER1, MappingTypes.PARAMETER_NODE_TYPE, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE },
				{ INFORMATIONITEM, MappingTypes.INFORMATION_ITEM_NODE_TYPE, STATEMACHINE, MappingTypes.STATE_MACHINE_NODE_TYPE },
				{ COLLABORATIONUSE1, MappingTypes.COLLABORATION_USE_NODE_TYPE, OPAQUEBEHAVIOR, MappingTypes.OPAQUE_BEHAVIOR_NODE_TYPE }
		});
	}
}
