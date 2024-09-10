/*****************************************************************************
 * Copyright (c) 2014 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  CEA LIST - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.registries;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.gmf.runtime.emf.type.core.IContainerDescriptor;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration;
import org.eclipse.papyrus.infra.types.ContainerConfiguration;
import org.eclipse.papyrus.infra.types.core.Activator;
import org.eclipse.papyrus.infra.types.core.IConfiguredContainerDescriptor;
import org.eclipse.papyrus.infra.types.core.extensionpoints.IContainerConfigurationKindExtensionPoint;
import org.eclipse.papyrus.infra.types.core.factories.IContainerFactory;

/**
 * Registry that manages all possible containerConfiguration
 */
public class ContainerConfigurationTypeRegistry {

	private volatile static ContainerConfigurationTypeRegistry registry;

	protected Map<String, IContainerFactory<? extends ContainerConfiguration>> containerConfigurationTypeToFactory = null;

	private ContainerConfigurationTypeRegistry() {
		super();
	}

	/**
	 * returns the singleton instance of this registry
	 *
	 * @return the singleton instance of this registry
	 */
	public static synchronized ContainerConfigurationTypeRegistry getInstance() {
		if (registry == null) {
			registry = new ContainerConfigurationTypeRegistry();
			registry.init();
		}
		return registry;
	}

	/**
	 * Inits the registry.
	 */
	protected void init() {
		containerConfigurationTypeToFactory = new HashMap<String, IContainerFactory<? extends ContainerConfiguration>>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(IContainerConfigurationKindExtensionPoint.EXTENSION_POINT_ID);
		for (IConfigurationElement configurationElement : elements) {
			String configurationClass = configurationElement.getAttribute(IContainerConfigurationKindExtensionPoint.CONFIGURATION_CLASS);
			try {
				Object factoryClass = configurationElement.createExecutableExtension(IContainerConfigurationKindExtensionPoint.FACTORY_CLASS);
				if (factoryClass instanceof IContainerFactory) {
					containerConfigurationTypeToFactory.put(configurationClass, (IContainerFactory<?>) factoryClass);
				}
			} catch (CoreException e) {
				Activator.log.error(e);
			}
		}
	}

	protected <T extends ContainerConfiguration> IContainerFactory<T> getFactory(ContainerConfiguration containerConfiguration) {
		String containerConfigurationType = containerConfiguration.eClass().getInstanceTypeName();
		// We assume here that the right factory is registered for the right ContainerConfiguration
		@SuppressWarnings("unchecked")
		IContainerFactory<T> factory = (IContainerFactory<T>) containerConfigurationTypeToFactory.get(containerConfigurationType);
		return factory;
	}

	public <T extends ContainerConfiguration> IContainerDescriptor getContainerDescriptor(T containerConfiguration) {
		if (containerConfiguration == null) {
			return null;
		} else {
			IContainerFactory<T> factory = getFactory(containerConfiguration);
			IConfiguredContainerDescriptor<T> containerDescriptor;
			if (factory == null) {
				// Provide default interpretation
				containerDescriptor = new DefaultContainerDescriptor<T>(containerConfiguration);
			} else {
				containerDescriptor = factory.createContainerDescriptor(containerConfiguration);
			}
			containerDescriptor.init(containerConfiguration);
			return containerDescriptor;
		}
	}

	public class DefaultContainerDescriptor<T extends ContainerConfiguration> implements IConfiguredContainerDescriptor<T> {

		protected ContainerConfiguration containerConfiguration;

		protected IElementMatcher matcher;

		protected EReference[] references;

		public DefaultContainerDescriptor(ContainerConfiguration containerConfiguration) {
			AbstractMatcherConfiguration matcherConfiguration = containerConfiguration.getContainerMatcherConfiguration();
			matcher = MatcherConfigurationTypeRegistry.getInstance().getMatcher(matcherConfiguration);
			EList<EReference> eContainmentFeatures = containerConfiguration.getEContainmentFeatures();
			references = eContainmentFeatures.toArray(new EReference[] {});
		}

		public IElementMatcher getMatcher() {
			return matcher;
		}

		public EReference[] getContainmentFeatures() {
			return references;
		}

		public void init(ContainerConfiguration containerConfiguration) {
			this.containerConfiguration = containerConfiguration;
		}
	}
}
