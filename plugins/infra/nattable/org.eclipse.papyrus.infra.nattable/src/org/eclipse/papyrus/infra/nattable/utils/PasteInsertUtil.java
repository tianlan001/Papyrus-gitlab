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

package org.eclipse.papyrus.infra.nattable.utils;

import java.util.Map;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.dialog.PasteImportStatusDialog;
import org.eclipse.papyrus.infra.nattable.manager.AbstractPasteImportInsertInNattableManager;
import org.eclipse.papyrus.infra.nattable.manager.InsertInNattableManager;
import org.eclipse.papyrus.infra.nattable.manager.PasteAxisInNattableManager;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.provider.TableStructuredSelection;
import org.eclipse.papyrus.infra.widgets.util.ImageConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * This allows to manage paste or insert util methods.
 */
public class PasteInsertUtil {

	/**
	 * This field is used to determine if we want open a dialog to prevent the user that the command creation and the command execution can take a
	 * long time
	 */
	private final static boolean useProgressMonitorDialog = true;

	/**
	 * This allows to paste.
	 * 
	 * @param currentNattableModelManager
	 *            The nattable model manager.
	 * @param currentSelection
	 *            The current selection.
	 * @param parameters
	 *            The parameters.
	 * @return The result of the paste.
	 */
	public static Object paste(final INattableModelManager currentNattableModelManager, final ISelection currentSelection, final Map<Object, Object> parameters) {
		return doAction(currentNattableModelManager, currentSelection, parameters, true);
	}

	/**
	 * This allows to insert.
	 * 
	 * @param currentNattableModelManager
	 *            The nattable model manager.
	 * @param currentSelection
	 *            The current selection.
	 * @param parameters
	 *            The parameters.
	 * @return The result of the insert.
	 */
	public static Object insert(final INattableModelManager currentNattableModelManager, final ISelection currentSelection, final Map<Object, Object> parameters) {
		return doAction(currentNattableModelManager, currentSelection, parameters, false);
	}

	/**
	 * This allows to paste or insert.
	 * 
	 * @param currentNattableModelManager
	 *            The nattable model manager.
	 * @param currentSelection
	 *            The current selection.
	 * @param parameters
	 *            The parameters.
	 * @param isPaste
	 *            <code>true</code> if this is a paste, <code>false</code> otherwise.
	 * @return The result of the action.
	 */
	protected static Object doAction(final INattableModelManager currentNattableModelManager, final ISelection currentSelection, final Map<Object, Object> parameters, final boolean isPaste) {
		final CSVPasteHelper pasteHelper = new CSVPasteHelper();

		boolean openProgressMonitor = useProgressMonitorDialog;
		final Object value = parameters.get(AbstractPasteInsertInTableHandler.OPEN__PROGRESS_MONITOR_DIALOG);
		if (value instanceof Boolean) {
			openProgressMonitor = ((Boolean) value).booleanValue();
		}

		// Try to get the selection in the nattable editor
		TableSelectionWrapper tableSelectionWrapper = null;
		if (currentSelection instanceof TableStructuredSelection) {
			tableSelectionWrapper = (TableSelectionWrapper) ((TableStructuredSelection) currentSelection).getAdapter(TableSelectionWrapper.class);
			if (tableSelectionWrapper.getSelectedCells().isEmpty()) {
				tableSelectionWrapper = null;
			}
		}

		// Calculate if the dialog must be opened during the process
		final Object res = parameters.get(AbstractPasteInsertInTableHandler.OPEN_DIALOG_ON_FAIL_BOOLEAN_PARAMETER);
		final boolean openDialog = ((null == res) || Boolean.TRUE.equals(res));

		final Object userAction = parameters.get(AbstractPasteInsertInTableHandler.USER_ACTION__PREFERRED_USER_ACTION);
		final int preferredUserAction = null == userAction ? UserActionConstants.UNDEFINED_USER_ACTION : Integer.parseInt(userAction.toString());

		final Object textToPaste = parameters.get(AbstractPasteInsertInTableHandler.TEXT_TO_PASTE);
		final String clipboardContentsAsString = null != textToPaste ? (String) textToPaste : TableClipboardUtils.getClipboardContentsAsString();

		IStatus result = null;
		if (null != clipboardContentsAsString && !clipboardContentsAsString.isEmpty()) {
			AbstractPasteImportInsertInNattableManager pasteManager = null;
			if (isPaste) {
				pasteManager = new PasteAxisInNattableManager(currentNattableModelManager, pasteHelper, openProgressMonitor, openDialog, preferredUserAction, tableSelectionWrapper, clipboardContentsAsString);
			} else {
				pasteManager = new InsertInNattableManager(currentNattableModelManager, pasteHelper, openProgressMonitor, openDialog, preferredUserAction, tableSelectionWrapper, clipboardContentsAsString);
			}
			result = pasteManager.doAction();
		} else {
			result = new Status(IStatus.ERROR, Activator.PLUGIN_ID, Messages.PasteImportHandler_EmptyClipboardString);
		}

		// Manage different types of dialog error depending of type error
		if (openDialog) {
			displayDialog(result);
		}
		return result;
	}

	/**
	 * This allows to display the error message when this is a paste configuration error type.
	 * 
	 * @param result
	 *            the result status of the paste process
	 */
	protected static void displayDialog(final IStatus result) {
		if (null != result) {
			// If the error is caracterized by the paste configuration error status, use the dialog concerning the paste configuration error
			if (isPasteConfigurationStatus(result)) {
				new PasteImportStatusDialog(Display.getDefault().getActiveShell(), result).open();
				// If this is not a paste configuration error status, open an error status to display the problem
			} else if (IStatus.OK != result.getSeverity()) {
				String messageDialog = null;
				switch (result.getSeverity()) {
				case IStatus.INFO:
					messageDialog = Messages.PasteInTableHandler_PasteInformation;
					break;
				case IStatus.WARNING:
					messageDialog = Messages.PasteInTableHandler_PasteWarning;
					break;
				case IStatus.ERROR:
					messageDialog = Messages.PasteInTableHandler_PasteError;
					break;
				case IStatus.CANCEL:
					messageDialog = Messages.PasteInTableHandler_PasteCancelled;
					break;
				}

				final ErrorDialog errorDialog = new ErrorDialog(Display.getDefault().getActiveShell(), Messages.PasteImportStatusDialog_ImportPasteDialogTitle, messageDialog, result, IStatus.OK
						| IStatus.INFO | IStatus.WARNING | IStatus.ERROR) {

					/**
					 * Redefine this method to manage the papyrus icon in shell.
					 * {@inheritDoc}
					 * 
					 * @see org.eclipse.jface.dialogs.ErrorDialog#configureShell(org.eclipse.swt.widgets.Shell)
					 */
					@Override
					protected void configureShell(Shell shell) {
						super.configureShell(shell);
						shell.setImage(org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(ImageConstants.PAPYRUS_ICON_PATH));
					}
				};
				errorDialog.open();
			}
		}
	}

	/**
	 * This allows to determinate if the status is a paste configuration error status.
	 * 
	 * @param status
	 *            the status
	 * @return <code>true </code> if the status is a paste configuration error status, <code>false</code> otherwise.
	 */
	protected static boolean isPasteConfigurationStatus(final IStatus status) {
		boolean result = false;
		switch (status.getSeverity()) {
		case IPapyrusNattableStatus.PASTE_CONFIGURATiON_INFO:
		case IPapyrusNattableStatus.PASTE_CONFIGURATiON_WARNING:
		case IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR:
			result = true;
			break;
		default:
			result = false;
			break;
		}
		return result;
	}

}
