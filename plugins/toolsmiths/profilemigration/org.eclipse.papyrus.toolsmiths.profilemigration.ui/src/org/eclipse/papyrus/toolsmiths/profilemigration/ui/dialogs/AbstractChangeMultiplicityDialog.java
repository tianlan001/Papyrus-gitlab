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

import org.eclipse.papyrus.toolsmiths.profilemigration.ui.utils.EStructuralFeatureUtil;
import org.eclipse.papyrus.uml.properties.widgets.EStructuralFeatureEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Abstract dialog to ask the user to add or remove value to slots to match with a new multiplicity
 */
public abstract class AbstractChangeMultiplicityDialog extends AbstractTabsProfileMigrationDialog {

	/**
	 * the property whose multiplicity change
	 */
	protected Property property;

	/**
	 * the old value of the modified multiplicity
	 */
	protected int oldValue;

	/**
	 * the new value of the modified multiplicity
	 */
	protected int newValue;

	/**
	 * Constructor.
	 *
	 * @param shell
	 *            the active shell
	 * @param title
	 *            the title of the dialog
	 * @param prefConst
	 *            the preference constant (show or not the dialog)
	 * @param property
	 *            the property whose multiplicity change
	 * @param oldValue
	 *            the old value of the modified multiplicity
	 * @param newValue
	 *            the new value of the modified multiplicity
	 * @param mapElementToStereotype
	 *            the map of element with every stereotype application of stereotypes concerning by the modification
	 */
	public AbstractChangeMultiplicityDialog(Shell shell, String title, String prefConst, Property property, int oldValue, int newValue, Map<Element, List<Stereotype>> mapElementToStereotype) {
		super(shell, title, prefConst, mapElementToStereotype);
		this.property = property;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.mapElementToStereotype = mapElementToStereotype;
	}

	/**
	 * @see org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.AbstractTabsProfileMigrationDialog#getMigrationActionTabContent(org.eclipse.swt.widgets.Composite, org.eclipse.uml2.uml.Element, org.eclipse.uml2.uml.Stereotype)
	 *
	 * @param body
	 * @param element
	 * @param stereotype
	 */
	@Override
	protected void getMigrationActionTabContent(Composite body, Element element, Stereotype stereotype) {
		EStructuralFeatureEditor editor = new EStructuralFeatureEditor(body, SWT.NONE);
		EStructuralFeatureUtil.setValueToEditor(editor, element, property, stereotype);
	}

}
