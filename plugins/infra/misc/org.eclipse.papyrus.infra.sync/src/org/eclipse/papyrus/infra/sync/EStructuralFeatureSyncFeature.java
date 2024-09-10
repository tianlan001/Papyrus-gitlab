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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.AbstractCommand;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.command.IdentityCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;

/**
 * A synchronization feature for {@link EStructuralFeature}s of {@link EObject}s.
 *
 * @author Laurent Wouters
 *
 * @param <M>
 *            The type of the underlying model element common to all synchronized items in a single bucket
 * @param <T>
 *            The type of the backend element to synchronize
 */
public abstract class EStructuralFeatureSyncFeature<M extends EObject, T> extends SyncFeature<M, T, Notification> {

	final EStructuralFeature[] features;

	/**
	 * The active listeners
	 */
	private EMFDispatchManager<Dispatcher> dispatchMgr = createMultipleDispatchManager();

	/**
	 * Initialized this feature
	 *
	 * @param bucket
	 *            The bucket doing the synchronization
	 */
	public EStructuralFeatureSyncFeature(SyncBucket<M, T, Notification> bucket, EStructuralFeature feature, EStructuralFeature... more) {
		super(bucket);

		this.features = ObjectArrays.concat(feature, more);
	}

	/**
	 * Callback for sub-classes of this feature when a new target has been added to a synchronized item
	 *
	 * @param from
	 *            the source time
	 * @param source
	 *            the corresponding source object
	 * @param to
	 *            The target item
	 * @param target
	 *            The added target
	 * 
	 * @return a command to append to the current synchronization operation
	 */
	protected Command onTargetAdded(SyncItem<M, T> from, EObject source, SyncItem<M, T> to, T target) {
		// by default, do nothing
		return null;
	}

	/**
	 * Callback for sub-classes of this feature when a new child has been removed from a target synchronized item
	 *
	 * @param to
	 *            The target item
	 * @param target
	 *            The removed target
	 * 
	 * @return a command to append to the current synchronization operation
	 */
	protected Command onTargetRemoved(SyncItem<M, T> to, T target) {
		// by default, do nothing
		return null;
	}

	/**
	 * Finds and returns the model object in the {@code to} side of a synchronization object that corresponds to
	 * the given source object in the {@code from} side. By default, the {@code sourceModel} is returned as is
	 * for the simple case of synchronizing multiple visualizations on the same model content.
	 * 
	 * @param from
	 *            the source sync-item of a synchronization operation
	 * @param to
	 *            the target sync-item of a synchronization operation
	 * @param sourceModel
	 *            an object added to the {@link SyncItem#getModel() model} of the {@code from} item
	 * @return the corresponding object in the {@code model} of the {@code to} item
	 */
	protected EObject getTargetModel(SyncItem<M, T> from, SyncItem<M, T> to, EObject sourceModel) {
		return sourceModel;
	}

	@Override
	public void observe(SyncItem<M, T> item) {
		for (EStructuralFeature next : features) {
			dispatchMgr.add(item, new Dispatcher(item, next));
		}
	}

	@Override
	public void unobserve(SyncItem<M, T> item) {
		dispatchMgr.remove(item);
	}

	@Override
	protected void onClear() {
		dispatchMgr.removeAll();
	}

	@Override
	public void synchronize(SyncItem<M, T> from, SyncItem<M, T> to, Notification message) {
		if (message == null) {
			synchronizeInit(from, to);
		} else {
			synchronizeIncrement(from, to, message);
		}
	}

	protected abstract Iterable<? extends T> getContents(T backend);

	protected boolean match(EObject sourceModel, EObject targetModel) {
		return sourceModel == targetModel;
	}

	/**
	 * Completely synchronizes the target item with the origin item
	 *
	 * @param from
	 *            The origin item
	 * @param to
	 *            The target item
	 */
	private void synchronizeInit(SyncItem<M, T> from, SyncItem<M, T> to) {
		Iterable<? extends T> childrenFrom = getContents(from.getBackend());
		List<? extends T> childrenTo = Lists.newArrayList(getContents(to.getBackend()));
		List<EObject> toAdd = new ArrayList<EObject>();

		Command additionalCommand = null;

		// find the missing children and the supplementary children in the target (to) item
		for (T source : childrenFrom) {
			EObject model = getModelOf(source);
			boolean found = false;
			for (Iterator<? extends T> iter = childrenTo.iterator(); iter.hasNext();) {
				T target = iter.next();
				EObject potential = getTargetModel(from, to, getModelOf(target));
				if (match(model, potential)) {
					found = true;
					iter.remove();

					// Hook it up for its own synchronization
					Command additional = onTargetAdded(from, model, to, target);
					if (additional != null) {
						additionalCommand = (additionalCommand == null) ? additional : additionalCommand.chain(additional);
					}
					break;
				}
			}
			if (!found) {
				toAdd.add(model);
			}
		}

		Command command = null;

		if (!childrenTo.isEmpty() || !toAdd.isEmpty()) {
			// compute the initial sync command
			CompoundCommand compound = new CompoundCommand("Initial Sync Command");
			command = compound;

			// remove the supplementary children of the target item
			for (int i = 0; i < childrenTo.size(); i++) {
				compound.append(getRemoveCommand(from, null, to, childrenTo.get(i)));
			}
			// add the missing ones
			for (int i = 0; i < toAdd.size(); i++) {
				EObject newSource = toAdd.get(i);
				if (shouldAdd(from, to, newSource)) {
					Command add = getAddCommand(from, to, newSource);
					if (add != null) {
						compound.append(add);
					}
				}
			}
		}

		if (additionalCommand != null) {
			command = (command == null) ? additionalCommand : command.chain(additionalCommand);
		}

		if (command != null) {
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
	private void synchronizeIncrement(SyncItem<M, T> from, SyncItem<M, T> to, Notification message) {
		Dispatcher dispatcher = dispatchMgr.getDispatcher(from, message.getFeature());

		switch (message.getEventType()) {
		case Notification.ADD:
			dispatcher.handleAdd(from, to, (EObject) message.getNewValue());
			break;
		case Notification.ADD_MANY:
			for (Object next : (Iterable<?>) message.getNewValue()) {
				dispatcher.handleAdd(from, to, (EObject) next);
			}
			break;
		case Notification.REMOVE:
			dispatcher.handleRemove(from, to, (EObject) message.getOldValue());
			break;
		case Notification.REMOVE_MANY:
			for (Object next : (Iterable<?>) message.getOldValue()) {
				dispatcher.handleRemove(from, to, (EObject) next);
			}
			break;
		case Notification.SET:
			if (message.getOldValue() != null) {
				dispatcher.handleRemove(from, to, (EObject) message.getOldValue());
			}
			if (message.getNewValue() != null) {
				dispatcher.handleAdd(from, to, (EObject) message.getNewValue());
			}
			break;
		case Notification.UNSET:
			// A single-valued feature may be unset
			if (message.getOldValue() instanceof EObject) {
				dispatcher.handleRemove(from, to, (EObject) message.getOldValue());
			}
			break;
		}
	}

	/**
	 * Queries whether a new source object should be added {@code to} the synchronization target in synchronization
	 * {@code from} a synchronization source. The default implementation always adds.
	 * 
	 * @param from
	 *            the source of a synchronization operation
	 * @param to
	 *            the target of a synchronization operation
	 * @param newSource
	 *            the new element added to the source
	 * 
	 * @return whether the new element should be synchronized to the target
	 */
	protected boolean shouldAdd(SyncItem<M, T> from, SyncItem<M, T> to, EObject newSource) {
		return true;
	}

	/**
	 * Gets the synchronization command for adding a new back-end to the specified target to correspond to a new source model element.
	 *
	 * @param from
	 *            The source item of the synchronization
	 * @param to
	 *            The target item to synchronize
	 * @param newSource
	 *            The new source object for which a corresponding target is to be added
	 * @return The command
	 */
	protected Command getAddCommand(final SyncItem<M, T> from, final SyncItem<M, T> to, final EObject newSource) {
		return new CommandWrapper(doGetAddCommand(from, to, newSource)) {
			private T addedTarget;

			@Override
			public void execute() {
				super.execute();

				@SuppressWarnings("unchecked")
				T newTarget = (T) getCommand().getResult().iterator().next();
				addedTarget = newTarget;

				Command additional = onTargetAdded(from, newSource, to, addedTarget);
				if (additional != null) {
					additional.execute();
				}
			}

			@Override
			public void undo() {
				super.undo();
				Command additional = onTargetRemoved(to, addedTarget);
				if (additional != null) {
					additional.execute();
				}
			}

			@Override
			public void redo() {
				super.redo();
				Command additional = onTargetAdded(from, newSource, to, addedTarget);
				if (additional != null) {
					additional.execute();
				}
			}
		};
	}

	protected Command doGetAddCommand(SyncItem<M, T> from, SyncItem<M, T> to, EObject newSource) {
		throw new UnsupportedOperationException("doGetAddCommand"); //$NON-NLS-1$
	}

	/**
	 * Gets the synchronization command for removing a back-end from the specified target on removal of its corresponding source model.
	 *
	 * @param from
	 *            The source item of the synchronization
	 * @param oldSource
	 *            the source object to which the {@code oldTarget} had corresponded. May be {@code null} in the case of an
	 *            initial synchronization where we have found targets that have no corresponding sources and that thus need to be removed
	 * @param to
	 *            The target item to synchronize
	 * @param oldTarget
	 *            The old back-end object that is to be removed
	 * @return The command
	 */
	protected Command getRemoveCommand(final SyncItem<M, T> from, final EObject oldSource, final SyncItem<M, T> to, final T oldTarget) {
		return new CommandWrapper(doGetRemoveCommand(from, oldSource, to, oldTarget)) {
			@Override
			public void execute() {
				super.execute();
				Command additional = onTargetRemoved(to, oldTarget);
				if (additional != null) {
					additional.execute();
				}
			}

			@Override
			public void undo() {
				super.undo();

				// Only notify of add if we had done that in the first place (this is not an initial sync that is being undone)
				if (oldSource != null) {
					Command additional = onTargetAdded(from, oldSource, to, oldTarget);
					if (additional != null) {
						additional.execute();
					}
				}
			}

			@Override
			public void redo() {
				super.redo();
				Command additional = onTargetRemoved(to, oldTarget);
				if (additional != null) {
					additional.execute();
				}
			}
		};
	}

	protected Command doGetRemoveCommand(SyncItem<M, T> from, EObject oldSource, SyncItem<M, T> to, T oldTarget) {
		throw new UnsupportedOperationException("doGetRemoveCommand"); //$NON-NLS-1$
	}

	/**
	 * Gets the model element associated to the specified back-end object, or <code>null</code> if none is found
	 *
	 * @param backend
	 *            the synchronization back-end
	 * @return The associated model element
	 */
	protected EObject getModelOf(T backend) {
		EObject notifier = getNotifier(backend);
		return (notifier == null) ? null : getModelOfNotifier(notifier);
	}

	protected abstract EObject getNotifier(T backend);

	protected abstract EObject getModelOfNotifier(EObject backendNotifier);

	/**
	 * Obtains a command that wraps a given {@code command} in a command registers a back-end element for synchronization
	 * on execute and redo and deregisters it on undo.
	 * 
	 * @param registry
	 *            the synchronization registry in which to register the back-end for synchronization
	 * @param backend
	 *            the element to synchronize
	 * @param command
	 *            the command to wrap. May be {@code null}, in which case we only perform the registration changes
	 * 
	 * @return the wrapper command (never {@code null})
	 */
	protected Command synchronizingWrapper(final SyncRegistry<?, ? super T, Notification> registry, final T backend, Command command) {
		class Wrapper extends CommandWrapper {
			Wrapper() {
				super();
			}

			Wrapper(Command command) {
				super(command);
			}

			@Override
			public void execute() {
				super.execute();

				registry.synchronize(backend);
			}

			@Override
			public void undo() {
				registry.desynchronize(backend);

				super.undo();
			}

			@Override
			public void redo() {
				super.redo();

				registry.synchronize(backend);
			}
		}

		class CleanWrapper extends Wrapper implements AbstractCommand.NonDirtying {

			CleanWrapper() {
				super();
			}

			@Override
			protected Command createCommand() {
				// When we don't have a command to wrap
				return IdentityCommand.INSTANCE;
			}

		}

		return (command == null) ? new CleanWrapper() : new Wrapper(command);
	}

	//
	// Nested types
	//

	/**
	 * Represents a dispatcher for this feature
	 *
	 * @author Laurent Wouters
	 */
	private class Dispatcher extends EStructuralFeatureSyncDispatcher<M, T> {
		public Dispatcher(SyncItem<M, T> item, EStructuralFeature feature) {
			super(item, feature);
		}

		@Override
		public EObject getNotifier() {
			return EStructuralFeatureSyncFeature.this.getNotifier(getItem().getBackend());
		}

		@Override
		public void onClear() {
			// clears the parent bucket
			getBucket().clear();
		}

		@Override
		public void onChange(Notification notification) {
			EStructuralFeatureSyncFeature.this.onChange(getItem(), notification);
		}

		EObject getModelOf(EObject notifier) {
			return EStructuralFeatureSyncFeature.this.getModelOfNotifier(notifier);
		}

		boolean handleAdd(SyncItem<M, T> from, SyncItem<M, T> to, EObject object) {
			boolean result = true; // In case the target has no children at all, yet

			EObject model = getModelOf(object);
			Iterable<? extends T> children = getContents(to.getBackend());
			for (Iterator<? extends T> iter = children.iterator(); !result && iter.hasNext();) {
				T potential = iter.next();
				result = !match(model, EStructuralFeatureSyncFeature.this.getModelOf(potential));
			}

			if (result && shouldAdd(from, to, model)) {
				Command add = getAddCommand(from, to, model);
				if (add != null) {
					react(add);
				}
			}

			return result;
		}

		boolean handleRemove(SyncItem<M, T> from, SyncItem<M, T> to, EObject object) {
			boolean result = false;

			EObject model = getModelOf(object);
			Iterable<? extends T> children = getContents(to.getBackend());
			for (Iterator<? extends T> iter = children.iterator(); !result && iter.hasNext();) {
				T potential = iter.next();
				result = match(model, EStructuralFeatureSyncFeature.this.getModelOf(potential));
				if (result) {
					react(getRemoveCommand(from, model, to, potential));
				}
			}

			return result;
		}
	}

}
