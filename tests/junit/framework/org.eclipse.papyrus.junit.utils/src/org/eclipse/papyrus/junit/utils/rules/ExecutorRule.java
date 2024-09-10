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

package org.eclipse.papyrus.junit.utils.rules;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;

import org.eclipse.papyrus.junit.utils.Activator;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.google.common.collect.Queues;

/**
 * A JUnit {@linkplain TestRule rule} that is an {@link Executor} running tasks at clean-up of the
 * test execution.
 */
public class ExecutorRule extends TestWatcher implements Executor {
	private final BlockingQueue<Runnable> queue = Queues.newLinkedBlockingQueue();

	public ExecutorRule() {
		super();
	}

	@Override
	public void execute(Runnable command) {
		queue.add(command);
	}

	protected void runPending() {
		for (Runnable next = queue.poll(); next != null; next = queue.poll()) {
			try {
				next.run();
			} catch (Exception e) {
				Activator.log.error("Uncaught exception in test shutdown runnable.", e); //$NON-NLS-1$
			}
		}
	}

	@Override
	protected void finished(Description description) {
		runPending();
	}
}
