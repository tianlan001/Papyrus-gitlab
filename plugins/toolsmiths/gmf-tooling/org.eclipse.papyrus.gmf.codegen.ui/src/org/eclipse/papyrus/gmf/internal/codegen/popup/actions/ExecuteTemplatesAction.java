/******************************************************************************
 * Copyright (c) 2005, 2020 Borland Software Corporation, CEA LIST, Artal
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *    Artem Tikhomirov (Borland) - initial API and implementation
 *    Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *    Etienne Allogo (ARTAL) - etienne.allogo@artal.fr - Bug 569174 : Obsolete plugins (graphdef /bridge, etc.) /external 'x-friends' removed
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.codegen.popup.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Runs generation of diagram editor project
 * 
 * @author artem
 */
public abstract class ExecuteTemplatesAction implements IObjectActionDelegate {

	private IFile gmFile;

	private IWorkbenchPart myPart;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		myPart = targetPart;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		gmFile = (IFile) ((IStructuredSelection) selection).getFirstElement();
	}

	public void run(IAction action) {
		ExecuteTemplatesOperation op = createOperation();
		op.setName(action.getText());
		op.setShell(getShell());
		op.setGenModelURI(URI.createPlatformResourceURI(gmFile.getFullPath().toString(), true));
		op.run();
	}

	protected abstract ExecuteTemplatesOperation createOperation();

	private Shell getShell() {
		return myPart.getSite().getShell();
	}
}
