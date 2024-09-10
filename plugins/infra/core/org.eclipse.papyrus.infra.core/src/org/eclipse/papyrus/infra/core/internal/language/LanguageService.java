/*****************************************************************************
 * Copyright (c) 2015, 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.infra.core.internal.language;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.eclipse.core.runtime.PlatformObject;
import org.eclipse.emf.common.util.URI;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.language.ILanguageChangeListener;
import org.eclipse.papyrus.infra.core.language.ILanguageProvider;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.language.LanguageChangeEvent;
import org.eclipse.papyrus.infra.core.resource.ModelSet;
import org.eclipse.papyrus.infra.core.services.IService;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.core.utils.ServiceUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * The implementation of the Papyrus Modeling Language Service.
 */
public class LanguageService extends PlatformObject implements ILanguageService, IService {

	private final CopyOnWriteArrayList<ILanguageProvider> languageProviders = Lists.newCopyOnWriteArrayList();

	private final CopyOnWriteArrayList<ILanguageChangeListener> languageListeners = Lists.newCopyOnWriteArrayList();

	private ServicesRegistry registry;

	public LanguageService() {
		super();
	}

	@Override
	public Set<ILanguage> getLanguages(URI modelURI, boolean uriHasFileExtension) {
		Set<ILanguage> result = Sets.newHashSet();

		for (ILanguageProvider next : languageProviders) {
			Iterables.addAll(result, next.getLanguages(this, modelURI, uriHasFileExtension));
		}

		return result;
	}

	@Override
	public Set<ILanguage> getLanguages(ModelSet modelSet) {
		Set<ILanguage> result = Sets.newHashSet();

		for (ILanguageProvider next : languageProviders) {
			Iterables.addAll(result, next.getLanguages(this, modelSet));
		}

		return result;
	}

	@Override
	public void addLanguageChangeListener(ILanguageChangeListener listener) {
		languageListeners.addIfAbsent(listener);
	}

	@Override
	public void removeLanguageChangeListener(ILanguageChangeListener listener) {
		languageListeners.remove(listener);
	}

	@Override
	public void addLanguageProvider(ILanguageProvider provider) {
		languageProviders.addIfAbsent(provider);
	}

	@Override
	public void removeLanguageProvider(ILanguageProvider provider) {
		if (provider != null) {
			languageProviders.remove(provider);
		}
	}

	@Override
	public void languagesChanged(LanguageChangeEvent event) {
		if (!languageListeners.isEmpty()) {
			// Forward to my own listeners
			event = new LanguageChangeEvent(this, event.getType(), event.getModelURI(), event.getURIHasExtension(), event.getLanguages());
			for (ILanguageChangeListener next : languageListeners) {
				try {
					next.languagesChanged(event);
				} catch (Exception e) {
					Activator.log.error("Uncaught exception in language change listener", e); //$NON-NLS-1$
				}
			}
		}
	}

	//
	// Adaptable API
	//


	/**
	 * Adaptation to other services in the registry takes precedence over registered adapter factories.
	 */
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		T result;

		if (adapter.isInstance(registry)) {
			result = adapter.cast(registry);
		} else {
			result = (registry == null) ? null : ServiceUtils.getInstance().getService(adapter, registry, null);
		}

		return (result != null) ? result : super.getAdapter(adapter);
	}

	//
	// Service lifecycle API
	//

	@Override
	public void init(ServicesRegistry servicesRegistry) throws ServiceException {
		this.registry = servicesRegistry;
	}

	@Override
	public void startService() throws ServiceException {
		languageProviders.addAllAbsent(ILanguageProvider.Registry.INSTANCE.getProviders());
	}

	@Override
	public void disposeService() throws ServiceException {
		languageProviders.clear();
		languageListeners.clear();
		registry = null;
	}

}
