/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.CustomBehaviour;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCommonBase;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Custom Behaviour</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.CustomBehaviourImpl#getSubject <em>Subject</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.CustomBehaviourImpl#getKey <em>Key</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.CustomBehaviourImpl#getEditPolicyQualifiedClassName <em>Edit Policy Qualified Class Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CustomBehaviourImpl extends EObjectImpl implements CustomBehaviour {
	/**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

	/**
	 * The default value of the '{@link #getEditPolicyQualifiedClassName() <em>Edit Policy Qualified Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditPolicyQualifiedClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String EDIT_POLICY_QUALIFIED_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditPolicyQualifiedClassName() <em>Edit Policy Qualified Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditPolicyQualifiedClassName()
	 * @generated
	 * @ordered
	 */
	protected String editPolicyQualifiedClassName = EDIT_POLICY_QUALIFIED_CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CustomBehaviourImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getCustomBehaviour();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenCommonBase getSubject() {
		if (eContainerFeatureID() != GMFGenPackage.CUSTOM_BEHAVIOUR__SUBJECT) return null;
		return (GenCommonBase)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.CUSTOM_BEHAVIOUR__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEditPolicyQualifiedClassName() {
		return editPolicyQualifiedClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEditPolicyQualifiedClassName(String newEditPolicyQualifiedClassName) {
		String oldEditPolicyQualifiedClassName = editPolicyQualifiedClassName;
		editPolicyQualifiedClassName = newEditPolicyQualifiedClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.CUSTOM_BEHAVIOUR__EDIT_POLICY_QUALIFIED_CLASS_NAME, oldEditPolicyQualifiedClassName, editPolicyQualifiedClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.CUSTOM_BEHAVIOUR__SUBJECT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.CUSTOM_BEHAVIOUR__SUBJECT, msgs);
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
			case GMFGenPackage.CUSTOM_BEHAVIOUR__SUBJECT:
				return eBasicSetContainer(null, GMFGenPackage.CUSTOM_BEHAVIOUR__SUBJECT, msgs);
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
			case GMFGenPackage.CUSTOM_BEHAVIOUR__SUBJECT:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_COMMON_BASE__BEHAVIOUR, GenCommonBase.class, msgs);
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
			case GMFGenPackage.CUSTOM_BEHAVIOUR__SUBJECT:
				return getSubject();
			case GMFGenPackage.CUSTOM_BEHAVIOUR__KEY:
				return getKey();
			case GMFGenPackage.CUSTOM_BEHAVIOUR__EDIT_POLICY_QUALIFIED_CLASS_NAME:
				return getEditPolicyQualifiedClassName();
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
			case GMFGenPackage.CUSTOM_BEHAVIOUR__KEY:
				setKey((String)newValue);
				return;
			case GMFGenPackage.CUSTOM_BEHAVIOUR__EDIT_POLICY_QUALIFIED_CLASS_NAME:
				setEditPolicyQualifiedClassName((String)newValue);
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
			case GMFGenPackage.CUSTOM_BEHAVIOUR__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case GMFGenPackage.CUSTOM_BEHAVIOUR__EDIT_POLICY_QUALIFIED_CLASS_NAME:
				setEditPolicyQualifiedClassName(EDIT_POLICY_QUALIFIED_CLASS_NAME_EDEFAULT);
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
			case GMFGenPackage.CUSTOM_BEHAVIOUR__SUBJECT:
				return getSubject() != null;
			case GMFGenPackage.CUSTOM_BEHAVIOUR__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case GMFGenPackage.CUSTOM_BEHAVIOUR__EDIT_POLICY_QUALIFIED_CLASS_NAME:
				return EDIT_POLICY_QUALIFIED_CLASS_NAME_EDEFAULT == null ? editPolicyQualifiedClassName != null : !EDIT_POLICY_QUALIFIED_CLASS_NAME_EDEFAULT.equals(editPolicyQualifiedClassName);
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
		result.append(" (key: ");
		result.append(key);
		result.append(", editPolicyQualifiedClassName: ");
		result.append(editPolicyQualifiedClassName);
		result.append(')');
		return result.toString();
	}

} //CustomBehaviourImpl
