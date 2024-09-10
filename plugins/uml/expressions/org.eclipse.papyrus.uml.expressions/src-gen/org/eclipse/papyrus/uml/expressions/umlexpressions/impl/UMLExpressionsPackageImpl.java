/**
 * Copyright (c) 2017 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.uml.expressions.umlexpressions.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.emf.expressions.ExpressionsPackage;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;

import org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractUMLEClassExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.HasAppliedStereotypesExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfStereotypeExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsStereotypedWithExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfStereotypeExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsFactory;
import org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UMLExpressionsPackageImpl extends EPackageImpl implements UMLExpressionsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass isStereotypedWithExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractStereotypeExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass hasAppliedStereotypesExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass isTypeOfExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractUMLEClassExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass isKindOfExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass isKindOfStereotypeExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass isTypeOfStereotypeExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass singleStereotypeAttributeEqualityExpressionEClass = null;

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
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UMLExpressionsPackageImpl() {
		super(eNS_URI, UMLExpressionsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link UMLExpressionsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UMLExpressionsPackage init() {
		if (isInited) return (UMLExpressionsPackage)EPackage.Registry.INSTANCE.getEPackage(UMLExpressionsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredUMLExpressionsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		UMLExpressionsPackageImpl theUMLExpressionsPackage = registeredUMLExpressionsPackage instanceof UMLExpressionsPackageImpl ? (UMLExpressionsPackageImpl)registeredUMLExpressionsPackage : new UMLExpressionsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		ExpressionsPackage.eINSTANCE.eClass();
		EcorePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theUMLExpressionsPackage.createPackageContents();

		// Initialize created meta-data
		theUMLExpressionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUMLExpressionsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UMLExpressionsPackage.eNS_URI, theUMLExpressionsPackage);
		return theUMLExpressionsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIsStereotypedWithExpression() {
		return isStereotypedWithExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractStereotypeExpression() {
		return abstractStereotypeExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractStereotypeExpression_StereotypeQualifiedName() {
		return (EAttribute)abstractStereotypeExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getAbstractStereotypeExpression_ProfileURI() {
		return (EAttribute)abstractStereotypeExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getHasAppliedStereotypesExpression() {
		return hasAppliedStereotypesExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIsTypeOfExpression() {
		return isTypeOfExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractUMLEClassExpression() {
		return abstractUMLEClassExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractUMLEClassExpression_UmlEClass() {
		return (EReference)abstractUMLEClassExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIsKindOfExpression() {
		return isKindOfExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIsKindOfStereotypeExpression() {
		return isKindOfStereotypeExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIsTypeOfStereotypeExpression() {
		return isTypeOfStereotypeExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSingleStereotypeAttributeEqualityExpression() {
		return singleStereotypeAttributeEqualityExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSingleStereotypeAttributeEqualityExpression_ExpectedValue() {
		return (EAttribute)singleStereotypeAttributeEqualityExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSingleStereotypeAttributeEqualityExpression_PropertyName() {
		return (EAttribute)singleStereotypeAttributeEqualityExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public UMLExpressionsFactory getUMLExpressionsFactory() {
		return (UMLExpressionsFactory)getEFactoryInstance();
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
		isStereotypedWithExpressionEClass = createEClass(IS_STEREOTYPED_WITH_EXPRESSION);

		abstractStereotypeExpressionEClass = createEClass(ABSTRACT_STEREOTYPE_EXPRESSION);
		createEAttribute(abstractStereotypeExpressionEClass, ABSTRACT_STEREOTYPE_EXPRESSION__STEREOTYPE_QUALIFIED_NAME);
		createEAttribute(abstractStereotypeExpressionEClass, ABSTRACT_STEREOTYPE_EXPRESSION__PROFILE_URI);

		hasAppliedStereotypesExpressionEClass = createEClass(HAS_APPLIED_STEREOTYPES_EXPRESSION);

		isTypeOfExpressionEClass = createEClass(IS_TYPE_OF_EXPRESSION);

		abstractUMLEClassExpressionEClass = createEClass(ABSTRACT_UMLE_CLASS_EXPRESSION);
		createEReference(abstractUMLEClassExpressionEClass, ABSTRACT_UMLE_CLASS_EXPRESSION__UML_ECLASS);

		isKindOfExpressionEClass = createEClass(IS_KIND_OF_EXPRESSION);

		isKindOfStereotypeExpressionEClass = createEClass(IS_KIND_OF_STEREOTYPE_EXPRESSION);

		isTypeOfStereotypeExpressionEClass = createEClass(IS_TYPE_OF_STEREOTYPE_EXPRESSION);

		singleStereotypeAttributeEqualityExpressionEClass = createEClass(SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION);
		createEAttribute(singleStereotypeAttributeEqualityExpressionEClass, SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION__EXPECTED_VALUE);
		createEAttribute(singleStereotypeAttributeEqualityExpressionEClass, SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION__PROPERTY_NAME);
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
		BooleanExpressionsPackage theBooleanExpressionsPackage = (BooleanExpressionsPackage)EPackage.Registry.INSTANCE.getEPackage(BooleanExpressionsPackage.eNS_URI);
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		isStereotypedWithExpressionEClass.getESuperTypes().add(this.getAbstractStereotypeExpression());
		abstractStereotypeExpressionEClass.getESuperTypes().add(theBooleanExpressionsPackage.getIBooleanEObjectExpression());
		hasAppliedStereotypesExpressionEClass.getESuperTypes().add(theBooleanExpressionsPackage.getIBooleanEObjectExpression());
		isTypeOfExpressionEClass.getESuperTypes().add(this.getAbstractUMLEClassExpression());
		abstractUMLEClassExpressionEClass.getESuperTypes().add(theBooleanExpressionsPackage.getIBooleanEObjectExpression());
		isKindOfExpressionEClass.getESuperTypes().add(this.getAbstractUMLEClassExpression());
		isKindOfStereotypeExpressionEClass.getESuperTypes().add(this.getAbstractStereotypeExpression());
		isTypeOfStereotypeExpressionEClass.getESuperTypes().add(this.getAbstractStereotypeExpression());
		singleStereotypeAttributeEqualityExpressionEClass.getESuperTypes().add(this.getAbstractStereotypeExpression());

		// Initialize classes, features, and operations; add parameters
		initEClass(isStereotypedWithExpressionEClass, IsStereotypedWithExpression.class, "IsStereotypedWithExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(abstractStereotypeExpressionEClass, AbstractStereotypeExpression.class, "AbstractStereotypeExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getAbstractStereotypeExpression_StereotypeQualifiedName(), ecorePackage.getEString(), "stereotypeQualifiedName", null, 0, 1, AbstractStereotypeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getAbstractStereotypeExpression_ProfileURI(), ecorePackage.getEString(), "profileURI", null, 0, 1, AbstractStereotypeExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(hasAppliedStereotypesExpressionEClass, HasAppliedStereotypesExpression.class, "HasAppliedStereotypesExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(isTypeOfExpressionEClass, IsTypeOfExpression.class, "IsTypeOfExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(abstractUMLEClassExpressionEClass, AbstractUMLEClassExpression.class, "AbstractUMLEClassExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAbstractUMLEClassExpression_UmlEClass(), theEcorePackage.getEClass(), null, "umlEClass", null, 0, 1, AbstractUMLEClassExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(isKindOfExpressionEClass, IsKindOfExpression.class, "IsKindOfExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(isKindOfStereotypeExpressionEClass, IsKindOfStereotypeExpression.class, "IsKindOfStereotypeExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(isTypeOfStereotypeExpressionEClass, IsTypeOfStereotypeExpression.class, "IsTypeOfStereotypeExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(singleStereotypeAttributeEqualityExpressionEClass, SingleStereotypeAttributeEqualityExpression.class, "SingleStereotypeAttributeEqualityExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(getSingleStereotypeAttributeEqualityExpression_ExpectedValue(), ecorePackage.getEString(), "expectedValue", null, 0, 1, SingleStereotypeAttributeEqualityExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSingleStereotypeAttributeEqualityExpression_PropertyName(), ecorePackage.getEString(), "propertyName", null, 0, 1, SingleStereotypeAttributeEqualityExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://www.eclipse.org/uml2/2.0.0/UML
		createUMLAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/uml2/2.0.0/UML</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createUMLAnnotations() {
		String source = "http://www.eclipse.org/uml2/2.0.0/UML"; //$NON-NLS-1$
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "originalName", "UMLExpressions" //$NON-NLS-1$ //$NON-NLS-2$
		   });
	}

} //UMLExpressionsPackageImpl
