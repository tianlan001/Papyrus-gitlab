/*****************************************************************************
 * Copyright (c) 2008 CEA LIST.
 *
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
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 Stereotype Display
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import java.util.StringTokenizer;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Insets;
import org.eclipse.gmf.runtime.diagram.ui.figures.NoteFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.IPapyrusNodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

/**
 * this is the custom figure of the comment
 *
 * @author Patrick Tessier
 *
 */
public class CornerBentFigure extends NoteFigure implements IPapyrusNodeUMLElementFigure, IPapyrusNodeFigure {

	protected NoteShadowBorder shadowborder;

	protected NoteFigureBorder noteBorder = null;

	/**
	 * Added for stereotypes properties
	 */
	protected StereotypePropertiesCompartment stereotypePropertiesContent;

	/**
	 * Added for stereotypes properties, displayed in the InBrace location
	 */
	protected Label stereotypePropertiesInBraceContent;

	/** Stereotype Wrapping Label */
	protected PapyrusWrappingLabel stereotypesLabel;

	/**
	 * The icon label.
	 */
	protected Label iconLabel;

	protected Color borderColor = ColorConstants.black;

	protected boolean shadow = true;

	public CornerBentFigure() {
		super(100, 60, new Insets(5, 5, 5, 14));
		setLayoutManager(new ToolbarLayout());
		if (this.getBorder() instanceof NoteFigureBorder) {
			noteBorder = (NoteFigureBorder) this.getBorder();
		}
		shadowborder = new NoteShadowBorder(3, getForegroundColor(), new Dimension(10, 10), noteBorder);
		setBorder(shadowborder);
	}

	/**
	 * this method is used to create the stereotype label.
	 */
	protected void createStereotypeLabel() {
		stereotypesLabel = new PapyrusWrappingLabel();
		stereotypesLabel.setOpaque(false);
		stereotypesLabel.setFont(getFont());
		stereotypesLabel.setForegroundColor(getForegroundColor());
		// Add the stereotype label to the figure at pos 0
		this.add(stereotypesLabel, getStereotypeLabelPosition());
	}

	/**
	 * Create Properties Content
	 */
	protected void createStereotypePropertiesContent() {
		stereotypePropertiesContent = new StereotypePropertiesCompartment();
		stereotypePropertiesContent.setFill(false);
		stereotypePropertiesContent.setLineWidth(0);
		stereotypePropertiesContent.setBorder(null);
		stereotypePropertiesContent.setUpperLine(false);
		stereotypePropertiesContent.setLayoutManager(new org.eclipse.papyrus.uml.diagram.common.figure.layout.PropertiesCompartmentLayoutManager());
		this.add(stereotypePropertiesContent, getStereotypePropertiesCompartmentPosition());
	}

	/**
	 * this method is used to create the stereotype label.
	 */
	protected void createStereotypePropertiesInBraceLabel() {
		stereotypePropertiesInBraceContent = new Label();
		stereotypePropertiesInBraceContent.setOpaque(false);
		// Add the stereotype label to the figure at pos 0
		this.add(stereotypePropertiesInBraceContent, getStereotypePropertiesLabelPosition());

	}

	/**
	 * Fill the Compartment with the properties
	 *
	 * @param stereotypeProperties
	 *            Properties List to display
	 */
	private void fillStereotypePropertiesInCompartment(String stereotypeProperties) {

		stereotypePropertiesContent.getChildren().clear();
		StringTokenizer stringTokenizer = new StringTokenizer(stereotypeProperties, ";");
		while (stringTokenizer.hasMoreElements()) {
			String tokenStereotype = stringTokenizer.nextToken();
			tokenStereotype = tokenStereotype.replace("#", "\n  ");
			tokenStereotype = tokenStereotype.replace("|", "\n  ");
			Label label = new Label(tokenStereotype);
			label.setLabelAlignment(PositionConstants.LEFT);
			stereotypePropertiesContent.add(label);
		}
		repaint();
	}

	/**
	 * Retrieve the Property position
	 *
	 * @return The integer for the Position of the property by incrementation
	 */
	protected int getStereotypePropertiesCompartmentPosition() {
		int position = getChildren().indexOf(stereotypesLabel);
		position++;
		return position;
	}

	/**
	 * to obtain the stereotypePropertiesContent of the class figure
	 *
	 */
	public RectangleFigure getStereotypePropertiesContent() {
		return this.stereotypePropertiesContent;
	}

	/**
	 * Returns the position of the stereotype properties location. this is just
	 * after stereotype position
	 *
	 * @return the position of the stereotype properties label in the figure
	 */
	protected int getStereotypePropertiesLabelPosition() {
		int position = getStereotypeLabelPosition();
		if (this.stereotypesLabel != null) {
			position++;
		}
		return position;
	}

	@Override
	public PapyrusWrappingLabel getStereotypesLabel() {
		return this.stereotypesLabel;
	}

	@Override
	public void setStereotypeDisplay(String stereotypes, Image image) {
		// Set stereotype text on figure
		if (!"".equals(stereotypes)) {
			setStereotypes(stereotypes);
		} else {
			setStereotypes(null);
		}

		setAppliedStereotypeIcon(image);

	}

	@Override
	public void setStereotypePropertiesInBrace(String stereotypeProperties) {
		if (stereotypeProperties == null) {
			// Remove label if any
			if (this.stereotypePropertiesInBraceContent != null) {
				this.remove(this.stereotypePropertiesInBraceContent);
				this.stereotypePropertiesInBraceContent = null;
			}
			return;
		}

		// Set the stereotype label if it does not already exist
		if (this.stereotypePropertiesInBraceContent == null) {
			this.createStereotypePropertiesInBraceLabel();
		}

		// Set stereotype text on figure
		if (!"".equals(stereotypeProperties)) {
			this.stereotypePropertiesInBraceContent.setText(StereotypeDisplayConstant.BRACE_LEFT + stereotypeProperties + StereotypeDisplayConstant.BRACE_RIGHT);
		} else {
			this.stereotypePropertiesInBraceContent.setText("");
		}

	}

	/**
	 * displays the new string corresponding to the list of stereotypes.
	 *
	 * if the string is <code>null</code>, then the figure that displays the
	 * stereotype label is removed from the NodeNamedElementFigure.
	 *
	 * @param stereotypeProperties
	 *            the string to be displayed.
	 */
	@Override
	public void setStereotypePropertiesInCompartment(String stereotypeProperties) {
		if (stereotypeProperties == null || "".equals(stereotypeProperties)) {
			// remove figure of stereotype properties compartment
			if (this.stereotypePropertiesContent != null) {
				this.remove(this.stereotypePropertiesContent);
				this.stereotypePropertiesContent = null;
			}
			return;
		}

		// set stereotype properties content
		if (stereotypePropertiesContent == null) {
			this.createStereotypePropertiesContent();
		}

		fillStereotypePropertiesInCompartment(stereotypeProperties);

	}

	/**
	 * Sets the stereotypes for this figure.
	 * <p>
	 * This implementation checks if the specified string is null or not.
	 * <ul>
	 * <li>if the string is <code>null</code>, it removes the label representing the stereotypes.</li>
	 * <li>if this is not <code>null</code>, it creates the stereotype label if needed and displays the specified string.</li>
	 * </ul>
	 * </p>
	 *
	 * @param stereotypes
	 *            the string representing the stereotypes to be displayed
	 */
	protected void setStereotypes(String stereotypes) {
		if (stereotypes == null) {
			// Remove label if any
			if (this.stereotypesLabel != null) {
				this.remove(this.stereotypesLabel);
				this.stereotypesLabel = null;
			}
			return;
		}

		// Set the stereotype label if it does not already exist
		if (this.stereotypesLabel == null) {
			this.createStereotypeLabel();
		}

		// Set stereotype text on figure
		if (!"".equals(stereotypes)) {
			this.stereotypesLabel.setText(stereotypes);
		} else {
			this.stereotypesLabel.setText("");
		}
	}

	/**
	 *
	 *
	 * @return the position of the stereotype label in the figure
	 */
	protected int getStereotypeLabelPosition() {
		int position = getIconLabelPosition();
		if ((this.iconLabel != null) && (this.iconLabel.getIcon() != null)) {
			position++;
		}
		return position;
	}

	/**
	 *
	 *
	 * @return
	 */
	protected int getIconLabelPosition() {
		return 0;
	}

	/**
	 * Sets the stereotype icon for this figure.
	 *
	 * @param stereotypes
	 *            the image representing the stereotype
	 */
	public void setAppliedStereotypeIcon(Image image) {
		if (image == null) {
			// Remove label if any
			if (this.iconLabel != null) {
				this.remove(this.iconLabel);
				this.iconLabel = null;
			}
			return;
		}

		// Set the stereotype label if it does not already exist
		if (this.iconLabel == null) {
			this.createIconLabel();
		}

		// Set stereotype icon on figure
		this.iconLabel.setIcon(image);
	}

	/**
	 * used to create a label that contains the icon.
	 */
	protected void createIconLabel() {
		iconLabel = new Label();
		// Add the label to the figure, at pos 0
		add(iconLabel, getIconLabelPosition());
		iconLabel.setLabelAlignment(PositionConstants.LEFT);
	}

	@Override
	public Color getBorderColor() {
		return borderColor;
	}

	@Override
	public void setBorderColor(Color borderColor) {
		// this.borderColor = borderColor;
		// super.setBorder(new LineBorder(borderColor));

	}

	@Override
	public boolean isShadow() {
		return shadow;
	}

	@Override
	public void setShadow(boolean shadow) {
		this.shadow = shadow;
		if (shadow == true) {
			this.setBorder(shadowborder);
		} else {
			if (noteBorder != null) {
				this.setBorder(noteBorder);
			}
		}
		revalidate();
		repaint();
	}

	@Override
	protected void paintBorder(Graphics g) {
		if (getBorder() == null) {
			return;
		}
		super.paintBorder(g);
	}
}
