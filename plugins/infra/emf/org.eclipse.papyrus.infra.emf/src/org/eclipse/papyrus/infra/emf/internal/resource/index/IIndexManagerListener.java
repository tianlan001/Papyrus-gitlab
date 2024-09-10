/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.emf.internal.resource.index;

import java.util.function.Consumer;

/**
 * Call-back protocol for notification of lifecycle events in the {@link IndexManager}.
 */
public interface IIndexManagerListener {

	/**
	 * Notify that the given index {@code manager} is starting the orchestration of indexing jobs.
	 *
	 * @param manager
	 *            the notifying index manager
	 */
	void indexingStarting(IndexManager manager);

	/**
	 * Notify that the given index {@code manager} has finished the orchestration of indexing jobs.
	 *
	 * @param manager
	 *            the notifying index manager
	 */
	void indexingFinished(IndexManager manager);

	/**
	 * Notify that the given index {@code manager} has paused the orchestration of indexing jobs.
	 *
	 * @param manager
	 *            the notifying index manager
	 * 
	 * @see #indexingResumed(IndexManager)
	 * @see IndexManager#pause()
	 */
	void indexingPaused(IndexManager manager);

	/**
	 * Notify that the given index {@code manager} has resumed the orchestration of indexing jobs.
	 *
	 * @param manager
	 *            the notifying index manager
	 * 
	 * @see #indexingPaused(IndexManager)
	 * @see IndexManager#resume()
	 */
	void indexingResumed(IndexManager manager);

	static IIndexManagerListener startingAdapter(Consumer<? super IndexManager> callback) {
		return new IndexManagerAdapter() {

			@Override
			public void indexingStarting(IndexManager manager) {
				callback.accept(manager);
			}
		};
	}

	static IIndexManagerListener finishedAdapter(Consumer<? super IndexManager> callback) {
		return new IndexManagerAdapter() {

			@Override
			public void indexingFinished(IndexManager manager) {
				callback.accept(manager);
			}
		};
	}

	static IIndexManagerListener pausedAdapter(Consumer<? super IndexManager> callback) {
		return new IndexManagerAdapter() {

			@Override
			public void indexingPaused(IndexManager manager) {
				callback.accept(manager);
			}
		};
	}

	static IIndexManagerListener resumedAdapter(Consumer<? super IndexManager> callback) {
		return new IndexManagerAdapter() {

			@Override
			public void indexingResumed(IndexManager manager) {
				callback.accept(manager);
			}
		};
	}

}
