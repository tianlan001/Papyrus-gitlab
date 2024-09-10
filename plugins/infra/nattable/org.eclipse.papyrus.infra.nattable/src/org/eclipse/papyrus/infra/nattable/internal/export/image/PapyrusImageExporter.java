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
package org.eclipse.papyrus.infra.nattable.internal.export.image;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.export.IOutputStreamProvider;
import org.eclipse.nebula.widgets.nattable.export.image.ImageExporter;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.export.image.ImageFormat;
import org.eclipse.papyrus.infra.nattable.export.streamprovider.PapyrusFileOutputStreamProvider;
import org.eclipse.papyrus.infra.nattable.style.configattribute.PapyrusExportConfigAttributes;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

/**
 * An {@link ImageExporter} to export a NatTable to image in Papyrus.
 *
 * <p>
 * Warning: Using this class is risky as it could cause severe damage to
 * Papyrus NatTables that show huge data sets (for example: a table with more than 20k
 * rows).
 * </p>
 */
public class PapyrusImageExporter extends ImageExporter {

	/** Flag indicates the usage of Papyrus ExportTableDialog to retrieve file path, file name and file extension. */
	private Boolean usePapyrusExportTableDialog = Boolean.TRUE;

	/** The current image file name. */
	private String currentImageName = null;

	/** The file output stream provider. */
	private IOutputStreamProvider outputStreamProvider = null;

	/**
	 * The default image name
	 */
	private static final String DEFAULT_IMAGE_NAME = "table_export.png"; //$NON-NLS-1$

	/**
	 * Default constructor.
	 */
	public PapyrusImageExporter() {
		// Use a custom file output stream provider, which permits to change the default image name
		this(new PapyrusFileOutputStreamProvider(DEFAULT_IMAGE_NAME, ImageFormat.IMAGE_FILTER_NAME_LIST, ImageFormat.IMAGE_FILTER_EXTENSION_LIST));
	}

	/**
	 * Constructor with an output stream provider.
	 *
	 * @param outputStreamProvider
	 *            The output stream provider
	 */
	public PapyrusImageExporter(final IOutputStreamProvider outputStreamProvider) {
		super(outputStreamProvider);
		this.outputStreamProvider = outputStreamProvider;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Overridden to use the file name and file extension defined in the {@link PapyrusExportConfigAttributes} for exporting image
	 * in case the default export table dialog is not used.
	 */
	@Override
	public void exportTable(final Shell shell, final ProgressBar progressBar, final OutputStream outputStream, final ILayer layer, final IConfigRegistry configRegistry) throws IOException {
		if (null == shell || null == layer || null == configRegistry) {
			throw new IllegalArgumentException("Shell, layer or configure registry must not be null"); //$NON-NLS-1$
		}

		this.usePapyrusExportTableDialog = configRegistry.getConfigAttribute(
				PapyrusExportConfigAttributes.EXPORT_IMAGE_USE_PAPYRUS_EXPORT_TABLE_DIALOG,
				DisplayMode.NORMAL);

		// If the ExportTableDialog is used, export table using the default behavior.
		// Otherwise, get the image name and image file type from the config registry to export
		if (this.usePapyrusExportTableDialog) {
			super.exportTable(shell, progressBar, outputStream, layer, configRegistry);
		} else {
			ImageFormat imageFormat = configRegistry.getConfigAttribute(
					PapyrusExportConfigAttributes.EXPORT_IMAGE_FORMAT,
					DisplayMode.NORMAL);

			this.currentImageName = configRegistry.getConfigAttribute(
					PapyrusExportConfigAttributes.EXPORT_IMAGE_FILENAME,
					DisplayMode.NORMAL);

			String imageFormatExtension = imageFormat.getImageFilterExtension();

			if (null != this.currentImageName && null != imageFormatExtension) {
				final int imageFormatIndex = getImageFormatIndex(imageFormatExtension);

				final int width = layer.getWidth();
				final int height = layer.getHeight();

				final Image image = new Image(shell.getDisplay(), width, height);
				GC gc = new GC(image);

				final Rectangle layerBounds = new Rectangle(0, 0, width, height);
				layer.getLayerPainter().paintLayer(layer, gc, 0, 0, layerBounds, configRegistry);

				final ImageLoader imageLoader = new ImageLoader();
				imageLoader.data = new ImageData[] { image.getImageData() };

				try {
					imageLoader.save(this.currentImageName, imageFormatIndex);
				} catch (IllegalArgumentException e) {
					Activator.log.error(e);
				} catch (SWTException e) {
					Activator.log.error(e);
				} finally {
					gc.dispose();
					image.dispose();
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 *
	 * Overidden to return the current image file as a result incase the default NatTable file dialog is not used.
	 */
	@Override
	public Object getResult() {
		if (this.usePapyrusExportTableDialog) {
			return super.getResult();
		} else {
			return (null != this.currentImageName) ? new File(this.currentImageName) : null;
		}
	}

	/**
	 * @return The output stream provider used in this exporter.
	 */
	public IOutputStreamProvider getOutputStreamProvider() {
		return this.outputStreamProvider;
	}
}
