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
package org.eclipse.papyrus.infra.emf.types.rules.container;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRuleFactory
 * @model kind="package"
 * @generated
 */
public interface InvariantContainerRulePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "container";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/emf/types/invariantcontainerrule/1.1";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "invariantcontainerrule";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	InvariantContainerRulePackage eINSTANCE = org.eclipse.papyrus.infra.emf.types.rules.container.impl.InvariantContainerRulePackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.rules.container.impl.InvariantContainerRuleConfigurationImpl <em>Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.impl.InvariantContainerRuleConfigurationImpl
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.impl.InvariantContainerRulePackageImpl#getInvariantContainerRuleConfiguration()
	 * @generated
	 */
	int INVARIANT_CONTAINER_RULE_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Permissions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT_CONTAINER_RULE_CONFIGURATION__PERMISSIONS = RuleBasedPackage.RULE_CONFIGURATION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT_CONTAINER_RULE_CONFIGURATION_FEATURE_COUNT = RuleBasedPackage.RULE_CONFIGURATION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVARIANT_CONTAINER_RULE_CONFIGURATION_OPERATION_COUNT = RuleBasedPackage.RULE_CONFIGURATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.types.rules.container.impl.HierarchyPermissionImpl <em>Hierarchy Permission</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.impl.HierarchyPermissionImpl
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.impl.InvariantContainerRulePackageImpl#getHierarchyPermission()
	 * @generated
	 */
	int HIERARCHY_PERMISSION = 1;

	/**
	 * The feature id for the '<em><b>Container Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_PERMISSION__CONTAINER_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Permitted</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_PERMISSION__PERMITTED = 1;

	/**
	 * The feature id for the '<em><b>Strict</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_PERMISSION__STRICT = 2;

	/**
	 * The number of structural features of the '<em>Hierarchy Permission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_PERMISSION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Hierarchy Permission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HIERARCHY_PERMISSION_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRuleConfiguration <em>Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRuleConfiguration
	 * @generated
	 */
	EClass getInvariantContainerRuleConfiguration();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRuleConfiguration#getPermissions <em>Permissions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Permissions</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRuleConfiguration#getPermissions()
	 * @see #getInvariantContainerRuleConfiguration()
	 * @generated
	 */
	EReference getInvariantContainerRuleConfiguration_Permissions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission <em>Hierarchy Permission</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hierarchy Permission</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission
	 * @generated
	 */
	EClass getHierarchyPermission();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission#getContainerType <em>Container Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Container Type</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission#getContainerType()
	 * @see #getHierarchyPermission()
	 * @generated
	 */
	EAttribute getHierarchyPermission_ContainerType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission#isPermitted <em>Permitted</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Permitted</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission#isPermitted()
	 * @see #getHierarchyPermission()
	 * @generated
	 */
	EAttribute getHierarchyPermission_Permitted();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission#isStrict <em>Strict</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Strict</em>'.
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission#isStrict()
	 * @see #getHierarchyPermission()
	 * @generated
	 */
	EAttribute getHierarchyPermission_Strict();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	InvariantContainerRuleFactory getInvariantContainerRuleFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.rules.container.impl.InvariantContainerRuleConfigurationImpl <em>Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.types.rules.container.impl.InvariantContainerRuleConfigurationImpl
		 * @see org.eclipse.papyrus.infra.emf.types.rules.container.impl.InvariantContainerRulePackageImpl#getInvariantContainerRuleConfiguration()
		 * @generated
		 */
		EClass INVARIANT_CONTAINER_RULE_CONFIGURATION = eINSTANCE.getInvariantContainerRuleConfiguration();

		/**
		 * The meta object literal for the '<em><b>Permissions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INVARIANT_CONTAINER_RULE_CONFIGURATION__PERMISSIONS = eINSTANCE.getInvariantContainerRuleConfiguration_Permissions();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.types.rules.container.impl.HierarchyPermissionImpl <em>Hierarchy Permission</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.types.rules.container.impl.HierarchyPermissionImpl
		 * @see org.eclipse.papyrus.infra.emf.types.rules.container.impl.InvariantContainerRulePackageImpl#getHierarchyPermission()
		 * @generated
		 */
		EClass HIERARCHY_PERMISSION = eINSTANCE.getHierarchyPermission();

		/**
		 * The meta object literal for the '<em><b>Container Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HIERARCHY_PERMISSION__CONTAINER_TYPE = eINSTANCE.getHierarchyPermission_ContainerType();

		/**
		 * The meta object literal for the '<em><b>Permitted</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HIERARCHY_PERMISSION__PERMITTED = eINSTANCE.getHierarchyPermission_Permitted();

		/**
		 * The meta object literal for the '<em><b>Strict</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HIERARCHY_PERMISSION__STRICT = eINSTANCE.getHierarchyPermission_Strict();

	}

} //InvariantContainerRulePackage
