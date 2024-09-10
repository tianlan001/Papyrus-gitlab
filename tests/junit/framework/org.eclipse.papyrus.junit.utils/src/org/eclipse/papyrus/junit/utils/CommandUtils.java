/*****************************************************************************
 * Copyright (c) 2018 Christian W. Damus and others.
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

package org.eclipse.papyrus.junit.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.commands.operations.ICompositeOperation;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.ICompositeCommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper;

import com.google.common.base.Strings;

/**
 * Utilities for testing and debugging commands.
 */
public class CommandUtils {

	/**
	 * Not instantiable by clients.
	 */
	private CommandUtils() {
		super();
	}

	/**
	 * Dump a {@code command} structure to standard error.
	 * 
	 * @param command
	 *            a command to dump
	 */
	public static void dump(Object command) {
		dump(command, System.err, 0);
	}

	/**
	 * Dump a {@code command} structure.
	 * 
	 * @param command
	 *            a command to dump
	 * @param sink
	 *            where to dump it
	 */
	public static void dump(Object command, Appendable sink) {
		dump(command, sink, 0);
	}

	static Object unwrap(Object command) {
		if (command instanceof ICommandProxy) {
			return ((ICommandProxy) command).getICommand();
		} else if (command instanceof CommandProxy) {
			return ((CommandProxy) command).getCommand();
		} else if (command instanceof ICommandWrapper<?>) {
			return ((ICommandWrapper<?>) command).getWrappedCommand();
		} else {
			return command;
		}
	}

	static void append(CharSequence text, Appendable sink) {
		try {
			sink.append(text);
		} catch (IOException e) {
			// best effort
		}
	}

	static void appendln(CharSequence text, Appendable sink) {
		try {
			sink.append(text).append(System.lineSeparator());
		} catch (IOException e) {
			// best effort
		}
	}

	static void appendln(Appendable sink) {
		try {
			sink.append(System.lineSeparator());
		} catch (IOException e) {
			// best effort
		}
	}

	static void dumpKind(Object command, Appendable sink, int depth) {
		String kind;
		if (command instanceof Command) {
			kind = command instanceof CompoundCommand ? "EMF*" : "EMF";
		} else if (command instanceof org.eclipse.gef.commands.Command) {
			kind = command instanceof org.eclipse.gef.commands.CompoundCommand ? "GEF*" : "GEF";
		} else if (command instanceof ICommand) {
			kind = command instanceof ICompositeCommand ? "GMF*" : "GMF";
		} else if (command instanceof IUndoableOperation) {
			kind = command instanceof ICompositeOperation ? "UOP*" : "UOP";
		} else {
			kind = "UNK";
		}

		StringBuilder buf = new StringBuilder(45);
		buf.append(Strings.padEnd(kind, 5, ' '));
		buf.append(Strings.repeat("  ", depth));

		String className = command.getClass().getName();
		buf.append(className.substring(className.lastIndexOf('.') + 1));
		buf.append(": ");

		append(buf, sink);
	}

	static void dump(Object command, Appendable sink, int depth) {
		Object unwrapped = unwrap(command);
		if (unwrapped != command) {
			dump(unwrapped, sink, depth);
			return;
		}

		dumpKind(command, sink, depth);

		if (command instanceof Command) {
			dump0((Command) command, sink, depth);
		} else if (command instanceof org.eclipse.gef.commands.Command) {
			dump0((org.eclipse.gef.commands.Command) command, sink, depth);
		} else if (command instanceof ICommand) {
			dump0((ICommand) command, sink, depth);
		} else if (command instanceof IUndoableOperation) {
			dump0((IUndoableOperation) command, sink, depth);
		} else {
			appendln("<unknown>", sink);
		}
	}

	static void dump0(org.eclipse.gef.commands.Command command, Appendable sink, int depth) {
		appendln(command.getLabel(), sink);

		if (command instanceof org.eclipse.gef.commands.CompoundCommand) {
			int nextDepth = depth + 1;
			org.eclipse.gef.commands.CompoundCommand compound = (org.eclipse.gef.commands.CompoundCommand) command;
			((List<?>) compound.getCommands()).stream().forEach(c -> dump(c, sink, nextDepth));
		}
	}

	static void dump0(Command command, Appendable sink, int depth) {
		appendln(command.getLabel(), sink);

		if (command instanceof CompoundCommand) {
			int nextDepth = depth + 1;
			CompoundCommand compound = (CompoundCommand) command;
			compound.getCommandList().stream().forEach(c -> dump(c, sink, nextDepth));
		}
	}

	static void dump0(ICommand command, Appendable sink, int depth) {
		appendln(command.getLabel(), sink);

		if (command instanceof ICompositeCommand) {
			int nextDepth = depth + 1;
			ICompositeCommand compound = (ICompositeCommand) command;
			((Iterator<?>) compound.iterator()).forEachRemaining(c -> dump(c, sink, nextDepth));
		}
	}

	static void dump0(IUndoableOperation command, Appendable sink, int depth) {
		appendln(command.getLabel(), sink);

		if (command instanceof ICompositeOperation) {
			int nextDepth = depth + 1;
			Collection<?> children = Collections.emptyList();

			try {
				Field childrenField = command.getClass().getDeclaredField("children"); //$NON-NLS-1$
				Object childrenValue = childrenField.get(command);
				if (childrenValue != null) {
					children = (childrenValue instanceof Collection<?>)
							? (Collection<?>) childrenValue
							: (childrenValue.getClass().isArray())
									? Arrays.asList((Object[]) childrenValue)
									: Collections.singleton(childrenValue);
				}
			} catch (Exception e) {
				// best effort
			}

			children.forEach(c -> dump(c, sink, nextDepth));
		}
	}

}
