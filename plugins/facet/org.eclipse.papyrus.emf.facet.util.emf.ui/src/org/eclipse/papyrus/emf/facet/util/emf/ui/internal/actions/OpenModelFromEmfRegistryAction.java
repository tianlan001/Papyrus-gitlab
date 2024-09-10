/*******************************************************************************
 * Copyright (c) 2008, 2010, 2011 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gabriel Barbier (Mia-Software) - initial API and implementation
 *    Nicolas Bros (Mia-Software)
 *    Nicolas Guyomar (Mia-Software) - Bug 333652 Extension point offering the possibility to declare an EPackage browser
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.util.emf.ui.internal.actions;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.emf.facet.util.emf.core.IBrowserRegistry;
import org.eclipse.papyrus.emf.facet.util.emf.ui.internal.dialogs.UriSelectionDialog;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * This is an action class which opens a dialog to select an EPackage to browse
 */
public class OpenModelFromEmfRegistryAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow fWorkbenchWindow;

	@Override
	public void dispose() {
		// nothing
	}

	@Override
	public void init(final IWorkbenchWindow window) {
		this.fWorkbenchWindow = window;
	}

	@Override
	public void run(final IAction action) {
		final IWorkbenchPage activePage = this.fWorkbenchWindow.getActivePage();
		if (activePage != null) {
			final UriSelectionDialog launcher = new UriSelectionDialog(
					this.fWorkbenchWindow.getShell());
			if (launcher.open() == Window.OK) {
				final String nsURI = launcher.getUriSelected();
				if (nsURI != null && nsURI.length() > 0) {
					EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(nsURI);
					IBrowserRegistry.INSTANCE.browseEPackage(ePackage);
				}
			}
		}
	}

	@Override
	public void selectionChanged(final IAction action, final ISelection selection) {
		// nothing
	}
}
