/******************************************************************************
 * Copyright (c) 2007, 2020 Borland Software Corporation, CEA LIST, Artal and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/ 
 * 
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 *     Borland Software Corporation - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 *****************************************************************************/
package org.eclipse.papyrus.gmf.internal.codegen.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWizard;

public class AddExtensionModelAction implements IObjectActionDelegate {

	private IStructuredSelection mySelection;

	private IWorkbenchPart myPart;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		myPart = targetPart;
	}

	public void selectionChanged(IAction action, ISelection selection) {
		mySelection = (IStructuredSelection) selection;
	}

	public void run(IAction action) {
		// Create the wizard
		IWorkbenchWizard wizard = createWizard();
		wizard.init(getWorkbench(), getSelection());
		
		// Create the wizard dialog
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		// Open the wizard dialog
		dialog.open();
	}
	
	protected IWorkbenchWizard createWizard() {
		return new GMFGenExtensionModelWizard();
	}

	private Shell getShell() {
		return myPart.getSite().getShell();
	}
	
	private IWorkbench getWorkbench() {
		return myPart.getSite().getWorkbenchWindow().getWorkbench();
	}

	private IStructuredSelection getSelection() {
		return mySelection;
	}
}
