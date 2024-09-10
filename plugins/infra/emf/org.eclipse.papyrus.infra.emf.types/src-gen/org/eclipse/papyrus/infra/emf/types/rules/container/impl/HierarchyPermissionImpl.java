/**
 * Copyright (c) 2014, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 568853
 */
package org.eclipse.papyrus.infra.emf.types.rules.container.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission;
import org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRulePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Hierarchy Permission</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.types.rules.container.impl.HierarchyPermissionImpl#getContainerType <em>Container Type</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.emf.types.rules.container.impl.HierarchyPermissionImpl#isPermitted <em>Permitted</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.emf.types.rules.container.impl.HierarchyPermissionImpl#isStrict <em>Strict</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HierarchyPermissionImpl extends MinimalEObjectImpl.Container implements HierarchyPermission {
	/**
	 * The default value of the '{@link #getContainerType() <em>Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerType()
	 * @generated
	 * @ordered
	 */
	protected static final String CONTAINER_TYPE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getContainerType() <em>Container Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getContainerType()
	 * @generated
	 * @ordered
	 */
	protected String containerType = CONTAINER_TYPE_EDEFAULT;

	/**
	 * The default value of the '{@link #isPermitted() <em>Permitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPermitted()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PERMITTED_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isPermitted() <em>Permitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isPermitted()
	 * @generated
	 * @ordered
	 */
	protected boolean permitted = PERMITTED_EDEFAULT;

	/**
	 * The default value of the '{@link #isStrict() <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrict()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRICT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStrict() <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrict()
	 * @generated
	 * @ordered
	 */
	protected boolean strict = STRICT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HierarchyPermissionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InvariantContainerRulePackage.Literals.HIERARCHY_PERMISSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getContainerType() {
		return containerType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContainerType(String newContainerType) {
		String oldContainerType = containerType;
		containerType = newContainerType;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InvariantContainerRulePackage.HIERARCHY_PERMISSION__CONTAINER_TYPE, oldContainerType, containerType));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isPermitted() {
		return permitted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPermitted(boolean newPermitted) {
		boolean oldPermitted = permitted;
		permitted = newPermitted;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InvariantContainerRulePackage.HIERARCHY_PERMISSION__PERMITTED, oldPermitted, permitted));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStrict() {
		return strict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStrict(boolean newStrict) {
		boolean oldStrict = strict;
		strict = newStrict;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InvariantContainerRulePackage.HIERARCHY_PERMISSION__STRICT, oldStrict, strict));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__CONTAINER_TYPE:
				return getContainerType();
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__PERMITTED:
				return isPermitted();
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__STRICT:
				return isStrict();
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
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__CONTAINER_TYPE:
				setContainerType((String)newValue);
				return;
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__PERMITTED:
				setPermitted((Boolean)newValue);
				return;
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__STRICT:
				setStrict((Boolean)newValue);
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
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__CONTAINER_TYPE:
				setContainerType(CONTAINER_TYPE_EDEFAULT);
				return;
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__PERMITTED:
				setPermitted(PERMITTED_EDEFAULT);
				return;
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__STRICT:
				setStrict(STRICT_EDEFAULT);
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
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__CONTAINER_TYPE:
				return CONTAINER_TYPE_EDEFAULT == null ? containerType != null : !CONTAINER_TYPE_EDEFAULT.equals(containerType);
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__PERMITTED:
				return permitted != PERMITTED_EDEFAULT;
			case InvariantContainerRulePackage.HIERARCHY_PERMISSION__STRICT:
				return strict != STRICT_EDEFAULT;
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
		result.append(" (containerType: ");
		result.append(containerType);
		result.append(", permitted: ");
		result.append(permitted);
		result.append(", strict: ");
		result.append(strict);
		result.append(')');
		return result.toString();
	}

} //HierarchyPermissionImpl
