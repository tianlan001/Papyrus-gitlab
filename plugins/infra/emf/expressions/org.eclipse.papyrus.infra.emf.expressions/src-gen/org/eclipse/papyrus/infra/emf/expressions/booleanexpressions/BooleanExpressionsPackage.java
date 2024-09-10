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
package org.eclipse.papyrus.infra.emf.expressions.booleanexpressions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.papyrus.infra.emf.expressions.ExpressionsPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/uml2/2.0.0/UML originalName='BooleanExpressions'"
 * @generated
 */
public interface BooleanExpressionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "booleanexpressions"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/expressions/booleanexpressions"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "booleanexpressions"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BooleanExpressionsPackage eINSTANCE = org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression <em>IBoolean Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getIBooleanExpression()
	 * @generated
	 */
	int IBOOLEAN_EXPRESSION = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBOOLEAN_EXPRESSION__NAME = ExpressionsPackage.IEXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBOOLEAN_EXPRESSION__DESCRIPTION = ExpressionsPackage.IEXPRESSION__DESCRIPTION;

	/**
	 * The number of structural features of the '<em>IBoolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBOOLEAN_EXPRESSION_FEATURE_COUNT = ExpressionsPackage.IEXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBOOLEAN_EXPRESSION___EVALUATE__OBJECT = ExpressionsPackage.IEXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>IBoolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBOOLEAN_EXPRESSION_OPERATION_COUNT = ExpressionsPackage.IEXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression <em>IBoolean EObject Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getIBooleanEObjectExpression()
	 * @generated
	 */
	int IBOOLEAN_EOBJECT_EXPRESSION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBOOLEAN_EOBJECT_EXPRESSION__NAME = IBOOLEAN_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION = IBOOLEAN_EXPRESSION__DESCRIPTION;

	/**
	 * The number of structural features of the '<em>IBoolean EObject Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT = IBOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT = IBOOLEAN_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>IBoolean EObject Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT = IBOOLEAN_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractMultiBooleanEObjectExpressionsReferenceExpressionImpl <em>Abstract Multi Boolean EObject Expressions Reference Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractMultiBooleanEObjectExpressionsReferenceExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getAbstractMultiBooleanEObjectExpressionsReferenceExpression()
	 * @generated
	 */
	int ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__NAME = IBOOLEAN_EOBJECT_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__DESCRIPTION = IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Referenced Expressions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__REFERENCED_EXPRESSIONS = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Multi Boolean EObject Expressions Reference Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION_FEATURE_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION___EVALUATE__OBJECT = IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Abstract Multi Boolean EObject Expressions Reference Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION_OPERATION_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.OrExpressionImpl <em>Or Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.OrExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getOrExpression()
	 * @generated
	 */
	int OR_EXPRESSION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION__NAME = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION__DESCRIPTION = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Referenced Expressions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION__REFERENCED_EXPRESSIONS = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__REFERENCED_EXPRESSIONS;

	/**
	 * The feature id for the '<em><b>Owned Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION__OWNED_EXPRESSIONS = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Or Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION_FEATURE_COUNT = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION___EVALUATE__OBJECT = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Or Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OR_EXPRESSION_OPERATION_COUNT = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbtractMultiBooleanEObjectExpressionsOwnedExpressionImpl <em>Abtract Multi Boolean EObject Expressions Owned Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbtractMultiBooleanEObjectExpressionsOwnedExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getAbtractMultiBooleanEObjectExpressionsOwnedExpression()
	 * @generated
	 */
	int ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__NAME = IBOOLEAN_EOBJECT_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__DESCRIPTION = IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owned Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abtract Multi Boolean EObject Expressions Owned Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION_FEATURE_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION___EVALUATE__OBJECT = IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Abtract Multi Boolean EObject Expressions Owned Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION_OPERATION_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AndExpressionImpl <em>And Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AndExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getAndExpression()
	 * @generated
	 */
	int AND_EXPRESSION = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION__NAME = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION__DESCRIPTION = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Referenced Expressions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION__REFERENCED_EXPRESSIONS = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__REFERENCED_EXPRESSIONS;

	/**
	 * The feature id for the '<em><b>Owned Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION__OWNED_EXPRESSIONS = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>And Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION_FEATURE_COUNT = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION___EVALUATE__OBJECT = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>And Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AND_EXPRESSION_OPERATION_COUNT = ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractSingleBooleanEObjectExpressionOwnedExpressionImpl <em>Abstract Single Boolean EObject Expression Owned Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractSingleBooleanEObjectExpressionOwnedExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getAbstractSingleBooleanEObjectExpressionOwnedExpression()
	 * @generated
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION__NAME = IBOOLEAN_EOBJECT_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION__DESCRIPTION = IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owned Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION__OWNED_EXPRESSION = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Single Boolean EObject Expression Owned Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION_FEATURE_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION___EVALUATE__OBJECT = IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Abstract Single Boolean EObject Expression Owned Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION_OPERATION_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.NotExpressionImpl <em>Not Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.NotExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getNotExpression()
	 * @generated
	 */
	int NOT_EXPRESSION = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_EXPRESSION__NAME = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_EXPRESSION__DESCRIPTION = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Owned Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_EXPRESSION__OWNED_EXPRESSION = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION__OWNED_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Referenced Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_EXPRESSION__REFERENCED_EXPRESSION = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Not Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_EXPRESSION_FEATURE_COUNT = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_EXPRESSION___EVALUATE__OBJECT = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Not Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NOT_EXPRESSION_OPERATION_COUNT = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractSingleBooleanEObjectExpressionReferenceExpressionImpl <em>Abstract Single Boolean EObject Expression Reference Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractSingleBooleanEObjectExpressionReferenceExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getAbstractSingleBooleanEObjectExpressionReferenceExpression()
	 * @generated
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION__NAME = IBOOLEAN_EOBJECT_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION__DESCRIPTION = IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Referenced Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION__REFERENCED_EXPRESSION = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract Single Boolean EObject Expression Reference Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION_FEATURE_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION___EVALUATE__OBJECT = IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Abstract Single Boolean EObject Expression Reference Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION_OPERATION_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.LiteralTrueExpressionImpl <em>Literal True Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.LiteralTrueExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getLiteralTrueExpression()
	 * @generated
	 */
	int LITERAL_TRUE_EXPRESSION = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_TRUE_EXPRESSION__NAME = IBOOLEAN_EOBJECT_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_TRUE_EXPRESSION__DESCRIPTION = IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION;

	/**
	 * The number of structural features of the '<em>Literal True Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_TRUE_EXPRESSION_FEATURE_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_TRUE_EXPRESSION___EVALUATE__OBJECT = IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Literal True Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_TRUE_EXPRESSION_OPERATION_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.LiteralFalseExpressionImpl <em>Literal False Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.LiteralFalseExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getLiteralFalseExpression()
	 * @generated
	 */
	int LITERAL_FALSE_EXPRESSION = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_FALSE_EXPRESSION__NAME = IBOOLEAN_EOBJECT_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_FALSE_EXPRESSION__DESCRIPTION = IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION;

	/**
	 * The number of structural features of the '<em>Literal False Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_FALSE_EXPRESSION_FEATURE_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_FALSE_EXPRESSION___EVALUATE__OBJECT = IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Literal False Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LITERAL_FALSE_EXPRESSION_OPERATION_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.ReferenceBooleanExpressionImpl <em>Reference Boolean Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.ReferenceBooleanExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getReferenceBooleanExpression()
	 * @generated
	 */
	int REFERENCE_BOOLEAN_EXPRESSION = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_BOOLEAN_EXPRESSION__NAME = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_BOOLEAN_EXPRESSION__DESCRIPTION = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Referenced Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_BOOLEAN_EXPRESSION__REFERENCED_EXPRESSION = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION__REFERENCED_EXPRESSION;

	/**
	 * The number of structural features of the '<em>Reference Boolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_BOOLEAN_EXPRESSION_FEATURE_COUNT = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_BOOLEAN_EXPRESSION___EVALUATE__OBJECT = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Reference Boolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REFERENCE_BOOLEAN_EXPRESSION_OPERATION_COUNT = ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.SingleEAttributeValueEqualityExpressionImpl <em>Single EAttribute Value Equality Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.SingleEAttributeValueEqualityExpressionImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getSingleEAttributeValueEqualityExpression()
	 * @generated
	 */
	int SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION__NAME = IBOOLEAN_EOBJECT_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION__DESCRIPTION = IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>EAttribute</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION__EATTRIBUTE = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expected Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION__EXPECTED_VALUE = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Single EAttribute Value Equality Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION_FEATURE_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION___EVALUATE__OBJECT = IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Single EAttribute Value Equality Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION_OPERATION_COUNT = IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.OrExpression <em>Or Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Or Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.OrExpression
	 * @generated
	 */
	EClass getOrExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbtractMultiBooleanEObjectExpressionsOwnedExpression <em>Abtract Multi Boolean EObject Expressions Owned Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abtract Multi Boolean EObject Expressions Owned Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbtractMultiBooleanEObjectExpressionsOwnedExpression
	 * @generated
	 */
	EClass getAbtractMultiBooleanEObjectExpressionsOwnedExpression();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbtractMultiBooleanEObjectExpressionsOwnedExpression#getOwnedExpressions <em>Owned Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Owned Expressions</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbtractMultiBooleanEObjectExpressionsOwnedExpression#getOwnedExpressions()
	 * @see #getAbtractMultiBooleanEObjectExpressionsOwnedExpression()
	 * @generated
	 */
	EReference getAbtractMultiBooleanEObjectExpressionsOwnedExpression_OwnedExpressions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression <em>IBoolean EObject Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBoolean EObject Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression
	 * @generated
	 */
	EClass getIBooleanEObjectExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression <em>IBoolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBoolean Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression
	 * @generated
	 */
	EClass getIBooleanExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractMultiBooleanEObjectExpressionsReferenceExpression <em>Abstract Multi Boolean EObject Expressions Reference Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Multi Boolean EObject Expressions Reference Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractMultiBooleanEObjectExpressionsReferenceExpression
	 * @generated
	 */
	EClass getAbstractMultiBooleanEObjectExpressionsReferenceExpression();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractMultiBooleanEObjectExpressionsReferenceExpression#getReferencedExpressions <em>Referenced Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Referenced Expressions</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractMultiBooleanEObjectExpressionsReferenceExpression#getReferencedExpressions()
	 * @see #getAbstractMultiBooleanEObjectExpressionsReferenceExpression()
	 * @generated
	 */
	EReference getAbstractMultiBooleanEObjectExpressionsReferenceExpression_ReferencedExpressions();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AndExpression <em>And Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>And Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AndExpression
	 * @generated
	 */
	EClass getAndExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.NotExpression <em>Not Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Not Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.NotExpression
	 * @generated
	 */
	EClass getNotExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionOwnedExpression <em>Abstract Single Boolean EObject Expression Owned Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Single Boolean EObject Expression Owned Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionOwnedExpression
	 * @generated
	 */
	EClass getAbstractSingleBooleanEObjectExpressionOwnedExpression();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionOwnedExpression#getOwnedExpression <em>Owned Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Owned Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionOwnedExpression#getOwnedExpression()
	 * @see #getAbstractSingleBooleanEObjectExpressionOwnedExpression()
	 * @generated
	 */
	EReference getAbstractSingleBooleanEObjectExpressionOwnedExpression_OwnedExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionReferenceExpression <em>Abstract Single Boolean EObject Expression Reference Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Single Boolean EObject Expression Reference Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionReferenceExpression
	 * @generated
	 */
	EClass getAbstractSingleBooleanEObjectExpressionReferenceExpression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionReferenceExpression#getReferencedExpression <em>Referenced Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Referenced Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionReferenceExpression#getReferencedExpression()
	 * @see #getAbstractSingleBooleanEObjectExpressionReferenceExpression()
	 * @generated
	 */
	EReference getAbstractSingleBooleanEObjectExpressionReferenceExpression_ReferencedExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralTrueExpression <em>Literal True Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Literal True Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralTrueExpression
	 * @generated
	 */
	EClass getLiteralTrueExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralFalseExpression <em>Literal False Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Literal False Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralFalseExpression
	 * @generated
	 */
	EClass getLiteralFalseExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.ReferenceBooleanExpression <em>Reference Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Reference Boolean Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.ReferenceBooleanExpression
	 * @generated
	 */
	EClass getReferenceBooleanExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression <em>Single EAttribute Value Equality Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Single EAttribute Value Equality Expression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression
	 * @generated
	 */
	EClass getSingleEAttributeValueEqualityExpression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression#getEAttribute <em>EAttribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>EAttribute</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression#getEAttribute()
	 * @see #getSingleEAttributeValueEqualityExpression()
	 * @generated
	 */
	EReference getSingleEAttributeValueEqualityExpression_EAttribute();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression#getExpectedValue <em>Expected Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expected Value</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression#getExpectedValue()
	 * @see #getSingleEAttributeValueEqualityExpression()
	 * @generated
	 */
	EAttribute getSingleEAttributeValueEqualityExpression_ExpectedValue();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BooleanExpressionsFactory getBooleanExpressionsFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.OrExpressionImpl <em>Or Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.OrExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getOrExpression()
		 * @generated
		 */
		EClass OR_EXPRESSION = eINSTANCE.getOrExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbtractMultiBooleanEObjectExpressionsOwnedExpressionImpl <em>Abtract Multi Boolean EObject Expressions Owned Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbtractMultiBooleanEObjectExpressionsOwnedExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getAbtractMultiBooleanEObjectExpressionsOwnedExpression()
		 * @generated
		 */
		EClass ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION = eINSTANCE.getAbtractMultiBooleanEObjectExpressionsOwnedExpression();

		/**
		 * The meta object literal for the '<em><b>Owned Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS = eINSTANCE.getAbtractMultiBooleanEObjectExpressionsOwnedExpression_OwnedExpressions();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression <em>IBoolean EObject Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getIBooleanEObjectExpression()
		 * @generated
		 */
		EClass IBOOLEAN_EOBJECT_EXPRESSION = eINSTANCE.getIBooleanEObjectExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression <em>IBoolean Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getIBooleanExpression()
		 * @generated
		 */
		EClass IBOOLEAN_EXPRESSION = eINSTANCE.getIBooleanExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractMultiBooleanEObjectExpressionsReferenceExpressionImpl <em>Abstract Multi Boolean EObject Expressions Reference Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractMultiBooleanEObjectExpressionsReferenceExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getAbstractMultiBooleanEObjectExpressionsReferenceExpression()
		 * @generated
		 */
		EClass ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION = eINSTANCE.getAbstractMultiBooleanEObjectExpressionsReferenceExpression();

		/**
		 * The meta object literal for the '<em><b>Referenced Expressions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION__REFERENCED_EXPRESSIONS = eINSTANCE.getAbstractMultiBooleanEObjectExpressionsReferenceExpression_ReferencedExpressions();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AndExpressionImpl <em>And Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AndExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getAndExpression()
		 * @generated
		 */
		EClass AND_EXPRESSION = eINSTANCE.getAndExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.NotExpressionImpl <em>Not Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.NotExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getNotExpression()
		 * @generated
		 */
		EClass NOT_EXPRESSION = eINSTANCE.getNotExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractSingleBooleanEObjectExpressionOwnedExpressionImpl <em>Abstract Single Boolean EObject Expression Owned Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractSingleBooleanEObjectExpressionOwnedExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getAbstractSingleBooleanEObjectExpressionOwnedExpression()
		 * @generated
		 */
		EClass ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION = eINSTANCE.getAbstractSingleBooleanEObjectExpressionOwnedExpression();

		/**
		 * The meta object literal for the '<em><b>Owned Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION__OWNED_EXPRESSION = eINSTANCE.getAbstractSingleBooleanEObjectExpressionOwnedExpression_OwnedExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractSingleBooleanEObjectExpressionReferenceExpressionImpl <em>Abstract Single Boolean EObject Expression Reference Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbstractSingleBooleanEObjectExpressionReferenceExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getAbstractSingleBooleanEObjectExpressionReferenceExpression()
		 * @generated
		 */
		EClass ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION = eINSTANCE.getAbstractSingleBooleanEObjectExpressionReferenceExpression();

		/**
		 * The meta object literal for the '<em><b>Referenced Expression</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION__REFERENCED_EXPRESSION = eINSTANCE.getAbstractSingleBooleanEObjectExpressionReferenceExpression_ReferencedExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.LiteralTrueExpressionImpl <em>Literal True Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.LiteralTrueExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getLiteralTrueExpression()
		 * @generated
		 */
		EClass LITERAL_TRUE_EXPRESSION = eINSTANCE.getLiteralTrueExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.LiteralFalseExpressionImpl <em>Literal False Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.LiteralFalseExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getLiteralFalseExpression()
		 * @generated
		 */
		EClass LITERAL_FALSE_EXPRESSION = eINSTANCE.getLiteralFalseExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.ReferenceBooleanExpressionImpl <em>Reference Boolean Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.ReferenceBooleanExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getReferenceBooleanExpression()
		 * @generated
		 */
		EClass REFERENCE_BOOLEAN_EXPRESSION = eINSTANCE.getReferenceBooleanExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.SingleEAttributeValueEqualityExpressionImpl <em>Single EAttribute Value Equality Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.SingleEAttributeValueEqualityExpressionImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.BooleanExpressionsPackageImpl#getSingleEAttributeValueEqualityExpression()
		 * @generated
		 */
		EClass SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION = eINSTANCE.getSingleEAttributeValueEqualityExpression();

		/**
		 * The meta object literal for the '<em><b>EAttribute</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION__EATTRIBUTE = eINSTANCE.getSingleEAttributeValueEqualityExpression_EAttribute();

		/**
		 * The meta object literal for the '<em><b>Expected Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION__EXPECTED_VALUE = eINSTANCE.getSingleEAttributeValueEqualityExpression_ExpectedValue();

	}

} //BooleanExpressionsPackage
