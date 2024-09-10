/*****************************************************************************
 * Copyright (c) 2011, 2016 LIFL, CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  LIFL - Initial API and implementation
 *  Christian W. Damus - bug 469188
 *  Mickael ADAM (ALL4TEC) mickael.adam@all4tec.net - bug 508275
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.sashwindows.di.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.papyrus.infra.core.sashwindows.di.DiFactory;
import org.eclipse.papyrus.infra.core.sashwindows.di.DiPackage;
import org.eclipse.papyrus.infra.core.sashwindows.di.PageRef;
import org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder;
import org.eclipse.uml2.common.util.SubsetSupersetEObjectContainmentWithInverseEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Tab Folder</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.core.sashwindows.di.impl.TabFolderImpl#getChildren <em>Children</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.core.sashwindows.di.impl.TabFolderImpl#getCurrentSelection <em>Current Selection</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TabFolderImpl extends AbstractPanelImpl implements TabFolder {

	/**
	 * The cached value of the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 */
	protected EList<PageRef> children;

	/**
	 * The cached value of the '{@link #getCurrentSelection() <em>Current Selection</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getCurrentSelection()
	 * @generated
	 * @ordered
	 * @since 1.2
	 */
	protected PageRef currentSelection;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected TabFolderImpl() {
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
		return DiPackage.Literals.TAB_FOLDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<PageRef> getChildren() {
		if (children == null) {
			children = new SubsetSupersetEObjectContainmentWithInverseEList<PageRef>(PageRef.class, this, DiPackage.TAB_FOLDER__CHILDREN, null, CHILDREN_ESUBSETS, DiPackage.PAGE_REF__PARENT);
		}
		return children;
	}

	/**
	 * The array of subset feature identifiers for the '{@link #getChildren() <em>Children</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getChildren()
	 * @generated
	 * @ordered
	 * @since 1.2
	 */
	protected static final int[] CHILDREN_ESUBSETS = new int[] { DiPackage.TAB_FOLDER__CURRENT_SELECTION };

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @since 1.2
	 */
	@Override
	public PageRef getCurrentSelection() {
		if (currentSelection != null && currentSelection.eIsProxy()) {
			InternalEObject oldCurrentSelection = (InternalEObject) currentSelection;
			currentSelection = (PageRef) eResolveProxy(oldCurrentSelection);
			if (currentSelection != oldCurrentSelection) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, DiPackage.TAB_FOLDER__CURRENT_SELECTION, oldCurrentSelection, currentSelection));
				}
			}
		}
		return currentSelection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @since 1.2
	 */
	public PageRef basicGetCurrentSelection() {
		return currentSelection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @since 1.2
	 */
	@Override
	public void setCurrentSelection(PageRef newCurrentSelection) {
		PageRef oldCurrentSelection = currentSelection;
		currentSelection = newCurrentSelection;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, DiPackage.TAB_FOLDER__CURRENT_SELECTION, oldCurrentSelection, currentSelection));
		}
		Resource.Internal eInternalResource = eInternalResource();
		if (eInternalResource == null || !eInternalResource.isLoading()) {
			if (newCurrentSelection != null) {
				EList<PageRef> children = getChildren();
				if (!children.contains(newCurrentSelection)) {
					children.add(newCurrentSelection);
				}
			}
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void addPage(Object pageIdentifier) {
		PageRef newPage = DiFactory.eINSTANCE.createPageRef();
		newPage.setPageIdentifier(pageIdentifier);
		getChildren().add(newPage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void addPage(int index, Object pageIdentifier) {
		PageRef newPage = DiFactory.eINSTANCE.createPageRef();
		newPage.setPageIdentifier(pageIdentifier);
		getChildren().add(index, newPage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 * @since 1.2
	 */
	@Override
	public void addPage(PageRef pageRef) {
		getChildren().add(pageRef);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated NOT
	 */
	@Override
	public void removePage(int pageIndex) {
		if (0 <= pageIndex && pageIndex < getChildren().size()) {
			getChildren().remove(pageIndex);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case DiPackage.TAB_FOLDER__CHILDREN:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getChildren()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
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
		case DiPackage.TAB_FOLDER__CHILDREN:
			return ((InternalEList<?>) getChildren()).basicRemove(otherEnd, msgs);
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
		case DiPackage.TAB_FOLDER__CHILDREN:
			return getChildren();
		case DiPackage.TAB_FOLDER__CURRENT_SELECTION:
			if (resolve) {
				return getCurrentSelection();
			}
			return basicGetCurrentSelection();
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
		case DiPackage.TAB_FOLDER__CHILDREN:
			getChildren().clear();
			getChildren().addAll((Collection<? extends PageRef>) newValue);
			return;
		case DiPackage.TAB_FOLDER__CURRENT_SELECTION:
			setCurrentSelection((PageRef) newValue);
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
		case DiPackage.TAB_FOLDER__CHILDREN:
			getChildren().clear();
			return;
		case DiPackage.TAB_FOLDER__CURRENT_SELECTION:
			setCurrentSelection((PageRef) null);
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
		case DiPackage.TAB_FOLDER__CHILDREN:
			return children != null && !children.isEmpty();
		case DiPackage.TAB_FOLDER__CURRENT_SELECTION:
			return currentSelection != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.core.sashwindows.di.TabFolder#movePage(int, int)
	 *
	 * @param oldIndex
	 * @param newIndex
	 * @generated NOT
	 */
	@Override
	public void movePage(int oldIndex, int newIndex) {
		int listSize = getChildren().size();
		if (newIndex >= listSize) {
			newIndex = listSize - 1;
		}
		if (newIndex < 0) {
			newIndex = 0;
		}

		if ((listSize == 0)
				|| (oldIndex == newIndex)
				|| (oldIndex < 0)) {
			return;
		}

		getChildren().move(newIndex, oldIndex);
	}

} // TabFolderImpl
