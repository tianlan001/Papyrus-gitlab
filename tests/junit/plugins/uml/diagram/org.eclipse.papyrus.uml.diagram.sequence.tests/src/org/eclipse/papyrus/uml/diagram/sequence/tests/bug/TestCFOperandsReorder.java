/*****************************************************************************
 * Copyright (c) 2018 EclipseSource, CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   EclipseSource - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.sequence.tests.bug;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.requests.RequestConstants;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.Size;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Message;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test class for Bug 533697.
 *
 * Tests that:
 *
 * <ul>
 * 	<li>It must be possible to reorder operands in a CF via a {@link RequestConstants#REQ_DROP Drop request}</li>
 *  <li>It is <strong>not</strong> possible to reparent an Operand (i.e. drop it to a different container)</li>
 * 	<li>After a drop, all Operands have the same size</li>
 * 	<li>After a drop, covered fragments are updated, without moving</li>
 * </ul>
 */
@PluginResource({ "resource/bugs/bug533697-reorderOperands.di", "resource/bugs/style.css" })
@ActiveDiagram("Reorder")
public class TestCFOperandsReorder extends AbstractOperandsTest {

	Map<IGraphicalEditPart, Dimension> initialSizes = new HashMap<>();

	IGraphicalEditPart operand1, operand2, operand3, operand4, otherOperand;
	IGraphicalEditPart message4, message5, message6, message8, message9, message10, message12, message13;

	@Before
	public void init() {
		initialSizes.clear();

		operand1 = (IGraphicalEditPart) editor.findEditPart("Operand 1", InteractionOperand.class);
		operand2 = (IGraphicalEditPart) editor.findEditPart("Operand 2", InteractionOperand.class);
		operand3 = (IGraphicalEditPart) editor.findEditPart("Operand 3", InteractionOperand.class);
		operand4 = (IGraphicalEditPart) editor.findEditPart("Operand 4", InteractionOperand.class);
		otherOperand = (IGraphicalEditPart) editor.findEditPart("Other Operand", InteractionOperand.class);

		initialSizes.put(operand1, getSize(operand1));
		initialSizes.put(operand2, getSize(operand2));
		initialSizes.put(operand3, getSize(operand3));
		initialSizes.put(operand4, getSize(operand4));
		initialSizes.put(otherOperand, getSize(otherOperand));

		message4 = (IGraphicalEditPart)editor.findEditPart("Message4", Message.class);
		message5 = (IGraphicalEditPart)editor.findEditPart("Message5", Message.class);
		message6 = (IGraphicalEditPart)editor.findEditPart("Message6", Message.class);
		message8 = (IGraphicalEditPart)editor.findEditPart("Message8", Message.class);
		message9 = (IGraphicalEditPart)editor.findEditPart("Message9", Message.class);
		message10 = (IGraphicalEditPart)editor.findEditPart("Message10", Message.class);
		message12 = (IGraphicalEditPart)editor.findEditPart("Message12", Message.class);
		message13 = (IGraphicalEditPart)editor.findEditPart("Message13", Message.class);

		assertCovered(message4, operand1);

		assertCovered(message5, operand2);
		assertCovered(message6, operand2);

		assertCovered(message8, operand3);
		assertCovered(message9, operand3);

		assertCovered(message10, operand4);
		assertCovered(message12, operand4);
		assertCovered(message13, operand4);
	}

	@Test
	public void testReorderFirstToSecond() {
		drop(operand1, operand2);

		assertCovered(message4, operand2);
		assertCovered(message5, operand2);

		assertCovered(message6, operand1);

		assertCovered(message8, operand3);
		assertCovered(message9, operand3);

		assertCovered(message10, operand4);
		assertCovered(message12, operand4);
		assertCovered(message13, operand4);
	}

	@Test
	public void testReorderSecondToFirst() {
		drop(operand2, operand1);

		assertCovered(message4, operand2);
		assertCovered(message5, operand2);

		assertCovered(message6, operand1);

		assertCovered(message8, operand3);
		assertCovered(message9, operand3);

		assertCovered(message10, operand4);
		assertCovered(message12, operand4);
		assertCovered(message13, operand4);
	}

	@Test
	public void testReorderLastToThird() {
		drop(operand4, operand3);

		assertCovered(message4, operand1);

		assertCovered(message5, operand2);
		assertCovered(message6, operand2);

		assertCovered(message8, operand4);
		assertCovered(message9, operand4);
		assertCovered(message10, operand4);

		assertCovered(message12, operand3);
		assertCovered(message13, operand3);
	}

	@Test
	public void testReorderThirdToLast() {
		drop(operand3, operand4);

		assertCovered(message4, operand1);

		assertCovered(message5, operand2);
		assertCovered(message6, operand2);

		assertCovered(message8, operand4);
		assertCovered(message9, operand4);
		assertCovered(message10, operand4);

		assertCovered(message12, operand3);
		assertCovered(message13, operand3);
	}

	@Test
	public void testReorderFirstToLast() {
		drop(operand1, operand4);

		assertCovered(message4, operand2);
		assertCovered(message5, operand2);

		assertCovered(message6, operand3);
		assertCovered(message8, operand3);

		assertCovered(message9, operand4);
		assertCovered(message10, operand4);
		assertCovered(message12, operand4);

		assertCovered(message13, operand1);
	}

	@Test
	public void testReorderLastToFirst() {
		drop(operand4, operand1);

		assertCovered(message4, operand4);
		assertCovered(message5, operand4);
		assertCovered(message6, operand4);

		assertCovered(message8, operand1);

		assertCovered(message9, operand2);
		assertCovered(message10, operand2);

		assertCovered(message12, operand3);
		assertCovered(message13, operand3);
	}

	@Test
	public void testReparentForbidden() {
		Assert.assertFalse(drop(operand1, otherOperand));
		Assert.assertFalse(drop(otherOperand, operand4));
	}

	private boolean drop(IGraphicalEditPart operand, IGraphicalEditPart onOperand) {
		ChangeBoundsRequest dropRequest = new ChangeBoundsRequest(RequestConstants.REQ_DROP);
		dropRequest.setEditParts(operand);
		Command command = onOperand.getCommand(dropRequest);
		boolean canExecute = command.canExecute();
		if (canExecute) {
			editor.execute(command);
		}

		return canExecute;
	}

	@After
	public void checkSizes() {
		for (Map.Entry<IGraphicalEditPart, Dimension> sizeEntry : initialSizes.entrySet()) {
			Dimension newSize = getSize(sizeEntry.getKey());
			Assert.assertEquals(sizeEntry.getValue(), newSize);
		}
		initialSizes.clear();
	}

	private Dimension getSize(IGraphicalEditPart part) {
		Node node = (Node)part.getNotationView();
		Size size = (Size)node.getLayoutConstraint();
		return new Dimension(size.getWidth(), size.getHeight());
	}

}
