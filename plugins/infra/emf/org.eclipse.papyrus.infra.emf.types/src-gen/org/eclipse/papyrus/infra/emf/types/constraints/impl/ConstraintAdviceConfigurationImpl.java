/**
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 */
package org.eclipse.papyrus.infra.emf.types.constraints.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.infra.emf.types.constraints.AdviceConstraint;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdviceConfiguration;
import org.eclipse.papyrus.infra.emf.types.constraints.ConstraintAdvicePackage;

import org.eclipse.papyrus.infra.types.impl.AbstractAdviceBindingConfigurationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.emf.types.constraints.impl.ConstraintAdviceConfigurationImpl#getConstraints <em>Constraint</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConstraintAdviceConfigurationImpl extends AbstractAdviceBindingConfigurationImpl implements ConstraintAdviceConfiguration {
	/**
	 * The cached value of the '{@link #getConstraints() <em>Constraint</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getConstraints()
	 * @generated
	 * @ordered
	 */
	protected EList<AdviceConstraint> constraints;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected ConstraintAdviceConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConstraintAdvicePackage.Literals.CONSTRAINT_ADVICE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<AdviceConstraint> getConstraints() {
		if (constraints == null) {
			constraints = new EObjectContainmentWithInverseEList<>(AdviceConstraint.class, this, ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT, ConstraintAdvicePackage.ADVICE_CONSTRAINT__OWNING_ADVICE);
		}
		return constraints;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getConstraints()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT:
			return ((InternalEList<?>) getConstraints()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT:
			return getConstraints();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT:
			getConstraints().clear();
			getConstraints().addAll((Collection<? extends AdviceConstraint>) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT:
			getConstraints().clear();
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ConstraintAdvicePackage.CONSTRAINT_ADVICE_CONFIGURATION__CONSTRAINT:
			return constraints != null && !constraints.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} // ConstraintAdviceConfigurationImpl
