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

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Represents a dispatch option for an event of interest going on on the EMF command stack
 *
 * @author Laurent Wouters
 */
public abstract class EMFDispatch {
	/**
	 * The current command aggregator
	 */
	private CompoundCommand aggregator;

	/**
	 * Gets the notifier object of interest, or <code>null</code> if all notifiers are of interest
	 *
	 * @return The notifier object of interest
	 */
	public abstract EObject getNotifier();

	/**
	 * Gets the changed feature of interest, or <code>null</code> if all features are of interest
	 *
	 * @return The changed feature of interest
	 */
	public abstract EStructuralFeature getFeature();

	/**
	 * Reacts to the specified change notification
	 *
	 * @param notification
	 *            The change notification
	 */
	public abstract void onChange(Notification notification);

	/**
	 * Reacts to the clearing of the listener
	 */
	public abstract void onClear();

	/**
	 * Dispatches the specified change notification
	 *
	 * @param notification
	 *            The change notification
	 * @param aggregator
	 *            The aggregated command being constructed
	 *
	 */
	public final void dispatch(Notification notification, CompoundCommand aggregator) {
		this.aggregator = aggregator;

		try {
			onChange(notification);
		} finally {
			this.aggregator = null;
		}
	}

	/**
	 * Reacts to a change with the specified command
	 *
	 * @param command
	 *            A command
	 */
	public final void react(Command command) {
		aggregator.append(command);
	}
}
