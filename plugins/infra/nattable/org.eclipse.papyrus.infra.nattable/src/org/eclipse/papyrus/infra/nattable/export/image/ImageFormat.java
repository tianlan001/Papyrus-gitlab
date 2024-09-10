/*****************************************************************************
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
 *      Thanh Liem PHAN (ALL4TEC) <thanhliem.phan@all4tec.net> - Bug 417095
 *      Vincent LORENZO (CEA-LIST) vincent.lorenzo@cea.fr - Bug 546966 (add to API)
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.export.image;

/**
 * Enumeration that is used to configure the image format.
 *
 * @since 6.2
 */
public enum ImageFormat {

	BMP("bmp", "BMP files (*.bmp)", "*.bmp"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	JPG("jpg", "JPG files (*.jpg)", "*.jpg"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	JPEG("jpeg", "JPEG files (*.jpeg)", "*.jpeg"), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	PNG("png", "PNG files (*.png)", "*.png"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

	/** The list of all image extensions. */
	public static final String[] IMAGE_EXTENSION_LIST = { PNG.getImageExtension(), BMP.getImageExtension(), JPG.getImageExtension(), JPEG.getImageExtension() };

	/** The list of all image filter names. */
	public static final String[] IMAGE_FILTER_NAME_LIST = { PNG.getImageFilterName(), BMP.getImageFilterName(), JPG.getImageFilterName(), JPEG.getImageFilterName() };

	/** The list of all image filter extensions. */
	public static final String[] IMAGE_FILTER_EXTENSION_LIST = { PNG.getImageFilterExtension(), BMP.getImageFilterExtension(), JPG.getImageFilterExtension(), JPEG.getImageFilterExtension() };


	/**
	 * an image extension
	 */
	private final String imageExtension;

	/**
	 * the filter name for the extension
	 */
	private final String imageExtensionFilterName;

	/**
	 * the filter to use to find files with a such extension
	 */
	private final String imageFilterExtension;


	/**
	 *
	 * Constructor.
	 *
	 * @param imageExtension
	 *            the extension of the image
	 * @param imageExtensionFilterName
	 *            the filter's name for this extension
	 * @param imageFilterExtension
	 *            the filter to use to find this extension
	 */
	private ImageFormat(final String imageExtension, final String imageExtensionFilterName, final String imageFilterExtension) {
		this.imageExtension = imageExtension;
		this.imageExtensionFilterName = imageExtensionFilterName;
		this.imageFilterExtension = imageFilterExtension;
	}

	/**
	 *
	 * @return
	 *         the image extension represented by the enum instance
	 */
	public final String getImageExtension() {
		return this.imageExtension;
	}

	/**
	 *
	 * @return
	 *         the filter name for the enum instance
	 */
	public final String getImageFilterName() {
		return this.imageExtensionFilterName;
	}

	/**
	 *
	 * @return
	 *         the image filter to use for the enum instance
	 */
	public final String getImageFilterExtension() {
		return this.imageFilterExtension;
	}

	/**
	 *
	 * @return
	 *         the default image format (png)
	 */
	public static final ImageFormat getDefaultImageFormat() {
		return PNG;
	}

}
