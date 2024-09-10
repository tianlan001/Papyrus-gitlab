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

import org.eclipse.jface.dialogs.IconAndMessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

/**
 * A dialog that display a message and a ProgressBar
 * (This dialog looks like the {@link org.eclipse.jface.dialogs.ProgressMonitorDialog}, the main difference
 * is that the ProgressMonitorDialog work with the run execution here we don't have a runnable)
 */
public class ProgressDialog extends IconAndMessageDialog {

	private Image image;
	private String title;
	private ProgressBar progressBar;
	private Label subTaskLabel;

	/**
	 *
	 * Constructor.
	 *
	 * @param parentShell
	 * @param title
	 * @param message
	 */
	public ProgressDialog(Shell parentShell, String title, String message) {
		super(parentShell);
		this.image = getInfoImage();
		this.message = message;
		this.title = title;
		setShellStyle(getDefaultOrientation() | SWT.BORDER | SWT.TITLE
				| SWT.APPLICATION_MODAL | SWT.CLOSE);
		setBlockOnOpen(false);
	}

	/**
	 * @see org.eclipse.jface.dialogs.IconAndMessageDialog#getImage()
	 *
	 * @return
	 */
	@Override
	protected Image getImage() {
		return image;
	}

	/**
	 *
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 *
	 * @param shell
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		if (title != null) {
			shell.setText(title);
		}
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 * @return
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		Control control = super.createDialogArea(parent);

		createMessageArea(parent);

		progressBar = new ProgressBar(parent, SWT.SMOOTH);
		GridData gd2 = new GridData();
		gd2.horizontalAlignment = GridData.FILL;
		gd2.grabExcessHorizontalSpace = true;
		gd2.horizontalSpan = 2;
		progressBar.setLayoutData(gd2);
		progressBar.setMaximum(4);

		GridData gd3 = new GridData();
		gd3.horizontalAlignment = GridData.FILL;
		gd3.grabExcessHorizontalSpace = true;
		gd3.horizontalSpan = 2;
		subTaskLabel = new Label(parent, SWT.WRAP);
		subTaskLabel.setText("Start operation"); //$NON-NLS-1$
		subTaskLabel.setLayoutData(gd3);

		return control;
	}

	/**
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		// Do nothing
	}

	/**
	 * Increase the progression
	 *
	 * @param subTaskMessage
	 *            the message of the subtask
	 */
	public void work(String subTaskMessage) {
		progressBar.setState(SWT.NORMAL);
		int sel = progressBar.getSelection();
		progressBar.setSelection(sel + 1);
		subTaskLabel.setText(subTaskMessage);
	}

}
