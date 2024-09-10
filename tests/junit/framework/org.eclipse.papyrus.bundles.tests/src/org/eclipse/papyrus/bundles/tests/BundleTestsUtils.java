/*****************************************************************************
 * Copyright (c) 2012, 2016, 2023 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) Vincent.Lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  Pauline DEVILLE (CEA LIST) pauline.deville@cea.fr - Bug 581848
 *  Vincent Lorenzo (CEA LIST) vincent.Lorenzo@cea.fr - Bug 582667
 *****************************************************************************/
package org.eclipse.papyrus.bundles.tests;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.internal.runtime.InternalPlatform;
import org.eclipse.pde.internal.core.PDECore;
import org.eclipse.pde.internal.core.feature.Feature;
import org.eclipse.pde.internal.core.ifeature.IFeature;
import org.eclipse.pde.internal.core.ifeature.IFeatureModel;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * @noreference This class is not intended to be referenced by clients.
 */
public class BundleTestsUtils {

	public static final String INCUBATION = "(Incubation)"; //$NON-NLS-1$

	public static final String VENDOR_NAME = "Eclipse Modeling Project"; //$NON-NLS-1$

	public static final String BUNDLE_NAME = "Bundle-Name"; //$NON-NLS-1$

	public static final String BUNDLE_VENDOR = "Bundle-Vendor"; //$NON-NLS-1$

	public static final String BUNDLE_VERSION = "Bundle-Version"; //$NON-NLS-1$

	public static final String PAPYRUS_PREFIX = "org.eclipse.papyrus."; //$NON-NLS-1$

	public static final String BUNDLE_REQUIREDEXECUTIONENVIRONMENT = "Bundle-RequiredExecutionEnvironment"; //$NON-NLS-1$

	public static final String REQUIRE_CAPABILITY = "Require-Capability"; //$NON-NLS-1$

	public static final String BUNDLE_IMPORT_PACKAGE = "Import-Package"; //$NON-NLS-1$

	public static final String JAVA_VERSION_5 = "J2SE-1.5"; //$NON-NLS-1$

	public static final String JAVA_VERSION_6 = "JavaSE-1.6"; //$NON-NLS-1$

	public static final String JAVA_VERSION_7 = "JavaSE-1.7"; //$NON-NLS-1$

	public static final String JAVA_VERSION_8 = "JavaSE-1.8"; //$NON-NLS-1$

	public static final String JAVA_VERSION_11 = "JavaSE-11"; //$NON-NLS-1$

	public static final String JAVA_VERSION_17 = "JavaSE-17"; //$NON-NLS-1$

	public static final String JAVA_VERSION_REGEX = Stream.of(JAVA_VERSION_5, JAVA_VERSION_6, JAVA_VERSION_7, JAVA_VERSION_8, JAVA_VERSION_11, JAVA_VERSION_17)
			.map(Pattern::quote)
			.collect(Collectors.joining("|")); //$NON-NLS-1$

	public static final String JAVA_17_CAPABILITY = "osgi\\.ee;filter:=\"\\(&\\(osgi\\.ee=JavaSE\\)\\(version=17\\)\\)\""; //$NON-NLS-1$

	public static final String REQUIRE_BUNDLE = "Require-Bundle"; //$NON-NLS-1$

	private BundleTestsUtils() {
		// to prevent instantiation
	}

	/**
	 *
	 * @return the Bundle with a name beginning by {@link #PAPYRUS_PREFIX}
	 */
	public static List<Bundle> getPapyrusBundles() {
		final List<Bundle> papyrusBundle = new ArrayList<>();
		BundleContext context = InternalPlatform.getDefault().getBundleContext();
		org.osgi.framework.Bundle[] bundles = context.getBundles();
		for (int i = 0; i < bundles.length; i++) {
			String currentName = bundles[i].getSymbolicName();
			if (currentName.startsWith(PAPYRUS_PREFIX)) {
				papyrusBundle.add(bundles[i]);
			}
		}

		return papyrusBundle;
	}

	/**
	 *
	 * @param bundle
	 *            a bundle
	 * @return
	 *         <code>true</code> if the bundle represents a Java Project
	 */
	public static boolean isJavaProject(final Bundle bundle) {
		// we are looking for folders "org/eclipse/papyrus" that contains classes. If not, it is not a Java project
		URL res = bundle.getResource("org/eclipse/papyrus"); //$NON-NLS-1$
		return res != null;
	}

	/**
	 *
	 * @return
	 *         the list of the Papyrus features
	 */
	public static List<Feature> getPapyrusFeature() {
		final List<Feature> features = new ArrayList<>();
		org.eclipse.pde.internal.core.FeatureModelManager manager = PDECore.getDefault().getFeatureModelManager();
		IFeatureModel[] models2 = manager.getModels();
		for (IFeatureModel iFeatureModel : models2) {
			final IFeature feature = iFeatureModel.getFeature();
			final String id = feature.getId();
			if (id.startsWith(PAPYRUS_PREFIX)) {
				features.add((Feature) feature);
			}
		}
		return features;
	}
}
