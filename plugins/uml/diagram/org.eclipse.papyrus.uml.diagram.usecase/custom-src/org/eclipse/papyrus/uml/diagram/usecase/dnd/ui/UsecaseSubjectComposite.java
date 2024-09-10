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
 * UI composite element dedicated to manage the choice for sibject
 *
 * @author Francois Le Fevre
 *
 */
public class UsecaseSubjectComposite extends Composite {

	private boolean keepSubject;
	private Button keepSubjectButton;
	private Button switchSubjectButton;

	public UsecaseSubjectComposite(Composite composite) {
		super(composite, SWT.NONE);
		this.setLayout(new GridLayout(1, true));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		keepSubject = true;
		createSubjectComposite();
	}

	public UsecaseSubjectComposite(Composite composite, boolean isKeepSubject) {
		super(composite, SWT.NONE);
		this.setLayout(new GridLayout(1, true));
		this.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		keepSubject = isKeepSubject;
		createSubjectComposite();
	}

	private Composite createSubjectComposite() {
		Label titleLabel = new Label(this, SWT.NONE);
		titleLabel.setText(org.eclipse.papyrus.uml.diagram.usecase.messages.Messages.UsecaseSubjectComposite_CONTEXT);

		Composite composite = new Composite(this, SWT.NULL);
		composite.setLayout(new RowLayout());

		keepSubjectButton = new Button(composite, SWT.RADIO);
		keepSubjectButton.setText(org.eclipse.papyrus.uml.diagram.usecase.messages.Messages.UsecaseSubjectComposite_KEEP_SUBJECT);
		keepSubjectButton.setSelection(keepSubject);
		switchSubjectButton = new Button(composite, SWT.RADIO);
		switchSubjectButton.setText(org.eclipse.papyrus.uml.diagram.usecase.messages.Messages.UsecaseSubjectComposite_SWITCH_SUBJECT);
		switchSubjectButton.setSelection(!keepSubject);

		SelectionListener selectOwnerListener = new SelectionAdapter() {

			/**
			 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
			 */
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (keepSubjectButton.equals(e.getSource())) {
					keepSubject = true;
				} else {
					keepSubject = false;
				}

			}
		};
		keepSubjectButton.addSelectionListener(selectOwnerListener);
		switchSubjectButton.addSelectionListener(selectOwnerListener);

		return composite;
	}

	/**
	 * @return the keepSubject
	 */
	public boolean isKeepSubject() {
		return keepSubject;
	}

}
