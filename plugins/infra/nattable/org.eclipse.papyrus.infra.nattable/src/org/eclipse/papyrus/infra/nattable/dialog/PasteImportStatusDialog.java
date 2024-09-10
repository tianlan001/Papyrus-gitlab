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
 *	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *	Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 476618
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.dialog;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.papyrus.infra.nattable.messages.Messages;
import org.eclipse.papyrus.infra.nattable.utils.IPapyrusNattableStatus;
import org.eclipse.papyrus.infra.widgets.util.ImageConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;


/**
 * This dialog is used to display paste error status when the error is a paste configuration.
 */
public class PasteImportStatusDialog extends MessageDialog {

	/**
	 * Message displayed in the paste dialog
	 */
	public static final String HOW_TO_PASTE_MESSAGE = Messages.PasteImportStatusDialog_PasteConfigurationMessage;

	/**
	 * Title for all dialog used by this class
	 */
	public static final String DIALOG_TITLE = Messages.PasteImportStatusDialog_ImportPasteDialogTitle;


	/**
	 * Constructor.
	 *
	 * @param parentShell
	 *            the shell parent to use
	 * @param status
	 *            the status of the process
	 */
	public PasteImportStatusDialog(final Shell parentShell, final IStatus status) {
		super(parentShell, DIALOG_TITLE, getPapyrusIcon(), status.getMessage(), getDialogImageType(status), new String[] { IDialogConstants.OK_LABEL }, 0);
	}

	/**
	 * Constructor.
	 *
	 * @param parentShell
	 *            the shell parent to use
	 * @param dialogMessage
	 *            the message to display
	 */
	public PasteImportStatusDialog(final Shell parentShell, final String dialogMessage) {
		super(parentShell, DIALOG_TITLE, getPapyrusIcon(), dialogMessage, MessageDialog.ERROR, new String[] { IDialogConstants.OK_LABEL }, 0);
	}

	/**
	 * Constructor.
	 *
	 * @param parentShell
	 *            the shell parent to use
	 * @param dialogMessage
	 *            the message to display
	 * @param dialogImageType
	 *            the dialog image type
	 */
	public PasteImportStatusDialog(final Shell parentShell, final String dialogMessage, final int dialogImageType) {
		super(parentShell, DIALOG_TITLE, getPapyrusIcon(), dialogMessage, dialogImageType, new String[] { IDialogConstants.OK_LABEL }, 0);
	}


	/**
	 * Get the dialog image.
	 * 
	 * @param status
	 *            the status.
	 * @return the dialog image.
	 */
	protected static int getDialogImageType(final IStatus status) {
		int dialogImage = 0;
		switch (status.getSeverity()) {
		case IPapyrusNattableStatus.PASTE_CONFIGURATiON_ERROR:
		case IStatus.ERROR:
			dialogImage = MessageDialog.ERROR;
			break;
		case IPapyrusNattableStatus.PASTE_CONFIGURATiON_INFO:
		case IStatus.INFO:
			dialogImage = MessageDialog.INFORMATION;
			break;
		case IPapyrusNattableStatus.PASTE_CONFIGURATiON_WARNING:
		case IStatus.WARNING:
			dialogImage = MessageDialog.WARNING;
			break;
		case IStatus.CANCEL:
			break;
		case IStatus.OK:
			break;
		default:
			break;
		}
		return dialogImage;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.dialogs.MessageDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	protected Control createDialogArea(final Composite parent) {
		final Composite comp = (Composite) super.createDialogArea(parent);
		final CLabel label = new CLabel(comp, SWT.NONE);
		label.setText(HOW_TO_PASTE_MESSAGE);
		label.setImage(getInfoImage());
		return comp;
	}

	/**
	 * Get the papyrus icon.
	 * 
	 * @return
	 * 		the papyrus icon
	 */
	private static final Image getPapyrusIcon() {
		return org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(ImageConstants.PAPYRUS_ICON_PATH);
	}

}
