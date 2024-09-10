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
 *   Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr - Bug 493756
 *   
 *****************************************************************************/

package org.eclipse.papyrus.customization.nattableconfiguration.pages;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.papyrus.customization.nattableconfiguration.helper.TableConfigurationHelper;
import org.eclipse.papyrus.customization.nattableconfiguration.messages.Messages;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.AxisIndexStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.TableHeaderAxisConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.AbstractAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.MasterObjectAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.NattableaxisproviderFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisprovider.SlaveObjectAxisProvider;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.DisplayStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestyleFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.NattablestylePackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablestyle.TableDisplayStyle;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattabletester.JavaTableTester;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattabletester.NattabletesterFactory;
import org.eclipse.papyrus.infra.widgets.editors.BrowseFileEditor;
import org.eclipse.papyrus.infra.widgets.providers.FileExtensions;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * This page allows to edit the name, type and icon of a table
 */
public class EditGenericNattableConfigurationFieldsNattableWizardPage extends AbstractTableConfigurationWizardPage {

	/**
	 * The current configuration to modify.
	 */
	private final TableConfiguration configuration;

	/**
	 * Constructor.
	 *
	 * @param helper
	 *            The table configuration helper.
	 */
	public EditGenericNattableConfigurationFieldsNattableWizardPage(final TableConfigurationHelper helper) {
		super(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_pageName, helper);
		configuration = helper.getTableConfiguration();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(final Composite parent) {
		setPageComplete(isPageComplete());
		final Composite container = new Composite(parent, SWT.BORDER);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		// Create the table group and its grid data
		final Group tableGroup = new Group(container, SWT.NONE);
		tableGroup.setLayout(new GridLayout(2, false));
		tableGroup.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_tableConfigurationLabel);
		final GridData tableGroupData = new GridData(SWT.FILL, SWT.BEGINNING, true, false);
		tableGroupData.horizontalSpan = 2;
		tableGroup.setLayoutData(tableGroupData);

		// Create the default name text of the table
		final Label labelDefaultName = new Label(tableGroup, SWT.NONE);
		labelDefaultName.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_defaultTableNameLabel);
		labelDefaultName.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Text defaultName = new Text(tableGroup, SWT.BORDER);
		defaultName.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				if (!helper.getDefaultTableName().equals(defaultName.getText())) {
					helper.setDefaultTableName(defaultName.getText());
				}
				setPageComplete(isPageComplete());
			}
		});
		final GridData defaultNameLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		defaultNameLayoutData.minimumWidth = 200;
		defaultName.setLayoutData(defaultNameLayoutData);

		// Create the type text of the table
		final Label labelType = new Label(tableGroup, SWT.NONE);
		labelType.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_tableTypeLabel);
		labelType.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Text type = new Text(tableGroup, SWT.BORDER);
		type.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				if (!helper.getTableType().equals(type.getText())) {
					helper.setTableType(type.getText());
				}
				setPageComplete(isPageComplete());
			}
		});
		final GridData typeLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		typeLayoutData.minimumWidth = 200;
		type.setLayoutData(typeLayoutData);

		// Create the description text of the table
		final Label labelDescription = new Label(tableGroup, SWT.NONE);
		labelDescription.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_descriptionLabel);
		labelDescription.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Text description = new Text(tableGroup, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		description.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				if (!helper.getTableConfigurationDescription().equals(description.getText())) {
					helper.setTableConfigurationDescription(description.getText());
				}
			}
		});
		final GridData descriptionLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		descriptionLayoutData.minimumWidth = 400;
		descriptionLayoutData.heightHint = 50;
		description.setLayoutData(descriptionLayoutData);

		// Create the icon browse editor for the icon of the table
		final BrowseFileEditor iconEditor = new BrowseFileEditor(tableGroup, SWT.NONE);
		iconEditor.setLabel(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_iconToUseLabel);
		iconEditor.setAllowFileSystem(false);
		iconEditor.setAllowWorkspace(true);

		// Get file extensions
		final Set<String> extensions = FileExtensions.imagesFilesExtensions.keySet();
		String[] str1 = extensions.toArray(new String[extensions.size()]);

		final Collection<String> extension2 = FileExtensions.imagesFilesExtensions.values();
		String[] str2 = extension2.toArray(new String[extensions.size()]);

		iconEditor.setFilters(str1, str2);

		iconEditor.getText().setEnabled(false);
		iconEditor.getText().addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				if (!configuration.getIconPath().equals(iconEditor.getText().getText())) {
					helper.setTableIcon(iconEditor.getText().getText());
				}
			}
		});
		final GridData iconGridData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		iconGridData.horizontalSpan = 2;
		iconEditor.setLayoutData(iconGridData);

		// Create the combo for the table style (flat or tree)
		final Label labelAxisKind = new Label(tableGroup, SWT.NONE);
		labelAxisKind.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_axisKindLabel);
		final Composite styleComposite = new Composite(tableGroup, SWT.NONE);
		final GridLayout styleCompositeLayoutData = new GridLayout(2, false);
		styleCompositeLayoutData.marginWidth = 0;
		styleCompositeLayoutData.marginHeight = 0;
		styleComposite.setLayout(styleCompositeLayoutData);
		styleComposite.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false));
		final Combo axisKindCombo = new Combo(styleComposite, SWT.NONE);
		final List<String> axisKindStyleList = new ArrayList<String>();
		axisKindStyleList.add(TableConfigurationHelper.AXIS_FLAT_STYLE);
		axisKindStyleList.add(TableConfigurationHelper.AXIS_TREE_STYLE);
		axisKindCombo.setItems((String[]) axisKindStyleList.toArray(new String[axisKindStyleList.size()]));
		final GridData axisKindLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		axisKindLayoutData.minimumWidth = 100;
		axisKindCombo.setLayoutData(axisKindLayoutData);

		// Create the combo for the column header style when this is a tree table
		final Combo treeAxisStyleCombo = new Combo(styleComposite, SWT.NONE);
		final List<String> treeAxisStyleList = new ArrayList<String>();
		treeAxisStyleList.add("Single header column"); //$NON-NLS-1$
		treeAxisStyleList.add("Multiple header column"); //$NON-NLS-1$
		treeAxisStyleCombo.setItems((String[]) treeAxisStyleList.toArray(new String[treeAxisStyleList.size()]));
		final GridData treeAxisStyleLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		treeAxisStyleLayoutData.minimumWidth = 200;
		treeAxisStyleCombo.setLayoutData(treeAxisStyleLayoutData);
		treeAxisStyleCombo.setVisible(false);

		// Manage the modification of the axis kind combo
		axisKindCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				// if this is flat, remove the possible table display value and hide the tree axis display style combo
				if (0 == axisKindCombo.getSelectionIndex()) {
					treeAxisStyleCombo.setVisible(false);
					TableDisplayStyle displayStyle = (TableDisplayStyle) configuration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
					if (null != displayStyle) {
						configuration.getStyles().remove(displayStyle);
					}
				} else {
					// This is tree table, set visible the tree axis display style combo
					treeAxisStyleCombo.setVisible(true);
					treeAxisStyleCombo.select(0);
				}
			}
		});

		// Manage the modification of the tree axis display style combo
		treeAxisStyleCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				// Get the table display style (or create it if necessary)
				TableDisplayStyle displayStyle = (TableDisplayStyle) configuration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
				if (null == displayStyle) {
					displayStyle = NattablestyleFactory.eINSTANCE.createTableDisplayStyle();
					configuration.getStyles().add(displayStyle);
				}

				// Set the correct value
				if (0 == treeAxisStyleCombo.getSelectionIndex()) {
					displayStyle.setDisplayStyle(DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN);
				} else {
					displayStyle.setDisplayStyle(DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN);
				}
			}
		});

		// Create the description text of the table
		final Label labelJavaTableTester = new Label(tableGroup, SWT.NONE);
		labelJavaTableTester.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_javaTableTesterLabel);
		labelJavaTableTester.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Text javaTableTester = new Text(tableGroup, SWT.BORDER);
		javaTableTester.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				JavaTableTester tableTester = configuration.getCreationTester() instanceof JavaTableTester ? (JavaTableTester) configuration.getCreationTester() : null;

				if (null == tableTester) {
					tableTester = NattabletesterFactory.eINSTANCE.createJavaTableTester();
					configuration.setCreationTester(tableTester);
				}

				tableTester.setTester(javaTableTester.getText());
			}
		});
		final GridData javaTableTesterLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		javaTableTesterLayoutData.minimumWidth = 600;
		javaTableTester.setLayoutData(javaTableTesterLayoutData);

		// Initialize the fields
		defaultName.setText(helper.getDefaultTableName());
		type.setText(helper.getTableType());
		description.setText(helper.getTableConfigurationDescription());
		if (null != configuration.getIconPath()) {
			iconEditor.getText().setText(configuration.getIconPath());
		}
		// Select the correct combo item corresponding to the current configuration
		TableDisplayStyle displayStyle = (TableDisplayStyle) configuration.getStyle(NattablestylePackage.eINSTANCE.getTableDisplayStyle());
		if (null != displayStyle) {
			axisKindCombo.select(1);
			if (DisplayStyle.HIERARCHIC_SINGLE_TREE_COLUMN.equals(displayStyle.getDisplayStyle())) {
				treeAxisStyleCombo.select(0);
			} else if (DisplayStyle.HIERARCHIC_MULTI_TREE_COLUMN.equals(displayStyle.getDisplayStyle())) {
				treeAxisStyleCombo.select(1);
			}
		} else {
			axisKindCombo.select(0);
		}
		if (configuration.getCreationTester() instanceof JavaTableTester) {
			javaTableTester.setText(((JavaTableTester) configuration.getCreationTester()).getTester());
		}

		// Create the rows and columns groups
		createRowsGroup(tableGroup);
		createColumnsGroup(tableGroup);

		// Create the master and slave groups
		createMasterGroup(tableGroup);
		createSlaveGroup(tableGroup);

		setControl(container); // should be a container composite here!
	}

	/**
	 * This allows to create the rows group and its fields.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createRowsGroup(final Composite parent) {

		// Create the rows composite
		final Composite rowsGroup = createSection(parent, Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_rowsManagementLabel);
		rowsGroup.setLayout(new GridLayout(4, false));

		// Create the index style combo
		final Label labelIndexStyle = new Label(rowsGroup, SWT.NONE);
		labelIndexStyle.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_indexStyleLabel);
		labelIndexStyle.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Combo rowsIndexStyleCombo = new Combo(rowsGroup, SWT.NONE);
		final List<String> indexStyleList = new ArrayList<String>();
		indexStyleList.add(AxisIndexStyle.ALPHABETIC.getName());
		indexStyleList.add(AxisIndexStyle.NUMERIC.getName());
		rowsIndexStyleCombo.setItems((String[]) indexStyleList.toArray(new String[indexStyleList.size()]));
		final GridData rowsIndexStyleLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		rowsIndexStyleLayoutData.minimumWidth = 100;
		rowsIndexStyleCombo.setLayoutData(rowsIndexStyleLayoutData);
		rowsIndexStyleCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				TableHeaderAxisConfiguration rowHeaderAxisConfiguration = configuration.getRowHeaderAxisConfiguration();
				if (null == rowHeaderAxisConfiguration) {
					rowHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
					configuration.setRowHeaderAxisConfiguration(rowHeaderAxisConfiguration);
				}

				if (0 == rowsIndexStyleCombo.getSelectionIndex()) {
					rowHeaderAxisConfiguration.setIndexStyle(AxisIndexStyle.ALPHABETIC);
				} else {
					rowHeaderAxisConfiguration.setIndexStyle(AxisIndexStyle.NUMERIC);
				}
			}
		});

		// Create the display index checkbox
		final Label displayIndexLabel = new Label(rowsGroup, SWT.NONE);
		displayIndexLabel.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_displayIndexLabel);
		displayIndexLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Button displayIndexButton = new Button(rowsGroup, SWT.CHECK);
		displayIndexButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		displayIndexButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				TableHeaderAxisConfiguration rowHeaderAxisConfiguration = configuration.getRowHeaderAxisConfiguration();
				if (null == rowHeaderAxisConfiguration) {
					rowHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
					configuration.setRowHeaderAxisConfiguration(rowHeaderAxisConfiguration);
				}

				rowHeaderAxisConfiguration.setDisplayIndex(displayIndexButton.getSelection());
			}
		});

		// Create the display label checkbox
		final Label displayLabelLabel = new Label(rowsGroup, SWT.NONE);
		displayLabelLabel.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_displayLabelLabel);
		displayLabelLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Button displayLabelButton = new Button(rowsGroup, SWT.CHECK);
		displayLabelButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		displayLabelButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				TableHeaderAxisConfiguration rowHeaderAxisConfiguration = configuration.getRowHeaderAxisConfiguration();
				if (null == rowHeaderAxisConfiguration) {
					rowHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
					configuration.setRowHeaderAxisConfiguration(rowHeaderAxisConfiguration);
				}

				rowHeaderAxisConfiguration.setDisplayLabel(displayLabelButton.getSelection());
			}
		});

		// Create the display filter checkbox
		final Label displayFilterLabel = new Label(rowsGroup, SWT.NONE);
		displayFilterLabel.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_displayFilterLabel);
		displayFilterLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Button displayFilterButton = new Button(rowsGroup, SWT.CHECK);
		displayFilterButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		displayFilterButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				TableHeaderAxisConfiguration rowHeaderAxisConfiguration = configuration.getRowHeaderAxisConfiguration();
				if (null == rowHeaderAxisConfiguration) {
					rowHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
					configuration.setRowHeaderAxisConfiguration(rowHeaderAxisConfiguration);
				}

				rowHeaderAxisConfiguration.setDisplayFilter(displayFilterButton.getSelection());
			}
		});

		// Initialize the fields
		// Select the correct combo item corresponding to the current configuration
		TableHeaderAxisConfiguration rowHeaderAxisConfiguration = configuration.getRowHeaderAxisConfiguration();
		if (null != rowHeaderAxisConfiguration) {
			if (AxisIndexStyle.ALPHABETIC.equals(rowHeaderAxisConfiguration.getIndexStyle())) {
				rowsIndexStyleCombo.select(0);
			} else {
				rowsIndexStyleCombo.select(1);
			}
			displayFilterButton.setSelection(rowHeaderAxisConfiguration.isDisplayFilter());
			displayIndexButton.setSelection(rowHeaderAxisConfiguration.isDisplayIndex());
			displayLabelButton.setSelection(rowHeaderAxisConfiguration.isDisplayLabel());
		} else {
			rowsIndexStyleCombo.select(1);
			displayFilterButton.setSelection(false);
			displayIndexButton.setSelection(true);
			displayLabelButton.setSelection(true);
		}
	}

	/**
	 * This allows to create the columns group and its fields.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createColumnsGroup(final Composite parent) {

		// Create the columns composite
		final Composite columnsGroup = createSection(parent, Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_columnsManagementLabel);
		columnsGroup.setLayout(new GridLayout(4, false));

		// Create the index style combo
		final Label labelIndexStyle = new Label(columnsGroup, SWT.NONE);
		labelIndexStyle.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_indexStyleLabel);
		labelIndexStyle.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Combo columnsIndexStyleCombo = new Combo(columnsGroup, SWT.NONE);
		final List<String> indexStyleList = new ArrayList<String>();
		indexStyleList.add(AxisIndexStyle.ALPHABETIC.getName());
		indexStyleList.add(AxisIndexStyle.NUMERIC.getName());
		columnsIndexStyleCombo.setItems((String[]) indexStyleList.toArray(new String[indexStyleList.size()]));
		final GridData columnsIndexStyleLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false);
		columnsIndexStyleLayoutData.minimumWidth = 100;
		columnsIndexStyleCombo.setLayoutData(columnsIndexStyleLayoutData);
		columnsIndexStyleCombo.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				TableHeaderAxisConfiguration columnHeaderAxisConfiguration = configuration.getColumnHeaderAxisConfiguration();
				if (null == columnHeaderAxisConfiguration) {
					columnHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
					configuration.setColumnHeaderAxisConfiguration(columnHeaderAxisConfiguration);
				}

				if (0 == columnsIndexStyleCombo.getSelectionIndex()) {
					columnHeaderAxisConfiguration.setIndexStyle(AxisIndexStyle.ALPHABETIC);
				} else {
					columnHeaderAxisConfiguration.setIndexStyle(AxisIndexStyle.NUMERIC);
				}
			}
		});

		// Create the display index checkbox
		final Label displayIndexLabel = new Label(columnsGroup, SWT.NONE);
		displayIndexLabel.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_displayIndexLabel);
		displayIndexLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Button displayIndexButton = new Button(columnsGroup, SWT.CHECK);
		displayIndexButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		displayIndexButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				TableHeaderAxisConfiguration columnHeaderAxisConfiguration = configuration.getColumnHeaderAxisConfiguration();
				if (null == columnHeaderAxisConfiguration) {
					columnHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
					configuration.setColumnHeaderAxisConfiguration(columnHeaderAxisConfiguration);
				}

				columnHeaderAxisConfiguration.setDisplayIndex(displayIndexButton.getSelection());
			}
		});

		// Create the display label checkbox
		final Label displayLabelLabel = new Label(columnsGroup, SWT.NONE);
		displayLabelLabel.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_displayLabelLabel);
		displayLabelLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Button displayLabelButton = new Button(columnsGroup, SWT.CHECK);
		displayLabelButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		displayLabelButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				TableHeaderAxisConfiguration columnHeaderAxisConfiguration = configuration.getColumnHeaderAxisConfiguration();
				if (null == columnHeaderAxisConfiguration) {
					columnHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
					configuration.setColumnHeaderAxisConfiguration(columnHeaderAxisConfiguration);
				}

				columnHeaderAxisConfiguration.setDisplayLabel(displayLabelButton.getSelection());
			}
		});

		// Create the display filter checkbox
		final Label displayFilterLabel = new Label(columnsGroup, SWT.NONE);
		displayFilterLabel.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_displayFilterLabel);
		displayFilterLabel.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Button displayFilterButton = new Button(columnsGroup, SWT.CHECK);
		displayFilterButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		displayFilterButton.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				TableHeaderAxisConfiguration columnHeaderAxisConfiguration = configuration.getColumnHeaderAxisConfiguration();
				if (null == columnHeaderAxisConfiguration) {
					columnHeaderAxisConfiguration = NattableaxisconfigurationFactory.eINSTANCE.createTableHeaderAxisConfiguration();
					configuration.setColumnHeaderAxisConfiguration(columnHeaderAxisConfiguration);
				}

				columnHeaderAxisConfiguration.setDisplayFilter(displayFilterButton.getSelection());
			}
		});

		// Initialize the fields
		// Select the correct combo item corresponding to the current configuration
		TableHeaderAxisConfiguration columnHeaderAxisConfiguration = configuration.getColumnHeaderAxisConfiguration();
		if (null != columnHeaderAxisConfiguration) {
			if (AxisIndexStyle.NUMERIC.equals(columnHeaderAxisConfiguration.getIndexStyle())) {
				columnsIndexStyleCombo.select(1);
			} else {
				columnsIndexStyleCombo.select(0);
			}
			displayFilterButton.setSelection(columnHeaderAxisConfiguration.isDisplayFilter());
			displayIndexButton.setSelection(columnHeaderAxisConfiguration.isDisplayIndex());
			displayLabelButton.setSelection(columnHeaderAxisConfiguration.isDisplayLabel());
		} else {
			columnsIndexStyleCombo.select(0);
			displayFilterButton.setSelection(false);
			displayIndexButton.setSelection(true);
			displayLabelButton.setSelection(true);
		}
	}

	/**
	 * This allows to create the master group and its fields.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createMasterGroup(final Composite parent) {

		// Create the master group
		final Composite masterGroup = createSection(parent, Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_masterLabel);
		masterGroup.setLayout(new GridLayout(2, false));

		// Create the name text of the master
		final Label labelame = new Label(masterGroup, SWT.NONE);
		labelame.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_nameLabel);
		labelame.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Text name = new Text(masterGroup, SWT.BORDER);
		name.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				// Set the master name
				getOrCreateMasterObjectAxisProvider(configuration).setName(name.getText());
			}
		});
		final GridData nameLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		nameLayoutData.minimumWidth = 200;
		name.setLayoutData(nameLayoutData);

		// Create the description text of the master
		final Label labelDescription = new Label(masterGroup, SWT.NONE);
		labelDescription.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_descriptionLabel);
		labelDescription.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Text description = new Text(masterGroup, SWT.BORDER | SWT.MULTI);
		description.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				// Set the master description
				getOrCreateMasterObjectAxisProvider(configuration).setDescription(description.getText());
			}
		});
		final GridData descriptionLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		descriptionLayoutData.minimumWidth = 400;
		descriptionLayoutData.heightHint = 50;
		description.setLayoutData(descriptionLayoutData);

		// Create the disconnect slave checkbox for the master
		final Label labelDisconnectSlave = new Label(masterGroup, SWT.NONE);
		labelDisconnectSlave.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_disconnectSlaveLabel);
		labelDisconnectSlave.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Button disconnectSlave = new Button(masterGroup, SWT.CHECK);
		disconnectSlave.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(final SelectionEvent e) {
				// Set the disconnect slave
				getOrCreateMasterObjectAxisProvider(configuration).setDisconnectSlave(disconnectSlave.getSelection());
			};
		});

		// Initialize the fields
		MasterObjectAxisProvider master = getMasterObjectAxisProvider(configuration);
		if (null != master) {
			name.setText(master.getName());
			description.setText(master.getDescription());
			disconnectSlave.setSelection(master.isDisconnectSlave());
		}
	}

	/**
	 * This allows to create the slave group and its fields.
	 * 
	 * @param parent
	 *            The parent composite.
	 */
	protected void createSlaveGroup(final Composite parent) {
		// Create the slave group
		final Composite slaveGroup = createSection(parent, Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_slaveLabel);
		slaveGroup.setLayout(new GridLayout(2, false));

		// Create the name text for the slave
		final Label labelame = new Label(slaveGroup, SWT.NONE);
		labelame.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_nameLabel);
		labelame.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Text name = new Text(slaveGroup, SWT.BORDER);
		name.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				// Set the slave description
				getOrCreateSlaveObjectAxisProvider(configuration).setName(name.getText());
			}
		});
		final GridData nameLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		nameLayoutData.minimumWidth = 200;
		name.setLayoutData(nameLayoutData);

		// Create the description text for the slave
		final Label labelDescription = new Label(slaveGroup, SWT.NONE);
		labelDescription.setText(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_descriptionLabel);
		labelDescription.setLayoutData(new GridData(SWT.BEGINNING, SWT.BEGINNING, false, false));
		final Text description = new Text(slaveGroup, SWT.BORDER | SWT.MULTI | SWT.WRAP | SWT.V_SCROLL);
		description.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(final ModifyEvent e) {
				// Set the slave description
				getOrCreateSlaveObjectAxisProvider(configuration).setDescription(description.getText());
			}
		});
		final GridData descriptionLayoutData = new GridData(SWT.BEGINNING, SWT.BEGINNING, true, false);
		descriptionLayoutData.minimumWidth = 400;
		descriptionLayoutData.heightHint = 50;
		description.setLayoutData(descriptionLayoutData);

		// Initialize the fields
		SlaveObjectAxisProvider slave = getSlaveObjectAxisProvider(configuration);
		if (null != slave) {
			name.setText(slave.getName());
			description.setText(slave.getDescription());
		}
	}

	/**
	 * This allows to create a section (which can be expand or collapse).
	 * 
	 * @param parent
	 *            The parent composite.
	 * @param sectionTitle
	 *            The section title.
	 * @return The created section.
	 */
	protected Composite createSection(final Composite parent, final String sectionTitle) {
		// Create a section to manage a better user-friendly interface
		final Section expandableCompoosite = new Section(parent, ExpandableComposite.TWISTIE | ExpandableComposite.CLIENT_INDENT);
		expandableCompoosite.setText(sectionTitle);
		expandableCompoosite.setExpanded(true);
		expandableCompoosite.setFont(JFaceResources.getFontRegistry().getBold(JFaceResources.DIALOG_FONT));
		expandableCompoosite.setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false, 2, 1));
		expandableCompoosite.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanged(final ExpansionEvent e) {
				expandableCompoosite.getParent().getParent().layout();
				expandableCompoosite.getParent().getParent().redraw();
			}
		});

		// Create the composite
		final Composite composite = new Composite(expandableCompoosite, SWT.NONE);
		expandableCompoosite.setClient(composite);

		return composite;
	}

	/**
	 * This allows to get the master axis provider of the configuration.
	 * 
	 * @param configuration
	 *            The current configuration.
	 * @return The master axis provider or <code>null</code> if not existing.
	 */
	protected MasterObjectAxisProvider getMasterObjectAxisProvider(final TableConfiguration configuration) {
		MasterObjectAxisProvider master = null;

		// Try to get an existing master axis provider
		if (0 < configuration.getRowAxisProviders().size()) {
			final Iterator<AbstractAxisProvider> rowAxisProvidersIterator = configuration.getRowAxisProviders().iterator();
			while (rowAxisProvidersIterator.hasNext() && null == master) {
				final AbstractAxisProvider axisProvider = rowAxisProvidersIterator.next();
				if (axisProvider instanceof MasterObjectAxisProvider) {
					master = (MasterObjectAxisProvider) axisProvider;
				}
			}
		}

		return master;
	}

	/**
	 * This allows to get the master axis provider of the configuration if existing or create it otherwise.
	 * 
	 * @param configuration
	 *            The current configuration.
	 * @return The got or created master axis provider.
	 */
	protected MasterObjectAxisProvider getOrCreateMasterObjectAxisProvider(final TableConfiguration configuration) {
		MasterObjectAxisProvider master = getMasterObjectAxisProvider(configuration);

		// The master doesn't exist, create it
		if (null == master) {
			master = NattableaxisproviderFactory.eINSTANCE.createMasterObjectAxisProvider();
			configuration.getRowAxisProviders().add(master);
		}

		// Set the master as default row axis provider if this is not already done
		if (null == configuration.getDefaultRowAxisProvider()) {
			configuration.setDefaultRowAxisProvider(master);
		}

		return master;
	}

	/**
	 * This allows to get the slave axis provider of the configuration.
	 * 
	 * @param configuration
	 *            The current configuration.
	 * @return The slave axis provider or <code>null</code> if not existing.
	 */
	protected SlaveObjectAxisProvider getSlaveObjectAxisProvider(final TableConfiguration configuration) {
		SlaveObjectAxisProvider slave = null;

		// Try to get an existing master axis provider
		if (0 < configuration.getRowAxisProviders().size()) {
			final Iterator<AbstractAxisProvider> columnAxisProvidersIterator = configuration.getColumnAxisProviders().iterator();
			while (columnAxisProvidersIterator.hasNext() && null == slave) {
				final AbstractAxisProvider axisProvider = columnAxisProvidersIterator.next();
				if (axisProvider instanceof SlaveObjectAxisProvider) {
					slave = (SlaveObjectAxisProvider) axisProvider;
				}
			}
		}

		return slave;
	}

	/**
	 * This allows to get the slave axis provider of the configuration if existing or create it otherwise.
	 * 
	 * @param configuration
	 *            The current configuration.
	 * @return The got or created slave axis provider.
	 */
	protected SlaveObjectAxisProvider getOrCreateSlaveObjectAxisProvider(final TableConfiguration configuration) {
		SlaveObjectAxisProvider slave = getSlaveObjectAxisProvider(configuration);

		// The master doesn't exist, create it
		if (null == slave) {
			slave = NattableaxisproviderFactory.eINSTANCE.createSlaveObjectAxisProvider();
			configuration.getColumnAxisProviders().add(slave);
		}

		// Set the master as default row axis provider if this is not already done
		if (null == configuration.getDefaultColumnAxisProvider()) {
			configuration.setDefaultColumnAxisProvider(slave);
		}

		return slave;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		boolean isComplete = true;
		final String name = helper.getDefaultTableName();
		final String type = helper.getTableType();
		String errorMessage = null;
		StringBuffer buffer = new StringBuffer();
		if (name.isEmpty()) {
			buffer.append(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_nameMustBeSetError);
		}

		if (type.isEmpty()) {
			buffer.append(Messages.EditGenericNattableConfigurationFieldsNattableWizardPage_typeMustBeSetError);
		}

		errorMessage = buffer.toString();
		if (errorMessage.isEmpty()) {
			setErrorMessage(null);
		} else {
			setErrorMessage(errorMessage.toString());
		}

		isComplete = !name.isEmpty() && !type.isEmpty();
		return isComplete;
	}

}
