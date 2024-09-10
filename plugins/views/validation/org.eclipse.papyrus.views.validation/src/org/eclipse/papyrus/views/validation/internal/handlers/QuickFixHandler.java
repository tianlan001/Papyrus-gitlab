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

package org.eclipse.papyrus.views.validation.internal.handlers;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.core.utils.AdapterUtils;
import org.eclipse.papyrus.views.validation.internal.Activator;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IMarkerResolution;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.dialogs.MarkerResolutionSelectionDialog;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

/**
 * Quick Fix command handler for the Papyrus Model Validation View.
 */
public class QuickFixHandler extends AbstractHandler {

	public QuickFixHandler() {
		super();
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection sel = (IStructuredSelection) selection;
			if (!sel.isEmpty()) {
				IMarker marker = AdapterUtils.adapt(sel.getFirstElement(), IMarker.class, null);
				if (marker != null) {
					if (IDE.getMarkerHelpRegistry().hasResolutions(marker)) {
						Shell shell = HandlerUtil.getActiveShellChecked(event);

						IMarkerResolution toRun = null;
						IMarkerResolution[] resolutions = IDE.getMarkerHelpRegistry().getResolutions(marker);
						switch (resolutions.length) {
						case 0:
							MessageDialog.openInformation(shell, "Quick Fix", "No quick fixes are available for the selected problem.");
							break;
						case 1:
							toRun = resolutions[0];
							break;
						default:
							MarkerResolutionSelectionDialog dlg = new MarkerResolutionSelectionDialog(shell, resolutions);
							if (dlg.open() == Window.OK) {
								Object[] result = dlg.getResult();
								toRun = ((result != null) && (result.length > 0)) ? (IMarkerResolution) result[0] : null;
							}
							break;
						}

						if (toRun != null) {
							runQuickFix(toRun, marker, HandlerUtil.getActiveWorkbenchWindowChecked(event));
						}
					}
				}
			}
		}

		return null;
	}

	void runQuickFix(final IMarkerResolution fix, final IMarker marker, IWorkbenchWindow window) {
		try {
			window.run(false, false, new IRunnableWithProgress() {

				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					fix.run(marker);

				}
			});
		} catch (InvocationTargetException e) {
			Activator.log.error(e);
		} catch (InterruptedException e) {
			Activator.log.error(e);
		}
	}
}
