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
 *  Vincent Lorenzo (CEA LIST) Vincent.Lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.internal.bundles.tests;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.util.NLS;
import org.eclipse.papyrus.bundles.tests.BundlesTests.Version;
import org.osgi.framework.Bundle;

/**
 * This class allows to parse the headers values of a bundle
 * 
 * @since 1.3
 */
public class PapyrusBundleDescription {

	/**
	 * the string required-bundle
	 */
	private static final String REQUIRE_BUNDLE = "Require-Bundle"; //$NON-NLS-1$

	/**
	 * the string bundle version
	 */
	private static final String BUNDLE_VERSION = "bundle-version=";//$NON-NLS-1$

	/**
	 * the string visibility reexport
	 */
	private static final String VISIBILITY_REEXPORT = "visibility:=reexport";//$NON-NLS-1$

	/**
	 * the string resolution optional
	 */
	private static final String RESOLUTION_OPTIONAL = "resolution:=optional";//$NON-NLS-1$

	/**
	 * the string installation greedy
	 */
	private static final String X_INSTALLATION_GREEDY = "x-installation:=greedy";//$NON-NLS-1$


	/**
	 * starting regex group string
	 */
	private static final String REGEX_START_GROUP = "(";//$NON-NLS-1$

	/**
	 * ending regex group string
	 */
	private static final String REGEX_END_GROUP = ")";//$NON-NLS-1$

	/**
	 * the regex string for optional char/group
	 */
	private static final String REGEX_OPTIONAL = "?";//$NON-NLS-1$

	/**
	 * the separator used between properties of the require bundle field
	 */
	private static final String PROPERTY_SEPARATOR = ";";//$NON-NLS-1$

	/**
	 * the regex group used to get a dependency name
	 */
	private static final String BUNDLE_NAME_REGEX_GROUP_1 = REGEX_START_GROUP + "[a-zA-Z0-9_\\.]*" + REGEX_END_GROUP; //$NON-NLS-1$ // this regex allows to match the name of the plugin

	/**
	 * the regex group used to get the dependency version range
	 */
	private static final String BUNDLE_VERSION_REGEX_GROUP_2 = REGEX_START_GROUP + PROPERTY_SEPARATOR + BUNDLE_VERSION + "\"[\\[\\(\\d\\.\\]\\),]*\"" + REGEX_END_GROUP; //$NON-NLS-1$

	/**
	 * the regex group used to get the optional value of a dependency
	 */
	private static final String BUNDLE_OPTIONAL_REGEX_GROUP_3 = REGEX_START_GROUP + PROPERTY_SEPARATOR + RESOLUTION_OPTIONAL + REGEX_END_GROUP;

	/**
	 * the regrex group used to get the reexport value of a dependency
	 */
	private static final String BUNDLE_REEXPORT_REGEX_GROUP_4 = REGEX_START_GROUP + PROPERTY_SEPARATOR + VISIBILITY_REEXPORT + REGEX_END_GROUP;

	/**
	 * the regex group used to get the greedy value of a dependency
	 */
	private static final String BUNDLE_GREEDY_GROUP_5 = REGEX_START_GROUP + PROPERTY_SEPARATOR + X_INSTALLATION_GREEDY + REGEX_END_GROUP;


	/**
	 * group matching on "," for next bundle, ";" for next property of the current bundle, and "nothing" for the end of the bundle list
	 */
	private static final String DEPENDENCY_SEPARATOR_REGEX_GROUP = REGEX_START_GROUP + "," + REGEX_END_GROUP; //$NON-NLS-1$

	/**
	 * the regex used to parse the field {@link #REQUIRE_BUNDLE} in a manifest
	 */
	private static final Pattern PATTERN = Pattern.compile(
			// the first group is the plugin name
			BUNDLE_NAME_REGEX_GROUP_1
					// the second group is the version, optional
					+ BUNDLE_VERSION_REGEX_GROUP_2 + REGEX_OPTIONAL
					// the third group is the optional information for bundle, optional
					+ BUNDLE_OPTIONAL_REGEX_GROUP_3 + REGEX_OPTIONAL
					// the fourth group is the reexport, optional
					+ BUNDLE_REEXPORT_REGEX_GROUP_4 + REGEX_OPTIONAL
					// the fifth group is the greedy value, optional
					+ BUNDLE_GREEDY_GROUP_5 + REGEX_OPTIONAL
					// the end of the description of a bundle
					+ DEPENDENCY_SEPARATOR_REGEX_GROUP + "?");//$NON-NLS-1$ not here for the last dependency


	/**
	 * the represented bundle
	 */
	private Bundle bundle;

	/**
	 * the list of the dependencies, with there version
	 */
	private Map<PapyrusBundleDescription, Version> dependencies;

	/**
	 * the list of the reexported dependencies
	 */
	private Set<PapyrusBundleDescription> reexportedDependencies;

	/**
	 * the list of the optional dependencies
	 */
	private Set<PapyrusBundleDescription> optionalDependencies;

	/**
	 * the list of the greedy dependencies
	 */
	private Set<PapyrusBundleDescription> greedyDependencies;

	/**
	 * 
	 * Constructor.
	 *
	 * @param bundleName
	 *            the name of the bundle to read
	 */
	public PapyrusBundleDescription(final String bundleName) {
		this(Platform.getBundle(bundleName));

	}

	/**
	 * 
	 * Constructor.
	 *
	 * @param bundle
	 *            the bundle to read
	 */
	private PapyrusBundleDescription(final Bundle bundle) {
		Assert.isNotNull(bundle);
		this.bundle = bundle;
	}

	/**
	 * 
	 * @return
	 * 		the collection of the dependencies for the current bundle
	 */
	public Collection<PapyrusBundleDescription> getDependencies() {
		if (null == this.dependencies) {
			initDependenciesFields();
		}
		return new HashSet<>(this.dependencies.keySet());
	}

	/**
	 * 
	 * @return
	 * 		the collection of the reexported dependencies for the current bundle
	 */
	public Collection<PapyrusBundleDescription> getReexportedDependencies() {
		if (null == this.reexportedDependencies) {
			initDependenciesFields();
		}
		return new HashSet<>(this.reexportedDependencies);
	}

	/**
	 * @return the collection of the optiona dependencies for the current bundle
	 */
	public Collection<PapyrusBundleDescription> getOptionalDependencies() {
		if (null == this.optionalDependencies) {
			initDependenciesFields();
		}
		return new HashSet<>(this.optionalDependencies);
	}

	/**
	 * @return the collection of the greedy dependencies for the current bundle
	 */
	public Collection<PapyrusBundleDescription> getGreedyDependencies() {
		if (null == this.greedyDependencies) {
			initDependenciesFields();
		}
		return new HashSet<>(this.greedyDependencies);
	}



	/**
	 * This method init the fields concerning the dependencies of the represented bundle
	 */
	private void initDependenciesFields() {
		this.dependencies = new HashMap<>();
		this.reexportedDependencies = new HashSet<>();
		this.optionalDependencies = new HashSet<>();
		this.greedyDependencies = new HashSet<>();
		final String value = this.bundle.getHeaders().get(REQUIRE_BUNDLE);
		final Matcher matcher = PATTERN.matcher(value);
		while (matcher.find()) {
			final String dependencyNameGroup1 = matcher.group(1);
			final String versionGroup2 = matcher.group(2);
			final String optionalGroup3 = matcher.group(3);
			final String reexportGroup4 = matcher.group(4);
			final String greedyGroup5 = matcher.group(5);

			if (null != dependencyNameGroup1 && !dependencyNameGroup1.isEmpty()) {
				final PapyrusBundleDescription newContainer = PapyrusBundleDescriptionRegistry.INSTANCE.getPapyrusBundleDescription(dependencyNameGroup1);
				if (null != versionGroup2 && !versionGroup2.isEmpty()) {
					// to be notified if we meet a manifest where the current regex doesn't work
					Assert.isTrue(versionGroup2.contains(BUNDLE_VERSION), NLS.bind("Please, fill a bug, the regex doesn't work for {0}.", this.bundle.getSymbolicName())); //$NON-NLS-1$
					String version = versionGroup2.replaceFirst(PROPERTY_SEPARATOR + BUNDLE_VERSION + "\"", ""); //$NON-NLS-1$ //$NON-NLS-2$
					version = version.substring(0, version.length() - 1);
					dependencies.put(newContainer, new Version(versionGroup2));
				} else {
					dependencies.put(newContainer, new Version(null));
				}

				if (null != optionalGroup3 && !optionalGroup3.isEmpty()) {
					// to be notified if we meet a manifest where the current regex doesn't work
					Assert.isTrue(optionalGroup3.contains(RESOLUTION_OPTIONAL), NLS.bind("Please, fill a bug, the regex doesn't work for {0}.", this.bundle.getSymbolicName())); //$NON-NLS-1$
					this.optionalDependencies.add(newContainer);
				}

				if (null != reexportGroup4 && !reexportGroup4.isEmpty()) {
					// to be notified if we meet a manifest where the current regex doesn't work
					Assert.isTrue(reexportGroup4.contains(VISIBILITY_REEXPORT), NLS.bind("Please, fill a bug, the regex doesn't work for {0}.", this.bundle.getSymbolicName())); //$NON-NLS-1$
					this.reexportedDependencies.add(newContainer);
				}


				if (null != greedyGroup5 && !greedyGroup5.isEmpty()) {
					// to be notified if we meet a manifest where the current regex doesn't work
					Assert.isTrue(greedyGroup5.contains(X_INSTALLATION_GREEDY), NLS.bind("Please, fill a bug, the regex doesn't work for {0}.", this.bundle.getSymbolicName())); //$NON-NLS-1$
					this.greedyDependencies.add(newContainer);
				}
			}
		}
	}

	/**
	 * 
	 * @param description
	 *            a papyrus bundle description which should declared as dependency of the represented plugin
	 * @return
	 * 		the expected version range for this dependency, or <code>null</code> if the {@link PapyrusBundleDescription} is not a dependency of the current bundle
	 */
	public Version getRegisteredDependencyVersion(final PapyrusBundleDescription aDependency) {
		Assert.isNotNull(aDependency);
		if (!this.dependencies.containsKey(aDependency)) {
			return null;
		}
		return this.dependencies.get(aDependency);
	}

	/**
	 * 
	 * @param dependencyName
	 *            the name of an expected dependency
	 * @return
	 * 		the expected version range for this dependency, or <code>null</code> if the {@link PapyrusBundleDescription} is not a dependency of the current bundle
	 */
	public Version getRegisteredDependencyVersion(final String dependencyName) {
		final PapyrusBundleDescription currentDesc = PapyrusBundleDescriptionRegistry.INSTANCE.getPapyrusBundleDescription(dependencyName);
		if (null != currentDesc) {
			return getRegisteredDependencyVersion(currentDesc);
		}
		return null;
	}

	/**
	 * 
	 * @return
	 * 		the name of the represented bundle
	 */
	public final String getBundleSymbolicName() {
		return this.bundle.getSymbolicName();
	}

	/**
	 * 
	 * @param coll
	 *            a collection of {@link PapyrusBundleDescription}
	 * @return
	 * 		a set of string representing the {@link PapyrusBundleDescription} with their symbolic name
	 */
	public static final Set<String> asSymbolicNameSet(final Collection<PapyrusBundleDescription> coll) {
		final Set<String> values = new HashSet<String>();
		for (PapyrusBundleDescription curr : coll) {
			values.add(curr.getBundleSymbolicName());
		}
		return values;
	}

	/**
	 * 
	 * @param dependencyName
	 *            the name of a dependency
	 * @return
	 * 		<code>true</code> if the current bundle has a dependency on wanted bundle identified by its name
	 */
	public boolean hasDependencyOn(final String dependencyName) {
		return hasDependencyOn(PapyrusBundleDescriptionRegistry.INSTANCE.getPapyrusBundleDescription(dependencyName));
	}

	/**
	 * 
	 * @param aPossibleDependency
	 *            a possible dependency
	 * @return
	 * 		<code>true</code> if the current bundle registers the argument as a dependency
	 */
	public boolean hasDependencyOn(final PapyrusBundleDescription aPossibleDependency) {
		return this.dependencies.containsKey(aPossibleDependency);
	}

	/**
	 * @see java.lang.Object#toString()
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return "Represented bundle: " + this.bundle.getSymbolicName(); //$NON-NLS-1$
	}

}
