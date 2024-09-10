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

package org.eclipse.papyrus.infra.ui.architecture.providers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IItemStyledLabelProvider;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedADElement;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDescriptionLanguage;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureDomain;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureFramework;
import org.eclipse.papyrus.infra.core.architecture.merged.MergedArchitectureViewpoint;

/**
 * An item provider factory for the <em>Merged Architecture Description</em> façade API.
 */
public class MergedArchitectureItemProviderAdapterFactory extends AdapterFactoryImpl implements ComposeableAdapterFactory, IDisposable {

	private final Set<?> supportedTypes = Set.of(
			IItemLabelProvider.class,
			IItemStyledLabelProvider.class,
			IItemPropertySource.class,
			IStructuredItemContentProvider.class,
			ITreeItemContentProvider.class,
			MergedADElement.class.getPackage());

	private final Map<MergedADElement, MergedArchitectureItemProvider> providers = new HashMap<>();

	private ComposedAdapterFactory parent;

	@Override
	public boolean isFactoryForType(Object type) {
		return supportedTypes.contains(type);
	}

	@Override
	public Object adapt(Object object, Object type) {
		if (isFactoryForType(type)) {
			Object adapter = super.adapt(object, type);
			if (!(type instanceof Class<?>) || (((Class<?>) type).isInstance(adapter))) {
				return adapter;
			}
		}

		return null;
	}

	@Override
	protected Adapter createAdapter(Notifier target) {
		if (target instanceof MergedArchitectureDomain) {
			return createDomainItemProvider((MergedArchitectureDomain) target);
		}
		if (target instanceof MergedArchitectureDescriptionLanguage) {
			return createDescriptionLanguageItemProvider((MergedArchitectureDescriptionLanguage) target);
		}
		if (target instanceof MergedArchitectureFramework) {
			return createFrameworkItemProvider((MergedArchitectureFramework) target);
		}
		if (target instanceof MergedArchitectureViewpoint) {
			return createViewpointItemProvider((MergedArchitectureViewpoint) target);
		}
		return null;
	}

	protected MergedArchitectureItemProvider createDomainItemProvider(MergedArchitectureDomain target) {
		return providers.computeIfAbsent(target, __ -> new MergedArchitectureDomainItemProvider(this, target));
	}

	protected MergedArchitectureItemProvider createDescriptionLanguageItemProvider(MergedArchitectureDescriptionLanguage target) {
		return providers.computeIfAbsent(target, __ -> new MergedArchitectureDescriptionLanguageItemProvider(this, target));
	}

	protected MergedArchitectureItemProvider createFrameworkItemProvider(MergedArchitectureFramework target) {
		return providers.computeIfAbsent(target, __ -> new MergedArchitectureFrameworkItemProvider(this, target));
	}

	protected MergedArchitectureItemProvider createViewpointItemProvider(MergedArchitectureViewpoint target) {
		return providers.computeIfAbsent(target, __ -> new MergedArchitectureViewpointItemProvider(this, target));
	}

	@Override
	public void dispose() {
		providers.values().forEach(IDisposable::dispose);
		providers.clear();
	}

	@Override
	public ComposeableAdapterFactory getRootAdapterFactory() {
		return parent == null ? null : parent.getRootAdapterFactory();
	}

	@Override
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
		this.parent = parentAdapterFactory;
	}

}
