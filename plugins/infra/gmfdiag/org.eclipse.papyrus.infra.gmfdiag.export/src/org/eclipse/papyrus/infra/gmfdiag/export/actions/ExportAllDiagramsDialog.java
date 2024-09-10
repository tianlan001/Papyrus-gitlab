/*******************************************************************************
 * Copyright (c) 2007 Anyware Technologies. All rights reserved. This program
 * and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which accompanies this distribution, and is
t https://www.eclipse.org/legal/epl-2.0/
t
t SPDX-License-Identifier: EPL-2.0
 *
 * Contributors: 
 * 		Jacques Lescot (Anyware Technologies) - initial API and implementation
 * 		Anass Radouani (AtoS) - use of ExporterManager removed
 * 		Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.net - Bug 440754
 *		Fred Eckertson (Cerner) - fred.eckertson@cerner.com - Bug 502705
 ******************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.export.actions;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.papyrus.infra.gmfdiag.export.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

/**
 *
 * Dialog for Export All diagrams contextual menu.
 *
 * @author <a href="mailto:jacques.lescot@anyware-tech.com">Jacques LESCOT</a>
 */
public class ExportAllDiagramsDialog extends Dialog {

	/** The dialog composite. */
	private ExportComposite dialogComposite = null;

	/** The output directory. */
	private IResource outPutDirectory = null;


	/**
	 * Instantiates a new export all diagrams dialog.
	 *
	 * @param parentShell
	 *            the parent shell
	 * @param initialOutputDirectory
	 *            the initial output directory
	 * @since 2.0
	 */
	public ExportAllDiagramsDialog(Shell parentShell, IResource initialOuputDirectory) {
		super(parentShell);
		outPutDirectory = initialOuputDirectory;
		setBlockOnOpen(true);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/**
	 * Configure shell.
	 *
	 * @param newShell
	 *            the new shell
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);

		// Set title to dialog
		newShell.setText(Messages.ExportAllDiagramsDialog_4);
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 * @return
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		dialogComposite = new ExportComposite(composite, SWT.NONE);
		dialogComposite.setOutputDirectory(outPutDirectory);
		return composite;
	}

	/**
	 * Gets the output directory.
	 *
	 * @return the output directory
	 */
	public IResource getOutputDirectory() {
		return dialogComposite.getOutputDirectory();
	}

	/**
	 * Gets the exporter.
	 *
	 * @return the exporter
	 */
	public String getExporter() {
		return dialogComposite.getExporter();
	}

	/**
	 * Gets the qualified name.
	 *
	 * @return the qualified name
	 */
	public boolean getQualifiedName() {
		return dialogComposite.getQualifiedName();
	}
}
