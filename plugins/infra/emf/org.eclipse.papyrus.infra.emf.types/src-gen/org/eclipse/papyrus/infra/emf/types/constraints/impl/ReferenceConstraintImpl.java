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
import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferenceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission;

import org.eclipse.papyrus.infra.emf.types.constraints.operations.ReferenceConstraintOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Reference Constraint</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ReferenceConstraintImpl#getPermissions <em>Permission</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ReferenceConstraintImpl extends AdviceConstraintImpl implements ReferenceConstraint {
	/**
	 * The cached value of the '{@link #getPermissions() <em>Permission</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getPermissions()
	 * @generated
	 * @ordered
	 */
	protected EList<ReferencePermission> permissions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ReferenceConstraintImpl() {
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
		return ConstraintAdvicePackage.Literals.REFERENCE_CONSTRAINT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<ReferencePermission> getPermissions() {
		if (permissions == null) {
			permissions = new EObjectContainmentEList<>(ReferencePermission.class, this, ConstraintAdvicePackage.REFERENCE_CONSTRAINT__PERMISSION);
		}
		return permissions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean approveRequest(IEditCommandRequest request) {
		return ReferenceConstraintOperations.approveRequest(this, request);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ConstraintAdvicePackage.REFERENCE_CONSTRAINT__PERMISSION:
			return ((InternalEList<?>) getPermissions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
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
		case ConstraintAdvicePackage.REFERENCE_CONSTRAINT__PERMISSION:
			return getPermissions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ConstraintAdvicePackage.REFERENCE_CONSTRAINT__PERMISSION:
			getPermissions().clear();
			getPermissions().addAll((Collection<? extends ReferencePermission>) newValue);
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
		case ConstraintAdvicePackage.REFERENCE_CONSTRAINT__PERMISSION:
			getPermissions().clear();
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
		case ConstraintAdvicePackage.REFERENCE_CONSTRAINT__PERMISSION:
			return permissions != null && !permissions.isEmpty();
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
		case ConstraintAdvicePackage.REFERENCE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST:
			return approveRequest((IEditCommandRequest) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} // ReferenceConstraintImpl
