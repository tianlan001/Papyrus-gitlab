/*****************************************************************************
 * Copyright (c) 2011, 2013 CEA LIST.
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
 *  Christian W. Damus (CEA) - Factor out workspace storage for pluggable storage providers (CDO)
 *****************************************************************************/
package org.eclipse.papyrus.views.properties.toolsmiths.ui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.papyrus.infra.properties.contexts.Context;
import org.eclipse.papyrus.infra.properties.internal.ui.runtime.IInternalConfigurationManager;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.views.properties.toolsmiths.Activator;
import org.eclipse.papyrus.views.properties.toolsmiths.storage.actions.IContextDeleteAction;
import org.eclipse.swt.widgets.Display;

/**
 * An action to delete an existing context. This action cannot be undone.
 * If you simply want to disable an existing context, see {@link ConfigurationManager#disableContext(Context)}
 *
 * @author Camille Letavernier
 */
public class RemoveContextAction {

	private IContextDeleteAction delegate;

	public RemoveContextAction(IContextDeleteAction delegate) {
		super();

		this.delegate = delegate;
	}

	/**
	 * Deletes the given context.
	 *
	 * @param sourceContext
	 *            The context to delete
	 */
	public void removeContext(final Context sourceContext) {
		// TODO : Close editors for the context being deleted
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(Display.getCurrent().getActiveShell());
		try {
			dialog.run(false, false, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						IInternalConfigurationManager mgr = (IInternalConfigurationManager) PropertiesRuntime.getConfigurationManager();
						delegate.delete(sourceContext, monitor);
						mgr.deleteContext(sourceContext);
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
