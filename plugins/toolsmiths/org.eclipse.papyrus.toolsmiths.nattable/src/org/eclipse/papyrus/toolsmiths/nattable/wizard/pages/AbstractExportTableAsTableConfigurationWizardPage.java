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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.nattable.wizard.pages;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;

/**
 *
 * Common abstract class for the Export Table as Table Configuration wizard
 */
public abstract class AbstractExportTableAsTableConfigurationWizardPage extends WizardPage {

	/**
	 * the table which is exported as table configuration
	 */
	private Table tableToExportAsTableConfiguration;

	/**
	 *
	 * Constructor.
	 *
	 * @param pageName
	 *            the wizard page name
	 * @param title
	 *            the title of the page
	 * @param titleImage
	 *            the image displayed in the title for the wizard page
	 */
	public AbstractExportTableAsTableConfigurationWizardPage(final String pageName, final String title, final ImageDescriptor titleImage) {
		super(pageName, title, titleImage);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param pageName
	 *            the wizard page name
	 */
	public AbstractExportTableAsTableConfigurationWizardPage(final String pageName) {
		this(pageName, null, (ImageDescriptor) null);
	}

	/**
	 *
	 * @param exportedTable
	 *            the table to export. This value can't be <code>null</code>
	 */
	public void setExportedTable(final Table exportedTable) {
		Assert.isNotNull(exportedTable, "The table to export can't be null"); //$NON-NLS-1$
		this.tableToExportAsTableConfiguration = exportedTable;
	}

	/**
	 *
	 * @return
	 *         the exported table
	 */
	final Table getExportedTable() {
		return this.tableToExportAsTableConfiguration;
	}

}
