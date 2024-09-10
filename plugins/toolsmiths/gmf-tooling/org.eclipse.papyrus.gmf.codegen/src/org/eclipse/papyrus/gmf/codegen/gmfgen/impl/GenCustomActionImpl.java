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

import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContributionManager;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenCustomAction;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Custom Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomActionImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomActionImpl#getQualifiedClassName <em>Qualified Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomActionImpl#isGenerateBoilerplate <em>Generate Boilerplate</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenCustomActionImpl#getName <em>Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenCustomActionImpl extends EObjectImpl implements GenCustomAction {
	/**
	 * The default value of the '{@link #getQualifiedClassName() <em>Qualified Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String QUALIFIED_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getQualifiedClassName() <em>Qualified Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getQualifiedClassName()
	 * @generated
	 * @ordered
	 */
	protected String qualifiedClassName = QUALIFIED_CLASS_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #isGenerateBoilerplate() <em>Generate Boilerplate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateBoilerplate()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATE_BOILERPLATE_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isGenerateBoilerplate() <em>Generate Boilerplate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerateBoilerplate()
	 * @generated
	 * @ordered
	 */
	protected boolean generateBoilerplate = GENERATE_BOILERPLATE_EDEFAULT;

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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenCustomActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenCustomAction();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenContributionManager getOwner() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_CUSTOM_ACTION__OWNER) return null;
		return (GenContributionManager)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getQualifiedClassName() {
		return qualifiedClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setQualifiedClassName(String newQualifiedClassName) {
		String oldQualifiedClassName = qualifiedClassName;
		qualifiedClassName = newQualifiedClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_ACTION__QUALIFIED_CLASS_NAME, oldQualifiedClassName, qualifiedClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean isGenerateBoilerplate() {
		return generateBoilerplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGenerateBoilerplate(boolean newGenerateBoilerplate) {
		boolean oldGenerateBoilerplate = generateBoilerplate;
		generateBoilerplate = newGenerateBoilerplate;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_ACTION__GENERATE_BOILERPLATE, oldGenerateBoilerplate, generateBoilerplate));
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
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CUSTOM_ACTION__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_CUSTOM_ACTION__OWNER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.GEN_CUSTOM_ACTION__OWNER, msgs);
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
			case GMFGenPackage.GEN_CUSTOM_ACTION__OWNER:
				return eBasicSetContainer(null, GMFGenPackage.GEN_CUSTOM_ACTION__OWNER, msgs);
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
			case GMFGenPackage.GEN_CUSTOM_ACTION__OWNER:
				return eInternalContainer().eInverseRemove(this, GMFGenPackage.GEN_CONTRIBUTION_MANAGER__ITEMS, GenContributionManager.class, msgs);
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
			case GMFGenPackage.GEN_CUSTOM_ACTION__OWNER:
				return getOwner();
			case GMFGenPackage.GEN_CUSTOM_ACTION__QUALIFIED_CLASS_NAME:
				return getQualifiedClassName();
			case GMFGenPackage.GEN_CUSTOM_ACTION__GENERATE_BOILERPLATE:
				return isGenerateBoilerplate();
			case GMFGenPackage.GEN_CUSTOM_ACTION__NAME:
				return getName();
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
			case GMFGenPackage.GEN_CUSTOM_ACTION__QUALIFIED_CLASS_NAME:
				setQualifiedClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_CUSTOM_ACTION__GENERATE_BOILERPLATE:
				setGenerateBoilerplate((Boolean)newValue);
				return;
			case GMFGenPackage.GEN_CUSTOM_ACTION__NAME:
				setName((String)newValue);
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
			case GMFGenPackage.GEN_CUSTOM_ACTION__QUALIFIED_CLASS_NAME:
				setQualifiedClassName(QUALIFIED_CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CUSTOM_ACTION__GENERATE_BOILERPLATE:
				setGenerateBoilerplate(GENERATE_BOILERPLATE_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CUSTOM_ACTION__NAME:
				setName(NAME_EDEFAULT);
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
			case GMFGenPackage.GEN_CUSTOM_ACTION__OWNER:
				return getOwner() != null;
			case GMFGenPackage.GEN_CUSTOM_ACTION__QUALIFIED_CLASS_NAME:
				return QUALIFIED_CLASS_NAME_EDEFAULT == null ? qualifiedClassName != null : !QUALIFIED_CLASS_NAME_EDEFAULT.equals(qualifiedClassName);
			case GMFGenPackage.GEN_CUSTOM_ACTION__GENERATE_BOILERPLATE:
				return generateBoilerplate != GENERATE_BOILERPLATE_EDEFAULT;
			case GMFGenPackage.GEN_CUSTOM_ACTION__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
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
		result.append(" (qualifiedClassName: ");
		result.append(qualifiedClassName);
		result.append(", generateBoilerplate: ");
		result.append(generateBoilerplate);
		result.append(", name: ");
		result.append(name);
		result.append(')');
		return result.toString();
	}

} //GenCustomActionImpl
