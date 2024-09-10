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

package org.eclipse.papyrus.infra.core.sasheditor.contentprovider;

import java.util.Objects;
import java.util.function.Consumer;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * Partial implementation of the {@link IPageModel} protocol.
 * 
 * @since 2.0
 */
public abstract class AbstractPageModel implements IPageModel {

	private ILabelProvider tabLabelProvider;
	private Consumer<String> onLabelChanged = this::noop;

	private final ILabelProviderListener labelListener = event -> {
		if (event.getElement() == getRawModel()) {
			onLabelChanged.accept(getTabTitle());
		}
	};

	/**
	 * Initializes me with the label provider to use to render tabs.
	 *
	 * @param tabLabelProvider
	 *            a label provider accepting my {@linkplain IPageModel#getRawModel() model}.
	 *            This label provider should not be shared with other clients; I will dispose it when I am {@linkplain #dispose() disposed}
	 * 
	 * @throws NullPointerException
	 *             if the {@code tabLabelProvider} is {@code null}
	 */
	public AbstractPageModel(ILabelProvider tabLabelProvider) {
		super();

		this.tabLabelProvider = Objects.requireNonNull(tabLabelProvider, "null tabLabelProvider"); //$NON-NLS-1$
		tabLabelProvider.addListener(labelListener);
	}

	/**
	 * I remove listener from my label provider.
	 */
	@Override
	public void dispose() {
		tabLabelProvider.removeListener(labelListener);
		tabLabelProvider.dispose();
	}

	protected ILabelProvider getTabLabelProvider() {
		return tabLabelProvider;
	}

	@Override
	public String getTabTitle() {
		return tabLabelProvider.getText(getRawModel());
	}

	@Override
	public Image getTabIcon() {
		return tabLabelProvider.getImage(getRawModel());
	}

	public void onLabelChanged(Consumer<? super String> handler) {
		onLabelChanged = onLabelChanged.andThen(handler);
	}

	private void noop(String label) {
		// Pass
	}
}
