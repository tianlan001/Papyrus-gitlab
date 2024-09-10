/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
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

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.plugin.RegistryReader;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.language.ILanguage;
import org.eclipse.papyrus.infra.core.language.ILanguageProvider;
import org.eclipse.papyrus.infra.core.language.ILanguageService;
import org.eclipse.papyrus.infra.core.language.Language;
import org.eclipse.papyrus.infra.core.language.Version;
import org.eclipse.papyrus.infra.core.resource.ModelSet;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/**
 * A registry of contributed {@link ILanguageProvider} implementations plugged in on my extension point.
 */
public class LanguageProviderRegistry implements ILanguageProvider.Registry {

	private static final String EXT_POINT = "language"; //$NON-NLS-1$

	private static final ILanguageProvider NULL_PROVIDER = new ILanguageProvider() {

		@Override
		public Iterable<ILanguage> getLanguages(ILanguageService languageService, URI modelURI, boolean uriHasFileExtension) {
			return Collections.emptyList();
		}
	};

	private final List<ILanguageProvider> providers = Lists.newArrayListWithExpectedSize(2);

	private boolean needPrune;

	public LanguageProviderRegistry() {
		super();

		new MyRegistryReader().readRegistry();
	}

	/**
	 * Prune out any null providers (failed to initialize) and replace
	 * descriptors that have been instantiated by their instances, to avoid
	 * delegation.
	 */
	private void prune() {
		if (needPrune) {
			needPrune = false;
			for (ListIterator<ILanguageProvider> iter = providers.listIterator(); iter.hasNext();) {

				ILanguageProvider next = iter.next();
				if (next == NULL_PROVIDER) {
					iter.remove(); // Don't need this, any more
				} else if (next instanceof MyRegistryReader.Descriptor) {
					MyRegistryReader.Descriptor desc = (MyRegistryReader.Descriptor) next;
					if (desc.instance != null) {
						iter.set(desc.instance);
					}
				}
			}
		}
	}

	@Override
	public Collection<ILanguageProvider> getProviders() {
		prune();
		return Collections.unmodifiableCollection(providers);
	}

	private void removeProvider(String className) {
		synchronized (providers) {
			for (Iterator<ILanguageProvider> iter = providers.iterator(); iter.hasNext();) {

				ILanguageProvider next = iter.next();
				if (next instanceof MyRegistryReader.Descriptor) {
					MyRegistryReader.Descriptor desc = (MyRegistryReader.Descriptor) next;
					if (className.equals(desc.getClassName())) {
						iter.remove();
						break;
					}
				} else if (className.equals(next.getClass().getName())) {
					iter.remove();
					break;
				}
			}
		}
	}

	//
	// Nested types
	//

	private class MyRegistryReader extends RegistryReader {

		private static final String A_CLASS = "class"; //$NON-NLS-1$

		private static final String E_PROVIDER = "provider"; //$NON-NLS-1$

		private static final String E_CONTENT_TYPE = "content-type"; //$NON-NLS-1$
		private static final String E_LANGUAGE = "language"; //$NON-NLS-1$
		private static final String A_ID = "id"; //$NON-NLS-1$
		private static final String A_VERSION = "version"; //$NON-NLS-1$
		private static final String A_NAME = "name"; //$NON-NLS-1$

		private Descriptor currentDescriptor;

		MyRegistryReader() {
			super(Platform.getExtensionRegistry(), Activator.PLUGIN_ID, EXT_POINT);
		}

		@Override
		protected boolean readElement(IConfigurationElement element, boolean add) {
			return add ? handleAdd(element) : handleRemove(element);
		}

		private boolean handleAdd(IConfigurationElement element) {
			boolean result = true;

			if (E_PROVIDER.equals(element.getName())) {
				currentDescriptor = new Descriptor(element, A_CLASS);
				providers.add(currentDescriptor);
			}

			return result;
		}

		private boolean handleRemove(IConfigurationElement element) {
			boolean result = true;

			if (E_PROVIDER.equals(element.getName())) {
				String className = getClassName(element, A_CLASS);
				if (className != null) {
					removeProvider(className);
				}
			}

			return result;
		}

		String getClassName(IConfigurationElement provider, String attributeName) {
			String result = provider.getAttribute(attributeName);
			if (result == null) {
				IConfigurationElement[] classes = provider.getChildren(attributeName);
				if (classes.length > 0) { // Assume a single occurrence
					result = classes[0].getAttribute("class"); //$NON-NLS-1$ It's always 'class' attribute of a nested element
				}
			}
			return result;
		}

		private class Descriptor extends PluginClassDescriptor implements ILanguageProvider {

			private ILanguageProvider instance;

			Descriptor(IConfigurationElement element, String attributeName) {
				super(element, attributeName);
			}

			String getClassName() {
				return MyRegistryReader.this.getClassName(element, attributeName);
			}

			ILanguageProvider getInstance() {
				if (instance == null) {
					try {
						String className = getClassName();
						if (className == null) {
							// It's the default implementation
							instance = createDefaultProvider();
						} else {
							instance = (ILanguageProvider) createInstance();
						}
					} catch (Exception e) {
						Activator.log.error("Failed to instantiate language provider extension.", e);
						instance = NULL_PROVIDER;
					}

					needPrune = true;
				}

				return instance;
			}

			@Override
			public Iterable<ILanguage> getLanguages(ILanguageService languageService, URI modelURI, boolean uriHasFileExtension) {
				return getInstance().getLanguages(languageService, modelURI, uriHasFileExtension);
			}

			@Override
			public java.lang.Iterable<ILanguage> getLanguages(ILanguageService languageService, ModelSet modelSet) {
				return getInstance().getLanguages(languageService, modelSet);
			}

			ILanguageProvider createDefaultProvider() throws CoreException {
				DefaultLanguageProvider result = new DefaultLanguageProvider();

				for (IConfigurationElement next : element.getChildren()) {
					if (E_CONTENT_TYPE.equals(next.getName())) {
						addContentType(result, next);
					} else if (E_LANGUAGE.equals(next.getName())) {
						addLanguage(result, next);
					}
				}

				return result;
			}

			private void addContentType(DefaultLanguageProvider provider, IConfigurationElement contentType) {
				String id = contentType.getAttribute(A_ID);
				if (Strings.isNullOrEmpty(id)) {
					logMissingAttribute(contentType, A_ID);
				} else {
					provider.addContentType(id);
				}
			}

			private void addLanguage(DefaultLanguageProvider provider, IConfigurationElement language) throws CoreException {
				String className = language.getAttribute(A_CLASS);
				if (Strings.isNullOrEmpty(className)) {
					className = Language.class.getName();
				}

				// Maybe the plug-in specifies the default Language class explicitly
				if (!className.equals(Language.class.getName())) {
					provider.addLanguage(language, A_CLASS);
				} else {
					String id = language.getAttribute(A_ID);
					if (Strings.isNullOrEmpty(id)) {
						logMissingAttribute(language, A_ID);
					}
					String version = language.getAttribute(A_VERSION);
					if (Strings.isNullOrEmpty(version)) {
						logMissingAttribute(language, A_VERSION);
					}
					String name = language.getAttribute(A_NAME);
					if (Strings.isNullOrEmpty(name)) {
						logMissingAttribute(language, A_NAME);
					}
					provider.addLanguage(new Language(id, new Version(version), name));
				}
			}
		}
	}
}
