/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.URIConverter;

/**
 * A service that is like the EMF {@link URIConverter} but that is discoverable
 * via OSGi and is intended to handle custom URI schemes, not arbitrary mappings
 * that are better handled by EMF's URI mapping extension point.
 * Registered services must declare the URI scheme that they handle via the
 * {@link #SCHEME_PROPERTY} property.
 */
public interface URIConverterService {

	/** A shared service instance that delegates to registered converter services. */
	URIConverterService INSTANCE = new DelegatingURIConverterService();

	/**
	 * OSGi service property indicating the URI scheme or schemes (if an array)
	 * that the converter service can convert.
	 */
	String SCHEME_PROPERTY = "papyrus.toolsmiths.uri.scheme"; //$NON-NLS-1$

	/**
	 * Normalize a URI in the given resource set {@code context}.
	 *
	 * @param uri
	 *            a URI to convert
	 * @param context
	 *            the resource set, which may be used to delegate to its {@link URIConverter}, if appropriate
	 * @return the converted URI, or the original URI if it cannot be converted
	 */
	URI normalize(URI uri, ResourceSet context);

}
