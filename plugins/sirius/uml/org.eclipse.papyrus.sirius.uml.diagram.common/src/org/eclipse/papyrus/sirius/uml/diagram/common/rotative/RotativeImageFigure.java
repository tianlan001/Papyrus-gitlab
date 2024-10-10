/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, OBEO
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  OBEO - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.rotative;

import org.eclipse.draw2d.PositionConstants;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.papyrus.sirius.uml.diagram.common.Activator;
import org.eclipse.sirius.diagram.CustomStyle;
import org.eclipse.sirius.diagram.ui.tools.api.figure.WorkspaceImageFigure;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;

/**
 * A RotativeImageFigure is used to rotate an image according to its position.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class RotativeImageFigure extends WorkspaceImageFigure {

	/**
	 * The path of the image with the default {@link PositionConstants.NORTH} orientation.
	 */
	private String imagePath;

	/**
	 * Image orientation among {@link PositionConstants.WEST}, {@link PositionConstants.EAST},
	 * {@link PositionConstants.SOUTH}, {@link PositionConstants.NORTH}.
	 */
	private int orientation;

	/**
	 * Creates a RotativeImageFigure.
	 *
	 * @param imagePath
	 *            the path of the image with the default {@link PositionConstants.NORTH} orientation.
	 */
	public RotativeImageFigure(String imagePath) {
		super(flyWeightImage(imagePath));
		this.imagePath = imagePath;
		this.orientation = PositionConstants.NORTH;
	}

	/**
	 * Refresh the figure.
	 *
	 * @param imageStyle
	 *            the style which contains image path
	 */
	public void refreshFigure(final CustomStyle imageStyle) {
		String imgPath = RotativeImageHelper.getInstance().getImagePath(imageStyle);
		if (imgPath != null && !imgPath.equals(this.imagePath)) {
			// Path has changed, probably because the VSM was reloaded
			this.imagePath = imgPath;
		}
		refreshFigure();
	}

	/**
	 * Refresh the figure by rotating the image if needed.
	 */
	private void refreshFigure() {
		setImage(getImage(this.imagePath, this.orientation));
		this.repaint();
	}

	/**
	 * Set orientation and refresh figure.
	 *
	 * @param orientation
	 *            the new image orientation among {@link PositionConstants.WEST}, {@link PositionConstants.EAST},
	 *            {@link PositionConstants.SOUTH}, {@link PositionConstants.NORTH}.
	 */
	public void setOrientation(int orientation) {
		this.orientation = orientation;
		refreshFigure();
	}

	/**
	 * Return an image from the given path.
	 * 
	 * @param path
	 *            the path of the image
	 * @param direction
	 *            one of {@link PositionConstants.WEST}, {@link PositionConstants.EAST}, {@link PositionConstants.SOUTH}, {@link PositionConstants.NORTH}.
	 * @return the image with the proper orientation
	 */
	private Image getImage(String path, int direction) {
		String key = getKey(path, direction);
		ImageRegistry registry = Activator.getDefault().getImageRegistry();
		Image image = registry.get(key);
		if (image == null) {
			if (direction == PositionConstants.NORTH) {
				image = flyWeightImage(path);
			} else {
				image = rotate(getImage(path, PositionConstants.NORTH), direction);
			}
			registry.put(key, image);
		}
		return image;
	}

	private String getKey(String path, int direction) {
		return path + direction;
	}

	/**
	 * Rotate an image.
	 * 
	 * see org.polarsys.kitalpha.sirius.rotativeimage.internal.helpers.RotativeWorkspaceImageHelper.rotate(Image, int)
	 * 
	 * @param image
	 *            the source
	 * @param direction
	 *            PositionConstants.WEST will rotate by 90 degrees on the left, PositionConstants.EAST will rotate by 90
	 *            degrees on the right, PositionConstants.SOUTH will rotate by 180 degrees.
	 * @return the rotated image
	 */
	private Image rotate(Image image, int direction) {
		if (image == null) {
			// We don't want to rotate a null image
			return null;
		}
		ImageData srcData = image.getImageData();
		int bytesPerPixel = srcData.bytesPerLine / srcData.width;
		byte[] newData = new byte[srcData.data.length];

		boolean isAlpha = srcData.alphaData != null;
		byte[] newAlphaData = null;

		int destBytesPerLine = 0;
		ImageData imgData = null;

		switch (direction) {
		case PositionConstants.WEST:
		case PositionConstants.EAST:
			destBytesPerLine = srcData.height * bytesPerPixel;
			imgData = new ImageData(srcData.height, srcData.width, srcData.depth, srcData.palette, destBytesPerLine, newData);
			break;
		default:
			// Default behavior for NORTH and SOUTH
			destBytesPerLine = srcData.width * bytesPerPixel;
			imgData = new ImageData(srcData.width, srcData.height, srcData.depth, srcData.palette, destBytesPerLine, newData);
		}

		if (isAlpha) {
			newAlphaData = new byte[srcData.alphaData.length];
			imgData.alphaData = newAlphaData;
		}
		imgData.alpha = srcData.alpha;

		for (int srcY = 0; srcY < srcData.height; srcY++) {
			for (int srcX = 0; srcX < srcData.width; srcX++) {
				int destX;
				int destY;

				switch (direction) {
				case PositionConstants.WEST: // left 90 degrees
					destX = srcY;
					destY = srcData.width - srcX - 1;
					break;
				case PositionConstants.EAST: // right 90 degrees
					destX = srcData.height - srcY - 1;
					destY = srcX;
					break;
				case PositionConstants.SOUTH: // 180 degrees
					destX = srcData.width - srcX - 1;
					destY = srcData.height - srcY - 1;
					break;
				default:
					destX = srcX;
					destY = srcY;
				}

				imgData.setPixel(destX, destY, srcData.getPixel(srcX, srcY));
				if (isAlpha) {
					imgData.setAlpha(destX, destY, srcData.getAlpha(srcX, srcY));
				}
			}
		}

		return new Image(image.getDevice(), imgData);
	}
}
