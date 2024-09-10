/*****************************************************************************
 * Copyright (c) 2010, 2013 CEA LIST.
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
 * Christian W. Damus (CEA) - Factor out workspace storage for pluggable storage providers (CDO)
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextEditAction;
import org.eclipse.swt.widgets.Display;

/**
 * An action to edit an existing context.
 * The context must not be read-only, which means it should not have been
 * registered via a plug-in extension
 *
 * @author Camille Letavernier
 */
public class EditContextAction {

	private IContextEditAction delegate;

	public EditContextAction(IContextEditAction delegate) {
		super();

		this.delegate = delegate;
	}

	/**
	 * Opens an Eclipse Editor to edit the given context.
	 *
	 * @param context
	 *            The context to edit
	 * @throws Exception
	 *             If the context cannot be edited
	 */
	public void openEditor(final Context context) {

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
		try {
			dialog.run(false, false, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						delegate.openEditor(context, monitor);
					} catch (CoreException ex) {
						Activator.log.error(ex);
					}
				}

			});
		} catch (Exception ex) {
			Activator.log.error(ex);
		}
	}
}
