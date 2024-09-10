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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class evaluates a {@link Pattern#matches(String, CharSequence) pattern match} with regards to a {@link ProfileNamespaceURIPattern}
 * and provides convenience methods to access the profile-identifying part (without version) and versioning part of the matched URI.
 *
 * @author Martin Fleck <mfleck@eclipsesource.com>
 */
public class ProfileNamespaceURIPatternMatchResult {

	/** Constant representing an unsuccessful match */
	public static final ProfileNamespaceURIPatternMatchResult NO_MATCH = new ProfileNamespaceURIPatternMatchResult(null, null);

	/** Result from the matching process, null if the matching was not successful */
	private MatchResult regexMatchResult;

	/** Format used to format the versioning part of the matched URI */
	private MessageFormat versionFormat;

	/** Cached strings for all group parts in the match */
	private String[] groups;

	/** Cached strings for all non-group parts in the match */
	private String[] nonGroups;

	/** The entire matched namespace URI */
	private String namespaceURI;

	/** The profile-identifying, versionless part of the match, i.e., a concatenation of the non-group parts in the match */
	private String versionlessNamespaceURI;

	/** The formatted versioning string of the match based on the given message format and the matched groups */
	private String version;

	/**
	 * Creates a new match result based on the given matcher.
	 * If the matcher does not match the {@link Matcher#matches() entire string}, then this match result is considered {@link #hasMatched() unsuccessful}
	 * and all methods querying the result will return null.
	 *
	 * @param matcher
	 *            match engine
	 * @see ProfileNamespaceURIPattern
	 * @see ProfileNamespaceURIPatternRegistry
	 */
	public ProfileNamespaceURIPatternMatchResult(final Matcher matcher) {
		this(matcher, null);
	}

	/**
	 * Creates a new match based on the given matcher and the provided format for the versioning part of the match.
	 * If the matcher does not match the {@link Matcher#matches() entire string}, then this match result is considered {@link #hasMatched() unsuccessful}
	 * and all methods querying the result will return null.
	 * The version format allows for individual formatting of the extracted version information and needs to conform to the {@link MessageFormat}.
	 * Indices in the format correspond to the indices of the groups matched by the pattern, e.g., {0} refers to group zero holding the entire pattern and {1} refers to the first matched group.
	 * The version format will only be used if the match is successful and is used to produce the {@link #getVersion() version string} of the matched URI.
	 *
	 * @param matcher
	 *            match engine
	 * @param versionFormat
	 *            format for the versioning part of the matched namespace URI
	 * @see ProfileNamespaceURIPattern
	 * @see ProfileNamespaceURIPatternRegistry
	 */
	public ProfileNamespaceURIPatternMatchResult(final Matcher matcher, final MessageFormat versionFormat) {
		if (matcher != null && matcher.matches()) {
			this.regexMatchResult = matcher.toMatchResult();
			this.versionFormat = versionFormat;
		}
	}

	/**
	 * Returns the format used to produce the {@link #getVersion() version string} from all version parts extracted from the matched URI.
	 * Indices in the format correspond to the indices of the groups matched by the pattern, e.g., {0} refers to group zero holding the entire pattern and {1} refers to the first matched group.
	 *
	 * @return the versionFormat
	 */
	public MessageFormat getVersionFormat() {
		return versionFormat;
	}

	/**
	 * Returns true if a successful match could be produced for the given URI considering the underlying pattern, false otherwise.
	 * If no successful match could be produced, all methods querying the result will return null.
	 *
	 * @return true if the match was successful, false otherwise.
	 */
	public boolean hasMatched() {
		return getRegexMatchResult() != null;
	}

	/**
	 * Returns the entire matched namespace URI. If the pattern did not match, null is returned.
	 *
	 * @return matched namespace URI or null
	 */
	public String getNamespaceURI() {
		if (!hasMatched()) {
			return null;
		}
		if (namespaceURI == null) {
			namespaceURI = getRegexMatchResult().group(0);
		}
		return namespaceURI;
	}

	/**
	 * Returns the versionless namespace URI of the matched URI or null the underlying pattern was not matched.
	 * The versionless namespace URI may be used to identify a profile, independent of its version.
	 *
	 * @return profile-identifying, versionless namespace URI or null if there is no match
	 */
	public String getVersionlessNamespaceURI() {
		if (!hasMatched()) {
			return null;
		}
		if (versionlessNamespaceURI == null) {
			versionlessNamespaceURI = String.join("", getNonGroups());
		}
		return versionlessNamespaceURI;
	}

	/**
	 * Returns the version of the matched namespace URI or null if the underlying pattern was not matched.
	 * The version string is formatted using the {@link #getVersionFormat() version format} provided when constructing this result.
	 * If no format was provided, the version parts get concatenated via comma.
	 *
	 * @return version string of the matched namespace URI or null if there is no match
	 */
	public String getVersion() {
		if (!hasMatched()) {
			return null;
		}
		if (version == null) {
			if (versionFormat != null) {
				version = versionFormat.format(getGroups());
			} else {
				// by default just concatenate version parts via comma without the entire match (group 0)
				version = String.join(",", Arrays.copyOfRange(getGroups(), 1, getGroups().length));
			}
		}
		return version;
	}

	/**
	 * Returns the Regex match result produced from the matcher given in the constructor.
	 * If there was no match, the match result is null.
	 *
	 * @return match result or null if there is no match
	 */
	protected MatchResult getRegexMatchResult() {
		return regexMatchResult;
	}

	/**
	 * Returns all group strings of the underlying match result. If no match result is present, null is returned.
	 * The indices of the groups match the indices of the returned array.
	 * Please note that group zero (at index zero) holds the entire matched pattern.
	 *
	 * @return group strings or null
	 */
	protected String[] getGroups() {
		if (!hasMatched()) {
			return null;
		}
		if (groups == null) {
			final int groupCount = getRegexMatchResult().groupCount();
			// group 0 has complete match and is not included in group count
			groups = new String[groupCount + 1];
			for (int i = 0; i <= groupCount; i++) {
				groups[i] = getRegexMatchResult().group(i);
			}
		}
		return groups;
	}

	/**
	 * Returns all non-group strings of the underlying match result.
	 * If no match result is present, null is returned.
	 *
	 * @return non-group strings or null
	 */
	protected String[] getNonGroups() {
		if (!hasMatched()) {
			return null;
		}
		if (nonGroups == null) {
			final int groupCount = getRegexMatchResult().groupCount();
			final ArrayList<String> nonGroupList = new ArrayList<String>();
			String nonGroup;
			int start = getRegexMatchResult().start();
			for (int i = 1; i <= groupCount; i++) {
				nonGroup = getNamespaceURI().substring(start, getRegexMatchResult().start(i));
				if (!nonGroup.isEmpty()) {
					nonGroupList.add(nonGroup);
				}
				start = getRegexMatchResult().end(i);
			}
			nonGroup = getNamespaceURI().substring(start, getRegexMatchResult().end());
			if (!nonGroup.isEmpty()) {
				nonGroupList.add(nonGroup);
			}
			nonGroups = nonGroupList.toArray(new String[nonGroupList.size()]);
		}
		return nonGroups;
	}

	@Override
	public String toString() {
		if (!hasMatched()) {
			return "No match.";
		}
		return getVersionlessNamespaceURI() + " [" + getVersion() + "]";
	}
}
