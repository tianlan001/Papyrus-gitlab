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

package org.eclipse.papyrus.infra.emf.readonly;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.papyrus.infra.core.resource.AbstractReadOnlyHandler;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

/**
 * A test rule that provides for manipulation of the {@linkplain AbstractReadOnlyHandler.ResourceReadOnlyCache read-only cache}.
 */
public class ReadOnlyCacheRule extends TestWatcher {

	private Executor originalExecutor;
	private QueuedExecutor myExecutor;

	public ReadOnlyCacheRule() {
		super();
	}

	/**
	 * Forces the tasks pending on the read-only cache executor to run now. Amongst possibly other things,
	 * this will cause the cache to be cleared, so that subsequent read-only queries will be computed anew.
	 */
	public void clear() {
		myExecutor.run();
	}

	@Override
	protected void starting(Description description) {
		myExecutor = new QueuedExecutor();
		originalExecutor = Activator.getDefault().setReadOnlyCacheExecutor(myExecutor);
	}

	@Override
	protected void finished(Description description) {
		try {
			clear();
		} finally {
			Activator.getDefault().setReadOnlyCacheExecutor(originalExecutor);
			originalExecutor = null;
			myExecutor = null;
		}
	}

	//
	// Nested types
	//

	private static class QueuedExecutor implements Executor {
		private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

		public void execute(Runnable command) {
			queue.offer(command);
		}

		void run() {
			for (Runnable next = queue.poll(); next != null; next = queue.poll()) {
				try {
					next.run();
				} catch (Exception e) {
					Activator.log.error("Uncaught exception in read-only cache runnable.", e); //$NON-NLS-1$
				}
			}
		}
	}
}
