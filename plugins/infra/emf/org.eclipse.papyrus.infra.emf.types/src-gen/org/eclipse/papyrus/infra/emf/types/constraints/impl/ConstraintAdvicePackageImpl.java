/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.emf.types.constraints.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest;
import org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.AnyReference;
import org.eclipse.papyrus.infra.emf.types.constraints.CompositeConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceFactory;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;
import org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeFilter;
import org.eclipse.papyrus.infra.emf.types.constraints.ElementTypeRelationshipKind;
import org.eclipse.papyrus.infra.emf.types.constraints.EndKind;
import org.eclipse.papyrus.infra.emf.types.constraints.EndPermission;
import org.eclipse.papyrus.infra.emf.types.constraints.Reference;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferenceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferenceKind;
import org.eclipse.papyrus.infra.emf.types.constraints.ReferencePermission;

import org.eclipse.papyrus.infra.emf.types.constraints.RelationshipConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.util.ConstraintAdviceValidator;
import org.eclipse.papyrus.infra.filters.FiltersPackage;

import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;

import org.eclipse.uml2.types.TypesPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class ConstraintAdvicePackageImpl extends EPackageImpl implements ConstraintAdvicePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass constraintAdviceConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass adviceConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass referenceConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass referencePermissionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass anyReferenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass referenceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass elementTypeFilterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass relationshipConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass endPermissionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EClass compositeConstraintEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum referenceKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum elementTypeRelationshipKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EEnum endKindEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private EDataType editCommandRequestEDataType = null;

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
	 * @see org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ConstraintAdvicePackageImpl() {
		super(eNS_URI, ConstraintAdviceFactory.eINSTANCE);
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
	 * This method is used to initialize {@link ConstraintAdvicePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ConstraintAdvicePackage init() {
		if (isInited) {
			return (ConstraintAdvicePackage) EPackage.Registry.INSTANCE.getEPackage(ConstraintAdvicePackage.eNS_URI);
		}

		// Obtain or create and register package
		Object registeredConstraintAdvicePackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		ConstraintAdvicePackageImpl theConstraintAdvicePackage = registeredConstraintAdvicePackage instanceof ConstraintAdvicePackageImpl ? (ConstraintAdvicePackageImpl) registeredConstraintAdvicePackage : new ConstraintAdvicePackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();
		ElementTypesConfigurationsPackage.eINSTANCE.eClass();
		FiltersPackage.eINSTANCE.eClass();
		TypesPackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theConstraintAdvicePackage.createPackageContents();

		// Initialize created meta-data
		theConstraintAdvicePackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put(theConstraintAdvicePackage,
				new EValidator.Descriptor() {
					@Override
					public EValidator getEValidator() {
						return ConstraintAdviceValidator.INSTANCE;
					}
				});

		// Mark meta-data to indicate it can't be changed
		theConstraintAdvicePackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ConstraintAdvicePackage.eNS_URI, theConstraintAdvicePackage);
		return theConstraintAdvicePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getConstraintAdviceConfiguration() {
		return constraintAdviceConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getConstraintAdviceConfiguration_Constraint() {
		return (EReference) constraintAdviceConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getAdviceConstraint() {
		return adviceConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getAdviceConstraint_Composite() {
		return (EReference) adviceConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getAdviceConstraint_Advice() {
		return (EReference) adviceConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getAdviceConstraint_OwningAdvice() {
		return (EReference) adviceConstraintEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getAdviceConstraint__ApproveRequest__IEditCommandRequest() {
		return adviceConstraintEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getAdviceConstraint__GetAdvice() {
		return adviceConstraintEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getReferenceConstraint() {
		return referenceConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getReferenceConstraint_Permission() {
		return (EReference) referenceConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getReferencePermission() {
		return referencePermissionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getReferencePermission_Permitted() {
		return (EAttribute) referencePermissionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getReferencePermission__Matches__EReference() {
		return referencePermissionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getAnyReference() {
		return anyReferenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getAnyReference_ReferenceKind() {
		return (EAttribute) anyReferenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getReference() {
		return referenceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getReference_Reference() {
		return (EReference) referenceEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getElementTypeFilter() {
		return elementTypeFilterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getElementTypeFilter_ElementType() {
		return (EReference) elementTypeFilterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getElementTypeFilter_Relationship() {
		return (EAttribute) elementTypeFilterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getRelationshipConstraint() {
		return relationshipConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getRelationshipConstraint_Permission() {
		return (EReference) relationshipConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getEndPermission() {
		return endPermissionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getEndPermission_EndKind() {
		return (EAttribute) endPermissionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getEndPermission_Permitted() {
		return (EAttribute) endPermissionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getEndPermission_EndFilter() {
		return (EReference) endPermissionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EOperation getEndPermission__Matches__EObject() {
		return endPermissionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EClass getCompositeConstraint() {
		return compositeConstraintEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EReference getCompositeConstraint_Constraint() {
		return (EReference) compositeConstraintEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EAttribute getCompositeConstraint_Operator() {
		return (EAttribute) compositeConstraintEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getReferenceKind() {
		return referenceKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getElementTypeRelationshipKind() {
		return elementTypeRelationshipKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EEnum getEndKind() {
		return endKindEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EDataType getEditCommandRequest() {
		return editCommandRequestEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public ConstraintAdviceFactory getConstraintAdviceFactory() {
		return (ConstraintAdviceFactory) getEFactoryInstance();
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
		constraintAdviceConfigurationEClass = createEClass(CONSTRAINT_ADVICE_CONFIGURATION);
		createEReference(constraintAdviceConfigurationEClass, CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT);

		adviceConstraintEClass = createEClass(ADVICE_CONSTRAINT);
		createEReference(adviceConstraintEClass, ADVICE_CONSTRAINT__COMPOSITE);
		createEReference(adviceConstraintEClass, ADVICE_CONSTRAINT__ADVICE);
		createEReference(adviceConstraintEClass, ADVICE_CONSTRAINT__OWNING_ADVICE);
		createEOperation(adviceConstraintEClass, ADVICE_CONSTRAINT___APPROVE_REQUEST__IEDITCOMMANDREQUEST);
		createEOperation(adviceConstraintEClass, ADVICE_CONSTRAINT___GET_ADVICE);

		compositeConstraintEClass = createEClass(COMPOSITE_CONSTRAINT);
		createEAttribute(compositeConstraintEClass, COMPOSITE_CONSTRAINT__OPERATOR);
		createEReference(compositeConstraintEClass, COMPOSITE_CONSTRAINT__CONSTRAINT);

		referenceConstraintEClass = createEClass(REFERENCE_CONSTRAINT);
		createEReference(referenceConstraintEClass, REFERENCE_CONSTRAINT__PERMISSION);

		referencePermissionEClass = createEClass(REFERENCE_PERMISSION);
		createEAttribute(referencePermissionEClass, REFERENCE_PERMISSION__PERMITTED);
		createEOperation(referencePermissionEClass, REFERENCE_PERMISSION___MATCHES__EREFERENCE);

		anyReferenceEClass = createEClass(ANY_REFERENCE);
		createEAttribute(anyReferenceEClass, ANY_REFERENCE__REFERENCE_KIND);

		referenceEClass = createEClass(REFERENCE);
		createEReference(referenceEClass, REFERENCE__REFERENCE);

		elementTypeFilterEClass = createEClass(ELEMENT_TYPE_FILTER);
		createEReference(elementTypeFilterEClass, ELEMENT_TYPE_FILTER__ELEMENT_TYPE);
		createEAttribute(elementTypeFilterEClass, ELEMENT_TYPE_FILTER__RELATIONSHIP);

		relationshipConstraintEClass = createEClass(RELATIONSHIP_CONSTRAINT);
		createEReference(relationshipConstraintEClass, RELATIONSHIP_CONSTRAINT__PERMISSION);

		endPermissionEClass = createEClass(END_PERMISSION);
		createEAttribute(endPermissionEClass, END_PERMISSION__END_KIND);
		createEAttribute(endPermissionEClass, END_PERMISSION__PERMITTED);
		createEReference(endPermissionEClass, END_PERMISSION__END_FILTER);
		createEOperation(endPermissionEClass, END_PERMISSION___MATCHES__EOBJECT);

		// Create enums
		referenceKindEEnum = createEEnum(REFERENCE_KIND);
		elementTypeRelationshipKindEEnum = createEEnum(ELEMENT_TYPE_RELATIONSHIP_KIND);
		endKindEEnum = createEEnum(END_KIND);

		// Create data types
		editCommandRequestEDataType = createEDataType(EDIT_COMMAND_REQUEST);
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
		ElementTypesConfigurationsPackage theElementTypesConfigurationsPackage = (ElementTypesConfigurationsPackage) EPackage.Registry.INSTANCE.getEPackage(ElementTypesConfigurationsPackage.eNS_URI);
		TypesPackage theTypesPackage = (TypesPackage) EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);
		FiltersPackage theFiltersPackage = (FiltersPackage) EPackage.Registry.INSTANCE.getEPackage(FiltersPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage) EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		constraintAdviceConfigurationEClass.getESuperTypes().add(theElementTypesConfigurationsPackage.getAbstractAdviceBindingConfiguration());
		compositeConstraintEClass.getESuperTypes().add(this.getAdviceConstraint());
		referenceConstraintEClass.getESuperTypes().add(this.getAdviceConstraint());
		referencePermissionEClass.getESuperTypes().add(theFiltersPackage.getFilteredElement());
		anyReferenceEClass.getESuperTypes().add(this.getReferencePermission());
		referenceEClass.getESuperTypes().add(this.getReferencePermission());
		elementTypeFilterEClass.getESuperTypes().add(theFiltersPackage.getFilter());
		relationshipConstraintEClass.getESuperTypes().add(this.getAdviceConstraint());
		endPermissionEClass.getESuperTypes().add(theFiltersPackage.getFilteredElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(constraintAdviceConfigurationEClass, ConstraintAdviceConfiguration.class, "ConstraintAdviceConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getConstraintAdviceConfiguration_Constraint(), this.getAdviceConstraint(), this.getAdviceConstraint_OwningAdvice(), "constraint", null, 1, -1, ConstraintAdviceConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
				!IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(adviceConstraintEClass, AdviceConstraint.class, "AdviceConstraint", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAdviceConstraint_Composite(), this.getCompositeConstraint(), this.getCompositeConstraint_Constraint(), "composite", null, 0, 1, AdviceConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getAdviceConstraint_Advice(), this.getConstraintAdviceConfiguration(), null, "advice", null, 1, 1, AdviceConstraint.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				IS_DERIVED, !IS_ORDERED);
		initEReference(getAdviceConstraint_OwningAdvice(), this.getConstraintAdviceConfiguration(), this.getConstraintAdviceConfiguration_Constraint(), "owningAdvice", null, 0, 1, AdviceConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
				!IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		EOperation op = initEOperation(getAdviceConstraint__ApproveRequest__IEditCommandRequest(), theTypesPackage.getBoolean(), "approveRequest", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, this.getEditCommandRequest(), "request", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEOperation(getAdviceConstraint__GetAdvice(), this.getConstraintAdviceConfiguration(), "getAdvice", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEClass(compositeConstraintEClass, CompositeConstraint.class, "CompositeConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCompositeConstraint_Operator(), theFiltersPackage.getOperatorKind(), "operator", null, 1, 1, CompositeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getCompositeConstraint_Constraint(), this.getAdviceConstraint(), this.getAdviceConstraint_Composite(), "constraint", null, 0, -1, CompositeConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(referenceConstraintEClass, ReferenceConstraint.class, "ReferenceConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReferenceConstraint_Permission(), this.getReferencePermission(), null, "permission", null, 1, -1, ReferenceConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, IS_ORDERED);

		initEClass(referencePermissionEClass, ReferencePermission.class, "ReferencePermission", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getReferencePermission_Permitted(), theTypesPackage.getBoolean(), "permitted", "true", 1, 1, ReferencePermission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		op = initEOperation(getReferencePermission__Matches__EReference(), theTypesPackage.getBoolean(), "matches", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, theEcorePackage.getEReference(), "reference", 1, 1, IS_UNIQUE, !IS_ORDERED);

		initEClass(anyReferenceEClass, AnyReference.class, "AnyReference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAnyReference_ReferenceKind(), this.getReferenceKind(), "referenceKind", null, 1, 1, AnyReference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(referenceEClass, Reference.class, "Reference", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getReference_Reference(), theEcorePackage.getEReference(), null, "reference", null, 1, 1, Reference.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				!IS_ORDERED);

		initEClass(elementTypeFilterEClass, ElementTypeFilter.class, "ElementTypeFilter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getElementTypeFilter_ElementType(), theElementTypesConfigurationsPackage.getElementTypeConfiguration(), null, "elementType", null, 1, 1, ElementTypeFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
				IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getElementTypeFilter_Relationship(), this.getElementTypeRelationshipKind(), "relationship", "subtype", 1, 1, ElementTypeFilter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED,
				!IS_ORDERED);

		initEClass(relationshipConstraintEClass, RelationshipConstraint.class, "RelationshipConstraint", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRelationshipConstraint_Permission(), this.getEndPermission(), null, "permission", null, 1, -1, RelationshipConstraint.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
				!IS_DERIVED, !IS_ORDERED);

		initEClass(endPermissionEClass, EndPermission.class, "EndPermission", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEndPermission_EndKind(), this.getEndKind(), "endKind", null, 1, 1, EndPermission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEAttribute(getEndPermission_Permitted(), theTypesPackage.getBoolean(), "permitted", "true", 1, 1, EndPermission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getEndPermission_EndFilter(), theFiltersPackage.getFilter(), null, "endFilter", null, 0, 1, EndPermission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED,
				!IS_ORDERED);

		op = initEOperation(getEndPermission__Matches__EObject(), theTypesPackage.getBoolean(), "matches", 1, 1, IS_UNIQUE, !IS_ORDERED);
		addEParameter(op, theEcorePackage.getEObject(), "end", 1, 1, IS_UNIQUE, !IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(referenceKindEEnum, ReferenceKind.class, "ReferenceKind");
		addEEnumLiteral(referenceKindEEnum, ReferenceKind.ANY);
		addEEnumLiteral(referenceKindEEnum, ReferenceKind.CONTAINMENT);
		addEEnumLiteral(referenceKindEEnum, ReferenceKind.CROSS);

		initEEnum(elementTypeRelationshipKindEEnum, ElementTypeRelationshipKind.class, "ElementTypeRelationshipKind");
		addEEnumLiteral(elementTypeRelationshipKindEEnum, ElementTypeRelationshipKind.EXACT_TYPE);
		addEEnumLiteral(elementTypeRelationshipKindEEnum, ElementTypeRelationshipKind.SPECIALIZATION_TYPE);
		addEEnumLiteral(elementTypeRelationshipKindEEnum, ElementTypeRelationshipKind.SUBTYPE);
		addEEnumLiteral(elementTypeRelationshipKindEEnum, ElementTypeRelationshipKind.SUPERTYPE);

		initEEnum(endKindEEnum, EndKind.class, "EndKind");
		addEEnumLiteral(endKindEEnum, EndKind.ALL);
		addEEnumLiteral(endKindEEnum, EndKind.SOURCE);
		addEEnumLiteral(endKindEEnum, EndKind.TARGET);

		// Initialize data types
		initEDataType(editCommandRequestEDataType, IEditCommandRequest.class, "EditCommandRequest", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/uml2/2.0.0/UML
		createUMLAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// duplicates
		createDuplicatesAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/uml2/2.0.0/UML</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createUMLAnnotations() {
		String source = "http://www.eclipse.org/uml2/2.0.0/UML";
		addAnnotation(this,
				source,
				new String[] {
						"originalName", "constraintAdvice"
				});
		addAnnotation(getAdviceConstraint__GetAdvice(),
				source,
				new String[] {
						"originalName", "advice"
				});
		addAnnotation(elementTypeRelationshipKindEEnum.getELiterals().get(0),
				source,
				new String[] {
						"originalName", "exact_type"
				});
		addAnnotation(elementTypeRelationshipKindEEnum.getELiterals().get(1),
				source,
				new String[] {
						"originalName", "specialization_type"
				});
	}

	/**
	 * Initializes the annotations for <b>duplicates</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createDuplicatesAnnotations() {
		String source = "duplicates";
		addAnnotation(compositeConstraintEClass,
				source,
				new String[] {
				});
		addAnnotation(referenceConstraintEClass,
				source,
				new String[] {
				});
		addAnnotation(anyReferenceEClass,
				source,
				new String[] {
				});
		addAnnotation(referenceEClass,
				source,
				new String[] {
				});
		addAnnotation(elementTypeFilterEClass,
				source,
				new String[] {
				});
		addAnnotation(relationshipConstraintEClass,
				source,
				new String[] {
				});
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";
		addAnnotation(compositeConstraintEClass,
				source,
				new String[] {
						"constraints", "operandCount"
				});
	}

} // ConstraintAdvicePackageImpl
