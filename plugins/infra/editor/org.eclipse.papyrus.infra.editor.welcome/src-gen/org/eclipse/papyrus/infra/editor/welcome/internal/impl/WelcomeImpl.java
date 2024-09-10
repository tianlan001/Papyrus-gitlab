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

import org.eclipse.papyrus.infra.editor.welcome.Welcome;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePackage;

import org.eclipse.papyrus.infra.editor.welcome.WelcomePage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Welcome</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeImpl#getWelcomePage <em>Welcome Page</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WelcomeImpl extends MinimalEObjectImpl.Container implements Welcome {
	/**
	 * The cached value of the '{@link #getWelcomePage() <em>Welcome Page</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getWelcomePage()
	 * @generated
	 * @ordered
	 */
	protected WelcomePage welcomePage;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected WelcomeImpl() {
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
		return WelcomePackage.Literals.WELCOME;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomePage getWelcomePage() {
		return welcomePage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public NotificationChain basicSetWelcomePage(WelcomePage newWelcomePage, NotificationChain msgs) {
		WelcomePage oldWelcomePage = welcomePage;
		welcomePage = newWelcomePage;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, WelcomePackage.WELCOME__WELCOME_PAGE, oldWelcomePage, newWelcomePage);
			if (msgs == null) {
				msgs = notification;
			} else {
				msgs.add(notification);
			}
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setWelcomePage(WelcomePage newWelcomePage) {
		if (newWelcomePage != welcomePage) {
			NotificationChain msgs = null;
			if (welcomePage != null) {
				msgs = ((InternalEObject) welcomePage).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - WelcomePackage.WELCOME__WELCOME_PAGE, null, msgs);
			}
			if (newWelcomePage != null) {
				msgs = ((InternalEObject) newWelcomePage).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - WelcomePackage.WELCOME__WELCOME_PAGE, null, msgs);
			}
			msgs = basicSetWelcomePage(newWelcomePage, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, WelcomePackage.WELCOME__WELCOME_PAGE, newWelcomePage, newWelcomePage));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomePage createWelcomePage() {
		WelcomePage newWelcomePage = (WelcomePage) create(WelcomePackage.Literals.WELCOME_PAGE);
		setWelcomePage(newWelcomePage);
		return newWelcomePage;
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
		case WelcomePackage.WELCOME__WELCOME_PAGE:
			return basicSetWelcomePage(null, msgs);
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
		case WelcomePackage.WELCOME__WELCOME_PAGE:
			return getWelcomePage();
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
		case WelcomePackage.WELCOME__WELCOME_PAGE:
			setWelcomePage((WelcomePage) newValue);
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
		case WelcomePackage.WELCOME__WELCOME_PAGE:
			setWelcomePage((WelcomePage) null);
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
		case WelcomePackage.WELCOME__WELCOME_PAGE:
			return welcomePage != null;
		}
		return super.eIsSet(featureID);
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

} // WelcomeImpl
