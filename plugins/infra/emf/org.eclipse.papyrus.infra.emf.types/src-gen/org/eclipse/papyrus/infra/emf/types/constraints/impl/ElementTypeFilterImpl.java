/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.emf.types.constraints.impl;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter;

import org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind;
import org.eclipse.papyrus.infra.emf.types.constraints.operations.ElementTypeFilterOperations;

import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Type Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ElementTypeFilterImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ElementTypeFilterImpl#getElementType <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ElementTypeFilterImpl#getRelationship <em>Relationship</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ElementTypeFilterImpl extends MinimalEObjectImpl.Container implements ElementTypeFilter {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The cached value of the '{@link #getElementType() <em>Element Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected ElementTypeConfiguration elementType;

	/**
	 * The default value of the '{@link #getRelationship() <em>Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRelationship()
	 * @generated
	 * @ordered
	 */
	protected static final ElementTypeRelationshipKind RELATIONSHIP_EDEFAULT = ElementTypeRelationshipKind.SUBTYPE;

	/**
	 * The cached value of the '{@link #getRelationship() <em>Relationship</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getRelationship()
	 * @generated
	 * @ordered
	 */
	protected ElementTypeRelationshipKind relationship = RELATIONSHIP_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ElementTypeFilterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConstraintAdvicePackage.Literals.ELEMENT_TYPE_FILTER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ElementTypeConfiguration getElementType() {
		if (elementType != null && elementType.eIsProxy()) {
			InternalEObject oldElementType = (InternalEObject) elementType;
			elementType = (ElementTypeConfiguration) eResolveProxy(oldElementType);
			if (elementType != oldElementType) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE, oldElementType, elementType));
				}
			}
		}
		return elementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ElementTypeConfiguration basicGetElementType() {
		return elementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setElementType(ElementTypeConfiguration newElementType) {
		ElementTypeConfiguration oldElementType = elementType;
		elementType = newElementType;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE, oldElementType, elementType));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ElementTypeRelationshipKind getRelationship() {
		return relationship;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setRelationship(ElementTypeRelationshipKind newRelationship) {
		ElementTypeRelationshipKind oldRelationship = relationship;
		relationship = newRelationship == null ? RELATIONSHIP_EDEFAULT : newRelationship;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__RELATIONSHIP, oldRelationship, relationship));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean matches(Object input) {
		return ElementTypeFilterOperations.matches(this, input);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__NAME:
			return getName();
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE:
			if (resolve) {
				return getElementType();
			}
			return basicGetElementType();
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__RELATIONSHIP:
			return getRelationship();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__NAME:
			setName((String) newValue);
			return;
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE:
			setElementType((ElementTypeConfiguration) newValue);
			return;
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__RELATIONSHIP:
			setRelationship((ElementTypeRelationshipKind) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__NAME:
			setName(NAME_EDEFAULT);
			return;
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE:
			setElementType((ElementTypeConfiguration) null);
			return;
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__RELATIONSHIP:
			setRelationship(RELATIONSHIP_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE:
			return elementType != null;
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER__RELATIONSHIP:
			return relationship != RELATIONSHIP_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case ConstraintAdvicePackage.ELEMENT_TYPE_FILTER___MATCHES__OBJECT:
			return matches(arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", relationship: ");
		result.append(relationship);
		result.append(')');
		return result.toString();
	}

} // ElementTypeFilterImpl
