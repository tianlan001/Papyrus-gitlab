/*******************************************************************************
 * Copyright (c) 2016 EclipseSource Services GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Martin Fleck - initial API and implementation
 *******************************************************************************/
package org.eclipse.papyrus.uml.modelrepair.internal.uripattern;

import static org.eclipse.papyrus.uml.modelrepair.Activator.PLUGIN_ID;
import static org.eclipse.papyrus.uml.modelrepair.Activator.log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

/**
 * This registry holds all profile namespace URI patterns that have been provided through the profile namespace
 * URI pattern extension point or via the API of this registry.
 *
 * @author Martin Fleck <mfleck@eclipsesource.com>
 */
public class ProfileNamespaceURIPatternRegistry {

	/** Singleton instance of this registry */
	public static final ProfileNamespaceURIPatternRegistry INSTANCE = new ProfileNamespaceURIPatternRegistry();

	/** ID under which extension points are registered */
	private static final String EXTENSION_ID = PLUGIN_ID + ".profileNamespaceURIPattern"; //$NON-NLS-1$

	/** Name of the profile namespace URI pattern configuration element */
	private static final String NAME = "profileNamespaceURIPattern"; //$NON-NLS-1$

	/** Attribute of the profile namespace URI pattern configuration element holding the URI pattern */
	private static final String URI_PATTERN = "uriPattern"; //$NON-NLS-1$

	/** Attribute of the profile namespace URI pattern configuration element holding the version format */
	private static final String VERSION_FORMAT = "versionFormat"; //$NON-NLS-1$

	/** List of registered profile namespace URI patterns */
	private List<ProfileNamespaceURIPattern> profileNamespaceURIPatterns = new ArrayList<ProfileNamespaceURIPattern>();

	/**
	 * Private constructor to avoid creation in favor of singleton instance.
	 */
	private ProfileNamespaceURIPatternRegistry() {
		initFromExtensionPoints();
	}

	/**
	 * Reads profile namespace URI patterns from the extension points and adds them to the list of patterns.
	 */
	private void initFromExtensionPoints() {
		final IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(EXTENSION_ID);

		// read data from plugins
		for (IConfigurationElement element : config) {
			try {
				if (NAME.equals(element.getName())) {
					ProfileNamespaceURIPattern pattern = processProfileNamespaceURIPattern(element);
					register(pattern);
				}
			} catch (Exception ex) {
				log.error(ex);
			}
		}
	}

	/**
	 * Reads the necessary data from the provided configuration element to create a new profile namespace URI pattern.
	 *
	 * @param element
	 *            configuration element
	 * @return newly created profile namespace URI pattern
	 */
	private ProfileNamespaceURIPattern processProfileNamespaceURIPattern(IConfigurationElement element) {
		final String uriPattern = element.getAttribute(URI_PATTERN);
		final String versionFormat = element.getAttribute(VERSION_FORMAT); // is optional, may return null
		return new ProfileNamespaceURIPattern(uriPattern, versionFormat);
	}

	/**
	 * Returns an unmodifiable list of all registered profile namespace URI patterns.
	 *
	 * @return list of registered profile namespace URI patterns
	 */
	public List<ProfileNamespaceURIPattern> getProfileNamespaceURIPatterns() {
		return Collections.unmodifiableList(profileNamespaceURIPatterns);
	}

	/**
	 * Adds the provided profile namespace URI pattern to this registry.
	 *
	 * @param profileNamespaceURIPattern
	 *            pattern to be registered
	 */
	public void register(ProfileNamespaceURIPattern profileNamespaceURIPattern) {
		profileNamespaceURIPatterns.add(profileNamespaceURIPattern);
	}

	/**
	 * Removes the given profile namespace URI patterns from this registry.
	 * If the pattern can not be found, the registry remains unchanged.
	 *
	 * @param profileNamespaceURIPattern
	 *            pattern to be unregistered, if present
	 */
	public void unregister(ProfileNamespaceURIPattern profileNamespaceURIPattern) {
		profileNamespaceURIPatterns.remove(profileNamespaceURIPattern);
	}

	/**
	 * Returns an {@link Optional} containing the first pattern that matches the given uri,
	 * if such a pattern exists.
	 *
	 * @param namespaceURI
	 *            namespace URI to be matched
	 * @return Optional containing the first matching pattern, if such a pattern exists
	 */
	public Optional<ProfileNamespaceURIPattern> tryFindPattern(String namespaceURI) {
		for (ProfileNamespaceURIPattern pattern : getProfileNamespaceURIPatterns()) {
			ProfileNamespaceURIPatternMatchResult match = pattern.match(namespaceURI);
			if (match.hasMatched()) {
				return Optional.of(pattern);
			}
		}
		return Optional.absent();
	}

	/**
	 * Returns a list of patterns that match the given namespace URI.
	 * If no such pattern exist, an empty list is returned.
	 *
	 * @param namespaceURI
	 *            namespace URI to be matched
	 * @return list of matching patterns
	 */
	public List<ProfileNamespaceURIPattern> findPatterns(String namespaceURI) {
		List<ProfileNamespaceURIPattern> patterns = Lists.newArrayList();
		for (ProfileNamespaceURIPattern pattern : getProfileNamespaceURIPatterns()) {
			ProfileNamespaceURIPatternMatchResult match = pattern.match(namespaceURI);
			if (match.hasMatched()) {
				patterns.add(pattern);
			}
		}
		return patterns;
	}

	/**
	 * Returns an {@link Optional} containing the first pattern match that matches the given namespace URI,
	 * if such a pattern match exists.
	 *
	 * @param namespaceURI
	 *            namespace URI to be matched
	 * @return Optional containing the first matching pattern match, if such a pattern match exists
	 */
	public Optional<ProfileNamespaceURIPatternMatchResult> tryFindMatch(String namespaceURI) {
		for (ProfileNamespaceURIPattern pattern : getProfileNamespaceURIPatterns()) {
			ProfileNamespaceURIPatternMatchResult match = pattern.match(namespaceURI);
			if (match.hasMatched()) {
				return Optional.of(match);
			}
		}
		return Optional.absent();
	}

	/**
	 * Returns a list of pattern matches that match the given namespace URI.
	 * If no such pattern match exist, an empty list is returned.
	 *
	 * @param namespaceURI
	 *            namespace URI to be matched
	 * @return list of matching pattern matches
	 */
	public List<ProfileNamespaceURIPatternMatchResult> findMatches(String namespaceURI) {
		List<ProfileNamespaceURIPatternMatchResult> matches = Lists.newArrayList();
		for (ProfileNamespaceURIPattern pattern : getProfileNamespaceURIPatterns()) {
			ProfileNamespaceURIPatternMatchResult match = pattern.match(namespaceURI);
			if (match.hasMatched()) {
				matches.add(match);
			}
		}
		return matches;
	}

	/**
	 * Returns an {@link Optional} containing the first valid pattern comparison that matches both namespace URIs,
	 * if such a comparison exists.
	 *
	 * @param lhsNamespaceUri
	 *            left-hand side namespace URI of the comparison
	 * @param rhsNamespaceUri
	 *            right-hand side namespace URI of the comparison
	 * @return Optional containing the first valid pattern comparison, if such a comparison exists
	 */
	public Optional<ProfileNamespaceURIPatternComparison> tryFindComparison(String lhsNamespaceUri, String rhsNamespaceUri) {
		for (ProfileNamespaceURIPattern pattern : getProfileNamespaceURIPatterns()) {
			ProfileNamespaceURIPatternComparison comparison = pattern.compare(lhsNamespaceUri, rhsNamespaceUri);
			if (comparison.isValid()) {
				return Optional.of(comparison);
			}
		}
		return Optional.absent();
	}

	/**
	 * Returns a list of valid pattern comparisons that match both namespace URIs.
	 * If no such comparison exist, an empty list is returned.
	 *
	 * @param lhsNamespaceUri
	 *            left-hand side namespace URI of the comparison
	 * @param rhsNamespaceUri
	 *            right-hand side namespace URI of the comparison
	 * @return list of valid comparisons between the given namespace URIs
	 */
	public List<ProfileNamespaceURIPatternComparison> findComparisons(String lhsNamespaceUri, String rhsNamespaceUri) {
		List<ProfileNamespaceURIPatternComparison> comparisons = Lists.newArrayList();
		for (ProfileNamespaceURIPattern pattern : getProfileNamespaceURIPatterns()) {
			ProfileNamespaceURIPatternComparison comparison = pattern.compare(lhsNamespaceUri, rhsNamespaceUri);
			if (comparison.isValid()) {
				comparisons.add(comparison);
			}
		}
		return comparisons;
	}
}
