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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersFactory;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig;
import org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping;

import org.eclipse.uml2.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class MigrationParametersPackageImpl extends EPackageImpl implements MigrationParametersPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass advancedConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass threadConfigEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass mappingParametersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uriMappingEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private MigrationParametersPackageImpl() {
		super(eNS_URI, MigrationParametersFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link MigrationParametersPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static MigrationParametersPackage init() {
		if (isInited) return (MigrationParametersPackage)EPackage.Registry.INSTANCE.getEPackage(MigrationParametersPackage.eNS_URI);

		// Obtain or create and register package
		MigrationParametersPackageImpl theMigrationParametersPackage = (MigrationParametersPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof MigrationParametersPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new MigrationParametersPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		TypesPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theMigrationParametersPackage.createPackageContents();

		// Initialize created meta-data
		theMigrationParametersPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theMigrationParametersPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(MigrationParametersPackage.eNS_URI, theMigrationParametersPackage);
		return theMigrationParametersPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAdvancedConfig() {
		return advancedConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAdvancedConfig_MappingParameters() {
		return (EReference)advancedConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdvancedConfig_RemoveUnmappedDiagrams() {
		return (EAttribute)advancedConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdvancedConfig_ConvertOpaqueExpressionToLiteralString() {
		return (EAttribute)advancedConfigEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdvancedConfig_RemoveUnmappedProfilesAndStereotypes() {
		return (EAttribute)advancedConfigEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdvancedConfig_RemoveUnmappedAnnotations() {
		return (EAttribute)advancedConfigEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAdvancedConfig_AlwaysAcceptSuggestedMappings() {
		return (EAttribute)advancedConfigEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getThreadConfig() {
		return threadConfigEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getThreadConfig_Name() {
		return (EAttribute)threadConfigEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getThreadConfig_MaxThreads() {
		return (EAttribute)threadConfigEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMappingParameters() {
		return mappingParametersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMappingParameters_UriMappings() {
		return (EReference)mappingParametersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMappingParameters_ProfileUriMappings() {
		return (EReference)mappingParametersEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getURIMapping() {
		return uriMappingEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getURIMapping_SourceURI() {
		return (EAttribute)uriMappingEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getURIMapping_TargetURI() {
		return (EAttribute)uriMappingEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MigrationParametersFactory getMigrationParametersFactory() {
		return (MigrationParametersFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		advancedConfigEClass = createEClass(ADVANCED_CONFIG);
		createEReference(advancedConfigEClass, ADVANCED_CONFIG__MAPPING_PARAMETERS);
		createEAttribute(advancedConfigEClass, ADVANCED_CONFIG__REMOVE_UNMAPPED_DIAGRAMS);
		createEAttribute(advancedConfigEClass, ADVANCED_CONFIG__CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING);
		createEAttribute(advancedConfigEClass, ADVANCED_CONFIG__REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES);
		createEAttribute(advancedConfigEClass, ADVANCED_CONFIG__REMOVE_UNMAPPED_ANNOTATIONS);
		createEAttribute(advancedConfigEClass, ADVANCED_CONFIG__ALWAYS_ACCEPT_SUGGESTED_MAPPINGS);

		threadConfigEClass = createEClass(THREAD_CONFIG);
		createEAttribute(threadConfigEClass, THREAD_CONFIG__NAME);
		createEAttribute(threadConfigEClass, THREAD_CONFIG__MAX_THREADS);

		mappingParametersEClass = createEClass(MAPPING_PARAMETERS);
		createEReference(mappingParametersEClass, MAPPING_PARAMETERS__URI_MAPPINGS);
		createEReference(mappingParametersEClass, MAPPING_PARAMETERS__PROFILE_URI_MAPPINGS);

		uriMappingEClass = createEClass(URI_MAPPING);
		createEAttribute(uriMappingEClass, URI_MAPPING__SOURCE_URI);
		createEAttribute(uriMappingEClass, URI_MAPPING__TARGET_URI);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		advancedConfigEClass.getESuperTypes().add(this.getThreadConfig());

		// Initialize classes, features, and operations; add parameters
		initEClass(advancedConfigEClass, AdvancedConfig.class, "AdvancedConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAdvancedConfig_MappingParameters(), this.getMappingParameters(), null, "mappingParameters", null, 1, 1, AdvancedConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAdvancedConfig_RemoveUnmappedDiagrams(), theTypesPackage.getBoolean(), "removeUnmappedDiagrams", "false", 1, 1, AdvancedConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getAdvancedConfig_ConvertOpaqueExpressionToLiteralString(), theTypesPackage.getBoolean(), "convertOpaqueExpressionToLiteralString", "true", 1, 1, AdvancedConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getAdvancedConfig_RemoveUnmappedProfilesAndStereotypes(), theTypesPackage.getBoolean(), "removeUnmappedProfilesAndStereotypes", "false", 1, 1, AdvancedConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getAdvancedConfig_RemoveUnmappedAnnotations(), theTypesPackage.getBoolean(), "removeUnmappedAnnotations", "false", 1, 1, AdvancedConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(getAdvancedConfig_AlwaysAcceptSuggestedMappings(), theTypesPackage.getBoolean(), "alwaysAcceptSuggestedMappings", "false", 1, 1, AdvancedConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(threadConfigEClass, ThreadConfig.class, "ThreadConfig", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getThreadConfig_Name(), theTypesPackage.getString(), "name", null, 1, 1, ThreadConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getThreadConfig_MaxThreads(), theTypesPackage.getInteger(), "maxThreads", "2", 1, 1, ThreadConfig.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(mappingParametersEClass, MappingParameters.class, "MappingParameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getMappingParameters_UriMappings(), this.getURIMapping(), null, "uriMappings", null, 0, -1, MappingParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEReference(getMappingParameters_ProfileUriMappings(), this.getURIMapping(), null, "profileUriMappings", null, 0, -1, MappingParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(uriMappingEClass, URIMapping.class, "URIMapping", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getURIMapping_SourceURI(), theTypesPackage.getString(), "sourceURI", null, 1, 1, URIMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getURIMapping_TargetURI(), theTypesPackage.getString(), "targetURI", null, 1, 1, URIMapping.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);
	}

} //MigrationParametersPackageImpl
