/*******************************************************************************
 * Copyright (c) 2012 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Gregoire Dupe (Mia-Software) - Bug 386387 - [CustomizedTreeContentProvider] The TreeElements are not preserved between two calls to getElements()
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.custom.ui.internal.exception;

public class CustomizedContentProviderRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 6962461313131533003L;

	public CustomizedContentProviderRuntimeException() {
		super();
	}

	public CustomizedContentProviderRuntimeException(final String message) {
		super(message);
	}

	public CustomizedContentProviderRuntimeException(final Throwable cause) {
		super(cause);
	}

	public CustomizedContentProviderRuntimeException(final String message,
			final Throwable cause) {
		super(message, cause);
	}

}
