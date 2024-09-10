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
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenChildSideAffixedNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Child Side Affixed Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenChildSideAffixedNodeImpl#getPreferredSideName <em>Preferred Side Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenChildSideAffixedNodeImpl#getLocatorClassName <em>Locator Class Name</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenChildSideAffixedNodeImpl extends GenChildNodeImpl implements GenChildSideAffixedNode {
	/**
	 * The default value of the '{@link #getPreferredSideName() <em>Preferred Side Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferredSideName()
	 * @generated
	 * @ordered
	 */
	protected static final String PREFERRED_SIDE_NAME_EDEFAULT = "NONE";

	/**
	 * The cached value of the '{@link #getPreferredSideName() <em>Preferred Side Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPreferredSideName()
	 * @generated
	 * @ordered
	 */
	protected String preferredSideName = PREFERRED_SIDE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocatorClassName() <em>Locator Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocatorClassName()
	 * @generated
	 * @ordered
	 */
	protected static final String LOCATOR_CLASS_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocatorClassName() <em>Locator Class Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLocatorClassName()
	 * @generated
	 * @ordered
	 */
	protected String locatorClassName = LOCATOR_CLASS_NAME_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenChildSideAffixedNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenChildSideAffixedNode();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPreferredSideName() {
		return preferredSideName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPreferredSideName(String newPreferredSideName) {
		String oldPreferredSideName = preferredSideName;
		preferredSideName = newPreferredSideName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE__PREFERRED_SIDE_NAME, oldPreferredSideName, preferredSideName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getLocatorClassName() {
		return locatorClassName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLocatorClassName(String newLocatorClassName) {
		String oldLocatorClassName = locatorClassName;
		locatorClassName = newLocatorClassName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE__LOCATOR_CLASS_NAME, oldLocatorClassName, locatorClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE__PREFERRED_SIDE_NAME:
				return getPreferredSideName();
			case GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE__LOCATOR_CLASS_NAME:
				return getLocatorClassName();
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
			case GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE__PREFERRED_SIDE_NAME:
				setPreferredSideName((String)newValue);
				return;
			case GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE__LOCATOR_CLASS_NAME:
				setLocatorClassName((String)newValue);
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
			case GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE__PREFERRED_SIDE_NAME:
				setPreferredSideName(PREFERRED_SIDE_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE__LOCATOR_CLASS_NAME:
				setLocatorClassName(LOCATOR_CLASS_NAME_EDEFAULT);
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
			case GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE__PREFERRED_SIDE_NAME:
				return PREFERRED_SIDE_NAME_EDEFAULT == null ? preferredSideName != null : !PREFERRED_SIDE_NAME_EDEFAULT.equals(preferredSideName);
			case GMFGenPackage.GEN_CHILD_SIDE_AFFIXED_NODE__LOCATOR_CLASS_NAME:
				return LOCATOR_CLASS_NAME_EDEFAULT == null ? locatorClassName != null : !LOCATOR_CLASS_NAME_EDEFAULT.equals(locatorClassName);
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
		result.append(" (preferredSideName: ");
		result.append(preferredSideName);
		result.append(", locatorClassName: ");
		result.append(locatorClassName);
		result.append(')');
		return result.toString();
	}

} //GenChildSideAffixedNodeImpl
