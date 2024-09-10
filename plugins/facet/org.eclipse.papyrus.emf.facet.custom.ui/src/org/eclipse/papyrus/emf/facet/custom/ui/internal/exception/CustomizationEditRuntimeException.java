/**
 *  Copyright (c) 2012 Mia-Software.
 *
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License 2.0
 *  which accompanies this distribution, and is available at
 *  https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *
 *  Contributors:
 *  	Gregoire Dupe (Mia-Software) - Bug 369987 - [Restructuring][Table] Switch to the new customization and facet framework
 */
package org.eclipse.papyrus.emf.facet.custom.ui.internal.exception;

public class CustomizationEditRuntimeException extends RuntimeException {

	private static final long serialVersionUID = -632229513667261515L;

	public CustomizationEditRuntimeException() {
		super();
	}

	public CustomizationEditRuntimeException(final String message) {
		super(message);
	}

	public CustomizationEditRuntimeException(final Throwable cause) {
		super(cause);
	}

	public CustomizationEditRuntimeException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
