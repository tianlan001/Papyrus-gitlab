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
 *  Christian W. Damus - bug 568782
 */
package org.eclipse.papyrus.uml.types.core.advices.settype.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

import org.eclipse.papyrus.uml.types.core.advices.settype.SetTypeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.advices.settype.SetTypeAdviceConfigurationFactory;
import org.eclipse.papyrus.uml.types.core.advices.settype.SetTypeAdviceConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SetTypeAdviceConfigurationPackageImpl extends EPackageImpl implements SetTypeAdviceConfigurationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass setTypeAdviceConfigurationEClass = null;

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
	 * @see org.eclipse.papyrus.uml.types.core.advices.settype.SetTypeAdviceConfigurationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SetTypeAdviceConfigurationPackageImpl() {
		super(eNS_URI, SetTypeAdviceConfigurationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link SetTypeAdviceConfigurationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SetTypeAdviceConfigurationPackage init() {
		if (isInited) return (SetTypeAdviceConfigurationPackage)EPackage.Registry.INSTANCE.getEPackage(SetTypeAdviceConfigurationPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredSetTypeAdviceConfigurationPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		SetTypeAdviceConfigurationPackageImpl theSetTypeAdviceConfigurationPackage = registeredSetTypeAdviceConfigurationPackage instanceof SetTypeAdviceConfigurationPackageImpl ? (SetTypeAdviceConfigurationPackageImpl)registeredSetTypeAdviceConfigurationPackage : new SetTypeAdviceConfigurationPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theSetTypeAdviceConfigurationPackage.createPackageContents();

		// Initialize created meta-data
		theSetTypeAdviceConfigurationPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theSetTypeAdviceConfigurationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SetTypeAdviceConfigurationPackage.eNS_URI, theSetTypeAdviceConfigurationPackage);
		return theSetTypeAdviceConfigurationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSetTypeAdviceConfiguration() {
		return setTypeAdviceConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSetTypeAdviceConfiguration_ValidTypes() {
		return (EAttribute)setTypeAdviceConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSetTypeAdviceConfiguration_CreationTypes() {
		return (EAttribute)setTypeAdviceConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public SetTypeAdviceConfigurationFactory getSetTypeAdviceConfigurationFactory() {
		return (SetTypeAdviceConfigurationFactory)getEFactoryInstance();
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
		setTypeAdviceConfigurationEClass = createEClass(SET_TYPE_ADVICE_CONFIGURATION);
		createEAttribute(setTypeAdviceConfigurationEClass, SET_TYPE_ADVICE_CONFIGURATION__VALID_TYPES);
		createEAttribute(setTypeAdviceConfigurationEClass, SET_TYPE_ADVICE_CONFIGURATION__CREATION_TYPES);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		setTypeAdviceConfigurationEClass.getESuperTypes().add(theElementTypesConfigurationsPackage.getAbstractAdviceBindingConfiguration());

		// Initialize classes, features, and operations; add parameters
		initEClass(setTypeAdviceConfigurationEClass, SetTypeAdviceConfiguration.class, "SetTypeAdviceConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSetTypeAdviceConfiguration_ValidTypes(), theEcorePackage.getEString(), "validTypes", null, 0, -1, SetTypeAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSetTypeAdviceConfiguration_CreationTypes(), theEcorePackage.getEString(), "creationTypes", null, 0, -1, SetTypeAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //SetTypeAdviceConfigurationPackageImpl
