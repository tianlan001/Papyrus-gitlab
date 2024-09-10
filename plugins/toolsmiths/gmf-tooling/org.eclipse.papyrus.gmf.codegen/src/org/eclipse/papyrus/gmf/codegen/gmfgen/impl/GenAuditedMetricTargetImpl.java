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

import org.eclipse.emf.codegen.ecore.genmodel.GenClass;
import org.eclipse.emf.codegen.ecore.genmodel.GenClassifier;
import org.eclipse.emf.codegen.ecore.genmodel.GenDataType;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditedMetricTarget;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricRule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Audited Metric Target</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditedMetricTargetImpl#getMetric <em>Metric</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditedMetricTargetImpl#getMetricValueContext <em>Metric Value Context</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenAuditedMetricTargetImpl extends GenAuditableImpl implements GenAuditedMetricTarget {
	/**
	 * The cached value of the '{@link #getMetric() <em>Metric</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetric()
	 * @generated
	 * @ordered
	 */
	protected GenMetricRule metric;

	/**
	 * The cached value of the '{@link #getMetricValueContext() <em>Metric Value Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetricValueContext()
	 * @generated
	 * @ordered
	 */
	protected GenDataType metricValueContext;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenAuditedMetricTargetImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenAuditedMetricTarget();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public GenClassifier getContext() {
		return getMetricValueContext();
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */	
	public GenClass getTargetClass() {
		if(getMetric() == null || getMetric().getTarget() == null) {
			return null;
		}
		GenClassifier genClassifier = getMetric().getTarget().getContext();		
		return (genClassifier instanceof GenClass) ? (GenClass)genClassifier : null;
	}	
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenMetricRule getMetric() {
		if (metric != null && metric.eIsProxy()) {
			InternalEObject oldMetric = (InternalEObject)metric;
			metric = (GenMetricRule)eResolveProxy(oldMetric);
			if (metric != oldMetric) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC, oldMetric, metric));
			}
		}
		return metric;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenMetricRule basicGetMetric() {
		return metric;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMetric(GenMetricRule newMetric) {
		GenMetricRule oldMetric = metric;
		metric = newMetric;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC, oldMetric, metric));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenDataType getMetricValueContext() {
		if (metricValueContext != null && metricValueContext.eIsProxy()) {
			InternalEObject oldMetricValueContext = (InternalEObject)metricValueContext;
			metricValueContext = (GenDataType)eResolveProxy(oldMetricValueContext);
			if (metricValueContext != oldMetricValueContext) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC_VALUE_CONTEXT, oldMetricValueContext, metricValueContext));
			}
		}
		return metricValueContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenDataType basicGetMetricValueContext() {
		return metricValueContext;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMetricValueContext(GenDataType newMetricValueContext) {
		GenDataType oldMetricValueContext = metricValueContext;
		metricValueContext = newMetricValueContext;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC_VALUE_CONTEXT, oldMetricValueContext, metricValueContext));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC:
				if (resolve) return getMetric();
				return basicGetMetric();
			case GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC_VALUE_CONTEXT:
				if (resolve) return getMetricValueContext();
				return basicGetMetricValueContext();
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
			case GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC:
				setMetric((GenMetricRule)newValue);
				return;
			case GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC_VALUE_CONTEXT:
				setMetricValueContext((GenDataType)newValue);
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
			case GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC:
				setMetric((GenMetricRule)null);
				return;
			case GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC_VALUE_CONTEXT:
				setMetricValueContext((GenDataType)null);
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
			case GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC:
				return metric != null;
			case GMFGenPackage.GEN_AUDITED_METRIC_TARGET__METRIC_VALUE_CONTEXT:
				return metricValueContext != null;
		}
		return super.eIsSet(featureID);
	}

} //GenAuditedMetricTargetImpl
