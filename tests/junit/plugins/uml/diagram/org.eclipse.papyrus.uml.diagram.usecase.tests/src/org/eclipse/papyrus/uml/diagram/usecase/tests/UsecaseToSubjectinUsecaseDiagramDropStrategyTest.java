/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.usecase.tests;

import static org.eclipse.papyrus.junit.matchers.CommandMatchers.GEF.canExecute;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.EditPartUtils;
import org.eclipse.papyrus.infra.gmfdiag.dnd.strategy.DropStrategy;
import org.eclipse.papyrus.junit.utils.rules.ActiveDiagram;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.diagram.usecase.dnd.strategy.UsecaseToSubjectinUsecaseDiagramDropStrategy;
import org.eclipse.papyrus.uml.diagram.usecase.edit.parts.SubjectComponentUsecasesEditPart;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UseCase;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * Test cases for the drop strategy that puts use cases into subjects.
 */
@PluginResource("test/resources/models/bug535696.di")
@ActiveDiagram("use cases")
public class UsecaseToSubjectinUsecaseDiagramDropStrategyTest {

	@Rule
	public final PapyrusEditorFixture editor = new PapyrusEditorFixture();

	private Classifier subject;
	private UseCase useCaseInSubject;
	private UseCase useCaseNotInSubject;

	/**
	 * Initializes me.
	 */
	public UsecaseToSubjectinUsecaseDiagramDropStrategyTest() {
		super();
	}

	/**
	 * Verify that moving a use case shape already within a subject shape does not duplicate the
	 * use case shape.
	 */
	@Test
	public void moveUseCaseWithinSubject() {
		IGraphicalEditPart useCaseEP = getEditPart(useCaseInSubject);

		Rectangle bounds = useCaseEP.getFigure().getBounds().getCopy();
		bounds.translate(50, -50);

		ChangeBoundsRequest req = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		req.setEditParts(useCaseEP);
		req.setMoveDelta(new Point(50, -50));
		req.setLocation(bounds.getCenter());

		Command moveCommand = useCaseEP.getCommand(req);
		assertThat(moveCommand, canExecute());
		editor.execute(moveCommand);

		List<IGraphicalEditPart> all = getAllTopEditParts(useCaseInSubject);
		assertThat("Wrong number of use case views", all.size(), is(1));
	}

	/**
	 * Verify that moving a use case shape from outside of a subject shape into the subject
	 * does not leave the outside shape also.
	 */
	@Test
	public void moveUseCaseIntoSubject() {
		IGraphicalEditPart useCaseEP = getEditPart(useCaseNotInSubject);
		EditPart compartmentEP = getCompartment(getEditPart(subject));

		Rectangle bounds = useCaseEP.getFigure().getBounds().getCopy();
		bounds.translate(-400, 0);

		ChangeBoundsRequest req = new ChangeBoundsRequest(RequestConstants.REQ_MOVE);
		req.setEditParts(useCaseEP);
		req.setMoveDelta(new Point(-400, 0));
		req.setLocation(bounds.getCenter());

		DropStrategy strategy = createDropStrategy();
		Command moveCommand = strategy.getCommand(req, compartmentEP);
		assertThat(moveCommand, canExecute());
		editor.execute(moveCommand);

		List<IGraphicalEditPart> all = getAllTopEditParts(useCaseNotInSubject);
		assertThat("Wrong number of use case views", all.size(), is(1));
	}

	//
	// Test framework
	//

	@Before
	public void findModelElements() {
		subject = (Classifier) editor.getModel().getOwnedType("Subject");
		useCaseInSubject = subject.getOwnedUseCase("In Subject");
		useCaseNotInSubject = (UseCase) subject.getPackage().getOwnedType("Not In Subject");
	}

	IGraphicalEditPart getEditPart(EObject element) {
		return getAllTopEditParts(element).get(0);
	}

	List<IGraphicalEditPart> getAllTopEditParts(EObject element) {
		List<IGraphicalEditPart> result = Lists.newArrayListWithExpectedSize(1);

		TreeIterator<EditPart> iter = DiagramEditPartsUtil.getAllContents(editor.getActiveDiagram(), true);
		while (iter.hasNext()) {
			EditPart next = iter.next();
			if (next instanceof IGraphicalEditPart) {
				IGraphicalEditPart gep = (IGraphicalEditPart) next;
				if (gep.resolveSemanticElement() == element) {
					result.add(gep);

					// Don't get any nested edit-parts such as compartments, labels, etc.
					iter.prune();
				}
			}
		}

		assertThat("Edit-part not found", result, hasItem(anything()));
		return result;
	}

	EditPart getCompartment(EditPart shapeEditPart) {
		return EditPartUtils.findFirstChildEditPartWithId(shapeEditPart, SubjectComponentUsecasesEditPart.VISUAL_ID);
	}

	/**
	 * Create a specialization of the drop strategy under test that doesn't pop up any dialog but just
	 * always updates the use case owner and subject.
	 *
	 * @return the drop strategy fixture
	 */
	DropStrategy createDropStrategy() {
		return new UsecaseToSubjectinUsecaseDiagramDropStrategy() {
			@Override
			protected ICommand getEditSlotsCommand(UseCase useCase, Classifier subject) {
				CompositeCommand result = new CompositeCommand("Update owner and subject");

				SetRequest setSubjectRequest = new SetRequest(useCase, UMLPackage.Literals.USE_CASE__SUBJECT, subject);
				SetValueCommand setSubjectCommand = new SetValueCommand(setSubjectRequest);
				result.add(setSubjectCommand);

				SetRequest setOwnerRequest = new SetRequest(subject, UMLPackage.Literals.CLASSIFIER__OWNED_USE_CASE, useCase);
				SetValueCommand setOwnerCommand = new SetValueCommand(setOwnerRequest);
				result.add(setOwnerCommand);

				return result.reduce();
			}
		};
	}
}
