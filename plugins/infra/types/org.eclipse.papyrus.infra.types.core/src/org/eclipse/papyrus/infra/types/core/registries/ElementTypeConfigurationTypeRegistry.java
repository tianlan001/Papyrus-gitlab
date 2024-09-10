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
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.papyrus.infra.types.ElementTypeConfiguration;
import org.eclipse.papyrus.infra.types.ElementTypesConfigurationsPackage;
import org.eclipse.papyrus.infra.types.core.Activator;
import org.eclipse.papyrus.infra.types.core.extensionpoints.IElementTypeConfigurationKindExtensionPoint;
import org.eclipse.papyrus.infra.types.core.factories.IElementTypeConfigurationFactory;
import org.eclipse.papyrus.infra.types.core.factories.impl.MetamodelTypeFactory;
import org.eclipse.papyrus.infra.types.core.factories.impl.SpecializationTypeFactory;

/**
 * Registry that manages all possible elementTypeConfigurationTypes
 */
public class ElementTypeConfigurationTypeRegistry {

	private volatile static ElementTypeConfigurationTypeRegistry registry;

	protected Map<String, IElementTypeConfigurationFactory<? extends ElementTypeConfiguration>> elementTypeConfigurationTypeToFactory = null;

	private ElementTypeConfigurationTypeRegistry() {
		super();
	}

	/**
	 * returns the singleton instance of this registry
	 *
	 * @return the singleton instance of this registry
	 */
	public static synchronized ElementTypeConfigurationTypeRegistry getInstance() {
		if (registry == null) {
			registry = new ElementTypeConfigurationTypeRegistry();
			registry.init();
		}
		return registry;
	}

	/**
	 * Inits the registry.
	 */
	protected void init() {
		elementTypeConfigurationTypeToFactory = new HashMap<String, IElementTypeConfigurationFactory<? extends ElementTypeConfiguration>>();
		IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor(IElementTypeConfigurationKindExtensionPoint.EXTENSION_POINT_ID);
		for (IConfigurationElement configurationElement : elements) {
			String configurationClass = configurationElement.getAttribute(IElementTypeConfigurationKindExtensionPoint.CONFIGURATION_CLASS);
			try {
				Object factoryClass = configurationElement.createExecutableExtension(IElementTypeConfigurationKindExtensionPoint.FACTORY_CLASS);
				if (factoryClass instanceof IElementTypeConfigurationFactory) {
					elementTypeConfigurationTypeToFactory.put(configurationClass, (IElementTypeConfigurationFactory<?>) factoryClass);
				}
			} catch (CoreException e) {
				Activator.log.error(e);
			}
		}
		// Register default interpretations
		elementTypeConfigurationTypeToFactory.put(ElementTypesConfigurationsPackage.eINSTANCE.getMetamodelTypeConfiguration().getInstanceTypeName(), new MetamodelTypeFactory());
		elementTypeConfigurationTypeToFactory.put(ElementTypesConfigurationsPackage.eINSTANCE.getSpecializationTypeConfiguration().getInstanceTypeName(), new SpecializationTypeFactory());
	}

	protected <T extends ElementTypeConfiguration> IElementTypeConfigurationFactory<T> getFactory(ElementTypeConfiguration elementTypeConfiguration) {
		String elementTypeConfigurationType = elementTypeConfiguration.eClass().getInstanceTypeName();
		// We assume here that the right factory is registered for the right ElementTypeConfiguration
		@SuppressWarnings("unchecked")
		IElementTypeConfigurationFactory<T> factory = (IElementTypeConfigurationFactory<T>) elementTypeConfigurationTypeToFactory.get(elementTypeConfigurationType);
		return factory;
	}

	public <T extends ElementTypeConfiguration> IElementType getElementType(T elementTypeConfiguration) {
		if (elementTypeConfiguration == null) {
			return null;
		} else {
			IElementTypeConfigurationFactory<T> factory = getFactory(elementTypeConfiguration);
			if (factory != null) {
				IHintedType elementType = factory.createElementType(elementTypeConfiguration);
				return elementType;
			}
		}
		return null;
	}
}
