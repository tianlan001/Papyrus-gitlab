/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Patrick Tessier (CEA LIST) Patrick.tessier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.hyperlink.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.hyperlink.Activator;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkDocument;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkSpecificObject;
import org.eclipse.papyrus.infra.hyperlink.object.HyperLinkWeb;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * The Class DocumentLabelProvider.
 */
public class HyperLinkLabelProvider extends LabelProvider {

	/** The HYPERLIN k_ we b_ ico n_ path. */
	protected final String HYPERLINK_WEB_ICON_PATH = "/icons/hyperlink.gif"; //$NON-NLS-1$

	/** The HYPERLIN k_ documen t_ ico n_ path. */
	protected final String HYPERLINK_DOCUMENT_ICON_PATH = "/icons/file.gif"; //$NON-NLS-1$

	/** The SEP. */
	private final String SEP = " - "; //$NON-NLS-1$

	/**
	 * {@inheritedDoc}.
	 *
	 * @param element
	 *            the element
	 *
	 * @return the image
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof HyperLinkDocument) {
			File theDoc = new File(((HyperLinkDocument) element).getHyperlinkDocument());
			if (theDoc != null) {
				FileSystemView view = FileSystemView.getFileSystemView();
				Icon icon = view.getSystemIcon(theDoc);
				if (icon instanceof ImageIcon) {
					ImageData imageData = convertAWTImageToSWT(((ImageIcon) icon).getImage());
					if (imageData != null) {
						return new Image(Display.getDefault(), imageData);
					}
				}
			}

			return org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(Activator.PLUGIN_ID, HYPERLINK_DOCUMENT_ICON_PATH);
		}

		if (element instanceof HyperLinkWeb) {
			try {
				InputStream stream = getRawStreamFromURL(new URL("http://www.google.com/s2/favicons?domain=" + ((HyperLinkWeb) element).getHyperLinkWeb()));
				if (stream != null) {
					try {
						BufferedImage image = ImageIO.read(stream);
						ImageData imageData = convertAWTImageToSWT(image);
						if (imageData != null) {
							return new Image(Display.getDefault(), imageData);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return org.eclipse.papyrus.infra.widgets.Activator.getDefault().getImage(Activator.PLUGIN_ID, HYPERLINK_WEB_ICON_PATH);
		}

		if (element instanceof HyperLinkSpecificObject) {
			EObject targetElement = ((HyperLinkSpecificObject) element).getTargetElement();
			if (targetElement != null) {
				try {
					return ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, targetElement).getLabelProvider().getImage(targetElement);
				} catch (ServiceException ex) {
					Activator.log.error(ex);
				}
			}
		}

		return super.getImage(element);
	}

	/**
	 * {@inheritedDoc}.
	 *
	 * @param element
	 *            the element
	 *
	 * @return the text
	 */
	@Override
	public String getText(Object element) {
		String out = ""; //$NON-NLS-1$
		if (element instanceof HyperLinkDocument) {
			out = ((HyperLinkDocument) element).getHyperlinkDocument();
		} else if (element instanceof HyperLinkWeb) {
			out = ((HyperLinkWeb) element).getHyperLinkWeb();
		} else if (element instanceof HyperLinkSpecificObject) {
			EObject targetElement = ((HyperLinkSpecificObject) element).getTargetElement();
			if (targetElement != null) {
				try {
					return ServiceUtilsForEObject.getInstance().getService(LabelProviderService.class, targetElement).getLabelProvider().getText(targetElement);
				} catch (ServiceException ex) {
					Activator.log.error(ex);
				}
			}
		} else {
			return super.getText(element);
		}

		out = ((HyperLinkObject) element).getTooltipText() + SEP + out;
		return out;
	}

	/**
	 * @since 2.0
	 */
	public static ImageData convertAWTImageToSWT(java.awt.Image image) {
		if (image == null) {
			throw new IllegalArgumentException("Null 'image' argument.");
		}
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		if (w == -1 || h == -1) {
			return null;
		}
		BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return convertToSWT(bi);
	}

	/**
	 * @since 2.0
	 */
	public static ImageData convertToSWT(BufferedImage bufferedImage) {
		if (bufferedImage.getColorModel() instanceof DirectColorModel) {
			DirectColorModel colorModel = (DirectColorModel) bufferedImage.getColorModel();
			PaletteData palette = new PaletteData(colorModel.getRedMask(),
					colorModel.getGreenMask(), colorModel.getBlueMask());
			ImageData data = new ImageData(bufferedImage.getWidth(),
					bufferedImage.getHeight(), colorModel.getPixelSize(),
					palette);
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[3];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					raster.getPixel(x, y, pixelArray);
					int pixel = palette.getPixel(new RGB(pixelArray[0],
							pixelArray[1], pixelArray[2]));
					data.setPixel(x, y, pixel);
				}
			}
			return data;
		} else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
			IndexColorModel colorModel = (IndexColorModel) bufferedImage.getColorModel();
			int size = colorModel.getMapSize();
			byte[] reds = new byte[size];
			byte[] greens = new byte[size];
			byte[] blues = new byte[size];
			colorModel.getReds(reds);
			colorModel.getGreens(greens);
			colorModel.getBlues(blues);
			RGB[] rgbs = new RGB[size];
			for (int i = 0; i < rgbs.length; i++) {
				rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF,
						blues[i] & 0xFF);
			}
			PaletteData palette = new PaletteData(rgbs);
			ImageData data = new ImageData(bufferedImage.getWidth(),
					bufferedImage.getHeight(), colorModel.getPixelSize(),
					palette);
			data.transparentPixel = colorModel.getTransparentPixel();
			WritableRaster raster = bufferedImage.getRaster();
			int[] pixelArray = new int[1];
			for (int y = 0; y < data.height; y++) {
				for (int x = 0; x < data.width; x++) {
					raster.getPixel(x, y, pixelArray);
					data.setPixel(x, y, pixelArray[0]);
				}
			}
			return data;
		}
		return null;
	}

	/**
	 * @since 2.0
	 */
	protected InputStream getRawStreamFromURL(URL url) {
		try {
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			return input;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
