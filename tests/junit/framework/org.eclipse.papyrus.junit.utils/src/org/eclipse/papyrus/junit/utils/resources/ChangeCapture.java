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

package org.eclipse.papyrus.junit.utils.resources;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.emf.transaction.NotificationFilter;
import org.eclipse.emf.transaction.ResourceSetChangeEvent;
import org.eclipse.emf.transaction.ResourceSetListenerImpl;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

/**
 * A listener that captures the {@link ChangeDescription}s of EMF {@link Transaction}s.
 * it is {@link AutoCloseable} for convenience of ensuring that it is removed from the editing domain
 * when no longer needed.
 * 
 * @see #getChangeDescription()
 */
public class ChangeCapture extends ResourceSetListenerImpl implements AutoCloseable {

	private TransactionalEditingDomain domain;

	private ChangeDescription changeDescription;

	/**
	 * Initializes me with my editing {@code domain}, to which I immediately begin listening for transactions
	 * (there is no need to add me as a listener explicitly). I only capture the changes of a transaction
	 * that actually makes non-read-only-compatible changes.
	 *
	 * @param domain
	 *            my editing domain
	 */
	public ChangeCapture(TransactionalEditingDomain domain) {
		super(NotificationFilter.NOT_TOUCH);

		this.domain = domain;
		domain.addResourceSetListener(this);
	}

	@Override
	public boolean isPostcommitOnly() {
		return true;
	}

	@Override
	public void resourceSetChanged(ResourceSetChangeEvent event) {
		// Ignore unbatched (non-transactional) changes
		if (event.getTransaction() != null) {
			this.changeDescription = event.getTransaction().getChangeDescription();
		}
	}

	/**
	 * Obtains the change description of the last committed transaction, if any.
	 * 
	 * @return the last transaction's changes, or {@code null} if none
	 */
	public ChangeDescription getChangeDescription() {
		return changeDescription;
	}

	/**
	 * Detaches me from my editing domain.
	 */
	@Override
	public void close() {
		if (domain != null) {
			domain.removeResourceSetListener(this);
			domain = null;
		}
	}

}
