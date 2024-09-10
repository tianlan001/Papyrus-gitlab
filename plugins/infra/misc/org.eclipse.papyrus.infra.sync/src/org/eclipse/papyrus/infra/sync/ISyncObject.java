/*****************************************************************************
 * Copyright (c) 2015, 2021 Christian W. Damus and others.
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
 *   Quentin Le Menez - bug 570177
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.sync;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.guava.internal.CheckedFuture;
import org.eclipse.papyrus.infra.sync.service.ISyncService;
import org.eclipse.papyrus.infra.sync.service.SyncServiceRunnable;

/**
 * Common interface of objects in the synchronization framework.
 */
public interface ISyncObject {
	/**
	 * Queries whether I am currently being actively synchronized. The only reason why I should not
	 * be synchronized is that I pertain to an object that is no longer in the model.
	 *
	 * @return whether I am participating in synchronization
	 */
	boolean isActive();

	/**
	 * Obtains a sync registry of a particular type.
	 *
	 * @param registryType
	 *            the sync registry to retrieve
	 * @return the registry (never {@code null} because it is created on-demand if necessary)
	 *
	 * @throws IllegalStateException
	 *             if the registry could not be instantiated for some reason
	 */
	<M, T, X, R extends SyncRegistry<M, T, X>> R getSyncRegistry(Class<R> registryType);

	/**
	 * Runs an operation in the context of the {@link ISyncService} that owns me.
	 * The {@link SyncServiceRunnable.Safe Safe} variant does not throw a checked exception.
	 *
	 * @param operation
	 *            a sync-service operation
	 * @return the result of the {@code operation}, if it completes without throwing
	 *
	 * @throws X
	 *             a checked exception that the {@code operation} may optionally declare
	 *
	 * @see SyncServiceRunnable.Safe
	 */
	<V, X extends Exception> V run(SyncServiceRunnable<V, X> operation) throws X;

	/**
	 * Asynchronously runs an operation in the context of the {@link ISyncService} that owns me.
	 * The {@link SyncServiceRunnable.Safe Safe} variant does not throw a checked exception.
	 *
	 * @param operation
	 *            a sync-service operation
	 * @return the future result of the {@code operation}
	 *
	 * @see SyncServiceRunnable.Safe
	 * @since 3.1
	 */
	<V, X extends Exception> CheckedFuture<V, X> runAsync(SyncServiceRunnable<V, X> operation);

	TransactionalEditingDomain getEditingDomain();

	/**
	 * Executes the specified {@code command} in the context of the current {@linkplain #getEditingDomain() editing domain}.
	 * If the domain has a read/write transaction in progress, then the command is executed as a nested transaction.
	 * Otherwise, it is assumed that the context of the command is an initial synchronization on opening a model or diagram
	 * and the command is executed in an unprotected write transaction.
	 *
	 * @param command
	 *            a command to execute in the current editing domain
	 */
	void execute(Command command);
}
