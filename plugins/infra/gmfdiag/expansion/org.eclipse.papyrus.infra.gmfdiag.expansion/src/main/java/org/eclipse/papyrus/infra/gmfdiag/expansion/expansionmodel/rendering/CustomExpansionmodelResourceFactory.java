/**
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	CEA LIST - Initial API and implementation
 *
 */
package org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.rendering;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.papyrus.infra.gmfdiag.expansion.expansionmodel.util.ExpansionModelResourceFactoryImpl;

/**
 * Custom resource factory to create custom resources with usage of uuids instead of index
 */
public class CustomExpansionmodelResourceFactory extends ExpansionModelResourceFactoryImpl {
	/**
	 * Creates an instance of the resource factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	public CustomExpansionmodelResourceFactory() {
		super();
	}

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 *
	 */
	@Override
	public Resource createResource(URI uri) {
		Resource result = new CustomExpansionmodelResource(uri);
		return result;
	}

}
