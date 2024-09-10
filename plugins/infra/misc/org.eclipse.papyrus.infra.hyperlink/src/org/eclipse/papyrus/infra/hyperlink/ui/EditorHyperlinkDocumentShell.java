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

import org.eclipse.papyrus.infra.hyperlink.messages.Messages;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class EditorHyperlinkDocumentShell.
 */
public class EditorHyperlinkDocumentShell extends AbstractEditHyperlinkShell {

	/** The usedefault tooltip. */
	protected boolean usedefaultTooltip = true;

	/** The hyperlink document. */
	protected HyperLinkDocument hyperlinkDocument;

	/**
	 * Instantiates a new editor hyperlink document shell.
	 * 
	 * @since 2.0
	 */
	public EditorHyperlinkDocumentShell(Shell parentShell) {
		super(parentShell, true);
	}

	/**
	 * Gets the hyperlink document.
	 *
	 * @return the hyperlinkDocument
	 */
	public HyperLinkDocument getHyperlinkDocument() {
		return hyperlinkDocument;
	}

	/**
	 * Sets the hyperlink document.
	 *
	 * @param hyperlinkDocument
	 *            the hyperlinkDocument to set
	 */
	public void setHyperlinkDocument(HyperLinkDocument hyperlinkDocument) {
		this.hyperlinkDocument = hyperlinkDocument;
		updateFields();
	}

	@Override
	protected void contentsCreated() {
		getObjectLabel().setText(Messages.AbstractEditHyperlinkDocumentShell_Document);

		// intialize "use default" check box
		getUseDefaultCheckBox().setSelection(usedefaultTooltip);
		getObjectLabelText().setEditable(false);
		if (usedefaultTooltip) {
			getTooltipInputText().setEditable(false);
			getTooltipInputText().setText(getObjectLabelText().getText());
		}

		updateFields();
	}

	private void updateFields() {
		if (getHyperlinkDocument() != null) {
			if (getObjectLabelText() != null) {
				getObjectLabelText().setText(getHyperlinkDocument().getHyperlinkDocument());
			}
			if (getTooltipInputText() != null) {
				getTooltipInputText().setText(getHyperlinkDocument().getTooltipText());
			}
		}
	}

	@Override
	protected void onSearch() {
		FileDialog fd = new FileDialog(getShell(), SWT.OPEN);
		fd.setText(Messages.EditorHyperlinkDocumentShell_Open);
		String[] filterExt = { "*.pdf", "*.doc", "*.txt", "*" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
		fd.setFilterExtensions(filterExt);
		String selected = fd.open();
		if (selected != null) {
			getObjectLabelText().setText(selected);
			if (usedefaultTooltip) {
				getTooltipInputText().setText(selected);
			}
		}
	}

	@Override
	protected void onUseDefaultTooltip() {
		usedefaultTooltip = getUseDefaultCheckBox().getSelection();
		if (usedefaultTooltip) {
			getTooltipInputText().setEditable(false);
			getTooltipInputText().setText(getObjectLabelText().getText());
		} else {
			getTooltipInputText().setEditable(true);
		}
	}

	@Override
	protected void cancelPressed() {
		hyperlinkDocument = null;
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		if (hyperlinkDocument == null) {
			hyperlinkDocument = new HyperLinkDocument();
		}
		hyperlinkDocument.setHyperlinkDocument(getObjectLabelText().getText().trim());
		hyperlinkDocument.setTooltipText(getTooltipInputText().getText().trim());

		super.cancelPressed();
	}
}
