/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Laurent Wouters laurent.wouters@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 465416
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.sync;

import java.util.Map;

import org.eclipse.papyrus.infra.sync.service.ISyncService;
import org.eclipse.papyrus.infra.sync.service.SyncServiceRunnable;

import com.google.common.collect.Maps;
import com.google.common.reflect.TypeToken;

/**
 * Represents a registry of synchronization buckets
 *
 * @author Laurent Wouters
 *
 * @param <M>
 *            The type of the underlying model element common to all synchronized items in a single bucket
 * @param <T>
 *            The type of the backend element to synchronize
 * @param <X>
 *            The type of change message carried by sync requests
 */
public abstract class SyncRegistry<M, T, X> extends SyncObject {
	private final Class<M> modelType;
	private final Class<T> backendType;

	/**
	 * The registry of buckets
	 */
	private final Map<M, SyncBucket<M, T, X>> buckets;

	/**
	 * Initializes this registry
	 */
	@SuppressWarnings("unchecked")
	public SyncRegistry() {
		super();

		@SuppressWarnings("serial")
		TypeToken<M> modelType = new TypeToken<M>(getClass()) {
		};
		this.modelType = (Class<M>) modelType.getRawType();

		@SuppressWarnings("serial")
		TypeToken<T> backendType = new TypeToken<T>(getClass()) {
		};
		this.backendType = (Class<T>) backendType.getRawType();

		// Synchronize buckets in the order in which they are registered
		this.buckets = Maps.newLinkedHashMap();
	}

	public final Class<M> getModelType() {
		return modelType;
	}

	public final Class<T> getBackendType() {
		return backendType;
	}

	/**
	 * A sync registry is always active.
	 * 
	 * @return {@code true}
	 */
	@Override
	public boolean isActive() {
		return true;
	}

	/**
	 * Gets the front-end model element for the specified {@code backend} element.
	 *
	 * @param backend
	 *            A back-end element
	 * @return The corresponding front-end model element
	 */
	protected abstract M getModelOf(T backend);

	/**
	 * Gets the registered bucket for the specified model.
	 *
	 * @param model
	 *            A model element
	 * @return The registered bucket for the specified {@code model}, or {@code null} if no {@linkplain ISyncObject#isActive() enabled} bucket
	 *         exists for the {@code model}
	 */
	public SyncBucket<M, T, X> getBucket(M model) {
		SyncBucket<M, T, X> result = buckets.get(model);

		if ((result != null) && !result.isActive()) {
			buckets.remove(result);
			result = null;
		}

		return result;
	}

	/**
	 * Registers the specified bucket
	 *
	 * @param bucket
	 *            A bucket
	 */
	public void register(SyncBucket<M, T, X> bucket) {
		if (!bucket.isActive()) {
			return;
		}
		buckets.put(bucket.getModel(), bucket);
	}

	/**
	 * Unregisters the specified bucket
	 *
	 * @param bucket
	 *            A bucket
	 */
	public void unregister(SyncBucket<M, T, X> bucket) {
		if (!bucket.isActive()) {
			return;
		}
		buckets.remove(bucket.getModel());
	}

	/**
	 * Determines whether the specified element is synchronized
	 *
	 * @param element
	 *            An element
	 * @return <code>true</code> if the element is synchronized
	 */
	public boolean isSynchronized(T element) {
		SyncBucket<M, T, X> bucket = getBucketFor(element);
		if (bucket == null) {
			return false;
		}
		return (bucket.get(element) != null);
	}

	/**
	 * Synchronizes the specified element
	 *
	 * @param element
	 *            An element
	 */
	public void synchronize(final T element) {
		final SyncBucket<M, T, X> bucket = getBucketFor(element);
		if (bucket == null) {
			return;
		}

		run(new SyncServiceRunnable.Safe<SyncItem<M, T>>() {
			@Override
			public SyncItem<M, T> run(ISyncService syncService) {
				return bucket.add(element);
			}
		});
	}

	/**
	 * Desynchronizes the specified element
	 *
	 * @param element
	 *            An element
	 */
	public void desynchronize(final T element) {
		final SyncBucket<M, T, X> bucket = getBucketFor(element);
		if (bucket == null) {
			return;
		}

		run(new SyncServiceRunnable.Safe<SyncItem<M, T>>() {
			@Override
			public SyncItem<M, T> run(ISyncService syncService) {
				return bucket.remove(element);
			}
		});
	}

	/**
	 * Queries whether the specified back-end element is synchronized by this registry in the bucket for the
	 * given front-end {@code model}.
	 * 
	 * @param backEnd
	 *            a potentially synchronized back-end
	 * @param model
	 *            a front-end of synchronization
	 * 
	 * @return whether the back-end is synchronized with the given {@code model}, even if this is now obsolete
	 *         knowledge because either or both are no longer {@linkplain ISyncObject#isActive() active}
	 */
	public boolean synchronizes(final T backEnd, final M model) {
		SyncBucket<M, T, X> bucket = buckets.get(model);
		return (bucket != null) && bucket.synchronizes(backEnd);
	}

	/**
	 * Determines the appropriate bucket for the specified element
	 *
	 * @param element
	 *            An element
	 * @return The appropriate bucket, or {@code null} if no {@linkplain ISyncObject#isActive() active} bucket
	 *         exists for the {@code element}
	 */
	public SyncBucket<M, T, X> getBucketFor(T element) {
		if (element == null) {
			return null;
		}
		M frontend = getModelOf(element);
		if (frontend == null) {
			return null;
		}
		return getBucket(frontend);
	}
}
