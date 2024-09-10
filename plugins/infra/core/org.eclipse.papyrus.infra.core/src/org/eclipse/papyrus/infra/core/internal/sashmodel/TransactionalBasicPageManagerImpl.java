/*****************************************************************************
 * Copyright (c) 2014, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.internal.sashmodel;

import java.util.Collections;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RollbackException;
import org.eclipse.emf.transaction.Transaction;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.papyrus.infra.core.sashwindows.di.SashWindowsMngr;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.BasicPageManagerImpl;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * Transactional implementation of the basic (headless) page manager.
 *
 * It runs all commands in write transactions on the editing domain, without using the CommandStack.
 *
 * @author Camille Letavernier
 *
 */
public class TransactionalBasicPageManagerImpl extends BasicPageManagerImpl {

	private EditingDomain editingDomain;

	public TransactionalBasicPageManagerImpl(SashWindowsMngr diSashModel, EditingDomain editingDomain) {
		super(diSashModel);

		this.editingDomain = editingDomain;
	}

	@Override
	public void closePage(final Object pageIdentifier) {
		run(editingDomain, () -> super.closePage(pageIdentifier));
	}

	@Override
	public void closeAllOpenedPages() {
		run(editingDomain, () -> super.closeAllOpenedPages());
	}

	@Override
	public void closeOtherPages(final Object pageIdentifier) {
		run(editingDomain, () -> super.closeOtherPages(pageIdentifier));
	}

	@Override
	public void openPage(final Object pageIdentifier) {
		run(editingDomain, () -> super.openPage(pageIdentifier));
	}

	@Override
	public void openPage(final Object pageIdentifier, final String editorID) {
		run(editingDomain, () -> super.openPage(pageIdentifier, editorID));
	}

	@Override
	public void closeAllOpenedPages(final Object pageIdentifier) {
		run(editingDomain, () -> super.closeAllOpenedPages(pageIdentifier));
	}

	protected static void run(EditingDomain domain, Runnable writeOperation) {
		if (domain instanceof InternalTransactionalEditingDomain) {
			try {
				run((InternalTransactionalEditingDomain) domain, writeOperation);
			} catch (Exception e) {
				Bundle self = FrameworkUtil.getBundle(TransactionalBasicPageManagerImpl.class);
				IStatus status = new Status(IStatus.ERROR, self.getSymbolicName(), "Sash model transaction failed.", e); //$NON-NLS-1$
				Platform.getLog(self).log(status);
			}
		} else {
			// No need for any transaction
			writeOperation.run();
		}
	}

	private static void run(InternalTransactionalEditingDomain domain, Runnable writeOperation) throws InterruptedException, RollbackException {
		Transaction transaction = domain.startTransaction(false, Collections.emptyMap());
		try {
			writeOperation.run();
		} finally {
			transaction.commit();
		}
	}

}
