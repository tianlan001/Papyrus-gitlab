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

import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Messages;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.utils.EStructuralFeatureUtil;
import org.eclipse.papyrus.uml.properties.widgets.EStructuralFeatureEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Dialog to ask the user to select the static value (common to every slot corresponding to the property)
 */
public class ChangeIsStaticFeatureDialog extends AbstractProfileMigrationDialog {

	private static final String DIALOG_TITLE = Messages.ChangeIsStaticFeatureDialog_Title;

	private Stereotype stereotype;

	private Property property;

	private Element element;

	private EStructuralFeatureEditor editor;

	/**
	 * Constructor.
	 *
	 * @param shell
	 *            the active shell
	 * @param stereotype
	 *            the owner of the property
	 * @param property
	 *            the modified property
	 * @param element
	 *            one element owning the stereotype
	 */
	public ChangeIsStaticFeatureDialog(Shell shell, Stereotype stereotype, Property property, Element element) {
		super(shell, DIALOG_TITLE, ProfileMigrationPreferenceConstants.CHANGE_IS_STATIC_FEATURE_PROPERTY);
		this.stereotype = stereotype;
		this.property = property;
		this.element = element;
	}

	@Override
	protected String getDecription() {
		return NLS.bind(Messages.ChangeIsStaticFeatureDialog_Description, property.getName(), stereotype.getName());
	}

	@Override
	protected void getMigrationActionSectionContent(Composite body, FormToolkit toolkit) {
		editor = new EStructuralFeatureEditor(body, 0);
		EStructuralFeatureUtil.setValueToEditor(editor, element, property, stereotype);
	}

}
