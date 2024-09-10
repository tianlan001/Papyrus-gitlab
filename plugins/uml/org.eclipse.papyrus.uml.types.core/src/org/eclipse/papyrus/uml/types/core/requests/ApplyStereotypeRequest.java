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
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

public class ApplyStereotypeRequest extends AbstractStereotypeRequest {

	/**
	 * Constructor.
	 *
	 * @param umlElement
	 * @param stereotype
	 * @param editingDomain
	 */
	public ApplyStereotypeRequest(Element umlElement, Stereotype stereotype, TransactionalEditingDomain editingDomain) {
		super(editingDomain, umlElement, stereotype);
	}

}
