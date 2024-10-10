/*****************************************************************************
 * Copyright (c) 2023 CEA LIST, Obeo.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Obeo - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.sirius.uml.diagram.common.rotative;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.eclipse.draw2d.AncestorListener;
import org.eclipse.draw2d.FigureListener;
import org.eclipse.draw2d.IFigure;

/**
 * This RotativeImageListener notify the RotativeImageEditPart when a change occurs.
 * 
 * @author <a href="mailto:glenn.plouhinec@obeo.fr">Glenn Plouhinec</a>
 */
public class RotativeImageListener implements FigureListener, PropertyChangeListener, AncestorListener {

	/**
	 * The EditPart to listen.
	 */
	private RotativeImageEditPart rotativeImageEditPart;

	/**
	 * Creates a new RotativeImageListener.
	 *
	 * @param rotativeImageEditPart
	 *            the EditPart to listen
	 */
	public RotativeImageListener(RotativeImageEditPart rotativeImageEditPart) {
		this.rotativeImageEditPart = rotativeImageEditPart;
	}

	/**
	 * Invoked when the figure moves.
	 * 
	 * @param figure
	 *            the figure that moved
	 */
	@Override
	public void figureMoved(IFigure figure) {
		rotativeImageEditPart.updateImage();
	}

	/**
	 * Invoked when a bound property is changed.
	 * 
	 * @param changeEvent
	 *            A PropertyChangeEvent object describing the event source and the property that has changed
	 */
	@Override
	public void propertyChange(PropertyChangeEvent changeEvent) {
		rotativeImageEditPart.updateImage();
	}

	/**
	 * Invoked when an ancestor is added.
	 * 
	 * @param ancestor
	 *            the added ancestor
	 */
	@Override
	public void ancestorAdded(IFigure ancestor) {
		rotativeImageEditPart.updateImage();
	}

	/**
	 * Invoked when an ancestor is moved.
	 * 
	 * @param ancestor
	 *            the ancestor that has moved
	 */
	@Override
	public void ancestorMoved(IFigure ancestor) {
		rotativeImageEditPart.updateImage();
	}

	/**
	 * Invoked when an ancestor is removed.
	 * 
	 * @param ancestor
	 *            the ancestor removed
	 */
	@Override
	public void ancestorRemoved(IFigure ancestor) {
		rotativeImageEditPart.updateImage();
	}

}
