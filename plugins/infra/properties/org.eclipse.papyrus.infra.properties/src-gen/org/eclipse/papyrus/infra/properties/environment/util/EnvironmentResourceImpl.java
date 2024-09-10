/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *  Christian W. Damus - bug 573986
 *****************************************************************************/
package org.eclipse.papyrus.infra.properties.environment.util;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

/**
 * <!-- begin-user-doc -->
 * The <b>Resource </b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.eclipse.papyrus.infra.properties.environment.util.EnvironmentResourceFactoryImpl
 * @generated
 */
public class EnvironmentResourceImpl extends XMIResourceImpl {
	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param uri the URI of the new resource.
	 * @generated
	 */
	public EnvironmentResourceImpl(URI uri) {
		super(uri);
	}

	/**
	 * @see org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl#useUUIDs()
	 *
	 * @return
	 *
	 * @generated NOT
	 */
	@Override
	protected boolean useUUIDs() {
		return true;
	}

	/**
	 * In order to maintain compatibility with resources created before this implementation
	 * adopted {@linkplain #useUUIDs() UUIDs for XMI IDs}, do not assign IDs to objects that
	 * do not have them while loading so that incoming HREFs will not be broken. Otherwise,
	 * every time the resource is loaded it would generate new distinct IDs for the same
	 * objects (so long as it is not saved for some reason) and HREFs created with these IDs
	 * will not work.
	 *
	 * @return {@code false}
	 */
	@Override
	protected boolean assignIDsWhileLoading() {
		return false;
	}

} // EnvironmentResourceImpl
