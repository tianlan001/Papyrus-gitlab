/*****************************************************************************
 * Copyright (c) 2015 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Francois Le Fevre (CEA LIST) francois.le-fevre@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.dnd.cmd;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.uml.diagram.usecase.dnd.ui.UsecaseSelectionDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.UseCase;

/**
 * An interactive Command to speicify ownership and subject when dropping an usecase to a usecase diagram
 * A dialog will be opened for the user to select ownership swith yes/no and to confirm change of subject validate/cancel
 *
 * @author Francois Le Fevre
 *
 */
public class UsecaseCommand extends AbstractCommand {
	/** Selected command kept here for undo / re-do purpose */
	private ICommand usecaseSelectionCommand;

	protected final UseCase sourceUsecase;
	protected final Classifier subject;


	public UsecaseCommand(UseCase sourceUsecase, Classifier subject) {
		super("Manage usecase subject and owner"); //$NON-NLS-1$
		this.sourceUsecase = sourceUsecase;
		this.subject = subject;
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		// Open the dialog to select the configurations
		UsecaseSelectionDialog dialog = new UsecaseSelectionDialog(Display.getCurrent().getActiveShell(), sourceUsecase, subject);

		if (dialog.open() != Window.OK) {
			return CommandResult.newCancelledCommandResult();
		}

		boolean isKeepOwner = dialog.isKeepOwner();
		boolean isKeepSubject = dialog.isKeepSubject();

		// should not be valid, it should have been already catched before
		if (isKeepOwner & isKeepSubject) {
			return null;
		}

		if (!isKeepOwner & isKeepSubject) {

			CompositeCommand cc = new CompositeCommand(getLabel());
			SetRequest setContextRequest = new SetRequest(subject, UMLPackage.eINSTANCE.getClassifier_OwnedUseCase(), sourceUsecase);
			SetValueCommand setContextCommand = new SetValueCommand(setContextRequest);
			cc.add(setContextCommand);

			usecaseSelectionCommand = cc.reduce();

			usecaseSelectionCommand.execute(new NullProgressMonitor(), null);

			CommandResult commandResult = usecaseSelectionCommand.getCommandResult();
			if (commandResult != null) {
				if (!commandResult.getStatus().isOK()) {
					return commandResult;
				}
			}
		}

		if (!isKeepOwner & !isKeepSubject) {
			CompositeCommand cc = new CompositeCommand(getLabel());
			SetRequest setContextRequest = new SetRequest(sourceUsecase, UMLPackage.eINSTANCE.getUseCase_Subject(), subject);
			SetValueCommand setContextCommand = new SetValueCommand(setContextRequest);
			cc.add(setContextCommand);

			setContextRequest = new SetRequest(subject, UMLPackage.eINSTANCE.getClassifier_OwnedUseCase(), sourceUsecase);
			setContextCommand = new SetValueCommand(setContextRequest);
			cc.add(setContextCommand);

			usecaseSelectionCommand = cc.reduce();

			usecaseSelectionCommand.execute(new NullProgressMonitor(), null);

			CommandResult commandResult = usecaseSelectionCommand.getCommandResult();
			if (commandResult != null) {
				if (!commandResult.getStatus().isOK()) {
					return commandResult;
				}
			}
		}

		if (isKeepOwner & !isKeepSubject) {
			CompositeCommand cc = new CompositeCommand(getLabel());
			SetRequest setContextRequest = new SetRequest(sourceUsecase, UMLPackage.eINSTANCE.getUseCase_Subject(), subject);
			SetValueCommand setContextCommand = new SetValueCommand(setContextRequest);
			cc.add(setContextCommand);

			usecaseSelectionCommand = cc.reduce();

			usecaseSelectionCommand.execute(new NullProgressMonitor(), null);

			CommandResult commandResult = usecaseSelectionCommand.getCommandResult();
			if (commandResult != null) {
				if (!commandResult.getStatus().isOK()) {
					return commandResult;
				}
			}
		}

		return CommandResult.newOKCommandResult();
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doRedoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param progressMonitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		if (usecaseSelectionCommand != null && usecaseSelectionCommand.canRedo()) {
			usecaseSelectionCommand.undo(progressMonitor, info);
		}
		return CommandResult.newOKCommandResult();
	}

	/**
	 * @see org.eclipse.gmf.runtime.common.core.command.AbstractCommand#doUndoWithResult(org.eclipse.core.runtime.IProgressMonitor, org.eclipse.core.runtime.IAdaptable)
	 *
	 * @param progressMonitor
	 * @param info
	 * @return
	 * @throws ExecutionException
	 */
	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
		if (usecaseSelectionCommand != null && usecaseSelectionCommand.canUndo()) {
			usecaseSelectionCommand.undo(progressMonitor, info);
		}
		return CommandResult.newOKCommandResult();
	}

	@Override
	public boolean canRedo() {
		return false;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

}
