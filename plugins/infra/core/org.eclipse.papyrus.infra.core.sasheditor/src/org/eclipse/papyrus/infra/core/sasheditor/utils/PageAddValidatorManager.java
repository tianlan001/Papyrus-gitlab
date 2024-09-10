/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.core.sasheditor.utils;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.infra.core.sasheditor.Activator;
import org.eclipse.papyrus.infra.core.sasheditor.api.IPageAddValidator;
import org.osgi.framework.Bundle;

/**
 * This allows to define a manager for the page add validators.
 *
 * @since 2.2
 */
public class PageAddValidatorManager {

	/**
	 * The page add validator extension identifier.
	 */
	private static String PAGEADDVALIDATOR_EXTENSION_ID = "org.eclipse.papyrus.infra.core.sasheditor.pageAddValidator"; //$NON-NLS-1$

	/**
	 * The class attribute of the extension point.
	 */
	private static String CLASS_ATTRIBUTE = "class"; //$NON-NLS-1$


	/**
	 * The singleton instance.
	 */
	private static PageAddValidatorManager instance;

	/**
	 * The declared validators in extension points.
	 */
	private Set<IPageAddValidator> validators;

	/**
	 * Private constructor to avoid external instanciation.
	 */
	private PageAddValidatorManager() {
		validators = new HashSet<>();
		initializeValidators();
	}

	/**
	 * Get the singleton instance.
	 *
	 * @return The singleton instance.
	 */
	public static PageAddValidatorManager getInstance() {
		if (null == instance) {
			instance = new PageAddValidatorManager();
		}
		return instance;
	}

	/**
	 * This allows to initialize the validators for the extension points.
	 */
	private void initializeValidators() {
		// Reading data from extensions
		final IConfigurationElement[] configElements = Platform.getExtensionRegistry().getConfigurationElementsFor(PAGEADDVALIDATOR_EXTENSION_ID);
		for (int i = 0; i < configElements.length; i++) {
			final IConfigurationElement element = configElements[i];
			final Bundle extensionBundle = Platform.getBundle(element.getDeclaringExtension().getNamespaceIdentifier());
			final String classAttribute = element.getAttribute(CLASS_ATTRIBUTE);
			try {
				// Get the class from the referenced one
				final Class<?> clazz = extensionBundle.loadClass(classAttribute);
				final Object obj = clazz.newInstance();
				validators.add((IPageAddValidator) obj);
			} catch (Exception e) {
				Activator.log.error("The class '" + classAttribute + "' cannot be accessible from plug-in '" + extensionBundle.getSymbolicName() + "'", e); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			}
		}
	}

	/**
	 * Determinate if the validators allow to add page or not.
	 *
	 * @param pageIdentifier
	 *            The page identifier.
	 * @return <code>true</code> if the page can be added, <code>false</code> otherwise.
	 */
	public boolean isValid(final Object pageIdentifier) {
		for (final IPageAddValidator validator : validators) {
			if (!validator.isValid(pageIdentifier)) {
				return false;
			}
		}
		return true;
	}

}
