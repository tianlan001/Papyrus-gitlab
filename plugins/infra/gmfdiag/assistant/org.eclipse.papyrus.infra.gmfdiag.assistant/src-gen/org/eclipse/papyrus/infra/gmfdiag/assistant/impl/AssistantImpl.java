/**
 * Copyright (c) 2014, 2015 Christian W. Damus and others.
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
package org.eclipse.papyrus.infra.gmfdiag.assistant.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import org.eclipse.papyrus.infra.gmfdiag.assistant.Assistant;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;

import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.operations.AssistantOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Assistant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantImpl#getProvider <em>Provider</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantImpl#getElementTypeID <em>Element Type ID</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.AssistantImpl#getElementType <em>Element Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AssistantImpl extends MinimalEObjectImpl.Container implements Assistant {
	/**
	 * The default value of the '{@link #getElementTypeID() <em>Element Type ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getElementTypeID()
	 * @generated
	 * @ordered
	 */
	protected static final String ELEMENT_TYPE_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getElementTypeID() <em>Element Type ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getElementTypeID()
	 * @generated
	 * @ordered
	 */
	protected String elementTypeID = ELEMENT_TYPE_ID_EDEFAULT;

	/**
	 * The default value of the '{@link #getElementType() <em>Element Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getElementType()
	 * @generated
	 * @ordered
	 */
	protected static final IElementType ELEMENT_TYPE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected AssistantImpl() {
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
		return AssistantPackage.Literals.ASSISTANT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getElementTypeID() {
		return elementTypeID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setElementTypeID(String newElementTypeID) {
		newElementTypeID = newElementTypeID == null ? ELEMENT_TYPE_ID_EDEFAULT : newElementTypeID;
		String oldElementTypeID = elementTypeID;
		elementTypeID = newElementTypeID;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.ASSISTANT__ELEMENT_TYPE_ID, oldElementTypeID, elementTypeID));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public IElementType getElementType() {
		return AssistantOperations.getElementType(this);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ModelingAssistantProvider getProvider() {
		ModelingAssistantProvider provider = basicGetProvider();
		return provider != null && provider.eIsProxy() ? (ModelingAssistantProvider) eResolveProxy((InternalEObject) provider) : provider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public ModelingAssistantProvider basicGetProvider() {
		return null;
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
		case AssistantPackage.ASSISTANT__PROVIDER:
			if (resolve) {
				return getProvider();
			}
			return basicGetProvider();
		case AssistantPackage.ASSISTANT__ELEMENT_TYPE_ID:
			return getElementTypeID();
		case AssistantPackage.ASSISTANT__ELEMENT_TYPE:
			return getElementType();
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
		case AssistantPackage.ASSISTANT__ELEMENT_TYPE_ID:
			setElementTypeID((String) newValue);
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
		case AssistantPackage.ASSISTANT__ELEMENT_TYPE_ID:
			setElementTypeID(ELEMENT_TYPE_ID_EDEFAULT);
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
		case AssistantPackage.ASSISTANT__PROVIDER:
			return isSetProvider();
		case AssistantPackage.ASSISTANT__ELEMENT_TYPE_ID:
			return ELEMENT_TYPE_ID_EDEFAULT == null ? elementTypeID != null : !ELEMENT_TYPE_ID_EDEFAULT.equals(elementTypeID);
		case AssistantPackage.ASSISTANT__ELEMENT_TYPE:
			return ELEMENT_TYPE_EDEFAULT == null ? getElementType() != null : !ELEMENT_TYPE_EDEFAULT.equals(getElementType());
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
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (elementTypeID: "); //$NON-NLS-1$
		result.append(elementTypeID);
		result.append(')');
		return result.toString();
	}

	/**
	 * Creates a new instance of the specified Ecore class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param eClass
	 *            The Ecore class of the instance to create.
	 * @return The new instance.
	 * @generated
	 */
	protected EObject create(EClass eClass) {
		return EcoreUtil.create(eClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public boolean isSetProvider() {
		return false;
	}

} // AssistantImpl
