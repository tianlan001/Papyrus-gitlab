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
package org.eclipse.papyrus.sirius.uml.diagram.communication.tests.drop.semantic;

import static org.junit.Assert.assertTrue;

import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.sirius.junit.util.diagram.AbstractSemanticDropSubNodeTests;
import org.eclipse.papyrus.sirius.uml.diagram.communication.tests.MappingTypes;
import org.eclipse.papyrus.sirius.uml.diagram.communication.tests.SemanticDropToolsIds;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Namespace;
import org.junit.Test;

/**
 * This class tests the semantic drop into {@link Interaction} container node.
 *
 * @author <a href="mailto:jessy.mallet@obeo.fr">Jessy Mallet</a>
 */
@PluginResource("resources/drop/subNodes/subNodeSemanticDrop.di")
public class SemanticDropIntoContainerTest extends AbstractSemanticDropSubNodeTests {

	private static final String DIAGRAM_NAME = "SubNodeSemanticDropCommunicationDiagramSirius"; //$NON-NLS-1$

	private static final String COMMENT = "Comment"; //$NON-NLS-1$

	private static final String CONSTRAINT = "Constraint1"; //$NON-NLS-1$

	private static final String DURATION_OBSERVATION = "DurationObservation1"; //$NON-NLS-1$

	private static final String INTERACTION = "Interaction1"; //$NON-NLS-1$

	private static final String LIFELINE = "Lifeline1"; //$NON-NLS-1$

	private static final String TIME_OBSERVATION = "TimeObservation1"; //$NON-NLS-1$

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testCommentDropIntoInteraction() {
		dropElementToContainer(COMMENT, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_COD_COMPARTMENTS_TYPE, SemanticDropToolsIds.DROP__COMMENT__TOOL, MappingTypes.COMMENT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testConstraintDropIntoInteraction() {
		dropElementToContainer(CONSTRAINT, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_COD_COMPARTMENTS_TYPE, SemanticDropToolsIds.DROP__CONSTRAINT__TOOL, MappingTypes.CONSTRAINT_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testDurationObservationDropIntoInteraction() {
		dropElementToContainer(DURATION_OBSERVATION, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_COD_COMPARTMENTS_TYPE, SemanticDropToolsIds.DROP__DURATION_OBSERVATION__TOOL, MappingTypes.DURATION_OBSERVATION_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testLifelineDropIntoInteraction() {
		dropElementToContainer(LIFELINE, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_COD_COMPARTMENTS_TYPE, SemanticDropToolsIds.DROP__LIFELINE__TOOL, MappingTypes.LIFELINE_NODE_TYPE);
	}

	@Test
	@ActiveDiagram(DIAGRAM_NAME)
	public void testTimeObservationDropIntoInteraction() {
		dropElementToContainer(TIME_OBSERVATION, INTERACTION, MappingTypes.INTERACTION_NODE_TYPE, MappingTypes.INTERACTION_NODE_COD_COMPARTMENTS_TYPE, SemanticDropToolsIds.DROP__TIME_OBSERVATION__TOOL, MappingTypes.TIME_OBSERVATION_NODE_TYPE);
	}

	/**
	 * Drop the element to a container.
	 * 
	 * @param elementNameToDrop
	 *            the semantic name of the element to drop.
	 * @param semanticOwnerName
	 *            the semantic name of the graphical parent containing the element to drop.
	 * @param containerMappingType
	 *            the mapping of the graphical container where the element is dropped.
	 * @param compartmentMappingType
	 *            the type of the compartment in which we want to drop the element.
	 * @param dropToolId
	 *            the drop tool to test.
	 * @param expectedMappingType
	 *            the expected mapping once the element is dropped.
	 */
	private void dropElementToContainer(String elementNameToDrop, String semanticOwnerName, String containerMappingType, String compartmentMappingType, String dropToolId, String expectedMappingType) {
		// Get semantic element representing the graphicalOwner
		Element rootElement = this.getRootElement();
		assertTrue(rootElement instanceof Namespace);
		Namespace semanticTargetOfGraphicalOwner = (Namespace) ((Namespace) rootElement).getMember(semanticOwnerName);
		// Get the semantic element to drop
		final Element elementToDrop;
		if (COMMENT.equals(elementNameToDrop)) {
			elementToDrop = semanticTargetOfGraphicalOwner.getOwnedComments().get(0);
		} else if (DURATION_OBSERVATION.equals(elementNameToDrop) || TIME_OBSERVATION.equals(elementNameToDrop)) {
			// Observations are stored in the Model root which is not the graphical Interaction owner
			elementToDrop = ((Namespace) rootElement).getMember(elementNameToDrop);
		} else {
			elementToDrop = semanticTargetOfGraphicalOwner.getMember(elementNameToDrop);
		}
		this.dropElementToContainer(semanticTargetOfGraphicalOwner, elementToDrop, containerMappingType, compartmentMappingType, dropToolId, expectedMappingType);
	}
}
