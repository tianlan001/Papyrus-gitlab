/*
 * Copyright (c) 2014, 2016 CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bug 485220
 *
 */
package org.eclipse.papyrus.commands.util;

import java.util.Map;

import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.papyrus.infra.emf.gmf.command.INonDirtying;


/**
 * Utilities for working with non-dirtying EMF, GEF, and GMF commands.
 *
 * @see INonDirtying
 * @see AbstractCommand.NonDirtying
 * 
 * @deprecated Use the {@link org.eclipse.papyrus.infra.emf.gmf.util.CommandUtils} API, instead.
 */
@Deprecated
public class NonDirtyingUtils {

	/**
	 * Not instantiable by clients.
	 */
	private NonDirtyingUtils() {
		super();
	}

	public static IUndoableOperation wrap(TransactionalEditingDomain domain, Command command) {
		return org.eclipse.papyrus.infra.emf.gmf.util.CommandUtils.wrap(domain, command);
	}

	public static IUndoableOperation wrap(TransactionalEditingDomain domain, Command command, Map<?, ?> options) {
		return org.eclipse.papyrus.infra.emf.gmf.util.CommandUtils.wrap(domain, command, options);
	}

	public static CompoundCommand nonDirtyingEMFCompound() {
		return org.eclipse.papyrus.infra.emf.gmf.util.CommandUtils.nonDirtyingEMFCompound();
	}

	/**
	 * Wrap a possibly {@linkplain INonDirtying non-dirtying} GEF command as a GMF command.
	 *
	 * @param command
	 *            a GEF command
	 * @return the most appropriate wrapper GMF command
	 */
	public static ICommand wrap(org.eclipse.gef.commands.Command command) {
		ICommand result;

		if (command instanceof INonDirtying) {
			result = new NonDirytingCommandProxy(command);
		} else {
			result = new CommandProxy(command);
		}

		return result;
	}

	public static Command chain(Command command1, Command command2) {
		return org.eclipse.papyrus.infra.emf.gmf.util.CommandUtils.chain(command1, command2);
	}

	public static org.eclipse.gef.commands.CompoundCommand nonDirtyingGEFCompound() {
		return new NonDirtyingGEFCompoundCommand();
	}

	public static org.eclipse.gef.commands.Command chain(org.eclipse.gef.commands.Command command1, org.eclipse.gef.commands.Command command2) {
		if ((command1 instanceof INonDirtying) && (command2 instanceof INonDirtying)) {
			return new NonDirtyingGEFCompoundCommand().chain(command1).chain(command2);
		}
		return command1.chain(command2);
	}

	public static CompositeCommand nonDirtyingGMFComposite(String label) {
		return org.eclipse.papyrus.infra.emf.gmf.util.CommandUtils.nonDirtyingGMFComposite(label);
	}

	public static ICommand compose(ICommand command1, ICommand command2) {
		return org.eclipse.papyrus.infra.emf.gmf.util.CommandUtils.compose(command1, command2);
	}

	//
	// Nested types
	//

	private static class NonDirtyingGEFCompoundCommand extends org.eclipse.gef.commands.CompoundCommand implements INonDirtying {

		@Override
		public void add(org.eclipse.gef.commands.Command command) {
			// GEF compounds allow appending null commands, which just has no effect
			if (command != null) {
				checkNonDirtying(command);
				super.add(command);
			}
		}

		@Override
		public org.eclipse.gef.commands.Command chain(org.eclipse.gef.commands.Command command) {
			add(command);
			return this;
		}

		private void checkNonDirtying(org.eclipse.gef.commands.Command command) {
			if (!(command instanceof INonDirtying)) {
				throw new IllegalArgumentException("Attempt to append a dirtying command to a non-dirtying compound."); //$NON-NLS-1$
			}
		}
	}

	private static class NonDirytingCommandProxy extends CommandProxy implements INonDirtying {

		NonDirytingCommandProxy(org.eclipse.gef.commands.Command command) {
			super(command);
		}
	}

}
