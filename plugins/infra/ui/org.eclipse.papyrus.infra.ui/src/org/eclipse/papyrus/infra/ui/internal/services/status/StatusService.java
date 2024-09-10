/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.internal.services.status;

import org.eclipse.papyrus.infra.ui.Activator;
import org.eclipse.papyrus.infra.ui.api.services.IStatusService;
import org.eclipse.papyrus.infra.ui.api.services.StatusServiceEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class StatusService implements IStatusService {

	/**
	 * This value has been copied from org.eclipse.ui.internal.WorkbenchPlugin
	 */
	private static final String DATA_SPLASH_SHELL = "org.eclipse.ui.workbench.splashShell"; //$NON-NLS-1$

	/**
	 * The dialog opened to display the progression
	 */
	private ProgressDialog dialog;

	/**
	 *
	 * @see org.eclipse.papyrus.infra.ui.api.services.IStatusService#trigger(org.eclipse.papyrus.infra.ui.api.services.StatusServiceEvent)
	 *
	 * @param event
	 */
	@Override
	public void trigger(StatusServiceEvent event) {
		if (event instanceof BeginStatusEvent) {
			try {
				// check if the splash screen is display (if true don't display the dialog)
				Shell splashShell = (Shell) Display.getCurrent().getData(DATA_SPLASH_SHELL);

				if (splashShell == null || splashShell.isDisposed()) {
					dialog = new ProgressDialog(Display.getDefault().getActiveShell(), ((BeginStatusEvent) event).getExecutionTitle(), ((BeginStatusEvent) event).getMessage());
					dialog.open();
					dialog.work(((BeginStatusEvent) event).getMessage());
				}
			} catch (IllegalArgumentException e) {
				Activator.log.error(e);
			}
		} else if (event instanceof StepStatusEvent) {
			if (dialog != null && dialog instanceof ProgressDialog) {
				dialog.work(((StepStatusEvent) event).getMessage());
			}
		} else if (event instanceof EndStatusEvent) {
			if (dialog != null) {
				dialog.close();
				dialog = null;
			}
		}
	}

}