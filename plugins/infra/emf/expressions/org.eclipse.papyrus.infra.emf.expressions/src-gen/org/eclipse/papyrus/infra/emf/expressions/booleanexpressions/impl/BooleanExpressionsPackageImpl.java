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
package org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EGenericType;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.ETypeParameter;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.papyrus.infra.emf.expressions.ExpressionsPackage;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractMultiBooleanEObjectExpressionsReferenceExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionOwnedExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionReferenceExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbtractMultiBooleanEObjectExpressionsOwnedExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AndExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsFactory;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralFalseExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralTrueExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.NotExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.OrExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.ReferenceBooleanExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression;

import org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionsPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BooleanExpressionsPackageImpl extends EPackageImpl implements BooleanExpressionsPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass orExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abtractMultiBooleanEObjectExpressionsOwnedExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBooleanEObjectExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iBooleanExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractMultiBooleanEObjectExpressionsReferenceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass andExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass notExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractSingleBooleanEObjectExpressionOwnedExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass abstractSingleBooleanEObjectExpressionReferenceExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalTrueExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass literalFalseExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass referenceBooleanExpressionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass singleEAttributeValueEqualityExpressionEClass = null;

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
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BooleanExpressionsPackageImpl() {
		super(eNS_URI, BooleanExpressionsFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link BooleanExpressionsPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BooleanExpressionsPackage init() {
		if (isInited) return (BooleanExpressionsPackage)EPackage.Registry.INSTANCE.getEPackage(BooleanExpressionsPackage.eNS_URI);

		// Obtain or create and register package
		Object registeredBooleanExpressionsPackage = EPackage.Registry.INSTANCE.get(eNS_URI);
		BooleanExpressionsPackageImpl theBooleanExpressionsPackage = registeredBooleanExpressionsPackage instanceof BooleanExpressionsPackageImpl ? (BooleanExpressionsPackageImpl)registeredBooleanExpressionsPackage : new BooleanExpressionsPackageImpl();

		isInited = true;

		// Initialize simple dependencies
		EcorePackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		Object registeredPackage = EPackage.Registry.INSTANCE.getEPackage(ExpressionsPackage.eNS_URI);
		ExpressionsPackageImpl theExpressionsPackage = (ExpressionsPackageImpl)(registeredPackage instanceof ExpressionsPackageImpl ? registeredPackage : ExpressionsPackage.eINSTANCE);

		// Create package meta-data objects
		theBooleanExpressionsPackage.createPackageContents();
		theExpressionsPackage.createPackageContents();

		// Initialize created meta-data
		theBooleanExpressionsPackage.initializePackageContents();
		theExpressionsPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBooleanExpressionsPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BooleanExpressionsPackage.eNS_URI, theBooleanExpressionsPackage);
		return theBooleanExpressionsPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getOrExpression() {
		return orExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbtractMultiBooleanEObjectExpressionsOwnedExpression() {
		return abtractMultiBooleanEObjectExpressionsOwnedExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbtractMultiBooleanEObjectExpressionsOwnedExpression_OwnedExpressions() {
		return (EReference)abtractMultiBooleanEObjectExpressionsOwnedExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBooleanEObjectExpression() {
		return iBooleanEObjectExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getIBooleanExpression() {
		return iBooleanExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractMultiBooleanEObjectExpressionsReferenceExpression() {
		return abstractMultiBooleanEObjectExpressionsReferenceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractMultiBooleanEObjectExpressionsReferenceExpression_ReferencedExpressions() {
		return (EReference)abstractMultiBooleanEObjectExpressionsReferenceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAndExpression() {
		return andExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getNotExpression() {
		return notExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractSingleBooleanEObjectExpressionOwnedExpression() {
		return abstractSingleBooleanEObjectExpressionOwnedExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractSingleBooleanEObjectExpressionOwnedExpression_OwnedExpression() {
		return (EReference)abstractSingleBooleanEObjectExpressionOwnedExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getAbstractSingleBooleanEObjectExpressionReferenceExpression() {
		return abstractSingleBooleanEObjectExpressionReferenceExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getAbstractSingleBooleanEObjectExpressionReferenceExpression_ReferencedExpression() {
		return (EReference)abstractSingleBooleanEObjectExpressionReferenceExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLiteralTrueExpression() {
		return literalTrueExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getLiteralFalseExpression() {
		return literalFalseExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getReferenceBooleanExpression() {
		return referenceBooleanExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EClass getSingleEAttributeValueEqualityExpression() {
		return singleEAttributeValueEqualityExpressionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EReference getSingleEAttributeValueEqualityExpression_EAttribute() {
		return (EReference)singleEAttributeValueEqualityExpressionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EAttribute getSingleEAttributeValueEqualityExpression_ExpectedValue() {
		return (EAttribute)singleEAttributeValueEqualityExpressionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public BooleanExpressionsFactory getBooleanExpressionsFactory() {
		return (BooleanExpressionsFactory)getEFactoryInstance();
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
		orExpressionEClass = createEClass(OR_EXPRESSION);

		abtractMultiBooleanEObjectExpressionsOwnedExpressionEClass = createEClass(ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION);
		createEReference(abtractMultiBooleanEObjectExpressionsOwnedExpressionEClass, ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS);

		iBooleanEObjectExpressionEClass = createEClass(IBOOLEAN_EOBJECT_EXPRESSION);

		iBooleanExpressionEClass = createEClass(IBOOLEAN_EXPRESSION);

		abstractMultiBooleanEObjectExpressionsReferenceExpressionEClass = createEClass(ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION);
		createEReference(abstractMultiBooleanEObjectExpressionsReferenceExpressionEClass, ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__REFERENCED_EXPRESSIONS);

		andExpressionEClass = createEClass(AND_EXPRESSION);

		notExpressionEClass = createEClass(NOT_EXPRESSION);

		abstractSingleBooleanEObjectExpressionOwnedExpressionEClass = createEClass(ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION);
		createEReference(abstractSingleBooleanEObjectExpressionOwnedExpressionEClass, ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION__OWNED_EXPRESSION);

		abstractSingleBooleanEObjectExpressionReferenceExpressionEClass = createEClass(ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION);
		createEReference(abstractSingleBooleanEObjectExpressionReferenceExpressionEClass, ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION__REFERENCED_EXPRESSION);

		literalTrueExpressionEClass = createEClass(LITERAL_TRUE_EXPRESSION);

		literalFalseExpressionEClass = createEClass(LITERAL_FALSE_EXPRESSION);

		referenceBooleanExpressionEClass = createEClass(REFERENCE_BOOLEAN_EXPRESSION);

		singleEAttributeValueEqualityExpressionEClass = createEClass(SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION);
		createEReference(singleEAttributeValueEqualityExpressionEClass, SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION__EATTRIBUTE);
		createEAttribute(singleEAttributeValueEqualityExpressionEClass, SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION__EXPECTED_VALUE);
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
		EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);
		ExpressionsPackage theExpressionsPackage = (ExpressionsPackage)EPackage.Registry.INSTANCE.getEPackage(ExpressionsPackage.eNS_URI);

		// Create type parameters
		ETypeParameter iBooleanExpressionEClass_IBooleanExpression_REDEFINED_CONTEXT_TYPE = addETypeParameter(iBooleanExpressionEClass, "IBooleanExpression_REDEFINED_CONTEXT_TYPE"); //$NON-NLS-1$

		// Set bounds for type parameters

		// Add supertypes to classes
		orExpressionEClass.getESuperTypes().add(this.getAbstractMultiBooleanEObjectExpressionsReferenceExpression());
		orExpressionEClass.getESuperTypes().add(this.getAbtractMultiBooleanEObjectExpressionsOwnedExpression());
		abtractMultiBooleanEObjectExpressionsOwnedExpressionEClass.getESuperTypes().add(this.getIBooleanEObjectExpression());
		EGenericType g1 = createEGenericType(this.getIBooleanExpression());
		EGenericType g2 = createEGenericType(theEcorePackage.getEObject());
		g1.getETypeArguments().add(g2);
		iBooleanEObjectExpressionEClass.getEGenericSuperTypes().add(g1);
		g1 = createEGenericType(theExpressionsPackage.getIExpression());
		g2 = createEGenericType(iBooleanExpressionEClass_IBooleanExpression_REDEFINED_CONTEXT_TYPE);
		g1.getETypeArguments().add(g2);
		g2 = createEGenericType(ecorePackage.getEBooleanObject());
		g1.getETypeArguments().add(g2);
		iBooleanExpressionEClass.getEGenericSuperTypes().add(g1);
		abstractMultiBooleanEObjectExpressionsReferenceExpressionEClass.getESuperTypes().add(this.getIBooleanEObjectExpression());
		andExpressionEClass.getESuperTypes().add(this.getAbstractMultiBooleanEObjectExpressionsReferenceExpression());
		andExpressionEClass.getESuperTypes().add(this.getAbtractMultiBooleanEObjectExpressionsOwnedExpression());
		notExpressionEClass.getESuperTypes().add(this.getAbstractSingleBooleanEObjectExpressionOwnedExpression());
		notExpressionEClass.getESuperTypes().add(this.getAbstractSingleBooleanEObjectExpressionReferenceExpression());
		abstractSingleBooleanEObjectExpressionOwnedExpressionEClass.getESuperTypes().add(this.getIBooleanEObjectExpression());
		abstractSingleBooleanEObjectExpressionReferenceExpressionEClass.getESuperTypes().add(this.getIBooleanEObjectExpression());
		literalTrueExpressionEClass.getESuperTypes().add(this.getIBooleanEObjectExpression());
		literalFalseExpressionEClass.getESuperTypes().add(this.getIBooleanEObjectExpression());
		referenceBooleanExpressionEClass.getESuperTypes().add(this.getAbstractSingleBooleanEObjectExpressionReferenceExpression());
		singleEAttributeValueEqualityExpressionEClass.getESuperTypes().add(this.getIBooleanEObjectExpression());

		// Initialize classes, features, and operations; add parameters
		initEClass(orExpressionEClass, OrExpression.class, "OrExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(abtractMultiBooleanEObjectExpressionsOwnedExpressionEClass, AbtractMultiBooleanEObjectExpressionsOwnedExpression.class, "AbtractMultiBooleanEObjectExpressionsOwnedExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAbtractMultiBooleanEObjectExpressionsOwnedExpression_OwnedExpressions(), this.getIBooleanEObjectExpression(), null, "ownedExpressions", null, 0, -1, AbtractMultiBooleanEObjectExpressionsOwnedExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(iBooleanEObjectExpressionEClass, IBooleanEObjectExpression.class, "IBooleanEObjectExpression", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(iBooleanExpressionEClass, IBooleanExpression.class, "IBooleanExpression", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(abstractMultiBooleanEObjectExpressionsReferenceExpressionEClass, AbstractMultiBooleanEObjectExpressionsReferenceExpression.class, "AbstractMultiBooleanEObjectExpressionsReferenceExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAbstractMultiBooleanEObjectExpressionsReferenceExpression_ReferencedExpressions(), this.getIBooleanEObjectExpression(), null, "referencedExpressions", null, 0, -1, AbstractMultiBooleanEObjectExpressionsReferenceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(andExpressionEClass, AndExpression.class, "AndExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(notExpressionEClass, NotExpression.class, "NotExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(abstractSingleBooleanEObjectExpressionOwnedExpressionEClass, AbstractSingleBooleanEObjectExpressionOwnedExpression.class, "AbstractSingleBooleanEObjectExpressionOwnedExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAbstractSingleBooleanEObjectExpressionOwnedExpression_OwnedExpression(), this.getIBooleanEObjectExpression(), null, "ownedExpression", null, 0, 1, AbstractSingleBooleanEObjectExpressionOwnedExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(abstractSingleBooleanEObjectExpressionReferenceExpressionEClass, AbstractSingleBooleanEObjectExpressionReferenceExpression.class, "AbstractSingleBooleanEObjectExpressionReferenceExpression", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getAbstractSingleBooleanEObjectExpressionReferenceExpression_ReferencedExpression(), this.getIBooleanEObjectExpression(), null, "referencedExpression", null, 0, 1, AbstractSingleBooleanEObjectExpressionReferenceExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		initEClass(literalTrueExpressionEClass, LiteralTrueExpression.class, "LiteralTrueExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(literalFalseExpressionEClass, LiteralFalseExpression.class, "LiteralFalseExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(referenceBooleanExpressionEClass, ReferenceBooleanExpression.class, "ReferenceBooleanExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(singleEAttributeValueEqualityExpressionEClass, SingleEAttributeValueEqualityExpression.class, "SingleEAttributeValueEqualityExpression", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(getSingleEAttributeValueEqualityExpression_EAttribute(), theEcorePackage.getEAttribute(), null, "eAttribute", null, 0, 1, SingleEAttributeValueEqualityExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$
		initEAttribute(getSingleEAttributeValueEqualityExpression_ExpectedValue(), ecorePackage.getEString(), "expectedValue", null, 0, 1, SingleEAttributeValueEqualityExpression.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED); //$NON-NLS-1$

		// Create annotations
		// http://www.eclipse.org/uml2/2.0.0/UML
		createUMLAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/uml2/2.0.0/UML</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	protected void createUMLAnnotations() {
		String source = "http://www.eclipse.org/uml2/2.0.0/UML"; //$NON-NLS-1$
		addAnnotation
		  (this,
		   source,
		   new String[] {
			   "originalName", "BooleanExpressions" //$NON-NLS-1$ //$NON-NLS-2$
		   });
//		addAnnotation
//		  (null,
//		   source,
//		   new String[] {
//			   "originalName", "REDEFINED_CONTEXT_TYPE" //$NON-NLS-1$ //$NON-NLS-2$
//		   });
	}

} //BooleanExpressionsPackageImpl
