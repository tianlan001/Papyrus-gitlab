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

package org.eclipse.papyrus.infra.core.internal.clipboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.plugin.RegistryReader;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.clipboard.ICopierFactory;
import org.eclipse.papyrus.infra.core.clipboard.ICopierFactory.Configuration;

/**
 * Registry of configurator extensions on the
 * {@code org.eclipse.papyrus.infra.core.copier} point.
 */
class CopierConfiguratorRegistry implements ICopierFactory.Configurator {

	private static final String EXT_PT = "copier"; //$NON-NLS-1$

	static CopierConfiguratorRegistry INSTANCE = new CopierConfiguratorRegistry();

	private final Collection<ICopierFactory.Configurator> configurators;

	private CopierConfiguratorRegistry() {
		super();

		configurators = new Reader(Platform.getExtensionRegistry(), Activator.PLUGIN_ID, EXT_PT).load();
	}

	@Override
	public void configureCopier(ICopierFactory.Configuration copierConfiguration) {
		configurators.forEach(c -> c.configureCopier(copierConfiguration));
	}

	//
	// Nested types
	//

	private static class Reader extends RegistryReader {

		private static final String FACTORY_CONFIGURATION = "factoryConfiguration"; //$NON-NLS-1$
		private static final String CONFIGURATOR = "configurator"; //$NON-NLS-1$

		private Collection<ICopierFactory.Configurator> configurators;
		private ICopierFactory.Configurator currentConfigurator;

		// Not really a predicate, but it is a boolean-valued function
		private Predicate<IConfigurationElement> configurationReader;

		Reader(IExtensionRegistry pluginRegistry, String pluginID, String extensionPointID) {
			super(pluginRegistry, pluginID, extensionPointID);
		}

		Collection<ICopierFactory.Configurator> load() {
			Collection<ICopierFactory.Configurator> result = new ArrayList<>();
			configurators = result;

			try {
				readRegistry();
			} finally {
				configurators = null;
			}

			return result;
		}

		@Override
		protected boolean readElement(IConfigurationElement element) {
			boolean result = false;

			if (FACTORY_CONFIGURATION.equals(element.getName())) {
				result = true;

				if (element.getAttribute(CONFIGURATOR) != null) {
					// Custom configurator
					currentConfigurator = new ConfiguratorDescriptor(element);
					configurationReader = null;
				} else {
					// Default configurator
					DefaultConfigurator configurator = new DefaultConfigurator();
					currentConfigurator = configurator;
					configurationReader = configurator::readElement;
				}

				configurators.add(currentConfigurator);
			} else if (currentConfigurator != null) {
				if (configurationReader != null) {
					result = configurationReader.test(element);
				} else {
					// Accept anything (who knows what a custom configurator may need?)
					result = true;
				}
			}

			return result;
		}

		//
		// Nested types
		//

		private class ConfiguratorDescriptor extends PluginClassDescriptor implements ICopierFactory.Configurator {

			private Optional<ICopierFactory.Configurator> resolved;

			ConfiguratorDescriptor(IConfigurationElement element) {
				super(element, CONFIGURATOR);
			}

			@Override
			public void configureCopier(Configuration copierConfiguration) {
				resolve().ifPresent(c -> c.configureCopier(copierConfiguration));
			}

			Optional<ICopierFactory.Configurator> resolve() {
				if (resolved == null) {
					ICopierFactory.Configurator delegate = null;

					try {
						delegate = (ICopierFactory.Configurator) createInstance();
					} catch (ClassCastException e) {
						Activator.log.warn("Not an ICopierFactory.Configurator extension in " + element.getContributor().getName()); //$NON-NLS-1$
					} catch (WrappedException e) {
						Activator.log.log(((CoreException) e.exception()).getStatus());
					} catch (Exception e) {
						Activator.log.error("Unhandled exception creating copier factory configurator extension in " + element.getContributor().getName(), e); //$NON-NLS-1$
					}

					resolved = Optional.ofNullable(delegate);
				}

				return resolved;
			}
		}
	}
}
