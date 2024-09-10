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

import java.lang.reflect.InvocationTargetException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.assistant.AssistantPackage;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ElementTypeFilter;
import org.eclipse.papyrus.infra.gmfdiag.assistant.ModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.internal.operations.ElementTypeFilterOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Element Type Filter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ElementTypeFilterImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ElementTypeFilterImpl#getElementTypeID <em>Element Type ID</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ElementTypeFilterImpl#getElementType <em>Element Type</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.gmfdiag.assistant.impl.ElementTypeFilterImpl#getProvider <em>Provider</em>}</li>
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
		return AssistantPackage.Literals.ELEMENT_TYPE_FILTER;
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
		newName = newName == null ? NAME_EDEFAULT : newName;
		String oldName = name;
		name = newName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.ELEMENT_TYPE_FILTER__NAME, oldName, name));
		}
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
			eNotify(new ENotificationImpl(this, Notification.SET, AssistantPackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE_ID, oldElementTypeID, elementTypeID));
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
		return ElementTypeFilterOperations.getElementType(this);
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
		return ElementTypeFilterOperations.getProvider(this);
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
		case AssistantPackage.ELEMENT_TYPE_FILTER__NAME:
			return getName();
		case AssistantPackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE_ID:
			return getElementTypeID();
		case AssistantPackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE:
			return getElementType();
		case AssistantPackage.ELEMENT_TYPE_FILTER__PROVIDER:
			if (resolve) {
				return getProvider();
			}
			return basicGetProvider();
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
		case AssistantPackage.ELEMENT_TYPE_FILTER__NAME:
			setName((String) newValue);
			return;
		case AssistantPackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE_ID:
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
		case AssistantPackage.ELEMENT_TYPE_FILTER__NAME:
			setName(NAME_EDEFAULT);
			return;
		case AssistantPackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE_ID:
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
		case AssistantPackage.ELEMENT_TYPE_FILTER__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case AssistantPackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE_ID:
			return ELEMENT_TYPE_ID_EDEFAULT == null ? elementTypeID != null : !ELEMENT_TYPE_ID_EDEFAULT.equals(elementTypeID);
		case AssistantPackage.ELEMENT_TYPE_FILTER__ELEMENT_TYPE:
			return ELEMENT_TYPE_EDEFAULT == null ? getElementType() != null : !ELEMENT_TYPE_EDEFAULT.equals(getElementType());
		case AssistantPackage.ELEMENT_TYPE_FILTER__PROVIDER:
			return basicGetProvider() != null;
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
		case AssistantPackage.ELEMENT_TYPE_FILTER___MATCHES__OBJECT:
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
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", elementTypeID: "); //$NON-NLS-1$
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

} // ElementTypeFilterImpl
