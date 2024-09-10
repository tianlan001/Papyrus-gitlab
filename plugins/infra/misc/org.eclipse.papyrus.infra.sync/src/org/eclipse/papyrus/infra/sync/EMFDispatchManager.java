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

package org.eclipse.papyrus.infra.sync;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.BasicEMap;
import org.eclipse.emf.common.util.EMap;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

/**
 * A manager of EMF dispatch registrations.
 */
public abstract class EMFDispatchManager<D extends EMFDispatch> {

	private final EMFListener listener;

	protected EMFDispatchManager(EMFListener listener) {
		super();

		this.listener = listener;
	}

	/**
	 * Creates a new dispatch manager that can manage multiple dispatches for each sync-item.
	 * 
	 * @param listener
	 *            the EMF listener that provides notifications
	 * @return a multi-dispatch manager
	 */
	public static <D extends EMFDispatch> EMFDispatchManager<D> createMultiple(EMFListener listener) {
		return new Multi<D>(listener);
	}

	/**
	 * Creates a new dispatch manager that manages a single dispatch for each sync-item.
	 * 
	 * @param listener
	 *            the EMF listener that provides notifications
	 * @return a single-dispatch manager
	 */
	public static <D extends EMFDispatch> EMFDispatchManager<D> createSingle(EMFListener listener) {
		return new Single<D>(listener);
	}

	public void add(SyncItem<?, ?> syncItem, D dispatch) {
		put(syncItem, dispatch);
		listener.add(dispatch);
	}

	public D getDispatcher(SyncItem<?, ?> syncItem, Object feature) {
		D result = null;

		for (D next : iterate(syncItem)) {
			EStructuralFeature nextFeature = next.getFeature();
			if ((nextFeature == feature) || (nextFeature == null)) {
				// A null feature indicates that any change triggers the synchronization
				result = next;
				break;
			}
		}

		return result;
	}

	protected abstract void put(SyncItem<?, ?> syncItem, D dispatch);

	protected abstract boolean isActive(SyncItem<?, ?> syncItem);

	protected abstract Iterable<SyncItem<?, ?>> getActive();

	protected abstract Iterable<D> iterate(SyncItem<?, ?> syncItem);

	protected abstract Iterable<Map.Entry<SyncItem<?, ?>, D>> activeEntries();

	protected abstract void clear();

	public void remove(SyncItem<?, ?> syncItem) {
		if (isActive(syncItem)) {

			for (Iterator<D> iter = iterate(syncItem).iterator(); iter.hasNext();) {
				listener.remove(iter.next());
				iter.remove();
			}
		}
	}

	public void remove(EMFDispatch dispatch) {
		for (Iterator<Map.Entry<SyncItem<?, ?>, D>> iter = activeEntries().iterator(); iter.hasNext();) {
			Map.Entry<SyncItem<?, ?>, D> next = iter.next();
			if (next.getValue() == dispatch) {
				listener.remove(dispatch);
				iter.remove();
			}
		}
	}

	public void removeAll() {
		for (SyncItem<?, ?> syncItem : getActive()) {
			for (EMFDispatch next : iterate(syncItem)) {
				listener.remove(next);
			}
		}

		clear();
	}

	//
	// Nested types
	//

	private static final class Multi<D extends EMFDispatch> extends EMFDispatchManager<D> {
		/**
		 * Currently active dispatchers.
		 */
		private final Multimap<SyncItem<?, ?>, D> active = ArrayListMultimap.create();

		Multi(EMFListener listener) {
			super(listener);
		}

		@Override
		protected void put(SyncItem<?, ?> syncItem, D dispatch) {
			active.put(syncItem, dispatch);
		}

		@Override
		protected boolean isActive(SyncItem<?, ?> syncItem) {
			return active.containsKey(syncItem);
		}

		@Override
		protected Iterable<SyncItem<?, ?>> getActive() {
			return active.keySet();
		}

		@Override
		protected Iterable<D> iterate(SyncItem<?, ?> syncItem) {
			return active.get(syncItem);
		}

		@Override
		protected Iterable<Entry<SyncItem<?, ?>, D>> activeEntries() {
			return active.entries();
		}

		@Override
		protected void clear() {
			active.clear();
		}
	}

	private static final class Single<D extends EMFDispatch> extends EMFDispatchManager<D> {
		/**
		 * Currently active dispatchers.
		 */
		private final EMap<SyncItem<?, ?>, D> active = new BasicEMap<SyncItem<?, ?>, D>() {
			private static final long serialVersionUID = 1L;

			@Override
			protected void didRemove(BasicEMap.Entry<SyncItem<?, ?>, D> entry) {
				mapping.clear();
			}

			@Override
			protected void didClear(BasicEList<BasicEMap.Entry<SyncItem<?, ?>, D>>[] oldEntryData) {
				mapping.clear();
			}
		};

		private final SingletonMapping mapping = new SingletonMapping();

		Single(EMFListener listener) {
			super(listener);
		}

		@Override
		protected void put(SyncItem<?, ?> syncItem, D dispatch) {
			active.put(syncItem, dispatch);
		}

		@Override
		protected boolean isActive(SyncItem<?, ?> syncItem) {
			return active.containsKey(syncItem);
		}

		@Override
		protected Iterable<SyncItem<?, ?>> getActive() {
			return active.keySet();
		}

		@Override
		protected Iterable<D> iterate(SyncItem<?, ?> syncItem) {
			return mapping.prime(syncItem);
		}

		@Override
		protected Iterable<Entry<SyncItem<?, ?>, D>> activeEntries() {
			return active.entrySet();
		}

		@Override
		protected void clear() {
			active.clear();
		}

		//
		// Nested types
		//

		private final class SingletonMapping implements Iterable<D> {
			private SyncItem<?, ?> key;
			private D value;

			private final Iterator<D> iterator = new Iterator<D>() {
				@Override
				public boolean hasNext() {
					return value != null;
				}

				@Override
				public D next() {
					if (value == null) {
						throw new NoSuchElementException();
					}
					D result = value;
					value = null;
					return result;
				}

				@Override
				public void remove() {
					active.remove(key);
				}
			};

			@Override
			public Iterator<D> iterator() {
				return iterator;
			}

			Iterable<D> prime(SyncItem<?, ?> key) {
				this.key = key;
				this.value = active.get(key);
				return this;
			}

			void clear() {
				this.key = null;
				this.value = null;
			}
		}
	}
}
