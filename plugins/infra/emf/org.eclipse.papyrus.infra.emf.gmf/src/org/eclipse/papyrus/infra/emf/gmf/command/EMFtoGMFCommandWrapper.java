/***************************************************************************
 * Copyright (c) 2007, 2016 Conselleria de Infraestructuras y Transporte, Generalitat de la Comunitat Valenciana, CEA, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: Mario Cervera Ubeda (Prodevelop)
 *    Christian W. Damus (CEA) - bug 430701
 *    Christian W. Damus - bug 485220
 *
 ******************************************************************************/
package org.eclipse.papyrus.infra.emf.gmf.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;

/**
 * A GMF Command that wraps an EMF command. Each method is redirected to the EMF one.
 */
public class EMFtoGMFCommandWrapper extends AbstractCommand implements ICommandWrapper<Command> {

	private static Function<Command, ICommand> wrapperFunction = EMFtoGMFCommandWrapper::new;
	private static Function<Command, ICommand> ndWrapperFunction = NonDirtying::new;

	/**
	 * The wrapped EMF Command. Package-level visibility so that the command stack wrapper can
	 * access the field.
	 */
	protected Command emfCommand;

	/**
	 * This variable is used to avoid reentrant call in canUndo/undo/redo
	 *
	 * @see https://bugs.eclipse.org/bugs/show_bug.cgi?id=389382
	 */
	protected boolean isBusy;

	/**
	 * Constructor.
	 *
	 * @param emfCommand
	 *            the emf command
	 */
	public EMFtoGMFCommandWrapper(Command emfCommand) {
		super(emfCommand.getLabel());
		this.emfCommand = emfCommand;
	}

	/**
	 * Wraps the given {@code command}, accounting for possible non-dirty state.
	 *
	 * @param command
	 *            a command to wrap
	 * @return the best wrapper for the {@code command}
	 */
	public static ICommand wrap(Command command) {
		if (command instanceof org.eclipse.emf.common.command.AbstractCommand.NonDirtying) {
			return ndWrapperFunction.apply(command);
		}
		return wrapperFunction.apply(command);
	}

	/**
	 * Returns the wrapped EMF command.
	 *
	 * @return the EMF command
	 */
	public Command getEMFCommand() {
		return emfCommand;
	}

	@Override
	public Command getWrappedCommand() {
		return getEMFCommand();
	}

	@Override
	protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

		emfCommand.execute();

		return CommandResult.newOKCommandResult();
	}

	@Override
	protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

		if (!isBusy) {
			isBusy = true;
			emfCommand.redo();
			isBusy = false;
		}

		return CommandResult.newOKCommandResult();
	}

	@Override
	protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {

		if (!isBusy) {
			isBusy = true;
			emfCommand.undo();
			isBusy = false;
		}

		return CommandResult.newOKCommandResult();
	}

	@Override
	public boolean canExecute() {
		return emfCommand.canExecute();
	}

	@Override
	public void dispose() {
		emfCommand.dispose();
	}

	@Override
	public boolean canUndo() {
		if (!isBusy) {
			isBusy = true;
			boolean res = emfCommand.canUndo();
			isBusy = false;
			return res;
		} else {
			return true;
		}
	}

	@Override
	public List getAffectedFiles() {
		ArrayList affectedFiles = new ArrayList();
		Collection<?> affectedObjects = emfCommand.getAffectedObjects();
		if (affectedObjects != null) {
			for (Object o : affectedObjects) {
				if (o instanceof EObject) {
					o = ((EObject) o).eResource();
				}
				if (o instanceof Resource) {
					o = WorkspaceSynchronizer.getFile((Resource) o);
				}
				if (o instanceof IFile) {
					affectedFiles.add(o);
				}
			}
		}
		return affectedFiles;
	}

	@Override
	public CommandResult getCommandResult() {
		Collection<?> res = emfCommand.getResult();
		if (res != null && !res.isEmpty()) {
			if (res.size() == 1) {
				return CommandResult.newOKCommandResult(res.iterator().next());
			}
			return CommandResult.newOKCommandResult(res);
		}
		return CommandResult.newOKCommandResult();
	}

	protected static void setWrapperFunction(Function<Command, ICommand> wrapperFunction) {
		EMFtoGMFCommandWrapper.wrapperFunction = wrapperFunction;
	}

	protected static void setNonDirtyingWrapperFunction(Function<Command, ICommand> wrapperFunction) {
		EMFtoGMFCommandWrapper.ndWrapperFunction = wrapperFunction;
	}

	//
	// Nested types
	//

	/**
	 * A non-dirtying wrapper for non-dirtying commands.
	 */
	public static class NonDirtying extends EMFtoGMFCommandWrapper implements INonDirtying {

		public NonDirtying(org.eclipse.emf.common.command.Command command) {
			super(command);

			if (!(command instanceof org.eclipse.emf.common.command.AbstractCommand.NonDirtying)) {
				throw new IllegalArgumentException("Wrapped command is not non-dirtying"); //$NON-NLS-1$
			}
		}

	}
}
