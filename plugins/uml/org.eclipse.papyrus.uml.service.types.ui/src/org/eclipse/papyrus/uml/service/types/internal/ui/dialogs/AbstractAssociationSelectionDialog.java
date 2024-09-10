/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.uml.service.types.internal.ui.dialogs;

import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.service.types.internal.ui.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

/**
 * code generated to create this chooser of association
 */
public class AbstractAssociationSelectionDialog extends Dialog {

	protected Object result;

	protected Shell shlAssociationselection;

	protected Table table;

	protected Button btnOk;

	protected Button btnCancel;

	/**
	 * Create the dialog.
	 *
	 * @param parent
	 * @param style
	 */
	public AbstractAssociationSelectionDialog(Shell parent, int style) {
		super(parent, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		setText("SWT Dialog"); //$NON-NLS-1$
	}

	/**
	 * Open the dialog.
	 *
	 * @return the result
	 */
	public Object open() {
		createContents();
		shlAssociationselection.open();
		shlAssociationselection.layout();
		Display display = getParent().getDisplay();
		while(!shlAssociationselection.isDisposed()) {
			if(!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	protected void createContents() {
		shlAssociationselection = new Shell(getParent(), getStyle());
		shlAssociationselection.setSize(501, 227);
		shlAssociationselection.setText(Messages.AbstractAssociationSelectionDialog_1);
		shlAssociationselection.setLayout(new FillLayout(SWT.HORIZONTAL));
		Composite composite = new Composite(shlAssociationselection, SWT.NONE);
		composite.setLayout(null);
		composite.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		Label lblSelectTheAssociation = new Label(composite, SWT.NONE);
		lblSelectTheAssociation.setLocation(24, 10);
		lblSelectTheAssociation.setSize(441, 30);
		lblSelectTheAssociation.setText(Messages.AbstractAssociationSelectionDialog_2);
		lblSelectTheAssociation.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		FontData[] fontdatas = { new FontData("Tahoma", 12, SWT.BOLD) }; //$NON-NLS-1$
		lblSelectTheAssociation.setFont(Activator.getFontManager().get(fontdatas));
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(25, 48, 440, 85);
		btnOk = new Button(composite, SWT.NONE);
		btnOk.setBounds(396, 159, 68, 23);
		btnOk.setText(Messages.AbstractAssociationSelectionDialog_4);
		btnCancel = new Button(composite, SWT.NONE);
		btnCancel.setBounds(291, 159, 68, 23);
		btnCancel.setText(Messages.AbstractAssociationSelectionDialog_5);
	}
}
