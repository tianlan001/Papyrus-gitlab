/*******************************************************************************
 * Copyright (c) 2006, 2020 Borland Software Corporation, CEA LIST, ARTAL
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 * 
 *  SPDX-License-Identifier: EPL-2.0
 * 
 *  Contributors:
 *     Borland - initial API and implementation
 *     Aurelien Didier (ARTAL) - aurelien.didier51@gmail.com - Bug 569174
 ******************************************************************************/
/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.eclipse.papyrus.gmf.codegen.gmfgen.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.codegen.ecore.genmodel.GenPackage;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenEditorGenerator;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricContainer;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Metric Container</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenMetricContainerImpl#getEditorGen <em>Editor Gen</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenMetricContainerImpl#getMetrics <em>Metrics</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenMetricContainerImpl extends EObjectImpl implements GenMetricContainer {
	/**
	 * The cached value of the '{@link #getMetrics() <em>Metrics</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetrics()
	 * @generated
	 * @ordered
	 */
	protected EList<GenMetricRule> metrics;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenMetricContainerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenMetricContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenEditorGenerator getEditorGen() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_METRIC_CONTAINER__EDITOR_GEN) return null;
		return (GenEditorGenerator)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<GenMetricRule> getMetrics() {
		if (metrics == null) {
			metrics = new EObjectContainmentWithInverseEList<GenMetricRule>(GenMetricRule.class, this, GMFGenPackage.GEN_METRIC_CONTAINER__METRICS, GMFGenPackage.GEN_METRIC_RULE__CONTAINER);
		}
		return metrics;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public Set<GenPackage> getAllTargetedModelPackages() {
		HashSet<GenPackage> packages = new HashSet<GenPackage>();
		for (GenMetricRule nextRule  : getMetrics()) {
			if(nextRule.getTarget() != null && nextRule.getTarget().getContext() != null) {
				packages.add(nextRule.getTarget().getContext().getGenPackage());
			}
		}
		return packages;
	}	

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
		@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_METRIC_CONTAINER__EDITOR_GEN:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.GEN_METRIC_CONTAINER__EDITOR_GEN, msgs);
			case GMFGenPackage.GEN_METRIC_CONTAINER__METRICS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getMetrics()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_METRIC_CONTAINER__EDITOR_GEN:
				return eBasicSetContainer(null, GMFGenPackage.GEN_METRIC_CONTAINER__EDITOR_GEN, msgs);
			case GMFGenPackage.GEN_METRIC_CONTAINER__METRICS:
				return ((InternalEList<?>)getMetrics()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
			case GMFGenPackage.GEN_METRIC_CONTAINER__EDITOR_GEN:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_EDITOR_GENERATOR__METRICS, GenEditorGenerator.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.GEN_METRIC_CONTAINER__EDITOR_GEN:
				return getEditorGen();
			case GMFGenPackage.GEN_METRIC_CONTAINER__METRICS:
				return getMetrics();
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
			case GMFGenPackage.GEN_METRIC_CONTAINER__METRICS:
				getMetrics().clear();
				getMetrics().addAll((Collection<? extends GenMetricRule>)newValue);
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
			case GMFGenPackage.GEN_METRIC_CONTAINER__METRICS:
				getMetrics().clear();
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
			case GMFGenPackage.GEN_METRIC_CONTAINER__EDITOR_GEN:
				return getEditorGen() != null;
			case GMFGenPackage.GEN_METRIC_CONTAINER__METRICS:
				return metrics != null && !metrics.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //GenMetricContainerImpl
