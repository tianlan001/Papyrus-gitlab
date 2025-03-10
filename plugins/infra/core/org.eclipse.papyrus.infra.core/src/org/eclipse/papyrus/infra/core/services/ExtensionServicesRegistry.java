/*****************************************************************************
 * Copyright (c) 2011, 2016 LIFL, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  LIFL - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.core.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.services.ServiceDescriptor.ServiceTypeKind;

/**
 * ServiceRegistry reading and registering services declared in Eclipse
 * Extensions.
 *
 * @author dumoulin
 *
 */
public class ExtensionServicesRegistry extends ServicesRegistry {

	/** ID of the extension (schema filename) */
	public static final String SERVICE_EXTENSION_ID = "service";

	/** Namespace where to look for the extension points. */
	protected String extensionPointNamespace;

	/** Extension point name inside the extension description **/
	public static final String SERVICE_EXTENSIONPOINT = "service";

	/** ServiceFactory Extension point **/
	public static final String SERVICE_FACTORY_ELEMENT_NAME = "serviceFactory";

	/** constant for the attribute factoryClass **/
	public static final String CONTEXTCLASS_ATTRIBUTE = "contextClass";

	/** extension point propertyname */
	private final static String STARTKIND_PROPERTY = "startKind";

	/** name of the dependsOn element */
	private static final String DEPENDSON_ELEMENT_NAME = "dependsOn";

	/** name of the key attribute inside the DEPENDSON element */
	private static final String DEPENDSON_KEY_ATTRIBUTE_NAME = "serviceKeyRef";

	/**
	 * Initializes me with the default extension-point namespace from which to load
	 * registered services.
	 *
	 * @throws ServiceException
	 *             on any problem in the loading of service registrations
	 * @since 2.0
	 */
	public ExtensionServicesRegistry() throws ServiceException {
		this(Activator.PLUGIN_ID);
	}

	/**
	 * Initializes me with the specified extension-point namespace from which to load
	 * registered services.
	 * Only the plug-in infra core can create instance, it is forbidden outside this plugins
	 *
	 * @param extensionPointNamespace
	 *            the extension point namespace from which to
	 *            load service registration extensions
	 *
	 * @throws ServiceException
	 *             on any problem in the loading of service registrations
	 */
	public ExtensionServicesRegistry(String extensionPointNamespace) throws ServiceException {
		this.extensionPointNamespace = extensionPointNamespace;
		registerDeclaredExtensions();
	}

	/**
	 * Register the services declared in Eclipse Extension.
	 *
	 * @throws Exception
	 */
	private void registerDeclaredExtensions() throws ServiceException {

		List<ServiceDescriptor> descriptors = new ArrayList<>();
		List<ServiceException> exceptions = null;

		// Reading data from plugins
		IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(extensionPointNamespace, SERVICE_EXTENSION_ID);

		for (IConfigurationElement ele : configElements) {
			ServiceDescriptor desc;
			if (SERVICE_EXTENSIONPOINT.equals(ele.getName())) {
				// Read a Service
				try {
					desc = readServiceDescriptor(ele);
					desc.setServiceTypeKind(ServiceTypeKind.service);
					// Add created desc
					descriptors.add(desc);
				} catch (ServiceException e) {
					// record exceptions
					if (exceptions == null) {
						exceptions = new ArrayList<>();
					}
					exceptions.add(e);
				}
			} else if (SERVICE_FACTORY_ELEMENT_NAME.equals(ele.getName())) {
				// Read a Service Factory
				try {
					desc = readServiceDescriptor(ele);
					desc.setServiceTypeKind(ServiceTypeKind.serviceFactory);
					// Add created desc
					descriptors.add(desc);
				} catch (ServiceException e) {
					// record exceptions
					if (exceptions == null) {
						exceptions = new ArrayList<>();
					}
					exceptions.add(e);
				}
			}
		}

		// Add found descriptors
		for (ServiceDescriptor desc : descriptors) {
			add(desc);
		}

		// Throw exceptions if pb encountered
		if (exceptions != null) {
			if (exceptions.size() == 1) {
				throw exceptions.get(0);
			} else {
				throw new ServiceException("Somme services are not started (first is shown)", exceptions.get(0)); //$NON-NLS-1$
			}

		}

	}

	/**
	 * Read descriptor values from provided element.
	 *
	 * @param ele
	 * @return
	 * @throws ServiceException
	 */
	private ServiceDescriptor readServiceDescriptor(IConfigurationElement ele) throws ServiceException {

		// classname
		String serviceClassname = ele.getAttribute("classname");

		// key
		String key = ele.getAttribute("id");
		if (key == null || key.length() == 0) {
			key = serviceClassname;
		}

		// Service start kind
		ServiceStartKind serviceStartKind = ServiceStartKind.LAZY;
		String serviceStartKindStr = ele.getAttribute(STARTKIND_PROPERTY);
		if (serviceStartKindStr != null && serviceStartKindStr.length() > 0) {
			try {
				serviceStartKind = ServiceStartKind.valueOf(serviceStartKindStr.toUpperCase());
			} catch (IllegalArgumentException e) {
				// Can't convert property
				throw new ServiceException("Can't convert property " + STARTKIND_PROPERTY + "(plugin=" + ele.getContributor() + "declaringExtension=" + ele.getDeclaringExtension() + ")", e);
			}
		}

		// priority
		int priority = 1;
		String priorityStr = ele.getAttribute("priority");
		if (priorityStr != null && priorityStr.length() > 0) {
			try {
				priority = Integer.parseInt(priorityStr);
			} catch (NumberFormatException e) {
			}
		}

		// Read dependsOn keys
		List<String> keys = getDependsOn(ele);

		// Create descriptor
		ServiceDescriptor desc = new ServiceDescriptor(key, serviceClassname, serviceStartKind, priority);
		desc.setClassBundleID(ele.getContributor().getName());

		if (keys.size() > 0) {
			desc.setRequiredServiceKeys(keys);
		}

		return desc;
	}

	/**
	 * Add dependsOn keys.
	 *
	 * @param parentElement
	 * @param model
	 */
	private List<String> getDependsOn(IConfigurationElement parentElement) {

		List<String> keys = new ArrayList<>();

		// Get children
		IConfigurationElement[] configElements = parentElement.getChildren(DEPENDSON_ELEMENT_NAME);

		for (IConfigurationElement ele : configElements) {
			String key = ele.getAttribute(DEPENDSON_KEY_ATTRIBUTE_NAME);
			if (key != null && key.length() > 0) {
				keys.add(key.trim());
			}
		}

		if (keys.size() == 0) {
			return Collections.emptyList();
		}

		return keys;
	}

}
