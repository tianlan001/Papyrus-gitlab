/**
* Copyright (c) 2017 CEA LIST.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  Maged Elaasar - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.core.architecture.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.Concern;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Representation Kind</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.RepresentationKindImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.RepresentationKindImpl#getConcerns <em>Concerns</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.RepresentationKindImpl#getGrayedIcon <em>Grayed Icon</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class RepresentationKindImpl extends ADElementImpl implements RepresentationKind {
	/**
	 * The cached value of the '{@link #getConcerns() <em>Concerns</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConcerns()
	 * @generated
	 * @ordered
	 */
	protected EList<Concern> concerns;

	/**
	 * The default value of the '{@link #getGrayedIcon() <em>Grayed Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGrayedIcon()
	 * @generated
	 * @ordered
	 */
	protected static final String GRAYED_ICON_EDEFAULT = null;
	/**
	 * The cached value of the '{@link #getGrayedIcon() <em>Grayed Icon</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGrayedIcon()
	 * @generated
	 * @ordered
	 */
	protected String grayedIcon = GRAYED_ICON_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RepresentationKindImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.REPRESENTATION_KIND;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<Concern> getConcerns() {
		if (concerns == null) {
			concerns = new EObjectResolvingEList<Concern>(Concern.class, this, ArchitecturePackage.REPRESENTATION_KIND__CONCERNS);
		}
		return concerns;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getGrayedIcon() {
		return grayedIcon;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setGrayedIcon(String newGrayedIcon) {
		String oldGrayedIcon = grayedIcon;
		grayedIcon = newGrayedIcon;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.REPRESENTATION_KIND__GRAYED_ICON, oldGrayedIcon, grayedIcon));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ArchitectureDescriptionLanguage getLanguage() {
		if (eContainerFeatureID() != ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE) return null;
		return (ArchitectureDescriptionLanguage)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLanguage(ArchitectureDescriptionLanguage newLanguage, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject)newLanguage, ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setLanguage(ArchitectureDescriptionLanguage newLanguage) {
		if (newLanguage != eInternalContainer() || (eContainerFeatureID() != ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE && newLanguage != null)) {
			if (EcoreUtil.isAncestor(this, newLanguage))
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			NotificationChain msgs = null;
			if (eInternalContainer() != null)
				msgs = eBasicRemoveFromContainer(msgs);
			if (newLanguage != null)
				msgs = ((InternalEObject)newLanguage).eInverseAdd(this, ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__REPRESENTATION_KINDS, ArchitectureDescriptionLanguage.class, msgs);
			msgs = basicSetLanguage(newLanguage, msgs);
			if (msgs != null) msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE, newLanguage, newLanguage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return basicSetLanguage((ArchitectureDescriptionLanguage)otherEnd, msgs);
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
			case ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE:
				return basicSetLanguage(null, msgs);
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
			case ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE:
				return eInternalContainer().eInverseRemove(this, ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__REPRESENTATION_KINDS, ArchitectureDescriptionLanguage.class, msgs);
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
			case ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE:
				return getLanguage();
			case ArchitecturePackage.REPRESENTATION_KIND__CONCERNS:
				return getConcerns();
			case ArchitecturePackage.REPRESENTATION_KIND__GRAYED_ICON:
				return getGrayedIcon();
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
			case ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE:
				setLanguage((ArchitectureDescriptionLanguage)newValue);
				return;
			case ArchitecturePackage.REPRESENTATION_KIND__CONCERNS:
				getConcerns().clear();
				getConcerns().addAll((Collection<? extends Concern>)newValue);
				return;
			case ArchitecturePackage.REPRESENTATION_KIND__GRAYED_ICON:
				setGrayedIcon((String)newValue);
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
			case ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE:
				setLanguage((ArchitectureDescriptionLanguage)null);
				return;
			case ArchitecturePackage.REPRESENTATION_KIND__CONCERNS:
				getConcerns().clear();
				return;
			case ArchitecturePackage.REPRESENTATION_KIND__GRAYED_ICON:
				setGrayedIcon(GRAYED_ICON_EDEFAULT);
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
			case ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE:
				return getLanguage() != null;
			case ArchitecturePackage.REPRESENTATION_KIND__CONCERNS:
				return concerns != null && !concerns.isEmpty();
			case ArchitecturePackage.REPRESENTATION_KIND__GRAYED_ICON:
				return GRAYED_ICON_EDEFAULT == null ? grayedIcon != null : !GRAYED_ICON_EDEFAULT.equals(grayedIcon);
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
		result.append(" (grayedIcon: "); //$NON-NLS-1$
		result.append(grayedIcon);
		result.append(')');
		return result.toString();
	}

} //RepresentationKindImpl
