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
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.diagram.activity.edit.policies;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.notation.Shape;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.papyrus.uml.diagram.activity.edit.parts.ParameterNodeNameEditPart;
import org.eclipse.papyrus.uml.diagram.common.editpolicies.AbstractMaskManagedEditPolicy;
import org.eclipse.papyrus.uml.diagram.common.helper.ActivityParameterNodeLabelHelper;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.uml2.uml.ActivityParameterNode;
import org.eclipse.uml2.uml.Parameter;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;


public class ActivityParameterMaskEditPolicy extends AbstractMaskManagedEditPolicy {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAdditionalListeners() {
		super.addAdditionalListeners();
		ActivityParameterNode node = getUMLElement();
		if (node == null) {
			return;
		}
		// add listner to parameter
		addListener(node.getParameter());
		// add listner to type
		addListener(node.getType());
		// add listner to states
		for (State state : node.getInStates()) {
			addListener(state);
		}
	}

	/**
	 * Add this class to target's listners.
	 *
	 * @param target
	 *            may be null
	 */
	protected void addListener(Object target) {
		if (target instanceof EObject) {
			getDiagramEventBroker().addNotificationListener((EObject) target, this);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getDefaultDisplayValue() {
		return ICustomAppearance.DEFAULT_UML_ACTIVITYPARAMETERNODE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getMasks() {
		return ActivityParameterNodeLabelHelper.getInstance().getMasks();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActivityParameterNode getUMLElement() {
		if (hostSemanticElement instanceof ActivityParameterNode) {
			return (ActivityParameterNode) hostSemanticElement;
		}
		return null;
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		Object object = notification.getNotifier();
		ActivityParameterNode activityParameterNode = getUMLElement();
		if (object == null || activityParameterNode == null) {
			return;
		}
		if (object.equals(activityParameterNode)) {
			notifyActivityParameterNodeChanged(activityParameterNode, notification);
		} else if (isParameter(object)) {
			notifyParameterChanged(notification);
		} else if (isType(object)) {
			notifyTypeChanged(notification);
		} else if (isState(object)) {
			notifyStateChanged(notification);
		}
	}

	/**
	 * Checks if the given object is a Type of the ActivityParameterNode
	 */
	protected boolean isType(Object object) {
		if (false == object instanceof Type) {
			return false;
		}
		return object.equals(getUMLElement().getType());
	}

	/**
	 * Checks if the given object is a Parameter of the ActivityParameterNode
	 */
	protected boolean isParameter(Object object) {
		if (false == object instanceof Parameter) {
			return false;
		}
		return object.equals(getUMLElement().getParameter());
	}

	/**
	 * Checks if the given object is a State of the ActivityParameterNode
	 */
	protected boolean isState(Object object) {
		if (false == object instanceof State) {
			return false;
		}
		return getUMLElement().getInStates().contains(object);
	}

	/**
	 * Checks if the given object is a Type of the ActivityParameterNode
	 *
	 * @param parameter
	 *            the {@link Parameter} that has changed
	 * @param notification
	 *            the notification send when the element has been changed
	 */
	protected void notifyParameterChanged(Notification notification) {
		if (UMLPackage.PARAMETER__NAME == notification.getFeatureID(Parameter.class)) {
			refreshDisplay();
		}
	}

	/**
	 * notifies that a type of the activity parameter node has changed.
	 *
	 * @param parameter
	 *            the {@link Parameter} that has changed
	 * @param notification
	 *            the notification send when the element has been changed
	 */
	protected void notifyTypeChanged(Notification notification) {
		if (UMLPackage.TYPE__NAME == notification.getFeatureID(notification.getNotifier().getClass())) {
			refreshDisplay();
		}
	}

	protected void notifyStateChanged(Notification notification) {
		if (UMLPackage.STATE__NAME == notification.getFeatureID(notification.getNotifier().getClass())) {
			refreshDisplay();
		}
	}

	/**
	 * notifies that the the property has changed.
	 *
	 * @param node
	 *            the ActivityParameterNode that has changed
	 * @param notification
	 *            the notification send when the element has been changed
	 */
	protected void notifyActivityParameterNodeChanged(ActivityParameterNode node, Notification notification) {
		switch (notification.getFeatureID(ActivityParameterNode.class)) {
		case UMLPackage.ACTIVITY_PARAMETER_NODE__NAME:
			refreshDisplay();
			break;
		case UMLPackage.ACTIVITY_PARAMETER_NODE__PARAMETER:
		case UMLPackage.ACTIVITY_PARAMETER_NODE__TYPE:
		case UMLPackage.ACTIVITY_PARAMETER_NODE__IN_STATE:
			switch (notification.getEventType()) {
			case Notification.ADD:
				addListener(notification.getNewValue());
				refreshDisplay();
				break;
			case Notification.ADD_MANY:
				if (notification.getNewValue() instanceof List<?>) {
					List<?> addedElements = (List<?>) notification.getNewValue();
					for (Object addedElement : addedElements) {
						addListener(addedElement);
					}
				}
				refreshDisplay();
				break;
			case Notification.REMOVE:
				removeListenerFromElement(notification.getOldValue());
				refreshDisplay();
				break;
			case Notification.REMOVE_MANY:
				if (notification.getOldValue() instanceof List<?>) {
					List<?> removedElements = (List<?>) notification.getOldValue();
					for (Object removedElement : removedElements) {
						removeListenerFromElement(removedElement);
					}
				}
				refreshDisplay();
				break;
			case Notification.SET:
				addListener(notification.getNewValue());
				removeListenerFromElement(notification.getOldValue());
				refreshDisplay();
				break;
			}
		default:
			break;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void refreshDisplay() {
		EditPart labelEditPart = getLabelEditPart();
		if (labelEditPart instanceof GraphicalEditPart) {
			ActivityParameterNodeLabelHelper.getInstance().refreshEditPartDisplay((GraphicalEditPart) labelEditPart);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void removeAdditionalListeners() {
		super.removeAdditionalListeners();
		ActivityParameterNode activityParameterNode = getUMLElement();
		if (activityParameterNode == null) {
			return;
		}
		// remove listner from parameter
		removeListenerFromElement(activityParameterNode.getParameter());
		// remove listner from type
		removeListenerFromElement(activityParameterNode.getType());
		// remove listner from states
		for (State state : activityParameterNode.getInStates()) {
			removeListenerFromElement(state);
		}
	}

	protected void removeListenerFromElement(Object target) {
		if (target instanceof EObject) {
			getDiagramEventBroker().removeNotificationListener((EObject) target, this);
		}
	}

	/**
	 *
	 * @return The label edit part, managed by this edit policy. The edit policy may be applied on the top-level node
	 * @since 2.0
	 */
	protected EditPart getLabelEditPart() {
		GraphicalEditPart ep = (GraphicalEditPart) super.getHost();
		if (ep instanceof ParameterNodeNameEditPart) {
			return ep;
		}
		return ep.getChildBySemanticHint(ParameterNodeNameEditPart.VISUAL_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected View getView() {
		View view = super.getView();
		if (view == null) {
			return null;
		}
		if (view instanceof Shape) {
			return view;
		}
		return (View) view.eContainer();
	}
}