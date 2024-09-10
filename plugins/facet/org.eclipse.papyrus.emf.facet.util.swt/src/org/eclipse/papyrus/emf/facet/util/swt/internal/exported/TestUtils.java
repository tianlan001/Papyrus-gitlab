/*******************************************************************************
 * Copyright (c) 2012 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - Bug 378475 - unit test failures after table refactoring
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.util.swt.internal.exported;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.IntroPart;

public final class TestUtils {

	private TestUtils() {
		// utility class
	}

	public static void closeWelcomePage() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				IWorkbenchPart activePart = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActivePart();
				if (activePart != null) {
					IntroPart adapter = (IntroPart) activePart.getAdapter(IntroPart.class);
					if (adapter != null) {
						PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().hideView((IViewPart) activePart);
					}
				}
			}
		});
	}

	public static void closeAllEditors() {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
			}
		});
	}
}
