/*****************************************************************************
 * Copyright (c) 2014 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.assistant.internal.core;

import java.util.Collections;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider;
import org.eclipse.papyrus.infra.gmfdiag.assistant.core.IModelingAssistantModelProvider;

import com.google.common.collect.Iterables;

/**
 * A provider that loads modeling assistant models from EMF resources.
 */
public class DefaultModelingAssistantModelProvider implements IModelingAssistantModelProvider {
	private final URI resourceURI;

	/**
	 * Initializes me.
	 */
	public DefaultModelingAssistantModelProvider(URI resourceURI) {
		this.resourceURI = resourceURI;
	}

	@Override
	public Iterable<? extends IModelingAssistantProvider> loadProviders(ResourceSet resourceSet) {
		Iterable<? extends IModelingAssistantProvider> result;

		Resource resource = null;

		try {
			resource = resourceSet.getResource(resourceURI, true);
		} catch (Exception e) {
			AssistantPlugin.log.error("Error loading modeling assistants model " + resourceURI, e); //$NON-NLS-1$

			// In case we managed to get something
			resource = resourceSet.getResource(resourceURI, false);
		}

		if (resource != null) {
			// Because we track all the resources loaded by this provider
			EcoreUtil.resolveAll(resource);

			result = Iterables.filter(resource.getContents(), IModelingAssistantProvider.class);
		} else {
			result = Collections.emptyList();
		}

		return result;
	}

}
