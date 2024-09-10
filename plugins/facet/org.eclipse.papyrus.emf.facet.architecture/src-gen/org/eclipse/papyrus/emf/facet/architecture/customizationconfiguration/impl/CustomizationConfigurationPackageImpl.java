/**
 *  Copyright (c) 2020 CEA LIST and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Vincent LORENZO - Initial API and implementation
 */
package org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.AbsoluteOrder;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationFactory;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.IApplicationRule;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Location;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.Redefinition;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.RelativeOrder;

import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.util.CustomizationConfigurationValidator;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.CustomPackage;

import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.EFacetPackage;

import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class CustomizationConfigurationPackageImpl extends EPackageImpl implements CustomizationConfigurationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass emfFacetTreeViewerConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass customizationReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass iApplicationRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass absoluteOrderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass relativeOrderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass redefinitionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum locationEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CustomizationConfigurationPackageImpl() {
		super(eNS_URI, CustomizationConfigurationFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 *
	 * <p>
	 * This method is used to initialize {@link CustomizationConfigurationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CustomizationConfigurationPackage init() {
		if (isInited) {
			return (CustomizationConfigurationPackage) EPackage.Registry.INSTANCE.getEPackage(CustomizationConfigurationPackage.eNS_URI);
		}

		// Obtain or create and register package
		Object registeredCustomizationConfigurationPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		CustomizationConfigurationPackageImpl theCustomizationConfigurationPackage = registeredCustomizationConfigurationPackage instanceof CustomizationConfigurationPackageImpl
				? (CustomizationConfigurationPackageImpl) registeredCustomizationConfigurationPackage
				: new CustomizationConfigurationPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		ArchitecturePackage.eINSTANCE.eClass();
		CustomPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();
		EFacetPackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theCustomizationConfigurationPackage.createPackageContents();

		// Initialize created meta-data
		theCustomizationConfigurationPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theCustomizationConfigurationPackage,
				new EValidator.Descriptor() {
					@Override
					public EValidator getEValidator() {
						return CustomizationConfigurationValidator.INSTANCE;
					}
				});

		// Mark meta-data to indicate it can't be changed
		theCustomizationConfigurationPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CustomizationConfigurationPackage.eNS_URI, theCustomizationConfigurationPackage);
		return theCustomizationConfigurationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getEMFFacetTreeViewerConfiguration() {
		return emfFacetTreeViewerConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getEMFFacetTreeViewerConfiguration_CustomizationReferences() {
		return (EReference) emfFacetTreeViewerConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getEMFFacetTreeViewerConfiguration_Extends() {
		return (EReference) emfFacetTreeViewerConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getCustomizationReference() {
		return customizationReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getCustomizationReference_Description() {
		return (EAttribute) customizationReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCustomizationReference_ReferencedCustomization() {
		return (EReference) customizationReferenceEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCustomizationReference_ApplicationRule() {
		return (EReference) customizationReferenceEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getIApplicationRule() {
		return iApplicationRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getAbsoluteOrder() {
		return absoluteOrderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getAbsoluteOrder_Order() {
		return (EAttribute) absoluteOrderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRelativeOrder() {
		return relativeOrderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getRelativeOrder_Location() {
		return (EAttribute) relativeOrderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRelativeOrder_RelativeCustomizationReference() {
		return (EReference) relativeOrderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRedefinition() {
		return redefinitionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRedefinition_RedefinedCustomizationReference() {
		return (EReference) redefinitionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getLocation() {
		return locationEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public CustomizationConfigurationFactory getCustomizationConfigurationFactory() {
		return (CustomizationConfigurationFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) {
			return;
		}
		isCreated = true;

		// Create classes and their features
		emfFacetTreeViewerConfigurationEClass = createEClass(EMF_FACET_TREE_VIEWER_CONFIGURATION);
		createEReference(emfFacetTreeViewerConfigurationEClass, EMF_FACET_TREE_VIEWER_CONFIGURATION__CUSTOMIZATION_REFERENCES);
		createEReference(emfFacetTreeViewerConfigurationEClass, EMF_FACET_TREE_VIEWER_CONFIGURATION__EXTENDS);

		customizationReferenceEClass = createEClass(CUSTOMIZATION_REFERENCE);
		createEAttribute(customizationReferenceEClass, CUSTOMIZATION_REFERENCE__DESCRIPTION);
		createEReference(customizationReferenceEClass, CUSTOMIZATION_REFERENCE__REFERENCED_CUSTOMIZATION);
		createEReference(customizationReferenceEClass, CUSTOMIZATION_REFERENCE__APPLICATION_RULE);

		iApplicationRuleEClass = createEClass(IAPPLICATION_RULE);

		absoluteOrderEClass = createEClass(ABSOLUTE_ORDER);
		createEAttribute(absoluteOrderEClass, ABSOLUTE_ORDER__ORDER);

		relativeOrderEClass = createEClass(RELATIVE_ORDER);
		createEAttribute(relativeOrderEClass, RELATIVE_ORDER__LOCATION);
		createEReference(relativeOrderEClass, RELATIVE_ORDER__RELATIVE_CUSTOMIZATION_REFERENCE);

		redefinitionEClass = createEClass(REDEFINITION);
		createEReference(redefinitionEClass, REDEFINITION__REDEFINED_CUSTOMIZATION_REFERENCE);

		// Create enums
		locationEEnum = createEEnum(LOCATION);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) {
			return;
		}
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		ArchitecturePackage theArchitecturePackage = (ArchitecturePackage) EPackage.Registry.INSTANCE.getEPackage(ArchitecturePackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		CustomPackage theCustomPackage = (CustomPackage) EPackage.Registry.INSTANCE.getEPackage(CustomPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		emfFacetTreeViewerConfigurationEClass.getESuperTypes().add(theArchitecturePackage.getTreeViewerConfiguration());
		absoluteOrderEClass.getESuperTypes().add(this.getIApplicationRule());
		relativeOrderEClass.getESuperTypes().add(this.getIApplicationRule());
		redefinitionEClass.getESuperTypes().add(this.getIApplicationRule());

		// Initialize classes, features, and operations; add parameters
		initEClass(emfFacetTreeViewerConfigurationEClass, EMFFacetTreeViewerConfiguration.class, "EMFFacetTreeViewerConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getEMFFacetTreeViewerConfiguration_CustomizationReferences(), this.getCustomizationReference(), null, "customizationReferences", null, 0, -1, EMFFacetTreeViewerConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, //$NON-NLS-1$
				IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getEMFFacetTreeViewerConfiguration_Extends(), this.getEMFFacetTreeViewerConfiguration(), null, "extends", null, 0, 1, EMFFacetTreeViewerConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, //$NON-NLS-1$
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(customizationReferenceEClass, CustomizationReference.class, "CustomizationReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getCustomizationReference_Description(), theEcorePackage.getEString(), "description", null, 0, 1, CustomizationReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(getCustomizationReference_ReferencedCustomization(), theCustomPackage.getCustomization(), null, "referencedCustomization", null, 1, 1, CustomizationReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, //$NON-NLS-1$
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCustomizationReference_ApplicationRule(), this.getIApplicationRule(), null, "applicationRule", null, 1, 1, CustomizationReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, //$NON-NLS-1$
				IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iApplicationRuleEClass, IApplicationRule.class, "IApplicationRule", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(absoluteOrderEClass, AbsoluteOrder.class, "AbsoluteOrder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAbsoluteOrder_Order(), theEcorePackage.getEInt(), "order", "0", 1, 1, AbsoluteOrder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$

		initEClass(relativeOrderEClass, RelativeOrder.class, "RelativeOrder", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getRelativeOrder_Location(), this.getLocation(), "location", "BEFORE", 1, 1, RelativeOrder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(getRelativeOrder_RelativeCustomizationReference(), this.getCustomizationReference(), null, "relativeCustomizationReference", null, 1, 1, RelativeOrder.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, //$NON-NLS-1$
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(redefinitionEClass, Redefinition.class, "Redefinition", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getRedefinition_RedefinedCustomizationReference(), this.getCustomizationReference(), null, "redefinedCustomizationReference", null, 1, 1, Redefinition.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, //$NON-NLS-1$
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(locationEEnum, Location.class, "Location"); //$NON-NLS-1$
		addEEnumLiteral(locationEEnum, Location.BEFORE);
		addEEnumLiteral(locationEEnum, Location.AFTER);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore"; //$NON-NLS-1$
		addAnnotation(emfFacetTreeViewerConfigurationEClass,
				source,
				new String[] {
						"constraints", "checkConfiguration" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(customizationReferenceEClass,
				source,
				new String[] {
						"constraints", "checkCustomizationReference" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(absoluteOrderEClass,
				source,
				new String[] {
						"constraints", "checkAbsoluteOrder" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(relativeOrderEClass,
				source,
				new String[] {
						"constraints", "checkRelativeOrder" //$NON-NLS-1$ //$NON-NLS-2$
				});
		addAnnotation(redefinitionEClass,
				source,
				new String[] {
						"constraints", "checkRedefinition" //$NON-NLS-1$ //$NON-NLS-2$
				});
	}

} // CustomizationConfigurationPackageImpl
