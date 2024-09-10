/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.CompositeCommand;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.diagram.ui.commands.CommandProxy;
import org.eclipse.gmf.runtime.diagram.ui.commands.ICommandProxy;
import org.eclipse.papyrus.commands.wrappers.EMFtoGEFCommandWrapper;
import org.eclipse.papyrus.infra.emf.gmf.command.EMFtoGMFCommandWrapper;
import org.eclipse.papyrus.commands.wrappers.GEFtoEMFCommandWrapper;
import org.eclipse.papyrus.infra.emf.gmf.command.GMFtoEMFCommandWrapper;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * Test suite for the {@link CommandTreeIterator} class.
 */
public class CommandTreeIteratorTest extends AbstractPapyrusTest {

	private Object fixture;

	private int gefCount;

	private int emfCount;

	private int gmfCount;

	public CommandTreeIteratorTest() {
		super();
	}

	@Test
	public void gefCompoundsAndWrappers() {
		assertThat(collectAll(fixture, Command.class).size(), is(gefCount));
	}

	@Test
	public void emfCompoundsAndWrappers() {
		assertThat(collectAll(fixture, org.eclipse.emf.common.command.Command.class).size(), is(emfCount));
	}

	@Test
	public void gmfCompoundsAndWrappers() {
		assertThat(collectAll(fixture, ICommand.class).size(), is(gmfCount));
	}

	@Test
	public void genericCompoundsAndWrappers() {
		assertThat(collectAll(fixture).size(), is(gefCount + emfCount + gmfCount));
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		CompoundCommand compoundGEFCommand = new CompoundCommand();

		// Add a simple EMF command wrapped
		compoundGEFCommand.add(EMFtoGEFCommandWrapper.wrap(new TestEMFCommand()));

		// Add a double-wrapped GMF command
		compoundGEFCommand.add(EMFtoGEFCommandWrapper.wrap(GMFtoEMFCommandWrapper.wrap(new TestGMFCommand())));

		// Add a (wrapped differently) GMF compound
		CompositeCommand compoundGMFCommand = new CompositeCommand("composite");
		compoundGEFCommand.add(new ICommandProxy(compoundGMFCommand));

		// Add a GEF command (wrapped) to the GMF compound
		compoundGMFCommand.add(new CommandProxy(new TestGEFCommand()));
		compoundGMFCommand.add(EMFtoGMFCommandWrapper.wrap(GEFtoEMFCommandWrapper.wrap(new TestGEFCommand())));

		// And now an EMF compound
		org.eclipse.emf.common.command.CompoundCommand compoundEMFCommand = new org.eclipse.emf.common.command.CompoundCommand();
		compoundGMFCommand.add(EMFtoGMFCommandWrapper.wrap(compoundEMFCommand));

		// And an EMF command to the EMF compound
		compoundEMFCommand.append(new TestEMFCommand());

		// and a GEF command
		compoundEMFCommand.append(GEFtoEMFCommandWrapper.wrap(new TestGEFCommand()));

		// Top it off with a GMF command on the GMF compound
		compoundGMFCommand.add(new TestGMFCommand());

		fixture = compoundGEFCommand;
	}

	<C> Set<C> collectAll(Object command, Class<C> type) {
		Set<C> result = Sets.newLinkedHashSet();

		for (Iterator<C> iter = CommandTreeIterator.iterate(command, type); iter.hasNext();) {
			result.add(iter.next());
		}

		return result;
	}

	Set<?> collectAll(Object command) {
		Set<Object> result = Sets.newLinkedHashSet();

		for (Iterator<?> iter = CommandTreeIterator.iterate(command); iter.hasNext();) {
			result.add(iter.next());
		}

		return result;
	}

	//
	// Nested types
	//

	private class TestEMFCommand extends AbstractCommand {
		TestEMFCommand() {
			super();

			if (fixture == null) {
				emfCount++;
			}
		}

		public void execute() {
			// Pass
		}

		public void redo() {
			// Pass
		}
	}

	private class TestGEFCommand extends Command {
		TestGEFCommand() {
			super();

			if (fixture == null) {
				gefCount++;
			}
		}
	}

	private class TestGMFCommand extends org.eclipse.gmf.runtime.common.core.command.AbstractCommand {
		TestGMFCommand() {
			super("test");

			if (fixture == null) {
				gmfCount++;
			}
		}

		@Override
		protected CommandResult doExecuteWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			return CommandResult.newCancelledCommandResult();
		}

		@Override
		protected CommandResult doRedoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			return CommandResult.newCancelledCommandResult();
		}

		@Override
		protected CommandResult doUndoWithResult(IProgressMonitor progressMonitor, IAdaptable info) throws ExecutionException {
			return CommandResult.newCancelledCommandResult();
		}
	}
}
