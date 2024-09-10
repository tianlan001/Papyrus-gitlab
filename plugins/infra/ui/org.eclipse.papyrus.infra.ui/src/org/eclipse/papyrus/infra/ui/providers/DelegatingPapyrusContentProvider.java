/*****************************************************************************
 * Copyright (c) 2012, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus (CEA) - bug 410346
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.ui.providers;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.tools.util.TypeUtils;
import org.eclipse.papyrus.infra.widgets.providers.IAdaptableContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IHierarchicContentProvider;
import org.eclipse.papyrus.infra.widgets.providers.IStaticContentProvider;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * A content-provider implementing the complete set of Papyrus-specific content-provider APIs that
 * delegates those APIs to another provider according to its actual capabilities.
 * 
 * @since 1.2
 */
public class DelegatingPapyrusContentProvider implements IAdaptableContentProvider, IHierarchicContentProvider, IStaticContentProvider {

	private static final Object[] NONE = {};

	private final ITreeContentProvider treeDelegate;
	private final IAdaptableContentProvider adaptableDelegate;
	private final IHierarchicContentProvider hierarchicDelegate;
	private final IStaticContentProvider staticDelegate;

	public DelegatingPapyrusContentProvider(ITreeContentProvider delegate) {
		super();

		treeDelegate = delegate;
		adaptableDelegate = TypeUtils.as(delegate, IAdaptableContentProvider.class);
		hierarchicDelegate = TypeUtils.as(delegate, IHierarchicContentProvider.class);
		staticDelegate = TypeUtils.as(delegate, IStaticContentProvider.class);
	}

	/**
	 * Obtains a content-provider based on the given {@code provider} that implements all of the
	 * the Papyrus-specific extension protocols.
	 * 
	 * @param provider
	 *            a tree-content provider
	 * @return a complete provider, which may be a delegating provider or may be the original
	 *         {@code provider} if it is already complete
	 */
	public static ITreeContentProvider wrap(ITreeContentProvider provider) {
		return ((provider instanceof IAdaptableContentProvider) && (provider instanceof IHierarchicContentProvider) && (provider instanceof IStaticContentProvider))
				? provider
				: new DelegatingPapyrusContentProvider(provider);
	}

	/**
	 * Obtains a content-provider based on the given providers that implements all of the
	 * the Papyrus-specific extension protocols.
	 * 
	 * @param first,&nbsp;second,&nbsp;rest
	 *            two or more tree-content providers
	 * @return a complete provider based on the given providers
	 */
	public static ITreeContentProvider compose(ITreeContentProvider first, ITreeContentProvider second, ITreeContentProvider... rest) {
		return compose(Lists.asList(first, second, rest));
	}

	/**
	 * Obtains a content-provider based on the given {@code providers} that implements all of the
	 * the Papyrus-specific extension protocols.
	 * 
	 * @param providers
	 *            zero or more tree-content providers
	 * @return a complete provider based on the given {@code providers}
	 */
	public static ITreeContentProvider compose(Iterable<? extends ITreeContentProvider> providers) {
		ITreeContentProvider result;

		// Obtain optimal result in case of a single provider
		if (providers instanceof Collection<?>) {
			Collection<? extends ITreeContentProvider> collection = (Collection<? extends ITreeContentProvider>) providers;
			switch (collection.size()) {
			case 0:
				result = new CompositePapyrusContentProvider();
				break;
			case 1:
				result = wrap(Iterables.getOnlyElement(collection));
				break;
			default:
				result = new CompositePapyrusContentProvider(providers);
				break;
			}
		} else {
			Iterator<? extends ITreeContentProvider> iter = providers.iterator();
			if (!iter.hasNext()) {
				result = new CompositePapyrusContentProvider();
			} else {
				ITreeContentProvider provider = iter.next();
				if (iter.hasNext()) {
					result = new CompositePapyrusContentProvider(providers);
				} else {
					result = wrap(provider);
				}
			}
		}

		return result;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		return treeDelegate.getElements(inputElement);
	}

	@Override
	public void dispose() {
		treeDelegate.dispose();
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		treeDelegate.inputChanged(viewer, oldInput, newInput);
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		return treeDelegate.getChildren(parentElement);
	}

	@Override
	public Object getParent(Object element) {
		return treeDelegate.getParent(element);
	}

	@Override
	public boolean hasChildren(Object element) {
		return treeDelegate.hasChildren(element);
	}

	@Override
	public Object getAdaptedValue(Object containerElement) {
		return (adaptableDelegate == null)
				? containerElement
				: adaptableDelegate.getAdaptedValue(containerElement);
	}

	@Override
	public boolean isValidValue(Object element) {
		return (hierarchicDelegate == null) || hierarchicDelegate.isValidValue(element);
	}

	@Override
	public Object[] getElements() {
		return (staticDelegate == null)
				? NONE
				: staticDelegate.getElements();
	}

}
