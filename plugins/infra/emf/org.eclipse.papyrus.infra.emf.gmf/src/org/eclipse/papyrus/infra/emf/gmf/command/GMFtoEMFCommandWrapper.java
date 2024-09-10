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
import java.util.function.Function;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;

/**
 * A EMF Command that wraps a GMF command. Each method is redirected to the GMF one.
 */
public class GMFtoEMFCommandWrapper extends AbstractCommand implements ICommandWrapper<ICommand> {

	private static Function<ICommand, Command> wrapperFunction = GMFtoEMFCommandWrapper::new;
	private static Function<ICommand, Command> ndWrapperFunction = NonDirtying::new;

	/**
	 *
	 * Wraps the GMF command return value to be returned by this method.
	 *
	 * @return the possible return value from the GMF command
	 */
	@Override
	public Collection<?> getResult() {

		Collection<Object> result = new ArrayList<Object>();
		if (getGMFReturnValue() != null) {
			result.add(getGMFReturnValue());
		} // else return an empty collection

		return result;
	}

	private Object getGMFReturnValue() {
		if (getGMFCommand().getCommandResult() != null) {
			return getGMFCommand().getCommandResult().getReturnValue();
		}

		return null;
	}

	/**
	 * The wrapped GMF Command. Package-level visibility so that the command stack wrapper can
	 * access the field.
	 */
	private final ICommand gmfCommand;

	/**
	 * Constructor.
	 *
	 * @param gmfCommand
	 *            the gmf command
	 */
	public GMFtoEMFCommandWrapper(ICommand gmfCommand) {
		super(gmfCommand.getLabel());
		this.gmfCommand = gmfCommand;
	}

	/**
	 * Wraps the given {@code command}, accounting for possible non-dirty state.
	 *
	 * @param command
	 *            a command to wrap
	 * @return the best wrapper for the {@code command}
	 */
	public static Command wrap(ICommand command) {
		if (command instanceof INonDirtying) {
			return ndWrapperFunction.apply(command);
		}
		return wrapperFunction.apply(command);
	}

	/**
	 * Returns the wrapped GMF command.
	 *
	 * @return the GMF command
	 */
	public ICommand getGMFCommand() {
		return gmfCommand;
	}

	@Override
	public ICommand getWrappedCommand() {
		return getGMFCommand();
	}

	@Override
	public boolean canExecute() {
		return gmfCommand.canExecute();
	}

	@Override
	public void dispose() {
		gmfCommand.dispose();
	}

	@Override
	public boolean canUndo() {
		return gmfCommand.canUndo();
	}

	@Override
	public void execute() {
		try {
			gmfCommand.execute(new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
		}

	}

	@Override
	public void redo() {
		try {
			gmfCommand.redo(new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
		}

	}

	@Override
	public void undo() {
		try {
			gmfCommand.undo(new NullProgressMonitor(), null);
		} catch (ExecutionException e) {
		}

	}

	@Override
	public Collection<?> getAffectedObjects() {
		return gmfCommand.getAffectedFiles();
	}

	@Override
	public String getDescription() {
		return gmfCommand.getLabel();
	}

	protected static void setWrapperFunction(Function<ICommand, Command> wrapperFunction) {
		GMFtoEMFCommandWrapper.wrapperFunction = wrapperFunction;
	}

	protected static void setNonDirtyingWrapperFunction(Function<ICommand, Command> wrapperFunction) {
		GMFtoEMFCommandWrapper.ndWrapperFunction = wrapperFunction;
	}


	//
	// Nested types
	//

	/**
	 * A non-dirtying wrapper for non-dirtying commands.
	 */
	public static class NonDirtying extends GMFtoEMFCommandWrapper implements AbstractCommand.NonDirtying {

		public NonDirtying(ICommand command) {
			super(command);

			if (!(command instanceof INonDirtying)) {
				throw new IllegalArgumentException("Wrapped command is not non-dirtying"); //$NON-NLS-1$
			}
		}

	}

}
