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

package org.eclipse.papyrus.toolsmiths.validation.properties.internal;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.papyrus.infra.properties.catalog.PropertiesURIHandler;
import org.eclipse.papyrus.toolsmiths.validation.common.URIConverterService;
import org.osgi.service.component.annotations.Component;

/**
 * A URI converter service for the Properties framework's <tt>ppe</tt> URI scheme.
 */
@Component(property = { URIConverterService.SCHEME_PROPERTY + "=ppe" })
public class PropertiesURIConverterService implements URIConverterService {

	private final PropertiesURIHandler uriHandler = new PropertiesURIHandler();

	@Override
	public URI normalize(URI uri, ResourceSet context) {
		return uriHandler.canHandle(uri) ? uriHandler.getConvertedURI(uri) : uri;
	}

}
