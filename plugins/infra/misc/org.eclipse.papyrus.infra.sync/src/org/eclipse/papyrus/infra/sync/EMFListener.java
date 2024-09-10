/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - bug 465416
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandWrapper;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.papyrus.infra.sync.internal.SyncService;
import org.eclipse.papyrus.infra.sync.internal.SyncServiceOperation;
import org.eclipse.papyrus.infra.sync.service.ISyncService;
import org.eclipse.papyrus.infra.sync.service.SyncServiceRunnable;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Represents a listener of the EMF command stack for the purpose of the synchronization API
 *
 * @author Laurent Wouters
 */
public class EMFListener implements ResourceSetListener {
	private final ISyncService syncService = SyncService.getCurrent();
	private TransactionalEditingDomain domain;

	/**
	 * The dispatchers by notifier and object and then feature
	 */
	private Map<EObject, Map<EStructuralFeature, List<EMFDispatch>>> dispatchers;

	/**
	 * Initializes this listener
	 *
	 * @param domain
	 *            The associated editing domain
	 */
	public EMFListener(TransactionalEditingDomain domain) {
		super();

		// Synchronize objects in the order in which they are registered
		this.dispatchers = Maps.newLinkedHashMap();

		this.domain = domain;
		domain.addResourceSetListener(this);
	}

	/**
	 * Disposes me, forgetting all of my registered dispatchers and disconnecting me from the editing domain that I listen to.
	 */
	public void dispose() {
		dispatchers.clear();

		if (domain != null) {
			domain.removeResourceSetListener(this);
			domain = null;
		}
	}

	/**
	 * Registers the specified dispatch option
	 *
	 * @param dispatcher
	 *            A dispatch option
	 */
	public void add(EMFDispatch dispatcher) {
		EObject notifier = dispatcher.getNotifier();
		EStructuralFeature feature = dispatcher.getFeature();
		Map<EStructuralFeature, List<EMFDispatch>> sub1 = dispatchers.get(notifier);
		if (sub1 == null) {
			// Synchronize features in the order in which they are registered
			sub1 = Maps.newLinkedHashMap();
			dispatchers.put(notifier, sub1);
		}
		List<EMFDispatch> sub2 = sub1.get(dispatcher.getFeature());
		if (sub2 == null) {
			sub2 = new ArrayList<EMFDispatch>();
			sub1.put(feature, sub2);
		}
		sub2.add(dispatcher);
	}

	/**
	 * Removes the specified dispatch option
	 *
	 * @param dispatcher
	 *            A dispatch option
	 */
	public void remove(EMFDispatch dispatcher) {
		Map<EStructuralFeature, List<EMFDispatch>> sub1 = dispatchers.get(dispatcher.getNotifier());
		if (sub1 == null) {
			return;
		}
		List<EMFDispatch> sub2 = sub1.get(dispatcher.getFeature());
		if (sub2 == null) {
			return;
		}
		sub2.remove(dispatcher);
	}

	@Override
	public NotificationFilter getFilter() {
		return NotificationFilter.NOT_TOUCH;
	}

	@Override
	public boolean isAggregatePrecommitListener() {
		return false;
	}

	@Override
	public boolean isPrecommitOnly() {
		return true;
	}

	@Override
	public boolean isPostcommitOnly() {
		return false;
	}

	@Override
	public void resourceSetChanged(ResourceSetChangeEvent arg0) {
		// this is post-commit, do nothing
	}

	@Override
	public Command transactionAboutToCommit(final ResourceSetChangeEvent event) throws RollbackException {
		// this is pre-commit, dispatch and aggregate
		return syncService.run(new SyncServiceRunnable.Safe<Command>() {
			/**
			 * A cache of matching dispatchers for this dispatch operation.
			 */
			private List<EMFDispatch> cache = Lists.newArrayList();

			@Override
			public Command run(ISyncService syncService) {
				Command result = null;
				CompoundCommand compound = null;

				for (Notification notification : event.getNotifications()) {
					Object objNotifier = notification.getNotifier();
					Object objFeature = notification.getFeature();
					if (!(objNotifier instanceof EObject)) {
						continue;
					}
					if (!(objFeature instanceof EStructuralFeature)) {
						continue;
					}
					EObject notifier = (EObject) objNotifier;
					EStructuralFeature feature = (EStructuralFeature) objFeature;

					addToCache(null, null);
					addToCache(notifier, null);
					addToCache(null, feature);
					addToCache(notifier, feature);
					if (cache.isEmpty()) {
						continue;
					}
					// instantiate if required
					if (result == null) {
						compound = new CompoundCommand();
						result = new SyncServiceCommandWrapper(syncService, compound);
					}
					// dispatch the notification
					for (EMFDispatch option : cache) {
						option.dispatch(notification, compound);
					}
					// clear the cache
					cache.clear();
				}
				return result;
			}

			/**
			 * Adds to the cache the dispatching options that matches the specified values
			 *
			 * @param notifier
			 *            The notifier object
			 * @param feature
			 *            The changed feature
			 */
			private void addToCache(EObject notifier, EStructuralFeature feature) {
				Map<EStructuralFeature, List<EMFDispatch>> sub1 = dispatchers.get(notifier);
				if (sub1 == null) {
					return;
				}
				List<EMFDispatch> sub2 = sub1.get(feature);
				if (sub2 == null) {
					return;
				}
				for (EMFDispatch dispatch : sub2) {
					if (!cache.contains(dispatch)) {
						cache.add(dispatch);
					}
				}
			}
		});
	}

	private static class SyncServiceCommandWrapper extends CommandWrapper {
		private final SyncService syncService;

		SyncServiceCommandWrapper(ISyncService syncService, Command command) {
			super(command);

			this.syncService = (SyncService) syncService;
		}

		@Override
		public void execute() {
			new SyncServiceOperation<Void>(syncService) {
				@Override
				protected Void doCall() throws Exception {
					SyncServiceCommandWrapper.super.execute();
					return null;
				}
			}.safeCall();
		}

		@Override
		public void undo() {
			new SyncServiceOperation<Void>(syncService) {
				@Override
				protected Void doCall() throws Exception {
					SyncServiceCommandWrapper.super.undo();
					return null;
				}
			}.safeCall();
		}

		@Override
		public void redo() {
			new SyncServiceOperation<Void>(syncService) {
				@Override
				protected Void doCall() throws Exception {
					SyncServiceCommandWrapper.super.redo();
					return null;
				}
			}.safeCall();
		}
	}
}
