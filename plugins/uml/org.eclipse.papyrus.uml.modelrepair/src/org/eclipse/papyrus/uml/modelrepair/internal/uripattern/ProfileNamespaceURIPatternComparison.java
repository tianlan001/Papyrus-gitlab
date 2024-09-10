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

/**
 * This convenience class providing comparison methods for two {@link ProfileNamespaceURIPatternMatchResult}es.
 *
 * @author Martin Fleck <mfleck@eclipsesource.com>
 */
public class ProfileNamespaceURIPatternComparison {

	/** Constant representing an invalid comparison */
	public static final ProfileNamespaceURIPatternComparison INVALID = new ProfileNamespaceURIPatternComparison(
			ProfileNamespaceURIPatternMatchResult.NO_MATCH, ProfileNamespaceURIPatternMatchResult.NO_MATCH);

	/** Left-hand side pattern match of the comparison */
	private final ProfileNamespaceURIPatternMatchResult lhsPatternMatch;

	/** Right-hand side pattern match of the comparison */
	private final ProfileNamespaceURIPatternMatchResult rhsPatternMatch;

	/**
	 * Creates a new comparison based on the given two matches.
	 * The resulting comparison is only valid if both matches have actually matched.
	 * All comparisons on an invalid comparison yield false as return value.
	 *
	 * @param lhsPatternMatch
	 *            first match
	 * @param rhsPatternMatch
	 *            second match
	 * @see ProfileNamespaceURIPattern
	 * @see ProfileNamespaceURIPatternRegistry
	 */
	public ProfileNamespaceURIPatternComparison(final ProfileNamespaceURIPatternMatchResult lhsPatternMatch,
			final ProfileNamespaceURIPatternMatchResult rhsPatternMatch) {
		this.lhsPatternMatch = lhsPatternMatch;
		this.rhsPatternMatch = rhsPatternMatch;
	}

	/**
	 * Left-hand side pattern match given when constructing this comparison.
	 *
	 * @return left-hand side pattern match
	 */
	public ProfileNamespaceURIPatternMatchResult getLHSPatternMatch() {
		return lhsPatternMatch;
	}

	/**
	 * Right-hand side pattern match given when constructing this comparison.
	 *
	 * @return second pattern match
	 */
	public ProfileNamespaceURIPatternMatchResult getRHSPatternMatch() {
		return rhsPatternMatch;
	}

	/**
	 * This comparison is valid if both pattern matches have matched.
	 *
	 * @return true if both pattern matches are valid, false otherwise
	 */
	public boolean isValid() {
		return getLHSPatternMatch() != null && getRHSPatternMatch() != null
				&& getLHSPatternMatch().hasMatched() && getRHSPatternMatch().hasMatched();
	}

	/**
	 * Returns true if both pattern matches have the same {@link ProfileNamespaceURIPatternMatchResult#getVersion() version string}.
	 * If this comparison is invalid, then this method returns false.
	 *
	 * @return true if both pattern matches refer to the same version, false otherwise or if this comparison is invalid
	 */
	public boolean isEqualVersion() {
		return isValid()
				&& getLHSPatternMatch().getVersion().equals(getRHSPatternMatch().getVersion());
	}

	/**
	 * Returns true if both pattern matches have the same {@link ProfileNamespaceURIPatternMatchResult#getVersionlessNamespaceURI() versionless namespace URI}.
	 * If this comparison is invalid, then this method returns false.
	 *
	 * @return true if both pattern matches refer to the same versionless namespace URI, false otherwise or if this comparison is invalid
	 */
	public boolean isEqualVersionlessNamespaceURI() {
		return isValid()
				&& getLHSPatternMatch().getVersionlessNamespaceURI().equals(getRHSPatternMatch().getVersionlessNamespaceURI());
	}

	/**
	 * Returns true if both pattern matches have the same {@link ProfileNamespaceURIPatternMatchResult#getNamespaceURI() namespace URI}.
	 * If this comparison is invalid, then this method returns false.
	 *
	 * @return true if both pattern matches refer to the same namespace URI, false otherwise or if this comparison is invalid
	 */
	public boolean isEqualNamespaceURI() {
		return isValid()
				&& getLHSPatternMatch().getNamespaceURI().equals(getRHSPatternMatch().getNamespaceURI());
	}

	@Override
	public String toString() {
		if (!isValid()) {
			return "Invalid comparison.";
		}
		return getLHSPatternMatch() + " versus " + getRHSPatternMatch();
	}
}
