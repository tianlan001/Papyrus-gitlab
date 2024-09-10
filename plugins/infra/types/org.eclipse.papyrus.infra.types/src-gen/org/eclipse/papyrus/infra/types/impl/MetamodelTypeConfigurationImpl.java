/**
 * Copyright (c) 2014 CEA LIST.
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
 */
package org.eclipse.papyrus.infra.types.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.MetamodelTypeConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metamodel Type Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.MetamodelTypeConfigurationImpl#getEClass <em>EClass</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.types.impl.MetamodelTypeConfigurationImpl#getEditHelperClassName <em>Edit Helper Class Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MetamodelTypeConfigurationImpl extends ElementTypeConfigurationImpl implements MetamodelTypeConfiguration {
	/**
	 * The cached value of the '{@link #getEClass() <em>EClass</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEClass()
	 * @generated
	 * @ordered
	 */
	protected EClass eClass;

	/**
	 * The default value of the '{@link #getEditHelperClassName() <em>Edit Helper Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditHelperClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String EDIT_HELPER_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getEditHelperClassName() <em>Edit Helper Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getEditHelperClassName()
	 * @generated
	 * @ordered
	 */
	protected String editHelperClassName = EDIT_HELPER_CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetamodelTypeConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ElementTypesConfigurationsPackage.Literals.METAMODEL_TYPE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getEClass() {
		if (eClass != null && eClass.eIsProxy()) {
			InternalEObject oldEClass = (InternalEObject)eClass;
			eClass = (EClass)eResolveProxy(oldEClass);
			if (eClass != oldEClass) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__ECLASS, oldEClass, eClass));
			}
		}
		return eClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass basicGetEClass() {
		return eClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEClass(EClass newEClass) {
		EClass oldEClass = eClass;
		eClass = newEClass;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__ECLASS, oldEClass, eClass));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getEditHelperClassName() {
		return editHelperClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setEditHelperClassName(String newEditHelperClassName) {
		String oldEditHelperClassName = editHelperClassName;
		editHelperClassName = newEditHelperClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__EDIT_HELPER_CLASS_NAME, oldEditHelperClassName, editHelperClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__ECLASS:
				if (resolve) return getEClass();
				return basicGetEClass();
			case ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__EDIT_HELPER_CLASS_NAME:
				return getEditHelperClassName();
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
			case ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__ECLASS:
				setEClass((EClass)newValue);
				return;
			case ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__EDIT_HELPER_CLASS_NAME:
				setEditHelperClassName((String)newValue);
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
			case ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__ECLASS:
				setEClass((EClass)null);
				return;
			case ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__EDIT_HELPER_CLASS_NAME:
				setEditHelperClassName(EDIT_HELPER_CLASS_NAME_EDEFAULT);
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
			case ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__ECLASS:
				return eClass != null;
			case ElementTypesConfigurationsPackage.METAMODEL_TYPE_CONFIGURATION__EDIT_HELPER_CLASS_NAME:
				return EDIT_HELPER_CLASS_NAME_EDEFAULT == null ? editHelperClassName != null : !EDIT_HELPER_CLASS_NAME_EDEFAULT.equals(editHelperClassName);
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
		result.append(" (editHelperClassName: ");
		result.append(editHelperClassName);
		result.append(')');
		return result.toString();
	}

} //MetamodelTypeConfigurationImpl
