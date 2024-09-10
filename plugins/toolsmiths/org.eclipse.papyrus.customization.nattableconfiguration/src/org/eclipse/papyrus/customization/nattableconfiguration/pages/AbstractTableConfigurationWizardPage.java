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
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.customization.nattableconfiguration.pages;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.customization.nattableconfiguration.helper.TableConfigurationHelper;

/**
 * The abstract wizard page for the table configuration.
 */
public abstract class AbstractTableConfigurationWizardPage extends WizardPage {

	/**
	 * The table configuration helper
	 */
	protected final TableConfigurationHelper helper;

	/**
	 * Constructor.
	 *
	 * @param pageName
	 *            The page name.
	 * @param helper
	 *            The table configuration helper.
	 */
	public AbstractTableConfigurationWizardPage(final String pageName, final TableConfigurationHelper helper) {
		super(pageName, pageName, null);
		this.helper = helper;
	}
}
