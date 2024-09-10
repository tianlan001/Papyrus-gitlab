/*****************************************************************************
 * Copyright (c) 2013, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus (CEA) - bug 433371
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.advice;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.common.core.command.CommandResult;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.emf.commands.core.command.AbstractTransactionalCommand;
import org.eclipse.papyrus.infra.core.sashwindows.di.service.IPageManager;
import org.eclipse.papyrus.infra.core.sashwindows.di.util.DiUtils;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;

/**
 * A Helper to build the command used to remove the page associated to the destroyed element
 *
 * @author Camille Letavernier
 *
 */
public class RemovePageHelper {

	public static ICommand getRemovePageCommand(final TransactionalEditingDomain editingDomain, final EObject elementToDestroy) {
		try {
			final IPageManager pageManager = ServiceUtilsForEObject.getInstance().getService(IPageManager.class, elementToDestroy);
			if (pageManager.allPages().contains(elementToDestroy)) {
				final Command command = DiUtils.getMemoizedCloseAllPagesCommand(editingDomain, pageManager, elementToDestroy);
				if (command != null) {
					return new AbstractTransactionalCommand(editingDomain, "Delete page", null) { //$NON-NLS-1$

						@Override
						public boolean canExecute() {
							return command.canExecute();
						}

						@Override
						protected CommandResult doExecuteWithResult(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
							command.execute();
							return CommandResult.newOKCommandResult();
						}
					};
				}
			}
		} catch (ServiceException ex) {
			// Ignore
		} catch (Exception ex) {
			// Ignore
		}

		return null;
	}
}
