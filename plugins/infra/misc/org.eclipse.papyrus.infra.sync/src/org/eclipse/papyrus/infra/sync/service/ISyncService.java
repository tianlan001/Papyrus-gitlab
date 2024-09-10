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

package org.eclipse.papyrus.infra.sync.service;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.sync.EMFDispatch;
import org.eclipse.papyrus.infra.sync.EMFDispatchManager;
import org.eclipse.papyrus.infra.sync.ISyncObject;
import org.eclipse.papyrus.infra.sync.policy.ISyncPolicy;

/**
 * A Papyrus Service providing EMF dispatch managers, synchronization registries, and other synchronization facilities.
 */
public interface ISyncService extends ISyncObject, IService {
	<D extends EMFDispatch> EMFDispatchManager<D> createSingleDispatchManager();

	<D extends EMFDispatch> EMFDispatchManager<D> createMultipleDispatchManager();

	IStatus evaluateTriggers(Object object);

	/**
	 * Obtains the sync service's current synchronization policy.
	 * 
	 * @return the synchronization policy. Never {@code null}
	 */
	ISyncPolicy getSyncPolicy();

	/**
	 * Sets the effective synchronization policy for the sync service.
	 * 
	 * @param syncPolicy
	 *            the new sync policy, or {@code null} to install a non-policy (meaning that everything is always synchronized)
	 */
	void setSyncPolicy(ISyncPolicy syncPolicy);

	/**
	 * Obtains the executor service on which to schedule asynchronous {@link SyncServiceRunnable}s.
	 * 
	 * @return the asynchronous execution service; never {@code null}
	 * 
	 * @see #setAsyncExecutor(Executor)
	 */
	Executor getAsyncExecutor();

	/**
	 * Sets the executor service on which to schedule asynchronous {@link SyncServiceRunnable}s.
	 * If different from the {@linkplain #getAsyncExecutor() current executor}, the current executor will be
	 * {@linkplain ExecutorService#shutdown() shut down} if it is an {@link ExecutorService}.
	 * 
	 * @param executor
	 *            the asynchronous execution service; must not be {@code null}
	 * 
	 * @see #getAsyncExecutor()
	 */
	void setAsyncExecutor(Executor executor);
}
