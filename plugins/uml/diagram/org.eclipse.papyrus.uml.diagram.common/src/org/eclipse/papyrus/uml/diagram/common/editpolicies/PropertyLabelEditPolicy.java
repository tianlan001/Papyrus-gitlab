/*****************************************************************************
 * Copyright (c) 2009,2017 CEA LIST.
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
 *  Nizar GUEDIDI (CEA LIST) - Update getUMLElement()
 *  Pauline DEVILLE (CEA LIST) - Bug 519845 - [ClassDiagram] Refresh of property default value 
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.common.editpolicies;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.papyrus.uml.diagram.common.Activator;
import org.eclipse.papyrus.uml.diagram.common.helper.PropertyLabelHelper;
import org.eclipse.papyrus.uml.tools.utils.ICustomAppearance;
import org.eclipse.uml2.uml.Duration;
import org.eclipse.uml2.uml.Expression;
import org.eclipse.uml2.uml.Interval;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.TimeExpression;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Specific edit policy for label displaying stereotypes and their properties for edges representing
 * UML elements.
 */
public class PropertyLabelEditPolicy extends AbstractMaskManagedEditPolicy {

	/**
	 * Creates a new PropertyLabelEditPolicy
	 */
	public PropertyLabelEditPolicy() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addAdditionalListeners() {
		super.addAdditionalListeners();
		Property property = getUMLElement();
		// check host semantic element is not null
		if (property == null) {
			Activator.log.error("No semantic element present when adding listeners in PropertyLabelEditPolicy", null); //$NON-NLS-1$
			return;
		}
		// adds a listener to the element itself, and to linked elements, like Type
		if (property.getType() != null) {
			getDiagramEventBroker().addNotificationListener(property.getType(), this);
		}
		if (property.getDefaultValue() != null) {
			addDefaultValueAditionnalListener(property.getDefaultValue());
		}
		getDiagramEventBroker().addNotificationListener(property.getUpperValue(), this);
		getDiagramEventBroker().addNotificationListener(property.getLowerValue(), this);
	}

	/**
	 * This method add listener for the value specification in parameter but also for each ValueSpecification in the parameter
	 * 
	 * e.x: an Expression as two operands which are ValueSpecification then 3 listeners are added:
	 * - one for the expression
	 * - one for each operand
	 * 
	 * @param valueSpecification
	 *            the root value
	 */
	public void addDefaultValueAditionnalListener(ValueSpecification valueSpecification) {
		getDiagramEventBroker().addNotificationListener(valueSpecification, this);
		if (valueSpecification instanceof Duration) {
			addDefaultValueAditionnalListener(((Duration) valueSpecification).getExpr());
		} else if (valueSpecification instanceof Expression) {
			for (ValueSpecification operand : ((Expression) valueSpecification).getOperands()) {
				addDefaultValueAditionnalListener(operand);
			}
		} else if (valueSpecification instanceof Interval) {
			addDefaultValueAditionnalListener(((Interval) valueSpecification).getMin());
			addDefaultValueAditionnalListener(((Interval) valueSpecification).getMax());
		} else if (valueSpecification instanceof TimeExpression) {
			addDefaultValueAditionnalListener(((TimeExpression) valueSpecification).getExpr());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<String> getDefaultDisplayValue() {
		return ICustomAppearance.DEFAULT_UML_PROPERTY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<String, String> getMasks() {
		return PropertyLabelHelper.getInstance().getMasks();
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	public Property getUMLElement() {
		EObject element = super.getUMLElement();
		if (element instanceof Property) {
			return (Property) element;
		}
		return null;
	}

	/**
	 * {@inheritedDoc}
	 */
	@Override
	public void notifyChanged(Notification notification) {
		super.notifyChanged(notification);
		// change the label of the figure managed by the host edit part (managed by the parent edit
		// part in general...)
		// it must be changed only if:
		// - the annotation corresponding to the display of the stereotype changes
		// - the stereotype application list has changed
		Object object = notification.getNotifier();
		Property property = getUMLElement();
		if (object == null || property == null) {
			return;
		}
		// list of displayed feature
		if (UMLPackage.eINSTANCE.getLiteralBoolean_Value().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getLiteralInteger_Value().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getLiteralReal_Value().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getLiteralString_Value().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getLiteralUnlimitedNatural_Value().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getDuration_Expr().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getExpression_Operand().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getExpression_Symbol().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getInterval_Max().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getInterval_Min().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getOpaqueExpression_Body().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getTimeExpression_Expr().equals(notification.getFeature())
				|| UMLPackage.eINSTANCE.getInstanceValue_Instance().equals(notification.getFeature())) {
			refreshDisplay();
		}
		if (object.equals(property)) {
			notifyPropertyChanged(property, notification);
		} else if (object.equals(property.getType())) {
			notifyPropertyTypeChanged(property.getType(), notification);
		}
		if (isMaskManagedAnnotation(object)) {
			refreshDisplay();
		}
		if (isRemovedMaskManagedLabelAnnotation(object, notification)) {
			refreshDisplay();
		}
	}

	/**
	 * notifies that the the property has changed.
	 *
	 * @param property
	 *            the property that has changed
	 * @param notification
	 *            the notification send when the element has been changed
	 */
	protected void notifyPropertyChanged(Property property, Notification notification) {
		switch (notification.getFeatureID(Property.class)) {
		case UMLPackage.PROPERTY__NAME:
		case UMLPackage.PROPERTY__VISIBILITY:
		case UMLPackage.PROPERTY__IS_DERIVED:
		case UMLPackage.PROPERTY__SUBSETTED_PROPERTY:
		case UMLPackage.PROPERTY__REDEFINED_PROPERTY:
		case UMLPackage.PROPERTY__IS_ORDERED:
		case UMLPackage.PROPERTY__IS_UNIQUE:
		case UMLPackage.PROPERTY__IS_READ_ONLY:
			refreshDisplay();
			break;
		case UMLPackage.PROPERTY__DEFAULT_VALUE:
		case UMLPackage.PROPERTY__TYPE:
		case UMLPackage.PROPERTY__LOWER:
		case UMLPackage.PROPERTY__LOWER_VALUE:
		case UMLPackage.PROPERTY__UPPER:
		case UMLPackage.PROPERTY__UPPER_VALUE:
			switch (notification.getEventType()) {
			// if it is added => adds listener to the type element
			case Notification.ADD:
				getDiagramEventBroker().addNotificationListener((EObject) notification.getNewValue(), this);
				refreshDisplay();
				// if it is removed => removes listener from the type element
				break;
			case Notification.ADD_MANY: // should never happen
				if (notification.getNewValue() instanceof List<?>) {
					List<?> addedElements = (List<?>) notification.getNewValue();
					for (Object addedElement : addedElements) {
						if (addedElement instanceof EObject) {
							getDiagramEventBroker().addNotificationListener((EObject) addedElement, this);
						}
					}
				}
				refreshDisplay();
				break;
			case Notification.REMOVE:
				getDiagramEventBroker().removeNotificationListener((EObject) notification.getOldValue(), this);
				refreshDisplay();
				break;
			case Notification.REMOVE_MANY: // should never happen
				if (notification.getOldValue() instanceof List<?>) {
					List<?> removedElements = (List<?>) notification.getOldValue();
					for (Object removedElement : removedElements) {
						if (removedElement instanceof EObject) {
							getDiagramEventBroker().removeNotificationListener((EObject) removedElement, this);
						}
					}
				}
				refreshDisplay();
				break;
			// if it is set, remove the old one and adds the new one. this is the method use when
			// the type is set or removed...
			case Notification.SET:
				if (notification.getOldValue() != null) {
					if (notification.getFeatureID(Property.class) == UMLPackage.PROPERTY__DEFAULT_VALUE) {
						removeDefaultValueAditionnalListener((ValueSpecification) notification.getOldValue());
					} else {
						getDiagramEventBroker().removeNotificationListener((EObject) notification.getOldValue(), this);
					}
				}
				if (notification.getNewValue() != null) {
					if (notification.getFeatureID(Property.class) == UMLPackage.PROPERTY__DEFAULT_VALUE) {
						addDefaultValueAditionnalListener((ValueSpecification) notification.getNewValue());
					} else {
						getDiagramEventBroker().addNotificationListener((EObject) notification.getNewValue(), this);
					}
				}
				refreshDisplay();
			default:
				break;
			}
			break;
		default:
			// does nothing in other cases
			break;
		}
	}

	/**
	 * notifies that the type of the property has changed.
	 *
	 * @param type
	 *            the type of the property that has changed
	 * @param notification
	 *            the notification send when the element has been changed
	 */
	protected void notifyPropertyTypeChanged(Type type, Notification notification) {
		switch (notification.getFeatureID(Property.class)) {
		case UMLPackage.TYPE__NAME:
			refreshDisplay(); // type name has changed => refresh the property display
			break;
		default:
			// does nothing by default
			break;
		}
	}

	/**
	 * Refreshes the display of the edit part
	 */
	@Override
	public void refreshDisplay() {
		// calls the helper for this edit Part
		PropertyLabelHelper.getInstance().refreshEditPartDisplay((GraphicalEditPart) getHost());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void removeAdditionalListeners() {
		super.removeAdditionalListeners();
		Property property = getUMLElement();
		if (property == null) {
			// check semantic element is not null and this is really an instance of Property
			return;
		}
		if (property.getType() != null) {
			getDiagramEventBroker().removeNotificationListener(property.getType(), this);
		}
		getDiagramEventBroker().removeNotificationListener(property.getUpperValue(), this);
		getDiagramEventBroker().removeNotificationListener(property.getLowerValue(), this);
		if (property.getDefaultValue() != null) {
			removeDefaultValueAditionnalListener(property.getDefaultValue());
		}
	}

	/**
	 * This method remove listener for the value specification in parameter but also for each ValueSpecification in the parameter
	 * 
	 * e.x: an Expression as two operands which are ValueSpecification then 3 listeners are removed:
	 * - one for the expression
	 * - one for each operand
	 * 
	 * @param valueSpecification
	 *            the root value
	 */
	public void removeDefaultValueAditionnalListener(ValueSpecification valueSpecification) {
		getDiagramEventBroker().removeNotificationListener(valueSpecification, this);
		if (valueSpecification instanceof Duration) {
			removeDefaultValueAditionnalListener(((Duration) valueSpecification).getExpr());
		} else if (valueSpecification instanceof Expression) {
			for (ValueSpecification operand : ((Expression) valueSpecification).getOperands()) {
				removeDefaultValueAditionnalListener(operand);
			}
		} else if (valueSpecification instanceof Interval) {
			removeDefaultValueAditionnalListener(((Interval) valueSpecification).getMin());
			removeDefaultValueAditionnalListener(((Interval) valueSpecification).getMax());
		} else if (valueSpecification instanceof TimeExpression) {
			removeDefaultValueAditionnalListener(((TimeExpression) valueSpecification).getExpr());
		}
	}
}
