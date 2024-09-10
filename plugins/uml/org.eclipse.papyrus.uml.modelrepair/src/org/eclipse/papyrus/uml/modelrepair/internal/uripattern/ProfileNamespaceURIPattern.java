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

import java.text.MessageFormat;
import java.util.regex.Pattern;

/**
 * A profile namespace URI pattern consists of a Regex pattern and an optional version format.
 * The {@link Pattern} can split profile namespace URIs into versionless namespace URIs which can be used as identification and and the version part.
 * The version format allows for individual formatting of the extracted version information and needs to conform to the {@link MessageFormat}.
 *
 * @author Martin Fleck <mfleck@eclipsesource.com>
 */
public class ProfileNamespaceURIPattern {

	/** Regex pattern to split the URI into the profile-identifying part and the versioning part. */
	private final Pattern pattern;

	/** Message format to format the version parts extracted from the namespace URI */
	private final MessageFormat versionFormat;

	/**
	 * Creates a new profile namespace URI pattern with the given namespace URI pattern.
	 * The URI pattern is a Regex that can split namespace URIs into the profile-identifying part (without version) and the versioning part.
	 * This constructor uses the default versioning formatting producing a comma-separated version string.
	 *
	 * @param pattern
	 *            pattern used to split the namespace URI
	 */
	public ProfileNamespaceURIPattern(final String pattern) {
		this(pattern, null);
	}

	/**
	 * Creates a new profile namespace URI pattern with the given namespace URI pattern and the provided version format.
	 * The URI pattern is a Regex that can split namespace URIs into the profile-identifying part (without version) and the versioning part.
	 * The version format allows for individual formatting of the extracted version information and needs to conform to the {@link MessageFormat}.
	 * Indices in the format correspond to the indices of the groups matched by the pattern, e.g., {0} refers to group zero holding the entire pattern and {1} refers to the first matched group.
	 *
	 * @param pattern
	 *            Regex pattern
	 * @param versionFormat
	 *            format for the versioning part of the matched namespace URI
	 * @see Pattern
	 * @see MessageFormat
	 */
	public ProfileNamespaceURIPattern(final String pattern, final String versionFormat) {
		this.pattern = Pattern.compile(pattern);
		if (versionFormat != null) {
			this.versionFormat = new MessageFormat(versionFormat);
		} else {
			this.versionFormat = null;
		}
	}

	/**
	 * Returns the Regex pattern that can split namespace URIs into the profile-identifying part and the versioning part.
	 *
	 * @return the Regex pattern
	 */
	public Pattern getRegexPattern() {
		return pattern;
	}

	/**
	 * Returns the format that is used to create the {@link ProfileNamespaceURIPatternMatchResult#getVersion() version} string from the groups
	 * of the {@link #getRegexPattern() namespace URI pattern}. If no such format was given, this method returns null.
	 *
	 * @return the versionFormat or null if no such format was given
	 */
	public MessageFormat getVersionFormat() {
		return versionFormat;
	}


	/**
	 * Compares the two given URIs based on how they {@link ProfileNamespaceURIPatternMatchResult match} this pattern.
	 * Only if both URIs match the pattern, a {@link ProfileNamespaceURIPatternComparison#isValid() valid} comparison result is returned.
	 *
	 * @param lhsUri
	 *            left-hand side URI for the comparison
	 * @param rhsUri
	 *            right-hand side URI for the comparison
	 * @return a non-null comparison result, which may or may not be valid
	 */
	public ProfileNamespaceURIPatternComparison compare(final String lhsUri, final String rhsUri) {
		final ProfileNamespaceURIPatternMatchResult lhsPattern = match(lhsUri);
		if (lhsPattern.hasMatched()) {
			final ProfileNamespaceURIPatternMatchResult rhsPattern = match(rhsUri);
			if (rhsPattern.hasMatched()) {
				// both uris match
				return new ProfileNamespaceURIPatternComparison(lhsPattern, rhsPattern);
			}
		}
		// none or only one uri matches
		return ProfileNamespaceURIPatternComparison.INVALID;
	}

	/**
	 * Matches the given uri with this namespace URI pattern.
	 *
	 * @param uri
	 *            uri to match
	 * @return a non-null match result, which may or may not {@link ProfileNamespaceURIPatternMatchResult#hasMatched() have matched}.
	 */
	public ProfileNamespaceURIPatternMatchResult match(final String uri) {
		if (uri == null) {
			return ProfileNamespaceURIPatternMatchResult.NO_MATCH;
		}
		return new ProfileNamespaceURIPatternMatchResult(pattern.matcher(uri), versionFormat);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pattern == null) ? 0 : pattern.pattern().hashCode());
		result = prime * result + ((versionFormat == null) ? 0 : versionFormat.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ProfileNamespaceURIPattern other = (ProfileNamespaceURIPattern) obj;
		if (pattern == null) {
			if (other.pattern != null) {
				return false;
			}
		} else if (!pattern.pattern().equals(other.pattern.pattern())) {
			return false;
		}
		if (versionFormat == null) {
			if (other.versionFormat != null) {
				return false;
			}
		} else if (!versionFormat.equals(other.versionFormat)) {
			return false;
		}
		return true;
	}
}

