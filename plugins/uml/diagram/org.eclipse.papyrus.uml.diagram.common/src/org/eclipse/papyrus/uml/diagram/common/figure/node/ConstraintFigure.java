/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Céline Janssens (ALL4TEC) celine.janssens@all4tec.net - Bug 440230 : Label Margin
 *  Mickael ADAM (ALL@TEC) mickael.adam@all4tec.net - bug 462448
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.figure.node;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.PapyrusWrappingLabel;
import org.eclipse.swt.graphics.Image;

/**
 * this class is used to display the a constraint with the possibility of
 * gradient qualified name
 *
 */
public class ConstraintFigure extends CornerBentFigure implements IPapyrusNodeNamedElementFigure, ILabelFigure, IMultilineEditableFigure {

	private static final String CHEVRON = String.valueOf("\u00AB") + String.valueOf("\u00BB");

	protected static final String LEFT_BRACE = "{";

	private WrappingLabel taggedLabel;

	protected static final String RIGHT_BRACE = "}";

	private WrappingLabel nameLabel;

	private Label qualifiedLabel;

	/** the depth of the qualified name **/
	private int depth = 0;

	/** main flow page */
	protected WrappingLabel page;

	/** separate the name label from the body */
	private boolean drawSeparator = false;



	/**
	 * Calculate the partial qualified name with a specified depth.
	 *
	 * @param qualifiedName
	 *            the qualified name can return null
	 */
	public String getQualifiedName(String qualifiedName, int depth) {
		int n = -1;

		int i = 0;
		if (depth <= 0) {
			return qualifiedName;
		}

		while (i < depth) {
			if ((n = qualifiedName.indexOf("::", n + 1)) != -1) {
				i++;
			} else {
				return null;
			}
		}

		if (n == -1) {
			return qualifiedName;
		} else {
			return qualifiedName.substring(n + 2);
		}

	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeNamedElementFigure#setQualifiedName(java.lang.String)
	 *
	 * @param qualifiedName
	 */
	@Override
	public void setQualifiedName(String qualifiedName) {
		String tmpQualifiedName = getQualifiedName(qualifiedName, depth);
		// two raisons to remove label!
		// null
		// or the qualified name is equal to 1
		if (qualifiedName == null || tmpQualifiedName == null || !tmpQualifiedName.contains("::")) { // Remove
			// label
			// if
			// any
			if (this.qualifiedLabel != null) {
				this.remove(this.qualifiedLabel);
				this.qualifiedLabel = null;
			}
			return;
		}

		// Set the stereotype label
		if (this.qualifiedLabel == null) {
			this.createQualifiedNameLabel();
		}
		// we have to not display name.

		int i = tmpQualifiedName.lastIndexOf("::");
		if (i != -1) {
			tmpQualifiedName = tmpQualifiedName.substring(0, i);
		}
		this.qualifiedLabel.setText("(" + tmpQualifiedName.trim() + ")");

	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeNamedElementFigure#getQualifiedNameLabel()
	 *
	 * @return
	 */
	@Override
	public WrappingLabel getQualifiedNameLabel() {
		return null;
	}


	public ConstraintFigure() {
		this(null);
	}

	public ConstraintFigure(String tagLabel) {
		super();

		nameLabel = new PapyrusWrappingLabel();

		nameLabel.setOpaque(false);
		nameLabel.setAlignment(PositionConstants.MIDDLE);
		this.add(nameLabel);
		initTagLabel(tagLabel);

		page = new PapyrusWrappingLabel("");
		page.setOpaque(false);
		page.setTextWrap(true);
		this.add(page);

	}

	/**
	 * Draw a separator between the name figure and the body's.
	 *
	 * @see org.eclipse.draw2d.Figure#paint(org.eclipse.draw2d.Graphics)
	 *
	 * @param graphics
	 */
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);

		if (!drawSeparator) {
			return;
		}

		Rectangle figureBounds = this.getBounds();
		Rectangle pageBounds = page.getBounds();
		graphics.pushState();
		graphics.setForegroundColor(getForegroundColor());
		graphics.setLineWidth(1);

		graphics.drawLine(new Point(figureBounds.x, pageBounds.y), new Point(figureBounds.x + figureBounds.width, pageBounds.y));
		graphics.popState();

	}

	/**
	 * Checks if the separator is wanted.
	 *
	 * @return true, if it is
	 * @since 3.0
	 */
	public boolean isSeparated() {
		return drawSeparator;
	}

	/**
	 * Set the separator option
	 *
	 * @param isSeparated
	 * @since 3.0
	 */
	public void setSeparated(boolean isSeparated) {
		this.drawSeparator = isSeparated;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeNamedElementFigure#getTaggedLabel()
	 *
	 * @return
	 */
	@Override
	public WrappingLabel getTaggedLabel() {
		return taggedLabel;
	}

	/**
	 * Create the tag label in the figure. The tag label is created if value is
	 * not null.
	 *
	 * @param value
	 *            the value to use
	 */
	protected void initTagLabel(String value) {
		if (value != null && value.length() > 0) {
			taggedLabel = new WrappingLabel();
			String textToDisplay = new StringBuffer(CHEVRON).insert(1, value).toString();
			taggedLabel.setText(textToDisplay);
			taggedLabel.setOpaque(false);
			taggedLabel.setForegroundColor(getNameLabel().getForegroundColor());
			taggedLabel.setFont(getNameLabel().getFont());
			this.add(taggedLabel, null, 0);
		}
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeNamedElementFigure#setDepth(int)
	 *
	 * @param depth
	 */
	@Override
	public void setDepth(int depth) {


	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeNamedElementFigure#getNameLabel()
	 *
	 * @return
	 */
	@Override
	public WrappingLabel getNameLabel() {
		return nameLabel;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeNamedElementFigure#setNameLabelIcon(boolean)
	 *
	 * @param displayNameLabelIcon
	 */
	@Override
	public void setNameLabelIcon(boolean displayNameLabelIcon) {


	}

	/**
	 * create the label that contains the qualified name.
	 */
	protected void createQualifiedNameLabel() {
		qualifiedLabel = new Label();
		qualifiedLabel.setOpaque(false);
		qualifiedLabel.setFont(getNameLabel().getFont());
		qualifiedLabel.setForegroundColor(getNameLabel().getForegroundColor());
		// Add the label to the figure, after the name
		this.add(qualifiedLabel, getQualifiedNameLabelPosition());
	}

	protected int getQualifiedNameLabelPosition() {
		int position = getStereotypePropertiesLabelPosition();
		if (stereotypePropertiesInBraceContent != null) {
			position++;
		}
		return position;
	}

	/**
	 * use to obtain the reference of this figure (use in order to launch an
	 * edit request)
	 *
	 * @return the constraintfigure
	 */
	public ConstraintFigure getConstraintFigure() {
		return this;
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.ILabelFigure#setText(java.lang.String)
	 *
	 * @param text
	 */
	@Override
	public void setText(String text) {
		// generates new ones
		// textFlow.setText(LEFT_BRACE + text + RIGHT_BRACE);
		page.setText(LEFT_BRACE + text + RIGHT_BRACE);
	}

	/**
	 *
	 * @return the container of the text flow
	 * @since 3.0
	 */
	public WrappingLabel getPageFlow() {
		return page;

	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.ILabelFigure#getText()
	 *
	 * @return the display string that represents the specification
	 */
	@Override
	public String getText() {
		return page.getText();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.ILabelFigure#setIcon(org.eclipse.swt.graphics.Image)
	 *
	 * @param icon
	 */
	@Override
	public void setIcon(Image icon) {
		// Nothing
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.ILabelFigure#getIcon()
	 *
	 * @return
	 */
	@Override
	public Image getIcon() {
		return null;
	}

	/**
	 *
	 * @see org.eclipse.draw2d.Figure#getMinimumSize(int, int)
	 *
	 *
	 */
	@Override
	public Dimension getMinimumSize(int wHint, int hHint) {
		return new Dimension(20, 20);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.figure.node.IMultilineEditableFigure#getEditionLocation()
	 *
	 */
	@Override
	public Point getEditionLocation() {
		return page.getLocation();
	}

	/**
	 * Create a label that contains the name of the element.
	 */
	@Override
	public void restoreNameLabel() {
		// nameLabel.setOpaque(false);
		// nameLabel.setAlignment(PositionConstants.MIDDLE);
		// getNameLabelContainer().add(nameLabel, getNameLabelConstraint(), getNameLabelPosition());
	}

	@Override
	public void removeNameLabel() {
		// if(getNameLabelContainer().getChildren().contains(nameLabel)) {
		// getNameLabelContainer().remove(nameLabel);
		// }
	}

	@Override
	public void removeStereotypeLabel() {


	}

	@Override
	public void restoreStereotypeLabel() {


	}

	@Override
	public void restoreTaggedLabel() {


	}

	@Override
	public void removeTaggedLabel() {


	}


}
