/*****************************************************************************
 * Copyright (c) 2009-2011 CEA LIST.
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
 *  Yann Tanguy (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.composite.custom.figures;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.diagram.ui.figures.BorderedNodeFigure;
import org.eclipse.papyrus.uml.diagram.common.figure.node.AffixedNamedElementFigure;
import org.eclipse.papyrus.uml.diagram.composite.custom.locators.BehaviorPositionLocator;
import org.eclipse.papyrus.uml.diagram.composite.custom.locators.LineDecoratorLocator;
import org.eclipse.swt.graphics.Color;


/**
 * Figure for Port representation.
 */
public class PortFigure extends AffixedNamedElementFigure {

	private BehaviorFigure myBehavior = null;

	private LineDecorator myLineDecor = null;

	public boolean isBehaviored() {
		return myBehavior != null && myLineDecor != null
				&& getBorderItemContainer().getChildren().contains(myBehavior) &&
				getBorderItemContainer().getChildren().contains(myBehavior);
	}

	public BehaviorFigure getBehavior() {
		return myBehavior;
	}

	public void restoreBehaviorFigure() {
		if (myBehavior == null) {
			myBehavior = createBehavior();
		}
		if (myLineDecor == null) {
			myLineDecor = createLineDecorator();
		}
		if (!getBorderItemContainer().getChildren().contains(myBehavior)) {
			getBorderItemContainer().add(myBehavior, new BehaviorPositionLocator(this));
		}
		if (!getBorderItemContainer().getChildren().contains(myLineDecor)) {
			getBorderItemContainer().add(myLineDecor, new LineDecoratorLocator(this));
		}
	}

	protected BehaviorFigure createBehavior() {
		BehaviorFigure behavior = new BehaviorFigure(PortFigure.this);
		behavior.setShadowColor(getShadowColor());
		behavior.setShadowWidth(getShadowWidth());
		behavior.setShadow(getShadow());
		behavior.setLineStyle(getLineStyle());
		behavior.setLineWidth(getLineWidth());
		behavior.setIsUsingGradient(false);
		behavior.setForegroundColor(getForegroundColor());
		behavior.setBackgroundColor(getForegroundColor());
		return behavior;
	}

	private LineDecorator createLineDecorator() {
		LineDecorator line = new LineDecorator();
		line.setForegroundColor(getForegroundColor());
		line.setBackgroundColor(getBackgroundColor());
		line.setLineWidth(getLineWidth());
		line.setLineStyle(getLineStyle());
		return line;
	}

	public void removeBehavior() {
		if (myBehavior != null) {
			if (getBorderItemContainer().getChildren().contains(myBehavior)) {
				getBorderItemContainer().remove(myBehavior);
			}
		}
		if (myLineDecor != null) {
			if (getBorderItemContainer().getChildren().contains(myLineDecor)) {
				getBorderItemContainer().remove(myLineDecor);
			}
		}
	}

	private IFigure getBorderItemContainer() {
		IFigure borderedFigure = this;
		while (false == borderedFigure instanceof BorderedNodeFigure && borderedFigure != null) {
			borderedFigure = borderedFigure.getParent();
		}
		return ((BorderedNodeFigure) borderedFigure).getBorderItemContainer();
	}

	@Override
	public void setShadowColor(String shadowColor) {
		super.setShadowColor(shadowColor);
		if (myBehavior != null) {
			myBehavior.setShadowColor(shadowColor);
		}
	}

	@Override
	public void setShadowWidth(int shadowWidth) {
		super.setShadowWidth(shadowWidth);
		if (myBehavior != null) {
			myBehavior.setShadowWidth(shadowWidth);
		}
	}

	@Override
	public void setShadow(boolean shadow) {
		super.setShadow(shadow);
		if (myBehavior != null) {
			myBehavior.setShadow(shadow);
		}
	}

	@Override
	public void setLineStyle(int s) {
		super.setLineStyle(s);
		if (myBehavior != null) {
			myBehavior.setLineStyle(s);
		}
		if (myLineDecor != null) {
			myLineDecor.setLineStyle(s);
		}
	}

	@Override
	public void setLineWidth(int w) {
		super.setLineWidth(w);
		if (myBehavior != null) {
			myBehavior.setLineWidth(w);
		}
		if (myLineDecor != null) {
			myLineDecor.setLineWidth(w);
		}
	}

	@Override
	public void setForegroundColor(Color fg) {
		super.setForegroundColor(fg);
		if (myBehavior != null) {
			myBehavior.setForegroundColor(fg);
			myBehavior.setBackgroundColor(fg);
		}
		if (myLineDecor != null) {
			myLineDecor.setForegroundColor(fg);
		}
	}
}
