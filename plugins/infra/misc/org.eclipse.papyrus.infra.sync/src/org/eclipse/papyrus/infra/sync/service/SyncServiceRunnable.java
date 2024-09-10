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

package org.eclipse.papyrus.infra.sync.service;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.Callable;

import org.eclipse.papyrus.infra.guava.internal.AbstractCheckedFuture;
import org.eclipse.papyrus.infra.guava.internal.CheckedFuture;
import org.eclipse.papyrus.infra.sync.ISyncObject;

import com.google.common.reflect.TypeToken;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;

/**
 * An operation that runs in the context of the {@link ISyncService synchronization service}.
 * The {@link SyncServiceRunnable.Safe Safe} variant does not throw a checked exception.
 *
 * @see ISyncService#run(SyncServiceRunnable)
 * @see SyncServiceRunnable.Safe
 */
public abstract class SyncServiceRunnable<V, X extends Exception> {

	@SuppressWarnings("serial")
	private final TypeToken<X> exceptionType = new TypeToken<>(getClass()) {
	};

	public abstract V run(ISyncService syncService) throws X;

	@SuppressWarnings("unchecked")
	public final Class<X> getExceptionType() {
		return (Class<X>) exceptionType.getRawType();
	}

	/**
	 * Obtains me as a {@link Runnable} checked future result in the context of a sync object.
	 *
	 * @param context
	 *            the synchronization context in which context I shall run
	 *
	 * @return a checked future result that implements that {@link Runnable} API for execution
	 * @since 3.1
	 */
	public CheckedFuture<V, X> asFuture(final ISyncObject context) {
		return checked(ListenableFutureTask.create(new Callable<V>() {
			@Override
			public V call() throws Exception {
				return context.run(SyncServiceRunnable.this);
			}
		}));
	}

	private CheckedFuture<V, X> checked(final ListenableFuture<V> future) {
		class Checked extends AbstractCheckedFuture<V, X> implements Runnable {
			Checked() {
				super(future);
			}

			@Override
			public void run() {
				((Runnable) future).run();
			}

			@Override
			protected X mapException(Exception e) {
				if (getExceptionType().isInstance(e)) {
					return getExceptionType().cast(e);
				} else {
					throw new UndeclaredThrowableException(e);
				}
			}
		}
		;

		return new Checked();
	}

	public static abstract class Safe<V> extends SyncServiceRunnable<V, RuntimeException> {
		@Override
		public abstract V run(ISyncService syncService);
	}
}
