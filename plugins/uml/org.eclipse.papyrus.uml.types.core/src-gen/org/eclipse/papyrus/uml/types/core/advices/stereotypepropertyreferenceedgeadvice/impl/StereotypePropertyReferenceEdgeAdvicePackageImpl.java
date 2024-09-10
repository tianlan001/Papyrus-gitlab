/**
 * Copyright (c) 2017, 2020 CEA LIST, Christian W. Damus, and others.
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

package org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceConfiguration;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdviceFactory;
import org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdvicePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class StereotypePropertyReferenceEdgeAdvicePackageImpl extends EPackageImpl implements StereotypePropertyReferenceEdgeAdvicePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stereotypePropertyReferenceEdgeAdviceConfigurationEClass = null;

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
	 * @see org.eclipse.papyrus.uml.types.core.advices.stereotypepropertyreferenceedgeadvice.StereotypePropertyReferenceEdgeAdvicePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private StereotypePropertyReferenceEdgeAdvicePackageImpl() {
		super(eNS_URI, StereotypePropertyReferenceEdgeAdviceFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link StereotypePropertyReferenceEdgeAdvicePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static StereotypePropertyReferenceEdgeAdvicePackage init() {
		if (isInited) return (StereotypePropertyReferenceEdgeAdvicePackage)EPackage.Registry.INSTANCE.getEPackage(StereotypePropertyReferenceEdgeAdvicePackage.eNS_URI);

		// Obtain or create and register package
		Object registeredStereotypePropertyReferenceEdgeAdvicePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		StereotypePropertyReferenceEdgeAdvicePackageImpl theStereotypePropertyReferenceEdgeAdvicePackage = registeredStereotypePropertyReferenceEdgeAdvicePackage instanceof StereotypePropertyReferenceEdgeAdvicePackageImpl ? (StereotypePropertyReferenceEdgeAdvicePackageImpl)registeredStereotypePropertyReferenceEdgeAdvicePackage : new StereotypePropertyReferenceEdgeAdvicePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theStereotypePropertyReferenceEdgeAdvicePackage.createPackageContents();

		// Initialize created meta-data
		theStereotypePropertyReferenceEdgeAdvicePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theStereotypePropertyReferenceEdgeAdvicePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(StereotypePropertyReferenceEdgeAdvicePackage.eNS_URI, theStereotypePropertyReferenceEdgeAdvicePackage);
		return theStereotypePropertyReferenceEdgeAdvicePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getStereotypePropertyReferenceEdgeAdviceConfiguration() {
		return stereotypePropertyReferenceEdgeAdviceConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStereotypePropertyReferenceEdgeAdviceConfiguration_FeatureToSet() {
		return (EAttribute)stereotypePropertyReferenceEdgeAdviceConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStereotypePropertyReferenceEdgeAdviceConfiguration_StereotypeQualifiedName() {
		return (EAttribute)stereotypePropertyReferenceEdgeAdviceConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getStereotypePropertyReferenceEdgeAdviceConfiguration_EdgeLabel() {
		return (EAttribute)stereotypePropertyReferenceEdgeAdviceConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public StereotypePropertyReferenceEdgeAdviceFactory getStereotypePropertyReferenceEdgeAdviceFactory() {
		return (StereotypePropertyReferenceEdgeAdviceFactory)getEFactoryInstance();
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
		stereotypePropertyReferenceEdgeAdviceConfigurationEClass = createEClass(STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION);
		createEAttribute(stereotypePropertyReferenceEdgeAdviceConfigurationEClass, STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__FEATURE_TO_SET);
		createEAttribute(stereotypePropertyReferenceEdgeAdviceConfigurationEClass, STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME);
		createEAttribute(stereotypePropertyReferenceEdgeAdviceConfigurationEClass, STEREOTYPE_PROPERTY_REFERENCE_EDGE_ADVICE_CONFIGURATION__EDGE_LABEL);
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
		stereotypePropertyReferenceEdgeAdviceConfigurationEClass.getESuperTypes().add(theElementTypesConfigurationsPackage.getAbstractAdviceBindingConfiguration());

		// Initialize classes, features, and operations; add parameters
		initEClass(stereotypePropertyReferenceEdgeAdviceConfigurationEClass, StereotypePropertyReferenceEdgeAdviceConfiguration.class, "StereotypePropertyReferenceEdgeAdviceConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStereotypePropertyReferenceEdgeAdviceConfiguration_FeatureToSet(), ecorePackage.getEString(), "featureToSet", null, 1, 1, StereotypePropertyReferenceEdgeAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStereotypePropertyReferenceEdgeAdviceConfiguration_StereotypeQualifiedName(), ecorePackage.getEString(), "stereotypeQualifiedName", null, 1, 1, StereotypePropertyReferenceEdgeAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getStereotypePropertyReferenceEdgeAdviceConfiguration_EdgeLabel(), theEcorePackage.getEString(), "edgeLabel", null, 0, 1, StereotypePropertyReferenceEdgeAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} // StereotypePropertyReferenceEdgeAdvicePackageImpl
