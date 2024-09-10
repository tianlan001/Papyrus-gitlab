/*******************************************************************************
 * Copyright (c) 2012, 2017 CEA LIST, Christian W. Damus, and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Nicolas Bros (Mia-Software) - Bug 374758 - [Table] repair the table
 *    Gregoire Dupe (Mia-Software) - Bug 372626 - Aggregates
 *    Thomas Cicognani (Soft-Maint) - Bug 420192 - UnsupportedOperationException in a usefull method
 *    Christian W. Damus - bug 515913
 *******************************************************************************/
package org.eclipse.papyrus.emf.facet.custom.core.internal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.papyrus.emf.facet.custom.metamodel.v0_2_0.custom.Customization;
import org.eclipse.papyrus.emf.facet.efacet.metamodel.v0_2_0.efacet.FacetSet;

/** Represents a list of {@link Customization}s that exists as a subset of a delegate list of {@link FacetSet}s. */
public class CustomizationsDelegatingList extends BasicEList<Customization> {
	private static final long serialVersionUID = 1L;

	private final List<FacetSet> delegate;

	public CustomizationsDelegatingList(final List<FacetSet> delegate) {
		super(customizations(delegate));
		this.delegate = delegate;
	}

	/**
	 * @since 3.0
	 */
	@Override
	protected void didAdd(int index, Customization newObject) {
		// Insert at the corresponding location in the delegate
		if (index > 0) {
			index = delegate.indexOf(get(index - 1)) + 1;
		}

		delegate.add(index, newObject);
	}

	/**
	 * @since 3.0
	 */
	@Override
	protected void didSet(int index, Customization newObject, Customization oldObject) {
		index = delegate.indexOf(oldObject);
		delegate.set(index, newObject);
	}

	/**
	 * @since 3.0
	 */
	@Override
	protected void didRemove(int index, Customization oldObject) {
		delegate.remove(oldObject);
	}

	/**
	 * @since 3.0
	 */
	@Override
	protected void didClear(int size, Object[] oldObjects) {
		delegate.removeAll(Arrays.asList(oldObjects));
	}

	/**
	 * @since 3.0
	 */
	@Override
	protected void didMove(int index, Customization movedObject, int oldIndex) {
		// Move to the corresponding location in the delegate
		if (index > 0) {
			index = delegate.indexOf(get(index - 1)) + 1;
		}
		if (delegate instanceof EList<?>) {
			((EList<FacetSet>) delegate).move(index, movedObject);
		} else {
			// Do it the hard way
			delegate.remove(movedObject);
			delegate.add(index, movedObject);
		}
	}

	/**
	 * Obtains the subset of a list of facet-sets that are customizations.
	 * 
	 * @param facetSets
	 *            the superset
	 * @return the subset of customizations
	 * @since 3.0
	 */
	protected static List<Customization> customizations(List<FacetSet> facetSets) {
		return facetSets.stream()
				.filter(Customization.class::isInstance).map(Customization.class::cast)
				.collect(Collectors.toList());
	}
}
