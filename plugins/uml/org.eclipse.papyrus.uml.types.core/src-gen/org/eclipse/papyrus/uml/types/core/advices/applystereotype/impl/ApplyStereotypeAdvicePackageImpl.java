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
package org.eclipse.papyrus.uml.types.core.advices.applystereotype.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdviceFactory;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdvicePackage;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ConstantValue;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.DynamicValue;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureToSet;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.FeatureValue;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.ListValue;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.QueryExecutionValue;
import org.eclipse.papyrus.uml.types.core.advices.applystereotype.StereotypeToApply;

import org.eclipse.uml2.types.TypesPackage;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ApplyStereotypeAdvicePackageImpl extends EPackageImpl implements ApplyStereotypeAdvicePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applyStereotypeAdviceConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stereotypeToApplyEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureToSetEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass featureValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constantValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dynamicValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass queryExecutionValueEClass = null;

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
	 * @see org.eclipse.papyrus.uml.types.core.advices.applystereotype.ApplyStereotypeAdvicePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ApplyStereotypeAdvicePackageImpl() {
		super(eNS_URI, ApplyStereotypeAdviceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ApplyStereotypeAdvicePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ApplyStereotypeAdvicePackage init() {
		if (isInited) return (ApplyStereotypeAdvicePackage)EPackage.Registry.INSTANCE.getEPackage(ApplyStereotypeAdvicePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredApplyStereotypeAdvicePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		ApplyStereotypeAdvicePackageImpl theApplyStereotypeAdvicePackage = registeredApplyStereotypeAdvicePackage instanceof ApplyStereotypeAdvicePackageImpl ? (ApplyStereotypeAdvicePackageImpl)registeredApplyStereotypeAdvicePackage : new ApplyStereotypeAdvicePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();
		UMLPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theApplyStereotypeAdvicePackage.createPackageContents();

		// Initialize created meta-data
		theApplyStereotypeAdvicePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theApplyStereotypeAdvicePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ApplyStereotypeAdvicePackage.eNS_URI, theApplyStereotypeAdvicePackage);
		return theApplyStereotypeAdvicePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getApplyStereotypeAdviceConfiguration() {
		return applyStereotypeAdviceConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getApplyStereotypeAdviceConfiguration_StereotypesToApply() {
		return (EReference)applyStereotypeAdviceConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStereotypeToApply() {
		return stereotypeToApplyEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStereotypeToApply_StereotypeQualifiedName() {
		return (EAttribute)stereotypeToApplyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStereotypeToApply_UpdateName() {
		return (EAttribute)stereotypeToApplyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStereotypeToApply_RequiredProfiles() {
		return (EAttribute)stereotypeToApplyEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getStereotypeToApply_FeaturesToSet() {
		return (EReference)stereotypeToApplyEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureToSet() {
		return featureToSetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getFeatureToSet_FeatureName() {
		return (EAttribute)featureToSetEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getFeatureToSet_Value() {
		return (EReference)featureToSetEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getFeatureValue() {
		return featureValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getListValue() {
		return listValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getListValue_Values() {
		return (EReference)listValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getConstantValue() {
		return constantValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getConstantValue_ValueInstance() {
		return (EReference)constantValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getDynamicValue() {
		return dynamicValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getQueryExecutionValue() {
		return queryExecutionValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ApplyStereotypeAdviceFactory getApplyStereotypeAdviceFactory() {
		return (ApplyStereotypeAdviceFactory)getEFactoryInstance();
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
		applyStereotypeAdviceConfigurationEClass = createEClass(APPLY_STEREOTYPE_ADVICE_CONFIGURATION);
		createEReference(applyStereotypeAdviceConfigurationEClass, APPLY_STEREOTYPE_ADVICE_CONFIGURATION__STEREOTYPES_TO_APPLY);

		stereotypeToApplyEClass = createEClass(STEREOTYPE_TO_APPLY);
		createEAttribute(stereotypeToApplyEClass, STEREOTYPE_TO_APPLY__STEREOTYPE_QUALIFIED_NAME);
		createEAttribute(stereotypeToApplyEClass, STEREOTYPE_TO_APPLY__UPDATE_NAME);
		createEAttribute(stereotypeToApplyEClass, STEREOTYPE_TO_APPLY__REQUIRED_PROFILES);
		createEReference(stereotypeToApplyEClass, STEREOTYPE_TO_APPLY__FEATURES_TO_SET);

		featureToSetEClass = createEClass(FEATURE_TO_SET);
		createEAttribute(featureToSetEClass, FEATURE_TO_SET__FEATURE_NAME);
		createEReference(featureToSetEClass, FEATURE_TO_SET__VALUE);

		featureValueEClass = createEClass(FEATURE_VALUE);

		listValueEClass = createEClass(LIST_VALUE);
		createEReference(listValueEClass, LIST_VALUE__VALUES);

		constantValueEClass = createEClass(CONSTANT_VALUE);
		createEReference(constantValueEClass, CONSTANT_VALUE__VALUE_INSTANCE);

		dynamicValueEClass = createEClass(DYNAMIC_VALUE);

		queryExecutionValueEClass = createEClass(QUERY_EXECUTION_VALUE);
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
		UMLPackage theUMLPackage = (UMLPackage)EPackage.Registry.INSTANCE.getEPackage(UMLPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		applyStereotypeAdviceConfigurationEClass.getESuperTypes().add(theElementTypesConfigurationsPackage.getAbstractAdviceBindingConfiguration());
		listValueEClass.getESuperTypes().add(this.getFeatureValue());
		constantValueEClass.getESuperTypes().add(this.getFeatureValue());
		dynamicValueEClass.getESuperTypes().add(this.getFeatureValue());
		queryExecutionValueEClass.getESuperTypes().add(this.getDynamicValue());

		// Initialize classes and features; add operations and parameters
		initEClass(applyStereotypeAdviceConfigurationEClass, ApplyStereotypeAdviceConfiguration.class, "ApplyStereotypeAdviceConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getApplyStereotypeAdviceConfiguration_StereotypesToApply(), this.getStereotypeToApply(), null, "stereotypesToApply", null, 0, -1, ApplyStereotypeAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stereotypeToApplyEClass, StereotypeToApply.class, "StereotypeToApply", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStereotypeToApply_StereotypeQualifiedName(), ecorePackage.getEString(), "stereotypeQualifiedName", null, 0, 1, StereotypeToApply.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStereotypeToApply_UpdateName(), ecorePackage.getEBoolean(), "updateName", null, 0, 1, StereotypeToApply.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStereotypeToApply_RequiredProfiles(), theEcorePackage.getEString(), "requiredProfiles", null, 1, -1, StereotypeToApply.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getStereotypeToApply_FeaturesToSet(), this.getFeatureToSet(), null, "featuresToSet", null, 0, -1, StereotypeToApply.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureToSetEClass, FeatureToSet.class, "FeatureToSet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFeatureToSet_FeatureName(), ecorePackage.getEString(), "featureName", null, 1, 1, FeatureToSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getFeatureToSet_Value(), this.getFeatureValue(), null, "value", null, 1, 1, FeatureToSet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(featureValueEClass, FeatureValue.class, "FeatureValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(listValueEClass, ListValue.class, "ListValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getListValue_Values(), this.getFeatureValue(), null, "values", null, 0, -1, ListValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(constantValueEClass, ConstantValue.class, "ConstantValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConstantValue_ValueInstance(), theUMLPackage.getValueSpecification(), null, "valueInstance", null, 0, 1, ConstantValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dynamicValueEClass, DynamicValue.class, "DynamicValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(queryExecutionValueEClass, QueryExecutionValue.class, "QueryExecutionValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //ApplyStereotypeAdvicePackageImpl
