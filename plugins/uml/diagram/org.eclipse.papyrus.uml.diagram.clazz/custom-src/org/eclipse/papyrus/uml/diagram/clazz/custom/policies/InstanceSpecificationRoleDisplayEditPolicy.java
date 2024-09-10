/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Gabriel Pascual (ALL4TEC)  gabriel.pascual@all4tec.net -  bug 382954
 *  Benoit Maggi (CEA LIST) benoit.maggi@cea.fr - Patch #44973
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.clazz.custom.policies;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.gef.ui.internal.editpolicies.GraphicalEditPolicyEx;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.core.listenerservice.IPapyrusListener;
import org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.internationalization.utils.utils.UMLLabelInternationalization;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.Property;

/**
 * It is used to refresh the label of the role in the instance specification
 */
@SuppressWarnings("restriction")
public abstract class InstanceSpecificationRoleDisplayEditPolicy extends GraphicalEditPolicyEx implements NotificationListener, IPapyrusListener {

	/** The Constant ACTIVATION_ERROR_MESSAGE. */
	private static final String ACTIVATION_ERROR_MESSAGE = "No semantic element was found during activation of the mask managed label edit policy"; //$NON-NLS-1$

	// FIXME : See #449476
	// Will be fixed AFTER chaos (new generator) release.
	// An EditPolicy key should not imply its behavior (MASK)
	public static String INSTANCE_SPECIFICATION_ROLE_DISPLAY = IMaskManagedLabelEditPolicy.MASK_MANAGED_LABEL_EDIT_POLICY;

	/**
	 * Stores the semantic element related to the edit policy. If resolveSemanticElement is used, there are problems when the edit part is getting
	 * destroyed, i.e. the link to the semantic element is removed, but the listeners should still be removed
	 */
	protected Element hostSemanticElement;

	private Property interestingProperty;

	/**
	 * Returns the view controlled by the host edit part
	 *
	 * @return the view controlled by the host edit part
	 */
	protected View getView() {
		return (View) getHost().getModel();
	}

	/**
	 * Sets the semantic element which is linked to the edit policy
	 *
	 * @return the element linked to the edit policy
	 */
	protected Element initSemanticElement() {
		return (Element) getView().getElement();
	}

	/**
	 * Gets the diagram event broker from the editing domain.
	 *
	 * @return the diagram event broker
	 */
	protected DiagramEventBroker getDiagramEventBroker() {
		TransactionalEditingDomain theEditingDomain = ((IGraphicalEditPart) getHost()).getEditingDomain();
		if (theEditingDomain != null) {
			return DiagramEventBroker.getInstance(theEditingDomain);
		}
		return null;
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void activate() {
		// retrieve the view and the element managed by the edit part
		View view = getView();
		if (view == null) {
			return;
		}
		hostSemanticElement = initSemanticElement();
		if (hostSemanticElement != null) {
			// adds a listener on the view and the element controlled by the editpart
			getDiagramEventBroker().addNotificationListener(view, this);
			getDiagramEventBroker().addNotificationListener(hostSemanticElement, this);
			if (getInterestingProperty() != null) {
				getDiagramEventBroker().addNotificationListener(getInterestingProperty(), this);
			}
			refreshDisplay();
		} else {
			Activator.log.error(ACTIVATION_ERROR_MESSAGE, null);
		}
	}

	/**
	 *
	 * {@inheritDoc}
	 */
	@Override
	public void deactivate() {
		// retrieve the view and the element managed by the edit part
		View view = getView();
		if (view == null) {
			return;
		}
		// remove notification on element and view
		getDiagramEventBroker().removeNotificationListener(view, this);
		getDiagramEventBroker().removeNotificationListener(hostSemanticElement, this);
		if (getInterestingProperty() != null) {
			getDiagramEventBroker().removeNotificationListener(getInterestingProperty(), this);
		}
		// removes the reference to the semantic element
		hostSemanticElement = null;
	}

	/**
	 * Return the end of the Instance specification that it listen
	 *
	 * @return an instancespecification
	 */
	public InstanceSpecification getEnd() {
		return (InstanceSpecification) ((IGraphicalEditPart) getHost()).resolveSemanticElement();
	}

	/**
	 * It look for the property that has to be listen o norder to refresh the role in the instancespecification Link
	 *
	 * @return the property
	 */
	public Property getInterestingProperty() {
		if (interestingProperty != null) {
			return interestingProperty;
		}
		if (hostSemanticElement != null) {
			List<Property> array = new ArrayList<>();
			InstanceSpecification instanceSpecification = (InstanceSpecification) hostSemanticElement;
			if (instanceSpecification.getClassifiers().size() > 0) {
				if (instanceSpecification.getClassifiers().get(0) instanceof Association) {
					Association association = (Association) instanceSpecification.getClassifiers().get(0);
					InstanceSpecification sourceElt = getEnd();
					Iterator<Property> iterator = association.getMemberEnds().iterator();
					while (iterator.hasNext()) {
						Property property = iterator.next();
						if (sourceElt.getClassifiers().contains(property.getType())) {
							array.add(property);
						}
					}
				}
			}
			if (array.size() == 1) {
				interestingProperty = array.get(0);
			} else if (array.size() > 1) {
				interestingProperty = getprefvalue(array);
			}
		}
		return interestingProperty;
	}

	/**
	 * get the property that are interesting from a list for example for a source or label
	 *
	 * @param array
	 *            the list of properties
	 * @return the property
	 */
	protected abstract Property getprefvalue(List<Property> array);

	/**
	 * refresh the display
	 */
	protected void refreshDisplay() {
		if (hostSemanticElement != null) {
			if (getInterestingProperty() != null) {
				((ITextAwareEditPart) getHost()).setLabelText(UMLLabelInternationalization.getInstance().getLabel(getInterestingProperty()));
			}
		}
	}

	/**
	 *
	 * @see org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener#notifyChanged(org.eclipse.emf.common.notify.Notification)
	 *
	 * @param notification
	 */
	@Override
	public void notifyChanged(Notification notification) {
		refreshDisplay();
	}
}
