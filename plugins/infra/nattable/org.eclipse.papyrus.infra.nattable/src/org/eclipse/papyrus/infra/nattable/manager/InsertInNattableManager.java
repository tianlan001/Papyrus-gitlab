/*****************************************************************************
 * Copyright (c) 2015 CEA LIST and others.
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

package org.eclipse.papyrus.infra.nattable.manager;

import java.io.Reader;
import java.io.StringReader;

import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;

/**
 * This class allows to insert axis from string.
 */
public class InsertInNattableManager extends AbstractInsertImportInNattableManager {

	/**
	 * the text to paste
	 */
	private final String pastedText;

	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            The nattable model manager.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param useProgressMonitorDialog
	 *            Boolean to determinate if a progress monitor dialog must be used.
	 * @param openDialog
	 *            Boolean to determinate if the dialog must be opened during the process.
	 * @param preferredUserAction
	 *            The preferred user action for the insert row action.
	 * @param pastedText
	 *            The text pasted.
	 */
	public InsertInNattableManager(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final boolean useProgressMonitorDialog, final boolean openDialog, final int preferredUserAction, final String pastedText) {
		this(tableManager, pasteHelper, useProgressMonitorDialog, openDialog, preferredUserAction, null, pastedText);
	}

	/**
	 * Constructor.
	 *
	 * @param tableManager
	 *            The nattable model manager.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param useProgressMonitorDialog
	 *            Boolean to determinate if a progress monitor dialog must be used.
	 * @param openDialog
	 *            Boolean to determinate if the dialog must be opened during the process.
	 * @param preferredUserAction
	 *            The preferred user action for the insert row action.
	 * @param tableSelectionWrapper
	 *            The current selection in the table.
	 * @param pastedText
	 *            The text pasted.
	 */
	public InsertInNattableManager(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final boolean useProgressMonitorDialog, final boolean openDialog, final int preferredUserAction, final TableSelectionWrapper tableSelectionWrapper,
			final String pastedText) {
		super(tableManager, pasteHelper, useProgressMonitorDialog, openDialog, preferredUserAction, tableSelectionWrapper);
		this.pastedText = pastedText;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.AbstractPasteImportInNattableManager#createReader()
	 *
	 * @return
	 */
	@Override
	protected Reader createReader() {
		return new StringReader(this.pastedText);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.nattable.manager.AbstractPasteImportInNattableManager#getDataSize()
	 *
	 * @return
	 */
	@Override
	protected long getDataSize() {
		return this.pastedText.length();
	}
}
