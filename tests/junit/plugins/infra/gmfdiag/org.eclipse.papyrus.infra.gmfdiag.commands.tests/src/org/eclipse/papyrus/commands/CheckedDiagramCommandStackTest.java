/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.commands;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collections;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoContext;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.workspace.IWorkspaceCommandStack;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.SemanticCreateCommand;
import org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.EditingDomainUndoContext;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper;
import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.eclipse.papyrus.junit.utils.rules.PapyrusEditorFixture;
import org.eclipse.papyrus.junit.utils.rules.PluginResource;
import org.eclipse.papyrus.uml.service.types.element.UMLElementTypes;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.OpaqueExpression;
import org.eclipse.uml2.uml.UMLPackage;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Regression tests for scenarios involving the {@link CheckedDiagramCommandStack}.
 */
@PluginResource("resources/constraint.di")
public class CheckedDiagramCommandStackTest {
	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	@ClassRule
	// Important: suppress the convenience provided by the fixture of ensuring that
	// operations executed on the diagram stack have the EMF editing-domain context
	// because that is the point of our tests: to verify that undo through the
	// editing-domain context is correct, that it does *not* miss the the command
	// executed on the diagram stack that without the fix for bug 491542 would not
	// have had this context
	public static final PapyrusEditorFixture editor = new PapyrusEditorFixture(false);

	/**
	 * Initializes me.
	 */
	public CheckedDiagramCommandStackTest() {
		super();
	}

	@Test
	public void undoChangesFromDiagramCommandStack() {
		CommandStack stack = executeAndVerifyCommand();
		assertThat("Cannot undo", stack.canUndo(), is(true));
		stack.undo();
		assertConstraintReverted();
	}

	private CommandStack executeAndVerifyCommand() {
		CommandStack result = getDiagramCommandStack();
		Command command = createSemanticCreateCommand();
		result.execute(command);
		assertThat("Command did not execute correctly and completely",
				getConstraintSpecification().getLanguages(),
				is(Arrays.asList("OCL", "Smalltalk", "Java")));
		return result;
	}

	@Test
	public void undoChangesFromEMFCommandStack() {
		org.eclipse.emf.common.command.CommandStack stack = editor.getEditingDomain().getCommandStack();
		executeAndVerifyCommand();
		assertThat("Cannot undo", stack.canUndo(), is(true));
		stack.undo();
		assertConstraintReverted();
	}

	@Test
	public void undoChangesFromOperationHistory() throws ExecutionException {
		IUndoContext context = new EditingDomainUndoContext(editor.getEditingDomain());
		IOperationHistory history = ((IWorkspaceCommandStack) editor.getEditingDomain().getCommandStack()).getOperationHistory();
		executeAndVerifyCommand();
		assertThat("Cannot undo", history.canUndo(context), is(true));
		history.undo(context, new NullProgressMonitor(), null);
		assertConstraintReverted();
	}

	//
	// Test framework
	//

	/**
	 * Asserts that the constraint in the test model has been reverted by undo
	 * to its original specification.
	 */
	void assertConstraintReverted() {
		assertThat("Constraint still has a specification", getConstraintSpecification(), nullValue());
	}

	/**
	 * Convoluted command with re-entrant execution that adds a sequence of languages to
	 * the constraint specification.
	 */
	Command createSemanticCreateCommand() {
		CreateElementRequest request = new CreateElementRequest(getConstraint(), UMLElementTypes.OPAQUE_EXPRESSION);

		// A nesting command that makes edits (using commands) to an object created
		// by the SemanticCreateCommand
		AbstractTransactionalCommand adviceCommand = new AbstractTransactionalCommand(editor.getEditingDomain(), "Do it", null) {
			@Override
			public boolean canExecute() {
				return true;
			}

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				OpaqueExpression specification = (OpaqueExpression) request.getNewElement();

				// Nested commands operating on the free-floating element

				AbstractTransactionalCommand nested1 = new AbstractTransactionalCommand(editor.getEditingDomain(), "Do it", null) {
					@Override
					public boolean canExecute() {
						return true;
					}

					@Override
					protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
						specification.getLanguages().add("OCL");
						specification.getLanguages().add("Java");
						return CommandResult.newOKCommandResult();
					}
				};
				getEditingDomain().getCommandStack().execute(ICommandWrapper.wrap(nested1, org.eclipse.emf.common.command.Command.class));

				org.eclipse.emf.common.command.Command nested2 = editor.getEditingDomain().createCommand(
						AddCommand.class,
						new CommandParameter(
								specification,
								UMLPackage.Literals.OPAQUE_EXPRESSION__LANGUAGE,
								Collections.singletonList("Smalltalk"),
								specification.getLanguages().indexOf("Java")));
				getEditingDomain().getCommandStack().execute(nested2);

				return CommandResult.newOKCommandResult();
			}
		};

		// The real command that creates the opaque specification of the constraint
		AbstractTransactionalCommand realCreateCommand = new AbstractTransactionalCommand(editor.getEditingDomain(), "Do it", null) {
			@Override
			public boolean canExecute() {
				return true;
			}

			@Override
			protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
				EObject result = getConstraint().createSpecification(null, null, UMLPackage.Literals.OPAQUE_EXPRESSION);
				return CommandResult.newOKCommandResult(result);
			}
		};

		SemanticCreateCommand semanticCreate = new SemanticCreateCommand(
				new CreateElementRequestAdapter(request),
				new ICommandProxy(realCreateCommand));

		// This is how a complex compound command typically is executed in the diagram
		// by palette tools and the like. This command is non-transactional, which is
		// key to the command executed on the diagram stack missing the critically
		// important editing-domain undo-context, which messes up the undo
		CompositeCommand composite = new CompositeCommand("Do it",
				Arrays.asList(semanticCreate, adviceCommand));

		CompoundCommand result = new CompoundCommand("Do it");
		result.add(toggleCanonical(editor.getActiveDiagram(), false));
		result.add(new ICommandProxy(composite));
		result.add(toggleCanonical(editor.getActiveDiagram(), true));

		return result;
	}

	@SuppressWarnings("restriction")
	private Command toggleCanonical(EditPart editPart, boolean enable) {
		return new org.eclipse.gmf.runtime.diagram.ui.internal.commands.ToggleCanonicalModeCommand(editPart, enable);
	}

	CommandStack getDiagramCommandStack() {
		DiagramEditPart diagram = houseKeeper.cleanUpLater(editor.openDiagram("classes").getActiveDiagram(), this::dispose);

		CommandStack result = diagram.getViewer().getEditDomain().getCommandStack();
		assertThat("Not a CheckedDiagramCommandStack", result, instanceOf(CheckedDiagramCommandStack.class));

		return result;
	}

	Constraint getConstraint() {
		return editor.getModel().getOwnedRule("constraint1");
	}

	OpaqueExpression getConstraintSpecification() {
		return (OpaqueExpression) getConstraint().getSpecification();
	}

	private void dispose(DiagramEditPart diagram) {
		editor.closeDiagram(diagram.getDiagramView().getName());
	}
}
