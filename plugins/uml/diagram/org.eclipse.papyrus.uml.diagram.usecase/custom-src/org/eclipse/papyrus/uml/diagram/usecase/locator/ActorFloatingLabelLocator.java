/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Pauline DEVILLE (CEA LIST) - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.usecase.locator;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.papyrus.uml.diagram.common.locator.RoundedRectangleLabelPositionLocator;

/**
 * The class relocate the actor name when it is the default position the label is moved
 * to the bottom the bottom of the actor and place in the center
 *
 * @since 4.0
 */
public class ActorFloatingLabelLocator extends RoundedRectangleLabelPositionLocator {

	protected static final int SPACE_BEETWEN_ACTOR_AND_LABEL = 5;

	protected static final int DEFAULT_ACTOR_LABEL_X_POSITION = 0;

	protected static final int DEFAULT_ACTOR_LABEL_Y_POSITION = 15;

	/**
	 * Constructor.
	 *
	 * @param borderItem
	 * @param parentFigure
	 * @param constraint
	 * @param interval
	 */
	public ActorFloatingLabelLocator(IFigure borderItem, IFigure parentFigure, Rectangle constraint, int interval) {
		super(borderItem, parentFigure, constraint, interval);
	}

	/**
	 * Constructor.
	 *
	 * @param borderItem
	 * @param parentFigure
	 * @param constraint
	 */
	public ActorFloatingLabelLocator(IFigure borderItem, IFigure parentFigure, Rectangle constraint) {
		super(borderItem, parentFigure, constraint);
	}

	/**
	 * Constructor.
	 *
	 * @param parentFigure
	 * @param preferredSide
	 * @param interval
	 */
	public ActorFloatingLabelLocator(IFigure parentFigure, int preferredSide, int interval) {
		super(parentFigure, preferredSide, interval);
	}

	/**
	 * Constructor.
	 *
	 * @param parentFigure
	 * @param preferredSide
	 */
	public ActorFloatingLabelLocator(IFigure parentFigure, int preferredSide) {
		super(parentFigure, preferredSide);
	}

	/**
	 * Constructor.
	 *
	 * @param parentFigure
	 */
	public ActorFloatingLabelLocator(IFigure parentFigure) {
		super(parentFigure);
	}

	/**
	 * Constructor.
	 *
	 * @param interval
	 * @param parentFigure
	 */
	public ActorFloatingLabelLocator(int interval, IFigure parentFigure) {
		super(interval, parentFigure);
	}

	/**
	 * get the default position of the label (bottom of the actor shape and center)
	 *
	 * @param labelFigure
	 * @param actorRect
	 * @return the default position
	 */
	private Point getDefaultLocation(Rectangle labelFigure, Rectangle actorRect) {
		int X = actorRect.x + (actorRect.width / 2) - labelFigure.width / 2;
		int Y = actorRect.y + actorRect.height + SPACE_BEETWEN_ACTOR_AND_LABEL;
		return new Point(X, Y);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.locator.RoundedRectangleLabelPositionLocator#relocate(org.eclipse.draw2d.IFigure)
	 *
	 * @param target
	 */
	@Override
	public void relocate(IFigure target) {
		Dimension size = getSize(target);
		Rectangle rectSuggested = getConstraint().getCopy();
		if (rectSuggested.getTopLeft().x == DEFAULT_ACTOR_LABEL_X_POSITION && rectSuggested.getTopLeft().y == DEFAULT_ACTOR_LABEL_Y_POSITION) {
			rectSuggested.setLocation(getDefaultLocation(target.getBounds().getCopy(), getParentBorder()));
			rectSuggested.setSize(size);
			target.setBounds(rectSuggested);
		} else {
			super.relocate(target);
		}
	}


}
