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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.eclipse.papyrus.infra.editor.welcome.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class WelcomeFactoryImpl extends EFactoryImpl implements WelcomeFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static WelcomeFactory init() {
		try {
			WelcomeFactory theWelcomeFactory = (WelcomeFactory) EPackage.Registry.INSTANCE.getEFactory(WelcomePackage.eNS_URI);
			if (theWelcomeFactory != null) {
				return theWelcomeFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new WelcomeFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public WelcomeFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case WelcomePackage.WELCOME:
			return createWelcome();
		case WelcomePackage.WELCOME_PAGE:
			return createWelcomePage();
		case WelcomePackage.WELCOME_SECTION:
			return createWelcomeSection();
		case WelcomePackage.SASH_COLUMN:
			return createSashColumn();
		case WelcomePackage.SASH_ROW:
			return createSashRow();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public Welcome createWelcome() {
		WelcomeImpl welcome = new WelcomeImpl();
		return welcome;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomePage createWelcomePage() {
		WelcomePageImpl welcomePage = new WelcomePageImpl();
		return welcomePage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomeSection createWelcomeSection() {
		WelcomeSectionImpl welcomeSection = new WelcomeSectionImpl();
		return welcomeSection;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SashColumn createSashColumn() {
		SashColumnImpl sashColumn = new SashColumnImpl();
		return sashColumn;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public SashRow createSashRow() {
		SashRowImpl sashRow = new SashRowImpl();
		return sashRow;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public WelcomePackage getWelcomePackage() {
		return (WelcomePackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static WelcomePackage getPackage() {
		return WelcomePackage.eINSTANCE;
	}

} // WelcomeFactoryImpl
