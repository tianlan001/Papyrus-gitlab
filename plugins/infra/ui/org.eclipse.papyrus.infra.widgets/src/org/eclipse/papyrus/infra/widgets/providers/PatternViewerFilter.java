/*****************************************************************************
 * Copyright (c) 2011 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Mickaël ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 500290: add ignore case boolean and some refactore
 *  Mickaël ADAM (ALL4TEC) - mickael.adam@all4tec.net - Bug 505797 - The validation of search field should allow ^ and $ wildcards
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.papyrus.infra.widgets.internal.StringMatcher;

/**
 * A ViewerFilter which can be used to match a pattern.
 *
 * The pattern accepts wildcards (* and ?), and ; as a pattern-separator
 *
 * For example:
 * foo;bar will match either "foo" or "bar"
 * foo* will match "foobar"
 *
 * @author Camille Letavernier
 *
 */
public class PatternViewerFilter extends AbstractTreeFilter {

	/** The wildcare to indicate that the end of a pattern is strict. */
	private static final String END_STRICT_WILDCARE = "$";//$NON-NLS-1$

	/** The wildcare to indicate that the start of a pattern is strict. */
	private static final String START_STRICT_WILDCARE = "^";//$NON-NLS-1$

	/** The separator for patterns */
	private static final String SEMI_COLON = ";";//$NON-NLS-1$

	/** the wilcard * */
	private static final String ASTERISK = "*";//$NON-NLS-1$

	private StringMatcher[] validPatterns = new StringMatcher[] { new StringMatcher(ASTERISK, true, false) };

	/** The current pattern. */
	private String currentPattern;

	/** True if strict. */
	private boolean strict = false;

	/** To ignore case. */
	private boolean ignoreCase = true;

	/**
	 * If the pattern is not strict, wildcards (*) will be added at the beginning and the end of the pattern
	 * The pattern foo becomes equivalent to *foo*
	 *
	 * @param strict
	 */
	public void setStrict(boolean strict) {
		this.strict = strict;
	}

	/**
	 * Set to true to ignore case.
	 *
	 * @param ignoreCase
	 *            the ignoreCase to set
	 */
	public void setIgnoreCase(boolean value) {
		if (value != ignoreCase) {
			this.ignoreCase = value;
			setPatterns();
		}
	}

	/**
	 * Set the pattern.
	 */
	public void setPattern(String value) {
		if (!value.equals(currentPattern)) {
			currentPattern = value;
			setPatterns();
		}
	}

	/**
	 * Set Patterns.
	 */
	protected void setPatterns() {
		String[] patterns = currentPattern.split(SEMI_COLON);
		this.validPatterns = new StringMatcher[patterns.length];
		int i = 0;
		for (String pattern : patterns) {
			StringBuilder patternBuilder = new StringBuilder();
			if (!strict) {
				if (pattern.startsWith(START_STRICT_WILDCARE)) {
					patternBuilder.append(pattern.subSequence(1, pattern.length()));
				} else {
					patternBuilder.append(ASTERISK);
					patternBuilder.append(pattern.trim());
				}

				if (pattern.endsWith(END_STRICT_WILDCARE)) {
					patternBuilder.setLength(patternBuilder.length() - 1);
				} else {
					patternBuilder.append(ASTERISK);
				}
			} else {
				patternBuilder.append(pattern);
			}
			validPatterns[i++] = new StringMatcher(patternBuilder.toString(), ignoreCase, false);
		}

		clearCache();
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.AbstractTreeFilter#isVisible(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean isVisible(Viewer viewer, Object parentElement, Object element) {
		IBaseLabelProvider labelProvider = ((StructuredViewer) viewer).getLabelProvider();
		if (labelProvider instanceof ILabelProvider) {
			for (StringMatcher pattern : validPatterns) {
				if (pattern.match(((ILabelProvider) labelProvider).getText(element))) {
					return true;
				}
			}
		}
		return false;
	}

}
