/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.internal.bundles.tests;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;

/**
 * @author Vincent LORENZO
 * @since 1.3
 */
public final class PapyrusBundleDescriptionRegistry {

	/**
	 * a cache to avoid to load and parse several time the same bundle
	 */
	private final Map<String, PapyrusBundleDescription> loadedBundlesCache;

	/**
	 * the singleton of this class
	 */
	public static final PapyrusBundleDescriptionRegistry INSTANCE = new PapyrusBundleDescriptionRegistry();

	/**
	 * 
	 * @param bundleName
	 *            the name of a bundle
	 * @return
	 * 		the {@link PapyrusBundleDescription} wrapping the {@link Bundle}, if exists, or <code>null</code>
	 */
	public final PapyrusBundleDescription getPapyrusBundleDescription(final String bundleName) {
		if (null != bundleName) {
			final Bundle bundle = Platform.getBundle(bundleName);
			if (null != bundle) {
				// ok the bundle exists
				if (null == loadedBundlesCache.get(bundleName)) {
					loadedBundlesCache.put(bundleName, new PapyrusBundleDescription(bundleName));
				}
				return loadedBundlesCache.get(bundleName);

			}
		}
		return null;
	}

	/**
	 * this method clear the registry
	 */
	public final void clearRegistry() {
		this.loadedBundlesCache.clear();
	}

	/**
	 * 
	 * Constructor.
	 *
	 */
	private PapyrusBundleDescriptionRegistry() {
		// to prevent instanciation
		loadedBundlesCache = new HashMap<String, PapyrusBundleDescription>();
	}



}
