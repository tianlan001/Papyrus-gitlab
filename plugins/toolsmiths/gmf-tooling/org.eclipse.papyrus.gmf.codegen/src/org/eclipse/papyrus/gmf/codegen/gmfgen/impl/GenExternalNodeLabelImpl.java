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
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GMFGenPackage;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenExternalNodeLabel;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenFloatingLabel;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen External Node Label</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenExternalNodeLabelImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenExternalNodeLabelImpl#getLocatorClassName <em>Locator Class Name</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenExternalNodeLabelImpl#getLabelVisibilityPreference <em>Label Visibility Preference</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenExternalNodeLabelImpl extends GenNodeLabelImpl implements GenExternalNodeLabel {
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
	 * The cached value of the '{@link #getLabelVisibilityPreference() <em>Label Visibility Preference</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLabelVisibilityPreference()
	 * @generated
	 * @ordered
	 */
	protected GenFloatingLabel labelVisibilityPreference;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenExternalNodeLabelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenExternalNodeLabel();
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
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__NAME, oldName, name));
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
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LOCATOR_CLASS_NAME, oldLocatorClassName, locatorClassName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenFloatingLabel getLabelVisibilityPreference() {
		return labelVisibilityPreference;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLabelVisibilityPreference(GenFloatingLabel newLabelVisibilityPreference, NotificationChain msgs) {
		GenFloatingLabel oldLabelVisibilityPreference = labelVisibilityPreference;
		labelVisibilityPreference = newLabelVisibilityPreference;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LABEL_VISIBILITY_PREFERENCE, oldLabelVisibilityPreference, newLabelVisibilityPreference);
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
	public void setLabelVisibilityPreference(GenFloatingLabel newLabelVisibilityPreference) {
		if (newLabelVisibilityPreference != labelVisibilityPreference) {
			NotificationChain msgs = null;
			if (labelVisibilityPreference != null)
				msgs = ((InternalEObject)labelVisibilityPreference).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LABEL_VISIBILITY_PREFERENCE, null, msgs);
			if (newLabelVisibilityPreference != null)
				msgs = ((InternalEObject)newLabelVisibilityPreference).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LABEL_VISIBILITY_PREFERENCE, null, msgs);
			msgs = basicSetLabelVisibilityPreference(newLabelVisibilityPreference, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LABEL_VISIBILITY_PREFERENCE, newLabelVisibilityPreference, newLabelVisibilityPreference));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LABEL_VISIBILITY_PREFERENCE:
				return basicSetLabelVisibilityPreference(null, msgs);
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
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__NAME:
				return getName();
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LOCATOR_CLASS_NAME:
				return getLocatorClassName();
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LABEL_VISIBILITY_PREFERENCE:
				return getLabelVisibilityPreference();
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
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__NAME:
				setName((String)newValue);
				return;
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LOCATOR_CLASS_NAME:
				setLocatorClassName((String)newValue);
				return;
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LABEL_VISIBILITY_PREFERENCE:
				setLabelVisibilityPreference((GenFloatingLabel)newValue);
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
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LOCATOR_CLASS_NAME:
				setLocatorClassName(LOCATOR_CLASS_NAME_EDEFAULT);
				return;
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LABEL_VISIBILITY_PREFERENCE:
				setLabelVisibilityPreference((GenFloatingLabel)null);
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
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LOCATOR_CLASS_NAME:
				return LOCATOR_CLASS_NAME_EDEFAULT == null ? locatorClassName != null : !LOCATOR_CLASS_NAME_EDEFAULT.equals(locatorClassName);
			case GMFGenPackage.GEN_EXTERNAL_NODE_LABEL__LABEL_VISIBILITY_PREFERENCE:
				return labelVisibilityPreference != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", locatorClassName: ");
		result.append(locatorClassName);
		result.append(')');
		return result.toString();
	}

} //GenExternalNodeLabelImpl
