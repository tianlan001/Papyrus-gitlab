/**
 * Copyright (c) 2014, 2021 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 568853, 570542
 */
package org.eclipse.papyrus.uml.types.core.matchers.stereotype;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

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
 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherFactory
 * @model kind="package"
 * @generated
 */
public interface StereotypeApplicationMatcherPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "stereotype";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/uml/types/stereotypematcher/1.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "stereotypematcher";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	StereotypeApplicationMatcherPackage eINSTANCE = org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherConfigurationImpl
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherPackageImpl#getStereotypeApplicationMatcherConfiguration()
	 * @generated
	 */
	int STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__DESCRIPTION = ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__OWNING_TYPE = ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__ANNOTATIONS = ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Matched Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__MATCHED_TYPE = ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION__MATCHED_TYPE;

	/**
	 * The feature id for the '<em><b>Stereotypes Qualified Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES = ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Profile Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI = ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION_FEATURE_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION_OPERATION_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_MATCHER_CONFIGURATION_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeMatcherAdviceConfigurationImpl <em>Stereotype Matcher Advice Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeMatcherAdviceConfigurationImpl
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherPackageImpl#getStereotypeMatcherAdviceConfiguration()
	 * @generated
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__DESCRIPTION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_TYPE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__ANNOTATIONS = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Before</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__BEFORE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__AFTER = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__IDENTIFIER = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__TARGET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__TARGET;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__CONTAINER_CONFIGURATION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__CONTAINER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHER_CONFIGURATION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__INHERITANCE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__INHERITANCE;

	/**
	 * The feature id for the '<em><b>Apply To All Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__APPLY_TO_ALL_TYPES = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_SET;

	/**
	 * The feature id for the '<em><b>Element Type Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__ELEMENT_TYPE_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__ELEMENT_TYPE_SET;

	/**
	 * The feature id for the '<em><b>Owning Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__OWNING_TARGET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TARGET;

	/**
	 * The feature id for the '<em><b>Matched Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__MATCHED_TYPE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Stereotypes Qualified Names</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Profile Uri</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION__PROFILE_URI = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Stereotype Matcher Advice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION_FEATURE_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Get Element Type Set</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION___GET_ELEMENT_TYPE_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION___GET_ELEMENT_TYPE_SET;

	/**
	 * The number of operations of the '<em>Stereotype Matcher Advice Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_MATCHER_ADVICE_CONFIGURATION_OPERATION_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.RequiredStereotypeConstraintConfigurationImpl <em>Required Stereotype Constraint Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.RequiredStereotypeConstraintConfigurationImpl
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherPackageImpl#getRequiredStereotypeConstraintConfiguration()
	 * @generated
	 */
	int REQUIRED_STEREOTYPE_CONSTRAINT_CONFIGURATION = 2;

	/**
	 * The number of structural features of the '<em>Required Stereotype Constraint Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_STEREOTYPE_CONSTRAINT_CONFIGURATION_FEATURE_COUNT = ConstraintAdvicePackage.ADVICE_CONSTRAINT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Approve Request</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_STEREOTYPE_CONSTRAINT_CONFIGURATION___APPROVE_REQUEST__IEDITCOMMANDREQUEST = ConstraintAdvicePackage.ADVICE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST;

	/**
	 * The number of operations of the '<em>Required Stereotype Constraint Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_STEREOTYPE_CONSTRAINT_CONFIGURATION_OPERATION_COUNT = ConstraintAdvicePackage.ADVICE_CONSTRAINT_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration
	 * @generated
	 */
	EClass getStereotypeApplicationMatcherConfiguration();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration#getStereotypesQualifiedNames <em>Stereotypes Qualified Names</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Stereotypes Qualified Names</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration#getStereotypesQualifiedNames()
	 * @see #getStereotypeApplicationMatcherConfiguration()
	 * @generated
	 */
	EAttribute getStereotypeApplicationMatcherConfiguration_StereotypesQualifiedNames();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration#getProfileUri <em>Profile Uri</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Profile Uri</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeApplicationMatcherConfiguration#getProfileUri()
	 * @see #getStereotypeApplicationMatcherConfiguration()
	 * @generated
	 */
	EAttribute getStereotypeApplicationMatcherConfiguration_ProfileUri();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration <em>Stereotype Matcher Advice Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stereotype Matcher Advice Configuration</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.StereotypeMatcherAdviceConfiguration
	 * @generated
	 */
	EClass getStereotypeMatcherAdviceConfiguration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.RequiredStereotypeConstraintConfiguration <em>Required Stereotype Constraint Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required Stereotype Constraint Configuration</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.RequiredStereotypeConstraintConfiguration
	 * @generated
	 */
	EClass getRequiredStereotypeConstraintConfiguration();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	StereotypeApplicationMatcherFactory getStereotypeApplicationMatcherFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherConfigurationImpl
		 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherPackageImpl#getStereotypeApplicationMatcherConfiguration()
		 * @generated
		 */
		EClass STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION = eINSTANCE.getStereotypeApplicationMatcherConfiguration();

		/**
		 * The meta object literal for the '<em><b>Stereotypes Qualified Names</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__STEREOTYPES_QUALIFIED_NAMES = eINSTANCE.getStereotypeApplicationMatcherConfiguration_StereotypesQualifiedNames();

		/**
		 * The meta object literal for the '<em><b>Profile Uri</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEREOTYPE_APPLICATION_MATCHER_CONFIGURATION__PROFILE_URI = eINSTANCE.getStereotypeApplicationMatcherConfiguration_ProfileUri();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeMatcherAdviceConfigurationImpl <em>Stereotype Matcher Advice Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeMatcherAdviceConfigurationImpl
		 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherPackageImpl#getStereotypeMatcherAdviceConfiguration()
		 * @generated
		 */
		EClass STEREOTYPE_MATCHER_ADVICE_CONFIGURATION = eINSTANCE.getStereotypeMatcherAdviceConfiguration();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.RequiredStereotypeConstraintConfigurationImpl <em>Required Stereotype Constraint Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.RequiredStereotypeConstraintConfigurationImpl
		 * @see org.eclipse.papyrus.uml.types.core.matchers.stereotype.impl.StereotypeApplicationMatcherPackageImpl#getRequiredStereotypeConstraintConfiguration()
		 * @generated
		 */
		EClass REQUIRED_STEREOTYPE_CONSTRAINT_CONFIGURATION = eINSTANCE.getRequiredStereotypeConstraintConfiguration();

	}

} //StereotypeApplicationMatcherPackage
