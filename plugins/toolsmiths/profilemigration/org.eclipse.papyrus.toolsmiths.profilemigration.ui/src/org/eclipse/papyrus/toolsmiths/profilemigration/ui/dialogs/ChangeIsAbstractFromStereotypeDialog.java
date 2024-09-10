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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Messages;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.preferences.ProfileMigrationPreferenceConstants;
import org.eclipse.papyrus.uml.tools.providers.UMLLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Dialog to ask the user which sub stereotype he want to apply instead of the newly abstract stereotype
 */
public class ChangeIsAbstractFromStereotypeDialog extends AbstractTabsProfileMigrationDialog {

	private Stereotype abstractStereotype;

	private List<Stereotype> subStereotypes;

	private Map<Element, List<Stereotype>> result = new HashMap<>();

	private Map<Element, TableViewer> tableViewer = new HashMap<>();;

	/**
	 * Constructor
	 * 
	 * @param shell
	 *            the active shell
	 * @param abstractStereotype
	 *            the stereotype becoming abstract
	 * @param subStereotype
	 *            the list of stereotype generalizing the abstractStereotype
	 * @param mapElementsToStereotpyes
	 *            the map of element with every stereotype application of the stereotypes which has become abstract (and sub stereotypes)
	 */
	public ChangeIsAbstractFromStereotypeDialog(Shell shell, Stereotype abstractStereotype, List<Stereotype> subStereotype, Map<Element, List<Stereotype>> mapElementsToStereotpyes) {
		super(shell, Messages.ChangeIsAbstractFromStereotypeDialog_Title, ProfileMigrationPreferenceConstants.SUPER_STEREOTYPE_BECOMING_ABSTRACT, mapElementsToStereotpyes);
		this.abstractStereotype = abstractStereotype;
		this.subStereotypes = subStereotype;
	}

	/**
	 * @see org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs.AbstractProfileMigrationDialog#getDecription()
	 */
	@Override
	protected String getDecription() {
		return NLS.bind(Messages.ChangeIsAbstractFromStereotypeDialog_Description, abstractStereotype.getName());
	}

	@Override
	protected void getMigrationActionTabContent(Composite body, Element element, Stereotype stereotype) {
		TableViewer tv = new TableViewer(body, SWT.CHECK);
		tv.setContentProvider(new IStructuredContentProvider() {
			/**
			 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
			 *
			 * @param inputElement
			 * @return the array of sub stereotype
			 */
			@Override
			public Object[] getElements(Object inputElement) {
				@SuppressWarnings("unchecked")
				List<Stereotype> v = (List<Stereotype>) inputElement;
				return v.toArray();
			}
		});
		tv.setLabelProvider(new UMLLabelProvider());
		tv.setInput(subStereotypes);
		tableViewer.put(element, tv);
	}

	@Override
	protected void okPressed() {
		for (Entry<Element, TableViewer> entry : tableViewer.entrySet()) {
			TableItem items[] = entry.getValue().getTable().getItems();
			List<Stereotype> checkedStereotypes = new ArrayList<>();
			for (TableItem item : items) {
				if (item.getChecked()) {
					checkedStereotypes.add((Stereotype) item.getData());
				}
			}
			result.put(entry.getKey(), checkedStereotypes);
		}
		super.okPressed();
	}

	/**
	 * @return The map linking the element to the list of stereotypes which should be applied on it
	 */
	public Map<Element, List<Stereotype>> getResult() {
		return result;
	}
}
