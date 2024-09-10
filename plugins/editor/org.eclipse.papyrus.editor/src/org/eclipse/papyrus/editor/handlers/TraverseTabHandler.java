/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Shuai Li (CEA LIST) <shuai.li@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.editor.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.papyrus.editor.Activator;
import org.eclipse.papyrus.editor.PapyrusMultiDiagramEditor;
import org.eclipse.papyrus.infra.core.sasheditor.editor.IPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

/**
 * The handler for the next/previous tab commands that let the user navigate to
 * the next/previous page of the active tab-folder with Ctrl+Shift/Ctrl+Shift+Tab
 *
 * @author Shuai Li
 */
public abstract class TraverseTabHandler extends AbstractHandler {
	private final boolean isPrevious;

	public TraverseTabHandler() {
		isPrevious = false;
	}

	public TraverseTabHandler(boolean isPrevious) {
		this.isPrevious = isPrevious;
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();

		if (activeWorkbenchWindow != null) {
			IWorkbenchPart activePart = activeWorkbenchWindow.getActivePage().getActivePart();

			if (activePart instanceof PapyrusMultiDiagramEditor) {
				PapyrusMultiDiagramEditor papyrusEditor = (PapyrusMultiDiagramEditor) activePart;
				try {
					IPage nextPage = null;
					if (isPrevious) {
						nextPage = papyrusEditor.getISashWindowsContainer().getPreviousPage();
					} else {
						nextPage = papyrusEditor.getISashWindowsContainer().getNextPage();
					}

					papyrusEditor.getISashWindowsContainer().selectPage(nextPage);
				} catch (Exception e) {
					Activator.log.error(e);
				}
			}
		}

		return null;
	}
}
