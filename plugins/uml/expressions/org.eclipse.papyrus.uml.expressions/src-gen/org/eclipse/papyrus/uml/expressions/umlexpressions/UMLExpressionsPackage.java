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
package org.eclipse.papyrus.uml.expressions.umlexpressions;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;

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
 * A string representing the expected value.
 * <!-- end-model-doc -->
 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/uml2/2.0.0/UML originalName='UMLExpressions'"
 * @generated
 */
public interface UMLExpressionsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "umlexpressions"; //$NON-NLS-1$

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.eclipse.org/papyrus/umlexpressions"; //$NON-NLS-1$

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "umlexpressions"; //$NON-NLS-1$

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UMLExpressionsPackage eINSTANCE = org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.AbstractStereotypeExpressionImpl <em>Abstract Stereotype Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.AbstractStereotypeExpressionImpl
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getAbstractStereotypeExpression()
	 * @generated
	 */
	int ABSTRACT_STEREOTYPE_EXPRESSION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STEREOTYPE_EXPRESSION__NAME = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STEREOTYPE_EXPRESSION__DESCRIPTION = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Stereotype Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STEREOTYPE_EXPRESSION__STEREOTYPE_QUALIFIED_NAME = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Profile URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STEREOTYPE_EXPRESSION__PROFILE_URI = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Abstract Stereotype Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STEREOTYPE_EXPRESSION_FEATURE_COUNT = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STEREOTYPE_EXPRESSION___EVALUATE__OBJECT = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Abstract Stereotype Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_STEREOTYPE_EXPRESSION_OPERATION_COUNT = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsStereotypedWithExpressionImpl <em>Is Stereotyped With Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsStereotypedWithExpressionImpl
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getIsStereotypedWithExpression()
	 * @generated
	 */
	int IS_STEREOTYPED_WITH_EXPRESSION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_STEREOTYPED_WITH_EXPRESSION__NAME = ABSTRACT_STEREOTYPE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_STEREOTYPED_WITH_EXPRESSION__DESCRIPTION = ABSTRACT_STEREOTYPE_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Stereotype Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_STEREOTYPED_WITH_EXPRESSION__STEREOTYPE_QUALIFIED_NAME = ABSTRACT_STEREOTYPE_EXPRESSION__STEREOTYPE_QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Profile URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_STEREOTYPED_WITH_EXPRESSION__PROFILE_URI = ABSTRACT_STEREOTYPE_EXPRESSION__PROFILE_URI;

	/**
	 * The number of structural features of the '<em>Is Stereotyped With Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_STEREOTYPED_WITH_EXPRESSION_FEATURE_COUNT = ABSTRACT_STEREOTYPE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_STEREOTYPED_WITH_EXPRESSION___EVALUATE__OBJECT = ABSTRACT_STEREOTYPE_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Is Stereotyped With Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_STEREOTYPED_WITH_EXPRESSION_OPERATION_COUNT = ABSTRACT_STEREOTYPE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.HasAppliedStereotypesExpressionImpl <em>Has Applied Stereotypes Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.HasAppliedStereotypesExpressionImpl
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getHasAppliedStereotypesExpression()
	 * @generated
	 */
	int HAS_APPLIED_STEREOTYPES_EXPRESSION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HAS_APPLIED_STEREOTYPES_EXPRESSION__NAME = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HAS_APPLIED_STEREOTYPES_EXPRESSION__DESCRIPTION = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION;

	/**
	 * The number of structural features of the '<em>Has Applied Stereotypes Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HAS_APPLIED_STEREOTYPES_EXPRESSION_FEATURE_COUNT = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HAS_APPLIED_STEREOTYPES_EXPRESSION___EVALUATE__OBJECT = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Has Applied Stereotypes Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HAS_APPLIED_STEREOTYPES_EXPRESSION_OPERATION_COUNT = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.AbstractUMLEClassExpressionImpl <em>Abstract UMLE Class Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.AbstractUMLEClassExpressionImpl
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getAbstractUMLEClassExpression()
	 * @generated
	 */
	int ABSTRACT_UMLE_CLASS_EXPRESSION = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_UMLE_CLASS_EXPRESSION__NAME = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_UMLE_CLASS_EXPRESSION__DESCRIPTION = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Uml EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_UMLE_CLASS_EXPRESSION__UML_ECLASS = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Abstract UMLE Class Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_UMLE_CLASS_EXPRESSION_FEATURE_COUNT = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_UMLE_CLASS_EXPRESSION___EVALUATE__OBJECT = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Abstract UMLE Class Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ABSTRACT_UMLE_CLASS_EXPRESSION_OPERATION_COUNT = BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsTypeOfExpressionImpl <em>Is Type Of Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsTypeOfExpressionImpl
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getIsTypeOfExpression()
	 * @generated
	 */
	int IS_TYPE_OF_EXPRESSION = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_EXPRESSION__NAME = ABSTRACT_UMLE_CLASS_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_EXPRESSION__DESCRIPTION = ABSTRACT_UMLE_CLASS_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Uml EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_EXPRESSION__UML_ECLASS = ABSTRACT_UMLE_CLASS_EXPRESSION__UML_ECLASS;

	/**
	 * The number of structural features of the '<em>Is Type Of Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_EXPRESSION_FEATURE_COUNT = ABSTRACT_UMLE_CLASS_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_EXPRESSION___EVALUATE__OBJECT = ABSTRACT_UMLE_CLASS_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Is Type Of Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_EXPRESSION_OPERATION_COUNT = ABSTRACT_UMLE_CLASS_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsKindOfExpressionImpl <em>Is Kind Of Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsKindOfExpressionImpl
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getIsKindOfExpression()
	 * @generated
	 */
	int IS_KIND_OF_EXPRESSION = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_EXPRESSION__NAME = ABSTRACT_UMLE_CLASS_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_EXPRESSION__DESCRIPTION = ABSTRACT_UMLE_CLASS_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Uml EClass</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_EXPRESSION__UML_ECLASS = ABSTRACT_UMLE_CLASS_EXPRESSION__UML_ECLASS;

	/**
	 * The number of structural features of the '<em>Is Kind Of Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_EXPRESSION_FEATURE_COUNT = ABSTRACT_UMLE_CLASS_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_EXPRESSION___EVALUATE__OBJECT = ABSTRACT_UMLE_CLASS_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Is Kind Of Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_EXPRESSION_OPERATION_COUNT = ABSTRACT_UMLE_CLASS_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsKindOfStereotypeExpressionImpl <em>Is Kind Of Stereotype Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsKindOfStereotypeExpressionImpl
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getIsKindOfStereotypeExpression()
	 * @generated
	 */
	int IS_KIND_OF_STEREOTYPE_EXPRESSION = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_STEREOTYPE_EXPRESSION__NAME = ABSTRACT_STEREOTYPE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_STEREOTYPE_EXPRESSION__DESCRIPTION = ABSTRACT_STEREOTYPE_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Stereotype Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_STEREOTYPE_EXPRESSION__STEREOTYPE_QUALIFIED_NAME = ABSTRACT_STEREOTYPE_EXPRESSION__STEREOTYPE_QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Profile URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_STEREOTYPE_EXPRESSION__PROFILE_URI = ABSTRACT_STEREOTYPE_EXPRESSION__PROFILE_URI;

	/**
	 * The number of structural features of the '<em>Is Kind Of Stereotype Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_STEREOTYPE_EXPRESSION_FEATURE_COUNT = ABSTRACT_STEREOTYPE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_STEREOTYPE_EXPRESSION___EVALUATE__OBJECT = ABSTRACT_STEREOTYPE_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Is Kind Of Stereotype Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_KIND_OF_STEREOTYPE_EXPRESSION_OPERATION_COUNT = ABSTRACT_STEREOTYPE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsTypeOfStereotypeExpressionImpl <em>Is Type Of Stereotype Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsTypeOfStereotypeExpressionImpl
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getIsTypeOfStereotypeExpression()
	 * @generated
	 */
	int IS_TYPE_OF_STEREOTYPE_EXPRESSION = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_STEREOTYPE_EXPRESSION__NAME = ABSTRACT_STEREOTYPE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_STEREOTYPE_EXPRESSION__DESCRIPTION = ABSTRACT_STEREOTYPE_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Stereotype Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_STEREOTYPE_EXPRESSION__STEREOTYPE_QUALIFIED_NAME = ABSTRACT_STEREOTYPE_EXPRESSION__STEREOTYPE_QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Profile URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_STEREOTYPE_EXPRESSION__PROFILE_URI = ABSTRACT_STEREOTYPE_EXPRESSION__PROFILE_URI;

	/**
	 * The number of structural features of the '<em>Is Type Of Stereotype Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_STEREOTYPE_EXPRESSION_FEATURE_COUNT = ABSTRACT_STEREOTYPE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_STEREOTYPE_EXPRESSION___EVALUATE__OBJECT = ABSTRACT_STEREOTYPE_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Is Type Of Stereotype Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IS_TYPE_OF_STEREOTYPE_EXPRESSION_OPERATION_COUNT = ABSTRACT_STEREOTYPE_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.SingleStereotypeAttributeEqualityExpressionImpl <em>Single Stereotype Attribute Equality Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.SingleStereotypeAttributeEqualityExpressionImpl
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getSingleStereotypeAttributeEqualityExpression()
	 * @generated
	 */
	int SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION__NAME = ABSTRACT_STEREOTYPE_EXPRESSION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION__DESCRIPTION = ABSTRACT_STEREOTYPE_EXPRESSION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Stereotype Qualified Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION__STEREOTYPE_QUALIFIED_NAME = ABSTRACT_STEREOTYPE_EXPRESSION__STEREOTYPE_QUALIFIED_NAME;

	/**
	 * The feature id for the '<em><b>Profile URI</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION__PROFILE_URI = ABSTRACT_STEREOTYPE_EXPRESSION__PROFILE_URI;

	/**
	 * The feature id for the '<em><b>Expected Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION__EXPECTED_VALUE = ABSTRACT_STEREOTYPE_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Property Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION__PROPERTY_NAME = ABSTRACT_STEREOTYPE_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Single Stereotype Attribute Equality Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION_FEATURE_COUNT = ABSTRACT_STEREOTYPE_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Evaluate</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION___EVALUATE__OBJECT = ABSTRACT_STEREOTYPE_EXPRESSION___EVALUATE__OBJECT;

	/**
	 * The number of operations of the '<em>Single Stereotype Attribute Equality Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION_OPERATION_COUNT = ABSTRACT_STEREOTYPE_EXPRESSION_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.IsStereotypedWithExpression <em>Is Stereotyped With Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Is Stereotyped With Expression</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.IsStereotypedWithExpression
	 * @generated
	 */
	EClass getIsStereotypedWithExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression <em>Abstract Stereotype Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract Stereotype Expression</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression
	 * @generated
	 */
	EClass getAbstractStereotypeExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression#getStereotypeQualifiedName <em>Stereotype Qualified Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stereotype Qualified Name</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression#getStereotypeQualifiedName()
	 * @see #getAbstractStereotypeExpression()
	 * @generated
	 */
	EAttribute getAbstractStereotypeExpression_StereotypeQualifiedName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression#getProfileURI <em>Profile URI</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Profile URI</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression#getProfileURI()
	 * @see #getAbstractStereotypeExpression()
	 * @generated
	 */
	EAttribute getAbstractStereotypeExpression_ProfileURI();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.HasAppliedStereotypesExpression <em>Has Applied Stereotypes Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Has Applied Stereotypes Expression</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.HasAppliedStereotypesExpression
	 * @generated
	 */
	EClass getHasAppliedStereotypesExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfExpression <em>Is Type Of Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Is Type Of Expression</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfExpression
	 * @generated
	 */
	EClass getIsTypeOfExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractUMLEClassExpression <em>Abstract UMLE Class Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Abstract UMLE Class Expression</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractUMLEClassExpression
	 * @generated
	 */
	EClass getAbstractUMLEClassExpression();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractUMLEClassExpression#getUmlEClass <em>Uml EClass</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Uml EClass</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractUMLEClassExpression#getUmlEClass()
	 * @see #getAbstractUMLEClassExpression()
	 * @generated
	 */
	EReference getAbstractUMLEClassExpression_UmlEClass();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfExpression <em>Is Kind Of Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Is Kind Of Expression</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfExpression
	 * @generated
	 */
	EClass getIsKindOfExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfStereotypeExpression <em>Is Kind Of Stereotype Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Is Kind Of Stereotype Expression</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfStereotypeExpression
	 * @generated
	 */
	EClass getIsKindOfStereotypeExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfStereotypeExpression <em>Is Type Of Stereotype Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Is Type Of Stereotype Expression</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfStereotypeExpression
	 * @generated
	 */
	EClass getIsTypeOfStereotypeExpression();

	/**
	 * Returns the meta object for class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression <em>Single Stereotype Attribute Equality Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Single Stereotype Attribute Equality Expression</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression
	 * @generated
	 */
	EClass getSingleStereotypeAttributeEqualityExpression();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression#getExpectedValue <em>Expected Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expected Value</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression#getExpectedValue()
	 * @see #getSingleStereotypeAttributeEqualityExpression()
	 * @generated
	 */
	EAttribute getSingleStereotypeAttributeEqualityExpression_ExpectedValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression#getPropertyName <em>Property Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Property Name</em>'.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression#getPropertyName()
	 * @see #getSingleStereotypeAttributeEqualityExpression()
	 * @generated
	 */
	EAttribute getSingleStereotypeAttributeEqualityExpression_PropertyName();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UMLExpressionsFactory getUMLExpressionsFactory();

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
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsStereotypedWithExpressionImpl <em>Is Stereotyped With Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsStereotypedWithExpressionImpl
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getIsStereotypedWithExpression()
		 * @generated
		 */
		EClass IS_STEREOTYPED_WITH_EXPRESSION = eINSTANCE.getIsStereotypedWithExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.AbstractStereotypeExpressionImpl <em>Abstract Stereotype Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.AbstractStereotypeExpressionImpl
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getAbstractStereotypeExpression()
		 * @generated
		 */
		EClass ABSTRACT_STEREOTYPE_EXPRESSION = eINSTANCE.getAbstractStereotypeExpression();

		/**
		 * The meta object literal for the '<em><b>Stereotype Qualified Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_STEREOTYPE_EXPRESSION__STEREOTYPE_QUALIFIED_NAME = eINSTANCE.getAbstractStereotypeExpression_StereotypeQualifiedName();

		/**
		 * The meta object literal for the '<em><b>Profile URI</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ABSTRACT_STEREOTYPE_EXPRESSION__PROFILE_URI = eINSTANCE.getAbstractStereotypeExpression_ProfileURI();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.HasAppliedStereotypesExpressionImpl <em>Has Applied Stereotypes Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.HasAppliedStereotypesExpressionImpl
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getHasAppliedStereotypesExpression()
		 * @generated
		 */
		EClass HAS_APPLIED_STEREOTYPES_EXPRESSION = eINSTANCE.getHasAppliedStereotypesExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsTypeOfExpressionImpl <em>Is Type Of Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsTypeOfExpressionImpl
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getIsTypeOfExpression()
		 * @generated
		 */
		EClass IS_TYPE_OF_EXPRESSION = eINSTANCE.getIsTypeOfExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.AbstractUMLEClassExpressionImpl <em>Abstract UMLE Class Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.AbstractUMLEClassExpressionImpl
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getAbstractUMLEClassExpression()
		 * @generated
		 */
		EClass ABSTRACT_UMLE_CLASS_EXPRESSION = eINSTANCE.getAbstractUMLEClassExpression();

		/**
		 * The meta object literal for the '<em><b>Uml EClass</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ABSTRACT_UMLE_CLASS_EXPRESSION__UML_ECLASS = eINSTANCE.getAbstractUMLEClassExpression_UmlEClass();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsKindOfExpressionImpl <em>Is Kind Of Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsKindOfExpressionImpl
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getIsKindOfExpression()
		 * @generated
		 */
		EClass IS_KIND_OF_EXPRESSION = eINSTANCE.getIsKindOfExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsKindOfStereotypeExpressionImpl <em>Is Kind Of Stereotype Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsKindOfStereotypeExpressionImpl
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getIsKindOfStereotypeExpression()
		 * @generated
		 */
		EClass IS_KIND_OF_STEREOTYPE_EXPRESSION = eINSTANCE.getIsKindOfStereotypeExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsTypeOfStereotypeExpressionImpl <em>Is Type Of Stereotype Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.IsTypeOfStereotypeExpressionImpl
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getIsTypeOfStereotypeExpression()
		 * @generated
		 */
		EClass IS_TYPE_OF_STEREOTYPE_EXPRESSION = eINSTANCE.getIsTypeOfStereotypeExpression();

		/**
		 * The meta object literal for the '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.impl.SingleStereotypeAttributeEqualityExpressionImpl <em>Single Stereotype Attribute Equality Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.SingleStereotypeAttributeEqualityExpressionImpl
		 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.impl.UMLExpressionsPackageImpl#getSingleStereotypeAttributeEqualityExpression()
		 * @generated
		 */
		EClass SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION = eINSTANCE.getSingleStereotypeAttributeEqualityExpression();

		/**
		 * The meta object literal for the '<em><b>Expected Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION__EXPECTED_VALUE = eINSTANCE.getSingleStereotypeAttributeEqualityExpression_ExpectedValue();

		/**
		 * The meta object literal for the '<em><b>Property Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SINGLE_STEREOTYPE_ATTRIBUTE_EQUALITY_EXPRESSION__PROPERTY_NAME = eINSTANCE.getSingleStereotypeAttributeEqualityExpression_PropertyName();

	}

} //UMLExpressionsPackage
