/*****************************************************************************
 * Copyright (c) 2010 CEA LIST.
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
 *  Nizar GUEDIDI (CEA LIST) - Update getUMLElement()
 *
 */
package org.eclipse.papyrus.uml.diagram.clazz.custom.policies;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractMaskManagedEditPolicy;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.papyrus.uml.tools.utils.InstanceSpecificationUtil;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.InstanceSpecification;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * The Class InstanceSpecificationNameLabelEditPolicy.
 */
public class InstanceSpecificationNameLabelEditPolicy extends AbstractMaskManagedEditPolicy {

	protected final Map<String, String> masks = new HashMap<>();

	public InstanceSpecificationNameLabelEditPolicy() {
		super();
		masks.put(ICustomAppearance.DISP_NAME, "Name");
		masks.put(ICustomAppearance.DISP_TYPE, "Type");
	}

	@Override
	protected void addAdditionalListeners() {
		super.addAdditionalListeners();
		Iterator<Classifier> iterator = getUMLElement().getClassifiers().iterator();
		while (iterator.hasNext()) {
			Classifier type = iterator.next();
			getDiagramEventBroker().addNotificationListener(type, this);
		}
	}

	@Override
	public void deactivate() {
		if (getUMLElement() != null) {
			Iterator<Classifier> iterator = getUMLElement().getClassifiers().iterator();
			while (iterator.hasNext()) {
				Classifier type = iterator.next();
				getDiagramEventBroker().removeNotificationListener(type, this);
			}
		}
		super.deactivate();
	}

	@Override
	public InstanceSpecification getUMLElement() {
		EObject element = super.getUMLElement();
		if (element instanceof InstanceSpecification) {
			return (InstanceSpecification) element;
		}
		return null;
	}

	/**
	 * @see org.eclipse.papyrus.infra.gmfdiag.common.editpolicies.IMaskManagedLabelEditPolicy#getMasks()
	 *
	 * @return
	 */
	@Override
	public Map<String, String> getMasks() {
		return masks;
	}

	@Override
	protected Collection<String> getDefaultDisplayValue() {
		return ICustomAppearance.DEFAULT_UML_INSTANCESPECIFICATION;
	}

	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		// change the label of the figure managed by the host edit part (managed by the parent edit
		// part in general...)
		// it must be changed only if:
		// - the annotation corresponding to the display of the stereotype changes
		// - the stereotype application list has changed
		Object object = notification.getNotifier();

		if (notification.getEventType() == Notification.ADD) {
			if (UMLPackage.eINSTANCE.getInstanceSpecification_Classifier().equals(notification.getFeature())) {
				getDiagramEventBroker().addNotificationListener((EObject) notification.getNewValue(), this);
			}
		}
		if (notification.getEventType() == Notification.REMOVE) {
			if (UMLPackage.eINSTANCE.getInstanceSpecification_Classifier().equals(notification.getFeature())) {
				getDiagramEventBroker().removeNotificationListener((EObject) notification.getOldValue(), this);
			}
		}
		if (object == null) {
			return;
		}
		if (UMLPackage.eINSTANCE.getNamedElement_Name().equals(notification.getFeature())) {
			refreshDisplay();
			return;
		} else if (UMLPackage.eINSTANCE.getInstanceSpecification_Classifier().equals(notification.getFeature())) {
			refreshDisplay();
			return;
		}
		if (isMaskManagedAnnotation(object)) {
			refreshDisplay();
			return;
		}
		if (isRemovedMaskManagedLabelAnnotation(object, notification)) {
			refreshDisplay();
			return;
		}
	}

	/**
	 * @see org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractMaskManagedEditPolicy#refreshDisplay()
	 *
	 */
	@Override
	public void refreshDisplay() {
		// calls the helper for this edit Part
		if (getUMLElement() != null) {
			((WrappingLabel) ((GraphicalEditPart) getHost()).getFigure()).setText(InstanceSpecificationUtil.getCustomLabel(getUMLElement(), getCurrentDisplayValue()));
			((WrappingLabel) ((GraphicalEditPart) getHost()).getFigure()).setTextUnderline(true);
		}
	}
}
