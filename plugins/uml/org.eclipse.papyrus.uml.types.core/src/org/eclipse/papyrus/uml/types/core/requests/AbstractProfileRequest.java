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

import java.util.Collections;
import java.util.List;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.type.core.EditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.requests.AbstractEditCommandRequest;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.Profile;

public abstract class AbstractProfileRequest extends AbstractEditCommandRequest {

	private org.eclipse.uml2.uml.Package umlPackage;
	private Profile profile;


	public AbstractProfileRequest(TransactionalEditingDomain editingDomain, org.eclipse.uml2.uml.Package umlPackage, Profile profile) {
		super(editingDomain);
		this.umlPackage = umlPackage;
		this.profile = profile;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest#getEditHelperContext()
	 *
	 * @return
	 */
	public Object getEditHelperContext() {
		IClientContext context = getClientContext();

		if (context == null) {
			return umlPackage;
		} else {
			return new EditHelperContext(umlPackage, context);
		}
	}

	/**
	 * @return the umlPackage
	 */
	public org.eclipse.uml2.uml.Package getUmlPackage() {
		return umlPackage;
	}

	/**
	 * @param umlPackage
	 *            the umlPackage to set
	 */
	public void setUmlPackage(org.eclipse.uml2.uml.Package umlPackage) {
		this.umlPackage = umlPackage;
	}

	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * @param profile
	 *            the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public List<Package> getElementsToEdit() {
		if (umlPackage != null) {
			return Collections.singletonList(umlPackage);
		}

		return Collections.emptyList();
	}
}
