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
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - adapt it for String usage from ColorPickerEditor.
 *****************************************************************************/
package org.eclipse.papyrus.infra.gmfdiag.widgets.editors;

import org.eclipse.core.databinding.observable.ChangeEvent;
import org.eclipse.core.databinding.observable.IChangeListener;
import org.eclipse.e4.ui.css.core.css2.CSS2ColorHelper;
import org.eclipse.gmf.runtime.diagram.ui.properties.sections.appearance.ColorPalettePopup;
import org.eclipse.gmf.runtime.draw2d.ui.figures.FigureUtilities;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.css.RGBColor;

/**
 * A color picker editor for color defined as string.
 */
public class StringColorPickerEditor extends AbstractValueEditor implements IChangeListener {

	/** The color picker. */
	protected Button colorPicker;

	/** The color picker popup. */
	protected ColorPalettePopup colorPickerPopup;

	/** The color. */
	protected RGB color;

	/** The color bounds. */
	protected Rectangle colorBounds;

	/** The background image. */
	protected Image backgroundImage;

	/** The default color. */
	protected String defaultColor = "-1";//$NON-NLS-1$

	/** The Constant defaultColorBoundsWithoutImage. */
	public static final Rectangle defaultColorBoundsWithoutImage = new Rectangle(0, 0, 15, 15);

	/** The Constant defaultColorBoundsWithImage. */
	public static final Rectangle defaultColorBoundsWithImage = new Rectangle(0, 12, 16, 4);

	/**
	 * Instantiates a new string color picker editor.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 */
	public StringColorPickerEditor(final Composite parent, final int style) {
		super(parent, style);

		colorPicker = new Button(this, SWT.PUSH);
		doSetColor("0");//$NON-NLS-1$
		colorPicker.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				ColorPalettePopup colorPickerPopup = new ColorPalettePopup(getShell(), IDialogConstants.BUTTON_BAR_HEIGHT);
				colorPickerPopup.setPreviousColor(Integer.valueOf(getValue()));
				Rectangle r = colorPicker.getBounds();
				Point location = colorPicker.getParent().toDisplay(r.x, r.y);
				colorPickerPopup.open(new Point(location.x, location.y + r.height));
				if (null != colorPickerPopup.getSelectedColor() || colorPickerPopup.useDefaultColor()) {
					setColor(colorPickerPopup.getSelectedColor());
					commit();
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// Nothing
			}
		});
	}

	/**
	 * Constructor. Allows to choose the default color of the button, else it be black.
	 *
	 * @param parent
	 *            the parent
	 * @param style
	 *            the style
	 * @param defaultColor
	 *            {@link FigureUtilities#colorToInteger(Color)}
	 */
	public StringColorPickerEditor(final Composite parent, final int style, final String defaultColor) {
		this(parent, style);
		this.defaultColor = defaultColor;
		doSetColor(defaultColor);
	}

	/**
	 * Do binding.
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#doBinding()
	 */
	@Override
	protected void doBinding() {
		// We don't do a real databinding here
		if (null != modelProperty) {
			getParent().addDisposeListener(new DisposeListener() {

				@Override
				public void widgetDisposed(DisposeEvent e) {
					StringColorPickerEditor.this.dispose();
				}
			});
			modelProperty.addChangeListener(this);
			handleChange(null);
		}
	}

	/**
	 * Dispose.
	 *
	 * @see org.eclipse.swt.widgets.Widget#dispose()
	 */
	@Override
	public void dispose() {
		if (null != modelProperty) {
			modelProperty.removeChangeListener(this);
		}
		super.dispose();
	}

	/**
	 * Sets the color & updates the ModelProperty.
	 *
	 * @param color
	 *            the new color
	 */
	private void setColor(final RGB color) {
		String colorValue = color == null ? defaultColor : String.valueOf(FigureUtilities.RGBToInteger(color));

		doSetColor(colorValue);

		if (null != modelProperty) {
			modelProperty.setValue(colorValue);
		}
	}

	/**
	 * Updates the color without updating the ModelProperty.
	 *
	 * @param color
	 *            the color
	 */
	private void doSetColor(final String color) {
		this.color = StringToRGB(color);
		updateButton();
	}

	/**
	 * String to rgb.
	 *
	 * @param color
	 *            the color
	 * @return the rgb
	 */
	RGB StringToRGB(final String color) {
		RGB rgb = null;
		if (color != null && !"-1".equals(color)) {//$NON-NLS-1$
			RGBColor rgbColor = CSS2ColorHelper.getRGBColor(color);

			if (null != rgbColor) {
				// extract RGB
				int red = Integer.parseInt(rgbColor.getRed().toString());
				int green = Integer.parseInt(rgbColor.getGreen().toString());
				int blue = Integer.parseInt(rgbColor.getBlue().toString());

				// get the the Color from RGB
				rgb = new RGB(red, green, blue);
			} else {
				try {
					rgb = FigureUtilities.integerToRGB(Integer.valueOf(color));
				} catch (NumberFormatException e) {
					rgb = new RGB(0, 0, 0);
				}
			}
		} else {
			rgb = new RGB(0, 0, 0);
		}
		return rgb;
	}

	/**
	 * Sets the image for a color button (square filled with the color that button represents).
	 *
	 * @see GradientFieldEditor#setButtonImage(RGB)
	 */
	private void updateButton() {
		// First, dispose the current image, if any
		Image image = colorPicker.getImage();
		if (image != null) {
			image.dispose();
		}

		Display display = colorPicker.getDisplay();
		// Now set the new image based on rgbColor
		Color color = new Color(display, this.color);

		image = backgroundImage == null ? new Image(display, 16, 16) : new Image(display, backgroundImage.getBounds());

		GC gc = new GC(image);
		gc.fillRectangle(image.getBounds());

		gc.setBackground(colorPicker.getBackground());
		gc.fillRectangle(image.getBounds());

		gc.setBackground(color);
		gc.setForeground(color);

		if (null != backgroundImage) {
			gc.fillRectangle(defaultColorBoundsWithImage);
			gc.drawImage(backgroundImage, 0, 0);
		} else {
			gc.fillRectangle(defaultColorBoundsWithoutImage);
			gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
			gc.drawRectangle(defaultColorBoundsWithoutImage);
		}

		gc.dispose();
		color.dispose();
		colorPicker.setImage(image);
	}

	/**
	 * Sets the image.
	 *
	 * @param image
	 *            the new image
	 */
	public void setImage(final Image image) {
		this.backgroundImage = image;
	}

	/**
	 * Sets the color bounds.
	 *
	 * @param rectangle
	 *            the new color bounds
	 */
	public void setColorBounds(final Rectangle rectangle) {
		this.colorBounds = rectangle;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractValueEditor#getValue()
	 */
	@Override
	public String getValue() {
		if (null == color) {
			return defaultColor;
		}

		return String.valueOf(FigureUtilities.RGBToInteger(color));
	}

	/**
	 * Gets the editable type.
	 *
	 * @return the editable type
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#getEditableType()
	 */
	@Override
	public Object getEditableType() {
		return Integer.class;
	}

	/**
	 * Sets the read only.
	 *
	 * @param readOnly
	 *            the new read only
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#setReadOnly(boolean)
	 */
	@Override
	public void setReadOnly(final boolean readOnly) {
		colorPicker.setEnabled(!readOnly);
	}

	/**
	 * Checks if is read only.
	 *
	 * @return true, if is read only
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#isReadOnly()
	 */
	@Override
	public boolean isReadOnly() {
		return !colorPicker.isEnabled();
	}

	/**
	 * Sets the tool tip text.
	 *
	 * @param text
	 *            the new tool tip text
	 * @see org.eclipse.swt.widgets.Control#setToolTipText(java.lang.String)
	 */
	@Override
	public void setToolTipText(final String text) {
		setLabelToolTipText(text);
	}

	/**
	 * Handle change.
	 *
	 * @param event
	 *            the event
	 * @see org.eclipse.core.databinding.observable.IChangeListener#handleChange(org.eclipse.core.databinding.observable.ChangeEvent)
	 */
	@Override
	public void handleChange(final ChangeEvent event) {
		if (null != modelProperty) {
			String value = (String) modelProperty.getValue();
			if (null == value) {
				value = "-1";//$NON-NLS-1$
			}

			doSetColor(value);
		}
	}

	/**
	 * Refresh value.
	 *
	 * @see org.eclipse.papyrus.infra.widgets.editors.AbstractEditor#refreshValue()
	 */
	@Override
	public void refreshValue() {
		handleChange(null);
	}

	/**
	 * Sets the default color for this Editor. The default color
	 * will be set when the "Default" button is pressed.
	 *
	 * @param color
	 *            The default color for this editor
	 */
	public void setDefaultColor(final String color) {
		this.defaultColor = color;
	}

}
