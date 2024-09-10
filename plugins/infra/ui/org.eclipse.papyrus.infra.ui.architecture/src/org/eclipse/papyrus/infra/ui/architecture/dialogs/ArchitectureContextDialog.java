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
 *   Maged Elaasar - Initial API and implementation
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Bug 550569
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.ui.architecture.dialogs;

import java.util.Arrays;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.ui.architecture.ArchitectureUIPlugin;
import org.eclipse.papyrus.infra.ui.architecture.widgets.ArchitectureContextComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

/**
 * A dialog to allow choosing a new architecture context and viewpoints.
 *
 * @since 2.1
 */
public class ArchitectureContextDialog extends Dialog {

	/**
	 * The dialog section name.
	 */
	private final static String DIALOG_SECTION = ArchitectureContextDialog.class.getName();

	/**
	 * The initial context ids.
	 */
	private String[] originalContextIds;

	/**
	 * The selected context ids.
	 */
	private String[] selectedContextIds;

	/**
	 * The selected viewpoints ids.
	 */
	private String[] selectedViewpointIds;

	/**
	 * The shell title.
	 */
	private String shellTitle;

	/**
	 * The label displayed in the dialog.
	 */
	private String labelText;

	/**
	 * Constructor.
	 *
	 * @param parentShell
	 *            The parent shell.
	 * @param shellTitle
	 *            The shell title.
	 * @param labelText
	 *            The label of text in the dialog.
	 */
	public ArchitectureContextDialog(final Shell parentShell, final String shellTitle, final String labelText) {
		super(parentShell);
		this.shellTitle = shellTitle;
		this.labelText = labelText;
	}

	/**
	 * Get the selected context ids.
	 *
	 * @return The selected context ids.
	 */
	public String[] getSelectedContextIds() {
		return selectedContextIds;
	}

	/**
	 * Get the selected viewpoints ids.
	 *
	 * @return The selected viewpoints ids.
	 */
	public String[] getSelectedViewpointIds() {
		return selectedViewpointIds;
	}

	/**
	 * Set the selected context ids.
	 *
	 * @param selectedContextIds
	 *            The selected context ids.
	 */
	public void setSelectedContexts(final String[] selectedContextIds) {
		this.selectedContextIds = selectedContextIds;
		this.originalContextIds = selectedContextIds;
	}

	/**
	 * Set the selected viewpoints ids.
	 *
	 * @param selectedContextIds
	 *            The selected viewpoints ids.
	 */
	public void setSelectedViewpoints(final String[] selectedViewpointIds) {
		this.selectedViewpointIds = selectedViewpointIds;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite container = (Composite) super.createDialogArea(parent);

		final Label label = new Label(container, SWT.NONE);
		label.setText(labelText);

		final ArchitectureDomainManager manager = ArchitectureDomainManager.getInstance();

		final ArchitectureContextComposite acc = new ArchitectureContextComposite(container, 1, 1, GridData.FILL_BOTH, 0, 0);
		acc.setAllowSeveralContexts(false);
		acc.setSelectedContexts(selectedContextIds);
		acc.setSelectedViewpoints(selectedViewpointIds);
		acc.setInput(manager.getVisibleArchitectureContexts().toArray(new MergedArchitectureContext[0]));
		acc.setUpdater(new ArchitectureContextComposite.Updater() {
			@Override
			public void update() {
				selectedContextIds = acc.getSelectedContexts();
				selectedViewpointIds = acc.getSelectedViewpoints();
			}
		});

		return container;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
	 */
	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(shellTitle);
	}

	/**
	 * [{@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#getDialogBoundsSettings()
	 */
	@Override
	protected IDialogSettings getDialogBoundsSettings() {
		final IDialogSettings globalSettings = ArchitectureUIPlugin.getPlugin().getDialogSettings();
		IDialogSettings dialogSettings = globalSettings.getSection(DIALOG_SECTION);
		if (dialogSettings == null) {
			dialogSettings = globalSettings.addNewSection(DIALOG_SECTION);
		}
		return dialogSettings;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#okPressed()
	 */
	@Override
	protected void okPressed() {
		if (!Arrays.equals(getSelectedContextIds(), originalContextIds)) {
			final MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_WARNING | SWT.YES | SWT.NO);
			messageBox.setMessage("Changing the architecture context may cause significant changes to the model.\nDo you like to proceed?"); //$NON-NLS-1$
			messageBox.setText("Warning"); //$NON-NLS-1$
			final int response = messageBox.open();
			if (response != SWT.YES) {
				return;
			}
		}
		super.okPressed();
	}
}