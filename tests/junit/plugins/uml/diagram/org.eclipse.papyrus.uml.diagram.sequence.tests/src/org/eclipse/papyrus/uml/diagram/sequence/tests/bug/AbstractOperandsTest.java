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

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.uml.diagram.sequence.providers.UMLElementTypes;
import org.eclipse.uml2.uml.ExecutionSpecification;
import org.eclipse.uml2.uml.InteractionFragment;
import org.eclipse.uml2.uml.InteractionOperand;
import org.eclipse.uml2.uml.Message;
import org.eclipse.uml2.uml.MessageEnd;
import org.eclipse.uml2.uml.MessageOccurrenceSpecification;
import org.eclipse.uml2.uml.util.UMLSwitch;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Rule;

public abstract class AbstractOperandsTest extends AbstractPapyrusTest {

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	protected void assertCovered(IGraphicalEditPart coveredPart, IGraphicalEditPart operandPart) {
		assertCoverage(coveredPart, operandPart, true);
	}

	protected void assertNotCovered(IGraphicalEditPart coveredPart, IGraphicalEditPart operandPart) {
		assertCoverage(coveredPart, operandPart, false);
	}

	protected void assertCoverage(IGraphicalEditPart coveredPart, IGraphicalEditPart operandPart, boolean expectedCoverage) {
		EObject semantic = coveredPart.getNotationView().getElement();
		InteractionOperand operand = getOperand(operandPart);

		assertCoverage(semantic, operand, expectedCoverage);
	}

	protected InteractionOperand getOperand(IGraphicalEditPart operandPart) {
		return (InteractionOperand) operandPart.getNotationView().getElement();
	}

	protected void assertCoverage(EObject semantic, InteractionOperand operand, boolean expectedCoverage) {
		new UMLSwitch<Void>() {
			@Override
			public Void caseMessage(Message object) {
				assertCoverage(object, operand, expectedCoverage);
				return null;
			}

			@Override
			public Void caseExecutionSpecification(ExecutionSpecification object) {
				assertCoverage(object, operand, expectedCoverage);
				return null;
			}
		}.doSwitch(semantic);
	}

	protected MessageEnd getSend(IGraphicalEditPart messageEditPart) {
		return ((Message) messageEditPart.getNotationView().getElement()).getSendEvent();
	}

	protected MessageEnd getReceive(IGraphicalEditPart messageEditPart) {
		return ((Message) messageEditPart.getNotationView().getElement()).getReceiveEvent();
	}

	protected InteractionFragment getStart(IGraphicalEditPart execSpecEditPart) {
		return ((ExecutionSpecification) execSpecEditPart.getNotationView().getElement()).getStart();
	}

	protected InteractionFragment getFinish(IGraphicalEditPart execSpecEditPart) {
		return ((ExecutionSpecification) execSpecEditPart.getNotationView().getElement()).getFinish();
	}

	protected void assertCoverage(Message message, InteractionOperand operand, boolean expectedCoverage) {
		assertCoverage(message.getSendEvent(), operand, expectedCoverage);
		assertCoverage(message.getReceiveEvent(), operand, expectedCoverage);
	}

	protected void assertCoverage(MessageEnd messageEnd, InteractionOperand operand, boolean expectedCoverage) {
		Assert.assertThat(messageEnd, IsInstanceOf.instanceOf(MessageOccurrenceSpecification.class));
		assertCoverage((InteractionFragment) messageEnd, operand, expectedCoverage);
	}

	protected void assertCoverage(ExecutionSpecification exec, InteractionOperand operand, boolean expectedCoverage) {
		assertCoverage(exec.getStart(), operand, expectedCoverage);
		assertCoverage(exec.getFinish(), operand, expectedCoverage);
	}

	protected void assertCoverage(InteractionFragment fragment, InteractionOperand operand, boolean expectedCoverage) {
		Assert.assertEquals(expectedCoverage, operand.getFragments().contains(fragment));
	}

	protected IGraphicalEditPart createOperand(Point location) {
		return editor.createShape(UMLElementTypes.InteractionOperand_Shape, location, null);
	}
}
