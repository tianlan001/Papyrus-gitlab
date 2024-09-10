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

package org.eclipse.papyrus.infra.gmfdiag.assistant.core;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.IModelingAssistantProvider;

/**
 * A provider of modeling-assistant models.
 */
public interface IModelingAssistantModelProvider {
	/**
	 * Loads zero or more (presumably model-based) providers into the specified EMF resource set.
	 * Modeling assistant providers are requested at most once from any given model-provider extension. Therefore, this method
	 * should not dynamically determine providers to load but should be static. The modeling assistant providers that it
	 * returns may be more dynamic in the specific modeling assistants that they provide.
	 * 
	 * @param resourceSet
	 *            a resource set into which resources may be loaded to obtain modeling assistant models
	 * 
	 * @return zero or more provider instances
	 */
	Iterable<? extends IModelingAssistantProvider> loadProviders(ResourceSet resourceSet);
}
