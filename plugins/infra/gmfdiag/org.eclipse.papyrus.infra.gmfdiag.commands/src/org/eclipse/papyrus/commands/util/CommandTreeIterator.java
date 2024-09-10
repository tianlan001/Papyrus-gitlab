/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.commands.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.papyrus.commands.wrappers.EMFtoGEFCommandWrapper;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.commands.wrappers.GMFtoGEFCommandWrapper;
import org.eclipse.papyrus.commands.wrappers.OperationToGEFCommandWrapper;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;

/**
 * An iterator over the tree structure of EMF, GEF, and GMF commands that returns leaf commands of one or all of these kinds,
 * with accounting for the various kinds of wrappers employed to intermix them. This iterator does not support the
 * optional {@link Iterator#remove()} operation.
 * 
 * @deprecated Use the {@link org.eclipse.papyrus.infra.emf.gmf.util.CommandTreeIterator} API, instead.
 */
@Deprecated
public class CommandTreeIterator<C> implements Iterator<C> {
	private final Class<C> type;

	private Iterator<?> current;
	private List<Iterator<?>> iterators = new ArrayList<Iterator<?>>();

	private C preparedNext;

	private CommandTreeIterator(Object root, Class<C> type) {
		super();

		this.type = type;

		root = unwrap(root);

		if (isCompound(root)) {
			pushIterator(root);
		} else {
			prepareNext(root);
		}
	}

	public static CommandTreeIterator<Command> iterateEMF(Object command) {
		return iterate(command, Command.class);
	}

	public static CommandTreeIterator<org.eclipse.gef.commands.Command> iterateGEF(Object command) {
		return iterate(command, org.eclipse.gef.commands.Command.class);
	}

	public static CommandTreeIterator<ICommand> iterateGMF(Object command) {
		return iterate(command, ICommand.class);
	}

	public static CommandTreeIterator<?> iterate(Object command) {
		return iterate(command, Object.class);
	}

	public static <C> CommandTreeIterator<C> iterate(Object command, Class<C> leafCommandType) {
		return new CommandTreeIterator<C>(command, leafCommandType);
	}

	private boolean prepareNext(Object command) {
		if (type.isInstance(command)) {
			preparedNext = type.cast(command);
		}

		return preparedNext != null;
	}

	private Iterator<?> pushIterator(Object compoundCommand) {
		if (current != null) {
			iterators.add(current);
		}
		current = iterator(compoundCommand);
		return current;
	}

	private Iterator<?> popIterator() {
		if (iterators.isEmpty()) {
			current = null;
		} else {
			current = iterators.remove(iterators.size() - 1);
		}

		return current;
	}

	private Object internalNext() {
		Object result = null;

		while ((result == null) && (current != null)) {
			if (current.hasNext()) {
				Object next = unwrap(current.next());
				if (isCompound(next)) {
					// Dive into it
					pushIterator(next);
				} else {
					// We have the next leaf
					result = next;
				}
			} else {
				popIterator();
			}
		}

		return result;
	}

	boolean isDone() {
		return (current == null) && iterators.isEmpty();
	}

	@Override
	public boolean hasNext() {
		while (!isDone() && (preparedNext == null)) {
			Object next = internalNext();
			if (type.isInstance(next)) {
				preparedNext = type.cast(next);
			}
		}

		return preparedNext != null;
	}

	@Override
	public C next() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		C result = preparedNext;
		preparedNext = null;
		return result;
	}

	/**
	 * Remove is not supported.
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException("remove"); //$NON-NLS-1$
	}

	private Object unwrap(Object command) {
		Object result = command;

		if (command instanceof ICommandProxy) {
			result = ((ICommandProxy) command).getICommand();
		} else if (command instanceof CommandProxy) {
			result = ((CommandProxy) command).getCommand();
		} else if (result instanceof EMFtoGEFCommandWrapper) {
			result = ((EMFtoGEFCommandWrapper) result).getEMFCommand();
		} else if (result instanceof EMFtoGMFCommandWrapper) {
			result = ((EMFtoGMFCommandWrapper) command).getEMFCommand();
		} else if (result instanceof GEFtoEMFCommandWrapper) {
			result = ((GEFtoEMFCommandWrapper) command).getGEFCommand();
		} else if (result instanceof GMFtoEMFCommandWrapper) {
			result = ((GMFtoEMFCommandWrapper) command).getGMFCommand();
		} else if (result instanceof GMFtoGEFCommandWrapper) {
			result = ((GMFtoGEFCommandWrapper) command).getGMFCommand();
		} else if (result instanceof OperationToGEFCommandWrapper) {
			result = ((OperationToGEFCommandWrapper) command).getOperation();
		}

		if (result != command) {
			// Could be turtles all the way down
			result = unwrap(result);
		}

		return result;
	}

	private boolean isCompound(Object command) {
		return ((command instanceof CompoundCommand) || (command instanceof org.eclipse.gef.commands.CompoundCommand) || (command instanceof ICompositeCommand));
	}

	private Iterator<?> iterator(Object compoundCommand) {
		if (compoundCommand instanceof CompoundCommand) {
			return ((CompoundCommand) compoundCommand).getCommandList().iterator();
		} else if (compoundCommand instanceof org.eclipse.gef.commands.CompoundCommand) {
			return ((org.eclipse.gef.commands.CompoundCommand) compoundCommand).getCommands().iterator();
		} else if (compoundCommand instanceof ICompositeCommand) {
			return ((ICompositeCommand) compoundCommand).iterator();
		} else {
			return Collections.emptyList().iterator();
		}
	}
}
