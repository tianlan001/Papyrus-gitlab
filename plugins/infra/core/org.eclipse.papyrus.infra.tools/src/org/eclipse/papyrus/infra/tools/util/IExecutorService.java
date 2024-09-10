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

package org.eclipse.papyrus.infra.tools.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * A specialized {@link ExecutorService} that also provides for synchronous
 * execution of tasks, with the possibility that these could be optimized
 * where appropriate (such as in a UI-thread executor to use a {@code syncExec}
 * call).
 * 
 * @since 2.0
 */
public interface IExecutorService extends ExecutorService {
	/**
	 * Synchronously executes a {code task}. The task will run on the
	 * appropriate executor thread, as usual, but control will return
	 * to the caller only when its execution is complete.
	 * 
	 * @param task
	 *            the task to execute
	 * 
	 * @throws InterruptedException
	 *             on interruption, as per {@link Future#get()}
	 * @throws ExecutionException
	 *             on failed execution, as per {@link Future#get()}
	 */
	void syncExec(Runnable task) throws InterruptedException, ExecutionException;

	/**
	 * Synchronously invokes a {code callable}. The callable will run on the
	 * appropriate executor thread, as usual, but control will return
	 * to the caller only when its execution is complete.
	 * 
	 * @param callable
	 *            the task to execute
	 * 
	 * @throws InterruptedException
	 *             on interruption, as per {@link Future#get()}
	 * @throws ExecutionException
	 *             on failed execution, as per {@link Future#get()}
	 */
	<V> V syncCall(Callable<V> callable) throws InterruptedException, ExecutionException;

	/**
	 * Submits a {@code task} with support for progress reporting.
	 * 
	 * @param task
	 *            the progress-metered task to execute
	 * 
	 * @return a future of undefined type that can be used, for example, to wait for the {@code task} to complete
	 */
	Future<?> submit(IProgressRunnable task);

	/**
	 * Submits a {@code callable} with support for progress reporting.
	 * 
	 * @param task
	 *            the progress-metered task to execute
	 * 
	 * @return the future result of the {@code callable}
	 */
	<V> Future<V> submit(IProgressCallable<V> callable);

	/**
	 * Synchronously executes a progress-monitored {code task}. The task will run on the
	 * appropriate executor thread, as usual, but control will return
	 * to the caller only when its execution is complete.
	 * 
	 * @param task
	 *            the task to execute
	 * 
	 * @throws InterruptedException
	 *             on interruption, as per {@link Future#get()}
	 * @throws ExecutionException
	 *             on failed execution, as per {@link Future#get()}
	 */
	void syncExec(IProgressRunnable task) throws InterruptedException, ExecutionException;

	/**
	 * Synchronously invokes a progress-monitored {code callable}. The callable will run on the
	 * appropriate executor thread, as usual, but control will return
	 * to the caller only when its execution is complete.
	 * 
	 * @param callable
	 *            the task to execute
	 * 
	 * @throws InterruptedException
	 *             on interruption, as per {@link Future#get()}
	 * @throws ExecutionException
	 *             on failed execution, as per {@link Future#get()}
	 */
	<V> V syncCall(IProgressCallable<V> callable) throws InterruptedException, ExecutionException;
}
