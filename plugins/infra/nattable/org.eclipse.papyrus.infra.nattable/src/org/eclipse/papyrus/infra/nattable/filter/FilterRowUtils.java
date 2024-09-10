/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.filter;


//package org.eclipse.nebula.widgets.nattable.extension.glazedlists.filterrow;

import static org.eclipse.nebula.widgets.nattable.util.ObjectUtils.isNotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.eclipse.nebula.widgets.nattable.filterrow.ParseResult;
import org.eclipse.nebula.widgets.nattable.filterrow.ParseResult.MatchType;
import org.eclipse.nebula.widgets.nattable.filterrow.TextMatchingMode;

import ca.odell.glazedlists.matchers.ThresholdMatcherEditor;

//import ca.odell.glazedlists.matchers.ThresholdMatcherEditor;

/**
 * 
 * Duplicated code from Ca.odell.glazedlist
 */
public class FilterRowUtils {

	public static List<ParseResult> parse(String string, String textDelimiter, TextMatchingMode textMatchingMode) {
		List<ParseResult> parseResults = new ArrayList<ParseResult>();

		if (textDelimiter != null) {
			StringTokenizer tok = new StringTokenizer(string, textDelimiter);
			while (tok.hasMoreTokens()) {
				parse(tok.nextToken(), textMatchingMode, parseResults);
			}
		} else {
			parse(string, textMatchingMode, parseResults);
		}

		return parseResults;
	}

	private static void parse(String string, TextMatchingMode textMatchingMode, List<ParseResult> parseResults) {
		ParseResult parseResult;

		switch (textMatchingMode) {
		case REGULAR_EXPRESSION:
			parseResult = parseExpression(string);
			break;
		default:
			parseResult = parseLiteral(string);
		}

		if (parseResult != null) {
			parseResults.add(parseResult);
		}
	}

	/**
	 * Parses the text entered in the filter row. The text is parsed to figure out the
	 * type of match operation (&lt;, &gt; etc.) and the value next to it.
	 *
	 * @param string
	 *            entered by the user in the filter row text box
	 */
	public static ParseResult parseExpression(String string) {
		Scanner scanner = new Scanner(string.trim());
		ParseResult parseResult = new ParseResult();

		Pattern p = Pattern.compile("<>|([>|<]?=?)"); //$NON-NLS-1$
		String opToken = scanner.findWithinHorizon(p, 2);
		if (isNotEmpty(opToken)) {
			parseResult.setMatchType(MatchType.parse(opToken));
			while (scanner.hasNext()) {
				parseResult.setValueToMatch(scanner.next());
			}
		}
		else {
			parseResult.setValueToMatch(string);
		}
		scanner.close();
		return parseResult;
	}

	public static ParseResult parseLiteral(String string) {
		ParseResult parseResult = new ParseResult();
		parseResult.setMatchType(MatchType.NONE);
		parseResult.setValueToMatch(string);
		return parseResult;
	}

	/**
	 * Set the Match operation on the {@link ThresholdMatcherEditor} corresponding to the {@link MatchType}. This must be done this way since ThresholdMatcherEditor.MatcherEditor is private.
	 *
	 * @param <T>
	 *            type of the row object
	 */
	public static <T> void setMatchOperation(PapyrusThresholdMatcherEditor<T, Object> thresholdMatcherEditor, MatchType matchType) {
		switch (matchType) {
		case GREATER_THAN:
			thresholdMatcherEditor.setMatchOperation(PapyrusThresholdMatcherEditor.GREATER_THAN);
			break;
		case GREATER_THAN_OR_EQUAL:
			thresholdMatcherEditor.setMatchOperation(PapyrusThresholdMatcherEditor.GREATER_THAN_OR_EQUAL);
			break;
		case LESS_THAN:
			thresholdMatcherEditor.setMatchOperation(PapyrusThresholdMatcherEditor.LESS_THAN);
			break;
		case LESS_THAN_OR_EQUAL:
			thresholdMatcherEditor.setMatchOperation(PapyrusThresholdMatcherEditor.LESS_THAN_OR_EQUAL);
			break;
		case NOT_EQUAL:
			thresholdMatcherEditor.setMatchOperation(PapyrusThresholdMatcherEditor.NOT_EQUAL);
			break;
		default:
			thresholdMatcherEditor.setMatchOperation(PapyrusThresholdMatcherEditor.EQUAL);
		}
	}

}
