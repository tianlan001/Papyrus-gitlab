/*****************************************************************************
 * Copyright (c) 2024 CEA LIST.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramReorderServices;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.junit.Before;
import org.junit.Test;

/**
 * Test {@link SequenceDiagramReorderServices} service.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramReorderServicesTest extends AbstractServicesTest {

	private SequenceDiagramReorderServices reorderServices;

	private Interaction interaction;

	private Lifeline lifeline1;

	private Lifeline lifeline2;

	private MessageOccurrenceSpecification messageOccurrenceSpecification1;

	private MessageOccurrenceSpecification messageOccurrenceSpecification2;

	@Before
	public void setUp() {
		reorderServices = new SequenceDiagramReorderServices();
	}

	@Test
	public void testGetEndsOrderingNullInteraction() {
		messageOccurrenceSpecification1 = create(MessageOccurrenceSpecification.class);
		messageOccurrenceSpecification2 = create(MessageOccurrenceSpecification.class);
		assertThrows(NullPointerException.class, () -> reorderServices.getEndsOrdering(null, List.of(messageOccurrenceSpecification1, messageOccurrenceSpecification2)));
	}

	@Test
	public void testGetEndsOrderingNullEventEnds() {
		interaction = create(Interaction.class);
		assertThrows(NullPointerException.class, () -> reorderServices.getEndsOrdering(interaction, null));
	}

	@Test
	public void testGetEndsOrderingEmptyInteraction() {
		/*
		 * Need to create the instances manually because we don't want the interaction to contain the message occurrence specifications.
		 */
		interaction = create(Interaction.class);
		messageOccurrenceSpecification1 = create(MessageOccurrenceSpecification.class);
		messageOccurrenceSpecification2 = create(MessageOccurrenceSpecification.class);
		List<EObject> result = reorderServices.getEndsOrdering(interaction, List.of(messageOccurrenceSpecification1, messageOccurrenceSpecification2));
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetEndsOrderingEmptyEventEnds() {
		createInteractionWithTwoMessageOccurrenceSpecifications();
		List<EObject> result = reorderServices.getEndsOrdering(interaction, Collections.emptyList());
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetEndsOrderingSubsetEventEnds() {
		createInteractionWithTwoMessageOccurrenceSpecifications();
		List<EObject> result = reorderServices.getEndsOrdering(interaction, List.of(messageOccurrenceSpecification1));
		assertTrue(result.size() == 1);
		assertEquals(messageOccurrenceSpecification1, result.get(0));
	}

	@Test
	public void testReorderLifelineNullInteraction() {
		lifeline1 = create(Lifeline.class);
		lifeline2 = create(Lifeline.class);
		assertThrows(NullPointerException.class, () -> reorderServices.reorderLifeline(null, lifeline2, null));
	}

	@Test
	public void testReorderLifelineNullLifeline() {
		createInteractionWithTwoLifelines();
		assertThrows(NullPointerException.class, () -> reorderServices.reorderLifeline(interaction, null, null));
	}

	@Test
	public void testReorderLifelineNullPredecessor() {
		createInteractionWithTwoLifelines();
		reorderServices.reorderLifeline(interaction, lifeline2, null);
		assertEquals(lifeline2, interaction.getLifelines().get(0));
		assertEquals(lifeline1, interaction.getLifelines().get(1));
	}

	@Test
	public void testReorderLifelineLeftToRight() {
		createInteractionWithTwoLifelines();
		reorderServices.reorderLifeline(interaction, lifeline1, lifeline2);
		assertEquals(lifeline2, interaction.getLifelines().get(0));
		assertEquals(lifeline1, interaction.getLifelines().get(1));
	}

	@Test
	public void testReorderLifelineRightToLeft() {
		createInteractionWithTwoLifelines();
		/*
		 * Add a third lifeline to test right to left with a non-null predecessor.
		 */
		Lifeline lifeline3 = create(Lifeline.class);
		interaction.getLifelines().add(lifeline3);
		reorderServices.reorderLifeline(interaction, lifeline3, lifeline1);
		assertEquals(lifeline1, interaction.getLifelines().get(0));
		assertEquals(lifeline3, interaction.getLifelines().get(1));
		assertEquals(lifeline2, interaction.getLifelines().get(2));
	}

	private void createInteractionWithTwoLifelines() {
		this.interaction = create(Interaction.class);
		this.lifeline1 = create(Lifeline.class);
		this.lifeline2 = create(Lifeline.class);
		this.interaction.getLifelines().add(lifeline1);
		this.interaction.getLifelines().add(lifeline2);
	}

	private void createInteractionWithTwoMessageOccurrenceSpecifications() {
		this.interaction = create(Interaction.class);
		this.messageOccurrenceSpecification1 = create(MessageOccurrenceSpecification.class);
		this.messageOccurrenceSpecification2 = create(MessageOccurrenceSpecification.class);
		this.interaction.getFragments().add(messageOccurrenceSpecification1);
		this.interaction.getFragments().add(messageOccurrenceSpecification2);
	}

}
