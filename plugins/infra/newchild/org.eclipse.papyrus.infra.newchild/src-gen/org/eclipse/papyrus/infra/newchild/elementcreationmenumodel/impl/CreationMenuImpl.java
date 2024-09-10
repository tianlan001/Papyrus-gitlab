/**
 * Copyright (c) 2017, 2021 CEA LIST, Christian W. Damus, and others.
 *  
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *  
 * Contributors:
 * 	CEA LIST - Initial API and implementation
 * 	Christian W. Damus - bug 572712
 * 
 */
package org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.CreationMenu;
import org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.ElementCreationMenuModelPackage;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Creation Menu</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreationMenuImpl#getElementType <em>Element Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreationMenuImpl#getRole <em>Role</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.newchild.elementcreationmenumodel.impl.CreationMenuImpl#isDisplayAllRoles <em>Display All Roles</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CreationMenuImpl extends MenuImpl implements CreationMenu {
	/**
	 * The cached value of the '{@link #getElementType() <em>Element Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected ElementTypeConfiguration elementType;

	/**
	 * The default value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected static final String ROLE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRole() <em>Role</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRole()
	 * @generated
	 * @ordered
	 */
	protected String role = ROLE_EDEFAULT;

	/**
	 * The default value of the '{@link #isDisplayAllRoles() <em>Display All Roles</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisplayAllRoles()
	 * @generated
	 * @ordered
	 */
	protected static final boolean DISPLAY_ALL_ROLES_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isDisplayAllRoles() <em>Display All Roles</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isDisplayAllRoles()
	 * @generated
	 * @ordered
	 */
	protected boolean displayAllRoles = DISPLAY_ALL_ROLES_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CreationMenuImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementCreationMenuModelPackage.Literals.CREATION_MENU;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ElementTypeConfiguration getElementType() {
		if (elementType != null && elementType.eIsProxy()) {
			InternalEObject oldElementType = (InternalEObject)elementType;
			elementType = (ElementTypeConfiguration)eResolveProxy(oldElementType);
			if (elementType != oldElementType) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ElementCreationMenuModelPackage.CREATION_MENU__ELEMENT_TYPE, oldElementType, elementType));
			}
		}
		return elementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementTypeConfiguration basicGetElementType() {
		return elementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setElementType(ElementTypeConfiguration newElementType) {
		ElementTypeConfiguration oldElementType = elementType;
		elementType = newElementType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementCreationMenuModelPackage.CREATION_MENU__ELEMENT_TYPE, oldElementType, elementType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRole() {
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRole(String newRole) {
		String oldRole = role;
		role = newRole;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementCreationMenuModelPackage.CREATION_MENU__ROLE, oldRole, role));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isDisplayAllRoles() {
		return displayAllRoles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDisplayAllRoles(boolean newDisplayAllRoles) {
		boolean oldDisplayAllRoles = displayAllRoles;
		displayAllRoles = newDisplayAllRoles;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementCreationMenuModelPackage.CREATION_MENU__DISPLAY_ALL_ROLES, oldDisplayAllRoles, displayAllRoles));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ElementCreationMenuModelPackage.CREATION_MENU__ELEMENT_TYPE:
				if (resolve) return getElementType();
				return basicGetElementType();
			case ElementCreationMenuModelPackage.CREATION_MENU__ROLE:
				return getRole();
			case ElementCreationMenuModelPackage.CREATION_MENU__DISPLAY_ALL_ROLES:
				return isDisplayAllRoles();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ElementCreationMenuModelPackage.CREATION_MENU__ELEMENT_TYPE:
				setElementType((ElementTypeConfiguration)newValue);
				return;
			case ElementCreationMenuModelPackage.CREATION_MENU__ROLE:
				setRole((String)newValue);
				return;
			case ElementCreationMenuModelPackage.CREATION_MENU__DISPLAY_ALL_ROLES:
				setDisplayAllRoles((Boolean)newValue);
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
			case ElementCreationMenuModelPackage.CREATION_MENU__ELEMENT_TYPE:
				setElementType((ElementTypeConfiguration)null);
				return;
			case ElementCreationMenuModelPackage.CREATION_MENU__ROLE:
				setRole(ROLE_EDEFAULT);
				return;
			case ElementCreationMenuModelPackage.CREATION_MENU__DISPLAY_ALL_ROLES:
				setDisplayAllRoles(DISPLAY_ALL_ROLES_EDEFAULT);
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
			case ElementCreationMenuModelPackage.CREATION_MENU__ELEMENT_TYPE:
				return elementType != null;
			case ElementCreationMenuModelPackage.CREATION_MENU__ROLE:
				return ROLE_EDEFAULT == null ? role != null : !ROLE_EDEFAULT.equals(role);
			case ElementCreationMenuModelPackage.CREATION_MENU__DISPLAY_ALL_ROLES:
				return displayAllRoles != DISPLAY_ALL_ROLES_EDEFAULT;
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
		result.append(" (role: ");
		result.append(role);
		result.append(", displayAllRoles: ");
		result.append(displayAllRoles);
		result.append(')');
		return result.toString();
	}

} //CreationMenuImpl
