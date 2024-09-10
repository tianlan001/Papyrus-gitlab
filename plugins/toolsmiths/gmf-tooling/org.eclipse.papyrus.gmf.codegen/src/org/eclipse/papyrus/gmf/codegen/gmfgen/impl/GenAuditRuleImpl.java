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
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditContainer;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRoot;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditRule;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenAuditable;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenConstraint;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDiagram;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenDomainAttributeTarget;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenLanguage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenSeverity;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Audit Rule</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRuleImpl#getRoot <em>Root</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRuleImpl#getId <em>Id</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRuleImpl#getRule <em>Rule</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRuleImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRuleImpl#getMessage <em>Message</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRuleImpl#getSeverity <em>Severity</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRuleImpl#isUseInLiveMode <em>Use In Live Mode</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRuleImpl#isRequiresConstraintAdapter <em>Requires Constraint Adapter</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenAuditRuleImpl#getCategory <em>Category</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenAuditRuleImpl extends GenRuleBaseImpl implements GenAuditRule {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected String id = ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRule() <em>Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRule()
	 * @generated
	 * @ordered
	 */
	protected GenConstraint rule;

	/**
	 * The cached value of the '{@link #getTarget() <em>Target</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTarget()
	 * @generated
	 * @ordered
	 */
	protected GenAuditable target;

	/**
	 * The default value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected static final String MESSAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getMessage() <em>Message</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMessage()
	 * @generated
	 * @ordered
	 */
	protected String message = MESSAGE_EDEFAULT;

	/**
	 * The default value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected static final GenSeverity SEVERITY_EDEFAULT = GenSeverity.ERROR_LITERAL;

	/**
	 * The cached value of the '{@link #getSeverity() <em>Severity</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSeverity()
	 * @generated
	 * @ordered
	 */
	protected GenSeverity severity = SEVERITY_EDEFAULT;

	/**
	 * The default value of the '{@link #isUseInLiveMode() <em>Use In Live Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseInLiveMode()
	 * @generated
	 * @ordered
	 */
	protected static final boolean USE_IN_LIVE_MODE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isUseInLiveMode() <em>Use In Live Mode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isUseInLiveMode()
	 * @generated
	 * @ordered
	 */
	protected boolean useInLiveMode = USE_IN_LIVE_MODE_EDEFAULT;

	/**
	 * The default value of the '{@link #isRequiresConstraintAdapter() <em>Requires Constraint Adapter</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isRequiresConstraintAdapter()
	 * @generated
	 * @ordered
	 */
	protected static final boolean REQUIRES_CONSTRAINT_ADAPTER_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #getCategory() <em>Category</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCategory()
	 * @generated
	 * @ordered
	 */
	protected GenAuditContainer category;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenAuditRuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenAuditRule();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenAuditRoot getRoot() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_AUDIT_RULE__ROOT) return null;
		return (GenAuditRoot)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenAuditable getTarget() {
		return target;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTarget(GenAuditable newTarget, NotificationChain msgs) {
		GenAuditable oldTarget = target;
		target = newTarget;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDIT_RULE__TARGET, oldTarget, newTarget);
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
	public void setTarget(GenAuditable newTarget) {
		if (newTarget != target) {
			NotificationChain msgs = null;
			if (target != null)
				msgs = ((InternalEObject)target).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_AUDIT_RULE__TARGET, null, msgs);
			if (newTarget != null)
				msgs = ((InternalEObject)newTarget).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_AUDIT_RULE__TARGET, null, msgs);
			msgs = basicSetTarget(newTarget, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDIT_RULE__TARGET, newTarget, newTarget));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getId() {
		return id;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		String oldId = id;
		id = newId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDIT_RULE__ID, oldId, id));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getMessage() {
		return message;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMessage(String newMessage) {
		String oldMessage = message;
		message = newMessage;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDIT_RULE__MESSAGE, oldMessage, message));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenConstraint getRule() {
		if (rule != null && rule.eIsProxy()) {
			InternalEObject oldRule = (InternalEObject)rule;
			rule = (GenConstraint)eResolveProxy(oldRule);
			if (rule != oldRule) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.GEN_AUDIT_RULE__RULE, oldRule, rule));
			}
		}
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenConstraint basicGetRule() {
		return rule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRule(GenConstraint newRule) {
		GenConstraint oldRule = rule;
		rule = newRule;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDIT_RULE__RULE, oldRule, rule));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenSeverity getSeverity() {
		return severity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setSeverity(GenSeverity newSeverity) {
		GenSeverity oldSeverity = severity;
		severity = newSeverity == null ? SEVERITY_EDEFAULT : newSeverity;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDIT_RULE__SEVERITY, oldSeverity, severity));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isUseInLiveMode() {
		return useInLiveMode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setUseInLiveMode(boolean newUseInLiveMode) {
		boolean oldUseInLiveMode = useInLiveMode;
		useInLiveMode = newUseInLiveMode;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDIT_RULE__USE_IN_LIVE_MODE, oldUseInLiveMode, useInLiveMode));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getConstraintAdapterClassName() {
		if(getDiagram() == null || getConstraintAdapterLocalClassName() == null) {
			return null;
		}		
		return getDiagram().getValidationProviderClassName() + "$" + getConstraintAdapterLocalClassName(); //$NON-NLS-1$
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public String getConstraintAdapterLocalClassName() {
		return "Adapter" + (getRoot().getRules().indexOf(this) + 1); //$NON-NLS-1$ 
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */	
	public String getConstraintAdapterQualifiedClassName() {
		if(getDiagram() == null) {
			return null;
		}
		return getDiagram().getValidationProviderQualifiedClassName() + "$" + getConstraintAdapterLocalClassName(); //$NON-NLS-1$
	}
	
	private GenDiagram getDiagram() {
		if(getRoot() != null) {
			return getRoot().getEditorGen().getDiagram();
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */	
	public boolean isRequiresConstraintAdapter() {
		if(getRule() != null) {
			if(getRule().getProvider().getLanguage() != GenLanguage.OCL_LITERAL || getTarget() instanceof GenDomainAttributeTarget) {
				return true;
			} else if(getTarget() != null && getTarget().getContext() != null) {
				return getTarget().getContext() != getTarget().getTargetClass();
			}
		}
		return false;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenAuditContainer getCategory() {
		if (category != null && category.eIsProxy()) {
			InternalEObject oldCategory = (InternalEObject)category;
			category = (GenAuditContainer)eResolveProxy(oldCategory);
			if (category != oldCategory) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.GEN_AUDIT_RULE__CATEGORY, oldCategory, category));
			}
		}
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenAuditContainer basicGetCategory() {
		return category;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetCategory(GenAuditContainer newCategory, NotificationChain msgs) {
		GenAuditContainer oldCategory = category;
		category = newCategory;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDIT_RULE__CATEGORY, oldCategory, newCategory);
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
	public void setCategory(GenAuditContainer newCategory) {
		if (newCategory != category) {
			NotificationChain msgs = null;
			if (category != null)
				msgs = ((InternalEObject)category).eInverseRemove(this, GMFGenPackage.GEN_AUDIT_CONTAINER__AUDITS, GenAuditContainer.class, msgs);
			if (newCategory != null)
				msgs = ((InternalEObject)newCategory).eInverseAdd(this, GMFGenPackage.GEN_AUDIT_CONTAINER__AUDITS, GenAuditContainer.class, msgs);
			msgs = basicSetCategory(newCategory, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_AUDIT_RULE__CATEGORY, newCategory, newCategory));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_AUDIT_RULE__ROOT:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.GEN_AUDIT_RULE__ROOT, msgs);
			case GMFGenPackage.GEN_AUDIT_RULE__CATEGORY:
				if (category != null)
					msgs = ((InternalEObject)category).eInverseRemove(this, GMFGenPackage.GEN_AUDIT_CONTAINER__AUDITS, GenAuditContainer.class, msgs);
				return basicSetCategory((GenAuditContainer)otherEnd, msgs);
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
			case GMFGenPackage.GEN_AUDIT_RULE__ROOT:
				return eBasicSetContainer(null, GMFGenPackage.GEN_AUDIT_RULE__ROOT, msgs);
			case GMFGenPackage.GEN_AUDIT_RULE__TARGET:
				return basicSetTarget(null, msgs);
			case GMFGenPackage.GEN_AUDIT_RULE__CATEGORY:
				return basicSetCategory(null, msgs);
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
			case GMFGenPackage.GEN_AUDIT_RULE__ROOT:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_AUDIT_ROOT__RULES, GenAuditRoot.class, msgs);
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
			case GMFGenPackage.GEN_AUDIT_RULE__ROOT:
				return getRoot();
			case GMFGenPackage.GEN_AUDIT_RULE__ID:
				return getId();
			case GMFGenPackage.GEN_AUDIT_RULE__RULE:
				if (resolve) return getRule();
				return basicGetRule();
			case GMFGenPackage.GEN_AUDIT_RULE__TARGET:
				return getTarget();
			case GMFGenPackage.GEN_AUDIT_RULE__MESSAGE:
				return getMessage();
			case GMFGenPackage.GEN_AUDIT_RULE__SEVERITY:
				return getSeverity();
			case GMFGenPackage.GEN_AUDIT_RULE__USE_IN_LIVE_MODE:
				return isUseInLiveMode();
			case GMFGenPackage.GEN_AUDIT_RULE__REQUIRES_CONSTRAINT_ADAPTER:
				return isRequiresConstraintAdapter();
			case GMFGenPackage.GEN_AUDIT_RULE__CATEGORY:
				if (resolve) return getCategory();
				return basicGetCategory();
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
			case GMFGenPackage.GEN_AUDIT_RULE__ID:
				setId((String)newValue);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__RULE:
				setRule((GenConstraint)newValue);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__TARGET:
				setTarget((GenAuditable)newValue);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__MESSAGE:
				setMessage((String)newValue);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__SEVERITY:
				setSeverity((GenSeverity)newValue);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__USE_IN_LIVE_MODE:
				setUseInLiveMode((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__CATEGORY:
				setCategory((GenAuditContainer)newValue);
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
			case GMFGenPackage.GEN_AUDIT_RULE__ID:
				setId(ID_EDEFAULT);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__RULE:
				setRule((GenConstraint)null);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__TARGET:
				setTarget((GenAuditable)null);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__MESSAGE:
				setMessage(MESSAGE_EDEFAULT);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__SEVERITY:
				setSeverity(SEVERITY_EDEFAULT);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__USE_IN_LIVE_MODE:
				setUseInLiveMode(USE_IN_LIVE_MODE_EDEFAULT);
				return;
			case GMFGenPackage.GEN_AUDIT_RULE__CATEGORY:
				setCategory((GenAuditContainer)null);
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
			case GMFGenPackage.GEN_AUDIT_RULE__ROOT:
				return getRoot() != null;
			case GMFGenPackage.GEN_AUDIT_RULE__ID:
				return ID_EDEFAULT == null ? id != null : !ID_EDEFAULT.equals(id);
			case GMFGenPackage.GEN_AUDIT_RULE__RULE:
				return rule != null;
			case GMFGenPackage.GEN_AUDIT_RULE__TARGET:
				return target != null;
			case GMFGenPackage.GEN_AUDIT_RULE__MESSAGE:
				return MESSAGE_EDEFAULT == null ? message != null : !MESSAGE_EDEFAULT.equals(message);
			case GMFGenPackage.GEN_AUDIT_RULE__SEVERITY:
				return severity != SEVERITY_EDEFAULT;
			case GMFGenPackage.GEN_AUDIT_RULE__USE_IN_LIVE_MODE:
				return useInLiveMode != USE_IN_LIVE_MODE_EDEFAULT;
			case GMFGenPackage.GEN_AUDIT_RULE__REQUIRES_CONSTRAINT_ADAPTER:
				return isRequiresConstraintAdapter() != REQUIRES_CONSTRAINT_ADAPTER_EDEFAULT;
			case GMFGenPackage.GEN_AUDIT_RULE__CATEGORY:
				return category != null;
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
		result.append(" (id: ");
		result.append(id);
		result.append(", message: ");
		result.append(message);
		result.append(", severity: ");
		result.append(severity);
		result.append(", useInLiveMode: ");
		result.append(useInLiveMode);
		result.append(')');
		return result.toString();
	}

} //GenAuditRuleImpl
