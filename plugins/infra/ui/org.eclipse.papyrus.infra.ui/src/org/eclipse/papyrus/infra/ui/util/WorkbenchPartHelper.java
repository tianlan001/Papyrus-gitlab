/*****************************************************************************
 * Copyright (c) 2012, 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) Vincent.Lorenzo@cea.fr - Initial API and implementation
 *  Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 509357
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.util;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * A helper for retrieving from Eclipse Platform UI:
 * <ul>
 * <li>the active editor part</li>
 * <li>the active workbench part</li>
 * <li>and the active workbench</li>
 * </ul>
 * 
 * @since 1.2
 */
public class WorkbenchPartHelper {

	private WorkbenchPartHelper() {
		// nothing to do
	}

	/**
	 * @return
	 * 		The current {@link IWorkbenchPage} or <code>null</code> if not found
	 * @since 2.0
	 */
	public static final IWorkbenchPage getCurrentActiveWorkbenchPage() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (null != workbench) {
			final IWorkbenchWindow activeWorkbench = workbench.getActiveWorkbenchWindow();
			if (null != activeWorkbench) {
				return activeWorkbench.getActivePage();
			}
		}

		return null;
	}


	/**
	 * @return
	 * 		The current {@link IWorkbenchPart} or <code>null</code> if not found
	 */
	public static final IWorkbenchPart getCurrentActiveWorkbenchPart() {
		final IWorkbenchPage activePage = getCurrentActiveWorkbenchPage();
		if (null != activePage) {
			return activePage.getActivePart();
		}

		return null;
	}

	/**
	 * @return
	 * 		The current {@link IEditorPart} or <code>null</code> if not found
	 */
	public static final IEditorPart getCurrentActiveEditorPart() {
		final IWorkbenchPage activePage = getCurrentActiveWorkbenchPage();
		if (null != activePage) {
			return activePage.getActiveEditor();
		}

		return null;
	}
}
