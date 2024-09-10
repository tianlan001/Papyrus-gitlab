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

package org.eclipse.papyrus.infra.emf.gmf.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper;

/**
 * An iterator over the tree structure of EMF, GEF, and GMF commands that returns leaf commands of one or all of these kinds,
 * with accounting for the various kinds of wrappers employed to intermix them. This iterator does not support the
 * optional {@link Iterator#remove()} operation.
 */
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

		if (command instanceof ICommandWrapper<?>) {
			result = ((ICommandWrapper<?>) command).getWrappedCommand();
		} else if (ICommandWrapper.isWrapper(command, Object.class)) {
			// Try a registered foreign wrapper
			result = ICommandWrapper.unwrap(command, Object.class);
		}

		if (result != command) {
			// Could be turtles all the way down
			result = unwrap(result);
		}

		return result;
	}

	private boolean isCompound(Object command) {
		return CommandUtils.isCompound(command);
	}

	private Iterator<?> iterator(Object compoundCommand) {
		return CommandUtils.getChildren(compoundCommand).iterator();
	}
}
