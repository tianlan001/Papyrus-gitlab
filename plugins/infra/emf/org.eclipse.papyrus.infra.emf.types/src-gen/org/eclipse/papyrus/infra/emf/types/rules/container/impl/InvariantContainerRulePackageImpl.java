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
 *  Christian W. Damus - bug 568853
 */
package org.eclipse.papyrus.infra.emf.types.rules.container.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.emf.types.rules.container.HierarchyPermission;
import org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRuleConfiguration;
import org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRuleFactory;
import org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRulePackage;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InvariantContainerRulePackageImpl extends EPackageImpl implements InvariantContainerRulePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass invariantContainerRuleConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hierarchyPermissionEClass = null;

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
	 * @see org.eclipse.papyrus.infra.emf.types.rules.container.InvariantContainerRulePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private InvariantContainerRulePackageImpl() {
		super(eNS_URI, InvariantContainerRuleFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link InvariantContainerRulePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static InvariantContainerRulePackage init() {
		if (isInited) return (InvariantContainerRulePackage)EPackage.Registry.INSTANCE.getEPackage(InvariantContainerRulePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredInvariantContainerRulePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		InvariantContainerRulePackageImpl theInvariantContainerRulePackage = registeredInvariantContainerRulePackage instanceof InvariantContainerRulePackageImpl ? (InvariantContainerRulePackageImpl)registeredInvariantContainerRulePackage : new InvariantContainerRulePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();
		RuleBasedPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theInvariantContainerRulePackage.createPackageContents();

		// Initialize created meta-data
		theInvariantContainerRulePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theInvariantContainerRulePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(InvariantContainerRulePackage.eNS_URI, theInvariantContainerRulePackage);
		return theInvariantContainerRulePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInvariantContainerRuleConfiguration() {
		return invariantContainerRuleConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getInvariantContainerRuleConfiguration_Permissions() {
		return (EReference)invariantContainerRuleConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHierarchyPermission() {
		return hierarchyPermissionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHierarchyPermission_ContainerType() {
		return (EAttribute)hierarchyPermissionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHierarchyPermission_Permitted() {
		return (EAttribute)hierarchyPermissionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getHierarchyPermission_Strict() {
		return (EAttribute)hierarchyPermissionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InvariantContainerRuleFactory getInvariantContainerRuleFactory() {
		return (InvariantContainerRuleFactory)getEFactoryInstance();
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
		invariantContainerRuleConfigurationEClass = createEClass(INVARIANT_CONTAINER_RULE_CONFIGURATION);
		createEReference(invariantContainerRuleConfigurationEClass, INVARIANT_CONTAINER_RULE_CONFIGURATION__PERMISSIONS);

		hierarchyPermissionEClass = createEClass(HIERARCHY_PERMISSION);
		createEAttribute(hierarchyPermissionEClass, HIERARCHY_PERMISSION__CONTAINER_TYPE);
		createEAttribute(hierarchyPermissionEClass, HIERARCHY_PERMISSION__PERMITTED);
		createEAttribute(hierarchyPermissionEClass, HIERARCHY_PERMISSION__STRICT);
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
		RuleBasedPackage theRuleBasedPackage = (RuleBasedPackage)EPackage.Registry.INSTANCE.getEPackage(RuleBasedPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		invariantContainerRuleConfigurationEClass.getESuperTypes().add(theRuleBasedPackage.getRuleConfiguration());

		// Initialize classes, features, and operations; add parameters
		initEClass(invariantContainerRuleConfigurationEClass, InvariantContainerRuleConfiguration.class, "InvariantContainerRuleConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getInvariantContainerRuleConfiguration_Permissions(), this.getHierarchyPermission(), null, "permissions", null, 0, -1, InvariantContainerRuleConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(hierarchyPermissionEClass, HierarchyPermission.class, "HierarchyPermission", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getHierarchyPermission_ContainerType(), theEcorePackage.getEString(), "containerType", null, 1, 1, HierarchyPermission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHierarchyPermission_Permitted(), theEcorePackage.getEBoolean(), "permitted", null, 1, 1, HierarchyPermission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHierarchyPermission_Strict(), theEcorePackage.getEBoolean(), "strict", null, 1, 1, HierarchyPermission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //InvariantContainerRulePackageImpl
