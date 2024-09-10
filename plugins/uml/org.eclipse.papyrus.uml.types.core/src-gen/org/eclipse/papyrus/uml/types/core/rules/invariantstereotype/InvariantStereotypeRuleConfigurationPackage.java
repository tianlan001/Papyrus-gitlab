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
package org.eclipse.papyrus.uml.types.core.rules.invariantstereotype;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage;

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
 * @see org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfigurationFactory
 * @model kind="package"
 * @generated
 */
public interface InvariantStereotypeRuleConfigurationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "invariantstereotype";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/uml/types/invariantstereotyperule/1.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "invariantstereotyperule";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InvariantStereotypeRuleConfigurationPackage eINSTANCE = org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl.InvariantStereotypeRuleConfigurationPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl.InvariantStereotypeRuleConfigurationImpl <em>Invariant Stereotype Rule Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl.InvariantStereotypeRuleConfigurationImpl
	 * @see org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl.InvariantStereotypeRuleConfigurationPackageImpl#getInvariantStereotypeRuleConfiguration()
	 * @generated
	 */
	int INVARIANT_STEREOTYPE_RULE_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Stereotype Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME = RuleBasedPackage.RULE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Required Profile</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT_STEREOTYPE_RULE_CONFIGURATION__REQUIRED_PROFILE = RuleBasedPackage.RULE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Strict</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STRICT = RuleBasedPackage.RULE_CONFIGURATION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Invariant Stereotype Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT_STEREOTYPE_RULE_CONFIGURATION_FEATURE_COUNT = RuleBasedPackage.RULE_CONFIGURATION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Invariant Stereotype Rule Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT_STEREOTYPE_RULE_CONFIGURATION_OPERATION_COUNT = RuleBasedPackage.RULE_CONFIGURATION_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration <em>Invariant Stereotype Rule Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Invariant Stereotype Rule Configuration</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration
	 * @generated
	 */
	EClass getInvariantStereotypeRuleConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration#getStereotypeQualifiedName <em>Stereotype Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stereotype Qualified Name</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration#getStereotypeQualifiedName()
	 * @see #getInvariantStereotypeRuleConfiguration()
	 * @generated
	 */
	EAttribute getInvariantStereotypeRuleConfiguration_StereotypeQualifiedName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration#getRequiredProfile <em>Required Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Required Profile</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration#getRequiredProfile()
	 * @see #getInvariantStereotypeRuleConfiguration()
	 * @generated
	 */
	EAttribute getInvariantStereotypeRuleConfiguration_RequiredProfile();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration#isStrict <em>Strict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strict</em>'.
	 * @see org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration#isStrict()
	 * @see #getInvariantStereotypeRuleConfiguration()
	 * @generated
	 */
	EAttribute getInvariantStereotypeRuleConfiguration_Strict();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	InvariantStereotypeRuleConfigurationFactory getInvariantStereotypeRuleConfigurationFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl.InvariantStereotypeRuleConfigurationImpl <em>Invariant Stereotype Rule Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl.InvariantStereotypeRuleConfigurationImpl
		 * @see org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl.InvariantStereotypeRuleConfigurationPackageImpl#getInvariantStereotypeRuleConfiguration()
		 * @generated
		 */
		EClass INVARIANT_STEREOTYPE_RULE_CONFIGURATION = eINSTANCE.getInvariantStereotypeRuleConfiguration();

		/**
		 * The meta object literal for the '<em><b>Stereotype Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME = eINSTANCE.getInvariantStereotypeRuleConfiguration_StereotypeQualifiedName();

		/**
		 * The meta object literal for the '<em><b>Required Profile</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INVARIANT_STEREOTYPE_RULE_CONFIGURATION__REQUIRED_PROFILE = eINSTANCE.getInvariantStereotypeRuleConfiguration_RequiredProfile();

		/**
		 * The meta object literal for the '<em><b>Strict</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STRICT = eINSTANCE.getInvariantStereotypeRuleConfiguration_Strict();

	}

} //InvariantStereotypeRuleConfigurationPackage
