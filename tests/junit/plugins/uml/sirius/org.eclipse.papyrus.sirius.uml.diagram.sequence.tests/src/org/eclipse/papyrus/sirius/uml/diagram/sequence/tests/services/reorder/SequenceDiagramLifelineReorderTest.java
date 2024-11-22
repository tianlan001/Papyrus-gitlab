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

import java.util.List;

import org.eclipse.papyrus.sirius.uml.diagram.common.services.tests.AbstractServicesTest;
import org.eclipse.papyrus.sirius.uml.diagram.sequence.services.reorder.SequenceDiagramSemanticReorderHelper;
import org.eclipse.uml2.uml.Interaction;
import org.eclipse.uml2.uml.Lifeline;
import org.junit.Test;

/**
 * Tests the {@link SequenceDiagramSemanticReorderHelper}.
 *
 * @author <a href="mailto:gwendal.daniel@obeosoft.com>Gwendal Daniel</a>
 */
public class SequenceDiagramLifelineReorderTest extends AbstractServicesTest {

	private SequenceDiagramSemanticReorderHelper reorderHelper = new SequenceDiagramSemanticReorderHelper();;

	Interaction interaction;

	private Lifeline[] create2LinesCase() {
		interaction = create(Interaction.class);
		interaction.getLifelines().add(create(Lifeline.class));
		interaction.getLifelines().add(create(Lifeline.class));

		return interaction.getLifelines().toArray(Lifeline[]::new);
	}

	@Test
	public void reorderNoContainer() {
		assertThrows(NullPointerException.class, () -> reorderHelper.reorderLifeline(create(Lifeline.class), null));
	}

	@Test
	public void testNoLifeline() {
		assertThrows(NullPointerException.class, () -> reorderHelper.reorderLifeline(null, null));
	}

	@Test
	public void moveToFirstPosition() {
		Lifeline[] lines = create2LinesCase();
		reorderHelper.reorderLifeline(lines[1], null);
		assertLinesOrder(lines[1], lines[0]);
	}

	@Test
	public void moveToLeftToRight() {
		Lifeline[] lines = create2LinesCase();

		reorderHelper.reorderLifeline(lines[0], lines[1]);
		assertLinesOrder(lines[1], lines[0]);
	}

	@Test
	public void moveToRightToLeft() {
		Lifeline[] lines = create2LinesCase();
		/*
		 * Add a third lifeline to test right to left with a non-null predecessor.
		 */
		Lifeline lifeline3 = create(Lifeline.class);
		interaction.getLifelines().add(lifeline3);

		reorderHelper.reorderLifeline(lifeline3, lines[0]);
		assertLinesOrder(lines[0], lifeline3, lines[1]);
	}

	private void assertLinesOrder(Lifeline... values) {
		assertEquals(List.of(values), interaction.getLifelines());
	}

}
