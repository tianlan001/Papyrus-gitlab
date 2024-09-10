/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.wizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.properties.ui.runtime.DisplayEngine;
import org.eclipse.papyrus.infra.properties.ui.util.PropertiesDisplayHelper;
import org.eclipse.papyrus.infra.properties.ui.widgets.layout.PropertiesLayout;
import org.eclipse.papyrus.toolsmiths.messages.Messages;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationConfiguration;
import org.eclipse.papyrus.toolsmiths.model.customizationplugin.CustomizationPluginFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;


public class CustomizationPage extends WizardPage {

	private CustomizationConfiguration configuration;

	private DisplayEngine displayEngine;

	protected CustomizationPage() {
		this(null);
	}

	protected CustomizationPage(CustomizationConfiguration configuration) {
		super(Messages.CustomizationPage_Customization);// , null, org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImageDescriptor("icons/papyrus.png"));
		setDescription("Select your customization configuration files");
		setTitle(Messages.CustomizationPage_Customization);
		this.configuration = configuration;
	}

	protected void setConfiguration(CustomizationConfiguration configuration) {
		this.configuration = configuration;
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new PropertiesLayout());

		if (configuration == null) {
			this.configuration = CustomizationPluginFactory.eINSTANCE.createCustomizationConfiguration();
		}

		displayEngine = PropertiesDisplayHelper.display(this.configuration, container);

		setControl(container);
	}

	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			this.configuration.setPlugin(getWizard().getPluginId());
		}
		super.setVisible(visible);
	}

	@Override
	public CreateNewCustomizationPluginWizard getWizard() {
		return (CreateNewCustomizationPluginWizard) super.getWizard();
	}

	public CustomizationConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 *
	 */
	@Override
	public void dispose() {
		if (displayEngine != null) {
			displayEngine.dispose();
		}
		super.dispose();
	}

}
