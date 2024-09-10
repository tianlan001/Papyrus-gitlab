/*****************************************************************************
 * Copyright (c) 2010, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - Initial API and implementation
 *   Christian W. Damus - bug 517404
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.BooleanValueStyle;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.StringValueStyle;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.appearance.helper.AppearanceHelper;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.FollowSVGSymbolEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.NameDisplayEditPolicy;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.IPapyrusWrappingLabel;
import org.eclipse.papyrus.infra.gmfdiag.common.figure.node.SelectableBorderedNodeFigure;
import org.eclipse.papyrus.infra.gmfdiag.common.model.NotationUtils;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.DiagramEditPartsUtil;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.NamedStyleProperties;
import org.eclipse.papyrus.infra.gmfdiag.common.utils.PositionEnum;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusNodeNamedElementFigure;
import org.eclipse.papyrus.uml.diagram.common.figure.node.NodeNamedElementFigure;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.uml2.uml.NamedElement;

/**
 * this editpart manage the font and icon of the name label and qualified name
 * label.
 *
 */
public abstract class NamedElementEditPart extends UMLNodeEditPart implements IUMLNamedElementEditPart, NamedStyleProperties {

	/** Default Top Margin when not present in CSS. */
	public static final int DEFAULT_TOP_MARGIN = 0;

	/** Default Bottom Margin when not present in CSS. */
	public static final int DEFAULT_BOTTOM_MARGIN = 0;

	/** Default Left Margin when not present in CSS, set to 5 - see bug 516265 */
	public static final int DEFAULT_LEFT_MARGIN = 5;

	/** Default Right Margin when not present in CSS, see left margin above */
	public static final int DEFAULT_RIGHT_MARGIN = 5;

	/**
	 * {@inheritDoc}
	 */
	public NamedElementEditPart(View view) {
		super(view);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.IUMLNamedElementEditPart#getNamedElement()
	 *
	 * @return
	 */
	@Override
	public NamedElement getNamedElement() {
		return (NamedElement) getUMLElement();
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	protected void handleNotificationEvent(Notification event) {
		super.handleNotificationEvent(event);

		// set the figure active when the feature of the of a class is true
		if (resolveSemanticElement() != null) {
			refreshIconNamedLabel();
			refreshFontColor();
		}
	}

	/**
	 * Refresh icon named label.
	 */
	private void refreshIconNamedLabel() {
		getNodeNamedElementFigure().setNameLabelIcon(AppearanceHelper.showElementIcon((View) getModel()));
	}


	/**
	 * Refresh.
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.UMLNodeEditPart#refresh()
	 */
	@Override
	public void refresh() {
		refreshNamePosition();
		super.refresh();
	}

	/**
	 * Refresh visuals.
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.UMLNodeEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		if (getNodeNamedElementFigure() != null && resolveSemanticElement() != null) {
			refreshIconNamedLabel();
			refreshFontColor();
			refreshLabelDisplay();
			refreshLabelMargin();
		}
	}


	/**
	 * Refresh name position.
	 */
	private void refreshNamePosition() {
		if (getPrimaryShape() instanceof NodeNamedElementFigure) {
			((NodeNamedElementFigure) getPrimaryShape()).setNamePosition(getNamePosition());
		}
	}


	/**
	 * Gets the name position.
	 *
	 * @return the name position
	 */
	public int getNamePosition() {
		// get the value of the CSS property
		View model = (View) getModel();
		StringValueStyle labelAlignment = (StringValueStyle) model.getNamedStyle(NotationPackage.eINSTANCE.getStringValueStyle(), TEXT_ALIGNMENT);

		int textAlignment = 0;
		if (labelAlignment != null) {
			String strLabelAlignment = labelAlignment.getStringValue();
			if (PositionEnum.LEFT.toString().equals(strLabelAlignment)) {
				textAlignment = PositionConstants.LEFT;
			}
			if (PositionEnum.RIGHT.toString().equals(strLabelAlignment)) {
				textAlignment = PositionConstants.RIGHT;
			}
			if (PositionEnum.CENTER.toString().equals(strLabelAlignment)) {
				textAlignment = PositionConstants.CENTER;
			}
		} else {
			textAlignment = getDefaultNamePosition();
		}
		return textAlignment;
	}


	/**
	 * Gets the default name position.
	 *
	 * @return the default name position
	 */
	protected int getDefaultNamePosition() {
		return PositionConstants.CENTER;
	}

	/**
	 * Refresh margin of named element children labels
	 * <ul>
	 * <li>Get Css values</li>
	 * <li>Get all the children figure</li>
	 * <li>If the child is a label then apply the margin</li>
	 * </ul>
	 * .
	 */
	private void refreshLabelMargin() {
		Object model = this.getModel();

		if (model instanceof View) {
			int leftMargin = NotationUtils.getIntValue((View) model, LEFT_MARGIN_PROPERTY, getDefaultLeftNameMargin());
			int rightMargin = NotationUtils.getIntValue((View) model, RIGHT_MARGIN_PROPERTY, getDefaultRightNameMargin());
			int topMargin = NotationUtils.getIntValue((View) model, TOP_MARGIN_PROPERTY, getDefaultTopNameMargin());
			int bottomMargin = NotationUtils.getIntValue((View) model, BOTTOM_MARGIN_PROPERTY, getDefaultBottomNameMargin());

			EObject semantic = resolveSemanticElement();

			// Get all margined labels for this element and set their margins
			for (TreeIterator<EditPart> contents = DiagramEditPartsUtil.getAllContents(this, false); contents.hasNext();) {
				EditPart next = contents.next();

				if (next instanceof IGraphicalEditPart) {
					IGraphicalEditPart gep = (IGraphicalEditPart) next;
					if (gep.resolveSemanticElement() != semantic) {
						// Different semantic element: it has its own margins
						contents.prune();
					} else {
						// Is its figure a papyrus-style wrapping label?
						IFigure figure = (gep instanceof IPapyrusEditPart)
								? ((IPapyrusEditPart) gep).getPrimaryShape()
								: gep.getFigure();
						if (figure instanceof IPapyrusWrappingLabel) {
							// Set the margins
							IPapyrusWrappingLabel label = (IPapyrusWrappingLabel) figure;
							label.setMarginLabel(leftMargin, topMargin, rightMargin, bottomMargin);
						}
					}
				}
			}
		}
	}


	protected int getDefaultBottomNameMargin() {
		return DEFAULT_BOTTOM_MARGIN;
	}

	protected int getDefaultTopNameMargin() {
		return DEFAULT_TOP_MARGIN;
	}

	protected int getDefaultRightNameMargin() {
		return DEFAULT_RIGHT_MARGIN;
	}

	protected int getDefaultLeftNameMargin() {
		return DEFAULT_LEFT_MARGIN;
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart#activate()
	 *
	 */
	@Override
	public void activate() {
		super.activate();
	}

	/**
	 * Refresh label display.
	 */
	protected void refreshLabelDisplay() {

		View view = getNotationView();
		IPapyrusNodeNamedElementFigure figure = getNodeNamedElementFigure();

		// SVGNodePlate can be null!
		if (svgNodePlate != null) {
			if (svgNodePlate.hasLabelBounds()) {
				figure.getNameLabel().setTextWrap(true);
			} else {
				boolean isWrap = NotationUtils.getBooleanValue(view, WRAP_NAME, false);
				figure.getNameLabel().setTextWrap(isWrap);
			}
		}

		// refresh the Label of Stereotypes according to the alignment, opacity and position
		figure.restoreStereotypeLabel();

		// Get NamedStyle display preferences
		BooleanValueStyle displayNameStyle = (BooleanValueStyle) view.getNamedStyle(NotationPackage.eINSTANCE.getBooleanValueStyle(), NameDisplayEditPolicy.DISPLAY_NAME);
		BooleanValueStyle displayTags = (BooleanValueStyle) view.getNamedStyle(NotationPackage.eINSTANCE.getBooleanValueStyle(), DISPLAY_TAGS);



		// Manage the display of Name Label
		if (displayNameStyle != null && !displayNameStyle.isBooleanValue()) {
			figure.removeNameLabel();
			figure.removeTaggedLabel();
		} else {
			figure.restoreNameLabel();
			// Manage the display of the Stereotypes Properties Label
			if (displayTags != null && !displayTags.isBooleanValue()) {
				figure.removeTaggedLabel();
			} else {
				figure.restoreTaggedLabel();
			}
		}
	}

	/**
	 * A method to specify the labels to be update when the font is refreshed.
	 * Subclasses should call super.refreshLabelsFont(font)
	 *
	 * @param font
	 *            the font to use
	 */
	@Override
	protected void refreshLabelsFont(Font font) {
		super.refreshLabelsFont(font);
		// Apply the font to the Name Label
		getNodeNamedElementFigure().getNameLabel().setFont(font);
		// Apply the font to the Qualified Name
		if (getNodeNamedElementFigure().getQualifiedNameLabel() != null) {
			getNodeNamedElementFigure().getQualifiedNameLabel().setFont(font);
		}
		// Apply the font to the tagged Label
		if (getNodeNamedElementFigure().getTaggedLabel() != null) {
			getNodeNamedElementFigure().getTaggedLabel().setFont(font);
		}
	}

	/**
	 * Gets the node named element figure.
	 *
	 * @return the node named element figure
	 */
	private IPapyrusNodeNamedElementFigure getNodeNamedElementFigure() {
		return (IPapyrusNodeNamedElementFigure) getPrimaryShape();
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	protected void setFontColor(Color color) {
		super.setFontColor(color);
		// Qualified Name
		if (getNodeNamedElementFigure().getQualifiedNameLabel() != null) {
			getNodeNamedElementFigure().getQualifiedNameLabel().setForegroundColor(color);
		}
		// TaggedLabel
		if (getNodeNamedElementFigure().getTaggedLabel() != null) {
			getNodeNamedElementFigure().getTaggedLabel().setForegroundColor(color);
		}
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editparts.UMLNodeEditPart#createDefaultEditPolicies()
	 *
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(NameDisplayEditPolicy.NAME_DISPLAY_EDITPOLICY, new NameDisplayEditPolicy());
		installEditPolicy(FollowSVGSymbolEditPolicy.FOLLOW_SVG_SYMBOL_EDITPOLICY, new FollowSVGSymbolEditPolicy());
	}

	/**
	 * @see org.eclipse.gmf.runtime.diagram.ui.editparts.AbstractBorderedShapeEditPart#createNodeFigure()
	 *
	 * @return
	 */
	@Override
	protected NodeFigure createNodeFigure() {
		return new SelectableBorderedNodeFigure(createMainFigureWithSVG());
	}

}
