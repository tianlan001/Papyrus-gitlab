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
package org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.drop.semantic;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticDropSubNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.usecase.tests.SemanticDropToolsIds;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * This class tests the semantic drop into a Subject container node.
 * 
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/drop/semanticSubNodes/semanticSubNodesDrop.di")
@RunWith(value = Parameterized.class)
public class SemanticDropIntoSubjectTest extends AbstractSemanticDropSubNodeTests {

	private static final String CONSTRAINT = "Constraint"; //$NON-NLS-1$

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String USECASE = "UseCase"; //$NON-NLS-1$

	private static final String ACTIVITY_CONTAINER = "Activity_Container"; //$NON-NLS-1$

	private static final String CLASS_CONTAINER = "Class_Container"; //$NON-NLS-1$

	private static final String COMPONENT_CONTAINER = "Component_Container"; //$NON-NLS-1$

	private static final String INTERACTION_CONTAINER = "Interaction_Container"; //$NON-NLS-1$

	private static final String STATEMACHINE_CONTAINER = "StateMachine_Container"; //$NON-NLS-1$

	private static final String FROM_ACTIVITY = "_FromActivity"; //$NON-NLS-1$

	private static final String FROM_CLASS = "_FromClass"; //$NON-NLS-1$

	private static final String FROM_COMPONENT = "_FromComponent"; //$NON-NLS-1$

	private static final String FROM_INTERACTION = "_FromInteraction"; //$NON-NLS-1$

	private static final String FROM_STATEMACHINE = "_FromStateMachine"; //$NON-NLS-1$

	private static final String DIAGRAM_NAME = "SemanticSubNodeDropUseCaseDiagramSirius"; //$NON-NLS-1$

	private final String elementToDropName;

	private final String dropToolId;

	private final String nodeMappingType;

	/**
	 * Constructor.
	 *
	 */
	public SemanticDropIntoSubjectTest(String elementToDropName, String dropToolId, String nodeMappingType) {
		this.elementToDropName = elementToDropName;
		this.dropToolId = dropToolId;
		this.nodeMappingType = nodeMappingType;
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoActivitySubject() {
		dropElementToSubjectContainer(ACTIVITY_CONTAINER, FROM_ACTIVITY, MappingTypes.ACTIVITY_NODE_TYPE, MappingTypes.ACTIVITY_NODE_USECASES_COMPARTMENTS_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoClassSubject() {
		dropElementToSubjectContainer(CLASS_CONTAINER, FROM_CLASS, MappingTypes.CLASS_NODE_TYPE, MappingTypes.CLASS_NODE_USECASES_COMPARTMENTS_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoComponentSubject() {
		dropElementToSubjectContainer(COMPONENT_CONTAINER, FROM_COMPONENT, MappingTypes.COMPONENT_NODE_TYPE, MappingTypes.COMPONENT_NODE_USECASES_COMPARTMENTS_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoInteractionSubject() {
		dropElementToSubjectContainer(INTERACTION_CONTAINER, FROM_INTERACTION, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_USECASES_COMPARTMENTS_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testSemanticDropIntoStateMachineSubject() {
		dropElementToSubjectContainer(STATEMACHINE_CONTAINER, FROM_STATEMACHINE, MappingTypes.STATE_MACHINE_NODE_TYPE, MappingTypes.STATE_MACHINE_NODE_USECASES_COMPARTMENTS_TYPE);
	}

	/**
	 * Drop the element to a Subject container.
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
	private void dropElementToSubjectContainer(String semanticOwnerName, String sourceSuffix, String containerMappingType, String compartmentMappingType) {
		Element rootElement = getRootElement();
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

	@Parameters(name = "{index} drop semantic element {0} into Subject Container")
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ COMMENT, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE },
				{ CONSTRAINT, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE },
				{ USECASE, SemanticDropToolsIds.DROP__USE_CASE__TOOL, MappingTypes.USE_CASE_NODE_TYPE },
		});
	}
}
