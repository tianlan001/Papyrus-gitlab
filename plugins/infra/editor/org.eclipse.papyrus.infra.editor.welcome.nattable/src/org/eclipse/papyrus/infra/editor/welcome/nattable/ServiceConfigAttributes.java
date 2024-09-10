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

package org.eclipse.papyrus.infra.editor.welcome.nattable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.style.ConfigAttribute;

/**
 * A factory for unique NatTable configuration attributes for Papyrus services.
 */
public class ServiceConfigAttributes {
	private static Map<Class<?>, ConfigAttribute<?>> configAttributes = new HashMap<>();

	private ServiceConfigAttributes() {
		super();
	}

	/**
	 * Obtains the (unique) configuration attribute for the given service type.
	 * 
	 * @param serviceType
	 *            a service type
	 * 
	 * @return the corresponding configuration attribute
	 */
	@SuppressWarnings("unchecked")
	public static <T> ConfigAttribute<T> get(Class<T> serviceType) {
		return (ConfigAttribute<T>) configAttributes.computeIfAbsent(serviceType, key -> new ConfigAttribute<T>());
	}

	/**
	 * Obtains a builder-like configurator object that uses the given service accessor to obtain
	 * services to register.
	 * 
	 * @param registry
	 *            the NatTable configuration registry
	 * @param serviceAccessor
	 *            a service accessor function
	 * 
	 * @return the configurator
	 */
	public static Configurator with(IConfigRegistry registry, Function<? super Class<?>, ?> serviceAccessor) {
		return new Configurator(registry, serviceAccessor);
	}

	public static <T> void registerService(Class<T> serviceType, IConfigRegistry configRegistry, T service) {
		configRegistry.registerConfigAttribute(get(serviceType), service);
	}

	public static <T> void registerService(Class<T> serviceType, IConfigRegistry configRegistry, T service, String targetDisplayMode) {
		configRegistry.registerConfigAttribute(get(serviceType), service, targetDisplayMode);
	}

	public static <T> void registerService(Class<T> serviceType, IConfigRegistry configRegistry, T service, String targetDisplayMode, String label) {
		configRegistry.registerConfigAttribute(get(serviceType), service, targetDisplayMode, label);
	}

	public static <T> T getService(Class<T> serviceType, IConfigRegistry config, String targetDisplayMode, String... labels) {
		return config.getConfigAttribute(get(serviceType), targetDisplayMode, labels);
	}

	public static <T> T getService(Class<T> serviceType, IConfigRegistry config, String targetDisplayMode, List<String> labels) {
		return config.getConfigAttribute(get(serviceType), targetDisplayMode, labels);
	}

	public static <T> T getService(Class<T> serviceType, IConfigRegistry config, String targetDisplayMode, LabelStack labels) {
		return getService(serviceType, config, targetDisplayMode, labels.getLabels());
	}

	public static <T> T getService(Class<T> serviceType, IConfigRegistry config, ILayerCell cell) {
		return getService(serviceType, config, cell.getDisplayMode(), cell.getConfigLabels().getLabels());
	}

	//
	// Nested types
	//

	/**
	 * A builder-like configurator object that automatically registers services in a particular
	 * NatTable configuration registry.
	 */
	public static class Configurator {
		private final IConfigRegistry configRegistry;
		private final Function<? super Class<?>, ?> serviceAccessor;

		Configurator(IConfigRegistry configRegistry, Function<? super Class<?>, ?> serviceAccessor) {
			super();

			this.configRegistry = configRegistry;
			this.serviceAccessor = serviceAccessor;
		}

		<T> T getService(Class<T> serviceType) {
			@SuppressWarnings("unchecked")
			Function<? super Class<T>, T> accessor = (Function<? super Class<T>, T>) serviceAccessor;
			return accessor.apply(serviceType);
		}

		public <T> Configurator register(Class<T> serviceType) {
			registerService(serviceType, configRegistry, getService(serviceType));
			return this;
		}

		public <T> Configurator register(Class<T> serviceType, String targetDisplayMode) {
			registerService(serviceType, configRegistry, getService(serviceType), targetDisplayMode);
			return this;
		}

		public <T> Configurator register(Class<T> serviceType, String targetDisplayMode, String label) {
			registerService(serviceType, configRegistry, getService(serviceType), targetDisplayMode, label);
			return this;
		}
	}
}
