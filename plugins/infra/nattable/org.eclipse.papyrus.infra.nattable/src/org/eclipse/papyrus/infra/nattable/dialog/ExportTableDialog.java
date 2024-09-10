/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Thanh Liem PHAN (ALL4TEC) <thanhliem.phan@all4tec.net> - Bug 417095
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.dialog;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.infra.nattable.enums.TreeTableAction;
import org.eclipse.papyrus.infra.nattable.export.image.ImageFormat;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
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
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

/**
 * Dialog lets user to export table to image.
 * 
 * @since 3.0
 */
public class ExportTableDialog extends Dialog {

	/** The dialog composite. */
	protected Composite dialogComposite = null;

	/** The filter index. */
	protected int filterIndex = -1;

	/** The output directory path. */
	protected Text outputPathTxt = null;

	/** The browse file system button. */
	protected Button browseFileSystemBtn = null;

	/** The browse workspace button. */
	protected Button browseWorkSpaceBtn = null;

	/** The image name. */
	protected Text imageNameTxt = null;

	/** The output format combobox. */
	protected Combo outputFormatCb = null;

	/** The tree action combobox. */
	protected Combo treeActionCb = null;

	/** The output directory resource. */
	protected IResource outputDirectory = null;

	/** The exporter string. */
	protected String exportFormat = null;

	/** The selected tree action string. */
	protected String selectedTreeAction = null;

	/** The default table name. */
	protected String defaultTableName = null;

	/** The default filter extension string array. */
	protected String[] defaultFilterExtensions = null;

	/** Flag to indicate that the table is a tree one. */
	protected boolean isTreeTable = false;

	/** The exported file name. */
	protected String exportedFileName = null;

	/**
	 * Instantiate a dialog to export a table.
	 *
	 * @param parentShell
	 *            The parent shell
	 * @param defaultOutputDir
	 *            The default output directory resource
	 * @param defaultTableName
	 *            The default table name
	 * @param defaultFilterExtensions
	 *            The default filter extensions
	 * @param isTreeTable
	 *            Flag to indicate that the table is a tree one
	 */
	public ExportTableDialog(final Shell parentShell, final IResource defaultOutputDir, final String defaultTableName, final String[] defaultFilterExtensions, final boolean isTreeTable) {
		super(parentShell);
		setBlockOnOpen(true);
		setShellStyle(getShellStyle() | SWT.RESIZE);

		this.outputDirectory = defaultOutputDir;
		this.defaultTableName = defaultTableName;
		this.defaultFilterExtensions = defaultFilterExtensions;
		this.isTreeTable = isTreeTable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void configureShell(final Shell newShell) {
		super.configureShell(newShell);

		// Set title to dialog
		newShell.setText(Messages.ExportTableDialog_ExportTableDialogTitle);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		dialogComposite = (Composite) super.createDialogArea(parent);

		createDialogComposite();
		createDialogContents();
		return dialogComposite;
	}

	/**
	 * Create the dialog composite.
	 */
	protected void createDialogComposite() {
		GridLayout dialogLayout = new GridLayout();
		GridData dialogLayoutData = new GridData(GridData.FILL_BOTH);
		dialogComposite.setLayout(dialogLayout);
		dialogComposite.setLayoutData(dialogLayoutData);
	}

	/**
	 * Create the dialog contents.
	 */
	protected void createDialogContents() {
		Composite composite = new Composite(dialogComposite, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		composite.setLayout(layout);
		composite.setFont(dialogComposite.getFont());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Create the 1st line for selecting output directory
		createOutputPathTextbox(composite);
		createBrowseWorkSpaceBtn(composite);
		createBrowseFileSystemBtn(composite);

		// Create the 2nd line for filling image file name
		createImageNameTextbox(composite);

		// Create the 3rd line for selecting image format
		createOutputFormatCombobox(composite);

		// If the table is the tree one, create a combobox to select expand|collapse|none action before exporting
		if (this.isTreeTable) {
			createTreeActionCombobox(composite);
		}

		// Initialise the exported file name
		computeExportedFileName();
	}

	/**
	 * Create the output path text box.
	 *
	 * @param composite
	 *            The parent composite
	 */
	protected void createOutputPathTextbox(final Composite composite) {
		Label selectOutputDirLbl = new Label(composite, SWT.NONE);
		selectOutputDirLbl.setText(Messages.ExportTableDialog_SelectOutputDirLabel);

		this.outputPathTxt = new Text(composite, SWT.BORDER);
		this.outputPathTxt.setEnabled(false);
		this.outputPathTxt.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		if (null != this.outputDirectory) {
			outputPathTxt.setText(this.outputDirectory.getLocation().toString());
		}
	}

	/**
	 * Create the browse file system button.
	 *
	 * @param composite
	 *            the parent composite
	 */
	protected void createBrowseFileSystemBtn(final Composite composite) {

		this.browseFileSystemBtn = new Button(composite, SWT.NONE);
		this.browseFileSystemBtn.setText(Messages.ExportTableDialog_BrowseFileSystem);
		this.browseFileSystemBtn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				DirectoryDialog dirDialog = new DirectoryDialog(getShell(), SWT.NONE);

				String absolutePath = dirDialog.open();
				if (null != absolutePath && !absolutePath.isEmpty()) {

					// Update the output path
					outputPathTxt.setText(absolutePath);

					// Update the exported file name when output dir is changed
					computeExportedFileName();
				}
			}
		});
	}

	/**
	 * Create the browse workspace button.
	 *
	 * @param composite
	 *            the parent composite
	 */
	protected void createBrowseWorkSpaceBtn(final Composite composite) {

		this.browseWorkSpaceBtn = new Button(composite, SWT.NONE);
		this.browseWorkSpaceBtn.setText(Messages.ExportTableDialog_BrowseWorkSpace);
		this.browseWorkSpaceBtn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				ContainerSelectionDialog csDialog = new ContainerSelectionDialog(Display.getCurrent().getActiveShell(), ResourcesPlugin.getWorkspace().getRoot(), true, Messages.ExportTableDialog_ContainerSelectionDialogTitle);

				if (Window.OK == csDialog.open()) {
					Object[] results = csDialog.getResult();
					if (1 == results.length && results[0] instanceof IPath) {
						URI uri = URI.createPlatformResourceURI(((IPath) results[0]).toString(), true);
						outputDirectory = ResourcesPlugin.getWorkspace().getRoot().findMember(new Path(uri.toPlatformString(true)));
						outputPathTxt.setText(outputDirectory.getLocation().toString());

						// Update the exported file name when output dir is changed
						computeExportedFileName();
					}
				}
			}
		});
	}

	/**
	 * Create the image name text box.
	 *
	 * @param composite
	 *            The parent composite
	 */
	protected void createImageNameTextbox(final Composite composite) {
		Label imageNameLbl = new Label(composite, SWT.NONE);
		imageNameLbl.setText(Messages.ExportTableDialog_ImageNameLabel);

		this.imageNameTxt = new Text(composite, SWT.BORDER);
		GridData imageNameTxtGridData = new GridData(GridData.FILL_HORIZONTAL);
		imageNameTxtGridData.horizontalSpan = 3;
		this.imageNameTxt.setLayoutData(imageNameTxtGridData);
		this.imageNameTxt.setText(this.defaultTableName);
		this.imageNameTxt.addModifyListener(new ModifyListener() {

			@Override
			public final void modifyText(final ModifyEvent event) {
				// Update the exported file name when image name text is changed
				computeExportedFileName();
			}
		});
	}

	/**
	 * Create the output format combobox.
	 *
	 * @param composite
	 *            The parent composite
	 */
	protected void createOutputFormatCombobox(final Composite composite) {
		Label outputFormatLbl = new Label(composite, SWT.NONE);
		outputFormatLbl.setText(Messages.ExportTableDialog_SelectOutputFormatLabel);

		this.outputFormatCb = new Combo(composite, SWT.NONE);
		GridData outputFormatCbGridData = new GridData(GridData.FILL_HORIZONTAL);
		outputFormatCbGridData.horizontalSpan = 3;
		this.outputFormatCb.setLayoutData(outputFormatCbGridData);

		// Load output format combobox
		for (String imageFileFormat : defaultFilterExtensions) {
			this.outputFormatCb.add(imageFileFormat);
		}
		this.outputFormatCb.setText(ImageFormat.getDefaultImageFormat().getImageExtension());
		this.exportFormat = this.outputFormatCb.getText();
		this.filterIndex = 0;

		this.outputFormatCb.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				exportFormat = outputFormatCb.getText();
				filterIndex = outputFormatCb.getSelectionIndex();

				// Update the exported file name when image format is changed
				computeExportedFileName();
			}
		});
	}

	/**
	 * Create the tree action combo box.
	 *
	 * @param composite
	 *            The parent composite
	 */
	protected void createTreeActionCombobox(final Composite composite) {
		Label selectTreeActionLbl = new Label(composite, SWT.NONE);
		selectTreeActionLbl.setText(Messages.ExportTableDialog_SelectTreeActionLabel);

		this.treeActionCb = new Combo(composite, SWT.NONE);
		GridData treeActionCbGridData = new GridData(GridData.FILL_HORIZONTAL);
		treeActionCbGridData.horizontalSpan = 3;
		this.treeActionCb.setLayoutData(treeActionCbGridData);

		// Load all values for the tree action combobox
		for (TreeTableAction action : TreeTableAction.VALUES) {
			this.treeActionCb.add(action.toString());
		}

		// Set the default action
		this.treeActionCb.setText(TreeTableAction.getDefaultTableAction().toString());
		this.selectedTreeAction = this.treeActionCb.getText();

		// Add the listener for the combobox
		this.treeActionCb.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(final SelectionEvent e) {
				// Update the selected tree action from the tree action combobox
				selectedTreeAction = treeActionCb.getText();
			}
		});
	}

	/**
	 * @return The selected tree action
	 */
	public String getSelectedTreeAction() {
		return this.selectedTreeAction;
	}

	/**
	 * @return The exported file name
	 */
	public String getExportedFileName() {
		return this.exportedFileName;
	}

	/**
	 * Compute the exported filename when controls are updated.
	 */
	protected void computeExportedFileName() {
		this.exportedFileName = this.outputPathTxt.getText() + File.separator + this.imageNameTxt.getText() + "." + this.exportFormat; //$NON-NLS-1$
	}

	/**
	 * @return The filter index
	 */
	public int getFilterIndex() {
		return filterIndex;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Overridden to show an override confirm message dialog when the exported file already exists in the destination folder.
	 */
	@Override
	protected void okPressed() {
		File file = new File(this.exportedFileName);
		if (file.exists()) {
			final MessageDialog dialog = new MessageDialog(
					getShell(),
					Messages.ExportTableDialog_OverrideConfirmMessasgeDialogText,
					null,
					NLS.bind(Messages.ExportTableDialog_OverrideConfirmMessasgeDialogMessage, this.imageNameTxt.getText() + "." + this.exportFormat), //$NON-NLS-1$
					MessageDialog.WARNING,
					new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL },
					0);

			if (dialog.open() != Window.OK) {
				return;
			}
		}

		super.okPressed();
	}
}
