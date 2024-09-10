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

package org.eclipse.papyrus.infra.editor.welcome.nattable.sorting;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.core.databinding.observable.IObservable;
import org.eclipse.core.databinding.observable.list.IListChangeListener;
import org.eclipse.core.databinding.observable.list.IObservableList;
import org.eclipse.core.databinding.observable.list.ListChangeEvent;

import ca.odell.glazedlists.AbstractEventList;
import ca.odell.glazedlists.event.ListEventPublisher;
import ca.odell.glazedlists.util.concurrent.LockFactory;

/**
 * @author damus
 *
 */
public class EventListObservableAdapter<E> extends AbstractEventList<E> {
	private final IChangeListener elementListener;
	private final IListChangeListener<E> listener;
	private IObservableList<E> delegate;

	public EventListObservableAdapter() {
		this(null);
	}

	public EventListObservableAdapter(ListEventPublisher publisher) {
		super(publisher);

		this.readWriteLock = LockFactory.DEFAULT.createReadWriteLock();

		this.listener = new IListChangeListener<E>() {
			@Override
			public void handleListChange(ListChangeEvent<? extends E> event) {
				readWriteLock.readLock().lock();
				try {
					updates.beginEvent();
					try {
						Stream.of(event.diff.getDifferences()).forEach(entry -> {
							if (entry.isAddition()) {
								updates.elementInserted(entry.getPosition(), entry.getElement());
								connect(entry.getElement());
							} else {
								disconnect(entry.getElement());
								updates.elementDeleted(entry.getPosition(), entry.getElement());
							}
						});
					} finally {
						updates.commitEvent();
					}
				} finally {
					readWriteLock.readLock().unlock();
				}
			}
		};

		this.elementListener = new IChangeListener() {

			@Override
			public void handleChange(ChangeEvent event) {
				readWriteLock.readLock().lock();
				try {
					updates.beginEvent();
					try {
						@SuppressWarnings("unchecked")
						E element = (E) event.getObservable();
						updates.elementUpdated(indexOf(element), element, element);
					} finally {
						updates.commitEvent();
					}
				} finally {
					readWriteLock.readLock().unlock();
				}
			}
		};
	}

	public synchronized void setDelegate(IObservableList<E> newDelegate) {
		IObservableList<E> oldDelegate = this.delegate;
		this.delegate = newDelegate;

		if (oldDelegate != null) {
			oldDelegate.removeListChangeListener(listener);
		}
		if (newDelegate != null) {
			newDelegate.addListChangeListener(listener);
		}

		readWriteLock.readLock().lock();
		try {
			updates.beginEvent();
			try {
				AtomicInteger i = new AtomicInteger();
				if (oldDelegate != null) {
					// Report updates for elements that have correspondents in the new list
					oldDelegate.subList(0, (newDelegate == null) ? 0 : newDelegate.size()).forEach(e -> {
						E newE = newDelegate.get(i.get());
						disconnect(e);
						updates.elementUpdated(i.getAndIncrement(), e, newE);
						connect(newE);
					});

					// Report deletions for any further elements
					if (oldDelegate.size() < i.get()) {
						oldDelegate.forEach(e -> {
							disconnect(e);
							updates.elementDeleted(i.getAndIncrement(), e);
						});
					}
				}

				if (newDelegate != null) {
					if ((newDelegate.size() > i.get())) {
						// Report insertions for any further new elements
						newDelegate.listIterator(i.get()).forEachRemaining(e -> {
							updates.elementInserted(i.getAndIncrement(), e);
							connect(e);
						});
					}
				}
			} finally {
				updates.commitEvent();
			}
		} finally {
			readWriteLock.readLock().unlock();
		}
	}

	@Override
	public void dispose() {
		if (delegate != null) {
			delegate.removeListChangeListener(listener);
		}
	}

	@Override
	public int size() {
		return (delegate == null) ? 0 : delegate.size();
	}

	@Override
	public E get(int index) {
		checkDelegate();
		return delegate.get(index);
	}

	private void checkDelegate() {
		if (delegate == null) {
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public void add(int index, E value) {
		checkDelegate();
		delegate.add(index, value);
	}

	@Override
	public E set(int index, E value) {
		checkDelegate();
		return delegate.set(index, value);
	}

	@Override
	public E remove(int index) {
		checkDelegate();
		return delegate.remove(index);
	}

	private void connect(E newElement) {
		if (newElement instanceof IObservable) {
			((IObservable) newElement).addChangeListener(elementListener);
		}
	}

	private void disconnect(E newElement) {
		if (newElement instanceof IObservable) {
			((IObservable) newElement).removeChangeListener(elementListener);
		}
	}
}
