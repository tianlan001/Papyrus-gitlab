/**
 * Copyright (c) 2009 Mia-Software.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Gregoire Dupe (Mia-Software) - initial API and implementation
 */
package org.eclipse.papyrus.emf.facet.efacet.core.exception;

import org.eclipse.papyrus.emf.facet.efacet.core.internal.exception.DerivedTypedElementTypeCheckingException;

/**
 * This exception occurs when the type of a query result is not the one expected
 *
 * @deprecated replaced by {@link DerivedTypedElementTypeCheckingException}
 */
@Deprecated
public class QueryTypeCheckingException extends QueryException {

	private static final long serialVersionUID = -9064274334817642819L;

	public QueryTypeCheckingException(final String message) {
		super(message);
	}

	public QueryTypeCheckingException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public QueryTypeCheckingException(final Throwable cause) {
		super(cause);
	}
}
