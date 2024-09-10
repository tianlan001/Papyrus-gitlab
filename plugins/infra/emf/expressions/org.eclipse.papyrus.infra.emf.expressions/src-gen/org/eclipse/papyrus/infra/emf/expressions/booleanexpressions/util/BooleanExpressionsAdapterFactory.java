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

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement;
import org.eclipse.papyrus.infra.emf.expressions.IExpression;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage
 * @generated
 */
public class BooleanExpressionsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static BooleanExpressionsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanExpressionsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = BooleanExpressionsPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BooleanExpressionsSwitch<Adapter> modelSwitch =
		new BooleanExpressionsSwitch<Adapter>() {
			@Override
			public Adapter caseOrExpression(OrExpression object) {
				return createOrExpressionAdapter();
			}
			@Override
			public Adapter caseAbtractMultiBooleanEObjectExpressionsOwnedExpression(AbtractMultiBooleanEObjectExpressionsOwnedExpression object) {
				return createAbtractMultiBooleanEObjectExpressionsOwnedExpressionAdapter();
			}
			@Override
			public Adapter caseIBooleanEObjectExpression(IBooleanEObjectExpression object) {
				return createIBooleanEObjectExpressionAdapter();
			}
			@Override
			public <IBooleanExpression_REDEFINED_CONTEXT_TYPE> Adapter caseIBooleanExpression(IBooleanExpression<IBooleanExpression_REDEFINED_CONTEXT_TYPE> object) {
				return createIBooleanExpressionAdapter();
			}
			@Override
			public Adapter caseAbstractMultiBooleanEObjectExpressionsReferenceExpression(AbstractMultiBooleanEObjectExpressionsReferenceExpression object) {
				return createAbstractMultiBooleanEObjectExpressionsReferenceExpressionAdapter();
			}
			@Override
			public Adapter caseAndExpression(AndExpression object) {
				return createAndExpressionAdapter();
			}
			@Override
			public Adapter caseNotExpression(NotExpression object) {
				return createNotExpressionAdapter();
			}
			@Override
			public Adapter caseAbstractSingleBooleanEObjectExpressionOwnedExpression(AbstractSingleBooleanEObjectExpressionOwnedExpression object) {
				return createAbstractSingleBooleanEObjectExpressionOwnedExpressionAdapter();
			}
			@Override
			public Adapter caseAbstractSingleBooleanEObjectExpressionReferenceExpression(AbstractSingleBooleanEObjectExpressionReferenceExpression object) {
				return createAbstractSingleBooleanEObjectExpressionReferenceExpressionAdapter();
			}
			@Override
			public Adapter caseLiteralTrueExpression(LiteralTrueExpression object) {
				return createLiteralTrueExpressionAdapter();
			}
			@Override
			public Adapter caseLiteralFalseExpression(LiteralFalseExpression object) {
				return createLiteralFalseExpressionAdapter();
			}
			@Override
			public Adapter caseReferenceBooleanExpression(ReferenceBooleanExpression object) {
				return createReferenceBooleanExpressionAdapter();
			}
			@Override
			public Adapter caseSingleEAttributeValueEqualityExpression(SingleEAttributeValueEqualityExpression object) {
				return createSingleEAttributeValueEqualityExpressionAdapter();
			}
			@Override
			public Adapter caseIBasicExpressionElement(IBasicExpressionElement object) {
				return createIBasicExpressionElementAdapter();
			}
			@Override
			public <CONTEXT_TYPE, RETURN_TYPE> Adapter caseIExpression(IExpression<CONTEXT_TYPE, RETURN_TYPE> object) {
				return createIExpressionAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.OrExpression <em>Or Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.OrExpression
	 * @generated
	 */
	public Adapter createOrExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbtractMultiBooleanEObjectExpressionsOwnedExpression <em>Abtract Multi Boolean EObject Expressions Owned Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbtractMultiBooleanEObjectExpressionsOwnedExpression
	 * @generated
	 */
	public Adapter createAbtractMultiBooleanEObjectExpressionsOwnedExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression <em>IBoolean EObject Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression
	 * @generated
	 */
	public Adapter createIBooleanEObjectExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression <em>IBoolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression
	 * @generated
	 */
	public Adapter createIBooleanExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractMultiBooleanEObjectExpressionsReferenceExpression <em>Abstract Multi Boolean EObject Expressions Reference Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractMultiBooleanEObjectExpressionsReferenceExpression
	 * @generated
	 */
	public Adapter createAbstractMultiBooleanEObjectExpressionsReferenceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AndExpression <em>And Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AndExpression
	 * @generated
	 */
	public Adapter createAndExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.NotExpression <em>Not Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.NotExpression
	 * @generated
	 */
	public Adapter createNotExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionOwnedExpression <em>Abstract Single Boolean EObject Expression Owned Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionOwnedExpression
	 * @generated
	 */
	public Adapter createAbstractSingleBooleanEObjectExpressionOwnedExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionReferenceExpression <em>Abstract Single Boolean EObject Expression Reference Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionReferenceExpression
	 * @generated
	 */
	public Adapter createAbstractSingleBooleanEObjectExpressionReferenceExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralTrueExpression <em>Literal True Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralTrueExpression
	 * @generated
	 */
	public Adapter createLiteralTrueExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralFalseExpression <em>Literal False Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.LiteralFalseExpression
	 * @generated
	 */
	public Adapter createLiteralFalseExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.ReferenceBooleanExpression <em>Reference Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.ReferenceBooleanExpression
	 * @generated
	 */
	public Adapter createReferenceBooleanExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression <em>Single EAttribute Value Equality Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.SingleEAttributeValueEqualityExpression
	 * @generated
	 */
	public Adapter createSingleEAttributeValueEqualityExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement <em>IBasic Expression Element</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement
	 * @generated
	 */
	public Adapter createIBasicExpressionElementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.infra.emf.expressions.IExpression <em>IExpression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.infra.emf.expressions.IExpression
	 * @generated
	 */
	public Adapter createIExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //BooleanExpressionsAdapterFactory
