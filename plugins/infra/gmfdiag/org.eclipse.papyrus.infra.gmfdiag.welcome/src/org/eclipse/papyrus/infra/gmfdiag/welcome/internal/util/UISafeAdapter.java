/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.gmfdiag.welcome.internal.util;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.transaction.RunnableWithResult;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.swt.widgets.Display;;

/**
 * An adapter that ensures reaction to a notification is performed on the UI thread.
 * To avoid unnecessary synchronization with the UI thread, this adapter never
 * reacts to {@linkplain Notification#isTouch() touch} notifications.
 */
public class UISafeAdapter extends AdapterImpl {

	@Override
	public void notifyChanged(Notification msg) {
		if (!msg.isTouch()) {
			// I have to run on the UI thread in order to operate on observables
			if (Display.getCurrent() != null) {
				doNotifyChanged(msg);
			} else {
				execOnDisplay(Display.getDefault(),
						msg.getNotifier(),
						() -> doNotifyChanged(msg));
			}
		}
	}

	/**
	 * Overridden by subclasses to react to a notification.
	 *
	 * @param msg
	 *            a notification
	 */
	protected void doNotifyChanged(Notification msg) {
		// Pass
	}

	private void execOnDisplay(Display display, Object notifier, Runnable action) {
		// If we're in an editing domain and there is an active transaction,
		// then we must be careful about how we synchronize with another thread,
		// in case someone in the call chain attempts further model access
		Transaction activeTransaction = null;
		TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(notifier);
		if (domain instanceof InternalTransactionalEditingDomain) {
			activeTransaction = ((InternalTransactionalEditingDomain) domain).getActiveTransaction();
		}

		if (activeTransaction != null) {
			try {
				// Replace the action with a privileged wrapper
				Runnable _action = action;
				action = TransactionUtil.createPrivilegedRunnable(domain, new RunnableWithResult.Impl<Void>() {
					@Override
					public void run() {
						_action.run();
					}
				});
			} catch (InterruptedException e) {
				// This can't actually happen (appears to be an error in the
				// specification of the API) so just leave the action as is
			}
		}

		// Go execute on the display thread
		display.asyncExec(action);
	}
}
