/*****************************************************************************
 * Copyright (c) 2016 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.uml.types.core.requests;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;

public class ApplyProfileRequest extends AbstractProfileRequest {

	/**
	 * Constructor.
	 *
	 * @param umlPackage
	 * @param profile
	 * @param editingDomain
	 */
	public ApplyProfileRequest(TransactionalEditingDomain editingDomain, Package umlPackage, Profile profile) {
		super(editingDomain, umlPackage, profile);
	}


}
