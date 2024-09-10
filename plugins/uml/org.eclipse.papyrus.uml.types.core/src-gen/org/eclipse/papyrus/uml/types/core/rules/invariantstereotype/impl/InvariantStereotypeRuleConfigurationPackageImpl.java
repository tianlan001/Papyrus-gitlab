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
package org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.rulebased.RuleBasedPackage;
import org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration;
import org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfigurationFactory;
import org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class InvariantStereotypeRuleConfigurationPackageImpl extends EPackageImpl implements InvariantStereotypeRuleConfigurationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass invariantStereotypeRuleConfigurationEClass = null;

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
	 * @see org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfigurationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private InvariantStereotypeRuleConfigurationPackageImpl() {
		super(eNS_URI, InvariantStereotypeRuleConfigurationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link InvariantStereotypeRuleConfigurationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static InvariantStereotypeRuleConfigurationPackage init() {
		if (isInited)
			return (InvariantStereotypeRuleConfigurationPackage) EPackage.Registry.INSTANCE.getEPackage(InvariantStereotypeRuleConfigurationPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredInvariantStereotypeRuleConfigurationPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		InvariantStereotypeRuleConfigurationPackageImpl theInvariantStereotypeRuleConfigurationPackage = registeredInvariantStereotypeRuleConfigurationPackage instanceof InvariantStereotypeRuleConfigurationPackageImpl
				? (InvariantStereotypeRuleConfigurationPackageImpl) registeredInvariantStereotypeRuleConfigurationPackage
				: new InvariantStereotypeRuleConfigurationPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();
		RuleBasedPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theInvariantStereotypeRuleConfigurationPackage.createPackageContents();

		// Initialize created meta-data
		theInvariantStereotypeRuleConfigurationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theInvariantStereotypeRuleConfigurationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(InvariantStereotypeRuleConfigurationPackage.eNS_URI, theInvariantStereotypeRuleConfigurationPackage);
		return theInvariantStereotypeRuleConfigurationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getInvariantStereotypeRuleConfiguration() {
		return invariantStereotypeRuleConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInvariantStereotypeRuleConfiguration_StereotypeQualifiedName() {
		return (EAttribute) invariantStereotypeRuleConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInvariantStereotypeRuleConfiguration_RequiredProfile() {
		return (EAttribute) invariantStereotypeRuleConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getInvariantStereotypeRuleConfiguration_Strict() {
		return (EAttribute) invariantStereotypeRuleConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public InvariantStereotypeRuleConfigurationFactory getInvariantStereotypeRuleConfigurationFactory() {
		return (InvariantStereotypeRuleConfigurationFactory) getEFactoryInstance();
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
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		invariantStereotypeRuleConfigurationEClass = createEClass(INVARIANT_STEREOTYPE_RULE_CONFIGURATION);
		createEAttribute(invariantStereotypeRuleConfigurationEClass, INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME);
		createEAttribute(invariantStereotypeRuleConfigurationEClass, INVARIANT_STEREOTYPE_RULE_CONFIGURATION__REQUIRED_PROFILE);
		createEAttribute(invariantStereotypeRuleConfigurationEClass, INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STRICT);
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
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		RuleBasedPackage theRuleBasedPackage = (RuleBasedPackage) EPackage.Registry.INSTANCE.getEPackage(RuleBasedPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		invariantStereotypeRuleConfigurationEClass.getESuperTypes().add(theRuleBasedPackage.getRuleConfiguration());

		// Initialize classes, features, and operations; add parameters
		initEClass(invariantStereotypeRuleConfigurationEClass, InvariantStereotypeRuleConfiguration.class, "InvariantStereotypeRuleConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getInvariantStereotypeRuleConfiguration_StereotypeQualifiedName(), theEcorePackage.getEString(), "stereotypeQualifiedName", null, 1, 1, InvariantStereotypeRuleConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInvariantStereotypeRuleConfiguration_RequiredProfile(), theEcorePackage.getEString(), "requiredProfile", null, 0, 1, InvariantStereotypeRuleConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getInvariantStereotypeRuleConfiguration_Strict(), ecorePackage.getEBoolean(), "strict", null, 1, 1, InvariantStereotypeRuleConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //InvariantStereotypeRuleConfigurationPackageImpl
