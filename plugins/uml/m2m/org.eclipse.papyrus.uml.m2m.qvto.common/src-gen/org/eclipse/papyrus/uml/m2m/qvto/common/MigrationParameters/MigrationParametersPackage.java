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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MigrationParametersFactory
 * @model kind="package"
 * @generated
 */
public interface MigrationParametersPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "MigrationParameters"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http:///MigrationParameters.ecore"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "MigrationParameters"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MigrationParametersPackage eINSTANCE = org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MigrationParametersPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.ThreadConfigImpl <em>Thread Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.ThreadConfigImpl
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MigrationParametersPackageImpl#getThreadConfig()
	 * @generated
	 */
	int THREAD_CONFIG = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THREAD_CONFIG__NAME = 0;

	/**
	 * The feature id for the '<em><b>Max Threads</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THREAD_CONFIG__MAX_THREADS = 1;

	/**
	 * The number of structural features of the '<em>Thread Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THREAD_CONFIG_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Thread Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THREAD_CONFIG_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.AdvancedConfigImpl <em>Advanced Config</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.AdvancedConfigImpl
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MigrationParametersPackageImpl#getAdvancedConfig()
	 * @generated
	 */
	int ADVANCED_CONFIG = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_CONFIG__NAME = THREAD_CONFIG__NAME;

	/**
	 * The feature id for the '<em><b>Max Threads</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_CONFIG__MAX_THREADS = THREAD_CONFIG__MAX_THREADS;

	/**
	 * The feature id for the '<em><b>Mapping Parameters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_CONFIG__MAPPING_PARAMETERS = THREAD_CONFIG_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Remove Unmapped Diagrams</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_CONFIG__REMOVE_UNMAPPED_DIAGRAMS = THREAD_CONFIG_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Convert Opaque Expression To Literal String</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_CONFIG__CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING = THREAD_CONFIG_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Remove Unmapped Profiles And Stereotypes</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_CONFIG__REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES = THREAD_CONFIG_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Remove Unmapped Annotations</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_CONFIG__REMOVE_UNMAPPED_ANNOTATIONS = THREAD_CONFIG_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Always Accept Suggested Mappings</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_CONFIG__ALWAYS_ACCEPT_SUGGESTED_MAPPINGS = THREAD_CONFIG_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Advanced Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_CONFIG_FEATURE_COUNT = THREAD_CONFIG_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Advanced Config</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ADVANCED_CONFIG_OPERATION_COUNT = THREAD_CONFIG_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MappingParametersImpl <em>Mapping Parameters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MappingParametersImpl
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MigrationParametersPackageImpl#getMappingParameters()
	 * @generated
	 */
	int MAPPING_PARAMETERS = 2;

	/**
	 * The feature id for the '<em><b>Uri Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_PARAMETERS__URI_MAPPINGS = 0;

	/**
	 * The feature id for the '<em><b>Profile Uri Mappings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_PARAMETERS__PROFILE_URI_MAPPINGS = 1;

	/**
	 * The number of structural features of the '<em>Mapping Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_PARAMETERS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Mapping Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MAPPING_PARAMETERS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.URIMappingImpl <em>URI Mapping</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.URIMappingImpl
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MigrationParametersPackageImpl#getURIMapping()
	 * @generated
	 */
	int URI_MAPPING = 3;

	/**
	 * The feature id for the '<em><b>Source URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URI_MAPPING__SOURCE_URI = 0;

	/**
	 * The feature id for the '<em><b>Target URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URI_MAPPING__TARGET_URI = 1;

	/**
	 * The number of structural features of the '<em>URI Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URI_MAPPING_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>URI Mapping</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int URI_MAPPING_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig <em>Advanced Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Advanced Config</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig
	 * @generated
	 */
	EClass getAdvancedConfig();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#getMappingParameters <em>Mapping Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Mapping Parameters</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#getMappingParameters()
	 * @see #getAdvancedConfig()
	 * @generated
	 */
	EReference getAdvancedConfig_MappingParameters();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedDiagrams <em>Remove Unmapped Diagrams</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remove Unmapped Diagrams</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedDiagrams()
	 * @see #getAdvancedConfig()
	 * @generated
	 */
	EAttribute getAdvancedConfig_RemoveUnmappedDiagrams();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isConvertOpaqueExpressionToLiteralString <em>Convert Opaque Expression To Literal String</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Convert Opaque Expression To Literal String</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isConvertOpaqueExpressionToLiteralString()
	 * @see #getAdvancedConfig()
	 * @generated
	 */
	EAttribute getAdvancedConfig_ConvertOpaqueExpressionToLiteralString();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedProfilesAndStereotypes <em>Remove Unmapped Profiles And Stereotypes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remove Unmapped Profiles And Stereotypes</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedProfilesAndStereotypes()
	 * @see #getAdvancedConfig()
	 * @generated
	 */
	EAttribute getAdvancedConfig_RemoveUnmappedProfilesAndStereotypes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedAnnotations <em>Remove Unmapped Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remove Unmapped Annotations</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isRemoveUnmappedAnnotations()
	 * @see #getAdvancedConfig()
	 * @generated
	 */
	EAttribute getAdvancedConfig_RemoveUnmappedAnnotations();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isAlwaysAcceptSuggestedMappings <em>Always Accept Suggested Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Always Accept Suggested Mappings</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.AdvancedConfig#isAlwaysAcceptSuggestedMappings()
	 * @see #getAdvancedConfig()
	 * @generated
	 */
	EAttribute getAdvancedConfig_AlwaysAcceptSuggestedMappings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig <em>Thread Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Thread Config</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig
	 * @generated
	 */
	EClass getThreadConfig();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig#getName()
	 * @see #getThreadConfig()
	 * @generated
	 */
	EAttribute getThreadConfig_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig#getMaxThreads <em>Max Threads</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Threads</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.ThreadConfig#getMaxThreads()
	 * @see #getThreadConfig()
	 * @generated
	 */
	EAttribute getThreadConfig_MaxThreads();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters <em>Mapping Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mapping Parameters</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters
	 * @generated
	 */
	EClass getMappingParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters#getUriMappings <em>Uri Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Uri Mappings</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters#getUriMappings()
	 * @see #getMappingParameters()
	 * @generated
	 */
	EReference getMappingParameters_UriMappings();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters#getProfileUriMappings <em>Profile Uri Mappings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Profile Uri Mappings</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.MappingParameters#getProfileUriMappings()
	 * @see #getMappingParameters()
	 * @generated
	 */
	EReference getMappingParameters_ProfileUriMappings();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping <em>URI Mapping</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>URI Mapping</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping
	 * @generated
	 */
	EClass getURIMapping();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping#getSourceURI <em>Source URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source URI</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping#getSourceURI()
	 * @see #getURIMapping()
	 * @generated
	 */
	EAttribute getURIMapping_SourceURI();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping#getTargetURI <em>Target URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target URI</em>'.
	 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.URIMapping#getTargetURI()
	 * @see #getURIMapping()
	 * @generated
	 */
	EAttribute getURIMapping_TargetURI();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MigrationParametersFactory getMigrationParametersFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.AdvancedConfigImpl <em>Advanced Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.AdvancedConfigImpl
		 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MigrationParametersPackageImpl#getAdvancedConfig()
		 * @generated
		 */
		EClass ADVANCED_CONFIG = eINSTANCE.getAdvancedConfig();

		/**
		 * The meta object literal for the '<em><b>Mapping Parameters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ADVANCED_CONFIG__MAPPING_PARAMETERS = eINSTANCE.getAdvancedConfig_MappingParameters();

		/**
		 * The meta object literal for the '<em><b>Remove Unmapped Diagrams</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVANCED_CONFIG__REMOVE_UNMAPPED_DIAGRAMS = eINSTANCE.getAdvancedConfig_RemoveUnmappedDiagrams();

		/**
		 * The meta object literal for the '<em><b>Convert Opaque Expression To Literal String</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVANCED_CONFIG__CONVERT_OPAQUE_EXPRESSION_TO_LITERAL_STRING = eINSTANCE.getAdvancedConfig_ConvertOpaqueExpressionToLiteralString();

		/**
		 * The meta object literal for the '<em><b>Remove Unmapped Profiles And Stereotypes</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVANCED_CONFIG__REMOVE_UNMAPPED_PROFILES_AND_STEREOTYPES = eINSTANCE.getAdvancedConfig_RemoveUnmappedProfilesAndStereotypes();

		/**
		 * The meta object literal for the '<em><b>Remove Unmapped Annotations</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVANCED_CONFIG__REMOVE_UNMAPPED_ANNOTATIONS = eINSTANCE.getAdvancedConfig_RemoveUnmappedAnnotations();

		/**
		 * The meta object literal for the '<em><b>Always Accept Suggested Mappings</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ADVANCED_CONFIG__ALWAYS_ACCEPT_SUGGESTED_MAPPINGS = eINSTANCE.getAdvancedConfig_AlwaysAcceptSuggestedMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.ThreadConfigImpl <em>Thread Config</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.ThreadConfigImpl
		 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MigrationParametersPackageImpl#getThreadConfig()
		 * @generated
		 */
		EClass THREAD_CONFIG = eINSTANCE.getThreadConfig();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute THREAD_CONFIG__NAME = eINSTANCE.getThreadConfig_Name();

		/**
		 * The meta object literal for the '<em><b>Max Threads</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute THREAD_CONFIG__MAX_THREADS = eINSTANCE.getThreadConfig_MaxThreads();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MappingParametersImpl <em>Mapping Parameters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MappingParametersImpl
		 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MigrationParametersPackageImpl#getMappingParameters()
		 * @generated
		 */
		EClass MAPPING_PARAMETERS = eINSTANCE.getMappingParameters();

		/**
		 * The meta object literal for the '<em><b>Uri Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_PARAMETERS__URI_MAPPINGS = eINSTANCE.getMappingParameters_UriMappings();

		/**
		 * The meta object literal for the '<em><b>Profile Uri Mappings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MAPPING_PARAMETERS__PROFILE_URI_MAPPINGS = eINSTANCE.getMappingParameters_ProfileUriMappings();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.URIMappingImpl <em>URI Mapping</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.URIMappingImpl
		 * @see org.eclipse.papyrus.uml.m2m.qvto.common.MigrationParameters.impl.MigrationParametersPackageImpl#getURIMapping()
		 * @generated
		 */
		EClass URI_MAPPING = eINSTANCE.getURIMapping();

		/**
		 * The meta object literal for the '<em><b>Source URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute URI_MAPPING__SOURCE_URI = eINSTANCE.getURIMapping_SourceURI();

		/**
		 * The meta object literal for the '<em><b>Target URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute URI_MAPPING__TARGET_URI = eINSTANCE.getURIMapping_TargetURI();

	}

} //MigrationParametersPackage
