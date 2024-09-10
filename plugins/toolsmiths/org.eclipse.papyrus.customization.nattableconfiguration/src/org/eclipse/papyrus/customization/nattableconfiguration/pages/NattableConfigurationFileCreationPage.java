/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.customization.nattableconfiguration.pages;

import org.eclipse.papyrus.customization.nattableconfiguration.helper.TableConfigurationHelper;
import org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * This page allows to edit the name of the Papyrus Table configuration to create.
 */
public class NattableConfigurationFileCreationPage extends EditGenericNattableConfigurationFieldsNattableWizardPage {
	/**
	 * The nattable configuration file name text composite.
	 */
	private Text nattableConfigurationFileName;

	/**
	 * The file name modify listener.
	 */
	private Listener fileNameModifyListener = new Listener() {

		@Override
		public void handleEvent(final Event e) {
			setPageComplete(canFlipToNextPage());
		}
	};

	/**
	 * Default constructor.
	 *
	 * @param helper
	 *            The table configuration helper
	 */
	public NattableConfigurationFileCreationPage(TableConfigurationHelper helper) {
		super(helper);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		Composite container = new Composite(parent, SWT.BORDER);
		final GridLayout gridLayout = new GridLayout(1, false);
		container.setLayout(gridLayout);
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		final Group group = createGroup(container, Messages.NattableConfigurationProjectCreationPage_nattableConfigurationFileNameLabel);
		nattableConfigurationFileName = new Text(group, SWT.BORDER);
		nattableConfigurationFileName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		nattableConfigurationFileName.addListener(SWT.Modify, fileNameModifyListener);
		super.createControl(container);
		setControl(container);
	}

	/**
	 * Create a new group with the given name.
	 *
	 * @param parent
	 *            The parent composite
	 * @param name
	 *            The name of the group
	 * @return The created group
	 */
	private static Group createGroup(final Composite parent, final String name) {
		final Group group = new Group(parent, SWT.NONE);
		group.setText(name);
		final GridLayout layout = new GridLayout(1, true);
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		group.setLayout(layout);
		final GridData data = new GridData(SWT.FILL, SWT.FILL, true, false);
		group.setLayoutData(data);
		return group;
	}

	/**
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.pages.EditGenericNattableConfigurationFieldsNattableWizardPage#isPageComplete()
	 *
	 * @return <code>true</code> if the page is complete, <code>false</false> otherwise
	 */
	@Override
	public boolean isPageComplete() {
		if (null != nattableConfigurationFileName) {
			if ("".equals(nattableConfigurationFileName.getText())) { //$NON-NLS-1$
				this.setErrorMessage("Set nattable configuration file name"); //$NON-NLS-1$
				return false;
			}
		}

		return super.isPageComplete();
	}

	/**
	 * @return The nattable configuration file name
	 */
	public String getNattableConfigurationFileName() {
		return nattableConfigurationFileName.getText();
	}

}
