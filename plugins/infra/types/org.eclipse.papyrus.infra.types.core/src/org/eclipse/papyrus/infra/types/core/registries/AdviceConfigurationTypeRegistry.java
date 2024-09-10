/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 459174
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.types.core.registries;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.emf.type.core.IAdviceBindingDescriptor;
import org.eclipse.papyrus.infra.types.AdviceConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.core.Activator;
import org.eclipse.papyrus.infra.types.core.IConfiguredEditHelperAdviceDescriptor;
import org.eclipse.papyrus.infra.types.core.extensionpoints.IAdviceKindExtensionPoint;
import org.eclipse.papyrus.infra.types.core.factories.IEditHelperAdviceFactory;
import org.eclipse.papyrus.infra.types.core.factories.impl.DefaultAdviceBindingFactory;
import org.eclipse.papyrus.infra.types.core.factories.impl.DefaultEditHelperAdviceFactory;

public class AdviceConfigurationTypeRegistry {

	private volatile static AdviceConfigurationTypeRegistry registry;

	protected Map<String, IEditHelperAdviceFactory<? extends AdviceConfiguration>> adviceConfigurationTypeToFactory = null;

	private AdviceConfigurationTypeRegistry() {
		super();
	}

	/**
	 * returns the singleton instance of this registry
	 *
	 * @return the singleton instance of this registry
	 */
	public static synchronized AdviceConfigurationTypeRegistry getInstance() {
		if (registry == null) {
			registry = new AdviceConfigurationTypeRegistry();
			registry.init();
		}
		return registry;
	}

	/**
	 * Inits the registry.
	 */
	protected void init() {
		adviceConfigurationTypeToFactory = new HashMap<String, IEditHelperAdviceFactory<? extends AdviceConfiguration>>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(IAdviceKindExtensionPoint.EXTENSION_POINT_ID);
		for (IConfigurationElement configurationElement : elements) {
			String configurationClass = configurationElement.getAttribute(IAdviceKindExtensionPoint.CONFIGURATION_CLASS);
			try {
				Object factoryClass = configurationElement.createExecutableExtension(IAdviceKindExtensionPoint.FACTORY_CLASS);
				if (factoryClass instanceof IEditHelperAdviceFactory) {
					adviceConfigurationTypeToFactory.put(configurationClass, (IEditHelperAdviceFactory<?>) factoryClass);
				}
			} catch (CoreException e) {
				Activator.log.error(e);
			}
		}
		// Register default interpretations
		adviceConfigurationTypeToFactory.put(ElementTypesConfigurationsPackage.eINSTANCE.getEditHelperAdviceConfiguration().getInstanceTypeName(), new DefaultEditHelperAdviceFactory());
		adviceConfigurationTypeToFactory.put(ElementTypesConfigurationsPackage.eINSTANCE.getAdviceBindingConfiguration().getInstanceTypeName(), new DefaultAdviceBindingFactory());
		adviceConfigurationTypeToFactory.put(ElementTypesConfigurationsPackage.eINSTANCE.getExternallyRegisteredAdvice().getInstanceTypeName(), new DefaultAdviceBindingFactory());
	}

	public <T extends AdviceConfiguration> IEditHelperAdviceFactory<T> getFactory(AdviceConfiguration adviceConfiguration) {
		String adviceConfigurationType = adviceConfiguration.eClass().getInstanceTypeName();
		// We assume here that the right factory is registered for the right AdviceConfiguration
		@SuppressWarnings("unchecked")
		IEditHelperAdviceFactory<T> factory = (IEditHelperAdviceFactory<T>) adviceConfigurationTypeToFactory.get(adviceConfigurationType);

		return factory;
	}

	public <T extends AdviceConfiguration> IAdviceBindingDescriptor getEditHelperAdviceDecriptor(T adviceConfiguration) {
		IAdviceBindingDescriptor result = null;

		if (adviceConfiguration != null) {
			AdviceDescriptorAdapter adapter = AdviceDescriptorAdapter.get(adviceConfiguration);
			if (adapter == null) {
				IEditHelperAdviceFactory<T> factory = getFactory(adviceConfiguration);
				if (factory != null) {
					IConfiguredEditHelperAdviceDescriptor<T> editHelperAdvice = factory.createEditHelperAdviceDescriptor(adviceConfiguration);
					editHelperAdvice.init(adviceConfiguration);
					adapter = new AdviceDescriptorAdapter(editHelperAdvice);
					adviceConfiguration.eAdapters().add(adapter);
				}
			}

			if (adapter != null) {
				result = adapter.getAdviceDescriptor();
			}
		}

		return result;
	}

	//
	// Nested types
	//

	private static class AdviceDescriptorAdapter extends AdapterImpl {
		private final IAdviceBindingDescriptor adviceDescriptor;

		AdviceDescriptorAdapter(IAdviceBindingDescriptor adviceDescriptor) {
			super();

			this.adviceDescriptor = adviceDescriptor;
		}

		@Override
		public boolean isAdapterForType(Object type) {
			return type == IAdviceBindingDescriptor.class;
		}

		public IAdviceBindingDescriptor getAdviceDescriptor() {
			return adviceDescriptor;
		}

		static AdviceDescriptorAdapter get(AdviceConfiguration configuration) {
			return (AdviceDescriptorAdapter) EcoreUtil.getExistingAdapter(configuration, IAdviceBindingDescriptor.class);
		}
	}
}
