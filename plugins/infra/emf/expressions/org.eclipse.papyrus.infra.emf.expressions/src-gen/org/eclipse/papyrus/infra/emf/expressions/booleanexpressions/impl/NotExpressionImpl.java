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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbstractSingleBooleanEObjectExpressionReferenceExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.NotExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Not Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.NotExpressionImpl#getReferencedExpression <em>Referenced Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NotExpressionImpl extends AbstractSingleBooleanEObjectExpressionOwnedExpressionImpl implements NotExpression {
	/**
	 * The cached value of the '{@link #getReferencedExpression() <em>Referenced Expression</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getReferencedExpression()
	 * @generated
	 * @ordered
	 */
	protected IBooleanEObjectExpression referencedExpression;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NotExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BooleanExpressionsPackage.Literals.NOT_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IBooleanEObjectExpression getReferencedExpression() {
		if (referencedExpression != null && referencedExpression.eIsProxy()) {
			InternalEObject oldReferencedExpression = (InternalEObject)referencedExpression;
			referencedExpression = (IBooleanEObjectExpression)eResolveProxy(oldReferencedExpression);
			if (referencedExpression != oldReferencedExpression) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BooleanExpressionsPackage.NOT_EXPRESSION__REFERENCED_EXPRESSION, oldReferencedExpression, referencedExpression));
			}
		}
		return referencedExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IBooleanEObjectExpression basicGetReferencedExpression() {
		return referencedExpression;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setReferencedExpression(IBooleanEObjectExpression newReferencedExpression) {
		IBooleanEObjectExpression oldReferencedExpression = referencedExpression;
		referencedExpression = newReferencedExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BooleanExpressionsPackage.NOT_EXPRESSION__REFERENCED_EXPRESSION, oldReferencedExpression, referencedExpression));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BooleanExpressionsPackage.NOT_EXPRESSION__REFERENCED_EXPRESSION:
				if (resolve) return getReferencedExpression();
				return basicGetReferencedExpression();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BooleanExpressionsPackage.NOT_EXPRESSION__REFERENCED_EXPRESSION:
				setReferencedExpression((IBooleanEObjectExpression)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case BooleanExpressionsPackage.NOT_EXPRESSION__REFERENCED_EXPRESSION:
				setReferencedExpression((IBooleanEObjectExpression)null);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case BooleanExpressionsPackage.NOT_EXPRESSION__REFERENCED_EXPRESSION:
				return referencedExpression != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == AbstractSingleBooleanEObjectExpressionReferenceExpression.class) {
			switch (derivedFeatureID) {
				case BooleanExpressionsPackage.NOT_EXPRESSION__REFERENCED_EXPRESSION: return BooleanExpressionsPackage.ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION__REFERENCED_EXPRESSION;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == AbstractSingleBooleanEObjectExpressionReferenceExpression.class) {
			switch (baseFeatureID) {
				case BooleanExpressionsPackage.ABSTRACT_SINGLE_BOOLEAN_EOBJECT_EXPRESSION_REFERENCE_EXPRESSION__REFERENCED_EXPRESSION: return BooleanExpressionsPackage.NOT_EXPRESSION__REFERENCED_EXPRESSION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //NotExpressionImpl
