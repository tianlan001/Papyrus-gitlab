/*****************************************************************************
 * Copyright (c) 2016, 2017, 2019 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 514955
 *   Jeremie Tatibouet (CEA LIST) jeremie.tatibouet@cea.fr - Bug 553875
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.validation.internal;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.papyrus.infra.services.validation.Activator;
import org.eclipse.papyrus.infra.services.validation.IPapyrusDiagnostician;
import org.eclipse.papyrus.infra.services.validation.IValidationFilter;
import org.eclipse.papyrus.infra.services.validation.IValidationHook;

/**
 * Provides access to the extensions diagnosticians and validationHooks
 */
public class ValidationRegistry {

	public static final String ID_DIAGNOSTICIANS = Activator.PLUGIN_ID + ".diagnosticians"; //$NON-NLS-1$

	public static final String ID_VALIDATION_HOOKS = Activator.PLUGIN_ID + ".validationHooks"; //$NON-NLS-1$

	/**
	 * Constants for tags in two extensions points
	 */
	public static final String DIAGNOSTICIAN = "diagnostician"; //$NON-NLS-1$

	public static final String VALIDATION_HOOK = "hook"; //$NON-NLS-1$

	public static final String FILTER = "filter"; //$NON-NLS-1$

	private static final String PRIORITY = "priority"; //$NON-NLS-1$

	public enum HookType {
		BEFORE, AFTER
	};

	private enum Priority {
		DEFAULT, LOWEST, LOW, NORMAL, HIGH, HIGHEST;
	}

	/**
	 * Return a diagnostician for an element of a model.
	 *
	 * @param element
	 *            an element of a model (that must be contained in an eResource)
	 * @return
	 */
	public static IPapyrusDiagnostician getDiagnostician(EObject element) {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] configElements = sort(reg.getConfigurationElementsFor(ID_DIAGNOSTICIANS));
		for (IConfigurationElement configElement : configElements) {
			try {
				final Object obj = configElement.createExecutableExtension(FILTER);
				if (obj instanceof IValidationFilter) {
					IValidationFilter filter = (IValidationFilter) obj;
					if (filter.isApplicable(element)) {
						final Object diagnostician = configElement.createExecutableExtension(DIAGNOSTICIAN);
						if (diagnostician instanceof IPapyrusDiagnostician) {
							return (IPapyrusDiagnostician) diagnostician;
						}
					}
				}
			} catch (CoreException exception) {
				Activator.log.error(exception);
			}
		}
		// fall back to ecore diagnostician
		return new EcoreDiagnostician();
	}

	/**
	 * Sort an array of extension configuration elements by priority, from highest to lowest.
	 *
	 * @param configElements
	 *            an array of configuration elements
	 *
	 * @return the sorted array
	 */
	private static IConfigurationElement[] sort(IConfigurationElement[] configElements) {
		Comparator<IConfigurationElement> byPriority = new Comparator<IConfigurationElement>() {
			private Map<IConfigurationElement, Priority> priorities = new HashMap<IConfigurationElement, ValidationRegistry.Priority>();

			Priority getPriority(IConfigurationElement config) {
				Priority result = priorities.get(config);
				if (result == null) {
					String priorityName = config.getAttribute(PRIORITY);
					if (priorityName == null) {
						priorityName = Priority.DEFAULT.name();
					} else {
						priorityName = priorityName.toUpperCase();
					}

					try {
						result = Priority.valueOf(priorityName);
					} catch (Exception e) {
						// No such value? Go with the default
					}
					if (result == null) {
						result = Priority.DEFAULT;
					}

					priorities.put(config, result);
				}

				return result;
			}

			public int compare(IConfigurationElement o1, IConfigurationElement o2) {
				// Sort from highest to lowest priority
				return getPriority(o2).compareTo(getPriority(o1));
			}
		};

		Arrays.sort(configElements, byPriority);

		return configElements;
	}

	/**
	 * Obtain a diagnostician for a given language
	 *
	 * @param languageID
	 *            the id of the language for which we want to obtain the diagnostician
	 * @return the associated diagnostician
	 */
	public static IPapyrusDiagnostician getDiagnostician(String languageID) {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] configElements = reg.getConfigurationElementsFor(ID_DIAGNOSTICIANS);
		for (IConfigurationElement configElement : configElements) {
			try {
				final String iConfiguratorIDext = configElement.getAttribute("id"); //$NON-NLS-1$
				if ((iConfiguratorIDext != null) && iConfiguratorIDext.equals(languageID)) {
					final Object obj = configElement.createExecutableExtension("class"); //$NON-NLS-1$
					if (obj instanceof IPapyrusDiagnostician) {
						return (IPapyrusDiagnostician) obj;
					}
				}
			} catch (CoreException exception) {
				Activator.log.error(exception);
			}
		}
		return null;
	}

	/**
	 * Execute validation hooks
	 *
	 * @param element
	 *            An element of the model we want to validate
	 * @param hookType
	 *            The hook type (currently before or after)
	 */
	public static void executeHooks(EObject element, HookType hookType) {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IConfigurationElement[] configElements = reg.getConfigurationElementsFor(ID_VALIDATION_HOOKS);
		for (IConfigurationElement configElement : configElements) {
			try {
				final Object obj = configElement.createExecutableExtension(FILTER);
				if (obj instanceof IValidationFilter) {
					IValidationFilter filter = (IValidationFilter) obj;
					if (filter.isApplicable(element)) {
						final Object hookObj = configElement.createExecutableExtension(VALIDATION_HOOK);
						if (hookObj instanceof IValidationHook) {
							IValidationHook validationHook = (IValidationHook) hookObj;
							if (hookType == HookType.BEFORE) {
								try {
									validationHook.beforeValidation(element);
								} catch (Exception exception) {
									Activator.log.error("Before | validation hook for" + configElement.getAttribute(VALIDATION_HOOK) + "execution failed", exception); //$NON-NLS-1$ //$NON-NLS-2$
								}
							} else if (hookType == HookType.AFTER) {
								try {
									validationHook.afterValidation(element);
								} catch (Exception exception) {
									Activator.log.error("After | validation hook for" + configElement.getAttribute(VALIDATION_HOOK) + "execution failed", exception); //$NON-NLS-1$ //$NON-NLS-2$
								}
							}
						}
					}
				}
			} catch (CoreException exception) {
				Activator.log.error(exception);
			}
		}
	}
}
