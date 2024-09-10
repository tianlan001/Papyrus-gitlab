/*****************************************************************************
 * Copyright (c) 2009, 2016 CEA LIST, Christian W. Damus, and others.
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
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Patrick Tessier (CEA LIST) - Initial API and implementation
 *  Celine JANSSENS (ALL4TEC) celine.janssens@all4tec.net - Bug 455311 Stereotype Display
 *  Christian W. Damus - bug 492482
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusUMLElementFigure;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayConstant;

/**
 * This edit policy is used to display only applied stereotypes and properties
 * in a external node (that is a labelEditPart) In order to use it, the edit
 * part has to be {@link IPapyrusEditPart} and the associated figure has to be {@link IPapyrusUMLElementFigure}
 */
public class AppliedStereotypeExternalNodeEditPolicy extends AppliedStereotypeLabelDisplayEditPolicy {

	protected View parentView = null;

	public AppliedStereotypeExternalNodeEditPolicy() {
		super();
	}

	@Override
	public void activate() {
		// retrieve the view and the element managed by the edit part
		View view = getView();
		if (view == null) {
			return;
		}
		super.activate();
		// add a listener for TimeObservationEditPart
		// eContainer = getParent() , but here it's the ECore model
		EObject parent = view.eContainer();
		if (parent instanceof View) {
			parentView = (View) parent;
			subscribe(parentView);
		}



	}

	@Override
	public void deactivate() {
		if (parentView != null) {
			unsubscribe(parentView);
		}

		super.deactivate();
	}

	/**
	 * Refresh the text of the stereotype
	 */
	@Override
	protected void refreshStereotypeDisplay() {
		IFigure figure;
		if (getHost() instanceof IPapyrusEditPart) {
			figure = ((IPapyrusEditPart) getHost()).getPrimaryShape();
		} else {
			figure = hostEditPart.getFigure();
		}

		if (figure instanceof IPapyrusUMLElementFigure) {// calculate text
			// and icon to display
			final String stereotypesToDisplay = stereotypesToDisplay();
			((IPapyrusUMLElementFigure) figure).setStereotypeDisplay(tag + (stereotypesToDisplay), null);

		}



	}

	/**
	 * Retrieve the List of the Stereotypes to Display
	 *
	 * @return the list of stereotypes to display with properties if there are
	 *         selected to be displayed
	 */
	@Override
	public String stereotypesToDisplay() {

		// retrieve all stereotypes to be displayed

		if (parentView == null) {
			return EMPTY_STRING;
		}

		// try to display stereotype properties
		final String stereotypesToDisplay = helper.getStereotypeTextToDisplay(parentView);
		final String stereotypesPropertiesToDisplay = helper.getStereotypePropertiesInBrace(parentView);

		String display = getStereotypeAndPropertiesTextToDisplay(stereotypesToDisplay, stereotypesPropertiesToDisplay);

		return display;

	}

	/**
	 * @param stereotypesToDisplay
	 * @param stereotypesPropertiesToDisplay
	 * @param display
	 * @return
	 */
	private String getStereotypeAndPropertiesTextToDisplay(final String stereotypesToDisplay, final String stereotypesPropertiesToDisplay) {
		StringBuilder display = new StringBuilder();
		if (stereotypesToDisplay != null && !stereotypesToDisplay.isEmpty()) {
			display.append(stereotypesToDisplay);
		}

		if (stereotypesPropertiesToDisplay != null && !stereotypesPropertiesToDisplay.isEmpty()) {
			if (display.length() > 0) {
				display.append(StereotypeDisplayConstant.STEREOTYPE_PROPERTY_SEPARATOR);
			}
			display.append(StereotypeDisplayConstant.BRACE_LEFT + stereotypesPropertiesToDisplay + StereotypeDisplayConstant.BRACE_RIGHT);
		}
		return display.toString();
	}

}
