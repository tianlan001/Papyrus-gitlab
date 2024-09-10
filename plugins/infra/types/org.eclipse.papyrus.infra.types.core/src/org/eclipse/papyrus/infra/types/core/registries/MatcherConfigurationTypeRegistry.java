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
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.IElementMatcher;
import org.eclipse.papyrus.infra.types.AbstractMatcherConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.core.Activator;
import org.eclipse.papyrus.infra.types.core.extensionpoints.IMatcherConfigurationKindExtensionPoint;
import org.eclipse.papyrus.infra.types.core.factories.IMatcherFactory;
import org.eclipse.papyrus.infra.types.core.factories.impl.DefaultMatcherFactory;

public class MatcherConfigurationTypeRegistry {

	private volatile static MatcherConfigurationTypeRegistry registry;

	protected Map<String, IMatcherFactory<? extends AbstractMatcherConfiguration>> matcherConfigurationTypeToFactory = null;

	private MatcherConfigurationTypeRegistry() {
		super();
	}

	public static synchronized MatcherConfigurationTypeRegistry getInstance() {
		if (registry == null) {
			registry = new MatcherConfigurationTypeRegistry();
			registry.init();
		}
		return registry;
	}

	protected void init() {
		matcherConfigurationTypeToFactory = new HashMap<String, IMatcherFactory<? extends AbstractMatcherConfiguration>>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(IMatcherConfigurationKindExtensionPoint.EXTENSION_POINT_ID);
		for (IConfigurationElement configurationElement : elements) {
			String configurationClass = configurationElement.getAttribute(IMatcherConfigurationKindExtensionPoint.CONFIGURATION_CLASS);
			try {
				Object factoryClass = configurationElement.createExecutableExtension(IMatcherConfigurationKindExtensionPoint.FACTORY_CLASS);
				if (factoryClass instanceof IMatcherFactory) {
					matcherConfigurationTypeToFactory.put(configurationClass, (IMatcherFactory<?>) factoryClass);
				}
			} catch (CoreException e) {
				Activator.log.error(e);
			}
		}
		// Register default interpretation
		matcherConfigurationTypeToFactory.put(ElementTypesConfigurationsPackage.eINSTANCE.getMatcherConfiguration().getInstanceTypeName(), new DefaultMatcherFactory());
	}

	protected <T extends AbstractMatcherConfiguration> IMatcherFactory<T> getFactory(T matcherConfiguration) {
		String matcherConfigurationType = matcherConfiguration.eClass().getInstanceTypeName();
		// We assume here that the right factory is registered for the right MatcherConfiguration
		@SuppressWarnings("unchecked")
		IMatcherFactory<T> factory = (IMatcherFactory<T>) matcherConfigurationTypeToFactory.get(matcherConfigurationType);
		return factory;
	}

	public <T extends AbstractMatcherConfiguration> IElementMatcher getMatcher(T matcherConfiguration) {
		if (matcherConfiguration == null) {
			return null;
		} else {
			IMatcherFactory<T> factory = getFactory(matcherConfiguration);
			if (factory == null) {
				// Provide dummy interpretation
				IElementMatcher matcher = new DummyElementMatcher();
				return matcher;
			} else {
				IElementMatcher matcher = factory.createElementMatcher(matcherConfiguration);
				return matcher;
			}
		}
	}

	public class DummyElementMatcher implements IElementMatcher {

		/**
		 * @see org.eclipse.gmf.runtime.emf.type.core.IElementMatcher#matches(org.eclipse.emf.ecore.EObject)
		 *
		 * @param eObject
		 * @return
		 */
		@Override
		public boolean matches(EObject eObject) {
			return true;
		}

	}
}
