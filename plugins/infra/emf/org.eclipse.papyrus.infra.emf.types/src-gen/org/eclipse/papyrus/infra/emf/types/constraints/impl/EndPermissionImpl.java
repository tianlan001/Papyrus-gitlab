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

import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.constraints.EndKind;
import org.eclipse.papyrus.infra.emf.types.constraints.EndPermission;

import org.eclipse.papyrus.infra.emf.types.constraints.operations.EndPermissionOperations;

import org.eclipse.papyrus.infra.filters.Filter;
import org.eclipse.papyrus.infra.filters.impl.FilteredElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>End Permission</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.EndPermissionImpl#getEndKind <em>End Kind</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.EndPermissionImpl#isPermitted <em>Permitted</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.EndPermissionImpl#getEndFilter <em>End Filter</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EndPermissionImpl extends FilteredElementImpl implements EndPermission {
	/**
	 * The default value of the '{@link #getEndKind() <em>End Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getEndKind()
	 * @generated
	 * @ordered
	 */
	protected static final EndKind END_KIND_EDEFAULT = EndKind.ALL;

	/**
	 * The cached value of the '{@link #getEndKind() <em>End Kind</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getEndKind()
	 * @generated
	 * @ordered
	 */
	protected EndKind endKind = END_KIND_EDEFAULT;

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
	 * The cached value of the '{@link #getEndFilter() <em>End Filter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getEndFilter()
	 * @generated
	 * @ordered
	 */
	protected Filter endFilter;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected EndPermissionImpl() {
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
		return ConstraintAdvicePackage.Literals.END_PERMISSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EndKind getEndKind() {
		return endKind;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setEndKind(EndKind newEndKind) {
		EndKind oldEndKind = endKind;
		endKind = newEndKind == null ? END_KIND_EDEFAULT : newEndKind;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.END_PERMISSION__END_KIND, oldEndKind, endKind));
		}
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
			eNotify(new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.END_PERMISSION__PERMITTED, oldPermitted, permitted));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Filter getEndFilter() {
		return endFilter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetEndFilter(Filter newEndFilter, NotificationChain msgs) {
		Filter oldEndFilter = endFilter;
		endFilter = newEndFilter;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.END_PERMISSION__END_FILTER, oldEndFilter, newEndFilter);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setEndFilter(Filter newEndFilter) {
		if (newEndFilter != endFilter) {
			NotificationChain msgs = null;
			if (endFilter != null) {
				msgs = ((InternalEObject) endFilter).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ConstraintAdvicePackage.END_PERMISSION__END_FILTER, null, msgs);
			}
			if (newEndFilter != null) {
				msgs = ((InternalEObject) newEndFilter).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ConstraintAdvicePackage.END_PERMISSION__END_FILTER, null, msgs);
			}
			msgs = basicSetEndFilter(newEndFilter, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, ConstraintAdvicePackage.END_PERMISSION__END_FILTER, newEndFilter, newEndFilter));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean matches(EObject end) {
		return EndPermissionOperations.matches(this, end);
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
		case ConstraintAdvicePackage.END_PERMISSION__END_FILTER:
			return basicSetEndFilter(null, msgs);
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
		case ConstraintAdvicePackage.END_PERMISSION__END_KIND:
			return getEndKind();
		case ConstraintAdvicePackage.END_PERMISSION__PERMITTED:
			return isPermitted();
		case ConstraintAdvicePackage.END_PERMISSION__END_FILTER:
			return getEndFilter();
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
		case ConstraintAdvicePackage.END_PERMISSION__END_KIND:
			setEndKind((EndKind) newValue);
			return;
		case ConstraintAdvicePackage.END_PERMISSION__PERMITTED:
			setPermitted((Boolean) newValue);
			return;
		case ConstraintAdvicePackage.END_PERMISSION__END_FILTER:
			setEndFilter((Filter) newValue);
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
		case ConstraintAdvicePackage.END_PERMISSION__END_KIND:
			setEndKind(END_KIND_EDEFAULT);
			return;
		case ConstraintAdvicePackage.END_PERMISSION__PERMITTED:
			setPermitted(PERMITTED_EDEFAULT);
			return;
		case ConstraintAdvicePackage.END_PERMISSION__END_FILTER:
			setEndFilter((Filter) null);
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
		case ConstraintAdvicePackage.END_PERMISSION__END_KIND:
			return endKind != END_KIND_EDEFAULT;
		case ConstraintAdvicePackage.END_PERMISSION__PERMITTED:
			return permitted != PERMITTED_EDEFAULT;
		case ConstraintAdvicePackage.END_PERMISSION__END_FILTER:
			return endFilter != null;
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
		case ConstraintAdvicePackage.END_PERMISSION___MATCHES__EOBJECT:
			return matches((EObject) arguments.get(0));
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
		result.append(" (endKind: ");
		result.append(endKind);
		result.append(", permitted: ");
		result.append(permitted);
		result.append(')');
		return result.toString();
	}

} // EndPermissionImpl
