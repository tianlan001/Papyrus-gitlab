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
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation  
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.palette.dialog;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.gmfdiag.paletteconfiguration.PaletteConfiguration;
import org.eclipse.papyrus.infra.properties.ui.runtime.DisplayEngine;
import org.eclipse.papyrus.infra.properties.ui.runtime.PropertiesRuntime;
import org.eclipse.papyrus.infra.properties.ui.util.PropertiesDisplayHelper;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

/**
 * {@link WizardPage} for informations of a {@link PaletteConfiguration} Model.
 *
 */
public class PaletteConfigurationInformationPage extends WizardPage {

	/** The {@link PaletteConfiguration} model */
	private PaletteConfiguration paletteConfiguration;

	/** The display engine */
	private DisplayEngine displayEngine;

	/**
	 * Constructor.
	 */
	protected PaletteConfigurationInformationPage(final String pageName, final PaletteConfiguration paletteConfiguration) {
		super(pageName);
		this.paletteConfiguration = paletteConfiguration;

		// install adapter
		AdapterImpl adapter = new AdapterImpl() {

			/**
			 * @see org.eclipse.emf.common.notify.impl.AdapterImpl#notifyChanged(org.eclipse.emf.common.notify.Notification)
			 */
			@Override
			public void notifyChanged(final Notification msg) {
				setPageComplete(validatePage());
			}
		};
		paletteConfiguration.eAdapters().add(adapter);
		adapter.setTarget(paletteConfiguration);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		// initialize dialog units
		initializeDialogUnits(parent);

		Composite control = new Composite(parent, SWT.NONE);
		Layout layout = new FillLayout();
		control.setLayout(layout);
		setControl(control);

		// Create the composite
		if (!PropertiesRuntime.getConstraintEngine().getDisplayUnits(paletteConfiguration).isEmpty()) {
			displayEngine = PropertiesDisplayHelper.display(paletteConfiguration, control);
		}
		setPageComplete(validatePage());
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#dispose()
	 */
	@Override
	public void dispose() {
		if (null != displayEngine) {
			displayEngine.dispose();
		}
		super.dispose();
	}


	/**
	 * Validate the paletteConfiguration properties. paletteConfiguration must at leazst have a name and an id.
	 */
	protected boolean validatePage() {
		return null != paletteConfiguration
				&& null != paletteConfiguration.getLabel()
				&& null != paletteConfiguration.getId()
				&& !paletteConfiguration.getLabel().isEmpty()
				&& !paletteConfiguration.getId().isEmpty();
	}

}
