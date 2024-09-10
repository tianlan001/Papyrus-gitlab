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

package org.eclipse.papyrus.infra.sync.internal;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.concurrent.Callable;

import org.eclipse.emf.common.util.WrappedException;

/**
 * Internal protocol for operations in the context of the {@link SyncService}.
 * All {@link SyncService} methods that can call out to client code (plug-in
 * extensions, listeners, etc.) must be implement as one of these operations.
 */
public abstract class SyncServiceOperation<V> implements Callable<V> {
	private final SyncService service;

	public SyncServiceOperation(SyncService service) {
		super();

		this.service = service;
	}

	@Override
	public final V call() throws Exception {
		return service.perform(this);
	}

	public final <X extends Throwable> V safeCall(Class<X> expected) throws X {
		V result;

		try {
			result = call();
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			// unwrap
			if (e instanceof InvocationTargetException) {
				Throwable t = ((InvocationTargetException) e).getTargetException();
				if (t instanceof Error) {
					// Always re-throw these
					throw (Error) t;
				} else {
					e = (Exception) t;
				}
			} else if (e instanceof WrappedException) {
				e = ((WrappedException) e).exception();
			}

			if (e instanceof RuntimeException) {
				// Just re-throw it
				throw (RuntimeException) e;
			}

			if (expected.isInstance(e)) {
				throw expected.cast(e);
			}
			throw new UndeclaredThrowableException(e);
		}

		return result;
	}

	public final V safeCall() {
		return safeCall(RuntimeException.class);
	}

	protected abstract V doCall() throws Exception;
}
