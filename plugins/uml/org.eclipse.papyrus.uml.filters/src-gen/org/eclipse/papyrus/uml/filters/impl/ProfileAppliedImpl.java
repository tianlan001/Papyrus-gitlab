/**
 * Copyright (c) 2014 Christian W. Damus and others.
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
package org.eclipse.papyrus.uml.filters.impl;

import java.lang.reflect.InvocationTargetException;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.uml.filters.ProfileApplied;
import org.eclipse.papyrus.uml.filters.UMLFiltersPackage;
import org.eclipse.papyrus.uml.filters.internal.operations.ProfileAppliedOperations;
import org.eclipse.uml2.uml.Profile;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Profile Applied</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.uml.filters.impl.ProfileAppliedImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.filters.impl.ProfileAppliedImpl#getProfileQualifiedName <em>Profile Qualified Name</em>}</li>
 * <li>{@link org.eclipse.papyrus.uml.filters.impl.ProfileAppliedImpl#getProfileURI <em>Profile URI</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProfileAppliedImpl extends MinimalEObjectImpl.Container implements ProfileApplied {
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
	 * The default value of the '{@link #getProfileQualifiedName() <em>Profile Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getProfileQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String PROFILE_QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProfileQualifiedName() <em>Profile Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getProfileQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String profileQualifiedName = PROFILE_QUALIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getProfileURI() <em>Profile URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getProfileURI()
	 * @generated
	 * @ordered
	 */
	protected static final String PROFILE_URI_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProfileURI() <em>Profile URI</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getProfileURI()
	 * @generated
	 * @ordered
	 */
	protected String profileURI = PROFILE_URI_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ProfileAppliedImpl() {
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
		return UMLFiltersPackage.Literals.PROFILE_APPLIED;
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
			eNotify(new ENotificationImpl(this, Notification.SET, UMLFiltersPackage.PROFILE_APPLIED__NAME, oldName, name));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getProfileQualifiedName() {
		return profileQualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setProfileQualifiedName(String newProfileQualifiedName) {
		newProfileQualifiedName = newProfileQualifiedName == null ? PROFILE_QUALIFIED_NAME_EDEFAULT : newProfileQualifiedName;
		String oldProfileQualifiedName = profileQualifiedName;
		profileQualifiedName = newProfileQualifiedName;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLFiltersPackage.PROFILE_APPLIED__PROFILE_QUALIFIED_NAME, oldProfileQualifiedName, profileQualifiedName));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getProfileURI() {
		return profileURI;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setProfileURI(String newProfileURI) {
		newProfileURI = newProfileURI == null ? PROFILE_URI_EDEFAULT : newProfileURI;
		String oldProfileURI = profileURI;
		profileURI = newProfileURI;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, UMLFiltersPackage.PROFILE_APPLIED__PROFILE_URI, oldProfileURI, profileURI));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean matches(Object input) {
		return ProfileAppliedOperations.matches(this, input);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Profile resolveProfile(Object context) {
		return ProfileAppliedOperations.resolveProfile(this, context);
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
		case UMLFiltersPackage.PROFILE_APPLIED__NAME:
			return getName();
		case UMLFiltersPackage.PROFILE_APPLIED__PROFILE_QUALIFIED_NAME:
			return getProfileQualifiedName();
		case UMLFiltersPackage.PROFILE_APPLIED__PROFILE_URI:
			return getProfileURI();
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
		case UMLFiltersPackage.PROFILE_APPLIED__NAME:
			setName((String) newValue);
			return;
		case UMLFiltersPackage.PROFILE_APPLIED__PROFILE_QUALIFIED_NAME:
			setProfileQualifiedName((String) newValue);
			return;
		case UMLFiltersPackage.PROFILE_APPLIED__PROFILE_URI:
			setProfileURI((String) newValue);
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
		case UMLFiltersPackage.PROFILE_APPLIED__NAME:
			setName(NAME_EDEFAULT);
			return;
		case UMLFiltersPackage.PROFILE_APPLIED__PROFILE_QUALIFIED_NAME:
			setProfileQualifiedName(PROFILE_QUALIFIED_NAME_EDEFAULT);
			return;
		case UMLFiltersPackage.PROFILE_APPLIED__PROFILE_URI:
			setProfileURI(PROFILE_URI_EDEFAULT);
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
		case UMLFiltersPackage.PROFILE_APPLIED__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case UMLFiltersPackage.PROFILE_APPLIED__PROFILE_QUALIFIED_NAME:
			return PROFILE_QUALIFIED_NAME_EDEFAULT == null ? profileQualifiedName != null : !PROFILE_QUALIFIED_NAME_EDEFAULT.equals(profileQualifiedName);
		case UMLFiltersPackage.PROFILE_APPLIED__PROFILE_URI:
			return PROFILE_URI_EDEFAULT == null ? profileURI != null : !PROFILE_URI_EDEFAULT.equals(profileURI);
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
		case UMLFiltersPackage.PROFILE_APPLIED___MATCHES__OBJECT:
			return matches(arguments.get(0));
		case UMLFiltersPackage.PROFILE_APPLIED___RESOLVE_PROFILE__OBJECT:
			return resolveProfile(arguments.get(0));
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

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", profileQualifiedName: "); //$NON-NLS-1$
		result.append(profileQualifiedName);
		result.append(", profileURI: "); //$NON-NLS-1$
		result.append(profileURI);
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

} // ProfileAppliedImpl
