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
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContributionItem;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenContributionManager;
import org.eclipse.papyrus.gmf.codegen.gmfgen.GenSharedContributionItem;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Gen Shared Contribution Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenSharedContributionItemImpl#getOwner <em>Owner</em>}</li>
 *   <li>{@link org.eclipse.papyrus.gmf.codegen.gmfgen.impl.GenSharedContributionItemImpl#getActualItem <em>Actual Item</em>}</li>
 * </ul>
 *
 * @generated
 */
public class GenSharedContributionItemImpl extends EObjectImpl implements GenSharedContributionItem {
	/**
	 * The cached value of the '{@link #getActualItem() <em>Actual Item</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getActualItem()
	 * @generated
	 * @ordered
	 */
	protected GenContributionItem actualItem;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GenSharedContributionItemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return GMFGenPackage.eINSTANCE.getGenSharedContributionItem();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenContributionManager getOwner() {
		if (eContainerFeatureID() != GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__OWNER) return null;
		return (GenContributionManager)eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public GenContributionItem getActualItem() {
		if (actualItem != null && actualItem.eIsProxy()) {
			InternalEObject oldActualItem = (InternalEObject)actualItem;
			actualItem = (GenContributionItem)eResolveProxy(oldActualItem);
			if (actualItem != oldActualItem) {
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__ACTUAL_ITEM, oldActualItem, actualItem));
			}
		}
		return actualItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GenContributionItem basicGetActualItem() {
		return actualItem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setActualItem(GenContributionItem newActualItem) {
		GenContributionItem oldActualItem = actualItem;
		actualItem = newActualItem;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__ACTUAL_ITEM, oldActualItem, actualItem));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__OWNER:
				if (eInternalContainer() != null)
					msgs = eBasicRemoveFromContainer(msgs);
				return eBasicSetContainer(otherEnd, GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__OWNER, msgs);
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
			case GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__OWNER:
				return eBasicSetContainer(null, GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__OWNER, msgs);
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
			case GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__OWNER:
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
			case GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__OWNER:
				return getOwner();
			case GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__ACTUAL_ITEM:
				if (resolve) return getActualItem();
				return basicGetActualItem();
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
			case GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__ACTUAL_ITEM:
				setActualItem((GenContributionItem)newValue);
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
			case GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__ACTUAL_ITEM:
				setActualItem((GenContributionItem)null);
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
			case GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__OWNER:
				return getOwner() != null;
			case GMFGenPackage.GEN_SHARED_CONTRIBUTION_ITEM__ACTUAL_ITEM:
				return actualItem != null;
		}
		return super.eIsSet(featureID);
	}

} //GenSharedContributionItemImpl
