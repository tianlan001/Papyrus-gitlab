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

import org.eclipse.papyrus.infra.emf.types.constraints.AnyReference;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind;

import org.eclipse.papyrus.infra.emf.types.constraints.operations.AnyReferenceOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Any Reference</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.AnyReferenceImpl#getReferenceKind <em>Reference Kind</em>}</li>
 * </ul>
 *
 * @generated
 */
public class AnyReferenceImpl extends ReferencePermissionImpl implements AnyReference {
	/**
	 * The default value of the '{@link #getReferenceKind() <em>Reference Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getReferenceKind()
	 * @generated
	 * @ordered
	 */
	protected static final ReferenceKind REFERENCE_KIND_EDEFAULT = ReferenceKind.ANY;

	/**
	 * The cached value of the '{@link #getReferenceKind() <em>Reference Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getReferenceKind()
	 * @generated
	 * @ordered
	 */
	protected ReferenceKind referenceKind = REFERENCE_KIND_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AnyReferenceImpl() {
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
		return ConstraintAdvicePackage.Literals.ANY_REFERENCE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ReferenceKind getReferenceKind() {
		return referenceKind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setReferenceKind(ReferenceKind newReferenceKind) {
		ReferenceKind oldReferenceKind = referenceKind;
		referenceKind = newReferenceKind == null ? REFERENCE_KIND_EDEFAULT : newReferenceKind;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.ANY_REFERENCE__REFERENCE_KIND, oldReferenceKind, referenceKind));
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
		return AnyReferenceOperations.matches(this, reference);
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
		case ConstraintAdvicePackage.ANY_REFERENCE__REFERENCE_KIND:
			return getReferenceKind();
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
		case ConstraintAdvicePackage.ANY_REFERENCE__REFERENCE_KIND:
			setReferenceKind((ReferenceKind) newValue);
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
		case ConstraintAdvicePackage.ANY_REFERENCE__REFERENCE_KIND:
			setReferenceKind(REFERENCE_KIND_EDEFAULT);
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
		case ConstraintAdvicePackage.ANY_REFERENCE__REFERENCE_KIND:
			return referenceKind != REFERENCE_KIND_EDEFAULT;
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
		case ConstraintAdvicePackage.ANY_REFERENCE___MATCHES__EREFERENCE:
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
		result.append(" (referenceKind: ");
		result.append(referenceKind);
		result.append(')');
		return result.toString();
	}

} // AnyReferenceImpl
