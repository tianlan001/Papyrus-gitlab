/**
 * Copyright (c) 2014, 2020 CEA LIST, Christian W. Damus, and others.
 * 
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 568853
 */
package org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.infra.types.rulebased.impl.RuleConfigurationImpl;
import org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfiguration;
import org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.InvariantStereotypeRuleConfigurationPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Invariant Stereotype Rule Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl.InvariantStereotypeRuleConfigurationImpl#getStereotypeQualifiedName <em>Stereotype Qualified Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl.InvariantStereotypeRuleConfigurationImpl#getRequiredProfile <em>Required Profile</em>}</li>
 *   <li>{@link org.eclipse.papyrus.uml.types.core.rules.invariantstereotype.impl.InvariantStereotypeRuleConfigurationImpl#isStrict <em>Strict</em>}</li>
 * </ul>
 *
 * @generated
 */
public class InvariantStereotypeRuleConfigurationImpl extends RuleConfigurationImpl implements InvariantStereotypeRuleConfiguration {
	/**
	 * The default value of the '{@link #getStereotypeQualifiedName() <em>Stereotype Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStereotypeQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected static final String STEREOTYPE_QUALIFIED_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getStereotypeQualifiedName() <em>Stereotype Qualified Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStereotypeQualifiedName()
	 * @generated
	 * @ordered
	 */
	protected String stereotypeQualifiedName = STEREOTYPE_QUALIFIED_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getRequiredProfile() <em>Required Profile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredProfile()
	 * @generated
	 * @ordered
	 */
	protected static final String REQUIRED_PROFILE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRequiredProfile() <em>Required Profile</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRequiredProfile()
	 * @generated
	 * @ordered
	 */
	protected String requiredProfile = REQUIRED_PROFILE_EDEFAULT;

	/**
	 * The default value of the '{@link #isStrict() <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrict()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRICT_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isStrict() <em>Strict</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStrict()
	 * @generated
	 * @ordered
	 */
	protected boolean strict = STRICT_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected InvariantStereotypeRuleConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return InvariantStereotypeRuleConfigurationPackage.Literals.INVARIANT_STEREOTYPE_RULE_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getStereotypeQualifiedName() {
		return stereotypeQualifiedName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStereotypeQualifiedName(String newStereotypeQualifiedName) {
		String oldStereotypeQualifiedName = stereotypeQualifiedName;
		stereotypeQualifiedName = newStereotypeQualifiedName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME, oldStereotypeQualifiedName, stereotypeQualifiedName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getRequiredProfile() {
		return requiredProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setRequiredProfile(String newRequiredProfile) {
		String oldRequiredProfile = requiredProfile;
		requiredProfile = newRequiredProfile;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__REQUIRED_PROFILE, oldRequiredProfile, requiredProfile));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isStrict() {
		return strict;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setStrict(boolean newStrict) {
		boolean oldStrict = strict;
		strict = newStrict;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STRICT, oldStrict, strict));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME:
			return getStereotypeQualifiedName();
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__REQUIRED_PROFILE:
			return getRequiredProfile();
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STRICT:
			return isStrict();
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
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME:
			setStereotypeQualifiedName((String) newValue);
			return;
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__REQUIRED_PROFILE:
			setRequiredProfile((String) newValue);
			return;
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STRICT:
			setStrict((Boolean) newValue);
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
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME:
			setStereotypeQualifiedName(STEREOTYPE_QUALIFIED_NAME_EDEFAULT);
			return;
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__REQUIRED_PROFILE:
			setRequiredProfile(REQUIRED_PROFILE_EDEFAULT);
			return;
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STRICT:
			setStrict(STRICT_EDEFAULT);
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
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STEREOTYPE_QUALIFIED_NAME:
			return STEREOTYPE_QUALIFIED_NAME_EDEFAULT == null ? stereotypeQualifiedName != null : !STEREOTYPE_QUALIFIED_NAME_EDEFAULT.equals(stereotypeQualifiedName);
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__REQUIRED_PROFILE:
			return REQUIRED_PROFILE_EDEFAULT == null ? requiredProfile != null : !REQUIRED_PROFILE_EDEFAULT.equals(requiredProfile);
		case InvariantStereotypeRuleConfigurationPackage.INVARIANT_STEREOTYPE_RULE_CONFIGURATION__STRICT:
			return strict != STRICT_EDEFAULT;
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
		if (eIsProxy())
			return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (stereotypeQualifiedName: ");
		result.append(stereotypeQualifiedName);
		result.append(", requiredProfile: ");
		result.append(requiredProfile);
		result.append(", strict: ");
		result.append(strict);
		result.append(')');
		return result.toString();
	}

} //InvariantStereotypeRuleConfigurationImpl
