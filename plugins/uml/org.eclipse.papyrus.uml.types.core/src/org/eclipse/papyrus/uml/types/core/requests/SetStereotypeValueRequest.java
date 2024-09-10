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

public class SetStereotypeValueRequest extends AbstractEditCommandRequest {


	private String propertyName;

	private Element umlElement;

	private Stereotype stereotype;

	private Object value;


	public SetStereotypeValueRequest(TransactionalEditingDomain editingDomain, Stereotype stereotype, Element elementToEdit, String propertyName, Object value) {
		super(editingDomain);
		this.umlElement = elementToEdit;
		this.propertyName = propertyName;
		this.value = value;
		this.stereotype = stereotype;
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

	public Object getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName
	 *            the propertyName to set
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}


	public List<Element> getElementsToEdit() {
		if (umlElement != null) {
			return Collections.singletonList(umlElement);
		}

		return Collections.emptyList();
	}


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
}
