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
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.OccurrenceSpecification;
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

	@Test
	public void testReorderFragmentNullFragment() {
		// We can use null for the semantic ends here, the method accepts null semantic ends.
		assertThrows(NullPointerException.class, () -> reorderServices.reorderFragment(null, null, null));
	}

	/**
	 * Reorders a single {@link ExecutionSpecification} to be the first fragment of its interaction.
	 * <p>
	 * The interaction doesn't contain other {@link ExecutionSpecification} instances.
	 * <p>
	 * The reordered execution is <i>unordered</i>, which means that the interaction contains the {@link ExecutionSpecification} and its {@link OccurrenceSpecification}s, in this order. The reordered {@link ExecutionSpecification} (and its
	 * {@link OccurrenceSpecification}s) are initially at the end of the interaction's fragment list. This replicates the creation from {@link CommonDiagramServices}.
	 */
	@Test
	public void testReorderFragmentSingleNewExecutionSpecificationToFirst() {
		ExecutionSpecification executionSpecification = addUnorderedExecutionSpecificationToInteraction();
		// reordering to first means that there is no startingEndPredecessor/finishingEndPredecessor
		reorderServices.reorderFragment(executionSpecification, null, null);
		assertContains(interaction.getFragments(), List.of(
				executionSpecification.getStart(),
				executionSpecification,
				executionSpecification.getFinish()));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} to be the first fragment of its interaction.
	 * <p>
	 * The interaction contains another {@link ExecutionSpecification} initially above the reordered one.
	 * <p>
	 * The reordered execution is <i>unordered</i>, which means that the interaction contains the {@link ExecutionSpecification} and its {@link OccurrenceSpecification}s, in this order. The reordered {@link ExecutionSpecification} (and its
	 * {@link OccurrenceSpecification}s) are initially at the end of the interaction's fragment list. This replicates the creation from {@link CommonDiagramServices}.
	 */
	@Test
	public void testReorderFragmentNewExecutionSpecificationToFirst() {
		ExecutionSpecification executionSpecification1 = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification executionSpecification2 = addUnorderedExecutionSpecificationToInteraction();
		// reordering to first means that there is no startingEndPredecessor/finishingEndPredecessor
		reorderServices.reorderFragment(executionSpecification2, null, null);
		assertContains(interaction.getFragments(), List.of(
				executionSpecification2.getStart(),
				executionSpecification2,
				executionSpecification2.getFinish(),
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish()));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} below an existing one.
	 * <p>
	 * The reordered execution is <i>unordered</i>, which means that the interaction contains the {@link ExecutionSpecification} and its {@link OccurrenceSpecification}s, in this order. The reordered {@link ExecutionSpecification} (and its
	 * {@link OccurrenceSpecification}s) are initially at the end of the interaction's fragment list. This replicates the creation from {@link CommonDiagramServices}.
	 */
	@Test
	public void testReorderFragmentNewExecutionSpecificationBelowExistingOne() {
		ExecutionSpecification executionSpecification1 = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification executionSpecification2 = addUnorderedExecutionSpecificationToInteraction();
		// startingEndPredecessor and finishingEndPredecessor are always equal when reordering a new ExecutionSpecification.
		reorderServices.reorderFragment(executionSpecification2, executionSpecification1.getFinish(), executionSpecification1.getFinish());
		List<InteractionFragment> fragments = interaction.getFragments();
		assertContains(fragments, List.of(
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish(),
				executionSpecification2.getStart(),
				executionSpecification2,
				executionSpecification2.getFinish()));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} between two existing ones.
	 * <p>
	 * The reordered execution is <i>unordered</i>, which means that the interaction contains the {@link ExecutionSpecification} and its {@link OccurrenceSpecification}s, in this order. The reordered {@link ExecutionSpecification} (and its
	 * {@link OccurrenceSpecification}s) are initially at the end of the interaction's fragment list. This replicates the creation from {@link CommonDiagramServices}.
	 */
	@Test
	public void testReorderFragmentNewExecutionSpecificationBetweenExistingOnes() {
		ExecutionSpecification executionSpecification1 = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification executionSpecification2 = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification executionSpecification3 = addUnorderedExecutionSpecificationToInteraction();
		// startingEndPredecessor and finishingEndPredecessor are always equal when reordering a new ExecutionSpecification.
		reorderServices.reorderFragment(executionSpecification3, executionSpecification1.getFinish(), executionSpecification1.getFinish());
		assertContains(interaction.getFragments(), List.of(
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish(),
				executionSpecification3.getStart(),
				executionSpecification3,
				executionSpecification3.getFinish(),
				executionSpecification2.getStart(),
				executionSpecification2,
				executionSpecification2.getFinish()));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} inside an enclosing one.
	 * <p>
	 * This happens when moving an {@link ExecutionSpecification} to a location that is covered by another {@link ExecutionSpecification} on a different lifeline. The reordered {@link ExecutionSpecification} is initially below the enclosing one.
	 */
	@Test
	public void testReorderFragmentExecutionSpecificationInsideEnclosingOne() {
		ExecutionSpecification enclosingExecutionSpecification = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification reorderedExecutionSpecification = addOrderedExecutionSpecificationToInteraction();
		// The startingEndPredecessor is the start occurrence of the enclosing execution. The finishingEndPredecessor is the reordered execution's starting end, since there isn't another execution between its start and finish.
		reorderServices.reorderFragment(reorderedExecutionSpecification, enclosingExecutionSpecification.getStart(), reorderedExecutionSpecification.getStart());
		assertContains(interaction.getFragments(), List.of(
				enclosingExecutionSpecification.getStart(),
				enclosingExecutionSpecification,
				reorderedExecutionSpecification.getStart(),
				reorderedExecutionSpecification,
				reorderedExecutionSpecification.getFinish(),
				enclosingExecutionSpecification.getFinish()));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} inside an enclosing one.
	 * <p>
	 * The reordered execution is <i>unordered</i>, which means that the interaction contains the {@link ExecutionSpecification} and its {@link OccurrenceSpecification}s, in this order. The reordered {@link ExecutionSpecification} (and its
	 * {@link OccurrenceSpecification}s) are initially at the end of the interaction's fragment list. This replicates the creation from {@link CommonDiagramServices}.
	 * <p>
	 * The reorder's <code>startingEndPredecessor</code> and <code>finishingEndPredecessor</code> are both equal to the enclosing execution's start. This is typically the case when the reorder happens as part of an {@link ExecutionSpecification} creation.
	 */
	@Test
	public void testReorderFragmentNewExecutionSpecificationInsideEnclosingOne() {
		ExecutionSpecification enclosingExecutionSpecification = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification reorderedExecutionSpecification = addUnorderedExecutionSpecificationToInteraction();
		// The startingEndPredecessor is the start occurrence of the enclosing execution. The finishingEndPredecessor is also the start occurrence of the enclosing execution, this typically happens on execution creation.
		reorderServices.reorderFragment(reorderedExecutionSpecification, enclosingExecutionSpecification.getStart(), enclosingExecutionSpecification.getStart());
		assertContains(interaction.getFragments(), List.of(
				enclosingExecutionSpecification.getStart(),
				enclosingExecutionSpecification,
				reorderedExecutionSpecification.getStart(),
				reorderedExecutionSpecification,
				reorderedExecutionSpecification.getFinish(),
				enclosingExecutionSpecification.getFinish()));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} below its enclosing one.
	 */
	@Test
	public void testReorderFragmentExecutionSpecificationBelowEnclosingOne() {
		ExecutionSpecification enclosingExecutionSpecification = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification reorderedExecutionSpecification = addOrderedExecutionSpecificationToInteraction(enclosingExecutionSpecification);
		// The finishingEndPredecessor is the reordered execution's starting end, since there isn't another execution between its start and finish.
		reorderServices.reorderFragment(reorderedExecutionSpecification, enclosingExecutionSpecification.getFinish(), reorderedExecutionSpecification.getStart());
		assertContains(interaction.getFragments(), List.of(
				enclosingExecutionSpecification.getStart(),
				enclosingExecutionSpecification,
				enclosingExecutionSpecification.getFinish(),
				reorderedExecutionSpecification.getStart(),
				reorderedExecutionSpecification,
				reorderedExecutionSpecification.getFinish()));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} above its enclosing one, and below another {@link ExecutionSpecification}.
	 */
	@Test
	public void testReorderFragmentExecutionSpecificationAboveEnclosingOne() {
		ExecutionSpecification executionSpecification1 = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification enclosingExecutionSpecification = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification reorderedExecutionSpecification = addOrderedExecutionSpecificationToInteraction(enclosingExecutionSpecification);
		reorderServices.reorderFragment(reorderedExecutionSpecification, executionSpecification1.getFinish(), reorderedExecutionSpecification.getStart());
		assertContains(interaction.getFragments(), List.of(
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish(),
				reorderedExecutionSpecification.getStart(),
				reorderedExecutionSpecification,
				reorderedExecutionSpecification.getFinish(),
				enclosingExecutionSpecification.getStart(),
				enclosingExecutionSpecification,
				enclosingExecutionSpecification.getFinish()));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} above its enclosing one, but with its finishing end still enclosed.
	 * <p>
	 * This happens when the enclosing {@link ExecutionSpecification} is on another lifeline, and the reordered {@link ExecutionSpecification} is still partially covered by it after the reorder operation.
	 */
	@Test
	public void testReorderFragmentExecutionSpecificationAboveEnclosingOneWithFinishingEndStillEnclosed() {
		ExecutionSpecification executionSpecification1 = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification enclosingExecutionSpecification = addOrderedExecutionSpecificationToInteraction();
		ExecutionSpecification reorderedExecutionSpecification = addOrderedExecutionSpecificationToInteraction(enclosingExecutionSpecification);
		// The finishingEndPredecessor is the enclosing execution's start since the finish end of the reordered execution is still enclosed in it.
		reorderServices.reorderFragment(reorderedExecutionSpecification, executionSpecification1.getFinish(), enclosingExecutionSpecification.getStart());
		assertContains(interaction.getFragments(), List.of(
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish(),
				reorderedExecutionSpecification.getStart(),
				reorderedExecutionSpecification,
				enclosingExecutionSpecification.getStart(),
				enclosingExecutionSpecification,
				reorderedExecutionSpecification.getFinish(),
				enclosingExecutionSpecification.getFinish()));
	}

	private void assertContains(List<?> from, List<?> content) {
		assertEquals(content.size(), from.size());
		for (int i = 0; i < content.size(); i++) {
			assertTrue(from.get(i).equals(content.get(i)));
		}
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

	private ExecutionSpecification addUnorderedExecutionSpecificationToInteraction() {
		if (this.interaction == null) {
			this.interaction = create(Interaction.class);
		}
		ExecutionSpecification executionSpecification = create(ActionExecutionSpecification.class);
		ExecutionOccurrenceSpecification start = create(ExecutionOccurrenceSpecification.class);
		start.setExecution(executionSpecification);
		executionSpecification.setStart(start);
		ExecutionOccurrenceSpecification finish = create(ExecutionOccurrenceSpecification.class);
		finish.setExecution(executionSpecification);
		executionSpecification.setFinish(finish);
		interaction.getFragments().add(executionSpecification);
		interaction.getFragments().add(start);
		interaction.getFragments().add(finish);
		return executionSpecification;
	}

	private ExecutionSpecification addOrderedExecutionSpecificationToInteraction() {
		return this.addOrderedExecutionSpecificationToInteraction(null);
	}

	private ExecutionSpecification addOrderedExecutionSpecificationToInteraction(ExecutionSpecification enclosingExecution) {
		if (this.interaction == null) {
			this.interaction = create(Interaction.class);
		}
		ExecutionSpecification executionSpecification = create(ActionExecutionSpecification.class);
		ExecutionOccurrenceSpecification start = create(ExecutionOccurrenceSpecification.class);
		start.setExecution(executionSpecification);
		executionSpecification.setStart(start);
		ExecutionOccurrenceSpecification finish = create(ExecutionOccurrenceSpecification.class);
		finish.setExecution(executionSpecification);
		executionSpecification.setFinish(finish);
		if (enclosingExecution == null) {
			interaction.getFragments().add(start);
			interaction.getFragments().add(executionSpecification);
			interaction.getFragments().add(finish);
		} else {
			int enclosingExecutionIndex = interaction.getFragments().indexOf(enclosingExecution);
			interaction.getFragments().add(enclosingExecutionIndex + 1, start);
			interaction.getFragments().add(enclosingExecutionIndex + 2, executionSpecification);
			interaction.getFragments().add(enclosingExecutionIndex + 3, finish);
		}
		return executionSpecification;
	}

}
