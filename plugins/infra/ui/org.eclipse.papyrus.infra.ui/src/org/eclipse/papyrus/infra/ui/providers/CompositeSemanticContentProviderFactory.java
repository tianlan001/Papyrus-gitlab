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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ITreeContentProvider;

/**
 * Default implementation of a composite content-provider factory.
 */
class CompositeSemanticContentProviderFactory implements ISemanticContentProviderFactory {
	private final List<ISemanticContentProviderFactory> factories;

	CompositeSemanticContentProviderFactory(ISemanticContentProviderFactory first, ISemanticContentProviderFactory second) {
		super();

		factories = Arrays.asList(first, second);
	}

	private CompositeSemanticContentProviderFactory(CompositeSemanticContentProviderFactory composite, ISemanticContentProviderFactory other) {
		super();

		this.factories = concat(composite, other);
	}

	private final List<ISemanticContentProviderFactory> concat(CompositeSemanticContentProviderFactory composite, ISemanticContentProviderFactory other) {
		List<ISemanticContentProviderFactory> result;

		if (other instanceof CompositeSemanticContentProviderFactory) {
			List<ISemanticContentProviderFactory> otherFactories = ((CompositeSemanticContentProviderFactory) other).factories;
			result = new ArrayList<>(composite.factories.size() + otherFactories.size());
			result.addAll(composite.factories);
			result.addAll(otherFactories);
		} else {
			result = new ArrayList<>(composite.factories.size() + 1);
			result.addAll(composite.factories);
			result.add(other);
		}

		return result;
	}

	@Override
	public ITreeContentProvider createSemanticContentProvider(ResourceSet resourceSet) {
		return DelegatingPapyrusContentProvider.compose(factories.stream()
				.map(f -> f.createSemanticContentProvider(resourceSet))
				.collect(Collectors.toList()));
	}

	@Override
	public ISemanticContentProviderFactory compose(ISemanticContentProviderFactory other) {
		return new CompositeSemanticContentProviderFactory(this, other);
	}

}
