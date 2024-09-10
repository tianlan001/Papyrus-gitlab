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

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * An analogue of the Eclipse JFace {@code IRunnableWithProgress} interface,
 * a protocol for executable computations that can report measurable progress.
 * Implementations of the {@link IExecutorService} can supply suitable progress
 * reporting to these callables.
 * 
 * @see IExecutorService
 * @since 2.0
 */
@FunctionalInterface
public interface IProgressCallable<V> {
	/**
	 * Computes a result.
	 * 
	 * @param monitor
	 *            for reporting of progress of the task
	 * 
	 * @return the result of the computation
	 * 
	 * @throws Exception
	 *             if the computation fails unrecoverably
	 */
	V call(IProgressMonitor monitor) throws Exception;

	/**
	 * Adapts a plain Java {@code runnable} task to a progress-runnable task.
	 * 
	 * @param label
	 *            an user-presentable label for the task
	 * @param runnable
	 *            a plain runnable
	 * 
	 * @return a progress runnable decorating the plain {@code runnable}
	 */
	static <V> IProgressCallable<V> convert(String label, Callable<V> callable) {
		return progress -> {
			if (progress != null) {
				progress.beginTask(label, IProgressMonitor.UNKNOWN);
			}

			try {
				return callable.call();
			} finally {
				if (progress != null) {
					progress.done();
				}
			}
		};
	}
}
