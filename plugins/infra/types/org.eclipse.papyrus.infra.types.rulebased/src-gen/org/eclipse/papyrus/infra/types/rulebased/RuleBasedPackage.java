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
 *  Christian W. Damus - bugs 568853, 571560
 */
package org.eclipse.papyrus.infra.types.rulebased;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedFactory
 * @model kind="package"
 * @generated
 */
public interface RuleBasedPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "rulebased";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/infra/types/rulebased/1.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "rulebased";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RuleBasedPackage eINSTANCE = org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedTypeConfigurationImpl <em>Type Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedTypeConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getRuleBasedTypeConfiguration()
	 * @generated
	 */
	int RULE_BASED_TYPE_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__DESCRIPTION = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__OWNING_TYPE = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__ANNOTATIONS = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__IDENTIFIER = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__NAME = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__NAME;

	/**
	 * The feature id for the '<em><b>Hint</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__HINT = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__HINT;

	/**
	 * The feature id for the '<em><b>Kind</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__KIND = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__KIND;

	/**
	 * The feature id for the '<em><b>Icon Entry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__ICON_ENTRY = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__ICON_ENTRY;

	/**
	 * The feature id for the '<em><b>Owned Advice</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__OWNED_ADVICE = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__OWNED_ADVICE;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__OWNING_SET = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__OWNING_SET;

	/**
	 * The feature id for the '<em><b>Owned Configurations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__OWNED_CONFIGURATIONS;

	/**
	 * The feature id for the '<em><b>Edit Helper Advice Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__EDIT_HELPER_ADVICE_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__CONTAINER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Specialized Types</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__SPECIALIZED_TYPES = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__SPECIALIZED_TYPES;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__MATCHER_CONFIGURATION = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION__MATCHER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Rule Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Type Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION_FEATURE_COUNT = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Type Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_BASED_TYPE_CONFIGURATION_OPERATION_COUNT = ElementTypesConfigurationsPackage.SPECIALIZATION_TYPE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.RuleConfigurationImpl <em>Rule Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getRuleConfiguration()
	 * @generated
	 */
	int RULE_CONFIGURATION = 1;

	/**
	 * The number of structural features of the '<em>Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_CONFIGURATION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RULE_CONFIGURATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.CompositeRuleConfigurationImpl <em>Composite Rule Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.CompositeRuleConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getCompositeRuleConfiguration()
	 * @generated
	 */
	int COMPOSITE_RULE_CONFIGURATION = 2;

	/**
	 * The feature id for the '<em><b>Composed Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES = RULE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Composite Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_RULE_CONFIGURATION_FEATURE_COUNT = RULE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Composite Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSITE_RULE_CONFIGURATION_OPERATION_COUNT = RULE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.NotRuleConfigurationImpl <em>Not Rule Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.NotRuleConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getNotRuleConfiguration()
	 * @generated
	 */
	int NOT_RULE_CONFIGURATION = 3;

	/**
	 * The feature id for the '<em><b>Composed Rule</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_RULE_CONFIGURATION__COMPOSED_RULE = RULE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Not Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_RULE_CONFIGURATION_FEATURE_COUNT = RULE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Not Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_RULE_CONFIGURATION_OPERATION_COUNT = RULE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.AndRuleConfigurationImpl <em>And Rule Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.AndRuleConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getAndRuleConfiguration()
	 * @generated
	 */
	int AND_RULE_CONFIGURATION = 4;

	/**
	 * The feature id for the '<em><b>Composed Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_RULE_CONFIGURATION__COMPOSED_RULES = COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES;

	/**
	 * The number of structural features of the '<em>And Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_RULE_CONFIGURATION_FEATURE_COUNT = COMPOSITE_RULE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>And Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_RULE_CONFIGURATION_OPERATION_COUNT = COMPOSITE_RULE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.OrRuleConfigurationImpl <em>Or Rule Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.OrRuleConfigurationImpl
	 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getOrRuleConfiguration()
	 * @generated
	 */
	int OR_RULE_CONFIGURATION = 5;

	/**
	 * The feature id for the '<em><b>Composed Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_RULE_CONFIGURATION__COMPOSED_RULES = COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES;

	/**
	 * The number of structural features of the '<em>Or Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_RULE_CONFIGURATION_FEATURE_COUNT = COMPOSITE_RULE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Or Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_RULE_CONFIGURATION_OPERATION_COUNT = COMPOSITE_RULE_CONFIGURATION_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration <em>Type Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Type Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration
	 * @generated
	 */
	EClass getRuleBasedTypeConfiguration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration#getRuleConfiguration <em>Rule Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rule Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration#getRuleConfiguration()
	 * @see #getRuleBasedTypeConfiguration()
	 * @generated
	 */
	EReference getRuleBasedTypeConfiguration_RuleConfiguration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration <em>Rule Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Rule Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration
	 * @generated
	 */
	EClass getRuleConfiguration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration <em>Composite Rule Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composite Rule Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration
	 * @generated
	 */
	EClass getCompositeRuleConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration#getComposedRules <em>Composed Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Composed Rules</em>'.
	 * @see org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration#getComposedRules()
	 * @see #getCompositeRuleConfiguration()
	 * @generated
	 */
	EReference getCompositeRuleConfiguration_ComposedRules();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration <em>Not Rule Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Not Rule Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration
	 * @generated
	 */
	EClass getNotRuleConfiguration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration#getComposedRule <em>Composed Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Composed Rule</em>'.
	 * @see org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration#getComposedRule()
	 * @see #getNotRuleConfiguration()
	 * @generated
	 */
	EReference getNotRuleConfiguration_ComposedRule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.rulebased.AndRuleConfiguration <em>And Rule Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>And Rule Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.rulebased.AndRuleConfiguration
	 * @generated
	 */
	EClass getAndRuleConfiguration();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.types.rulebased.OrRuleConfiguration <em>Or Rule Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Or Rule Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.types.rulebased.OrRuleConfiguration
	 * @generated
	 */
	EClass getOrRuleConfiguration();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RuleBasedFactory getRuleBasedFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedTypeConfigurationImpl <em>Type Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedTypeConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getRuleBasedTypeConfiguration()
		 * @generated
		 */
		EClass RULE_BASED_TYPE_CONFIGURATION = eINSTANCE.getRuleBasedTypeConfiguration();

		/**
		 * The meta object literal for the '<em><b>Rule Configuration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION = eINSTANCE.getRuleBasedTypeConfiguration_RuleConfiguration();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.RuleConfigurationImpl <em>Rule Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getRuleConfiguration()
		 * @generated
		 */
		EClass RULE_CONFIGURATION = eINSTANCE.getRuleConfiguration();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.CompositeRuleConfigurationImpl <em>Composite Rule Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.CompositeRuleConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getCompositeRuleConfiguration()
		 * @generated
		 */
		EClass COMPOSITE_RULE_CONFIGURATION = eINSTANCE.getCompositeRuleConfiguration();

		/**
		 * The meta object literal for the '<em><b>Composed Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES = eINSTANCE.getCompositeRuleConfiguration_ComposedRules();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.NotRuleConfigurationImpl <em>Not Rule Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.NotRuleConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getNotRuleConfiguration()
		 * @generated
		 */
		EClass NOT_RULE_CONFIGURATION = eINSTANCE.getNotRuleConfiguration();

		/**
		 * The meta object literal for the '<em><b>Composed Rule</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NOT_RULE_CONFIGURATION__COMPOSED_RULE = eINSTANCE.getNotRuleConfiguration_ComposedRule();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.AndRuleConfigurationImpl <em>And Rule Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.AndRuleConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getAndRuleConfiguration()
		 * @generated
		 */
		EClass AND_RULE_CONFIGURATION = eINSTANCE.getAndRuleConfiguration();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.types.rulebased.impl.OrRuleConfigurationImpl <em>Or Rule Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.OrRuleConfigurationImpl
		 * @see org.eclipse.papyrus.infra.types.rulebased.impl.RuleBasedPackageImpl#getOrRuleConfiguration()
		 * @generated
		 */
		EClass OR_RULE_CONFIGURATION = eINSTANCE.getOrRuleConfiguration();

	}

} //RuleBasedPackage
