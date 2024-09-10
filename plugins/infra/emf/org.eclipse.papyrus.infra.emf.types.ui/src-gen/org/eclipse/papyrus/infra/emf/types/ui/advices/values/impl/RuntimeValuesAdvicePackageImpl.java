/**
 * Copyright (c) 2016, 2020 CEA LIST, Christian W. Damus, and others.
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
 
package org.eclipse.papyrus.infra.emf.types.ui.advices.values.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.constraints.ConstraintsPackage;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdviceFactory;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.ui.advices.values.ViewToDisplay;

import org.eclipse.papyrus.infra.properties.contexts.ContextsPackage;

import org.eclipse.papyrus.infra.properties.environment.EnvironmentPackage;

import org.eclipse.papyrus.infra.properties.ui.UiPackage;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class RuntimeValuesAdvicePackageImpl extends EPackageImpl implements RuntimeValuesAdvicePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass runtimeValuesAdviceConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass viewToDisplayEClass = null;

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
	 * @see org.eclipse.papyrus.infra.emf.types.ui.advices.values.RuntimeValuesAdvicePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private RuntimeValuesAdvicePackageImpl() {
		super(eNS_URI, RuntimeValuesAdviceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link RuntimeValuesAdvicePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static RuntimeValuesAdvicePackage init() {
		if (isInited) return (RuntimeValuesAdvicePackage)EPackage.Registry.INSTANCE.getEPackage(RuntimeValuesAdvicePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredRuntimeValuesAdvicePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		RuntimeValuesAdvicePackageImpl theRuntimeValuesAdvicePackage = registeredRuntimeValuesAdvicePackage instanceof RuntimeValuesAdvicePackageImpl ? (RuntimeValuesAdvicePackageImpl)registeredRuntimeValuesAdvicePackage : new RuntimeValuesAdvicePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		ConstraintsPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();
		EnvironmentPackage.eINSTANCE.eClass();
		ContextsPackage.eINSTANCE.eClass();
		UiPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theRuntimeValuesAdvicePackage.createPackageContents();

		// Initialize created meta-data
		theRuntimeValuesAdvicePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theRuntimeValuesAdvicePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(RuntimeValuesAdvicePackage.eNS_URI, theRuntimeValuesAdvicePackage);
		return theRuntimeValuesAdvicePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getRuntimeValuesAdviceConfiguration() {
		return runtimeValuesAdviceConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getRuntimeValuesAdviceConfiguration_ViewsToDisplay() {
		return (EReference)runtimeValuesAdviceConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getViewToDisplay() {
		return viewToDisplayEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getViewToDisplay_View() {
		return (EReference)viewToDisplayEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public RuntimeValuesAdviceFactory getRuntimeValuesAdviceFactory() {
		return (RuntimeValuesAdviceFactory)getEFactoryInstance();
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
		runtimeValuesAdviceConfigurationEClass = createEClass(RUNTIME_VALUES_ADVICE_CONFIGURATION);
		createEReference(runtimeValuesAdviceConfigurationEClass, RUNTIME_VALUES_ADVICE_CONFIGURATION__VIEWS_TO_DISPLAY);

		viewToDisplayEClass = createEClass(VIEW_TO_DISPLAY);
		createEReference(viewToDisplayEClass, VIEW_TO_DISPLAY__VIEW);
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
		ContextsPackage theContextsPackage = (ContextsPackage)EPackage.Registry.INSTANCE.getEPackage(ContextsPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		runtimeValuesAdviceConfigurationEClass.getESuperTypes().add(theElementTypesConfigurationsPackage.getAbstractAdviceBindingConfiguration());

		// Initialize classes, features, and operations; add parameters
		initEClass(runtimeValuesAdviceConfigurationEClass, RuntimeValuesAdviceConfiguration.class, "RuntimeValuesAdviceConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRuntimeValuesAdviceConfiguration_ViewsToDisplay(), this.getViewToDisplay(), null, "viewsToDisplay", null, 0, -1, RuntimeValuesAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(viewToDisplayEClass, ViewToDisplay.class, "ViewToDisplay", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getViewToDisplay_View(), theContextsPackage.getView(), null, "view", null, 1, 1, ViewToDisplay.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //RuntimeValuesAdvicePackageImpl
