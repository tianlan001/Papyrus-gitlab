/**
 * Copyright (c) 2006, 2015, 2020, 2021 Borland Software Corporation, CEA LIST, ARTAL
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Borland - Initial API and implementation for code duplicated from gmf tooling repository
 *   CEA LIST - Initial API and implementation for code from Papyrus gmfgenextension
 *   Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.RefreshHook;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Refresh Hook</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.RefreshHookImpl#getRefreshCondition <em>Refresh Condition</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.RefreshHookImpl#getRefreshAction <em>Refresh Action</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RefreshHookImpl extends EObjectImpl implements RefreshHook {
	/**
	 * The default value of the '{@link #getRefreshCondition() <em>Refresh Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefreshCondition()
	 * @generated
	 * @ordered
	 */
	protected static final String REFRESH_CONDITION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRefreshCondition() <em>Refresh Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefreshCondition()
	 * @generated
	 * @ordered
	 */
	protected String refreshCondition = REFRESH_CONDITION_EDEFAULT;

	/**
	 * The default value of the '{@link #getRefreshAction() <em>Refresh Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefreshAction()
	 * @generated
	 * @ordered
	 */
	protected static final String REFRESH_ACTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRefreshAction() <em>Refresh Action</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRefreshAction()
	 * @generated
	 * @ordered
	 */
	protected String refreshAction = REFRESH_ACTION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RefreshHookImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getRefreshHook();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRefreshCondition() {
		return refreshCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRefreshCondition(String newRefreshCondition) {
		String oldRefreshCondition = refreshCondition;
		refreshCondition = newRefreshCondition;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.REFRESH_HOOK__REFRESH_CONDITION, oldRefreshCondition, refreshCondition));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRefreshAction() {
		return refreshAction;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRefreshAction(String newRefreshAction) {
		String oldRefreshAction = refreshAction;
		refreshAction = newRefreshAction;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.REFRESH_HOOK__REFRESH_ACTION, oldRefreshAction, refreshAction));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.REFRESH_HOOK__REFRESH_CONDITION:
				return getRefreshCondition();
			case GMFGenPackage.REFRESH_HOOK__REFRESH_ACTION:
				return getRefreshAction();
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
			case GMFGenPackage.REFRESH_HOOK__REFRESH_CONDITION:
				setRefreshCondition((String)newValue);
				return;
			case GMFGenPackage.REFRESH_HOOK__REFRESH_ACTION:
				setRefreshAction((String)newValue);
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
			case GMFGenPackage.REFRESH_HOOK__REFRESH_CONDITION:
				setRefreshCondition(REFRESH_CONDITION_EDEFAULT);
				return;
			case GMFGenPackage.REFRESH_HOOK__REFRESH_ACTION:
				setRefreshAction(REFRESH_ACTION_EDEFAULT);
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
			case GMFGenPackage.REFRESH_HOOK__REFRESH_CONDITION:
				return REFRESH_CONDITION_EDEFAULT == null ? refreshCondition != null : !REFRESH_CONDITION_EDEFAULT.equals(refreshCondition);
			case GMFGenPackage.REFRESH_HOOK__REFRESH_ACTION:
				return REFRESH_ACTION_EDEFAULT == null ? refreshAction != null : !REFRESH_ACTION_EDEFAULT.equals(refreshAction);
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
		result.append(" (refreshCondition: ");
		result.append(refreshCondition);
		result.append(", refreshAction: ");
		result.append(refreshAction);
		result.append(')');
		return result.toString();
	}

} //RefreshHookImpl
