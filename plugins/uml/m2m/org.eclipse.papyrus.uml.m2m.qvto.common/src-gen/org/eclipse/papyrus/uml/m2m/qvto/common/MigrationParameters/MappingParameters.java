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
package org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Mapping Parameters</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters#getUriMappings <em>Uri Mappings</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters#getProfileUriMappings <em>Profile Uri Mappings</em>}</li>
 * </ul>
 *
 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getMappingParameters()
 * @model
 * @generated
 */
public interface MappingParameters extends EObject {
	/**
	 * Returns the value of the '<em><b>Uri Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * When models/libraries have been migrated separately, maps the libraries imported from RSA to their Papyrus equivalent
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Uri Mappings</em>' containment reference list.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getMappingParameters_UriMappings()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<URIMapping> getUriMappings();

	/**
	 * Returns the value of the '<em><b>Profile Uri Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping}.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * <!-- begin-model-doc -->
	 * When profiles have been migrated separately, maps the profiles imported from RSA to their Papyrus equivalent
	 * <!-- end-model-doc -->
	 * @return the value of the '<em>Profile Uri Mappings</em>' containment reference list.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#getMappingParameters_ProfileUriMappings()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<URIMapping> getProfileUriMappings();

} // MappingParameters
