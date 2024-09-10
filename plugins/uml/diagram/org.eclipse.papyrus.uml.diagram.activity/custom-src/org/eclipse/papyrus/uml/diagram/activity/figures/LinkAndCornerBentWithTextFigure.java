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
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.figures;

import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineShape;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.gmf.runtime.diagram.ui.figures.IBorderItemLocator;
import org.eclipse.papyrus.uml.diagram.activity.edit.policies.BehaviorPropertyNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.activity.locator.LinkedBehaviorLocator;
import org.eclipse.papyrus.uml.diagram.common.figure.node.CornerBentFigure;
import org.eclipse.swt.graphics.Color;


public class LinkAndCornerBentWithTextFigure extends CornerBentFigure {

	private static final Color THIS_BACK = new Color(null, 248, 249, 214);

	private WrappedLabel fCornerBentContent;

	private PolylineShape fLinkToBehaviorProperty;

	public LinkAndCornerBentWithTextFigure() {


		this.setBackgroundColor(THIS_BACK);
		createContents();
	}

	private void createContents() {
		fCornerBentContent = new WrappedLabel();
		this.add(fCornerBentContent);
		fLinkToBehaviorProperty = new PolylineShape();
		fLinkToBehaviorProperty.setLineWidth(1);
		fLinkToBehaviorProperty.setLineStyle(Graphics.LINE_DASH);
		this.add(fLinkToBehaviorProperty);
		// do not add link in this figure but refresh it when figure moves
		addFigureListener(new FigureListener() {

			@Override
			public void figureMoved(IFigure source) {
				refreshLinkToBehaviorProperty();
			}
		});
	}

	public IBorderItemLocator getBorderItemLocator() {
		IFigure parentFigure = this.getParent();
		if (parentFigure != null && parentFigure.getLayoutManager() != null) {
			Object constraint = parentFigure.getLayoutManager().getConstraint(this);
			return (IBorderItemLocator) constraint;
		}
		return null;
	}

	/**
	 * Refresh the link between parent figure and this one
	 */
	private void refreshLinkToBehaviorProperty() {
		if (getLinkToBehaviorProperty().getParent() == null) {
			// add in appropriate figure
			getParent().add(getLinkToBehaviorProperty());
		}
		if (false == getBorderItemLocator() instanceof LinkedBehaviorLocator) {
			return;
		}
		if (getParent() != null && getParent().getParent() instanceof BorderedNodeFigure) {
			BorderedNodeFigure gParent = (BorderedNodeFigure) getParent().getParent();
			Rectangle parentBounds = gParent.getHandleBounds().getCopy();
			Point parentCenter = parentBounds.getCenter();
			IFigure rect = gParent.getMainFigure();
			LinkedBehaviorLocator linkedBehaviorLocator = (LinkedBehaviorLocator) getBorderItemLocator();
			Rectangle currentBounds = linkedBehaviorLocator.getCorrectItemLocation(this);
			Point end = BehaviorPropertyNodeEditPolicy.getAppropriateBorderPoint(parentCenter, currentBounds);
			PointList polygonalBounds = new PointList(4);
			polygonalBounds.addPoint(rect.getBounds().getTopLeft());
			polygonalBounds.addPoint(rect.getBounds().getTopRight());
			polygonalBounds.addPoint(rect.getBounds().getBottomRight());
			polygonalBounds.addPoint(rect.getBounds().getBottomLeft());
			Point start = BehaviorPropertyNodeEditPolicy.getIntersectionPoint(polygonalBounds, parentCenter, end);
			if (start == null) {
				// in case start computation fails
				start = parentCenter;
			}
			// adapt ends to bounds
			Rectangle linkBounds = new Rectangle(start, end);
			getLinkToBehaviorProperty().setStart(start.translate(linkBounds.getLocation().getNegated()));
			getLinkToBehaviorProperty().setEnd(end.translate(linkBounds.getLocation().getNegated()));
			getLinkToBehaviorProperty().setBounds(linkBounds);
		}
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		getLinkToBehaviorProperty().setVisible(visible);
	}

	public WrappedLabel getCornerBentContent() {
		return fCornerBentContent;
	}

	public PolylineShape getLinkToBehaviorProperty() {
		return fLinkToBehaviorProperty;
	}
}
