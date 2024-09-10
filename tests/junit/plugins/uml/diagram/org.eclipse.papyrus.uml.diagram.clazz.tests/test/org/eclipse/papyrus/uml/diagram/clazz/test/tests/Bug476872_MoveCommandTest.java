/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Fanch BONNABESSE (ALL4TEC) fanch.bonnabesse@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.clazz.test.tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationEditPart;
import org.eclipse.papyrus.uml.diagram.clazz.edit.parts.EnumerationLiteralEditPart;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * The Class Bug476872_MoveCommandTest.
 */
@PluginResource("/model/bug476872_ClassDiagram/uml.di")
public class Bug476872_MoveCommandTest extends AbstractPapyrusTest {

	/** The name of the target enumeration of the literals. */
	private static final String NAME_ENUMERATION_2 = "Enumeration2"; //$NON-NLS-1$
	/** The name of the first literal to move. */
	private static final String NAME_LITERAL_1_1 = "Literal1-1"; //$NON-NLS-1$ Literal contains on the Enumeration1
	/** The name of the second literal to move. */
	private static final String NAME_LITERAL_1_2 = "Literal1-2"; //$NON-NLS-1$ Literal contains on the Enumeration1
	/** The name of the diagram used for testing. */
	private static final String NAME_DIAGRAM = "ClassDiagram"; //$NON-NLS-1$

	/** The Papyrus editor fixture. */
	@Rule
	public final PapyrusEditorFixture papyrusEditorFixture = new PapyrusEditorFixture();

	/** The target Enumeration edit part. */
	private EditPart targetEnumerationEditPart = null;

	/** The first Enumeration Literal edit part. */
	private EditPart firstEnumerationLiteralEditPart = null;

	/** The second Enumeration Literal edit part. */
	private EditPart secondEnumerationLiteralEditPart = null;

	/** The diagram. */
	private Diagram diagram = null;

	@Before
	public void init() {
		diagram = DiagramUtils.getNotationDiagram(papyrusEditorFixture.getModelSet(), NAME_DIAGRAM);
		assertNotNull(diagram);

		papyrusEditorFixture.getPageManager().openPage(diagram);
		papyrusEditorFixture.flushDisplayEvents();

		targetEnumerationEditPart = getEditPart(NAME_ENUMERATION_2);
		Assert.assertTrue("The Edit Part must be a EnumerationEditPart", targetEnumerationEditPart instanceof EnumerationEditPart); //$NON-NLS-1$

		firstEnumerationLiteralEditPart = getEditPart(NAME_LITERAL_1_1);
		Assert.assertTrue("The Edit Part must be a EnumerationLiteralEditPart", firstEnumerationLiteralEditPart instanceof EnumerationLiteralEditPart); //$NON-NLS-1$

		secondEnumerationLiteralEditPart = getEditPart(NAME_LITERAL_1_2);
		Assert.assertTrue("The Edit Part must be a EnumerationLiteralEditPart", secondEnumerationLiteralEditPart instanceof EnumerationLiteralEditPart); //$NON-NLS-1$
	}

	/**
	 * Tests of the move of one literal.
	 */
	@Test
	public void testMoveOneLiteral() {
		final Object modelToMove = firstEnumerationLiteralEditPart.getModel();

		assertThat(modelToMove, instanceOf(NodeImpl.class));

		final EObject elementToMove = ((NodeImpl) modelToMove).getElement();

		List<EObject> elements = new ArrayList<EObject>();
		elements.add(elementToMove);

		// Get the command that moved the first literal
		ICommand command = getCommandMovedElements(elements);

		assertNotNull(command);

		// Moved the literal to another Enumeration
		getCommandStack().execute(new GMFtoEMFCommandWrapper(command));

		// Get views of the two Literals, the first must be null.
		View firstLiteralView = getDiagramView(NAME_LITERAL_1_1);
		View secondLiteralView = getDiagramView(NAME_LITERAL_1_2);

		assertNull(firstLiteralView);
		assertNotNull(secondLiteralView);

		getCommandStack().undo();

		// After the undo command, the two literals must be not null
		firstLiteralView = getDiagramView(NAME_LITERAL_1_1);
		secondLiteralView = getDiagramView(NAME_LITERAL_1_2);

		assertNotNull(firstLiteralView);
		assertNotNull(secondLiteralView);

		getCommandStack().redo();

		// After the redo command, only the view of the first literal must be null
		firstLiteralView = getDiagramView(NAME_LITERAL_1_1);
		secondLiteralView = getDiagramView(NAME_LITERAL_1_2);

		assertNull(firstLiteralView);
		assertNotNull(secondLiteralView);
	}

	/**
	 * Tests of the move of two literals.
	 */
	@Test
	public void testMoveTwoLiterals() {
		final Object firstModelToMove = firstEnumerationLiteralEditPart.getModel();

		assertThat(firstModelToMove, instanceOf(NodeImpl.class));

		final Object secondModelToMove = secondEnumerationLiteralEditPart.getModel();

		assertThat(secondModelToMove, instanceOf(NodeImpl.class));

		final EObject firstElementToMove = ((NodeImpl) firstModelToMove).getElement();
		final EObject secondElementToMove = ((NodeImpl) secondModelToMove).getElement();

		List<EObject> elements = new ArrayList<EObject>();
		elements.add(firstElementToMove);
		elements.add(secondElementToMove);

		// Get the command that moved the two literals (first and second)
		ICommand command = getCommandMovedElements(elements);

		assertNotNull(command);

		// Moved the two literals to another Enumeration
		getCommandStack().execute(new GMFtoEMFCommandWrapper(command));

		// Get views of the two Literals, the two must be null.
		View firstLiteralView = getDiagramView(NAME_LITERAL_1_1);
		View secondLiteralView = getDiagramView(NAME_LITERAL_1_2);

		assertNull(firstLiteralView);
		assertNull(secondLiteralView);

		getCommandStack().undo();

		// After the undo command, the two literals must be not null
		firstLiteralView = getDiagramView(NAME_LITERAL_1_1);
		secondLiteralView = getDiagramView(NAME_LITERAL_1_2);

		assertNotNull(firstLiteralView);
		assertNotNull(secondLiteralView);

		getCommandStack().redo();

		// After the redo command, the two views of the literals must be null
		firstLiteralView = getDiagramView(NAME_LITERAL_1_1);
		secondLiteralView = getDiagramView(NAME_LITERAL_1_2);

		assertNull(firstLiteralView);
		assertNull(secondLiteralView);
	}

	/**
	 * This creates the command to move the Elements.
	 *
	 * @param elementsToMove
	 *            The list of the elements to move.
	 * @return The command to move the elements.
	 */
	private ICommand getCommandMovedElements(final List<EObject> elementsToMove) {
		ICommand command = null;

		final Object modelTarget = targetEnumerationEditPart.getModel();

		assertThat(modelTarget, instanceOf(NodeImpl.class));

		final EObject elementTarget = ((NodeImpl) modelTarget).getElement();
		final MoveRequest moveRequest = new MoveRequest(elementTarget, elementsToMove);
		final IElementEditService provider = ElementEditServiceUtils.getCommandProvider(elementTarget);

		assertNotNull(provider);

		// Retrieve delete command from the Element Edit service
		command = provider.getEditCommand(moveRequest);

		return command;
	}


	/**
	 * Gets the command stack.
	 *
	 * @return The command stack
	 */
	private CommandStack getCommandStack() {
		return papyrusEditorFixture.getEditingDomain().getCommandStack();
	}

	/**
	 * Gets the edit part.
	 *
	 * @param semanticElement
	 *            the semantic element
	 * @return The edit part
	 */
	private EditPart getEditPart(final String semanticElement) {
		final View diagramView = getDiagramView(semanticElement);
		assertNotNull(diagramView);

		final IGraphicalEditPart semanticEP = DiagramUtils.findEditPartforView(papyrusEditorFixture.getEditor(), diagramView, IGraphicalEditPart.class);
		assertNotNull(semanticEP);

		return semanticEP;
	}

	/**
	 * Get view of the semantic element.
	 *
	 * @param semanticElement
	 *            The name of the semantic element.
	 * @return A corresponding view of the semantic element.
	 */
	private View getDiagramView(final String semanticElement) {
		View diagramView = DiagramUtils.findShape(diagram, semanticElement);
		if (null == diagramView) {
			diagramView = DiagramUtils.findEdge(diagram, semanticElement);
		}
		if (null == diagramView) {
			Iterator<?> iterator = diagram.eAllContents();
			while (null == diagramView && iterator.hasNext()) {
				final Object object = iterator.next();
				if (object instanceof View) {
					diagramView = DiagramUtils.findShape((View) object, semanticElement);
					if (null == diagramView) {
						diagramView = DiagramUtils.findEdge((View) object, semanticElement);
					}
				}
			}
		}

		return diagramView;
	}
}
