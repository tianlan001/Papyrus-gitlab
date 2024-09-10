/**
 *  Copyright (c) 2020 CEA LIST and others.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  Vincent LORENZO - Initial API and implementation
 */
package org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationConfigurationPackage;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.CustomizationReference;
import org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.EMFFacetTreeViewerConfiguration;

import org.eclipse.papyrus.infra.core.architecture.impl.TreeViewerConfigurationImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>EMF Facet Tree Viewer Configuration</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.EMFFacetTreeViewerConfigurationImpl#getCustomizationReferences <em>Customization References</em>}</li>
 * <li>{@link org.eclipse.papyrus.emf.facet.architecture.customizationconfiguration.impl.EMFFacetTreeViewerConfigurationImpl#getExtends <em>Extends</em>}</li>
 * </ul>
 *
 * @generated
 */
public class EMFFacetTreeViewerConfigurationImpl extends TreeViewerConfigurationImpl implements EMFFacetTreeViewerConfiguration {
	/**
	 * The cached value of the '{@link #getCustomizationReferences() <em>Customization References</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCustomizationReferences()
	 * @generated
	 * @ordered
	 */
	protected EList<CustomizationReference> customizationReferences;

	/**
	 * The cached value of the '{@link #getExtends() <em>Extends</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getExtends()
	 * @generated
	 * @ordered
	 */
	protected EMFFacetTreeViewerConfiguration extends_;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected EMFFacetTreeViewerConfigurationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CustomizationConfigurationPackage.Literals.EMF_FACET_TREE_VIEWER_CONFIGURATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<CustomizationReference> getCustomizationReferences() {
		if (customizationReferences == null) {
			customizationReferences = new EObjectContainmentEList<>(CustomizationReference.class, this, CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__CUSTOMIZATION_REFERENCES);
		}
		return customizationReferences;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EMFFacetTreeViewerConfiguration getExtends() {
		if (extends_ != null && extends_.eIsProxy()) {
			InternalEObject oldExtends = (InternalEObject) extends_;
			extends_ = (EMFFacetTreeViewerConfiguration) eResolveProxy(oldExtends);
			if (extends_ != oldExtends) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__EXTENDS, oldExtends, extends_));
				}
			}
		}
		return extends_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public EMFFacetTreeViewerConfiguration basicGetExtends() {
		return extends_;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setExtends(EMFFacetTreeViewerConfiguration newExtends) {
		EMFFacetTreeViewerConfiguration oldExtends = extends_;
		extends_ = newExtends;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__EXTENDS, oldExtends, extends_));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__CUSTOMIZATION_REFERENCES:
			return ((InternalEList<?>) getCustomizationReferences()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__CUSTOMIZATION_REFERENCES:
			return getCustomizationReferences();
		case CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__EXTENDS:
			if (resolve) {
				return getExtends();
			}
			return basicGetExtends();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__CUSTOMIZATION_REFERENCES:
			getCustomizationReferences().clear();
			getCustomizationReferences().addAll((Collection<? extends CustomizationReference>) newValue);
			return;
		case CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__EXTENDS:
			setExtends((EMFFacetTreeViewerConfiguration) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__CUSTOMIZATION_REFERENCES:
			getCustomizationReferences().clear();
			return;
		case CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__EXTENDS:
			setExtends((EMFFacetTreeViewerConfiguration) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__CUSTOMIZATION_REFERENCES:
			return customizationReferences != null && !customizationReferences.isEmpty();
		case CustomizationConfigurationPackage.EMF_FACET_TREE_VIEWER_CONFIGURATION__EXTENDS:
			return extends_ != null;
		}
		return super.eIsSet(featureID);
	}

} // EMFFacetTreeViewerConfigurationImpl
