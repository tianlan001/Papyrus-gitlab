/*******************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.papyrus.uml.diagram.timing.custom;

/** An exception indicating that the operation that was asked should not be performed, as it is not allowed. */
public class OperationForbiddenException extends Exception {

	private static final long serialVersionUID = -4972361328078373414L;

	public OperationForbiddenException() {
		super();
	}

	public OperationForbiddenException(final String message) {
		super(message);
	}
}
