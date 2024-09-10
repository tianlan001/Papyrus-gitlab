/*****************************************************************************
 * Copyright (c) 2013, 2015 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 479999
 *   Christian W. Damus - bug 469188
 *****************************************************************************/
package org.eclipse.papyrus.infra.tools.util;

import static com.google.common.collect.Iterables.filter;
import static com.google.common.collect.Iterables.find;

import java.util.function.Supplier;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.Notifier;

import com.google.common.base.Predicates;


public class PlatformHelper {

	/**
	 * Attempt to get an adapter of the specified target {@code type} from an {@code object}
	 * by any means available.
	 *
	 * @param object
	 *            an object to adapt
	 * @param type
	 *            the type of adapter to get
	 *
	 * @return the best-effort adapter of the given {@code type} or {@code null} if no
	 *         adapter is available
	 */
	public static <T> T getAdapter(Object object, Class<T> type) {
		T result = null;

		// Don't provide adapters for null
		if (object != null) {
			if (type.isInstance(object)) {
				result = type.cast(object);
			} else if (object instanceof IAdaptable) {
				result = getIntrinsicAdapter((IAdaptable) object, type);
			}

			if (result == null) {
				result = getExtrinsicAdapter(object, type);

				if ((result == null) && (object instanceof Notifier)) {
					result = getEMFAdapter((Notifier) object, type);
				}
			}
		}

		return result;
	}

	private static <T> T getIntrinsicAdapter(IAdaptable adaptable, Class<T> type) {
		T result = null;

		Object attempt = adaptable.getAdapter(type);
		if (type.isInstance(attempt)) {
			result = type.cast(attempt);
		}

		return result;
	}

	private static <T> T getExtrinsicAdapter(Object object, Class<T> type) {
		T result = null;

		Object attempt = Platform.getAdapterManager().getAdapter(object, type);
		if (type.isInstance(attempt)) {
			result = type.cast(attempt);
		}

		return result;
	}

	private static <T> T getEMFAdapter(Notifier notifier, Class<T> type) {
		return find(filter(notifier.eAdapters(), type), Predicates.alwaysTrue(), null);
	}

	/**
	 * Get an adapter of the specified target {@code type} from an {@code object} by any means available.
	 *
	 * @param object
	 *            an object to adapt. May be {@code null}, in which case the {@code defaultAdapter} is returned
	 * @param type
	 *            the type of adapter to get
	 * @param defaultAdapter
	 *            a default adapter to return if none can be obtained (may be {@code null}
	 *
	 * @return the best-effort adapter of the given {@code type}, else the {@code defaultAdapter}
	 * @since 2.0
	 */
	public static <T> T getAdapter(Object object, Class<T> type, T defaultAdapter) {
		T result = defaultAdapter;

		if (object != null) {
			T adapter = getAdapter(object, type);
			if (adapter != null) {
				result = adapter;
			}
		}

		return result;
	}

	/**
	 * Get an adapter of the specified target {@code type} from an {@code object} by any means available.
	 *
	 * @param object
	 *            an object to adapt. May be {@code null}, in which case the {@code defaultAdapter} is returned
	 * @param type
	 *            the type of adapter to get
	 * @param defaultSupplier
	 *            a supplier to consult for a default adapter in the case that none can be
	 *            obtained by the usual means (may be {@code null}
	 *
	 * @return the best-effort adapter of the given {@code type}, else the {@code defaultAdapter}
	 * @since 2.0
	 */
	public static <T> T getAdapter(Object object, Class<T> type, Supplier<T> defaultAdapter) {
		T result = null;

		if (object != null) {
			T adapter = getAdapter(object, type);
			if (adapter != null) {
				result = adapter;
			}
		}

		return (result != null) ? result : defaultAdapter.get();
	}
}
