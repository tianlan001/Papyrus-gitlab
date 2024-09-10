/*****************************************************************************
 * Copyright (c) 2016 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - adapt it for String usage from XWTAwareColorPicker.
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.widgets.editors;

import org.eclipse.papyrus.infra.gmfdiag.widgets.Activator;
import org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

/**
 * A widget to edit Colors represented by an String value The editor is a Button
 * with a color-picker popup.
 */
public class XWTAwareStringColorPicker extends AbstractPropertyEditor {

	/** The editor. */
	private StringColorPickerEditor editor;

	/** The image path. */
	private String imagePath;

	/** The x. */
	private int x = -1;

	/** The y. */
	private int y = -1;

	/** The width. */
	private int width = -1;

	/** The height. */
	private int height = -1;

	/**
	 * Instantiates a new XWT aware string color picker.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public XWTAwareStringColorPicker(final Composite parent, final int style) {
		editor = new StringColorPickerEditor(parent, style);
		setEditor(editor);
	}

	/**
	 * Sets the image.
	 *
	 * @param imagePath
	 *            the new image
	 */
	public void setImage(final String imagePath) {
		this.imagePath = imagePath;
		Image image = Activator.getDefault().getImageFromPlugin(imagePath);
		if (null != image) {
			editor.setImage(image);
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.properties.ui.widgets.AbstractPropertyEditor#doBinding()
	 *
	 */
	@Override
	protected void doBinding() {
		super.doBinding();
		Object defaultValue = input.getDefaultValue(propertyPath);
		if (defaultValue instanceof String) {
			editor.setDefaultColor((String) defaultValue);
		}
	}

	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public String getImage() {
		return imagePath;
	}

	/**
	 * Check bounds.
	 */
	protected void checkBounds() {
		if (-1 < x && -1 < y && -1 < width && -1 < height) {
			editor.setColorBounds(new Rectangle(x, y, width, height));
		}
	}

	/**
	 * Gets the x.
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Sets the x.
	 *
	 * @param x
	 *            the new x
	 */
	public void setX(final int x) {
		this.x = x;
	}

	/**
	 * Gets the y.
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Sets the y.
	 *
	 * @param y
	 *            the new y
	 */
	public void setY(final int y) {
		this.y = y;
	}

	/**
	 * Gets the width.
	 *
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Sets the width.
	 *
	 * @param width
	 *            the new width
	 */
	public void setWidth(final int width) {
		this.width = width;
	}

	/**
	 * Gets the height.
	 *
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the height.
	 *
	 * @param height
	 *            the new height
	 */
	public void setHeight(final int height) {
		this.height = height;
	}
}
