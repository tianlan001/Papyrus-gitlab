/**
 * Copyright (c) 2017 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.emf.expressions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * <!-- begin-model-doc -->
 * We don';t use the UML Primitives Types, because using it, the generated Ecore model has a dependency on Types.ecore and the generated code has a dependency on uml.
 * 
 * <!-- end-model-doc -->
 * @see org.eclipse.papyrus.infra.emf.expressions.ExpressionsFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/uml2/2.0.0/UML originalName='Expressions'"
 * @generated
 */
public interface ExpressionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "expressions"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/expressions"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "expressions"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ExpressionsPackage eINSTANCE = org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement <em>IBasic Expression Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement
	 * @see org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionsPackageImpl#getIBasicExpressionElement()
	 * @generated
	 */
	int IBASIC_EXPRESSION_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBASIC_EXPRESSION_ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBASIC_EXPRESSION_ELEMENT__DESCRIPTION = 1;

	/**
	 * The number of structural features of the '<em>IBasic Expression Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBASIC_EXPRESSION_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>IBasic Expression Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IBASIC_EXPRESSION_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.IExpression <em>IExpression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.IExpression
	 * @see org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionsPackageImpl#getIExpression()
	 * @generated
	 */
	int IEXPRESSION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXPRESSION__NAME = IBASIC_EXPRESSION_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXPRESSION__DESCRIPTION = IBASIC_EXPRESSION_ELEMENT__DESCRIPTION;

	/**
	 * The number of structural features of the '<em>IExpression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXPRESSION_FEATURE_COUNT = IBASIC_EXPRESSION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXPRESSION___EVALUATE__OBJECT = IBASIC_EXPRESSION_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>IExpression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXPRESSION_OPERATION_COUNT = IBASIC_EXPRESSION_ELEMENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionCatalogImpl <em>Expression Catalog</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionCatalogImpl
	 * @see org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionsPackageImpl#getExpressionCatalog()
	 * @generated
	 */
	int EXPRESSION_CATALOG = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_CATALOG__NAME = IBASIC_EXPRESSION_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_CATALOG__DESCRIPTION = IBASIC_EXPRESSION_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_CATALOG__EXPRESSIONS = IBASIC_EXPRESSION_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expression Catalog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_CATALOG_FEATURE_COUNT = IBASIC_EXPRESSION_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Expression Catalog</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_CATALOG_OPERATION_COUNT = IBASIC_EXPRESSION_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement <em>IBasic Expression Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IBasic Expression Element</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement
	 * @generated
	 */
	EClass getIBasicExpressionElement();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement#getName()
	 * @see #getIBasicExpressionElement()
	 * @generated
	 */
	EAttribute getIBasicExpressionElement_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement#getDescription()
	 * @see #getIBasicExpressionElement()
	 * @generated
	 */
	EAttribute getIBasicExpressionElement_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.IExpression <em>IExpression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IExpression</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.IExpression
	 * @generated
	 */
	EClass getIExpression();

	/**
	 * Returns the meta object for the '{@link org.eclipse.papyrus.infra.emf.expressions.IExpression#evaluate(java.lang.Object) <em>Evaluate</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Evaluate</em>' operation.
	 * @see org.eclipse.papyrus.infra.emf.expressions.IExpression#evaluate(java.lang.Object)
	 * @generated
	 */
	EOperation getIExpression__Evaluate__Object();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.infra.emf.expressions.ExpressionCatalog <em>Expression Catalog</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression Catalog</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.ExpressionCatalog
	 * @generated
	 */
	EClass getExpressionCatalog();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.papyrus.infra.emf.expressions.ExpressionCatalog#getExpressions <em>Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Expressions</em>'.
	 * @see org.eclipse.papyrus.infra.emf.expressions.ExpressionCatalog#getExpressions()
	 * @see #getExpressionCatalog()
	 * @generated
	 */
	EReference getExpressionCatalog_Expressions();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ExpressionsFactory getExpressionsFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement <em>IBasic Expression Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement
		 * @see org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionsPackageImpl#getIBasicExpressionElement()
		 * @generated
		 */
		EClass IBASIC_EXPRESSION_ELEMENT = eINSTANCE.getIBasicExpressionElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBASIC_EXPRESSION_ELEMENT__NAME = eINSTANCE.getIBasicExpressionElement_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IBASIC_EXPRESSION_ELEMENT__DESCRIPTION = eINSTANCE.getIBasicExpressionElement_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.IExpression <em>IExpression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.IExpression
		 * @see org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionsPackageImpl#getIExpression()
		 * @generated
		 */
		EClass IEXPRESSION = eINSTANCE.getIExpression();

		/**
		 * The meta object literal for the '<em><b>Evaluate</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation IEXPRESSION___EVALUATE__OBJECT = eINSTANCE.getIExpression__Evaluate__Object();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionCatalogImpl <em>Expression Catalog</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionCatalogImpl
		 * @see org.eclipse.papyrus.infra.emf.expressions.impl.ExpressionsPackageImpl#getExpressionCatalog()
		 * @generated
		 */
		EClass EXPRESSION_CATALOG = eINSTANCE.getExpressionCatalog();

		/**
		 * The meta object literal for the '<em><b>Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXPRESSION_CATALOG__EXPRESSIONS = eINSTANCE.getExpressionCatalog_Expressions();

	}

} //ExpressionsPackage
