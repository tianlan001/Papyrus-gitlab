/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, Christian W. Damus, and others.
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
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkWeb;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class HyperlinkWebEditor is used to add or modify an hyperlink web entry.
 * you can use the default button for the tooltip.
 */
public class EditorHyperLinkWebShell extends AbstractEditHyperlinkShell {

	/** The Constant HYPERLINKS_LABEL. */
	protected static final String HYPERLINKS_LABEL = Messages.EditorHyperLinkWebShell_Hyperlinks;

	/** The Constant HTTP. */
	protected static final String HTTP = "http://"; //$NON-NLS-1$

	/** The hyper link web. */
	private HyperLinkWeb hyperLinkWeb = null;

	/** The usedefault tooltip. */
	private boolean usedefaultTooltip = true;

	/**
	 * Instantiates a new hyperlink web editor.
	 * 
	 * @since 2.0
	 */
	public EditorHyperLinkWebShell(Shell parentShell) {
		super(parentShell, false);
	}

	@Override
	protected void contentsCreated() {
		this.getObjectLabel().setText(HYPERLINKS_LABEL);

		updateFields();

		// fill information
		if (hyperLinkWeb != null) {
			this.getObjectLabelText().setText(hyperLinkWeb.getHyperLinkWeb());
			this.getTooltipInputText().setText(hyperLinkWeb.getTooltipText());
		} else {
			this.getObjectLabelText().setText(HTTP);
			this.getObjectLabelText().setSelection(HTTP.length());
		}
		// intialize "use default" check box
		getUseDefaultCheckBox().setSelection(usedefaultTooltip);
		if (usedefaultTooltip) {
			getTooltipInputText().setEditable(false);
			getTooltipInputText().setText(getObjectLabelText().getText());
		}

		// add a key listener on inputText to synchronize with the tooltip in
		// the case of use default
		getObjectLabelText().addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (usedefaultTooltip) {
					getTooltipInputText().setText(getObjectLabelText().getText());
				}
			}
		});
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
		hyperLinkWeb = null;
		super.cancelPressed();
	}


	@Override
	protected void okPressed() {
		if (hyperLinkWeb == null) {
			hyperLinkWeb = new HyperLinkWeb();
		}
		hyperLinkWeb.setHyperLinkWeb(getObjectLabelText().getText().trim());
		hyperLinkWeb.setTooltipText(getTooltipInputText().getText().trim());

		super.okPressed();
	}


	/**
	 * Gets the hyper link web.
	 *
	 * @return the hyperLinkWeb
	 */
	public HyperLinkWeb getHyperLinkWeb() {
		return hyperLinkWeb;
	}

	/**
	 * Sets the hyper link web.
	 *
	 * @param hyperLinkWeb
	 *            the hyperLinkWeb to set
	 */
	public void setHyperLinkWeb(HyperLinkWeb hyperLinkWeb) {
		this.hyperLinkWeb = hyperLinkWeb;
		updateFields();
	}

	private void updateFields() {
		if (hyperLinkWeb != null) {
			if (getObjectLabelText() != null) {
				getObjectLabelText().setText(hyperLinkWeb.getHyperLinkWeb());
			}
			if (getTooltipInputText() != null) {
				getTooltipInputText().setText(hyperLinkWeb.getTooltipText());
			}
		}

	}

}
