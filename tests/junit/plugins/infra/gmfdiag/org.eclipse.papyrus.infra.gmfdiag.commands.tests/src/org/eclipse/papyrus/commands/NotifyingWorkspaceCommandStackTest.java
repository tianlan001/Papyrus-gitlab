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

package org.eclipse.papyrus.commands;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.transaction.TransactionalCommandStack;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.TransactionalEditingDomainImpl;
import org.eclipse.papyrus.infra.emf.gmf.command.NotifyingWorkspaceCommandStack;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Regression tests for the {@link NotifyingWorkspaceCommandStack} class.
 */
public class NotifyingWorkspaceCommandStackTest extends AbstractPapyrusTest {

	private TransactionalEditingDomain domain;
	private NotifyingWorkspaceCommandStack fixture;

	@Test
	public void dirtyAfterExecute() {
		assertNotDirty();
		execute(new TestCommand());
		assertDirty();
	}

	@Test
	public void notDirtyAfterExecuteNonDirtying() {
		assertNotDirty();
		execute(new NonDirtyingCommand());
		assertNotDirty();
	}

	@Test
	public void notDirtyAfterExecute_undo() {
		assertNotDirty();
		execute(new TestCommand());
		undo();
		assertNotDirty();
	}

	@Test
	public void notDirtyAfterExecuteNonDirtying_undo() {
		assertNotDirty();
		execute(new NonDirtyingCommand());
		undo();
		assertNotDirty();
	}

	@Test
	public void notDirtyAfterExecute_save() {
		assertNotDirty();
		execute(new TestCommand());
		save();
		assertNotDirty();
	}

	@Test
	public void dirtyAfterExecute_save_undo() {
		assertNotDirty();
		execute(new TestCommand());
		save();
		undo();
		assertDirty();
	}

	@Test
	public void notDirtyAfterExecute_save_undo_save() {
		assertNotDirty();
		execute(new TestCommand());
		save();
		undo();
		save();
		assertNotDirty();
	}

	@Test
	public void dirtyAfterExecute_save_undo_save_redo() {
		assertNotDirty();
		execute(new TestCommand());
		save();
		undo();
		save();
		redo();
		assertDirty();
	}

	@Test
	public void notDirtyAfterExecute_save_executeNonDirtying_undo_undo_redo_redo() {
		assertNotDirty();
		execute(new TestCommand());
		save();
		execute(new NonDirtyingCommand());
		undo();
		undo();
		redo();
		redo();
		assertNotDirty();
	}

	@Test
	public void notDirtyAfterExecute_save_execute_undo_undo_redo() {
		assertNotDirty();
		execute(new TestCommand());
		save();
		execute(new TestCommand());
		undo();
		undo();
		redo();
		assertNotDirty();
	}

	@Test
	public void dirtyAfterExecute_save_execute_undo_undo_save_redo() {
		assertNotDirty();
		execute(new TestCommand());
		save();
		execute(new TestCommand());
		undo();
		undo();
		save();
		redo();
		assertDirty();
	}

	//
	// Test framework
	//

	@Before
	public void createFixture() {
		fixture = new NotifyingWorkspaceCommandStack(CheckedOperationHistory.getInstance());
		AdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
		domain = new TransactionalEditingDomainImpl(adapterFactory, (TransactionalCommandStack) fixture);
	}

	@After
	public void destroyFixture() {
		// This disposes the command stack for us
		domain.dispose();
		domain = null;
		fixture = null;
	}

	void execute(Command command) {
		assertThat("Cannot execute", command.canExecute(), is(true));
		fixture.execute(command);
	}

	void undo() {
		assertThat("Cannot undo", fixture.canUndo(), is(true));
		fixture.undo();
	}

	void redo() {
		assertThat("Cannot redo", fixture.canRedo(), is(true));
		fixture.redo();
	}

	void save() {
		assertDirty();
		fixture.saveIsDone();
	}

	void assertDirty() {
		assertThat("Stack is not dirty", fixture.isSaveNeeded(), is(true));
	}

	void assertNotDirty() {
		assertThat("Stack is dirty", fixture.isSaveNeeded(), is(false));
	}

	static class TestCommand extends AbstractCommand {
		TestCommand() {
			super();
		}

		@Override
		public boolean canExecute() {
			return true;
		}

		public void execute() {
			// Pass
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public void undo() {
			// Pass
		}

		public void redo() {
			// Pass
		}
	}

	static class NonDirtyingCommand extends TestCommand implements AbstractCommand.NonDirtying {
		NonDirtyingCommand() {
			super();
		}
	}
}
