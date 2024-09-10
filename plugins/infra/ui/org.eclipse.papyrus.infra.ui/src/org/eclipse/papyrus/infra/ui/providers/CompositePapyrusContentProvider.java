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

import java.util.Objects;
import java.util.stream.Stream;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.widgets.providers.IAdaptableContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

import com.google.common.collect.Iterables;

/**
 * A content provider that synthesizes content from multiple other providers.
 * 
 * @since 1.2
 */
public class CompositePapyrusContentProvider implements IAdaptableContentProvider, IHierarchicContentProvider, IStaticContentProvider {

	private final ITreeContentProvider[] delegates;

	public CompositePapyrusContentProvider(ITreeContentProvider... delegates) {
		super();

		this.delegates = new ITreeContentProvider[delegates.length];
		for (int i = 0; i < delegates.length; i++) {
			// Wrap it or not, as needed
			this.delegates[i] = DelegatingPapyrusContentProvider.wrap(delegates[i]);
		}
	}

	public CompositePapyrusContentProvider(Iterable<? extends ITreeContentProvider> delegates) {
		this(Iterables.toArray(delegates, ITreeContentProvider.class));
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return Stream.of(delegates)
				.flatMap(d -> Stream.of(d.getElements(inputElement)))
				.toArray();
	}

	@Override
	public void dispose() {
		Stream.of(delegates).forEach(ITreeContentProvider::dispose);
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		Stream.of(delegates).forEach(d -> d.inputChanged(viewer, oldInput, newInput));
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return Stream.of(delegates)
				.flatMap(d -> Stream.of(d.getChildren(parentElement)))
				.toArray();
	}

	@Override
	public Object getParent(Object element) {
		return Stream.of(delegates)
				.map(d -> d.getParent(element))
				.filter(Objects::nonNull)
				.findAny().orElse(null);
	}

	@Override
	public boolean hasChildren(Object element) {
		return Stream.of(delegates)
				.anyMatch(d -> d.hasChildren(element));
	}

	@Override
	public Object getAdaptedValue(Object containerElement) {
		return Stream.of(delegates)
				.map(IAdaptableContentProvider.class::cast)
				.map(d -> d.getAdaptedValue(containerElement))
				.filter(Objects::nonNull)
				.findAny().orElse(containerElement);
	}

	@Override
	public boolean isValidValue(Object element) {
		return Stream.of(delegates)
				.map(IHierarchicContentProvider.class::cast)
				.anyMatch(d -> d.isValidValue(element));
	}

	@Override
	public Object[] getElements() {
		return Stream.of(delegates)
				.map(IStaticContentProvider.class::cast)
				.flatMap(d -> Stream.of(d.getElements()))
				.toArray();
	}

}
