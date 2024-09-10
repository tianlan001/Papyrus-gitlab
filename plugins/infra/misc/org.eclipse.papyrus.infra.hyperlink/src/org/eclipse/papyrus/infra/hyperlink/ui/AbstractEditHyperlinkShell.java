/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 488965
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.hyperlink.ui;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Abstract superclass of the dialog presented for creation of a new hyperlink.
 */
public abstract class AbstractEditHyperlinkShell extends TrayDialog {

	/** The Constant OBJECT_LABEL. */
	protected static final String OBJECT_LABEL = Messages.AbstractEditHyperlinkShell_object;

	/** The tooltip input text. */
	private Text tooltipInputText = null;

	/** The tooltip input label. */
	private CLabel tooltipInputLabel = null;

	/** The Object labeltext. */
	private Text objectLabeltext = null;

	/** The Objectc label. */
	private CLabel objectLabel = null;

	private Button useDefaultCheckBox = null;

	/** The search button. */
	private Button searchButton = null;

	private boolean hasSearchButton;

	/**
	 * @since 2.0
	 */
	protected AbstractEditHyperlinkShell(Shell shell, boolean hasSearchButton) {
		super(shell);

		this.hasSearchButton = hasSearchButton;
	}

	/**
	 * @since 2.0
	 */
	protected AbstractEditHyperlinkShell(IShellProvider shellProvider, boolean hasSearchButton) {
		super(shellProvider);

		this.hasSearchButton = hasSearchButton;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);

		newShell.setText(Messages.AbstractEditHyperlinkShell_EditHyperLink);
		newShell.setToolTipText(Messages.AbstractEditHyperlinkShell_EditionOfAHyperLink);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite result = (Composite) super.createDialogArea(parent);

		GridData gridData51 = new GridData();
		gridData51.horizontalAlignment = GridData.FILL;
		gridData51.verticalAlignment = GridData.CENTER;
		GridData gridData3 = new GridData();
		gridData3.grabExcessHorizontalSpace = true;
		gridData3.verticalAlignment = GridData.CENTER;
		gridData3.horizontalAlignment = GridData.FILL;
		GridData gridData2 = new GridData();
		gridData2.grabExcessHorizontalSpace = true;
		gridData2.verticalAlignment = GridData.CENTER;
		gridData2.horizontalAlignment = GridData.FILL;
		GridData gridData1 = new GridData();
		gridData1.horizontalSpan = hasSearchButton ? 5 : 6;
		gridData1.verticalAlignment = GridData.CENTER;
		gridData1.grabExcessHorizontalSpace = true;
		gridData1.grabExcessVerticalSpace = false;
		gridData1.horizontalAlignment = GridData.FILL;
		GridData gridData = new GridData();
		gridData.horizontalSpan = 5;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.CENTER;
		gridData.grabExcessHorizontalSpace = true;
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 7;
		gridLayout.makeColumnsEqualWidth = true;

		result.setLayout(gridLayout);

		objectLabel = new CLabel(result, SWT.NONE);
		objectLabel.setText(OBJECT_LABEL);
		objectLabeltext = new Text(result, SWT.BORDER);
		objectLabeltext.setLayoutData(gridData1);

		if (hasSearchButton) {
			searchButton = new Button(result, SWT.PUSH);
			searchButton.setImage(Activator.getDefault().getIcon(Activator.IMG_LOUPE));
			searchButton.setLayoutData(gridData51);
			searchButton.setText(""); //$NON-NLS-1$
			searchButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					onSearch();
				}
			});
		}

		tooltipInputLabel = new CLabel(result, SWT.NONE);
		tooltipInputLabel.setText(Messages.AbstractEditHyperlinkShell_ToolTipText);
		tooltipInputLabel.setToolTipText(Messages.AbstractEditHyperlinkShell_ToolTipText_);
		tooltipInputText = new Text(result, SWT.BORDER);
		tooltipInputText.setLayoutData(gridData);

		useDefaultCheckBox = new Button(result, SWT.CHECK);
		useDefaultCheckBox.setText(Messages.AbstractEditHyperlinkShell_UseDefault);
		useDefaultCheckBox.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				onUseDefaultTooltip();
			}
		});

		contentsCreated();

		return result;
	}

	/**
	 * Hook that subclasses may override to handle the creation of the dialog contents.
	 * 
	 * @since 2.0
	 */
	protected void contentsCreated() {
		// Pass
	}

	/**
	 * Gets the tooltip input text.
	 *
	 * @return the tooltipInputText
	 */
	protected Text getTooltipInputText() {
		return tooltipInputText;
	}

	/**
	 * @since 2.0
	 */
	protected Text getObjectLabelText() {
		return objectLabeltext;
	}

	/**
	 * @since 2.0
	 */
	protected CLabel getObjectLabel() {
		return objectLabel;
	}

	protected Button getUseDefaultCheckBox() {
		return useDefaultCheckBox;
	}

	/**
	 * @since 2.0
	 */
	protected Button getSearchButton() {
		return searchButton;
	}

	/**
	 * Overridden by subclasses to handle invocation of the search button.
	 * 
	 * @since 2.0
	 */
	protected void onSearch() {
		// Pass
	}

	/**
	 * Overridden by subclasses to handle invocation of the "use default tooltip" button.
	 * 
	 * @since 2.0
	 */
	protected void onUseDefaultTooltip() {
		// Pass
	}
}
