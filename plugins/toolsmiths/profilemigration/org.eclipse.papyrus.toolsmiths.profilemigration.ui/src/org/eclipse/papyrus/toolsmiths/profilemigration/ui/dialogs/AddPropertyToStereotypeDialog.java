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
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.utils.EStructuralFeatureUtil;
import org.eclipse.papyrus.uml.properties.widgets.EStructuralFeatureEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Dialog to ask the user to add value to slot corresponding to newly add property
 */
public class AddPropertyToStereotypeDialog extends AbstractTabsProfileMigrationDialog {

	private static final String DIALOG_TITLE = Messages.AddPropertyToStereotypeDialog_Title;

	private Stereotype stereotype;

	private Property property;

	/**
	 * Constructor.
	 *
	 * @param shell
	 *            the active shell
	 * @param stereotype
	 *            the stereotype owning the new property
	 * @param property
	 *            the newly added property
	 * @param mapElementToStereotype
	 *            the map of element with every stereotype application of stereotypes owning the new property (and sub stereotypes)
	 */
	public AddPropertyToStereotypeDialog(Shell shell, Stereotype stereotype, Property property, Map<Element, List<Stereotype>> mapElementToStereotype) {
		super(shell, DIALOG_TITLE, ProfileMigrationPreferenceConstants.ADD_PROPERTY_TO_STEREOTYPE, mapElementToStereotype);
		this.stereotype = stereotype;
		this.property = property;
	}

	@Override
	protected String getDecription() {
		return NLS.bind(Messages.AddPropertyToStereotypeDialog_Description,
				new Object[] { property.getName(), stereotype.getName(), property.getLower(), property.getUpper() });
	}

	@Override
	protected void getMigrationActionTabContent(Composite body, Element element, Stereotype stereotype) {
		EStructuralFeatureEditor editor = new EStructuralFeatureEditor(body, 0);
		EStructuralFeatureUtil.setValueToEditor(editor, element, property, stereotype);
	}

}
