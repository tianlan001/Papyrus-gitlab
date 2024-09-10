/**
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 */
package org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.Cell;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.EObjectAxisWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.IdAxisWrapper;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.NattablecellFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattablecell.NattablecellPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class NattablecellFactoryImpl extends EFactoryImpl implements NattablecellFactory {

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static NattablecellFactory init() {
		try {
			NattablecellFactory theNattablecellFactory = (NattablecellFactory)EPackage.Registry.INSTANCE.getEFactory(NattablecellPackage.eNS_URI);
			if (theNattablecellFactory != null) {
				return theNattablecellFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new NattablecellFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NattablecellFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case NattablecellPackage.CELL: return createCell();
			case NattablecellPackage.EOBJECT_AXIS_WRAPPER: return createEObjectAxisWrapper();
			case NattablecellPackage.ID_AXIS_WRAPPER: return createIdAxisWrapper();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Cell createCell() {
		CellImpl cell = new CellImpl();
		return cell;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObjectAxisWrapper createEObjectAxisWrapper() {
		EObjectAxisWrapperImpl eObjectAxisWrapper = new EObjectAxisWrapperImpl();
		return eObjectAxisWrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IdAxisWrapper createIdAxisWrapper() {
		IdAxisWrapperImpl idAxisWrapper = new IdAxisWrapperImpl();
		return idAxisWrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NattablecellPackage getNattablecellPackage() {
		return (NattablecellPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static NattablecellPackage getPackage() {
		return NattablecellPackage.eINSTANCE;
	}
} // NattablecellFactoryImpl
