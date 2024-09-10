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

import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Abstract dialog with the main structure for a dialog using tabs to display multiple stereotype application
 * 
 * ---------------------
 * tabs for each element owning a stereotype application concerning by the migration
 * ---------------------
 * tabs for each stereotype applied is the element concerning by the migration (if there is more than one)
 * ---------------------
 */
public abstract class AbstractTabsProfileMigrationDialog extends AbstractProfileMigrationDialog {

	/**
	 * The map of element with every stereotype application of stereotypes concerning by the modification
	 */
	protected Map<Element, List<Stereotype>> mapElementToStereotype;

	/**
	 * Constructor.
	 *
	 * @param shell
	 *            the shell
	 * @param title
	 *            the title of the dialog
	 * @param prefConst
	 *            the preference constant which correspond to this dialog display
	 * @param mapElementToStereotype
	 *            the map of element with every stereotype application of stereotypes concerning by the modification
	 */
	public AbstractTabsProfileMigrationDialog(Shell shell, String title, String prefConst, Map<Element, List<Stereotype>> mapElementToStereotype) {
		super(shell, title, prefConst);
		this.mapElementToStereotype = mapElementToStereotype;
	}

	@Override
	protected void getMigrationActionSectionContent(Composite body, FormToolkit toolkit) {
		TabFolder tabFolder = new TabFolder(body, SWT.NONE);
		UMLLabelProvider labelProvider = new UMLLabelProvider();
		for (Element element : mapElementToStereotype.keySet()) {
			TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
			tabItem.setText(labelProvider.getText(element));
			tabItem.setControl(getTabElementControl(element, tabFolder));
		}
	}

	private Control getTabElementControl(Element element, TabFolder tabFolder) {
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		// StructuralFeatureEditor initialization
		if (!mapElementToStereotype.isEmpty()) {
			List<Stereotype> stereotypes = mapElementToStereotype.get(element);
			if (stereotypes.size() == 1) {
				getMigrationActionTabContent(composite, element, stereotypes.get(0));
			} else if (stereotypes.size() > 1) {
				TabFolder tabFolder2 = new TabFolder(composite, SWT.NONE);
				UMLLabelProvider labelProvider = new UMLLabelProvider();
				for (Stereotype stereotype : stereotypes) {
					TabItem tabItem = new TabItem(tabFolder2, SWT.NONE);
					tabItem.setText(labelProvider.getText(stereotype));
					tabItem.setControl(getTabControlStereotype(element, stereotype, tabFolder2));
				}
			}
		}
		return composite;
	}

	private Control getTabControlStereotype(Element element, Stereotype stereotype, TabFolder tabFolder2) {
		Composite composite = new Composite(tabFolder2, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));
		getMigrationActionTabContent(composite, element, stereotype);
		return composite;
	}

	/**
	 * Create the content for each tab
	 * 
	 * @param body
	 *            the body
	 * @param element
	 *            the element owning the stereotype application
	 * @param stereotype
	 *            the stereotype applied
	 */
	protected abstract void getMigrationActionTabContent(Composite body, Element element, Stereotype stereotype);

}
