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

package org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core.util;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantOperation;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.uml2.common.util.CacheAdapter;

import com.google.common.base.Function;

/**
 * A simple weak cache of computed data for some input type.
 *
 * @param <I>
 *            the input type, on which the provider computes provided data
 * @param <T>
 *            the kind of data cached
 */
public class ProviderCache<I, T> {
	private final Function<? super I, ? extends T> cacheFunction;

	private Reference<I> ref;
	private T cache;

	/**
	 * Initializes me with a function that lazily computes the provided data from the input if I have not already cached it.
	 *
	 * @param cacheFunction
	 *            the cache populator function
	 */
	private ProviderCache(Function<? super I, ? extends T> cacheFunction) {
		super();

		this.cacheFunction = cacheFunction;
	}

	/**
	 * Gets the cache for a particular provider operation.
	 *
	 * @param provider
	 *            the provider for which to get the cache
	 * @param key
	 *            the provider operation for which to get the cache
	 * @return the cache, or {@code null} if there is no cache for this {@code provider}. The caches may be flushed at any time, for example
	 *         when the provider model is reloaded
	 */
	@SuppressWarnings("unchecked")
	public static <I, T> ProviderCache<I, T> getCache(ModelingAssistantProvider provider, Class<? extends IModelingAssistantOperation> key) {
		ProviderCache<I, T> result = null;

		CacheAdapter adapter = CacheAdapter.getCacheAdapter(provider); // bug 541590 [CDO] - change is not required here
		if (adapter != null) {
			result = (ProviderCache<I, T>) adapter.get(provider, key);
		}

		return result;
	}

	/**
	 * Creates or replaces the cache for a particular provider operation.
	 *
	 * @param provider
	 *            the provider for which to create the cache
	 * @param key
	 *            the provider operation for which to create the cache
	 * @param cacheFunction
	 *            the cache populator function
	 * @return the new cache (never {@code null})
	 */
	public static <I, T> ProviderCache<I, T> cache(ModelingAssistantProvider provider, Class<? extends IModelingAssistantOperation> key, Function<? super I, ? extends T> cacheFunction) {
		ProviderCache<I, T> result = new ProviderCache<>(cacheFunction);

		CacheAdapter adapter = CacheAdapter.getCacheAdapter(provider); // bug 541590 [CDO] - change is not required here
		if (adapter != null) {
			adapter.put(provider, key, result);
		}

		return result;
	}

	/**
	 * Obtains the cached data for the specified input.
	 *
	 * @param input
	 *            an input object
	 * @return the corresponding data
	 */
	public T get(I input) {
		T result = null;

		I lastKnown = (ref != null) ? ref.get() : null;
		if (input == lastKnown) {
			result = cache;
		} else {
			result = cacheFunction.apply(input);
			ref = new WeakReference<>(input);
			cache = result;
		}

		return result;
	}
}
