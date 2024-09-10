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
package org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

import org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement;
import org.eclipse.papyrus.infra.emf.expressions.IExpression;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage
 * @generated
 */
public class BooleanExpressionsSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BooleanExpressionsPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanExpressionsSwitch() {
		if (modelPackage == null) {
			modelPackage = BooleanExpressionsPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case BooleanExpressionsPackage.OR_EXPRESSION: {
				OrExpression orExpression = (OrExpression)theEObject;
				T result = caseOrExpression(orExpression);
				if (result == null) result = caseAbstractMultiBooleanEObjectExpressionsReferenceExpression(orExpression);
				if (result == null) result = caseAbtractMultiBooleanEObjectExpressionsOwnedExpression(orExpression);
				if (result == null) result = caseIBooleanEObjectExpression(orExpression);
				if (result == null) result = caseIBooleanExpression(orExpression);
				if (result == null) result = caseIExpression(orExpression);
				if (result == null) result = caseIBasicExpressionElement(orExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION: {
				AbtractMultiBooleanEObjectExpressionsOwnedExpression abtractMultiBooleanEObjectExpressionsOwnedExpression = (AbtractMultiBooleanEObjectExpressionsOwnedExpression)theEObject;
				T result = caseAbtractMultiBooleanEObjectExpressionsOwnedExpression(abtractMultiBooleanEObjectExpressionsOwnedExpression);
				if (result == null) result = caseIBooleanEObjectExpression(abtractMultiBooleanEObjectExpressionsOwnedExpression);
				if (result == null) result = caseIBooleanExpression(abtractMultiBooleanEObjectExpressionsOwnedExpression);
				if (result == null) result = caseIExpression(abtractMultiBooleanEObjectExpressionsOwnedExpression);
				if (result == null) result = caseIBasicExpressionElement(abtractMultiBooleanEObjectExpressionsOwnedExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.IBOOLEAN_EOBJECT_EXPRESSION: {
				IBooleanEObjectExpression iBooleanEObjectExpression = (IBooleanEObjectExpression)theEObject;
				T result = caseIBooleanEObjectExpression(iBooleanEObjectExpression);
				if (result == null) result = caseIBooleanExpression(iBooleanEObjectExpression);
				if (result == null) result = caseIExpression(iBooleanEObjectExpression);
				if (result == null) result = caseIBasicExpressionElement(iBooleanEObjectExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.IBOOLEAN_EXPRESSION: {
				IBooleanExpression<?> iBooleanExpression = (IBooleanExpression<?>)theEObject;
				T result = caseIBooleanExpression(iBooleanExpression);
				if (result == null) result = caseIExpression(iBooleanExpression);
				if (result == null) result = caseIBasicExpressionElement(iBooleanExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.ABSTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_REFERENCE_EXPRESSION: {
				AbstractMultiBooleanEObjectExpressionsReferenceExpression abstractMultiBooleanEObjectExpressionsReferenceExpression = (AbstractMultiBooleanEObjectExpressionsReferenceExpression)theEObject;
				T result = caseAbstractMultiBooleanEObjectExpressionsReferenceExpression(abstractMultiBooleanEObjectExpressionsReferenceExpression);
				if (result == null) result = caseIBooleanEObjectExpression(abstractMultiBooleanEObjectExpressionsReferenceExpression);
				if (result == null) result = caseIBooleanExpression(abstractMultiBooleanEObjectExpressionsReferenceExpression);
				if (result == null) result = caseIExpression(abstractMultiBooleanEObjectExpressionsReferenceExpression);
				if (result == null) result = caseIBasicExpressionElement(abstractMultiBooleanEObjectExpressionsReferenceExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.AND_EXPRESSION: {
				AndExpression andExpression = (AndExpression)theEObject;
				T result = caseAndExpression(andExpression);
				if (result == null) result = caseAbstractMultiBooleanEObjectExpressionsReferenceExpression(andExpression);
				if (result == null) result = caseAbtractMultiBooleanEObjectExpressionsOwnedExpression(andExpression);
				if (result == null) result = caseIBooleanEObjectExpression(andExpression);
				if (result == null) result = caseIBooleanExpression(andExpression);
				if (result == null) result = caseIExpression(andExpression);
				if (result == null) result = caseIBasicExpressionElement(andExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.NOT_EXPRESSION: {
				NotExpression notExpression = (NotExpression)theEObject;
				T result = caseNotExpression(notExpression);
				if (result == null) result = caseAbstractSingleBooleanEObjectExpressionOwnedExpression(notExpression);
				if (result == null) result = caseAbstractSingleBooleanEObjectExpressionReferenceExpression(notExpression);
				if (result == null) result = caseIBooleanEObjectExpression(notExpression);
				if (result == null) result = caseIBooleanExpression(notExpression);
				if (result == null) result = caseIExpression(notExpression);
				if (result == null) result = caseIBasicExpressionElement(notExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_OWNED_EXPRESSION: {
				AbstractSingleBooleanEObjectExpressionOwnedExpression abstractSingleBooleanEObjectExpressionOwnedExpression = (AbstractSingleBooleanEObjectExpressionOwnedExpression)theEObject;
				T result = caseAbstractSingleBooleanEObjectExpressionOwnedExpression(abstractSingleBooleanEObjectExpressionOwnedExpression);
				if (result == null) result = caseIBooleanEObjectExpression(abstractSingleBooleanEObjectExpressionOwnedExpression);
				if (result == null) result = caseIBooleanExpression(abstractSingleBooleanEObjectExpressionOwnedExpression);
				if (result == null) result = caseIExpression(abstractSingleBooleanEObjectExpressionOwnedExpression);
				if (result == null) result = caseIBasicExpressionElement(abstractSingleBooleanEObjectExpressionOwnedExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION: {
				AbstractSingleBooleanEObjectExpressionReferenceExpression abstractSingleBooleanEObjectExpressionReferenceExpression = (AbstractSingleBooleanEObjectExpressionReferenceExpression)theEObject;
				T result = caseAbstractSingleBooleanEObjectExpressionReferenceExpression(abstractSingleBooleanEObjectExpressionReferenceExpression);
				if (result == null) result = caseIBooleanEObjectExpression(abstractSingleBooleanEObjectExpressionReferenceExpression);
				if (result == null) result = caseIBooleanExpression(abstractSingleBooleanEObjectExpressionReferenceExpression);
				if (result == null) result = caseIExpression(abstractSingleBooleanEObjectExpressionReferenceExpression);
				if (result == null) result = caseIBasicExpressionElement(abstractSingleBooleanEObjectExpressionReferenceExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.LITERAL_TRUE_EXPRESSION: {
				LiteralTrueExpression literalTrueExpression = (LiteralTrueExpression)theEObject;
				T result = caseLiteralTrueExpression(literalTrueExpression);
				if (result == null) result = caseIBooleanEObjectExpression(literalTrueExpression);
				if (result == null) result = caseIBooleanExpression(literalTrueExpression);
				if (result == null) result = caseIExpression(literalTrueExpression);
				if (result == null) result = caseIBasicExpressionElement(literalTrueExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.LITERAL_FALSE_EXPRESSION: {
				LiteralFalseExpression literalFalseExpression = (LiteralFalseExpression)theEObject;
				T result = caseLiteralFalseExpression(literalFalseExpression);
				if (result == null) result = caseIBooleanEObjectExpression(literalFalseExpression);
				if (result == null) result = caseIBooleanExpression(literalFalseExpression);
				if (result == null) result = caseIExpression(literalFalseExpression);
				if (result == null) result = caseIBasicExpressionElement(literalFalseExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.REFERENCE_BOOLEAN_EXPRESSION: {
				ReferenceBooleanExpression referenceBooleanExpression = (ReferenceBooleanExpression)theEObject;
				T result = caseReferenceBooleanExpression(referenceBooleanExpression);
				if (result == null) result = caseAbstractSingleBooleanEObjectExpressionReferenceExpression(referenceBooleanExpression);
				if (result == null) result = caseIBooleanEObjectExpression(referenceBooleanExpression);
				if (result == null) result = caseIBooleanExpression(referenceBooleanExpression);
				if (result == null) result = caseIExpression(referenceBooleanExpression);
				if (result == null) result = caseIBasicExpressionElement(referenceBooleanExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case BooleanExpressionsPackage.SINGLE_EATTRIBUTE_VALUE_EQUALITY_EXPRESSION: {
				SingleEAttributeValueEqualityExpression singleEAttributeValueEqualityExpression = (SingleEAttributeValueEqualityExpression)theEObject;
				T result = caseSingleEAttributeValueEqualityExpression(singleEAttributeValueEqualityExpression);
				if (result == null) result = caseIBooleanEObjectExpression(singleEAttributeValueEqualityExpression);
				if (result == null) result = caseIBooleanExpression(singleEAttributeValueEqualityExpression);
				if (result == null) result = caseIExpression(singleEAttributeValueEqualityExpression);
				if (result == null) result = caseIBasicExpressionElement(singleEAttributeValueEqualityExpression);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Or Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Or Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseOrExpression(OrExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abtract Multi Boolean EObject Expressions Owned Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abtract Multi Boolean EObject Expressions Owned Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbtractMultiBooleanEObjectExpressionsOwnedExpression(AbtractMultiBooleanEObjectExpressionsOwnedExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBoolean EObject Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBoolean EObject Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBooleanEObjectExpression(IBooleanEObjectExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBoolean Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBoolean Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <IBooleanExpression_REDEFINED_CONTEXT_TYPE> T caseIBooleanExpression(IBooleanExpression<IBooleanExpression_REDEFINED_CONTEXT_TYPE> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Multi Boolean EObject Expressions Reference Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Multi Boolean EObject Expressions Reference Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractMultiBooleanEObjectExpressionsReferenceExpression(AbstractMultiBooleanEObjectExpressionsReferenceExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>And Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>And Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAndExpression(AndExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Not Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Not Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseNotExpression(NotExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Single Boolean EObject Expression Owned Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Single Boolean EObject Expression Owned Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractSingleBooleanEObjectExpressionOwnedExpression(AbstractSingleBooleanEObjectExpressionOwnedExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Abstract Single Boolean EObject Expression Reference Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Abstract Single Boolean EObject Expression Reference Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAbstractSingleBooleanEObjectExpressionReferenceExpression(AbstractSingleBooleanEObjectExpressionReferenceExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal True Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal True Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralTrueExpression(LiteralTrueExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Literal False Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Literal False Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseLiteralFalseExpression(LiteralFalseExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Reference Boolean Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Reference Boolean Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseReferenceBooleanExpression(ReferenceBooleanExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Single EAttribute Value Equality Expression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Single EAttribute Value Equality Expression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSingleEAttributeValueEqualityExpression(SingleEAttributeValueEqualityExpression object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IBasic Expression Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IBasic Expression Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIBasicExpressionElement(IBasicExpressionElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IExpression</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IExpression</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public <CONTEXT_TYPE, RETURN_TYPE> T caseIExpression(IExpression<CONTEXT_TYPE, RETURN_TYPE> object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //BooleanExpressionsSwitch
