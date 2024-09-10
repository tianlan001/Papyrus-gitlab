/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.uml.extensionpoints.metamodel;

import java.util.Collection;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.papyrus.uml.extensionpoints.Activator;
import org.eclipse.papyrus.uml.extensionpoints.standard.FilteredRegisteredElementsSelectionDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Shows a list of registered metamodels to the user with a text entry field for a string pattern
 * used to filter the list of resources.
 *
 * @since 1.9.0
 */
public class FilteredRegisteredMetamodelsSelectionDialog extends FilteredRegisteredElementsSelectionDialog {

	/** ID for this dialog preferences section */
	protected static final String DIALOG_SETTINGS = Activator.PLUGIN_ID + "dialogs.registeredmetamodels";

	/**
	 * Creates a new instance of the class
	 *
	 * @param shell
	 *            the parent shell
	 * @param multi
	 *            the multiple selection flag
	 * @param input
	 *            the input in which selection is done
	 * @param alreadySelected
	 *            list of already selected items
	 */
	public FilteredRegisteredMetamodelsSelectionDialog(Shell shell, boolean multi, Object[] input, Collection alreadySelected) {
		super(shell, multi, input, alreadySelected, "Metamodels to import: ", "Metamodels already imported: ");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected IDialogSettings getDialogSettings() {
		IDialogSettings settings = Activator.getDefault().getDialogSettings().getSection(DIALOG_SETTINGS);
		if (settings == null) {
			settings = Activator.getDefault().getDialogSettings().addNewSection(DIALOG_SETTINGS);
		}
		return settings;
	}
}
