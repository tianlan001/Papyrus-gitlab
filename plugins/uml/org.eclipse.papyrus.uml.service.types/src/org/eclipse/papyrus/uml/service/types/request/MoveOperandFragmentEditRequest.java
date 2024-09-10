/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
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
 *****************************************************************************/

package org.eclipse.papyrus.uml.service.types.request;

import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.gmf.runtime.emf.type.core.EditHelperContext;
import org.eclipse.gmf.runtime.emf.type.core.IClientContext;
import org.eclipse.gmf.runtime.emf.type.core.requests.AbstractEditCommandRequest;
import org.eclipse.uml2.uml.Element;

/**
 * 
 * Move Operand Fragment Edit Request.
 *
 */
public class MoveOperandFragmentEditRequest extends AbstractEditCommandRequest {

	/** element to edit */
	private Element elementToEdit;

	/**
	 * 
	 * Constructor.
	 *
	 * @param editingDomain
	 *            the editing domain
	 * @param elementToEdit
	 *            the element to edit 
	 */
	public MoveOperandFragmentEditRequest(TransactionalEditingDomain editingDomain, Element elementToEdit) {
		super(editingDomain);
		this.elementToEdit = elementToEdit;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.gmf.runtime.emf.type.core.requests.IEditCommandRequest#getEditHelperContext()
	 */
	@Override
	public Object getEditHelperContext() {
		IClientContext context = getClientContext();
		if (context == null) {
			return getElementToEdit();
		} else {
			return new EditHelperContext(getElementToEdit(), context);
		}
	}

	/**
	 * Gets the element to edit.
	 */
	public Element getElementToEdit() {
		return elementToEdit;
	}
}