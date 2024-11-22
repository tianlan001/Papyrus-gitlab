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
package org.eclipse.papyrus.sirius.uml.diagram.sequence.tests.services.reorder;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramOrderServices;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramReorderElementSwitch;
import org.eclipse.uml2.uml.ActionExecutionSpecification;
import org.eclipse.uml2.uml.CombinedFragment;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.InteractionUse;
import org.eclipse.uml2.uml.Lifeline;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link SequenceDiagramReorderElementSwitch}.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramReorderElementSwitchTest extends AbstractServicesTest {

	private SequenceDiagramOrderServices orderService;

	private Interaction interaction;

	private Lifeline lifeline1;

	private Lifeline lifeline2;

	@Before
	public void setUp() {
		orderService = new SequenceDiagramOrderServices();

		interaction = createInteractionWithTwoLifelines();
	}

	@Test
	public void testReorderFragmentNullFragment() {
		// We can use null for the semantic ends here, the switch accepts null semantic ends.
		assertThrows(NullPointerException.class, () -> new SequenceDiagramReorderElementSwitch(null, null).doSwitch(null));
	}

	/**
	 * Reorders an {@link ExecutionSpecification} to be the first fragment of its interaction.
	 */
	@Test
	public void testReorderSingleExecutionToFirst() {
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		// reordering to first means that there is no startingEndPredecessor/finishingEndPredecessor
		new SequenceDiagramReorderElementSwitch(null, null).doSwitch(executionSpecification);
		assertListEquals(orderService.getEndsOrdering(interaction), List.of(
				orderService.getStartingEnd(executionSpecification),
				orderService.getFinishingEnd(executionSpecification)));
		assertListEquals(interaction.getFragments(), List.of(
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
		new SequenceDiagramReorderElementSwitch(null, null).doSwitch(executionSpecification2);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(executionSpecification2),
				this.orderService.getFinishingEnd(executionSpecification2),
				this.orderService.getStartingEnd(executionSpecification1),
				this.orderService.getFinishingEnd(executionSpecification1)));
		assertListEquals(interaction.getFragments(), List.of(
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
		new SequenceDiagramReorderElementSwitch(this.orderService.getFinishingEnd(executionSpecification2), this.orderService.getStartingEnd(executionSpecification1))
				.doSwitch(executionSpecification1);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(executionSpecification2),
				this.orderService.getFinishingEnd(executionSpecification2),
				this.orderService.getStartingEnd(executionSpecification1),
				this.orderService.getFinishingEnd(executionSpecification1)));
		assertListEquals(interaction.getFragments(), List.of(
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
		new SequenceDiagramReorderElementSwitch(this.orderService.getFinishingEnd(executionSpecification1), this.orderService.getStartingEnd(executionSpecification3))
				.doSwitch(executionSpecification3);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(executionSpecification1),
				this.orderService.getFinishingEnd(executionSpecification1),
				this.orderService.getStartingEnd(executionSpecification3),
				this.orderService.getFinishingEnd(executionSpecification3),
				this.orderService.getStartingEnd(executionSpecification2),
				this.orderService.getFinishingEnd(executionSpecification2)));
		assertListEquals(interaction.getFragments(), List.of(
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
		new SequenceDiagramReorderElementSwitch(this.orderService.getStartingEnd(combinedFragment.getOperands().get(0)), this.orderService.getStartingEnd(executionSpecification))
				.doSwitch(executionSpecification);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(combinedFragment),
				this.orderService.getStartingEnd(combinedFragment.getOperands().get(0)),
				this.orderService.getStartingEnd(executionSpecification),
				this.orderService.getFinishingEnd(executionSpecification),
				this.orderService.getFinishingEnd(combinedFragment)));
		assertListEquals(interaction.getFragments(), List.of(combinedFragment));
		assertListEquals(combinedFragment.getOperands().get(0).getFragments(), List.of(
				executionSpecification.getStart(),
				executionSpecification,
				executionSpecification.getFinish()));
	}

	@Test
	public void testReorderMessageFirst() {
		ExecutionSpecification executionSpecification1 = createExecutionSpecification(interaction, lifeline1);
		Message message = createMessage(interaction, lifeline1, lifeline2);
		new SequenceDiagramReorderElementSwitch(null, null)
				.doSwitch(message);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(message),
				this.orderService.getFinishingEnd(message),
				this.orderService.getStartingEnd(executionSpecification1),
				this.orderService.getFinishingEnd(executionSpecification1)));
		assertListEquals(interaction.getFragments(), List.of(
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
		new SequenceDiagramReorderElementSwitch(this.orderService.getFinishingEnd(executionSpecification), this.orderService.getStartingEnd(message))
				.doSwitch(message);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(executionSpecification),
				this.orderService.getFinishingEnd(executionSpecification),
				this.orderService.getStartingEnd(message),
				this.orderService.getFinishingEnd(message)));
		assertListEquals(interaction.getFragments(), List.of(
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
		new SequenceDiagramReorderElementSwitch(this.orderService.getFinishingEnd(executionSpecification1), this.orderService.getStartingEnd(message))
				.doSwitch(message);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(executionSpecification1),
				this.orderService.getFinishingEnd(executionSpecification1),
				this.orderService.getStartingEnd(message),
				this.orderService.getFinishingEnd(message),
				this.orderService.getStartingEnd(executionSpecification2),
				this.orderService.getFinishingEnd(executionSpecification2)));
		assertListEquals(interaction.getFragments(), List.of(
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
		new SequenceDiagramReorderElementSwitch(this.orderService.getStartingEnd(executionSpecification1), this.orderService.getStartingEnd(message))
				.doSwitch(message);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(executionSpecification1),
				this.orderService.getStartingEnd(message),
				this.orderService.getFinishingEnd(message),
				this.orderService.getFinishingEnd(executionSpecification1)));
		assertListEquals(interaction.getFragments(), List.of(
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
		new SequenceDiagramReorderElementSwitch(this.orderService.getStartingEnd(combinedFragment.getOperands().get(0)), this.orderService.getStartingEnd(message))
				.doSwitch(message);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(combinedFragment),
				this.orderService.getStartingEnd(combinedFragment.getOperands().get(0)),
				this.orderService.getStartingEnd(message),
				this.orderService.getFinishingEnd(message),
				this.orderService.getFinishingEnd(combinedFragment)));
		assertListEquals(interaction.getFragments(), List.of(combinedFragment));
		assertListEquals(combinedFragment.getOperands().get(0).getFragments(), List.of(
				message.getSendEvent(),
				message.getReceiveEvent()));
	}

	@Test
	public void testReorderCombinedFragmentFirst() {
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		CombinedFragment combinedFragment = createCombinedFragment(interaction, lifeline1);
		new SequenceDiagramReorderElementSwitch(null, null)
				.doSwitch(combinedFragment);
		assertEquals(1, combinedFragment.getOperands().size());
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(combinedFragment),
				this.orderService.getStartingEnd(combinedFragment.getOperands().get(0)),
				this.orderService.getFinishingEnd(combinedFragment),
				this.orderService.getStartingEnd(executionSpecification),
				this.orderService.getFinishingEnd(executionSpecification)));
		assertListEquals(interaction.getFragments(), List.of(
				combinedFragment,
				executionSpecification.getStart(),
				executionSpecification,
				executionSpecification.getFinish()));
	}

	@Test
	public void testReorderCombinedFragmentAfterExecution() {
		CombinedFragment combinedFragment = createCombinedFragment(interaction, lifeline1);
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		new SequenceDiagramReorderElementSwitch(this.orderService.getFinishingEnd(executionSpecification), this.orderService.getStartingEnd(combinedFragment))
				.doSwitch(combinedFragment);
		assertEquals(1, combinedFragment.getOperands().size());
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(executionSpecification),
				this.orderService.getFinishingEnd(executionSpecification),
				this.orderService.getStartingEnd(combinedFragment),
				this.orderService.getStartingEnd(combinedFragment.getOperands().get(0)),
				this.orderService.getFinishingEnd(combinedFragment)));
		assertListEquals(interaction.getFragments(), List.of(
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
		new SequenceDiagramReorderElementSwitch(this.orderService.getFinishingEnd(executionSpecification1), this.orderService.getStartingEnd(combinedFragment))
				.doSwitch(combinedFragment);
		assertEquals(1, combinedFragment.getOperands().size());
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(executionSpecification1),
				this.orderService.getFinishingEnd(executionSpecification1),
				this.orderService.getStartingEnd(combinedFragment),
				this.orderService.getStartingEnd(combinedFragment.getOperands().get(0)),
				this.orderService.getFinishingEnd(combinedFragment),
				this.orderService.getStartingEnd(executionSpecification2),
				this.orderService.getFinishingEnd(executionSpecification2)));
		assertListEquals(interaction.getFragments(), List.of(
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish(),
				combinedFragment,
				executionSpecification2.getStart(),
				executionSpecification2,
				executionSpecification2.getFinish()));
	}

	@Test
	public void testReorderInteractionUseFirst() {
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		InteractionUse interactionUse = createInteractionUse(interaction, lifeline1);
		new SequenceDiagramReorderElementSwitch(null, null)
				.doSwitch(interactionUse);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(interactionUse),
				this.orderService.getFinishingEnd(interactionUse),
				this.orderService.getStartingEnd(executionSpecification),
				this.orderService.getFinishingEnd(executionSpecification)));
		assertListEquals(interaction.getFragments(), List.of(
				interactionUse,
				executionSpecification.getStart(),
				executionSpecification,
				executionSpecification.getFinish()));
	}

	@Test
	public void testReorderInteractionUseAfterExecution() {
		InteractionUse interactionUse = createInteractionUse(interaction, lifeline1);
		ExecutionSpecification executionSpecification = createExecutionSpecification(interaction, lifeline1);
		new SequenceDiagramReorderElementSwitch(this.orderService.getFinishingEnd(executionSpecification), this.orderService.getStartingEnd(interactionUse))
				.doSwitch(interactionUse);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(executionSpecification),
				this.orderService.getFinishingEnd(executionSpecification),
				this.orderService.getStartingEnd(interactionUse),
				this.orderService.getFinishingEnd(interactionUse)));
		assertListEquals(interaction.getFragments(), List.of(
				executionSpecification.getStart(),
				executionSpecification,
				executionSpecification.getFinish(),
				interactionUse));
	}

	@Test
	public void testReorderInteractionUseBetweenExecutions() {
		ExecutionSpecification executionSpecification1 = createExecutionSpecification(interaction, lifeline1);
		ExecutionSpecification executionSpecification2 = createExecutionSpecification(interaction, lifeline1);
		InteractionUse interactionUse = createInteractionUse(interaction, lifeline1);
		new SequenceDiagramReorderElementSwitch(this.orderService.getFinishingEnd(executionSpecification1), this.orderService.getStartingEnd(interactionUse))
				.doSwitch(interactionUse);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(executionSpecification1),
				this.orderService.getFinishingEnd(executionSpecification1),
				this.orderService.getStartingEnd(interactionUse),
				this.orderService.getFinishingEnd(interactionUse),
				this.orderService.getStartingEnd(executionSpecification2),
				this.orderService.getFinishingEnd(executionSpecification2)));
		assertListEquals(interaction.getFragments(), List.of(
				executionSpecification1.getStart(),
				executionSpecification1,
				executionSpecification1.getFinish(),
				interactionUse,
				executionSpecification2.getStart(),
				executionSpecification2,
				executionSpecification2.getFinish()));
	}

	@Test
	public void testReorderInteractionUseInsideInteractionOperand() {
		CombinedFragment combinedFragment = createCombinedFragment(interaction, lifeline1, lifeline2);
		InteractionUse interactionUse = createInteractionUse(interaction, lifeline1);
		assertEquals(1, combinedFragment.getOperands().size());
		new SequenceDiagramReorderElementSwitch(this.orderService.getStartingEnd(combinedFragment.getOperands().get(0)), this.orderService.getStartingEnd(interactionUse))
				.doSwitch(interactionUse);
		assertListEquals(this.orderService.getEndsOrdering(interaction), List.of(
				this.orderService.getStartingEnd(combinedFragment),
				this.orderService.getStartingEnd(combinedFragment.getOperands().get(0)),
				this.orderService.getStartingEnd(interactionUse),
				this.orderService.getFinishingEnd(interactionUse),
				this.orderService.getFinishingEnd(combinedFragment)));
		assertListEquals(interaction.getFragments(), List.of(combinedFragment));
		assertListEquals(combinedFragment.getOperands().get(0).getFragments(), List.of(interactionUse));
	}

	private void assertListEquals(List<?> source, List<?> target) {
		assertArrayEquals(source.toArray(), target.toArray());
	}

	private Interaction createInteractionWithTwoLifelines() {
		Interaction i = create(Interaction.class);
		orderService.refreshEndsModel(i);
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
		this.orderService.createStartingEnd(executionSpecification);
		this.orderService.createFinishingEnd(executionSpecification);
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
		this.orderService.createStartingEnd(message);
		this.orderService.createFinishingEnd(message);
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
		this.orderService.createStartingEnd(combinedFragment);
		this.orderService.createStartingEnd(interactionOperand);
		this.orderService.createFinishingEnd(combinedFragment);
		return combinedFragment;
	}

	private InteractionUse createInteractionUse(Element parent, Lifeline... lifelines) {
		InteractionUse interactionUse = create(InteractionUse.class);
		interactionUse.getCovereds().addAll(Arrays.asList(lifelines));
		if (parent instanceof Interaction interaction) {
			interaction.getFragments().add(interactionUse);
		} else if (parent instanceof InteractionOperand operand) {
			operand.getFragments().add(interactionUse);
		}
		this.orderService.createStartingEnd(interactionUse);
		this.orderService.createFinishingEnd(interactionUse);
		return interactionUse;
	}
}
