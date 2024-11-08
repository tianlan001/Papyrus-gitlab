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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramSemanticReorderHelper;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the {@link SequenceDiagramSemanticReorderHelper}.
 * 
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramSemanticReorderHelperTest extends AbstractServicesTest {

	private SequenceDiagramSemanticReorderHelper semanticReorderHelper;

	private Interaction interaction;

	private Lifeline lifeline1;

	private Lifeline lifeline2;

	@Before
	public void setUp() {
		this.semanticReorderHelper = new SequenceDiagramSemanticReorderHelper();
		this.interaction = create(Interaction.class);
		this.lifeline1 = create(Lifeline.class);
		this.lifeline2 = create(Lifeline.class);
		this.interaction.getLifelines().add(lifeline1);
		this.interaction.getLifelines().add(lifeline2);
	}

	@Test
	public void testReorderLifelineNullInteraction() {
		lifeline1 = create(Lifeline.class);
		lifeline2 = create(Lifeline.class);
		assertThrows(NullPointerException.class, () -> semanticReorderHelper.reorderLifeline(null, lifeline2, null));
	}

	@Test
	public void testReorderLifelineNullLifeline() {
		assertThrows(NullPointerException.class, () -> semanticReorderHelper.reorderLifeline(interaction, null, null));
	}

	@Test
	public void testReorderLifelineNullPredecessor() {
		semanticReorderHelper.reorderLifeline(interaction, lifeline2, null);
		assertEquals(lifeline2, interaction.getLifelines().get(0));
		assertEquals(lifeline1, interaction.getLifelines().get(1));
	}

	@Test
	public void testReorderLifelineLeftToRight() {
		semanticReorderHelper.reorderLifeline(interaction, lifeline1, lifeline2);
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
		semanticReorderHelper.reorderLifeline(interaction, lifeline3, lifeline1);
		assertEquals(lifeline1, interaction.getLifelines().get(0));
		assertEquals(lifeline3, interaction.getLifelines().get(1));
		assertEquals(lifeline2, interaction.getLifelines().get(2));
	}

}
