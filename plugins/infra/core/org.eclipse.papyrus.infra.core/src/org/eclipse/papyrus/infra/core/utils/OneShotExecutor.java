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

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A wrapper for an {@link Executor} that permits only a single
 * {@link Runnable} to be pending execution at any time. If while
 * a task is pending additional tasks are requested for execution,
 * the last requested task will supersede the pending task so that
 * when some task eventually is executed, it will only be one
 * task. So, it is best to use this for asynchronous execution of
 * runnables that all do the same thing, where the aim is just to
 * avoid redundant execution.
 * 
 * @since 3.0
 */
public final class OneShotExecutor implements Executor {
	private final AtomicReference<Runnable> pending = new AtomicReference<>();
	private final Executor delegate;

	/**
	 * Initializes me with the {@code executor} on which to submit
	 * tasks.
	 * 
	 * @param executor
	 *            the executor to which I delegate execution of tasks
	 */
	public OneShotExecutor(Executor executor) {
		super();

		this.delegate = executor;
	}

	@Override
	public void execute(Runnable command) {
		delegate.execute(new Task(command));
	}

	//
	// Nested types
	//

	/**
	 * A one-shot task that replaces any pending task and executes
	 * its delegate if and only if it is still the pending task
	 * when its time to execute arrives.
	 */
	private final class Task implements Runnable {
		private final Runnable delegate;

		Task(Runnable command) {
			super();

			this.delegate = command;
			pending.set(this);
		}

		@Override
		public void run() {
			if (pending.compareAndSet(this, null)) {
				delegate.run();
			}
		}
	}
}
