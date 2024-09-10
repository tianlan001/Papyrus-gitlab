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
 *  Francois Le Fevre (CEA LIST) francois.le-fevre - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.usecase.dnd.ui;

import org.eclipse.papyrus.uml.diagram.usecase.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * UI composite element dedicated to manage the choice for ownership switch
 *
 * @author Francois Le Fevre
 *
 */
public class UsecaseOwnerComposite extends Composite {

	private boolean keepOwner;
	private Button keepOwnerButton;
	private Button switchOwnerButton;

	public UsecaseOwnerComposite(Composite composite) {
		super(composite, SWT.NONE);
		this.setLayout(new GridLayout(1, true));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		keepOwner = true;
		createOwnerComposite();
	}

	public UsecaseOwnerComposite(Composite composite, boolean isKeepOwner) {
		super(composite, SWT.NONE);
		this.setLayout(new GridLayout(1, true));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		keepOwner = isKeepOwner;
		createOwnerComposite();
	}

	private Composite createOwnerComposite() {
		Label titleLabel = new Label(this, SWT.NONE);
		titleLabel.setText(Messages.UsecaseOwnerComposite_TITLE);

		Composite composite = new Composite(this, SWT.NULL);
		composite.setLayout(new RowLayout());

		keepOwnerButton = new Button(composite, SWT.RADIO);
		keepOwnerButton.setText(Messages.UsecaseOwnerComposite_OWNER_KEEP);
		keepOwnerButton.setSelection(keepOwner);
		switchOwnerButton = new Button(composite, SWT.RADIO);
		switchOwnerButton.setText(Messages.UsecaseOwnerComposite_OWNER_SWITCH);
		switchOwnerButton.setSelection(!keepOwner);

		SelectionListener selectOwnerListener = new SelectionAdapter() {

			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (keepOwnerButton.equals(e.getSource())) {
					keepOwner = true;
				} else {
					keepOwner = false;
				}

			}
		};
		keepOwnerButton.addSelectionListener(selectOwnerListener);
		switchOwnerButton.addSelectionListener(selectOwnerListener);

		return composite;
	}

	/**
	 * @return the keepOwner
	 */
	public boolean isKeepOwner() {
		return keepOwner;
	}

}
