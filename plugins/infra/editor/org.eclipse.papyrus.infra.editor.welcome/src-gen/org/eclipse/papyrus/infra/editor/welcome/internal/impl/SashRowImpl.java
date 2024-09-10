/**
 * Copyright (c) 2015 Christian W. Damus and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.editor.welcome.internal.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.papyrus.infra.editor.welcome.SashColumn;
import org.eclipse.papyrus.infra.editor.welcome.SashRow;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePackage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sash Row</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashRowImpl#getY <em>Y</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashRowImpl#getPage <em>Page</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashRowImpl#getColumn <em>Column</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SashRowImpl extends MinimalEObjectImpl.Container implements SashRow {
	/**
	 * The default value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected static final int Y_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getY() <em>Y</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getY()
	 * @generated
	 * @ordered
	 */
	protected int y = Y_EDEFAULT;

	/**
	 * This is true if the Y attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean yESet;

	/**
	 * The cached value of the '{@link #getPage() <em>Page</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getPage()
	 * @generated
	 * @ordered
	 */
	protected WelcomePage page;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SashRowImpl() {
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
		return WelcomePackage.Literals.SASH_ROW;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getY() {
		return y;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setY(int newY) {
		int oldY = y;
		y = newY;
		boolean oldYESet = yESet;
		yESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, WelcomePackage.SASH_ROW__Y, oldY, y, !oldYESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetY() {
		int oldY = y;
		boolean oldYESet = yESet;
		y = Y_EDEFAULT;
		yESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, WelcomePackage.SASH_ROW__Y, oldY, Y_EDEFAULT, oldYESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetY() {
		return yESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomePage getPage() {
		if (page != null && page.eIsProxy()) {
			InternalEObject oldPage = (InternalEObject) page;
			page = (WelcomePage) eResolveProxy(oldPage);
			if (page != oldPage) {
				if (eNotificationRequired()) {
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, WelcomePackage.SASH_ROW__PAGE, oldPage, page));
				}
			}
		}
		return page;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public WelcomePage basicGetPage() {
		return page;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setPage(WelcomePage newPage) {
		WelcomePage oldPage = page;
		page = newPage;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, WelcomePackage.SASH_ROW__PAGE, oldPage, page));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SashColumn getColumn() {
		if (eContainerFeatureID() != WelcomePackage.SASH_ROW__COLUMN) {
			return null;
		}
		return (SashColumn) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetColumn(SashColumn newColumn, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newColumn, WelcomePackage.SASH_ROW__COLUMN, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setColumn(SashColumn newColumn) {
		if (newColumn != eInternalContainer() || (eContainerFeatureID() != WelcomePackage.SASH_ROW__COLUMN && newColumn != null)) {
			if (EcoreUtil.isAncestor(this, newColumn)) {
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newColumn != null) {
				msgs = ((InternalEObject) newColumn).eInverseAdd(this, WelcomePackage.SASH_COLUMN__SASH_ROW, SashColumn.class, msgs);
			}
			msgs = basicSetColumn(newColumn, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, WelcomePackage.SASH_ROW__COLUMN, newColumn, newColumn));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case WelcomePackage.SASH_ROW__COLUMN:
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			return basicSetColumn((SashColumn) otherEnd, msgs);
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
		case WelcomePackage.SASH_ROW__COLUMN:
			return basicSetColumn(null, msgs);
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
	public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
		switch (eContainerFeatureID()) {
		case WelcomePackage.SASH_ROW__COLUMN:
			return eInternalContainer().eInverseRemove(this, WelcomePackage.SASH_COLUMN__SASH_ROW, SashColumn.class, msgs);
		}
		return super.eBasicRemoveFromContainerFeature(msgs);
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
		case WelcomePackage.SASH_ROW__Y:
			return getY();
		case WelcomePackage.SASH_ROW__PAGE:
			if (resolve) {
				return getPage();
			}
			return basicGetPage();
		case WelcomePackage.SASH_ROW__COLUMN:
			return getColumn();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case WelcomePackage.SASH_ROW__Y:
			setY((Integer) newValue);
			return;
		case WelcomePackage.SASH_ROW__PAGE:
			setPage((WelcomePage) newValue);
			return;
		case WelcomePackage.SASH_ROW__COLUMN:
			setColumn((SashColumn) newValue);
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
		case WelcomePackage.SASH_ROW__Y:
			unsetY();
			return;
		case WelcomePackage.SASH_ROW__PAGE:
			setPage((WelcomePage) null);
			return;
		case WelcomePackage.SASH_ROW__COLUMN:
			setColumn((SashColumn) null);
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
		case WelcomePackage.SASH_ROW__Y:
			return isSetY();
		case WelcomePackage.SASH_ROW__PAGE:
			return page != null;
		case WelcomePackage.SASH_ROW__COLUMN:
			return getColumn() != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) {
			return super.toString();
		}

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (y: "); //$NON-NLS-1$
		if (yESet) {
			result.append(y);
		} else {
			result.append("<unset>"); //$NON-NLS-1$
		}
		result.append(')');
		return result.toString();
	}

	/**
	 * Creates a new instance of the specified Ecore class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @param eClass
	 *            The Ecore class of the instance to create.
	 * @return The new instance.
	 * @generated
	 */
	protected EObject create(EClass eClass) {
		return EcoreUtil.create(eClass);
	}

} // SashRowImpl
