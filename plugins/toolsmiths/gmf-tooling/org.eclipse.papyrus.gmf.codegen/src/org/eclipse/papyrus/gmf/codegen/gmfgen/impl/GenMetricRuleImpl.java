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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMeasurable;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricContainer;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenMetricRule;
import org.eclipse.papyrus.gmf.codegen.gmfgen.ValueExpression;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Metric Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenMetricRuleImpl#getKey <em>Key</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenMetricRuleImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenMetricRuleImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenMetricRuleImpl#getLowLimit <em>Low Limit</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenMetricRuleImpl#getHighLimit <em>High Limit</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenMetricRuleImpl#getContainer <em>Container</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenMetricRuleImpl extends GenRuleBaseImpl implements GenMetricRule {
	/**
	 * The default value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected static final String KEY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
	protected String key = KEY_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected ValueExpression rule;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected GenMeasurable target;

	/**
	 * The default value of the '{@link #getLowLimit() <em>Low Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowLimit()
	 * @generated
	 * @ordered
	 */
	protected static final Double LOW_LIMIT_EDEFAULT= null;

	/**
	 * The cached value of the '{@link #getLowLimit() <em>Low Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLowLimit()
	 * @generated
	 * @ordered
	 */
	protected Double lowLimit = LOW_LIMIT_EDEFAULT;

	/**
	 * The default value of the '{@link #getHighLimit() <em>High Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHighLimit()
	 * @generated
	 * @ordered
	 */
	protected static final Double HIGH_LIMIT_EDEFAULT= null;

	/**
	 * The cached value of the '{@link #getHighLimit() <em>High Limit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHighLimit()
	 * @generated
	 * @ordered
	 */
	protected Double highLimit = HIGH_LIMIT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenMetricRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenMetricRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getKey() {
		return key;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__KEY, oldKey, key));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ValueExpression getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject)rule;
			rule = (ValueExpression)eResolveProxy(oldRule);
			if (rule != oldRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.GEN_METRIC_RULE__RULE, oldRule, rule));
			}
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueExpression basicGetRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRule(ValueExpression newRule) {
		ValueExpression oldRule = rule;
		rule = newRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__RULE, oldRule, rule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenMeasurable getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(GenMeasurable newTarget, NotificationChain msgs) {
		GenMeasurable oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__TARGET, oldTarget, newTarget);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTarget(GenMeasurable newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_METRIC_RULE__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_METRIC_RULE__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenMetricContainer getContainer() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_METRIC_RULE__CONTAINER) return null;
		return (GenMetricContainer)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetContainer(GenMetricContainer newContainer, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newContainer, GMFGenPackage.GEN_METRIC_RULE__CONTAINER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setContainer(GenMetricContainer newContainer) {
		if (newContainer != eInternalContainer() || (eContainerFeatureID() != GMFGenPackage.GEN_METRIC_RULE__CONTAINER && newContainer != null)) {
			if (EcoreUtil.isAncestor(this, newContainer))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString());
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newContainer != null)
				msgs = ((InternalEObject)newContainer).eInverseAdd(this, GMFGenPackage.GEN_METRIC_CONTAINER__METRICS, GenMetricContainer.class, msgs);
			msgs = basicSetContainer(newContainer, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__CONTAINER, newContainer, newContainer));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double getLowLimit() {
		return lowLimit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLowLimit(Double newLowLimit) {
		Double oldLowLimit = lowLimit;
		lowLimit = newLowLimit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__LOW_LIMIT, oldLowLimit, lowLimit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Double getHighLimit() {
		return highLimit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setHighLimit(Double newHighLimit) {
		Double oldHighLimit = highLimit;
		highLimit = newHighLimit;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_METRIC_RULE__HIGH_LIMIT, oldHighLimit, highLimit));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetContainer((GenMetricContainer)otherEnd, msgs);
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
			case GMFGenPackage.GEN_METRIC_RULE__TARGET:
				return basicSetTarget(null, msgs);
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				return basicSetContainer(null, msgs);
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
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_METRIC_CONTAINER__METRICS, GenMetricContainer.class, msgs);
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
			case GMFGenPackage.GEN_METRIC_RULE__KEY:
				return getKey();
			case GMFGenPackage.GEN_METRIC_RULE__RULE:
				if (resolve) return getRule();
				return basicGetRule();
			case GMFGenPackage.GEN_METRIC_RULE__TARGET:
				return getTarget();
			case GMFGenPackage.GEN_METRIC_RULE__LOW_LIMIT:
				return getLowLimit();
			case GMFGenPackage.GEN_METRIC_RULE__HIGH_LIMIT:
				return getHighLimit();
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				return getContainer();
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
			case GMFGenPackage.GEN_METRIC_RULE__KEY:
				setKey((String)newValue);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__RULE:
				setRule((ValueExpression)newValue);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__TARGET:
				setTarget((GenMeasurable)newValue);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__LOW_LIMIT:
				setLowLimit((Double)newValue);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__HIGH_LIMIT:
				setHighLimit((Double)newValue);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				setContainer((GenMetricContainer)newValue);
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
			case GMFGenPackage.GEN_METRIC_RULE__KEY:
				setKey(KEY_EDEFAULT);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__RULE:
				setRule((ValueExpression)null);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__TARGET:
				setTarget((GenMeasurable)null);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__LOW_LIMIT:
				setLowLimit(LOW_LIMIT_EDEFAULT);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__HIGH_LIMIT:
				setHighLimit(HIGH_LIMIT_EDEFAULT);
				return;
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				setContainer((GenMetricContainer)null);
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
			case GMFGenPackage.GEN_METRIC_RULE__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
			case GMFGenPackage.GEN_METRIC_RULE__RULE:
				return rule != null;
			case GMFGenPackage.GEN_METRIC_RULE__TARGET:
				return target != null;
			case GMFGenPackage.GEN_METRIC_RULE__LOW_LIMIT:
				return LOW_LIMIT_EDEFAULT == null ? lowLimit != null : !LOW_LIMIT_EDEFAULT.equals(lowLimit);
			case GMFGenPackage.GEN_METRIC_RULE__HIGH_LIMIT:
				return HIGH_LIMIT_EDEFAULT == null ? highLimit != null : !HIGH_LIMIT_EDEFAULT.equals(highLimit);
			case GMFGenPackage.GEN_METRIC_RULE__CONTAINER:
				return getContainer() != null;
		}
		return super.eIsSet(featureID);
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
		result.append(" (key: ");
		result.append(key);
		result.append(", lowLimit: ");
		result.append(lowLimit);
		result.append(", highLimit: ");
		result.append(highLimit);
		result.append(')');
		return result.toString();
	}

} //GenMetricRuleImpl
