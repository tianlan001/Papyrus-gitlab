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

package org.eclipse.papyrus.infra.core.utils;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.papyrus.junit.utils.rules.HouseKeeper;
import org.junit.Rule;
import org.junit.Test;

/**
 * Automated tests for the {@link JobExecutorService} class.
 */
public class JobExecutorServiceTest {

	@Rule
	public final HouseKeeper houseKeeper = new HouseKeeper();

	public JobExecutorServiceTest() {
		super();
	}

	@Test
	public void executeAndShutDown() throws Exception {
		ExecutorService exec = createFixture();

		LatchedRunnable run1 = new LatchedRunnable();
		LatchedRunnable run2 = new LatchedRunnable();

		exec.execute(run1);
		exec.execute(run2);

		run1.release();
		run2.release();

		run1.assertDone();
		run2.assertDone();

		exec.shutdown();
		assertTerminated(exec);
	}

	@Test
	public void executeAndShutDownNow() throws Exception {
		ExecutorService exec = createFixture();

		LatchedRunnable run1 = new LatchedRunnable();
		LatchedRunnable run2 = new LatchedRunnable();

		exec.execute(run1);
		exec.execute(run2);

		// Do not release the runnables!

		List<Runnable> pending = exec.shutdownNow();

		assertShutdown(exec);
		assertThat(pending, hasItem(run2)); // Serial execution didn't get this far

		run1.assertNotDone();
		run2.assertNotDone();

		// Just to clean up
		run1.release();
		run2.release();

		assertTerminated(exec);
	}

	//
	// Test framework
	//

	ExecutorService createFixture() {
		return houseKeeper.cleanUpLater(new JobExecutorService(), "shutdownNow");
	}

	void assertShutdown(ExecutorService executorService) throws Exception {
		assertThat("Executor not terminated", executorService.isShutdown(), is(true));
	}

	void assertTerminated(ExecutorService executorService) throws Exception {
		assertThat("Executor not shut down", executorService.awaitTermination(1L, TimeUnit.SECONDS), is(true));
		assertShutdown(executorService);
		assertThat("Executor not terminated", executorService.isTerminated(), is(true));
	}

	static class LatchedRunnable implements Runnable {
		private final CountDownLatch startLatch = new CountDownLatch(1);
		private final CountDownLatch doneLatch = new CountDownLatch(1);

		private volatile boolean interrupted;
		private volatile boolean done;

		public void run() {
			try {
				startLatch.await();

				done = true;
				doneLatch.countDown();
			} catch (InterruptedException e) {
				interrupted = true;
			}
		}

		void release() {
			startLatch.countDown();
		}

		void assertNotInterrupted() {
			assertThat("Runnable was interrupted", interrupted, is(false));
		}

		void assertDone() throws InterruptedException {
			assertNotInterrupted();

			doneLatch.await(1L, TimeUnit.SECONDS);
			assertThat("Runnable not done", done, is(true));
		}

		void assertNotDone() throws InterruptedException {
			assertNotInterrupted();

			assertThat("Runnable is done", done, is(false));
		}
	}
}
