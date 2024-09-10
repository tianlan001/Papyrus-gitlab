/*****************************************************************************
 * Copyright (c) 2015 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.gmfdiag.representation.impl;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.papyrus.infra.gmfdiag.representation.AssistantRule;
import org.eclipse.papyrus.infra.gmfdiag.representation.impl.AssistantRuleImpl;

/**
 * Implementation of derived features and operations for the {@link AssistantRule} model class.
 */
public class AssistantRuleCustomImpl extends AssistantRuleImpl {

	// A pattern that cannot match any string
	private static final Pattern IMPOSSIBLE_PATTERN = Pattern.compile("^(?<=x)$"); //$NON-NLS-1$

	// A pattern that matches any string
	private static final Pattern WILDCARD_PATTERN = Pattern.compile(".*"); //$NON-NLS-1$

	private Pattern elementTypeIDPattern;

	protected AssistantRuleCustomImpl() {
		super();
	}

	@Override
	public boolean matches(IElementType elementType) {
		String actualID = elementType.getId();
		return (actualID != null) && getElementTypeIDPattern().matcher(actualID).matches();
	}

	Pattern getElementTypeIDPattern() {
		if (elementTypeIDPattern == null) {
			try {
				String elementTypeID = getElementTypeID();
				elementTypeIDPattern = (elementTypeID == null) ? WILDCARD_PATTERN : Pattern.compile(asRegex(elementTypeID));
			} catch (PatternSyntaxException e) {
				elementTypeIDPattern = IMPOSSIBLE_PATTERN;
			}
		}

		return elementTypeIDPattern;
	}

	@Override
	public void setElementTypeID(String newElementTypeID) {
		// Forget the regex, to recompute it when next needed
		elementTypeIDPattern = null;
		super.setElementTypeID(newElementTypeID);
	}

	static String asRegex(String elementTypeIDPattern) {
		StringBuilder result = new StringBuilder(elementTypeIDPattern);

		for (int i = 0; i < result.length(); i++) {
			char ch = result.charAt(i);
			switch (ch) {
			case '.':
			case '?':
			case '[':
			case ']':
			case '(':
			case ')':
			case '{':
			case '}':
			case '^':
			case '$':
				if (!escaped(result, i)) {
					result.insert(i, '\\');
					i++;
				}
				break;
			case '*':
				if (!escaped(result, i)) {
					result.insert(i, '.');
					i++;
				}
				break;
			}
		}

		return result.toString();
	}

	/**
	 * Is the character at an {@code offset} of a sequence escaped?
	 */
	private static boolean escaped(CharSequence chars, int offset) {
		boolean result = (offset > 0) && (chars.charAt(offset - 1) == '\\');

		if (result) {
			// make sure the escape character isn't, itself, escaped
			for (int i = offset - 2; (i >= 0) && (chars.charAt(i) == '\\'); i--) {
				result = !result;
			}
		}

		return result;
	}
}
