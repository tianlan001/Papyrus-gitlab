/*****************************************************************************
 * Copyright (c) 2014 Cedric Dumoulin.
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
 *  Cedric Dumoulin  Cedric.dumoulin@lifl.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter;

import java.util.List;

import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.Profile;

/**
 * 
 * @author cedric dumoulin
 *
 */
public class AppliedProfileCatalog {

	/**
	 * Uml Element from which we compute the AppliedProfileCatalog.
	 * 
	 */
	protected NamedElement umlElement;
	
	/**
	 * Constructor.
	 *
	 * @param umlElement
	 */
	public AppliedProfileCatalog(NamedElement umlElement) {
		super();
		this.umlElement = umlElement;
	}
	
	/**
	 * @return the umlElement
	 */
	public NamedElement getUmlElement() {
		return umlElement;
	}


	public List<Profile> getAppliedProfiles() {
		return null;
		
	}
}
