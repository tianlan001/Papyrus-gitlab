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

package org.eclipse.papyrus.uml.diagram.common.tests.tests;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.requests.MoveRequest;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.gmf.runtime.notation.impl.NodeImpl;
import org.eclipse.gmf.runtime.notation.impl.ShapeImpl;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.services.edit.service.ElementEditServiceUtils;
import org.eclipse.papyrus.infra.services.edit.service.IElementEditService;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.DiagramUtils;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.common.editparts.ClassEditPart;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Generalization;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * The Class Bug476873_MoveCommandTest.
 */
@PluginResource("/resources/476873/uml.di")
public class Bug476873_MoveCommandTest extends AbstractPapyrusTest {

	/** The name of the source class of the generalizations. */
	private static final String NAME_CLASS_1 = "Class1"; //$NON-NLS-1$
	/** The name of the target class of the generalizations. */
	private static final String NAME_CLASS_3 = "Class3"; //$NON-NLS-1$
	/** The name of the diagram used for testing. */
	private static final String NAME_DIAGRAM = "ClassDiagram"; //$NON-NLS-1$

	/** The Papyrus editor fixture. */
	@Rule
	public final PapyrusEditorFixture papyrusEditorFixture = new PapyrusEditorFixture();

	/** The target Class edit part. */
	private EditPart targetClassEditPart = null;

	/** The first generalization to move. */
	private Generalization firstGeneralization = null;

	/** The second generalization to move. */
	private Generalization secondGeneralization = null;

	/** The diagram. */
	private Diagram diagram = null;

	@Before
	public void init() {
		diagram = DiagramUtils.getNotationDiagram(papyrusEditorFixture.getModelSet(), NAME_DIAGRAM);

		assertNotNull(diagram);

		papyrusEditorFixture.getPageManager().openPage(diagram);
		papyrusEditorFixture.flushDisplayEvents();

		targetClassEditPart = getEditPart(NAME_CLASS_3); // The target class of the generalizations
		Assert.assertTrue("The Edit Part must be a ClassEditPart", targetClassEditPart instanceof ClassEditPart); //$NON-NLS-1$

		EditPart sourceClassEditPart = getEditPart(NAME_CLASS_1); // The source class of the generalizations
		Assert.assertTrue("The Edit Part must be a ClassEditPart", sourceClassEditPart instanceof ClassEditPart); //$NON-NLS-1$

		Object sourceModel = sourceClassEditPart.getModel();

		if (sourceModel instanceof ShapeImpl) {
			EObject sourceElement = ((ShapeImpl) sourceModel).getElement();

			if (sourceElement instanceof Classifier) {
				EList<Generalization> generalizations = ((Classifier) sourceElement).getGeneralizations();

				if (generalizations.size() >= 2) {
					firstGeneralization = generalizations.get(0);
					secondGeneralization = generalizations.get(1);
				}

			}
		}
	}

	/**
	 * Tests of the move of one generalization.
	 */
	@Test
	public void testMoveOneGeneralization() {
		List<EObject> elements = new ArrayList<EObject>();
		elements.add(firstGeneralization);

		// Retrieve the command to move the first generalization
		ICommand command = getCommandMovedElements(elements);

		assertNotNull(command);

		// Get views of the generalizations, The two must be not null.
		View firstGeneralizationView = getDiagramViewGeneralization(firstGeneralization);
		View secondGeneralizationView = getDiagramViewGeneralization(secondGeneralization);

		assertNotNull(firstGeneralizationView);
		assertNotNull(secondGeneralizationView);

		// Moved the first generalization
		getCommandStack().execute(new GMFtoEMFCommandWrapper(command));

		// Get the views, only the first must be null
		firstGeneralizationView = getDiagramViewGeneralization(firstGeneralization);
		secondGeneralizationView = getDiagramViewGeneralization(secondGeneralization);

		assertNull(firstGeneralizationView);
		assertNotNull(secondGeneralizationView);

		getCommandStack().undo();

		// After the undo command, the two views must be not null
		firstGeneralizationView = getDiagramViewGeneralization(firstGeneralization);
		secondGeneralizationView = getDiagramViewGeneralization(secondGeneralization);

		assertNotNull(firstGeneralizationView);
		assertNotNull(secondGeneralizationView);

		getCommandStack().redo();

		// After the redo command, only the first must be null
		firstGeneralizationView = getDiagramViewGeneralization(firstGeneralization);
		secondGeneralizationView = getDiagramViewGeneralization(secondGeneralization);

		assertNull(firstGeneralizationView);
		assertNotNull(secondGeneralizationView);
	}

	/**
	 * Tests of the move of two generalizations.
	 */
	@Test
	public void testMoveTwoGeneralizations() {
		List<EObject> elements = new ArrayList<EObject>();
		elements.add(firstGeneralization);
		elements.add(secondGeneralization);

		// Retrieve the command to move the generalizations (first and second)
		ICommand command = getCommandMovedElements(elements);

		assertNotNull(command);

		// Get views of the generalizations, The two must be not null.
		View diagramViewGeneralization1 = getDiagramViewGeneralization(firstGeneralization);
		View diagramViewGeneralization2 = getDiagramViewGeneralization(secondGeneralization);

		assertNotNull(diagramViewGeneralization1);
		assertNotNull(diagramViewGeneralization2);

		// Moved the two generalizations
		getCommandStack().execute(new GMFtoEMFCommandWrapper(command));

		// Get the views, the two must be null
		diagramViewGeneralization1 = getDiagramViewGeneralization(firstGeneralization);
		diagramViewGeneralization2 = getDiagramViewGeneralization(secondGeneralization);

		assertNull(diagramViewGeneralization1);
		assertNull(diagramViewGeneralization2);

		getCommandStack().undo();

		// After the undo command, the two views must be not null
		diagramViewGeneralization1 = getDiagramViewGeneralization(firstGeneralization);
		diagramViewGeneralization2 = getDiagramViewGeneralization(secondGeneralization);

		assertNotNull(diagramViewGeneralization1);
		assertNotNull(diagramViewGeneralization2);

		getCommandStack().redo();

		// After the redo command, the two must be null
		diagramViewGeneralization1 = getDiagramViewGeneralization(firstGeneralization);
		diagramViewGeneralization2 = getDiagramViewGeneralization(secondGeneralization);

		assertNull(diagramViewGeneralization1);
		assertNull(diagramViewGeneralization2);
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

		final Object modelTarget = targetClassEditPart.getModel();

		assertThat(modelTarget, instanceOf(NodeImpl.class));

		final EObject elementTarget = ((NodeImpl) modelTarget).getElement();

		assertNotNull(elementTarget);

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
	 * @return the command stack
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
	 * Gets view of the semantic element.
	 *
	 * @param semanticElement
	 *            The name of the semantic element.
	 * @return A corresponding view of the semantic element.
	 */
	private View getDiagramView(final String semanticElement) {
		View diagramView = DiagramUtils.findShape(diagram, semanticElement);
		if (diagramView == null) {
			diagramView = DiagramUtils.findEdge(diagram, semanticElement);
		}
		if (diagramView == null) {
			Iterator<?> iterator = diagram.eAllContents();
			while (null == diagramView && iterator.hasNext()) {
				final Object object = iterator.next();
				if (object instanceof View) {
					diagramView = DiagramUtils.findShape((View) object, semanticElement);
					if (diagramView == null) {
						diagramView = DiagramUtils.findEdge((View) object, semanticElement);
					}
				}
			}
		}

		return diagramView;
	}

	/**
	 * Gets view of a generalization on the diagram.
	 *
	 * @param generalization
	 *            The Generalization.
	 * @return A corresponding view of the generalization.
	 */
	private View getDiagramViewGeneralization(final Generalization generalization) {
		View diagramView = findViewGeneralization(diagram, generalization);
		if (diagramView == null) {
			Iterator<?> iterator = diagram.eAllContents();
			while (null == diagramView && iterator.hasNext()) {
				final Object object = iterator.next();
				if (object instanceof View) {
					diagramView = findViewGeneralization((View) object, generalization);
				}
			}
		}

		return diagramView;
	}

	/**
	 * Gets view of the generalization on a specific element.
	 *
	 * @param container
	 *            The specific element to find generalization.
	 * @param generalization
	 *            Generalization to find.
	 * @return A view of the generalization.
	 */
	private View findViewGeneralization(final View container, final Generalization generalization) {
		for (final Object viewObject : container.getChildren()) {
			final View view = (View) viewObject;
			if (view.getElement() instanceof Generalization) {
				final Generalization element = (Generalization) view.getElement();
				if (generalization.equals(element)) {
					return view;
				}
			}
		}
		return null;
	}
}
