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
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.Stereotype;

public abstract class AbstractStereotypeRequest extends AbstractEditCommandRequest {
	private Element umlElement;
	private Stereotype stereotype;

	public AbstractStereotypeRequest(TransactionalEditingDomain editingDomain, Element umlElement, Stereotype stereotype) {
		super(editingDomain);
		this.umlElement = umlElement;
		this.stereotype = stereotype;
	}

	/**
	 * @see org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest#getEditHelperContext()
	 *
	 * @return
	 */
	public Object getEditHelperContext() {
		IClientContext context = getClientContext();

		if (context == null) {
			return umlElement;
		} else {
			return new EditHelperContext(umlElement, context);
		}
	}

	/**
	 * @return the umlElement
	 */
	public Element getUmlElement() {
		return umlElement;
	}

	/**
	 * @param umlElement
	 *            the umlElement to set
	 */
	public void setUmlElement(Element umlElement) {
		this.umlElement = umlElement;
	}

	/**
	 * @return the stereotype
	 */
	public Stereotype getStereotype() {
		return stereotype;
	}

	/**
	 * @param stereotype
	 *            the stereotype to set
	 */
	public void setStereotype(Stereotype stereotype) {
		this.stereotype = stereotype;
	}

	public List<Element> getElementsToEdit() {
		if (umlElement != null) {
			return Collections.singletonList(umlElement);
		}

		return Collections.emptyList();
	}
}
