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
 *	 Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 476618
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;

/**
 * This class allows to import axis from string (managed as a paste).
 */
public class ImportAxisAsInsertInNattableManager extends AbstractInsertImportInNattableManager {

	/**
	 * the file to import
	 */
	private File file;

	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            The table manager.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param fileToImport
	 *            The file to import.
	 * @param useProgressMonitorDialog
	 *            Boolean to determinate if a progress monitor must be used for the command.
	 * @param openDialog
	 *            Boolean to determinate if the dialog must be opened during the process.
	 * @param preferredUserAction
	 *            The preferred user action for the insert row action.
	 * @param tableSelectionWrapper
	 *            The current selection in the table.
	 */
	public ImportAxisAsInsertInNattableManager(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final File fileToImport, final boolean useProgressMonitorDialog, final boolean openDialog, final int preferredUserAction,
			final TableSelectionWrapper tableSelectionWrapper) {
		super(tableManager, pasteHelper, useProgressMonitorDialog, openDialog, preferredUserAction, tableSelectionWrapper);
		this.file = fileToImport;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.AbstractPasteImportInNattableManager#createReader()
	 */
	@Override
	protected Reader createReader() {
		FileReader reader = null;
		try {
			reader = new FileReader(file);
		} catch (FileNotFoundException e) {
			Activator.log.error(e);
		}
		return reader;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.AbstractPasteImportInNattableManager#getDataSize()
	 */
	@Override
	protected long getDataSize() {
		return this.file.length();
	}

}
