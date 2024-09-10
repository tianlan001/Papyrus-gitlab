/*****************************************************************************
 * Copyright (c) 2014, 2020 CEA LIST and others.
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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug #471903
 *   Vincent LORENZO (CEA LIST) vincent.lorenzo@cea.fr - bug 564127, 564248
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListener;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.papyrus.infra.core.sashwindows.di.DiPackage;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.manager.axis.CompositeAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.manager.axis.IAxisManagerForEventList;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.NattablePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.ITreeItemAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.NattableaxisPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;


/**
 * @author Vincent Lorenzo
 *
 *         This listener is used to notify {@link CompositeAxisManagerForEventList} when an event comes
 *
 */
public class UpdateTableContentListener implements ResourceSetListener {

	/**
	 * the current table for which we want notify its axis manager
	 */
	protected NattableModelManager tableManager;

	/**
	 * the axis manager to notify
	 */
	protected IAxisManagerForEventList axisManager;

	/**
	 * Constructor.
	 *
	 */
	public UpdateTableContentListener(final NattableModelManager tableManager, final IAxisManagerForEventList axisManager) {
		this.tableManager = tableManager;
		this.axisManager = axisManager;
	}


	/**
	 * @see org.eclipse.emf.transaction.ResourceSetListener#getFilter()
	 *
	 * @return
	 */
	@Override
	public NotificationFilter getFilter() {
		return new NotificationFilter.Custom() {

			@Override
			public boolean matches(Notification notification) {
				if (notification.isTouch() || notification.getNotifier() == null || notification.getFeature() == null) {
					return false;
				}
				Object notifier = notification.getNotifier();
				if (notifier instanceof EObject) {
					// to avoid to refresh table when a page (diagram/table/... is created, opened, closed)
					if (DiPackage.eINSTANCE.getEClassifiers().contains(((EObject) notifier).eClass())) {
						// bug 564127
						return false;
					}
					if (EcoreUtil.getRootContainer(((EObject) notifier).eClass()) == NattablePackage.eINSTANCE) {
						// we must verify than the notification concern the current managed table
						try {
							if (TableHelper.findTable((EObject) notifier) != tableManager.getTable()) {
								return false;
							}
						} catch (Exception e) {
							Activator.log.error(e);
						}
					}
				}
				final Object feature = notification.getFeature();
				if (feature == NattableaxisconfigurationPackage.eINSTANCE.getAbstractHeaderAxisConfiguration_DisplayFilter()) {
					// notification already managed by org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager.resourceSetListener
					return false;
				}
				return true;
			}
		};
	}


	/**
	 * @see org.eclipse.emf.transaction.ResourceSetListener#transactionAboutToCommit(org.eclipse.emf.transaction.ResourceSetChangeEvent)
	 *
	 * @param event
	 * @return
	 * @throws RollbackException
	 */
	@Override
	public final Command transactionAboutToCommit(ResourceSetChangeEvent event) throws RollbackException {
		// nothing to do
		return null;
	}


	/**
	 * @return <code>true</code> if there are changes on TreeFillingConfiguration
	 */
	protected boolean containsTreeFillingConfigurationChange(ResourceSetChangeEvent event) {
		List<Notification> notifications = event.getNotifications();
		for (Notification current : notifications) {
			Object feature = current.getFeature();
			if (feature == NattableaxisconfigurationPackage.eINSTANCE.getAbstractHeaderAxisConfiguration_OwnedAxisConfigurations()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @see org.eclipse.emf.transaction.ResourceSetListener#resourceSetChanged(org.eclipse.emf.transaction.ResourceSetChangeEvent)
	 *
	 * @param event
	 */
	@Override
	public void resourceSetChanged(final ResourceSetChangeEvent event) {
		if (containsTreeFillingConfigurationChange(event)) {
			this.axisManager.fillingConfigurationsHaveChanged();
			return;
		}
		// The initial notifications
		final List<Notification> initialNotifications = event.getNotifications();
		// Create a copy of notifications to keep only the managed one
		final List<Notification> managedNotifications = new ArrayList<>(initialNotifications);
		final Iterator<Notification> notificationsIterator = initialNotifications.iterator();
		int index = 0;

		// Loop on initial notifications
		while (notificationsIterator.hasNext()) {
			Notification current = notificationsIterator.next();

			if (managedNotifications.contains(current)) {
				int currentEvent = current.getEventType();

				// filtering notification concerning creation of ITreeItemAxis created as children
				if (Notification.ADD == currentEvent) {
					current = getAddNotification(current, managedNotifications, index);
					// check if the remove event is a move action
				} else if (Notification.REMOVE_MANY == currentEvent || Notification.REMOVE == currentEvent && notificationsIterator.hasNext()) {
					current = getMoveNotification(current, managedNotifications, index);
				}

				if (null != this.axisManager && null != current) {
					this.axisManager.manageEvent(current);
				}
			}

			index++;
		}
		tableManager.refreshNatTable();
	}

	/**
	 * This allow to get the notification from the initial add notification.
	 *
	 * @param initialNotification
	 *            The initial add notification.
	 * @param managedNotifications
	 *            The already managed notifications.
	 * @param index
	 *            The index of the notification in the list of initial notifications.
	 * @return The notification to manage.
	 */
	protected Notification getAddNotification(final Notification initialNotification, final List<Notification> managedNotifications, final int index) {
		Notification currentNotification = initialNotification;

		if (NattableaxisPackage.eINSTANCE.getITreeItemAxis_Children() == currentNotification.getFeature()) {
			if (currentNotification.getNotifier() instanceof ITreeItemAxis && (null == ((EObject) currentNotification.getNotifier()).eContainer() || (((EObject) currentNotification.getNotifier()).eContainer()) instanceof AbstractAxisProvider)) {
				currentNotification = null;
			}
		}

		return currentNotification;
	}

	/**
	 * This allow to get the move notification from the initial remove notification. This one will be managed as a move (because the move does not exist in GMD command (replaced by add and remove)).
	 *
	 * @param initialNotification
	 *            The initial remove notification.
	 * @param managedNotifications
	 *            The already managed notifications.
	 * @param index
	 *            The index of the notification in the list of initial notifications.
	 * @return The notification to manage.
	 */
	@SuppressWarnings("unchecked")
	protected Notification getMoveNotification(final Notification initialNotification, final List<Notification> managedNotifications, final int index) {
		Notification currentNotification = initialNotification;

		// Create a list of remaining notifications to check if a
		final List<Notification> remainingNotifications = managedNotifications.subList(index + 1, managedNotifications.size());
		Iterator<Notification> remainingIterator = remainingNotifications.iterator();

		// we need to verify that a remove_many is not followed by an add_many. in this case it is probably a move inside a list
		boolean isAMove = false;

		while (!isAMove && remainingIterator.hasNext()) {
			Notification nextNotification = remainingIterator.next();
			int nextEventType = nextNotification.getEventType();
			// Check that the next notification manage a move action with the current one
			if (Notification.ADD_MANY == nextEventType && currentNotification.getNotifier().equals(nextNotification.getNotifier()) && currentNotification.getFeature().equals(nextNotification.getFeature())) {
				// Get the old value(s)
				Collection<Object> oldValue = null;
				if (Notification.REMOVE == currentNotification.getEventType()) {
					oldValue = new ArrayList<>(1);
					oldValue.add(currentNotification.getOldValue());
				} else {
					oldValue = (Collection<Object>) currentNotification.getOldValue();
				}
				// Get the new values
				Collection<?> newValue = (Collection<?>) nextNotification.getNewValue();

				// Check that all the new values contains the old one (else it's not a move, don't continue
				isAMove = newValue.containsAll(oldValue);
				if (isAMove) {
					if (oldValue.size() == newValue.size()) {
						// The old and new values are just reorganized, the notification no need to be executed (only refresh on the nattable is necessary)
						currentNotification = null;
					} else {
						// The move is done from a parent to another one, recreate a added notification with only the new added objects at the good position
						int position = 0;

						// Get the position to add it
						Iterator<?> newValueIterator = newValue.iterator();
						while (newValueIterator.hasNext() && oldValue.contains(newValueIterator.next())) {
							position++;
						}

						// Get the objects to add
						final List<Object> addedObject = new ArrayList<>(newValue);
						addedObject.removeAll(oldValue);

						// Create the add notifications
						currentNotification = new ENotificationImpl((InternalEObject) currentNotification.getNotifier(), Notification.ADD_MANY, (EStructuralFeature) currentNotification.getFeature(), null, addedObject, position);
					}

					// The move manage the delete and the add notification so skip the next one
					managedNotifications.remove(nextNotification);
				}
			}
		}

		return currentNotification;
	}

	/**
	 * @see org.eclipse.emf.transaction.ResourceSetListener#isAggregatePrecommitListener()
	 *
	 * @return
	 */
	@Override
	public final boolean isAggregatePrecommitListener() {
		// nothing to do
		return false;
	}

	/**
	 * @see org.eclipse.emf.transaction.ResourceSetListener#isPrecommitOnly()
	 *
	 * @return
	 */
	@Override
	public final boolean isPrecommitOnly() {
		// nothing to do
		return false;
	}

	/**
	 * @see org.eclipse.emf.transaction.ResourceSetListener#isPostcommitOnly()
	 *
	 * @return
	 */
	@Override
	public final boolean isPostcommitOnly() {
		// nothing to do
		return false;
	}
}
