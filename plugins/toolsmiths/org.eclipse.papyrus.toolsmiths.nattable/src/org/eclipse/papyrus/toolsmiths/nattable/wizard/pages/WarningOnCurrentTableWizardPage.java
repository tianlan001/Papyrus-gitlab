/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.nattable.wizard.pages;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.toolsmiths.nattable.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 *
 * This page display a warning, according to a {@link IStatus} before to launch the process of TableConfiguration wizard.
 *
 */
public class WarningOnCurrentTableWizardPage extends WizardPage implements SelectionListener {

	/**
	 * The title of the page
	 */
	private static final String PAGE_TITLE = "WarningOnCurrentTableWizardPage"; //$NON-NLS-1$

	/**
	 * the status to display
	 */
	private final IStatus status;

	/**
	 * the checkbox used to be sure the user read the warning message
	 */
	private Button checkbox;

	/**
	 *
	 * Constructor.
	 *
	 * @param status
	 *            the status to display
	 */
	public WarningOnCurrentTableWizardPage(final IStatus status) {
		super(PAGE_TITLE);
		this.status = status;
		setMessage(Messages.WarningOnCurrentTableWizardPage_PleaseCheckDocumentation, DialogPage.WARNING);
		setPageComplete(false);
	}

	/**
	 *
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new GridLayout(1, true));

		Text text = new Text(composite, SWT.MULTI);
		text.setText(status.getMessage());
		text.setEditable(false);
		text.setEnabled(true);

		GridData layout = new GridData(SWT.FILL);
		text.setLayoutData(layout);


		checkbox = new Button(composite, SWT.CHECK);
		checkbox.setText(Messages.WarningOnCurrentTableWizardPage_OKMessageRead);
		layout = new GridData(SWT.FILL);
		checkbox.setLayoutData(layout);
		checkbox.addSelectionListener(this);
		setControl(composite);
	}


	/**
	 *
	 * @param e
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		setPageComplete(this.checkbox.getSelection());

	}

	/**
	 *
	 * @param e
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		setPageComplete(this.checkbox.getSelection());
	}


}
