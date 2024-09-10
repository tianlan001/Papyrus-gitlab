/**
 * Copyright (c) 2014 CEA LIST.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *   Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *   Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr - bug 496176
 * 
 */
package org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Mapping Parameters</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MappingParametersImpl#getUriMappings <em>Uri Mappings</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MappingParametersImpl#getProfileUriMappings <em>Profile Uri Mappings</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MappingParametersImpl extends MinimalEObjectImpl.Container implements MappingParameters {
	/**
	 * The cached value of the '{@link #getUriMappings() <em>Uri Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUriMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<URIMapping> uriMappings;

	/**
	 * The cached value of the '{@link #getProfileUriMappings() <em>Profile Uri Mappings</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProfileUriMappings()
	 * @generated
	 * @ordered
	 */
	protected EList<URIMapping> profileUriMappings;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MappingParametersImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MigrationParametersPackage.Literals.MAPPING_PARAMETERS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<URIMapping> getUriMappings() {
		if (uriMappings == null) {
			uriMappings = new EObjectContainmentEList<URIMapping>(URIMapping.class, this, MigrationParametersPackage.MAPPING_PARAMETERS__URI_MAPPINGS);
		}
		return uriMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<URIMapping> getProfileUriMappings() {
		if (profileUriMappings == null) {
			profileUriMappings = new EObjectContainmentEList<URIMapping>(URIMapping.class, this, MigrationParametersPackage.MAPPING_PARAMETERS__PROFILE_URI_MAPPINGS);
		}
		return profileUriMappings;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case MigrationParametersPackage.MAPPING_PARAMETERS__URI_MAPPINGS:
				return ((InternalEList<?>)getUriMappings()).basicRemove(otherEnd, msgs);
			case MigrationParametersPackage.MAPPING_PARAMETERS__PROFILE_URI_MAPPINGS:
				return ((InternalEList<?>)getProfileUriMappings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case MigrationParametersPackage.MAPPING_PARAMETERS__URI_MAPPINGS:
				return getUriMappings();
			case MigrationParametersPackage.MAPPING_PARAMETERS__PROFILE_URI_MAPPINGS:
				return getProfileUriMappings();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case MigrationParametersPackage.MAPPING_PARAMETERS__URI_MAPPINGS:
				getUriMappings().clear();
				getUriMappings().addAll((Collection<? extends URIMapping>)newValue);
				return;
			case MigrationParametersPackage.MAPPING_PARAMETERS__PROFILE_URI_MAPPINGS:
				getProfileUriMappings().clear();
				getProfileUriMappings().addAll((Collection<? extends URIMapping>)newValue);
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
			case MigrationParametersPackage.MAPPING_PARAMETERS__URI_MAPPINGS:
				getUriMappings().clear();
				return;
			case MigrationParametersPackage.MAPPING_PARAMETERS__PROFILE_URI_MAPPINGS:
				getProfileUriMappings().clear();
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
			case MigrationParametersPackage.MAPPING_PARAMETERS__URI_MAPPINGS:
				return uriMappings != null && !uriMappings.isEmpty();
			case MigrationParametersPackage.MAPPING_PARAMETERS__PROFILE_URI_MAPPINGS:
				return profileUriMappings != null && !profileUriMappings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //MappingParametersImpl
