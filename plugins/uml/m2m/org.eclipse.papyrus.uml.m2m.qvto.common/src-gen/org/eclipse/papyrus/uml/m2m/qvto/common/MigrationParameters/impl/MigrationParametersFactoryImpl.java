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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MigrationParametersFactoryImpl extends EFactoryImpl implements MigrationParametersFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static MigrationParametersFactory init() {
		try {
			MigrationParametersFactory theMigrationParametersFactory = (MigrationParametersFactory)EPackage.Registry.INSTANCE.getEFactory(MigrationParametersPackage.eNS_URI);
			if (theMigrationParametersFactory != null) {
				return theMigrationParametersFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MigrationParametersFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MigrationParametersFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case MigrationParametersPackage.ADVANCED_CONFIG: return createAdvancedConfig();
			case MigrationParametersPackage.THREAD_CONFIG: return createThreadConfig();
			case MigrationParametersPackage.MAPPING_PARAMETERS: return createMappingParameters();
			case MigrationParametersPackage.URI_MAPPING: return createURIMapping();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdvancedConfig createAdvancedConfig() {
		AdvancedConfigImpl advancedConfig = new AdvancedConfigImpl();
		return advancedConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThreadConfig createThreadConfig() {
		ThreadConfigImpl threadConfig = new ThreadConfigImpl();
		return threadConfig;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MappingParameters createMappingParameters() {
		MappingParametersImpl mappingParameters = new MappingParametersImpl();
		return mappingParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public URIMapping createURIMapping() {
		URIMappingImpl uriMapping = new URIMappingImpl();
		return uriMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MigrationParametersPackage getMigrationParametersPackage() {
		return (MigrationParametersPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MigrationParametersPackage getPackage() {
		return MigrationParametersPackage.eINSTANCE;
	}

} //MigrationParametersFactoryImpl
