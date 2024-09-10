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

import java.lang.reflect.InvocationTargetException;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.papyrus.infra.editor.welcome.SashColumn;
import org.eclipse.papyrus.infra.editor.welcome.SashRow;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePackage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePage;

import org.eclipse.papyrus.infra.editor.welcome.internal.operations.SashColumnOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Sash Column</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashColumnImpl#getX <em>X</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashColumnImpl#getSashRows <em>Sash Row</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.SashColumnImpl#getPage <em>Page</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SashColumnImpl extends MinimalEObjectImpl.Container implements SashColumn {
	/**
	 * The default value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected static final int X_EDEFAULT = 0;

	/**
	 * The cached value of the '{@link #getX() <em>X</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getX()
	 * @generated
	 * @ordered
	 */
	protected int x = X_EDEFAULT;

	/**
	 * This is true if the X attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 * @ordered
	 */
	protected boolean xESet;

	/**
	 * The cached value of the '{@link #getSashRows() <em>Sash Row</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getSashRows()
	 * @generated
	 * @ordered
	 */
	protected EList<SashRow> sashRows;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected SashColumnImpl() {
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
		return WelcomePackage.Literals.SASH_COLUMN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getX() {
		return x;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setX(int newX) {
		int oldX = x;
		x = newX;
		boolean oldXESet = xESet;
		xESet = true;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, WelcomePackage.SASH_COLUMN__X, oldX, x, !oldXESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void unsetX() {
		int oldX = x;
		boolean oldXESet = xESet;
		x = X_EDEFAULT;
		xESet = false;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.UNSET, WelcomePackage.SASH_COLUMN__X, oldX, X_EDEFAULT, oldXESet));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isSetX() {
		return xESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<SashRow> getSashRows() {
		if (sashRows == null) {
			sashRows = new EObjectContainmentWithInverseEList<>(SashRow.class, this, WelcomePackage.SASH_COLUMN__SASH_ROW, WelcomePackage.SASH_ROW__COLUMN);
		}
		return sashRows;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SashRow createSashRow() {
		SashRow newSashRow = (SashRow) create(WelcomePackage.Literals.SASH_ROW);
		getSashRows().add(newSashRow);
		return newSashRow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomePage getPage() {
		if (eContainerFeatureID() != WelcomePackage.SASH_COLUMN__PAGE) {
			return null;
		}
		return (WelcomePage) eInternalContainer();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetPage(WelcomePage newPage, NotificationChain msgs) {
		msgs = eBasicSetContainer((InternalEObject) newPage, WelcomePackage.SASH_COLUMN__PAGE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setPage(WelcomePage newPage) {
		if (newPage != eInternalContainer() || (eContainerFeatureID() != WelcomePackage.SASH_COLUMN__PAGE && newPage != null)) {
			if (EcoreUtil.isAncestor(this, newPage)) {
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newPage != null) {
				msgs = ((InternalEObject) newPage).eInverseAdd(this, WelcomePackage.WELCOME_PAGE__SASH_COLUMN, WelcomePage.class, msgs);
			}
			msgs = basicSetPage(newPage, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, WelcomePackage.SASH_COLUMN__PAGE, newPage, newPage));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SashRow getSashRow(int index) {
		return SashColumnOperations.getSashRow(this, index);
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
		case WelcomePackage.SASH_COLUMN__SASH_ROW:
			return ((InternalEList<InternalEObject>) (InternalEList<?>) getSashRows()).basicAdd(otherEnd, msgs);
		case WelcomePackage.SASH_COLUMN__PAGE:
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			return basicSetPage((WelcomePage) otherEnd, msgs);
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
		case WelcomePackage.SASH_COLUMN__SASH_ROW:
			return ((InternalEList<?>) getSashRows()).basicRemove(otherEnd, msgs);
		case WelcomePackage.SASH_COLUMN__PAGE:
			return basicSetPage(null, msgs);
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
		case WelcomePackage.SASH_COLUMN__PAGE:
			return eInternalContainer().eInverseRemove(this, WelcomePackage.WELCOME_PAGE__SASH_COLUMN, WelcomePage.class, msgs);
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
		case WelcomePackage.SASH_COLUMN__X:
			return getX();
		case WelcomePackage.SASH_COLUMN__SASH_ROW:
			return getSashRows();
		case WelcomePackage.SASH_COLUMN__PAGE:
			return getPage();
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
		case WelcomePackage.SASH_COLUMN__X:
			setX((Integer) newValue);
			return;
		case WelcomePackage.SASH_COLUMN__SASH_ROW:
			getSashRows().clear();
			getSashRows().addAll((Collection<? extends SashRow>) newValue);
			return;
		case WelcomePackage.SASH_COLUMN__PAGE:
			setPage((WelcomePage) newValue);
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
		case WelcomePackage.SASH_COLUMN__X:
			unsetX();
			return;
		case WelcomePackage.SASH_COLUMN__SASH_ROW:
			getSashRows().clear();
			return;
		case WelcomePackage.SASH_COLUMN__PAGE:
			setPage((WelcomePage) null);
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
		case WelcomePackage.SASH_COLUMN__X:
			return isSetX();
		case WelcomePackage.SASH_COLUMN__SASH_ROW:
			return sashRows != null && !sashRows.isEmpty();
		case WelcomePackage.SASH_COLUMN__PAGE:
			return getPage() != null;
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
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
		case WelcomePackage.SASH_COLUMN___GET_SASH_ROW__INT:
			return getSashRow((Integer) arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
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
		result.append(" (x: "); //$NON-NLS-1$
		if (xESet) {
			result.append(x);
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

} // SashColumnImpl
