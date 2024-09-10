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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.papyrus.infra.core.Activator;

import com.google.common.collect.Lists;

/**
 * An {@link ExecutorService} implemented by an Eclipse Platform {@link Job}.
 */
public class JobExecutorService extends AbstractExecutorService {

	private final ExecutorJob job = new ExecutorJob();

	public JobExecutorService() {
		super();
	}

	@Override
	public void shutdown() {
		job.shutdown();
	}

	@Override
	public List<Runnable> shutdownNow() {
		return job.shutdownNow();
	}

	@Override
	public boolean isShutdown() {
		return job.isShutdown();
	}

	@Override
	public boolean isTerminated() {
		return job.isDone();
	}

	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return job.awaitTermination(timeout, unit);
	}

	@Override
	public void execute(Runnable command) {
		job.add(command);
	}

	//
	// Nested types
	//

	private static class ExecutorJob extends Job {
		private static final AtomicInteger count = new AtomicInteger();

		private final AtomicBoolean shuttingDown = new AtomicBoolean();

		private final AtomicBoolean done = new AtomicBoolean();

		private final Lock lock = new ReentrantLock();
		private final Condition doneCond = lock.newCondition();

		private final Runnable poisonPill = new Runnable() {
			@Override
			public void run() {
				// Pass
			}
		};

		private Queue<Runnable> workQueue;

		ExecutorJob() {
			super("ExecutorJob-" + count.incrementAndGet());

			setSystem(true);
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			lock.lock();

			try {
				for (Runnable next = workQueue.poll(); next != null; next = workQueue.poll()) {
					if (next == poisonPill) {
						// We're done, here
						setDone();
						break;
					} else {
						// Let the queue be worked some more
						lock.unlock();

						try {
							next.run();
						} catch (Exception e) {
							Activator.log.error("Uncaught exception in ExecutorJob work item.", e); //$NON-NLS-1$
						} finally {
							// Lock the queue for the next go-around
							lock.lock();
						}
					}
				}
			} finally {
				workQueue = null;
				lock.unlock();
			}

			return Status.OK_STATUS;
		}

		void add(Runnable workItem) {
			if (isShutdown()) {
				throw new RejectedExecutionException("shutting down"); //$NON-NLS-1$
			}

			lock.lock();

			try {
				if (workQueue == null) {
					workQueue = new LinkedList<Runnable>();
					schedule();
				}
				workQueue.offer(workItem);
			} finally {
				lock.unlock();
			}
		}

		boolean isDone() {
			return done.get();
		}

		void shutdown() {
			if (shuttingDown.compareAndSet(false, true)) {
				lock.lock();

				try {
					if (workQueue == null) {
						// Already shut down. Signal done
						setDone();
					} else {
						workQueue.offer(poisonPill);
					}
				} finally {
					lock.unlock();
				}
			}
		}

		boolean isShutdown() {
			return shuttingDown.get();
		}

		/**
		 * @precondition The {@link #lock} is locked
		 */
		private void setDone() {
			done.set(true);
			doneCond.signalAll();
		}

		List<Runnable> shutdownNow() {
			// Don't accept new work
			shuttingDown.set(true);

			List<Runnable> result;

			lock.lock();

			try {
				if (workQueue == null) {
					// Already shut down
					result = Collections.emptyList();

					// Signal done
					setDone();
				} else {
					result = Lists.newArrayList(workQueue);
					workQueue.clear();

					// In case we just picked it up
					result.remove(poisonPill);

					// To be sure that we will shut down
					workQueue.offer(poisonPill);
				}
			} finally {
				lock.unlock();
			}

			return result;
		}

		boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
			boolean result;

			lock.lock();
			try {
				result = done.get();
				if (!result) {
					doneCond.await(timeout, unit);
				}
				result = done.get();
			} finally {
				lock.unlock();
			}

			return result;
		}
	}
}
