/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 482443
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.common.wizards;

import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.papyrus.infra.nattable.common.messages.Messages;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;

/**
 * Wizard declaration to display and choose existing Nattable configurations
 */
public class CreateNattableFromCatalogWizard extends Wizard {

	/**
	 * The page which allow to select the table to create.
	 */
	private ChooseNattableConfigWizardPage page;

	/**
	 * The selected view prototypes in the wizard.
	 */
	private Map<ViewPrototype, Integer> selectedViewPrototypes;

	/**
	 * The table name by view prototype.
	 */
	private Map<ViewPrototype, String> tableNames;

	/**
	 * The context of the future table.
	 */
	private EObject context;

	/**
	 * Constructor.
	 *
	 * @param context
	 *            The context of the table to create
	 */
	public CreateNattableFromCatalogWizard(final EObject context) {
		this.context = context;
	}

	/**
	 * Enables the finish button when there's at least one selected configuration.
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		this.selectedViewPrototypes = page.getSelectedViewPrototypes();
		this.tableNames = page.getTableNames();
		if (this.selectedViewPrototypes != null && this.tableNames != null) {
			if (this.selectedViewPrototypes.size() > 0) {

				return true;
			}
		}
		return false;
	}

	/**
	 * Create and add the unique page to the wizard
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 *
	 */
	@Override
	public void addPages() {
		String description = Messages.CreateNattableFromCatalogWizard_0;
		page = new ChooseNattableConfigWizardPage(description, context);
		page.setTitle(Messages.CreateNattableFromCatalogWizard_1);
		page.setDescription(description);
		addPage(page);
	}

	/**
	 * Declare wizard title
	 *
	 * @see org.eclipse.jface.wizard.Wizard#getWindowTitle()
	 *
	 * @return
	 */
	@Override
	public String getWindowTitle() {
		return Messages.CreateNattableFromCatalogWizard_2;
	}

	/**
	 * Getter for selected view prototypes.
	 *
	 * @return The selected view prototypes.
	 */
	public Map<ViewPrototype, Integer> getSelectedViewPrototypes() {
		return selectedViewPrototypes;
	}


	/**
	 * Getter for the selected table names.
	 *
	 * @return The selected table names.
	 */
	public Map<ViewPrototype, String> getTableNames() {
		return tableNames;
	}

}
