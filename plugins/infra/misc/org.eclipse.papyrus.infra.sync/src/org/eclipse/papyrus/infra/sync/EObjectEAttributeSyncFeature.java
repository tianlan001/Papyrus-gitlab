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
 *   Christian W. Damus - bug 465416
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.sync;

import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.SetCommand;

import com.google.common.base.Objects;

/**
 * A synchronization feature for synchronizing {@link EAttribute}s between {@link EObject}s.
 *
 * @author Laurent Wouters
 *
 * @param <M>
 *            The type of the underlying model element common to all synchronized items in a single bucket
 */
public class EObjectEAttributeSyncFeature<M extends EObject> extends SyncFeature<M, EObject, Notification> {

	private final EAttribute attribute;

	/**
	 * The active listeners
	 */
	private EMFDispatchManager<Dispatcher> dispatchMgr = createSingleDispatchManager();

	/**
	 * Initialized this feature
	 *
	 * @param bucket
	 *            The bucket doing the synchronization
	 */
	public EObjectEAttributeSyncFeature(SyncBucket<M, EObject, Notification> bucket, EAttribute attribute) {
		super(bucket);

		this.attribute = attribute;
	}

	public static <M extends EObject> EObjectEAttributeSyncFeature<M> create(SyncBucket<M, EObject, Notification> bucket, EAttribute attribute) {
		return new EObjectEAttributeSyncFeature<M>(bucket, attribute);
	}

	@Override
	public void observe(SyncItem<M, EObject> item) {
		dispatchMgr.add(item, new Dispatcher(item, attribute));
	}

	@Override
	public void unobserve(SyncItem<M, EObject> item) {
		dispatchMgr.remove(item);
	}

	@Override
	protected void onClear() {
		dispatchMgr.removeAll();
	}

	@Override
	public void synchronize(SyncItem<M, EObject> from, SyncItem<M, EObject> to, Notification message) {
		if (message == null) {
			synchronizeInit(from, to);
		} else {
			synchronizeIncrement(from, to, message);
		}
	}

	/**
	 * Completely synchronizes the target item with the origin item
	 *
	 * @param from
	 *            The origin item
	 * @param to
	 *            The target item
	 */
	private void synchronizeInit(SyncItem<M, EObject> from, SyncItem<M, EObject> to) {
		Object valueFrom = from.getBackend().eGet(attribute);
		Object valueTo = to.getBackend().eGet(attribute);

		if (!Objects.equal(valueFrom, valueTo)) {
			Command command = SetCommand.create(getEditingDomain(), to.getBackend(), attribute, valueFrom);
			execute(command);
		}
	}

	/**
	 * Synchronizes the target item with the specified change
	 *
	 * @param to
	 *            The target item
	 * @param message
	 *            The change message
	 */
	private void synchronizeIncrement(SyncItem<M, EObject> from, SyncItem<M, EObject> to, Notification message) {
		Dispatcher dispatcher = dispatchMgr.getDispatcher(from, message.getFeature());

		switch (message.getEventType()) {
		case Notification.ADD:
			dispatcher.handleAdd(from, to, message.getNewValue());
			break;
		case Notification.ADD_MANY:
			for (Object next : (Iterable<?>) message.getNewValue()) {
				dispatcher.handleAdd(from, to, next);
			}
			break;
		case Notification.REMOVE:
			dispatcher.handleRemove(from, to, message.getOldValue());
			break;
		case Notification.REMOVE_MANY:
			for (Object next : (Iterable<?>) message.getOldValue()) {
				dispatcher.handleRemove(from, to, next);
			}
			break;
		case Notification.SET:
			if (message.getOldValue() != null) {
				dispatcher.handleRemove(from, to, message.getOldValue());
			}
			if (message.getNewValue() != null) {
				dispatcher.handleAdd(from, to, message.getNewValue());
			}
			break;
		case Notification.UNSET:
			// A single-valued feature may be unset
			if (message.getOldValue() instanceof EObject) {
				dispatcher.handleRemove(from, to, message.getOldValue());
			}
			break;
		}
	}

	protected Command getAddCommand(SyncItem<M, EObject> from, SyncItem<M, EObject> to, Object value) {
		return attribute.isMany()
				? AddCommand.create(getEditingDomain(), to.getBackend(), attribute, value)
				: SetCommand.create(getEditingDomain(), to.getBackend(), attribute, value);
	}

	protected Command getRemoveCommand(SyncItem<M, EObject> from, SyncItem<M, EObject> to, Object value) {
		return attribute.isMany()
				? RemoveCommand.create(getEditingDomain(), to.getBackend(), attribute, value)
				: SetCommand.create(getEditingDomain(), to.getBackend(), attribute, SetCommand.UNSET_VALUE);
	}

	/**
	 * Gets the model element associated to the specified back-end object, or <code>null</code> if none is found
	 *
	 * @param backend
	 *            the synchronization back-end
	 * @return The associated model element
	 */
	protected EObject getModelOf(EObject backend) {
		EObject notifier = getNotifier(backend);
		return (notifier == null) ? null : getModelOfNotifier(notifier);
	}

	protected EObject getNotifier(EObject backend) {
		return backend;
	}

	protected EObject getModelOfNotifier(EObject backendNotifier) {
		return backendNotifier;
	}

	//
	// Nested types
	//

	/**
	 * Represents a dispatcher for this feature
	 *
	 * @author Laurent Wouters
	 */
	private class Dispatcher extends EStructuralFeatureSyncDispatcher<M, EObject> {
		public Dispatcher(SyncItem<M, EObject> item, EStructuralFeature feature) {
			super(item, feature);
		}

		@Override
		public EObject getNotifier() {
			return EObjectEAttributeSyncFeature.this.getNotifier(getItem().getBackend());
		}

		@Override
		public void onClear() {
			// clears the parent bucket
			getBucket().clear();
		}

		@Override
		public void onChange(Notification notification) {
			EObjectEAttributeSyncFeature.this.onChange(getItem(), notification);
		}

		List<?> getContents(EObject owner) {
			List<?> result;

			if (attribute.isMany()) {
				result = (List<?>) owner.eGet(attribute);
			} else {
				Object value = owner.eGet(attribute);
				if (value == null) {
					result = ECollections.EMPTY_ELIST;
				} else {
					result = ECollections.singletonEList(value);
				}
			}

			return result;
		}

		boolean handleAdd(SyncItem<M, EObject> from, SyncItem<M, EObject> to, Object value) {
			boolean result;

			List<?> values = getContents(to.getBackend());
			result = !values.contains(value);

			if (result) {
				react(getAddCommand(from, to, value));
			}

			return result;
		}

		boolean handleRemove(SyncItem<M, EObject> from, SyncItem<M, EObject> to, Object value) {
			boolean result;

			List<?> values = getContents(to.getBackend());
			result = values.contains(value);

			if (result) {
				react(getRemoveCommand(from, to, value));
			}

			return result;
		}
	}
}
