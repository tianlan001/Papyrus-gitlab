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

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.papyrus.infra.core.Activator;
import org.eclipse.papyrus.infra.core.clipboard.ICopierFactory;
import org.eclipse.papyrus.infra.core.clipboard.ICopierFactory.Configuration;
import org.eclipse.papyrus.infra.core.clipboard.ICopierFactory.Configurator;

import com.google.common.base.Strings;

/**
 * Default configurator that reads configuration rules from the extension point.
 */
class DefaultConfigurator implements Configurator {

	private static final String REFERENCE_FILTER = "referenceFilter"; //$NON-NLS-1$
	private static final String REFERENCE_URI = "referenceURI"; //$NON-NLS-1$
	private static final String OWNER_TYPE_URI = "ownerTypeURI"; //$NON-NLS-1$

	private final Collection<ICopierFactory.Configurator> configurationRules = new ArrayList<>();

	/**
	 * Initializes me.
	 */
	public DefaultConfigurator() {
		super();
	}

	@Override
	public void configureCopier(Configuration copierConfiguration) {
		configurationRules.forEach(rule -> rule.configureCopier(copierConfiguration));
	}

	boolean readElement(IConfigurationElement element) {
		boolean result = false;

		if (REFERENCE_FILTER.equals(element.getName())) {
			String referenceURI = element.getAttribute(REFERENCE_URI);
			if (!Strings.isNullOrEmpty(referenceURI)) {
				result = true;
				String ownerTypeURI = element.getAttribute(OWNER_TYPE_URI);

				try {
					configurationRules.add(referenceFilter(referenceURI, ownerTypeURI));
				} catch (Exception e) {
					Activator.log.warn(String.format("Invalid fopier reference filter extension in %s: %s", //$NON-NLS-1$
							element.getContributor().getName(), e.getMessage()));
				}
			}
		}

		return result;
	}

	private <T extends EModelElement> Optional<T> getEcoreElement(String uriString, Class<T> metaclass) {
		EObject result = null;
		URI uri = URI.createURI(uriString, true);

		EPackage epackage = EPackage.Registry.INSTANCE.getEPackage(uri.trimFragment().toString());
		if (epackage != null) {
			result = epackage.eResource().getEObject(uri.fragment());
		}

		return Optional.ofNullable(result).map(metaclass::cast);
	}

	private ICopierFactory.Configurator referenceFilter(String referenceURI, String ownerTypeURI) {
		EReference reference = getEcoreElement(referenceURI, EReference.class).orElseThrow(
				() -> new IllegalArgumentException("missing or invalid reference URI")); //$NON-NLS-1$

		EClass ownerType = getEcoreElement(ownerTypeURI, EClass.class)
				.orElse(reference.getEContainingClass());

		return config -> config.filterReferences(
				(ref, owner) -> (ref == reference) && ownerType.isInstance(owner));
	}
}
