/*******************************************************************************
 * Copyright (c) 2022 CEA LIST, Obeo
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Obeo - Initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.sirius.properties.uml.services;

import java.util.Arrays;
import java.util.List;

import org.eclipse.papyrus.uml.tools.utils.ImageUtil;
import org.eclipse.uml2.uml.Image;

/**
 * This service class includes all services used for {@link Image} UMLmodel elements.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class PropertiesImageServices {

	/**
	 * The kind of image display Undefined.
	 */
	private static final String KIND_UNDEFINED = "undefined"; //$NON-NLS-1$

	/**
	 * The kind of image display Displays the image as an Icon in the element edit
	 * part.
	 */
	private static final String KIND_ICON = "icon"; //$NON-NLS-1$

	/**
	 * The kind of image display The image replaces the element edit part.
	 */
	private static final String KIND_SHAPE = "shape"; //$NON-NLS-1$

	// Image observable value

	/**
	 * Get the name of a given {@link Image}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.ImageNameObservableValue.doGetValue()}.
	 * 
	 * @param image
	 *            the image which contains the name
	 * @return the name of a given {@link Image}.
	 */
	public String getImageName(Image image) {
		return ImageUtil.getName(image);
	}

	/**
	 * Set the name of a given {@link Image}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.ImageNameObservableValue.doSetValue(Object)}.
	 * 
	 * @param image
	 *            the image with the name to set
	 * @param name
	 *            the name to set.
	 */
	public void setImageName(Image image, String name) {
		ImageUtil.setName(image, name);
	}

	/**
	 * Get the kind of a given {@link Image}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.ImageKindObservableValue.doGetValue()}.
	 * 
	 * @param image
	 *            the image which contains the kind
	 * @return the kind of a given {@link Image}.
	 */
	public String getImageKind(Image image) {
		return ImageUtil.getKind(image);
	}

	/**
	 * Set the kind of a given {@link Image}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.ImageKindObservableValue.doSetValue(Object)}.
	 * 
	 * @param image
	 *            the image with the kind to set
	 * @param kind
	 *            the kind to set.
	 */
	public void setImageKind(Image image, String kind) {
		ImageUtil.setKind(image, kind);
	}

	/**
	 * Get list of kind candidates.
	 * 
	 * @param obj
	 *            A given {@link Image} which contains kind information.
	 * @return list of kind candidates.
	 */
	public List<String> getImageKindEnumerations(Image obj) {
		return Arrays.asList(KIND_UNDEFINED, KIND_ICON, KIND_SHAPE);
	}

	/**
	 * Get the expression of a given {@link Image}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.ImageExpressionObservableValue.doGetValue()}.
	 * 
	 * @param image
	 *            the image which contains the expression
	 * @return the expression of a given {@link Image}.
	 */
	public String getImageExpression(Image image) {
		return ImageUtil.getExpression(image);
	}

	/**
	 * Set the expression of a given {@link Image}.
	 * 
	 * @see {@link org.eclipse.papyrus.uml.properties.databinding.ImageExpressionObservableValue.doSetValue(Object)}.
	 * 
	 * @param image
	 *            the image with the expression to set
	 * @param expression
	 *            the expression to set.
	 */
	public void setImageExpression(Image image, String expression) {
		ImageUtil.setExpression(image, expression);
	}
}
