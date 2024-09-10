/**
 * Copyright (c) 2014, 2020 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bugs 568782, 568853
 */
package org.eclipse.papyrus.uml.types.core.advices.applystereotype;

import org.eclipse.emf.ecore.EAttribute;
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
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceFactory
 * @model kind="package"
 * @generated
 */
public interface ApplyStereotypeAdvicePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "applystereotype";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/uml/types/applystereotypeadvice/1.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "applystereotypeadvice";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ApplyStereotypeAdvicePackage eINSTANCE = org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdviceConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdviceConfigurationImpl
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getApplyStereotypeAdviceConfiguration()
	 * @generated
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__DESCRIPTION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owning Type</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__OWNING_TYPE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TYPE;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__ANNOTATIONS = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Before</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__BEFORE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__BEFORE;

	/**
	 * The feature id for the '<em><b>After</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__AFTER = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__AFTER;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__IDENTIFIER = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__IDENTIFIER;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__TARGET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__TARGET;

	/**
	 * The feature id for the '<em><b>Container Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__CONTAINER_CONFIGURATION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__CONTAINER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Matcher Configuration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__MATCHER_CONFIGURATION = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__MATCHER_CONFIGURATION;

	/**
	 * The feature id for the '<em><b>Inheritance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__INHERITANCE = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__INHERITANCE;

	/**
	 * The feature id for the '<em><b>Apply To All Types</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__APPLY_TO_ALL_TYPES = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__APPLY_TO_ALL_TYPES;

	/**
	 * The feature id for the '<em><b>Owning Set</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__OWNING_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_SET;

	/**
	 * The feature id for the '<em><b>Element Type Set</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__ELEMENT_TYPE_SET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__ELEMENT_TYPE_SET;

	/**
	 * The feature id for the '<em><b>Owning Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__OWNING_TARGET = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION__OWNING_TARGET;

	/**
	 * The feature id for the '<em><b>Stereotypes To Apply</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION__STEREOTYPES_TO_APPLY = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLY_STEREOTYPE_ADVICE_CONFIGURATION_FEATURE_COUNT = ElementTypesConfigurationsPackage.ABSTRACT_ADVICE_BINDING_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.StereotypeToApplyImpl <em>Stereotype To Apply</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.StereotypeToApplyImpl
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getStereotypeToApply()
	 * @generated
	 */
	int STEREOTYPE_TO_APPLY = 1;

	/**
	 * The feature id for the '<em><b>Stereotype Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_TO_APPLY__STEREOTYPE_QUALIFIED_NAME = 0;

	/**
	 * The feature id for the '<em><b>Update Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_TO_APPLY__UPDATE_NAME = 1;

	/**
	 * The feature id for the '<em><b>Required Profiles</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_TO_APPLY__REQUIRED_PROFILES = 2;

	/**
	 * The feature id for the '<em><b>Features To Set</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_TO_APPLY__FEATURES_TO_SET = 3;

	/**
	 * The number of structural features of the '<em>Stereotype To Apply</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STEREOTYPE_TO_APPLY_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.FeatureToSetImpl <em>Feature To Set</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.FeatureToSetImpl
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getFeatureToSet()
	 * @generated
	 */
	int FEATURE_TO_SET = 2;

	/**
	 * The feature id for the '<em><b>Feature Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_TO_SET__FEATURE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_TO_SET__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Feature To Set</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_TO_SET_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.FeatureValueImpl <em>Feature Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.FeatureValueImpl
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getFeatureValue()
	 * @generated
	 */
	int FEATURE_VALUE = 3;

	/**
	 * The number of structural features of the '<em>Feature Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FEATURE_VALUE_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ListValueImpl <em>List Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ListValueImpl
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getListValue()
	 * @generated
	 */
	int LIST_VALUE = 4;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_VALUE__VALUES = FEATURE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>List Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_VALUE_FEATURE_COUNT = FEATURE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ConstantValueImpl <em>Constant Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ConstantValueImpl
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getConstantValue()
	 * @generated
	 */
	int CONSTANT_VALUE = 5;

	/**
	 * The feature id for the '<em><b>Value Instance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE__VALUE_INSTANCE = FEATURE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Constant Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_VALUE_FEATURE_COUNT = FEATURE_VALUE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.DynamicValueImpl <em>Dynamic Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.DynamicValueImpl
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getDynamicValue()
	 * @generated
	 */
	int DYNAMIC_VALUE = 6;

	/**
	 * The number of structural features of the '<em>Dynamic Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DYNAMIC_VALUE_FEATURE_COUNT = FEATURE_VALUE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.QueryExecutionValueImpl <em>Query Execution Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.QueryExecutionValueImpl
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getQueryExecutionValue()
	 * @generated
	 */
	int QUERY_EXECUTION_VALUE = 7;

	/**
	 * The number of structural features of the '<em>Query Execution Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QUERY_EXECUTION_VALUE_FEATURE_COUNT = DYNAMIC_VALUE_FEATURE_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceConfiguration
	 * @generated
	 */
	EClass getApplyStereotypeAdviceConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceConfiguration#getStereotypesToApply <em>Stereotypes To Apply</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Stereotypes To Apply</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceConfiguration#getStereotypesToApply()
	 * @see #getApplyStereotypeAdviceConfiguration()
	 * @generated
	 */
	EReference getApplyStereotypeAdviceConfiguration_StereotypesToApply();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply <em>Stereotype To Apply</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Stereotype To Apply</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply
	 * @generated
	 */
	EClass getStereotypeToApply();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply#getStereotypeQualifiedName <em>Stereotype Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stereotype Qualified Name</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply#getStereotypeQualifiedName()
	 * @see #getStereotypeToApply()
	 * @generated
	 */
	EAttribute getStereotypeToApply_StereotypeQualifiedName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply#isUpdateName <em>Update Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Update Name</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply#isUpdateName()
	 * @see #getStereotypeToApply()
	 * @generated
	 */
	EAttribute getStereotypeToApply_UpdateName();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply#getRequiredProfiles <em>Required Profiles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Required Profiles</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply#getRequiredProfiles()
	 * @see #getStereotypeToApply()
	 * @generated
	 */
	EAttribute getStereotypeToApply_RequiredProfiles();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply#getFeaturesToSet <em>Features To Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Features To Set</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply#getFeaturesToSet()
	 * @see #getStereotypeToApply()
	 * @generated
	 */
	EReference getStereotypeToApply_FeaturesToSet();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet <em>Feature To Set</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature To Set</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet
	 * @generated
	 */
	EClass getFeatureToSet();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet#getFeatureName <em>Feature Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature Name</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet#getFeatureName()
	 * @see #getFeatureToSet()
	 * @generated
	 */
	EAttribute getFeatureToSet_FeatureName();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet#getValue()
	 * @see #getFeatureToSet()
	 * @generated
	 */
	EReference getFeatureToSet_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureValue <em>Feature Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Feature Value</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureValue
	 * @generated
	 */
	EClass getFeatureValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.ListValue <em>List Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Value</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.ListValue
	 * @generated
	 */
	EClass getListValue();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.ListValue#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.ListValue#getValues()
	 * @see #getListValue()
	 * @generated
	 */
	EReference getListValue_Values();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.ConstantValue <em>Constant Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant Value</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.ConstantValue
	 * @generated
	 */
	EClass getConstantValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.ConstantValue#getValueInstance <em>Value Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value Instance</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.ConstantValue#getValueInstance()
	 * @see #getConstantValue()
	 * @generated
	 */
	EReference getConstantValue_ValueInstance();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.DynamicValue <em>Dynamic Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dynamic Value</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.DynamicValue
	 * @generated
	 */
	EClass getDynamicValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.QueryExecutionValue <em>Query Execution Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Query Execution Value</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.QueryExecutionValue
	 * @generated
	 */
	EClass getQueryExecutionValue();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ApplyStereotypeAdviceFactory getApplyStereotypeAdviceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdviceConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdviceConfigurationImpl
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getApplyStereotypeAdviceConfiguration()
		 * @generated
		 */
		EClass APPLY_STEREOTYPE_ADVICE_CONFIGURATION = eINSTANCE.getApplyStereotypeAdviceConfiguration();

		/**
		 * The meta object literal for the '<em><b>Stereotypes To Apply</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLY_STEREOTYPE_ADVICE_CONFIGURATION__STEREOTYPES_TO_APPLY = eINSTANCE.getApplyStereotypeAdviceConfiguration_StereotypesToApply();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.StereotypeToApplyImpl <em>Stereotype To Apply</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.StereotypeToApplyImpl
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getStereotypeToApply()
		 * @generated
		 */
		EClass STEREOTYPE_TO_APPLY = eINSTANCE.getStereotypeToApply();

		/**
		 * The meta object literal for the '<em><b>Stereotype Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEREOTYPE_TO_APPLY__STEREOTYPE_QUALIFIED_NAME = eINSTANCE.getStereotypeToApply_StereotypeQualifiedName();

		/**
		 * The meta object literal for the '<em><b>Update Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEREOTYPE_TO_APPLY__UPDATE_NAME = eINSTANCE.getStereotypeToApply_UpdateName();

		/**
		 * The meta object literal for the '<em><b>Required Profiles</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STEREOTYPE_TO_APPLY__REQUIRED_PROFILES = eINSTANCE.getStereotypeToApply_RequiredProfiles();

		/**
		 * The meta object literal for the '<em><b>Features To Set</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference STEREOTYPE_TO_APPLY__FEATURES_TO_SET = eINSTANCE.getStereotypeToApply_FeaturesToSet();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.FeatureToSetImpl <em>Feature To Set</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.FeatureToSetImpl
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getFeatureToSet()
		 * @generated
		 */
		EClass FEATURE_TO_SET = eINSTANCE.getFeatureToSet();

		/**
		 * The meta object literal for the '<em><b>Feature Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FEATURE_TO_SET__FEATURE_NAME = eINSTANCE.getFeatureToSet_FeatureName();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FEATURE_TO_SET__VALUE = eINSTANCE.getFeatureToSet_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.FeatureValueImpl <em>Feature Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.FeatureValueImpl
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getFeatureValue()
		 * @generated
		 */
		EClass FEATURE_VALUE = eINSTANCE.getFeatureValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ListValueImpl <em>List Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ListValueImpl
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getListValue()
		 * @generated
		 */
		EClass LIST_VALUE = eINSTANCE.getListValue();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIST_VALUE__VALUES = eINSTANCE.getListValue_Values();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ConstantValueImpl <em>Constant Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ConstantValueImpl
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getConstantValue()
		 * @generated
		 */
		EClass CONSTANT_VALUE = eINSTANCE.getConstantValue();

		/**
		 * The meta object literal for the '<em><b>Value Instance</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTANT_VALUE__VALUE_INSTANCE = eINSTANCE.getConstantValue_ValueInstance();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.DynamicValueImpl <em>Dynamic Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.DynamicValueImpl
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getDynamicValue()
		 * @generated
		 */
		EClass DYNAMIC_VALUE = eINSTANCE.getDynamicValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.QueryExecutionValueImpl <em>Query Execution Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.QueryExecutionValueImpl
		 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl.ApplyStereotypeAdvicePackageImpl#getQueryExecutionValue()
		 * @generated
		 */
		EClass QUERY_EXECUTION_VALUE = eINSTANCE.getQueryExecutionValue();

	}

} //ApplyStereotypeAdvicePackage
