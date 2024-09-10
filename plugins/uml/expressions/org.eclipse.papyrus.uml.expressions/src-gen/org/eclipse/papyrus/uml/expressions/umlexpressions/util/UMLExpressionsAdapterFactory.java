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
package org.eclipse.papyrus.uml.expressions.umlexpressions.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.eclipse.papyrus.infra.emf.expressions.IBasicExpressionElement;
import org.eclipse.papyrus.infra.emf.expressions.IExpression;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanExpression;

import org.eclipse.papyrus.uml.expressions.umlexpressions.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.UMLExpressionsPackage
 * @generated
 */
public class UMLExpressionsAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static UMLExpressionsPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UMLExpressionsAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = UMLExpressionsPackage.eINSTANCE;
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
	protected UMLExpressionsSwitch<Adapter> modelSwitch =
		new UMLExpressionsSwitch<Adapter>() {
			@Override
			public Adapter caseIsStereotypedWithExpression(IsStereotypedWithExpression object) {
				return createIsStereotypedWithExpressionAdapter();
			}
			@Override
			public Adapter caseAbstractStereotypeExpression(AbstractStereotypeExpression object) {
				return createAbstractStereotypeExpressionAdapter();
			}
			@Override
			public Adapter caseHasAppliedStereotypesExpression(HasAppliedStereotypesExpression object) {
				return createHasAppliedStereotypesExpressionAdapter();
			}
			@Override
			public Adapter caseIsTypeOfExpression(IsTypeOfExpression object) {
				return createIsTypeOfExpressionAdapter();
			}
			@Override
			public Adapter caseAbstractUMLEClassExpression(AbstractUMLEClassExpression object) {
				return createAbstractUMLEClassExpressionAdapter();
			}
			@Override
			public Adapter caseIsKindOfExpression(IsKindOfExpression object) {
				return createIsKindOfExpressionAdapter();
			}
			@Override
			public Adapter caseIsKindOfStereotypeExpression(IsKindOfStereotypeExpression object) {
				return createIsKindOfStereotypeExpressionAdapter();
			}
			@Override
			public Adapter caseIsTypeOfStereotypeExpression(IsTypeOfStereotypeExpression object) {
				return createIsTypeOfStereotypeExpressionAdapter();
			}
			@Override
			public Adapter caseSingleStereotypeAttributeEqualityExpression(SingleStereotypeAttributeEqualityExpression object) {
				return createSingleStereotypeAttributeEqualityExpressionAdapter();
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
			public <IBooleanExpression_REDEFINED_CONTEXT_TYPE> Adapter caseIBooleanExpression(IBooleanExpression<IBooleanExpression_REDEFINED_CONTEXT_TYPE> object) {
				return createIBooleanExpressionAdapter();
			}
			@Override
			public Adapter caseIBooleanEObjectExpression(IBooleanEObjectExpression object) {
				return createIBooleanEObjectExpressionAdapter();
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
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.IsStereotypedWithExpression <em>Is Stereotyped With Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.IsStereotypedWithExpression
	 * @generated
	 */
	public Adapter createIsStereotypedWithExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression <em>Abstract Stereotype Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractStereotypeExpression
	 * @generated
	 */
	public Adapter createAbstractStereotypeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.HasAppliedStereotypesExpression <em>Has Applied Stereotypes Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.HasAppliedStereotypesExpression
	 * @generated
	 */
	public Adapter createHasAppliedStereotypesExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfExpression <em>Is Type Of Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfExpression
	 * @generated
	 */
	public Adapter createIsTypeOfExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractUMLEClassExpression <em>Abstract UMLE Class Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.AbstractUMLEClassExpression
	 * @generated
	 */
	public Adapter createAbstractUMLEClassExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfExpression <em>Is Kind Of Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfExpression
	 * @generated
	 */
	public Adapter createIsKindOfExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfStereotypeExpression <em>Is Kind Of Stereotype Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.IsKindOfStereotypeExpression
	 * @generated
	 */
	public Adapter createIsKindOfStereotypeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfStereotypeExpression <em>Is Type Of Stereotype Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.IsTypeOfStereotypeExpression
	 * @generated
	 */
	public Adapter createIsTypeOfStereotypeExpressionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression <em>Single Stereotype Attribute Equality Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see org.eclipse.papyrus.uml.expressions.umlexpressions.SingleStereotypeAttributeEqualityExpression
	 * @generated
	 */
	public Adapter createSingleStereotypeAttributeEqualityExpressionAdapter() {
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

} //UMLExpressionsAdapterFactory
