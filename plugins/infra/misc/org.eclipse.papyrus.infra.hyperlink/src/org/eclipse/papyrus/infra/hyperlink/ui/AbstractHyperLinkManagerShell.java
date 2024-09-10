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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.IShellProvider;
import org.eclipse.papyrus.infra.hyperlink.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

/**
 * Abstract superclass of the initial dialog shown for managing the hyperlinks attached
 * to an object.
 */
public abstract class AbstractHyperLinkManagerShell extends TrayDialog {

	private TabFolder tabFolder = null;

	private ArrayList<AbstractHyperLinkTab> tabList = new ArrayList<>();


	/**
	 * @since 2.0
	 */
	public AbstractHyperLinkManagerShell(Shell parentShell) {
		super(parentShell);

		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/**
	 * @since 2.0
	 */
	public AbstractHyperLinkManagerShell(IShellProvider parentProvider) {
		super(parentProvider);

		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	/**
	 * @since 2.0
	 */
	protected TabFolder getTabFolder() {
		return tabFolder;
	}

	/**
	 * Obtains an unmodifiable view of the tabs instantiated in the dialog.
	 * 
	 * @return an unmodifiable view of my tabs
	 * 
	 * @since 2.0
	 */
	protected List<AbstractHyperLinkTab> getTabs() {
		return Collections.unmodifiableList(tabList);
	}

	/**
	 * Adds a tab to the dialog.
	 * 
	 * @param tab
	 *            a tab to add
	 * 
	 * @since 2.0
	 */
	protected void addTab(AbstractHyperLinkTab tab) {
		tabList.add(tab);
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);

		newShell.setText(Messages.AbstractHyperLinkManagerShell_HyperLink);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		// Tabs on the top
		tabFolder = new TabFolder(parent, SWT.TOP);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));
		applyDialogFont(tabFolder);
		return tabFolder;
	}

	@Override
	protected void cancelPressed() {
		tabList.clear();
		super.cancelPressed();
	}

	@Override
	protected void okPressed() {
		saveDialogSettings();
		tabList.clear();

		super.okPressed();
	}

	/**
	 * Overridden by subclasses to save dialog-specific settings.
	 * The default implementation does nothing.
	 * 
	 * @since 2.0
	 */
	protected void saveDialogSettings() {
		// Pass
	}
}
