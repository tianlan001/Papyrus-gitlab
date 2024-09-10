/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Bug 571814
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.nattable.wizard.pages;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.papyrus.infra.architecture.ArchitectureDomainManager;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureContext;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableconfiguration.TableConfiguration;
import org.eclipse.papyrus.infra.viewpoints.policy.PolicyChecker;
import org.eclipse.papyrus.infra.viewpoints.policy.ViewPrototype;
import org.eclipse.papyrus.toolsmiths.nattable.messages.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 *
 * This page allows to define the required data to export a table as TableConfiguration
 */
public class DefineTableConfigurationDataWizardPage extends AbstractExportTableAsTableConfigurationWizardPage implements ModifyListener {

	/**
	 * the name of the current page
	 */
	public static final String DATA_PAGE_TITLE = Messages.DefineTableConfigurationDataWizardPage_DataPageTitle;

	/**
	 * the list of existing ID
	 */
	private static final Collection<String> forbiddenIDs = getAllExistingIds();

	/**
	 * the new table type
	 */
	private String newTableType;

	/**
	 * the new table configuration configuration name
	 */
	private String newTableConfigurationName;

	/**
	 * the new table configuration description
	 */
	private String newTableConfigurationDescription;

	/**
	 * The text field used to define the table name
	 */
	private Text tableNameField;

	/**
	 * The text field used to define the table type
	 */
	private Text tableTypeField;

	/**
	 * the text field used to define the table configuration description
	 */
	private Text tableDescriptionField;

	/**
	 * Constructor.
	 *
	 * @param pageName
	 */
	public DefineTableConfigurationDataWizardPage() {
		super(DATA_PAGE_TITLE);
		setMessage(Messages.DefineTableConfigurationDataWizardPage_ConfigureTableConfiguration);
	}



	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	public void createControl(final Composite parent) {
		final Composite composite = new Composite(parent, SWT.BORDER);
		composite.setLayout(new GridLayout(1, true));

		// the output group
		final Group outputGroup = new Group(composite, SWT.NONE);
		outputGroup.setText(Messages.DefineTableConfigurationDataWizardPage_EnterNewTableConfigurationInformation);
		outputGroup.setLayout(new GridLayout(2, true));
		outputGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Label tableNameLabel = new Label(outputGroup, SWT.NONE);
		tableNameLabel.setText(Messages.DefineTableConfigurationDataWizardPage_DefineTableTable);
		tableNameLabel.setLayoutData(new GridData(GridData.FILL, SWT.FILL, true, false));

		this.tableNameField = new Text(outputGroup, SWT.BORDER);
		this.tableNameField.setText(this.newTableConfigurationName);
		this.tableNameField.setLayoutData(new GridData(GridData.FILL, SWT.FILL, true, false));
		this.tableNameField.addModifyListener(this);


		final Label tableTypeLabel = new Label(outputGroup, SWT.NONE);
		tableTypeLabel.setText(Messages.DefineTableConfigurationDataWizardPage_DefineTableType);
		tableTypeLabel.setLayoutData(new GridData(GridData.FILL, SWT.FILL, true, false));

		this.tableTypeField = new Text(outputGroup, SWT.BORDER);
		this.tableTypeField.setText(this.buildNewTableType());
		this.tableTypeField.setLayoutData(new GridData(GridData.FILL, SWT.FILL, true, false));
		this.tableTypeField.addModifyListener(this);


		final Label tableDescriptionLabel = new Label(outputGroup, SWT.NONE);
		tableDescriptionLabel.setText(Messages.DefineTableConfigurationDataWizardPage_DefineTableConfigurationDescription);
		tableDescriptionLabel.setLayoutData(new GridData(GridData.FILL, SWT.FILL, true, false));

		this.tableDescriptionField = new Text(outputGroup, SWT.MULTI | SWT.BORDER);
		this.tableDescriptionField.setText(this.newTableConfigurationDescription);
		final GridData descriptionData = new GridData(SWT.FILL, SWT.FILL, true, true);
		descriptionData.horizontalSpan = 2;
		this.tableDescriptionField.setLayoutData(descriptionData);
		this.tableDescriptionField.addModifyListener(this);
		setControl(composite);
	}

	/**
	 *
	 * @return
	 *         the initial description proposed by the wizard
	 */
	private String buildNewTableConfigurationDescription() {
		return null != getExportedTable().getDescription() ? getExportedTable().getDescription() : ""; //$NON-NLS-1$
	}

	/**
	 *
	 * @return
	 *         the initial name proposed in the wizard
	 */
	private String buildNewTableName() {
		return null != getExportedTable().getName() ? getExportedTable().getName().replaceAll("\\s+", "") : ""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	}

	/**
	 *
	 * @return
	 *         the initial type proposed in the wizard
	 */
	private String buildNewTableType() {
		final StringBuilder builder = new StringBuilder(buildNewTableName());
		builder.append("Type"); //$NON-NLS-1$
		return builder.toString();
	}


	/**
	 * Calculates if the current page is complete and update its status using {@link #setPageComplete(boolean)}
	 */
	private void setPageComplete() {
		boolean isComplete = true;
		setErrorMessage(null);
		if (!checkDefaultTableName()) {
			isComplete = false;
		}
		if (!checkTableType()) {
			isComplete = false;
		}
		if (!checkTableDescription()) {
			isComplete = false;
		}
		setPageComplete(isComplete);
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the table configuration name is valid and set error message if not
	 */
	private boolean checkDefaultTableName() {
		final boolean res = null != this.newTableConfigurationName && this.newTableConfigurationName.length() > 0;
		if (!res) {
			setErrorMessage(Messages.DefineTableConfigurationDataWizardPage_PleaseSetAName);
		}
		return res;
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the table configuration type is valid and set error message if not
	 */
	private boolean checkTableType() {
		boolean res = null != this.newTableType && this.newTableType.length() > 0;
		if (res) {
			if (DefineTableConfigurationDataWizardPage.forbiddenIDs.contains(this.newTableType)) {
				res = false;
				setErrorMessage(Messages.DefineTableConfigurationDataWizardPage_TableTypeAlreadyExists);
			} else if (this.newTableType != null && this.newTableType.contains(" ")) { //$NON-NLS-1$
				setErrorMessage("The type field must not contains space."); //$NON-NLS-1$
			}
		} else {
			setErrorMessage(Messages.DefineTableConfigurationDataWizardPage_PleaseSetAType);
		}
		return res;
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the table description is valid and set error message if not
	 */
	private boolean checkTableDescription() {
		return true;
	}


	/**
	 * cross the Architecture Context avalaible in the current Papyrus installation to get all known ID *
	 *
	 * @return
	 *         all the known Id
	 */
	private static final Collection<String> getAllExistingIds() {
		final Set<String> tableType = new HashSet<>();
		for (final MergedArchitectureContext mac : ArchitectureDomainManager.getInstance().getVisibleArchitectureContexts()) {
			for (final ViewPrototype proto : PolicyChecker.getFor(mac).getAllPrototypes()) {
				final String impl = proto.getImplementation();
				final String implId = proto.getRepresentationKind().getImplementationID();
				// I think these ID must be equals, but not sure
				if (null != impl) {
					tableType.add(impl);
				}
				if (null != implId) {
					tableType.add(implId);
				}

			}
		}

		return tableType;
	}



	/**
	 * @see com.cea.papyrus.infra.nattable.export.wizard.AbstractExportTableAsTableConfigurationWizardPage#setExportedTable(org.eclipse.papyrus.infra.nattable.model.nattable.Table)
	 *
	 * @param exportedTable
	 */
	@Override
	public void setExportedTable(Table exportedTable) {
		super.setExportedTable(exportedTable);
		this.newTableConfigurationDescription = buildNewTableConfigurationDescription();
		this.newTableConfigurationName = buildNewTableName();
		this.newTableType = buildNewTableType();
	}


	/**
	 *
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 *
	 * @param e
	 */
	@Override
	public void modifyText(final ModifyEvent e) {
		final Object source = e.getSource();
		if (source instanceof Text) {
			final Text txt = (Text) source;
			if (this.tableNameField == txt) {
				this.newTableConfigurationName = this.tableNameField.getText();
			}
			if (this.tableTypeField == txt) {
				this.newTableType = this.tableTypeField.getText();

			}
			if (this.tableDescriptionField == txt) {
				this.newTableConfigurationDescription = this.tableDescriptionField.getText();
			}
		}

		setPageComplete();
	}

	/**
	 * @return the newTableType
	 */
	public String getNewTableType() {
		return newTableType;
	}

	/**
	 * @return the newTableDefaultName
	 */
	public String getNewTableDefaultName() {
		return newTableConfigurationName;
	}

	/**
	 * @return the newTableConfigurationDescription
	 */
	public String getNewTableConfigurationDescription() {
		return newTableConfigurationDescription;
	}


	/**
	 *
	 * @return
	 *         the file name for the new {@link TableConfiguration}
	 */
	public String getFileName() {
		return getNewTableType();
	}

}