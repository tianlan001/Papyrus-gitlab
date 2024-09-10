/*******************************************************************************
 * Copyright (c) 2017, 2019 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Thanh Liem PHAN (ALL4TEC) <thanhliem.phan@all4tec.net> - Bug 417095
 *     Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Bug 546965
 ******************************************************************************/
package org.eclipse.papyrus.infra.nattable.internal.export.image;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.export.ExportConfigAttributes;
import org.eclipse.nebula.widgets.nattable.export.IOutputStreamProvider;
import org.eclipse.nebula.widgets.nattable.export.ITableExporter;
import org.eclipse.nebula.widgets.nattable.export.NatExporter;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.emf.utils.ResourceUtils;
import org.eclipse.papyrus.infra.nattable.export.streamprovider.PapyrusFileOutputStreamProvider;
import org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager;
import org.eclipse.papyrus.infra.nattable.manager.table.TreeNattableModelManager;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.style.configattribute.PapyrusExportConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.swt.widgets.Shell;

/**
 * NatTable exporter to export image in Papyrus.
 *
 * <p>
 * Warning: Using this class is risky as it could cause severe damage to
 * Papyrus NatTables that show huge data sets (for example: a table with more than 20k
 * rows).
 * </p>
 */
public class PapyrusNatExporter extends NatExporter {

	/** The parent shell. */
	private final Shell shell;

	/**
	 * Default constructor.
	 *
	 * @param shell
	 *            The parent shell
	 */
	public PapyrusNatExporter(final Shell shell) {
		super(shell);
		this.shell = shell;
	}

	/**
	 * {@inheritDocs}
	 *
	 * Overridden to get file name and file format from config registry instead of the default file dialog in NatTable
	 * when the default NatTable dialog is not used.
	 */
	@Override
	public void exportSingleTable(final ILayer layer, final IConfigRegistry configRegistry) {
		this.openResult = configRegistry.getConfigAttribute(PapyrusExportConfigAttributes.OPEN_RESULT_AFTER_EXPORT, DisplayMode.NORMAL);
		final Boolean useNatTableFileDialog = configRegistry.getConfigAttribute(PapyrusExportConfigAttributes.EXPORT_IMAGE_USE_PAPYRUS_EXPORT_TABLE_DIALOG, DisplayMode.NORMAL);

		// If the Nattable file dialog is used, get parameters from the default file dialog to do the export
		// Otherwise, get them from the config registry instead
		if (useNatTableFileDialog) {
			setParameters(configRegistry);
			super.exportSingleTable(layer, configRegistry);
		} else {
			// Get the image exporter attribute from the config registry
			final ITableExporter imageExporter = configRegistry.getConfigAttribute(ExportConfigAttributes.TABLE_EXPORTER, DisplayMode.NORMAL);

			if (null == imageExporter) {
				return;
			}

			Runnable exportRunnable = new Runnable() {

				@Override
				public void run() {
					try {
						imageExporter.exportTable(PapyrusNatExporter.this.shell, null, null, layer, configRegistry);
						PapyrusNatExporter.this.exportSucceeded = true;
					} catch (Exception e) {
						PapyrusNatExporter.this.exportSucceeded = false;
						handleExportException(e);
					}

					openExport(imageExporter);
				}
			};

			if (null != this.shell) {
				// Run with the SWT display so that the progress bar can paint
				this.shell.getDisplay().asyncExec(exportRunnable);
			} else {
				exportRunnable.run();
			}
		}
	}

	/**
	 * Set parameters for the {@link PapyrusFileOutputStreamProvider}.
	 * The nattable, the current table name, the current project directory and the table type will be used to initialise the export dialog.
	 *
	 * @param configRegistry
	 *            The config registry
	 */
	protected void setParameters(final IConfigRegistry configRegistry) {
		final INattableModelManager modelManager = configRegistry.getConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
		if (null != modelManager) {
			// Get the table name to use as default export image name
			String tableName = modelManager.getTableName();

			// Then get the current directory resource as the default one to be used for exporting
			Table table = modelManager.getTable();
			IFile fileResource = ResourceUtils.getFile(table.eResource());
			IResource currentProjectDir = null;

			if (null != fileResource) {
				currentProjectDir = fileResource.getParent().findMember("."); //$NON-NLS-1$
			}

			// Determine if the table is a tree one
			boolean isTreeTable = modelManager instanceof TreeNattableModelManager;

			if (null != tableName && !tableName.isEmpty() && null != currentProjectDir) {
				// Get the image exporter attribute from the config registry
				final ITableExporter imageExporter = configRegistry.getConfigAttribute(ExportConfigAttributes.TABLE_EXPORTER, DisplayMode.NORMAL);

				if (imageExporter instanceof PapyrusImageExporter) {
					// Get the output stream provider from the PapyrusImageExporter
					IOutputStreamProvider outputStreamProvider = ((PapyrusImageExporter) imageExporter).getOutputStreamProvider();

					if (outputStreamProvider instanceof PapyrusFileOutputStreamProvider) {
						// Set parameters for the PapyrusFileOutputStreamProvider
						final NatTable natTable = modelManager.getAdapter(NatTable.class);
						((PapyrusFileOutputStreamProvider) outputStreamProvider).setParameters(natTable, tableName, currentProjectDir, isTreeTable);
					}
				}
			}
		}
	}
}
