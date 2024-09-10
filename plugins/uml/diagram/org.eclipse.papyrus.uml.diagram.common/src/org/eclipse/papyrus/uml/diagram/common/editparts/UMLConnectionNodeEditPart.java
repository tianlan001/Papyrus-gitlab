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
 *  Remi Schnekenburger (CEA LIST) remi.schnekenburger@cea.fr - Initial API and implementation
 *  Gabriel Pascual (ALL4TEC) gabriel.pascual@all4tec.fr - Bug 393532
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editparts;

import java.util.Iterator;
import java.util.StringTokenizer;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.Request;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.gmfdiag.common.editpart.ConnectionEditPart;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ApplyStereotypeEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.ShowHideLabelEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.figure.edge.UMLEdgeFigure;
import org.eclipse.papyrus.uml.diagram.common.service.ApplyStereotypeRequest;
import org.eclipse.papyrus.uml.diagram.common.stereotype.display.helper.StereotypeDisplayUtil;
import org.eclipse.papyrus.uml.tools.listeners.StereotypeElementListener.StereotypeExtensionNotification;
import org.eclipse.swt.graphics.Image;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

/**
 * Abstract edit part for all connection nodes that control UML elements
 * represented by edges. It then defines basic behavior for Stereotype
 * management. It has facilities to retrieve UML element controlled by this edit
 * part
 */
public abstract class UMLConnectionNodeEditPart extends ConnectionEditPart implements IUMLEditPart, IEditpartListenerAccess {

	/**
	 * Creates a new UMLConnectionNodeEditPart
	 *
	 * @param view
	 *            owned view by this edit part
	 */
	public UMLConnectionNodeEditPart(View view) {
		super(view);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void addSemanticListeners() {
		super.addSemanticListeners();

		// retrieve element
		final Element element = getUMLElement();
		if (element == null) {
			return;
		}

		// add listener to react to the application and remove of a stereotype
		addListenerFilter(STEREOTYPABLE_ELEMENT, this, resolveSemanticElement());

		// add a lister to each already applied stereotyped
		for (EObject stereotypeApplication : element.getStereotypeApplications()) {
			addListenerFilter(STEREOTYPED_ELEMENT, this, stereotypeApplication);
		}
	}

	@Override
	public final Iterator getEventListenerIterator(Class clazz) {
		return getEventListeners(clazz);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void handleNotificationEvent(Notification event) {
		super.handleNotificationEvent(event);

		// NOTA: should check here which element has to be refreshed

		// check if this concerns a stereotype application or unapplication
		final int eventType = event.getEventType();

		if (eventType == StereotypeExtensionNotification.STEREOTYPE_APPLIED_TO_ELEMENT) {
			// a stereotype was applied to the notifier
			// then a new listener should be added to the stereotype application
			addListenerFilter(STEREOTYPED_ELEMENT, this, (EObject) event.getNewValue());
		}

		// // refresh the figure if stereotypes have changed
		// if (resolveSemanticElement() != null) {
		// refreshAppliedStereotypes();
		// }
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		// adds the stereotype application edit policy
		installEditPolicy(ApplyStereotypeRequest.APPLY_STEREOTYPE_REQUEST, new ApplyStereotypeEditPolicy());

		// adds a custom EditPolicy to manage the displaying of each label on
		// the connector
		// this editpolicy erase ConnectionLabelsEditPolicy
		installEditPolicy(ShowHideLabelEditPolicy.SHOW_HIDE_LABEL_ROLE, new ShowHideLabelEditPolicy());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EditPart getTargetEditPart(Request request) {
		if (ApplyStereotypeRequest.APPLY_STEREOTYPE_REQUEST.equals(request.getType())) {
			return this;
		}
		return super.getTargetEditPart(request);
	}

	/**
	 * <p>
	 * Returns the primary shape being the View of this edit part.
	 * </p>
	 * <b>Warning</b> It should never return <code>null</code>
	 *
	 * @return the primary shape associated to this edit part.
	 */
	@Override
	public abstract UMLEdgeFigure getPrimaryShape();

	/**
	 * Refresh the display of stereotypes for this uml node edit part.
	 */
	public void refreshAppliedStereotypes() {
		// computes the stereotypes to be displayed
		final String stereotypesToDisplay = stereotypesToDisplay();
		// computes the icon to be displayed
		final Image imageToDisplay = stereotypeIconToDisplay();

		// if the string is not empty, then, the figure has to display it. Else,
		// it displays nothing
		if (!"".equals(stereotypesToDisplay) || imageToDisplay != null) {
			getPrimaryShape().setStereotypeDisplay(stereotypesToDisplay, imageToDisplay);
		} else {
			// getPrimaryShape().setStereotypeDisplay(null, null);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void removeSemanticListeners() {
		super.removeSemanticListeners();

		// remove listeners to react to the application and remove of
		// stereotypes
		removeListenerFilter(STEREOTYPABLE_ELEMENT);
		removeListenerFilter(STEREOTYPED_ELEMENT);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Element getUMLElement() {
		return (Element) resolveSemanticElement();
	}

	/**
	 * get the list of stereotype to display from the eannotation
	 *
	 * @return the list of stereotypes to display
	 */
	public String stereotypesToDisplay() {
		String stereotypesToDisplay = StereotypeDisplayUtil.getInstance().getStereotypeTextToDisplay((View) getModel());

		return stereotypesToDisplay;

	}

	/**
	 * Returns the image to be displayed for the applied stereotypes.
	 *
	 * @return the image that represents the first applied stereotype or <code>null</code> if no image has to be displayed
	 */
	public Image stereotypeIconToDisplay() {


		// retrieve the first stereotype in the list of displayed stereotype
		String stereotypesToDisplay = StereotypeDisplayUtil.getInstance().getStereotypeTextToDisplay((View) getModel());
		StringTokenizer tokenizer = new StringTokenizer(stereotypesToDisplay, ",");
		if (tokenizer.hasMoreTokens()) {
			String firstStereotypeName = tokenizer.nextToken();
			Stereotype stereotype = getUMLElement().getAppliedStereotype(firstStereotypeName);
			return Activator.getIconElement(getUMLElement(), stereotype, false);
		}

		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setLineWidth(int width) {
		getPrimaryShape().setLineWidth(width < 0 ? 1 : width);
	}

}
