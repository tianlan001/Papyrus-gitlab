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
 *  Christian W. Damus (CEA) - Workspace-independent model validation view (CDO)
 *  Mickael ADAM (ALL4TEC) - Bug 500219 - implementation of IStyledLabelProvider
 *  Christian W. Damus - bug 508629
 *****************************************************************************/
package org.eclipse.papyrus.infra.services.labelprovider.service;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.papyrus.infra.services.labelprovider.Activator;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

/**
 * An ExtensibleLabelProvider encapsulates a set of LabelProviders. Each LabelProvider handles only a few kind of elements.
 * The ExtensibleLabelProvider retrieves the most appropriate LabelProvider for each object.
 *
 * When more than one LabelProvider match an element, the one with the smaller priority is used.
 *
 * @author Camille Letavernier
 *
 */
public class ExtensibleLabelProvider implements ILabelProvider, IQualifierLabelProvider, ILabelProviderListener, IColorProvider, IFontProvider, IStyledLabelProvider {

	private final CopyOnWriteArrayList<ILabelProviderListener> listeners;

	private final SortedMap<Integer, List<IFilteredLabelProvider>> providers;

	private final ILabelProvider defaultProvider;

	public ExtensibleLabelProvider() {
		listeners = new CopyOnWriteArrayList<ILabelProviderListener>();
		providers = new TreeMap<Integer, List<IFilteredLabelProvider>>();

		defaultProvider = new LabelProvider();
	}

	@Override
	public void addListener(ILabelProviderListener listener) {
		listeners.addIfAbsent(listener);
	}

	@Override
	public void dispose() {
		listeners.clear();
		for (List<IFilteredLabelProvider> filteredProviders : providers.values()) {
			for (IFilteredLabelProvider provider : filteredProviders) {
				provider.dispose();
			}
		}
		providers.clear();
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {
		listeners.remove(listener);
	}

	public void registerProvider(int priority, IFilteredLabelProvider provider) {
		getProviders(priority).add(provider);
		provider.addListener(this);
	}

	@Override
	public Color getForeground(Object element) {
		IColorProvider provider = getProvider(element, IColorProvider.class);
		if (provider != null) {
			return provider.getForeground(element);
		}
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		IColorProvider provider = getProvider(element, IColorProvider.class);
		if (provider != null) {
			return provider.getBackground(element);
		}
		return null;
	}

	@Override
	public Font getFont(Object element) {
		IFontProvider provider = getProvider(element, IFontProvider.class);
		if (provider != null) {
			return provider.getFont(element);
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {
		return getProvider(element).getImage(element);
	}

	@Override
	public String getText(Object element) {
		return getProvider(element).getText(element);
	}

	@Override
	public Image getQualifierImage(Object element) {
		Image result = null;

		IQualifierLabelProvider provider = getProvider(element, IQualifierLabelProvider.class);
		if (provider != null) {
			result = provider.getQualifierImage(element);
		}

		return result;
	}

	@Override
	public String getQualifierText(Object element) {
		String result = null;

		IQualifierLabelProvider provider = getProvider(element, IQualifierLabelProvider.class);
		if (provider != null) {
			result = provider.getQualifierText(element);
		}

		return result;
	}

	protected final ILabelProvider getProvider(Object element) {
		for (List<IFilteredLabelProvider> filteredProviders : providers.values()) {
			for (IFilteredLabelProvider provider : filteredProviders) {
				if (provider.accept(element)) {
					return provider;
				}
			}
		}

		return defaultProvider;
	}

	protected final <T> T getProvider(Object element, Class<T> type) {
		for (List<IFilteredLabelProvider> filteredProviders : providers.values()) {
			for (IFilteredLabelProvider provider : filteredProviders) {
				if ((type.isInstance(provider)) && provider.accept(element)) {
					return type.cast(provider);
				}
			}
		}

		return type.isInstance(defaultProvider) ? type.cast(defaultProvider) : null;
	}

	protected final List<IFilteredLabelProvider> getProviders(int priority) {
		if (!providers.containsKey(priority)) {
			providers.put(priority, new LinkedList<IFilteredLabelProvider>());
		}

		return providers.get(priority);
	}

	/**
	 * {@inheritDoc}
	 *
	 * Forwards the event to each listener
	 */
	@Override
	public void labelProviderChanged(LabelProviderChangedEvent event) {
		if (!listeners.isEmpty()) {
			LabelProviderChangedEvent myEvent = new LabelProviderChangedEvent(this, event.getElements());
			for (ILabelProviderListener listener : listeners) {
				try {
					listener.labelProviderChanged(myEvent);
				} catch (Exception e) {
					Activator.log.error("Uncaught exception in label provider listener", e); //$NON-NLS-1$
				}
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider#getStyledText(java.lang.Object)
	 */
	@Override
	public StyledString getStyledText(final Object element) {
		StyledString text = null;

		ILabelProvider provider = getProvider(element);
		if (provider instanceof IStyledLabelProvider) {
			text = ((IStyledLabelProvider) provider).getStyledText(element);
		} else {
			text = new StyledString(provider.getText(element));
		}

		return text;
	}
}
