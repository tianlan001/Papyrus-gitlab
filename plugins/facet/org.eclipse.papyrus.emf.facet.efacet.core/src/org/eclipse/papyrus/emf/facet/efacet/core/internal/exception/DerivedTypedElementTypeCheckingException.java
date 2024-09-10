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
 *     Nicolas Bros (Mia-Software) - Bug 362191 - [Restructuring] Query mechanism for eFacet2
 */
package org.eclipse.papyrus.emf.facet.efacet.core.internal.exception;

import org.eclipse.papyrus.emf.facet.efacet.core.exception.DerivedTypedElementException;

/**
 * This exception occurs when the type of the result of evaluating a derived typed element is not
 * the one that was expected
 *
 * @since 0.2
 */
public class DerivedTypedElementTypeCheckingException extends DerivedTypedElementException {

	private static final long serialVersionUID = -9064274334817642819L;

	public DerivedTypedElementTypeCheckingException(final String message) {
		super(message);
	}

	public DerivedTypedElementTypeCheckingException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public DerivedTypedElementTypeCheckingException(final Throwable cause) {
		super(cause);
	}
}
