/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.customization.nattableconfiguration.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.common.ui.celleditor.ExtendedComboBoxCellEditor;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.papyrus.customization.nattableconfiguration.helper.TableConfigurationHelper;
import org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NameSimplifier;
import org.eclipse.papyrus.customization.nattableconfiguration.utils.NattableConfigurationEditingSupport;
import org.eclipse.papyrus.infra.nattable.manager.axis.AxisManagerFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisManagerRepresentation;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TableHeaderAxisConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Table;

/**
 * The column axis header configuration wizard page which allow to define the axis managers, the label provider configurations (hidden for user).
 */
public class ColumnConfigurationWizardPage extends AbstractAxisConfigurationWizardPage {

	/**
	 * The axis manager proposed to the user(even if the registered axis managers in plugins are not correctly managed).
	 */
	protected static final List<String> requiredProposedAxisManagers = new ArrayList<String>();

	/**
	 * The label provider context proposed to the user(even if the registered axis managers in plugins are not correctly managed).
	 */
	protected static final List<String> requiredProposedLabelProviderContexts = new ArrayList<String>();

	/**
	 * Initialize the previous map.
	 */
	static {
		requiredProposedAxisManagers.add("EMF Feature (org.eclipse.papyrus.infra.emf.nattable.feature.axis.manager)"); //$NON-NLS-1$
		requiredProposedAxisManagers.add("EMF Operation (org.eclipse.papyrus.infra.emf.nattable.operation.axis.manager)"); //$NON-NLS-1$
		requiredProposedAxisManagers.add("UML Element (org.eclipse.papyrus.uml.nattable.element.axis.manager)"); //$NON-NLS-1$
		requiredProposedAxisManagers.add("UML Feature (org.eclipse.papyrus.uml.nattable.feature.axis.manager)"); //$NON-NLS-1$
		requiredProposedAxisManagers.add("UML Operation (org.eclipse.papyrus.uml.nattable.operation.axis.manager)"); //$NON-NLS-1$

		requiredProposedLabelProviderContexts.add("Header"); //$NON-NLS-1$
		requiredProposedLabelProviderContexts.add("Header Feature"); //$NON-NLS-1$
		requiredProposedLabelProviderContexts.add("Header Operation"); //$NON-NLS-1$
	}


	/**
	 * Constructor.
	 *
	 * @param helper
	 *            The table configuration helper.
	 */
	public ColumnConfigurationWizardPage(final TableConfigurationHelper helper) {
		super(Messages.ColumnConfigurationWizardPage_pageName, helper);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.pages.AbstractAxisConfigurationWizardPage#getHeaderAxisConfiguration()
	 */
	@Override
	public TableHeaderAxisConfiguration getHeaderAxisConfiguration() {
		return helper.getTableConfiguration().getColumnHeaderAxisConfiguration();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.pages.AbstractAxisConfigurationWizardPage#createAxisManagerRepresentation()
	 */
	@Override
	public AxisManagerRepresentation createAxisManagerRepresentation() {
		final AxisManagerRepresentation createdRepresentation = super.createAxisManagerRepresentation();
		createdRepresentation.setAxisManagerId("org.eclipse.papyrus.uml.nattable.element.axis.manager"); //$NON-NLS-1$

		// Manage the label provider configuration and the label provider context depending to the axis manager
		manageLabelProviderConfiguration(createdRepresentation);
		manageCorrespondenceAxisManagerAndContext(createdRepresentation);

		return createdRepresentation;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.customization.nattableconfiguration.pages.AbstractAxisConfigurationWizardPage#createColumns(org.eclipse.jface.viewers.TableViewer)
	 */
	@Override
	protected void createAxisManagersColumns(final TableViewer tableViewer) {

		final Table table = tableViewer.getTable();
		final Collection<String> knownAxis = AxisManagerFactory.INSTANCE.getAllRegisteredAxisManager().keySet();

		// Define the titles and bounds of each columns
		final int[] bounds = { 500, 500 };
		final String[] titles = { Messages.ConfigurationWizardPage_axisManagerIdColumnName, Messages.ConfigurationWizardPage_labelProviderContextColumnName };

		// Create the first column for the axis manager id
		final TableViewerColumn axisManagerIdColumn = createTableViewerColumn(tableViewer, titles[0], bounds[0]);
		final ColumnLabelProvider axisManagerIdLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				final StringBuilder value = new StringBuilder();
				if (element instanceof AxisManagerRepresentation) {
					final String axisManagerId = ((AxisManagerRepresentation) element).getAxisManagerId();
					boolean axisManagerFound = NameSimplifier.axisManagerNames.containsKey(axisManagerId);
					if (axisManagerFound) {
						value.append(NameSimplifier.axisManagerNames.get(axisManagerId));
					} else {
						value.append(null != axisManagerId ? axisManagerId : ""); //$NON-NLS-1$
					}
				}
				return value.toString();
			}
		};
		axisManagerIdColumn.setLabelProvider(axisManagerIdLabelProvider);
		// Create the combo box of the axis manager identifier
		axisManagerIdColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, axisManagerIdLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof AxisManagerRepresentation) {
					// set the new value
					if (NameSimplifier.axisManagerNames.containsValue(value)) {
						for (final Entry<String, String> entry : NameSimplifier.axisManagerNames.entrySet()) {
							if (((String) value).contains(entry.getValue()) && ((String) value).contains(entry.getKey())) {
								((AxisManagerRepresentation) element).setAxisManagerId(entry.getKey());
							}
						}
					} else {
						((AxisManagerRepresentation) element).setAxisManagerId((String) value);
					}

					// Manage the label provider configuration and the label provider context depending to the axis manager
					manageLabelProviderConfiguration((AxisManagerRepresentation) element);
					manageCorrespondenceAxisManagerAndContext((AxisManagerRepresentation) element);

					tableViewer.refresh();
					setPageComplete(isPageComplete());
				}
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new ExtendedComboBoxCellEditor(table, createAxisManagerIdItems(requiredProposedAxisManagers, knownAxis), new LabelProvider(), SWT.NONE) {

					@Override
					public Object doGetValue() {
						// Redefine this to allow other value than the proposed ones from the combo
						if (getControl() instanceof CCombo) {
							return ((CCombo) getControl()).getText();
						}
						return super.doGetValue();
					}
				};
			}
		});

		// Create the second column for the label provider context
		final TableViewerColumn labelProviderContextColumn = createTableViewerColumn(tableViewer, titles[1], bounds[1]);
		final ColumnLabelProvider labelProviderContextLabelProvider = new ColumnLabelProvider() {

			@Override
			public String getText(final Object element) {
				String result = ""; //$NON-NLS-1$
				if (element instanceof AxisManagerRepresentation) {
					if (NameSimplifier.labelProviderContextNames.containsKey(((AxisManagerRepresentation) element).getLabelProviderContext())) {
						result = NameSimplifier.labelProviderContextNames.get(((AxisManagerRepresentation) element).getLabelProviderContext());
					} else {
						result = ((AxisManagerRepresentation) element).getLabelProviderContext();
					}
				}
				return result;
			}
		};
		labelProviderContextColumn.setLabelProvider(labelProviderContextLabelProvider);
		// Create the combo box of the label provider contexts
		labelProviderContextColumn.setEditingSupport(new NattableConfigurationEditingSupport(tableViewer, labelProviderContextLabelProvider) {

			@Override
			protected void setValue(final Object element, final Object value) {
				if (element instanceof AxisManagerRepresentation) {
					// set the new value
					if (NameSimplifier.labelProviderContextNames.containsValue(value)) {

						for (final Entry<String, String> entry : NameSimplifier.labelProviderContextNames.entrySet()) {
							if (entry.getValue().equals(value)) {
								((AxisManagerRepresentation) element).setLabelProviderContext(entry.getKey());
							}
						}

						tableViewer.refresh();
						setPageComplete(isPageComplete());
					}
				}
			}

			@Override
			protected CellEditor getCellEditor(final Object element) {
				return new ExtendedComboBoxCellEditor(table, requiredProposedLabelProviderContexts, new LabelProvider(), SWT.NONE);
			}
		});
	}

	/**
	 * This allows to change the label provider context corresponding to the axis manager id set.
	 * 
	 * @param element
	 *            The current axis manager representation.
	 */
	protected void manageCorrespondenceAxisManagerAndContext(final AxisManagerRepresentation element) {
		if (NameSimplifier.correspondenceAxisManagerAndContext.containsKey(element.getAxisManagerId())) {
			element.setLabelProviderContext(NameSimplifier.correspondenceAxisManagerAndContext.get(element.getAxisManagerId()));
		}
	}
}
