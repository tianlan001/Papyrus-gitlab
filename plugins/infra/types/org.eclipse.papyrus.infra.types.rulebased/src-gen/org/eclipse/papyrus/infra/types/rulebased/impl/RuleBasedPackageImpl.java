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
package org.eclipse.papyrus.infra.types.rulebased.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

import org.eclipse.papyrus.infra.types.rulebased.AndRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.CompositeRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.NotRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.OrRuleConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedFactory;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedTypeConfiguration;
import org.eclipse.papyrus.infra.types.rulebased.RuleConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RuleBasedPackageImpl extends EPackageImpl implements RuleBasedPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleBasedTypeConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass ruleConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compositeRuleConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass notRuleConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass andRuleConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass orRuleConfigurationEClass = null;

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
	 * @see org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RuleBasedPackageImpl() {
		super(eNS_URI, RuleBasedFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RuleBasedPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RuleBasedPackage init() {
		if (isInited) return (RuleBasedPackage)EPackage.Registry.INSTANCE.getEPackage(RuleBasedPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredRuleBasedPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		RuleBasedPackageImpl theRuleBasedPackage = registeredRuleBasedPackage instanceof RuleBasedPackageImpl ? (RuleBasedPackageImpl)registeredRuleBasedPackage : new RuleBasedPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRuleBasedPackage.createPackageContents();

		// Initialize created meta-data
		theRuleBasedPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRuleBasedPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RuleBasedPackage.eNS_URI, theRuleBasedPackage);
		return theRuleBasedPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRuleBasedTypeConfiguration() {
		return ruleBasedTypeConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuleBasedTypeConfiguration_RuleConfiguration() {
		return (EReference)ruleBasedTypeConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRuleConfiguration() {
		return ruleConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getCompositeRuleConfiguration() {
		return compositeRuleConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getCompositeRuleConfiguration_ComposedRules() {
		return (EReference)compositeRuleConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNotRuleConfiguration() {
		return notRuleConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getNotRuleConfiguration_ComposedRule() {
		return (EReference)notRuleConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAndRuleConfiguration() {
		return andRuleConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOrRuleConfiguration() {
		return orRuleConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuleBasedFactory getRuleBasedFactory() {
		return (RuleBasedFactory)getEFactoryInstance();
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
		ruleBasedTypeConfigurationEClass = createEClass(RULE_BASED_TYPE_CONFIGURATION);
		createEReference(ruleBasedTypeConfigurationEClass, RULE_BASED_TYPE_CONFIGURATION__RULE_CONFIGURATION);

		ruleConfigurationEClass = createEClass(RULE_CONFIGURATION);

		compositeRuleConfigurationEClass = createEClass(COMPOSITE_RULE_CONFIGURATION);
		createEReference(compositeRuleConfigurationEClass, COMPOSITE_RULE_CONFIGURATION__COMPOSED_RULES);

		notRuleConfigurationEClass = createEClass(NOT_RULE_CONFIGURATION);
		createEReference(notRuleConfigurationEClass, NOT_RULE_CONFIGURATION__COMPOSED_RULE);

		andRuleConfigurationEClass = createEClass(AND_RULE_CONFIGURATION);

		orRuleConfigurationEClass = createEClass(OR_RULE_CONFIGURATION);
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
		ElementTypesConfigurationsPackage theElementTypesConfigurationsPackage = (ElementTypesConfigurationsPackage)EPackage.Registry.INSTANCE.getEPackage(ElementTypesConfigurationsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		ruleBasedTypeConfigurationEClass.getESuperTypes().add(theElementTypesConfigurationsPackage.getSpecializationTypeConfiguration());
		compositeRuleConfigurationEClass.getESuperTypes().add(this.getRuleConfiguration());
		notRuleConfigurationEClass.getESuperTypes().add(this.getRuleConfiguration());
		andRuleConfigurationEClass.getESuperTypes().add(this.getCompositeRuleConfiguration());
		orRuleConfigurationEClass.getESuperTypes().add(this.getCompositeRuleConfiguration());

		// Initialize classes, features, and operations; add parameters
		initEClass(ruleBasedTypeConfigurationEClass, RuleBasedTypeConfiguration.class, "RuleBasedTypeConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuleBasedTypeConfiguration_RuleConfiguration(), this.getRuleConfiguration(), null, "ruleConfiguration", null, 1, 1, RuleBasedTypeConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ruleConfigurationEClass, RuleConfiguration.class, "RuleConfiguration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(compositeRuleConfigurationEClass, CompositeRuleConfiguration.class, "CompositeRuleConfiguration", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompositeRuleConfiguration_ComposedRules(), this.getRuleConfiguration(), null, "composedRules", null, 2, -1, CompositeRuleConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(notRuleConfigurationEClass, NotRuleConfiguration.class, "NotRuleConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getNotRuleConfiguration_ComposedRule(), this.getRuleConfiguration(), null, "composedRule", null, 1, 1, NotRuleConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(andRuleConfigurationEClass, AndRuleConfiguration.class, "AndRuleConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(orRuleConfigurationEClass, OrRuleConfiguration.class, "OrRuleConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //RuleBasedPackageImpl
