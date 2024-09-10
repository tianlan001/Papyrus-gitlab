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
import org.eclipse.papyrus.infra.sync.internal.SyncService;
import org.eclipse.papyrus.infra.sync.service.ISyncService;
import org.eclipse.papyrus.infra.sync.service.SyncServiceRunnable;

/**
 * A core synchronization framework object.
 */
public abstract class SyncObject implements ISyncObject {

	private final ISyncService syncService;

	SyncObject() {
		this(SyncService.getCurrent());
	}

	SyncObject(ISyncService syncService) {
		super();

		if (syncService == null) {
			throw new IllegalStateException("Must be created within a SyncServiceRunnable"); //$NON-NLS-1$
		}

		this.syncService = syncService;
	}

	final ISyncService getSyncService() {
		return syncService;
	}

	@Override
	public TransactionalEditingDomain getEditingDomain() {
		return getSyncService().getEditingDomain();
	}

	@Override
	public <V, X extends Exception> V run(SyncServiceRunnable<V, X> operation) throws X {
		return getSyncService().run(operation);
	}

	@Override
	public <M, T, X, R extends SyncRegistry<M, T, X>> R getSyncRegistry(Class<R> registryType) {
		return getSyncService().getSyncRegistry(registryType);
	}

	@Override
	public void execute(Command command) {
		getSyncService().execute(command);
	}

	@Override
	public <V, X extends Exception> CheckedFuture<V, X> runAsync(SyncServiceRunnable<V, X> operation) {
		CheckedFuture<V, X> result = operation.asFuture(this);
		getSyncService().getAsyncExecutor().execute((Runnable) result);
		return result;
	}
}
