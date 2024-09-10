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
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 476618
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
import org.eclipse.papyrus.infra.nattable.provider.PasteEObjectAxisInNattableCommandProvider;
import org.eclipse.papyrus.infra.nattable.provider.PasteEObjectTreeAxisInNattableCommandProvider;
import org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionNattableCommandProvider;
import org.eclipse.papyrus.infra.nattable.provider.PasteInSelectionTreeNattableCommandProvider;
import org.eclipse.papyrus.infra.nattable.provider.PasteNattableCommandProvider;
import org.eclipse.papyrus.infra.nattable.utils.CSVPasteHelper;
import org.eclipse.papyrus.infra.nattable.utils.IPapyrusNattableStatus;
import org.eclipse.papyrus.infra.nattable.utils.PapyrusNattableStatus;
import org.eclipse.papyrus.infra.nattable.utils.PasteEnablementStatus;
import org.eclipse.papyrus.infra.nattable.utils.PasteHelperUtils;
import org.eclipse.papyrus.infra.nattable.utils.PasteModeEnumeration;
import org.eclipse.papyrus.infra.nattable.utils.TableHelper;
import org.eclipse.papyrus.infra.nattable.utils.TableSelectionWrapper;
import org.eclipse.swt.widgets.Display;

/**
 * Abstract class for the copy/paste and import file in the table.
 */
public abstract class AbstractPasteImportInNattableManager extends AbstractPasteImportInsertInNattableManager {

	/**
	 * This allows to define if the paste is an overwrite or a basic paste EOBject.
	 */
	protected boolean isPasteWithOverwrite;

	/**
	 * This boolean allows to determinate if the paste with overwrite was already calculated or not and prevent another calculation is this was already done.
	 */
	private boolean isPasteWithOverwriteAlreadyCalculated = false;

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
	public AbstractPasteImportInNattableManager(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final boolean useProgressMonitorDialog, final boolean openDialog, final int preferredUserAction) {
		super(tableManager, pasteHelper, useProgressMonitorDialog, openDialog, preferredUserAction);
		this.isPasteWithOverwrite = false;
		this.isPasteWithOverwriteAlreadyCalculated = false;
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
	public AbstractPasteImportInNattableManager(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final boolean useProgressMonitorDialog, final boolean openDialog, final int preferredUserAction,
			final TableSelectionWrapper tableSelectionWrapper) {
		super(tableManager, pasteHelper, useProgressMonitorDialog, openDialog, preferredUserAction, tableSelectionWrapper);
		this.isPasteWithOverwrite = false;
		this.isPasteWithOverwriteAlreadyCalculated = false;
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
	 * @param isPasteWithOverwrite
	 *            Boolean to determinate if this is a paste with overwrite.
	 */
	public AbstractPasteImportInNattableManager(final INattableModelManager tableManager, final CSVPasteHelper pasteHelper, final boolean useProgressMonitorDialog, final boolean openDialog, final int preferredUserAction,
			final TableSelectionWrapper tableSelectionWrapper, final boolean isPasteWithOverwrite) {
		super(tableManager, pasteHelper, useProgressMonitorDialog, openDialog, preferredUserAction, tableSelectionWrapper);
		this.isPasteWithOverwrite = isPasteWithOverwrite;
		this.isPasteWithOverwriteAlreadyCalculated = true;
	}

	/**
	 * This method manages the paste and the possible error dialog before doing the paste.
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.AbstractPasteImportInsertInNattableManager#doAction()
	 */
	@Override
	public IStatus doAction() {
		IStatus resultStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteImportInNattableManager_PasteNotYetManaged);

		PasteEnablementStatus pasteStatus = null;

		int maxDepth = Integer.MAX_VALUE;

		// Check if the paste configuration is needed for the paste action
		boolean useElse = true;
		if (!isNeededPasteConfiguration()) {
			if (!isPasteWithOverwriteAlreadyCalculated) {
				isPasteWithOverwrite = PasteHelperUtils.isPasteWithOverwrite(tableManager, pasteHelper, createReader());
			}
			if (isPasteWithOverwrite) {
				final MultiStatus status = new MultiStatus(Activator.PLUGIN_ID, IStatus.OK, Messages.AbstractPasteImportInsertInNattableManager_NoPasteConfigurationNeeded, null);
				if (null == tableSelectionWrapper) {
					// Check the configuration if no selection is available
					pasteStatus = findPasteModeFromTableConfiguration(this.tableManager);
					if (PasteModeEnumeration.PASTE_NO_CONFIGURATION != pasteStatus.getPasteMode() && PasteModeEnumeration.CANT_PASTE != pasteStatus.getPasteMode()) {
						pasteStatus = findPasteConfigurationAxisIdentifier(this.tableManager);
					}
				} else {
					// If the paste is a paste overwrite on columns, no need to check the paste configuration
					if (!tableSelectionWrapper.getFullySelectedColumns().isEmpty()) {
						status.add(new Status(IStatus.OK, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_NoPasteConfigurationNeeded));
						pasteStatus = new PasteEnablementStatus(status, null);
						// If the paste is a paste overwrite on rows, check the paste configuration only for axis identifier
					} else if (!tableSelectionWrapper.getFullySelectedRows().isEmpty()) {
						pasteStatus = findPasteConfigurationAxisIdentifier(this.tableManager);
						// If the paste is a paste overwrite on cells, no need to check the paste configuration
					} else {
						status.add(new Status(IStatus.OK, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_NoPasteConfigurationNeeded));
						pasteStatus = new PasteEnablementStatus(null, status);
					}
				}
				useElse = false;
			}
		}

		if (useElse) {
			if(TableHelper.isTreeTable(tableManager)){
				maxDepth = PasteHelperUtils.getMaxDepthToPaste(tableManager, pasteHelper, createReader());
			}
			pasteStatus = findPasteModeFromTableConfiguration(this.tableManager);
			if (pasteStatus.getPasteMode() == PasteModeEnumeration.PASTE_EOBJECT_ROW_OR_COLUMN) {
				final boolean value = MessageDialog.openQuestion(Display.getDefault().getActiveShell(), PasteImportStatusDialog.DIALOG_TITLE, Messages.AbstractPasteImportInsertInNattableManager_WhatAreYouPasting);
				if (value) {
					pasteStatus.getColumnStatus().add(new Status(IStatus.CANCEL, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_TheUserChosesToPasteRows));
				} else {
					pasteStatus.getRowStatus().add(new Status(IStatus.CANCEL, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_TheUserChosesToPasteColumns));
				}
			}
		}

		final PasteModeEnumeration pasteMode = pasteStatus.getPasteMode();
		switch (pasteMode) {
		case PASTE_NO_CONFIGURATION:
			resultStatus = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_INFO, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_NoPasteConfiguration2);
			break;
		case CANT_PASTE:
			resultStatus = createCantPasteStatus(pasteStatus);
			break;
		case PASTE_EOBJECT_ROW:
			resultStatus = pasteRow(this.tableManager, pasteStatus, pasteHelper, maxDepth);
			break;
		case PASTE_EOBJECT_COLUMN:
			resultStatus = pasteColumn(this.tableManager, pasteStatus, pasteHelper);
			break;
		case PASTE_EOBJECT_ROW_OR_COLUMN:
			// nothing to do
			break;
		default:
			break;
		}

		return resultStatus;
	}

	/**
	 * Some paste actions don't need paste configuration. This methos allows to determinate if a paste configuration is needed for the paste action.
	 * 
	 * @return <code>true</code> if a paste configuration is needed, <code>false</code> otherwise.
	 */
	protected boolean isNeededPasteConfiguration() {
		boolean result = true;

		if (null == tableSelectionWrapper
				|| ((tableSelectionWrapper.getFullySelectedColumns().isEmpty() && tableSelectionWrapper.getFullySelectedRows().isEmpty() && !tableSelectionWrapper.getSelectedCells().isEmpty())
						|| (!tableSelectionWrapper.getFullySelectedColumns().isEmpty() && tableSelectionWrapper.getFullySelectedRows().isEmpty())
						|| (tableSelectionWrapper.getFullySelectedColumns().isEmpty() && !tableSelectionWrapper.getFullySelectedRows().isEmpty()))) {
			result = false;
		}

		return result;
	}

	/**
	 * Manage the status when can't paste.
	 * 
	 * @param pasteStatus
	 *            The paste status.
	 * @return The status when this can't be pasted
	 */
	protected IStatus createCantPasteStatus(final PasteEnablementStatus pasteStatus) {
		IStatus resultStatus = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_INFO, Activator.PLUGIN_ID, Messages.AbstractPasteImportInsertInNattableManager_NoPasteConfiguration2);

		final MultiStatus rowStatus = pasteStatus.getRowStatus();
		final MultiStatus columnStatus = pasteStatus.getColumnStatus();
		if (rowStatus == null && columnStatus != null) {
			resultStatus = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, getFirstNonOKStatus(columnStatus).getMessage());
		} else if (columnStatus == null && rowStatus != null) {
			resultStatus = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, getFirstNonOKStatus(rowStatus).getMessage());
		} else if (columnStatus != null && rowStatus != null) {
			final StringBuffer buffer = new StringBuffer(Messages.AbstractPasteImportInsertInNattableManager_NoPasteConfiguration);
			buffer.append(Messages.AbstractPasteImportInNattableManager_PasteRowsError);
			buffer.append(getFirstNonOKStatus(rowStatus).getMessage());
			buffer.append(Messages.AbstractPasteImportInNattableManager_PasteColumnsError);
			buffer.append(getFirstNonOKStatus(columnStatus).getMessage());
			resultStatus = new PapyrusNattableStatus(IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR, Activator.PLUGIN_ID, buffer.toString());
		}

		return resultStatus;
	}

	/**
	 * Paste the column.
	 *
	 * @param manager
	 *            the table manager
	 * @param pasteStatus
	 *            the paste status
	 * @param pasteHelper
	 *            the paste helper
	 * @return
	 *         <code>true</code> if the paste can be done
	 */
	private IStatus pasteColumn(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper) {
		IStatus resultStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.AbstractPasteImportInNattableManager_PasteNotYetManaged);
		if (null != tableSelectionWrapper) {
			resultStatus = pasteColumns(manager, pasteStatus, pasteHelper, tableSelectionWrapper);
		}
		return resultStatus;
	}

	/**
	 * Paste the row.
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
	private IStatus pasteRow(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper, final int maxDepth) {
		IStatus resultStatus = Status.OK_STATUS;

		if (TableHelper.isTreeTable(manager)) {
			if (null != tableSelectionWrapper) {
				resultStatus = pasteTreeRows(manager, pasteStatus, pasteHelper, tableSelectionWrapper);
			} else {
				resultStatus = pasteTreeRows(manager, pasteStatus, pasteHelper, maxDepth);
			}
		} else {
			if (null != tableSelectionWrapper) {
				resultStatus = pasteNormalRows(manager, pasteStatus, pasteHelper, tableSelectionWrapper);
			} else {
				resultStatus = pasteNormalRows(manager, pasteStatus, pasteHelper);
			}
		}

		return resultStatus;
	}

	/**
	 * Paste the rows for a tree table with a current selection.
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
	private IStatus pasteTreeRows(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper, final TableSelectionWrapper tableSelectionWrapper) {
		IStatus status = Status.OK_STATUS;
		if (status.isOK()) {
			status = pasteTree(manager, pasteStatus, useProgressMonitorDialog, createReader(), tableSelectionWrapper, getDataSize());
		}
		return status;
	}

	/**
	 * Paste the rows for a tree table without selection.
	 * 
	 * @param manager
	 *            The nattable manager.
	 * @param pasteStatus
	 *            The paste status.
	 * @param pasteHelper
	 *            The paste helper.
	 * @param tableSelectionWrapper
	 *            The current selection.
	 * @param maxDepth
	 *            The maximum depth to check.
	 * @return The status of the paste.
	 */
	private IStatus pasteTreeRows(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper, final int maxDepth) {
		IStatus status = checkTreeTableConfiguration(manager, maxDepth);
		if (status.isOK()) {
			status = pasteTree(manager, pasteStatus, useProgressMonitorDialog, createReader(), null, getDataSize());
		}
		return status;
	}

	/**
	 * This allow to paste rows in a flat nattable.
	 * 
	 * @param manager
	 *            The nattable model manager.
	 * @param pasteStatus
	 *            The paste status.
	 * @param pasteHelper
	 *            The paste helper.
	 * @return The status corresponding to the paste.
	 */
	private IStatus pasteNormalRows(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper) {
		final Reader reader = createReader();
		IStatus status = verifyColumnCountOnFirstLine(manager, reader);
		try {
			reader.close();
		} catch (final IOException e) {
			Activator.log.error(e);
		}
		if (status.isOK()) {
			status = paste(manager, pasteStatus, useProgressMonitorDialog, createReader(), null, getDataSize());
		}
		return status;
	}

	/**
	 * This allow to paste rows in a flat nattable with selection
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
	private IStatus pasteNormalRows(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper, final TableSelectionWrapper tableSelectionWrapper) {
		final Reader reader = createReader();
		IStatus status = verifyColumnCountOnFirstLine(manager, reader);
		try {
			reader.close();
		} catch (final IOException e) {
			Activator.log.error(e);
		}
		if (status.isOK()) {
			status = paste(manager, pasteStatus, useProgressMonitorDialog, createReader(), tableSelectionWrapper, getDataSize());
		}
		return status;
	}

	/**
	 * Paste the columns for a table with a current selection.
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
	private IStatus pasteColumns(final INattableModelManager manager, final PasteEnablementStatus pasteStatus, final CSVPasteHelper pasteHelper, final TableSelectionWrapper tableSelectionWrapper) {
		IStatus status = Status.OK_STATUS;

		if (TableHelper.isTreeTable(manager)) {
			status = pasteTree(manager, pasteStatus, useProgressMonitorDialog, createReader(), tableSelectionWrapper, getDataSize());
		} else {
			status = paste(manager, pasteStatus, useProgressMonitorDialog, createReader(), tableSelectionWrapper, getDataSize());
		}

		return status;
	}

	/**
	 * Create the command provider for the paste in the tree table and execute the this command.
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
	private IStatus pasteTree(final INattableModelManager manager, final PasteEnablementStatus status, final boolean useProgressMonitorDialog, final Reader reader, final TableSelectionWrapper tableSelectionWrapper, final long totalSize) {
		IStatus resultStatus = Status.OK_STATUS;

		PasteNattableCommandProvider commandProvider = null;

		if (!isPasteWithOverwrite) {
			switch (status.getPasteMode()) {
			case PASTE_EOBJECT_ROW:
				commandProvider = new PasteEObjectTreeAxisInNattableCommandProvider(manager, false, reader, this.pasteHelper, totalSize);
				break;
			default:
				break;
			}
		} else if (null != tableSelectionWrapper) {
			switch (status.getPasteMode()) {
			case PASTE_EOBJECT_ROW:
				commandProvider = new PasteInSelectionTreeNattableCommandProvider(manager, false, false, reader, this.pasteHelper, this.tableSelectionWrapper, this.preferredUserAction, totalSize);
				break;
			case PASTE_EOBJECT_COLUMN:
				commandProvider = new PasteInSelectionTreeNattableCommandProvider(manager, true, false, reader, this.pasteHelper, this.tableSelectionWrapper, this.preferredUserAction, totalSize);
				break;
			default:
				break;
			}
		} else {
			commandProvider = new PasteInSelectionTreeNattableCommandProvider(manager, PasteModeEnumeration.PASTE_EOBJECT_COLUMN.equals(status.getPasteMode()), true, reader, this.pasteHelper, this.tableSelectionWrapper, this.preferredUserAction, totalSize);
		}

		if (null != commandProvider) {
			resultStatus = commandProvider.executePasteFromStringCommand(useProgressMonitorDialog, openDialog);
		}
		return resultStatus;
	}

	/**
	 * Create the command provider for the paste in the flat table and execute the this command.
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
	private IStatus paste(final INattableModelManager manager, final PasteEnablementStatus status, final boolean useProgressMonitorDialog, final Reader reader, final TableSelectionWrapper tableSelectionWrapper, final long totalSize) {
		IStatus resultStatus = Status.OK_STATUS;

		PasteNattableCommandProvider commandProvider = null;

		if (!isPasteWithOverwrite) {
			switch (status.getPasteMode()) {
			case PASTE_EOBJECT_ROW:
				commandProvider = new PasteEObjectAxisInNattableCommandProvider(manager, false, reader, this.pasteHelper, totalSize);
				break;
			default:
				break;
			}
		} else if (null != tableSelectionWrapper) {
			switch (status.getPasteMode()) {
			case PASTE_EOBJECT_ROW:
				commandProvider = new PasteInSelectionNattableCommandProvider(manager, false, false, reader, pasteHelper, tableSelectionWrapper, this.preferredUserAction, totalSize);
				break;
			case PASTE_EOBJECT_COLUMN:
				commandProvider = new PasteInSelectionNattableCommandProvider(manager, true, false, reader, pasteHelper, tableSelectionWrapper, this.preferredUserAction, totalSize);
				break;
			default:
				break;
			}
		} else {
			commandProvider = new PasteInSelectionNattableCommandProvider(manager, PasteModeEnumeration.PASTE_EOBJECT_COLUMN.equals(status.getPasteMode()), true, reader, pasteHelper, tableSelectionWrapper, this.preferredUserAction, totalSize);
		}

		if (commandProvider != null) {
			resultStatus = commandProvider.executePasteFromStringCommand(useProgressMonitorDialog, openDialog);
		}
		return resultStatus;
	}
}
