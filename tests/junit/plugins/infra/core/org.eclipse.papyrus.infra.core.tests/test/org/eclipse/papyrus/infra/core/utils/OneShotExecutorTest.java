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

package org.eclipse.papyrus.infra.core.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * Test cases for the {@link OneShotExecutor} class.
 */
public class OneShotExecutorTest {

	private final TestExecutor executor = new TestExecutor();
	private final Executor fixture = new OneShotExecutor(executor);

	/**
	 * Initializes me.
	 */
	public OneShotExecutorTest() {
		super();
	}

	@Test
	public void executionsPiledUp() {
		final AtomicInteger count = new AtomicInteger();

		for (int i = 0; i < 5; i++) {
			fixture.execute(count::incrementAndGet);
		}

		executor.drain();

		assertThat("Not a one-shot execution", count.get(), is(1));
	}

	@Test
	public void executionsInSequence() {
		final AtomicInteger count = new AtomicInteger();

		for (int i = 0; i < 5; i++) {
			fixture.execute(count::incrementAndGet);
			executor.drain();
		}

		assertThat("Wrong number of executions", count.get(), is(5));
	}

	//
	// Test framework
	//

	private static final class TestExecutor implements Executor {
		Queue<Runnable> queue = new LinkedList<>();

		@Override
		public void execute(Runnable command) {
			queue.add(command);
		}

		void drain() {
			for (Runnable next = queue.poll(); next != null; next = queue.poll()) {
				try {
					next.run();
				} catch (Exception e) {
					e.printStackTrace();
					fail("Uncaught exception in test runnable: " + e.getMessage());
				}
			}
		}
	}
}
