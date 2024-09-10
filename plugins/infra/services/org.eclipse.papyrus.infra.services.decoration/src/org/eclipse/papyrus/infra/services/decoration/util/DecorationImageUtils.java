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
 *   Vincent Lorenzo (CEA LIST) - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.decoration.util;

import java.util.List;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.DecorationOverlayIcon;
import org.eclipse.papyrus.infra.widgets.Activator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * This class provides methods utils to add decoration (overlay) to an existing image.
 * 
 * @since 1.2
 */
public class DecorationImageUtils {

	/** point corresponding to the size 16x16. */
	public static final Point SIZE_16_16 = new Point(16, 16);

	/**
	 * The number of decoration is 5 (number of possible decoration position as enumerated by constants in IDecoration from JFace
	 * (TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, UNDERLAY)fro mTOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT, UNDERLAY)
	 */
	public static final int NB_DECORATIONS = 5;

	private DecorationImageUtils() {
		// to prevent instanciation
	}

	/**
	 * 
	 * @param decoratorTarget
	 *            the target image
	 * @param decorations
	 *            the decoration to add to the image
	 * @param size
	 *            the final size of the returned image
	 * @return
	 * 		the decorated image according to the parameters, or <code>null</code> if parameters do not allow to create it
	 */
	public static final Image getDecoratedImage(final Image decoratorTarget, final List<IPapyrusDecoration> decorations, final Point size) {
		if (decoratorTarget == null) {
			return null;
		}

		if (decorations == null) {
			return decoratorTarget;
		}

		Image decoratedImage = null;

		// Construct a new image identifier
		String decoratedImageId = calcId(decoratorTarget, decorations);

		decoratedImage = Activator.getDefault().getImageRegistry().get(decoratedImageId);
		// Return the stored image if we have one
		if (decoratedImage == null) {
			// Otherwise create a new image and store it
			ImageDescriptor[] decorationImages = new ImageDescriptor[NB_DECORATIONS];
			// Store the decoration by position
			IPapyrusDecoration[] decorationByPosition = new IPapyrusDecoration[NB_DECORATIONS];

			for (IPapyrusDecoration decoration : decorations) {
				IPapyrusDecoration existingDecoration = decorationByPosition[decoration.getPositionForJFace()];
				if (existingDecoration == null || existingDecoration.getPriority() < decoration.getPriority()) {
					// if no decoration exists for the current position
					// or if the existing decoration has a lower priority than the current
					// replace the existing decoration with the current one
					decorationImages[decoration.getPositionForJFace()] = decoration.getDecorationImageForME();
					decorationByPosition[decoration.getPositionForJFace()] = decoration;
				}
			}
			ImageDescriptor decoratedImageDesc = new DecorationOverlayIcon(decoratorTarget, decorationImages, size);
			Activator.getDefault().getImageRegistry().put(decoratedImageId, decoratedImageDesc);
			return Activator.getDefault().getImageRegistry().get(decoratedImageId);
		}
		return decoratedImage;
	}


	/**
	 * @param decoratorTarget
	 *            the target image, should not be <code>null</code> to avoid null pointer.
	 * @param decorations
	 *            the decoration to add to the image, should not be <code>null</code> to avoid null pointer.
	 * @return
	 * 		a unique id to identify the image, or <code>""</code>
	 */
	public static final String calcId(final Image decoratorTarget, final List<IPapyrusDecoration> decorations) {
		StringBuilder decoratedImageId = new StringBuilder(decoratorTarget.toString());
		for (IPapyrusDecoration decoration : decorations) {
			decoratedImageId.append(decoration.getDecorationImageForME().toString());
			decoratedImageId.append(decoration.getPosition());
		}
		return decoratedImageId.toString();
	}
}
