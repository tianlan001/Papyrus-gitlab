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
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.core.architecture.ArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.ArchitecturePackage;
import org.eclipse.papyrus.infra.core.architecture.RepresentationKind;
import org.eclipse.papyrus.infra.core.architecture.TreeViewerConfiguration;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Description Language</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureDescriptionLanguageImpl#getRepresentationKinds <em>Representation Kinds</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureDescriptionLanguageImpl#getMetamodel <em>Metamodel</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureDescriptionLanguageImpl#getProfiles <em>Profiles</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.core.architecture.impl.ArchitectureDescriptionLanguageImpl#getTreeViewerConfigurations <em>Tree Viewer Configurations</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ArchitectureDescriptionLanguageImpl extends ArchitectureContextImpl implements ArchitectureDescriptionLanguage {
	/**
	 * The cached value of the '{@link #getRepresentationKinds() <em>Representation Kinds</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRepresentationKinds()
	 * @generated
	 * @ordered
	 */
	protected EList<RepresentationKind> representationKinds;

	/**
	 * The cached value of the '{@link #getMetamodel() <em>Metamodel</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMetamodel()
	 * @generated
	 * @ordered
	 */
	protected EPackage metamodel;

	/**
	 * The cached value of the '{@link #getProfiles() <em>Profiles</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProfiles()
	 * @generated
	 * @ordered
	 */
	protected EList<EPackage> profiles;

	/**
	 * The cached value of the '{@link #getTreeViewerConfigurations() <em>Tree Viewer Configurations</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTreeViewerConfigurations()
	 * @generated
	 * @ordered
	 */
	protected EList<TreeViewerConfiguration> treeViewerConfigurations;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArchitectureDescriptionLanguageImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.ARCHITECTURE_DESCRIPTION_LANGUAGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<RepresentationKind> getRepresentationKinds() {
		if (representationKinds == null) {
			representationKinds = new EObjectContainmentWithInverseEList<RepresentationKind>(RepresentationKind.class, this, ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__REPRESENTATION_KINDS, ArchitecturePackage.REPRESENTATION_KIND__LANGUAGE);
		}
		return representationKinds;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EPackage getMetamodel() {
		if (metamodel != null && metamodel.eIsProxy()) {
			InternalEObject oldMetamodel = (InternalEObject)metamodel;
			metamodel = (EPackage)eResolveProxy(oldMetamodel);
			if (metamodel != oldMetamodel) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__METAMODEL, oldMetamodel, metamodel));
			}
		}
		return metamodel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EPackage basicGetMetamodel() {
		return metamodel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setMetamodel(EPackage newMetamodel) {
		EPackage oldMetamodel = metamodel;
		metamodel = newMetamodel;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__METAMODEL, oldMetamodel, metamodel));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<EPackage> getProfiles() {
		if (profiles == null) {
			profiles = new EObjectResolvingEList<EPackage>(EPackage.class, this, ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__PROFILES);
		}
		return profiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<TreeViewerConfiguration> getTreeViewerConfigurations() {
		if (treeViewerConfigurations == null) {
			treeViewerConfigurations = new EObjectContainmentEList<TreeViewerConfiguration>(TreeViewerConfiguration.class, this, ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__TREE_VIEWER_CONFIGURATIONS);
		}
		return treeViewerConfigurations;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__REPRESENTATION_KINDS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getRepresentationKinds()).basicAdd(otherEnd, msgs);
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
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__REPRESENTATION_KINDS:
				return ((InternalEList<?>)getRepresentationKinds()).basicRemove(otherEnd, msgs);
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__TREE_VIEWER_CONFIGURATIONS:
				return ((InternalEList<?>)getTreeViewerConfigurations()).basicRemove(otherEnd, msgs);
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
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__REPRESENTATION_KINDS:
				return getRepresentationKinds();
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__METAMODEL:
				if (resolve) return getMetamodel();
				return basicGetMetamodel();
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__PROFILES:
				return getProfiles();
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__TREE_VIEWER_CONFIGURATIONS:
				return getTreeViewerConfigurations();
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
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__REPRESENTATION_KINDS:
				getRepresentationKinds().clear();
				getRepresentationKinds().addAll((Collection<? extends RepresentationKind>)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__METAMODEL:
				setMetamodel((EPackage)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__PROFILES:
				getProfiles().clear();
				getProfiles().addAll((Collection<? extends EPackage>)newValue);
				return;
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__TREE_VIEWER_CONFIGURATIONS:
				getTreeViewerConfigurations().clear();
				getTreeViewerConfigurations().addAll((Collection<? extends TreeViewerConfiguration>)newValue);
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
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__REPRESENTATION_KINDS:
				getRepresentationKinds().clear();
				return;
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__METAMODEL:
				setMetamodel((EPackage)null);
				return;
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__PROFILES:
				getProfiles().clear();
				return;
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__TREE_VIEWER_CONFIGURATIONS:
				getTreeViewerConfigurations().clear();
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
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__REPRESENTATION_KINDS:
				return representationKinds != null && !representationKinds.isEmpty();
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__METAMODEL:
				return metamodel != null;
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__PROFILES:
				return profiles != null && !profiles.isEmpty();
			case ArchitecturePackage.ARCHITECTURE_DESCRIPTION_LANGUAGE__TREE_VIEWER_CONFIGURATIONS:
				return treeViewerConfigurations != null && !treeViewerConfigurations.isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ArchitectureDescriptionLanguageImpl
