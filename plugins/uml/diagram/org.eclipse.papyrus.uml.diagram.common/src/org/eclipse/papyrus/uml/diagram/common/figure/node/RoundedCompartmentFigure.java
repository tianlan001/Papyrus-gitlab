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
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Border;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.e4.ui.css.core.css2.CSS2ColorHelper;
import org.eclipse.gmf.runtime.diagram.ui.figures.ResizableCompartmentFigure;
import org.eclipse.gmf.runtime.draw2d.ui.figures.RoundedRectangleBorder;
import org.eclipse.gmf.runtime.draw2d.ui.graphics.ColorRegistry;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.NotVisibleBorder;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SVGNodePlateFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SlidableRoundedRectangleAnchor;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.FigureUtils;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.w3c.dom.css.RGBColor;

/**
 * A rectangular figure that supports compartment.
 */
public class RoundedCompartmentFigure extends NodeNamedElementFigure implements IRoundedRectangleFigure {

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

	/** True if the figure has header. */
	private boolean hasHeader = false;

	/** The cached border. */
	private Border cachedBorder;

	/** The cached transparency. */
	private int cachedTransparency;

	/** The shadow width. */
	private int shadowWidth = 4;

	/** The shadow color. */
	private String shadowColor = null;

	/** set to true to define the figure as a package. */
	private boolean isPackage = false;

	/** set to true to define if the figure has a shadow. */
	private boolean shadow;

	/** The label color. */
	private String labelColor = null;

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
	public void setShadowColor(final String shadowColor) {
		this.shadowColor = shadowColor;
	}

	/**
	 * @param borderStyle
	 *            the borderStyle to set
	 */
	@Override
	public void setBorderStyle(final int borderStyle) {
		this.borderStyle = borderStyle;

		Border border = getBorder();
		if (border instanceof RoundedRectangleBorder) {
			((RoundedRectangleBorder) border).setStyle(borderStyle);
		}
	}

	/**
	 * Instantiates a new rounded compartment figure.
	 */
	public RoundedCompartmentFigure() {
		this(null, null);
	}

	/**
	 * Instantiates a new rounded compartment figure.
	 */
	public RoundedCompartmentFigure(final String taggedLabel) {
		this(null, taggedLabel);
	}

	/**
	 * Constructor.
	 *
	 * @param compartmentFigure
	 *            the compartment figure
	 */
	public RoundedCompartmentFigure(final List<String> compartmentFigure) {
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
	public RoundedCompartmentFigure(final List<String> compartmentFigure, final String taggedLabelValue) {
		super(taggedLabelValue);
		setOpaque(false);
		setLayoutManager(new AutomaticCompartmentLayoutManager());
		if (null != compartmentFigure) {
			createContentPane(compartmentFigure);
		}
		setBorder(getRoundedBorder());
	}

	/**
	 * @param shadowWidth
	 *            the shadowWidth to set
	 */
	@Override
	public void setShadowWidth(final int shadowWidth) {
		this.shadowWidth = shadowWidth;
	}

	/**
	 * @return shadowWidth
	 */
	protected int getShadowWidth() {
		return this.shadowWidth;
	}

	/**
	 * @param isPackage
	 *            the isPackage to set
	 */
	@Override
	public void setIsPackage(final boolean isPackage) {
		this.isPackage = isPackage;
	}

	/**
	 * Creates the content pane.
	 *
	 * @param compartmentFigure
	 *            the compartment figure
	 */
	protected void createContentPane(final List<String> compartmentFigure) {
		containerFigures = new HashMap<>();
		for (final String id : compartmentFigure) {
			final RectangleFigure newFigure = new RectangleFigure();
			newFigure.setLayoutManager(new SubCompartmentLayoutManager());
			// to debug graphically: newFigure.setFill(true);
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
	public RectangleFigure getCompartment(final String id) {
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
	public void paintFigure(final Graphics graphics) {

		// Expand the clip
		Rectangle clipRectangle = new Rectangle();
		graphics.getClip(clipRectangle);
		graphics.setClip(clipRectangle.expand(2, 2));

		graphics.pushState();
		Rectangle rectangle = getBounds().getCopy();

		refreshCornerSizeWhenOval();

		applyTransparency(graphics);

		// in case of package polygon
		if (isPackage) {
			final SVGNodePlateFigure mainFigure = FigureUtils.findParentFigureInstance(this, SVGNodePlateFigure.class);
			// Get the connection anchor
			final ConnectionAnchor connectionAnchor = mainFigure.getConnectionAnchor(""); //$NON-NLS-1$

			if (connectionAnchor instanceof SlidableRoundedRectangleAnchor) {

				// get the polygon points with the Anchor.
				final PointList polygonPoints = ((SlidableRoundedRectangleAnchor) connectionAnchor).getPolygonPoints();
				this.translateToRelative(polygonPoints);

				// setClip
				graphics.clipRect(clipRectangle.expand(1, 1));

				// Draw shadow
				if (isShadow()) {
					// Set the transparency for shadow
					setShadowTransparency(graphics, true);

					polygonPoints.translate(shadowWidth, shadowWidth);

					// expand clip for draw shadow
					clipRectangle.width += shadowWidth;
					clipRectangle.height += shadowWidth;

					graphics.setClip(clipRectangle);

					// set the background color
					graphics.setBackgroundColor(getColorOfShadow());

					// Draw the shadow
					graphics.fillPolygon(polygonPoints);

					// reposition clip
					polygonPoints.translate(-shadowWidth, -shadowWidth);
					clipRectangle.width -= shadowWidth;
					clipRectangle.height -= shadowWidth;
					graphics.clipRect(clipRectangle);

					// Reset the transparency for shadow
					setShadowTransparency(graphics, false);
				}

				// Fill figure
				fillPolygon(graphics, polygonPoints);

				graphics.setLineWidth(getLineWidth());
				// set the lineStyle: not compatible with custom style
				graphics.setLineStyle(borderStyle);

				// border draw trough graphics
				if (cachedBorder == null) {
					cachedBorder = getBorder();
				}

				// no used of the border of figure
				if (null != getBorder() && !(getBorder() instanceof NotVisibleBorder)) {
					// Set a not visible border to the figure
					setBorder(new NotVisibleBorder());
				}

				if (!noBorder) {
					// Draw lines
					if (graphics.getLineStyle() == Graphics.LINE_CUSTOM) {
						graphics.setLineDash(getCustomDash());
					}
					graphics.drawPolyline(polygonPoints);
				}
			}
		} else {

			// Retrieve the border when was be set to null for package
			if (null != cachedBorder) {
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
				graphics.setBackgroundColor(getColorOfShadow());

				// draw the shadow
				graphics.fillRoundRectangle(rectangle, cornerDimension.width, cornerDimension.height);

				rectangle.translate(-shadowWidth, -shadowWidth);

				// reposition clip
				clipRectangle.width -= shadowWidth;
				clipRectangle.height -= shadowWidth;
				graphics.clipRect(clipRectangle);

				// Reset the transparency for shadow
				setShadowTransparency(graphics, false);
			}

			fillRoundedRectangle(graphics, rectangle, cornerDimension.width, cornerDimension.height);

			// Draw header if needed
			if (hasHeader) {
				PointList header = getHeader();
				graphics.drawPolyline(header);
				// calculate the path
				Path path = new Path(null);
				path.moveTo(getBounds().x + cornerDimension.width / 2, getBounds().y);
				for (int i = 0; i < header.size(); i++) {
					path.lineTo(header.getPoint(i).x, header.getPoint(i).y);
				}
				path.lineTo(getBounds().x, header.getPoint(header.size() - 1).y + cornerDimension.height / 2);
				path.addArc(getBounds().x, getBounds().y, cornerDimension.width, cornerDimension.height, 180, -90);

				graphics.pushState();

				// Set the clip
				graphics.clipPath(path);

				// Paint the Name
				paintName(graphics);

				graphics.popState();
				path.dispose();
			}

			// Draw border
			Border border = getBorder();
			if (border instanceof RoundedRectangleBorder) {
				((RoundedRectangleBorder) border).setArcHeight(cornerDimension.height);
				((RoundedRectangleBorder) border).setArcWidth(cornerDimension.width);
				((RoundedRectangleBorder) border).setWidth(getLineWidth());
				((RoundedRectangleBorder) border).setStyle(borderStyle);
			}
		}
		graphics.popState();
	}

	/**
	 * Fill polygon.
	 *
	 * @param graphics
	 *            the graphics
	 * @param polygonPoints
	 *            the polygon points
	 */
	protected void fillPolygon(final Graphics graphics, final PointList polygonPoints) {
		graphics.pushState();

		final Path path = new Path(null);
		path.moveTo(polygonPoints.getPoint(0).x, polygonPoints.getPoint(0).y);
		for (int i = 1; i < polygonPoints.size(); i++) {
			path.lineTo(polygonPoints.getPoint(i).x, polygonPoints.getPoint(i).y);
		}
		path.close();

		// Set the clip
		graphics.clipPath(path);

		// Defining the color and fill the figure
		if (!isUsingGradient()) {
			graphics.setBackgroundColor(getBackgroundColor());
			graphics.setForegroundColor(getBackgroundColor());
			graphics.fillPath(path);
		} else {
			graphics.setForegroundColor(ColorRegistry.getInstance().getColor(getGradientColor2()));
			graphics.setBackgroundColor(ColorRegistry.getInstance().getColor(getGradientColor1()));
			graphics.fillGradient(getBounds(), getGradientStyle() == 0);
		}

		// Paint the name
		if (!hasHeader || isPackage) {
			paintName(graphics);
		}

		path.dispose();
		graphics.popState();
	}


	/**
	 * Fill rounded rectangle.
	 *
	 * @param graphics
	 *            the graphics
	 * @param rect
	 *            the rectangle
	 * @param arcWidth
	 *            the arc width
	 * @param arcHeight
	 *            the arc height
	 */
	protected void fillRoundedRectangle(final Graphics graphics, final Rectangle rectangle, final int arcWidths, final int arcHeights) {
		graphics.pushState();
		final Rectangle rect = rectangle.getCopy();
		final Dimension arc = new Dimension(arcWidths, arcHeights);

		// Calculate the path to paint
		Path path = new Path(null);
		rect.setWidth(rect.width - 1);
		rect.setHeight(rect.height - 1);
		if (isOval) {
			arc.width = rect.width;
			arc.height = rect.height;
		} else {
			if (rect.width < arc.width) {
				arc.width = rect.width;
			}
			if (rect.height < arc.height) {
				arc.height = rect.height;
			}
		}

		path.moveTo(rect.x + arc.width / 2, rect.y);
		path.lineTo(rect.x + rect.width - arc.width / 2, rect.y);
		path.addArc(rect.x + rect.width - arc.width, rect.y, arc.width, arc.height, 90, -90);
		path.lineTo(rect.x + rect.width, rect.y + rect.height - arc.height / 2);
		path.addArc(rect.x + rect.width - arc.width, rect.y + rect.height - arc.height, arc.width, arc.height, 0, -90);
		path.lineTo(rect.x + arc.width / 2, rect.y + rect.height);
		path.addArc(rect.x, rect.y + rect.height - arc.height, arc.width, arc.height, -90, -90);
		path.lineTo(rect.x, rect.y + arc.height / 2);
		path.addArc(rect.x, rect.y, arc.width, arc.height, 180, -90);

		// set the Color
		if (!isUsingGradient()) {
			graphics.setBackgroundColor(getBackgroundColor());
			graphics.setForegroundColor(getBackgroundColor());
		} else {
			graphics.setForegroundColor(ColorRegistry.getInstance().getColor(getGradientColor2()));
			graphics.setBackgroundColor(ColorRegistry.getInstance().getColor(getGradientColor1()));
		}

		// Set the Clip
		graphics.clipPath(path);

		// Fill the figure
		graphics.fillGradient(getBounds(), 0 == getGradientStyle());

		if (!hasHeader) {
			paintName(graphics);
		}

		graphics.popState();

		path.dispose();
	}

	/**
	 * Paint name.
	 *
	 * @param graphics
	 *            the graphics
	 */
	protected void paintName(final Graphics graphics) {
		if (null != getColorOfLabelBackground()) {

			Rectangle nameBounds = new Rectangle(getLocation(), getLabelsDimension());

			// Move bounds to have a better gradient effect.
			nameBounds.y = nameBounds.y - nameBounds.height;
			nameBounds.height *= 2;
			nameBounds.width = getBounds().width;

			// Paint it
			if (isUsingGradient()) {
				graphics.setForegroundColor(ColorConstants.white);
			} else {
				graphics.setForegroundColor(getColorOfLabelBackground());
			}
			graphics.setBackgroundColor(getColorOfLabelBackground());
			graphics.fillGradient(nameBounds, getGradientStyle() == 0);
		}
	}


	/**
	 * Gets the color of shadow as org.eclipse.swt.graphics.Color.
	 *
	 * @return the color of shadow
	 */
	protected Color getColorOfShadow() {
		Color color = stringToColor(shadowColor);
		return color != null ? color : getForegroundColor();
	}

	/**
	 * Gets the color of label background.
	 *
	 * @return the color of label background
	 */
	protected Color getColorOfLabelBackground() {
		return stringToColor(labelColor);
	}

	/**
	 * String to color.
	 *
	 * @param stringColor
	 *            the color as String
	 * @return the color
	 */
	private Color stringToColor(String stringColor) {
		Color color = null;
		if (stringColor != null && !"-1".equals(stringColor)) {
			// get the the RGBColor from string
			final RGBColor rgbColor = CSS2ColorHelper.getRGBColor(stringColor);
			if (rgbColor != null) {
				// extract RGB
				final int red = Integer.parseInt(rgbColor.getRed().toString());
				final int green = Integer.parseInt(rgbColor.getGreen().toString());
				final int blue = Integer.parseInt(rgbColor.getBlue().toString());

				// get the the Color from RGB
				color = new Color(Display.getCurrent(), new RGB(red, green, blue));
			}

			if (color == null) {
				try {
					color = ColorRegistry.getInstance().getColor(Integer.valueOf(stringColor));
				} catch (final NumberFormatException e) {
					Activator.log.error("Shadow Color not well set", e);
				}
			}
		}

		return color;
	}

	/**
	 * Sets the shadow transparency.
	 *
	 * @param graphics
	 *            the graphics
	 * @param toApplied
	 *            the to applied
	 */
	private void setShadowTransparency(final Graphics graphics, final boolean toApplied) {
		// Set transparency to be used for the shadow
		if (toApplied) {
			cachedTransparency = getTransparency();
			// Set Shadow transparency
			int transparency = cachedTransparency + (100 - cachedTransparency) / 2;
			if (transparency > 100) {
				transparency = 100;
			}
			graphics.setAlpha(255 - transparency * 255 / 100);
		} else {
			// Reset Shadow transparency
			graphics.setAlpha(255 - cachedTransparency * 255 / 100);
		}
	}


	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.PapyrusNodeFigure#isShadow()
	 *
	 * @return true if has a shadow
	 */
	@Override
	public boolean isShadow() {
		return shadow;
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.PapyrusNodeFigure#setShadow(boolean)
	 *
	 * @param shadow
	 */
	@Override
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
	}

	/**
	 * Gets the header point list.
	 *
	 * @return the point list to draw an header. its width is set to the width of the name and it's position to the left.
	 */
	protected PointList getHeader() {
		PointList points = new PointList();

		Rectangle labelBounds = new Rectangle(getLocation(), getLabelsDimension());

		// case the size of the label is 0 or -1 (no label)
		if (labelBounds.width <= 0) {
			labelBounds.width = getBounds().width / 4;
		}

		Point verticalStart = new Point();
		Point verticalEnd = new Point();
		Point diagonalStart = new Point();
		Point diagonalEnd = new Point();
		Point horizontalStart = new Point();
		Point horizontalEnd = new Point();

		verticalStart.x = labelBounds.x + labelBounds.width + 10;
		verticalStart.y = getBounds().y;
		points.addPoint(verticalStart);

		verticalEnd.x = verticalStart.x;
		verticalEnd.y = verticalStart.y + labelBounds.height / 2 + 3;
		points.addPoint(verticalEnd);

		diagonalStart.x = verticalEnd.x;
		diagonalStart.y = verticalEnd.y;
		points.addPoint(diagonalStart);

		diagonalEnd.x = diagonalStart.x - labelBounds.height / 2 + 3;
		diagonalEnd.y = labelBounds.y + labelBounds.height - 1;
		points.addPoint(diagonalEnd);

		horizontalStart.x = diagonalEnd.x;
		horizontalStart.y = diagonalEnd.y;
		points.addPoint(horizontalStart);

		horizontalEnd.x = labelBounds.x;
		horizontalEnd.y = horizontalStart.y;
		points.addPoint(horizontalEnd);
		return points;
	}

	/**
	 * @return the Rounded border used as border for this figure.
	 *
	 */
	private Border getRoundedBorder() {

		refreshCornerSizeWhenOval();

		final RoundedRectangleBorder border = new RoundedRectangleBorder(cornerDimension.width, cornerDimension.height) {
			/**
			 * @see org.eclipse.gmf.runtime.draw2d.ui.figures.RoundedRectangleBorder#paint(org.eclipse.draw2d.IFigure, org.eclipse.draw2d.Graphics, org.eclipse.draw2d.geometry.Insets)
			 *
			 * @param figure
			 * @param graphics
			 * @param insets
			 */
			@Override
			public void paint(final IFigure figure, final Graphics graphics, final Insets insets) {
				final int transparency = 255 - ((NodeFigure) figure).getTransparency() * 255 / 100;
				graphics.setAlpha(transparency);
				if (getStyle() == Graphics.LINE_CUSTOM) {
					graphics.setLineDash(getCustomDash());
				}
				super.paint(figure, graphics, insets);
			}
		};

		return border;
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
			}
		}
	}

	/**
	 * Gets the package header.
	 *
	 * @return the package header
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#getPackageHeader()
	 */
	@Override
	public Rectangle getPackageHeader() {
		Rectangle headerBound = new Rectangle();
		if (isPackage) {
			headerBound.setBounds(getLocation(), getLabelsDimension());
			if (-1 == headerBound.width) {
				headerBound.width = 60;
			}
			// If the width of the figure is < to the label width
			headerBound.width = Math.min(headerBound.width, getBounds().width);
		}
		return headerBound;
	}

	/**
	 * Sets the corner dimension.
	 *
	 * @param cornerDimension
	 *            the new corner dimension
	 */
	@Override
	public void setCornerDimensions(final Dimension cornerDimension) {
		if (cornerDimension.width == 0 || cornerDimension.height == 0) {
			this.cornerDimension = new Dimension();
		} else {
			this.cornerDimension = cornerDimension;
		}
	}

	/**
	 * Sets the oval.
	 *
	 * @param booleanValue
	 *            the new oval
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#setOval(boolean)
	 */
	@Override
	public void setOval(final boolean booleanValue) {
		isOval = booleanValue;
		if (booleanValue) {
			refreshCornerSizeWhenOval();
		}
	}

	/**
	 * Checks if is oval.
	 *
	 * @return true, if is oval
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#isOval()
	 */
	@Override
	public boolean isOval() {
		return isOval;
	}

	/**
	 * Sets the floating name constrained.
	 *
	 * @param booleanValue
	 *            the new floating name constrained
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#setFloatingNameConstrained(boolean)
	 */
	@Override
	public void setFloatingNameConstrained(final boolean booleanValue) {
		isLabelConstrained = booleanValue;
	}

	/**
	 * Checks if is floating name constrained.
	 *
	 * @return true, if is floating name constrained
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#isFloatingNameConstrained()
	 */
	@Override
	public boolean isFloatingNameConstrained() {
		return isLabelConstrained;
	}

	/**
	 * Sets the floating name offset.
	 *
	 * @param offset
	 *            the new floating name offset
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#setFloatingNameOffset(org.eclipse.draw2d.geometry.Dimension)
	 */
	@Override
	public void setFloatingNameOffset(final Dimension offset) {
		this.floatingNameOffset = offset;

	}

	/**
	 * Gets the floating name offset.
	 *
	 * @return the floating name offset
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#getFloatingNameOffset()
	 */
	@Override
	public Dimension getFloatingNameOffset() {
		return floatingNameOffset;
	}

	/**
	 * Sets the checks for header.
	 *
	 * @param hasHeader
	 *            the new checks for header
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#setHasHeader(boolean)
	 */
	@Override
	public void setHasHeader(final boolean hasHeader) {
		this.hasHeader = hasHeader;
	}

	/**
	 * Checks for header.
	 *
	 * @return true, if successful
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#hasHeader()
	 */
	@Override
	public boolean hasHeader() {
		return hasHeader;
	}

	/**
	 * Gets the dimension of all labels together.
	 *
	 * @return the dimension. (-1,-1) if there is no label.
	 * @since 3.0
	 */
	public Dimension getLabelsDimension() {
		final Dimension labelDimension = new Dimension(-1, -1);

		if (null != nameLabel && labelDimension.width < nameLabel.getPreferredSize().width) {
			labelDimension.width = nameLabel.getPreferredSize().width;
		}
		if (null != taggedLabel && labelDimension.width < taggedLabel.getPreferredSize().width) {
			labelDimension.width = taggedLabel.getPreferredSize().width;
		}
		if (null != stereotypesLabel && labelDimension.width < stereotypesLabel.getPreferredSize().width) {
			labelDimension.width = stereotypesLabel.getPreferredSize().width;
		}
		if (null != stereotypePropertiesInBraceContent && labelDimension.width < stereotypePropertiesInBraceContent.getPreferredSize().width) {
			labelDimension.width = stereotypePropertiesInBraceContent.getPreferredSize().width;
		}
		if (null != qualifiedLabel && labelDimension.width < qualifiedLabel.getPreferredSize().width) {
			labelDimension.width = qualifiedLabel.getPreferredSize().width;
		}

		final ResizableCompartmentFigure firstCompartment = FigureUtils.findChildFigureInstance(this, ResizableCompartmentFigure.class);
		if (null != firstCompartment) {
			labelDimension.height = firstCompartment.getBounds().y - getBounds().y;
		}

		return labelDimension;
	}

	/**
	 * Gets the name background color.
	 *
	 * @return the label background color
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#getNameBackgroundColor()
	 */
	@Override
	public String getNameBackgroundColor() {
		return labelColor;
	}


	/**
	 * Sets the label background color.
	 *
	 * @param labelBackgroundColor
	 *            the new label background color
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IRoundedRectangleFigure#setNameBackgroundColor(java.lang.String)
	 */
	@Override
	public void setNameBackgroundColor(String labelBackgroundColor) {
		this.labelColor = labelBackgroundColor;
	}

}
