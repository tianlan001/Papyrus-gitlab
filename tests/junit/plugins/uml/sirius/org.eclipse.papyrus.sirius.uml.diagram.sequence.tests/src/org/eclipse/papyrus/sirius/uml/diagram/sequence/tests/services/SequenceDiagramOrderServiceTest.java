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

import java.util.List;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.SequenceDiagramOrderServices;
import org.eclipse.uml2.uml.BehaviorExecutionSpecification;
import org.eclipse.uml2.uml.ExecutionOccurrenceSpecification;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.Interaction;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link SequenceDiagramOrderServices} services.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramOrderServiceTest extends AbstractServicesTest {

	private SequenceDiagramOrderServices orderService;

	private Interaction interaction;

	private ExecutionSpecification executionSpecification;

	private EAnnotation executionSpecificationStartAnnotation;

	private EAnnotation executionSpecificationFinishAnnotation;

	private Interaction createInteraction() {
		Interaction result = create(Interaction.class);
		orderService.refreshEndsModel(result);
		return result;
	}

	@Before
	public void setUp() {
		orderService = new SequenceDiagramOrderServices();
		interaction = createInteraction();
		executionSpecification = create(BehaviorExecutionSpecification.class);

		ExecutionOccurrenceSpecification start = create(ExecutionOccurrenceSpecification.class);
		start.setExecution(executionSpecification);
		executionSpecification.setStart(start);
		interaction.getFragments().add(start);

		interaction.getFragments().add(executionSpecification);

		ExecutionOccurrenceSpecification finish = create(ExecutionOccurrenceSpecification.class);
		finish.setExecution(executionSpecification);
		executionSpecification.setFinish(finish);
		interaction.getFragments().add(finish);

		executionSpecificationStartAnnotation = orderService.createStartingEnd(executionSpecification);
		executionSpecificationFinishAnnotation = orderService.createFinishingEnd(executionSpecification);
	}

	@Test
	public void testGetEndsOrderingNullInteraction() {
		assertThrows(NullPointerException.class, () -> orderService.getEndsOrdering(null, List.of(executionSpecificationStartAnnotation, executionSpecificationFinishAnnotation)));
	}

	@Test
	public void testGetEndsOrderingNullEventEnds() {
		interaction = createInteraction();
		assertThrows(NullPointerException.class, () -> orderService.getEndsOrdering(interaction, null));
	}

	@Test
	public void testGetEndsOrderingEmptyInteraction() {
		Interaction emptyInteraction = createInteraction();
		List<EObject> result = orderService.getEndsOrdering(emptyInteraction, List.of(executionSpecificationStartAnnotation, executionSpecificationFinishAnnotation));
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetEndsOrderingEmptyEventEnds() {
		List<EObject> result = orderService.getEndsOrdering(interaction, List.of());
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetEndsOrderingSubsetEventEnds() {
		List<EObject> result = orderService.getEndsOrdering(interaction, List.of(executionSpecificationStartAnnotation));
		assertEquals(1, result.size());
		assertEquals(executionSpecificationStartAnnotation, result.get(0));
	}

}
