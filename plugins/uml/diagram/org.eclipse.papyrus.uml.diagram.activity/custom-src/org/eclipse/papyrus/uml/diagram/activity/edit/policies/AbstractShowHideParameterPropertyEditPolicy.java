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
 *   Camille Letavernier - camille.letavernier@cea.fr - Bug 490054
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gef.editpolicies.AbstractEditPolicy;
import org.eclipse.gmf.runtime.diagram.core.listener.DiagramEventBroker;
import org.eclipse.gmf.runtime.diagram.core.listener.NotificationListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.gmf.runtime.notation.NotationPackage;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.infra.emf.gmf.util.GMFUnsafe;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ActivityParameterNodeEditPart;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.uml2.uml.ActivityParameterNode;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * Base class to show and hide stream and exception label in {@link ActivityParameterNodeEditPart}
 *
 * @since 2.0
 */
public abstract class AbstractShowHideParameterPropertyEditPolicy extends AbstractEditPolicy implements NotificationListener {

	protected ActivityParameterNode semanticElement;

	protected Parameter currentParameter;

	@Override
	public void activate() {
		super.activate();
		// add listeners
		startListen();
	}

	@Override
	public void deactivate() {
		// remove listeners
		stopListen();
		super.deactivate();
	}

	/**
	 * Add listeners to host semantic
	 */
	protected void startListen() {
		semanticElement = findHostSemantic();
		addEObjectListener(semanticElement);
		currentParameter = semanticElement.getParameter();
		addEObjectListener(currentParameter);
		refresh();
	}

	public abstract void refresh();

	/**
	 * Remove listeners to host semantic
	 */
	protected void stopListen() {
		removeEObjectListener(semanticElement);
		removeEObjectListener(currentParameter);
	}

	/**
	 * Add this class to listeners of eObject
	 */
	protected void addEObjectListener(Object object) {
		if (object instanceof EObject) {
			getDiagramEventBroker().addNotificationListener((EObject) object, this);
		}
	}

	/**
	 * Remove this class from listeners of eObject
	 */
	protected void removeEObjectListener(Object object) {
		if (object instanceof EObject) {
			getDiagramEventBroker().removeNotificationListener((EObject) object, this);
		}
	}

	@Override
	public IGraphicalEditPart getHost() {
		return (IGraphicalEditPart) super.getHost();
	}

	/**
	 * Resolve host semantic element
	 *
	 * @return {@link ActivityParameterNode}
	 */
	protected ActivityParameterNode findHostSemantic() {
		return (ActivityParameterNode) getHost().resolveSemanticElement();
	}

	/**
	 * Gets diagram event broker from editing domain.
	 *
	 * @return diagram event broker
	 */
	protected DiagramEventBroker getDiagramEventBroker() {
		TransactionalEditingDomain editingDomain = getHost().getEditingDomain();
		if (editingDomain != null) {
			return DiagramEventBroker.getInstance(editingDomain);
		}
		return null;
	}

	@Override
	public void notifyChanged(Notification notification) {
		Object object = notification.getNotifier();
		if (object == semanticElement) {
			notifyActivityParameterNode(notification);
		} else if (object != null && object == currentParameter) {
			notifyParameter(notification);
		}
	}

	/**
	 * Process notification from {@link Parameter}
	 *
	 * @param notification
	 */
	protected abstract void notifyParameter(Notification notification);

	/**
	 * Process notification from {@link ActivityParameterNode}
	 *
	 * @param notification
	 */
	protected void notifyActivityParameterNode(Notification notification) {
		switch (notification.getFeatureID(ActivityParameterNode.class)) {
		case UMLPackage.ACTIVITY_PARAMETER_NODE__PARAMETER:
			switch (notification.getEventType()) {
			case Notification.SET:
				removeEObjectListener(notification.getOldValue());
				addEObjectListener(notification.getNewValue());
				if (notification.getFeature() == UMLPackage.Literals.ACTIVITY_PARAMETER_NODE__PARAMETER) {
					currentParameter = (Parameter) notification.getNewValue();
				}
				break;
			case Notification.UNSET:
				removeEObjectListener(notification.getOldValue());
				if (notification.getFeature() == UMLPackage.Literals.ACTIVITY_PARAMETER_NODE__PARAMETER) {
					currentParameter = null;
				}
				break;
			}
			refresh();
			break;
		}
	}

	/**
	 * Usafe execute GMF command
	 */
	protected void execute(final TransactionalEditingDomain domain, SetValueCommand setVisibleCommand) {
		try {
			GMFUnsafe.write(domain, setVisibleCommand);
		} catch (Exception e) {
			Activator.log.error(e);
		}
	}

	/**
	 * Set visibility of input view to false
	 */
	protected void hideLabelView(View view) {
		if (view.isVisible()) {
			SetValueCommand setVisibleCommand = new SetValueCommand(new SetRequest(view, NotationPackage.eINSTANCE.getView_Visible(), Boolean.FALSE));
			execute(getHost().getEditingDomain(), setVisibleCommand);
		}
	}

	/**
	 * Set visibility of input view to true
	 */
	protected void showLabelView(View view) {
		if (!view.isVisible()) {
			SetValueCommand setVisibleCommand = new SetValueCommand(new SetRequest(view, NotationPackage.eINSTANCE.getView_Visible(), Boolean.TRUE));
			execute(getHost().getEditingDomain(), setVisibleCommand);
		}
	}

	/**
	 * Find in host label view with VID in type
	 */
	protected View getLabelView(String VID) {
		View labelView = null;
		for (Object child : getHost().getNotationView().getChildren()) {
			View view = (View) child;
			if (view.getType().equals(VID)) {
				labelView = view;
				break;
			}
		}
		assert labelView == null : "Child view should be always present in host view";

		return labelView;
	}
}
