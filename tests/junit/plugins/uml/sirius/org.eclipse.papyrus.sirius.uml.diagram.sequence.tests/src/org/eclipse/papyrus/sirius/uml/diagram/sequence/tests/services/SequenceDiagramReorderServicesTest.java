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

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramReorderServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramServices;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
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
		interaction = createInteractionWithTwoLifelines();
		interaction.createEAnnotation(SequenceDiagramServices.ORDERING_ANNOTATION_SOURCE);
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
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		Interaction emptyInteraction = createInteractionWithTwoLifelines();
		List<EObject> result = reorderServices.getEndsOrdering(emptyInteraction, List.of(getStartAnnotation(executionSpecification), getFinishAnnotation(executionSpecification)));
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetEndsOrderingEmptyEventEnds() {
		createExecutionSpecification(interaction, lifeline1);
		List<EObject> result = reorderServices.getEndsOrdering(interaction, List.of());
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetEndsOrderingSubsetEventEnds() {
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		List<EObject> result = reorderServices.getEndsOrdering(interaction, List.of(getStartAnnotation(executionSpecification)));
		assertEquals(1, result.size());
		assertEquals(getStartAnnotation(executionSpecification), result.get(0));
	}

	@Test
	public void testReorderLifelineNullInteraction() {
		lifeline1 = create(Lifeline.class);
		lifeline2 = create(Lifeline.class);
		assertThrows(NullPointerException.class, () -> reorderServices.reorderLifeline(null, lifeline2, null));
	}

	@Test
	public void testReorderLifelineNullLifeline() {
		assertThrows(NullPointerException.class, () -> reorderServices.reorderLifeline(interaction, null, null));
	}

	@Test
	public void testReorderLifelineNullPredecessor() {
		reorderServices.reorderLifeline(interaction, lifeline2, null);
		assertEquals(lifeline2, interaction.getLifelines().get(0));
		assertEquals(lifeline1, interaction.getLifelines().get(1));
	}

	@Test
	public void testReorderLifelineLeftToRight() {
		reorderServices.reorderLifeline(interaction, lifeline1, lifeline2);
		assertEquals(lifeline2, interaction.getLifelines().get(0));
		assertEquals(lifeline1, interaction.getLifelines().get(1));
	}

	@Test
	public void testReorderLifelineRightToLeft() {
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
	 * Reorders an {@link ExecutionSpecification} to be the first fragment of its interaction.
	 */
	@Test
	public void testReorderSingleExecutionToFirst() {
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		// reordering to first means that there is no startingEndPredecessor/finishingEndPredecessor
		reorderServices.reorderFragment(executionSpecification, null, null);
		assertContains(getOrdering(), List.of(this.getStartAnnotation(executionSpecification), this.getFinishAnnotation(executionSpecification)));
		assertContains(interaction.getFragments(), List.of(
				executionSpecification.getStart(),
				executionSpecification,
				executionSpecification.getFinish()));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} to be the first fragment of its interaction.
	 * <p>
	 * The interaction contains another {@link ExecutionSpecification} initially above the reordered one.
	 */
	@Test
	public void testReorderExecutionToFirst() {
		ExecutionSpecification executionSpecification1 = createExecutionSpecification(interaction, lifeline1);
		ExecutionSpecification executionSpecification2 = createExecutionSpecification(interaction, lifeline1);
		// reordering to first means that there is no startingEndPredecessor/finishingEndPredecessor
		reorderServices.reorderFragment(executionSpecification2, null, null);
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(executionSpecification2),
				this.getFinishAnnotation(executionSpecification2),
				this.getStartAnnotation(executionSpecification1),
				this.getFinishAnnotation(executionSpecification1)));
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
	 */
	@Test
	public void testReorderExecutionAfterExecution() {
		ExecutionSpecification executionSpecification1 = createExecutionSpecification(interaction, lifeline1);
		ExecutionSpecification executionSpecification2 = createExecutionSpecification(interaction, lifeline1);
		reorderServices.reorderFragment(executionSpecification1, getFinishAnnotation(executionSpecification2), getStartAnnotation(executionSpecification1));
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(executionSpecification2),
				this.getFinishAnnotation(executionSpecification2),
				this.getStartAnnotation(executionSpecification1),
				this.getFinishAnnotation(executionSpecification1)));
		assertContains(interaction.getFragments(), List.of(
				executionSpecification2.getStart(),
				executionSpecification2,
				executionSpecification2.getFinish(),
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish()));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} between two existing ones.
	 */
	@Test
	public void testReorderExecutionBetweenExecutions() {
		ExecutionSpecification executionSpecification1 = createExecutionSpecification(interaction, lifeline1);
		ExecutionSpecification executionSpecification2 = createExecutionSpecification(interaction, lifeline1);
		ExecutionSpecification executionSpecification3 = createExecutionSpecification(interaction, lifeline1);
		reorderServices.reorderFragment(executionSpecification3, getFinishAnnotation(executionSpecification1), getStartAnnotation(executionSpecification3));
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(executionSpecification1),
				this.getFinishAnnotation(executionSpecification1),
				this.getStartAnnotation(executionSpecification3),
				this.getFinishAnnotation(executionSpecification3),
				this.getStartAnnotation(executionSpecification2),
				this.getFinishAnnotation(executionSpecification2)));
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

	@Test
	public void testReorderExecutionInsideCombinedFragment() {
		CombinedFragment combinedFragment = createCombinedFragment(interaction, lifeline1);
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		assertEquals(1, combinedFragment.getOperands().size());
		reorderServices.reorderFragment(executionSpecification, getStartAnnotation(combinedFragment.getOperands().get(0)), getStartAnnotation(executionSpecification));
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(combinedFragment),
				this.getStartAnnotation(combinedFragment.getOperands().get(0)),
				this.getStartAnnotation(executionSpecification),
				this.getFinishAnnotation(executionSpecification),
				this.getFinishAnnotation(combinedFragment)));
		assertContains(interaction.getFragments(), List.of(combinedFragment));
		assertContains(combinedFragment.getOperands().get(0).getFragments(), List.of(
				executionSpecification.getStart(),
				executionSpecification,
				executionSpecification.getFinish()));
	}

	@Test
	public void testReorderMessageFirst() {
		ExecutionSpecification executionSpecification1 = createExecutionSpecification(interaction, lifeline1);
		Message message = createMessage(interaction, lifeline1, lifeline2);
		reorderServices.reorderMessage(message, null, null);
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(message),
				this.getFinishAnnotation(message),
				this.getStartAnnotation(executionSpecification1),
				this.getFinishAnnotation(executionSpecification1)));
		assertContains(interaction.getFragments(), List.of(
				message.getSendEvent(),
				message.getReceiveEvent(),
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish()));
	}

	@Test
	public void testReorderMessageAfterExecution() {
		Message message = createMessage(interaction, lifeline1, lifeline2);
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		reorderServices.reorderMessage(message, getFinishAnnotation(executionSpecification), getStartAnnotation(message));
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(executionSpecification),
				this.getFinishAnnotation(executionSpecification),
				this.getStartAnnotation(message),
				this.getFinishAnnotation(message)));
		assertContains(interaction.getFragments(), List.of(
				executionSpecification.getStart(),
				executionSpecification,
				executionSpecification.getFinish(),
				message.getSendEvent(),
				message.getReceiveEvent()));
	}

	@Test
	public void testReorderMessageBetweenExecutions() {
		ExecutionSpecification executionSpecification1 = createExecutionSpecification(interaction, lifeline1);
		ExecutionSpecification executionSpecification2 = createExecutionSpecification(interaction, lifeline1);
		Message message = createMessage(interaction, lifeline1, lifeline2);
		reorderServices.reorderMessage(message, getFinishAnnotation(executionSpecification1), getStartAnnotation(message));
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(executionSpecification1),
				this.getFinishAnnotation(executionSpecification1),
				this.getStartAnnotation(message),
				this.getFinishAnnotation(message),
				this.getStartAnnotation(executionSpecification2),
				this.getFinishAnnotation(executionSpecification2)));
		assertContains(interaction.getFragments(), List.of(
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish(),
				message.getSendEvent(),
				message.getReceiveEvent(),
				executionSpecification2.getStart(),
				executionSpecification2,
				executionSpecification2.getFinish()));

	}

	@Test
	public void testReorderMessageInsideExecution() {
		ExecutionSpecification executionSpecification1 = createExecutionSpecification(interaction, lifeline1);
		Message message = createMessage(interaction, lifeline1, lifeline2);
		reorderServices.reorderMessage(message, getStartAnnotation(executionSpecification1), getStartAnnotation(message));
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(executionSpecification1),
				this.getStartAnnotation(message),
				this.getFinishAnnotation(message),
				this.getFinishAnnotation(executionSpecification1)));
		assertContains(interaction.getFragments(), List.of(
				executionSpecification1.getStart(),
				executionSpecification1,
				message.getSendEvent(),
				message.getReceiveEvent(),
				executionSpecification1.getFinish()));
	}

	@Test
	public void testReorderMessageInsideInteractionOperand() {
		CombinedFragment combinedFragment = createCombinedFragment(interaction, lifeline1, lifeline2);
		Message message = createMessage(interaction, lifeline1, lifeline2);
		assertEquals(1, combinedFragment.getOperands().size());
		reorderServices.reorderMessage(message, getStartAnnotation(combinedFragment.getOperands().get(0)), getStartAnnotation(message));
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(combinedFragment),
				this.getStartAnnotation(combinedFragment.getOperands().get(0)),
				this.getStartAnnotation(message),
				this.getFinishAnnotation(message),
				this.getFinishAnnotation(combinedFragment)));
		assertContains(interaction.getFragments(), List.of(combinedFragment));
		assertContains(combinedFragment.getOperands().get(0).getFragments(), List.of(
				message.getSendEvent(),
				message.getReceiveEvent()));
	}

	@Test
	public void testReorderCombinedFragmentFirst() {
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		CombinedFragment combinedFragment = createCombinedFragment(interaction, lifeline1);
		reorderServices.reorderFragment(combinedFragment, null, null);
		assertEquals(1, combinedFragment.getOperands().size());
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(combinedFragment),
				this.getStartAnnotation(combinedFragment.getOperands().get(0)),
				this.getFinishAnnotation(combinedFragment),
				this.getStartAnnotation(executionSpecification),
				this.getFinishAnnotation(executionSpecification)));
		assertContains(interaction.getFragments(), List.of(
				combinedFragment,
				executionSpecification.getStart(),
				executionSpecification,
				executionSpecification.getFinish()));
	}

	@Test
	public void testReorderCombinedFragmentAfterExecution() {
		CombinedFragment combinedFragment = createCombinedFragment(interaction, lifeline1);
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		reorderServices.reorderFragment(combinedFragment, getFinishAnnotation(executionSpecification), getStartAnnotation(combinedFragment));
		assertEquals(1, combinedFragment.getOperands().size());
		assertContains(getOrdering(), List.of(
				this.getStartAnnotation(executionSpecification),
				this.getFinishAnnotation(executionSpecification),
				this.getStartAnnotation(combinedFragment),
				this.getStartAnnotation(combinedFragment.getOperands().get(0)),
				this.getFinishAnnotation(combinedFragment)));
		assertContains(interaction.getFragments(), List.of(
				executionSpecification.getStart(),
				executionSpecification,
				executionSpecification.getFinish(),
				combinedFragment));
	}

	@Test
	public void testReorderCombinedFragmentBetweenExecutions() {
		ExecutionSpecification executionSpecification1 = createExecutionSpecification(interaction, lifeline1);
		ExecutionSpecification executionSpecification2 = createExecutionSpecification(interaction, lifeline1);
		CombinedFragment combinedFragment = createCombinedFragment(interaction, lifeline1);
		reorderServices.reorderFragment(combinedFragment, getFinishAnnotation(executionSpecification1), getStartAnnotation(combinedFragment));
		assertEquals(1, combinedFragment.getOperands().size());
		assertContains(getOrdering(), List.of(
				getStartAnnotation(executionSpecification1),
				getFinishAnnotation(executionSpecification1),
				getStartAnnotation(combinedFragment),
				getStartAnnotation(combinedFragment.getOperands().get(0)),
				getFinishAnnotation(combinedFragment),
				getStartAnnotation(executionSpecification2),
				getFinishAnnotation(executionSpecification2)));
		assertContains(interaction.getFragments(), List.of(
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish(),
				combinedFragment,
				executionSpecification2.getStart(),
				executionSpecification2,
				executionSpecification2.getFinish()));
	}



	private void assertContains(List<?> from, List<?> content) {
		assertEquals(content.size(), from.size());
		for (int i = 0; i < content.size(); i++) {
			assertTrue(from.get(i).equals(content.get(i)));
		}
	}

	private Interaction createInteractionWithTwoLifelines() {
		Interaction i = create(Interaction.class);
		this.lifeline1 = create(Lifeline.class);
		this.lifeline2 = create(Lifeline.class);
		i.getLifelines().add(lifeline1);
		i.getLifelines().add(lifeline2);
		return i;
	}

	private ExecutionSpecification createExecutionSpecification(Element parent, Lifeline lifeline) {
		ExecutionSpecification executionSpecification = create(ActionExecutionSpecification.class);
		executionSpecification.getCovereds().add(lifeline);
		ExecutionOccurrenceSpecification start = create(ExecutionOccurrenceSpecification.class);
		start.getCovereds().add(lifeline);
		start.setExecution(executionSpecification);
		executionSpecification.setStart(start);
		ExecutionOccurrenceSpecification finish = create(ExecutionOccurrenceSpecification.class);
		finish.getCovereds().add(lifeline);
		finish.setExecution(executionSpecification);
		executionSpecification.setFinish(finish);
		if (parent instanceof Interaction interaction) {
			interaction.getFragments().add(start);
			interaction.getFragments().add(executionSpecification);
			interaction.getFragments().add(finish);
		} else if (parent instanceof InteractionOperand operand) {
			operand.getFragments().add(start);
			operand.getFragments().add(executionSpecification);
			operand.getFragments().add(finish);
		}
		this.getOrdering().add(createAnnotationWithReference(start, executionSpecification, SequenceDiagramServices.START_ANNOTATION_SOURCE));
		this.getOrdering().add(createAnnotationWithReference(finish, executionSpecification, SequenceDiagramServices.END_ANNOTATION_SOURCE));
		return executionSpecification;
	}

	private Message createMessage(Interaction parent, Lifeline sendLifeline, Lifeline receiveLifeline) {
		Message message = create(Message.class);
		MessageOccurrenceSpecification sendEvent = create(MessageOccurrenceSpecification.class);
		sendEvent.getCovereds().add(sendLifeline);
		sendEvent.setMessage(message);
		message.setSendEvent(sendEvent);
		MessageOccurrenceSpecification receiveEvent = create(MessageOccurrenceSpecification.class);
		receiveEvent.getCovereds().add(receiveLifeline);
		receiveEvent.setMessage(message);
		message.setReceiveEvent(receiveEvent);
		interaction.getMessages().add(message);
		interaction.getFragments().add(sendEvent);
		interaction.getFragments().add(receiveEvent);
		this.getOrdering().add(createAnnotationWithReference(sendEvent, message, SequenceDiagramServices.START_ANNOTATION_SOURCE));
		this.getOrdering().add(createAnnotationWithReference(receiveEvent, message, SequenceDiagramServices.END_ANNOTATION_SOURCE));
		return message;
	}

	private CombinedFragment createCombinedFragment(Element parent, Lifeline... lifelines) {
		CombinedFragment combinedFragment = create(CombinedFragment.class);
		combinedFragment.getCovereds().addAll(Arrays.asList(lifelines));
		InteractionOperand interactionOperand = create(InteractionOperand.class);
		interactionOperand.getCovereds().addAll(Arrays.asList(lifelines));
		combinedFragment.getOperands().add(interactionOperand);
		if (parent instanceof Interaction interaction) {
			interaction.getFragments().add(combinedFragment);
		} else if (parent instanceof InteractionOperand operand) {
			operand.getFragments().add(interactionOperand);
		}
		this.getOrdering().add(createAnnotationWithReference(combinedFragment, null, SequenceDiagramServices.START_ANNOTATION_SOURCE));
		this.getOrdering().add(createAnnotationWithReference(interactionOperand, null, SequenceDiagramServices.START_ANNOTATION_SOURCE));
		this.getOrdering().add(createAnnotationWithReference(combinedFragment, null, SequenceDiagramServices.END_ANNOTATION_SOURCE));
		return combinedFragment;

	}

	private EAnnotation createAnnotationWithReference(InteractionFragment fragment, Element baseElement, String source) {
		Objects.requireNonNull(fragment);
		Objects.requireNonNull(source);
		EAnnotation annotation = EcoreFactory.eINSTANCE.createEAnnotation();
		annotation.setSource(source);
		annotation.getReferences().add(fragment);
		if (baseElement != null) {
			annotation.getReferences().add(baseElement);
		}
		return annotation;
	}

	private EAnnotation getStartAnnotation(Element element) {
		return getAnnotation(element, SequenceDiagramServices.START_ANNOTATION_SOURCE);
	}

	private EAnnotation getFinishAnnotation(Element element) {
		return getAnnotation(element, SequenceDiagramServices.END_ANNOTATION_SOURCE);
	}

	private EAnnotation getAnnotation(Element element, String source) {
		return this.getOrdering().stream()
				.filter(EAnnotation.class::isInstance)
				.map(EAnnotation.class::cast)
				.filter(annotation -> Objects.equals(annotation.getSource(), source) && annotation.getReferences().contains(element))
				.findFirst()
				.orElse(null);
	}

	private List<EObject> getOrdering() {
		return this.interaction.getEAnnotation(SequenceDiagramServices.ORDERING_ANNOTATION_SOURCE).getContents();
	}
}
