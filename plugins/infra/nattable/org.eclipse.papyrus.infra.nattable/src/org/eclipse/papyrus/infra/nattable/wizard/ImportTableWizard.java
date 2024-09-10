/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *
 *		 Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.wizard;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.manager.AbstractPasteImportInsertInNattableManager;
import org.eclipse.papyrus.infra.nattable.manager.ImportAxisAsInsertInNattableManager;
import org.eclipse.papyrus.infra.nattable.manager.ImportAxisAsPasteInNattableManager;
import org.eclipse.papyrus.infra.nattable.manager.InsertInNattableManager;
import org.eclipse.papyrus.infra.nattable.manager.PasteAxisInNattableManager;
import org.eclipse.papyrus.infra.nattable.manager.table.NattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.paste.PasteSeparator;
import org.eclipse.papyrus.infra.nattable.paste.TextDelimiter;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.PasteHelperUtils;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.papyrus.infra.nattable.utils.UserActionConstants;
import org.eclipse.papyrus.infra.nattable.wizard.pages.ImportCSVConfigurationPage;
import org.eclipse.papyrus.infra.nattable.wizard.pages.ImportFilePage;
import org.eclipse.papyrus.infra.nattable.wizard.pages.ImportInvertedTableErrorPage;
import org.eclipse.papyrus.infra.nattable.wizard.pages.ImportTableErrorPage;
import org.eclipse.papyrus.infra.widgets.util.FileUtil;
import org.eclipse.ui.IImportWizard;

/**
 *
 * This wizard allows to import a table in the Papyrus nattable editor
 *
 */
public class ImportTableWizard extends AbstractTableWizard implements IImportWizard {

	/**
	 * the import page
	 */
	private ImportFilePage importPage;

	/**
	 * the page used to configure the CSV import
	 */
	private ImportCSVConfigurationPage csvConfigurationPage;

	/**
	 * The initial selected file path.
	 */
	private String importedText = ""; //$NON-NLS-1$

	/**
	 * Boolean to determinate if a progress monitor must be used for the command.
	 */
	private boolean useProgressMonitor = true;

	/**
	 * Boolean to determinate if the dialog must be opened during the process.
	 */
	private boolean openDialog = true;

	/**
	 * The preferred user action defined.
	 */
	private int preferredUserAction = UserActionConstants.UNDEFINED_USER_ACTION;

	/**
	 *
	 * Constructor.
	 *
	 */
	public ImportTableWizard() {
		setWindowTitle(Messages.ImportTableWizard_ImportTableFromFileInPapyrusModel);
		String pluginId = Activator.PLUGIN_ID;
		String path = "icons/importTablePapyrusWizban.png"; //$NON-NLS-1$
		ImageDescriptor desc = org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImageDescriptor(pluginId, path);
		setDefaultPageImageDescriptor(desc);
	}

	/**
	 *
	 * Constructor.
	 *
	 */
	public ImportTableWizard(final String importedText, final boolean useProgressMonitor, final boolean openDialog, final int preferredUserAction) {
		this();
		this.importedText = importedText;
		this.useProgressMonitor = useProgressMonitor;
		this.openDialog = openDialog;
		this.preferredUserAction = preferredUserAction;
	}

	/**
	 *
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 *
	 */
	@Override
	public void addPages() {
		super.addPages();
		final String pageTitle = Messages.ImportTableWizard_ImportTable;
		final ImageDescriptor desc = null;
		if (manager != null) {
			if (manager.getTable().isInvertAxis()) {
				addPage(new ImportInvertedTableErrorPage(Messages.ImportTableWizard_ImportTableError, pageTitle, desc));
			} else {
				this.importPage = new ImportFilePage(pageTitle, pageTitle, desc, this.manager);
				addPage(this.importPage);
				this.csvConfigurationPage = new ImportCSVConfigurationPage(pageTitle, Messages.ImportTableWizard_ConfigureImport, desc, PasteSeparator.SEMICOLON, TextDelimiter.DOUBLE_QUOTE);
				addPage(this.csvConfigurationPage);
			}
		} else {
			addPage(new ImportTableErrorPage(Messages.ImportTableWizard_ImportTableError, pageTitle, desc));
		}
	}


	/**
	 *
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 *
	 * @return
	 */
	@Override
	public boolean performFinish() {
		// the import file
		File file = null;
		if (openDialog) {
			file = FileUtil.getFile(this.importPage.getFilePath());
		}
		final CSVPasteHelper pasteHelper = new CSVPasteHelper(this.csvConfigurationPage.getSeparator(), this.csvConfigurationPage.getTextDelimiter(), ","); //$NON-NLS-1$

		final TableStructuredSelection currentSelection = ((NattableModelManager) this.manager).getSelectionInTable();
		TableSelectionWrapper tableSelectionWrapper = null;
		if (null != currentSelection) {
			tableSelectionWrapper = (TableSelectionWrapper) ((TableStructuredSelection) currentSelection).getAdapter(TableSelectionWrapper.class);
			if (tableSelectionWrapper.getSelectedCells().isEmpty()) {
				tableSelectionWrapper = null;
			}
		}

		AbstractPasteImportInsertInNattableManager pasteManager = null;

		// The insert is not authorized when columns or cells are selected, so its a basic with header paste
		if (null != tableSelectionWrapper &&
				((!tableSelectionWrapper.getFullySelectedColumns().isEmpty()) ||
						(tableSelectionWrapper.getFullySelectedColumns().isEmpty() && tableSelectionWrapper.getFullySelectedRows().isEmpty() && !tableSelectionWrapper.getSelectedCells().isEmpty()))) {
			if (null != file && openDialog) {
				pasteManager = new ImportAxisAsPasteInNattableManager(this.manager, pasteHelper, file, useProgressMonitor, openDialog, preferredUserAction, null, false);
			} else {
				pasteManager = new PasteAxisInNattableManager(this.manager, pasteHelper, useProgressMonitor, openDialog, preferredUserAction, null, importedText, false);
			}
		} else {
			// Is no selection or rows selection, check if columns are present
			if (null != file && openDialog) {
				boolean isPasteWithOverwrite = PasteHelperUtils.isPasteWithOverwrite(manager, pasteHelper, createReader(file));
				if (!isPasteWithOverwrite) {
					pasteManager = new ImportAxisAsPasteInNattableManager(this.manager, pasteHelper, file, useProgressMonitor, openDialog, preferredUserAction, tableSelectionWrapper, isPasteWithOverwrite);
				} else {
					pasteManager = new ImportAxisAsInsertInNattableManager(this.manager, pasteHelper, file, useProgressMonitor, openDialog, preferredUserAction, tableSelectionWrapper);
				}
			} else {
				boolean isPasteWithOverwrite = PasteHelperUtils.isPasteWithOverwrite(manager, pasteHelper, new StringReader(importedText));
				if (!isPasteWithOverwrite) {
					pasteManager = new PasteAxisInNattableManager(this.manager, pasteHelper, useProgressMonitor, openDialog, preferredUserAction, tableSelectionWrapper, importedText, isPasteWithOverwrite);
				} else {
					pasteManager = new InsertInNattableManager(this.manager, pasteHelper, useProgressMonitor, openDialog, preferredUserAction, tableSelectionWrapper, importedText);
				}
			}
		}

		IStatus status = pasteManager.doAction();
		// TODO : do something with the status ?
		return true;
	}

	/**
	 * This allows to create the reader and returns it.
	 *
	 * @param file
	 *            The file to read.
	 * @return
	 * 		a new reader
	 */
	protected Reader createReader(final File file) {
		FileReader reader = null;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			Activator.log.error(e);
		}
		return reader;
	}
}
