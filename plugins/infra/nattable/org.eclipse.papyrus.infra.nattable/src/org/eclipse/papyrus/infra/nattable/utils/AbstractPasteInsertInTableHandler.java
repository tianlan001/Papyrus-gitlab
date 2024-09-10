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

package org.eclipse.papyrus.infra.nattable.utils;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.papyrus.infra.nattable.dialog.PasteImportStatusDialog;
import org.eclipse.papyrus.infra.nattable.handler.AbstractTableHandler;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.papyrus.infra.widgets.util.ImageConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * The abstract handler for the paste and insert handler.
 */
public abstract class AbstractPasteInsertInTableHandler extends AbstractTableHandler {

	/**
	 * The variable name to determinate if the final dialog must be opened at the end of the paste.
	 */
	public static final String OPEN_DIALOG_ON_FAIL_BOOLEAN_PARAMETER = "openDialogOnFail"; //$NON-NLS-1$

	/**
	 * The variable name to determinate if the paste action must use the progress monitor;
	 */
	public static final String OPEN__PROGRESS_MONITOR_DIALOG = "openProgressMonitorDialog"; //$NON-NLS-1$
	
	/**
	 * The variable name to determinate the text to paste
	 */
	public static final String TEXT_TO_PASTE = "textToPaste"; //$NON-NLS-1$

	/**
	 * The variable name to determinate the preferred user action to use for the row insert action when he is not determinate.
	 */
	public static final String USER_ACTION__PREFERRED_USER_ACTION = "preferredUserAction"; //$NON-NLS-1$

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	@Override
	public abstract Object execute(ExecutionEvent event) throws ExecutionException;

	/**
	 * This allows to display the error message when this is a paste configuration error type.
	 * 
	 * @param result
	 *            the result status of the paste process
	 * @deprecated since 2.0
	 */
	@Deprecated
	protected void displayDialog(final IStatus result) {
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
						shell.setImage(Activator.getDefault().getImage(ImageConstants.PAPYRUS_ICON_PATH));
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
	 * @deprecated since 2.0
	 */
	@Deprecated
	protected boolean isPasteConfigurationStatus(final IStatus status) {
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
