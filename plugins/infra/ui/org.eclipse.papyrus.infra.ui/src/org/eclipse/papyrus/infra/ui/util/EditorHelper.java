/*****************************************************************************
 * Copyright (c) 2012, 2021 CEA LIST, Christian W. Damus, and others.
 *
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
 *  Christian W. Damus - bugs 488965, 573987
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.util;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * A helper that provides convenient access to the UI context of editors in a workbench,
 * such as active window, part, editor, page, etc.
 *
 * @since 1.2
 *
 */
public class EditorHelper {

	private EditorHelper() {
		// nothing to do
	}


	/**
	 * Obtain a specific helper instance for the given {@code workbench} that
	 * makes the best effort to get results, even if they are not actually
	 * active elements of the workbench.
	 *
	 * @param workbench
	 *            the workbench
	 *
	 * @return the specific nest-effort helper instance for the {@code workbench}
	 *
	 * @since 3.3
	 * @see #getInstance(IWorkbench, boolean)
	 */
	public static Instance getInstance(IWorkbench workbench) {
		return getInstance(workbench, true);
	}

	/**
	 * Obtain a specific helper instance for the given {@code workbench} that
	 * optionally makes the best effort to get results, even if they are not
	 * actually active elements of the workbench.
	 *
	 * @param workbench
	 *            the workbench
	 * @param bestEffort
	 *            whether to make a best effort to get an element, even if none is actually active in the literal sense
	 *
	 * @return the specific helper instance for the {@code workbench}
	 *
	 * @since 3.3
	 */
	public static Instance getInstance(IWorkbench workbench, boolean bestEffort) {
		return new Instance(workbench, bestEffort);
	}

	/**
	 *
	 * @return
	 *         the current workbench window or <code>null</code> if not found
	 */
	public static final Shell getActiveShell() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			return workbench.getActiveWorkbenchWindow().getShell();
		}
		return Display.getCurrent().getActiveShell();
	}

	/**
	 *
	 * @return
	 *         the current workbench window or <code>null</code> if not found
	 */
	public static final IWorkbenchWindow getActiveWindow() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			return workbench.getActiveWorkbenchWindow();
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         the current editor or <code>null</code> if not found
	 */
	public static final IEditorPart getCurrentEditor() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			final IWorkbenchWindow activeWorkbench = workbench.getActiveWorkbenchWindow();
			if (activeWorkbench != null) {
				final IWorkbenchPage activePage = activeWorkbench.getActivePage();
				if (activePage != null) {
					return activePage.getActiveEditor();
				}
			}
		}
		return null;
	}

	/**
	 *
	 * @return
	 *         the current active part or <code>null</code> if not found
	 */
	public static final IWorkbenchPart getActivePart() {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		if (workbench != null) {
			final IWorkbenchWindow activeWorkbench = workbench.getActiveWorkbenchWindow();
			if (activeWorkbench != null) {
				final IWorkbenchPage activePage = activeWorkbench.getActivePage();
				if (activePage != null) {
					return activePage.getActivePart();
				}
			}
		}
		return null;
	}

	//
	// Nested types
	//

	/**
	 * A specific editor-helper instance for a workbench.
	 *
	 * @since 3.3
	 */
	public static final class Instance {

		private final IWorkbench workbench;
		private final boolean bestEffort;

		private Instance(IWorkbench workbench, boolean bestEffort) {
			super();

			this.workbench = workbench;
			this.bestEffort = bestEffort;
		}

		/**
		 * Get the currently active workbench window shell, or whatever is the display's
		 * active shell if no workbench window actually is active.
		 *
		 * @return the current active shell
		 */
		public Shell getActiveShell() {
			IWorkbenchWindow window = getActiveWindow();
			return (window == null) ? Display.getCurrent().getActiveShell() : window.getShell();
		}

		/**
		 * Get the currently active workbench window, or the first available window as a best-effort
		 * guess if applicable.
		 *
		 * @return the active workbench window, or a best-effort guess if I am so configured
		 */
		public IWorkbenchWindow getActiveWindow() {
			if (workbench == null) {
				return null;
			}

			IWorkbenchWindow result = workbench.getActiveWorkbenchWindow();

			if (result == null && bestEffort && workbench.getWorkbenchWindowCount() > 0) {
				// There isn't actually an active page. So just get some page
				result = workbench.getWorkbenchWindows()[0];
			}

			return result;
		}

		/**
		 * Get the currently active workbench page, or the first available page as a best-effort
		 * guess if applicable.
		 *
		 * @return the active workbench page, or a best-effort guess if I am so configured
		 */
		public IWorkbenchPage getActivePage() {
			IWorkbenchPage result = null;

			IWorkbenchWindow window = getActiveWindow();
			if (window != null) {
				result = window.getActivePage();
				if (result == null && bestEffort) {
					// It's not active. Just get some page
					IWorkbenchPage[] pages = window.getPages();
					if (pages.length > 0) {
						result = pages[0];
					}
				}
			}

			return result;
		}

		/**
		 * Get the currently active editor, if any, in the currently active {@linkplain #getActivePage() workbench page}.
		 *
		 * @return the active editor in the active page, or {@code null} if either the active page has no active editor or
		 *         an active page could not be determined
		 *
		 * @see #getActivePage()
		 */
		public IEditorPart getActiveEditor() {
			IWorkbenchPage page = getActivePage();
			return (page == null) ? null : page.getActiveEditor();
		}

		/**
		 * Get the currently active part, if any, in the currently active {@linkplain #getActivePage() workbench page}.
		 *
		 * @return the active part in the active page, or {@code null} if either the active page has no active part or
		 *         an active page could not be determined
		 *
		 * @see #getActivePage()
		 */
		public IWorkbenchPart getActivePart() {
			IWorkbenchPage page = getActivePage();
			return (page == null) ? null : page.getActivePart();
		}

	}

}
