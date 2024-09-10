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

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.papyrus.infra.editor.welcome.WelcomePackage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomePage;
import org.eclipse.papyrus.infra.editor.welcome.WelcomeSection;

import org.eclipse.papyrus.infra.editor.welcome.internal.operations.WelcomeSectionOperations;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Section</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeSectionImpl#getIdentifiers <em>Identifier</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeSectionImpl#isHidden <em>Hidden</em>}</li>
 * <li>{@link org.eclipse.papyrus.infra.editor.welcome.internal.impl.WelcomeSectionImpl#getPage <em>Page</em>}</li>
 * </ul>
 *
 * @generated
 */
public class WelcomeSectionImpl extends MinimalEObjectImpl.Container implements WelcomeSection {
	/**
	 * The cached value of the '{@link #getIdentifiers() <em>Identifier</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #getIdentifiers()
	 * @generated
	 * @ordered
	 */
	protected EList<String> identifiers;

	/**
	 * The default value of the '{@link #isHidden() <em>Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isHidden()
	 * @generated
	 * @ordered
	 */
	protected static final boolean HIDDEN_EDEFAULT = false;

	/**
	 * The cached value of the '{@link #isHidden() <em>Hidden</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @see #isHidden()
	 * @generated
	 * @ordered
	 */
	protected boolean hidden = HIDDEN_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	protected WelcomeSectionImpl() {
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
		return WelcomePackage.Literals.WELCOME_SECTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EList<String> getIdentifiers() {
		if (identifiers == null) {
			identifiers = new EDataTypeUniqueEList<>(String.class, this, WelcomePackage.WELCOME_SECTION__IDENTIFIER);
		}
		return identifiers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public void setHidden(boolean newHidden) {
		boolean oldHidden = hidden;
		hidden = newHidden;
		if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, WelcomePackage.WELCOME_SECTION__HIDDEN, oldHidden, hidden));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomePage getPage() {
		if (eContainerFeatureID() != WelcomePackage.WELCOME_SECTION__PAGE) {
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
		msgs = eBasicSetContainer((InternalEObject) newPage, WelcomePackage.WELCOME_SECTION__PAGE, msgs);
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
		if (newPage != eInternalContainer() || (eContainerFeatureID() != WelcomePackage.WELCOME_SECTION__PAGE && newPage != null)) {
			if (EcoreUtil.isAncestor(this, newPage)) {
				throw new IllegalArgumentException("Recursive containment not allowed for " + toString()); //$NON-NLS-1$
			}
			NotificationChain msgs = null;
			if (eInternalContainer() != null) {
				msgs = eBasicRemoveFromContainer(msgs);
			}
			if (newPage != null) {
				msgs = ((InternalEObject) newPage).eInverseAdd(this, WelcomePackage.WELCOME_PAGE__SECTION, WelcomePage.class, msgs);
			}
			msgs = basicSetPage(newPage, msgs);
			if (msgs != null) {
				msgs.dispatch();
			}
		} else if (eNotificationRequired()) {
			eNotify(new ENotificationImpl(this, Notification.SET, WelcomePackage.WELCOME_SECTION__PAGE, newPage, newPage));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public boolean isIdentifiedBy(String identifier) {
		return WelcomeSectionOperations.isIdentifiedBy(this, identifier);
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
		case WelcomePackage.WELCOME_SECTION__PAGE:
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
		case WelcomePackage.WELCOME_SECTION__PAGE:
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
		case WelcomePackage.WELCOME_SECTION__PAGE:
			return eInternalContainer().eInverseRemove(this, WelcomePackage.WELCOME_PAGE__SECTION, WelcomePage.class, msgs);
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
		case WelcomePackage.WELCOME_SECTION__IDENTIFIER:
			return getIdentifiers();
		case WelcomePackage.WELCOME_SECTION__HIDDEN:
			return isHidden();
		case WelcomePackage.WELCOME_SECTION__PAGE:
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
		case WelcomePackage.WELCOME_SECTION__IDENTIFIER:
			getIdentifiers().clear();
			getIdentifiers().addAll((Collection<? extends String>) newValue);
			return;
		case WelcomePackage.WELCOME_SECTION__HIDDEN:
			setHidden((Boolean) newValue);
			return;
		case WelcomePackage.WELCOME_SECTION__PAGE:
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
		case WelcomePackage.WELCOME_SECTION__IDENTIFIER:
			getIdentifiers().clear();
			return;
		case WelcomePackage.WELCOME_SECTION__HIDDEN:
			setHidden(HIDDEN_EDEFAULT);
			return;
		case WelcomePackage.WELCOME_SECTION__PAGE:
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
		case WelcomePackage.WELCOME_SECTION__IDENTIFIER:
			return identifiers != null && !identifiers.isEmpty();
		case WelcomePackage.WELCOME_SECTION__HIDDEN:
			return hidden != HIDDEN_EDEFAULT;
		case WelcomePackage.WELCOME_SECTION__PAGE:
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
		case WelcomePackage.WELCOME_SECTION___IS_IDENTIFIED_BY__STRING:
			return isIdentifiedBy((String) arguments.get(0));
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
		result.append(" (identifier: "); //$NON-NLS-1$
		result.append(identifiers);
		result.append(", hidden: "); //$NON-NLS-1$
		result.append(hidden);
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

} // WelcomeSectionImpl
