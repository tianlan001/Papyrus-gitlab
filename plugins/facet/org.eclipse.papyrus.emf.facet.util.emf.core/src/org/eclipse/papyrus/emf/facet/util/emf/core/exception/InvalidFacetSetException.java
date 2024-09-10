/**
 * Copyright (c) 2011 Mia-Software.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas Guyomar (Mia-Software) - Bug 338811 - A model registration method in the interface ICatalogSetManager
 *   Gregoire Dupe (Mia-Software) - Bug 373078 - API Cleaning
 */
package org.eclipse.papyrus.emf.facet.util.emf.core.exception;

/**
 * This exception is raised when an invalid FacetSet is found
 *
 * @since 0.2
 */
public final class InvalidFacetSetException extends Exception {

	private static final long serialVersionUID = -7684948344074635385L;

	public InvalidFacetSetException(final String message) {
		super(message);
	}

	public InvalidFacetSetException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public InvalidFacetSetException(final Throwable cause) {
		super(cause);
	}

}
