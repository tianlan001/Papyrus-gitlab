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

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.AbtractMultiBooleanEObjectExpressionsOwnedExpression;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.BooleanExpressionsPackage;
import org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.IBooleanEObjectExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Abtract Multi Boolean EObject Expressions Owned Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbtractMultiBooleanEObjectExpressionsOwnedExpressionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbtractMultiBooleanEObjectExpressionsOwnedExpressionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.emf.expressions.booleanexpressions.impl.AbtractMultiBooleanEObjectExpressionsOwnedExpressionImpl#getOwnedExpressions <em>Owned Expressions</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class AbtractMultiBooleanEObjectExpressionsOwnedExpressionImpl extends MinimalEObjectImpl.Container implements AbtractMultiBooleanEObjectExpressionsOwnedExpression {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected String description = DESCRIPTION_EDEFAULT;

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
	protected AbtractMultiBooleanEObjectExpressionsOwnedExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BooleanExpressionsPackage.Literals.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<IBooleanEObjectExpression> getOwnedExpressions() {
		if (ownedExpressions == null) {
			ownedExpressions = new EObjectContainmentEList<IBooleanEObjectExpression>(IBooleanEObjectExpression.class, this, BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS);
		}
		return ownedExpressions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Boolean evaluate(EObject context) {
		// TODO: implement this method
		// Ensure that you remove @generated or mark it @generated NOT
		throw new UnsupportedOperationException();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS:
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
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__NAME:
				return getName();
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__DESCRIPTION:
				return getDescription();
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS:
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
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__NAME:
				setName((String)newValue);
				return;
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS:
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
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS:
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
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION__OWNED_EXPRESSIONS:
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case BooleanExpressionsPackage.ABTRACT_MULTI_BOOLEAN_EOBJECT_EXPRESSIONS_OWNED_EXPRESSION___EVALUATE__OBJECT:
				return evaluate((EObject)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: "); //$NON-NLS-1$
		result.append(name);
		result.append(", description: "); //$NON-NLS-1$
		result.append(description);
		result.append(')');
		return result.toString();
	}

} //AbtractMultiBooleanEObjectExpressionsOwnedExpressionImpl
