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
package org.eclipse.papyrus.infra.nattable.export.streamprovider;

import java.io.OutputStream;
import java.io.PrintStream;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.window.Window;
import org.eclipse.nebula.widgets.nattable.Messages;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.export.FileOutputStreamProvider;
import org.eclipse.nebula.widgets.nattable.tree.command.TreeCollapseAllCommand;
import org.eclipse.nebula.widgets.nattable.tree.command.TreeExpandAllCommand;
import org.eclipse.papyrus.infra.nattable.dialog.ExportTableDialog;
import org.eclipse.papyrus.infra.nattable.enums.TreeTableAction;
import org.eclipse.papyrus.infra.nattable.export.image.ImageFormat;
import org.eclipse.swt.widgets.Shell;

/**
 * A custom file output stream provider used in Papyrus.
 * It uses a custom {@link ExportTableDialog} instead of the default one.
 * It provides a method to set parameters for the {@link ExportTableDialog}.
 */
public class PapyrusFileOutputStreamProvider extends FileOutputStreamProvider {

	/** The natTable used to do the expand/collapse action. */
	protected NatTable natTable = null;

	/** The default output directory resource. */
	protected IResource defaultOutputDir = null;

	/** Flag to indicate that the table is a tree one. */
	protected boolean isTreeTable = false;

	/**
	 * Default constructor.
	 *
	 * @param defaultFileName
	 *            The default file name
	 * @param defaultFilterNames
	 *            The default filter names
	 * @param defaultFilterExtensions
	 *            The default filter extensions
	 */
	public PapyrusFileOutputStreamProvider(final String defaultFileName, final String[] defaultFilterNames, final String[] defaultFilterExtensions) {
		super(defaultFileName, defaultFilterNames, defaultFilterExtensions);
	}

	/**
	 * Open an {@link ExportTableDialog} to let a user choose the location to write the
	 * export to, and return the corresponding {@link PrintStream} to that file.
	 */
	@Override
	public OutputStream getOutputStream(Shell shell) {
		ExportTableDialog dialog = new ExportTableDialog(shell, this.defaultOutputDir, this.defaultFileName, ImageFormat.IMAGE_EXTENSION_LIST, this.isTreeTable);

		// Reset the current file name and the extension filter index each time the ExportTableDialog is opened
		// to avoid the case that if the dialog is cancelled, the old values could be accidentally reused
		this.currentFileName = null;
		this.extFilterIndex = -1;

		if (Window.OK == dialog.open()) {
			this.currentFileName = dialog.getExportedFileName();
			this.extFilterIndex = dialog.getFilterIndex();
		}

		if (null == this.currentFileName) {
			return null;
		}

		if (isTreeTable && null != this.natTable) {
			// Get the selected action from the dialog
			TreeTableAction tableAction = TreeTableAction.getAction(dialog.getSelectedTreeAction());

			// Expand or collapse the table depends on the user's choice
			if (TreeTableAction.EXPAND.equals(tableAction)) {
				this.natTable.doCommand(new TreeExpandAllCommand());
			} else if (TreeTableAction.COLLAPSE.equals(tableAction)) {
				this.natTable.doCommand(new TreeCollapseAllCommand());
			}
		}

		try {
			return new PrintStream(this.currentFileName);
		} catch (Exception e) {
			throw new RuntimeException(
					Messages.getString("FileOutputStreamProvider.errorMessage", this.currentFileName), e); //$NON-NLS-1$
		}
	}

	/**
	 * Set parameters for this output stream provider.
	 *
	 * @param natTable
	 *            The natTable used to do the expand or collapse command
	 * @param defaultFileName
	 *            The default file name to be set
	 * @param defaultOutputDir
	 *            The default output directory resource
	 * @param isTreeTable
	 *            Flag to indicate that the table is a tree one
	 */
	public void setParameters(final NatTable natTable, final String defaultFileName, final IResource defaultOutputDir, final boolean isTreeTable) {
		this.natTable = natTable;
		this.defaultFileName = defaultFileName;
		this.defaultOutputDir = defaultOutputDir;
		this.isTreeTable = isTreeTable;
	}
}
