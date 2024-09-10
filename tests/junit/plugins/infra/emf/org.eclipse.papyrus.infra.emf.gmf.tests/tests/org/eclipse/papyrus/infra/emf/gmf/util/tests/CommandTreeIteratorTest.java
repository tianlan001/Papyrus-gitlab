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

package org.eclipse.papyrus.infra.emf.gmf.util.tests;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.gmf.runtime.common.core.command.AbstractCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.papyrus.infra.emf.gmf.command.ICommandWrapper;
import org.eclipse.papyrus.infra.emf.gmf.util.CommandTreeIterator;
import org.junit.Test;

/**
 * Test cases for the {@link CommandTreeIterator} class.
 */
public class CommandTreeIteratorTest {

	/**
	 * Initializes me.
	 */
	public CommandTreeIteratorTest() {
		super();
	}

	/**
	 * Verifies that chains of command wrappers are followed in unwrapping.
	 * 
	 * @see <a href="http://eclip.se/496598">bug 496598</a>
	 */
	@Test
	public void handlesWrapperChains() {
		// We will look for this in the command tree
		ICommand nugget = new TestCommand();

		// Wrap the nugget in an EMF wrapper in a GMF wrapper
		ICommand multiWrapped = ICommandWrapper.wrap(
				ICommandWrapper.wrap(nugget, Command.class),
				ICommand.class);

		// Sandwich the wrapper in a composite
		ICommand command = new TestCommand().compose(
				multiWrapped).compose(new TestCommand());

		// And dig for the nugget
		boolean found = false;
		for (CommandTreeIterator<ICommand> iter = CommandTreeIterator.iterateGMF(command); iter.hasNext();) {
			// Make sure that we always iterate, otherwise a successful test will loop forever
			found = (iter.next() == nugget) || found;
		}

		assertThat("Leaf command not found", found, is(true));
	}

	//
	// Test framework
	//

	static class TestCommand extends AbstractCommand {
		TestCommand() {
			super("Test");
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			return CommandResult.newOKCommandResult();
		}

		@Override
		protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			return CommandResult.newOKCommandResult();
		}

		@Override
		protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			return CommandResult.newOKCommandResult();
		}
	}
}
