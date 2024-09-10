/*****************************************************************************
 * Copyright (c) 2009, 2018 CEA LIST.
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
 *  Celine Janssens (All4Tec) celine.janssens@all4tec.net - Bug 472342
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusUMLElementFigure;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Stereotype;

/**
 * the goal of this edit policy is to display applied stereotype and properties
 * of stereotypes in to a label attached to a link the edge figure figure has to
 * be a {@link IPapyrusUMLElementFigure}
 */
public class AppliedStereotypeLinkLabelDisplayEditPolicy extends AppliedStereotypeLabelDisplayEditPolicy {

	public AppliedStereotypeLinkLabelDisplayEditPolicy() {
		super();
	}

	public AppliedStereotypeLinkLabelDisplayEditPolicy(String tag) {
		super(tag);
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeLabelDisplayEditPolicy#activate()
	 *
	 */
	@Override
	public void activate() {
		super.activate();

	}

	@Override
	public String stereotypesToDisplay() {
		// if the display is not as Brace location the properties of the stereotype is not display
		return super.stereotypesToDisplay();

	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractAppliedStereotypeDisplayEditPolicy#refreshStereotypeBraceStructure(org.eclipse.uml2.uml.Stereotype)
	 *
	 * @param stereotype
	 */
	@Override
	public void refreshStereotypeBraceStructure(Stereotype stereotype) {
		// Do nothing
		// No brace for link Label
	}

	/**
	 * Refreshes the stereotype display
	 */
	@Override
	protected void refreshStereotypeDisplay() {
		IFigure figure = ((GraphicalEditPart) getHost()).getFigure();
		// View view = (View) getHost().getModel();

		// calculate text and icon to display
		final String stereotypesToDisplay = stereotypesToDisplay();
		// computes the icon to be displayed
		final Image imageToDisplay = stereotypeIconToDisplay();

		// if the string is not empty, then, the figure has to display it. Else,
		// it displays nothing
		if (figure instanceof IPapyrusUMLElementFigure) {
			((IPapyrusUMLElementFigure) figure).setStereotypeDisplay(tag + stereotypesToDisplay, imageToDisplay);
		}
	}

}
