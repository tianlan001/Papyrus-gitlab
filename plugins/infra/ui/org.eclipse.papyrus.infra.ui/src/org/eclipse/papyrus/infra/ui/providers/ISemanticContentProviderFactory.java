/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.ui.providers;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.papyrus.infra.core.resource.IModel;
import org.eclipse.papyrus.infra.core.resource.ModelSet;

/**
 * <p>
 * A protocol for creation of semantic model content providers on EMF resource sets.
 * </p>
 * <p>
 * It is expected that {@link IModel}s representing semantic model content in the
 * {@link ModelSet} provide adapters of this interface type for the purpose of obtaining
 * suitable content-providers for presentation of the model content to the user.
 * Because there there are potentially multiple such {@code IModel}s that have
 * semantic content, it is possible that multiple content-providers will have to be
 * combined via the {@link #compose(ISemanticContentProviderFactory)} API.
 * </p>
 * 
 * @see IModel
 * @see #compose(ISemanticContentProviderFactory)
 * @since 1.2
 */
@FunctionalInterface
public interface ISemanticContentProviderFactory {
	/**
	 * Creates a semantic model content provider on the given {@code ResourceSet}.
	 * 
	 * @param resourceSet
	 *            a resource set
	 * 
	 * @return the semantic model content provider
	 */
	ITreeContentProvider createSemanticContentProvider(ResourceSet resourceSet);

	/**
	 * Obtains a factory that composes my provider with an{@code other} factory's provider.
	 * 
	 * @param other
	 *            another semantic content-provider factory
	 * @return the composed factory, which generally creates composed content providers
	 */
	default ISemanticContentProviderFactory compose(ISemanticContentProviderFactory other) {
		return new CompositeSemanticContentProviderFactory(this, other);
	}
}
