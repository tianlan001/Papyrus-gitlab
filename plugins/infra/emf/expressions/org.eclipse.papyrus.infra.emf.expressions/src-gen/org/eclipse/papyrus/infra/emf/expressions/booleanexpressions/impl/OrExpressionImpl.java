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

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbtractMultiBooleanEObjectExpressionsOwnedExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.OrExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Or Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.OrExpressionImpl#getOwnedExpressions <em>Owned Expressions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OrExpressionImpl extends AbstractMultiBooleanEObjectExpressionsReferenceExpressionImpl implements OrExpression {
	/**
	 * The cached value of the '{@link #getOwnedExpressions() <em>Owned Expressions</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwnedExpressions()
	 * @generated
	 * @ordered
	 */
	protected EList<IBooleanEObjectExpression> ownedExpressions;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OrExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BooleanExpressionsPackage.Literals.OR_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBooleanEObjectExpression> getOwnedExpressions() {
		if (ownedExpressions == null) {
			ownedExpressions = new EObjectContainmentEList<IBooleanEObjectExpression>(IBooleanEObjectExpression.class, this, BooleanExpressionsPackage.OR_EXPRESSION__OWNED_EXPRESSIONS);
		}
		return ownedExpressions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BooleanExpressionsPackage.OR_EXPRESSION__OWNED_EXPRESSIONS:
				return ((InternalEList<?>)getOwnedExpressions()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case BooleanExpressionsPackage.OR_EXPRESSION__OWNED_EXPRESSIONS:
				return getOwnedExpressions();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BooleanExpressionsPackage.OR_EXPRESSION__OWNED_EXPRESSIONS:
				getOwnedExpressions().clear();
				getOwnedExpressions().addAll((Collection<? extends IBooleanEObjectExpression>)newValue);
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
			case BooleanExpressionsPackage.OR_EXPRESSION__OWNED_EXPRESSIONS:
				getOwnedExpressions().clear();
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
			case BooleanExpressionsPackage.OR_EXPRESSION__OWNED_EXPRESSIONS:
				return ownedExpressions != null && !ownedExpressions.isEmpty();
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
		if (baseClass == AbtractMultiBooleanEObjectExpressionsOwnedExpression.class) {
			switch (derivedFeatureID) {
				case BooleanExpressionsPackage.OR_EXPRESSION__OWNED_EXPRESSIONS: return BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS;
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
		if (baseClass == AbtractMultiBooleanEObjectExpressionsOwnedExpression.class) {
			switch (baseFeatureID) {
				case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS: return BooleanExpressionsPackage.OR_EXPRESSION__OWNED_EXPRESSIONS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //OrExpressionImpl
