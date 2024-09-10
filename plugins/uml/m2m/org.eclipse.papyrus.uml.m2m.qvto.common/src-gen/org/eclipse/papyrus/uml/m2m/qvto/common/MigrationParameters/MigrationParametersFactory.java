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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage
 * @generated
 */
public interface MigrationParametersFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MigrationParametersFactory eINSTANCE = org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MigrationParametersFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Advanced Config</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Advanced Config</em>'.
	 * @generated
	 */
	AdvancedConfig createAdvancedConfig();

	/**
	 * Returns a new object of class '<em>Thread Config</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Thread Config</em>'.
	 * @generated
	 */
	ThreadConfig createThreadConfig();

	/**
	 * Returns a new object of class '<em>Mapping Parameters</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Mapping Parameters</em>'.
	 * @generated
	 */
	MappingParameters createMappingParameters();

	/**
	 * Returns a new object of class '<em>URI Mapping</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>URI Mapping</em>'.
	 * @generated
	 */
	URIMapping createURIMapping();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	MigrationParametersPackage getMigrationParametersPackage();

} //MigrationParametersFactory
