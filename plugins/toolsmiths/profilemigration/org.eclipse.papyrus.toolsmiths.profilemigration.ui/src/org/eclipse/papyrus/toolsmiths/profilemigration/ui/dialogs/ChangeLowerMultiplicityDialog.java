/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
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
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs;

import java.util.List;
import java.util.Map;

import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Messages;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Dialog to ask the user to modify the number of values to match with the new multiplicity
 */
public class ChangeLowerMultiplicityDialog extends AbstractChangeMultiplicityDialog {

	/**
	 * The title of the dialog
	 */
	private static final String DIALOG_TITLE = Messages.ChangeLowerMultiplicityDialog_Title;

	/**
	 * Constructor.
	 *
	 * @param shell
	 *            the active shell
	 * @param property
	 *            the property whose multiplicity change
	 * @param oldValue
	 *            the old value of the modified multiplicity
	 * @param newValue
	 *            the new value of the modified multiplicity
	 * @param mapElementToStereotype
	 *            the map of element with every stereotype application of stereotypes owning the modified property (and sub stereotypes)
	 */
	public ChangeLowerMultiplicityDialog(Shell shell, Property property, int oldValue, int newValue, Map<Element, List<Stereotype>> mapElementToStereotype) {
		super(shell, DIALOG_TITLE, ProfileMigrationPreferenceConstants.CHANGE_LOWER_MULTIPLICITY_PROPERTY, property, oldValue, newValue, mapElementToStereotype);
	}

	@Override
	protected String getDecription() {
		return NLS.bind(Messages.ChangeLowerMultiplicityDialog_Description, new Object[] { property.getName(), ((NamedElement) property.getOwner()).getName(), oldValue == -1 ? "*" : oldValue, newValue == -1 ? "*" : newValue }); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
