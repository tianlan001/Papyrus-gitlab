/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.profilemigration.ui.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.toolsmiths.profilemigration.ui.Messages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.FormDialog;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * This dialog ask the use to select the old file
 */
public class ProfileMigrationToolConfigurationDialog extends FormDialog {

	/**
	 * Skip press return value
	 */
	public static final int SKIP = 3;

	/**
	 * Widget to access directory browsing
	 */
	protected Button selectTargetDirectoryButton;

	/**
	 * Widget to display the selected paths
	 */
	protected Text selectedPathText;

	/**
	 * The selected file path
	 */
	protected String filePath;

	/**
	 * The Profile name
	 */
	protected String profileName;

	/**
	 * Files to appear in the selection dialog
	 */
	protected String fileExtension;

	protected final static String IMPORT_FILE_BUTTON_NAME = Messages.ProfileMigrationToolConfigurationDialog_ImportButtonName;

	protected final static String IMPORT_FILE_BUTTON_TOOLTIP = Messages.ProfileMigrationToolConfigurationDialog_ImportButtonTooltip;

	protected final static String SKIP_BUTTON_NAME = Messages.ProfileMigrationToolConfigurationDialog_SkipButtonName;

	protected final static String SKIP_BUTTON_TOOLTIP = Messages.ProfileMigrationToolConfigurationDialog_SkipButtonTooltip;

	protected final static String DIALOG_TITLE = Messages.ProfileMigrationToolConfigurationDialog_Title;

	protected final static String FILE_SELECTION_SECTION_TITLE = Messages.ProfileMigrationToolConfigurationDialog_FileSelectionSelectionTitle;

	protected final static String FILE_SELECTION_SECTION_DESCRIPTION = Messages.ProfileMigrationToolConfigurationDialog_FileSelectionSelectionDescription;

	private final static String EMPTY_STRING = ""; //$NON-NLS-1$


	public ProfileMigrationToolConfigurationDialog(Shell parentShell, String profileName) {
		super(parentShell);
		this.fileExtension = "*.profile.uml"; //$NON-NLS-1$
		this.filePath = EMPTY_STRING;
		this.profileName = profileName;
	}

	/**
	 * Disable the Ok button (since the path is empty)
	 *
	 * @see org.eclipse.ui.forms.FormDialog#createButtonBar(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 * @return
	 */
	@Override
	protected Control createButtonBar(Composite parent) {
		Control control = super.createButtonBar(parent);
		this.getButton(IDialogConstants.OK_ID).setEnabled(false);
		return control;
	}

	/**
	 * Add the skip button to the button bar
	 *
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 *
	 * @param parent
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button button = createButton(parent, IDialogConstants.SKIP_ID, SKIP_BUTTON_NAME, true);
		button.setToolTipText(SKIP_BUTTON_TOOLTIP);
		super.createButtonsForButtonBar(parent);
	}

	@Override
	protected void createFormContent(IManagedForm mform) {
		mform.getForm().setText(DIALOG_TITLE);
		ScrolledForm scrolledForm = mform.getForm();
		FormToolkit toolkit = mform.getToolkit();
		Composite parent = scrolledForm.getBody();
		parent.setLayout(new GridLayout());
		createSelectionFileSection(scrolledForm.getBody(), toolkit);
	}

	@Override
	protected void buttonPressed(int buttonId) {
		super.buttonPressed(buttonId);
		if (buttonId == IDialogConstants.SKIP_ID) {
			skipPress();
		}
	}

	/**
	 * Create the file selection section
	 *
	 * @param body
	 * @param toolkit
	 */
	private void createSelectionFileSection(Composite body, FormToolkit toolkit) {
		String lSectionTitle = FILE_SELECTION_SECTION_TITLE;
		Section lSection = toolkit.createSection(body, ExpandableComposite.EXPANDED | ExpandableComposite.TITLE_BAR);
		lSection.setLayoutData(new GridData(GridData.FILL_VERTICAL));
		if (lSectionTitle != null) {
			lSection.setText(lSectionTitle);
		}
		ScrolledForm lInsideScrolledForm = toolkit.createScrolledForm(lSection);
		lInsideScrolledForm.setExpandHorizontal(true);
		lInsideScrolledForm.setExpandVertical(true);
		Composite lBody = lInsideScrolledForm.getBody();
		GridLayout lLayout = new GridLayout();
		lLayout.numColumns = 1;
		lBody.setLayout(lLayout);
		toolkit.createLabel(lBody, NLS.bind(FILE_SELECTION_SECTION_DESCRIPTION, profileName), SWT.WRAP);
		lSection.setClient(lInsideScrolledForm);
		createSelectDir(lBody, toolkit);
	}

	/**
	 * Create the text field and the button to select a file
	 *
	 * @param container
	 * @param toolkit
	 */
	private void createSelectDir(Composite container, FormToolkit toolkit) {
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, true));
		composite.setBackground(container.getBackground());
		GridLayout layout = new GridLayout(2, false);
		composite.setLayout(layout);
		selectTargetDirectoryButton = new Button(composite, SWT.PUSH);
		selectTargetDirectoryButton.setText(IMPORT_FILE_BUTTON_NAME);
		selectTargetDirectoryButton.setToolTipText(IMPORT_FILE_BUTTON_TOOLTIP);
		selectTargetDirectoryButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false));
		selectedPathText = new Text(composite, SWT.BORDER | SWT.PUSH);
		selectedPathText.setText(EMPTY_STRING);
		selectedPathText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));
		selectTargetDirectoryButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Shell shell = container.getShell();
				FileDialog fileDialog;
				fileDialog = new FileDialog(shell, SWT.NONE);
				fileDialog.setText(Messages.ProfileMigrationToolConfigurationDialog_FileSelection);
				if (fileExtension != null && !fileExtension.isEmpty()) {
					fileDialog.setFilterExtensions(new String[] { fileExtension, "*" }); //$NON-NLS-1$
				}
				String filePath = null;
				if ((filePath = fileDialog.open()) != null) {
					selectedPathText.setText(filePath);
					ProfileMigrationToolConfigurationDialog.this.filePath = filePath;
				}
				checkInformation();
			}
		});
	}

	/**
	 * Get the file path
	 *
	 * @return the file path
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * Action when skip button press
	 */
	protected void skipPress() {
		setReturnCode(SKIP);
		close();
	}

	/**
	 * Check if the path is valid, active the OK button if it is valid
	 *
	 * @return true if the path is valid, false otherwise
	 */
	private boolean checkInformation() {
		String text = selectedPathText.getText();
		boolean validSelectedDirectory = text != null && text != EMPTY_STRING;
		if (validSelectedDirectory) {
			this.getButton(IDialogConstants.OK_ID).setEnabled(validSelectedDirectory);
		} else {
			this.getButton(IDialogConstants.OK_ID).setEnabled(validSelectedDirectory);
		}
		return validSelectedDirectory;
	}
}
