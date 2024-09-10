/*****************************************************************************
 * Copyright (c) 2011 Atos.
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
 *   Atos - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.diagram.activity.exception;

/**
 * Exception raised when an edit policy was not correctly configure
 *
 * @author arthur daussy
 *
 */
public class EditPolicyConfigurationException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -7560556980743988409L;

	/**
	 * Edit Policy class
	 */
	private Class<?> editPolicyClass;

	public EditPolicyConfigurationException(String arg0, Class<?> editPolicyClass) {
		super(arg0);
		this.editPolicyClass = editPolicyClass;
	}

	@Override
	public String getMessage() {
		String detailMessage = super.getMessage();
		return "EditPolicy configuration error for class " + editPolicyClass.toString() + " :" + detailMessage;//////$NON-NLS-1$
	}
}
