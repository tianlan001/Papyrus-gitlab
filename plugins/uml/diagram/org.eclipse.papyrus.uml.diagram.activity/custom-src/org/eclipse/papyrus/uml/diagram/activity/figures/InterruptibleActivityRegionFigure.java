/*****************************************************************************
 * Copyright (c) 2010, 2014 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and Implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.figures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.e4.ui.css.core.css2.CSS2ColorHelper;
import org.eclipse.gmf.runtime.draw2d.ui.figures.RoundedRectangleBorder;
import org.eclipse.gmf.runtime.draw2d.ui.graphics.ColorRegistry;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.GradientStyle;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SubCompartmentLayoutManager;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.FigureUtils;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AutomaticCompartmentLayoutManager;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeUMLElementFigure;
import org.eclipse.papyrus.uml.diagram.common.figure.node.PapyrusNodeFigure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.css.RGBColor;

/**
 * This figure handles a rounded dashed rectangle Papyrus node, with no
 * displayed label.
 *
 * @author madam
 */
public class InterruptibleActivityRegionFigure extends PapyrusNodeFigure implements IPapyrusNodeUMLElementFigure, IFigure, IRoundedRectangleFigure {

	/** The container figures. */
	private Map<String, RectangleFigure> containerFigures;

	/** The corner dimension. */
	protected Dimension cornerDimension = new Dimension();

	/** True if the figure is oval. */
	protected boolean isOval = false;

	/** The is label constrained. */
	protected boolean isLabelConstrained = false;

	/** The floating name offset. */
	protected Dimension floatingNameOffset = new Dimension();

	/** The border style. */
	protected int borderStyle = Graphics.LINE_SOLID;

	/** The cached border. */
	private Border cachedBorder;

	/** The cached transparency. */
	private int cachedTransparency;

	/** The shadow width. */
	private int shadowWidth = 4;

	/** The shadow color. */
	String shadowColor = null;

	/**
	 * Gets the shadow color.
	 *
	 * @return the shadowColor
	 */
	@Override
	public String getShadowColor() {
		return shadowColor;
	}

	/**
	 * Sets the shadow color.
	 *
	 * @param shadowColor
	 *            the shadowColor to set
	 */
	@Override
	public void setShadowColor(String shadowColor) {
		this.shadowColor = shadowColor;
	}

	/**
	 * @param borderStyle
	 *            the borderStyle to set
	 */
	@Override
	public void setBorderStyle(int borderStyle) {
		this.borderStyle = borderStyle;
		if (shadowborder != null) {
			shadowborder.setStyle(borderStyle);
		}
	}

	/**
	 * Instantiates a new rounded compartment figure.
	 */
	public InterruptibleActivityRegionFigure() {
		this(null, null);
	}

	/**
	 * Constructor.
	 *
	 * @param compartmentFigure
	 *            the compartment figure
	 */
	public InterruptibleActivityRegionFigure(List<String> compartmentFigure) {
		this(compartmentFigure, null);
	}

	/**
	 * Constructor with a tagged value.
	 *
	 * @param compartmentFigure
	 *            a list of id for the compartment figure
	 * @param taggedLabelValue
	 *            the value to display as tagged value
	 */
	public InterruptibleActivityRegionFigure(List<String> compartmentFigure, String taggedLabelValue) {
		super();
		setOpaque(false);
		setLayoutManager(new AutomaticCompartmentLayoutManager());
		if (compartmentFigure != null) {
			createContentPane(compartmentFigure);
		}
	}

	/**
	 * @param shadowWidth
	 *            the shadowWidth to set
	 */
	@Override
	public void setShadowWidth(int shadowWidth) {
		this.shadowWidth = shadowWidth;
	}

	/**
	 * @param isPackage
	 *            the isPackage to set
	 */
	@Override
	public void setIsPackage(boolean isPackage) {
		// The Region is not a named element
	}

	/**
	 * Creates the content pane.
	 *
	 * @param compartmentFigure
	 *            the compartment figure
	 */
	protected void createContentPane(List<String> compartmentFigure) {
		containerFigures = new HashMap<>();
		for (String id : compartmentFigure) {
			RectangleFigure newFigure = new RectangleFigure();
			newFigure.setLayoutManager(new SubCompartmentLayoutManager());
			newFigure.setFill(false);
			newFigure.setBorder(null);
			newFigure.setOutline(false);
			newFigure.setOpaque(false);
			this.add(newFigure);
			containerFigures.put(id, newFigure);
		}
	}

	/**
	 * Get the RectangleFigure containing the wanted compartment.
	 *
	 * @param id
	 *            the id to find the right compartment
	 * @return the RectangleFigure
	 */
	public RectangleFigure getCompartment(String id) {
		return containerFigures.get(id);
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#getCornerDimensions()
	 *
	 * @return
	 */
	@Override
	public Dimension getCornerDimensions() {
		return cornerDimension;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#getRoundedRectangleBounds()
	 *
	 * @return
	 */
	@Override
	public Rectangle getRoundedRectangleBounds() {
		return getBounds();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void paintFigure(Graphics graphics) {

		graphics.pushState();
		Rectangle rectangle = getBounds().getCopy();
		Rectangle clipRectangle = getBounds().getCopy();
		refreshCornerSizeWhenOval();

		applyTransparency(graphics);


		// Retrieve the border when was be set to null for package
		if (cachedBorder != null) {
			setBorder(cachedBorder);
			cachedBorder = null;
		}

		// Draw shadow
		if (isShadow()) {

			// Set the transparency for shadow
			setShadowTransparency(graphics, true);

			rectangle.translate(shadowWidth, shadowWidth);

			// expand clip for draw shadow
			graphics.getClip(clipRectangle);
			clipRectangle.width += shadowWidth;
			clipRectangle.height += shadowWidth;
			graphics.setClip(clipRectangle);

			// set the background color
			setShadowBackgroudColor(graphics);

			// draw the shadow
			graphics.fillRoundRectangle(rectangle, cornerDimension.width, cornerDimension.height);

			rectangle.translate(-shadowWidth, -shadowWidth);

			// reposition clip
			clipRectangle.width -= shadowWidth;
			clipRectangle.height -= shadowWidth;
			graphics.setClip(clipRectangle);

			// Reset the transparency for shadow
			setShadowTransparency(graphics, false);
		}

		if (isUsingGradient()) {
			Pattern pattern = getGradientPattern();
			graphics.setBackgroundPattern(pattern);
			graphics.fillRoundRectangle(rectangle, cornerDimension.width, cornerDimension.height);
			graphics.setBackgroundPattern(null);
			pattern.dispose();
		} else {
			graphics.pushState();
			graphics.setBackgroundColor(getBackgroundColor());
			graphics.setForegroundColor(getForegroundColor());
			graphics.fillRoundRectangle(rectangle, cornerDimension.width, cornerDimension.height);
			graphics.popState();
		}


		graphics.popState();
	}

	/**
	 * @param graphics
	 */
	private void setShadowBackgroudColor(Graphics graphics) {

		if (shadowColor != null) {
			// get the the RGBColor from string
			RGBColor rgbColor = CSS2ColorHelper.getRGBColor(shadowColor);

			// extract RGB
			int red = Integer.parseInt(rgbColor.getRed().toString());
			int green = Integer.parseInt(rgbColor.getGreen().toString());
			int blue = Integer.parseInt(rgbColor.getBlue().toString());

			// get the the Color from RGB
			Color color = new Color(Display.getCurrent(), new RGB(red, green, blue));
			graphics.setBackgroundColor(color);
		} else {
			graphics.setBackgroundColor(getForegroundColor());
		}
	}

	/**
	 * Sets the shadow transparency.
	 *
	 * @param graphics
	 *            the graphics
	 * @param toApplied
	 *            the to applied
	 */
	private void setShadowTransparency(Graphics graphics, boolean toApplied) {
		// Set transparency to be used for the shadow
		if (toApplied) {
			cachedTransparency = getTransparency();
			// Set Shadow transparency
			int transparency = cachedTransparency + (100 - cachedTransparency) / 2;
			if (transparency > 100) {
				transparency = 100;
			}
			setTransparency((transparency));
			applyTransparency(graphics);
		} else {
			// Reset Shadow transparency
			setTransparency(cachedTransparency);
			applyTransparency(graphics);
		}
	}


	/**
	 * Gets the gradient pattern.
	 *
	 * @return the gradient pattern
	 */
	private Pattern getGradientPattern() {
		Rectangle rectangle = getBounds().getCopy();

		boolean isVertical = (getGradientStyle() == GradientStyle.VERTICAL) ? true : false;

		Point startGradientPoint = rectangle.getTopLeft();
		Point endGradientPoint = rectangle.getBottomRight();

		// Place start and end point according to isVertical.
		if (isVertical) {
			startGradientPoint.x = rectangle.getTopLeft().x + rectangle.width() / 2;
			endGradientPoint.x = startGradientPoint.x;
		} else {
			startGradientPoint.y = rectangle.getTopLeft().y + rectangle.height() / 2;
			endGradientPoint.y = startGradientPoint.y;
		}

		// Take zoom into account
		double scale = FigureUtils.getScale(this);
		startGradientPoint.scale(scale);
		endGradientPoint.scale(scale);

		// get alpha with convert transparency from 0 -> 100 to 255 -> 0
		int alpha = (int) ((255.0 / 100.0) * (100.0 - getTransparency()));

		// create pattern to display
		Pattern pattern = new Pattern(Display.getCurrent(), startGradientPoint.x,
				startGradientPoint.y, endGradientPoint.x, endGradientPoint.y,
				ColorRegistry.getInstance().getColor(getGradientColor2()), alpha,
				ColorRegistry.getInstance().getColor(getGradientColor1()), alpha);
		return pattern;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.PapyrusNodeFigure#setShadow(boolean)
	 *
	 * @param shadow
	 */
	@Override
	public void setShadow(boolean shadow) {
		super.setShadow(shadow);

		refreshCornerSizeWhenOval();

		RoundedRectangleBorder border = new RoundedRectangleBorder(cornerDimension.width, cornerDimension.height) {
			/**
			 * @see org.eclipse.gmf.runtime.draw2d.ui.figures.RoundedRectangleBorder#paint(org.eclipse.draw2d.IFigure, org.eclipse.draw2d.Graphics, org.eclipse.draw2d.geometry.Insets)
			 *
			 * @param figure
			 * @param graphics
			 * @param insets
			 */
			@Override
			public void paint(IFigure figure, Graphics graphics, Insets insets) {
				int transparency = 255 - ((NodeFigure) figure).getTransparency() * 255 / 100;
				graphics.setAlpha(transparency);
				super.paint(figure, graphics, insets);
			}
		};

		border.setWidth(getLineWidth());
		border.setStyle(borderStyle);
		setBorder(border);
		setLineStyle(borderStyle);
	}


	/**
	 * Refresh corner size when oval.
	 */
	private void refreshCornerSizeWhenOval() {
		// Set the corner dimension if is oval in case of resizing
		if (isOval) {
			if (cornerDimension.width != getBounds().width || cornerDimension.height != getBounds().height) {
				cornerDimension.width = getBounds().width;
				cornerDimension.height = getBounds().height;
				// Force to repaint the border thought setShadow()
				setShadow(isShadow());
			}
		}
	}

	/**
	 * Sets the corner dimension.
	 *
	 * @param cornerDimension
	 *            the new corner dimension
	 */
	@Override
	public void setCornerDimensions(Dimension cornerDimension) {
		this.cornerDimension = cornerDimension;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#setOval(boolean)
	 *
	 * @param booleanValue
	 */
	@Override
	public void setOval(boolean booleanValue) {
		isOval = booleanValue;
		if (booleanValue) {
			refreshCornerSizeWhenOval();
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#isOval()
	 *
	 * @return
	 */
	@Override
	public boolean isOval() {
		return isOval;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#setFloatingNameConstrained(boolean)
	 *
	 * @param booleanValue
	 */
	@Override
	public void setFloatingNameConstrained(boolean booleanValue) {
		isLabelConstrained = booleanValue;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#isFloatingNameConstrained()
	 *
	 * @return
	 */
	@Override
	public boolean isFloatingNameConstrained() {
		return isLabelConstrained;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#setFloatingNameOffset(org.eclipse.draw2d.geometry.Dimension)
	 *
	 * @param offset
	 */
	@Override
	public void setFloatingNameOffset(Dimension offset) {
		this.floatingNameOffset = offset;

	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#getFloatingNameOffset()
	 *
	 * @return
	 */
	@Override
	public Dimension getFloatingNameOffset() {
		return floatingNameOffset;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#getPackageHeader()
	 *
	 * @return
	 */
	@Override
	public Rectangle getPackageHeader() {
		// The Region is not a named element
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#setHasHeader(boolean)
	 *
	 * @param hasHeader
	 */
	@Override
	public void setHasHeader(boolean hasHeader) {
		// The Region is not a named element
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#hasHeader()
	 *
	 * @return
	 */
	@Override
	public boolean hasHeader() {
		return false;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusUMLElementFigure#setStereotypeDisplay(java.lang.String, org.eclipse.swt.graphics.Image)
	 *
	 * @param stereotypes
	 * @param image
	 */
	@Override
	public void setStereotypeDisplay(String stereotypes, Image image) {

	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeUMLElementFigure#setStereotypePropertiesInBrace(java.lang.String)
	 *
	 * @param stereotypeProperties
	 */
	@Override
	public void setStereotypePropertiesInBrace(String stereotypeProperties) {

	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeUMLElementFigure#setStereotypePropertiesInCompartment(java.lang.String)
	 *
	 * @param stereotypeProperties
	 */
	@Override
	public void setStereotypePropertiesInCompartment(String stereotypeProperties) {

	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeUMLElementFigure#getStereotypesLabel()
	 *
	 * @return
	 */
	@Override
	public PapyrusWrappingLabel getStereotypesLabel() {
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#getNameBackgroundColor()
	 *
	 * @return
	 */
	@Override
	public String getNameBackgroundColor() {
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#setNameBackgroundColor(java.lang.String)
	 *
	 * @param labelBackgroundColor
	 */
	@Override
	public void setNameBackgroundColor(final String labelBackgroundColor) {
		// Do nothing
	}


}
