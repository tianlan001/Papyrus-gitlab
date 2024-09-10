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

import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;

import com.google.common.collect.Iterables;

/**
 * Interface for a policy that may be consulted to determine whether a {@link Runnable} should be submitted
 * to an {@link Executor} from the context of the current (calling) thread. These are useful for choosing
 * between multiple alternative executors, the one to use for any given task.
 */
public interface IExecutorPolicy {
	/**
	 * A executor policy that isn't a policy: it just {@linkplain Ranking#ACCEPTABLE accepts} anything.
	 */
	IExecutorPolicy NULL = new IExecutorPolicy() {
		@Override
		public Ranking rank(Runnable task, Executor executor) {
			return Ranking.ACCEPTABLE;
		}
	};

	/**
	 * Ranks the suitability of a given {@code executor} for submission of a particular {@code task}.
	 * 
	 * @param task
	 *            a task to be submitted
	 * @param executor
	 *            an executor to which the task might be submitted
	 * @return a ranking of how appropriate the {@code executor} is for the {@code task}
	 * 
	 * @see Ranking#select(IExecutorPolicy, Runnable, Executor, Executor)
	 * @see Ranking#select(IExecutorPolicy, Runnable, Iterable)
	 */
	Ranking rank(Runnable task, Executor executor);

	//
	// Nested types
	//

	/**
	 * Enumeration of executor rankings.
	 * 
	 * @see IExecutorPolicy#rank(Runnable, Executor)
	 */
	enum Ranking {
		/** A ranking indicating that the task must not be submitted to the executor in question. */
		DEPRECATED, //
		/** A ranking indicating that the task should only be submitted to the executor in question if no other is more suitable. */
		DISCOURAGED, //
		/** A ranking indicating that the executor in question is appropriate for submission of the task. */
		ACCEPTABLE, //
		/** A ranking indicating that the executor in question should be preferred over others for submission of the task. */
		PREFERRED, //
		/** A ranking indicating that the task must be submitted to the executor in question. */
		REQUIRED; //

		/**
		 * Selects the best executor for submission of a given {@code task} according to the {@code policy}.
		 * This is the most efficient variant of the {@code select} operation for a pair of executors.
		 * 
		 * @param policy
		 *            an executor policy
		 * @param task
		 *            a task to be submitted
		 * @param executor1
		 *            one executor
		 * @param executor2
		 *            an alternative executor
		 * @return the higher-ranked executor, or {@code executor1} in case of a tied ranking
		 * 
		 * @throws RejectedExecutionException
		 *             in the case that both executors are {@linkplain #DEPRECATED deprecated} by the {@code policy}
		 */
		public static Executor select(IExecutorPolicy policy, Runnable task, Executor executor1, Executor executor2) throws RejectedExecutionException {
			Ranking rank1 = policy.rank(task, executor1);
			Ranking rank2 = policy.rank(task, executor2);

			if (rank1 == DEPRECATED && rank2 == DEPRECATED) {
				// We cannot use either of these executors
				throw new RejectedExecutionException("All available executors rejected by policy");
			}

			// Take the first match in case of a tie
			return rank1.ordinal() < rank2.ordinal() ? executor2 : executor1;
		}

		/**
		 * Selects the best executor for submission of a given {@code task} according to the {@code policy}.
		 * 
		 * @param policy
		 *            an executor policy
		 * @param task
		 *            a task to be submitted
		 * @param executor1
		 *            one executor
		 * @param executor2
		 *            another executor
		 * @param executor3
		 *            a third executor
		 * @param more
		 *            optional additional executors
		 * @return the highest-ranked executor. In case of ties, the first executor of the highest rank is selected
		 * 
		 * @throws RejectedExecutionException
		 *             in the case that both executors are {@linkplain #DEPRECATED deprecated} by the {@code policy}
		 */
		public static Executor select(IExecutorPolicy policy, Runnable task, Executor executor1, Executor executor2, Executor executor3, Executor... more) throws RejectedExecutionException {
			return select(policy, task, Iterables.concat(Arrays.asList(executor1, executor2, executor3), Arrays.asList(more)));
		}

		/**
		 * Selects the best executor for submission of a given {@code task} according to the {@code policy}.
		 * 
		 * @param policy
		 *            an executor policy
		 * @param task
		 *            a task to be submitted
		 * @param executors
		 *            a collection of executors to choose from
		 * @return the highest-ranked executor. In case of ties, the first executor of the highest rank is selected
		 * 
		 * @throws RejectedExecutionException
		 *             in the case that both executors are {@linkplain #DEPRECATED deprecated} by the {@code policy}
		 */
		public static Executor select(IExecutorPolicy policy, Runnable task, Iterable<? extends Executor> executors) throws RejectedExecutionException {
			Iterator<? extends Executor> iter = executors.iterator();
			if (!iter.hasNext()) {
				throw new IllegalArgumentException("no executors to select"); //$NON-NLS-1$
			}

			Executor result = iter.next();
			Ranking currentRank = policy.rank(task, result);
			while ((currentRank != REQUIRED) && iter.hasNext()) {
				Executor next = iter.next();
				Ranking nextRank = policy.rank(task, next);
				if (nextRank.ordinal() > currentRank.ordinal()) {
					result = next;
					currentRank = nextRank;
				}
			}

			if (currentRank == DEPRECATED) {
				// We cannot use any of these executors
				throw new RejectedExecutionException("All available executors rejected by policy");
			}

			return result;
		}
	}
}
