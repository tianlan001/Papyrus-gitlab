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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.deletion.semantic.nodes;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractDeleteSemanticNodeTest;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.MappingTypes;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.DNodeContainer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * Delete sub-nodes from model and from container of UseCase diagram.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/deletion/nodes/Node_DeleteSemanticTest.di")
@RunWith(value = Parameterized.class)
public class SubNodeDeleteSemanticTest extends AbstractDeleteSemanticNodeTest {

	private static final String ACTIVITY_TO_DELETE_FROM_PACKAGE = "ActivityToDeleteFromPackage"; //$NON-NLS-1$

	private static final String ACTOR_TO_DELETE_FROM_PACKAGE = "ActorToDeleteFromPackage"; //$NON-NLS-1$

	private static final String CLASS_TO_DELETE_FROM_PACKAGE = "ClassToDeleteFromPackage"; //$NON-NLS-1$

	private static final String COMPONENT_TO_DELETE_FROM_PACKAGE = "ComponentToDeleteFromPackage"; //$NON-NLS-1$

	private static final String CONSTRAINT_TO_DELETE_FROM_PACKAGE = "ConstraintToDeleteFromPackage"; //$NON-NLS-1$

	private static final String INTERACTION_TO_DELETE_FROM_PACKAGE = "InteractionToDeleteFromPackage"; //$NON-NLS-1$

	private static final String PACKAGE_TO_DELETE_FROM_PACKAGE = "PackageToDeleteFromPackage"; //$NON-NLS-1$

	private static final String STATE_MACHINE_TO_DELETE_FROM_PACKAGE = "StateMachineToDeleteFromPackage"; //$NON-NLS-1$

	private static final String USE_CASE_TO_DELETE_FROM_ACTIVITY = "UseCaseToDeleteFromActivity"; //$NON-NLS-1$

	private static final String USE_CASE_TO_DELETE_FROM_CLASS = "UseCaseToDeleteFromClass"; //$NON-NLS-1$

	private static final String USE_CASE_TO_DELETE_FROM_COMPONENT = "UseCaseToDeleteFromComponent"; //$NON-NLS-1$

	private static final String USE_CASE_TO_DELETE_FROM_INTERACTION = "UseCaseToDeleteFromInteraction"; //$NON-NLS-1$

	private static final String USE_CASE_TO_DELETE_FROM_PACKAGE = "UseCaseToDeleteFromPackage"; //$NON-NLS-1$

	private static final String USE_CASE_TO_DELETE_FROM_STATE_MACHINE = "UseCaseToDeleteFromStateMachine"; //$NON-NLS-1$

	private static final String CONSTRAINT_TO_DELETE_FROM_ACTIVITY = "ConstraintToDeleteFromActivity"; //$NON-NLS-1$

	private static final String CONSTRAINT_TO_DELETE_FROM_CLASS = "ConstraintToDeleteFromClass"; //$NON-NLS-1$

	private static final String CONSTRAINT_TO_DELETE_FROM_COMPONENT = "ConstraintToDeleteFromComponent"; //$NON-NLS-1$

	private static final String CONSTRAINT_TO_DELETE_FROM_INTERACTION = "ConstraintToDeleteFromInteraction"; //$NON-NLS-1$

	private static final String CONSTRAINT_TO_DELETE_FROM_STATE_MACHINE = "ConstraintToDeleteFromStateMachine"; //$NON-NLS-1$

	private static final String ACTIVITY_CONTAINER = "ActivityContainer"; //$NON-NLS-1$

	private static final String CLASS_CONTAINER = "ClassContainer"; //$NON-NLS-1$

	private static final String COMPONENT_CONTAINER = "ComponentContainer"; //$NON-NLS-1$

	private static final String INTERACTION_CONTAINER = "InteractionContainer"; //$NON-NLS-1$

	private static final String PACKAGE_CONTAINER = "PackageContainer"; //$NON-NLS-1$

	private static final String STATE_MACHINE_CONTAINER = "StateMachineContainer"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "Node_Delete_UseCaseDiagramSirius"; //$NON-NLS-1$

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
		final DDiagramElement compartment = container.getOwnedDiagramElements().get(0);
		return compartment;
	}

	@Parameters(name = "{index} delete node {0}")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ ACTIVITY_TO_DELETE_FROM_PACKAGE, MappingTypes.ACTIVITY_NODE_TYPE, PACKAGE_CONTAINER, MappingTypes.PACKAGE_NODE_TYPE },
				{ ACTOR_TO_DELETE_FROM_PACKAGE, MappingTypes.ACTOR_NODE_TYPE, PACKAGE_CONTAINER, MappingTypes.PACKAGE_NODE_TYPE },
				{ CLASS_TO_DELETE_FROM_PACKAGE, MappingTypes.CLASS_NODE_TYPE, PACKAGE_CONTAINER, MappingTypes.PACKAGE_NODE_TYPE },
				{ COMPONENT_TO_DELETE_FROM_PACKAGE, MappingTypes.COMPONENT_NODE_TYPE, PACKAGE_CONTAINER, MappingTypes.PACKAGE_NODE_TYPE },
				{ CONSTRAINT_TO_DELETE_FROM_ACTIVITY, MappingTypes.CONSTRAINT_NODE_TYPE, ACTIVITY_CONTAINER, MappingTypes.ACTIVITY_NODE_TYPE },
				{ CONSTRAINT_TO_DELETE_FROM_CLASS, MappingTypes.CONSTRAINT_NODE_TYPE, CLASS_CONTAINER, MappingTypes.CLASS_NODE_TYPE },
				{ CONSTRAINT_TO_DELETE_FROM_COMPONENT, MappingTypes.CONSTRAINT_NODE_TYPE, COMPONENT_CONTAINER, MappingTypes.COMPONENT_NODE_TYPE },
				{ CONSTRAINT_TO_DELETE_FROM_INTERACTION, MappingTypes.CONSTRAINT_NODE_TYPE, INTERACTION_CONTAINER, MappingTypes.INTERACTION_NODE_TYPE },
				{ CONSTRAINT_TO_DELETE_FROM_PACKAGE, MappingTypes.CONSTRAINT_NODE_TYPE, PACKAGE_CONTAINER, MappingTypes.PACKAGE_NODE_TYPE },
				{ CONSTRAINT_TO_DELETE_FROM_STATE_MACHINE, MappingTypes.CONSTRAINT_NODE_TYPE, STATE_MACHINE_CONTAINER, MappingTypes.STATE_MACHINE_NODE_TYPE },
				{ INTERACTION_TO_DELETE_FROM_PACKAGE, MappingTypes.INTERACTION_NODE_TYPE, PACKAGE_CONTAINER, MappingTypes.PACKAGE_NODE_TYPE },
				{ PACKAGE_TO_DELETE_FROM_PACKAGE, MappingTypes.PACKAGE_NODE_TYPE, PACKAGE_CONTAINER, MappingTypes.PACKAGE_NODE_TYPE },
				{ STATE_MACHINE_TO_DELETE_FROM_PACKAGE, MappingTypes.STATE_MACHINE_NODE_TYPE, PACKAGE_CONTAINER, MappingTypes.PACKAGE_NODE_TYPE },
				{ USE_CASE_TO_DELETE_FROM_ACTIVITY, MappingTypes.USE_CASE_NODE_TYPE, ACTIVITY_CONTAINER, MappingTypes.ACTIVITY_NODE_TYPE },
				{ USE_CASE_TO_DELETE_FROM_CLASS, MappingTypes.USE_CASE_NODE_TYPE, CLASS_CONTAINER, MappingTypes.CLASS_NODE_TYPE },
				{ USE_CASE_TO_DELETE_FROM_COMPONENT, MappingTypes.USE_CASE_NODE_TYPE, COMPONENT_CONTAINER, MappingTypes.COMPONENT_NODE_TYPE },
				{ USE_CASE_TO_DELETE_FROM_INTERACTION, MappingTypes.USE_CASE_NODE_TYPE, INTERACTION_CONTAINER, MappingTypes.INTERACTION_NODE_TYPE },
				{ USE_CASE_TO_DELETE_FROM_PACKAGE, MappingTypes.USE_CASE_NODE_TYPE, PACKAGE_CONTAINER, MappingTypes.PACKAGE_NODE_TYPE },
				{ USE_CASE_TO_DELETE_FROM_STATE_MACHINE, MappingTypes.USE_CASE_NODE_TYPE, STATE_MACHINE_CONTAINER, MappingTypes.STATE_MACHINE_NODE_TYPE }
		});
	}
}
