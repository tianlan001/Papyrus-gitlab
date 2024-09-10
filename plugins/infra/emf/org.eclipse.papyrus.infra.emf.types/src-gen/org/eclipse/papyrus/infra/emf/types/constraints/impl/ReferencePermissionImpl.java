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
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission;

import org.eclipse.papyrus.infra.emf.types.constraints.operations.ReferencePermissionOperations;
import org.eclipse.papyrus.infra.filters.impl.FilteredElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference Permission</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferencePermissionImpl#isPermitted <em>Permitted</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ReferencePermissionImpl extends FilteredElementImpl implements ReferencePermission {
	/**
	 * The default value of the '{@link #isPermitted() <em>Permitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isPermitted()
	 * @generated
	 * @ordered
	 */
	protected static final boolean PERMITTED_EDEFAULT = true;

	/**
	 * The cached value of the '{@link #isPermitted() <em>Permitted</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isPermitted()
	 * @generated
	 * @ordered
	 */
	protected boolean permitted = PERMITTED_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ReferencePermissionImpl() {
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
		return ConstraintAdvicePackage.Literals.REFERENCE_PERMISSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isPermitted() {
		return permitted;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setPermitted(boolean newPermitted) {
		boolean oldPermitted = permitted;
		permitted = newPermitted;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.REFERENCE_PERMISSION__PERMITTED, oldPermitted, permitted));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean matches(EReference reference) {
		return ReferencePermissionOperations.matches(this, reference);
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
		case ConstraintAdvicePackage.REFERENCE_PERMISSION__PERMITTED:
			return isPermitted();
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
		case ConstraintAdvicePackage.REFERENCE_PERMISSION__PERMITTED:
			setPermitted((Boolean) newValue);
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
		case ConstraintAdvicePackage.REFERENCE_PERMISSION__PERMITTED:
			setPermitted(PERMITTED_EDEFAULT);
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
		case ConstraintAdvicePackage.REFERENCE_PERMISSION__PERMITTED:
			return permitted != PERMITTED_EDEFAULT;
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
		case ConstraintAdvicePackage.REFERENCE_PERMISSION___MATCHES__EREFERENCE:
			return matches((EReference) arguments.get(0));
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
		result.append(" (permitted: ");
		result.append(permitted);
		result.append(')');
		return result.toString();
	}

} // ReferencePermissionImpl
