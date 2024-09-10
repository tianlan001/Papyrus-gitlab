/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
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
 *   Christian W. Damus - add prototype reference to Context (CDO)
 *   Vincent Lorenzo - Bug 520271
 *   Christian W. Damus - bug 573986
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.contexts.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectWithInverseResolvingEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;
import org.eclipse.papyrus.infra.properties.contexts.DataContextElement;
import org.eclipse.papyrus.infra.properties.contexts.Property;
import org.eclipse.papyrus.infra.properties.environment.Type;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl#getLabel <em>Label</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl#getType <em>Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl#getContextElement <em>Context Element</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl#getMultiplicity <em>Multiplicity</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl#getRedefinedProperties <em>Redefined Properties</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.properties.contexts.impl.PropertyImpl#getRedefinedByProperties <em>Redefined By Properties</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PropertyImpl extends AnnotatableImpl implements Property {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected static final String LABEL_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabel()
	 * @generated
	 * @ordered
	 */
	protected String label = LABEL_EDEFAULT;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final Type TYPE_EDEFAULT = Type.STRING;

	/**
	 * The cached value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected Type type = TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #getMultiplicity() <em>Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected static final int MULTIPLICITY_EDEFAULT = 1;

	/**
	 * The cached value of the '{@link #getMultiplicity() <em>Multiplicity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMultiplicity()
	 * @generated
	 * @ordered
	 */
	protected int multiplicity = MULTIPLICITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRedefinedProperties() <em>Redefined Properties</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRedefinedProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> redefinedProperties;

	/**
	 * The cached value of the '{@link #getRedefinedByProperties() <em>Redefined By Properties</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRedefinedByProperties()
	 * @generated
	 * @ordered
	 */
	protected EList<Property> redefinedByProperties;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ContextsPackage.Literals.PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.PROPERTY__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLabel() {
		return label;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLabel(String newLabel) {
		String oldLabel = label;
		label = newLabel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.PROPERTY__LABEL, oldLabel, label));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Type getType() {
		return type;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(Type newType) {
		Type oldType = type;
		type = newType == null ? TYPE_EDEFAULT : newType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.PROPERTY__TYPE, oldType, type));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public DataContextElement getContextElement() {
		if (eContainerFeatureID() != ContextsPackage.PROPERTY__CONTEXT_ELEMENT) return null;
		return (DataContextElement)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContextElement(DataContextElement newContextElement, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newContextElement, ContextsPackage.PROPERTY__CONTEXT_ELEMENT, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContextElement(DataContextElement newContextElement) {
		if (newContextElement != eInternalContainer() || (eContainerFeatureID() != ContextsPackage.PROPERTY__CONTEXT_ELEMENT && newContextElement != null)) {
			if (EcoreUtil.isAncestor(this, newContextElement))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContextElement != null)
				msgs = ((InternalEObject)newContextElement).eInverseAdd(this, ContextsPackage.DATA_CONTEXT_ELEMENT__PROPERTIES, DataContextElement.class, msgs);
			msgs = basicSetContextElement(newContextElement, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.PROPERTY__CONTEXT_ELEMENT, newContextElement, newContextElement));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int getMultiplicity() {
		return multiplicity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMultiplicity(int newMultiplicity) {
		int oldMultiplicity = multiplicity;
		multiplicity = newMultiplicity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.PROPERTY__MULTIPLICITY, oldMultiplicity, multiplicity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ContextsPackage.PROPERTY__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Property> getRedefinedProperties() {
		if (redefinedProperties == null) {
			redefinedProperties = new EObjectWithInverseResolvingEList.ManyInverse<Property>(Property.class, this, ContextsPackage.PROPERTY__REDEFINED_PROPERTIES, ContextsPackage.PROPERTY__REDEFINED_BY_PROPERTIES);
		}
		return redefinedProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Property> getRedefinedByProperties() {
		if (redefinedByProperties == null) {
			redefinedByProperties = new EObjectWithInverseResolvingEList.ManyInverse<Property>(Property.class, this, ContextsPackage.PROPERTY__REDEFINED_BY_PROPERTIES, ContextsPackage.PROPERTY__REDEFINED_PROPERTIES);
		}
		return redefinedByProperties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ContextsPackage.PROPERTY__CONTEXT_ELEMENT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetContextElement((DataContextElement)otherEnd, msgs);
			case ContextsPackage.PROPERTY__REDEFINED_PROPERTIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRedefinedProperties()).basicAdd(otherEnd, msgs);
			case ContextsPackage.PROPERTY__REDEFINED_BY_PROPERTIES:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRedefinedByProperties()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ContextsPackage.PROPERTY__CONTEXT_ELEMENT:
				return basicSetContextElement(null, msgs);
			case ContextsPackage.PROPERTY__REDEFINED_PROPERTIES:
				return ((InternalEList<?>)getRedefinedProperties()).basicRemove(otherEnd, msgs);
			case ContextsPackage.PROPERTY__REDEFINED_BY_PROPERTIES:
				return ((InternalEList<?>)getRedefinedByProperties()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case ContextsPackage.PROPERTY__CONTEXT_ELEMENT:
				return eInternalContainer().eInverseRemove(this, ContextsPackage.DATA_CONTEXT_ELEMENT__PROPERTIES, DataContextElement.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ContextsPackage.PROPERTY__NAME:
				return getName();
			case ContextsPackage.PROPERTY__LABEL:
				return getLabel();
			case ContextsPackage.PROPERTY__TYPE:
				return getType();
			case ContextsPackage.PROPERTY__CONTEXT_ELEMENT:
				return getContextElement();
			case ContextsPackage.PROPERTY__MULTIPLICITY:
				return getMultiplicity();
			case ContextsPackage.PROPERTY__DESCRIPTION:
				return getDescription();
			case ContextsPackage.PROPERTY__REDEFINED_PROPERTIES:
				return getRedefinedProperties();
			case ContextsPackage.PROPERTY__REDEFINED_BY_PROPERTIES:
				return getRedefinedByProperties();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ContextsPackage.PROPERTY__NAME:
				setName((String)newValue);
				return;
			case ContextsPackage.PROPERTY__LABEL:
				setLabel((String)newValue);
				return;
			case ContextsPackage.PROPERTY__TYPE:
				setType((Type)newValue);
				return;
			case ContextsPackage.PROPERTY__CONTEXT_ELEMENT:
				setContextElement((DataContextElement)newValue);
				return;
			case ContextsPackage.PROPERTY__MULTIPLICITY:
				setMultiplicity((Integer)newValue);
				return;
			case ContextsPackage.PROPERTY__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case ContextsPackage.PROPERTY__REDEFINED_PROPERTIES:
				getRedefinedProperties().clear();
				getRedefinedProperties().addAll((Collection<? extends Property>)newValue);
				return;
			case ContextsPackage.PROPERTY__REDEFINED_BY_PROPERTIES:
				getRedefinedByProperties().clear();
				getRedefinedByProperties().addAll((Collection<? extends Property>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ContextsPackage.PROPERTY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ContextsPackage.PROPERTY__LABEL:
				setLabel(LABEL_EDEFAULT);
				return;
			case ContextsPackage.PROPERTY__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case ContextsPackage.PROPERTY__CONTEXT_ELEMENT:
				setContextElement((DataContextElement)null);
				return;
			case ContextsPackage.PROPERTY__MULTIPLICITY:
				setMultiplicity(MULTIPLICITY_EDEFAULT);
				return;
			case ContextsPackage.PROPERTY__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ContextsPackage.PROPERTY__REDEFINED_PROPERTIES:
				getRedefinedProperties().clear();
				return;
			case ContextsPackage.PROPERTY__REDEFINED_BY_PROPERTIES:
				getRedefinedByProperties().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ContextsPackage.PROPERTY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ContextsPackage.PROPERTY__LABEL:
				return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
			case ContextsPackage.PROPERTY__TYPE:
				return type != TYPE_EDEFAULT;
			case ContextsPackage.PROPERTY__CONTEXT_ELEMENT:
				return getContextElement() != null;
			case ContextsPackage.PROPERTY__MULTIPLICITY:
				return multiplicity != MULTIPLICITY_EDEFAULT;
			case ContextsPackage.PROPERTY__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case ContextsPackage.PROPERTY__REDEFINED_PROPERTIES:
				return redefinedProperties != null && !redefinedProperties.isEmpty();
			case ContextsPackage.PROPERTY__REDEFINED_BY_PROPERTIES:
				return redefinedByProperties != null && !redefinedByProperties.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", label: "); //$NON-NLS-1$
		result.append(label);
		result.append(", type: "); //$NON-NLS-1$
		result.append(type);
		result.append(", multiplicity: "); //$NON-NLS-1$
		result.append(multiplicity);
		result.append(", description: "); //$NON-NLS-1$
		result.append(description);
		result.append(')');
		return result.toString();
	}

} // PropertyImpl
