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

import java.io.IOException;
import java.io.Reader;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.MultiStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.dialog.PasteImportStatusDialog;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionNattableCommandProvider;
import org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionTreeNattableCommandProvider;
import org.eclipse.papyrus.infra.nattable.provider.PasteNattableCommandProvider;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.IPapyrusNattableStatus;
import org.eclipse.papyrus.infra.nattable.utils.PapyrusNattableStatus;
import org.eclipse.papyrus.infra.nattable.utils.PasteEnablementStatus;
import org.eclipse.papyrus.infra.nattable.utils.PasteModeEnumeration;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.swt.widgets.Display;

/**
 * This class allows to insert axis from string.
 */
public abstract class AbstractInsertImportInNattableManager extends AbstractPasteImportInsertInNattableManager {

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
	 */
	public AbstractInsertImportInNattableManager(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final boolean useProgressMonitorDialog, final boolean openDialog, final int preferredUserAction) {
		this(tableManager, pasteHelper, useProgressMonitorDialog, openDialog, preferredUserAction, null);
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
	 */
	public AbstractInsertImportInNattableManager(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final boolean useProgressMonitorDialog, final boolean openDialog, final int preferredUserAction,
			final TableSelectionWrapper tableSelectionWrapper) {
		super(tableManager, pasteHelper, useProgressMonitorDialog, openDialog, preferredUserAction, tableSelectionWrapper);
	}

	/**
	 * This method manages the paste and the possible error dialog before doing the paste.
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.AbstractPasteImportInsertInNattableManager#doAction()
	 */
	@Override
	public IStatus doAction() {
		IStatus resultStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.InsertInNattableManager_InsertNotYetManaged);

		PasteEnablementStatus pasteStatus = null;

		int maxDepth = Integer.MAX_VALUE;

		// Check if the paste configuration is needed for the paste action
		pasteStatus = findPasteModeFromTableConfiguration(this.tableManager);
		if (PasteModeEnumeration.PASTE_NO_CONFIGURATION != pasteStatus.getPasteMode() && PasteModeEnumeration.CANT_PASTE != pasteStatus.getPasteMode()) {
			pasteStatus = findPasteConfigurationAxisIdentifier(this.tableManager);
		}
		if (pasteStatus.getPasteMode() == PasteModeEnumeration.PASTE_EOBJECT_ROW_OR_COLUMN) {
			final boolean value = MessageDialog.openQuestion(Display.getDefault().getActiveShell(), PasteImportStatusDialog.DIALOG_TITLE, Messages.AbstractPasteImportInsertInNattableManager_WhatAreYouPasting);
			if (value) {
				pasteStatus.getColumnStatus().add(new Status(IStatus.CANCEL, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_TheUserChosesToPasteRows));
			} else {
				pasteStatus.getRowStatus().add(new Status(IStatus.CANCEL, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_TheUserChosesToPasteColumns));
			}
		}

		final PasteModeEnumeration pasteMode = pasteStatus.getPasteMode();
		switch (pasteMode) {
		case PASTE_NO_CONFIGURATION:
			resultStatus = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_INFO, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_NoPasteConfiguration2);
			break;
		case CANT_PASTE:
			resultStatus = createCantInsertStatus(pasteStatus);
			break;
		case PASTE_EOBJECT_ROW:
			resultStatus = insertRow(this.tableManager, pasteStatus, pasteHelper, maxDepth);
			break;
		case PASTE_EOBJECT_COLUMN:
		case PASTE_EOBJECT_ROW_OR_COLUMN:
			// nothing to do
			break;
		default:
			break;
		}

		return resultStatus;
	}

	/**
	 * Manage the status when can't paste.
	 * 
	 * @param pasteStatus
	 *            The paste status.
	 * @return The status when this can't be pasted
	 */
	protected IStatus createCantInsertStatus(final PasteEnablementStatus pasteStatus) {
		IStatus resultStatus = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_INFO, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_NoPasteConfiguration2);

		final MultiStatus rowStatus = pasteStatus.getRowStatus();
		final MultiStatus columnStatus = pasteStatus.getColumnStatus();
		if (rowStatus == null && columnStatus != null) {
			resultStatus = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, getFirstNonOKStatus(columnStatus).getMessage());
		} else if (columnStatus == null && rowStatus != null) {
			resultStatus = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, getFirstNonOKStatus(rowStatus).getMessage());
		} else if (columnStatus != null && rowStatus != null) {
			final StringBuffer buffer = new StringBuffer(Messages.AbstractPasteImportInsertInNattableManager_NoPasteConfiguration);
			buffer.append(Messages.InsertInNattableManager_InsertRowsError);
			buffer.append(getFirstNonOKStatus(rowStatus).getMessage());
			buffer.append(Messages.InsertInNattableManager_InsertColumnsError);
			buffer.append(getFirstNonOKStatus(columnStatus).getMessage());
			resultStatus = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, buffer.toString());
		}

		return resultStatus;
	}

	/**
	 * Insert the row.
	 *
	 * @param manager
	 *            the table manager
	 * @param pasteStatus
	 *            the paste status
	 * @param pasteHelper
	 *            the paste helper
	 * @param maxDepth
	 *            The maximum depth to check.
	 * @return
	 *         <code>true</code> if the paste can be done
	 */
	private IStatus insertRow(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper, final int maxDepth) {
		IStatus resultStatus = Status.OK_STATUS;

		if (TableHelper.isTreeTable(manager)) {
			if (null != tableSelectionWrapper) {
				resultStatus = insertTreeRows(manager, pasteStatus, pasteHelper, tableSelectionWrapper);
			} else {
				resultStatus = insertTreeRows(manager, pasteStatus, pasteHelper, maxDepth);
			}
		} else {
			if (null != tableSelectionWrapper) {
				resultStatus = insertNormalRows(manager, pasteStatus, pasteHelper, tableSelectionWrapper);
			} else {
				resultStatus = insertNormalRows(manager, pasteStatus, pasteHelper);
			}
		}

		return resultStatus;
	}

	/**
	 * Insert the rows for a tree table with a current selection.
	 * 
	 * @param manager
	 *            The nattable manager.
	 * @param pasteStatus
	 *            The paste status.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param tableSelectionWrapper
	 *            The current selection.
	 * @return The status of the paste.
	 */
	private IStatus insertTreeRows(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper, final TableSelectionWrapper tableSelectionWrapper) {
		IStatus status = Status.OK_STATUS;
		if (status.isOK()) {
			status = insertTree(manager, pasteStatus, useProgressMonitorDialog, createReader(), tableSelectionWrapper, getDataSize());
		}
		return status;
	}

	/**
	 * Insert the rows for a tree table without selection.
	 * 
	 * @param manager
	 *            The nattable manager.
	 * @param pasteStatus
	 *            The paste status.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param maxDepth
	 *            The maximum depth to check.
	 * @return The status of the paste.
	 */
	private IStatus insertTreeRows(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper, final int maxDepth) {
		IStatus status = checkTreeTableConfiguration(manager, maxDepth);
		if (status.isOK()) {
			status = insertTree(manager, pasteStatus, useProgressMonitorDialog, createReader(), null, getDataSize());
		}
		return status;
	}

	/**
	 * This allow to insert rows in a flat nattable.
	 * 
	 * @param manager
	 *            The nattable model manager.
	 * @param pasteStatus
	 *            The paste status.
	 * @param pasteHelper
	 *            The paste helper.
	 * @return The status corresponding to the paste.
	 */
	private IStatus insertNormalRows(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper) {
		final Reader reader = createReader();
		IStatus status = verifyColumnCountOnFirstLine(manager, reader);
		try {
			reader.close();
		} catch (final IOException e) {
			Activator.log.error(e);
		}
		if (status.isOK()) {
			status = insert(manager, pasteStatus, useProgressMonitorDialog, createReader(), null, getDataSize());
		}
		return status;
	}

	/**
	 * This allow to insert rows in a flat nattable with selection
	 * 
	 * @param manager
	 *            The nattable model manager.
	 * @param pasteStatus
	 *            The paste status.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param tableSelectionWrapper
	 *            The current selection in table.
	 * @return The status corresponding to the paste.
	 */
	private IStatus insertNormalRows(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper, final TableSelectionWrapper tableSelectionWrapper) {
		final Reader reader = createReader();
		IStatus status = verifyColumnCountOnFirstLine(manager, reader);
		try {
			reader.close();
		} catch (final IOException e) {
			Activator.log.error(e);
		}
		if (status.isOK()) {
			status = insert(manager, pasteStatus, useProgressMonitorDialog, createReader(), tableSelectionWrapper, getDataSize());
		}
		return status;
	}

	/**
	 * Create the command provider for the insert in the tree table and execute the this command.
	 * 
	 * @param manager
	 *            the table manager
	 * @param status
	 *            the status
	 * @param useProgressMonitorDialog
	 *            if <code>true</code> a progress monitor will be used
	 * @param reader
	 *            the reader used to import data in the table.
	 * @param tableSelectionWrapper
	 *            The table selection wrapper for the current table.
	 * @param totalSize
	 *            the full size of the elements to import
	 * @return The result status
	 */
	private IStatus insertTree(final INattableModelManager manager, final PasteEnablementStatus status, final boolean useProgressMonitorDialog, final Reader reader, final TableSelectionWrapper tableSelectionWrapper, final long totalSize) {
		IStatus resultStatus = Status.OK_STATUS;

		PasteNattableCommandProvider commandProvider = null;
		switch (status.getPasteMode()) {
		case PASTE_EOBJECT_ROW:
			commandProvider = new PasteInSelectionTreeNattableCommandProvider(manager, false, true, reader, this.pasteHelper, this.tableSelectionWrapper, preferredUserAction, totalSize);
			break;
		default:
			break;
		}
		if (null != commandProvider) {
			resultStatus = commandProvider.executePasteFromStringCommand(useProgressMonitorDialog, openDialog);
		}
		return resultStatus;
	}

	/**
	 * Create the command provider for the insert in the flat table and execute the this command.
	 *
	 * @param manager
	 *            the table manager
	 * @param status
	 *            the status
	 * @param useProgressMonitorDialog
	 *            if <code>true</code> a progress monitor will be used
	 * @param reader
	 *            the reader used to import data in the table.
	 * @param tableSelectionWrapper
	 *            The table selection wrapper for the current table.
	 * @param totalSize
	 *            the full size of the elements to import
	 * @return The result status
	 */
	private IStatus insert(final INattableModelManager manager, final PasteEnablementStatus status, final boolean useProgressMonitorDialog, final Reader reader, final TableSelectionWrapper tableSelectionWrapper, final long totalSize) {
		IStatus resultStatus = Status.OK_STATUS;

		PasteNattableCommandProvider commandProvider = null;
		switch (status.getPasteMode()) {
		case PASTE_EOBJECT_ROW:
			commandProvider = new PasteInSelectionNattableCommandProvider(manager, false, true, reader, pasteHelper, tableSelectionWrapper, preferredUserAction, totalSize);
			break;
		default:
			break;
		}
		if (commandProvider != null) {
			resultStatus = commandProvider.executePasteFromStringCommand(useProgressMonitorDialog, openDialog);
		}
		return resultStatus;
	}
}
