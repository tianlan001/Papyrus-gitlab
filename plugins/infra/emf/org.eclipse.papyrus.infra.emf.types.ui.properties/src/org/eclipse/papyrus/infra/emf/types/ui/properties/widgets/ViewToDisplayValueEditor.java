/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *	Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.emf.types.ui.properties.widgets;

import org.eclipse.jface.window.Window;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay;
import org.eclipse.papyrus.infra.emf.types.ui.properties.messages.Messages;
import org.eclipse.papyrus.infra.emf.types.ui.properties.providers.ViewContentProvider;
import org.eclipse.papyrus.infra.emf.types.ui.properties.providers.ViewLabelProvider;
import org.eclipse.papyrus.infra.properties.contexts.View;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog;
import org.eclipse.papyrus.infra.widgets.editors.TreeSelectorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

/**
 * A value editor for {@link ViewToDisplay} object.
 */
public class ViewToDisplayValueEditor extends ReferenceDialog {


	/** Browse icon */
	private static final String BROWSE_ICON = "/icons/browse_12x12.gif"; //$NON-NLS-1$

	/** THe default height. */
	private final static int DEFAULT_HEIGHT_HINT = 55;

	/** THe default width. */
	private final static int DEFAULT_WIDTH_HINT = 100;

	/** Unique button. */
	private Button button = null;

	/**
	 * Default constructor.
	 */
	public ViewToDisplayValueEditor(final Composite parent, final int style) {
		super(parent, style);
		((GridLayout) getLayout()).numColumns++;
		button = factory.createButton(this, null, SWT.PUSH);
		button.setImage(Activator.getDefault().getImage(BROWSE_ICON));
		button.setToolTipText(Messages.ViewToDisplayValueEditor_browserButtonTooltip);
		button.setEnabled(!readOnly);

		// Display menu when user select button
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				handleManageBrowseButtonPressed();
			}
		});

		setLabelProvider(new ViewLabelProvider());

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#getDefaultLayoutData()
	 */
	@Override
	protected GridData getDefaultLayoutData() {
		GridData data = super.getDefaultLayoutData();
		data.minimumHeight = DEFAULT_HEIGHT_HINT;
		data.minimumWidth = DEFAULT_WIDTH_HINT;

		data.grabExcessHorizontalSpace = true;
		return data;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog#updateControls()
	 */
	@Override
	public void updateControls() {
		// Check if the edit & create buttons should be displayed
		setExclusion(editInstanceButton, true);
		setExclusion(createInstanceButton, true);
		setExclusion(browseValuesButton, true);

		setExclusion(unsetButton, mandatory);
		// Do not display unset if the value is mandatory
		if (!mandatory) {
			boolean enabled = !readOnly;
			enabled = enabled && getValue() != null;

			unsetButton.setEnabled(enabled);
		}
	}


	/**
	 * Updates the displayed label for the current value
	 */
	@Override
	public void updateLabel() {
		// Do nothing
	}

	/**
	 * Handles action when user press the Manage bundle button in the combo area
	 */
	protected void handleManageBrowseButtonPressed() {
		TreeSelectorDialog dialog = new TreeSelectorDialog(getParent().getShell());

		dialog.setContentProvider(new ViewContentProvider());
		dialog.setLabelProvider(new ViewLabelProvider());

		dialog.setTitle(Messages.ViewToDisplayValueEditor_dialogTitle);
		dialog.setMessage(Messages.ViewToDisplayValueEditor_dialogMessage);

		if (Window.OK == dialog.open()) {
			Object[] values = dialog.getResult();
			if (1 != values.length) {
				error = true;
			} else if (values[0] instanceof View) {
				setValue(((View) values[0]));
			} else {
				error = true;
			}
			updateStatus(null);
			pack();
			layout();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog#editAction()
	 */
	@Override
	protected void editAction() {
		handleManageBrowseButtonPressed();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.widgets.editors.ReferenceDialog#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(final boolean readOnly) {
		super.setReadOnly(readOnly);
		button.setEnabled(!readOnly);
	}
}
