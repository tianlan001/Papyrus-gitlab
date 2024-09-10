/*******************************************************************************
 * Copyright (c) 2011 Mia-Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Nicolas Bros (Mia-Software) - Bug 361612 - New core for new version of the Facet metamodel
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.efacet.core.internal.exception;

/**
 * Happens when there is an error evaluating a Facet's conformance typed element.
 *
 * @since 0.2
 */
public class FacetConformanceEvaluationException extends Exception {
	private static final long serialVersionUID = -5306430483154103388L;

	public FacetConformanceEvaluationException(final String message) {
		super(message);
	}

	public FacetConformanceEvaluationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public FacetConformanceEvaluationException(final Throwable cause) {
		super(cause);
	}
}
