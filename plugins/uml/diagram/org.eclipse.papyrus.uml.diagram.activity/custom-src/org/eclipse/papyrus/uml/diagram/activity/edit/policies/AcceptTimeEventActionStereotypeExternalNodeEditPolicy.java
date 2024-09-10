/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.IPapyrusEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeExternalNodeEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.node.IPapyrusUMLElementFigure;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.uml2.uml.AcceptEventAction;
import org.eclipse.uml2.uml.TimeEvent;
import org.eclipse.uml2.uml.UMLPackage;


/**
 * this is a specific class to display stereotype label for accept event action, it is only display if the event is a TimeEvent
 *
 */
public class AcceptTimeEventActionStereotypeExternalNodeEditPolicy extends AppliedStereotypeExternalNodeEditPolicy {

	/**
	 * add a specific test about TimeEvent
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AppliedStereotypeExternalNodeEditPolicy#refreshStereotypeDisplay()
	 *
	 */
	@Override
	protected void refreshStereotypeDisplay() {
		if (getHost() instanceof IPapyrusEditPart) {
			IFigure figure = ((IPapyrusEditPart) getHost()).getPrimaryShape();

			if (getUMLElement() instanceof AcceptEventAction) {
				if (((AcceptEventAction) getUMLElement()).getTriggers().size() > 0
						&& (((AcceptEventAction) getUMLElement()).getTriggers().get(0).getEvent() instanceof TimeEvent)) {
					if (figure instanceof IPapyrusUMLElementFigure) {
						// calculate text
						// and icon to display
						final String stereotypesToDisplay = stereotypesToDisplay();
						((IPapyrusUMLElementFigure) figure).setStereotypeDisplay(tag + (stereotypesToDisplay), null);
					}
				} else {
					((IPapyrusUMLElementFigure) figure).setStereotypeDisplay("", null);
				}
			}
		}

	}

	/**
	 *
	 * do not pary attention if this compartment or brace due to shape change
	 *
	 * @return the list of stereotypes to display with properties if there are
	 *         selected to be displayed
	 */
	@Override
	public String stereotypesToDisplay() {

		// retrieve all stereotypes to be displayed

		if (parentView == null) {
			return "";
		}

		// try to display stereotype properties
		String stereotypesToDisplay = StereotypeDisplayUtil.getInstance().getStereotypeTextToDisplay(parentView);
		return stereotypesToDisplay;

	}

	/**
	 * add notification about trigger
	 *
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractAppliedStereotypeDisplayEditPolicy#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	public void notifyChanged(Notification notification) {

		super.notifyChanged(notification);
		if (UMLPackage.eINSTANCE.getAcceptEventAction_Trigger().equals(notification.getFeature())) {
			refreshStereotypeDisplay();
		}
	}

}
