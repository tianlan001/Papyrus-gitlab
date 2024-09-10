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
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Dialog to ask the user if he wants to modify values which was the deleted EnumerationLiteral
 */
public class DeleteEnumerationLiteralFromEnumerationDialog extends AbstractTabsProfileMigrationDialog {

	private static final String DIALOG_TITLE = Messages.DeleteEnumerationLiteralFromEnumerationDialog_Title;

	private Enumeration enumeration;

	private EnumerationLiteral enumerationLiteral;

	/**
	 * Constructor.
	 *
	 * @param shell
	 *            the active shell
	 * @param enumeration
	 *            the Enumeration owning the deleted EnumerationLiteral
	 * @param enumerationLiteral
	 *            the deleted EnumerationLiteral
	 * @param mapElementToStereotype
	 *            the map of element with every stereotype application of stereotypes owning a property type by the Enumeration (and sub stereotypes)
	 */
	public DeleteEnumerationLiteralFromEnumerationDialog(Shell shell, Enumeration enumeration, EnumerationLiteral enumerationLiteral, Map<Element, List<Stereotype>> mapElementToStereotype) {
		super(shell, DIALOG_TITLE, ProfileMigrationPreferenceConstants.DELETE_ENUM_LITERAL_FROM_ENUMERATION, mapElementToStereotype);
		this.enumeration = enumeration;
		this.enumerationLiteral = enumerationLiteral;
	}

	@Override
	protected String getDecription() {
		return NLS.bind(Messages.DeleteEnumerationLiteralFromEnumerationDialog_Description, enumerationLiteral.getName(), enumeration.getName());
	}

	@Override
	protected void getMigrationActionTabContent(Composite body, Element element, Stereotype stereotype) {
		for (Property property : stereotype.getAllAttributes()) {
			if (property.getType() == enumeration) {
				EStructuralFeatureEditor editor = new EStructuralFeatureEditor(body, 0);
				EStructuralFeatureUtil.setValueToEditor(editor, element, property, stereotype);
			}
		}
	}

}
