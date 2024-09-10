/**
 * Copyright (c) 2013 CEA LIST.
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
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.NattableaxisconfigurationPackage;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.PasteEObjectConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Paste EObject Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.impl.PasteEObjectConfigurationImpl#getPastedElementId <em>Pasted Element Id</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.impl.PasteEObjectConfigurationImpl#getPasteElementContainementFeature <em>Paste Element Containement Feature</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxisconfiguration.impl.PasteEObjectConfigurationImpl#getAxisIdentifier <em>Axis Identifier</em>}</li>
 * </ul>
 *
 * @generated
 */
public class PasteEObjectConfigurationImpl extends IPasteConfigurationImpl implements PasteEObjectConfiguration {

	/**
	 * The default value of the '{@link #getPastedElementId() <em>Pasted Element Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPastedElementId()
	 * @generated
	 * @ordered
	 */
	protected static final String PASTED_ELEMENT_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPastedElementId() <em>Pasted Element Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPastedElementId()
	 * @generated
	 * @ordered
	 */
	protected String pastedElementId = PASTED_ELEMENT_ID_EDEFAULT;

	/**
	 * The cached value of the '{@link #getPasteElementContainementFeature() <em>Paste Element Containement Feature</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPasteElementContainementFeature()
	 * @generated
	 * @ordered
	 */
	protected EStructuralFeature pasteElementContainementFeature;

	/**
	 * The cached value of the '{@link #getAxisIdentifier() <em>Axis Identifier</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getAxisIdentifier()
	 * @generated
	 * @ordered
	 */
	protected IAxis axisIdentifier;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PasteEObjectConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return NattableaxisconfigurationPackage.Literals.PASTE_EOBJECT_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getPastedElementId() {
		return pastedElementId;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPastedElementId(String newPastedElementId) {
		String oldPastedElementId = pastedElementId;
		pastedElementId = newPastedElementId;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTED_ELEMENT_ID, oldPastedElementId, pastedElementId));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EStructuralFeature getPasteElementContainementFeature() {
		if (pasteElementContainementFeature != null && pasteElementContainementFeature.eIsProxy()) {
			InternalEObject oldPasteElementContainementFeature = (InternalEObject)pasteElementContainementFeature;
			pasteElementContainementFeature = (EStructuralFeature)eResolveProxy(oldPasteElementContainementFeature);
			if (pasteElementContainementFeature != oldPasteElementContainementFeature) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTE_ELEMENT_CONTAINEMENT_FEATURE, oldPasteElementContainementFeature, pasteElementContainementFeature));
			}
		}
		return pasteElementContainementFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EStructuralFeature basicGetPasteElementContainementFeature() {
		return pasteElementContainementFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setPasteElementContainementFeature(EStructuralFeature newPasteElementContainementFeature) {
		EStructuralFeature oldPasteElementContainementFeature = pasteElementContainementFeature;
		pasteElementContainementFeature = newPasteElementContainementFeature;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTE_ELEMENT_CONTAINEMENT_FEATURE, oldPasteElementContainementFeature, pasteElementContainementFeature));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IAxis getAxisIdentifier() {
		return axisIdentifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetAxisIdentifier(IAxis newAxisIdentifier, NotificationChain msgs) {
		IAxis oldAxisIdentifier = axisIdentifier;
		axisIdentifier = newAxisIdentifier;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__AXIS_IDENTIFIER, oldAxisIdentifier, newAxisIdentifier);
			if (msgs == null) msgs = notification; else msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAxisIdentifier(IAxis newAxisIdentifier) {
		if (newAxisIdentifier != axisIdentifier) {
			NotificationChain msgs = null;
			if (axisIdentifier != null)
				msgs = ((InternalEObject)axisIdentifier).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__AXIS_IDENTIFIER, null, msgs);
			if (newAxisIdentifier != null)
				msgs = ((InternalEObject)newAxisIdentifier).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__AXIS_IDENTIFIER, null, msgs);
			msgs = basicSetAxisIdentifier(newAxisIdentifier, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__AXIS_IDENTIFIER, newAxisIdentifier, newAxisIdentifier));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__AXIS_IDENTIFIER:
				return basicSetAxisIdentifier(null, msgs);
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
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTED_ELEMENT_ID:
				return getPastedElementId();
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTE_ELEMENT_CONTAINEMENT_FEATURE:
				if (resolve) return getPasteElementContainementFeature();
				return basicGetPasteElementContainementFeature();
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__AXIS_IDENTIFIER:
				return getAxisIdentifier();
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
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTED_ELEMENT_ID:
				setPastedElementId((String)newValue);
				return;
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTE_ELEMENT_CONTAINEMENT_FEATURE:
				setPasteElementContainementFeature((EStructuralFeature)newValue);
				return;
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__AXIS_IDENTIFIER:
				setAxisIdentifier((IAxis)newValue);
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
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTED_ELEMENT_ID:
				setPastedElementId(PASTED_ELEMENT_ID_EDEFAULT);
				return;
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTE_ELEMENT_CONTAINEMENT_FEATURE:
				setPasteElementContainementFeature((EStructuralFeature)null);
				return;
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__AXIS_IDENTIFIER:
				setAxisIdentifier((IAxis)null);
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
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTED_ELEMENT_ID:
				return PASTED_ELEMENT_ID_EDEFAULT == null ? pastedElementId != null : !PASTED_ELEMENT_ID_EDEFAULT.equals(pastedElementId);
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__PASTE_ELEMENT_CONTAINEMENT_FEATURE:
				return pasteElementContainementFeature != null;
			case NattableaxisconfigurationPackage.PASTE_EOBJECT_CONFIGURATION__AXIS_IDENTIFIER:
				return axisIdentifier != null;
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
		result.append(" (pastedElementId: "); //$NON-NLS-1$
		result.append(pastedElementId);
		result.append(')');
		return result.toString();
	}
} // PasteEObjectConfigurationImpl
