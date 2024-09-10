/*******************************************************************************
 * Copyright (c) 2012 Mia-Software
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - Bug 361612 - New core for new version of the Facet metamodel
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.efacet.core.internal.exception;

/**
 * This exception is raised when an object cannot be extended by a facet because the class of the object does not correspond
 * to the extended metaclass of the facet.
 *
 * @since 0.2
 */
public class NonApplicableFacetException extends Exception {

	private static final long serialVersionUID = 2984069177830836577L;
}
