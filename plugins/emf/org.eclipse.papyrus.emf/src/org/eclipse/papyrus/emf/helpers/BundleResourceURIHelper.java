/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.helpers;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.emf.Activator;
import org.osgi.framework.Bundle;

/**
 * This class allows to help to find the name of a bundle providing a given EMF Resource
 */
public class BundleResourceURIHelper {

	/**
	 * This map contains the uri declared in xml file header and the name of the bundle provided them
	 */
	private static final Map<String, String> URI_VS_BUNDLE_NAME = new HashMap<>();

	/**
	 * Instance of this class
	 */
	public static final BundleResourceURIHelper INSTANCE = new BundleResourceURIHelper();

	/**
	 *
	 * Constructor.
	 *
	 */
	private BundleResourceURIHelper() {
		// to prevent instanciation
	}

	/**
	 *
	 * @param resource
	 *            a resource
	 * @return
	 *         the bundle providing this resource
	 */
	public String getBundleNameFromResource(final Resource resource) {
		final URI uri = resource.getURI();

		if (uri.isPlatformPlugin() || uri.isPlatformResource()) {
			final String projectName = uri.segment(1);
			return projectName;
		}

		// in this case uri.hasAuthority() returns true
		final String uriToString = uri.toString();
		// maybe the uri is declared as nsURI (see UML, and gmf notation for example
		return getBundleNameFromNS_URI(uriToString);
	}

	/**
	 *
	 * @param nsURIs
	 *            a collection of URI
	 * @return
	 *         a set with the bundle name providing the nsURI
	 */
	public Set<String> getBundleNameFromNS_URI(final Collection<String> nsURIs) {
		final Set<String> result = new HashSet<>();
		for (final String currentNS_URI : nsURIs) {
			final String bundleName = getBundleNameFromNS_URI(currentNS_URI);
			if (bundleName != null) {
				result.add(bundleName);
			} else {
				Activator.log.warn(NLS.bind("Papyrus: We don't found the bundle providing the nsURI {0}", currentNS_URI)); //$NON-NLS-1$
			}
		}

		return result;
	}

	/**
	 *
	 * @param nsURI
	 *            the nsURI from a metamodel
	 * @return
	 *         the bundle provided the EPackage corresponding to this nsURI, or <code>null</code> if not found
	 */
	public String getBundleNameFromNS_URI(final String nsURI) {
		String bundleName = URI_VS_BUNDLE_NAME.get(nsURI);
		if (bundleName == null) {
			final EPackage ePackage = EPackageRegistryImpl.INSTANCE.getEPackage(nsURI);
			if (ePackage != null) {
				final Class<?> class_ = ePackage.getClass();
				final Bundle b = org.osgi.framework.FrameworkUtil.getBundle(class_);
				if (b == null) {
					throw new UnsupportedOperationException();
				}
				bundleName = b.getSymbolicName();
				if (bundleName != null) {
					URI_VS_BUNDLE_NAME.put(nsURI, b.getSymbolicName());
				} else {
					Activator.log.warn(NLS.bind("Papyrus: We don't found a bundle named {0}.", bundleName)); //$NON-NLS-1$
				}
			}
		}
		return bundleName;
	}

}
