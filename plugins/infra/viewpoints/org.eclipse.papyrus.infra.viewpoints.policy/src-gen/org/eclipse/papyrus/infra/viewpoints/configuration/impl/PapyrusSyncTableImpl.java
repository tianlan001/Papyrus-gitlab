/**
 * Copyright (c) 2015 CEA LIST and others.
 * 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors:
 *  CEA LIST - Initial API and implementation
 *  
 * 
 */
package org.eclipse.papyrus.infra.viewpoints.configuration.impl;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.papyrus.infra.viewpoints.configuration.ConfigurationPackage;
import org.eclipse.papyrus.infra.viewpoints.configuration.PapyrusSyncTable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Papyrus Sync Table</b></em>'.
 * @deprecated Use {@link org.eclipse.papyrus.infra.nattable.representation.PapyrusSyncTableImpl} instead
 * <!-- end-user-doc -->
 *
 * @generated
 */
public class PapyrusSyncTableImpl extends PapyrusViewImpl implements PapyrusSyncTable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected PapyrusSyncTableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ConfigurationPackage.Literals.PAPYRUS_SYNC_TABLE;
	}

} //PapyrusSyncTableImpl
