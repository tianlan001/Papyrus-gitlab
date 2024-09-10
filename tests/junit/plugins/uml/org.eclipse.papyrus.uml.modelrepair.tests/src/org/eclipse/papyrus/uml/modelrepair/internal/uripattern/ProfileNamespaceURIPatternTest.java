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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.uml.modelrepair.internal.stereotypes.StereotypeRepairRegressionTest;
import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Tests for {@link ProfileNamespaceURIPattern}s and the related functionality.
 * The extended profile migration capabilities provided through the profile namespace URI patterns is tested in {@link StereotypeRepairRegressionTest}.
 *
 * @author Martin Fleck <mfleck@eclipsesource.com>
 */
public class ProfileNamespaceURIPatternTest extends AbstractPapyrusTest {

	// no version in URI

	private static final String NO_VERSION_PATTERN = "^http://www\\.eclipse\\.org/my/profile/Language/.*$"; //$NON-NLS-1$

	private static final String NO_VERSION_FORMAT = "{0}"; //$NON-NLS-1$

	private static final String NO_VERSION_A_URI = "http://www.eclipse.org/my/profile/Language/PackageA"; //$NON-NLS-1$

	private static final String NO_VERSION_B_URI = "http://www.eclipse.org/my/profile/Language/PackageB"; //$NON-NLS-1$

	private static final String NO_VERSION_VERSION_DEFAULT = ""; //$NON-NLS-1$

	// single version in URI

	private static final String SINGLE_VERSION_PATTERN = "^http://www\\.eclipse\\.org/my/profile/version/([^/]+)/Language/.*$"; //$NON-NLS-1$

	private static final String SINGLE_VERSION_FORMAT = "-{1}-"; //$NON-NLS-1$

	private static final String SINGLE_VERSION_A7_URI = "http://www.eclipse.org/my/profile/version/7/Language/PackageA"; //$NON-NLS-1$

	private static final String SINGLE_VERSION_A7_VERSIONLESS_URI = "http://www.eclipse.org/my/profile/version//Language/PackageA"; //$NON-NLS-1$

	private static final String SINGLE_VERSION_A7_VERSION_DEFAULT = "7"; //$NON-NLS-1$

	private static final String SINGLE_VERSION_A7_VERSION_FORMAT = "-7-"; //$NON-NLS-1$

	private static final String SINGLE_VERSION_B7_URI = "http://www.eclipse.org/my/profile/version/7/Language/PackageB"; //$NON-NLS-1$

	private static final String SINGLE_VERSION_A8_URI = "http://www.eclipse.org/my/profile/version/8/Language/PackageA"; //$NON-NLS-1$

	private static final String SINGLE_VERSION_NONMATCHING_URI = "http://www.eclipse.org/different/profile/version/7/Language/PackageA"; //$NON-NLS-1$

	// multiple versions in URI

	private static final String MULTI_VERSION_PATTERN = "^http://www\\.eclipse\\.org/my/profile/version/([^/]+)/Language/([^/]+)/.*$"; //$NON-NLS-1$

	private static final String MULTI_VERSION_FORMAT = "{1} - {2}"; //$NON-NLS-1$

	private static final String MULTI_VERSION_A7_701_URI = "http://www.eclipse.org/my/profile/version/7/Language/7.0.1/PackageA"; //$NON-NLS-1$

	private static final String MULTI_VERSION_A7_701_VERSIONLESS_URI = "http://www.eclipse.org/my/profile/version//Language//PackageA"; //$NON-NLS-1$

	private static final String MULTI_VERSION_A7_701_VERSION_DEFAULT = "7,7.0.1"; // comma-separated //$NON-NLS-1$

	private static final String MULTI_VERSION_A7_701_VERSION_FORMAT = "7 - 7.0.1"; //$NON-NLS-1$

	private static final String MULTI_VERSION_B7_701_URI = "http://www.eclipse.org/my/profile/version/7/Language/7.0.1/PackageB"; //$NON-NLS-1$

	private static final String MULTI_VERSION_A7_702_URI = "http://www.eclipse.org/my/profile/version/7/Language/7.0.2/PackageA"; //$NON-NLS-1$

	// non-matching pattern

	private static final String NON_MATCHING_PATTERN = "^http://www\\.eclipse\\.org/no/match/version/([^/]+)/Language/([^/]+)/.*$"; //$NON-NLS-1$

	// pattern registered via extension point

	private static final String EXTENSION_POINT_PATTERN = "^http://www\\.eclipse\\.org/my/profile/test/([^/]+)/Language/.*$"; //$NON-NLS-1$

	private static final String EXTENSION_POINT_URI = "http://www.eclipse.org/my/profile/test/7/Language/PackageA"; //$NON-NLS-1$

	private static final String EXTENSION_POINT_VERSIONLESS_URI = "http://www.eclipse.org/my/profile/test//Language/PackageA"; //$NON-NLS-1$

	private static final String EXTENSION_POINT_VERSION = "#7#"; // custom version format: #{0}# //$NON-NLS-1$


	/**
	 * Predicate that evaluates whether a specific pattern is the one we registered via an extension point.
	 *
	 * @return new predicate
	 */
	protected static Predicate<ProfileNamespaceURIPattern> isExtensionPointPattern() {
		return new Predicate<ProfileNamespaceURIPattern>() {
			@Override
			public boolean apply(ProfileNamespaceURIPattern pattern) {
				return pattern.getRegexPattern() != null && EXTENSION_POINT_PATTERN.equals(pattern.getRegexPattern().pattern()) &&
						pattern.getVersionFormat() != null;
			}
		};
	}

	// no version matching behavior

	/**
	 * Tests whether the pattern match yields the correct results for an URI with no version part
	 * and the default version format. When there is no versioning information, the versionless URI
	 * and the given URI should be the same.
	 */
	@Test
	public void matchNoVersionCorrectURIDefaultVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(NO_VERSION_PATTERN);
		final ProfileNamespaceURIPatternMatchResult match = pattern.match(NO_VERSION_A_URI);

		assertThat("Pattern should have matched.", match.hasMatched());
		assertThat("Matched namespace URI should be equal to given namesapce URI.",
				match.getNamespaceURI(), is(NO_VERSION_A_URI));
		assertThat("Wrong extracted versionless namespace URI.",
				match.getVersionlessNamespaceURI(), is(NO_VERSION_A_URI));
		assertThat("Wrong extracted version.",
				match.getVersion(), is(NO_VERSION_VERSION_DEFAULT));
	}

	/**
	 * Tests whether the pattern match yields the correct results for an URI with no version part
	 * and a given version format. When there is no versioning information and the format is '{0}',
	 * the versionless URI, the version and the given URI should be the same.
	 */
	@Test
	public void matchNoVersionCorrectURIGivenVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(NO_VERSION_PATTERN, NO_VERSION_FORMAT);
		final ProfileNamespaceURIPatternMatchResult match = pattern.match(NO_VERSION_A_URI);

		assertThat("Pattern should have matched.", match.hasMatched());
		assertThat("Matched namespace URI should be equal to given namesapce URI.",
				match.getNamespaceURI(), is(NO_VERSION_A_URI));
		assertThat("Wrong extracted versionless namespace URI.",
				match.getVersionlessNamespaceURI(), is(NO_VERSION_A_URI));
		assertThat("Wrong extracted version.",
				match.getVersion(), is(NO_VERSION_A_URI));
	}

	// single version matching behavior

	/**
	 * Tests whether the pattern match yields the correct results for an URI with a single version part
	 * and the default version format.
	 */
	@Test
	public void matchSingleVersionCorrectURIDefaultVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(SINGLE_VERSION_PATTERN);
		final ProfileNamespaceURIPatternMatchResult match = pattern.match(SINGLE_VERSION_A7_URI);

		assertThat("Pattern should have matched.", match.hasMatched());
		assertThat("Matched namespace URI should be equal to given namesapce URI.",
				match.getNamespaceURI(), is(SINGLE_VERSION_A7_URI));
		assertThat("Wrong extracted versionless namespace URI.",
				match.getVersionlessNamespaceURI(), is(SINGLE_VERSION_A7_VERSIONLESS_URI));
		assertThat("Wrong extracted version.",
				match.getVersion(), is(SINGLE_VERSION_A7_VERSION_DEFAULT));
	}

	/**
	 * Tests whether the pattern match yields the correct results for an URI with a single version part
	 * and a given version format.
	 */
	@Test
	public void matchSingleVersionCorrectURIGivenVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(SINGLE_VERSION_PATTERN, SINGLE_VERSION_FORMAT);
		final ProfileNamespaceURIPatternMatchResult match = pattern.match(SINGLE_VERSION_A7_URI);

		assertThat("Pattern should have matched.", match.hasMatched());
		assertThat("Matched namespace URI should be equal to given namesapce URI.",
				match.getNamespaceURI(), is(SINGLE_VERSION_A7_URI));
		assertThat("Wrong extracted versionless namespace URI.",
				match.getVersionlessNamespaceURI(), is(SINGLE_VERSION_A7_VERSIONLESS_URI));
		assertThat("Wrong extracted version.",
				match.getVersion(), is(SINGLE_VERSION_A7_VERSION_FORMAT));
	}

	// multiple version matching behavior

	/**
	 * Tests whether the pattern match yields the correct results for an URI with a multiple version parts
	 * and the default version format.
	 */
	@Test
	public void matchMultipleVersionCorrectURIDefaultVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(MULTI_VERSION_PATTERN);
		final ProfileNamespaceURIPatternMatchResult match = pattern.match(MULTI_VERSION_A7_701_URI);

		assertThat("Pattern should have matched.", match.hasMatched());
		assertThat("Matched namespace URI should be equal to given namesapce URI.",
				match.getNamespaceURI(), is(MULTI_VERSION_A7_701_URI));
		assertThat("Wrong extracted versionless namespace URI.",
				match.getVersionlessNamespaceURI(), is(MULTI_VERSION_A7_701_VERSIONLESS_URI));
		assertThat("Wrong extracted version.",
				match.getVersion(), is(MULTI_VERSION_A7_701_VERSION_DEFAULT));
	}

	/**
	 * Tests whether the pattern match yields the correct results for an URI with a multiple version parts
	 * and a given version format.
	 */
	@Test
	public void matchMultipleVersionCorrectURIGivenVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(MULTI_VERSION_PATTERN, MULTI_VERSION_FORMAT);
		final ProfileNamespaceURIPatternMatchResult match = pattern.match(MULTI_VERSION_A7_701_URI);

		assertThat("Pattern should have matched.", match.hasMatched());
		assertThat("Matched namespace URI should be equal to given namesapce URI.",
				match.getNamespaceURI(), is(MULTI_VERSION_A7_701_URI));
		assertThat("Wrong extracted versionless namespace URI.",
				match.getVersionlessNamespaceURI(), is(MULTI_VERSION_A7_701_VERSIONLESS_URI));
		assertThat("Wrong extracted version.",
				match.getVersion(), is(MULTI_VERSION_A7_701_VERSION_FORMAT));
	}

	// no match behavior

	/**
	 * Tests whether the pattern match yields the correct results for an URI that does not match the pattern.
	 * The namespace URI, the versionless namespace URI and the version should be null.
	 */
	@Test
	public void matchCorrectNoMatchBehavior() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(NON_MATCHING_PATTERN);
		final ProfileNamespaceURIPatternMatchResult match = pattern.match(MULTI_VERSION_A7_701_URI);

		assertThat("Pattern should not have matched.", !match.hasMatched());
		assertThat("Unmatched URI should yield null namespace URI.",
				match.getNamespaceURI(), nullValue());
		assertThat("Unmatched versionless URI should be null.",
				match.getVersionlessNamespaceURI(), nullValue());
		assertThat("Unmatched version should be null.",
				match.getVersion(), nullValue());
	}

	/**
	 * Tests whether the pattern match yields the correct results for the NO_MATCH constant.
	 * The namespace URI, the versionless namespace URI and the version should be null.
	 */
	@Test
	public void matchCorrectNoMatchConstantBehavior() {
		final ProfileNamespaceURIPatternMatchResult match = ProfileNamespaceURIPatternMatchResult.NO_MATCH;

		assertThat("Pattern should not have matched.", !match.hasMatched());
		assertThat("Unmatched URI should yield null namespace URI.",
				match.getNamespaceURI(), nullValue());
		assertThat("Unmatched versionless URI should be null.",
				match.getVersionlessNamespaceURI(), nullValue());
		assertThat("Unmatched version should be null.",
				match.getVersion(), nullValue());
	}

	// no version comparison behavior

	/**
	 * Tests whether the comparison between two identical URIs with no version part yields the expected,
	 * equal result.
	 */
	@Test
	public void compareNoVersionSameURI() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(NO_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(NO_VERSION_A_URI, NO_VERSION_A_URI);

		assertThat("Comparison should be valid.", comparison.isValid());
		assertThat("Same URI should be equal to itself (namespace URI).", comparison.isEqualNamespaceURI());
		assertThat("Same URI should be equal to itself (versionless URI).", comparison.isEqualVersionlessNamespaceURI());
		assertThat("Same URI should be equal to itself (version).", comparison.isEqualVersion());
	}

	/**
	 * Tests whether the comparison between two different URIs with no version part yields the expected,
	 * result.
	 */
	@Test
	public void compareNoVersionDifferentURI() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(NO_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(NO_VERSION_A_URI, NO_VERSION_B_URI);

		assertThat("Comparison should be valid.", comparison.isValid());
		assertThat("Should not be the same namespace URI.", !comparison.isEqualNamespaceURI());
		assertThat("Should not be the same versionless URI.", !comparison.isEqualVersionlessNamespaceURI());
		assertThat("Should be the same version (no version).", comparison.isEqualVersion());
	}

	// single version comparison behavior

	/**
	 * Tests whether the comparison between two identical URIs with a single version part yields the expected,
	 * equal result.
	 */
	@Test
	public void compareSingleVersionSameURISameVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(SINGLE_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(SINGLE_VERSION_A7_URI, SINGLE_VERSION_A7_URI);

		assertThat("Comparison should be valid.", comparison.isValid());
		assertThat("Same URI should be equal to itself (namespace URI).", comparison.isEqualNamespaceURI());
		assertThat("Same URI should be equal to itself (versionless URI).", comparison.isEqualVersionlessNamespaceURI());
		assertThat("Same URI should be equal to itself (version).", comparison.isEqualVersion());
	}

	/**
	 * Tests whether the comparison between two versionless URI-equal but version-different URIs with a single version
	 * part yields the expected result.
	 */
	@Test
	public void compareSingleVersionSameURIDifferentVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(SINGLE_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(SINGLE_VERSION_A7_URI, SINGLE_VERSION_A8_URI);

		assertThat("Comparison should be valid.", comparison.isValid());
		assertThat("Should be different namespace URI.", !comparison.isEqualNamespaceURI());
		assertThat("Should be the same versionless URI.", comparison.isEqualVersionlessNamespaceURI());
		assertThat("Should be different versions (7 and 8).", !comparison.isEqualVersion());
	}

	/**
	 * Tests whether the comparison between two versionless URI-different but version-equal URIs with a single version
	 * part yields the expected result.
	 */
	@Test
	public void compareSingleVersionDifferentURISameVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(SINGLE_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(SINGLE_VERSION_A7_URI, SINGLE_VERSION_B7_URI);

		assertThat("Comparison should be valid.", comparison.isValid());
		assertThat("Should be different namespace URI.", !comparison.isEqualNamespaceURI());
		assertThat("Should be different versionless URI.", !comparison.isEqualVersionlessNamespaceURI());
		assertThat("Should be the same version (7 and 7).", comparison.isEqualVersion());
	}

	/**
	 * Tests whether the comparison between two versionless URI-different and version-different URIs with a single version
	 * part yields the expected result.
	 */
	@Test
	public void compareSingleVersionDifferentURIDifferentVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(SINGLE_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(SINGLE_VERSION_A8_URI, SINGLE_VERSION_B7_URI);

		assertThat("Comparison should be valid.", comparison.isValid());
		assertThat("Should be different namespace URI.", !comparison.isEqualNamespaceURI());
		assertThat("Should be different versionless URI.", !comparison.isEqualVersionlessNamespaceURI());
		assertThat("Should be different versions (8 and 7).", !comparison.isEqualVersion());
	}

	// multiple version comparison behavior

	/**
	 * Tests whether the comparison between two identical URIs with multiple version parts yields the expected,
	 * equal result.
	 */
	@Test
	public void compareMultiVersionSameURISameVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(MULTI_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(MULTI_VERSION_A7_701_URI, MULTI_VERSION_A7_701_URI);

		assertThat("Comparison should be valid.", comparison.isValid());
		assertThat("Same URI should be equal to itself (namespace URI).", comparison.isEqualNamespaceURI());
		assertThat("Same URI should be equal to itself (versionless URI).", comparison.isEqualVersionlessNamespaceURI());
		assertThat("Same URI should be equal to itself (version).", comparison.isEqualVersion());
	}

	/**
	 * Tests whether the comparison between two versionless URI-equal but version-different URIs with multiple version
	 * parts yields the expected result.
	 */
	@Test
	public void compareMultiVersionSameURIDifferentVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(MULTI_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(MULTI_VERSION_A7_701_URI, MULTI_VERSION_A7_702_URI);

		assertThat("Comparison should be valid.", comparison.isValid());
		assertThat("Should be different namespace URI.", !comparison.isEqualNamespaceURI());
		assertThat("Should be the same versionless URI.", comparison.isEqualVersionlessNamespaceURI());
		assertThat("Should be different versions (7,7.0.1 and 7,7.0.2).", !comparison.isEqualVersion());
	}

	/**
	 * Tests whether the comparison between two versionless URI-different but version-equal URIs with multiple version
	 * parts yields the expected result.
	 */
	@Test
	public void compareMultiVersionDifferentURISameVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(MULTI_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(MULTI_VERSION_A7_701_URI, MULTI_VERSION_B7_701_URI);

		assertThat("Comparison should be valid.", comparison.isValid());
		assertThat("Should be different namespace URI.", !comparison.isEqualNamespaceURI());
		assertThat("Should be different versionless URI.", !comparison.isEqualVersionlessNamespaceURI());
		assertThat("Should be the same version (7,7.0.1 and 7,7.0.1).", comparison.isEqualVersion());
	}

	/**
	 * Tests whether the comparison between two versionless URI-different and version-different URIs with multiple version
	 * parts yields the expected result.
	 */
	@Test
	public void compareMultiVersionDifferentURIDifferentVersion() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(MULTI_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(MULTI_VERSION_A7_702_URI, MULTI_VERSION_B7_701_URI);

		assertThat("Comparison should be valid.", comparison.isValid());
		assertThat("Should be different namespace URI.", !comparison.isEqualNamespaceURI());
		assertThat("Should be the same versionless URI.", !comparison.isEqualVersionlessNamespaceURI());
		assertThat("Should be the same version (7,7.0.2 and 7,7.0.1).", !comparison.isEqualVersion());
	}


	// invalid comparison behavior

	/**
	 * Tests whether the comparison of two non-matching URIs yields the expected, invalid results.
	 */
	@Test
	public void compareCorrectInvalidComparisonBehaviorWrongPattern() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(NON_MATCHING_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(SINGLE_VERSION_A7_URI, SINGLE_VERSION_A7_URI);

		assertThat("Comparison should not be valid.", !comparison.isValid());
		assertThat("Namespace URIs should not be equal due to invalid comparison.", !comparison.isEqualNamespaceURI());
		assertThat("Versionless URIs should not be equal due to invalid comparison.", !comparison.isEqualVersionlessNamespaceURI());
		assertThat("Versions should not be equal due to invalid comparison.", !comparison.isEqualVersion());
	}

	/**
	 * Tests whether the comparison of one non-matching URIs on the left-hand side yields the expected, invalid results.
	 */
	@Test
	public void compareCorrectInvalidComparisonBehaviorWrongLHS() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(SINGLE_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(SINGLE_VERSION_NONMATCHING_URI, SINGLE_VERSION_A7_URI);

		assertThat("Comparison should not be valid.", !comparison.isValid());
		assertThat("Namespace URIs should not be equal due to invalid comparison.", !comparison.isEqualNamespaceURI());
		assertThat("Versionless URIs should not be equal due to invalid comparison.", !comparison.isEqualVersionlessNamespaceURI());
		assertThat("Versions should not be equal due to invalid comparison.", !comparison.isEqualVersion());
	}

	/**
	 * Tests whether the comparison of one non-matching URIs on the right-hand side yields the expected, invalid results.
	 */
	@Test
	public void compareCorrectInvalidComparisonBehaviorWrongRHS() {
		final ProfileNamespaceURIPattern pattern = new ProfileNamespaceURIPattern(SINGLE_VERSION_PATTERN);
		final ProfileNamespaceURIPatternComparison comparison = pattern.compare(SINGLE_VERSION_A7_URI, SINGLE_VERSION_NONMATCHING_URI);

		assertThat("Comparison should not be valid.", !comparison.isValid());
		assertThat("Namespace URIs should not be equal due to invalid comparison.", !comparison.isEqualNamespaceURI());
		assertThat("Versionless URIs should not be equal due to invalid comparison.", !comparison.isEqualVersionlessNamespaceURI());
		assertThat("Versions should not be equal due to invalid comparison.", !comparison.isEqualVersion());
	}

	/**
	 * Tests whether the INVALID comparison constant yields the expected, invalid results.
	 */
	@Test
	public void compareCorrectInvalidComparisonConstantBehavior() {
		final ProfileNamespaceURIPatternComparison comparison = ProfileNamespaceURIPatternComparison.INVALID;

		assertThat("Comparison should not be valid.", !comparison.isValid());
		assertThat("Namespace URIs should not be equal due to invalid comparison.", !comparison.isEqualNamespaceURI());
		assertThat("Versionless URIs should not be equal due to invalid comparison.", !comparison.isEqualVersionlessNamespaceURI());
		assertThat("Versions should not be equal due to invalid comparison.", !comparison.isEqualVersion());
	}

	// registry behavior

	/**
	 * Tests whether the extension point provided through the plugin.xml is correctly loaded into the registry.
	 */
	@Test
	public void registryExtensionPointRegistered() {
		ProfileNamespaceURIPatternRegistry registry = ProfileNamespaceURIPatternRegistry.INSTANCE;
		assertThat("Pattern from extension point should be in registry.",
				Iterables.any(registry.getProfileNamespaceURIPatterns(), isExtensionPointPattern()));
	}

	/**
	 * Tests whether the extension point provided through the plugin.xml behaves as expected, i.e., matches a given string.
	 * This is important to ensure that there are no encoding/decoding issues between the strings when loaded from the extension point.
	 */
	@Test
	public void registryExtensionPointCorrectMatch() {
		ProfileNamespaceURIPatternRegistry registry = ProfileNamespaceURIPatternRegistry.INSTANCE;
		Iterable<ProfileNamespaceURIPattern> registeredPatterns = Iterables.filter(
				registry.getProfileNamespaceURIPatterns(), isExtensionPointPattern());

		// pattern has only been registered once
		ProfileNamespaceURIPattern registeredPattern = Iterables.getOnlyElement(registeredPatterns);
		ProfileNamespaceURIPatternMatchResult match = registeredPattern.match(EXTENSION_POINT_URI);

		assertThat("Pattern should have matched.", match.hasMatched());
		assertThat("Wrong extracted versionless URI.",
				match.getVersionlessNamespaceURI(), is(EXTENSION_POINT_VERSIONLESS_URI));
		assertThat("Wrong extracted version.",
				match.getVersion(), is(EXTENSION_POINT_VERSION));
	}

	/**
	 * Tests whether registering and unregistering patterns through the registry API works as expected.
	 * Unregistering a pattern actually removes the pattern from the registry.
	 * Registering a pattern actually adds the pattern to the registry.
	 * Unregistering a non-existing pattern leaves the registry unchanged.
	 */
	@Test
	public void registryCorrectUnregisterAndRegister() {
		ProfileNamespaceURIPatternRegistry registry = ProfileNamespaceURIPatternRegistry.INSTANCE;
		Iterable<ProfileNamespaceURIPattern> registeredPatterns = Iterables.filter(
				registry.getProfileNamespaceURIPatterns(), isExtensionPointPattern());

		ProfileNamespaceURIPattern registeredPattern = Iterables.getOnlyElement(registeredPatterns);
		int originalSize = registry.getProfileNamespaceURIPatterns().size();

		// unregister existing pattern
		registry.unregister(registeredPattern);
		assertThat("Registry should have one entry less.",
				registry.getProfileNamespaceURIPatterns().size(),
				is(originalSize - 1));

		// register new pattern
		registry.register(registeredPattern);
		assertThat("Registry should have all entries.",
				registry.getProfileNamespaceURIPatterns().size(),
				is(originalSize));

		// unregister non-existing pattern
		registry.unregister(new ProfileNamespaceURIPattern("non-existing"));
		assertThat("Registry should not have lost an entry.",
				registry.getProfileNamespaceURIPatterns().size(),
				is(originalSize));
	}
}
