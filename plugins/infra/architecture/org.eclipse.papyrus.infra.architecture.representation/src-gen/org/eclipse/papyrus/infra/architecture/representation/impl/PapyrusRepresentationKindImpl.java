/**
 * Copyright (c) 2016 CEA LIST.
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
package org.eclipse.papyrus.infra.architecture.representation.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.architecture.representation.ModelRule;
import org.eclipse.papyrus.infra.architecture.representation.OwningRule;
import org.eclipse.papyrus.infra.architecture.representation.PapyrusRepresentationKind;
import org.eclipse.papyrus.infra.architecture.representation.RepresentationPackage;
import org.eclipse.papyrus.infra.core.architecture.impl.RepresentationKindImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Papyrus Representation Kind</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.PapyrusRepresentationKindImpl#getParent <em>Parent</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.PapyrusRepresentationKindImpl#getModelRules <em>Model Rules</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.PapyrusRepresentationKindImpl#getOwningRules <em>Owning Rules</em>}</li>
 *   <li>{@link org.eclipse.papyrus.infra.architecture.representation.impl.PapyrusRepresentationKindImpl#getImplementationID <em>Implementation ID</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class PapyrusRepresentationKindImpl extends RepresentationKindImpl implements PapyrusRepresentationKind {
	/**
	 * The cached value of the '{@link #getParent() <em>Parent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getParent()
	 * @generated
	 * @ordered
	 */
	protected PapyrusRepresentationKind parent;

	/**
	 * The cached value of the '{@link #getModelRules() <em>Model Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelRules()
	 * @generated
	 * @ordered
	 */
	protected EList<ModelRule> modelRules;

	/**
	 * The cached value of the '{@link #getOwningRules() <em>Owning Rules</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getOwningRules()
	 * @generated
	 * @ordered
	 */
	protected EList<OwningRule> owningRules;

	/**
	 * The default value of the '{@link #getImplementationID() <em>Implementation ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationID()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTATION_ID_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getImplementationID() <em>Implementation ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementationID()
	 * @generated
	 * @ordered
	 */
	protected String implementationID = IMPLEMENTATION_ID_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PapyrusRepresentationKindImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RepresentationPackage.Literals.PAPYRUS_REPRESENTATION_KIND;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public PapyrusRepresentationKind getParent() {
		if (parent != null && parent.eIsProxy()) {
			InternalEObject oldParent = (InternalEObject)parent;
			parent = (PapyrusRepresentationKind)eResolveProxy(oldParent);
			if (parent != oldParent) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__PARENT, oldParent, parent));
			}
		}
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PapyrusRepresentationKind basicGetParent() {
		return parent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setParent(PapyrusRepresentationKind newParent) {
		PapyrusRepresentationKind oldParent = parent;
		parent = newParent;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__PARENT, oldParent, parent));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<ModelRule> getModelRules() {
		if (modelRules == null) {
			modelRules = new EObjectContainmentEList<ModelRule>(ModelRule.class, this, RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES);
		}
		return modelRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EList<OwningRule> getOwningRules() {
		if (owningRules == null) {
			owningRules = new EObjectContainmentEList<OwningRule>(OwningRule.class, this, RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES);
		}
		return owningRules;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getImplementationID() {
		return implementationID;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setImplementationID(String newImplementationID) {
		String oldImplementationID = implementationID;
		implementationID = newImplementationID;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID, oldImplementationID, implementationID));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES:
				return ((InternalEList<?>)getModelRules()).basicRemove(otherEnd, msgs);
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES:
				return ((InternalEList<?>)getOwningRules()).basicRemove(otherEnd, msgs);
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
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__PARENT:
				if (resolve) return getParent();
				return basicGetParent();
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES:
				return getModelRules();
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES:
				return getOwningRules();
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID:
				return getImplementationID();
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
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__PARENT:
				setParent((PapyrusRepresentationKind)newValue);
				return;
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES:
				getModelRules().clear();
				getModelRules().addAll((Collection<? extends ModelRule>)newValue);
				return;
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES:
				getOwningRules().clear();
				getOwningRules().addAll((Collection<? extends OwningRule>)newValue);
				return;
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID:
				setImplementationID((String)newValue);
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
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__PARENT:
				setParent((PapyrusRepresentationKind)null);
				return;
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES:
				getModelRules().clear();
				return;
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES:
				getOwningRules().clear();
				return;
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID:
				setImplementationID(IMPLEMENTATION_ID_EDEFAULT);
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
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__PARENT:
				return parent != null;
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__MODEL_RULES:
				return modelRules != null && !modelRules.isEmpty();
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__OWNING_RULES:
				return owningRules != null && !owningRules.isEmpty();
			case RepresentationPackage.PAPYRUS_REPRESENTATION_KIND__IMPLEMENTATION_ID:
				return IMPLEMENTATION_ID_EDEFAULT == null ? implementationID != null : !IMPLEMENTATION_ID_EDEFAULT.equals(implementationID);
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
		result.append(" (implementationID: "); //$NON-NLS-1$
		result.append(implementationID);
		result.append(')');
		return result.toString();
	}

} //PapyrusRepresentationKindImpl
