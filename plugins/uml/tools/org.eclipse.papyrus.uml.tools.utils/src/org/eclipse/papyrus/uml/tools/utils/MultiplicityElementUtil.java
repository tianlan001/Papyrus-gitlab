/*****************************************************************************
 * Copyright (c) 2009 CEA LIST.
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
 *  Yann TANGUY (CEA LIST) yann.tanguy@cea.fr - Initial API and implementation
 *  Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Bug 496905
 *
 *****************************************************************************/
package org.eclipse.papyrus.uml.tools.utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import org.eclipse.uml2.uml.LiteralInteger;
import org.eclipse.uml2.uml.LiteralString;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.MultiplicityElement;
import org.eclipse.uml2.uml.ValueSpecification;

/**
 * Utility class for <code>org.eclipse.uml2.uml.MultiplicityElement</code><BR>
 */
public class MultiplicityElementUtil {

	/**
	 * The quote character representation.
	 */
	private static final String QUOTE = "\"";

	private static final Collection<String> DEFAULT_MULTIPLICITY = Collections.unmodifiableCollection(Arrays.asList("[1]", "1"));

	/**
	 * The string representing the multiplicity with space " [x..y]"
	 * Clients should format the spaces at the calling side, but this method left here for backward compatibility
	 * 
	 * @param element
	 *            The multiplicity element to parse.
	 */
	@Deprecated
	public static String getMultiplicityAsString(final MultiplicityElement element) {
		return getMultiplicityAsString(element, false);
	}

	/**
	 * The string representing the multiplicity with space " [x..y]"
	 * Clients should format the spaces at the calling side, but this method left here for backward compatibility (with quote edition for LiteralString).
	 * 
	 * @param element
	 *            The multiplicity element to parse.
	 * @param isEdition
	 *            Boolean to determinate if the quote will be added for the LiteralString
	 */
	@Deprecated
	public static String getMultiplicityAsString(final MultiplicityElement element, final boolean isEdition) {
		String multiplicity = formatMultiplicity(element, isEdition);
		return multiplicity == null || multiplicity.isEmpty() ? "" : " " + multiplicity;
	}

	/**
	 * Return the multiplicity of the element "[x..y]"
	 *
	 * @param element
	 *            The multiplicity element to parse.
	 * @return the string representing the multiplicity
	 */
	public static String formatMultiplicity(final MultiplicityElement element) {
		return formatMultiplicity(element, false);
	}

	/**
	 * Return the multiplicity of the element "[x..y]" (with quote edition for LiteralString).
	 *
	 * @param element
	 *            The multiplicity element to parse.
	 * @param isEdition
	 *            Boolean to determinate if the quote will be added for the LiteralString
	 * @return the string representing the multiplicity
	 */
	public static String formatMultiplicity(final MultiplicityElement element, final boolean isEdition) {
		String multiplicityStr = formatMultiplicityNoBrackets(element, isEdition);
		if (multiplicityStr == null || multiplicityStr.isEmpty()) {
			return "";
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		buffer.append(multiplicityStr);
		buffer.append("]");
		return buffer.toString();
	}

	/**
	 * Returns the String corresponding to the multiplicity without square brackets
	 *
	 * @param element
	 *            The multiplicity element to parse.
	 * @return the string representing the multiplicity, without square brackets
	 */
	public static String formatMultiplicityNoBrackets(final MultiplicityElement element) {
		return formatMultiplicityNoBrackets(element, false);
	}

	/**
	 * Returns the String corresponding to the multiplicity without square brackets (with quote edition for LiteralString).
	 *
	 * @param element
	 *            The multiplicity element to parse.
	 * @param isEdition
	 *            Boolean to determinate if the quote will be added for the LiteralString
	 * @return the string representing the multiplicity, without square brackets
	 */
	public static String formatMultiplicityNoBrackets(final MultiplicityElement element, final boolean isEdition) {
		ValueSpecification lowerSpecification = element.getLowerValue();
		ValueSpecification upperSpecification = element.getUpperValue();
		if (lowerSpecification == null && upperSpecification == null) {
			return setupMultiplicityAsInteger(element.getLower(), element.getUpper());
		}
		if (lowerSpecification == null && upperSpecification instanceof LiteralUnlimitedNatural) {
			return setupMultiplicityAsInteger(element.getLower(), ((LiteralUnlimitedNatural) upperSpecification).unlimitedValue());
		}
		if (lowerSpecification instanceof LiteralInteger && upperSpecification == null) {
			return setupMultiplicityAsInteger(((LiteralInteger) lowerSpecification).integerValue(), element.getUpper());
		}
		if (lowerSpecification instanceof LiteralInteger && upperSpecification instanceof LiteralUnlimitedNatural) {
			return setupMultiplicityAsInteger(((LiteralInteger) lowerSpecification).integerValue(), ((LiteralUnlimitedNatural) upperSpecification).unlimitedValue());
		}
		return setupMultiplicityAsString(element, lowerSpecification, upperSpecification, isEdition);
	}

	private static String setupMultiplicityAsInteger(int lower, int upper) {
		// special case for [1] and [*]
		if (lower == upper) {
			return new Integer(lower).toString();
		} else if ((lower == 0) && (upper == -1)) {
			return "*";
		} else if (upper == -1) {
			return lower + "..*";
		} else {
			return lower + ".." + upper;
		}
	}

	private static String setupMultiplicityAsString(final MultiplicityElement element, final ValueSpecification lower, final ValueSpecification upper, final boolean isEdition) {
		String lowerStr = getStringSpecificationValue(lower, isEdition);
		if ("*".equals(lowerStr)) {
			return "";
		}
		String upperStr = getStringSpecificationValue(upper, isEdition);
		if (lowerStr != null && false == lowerStr.isEmpty() && lowerStr.equalsIgnoreCase(upperStr)) {
			return lowerStr;
		}
		StringBuffer result = new StringBuffer();
		result.append(lowerStr == null || lowerStr.isEmpty() ? element.getLower() : lowerStr);
		result.append("..");
		result.append(upperStr == null || upperStr.isEmpty() ? getUpper(element) : upperStr);
		return result.toString();
	}

	private static String getUpper(MultiplicityElement element) {
		return element.getUpper() == -1 ? "" : new Integer(element.getUpper()).toString();
	}

	/**
	 * Parses the given String and returns the value of the multiplicity.
	 * <P>
	 * a lower bound with value infinite (<code>*</code>) will be set at <code>-1</code>.
	 * </P>
	 *
	 * @param value
	 *            the string representing the multiplicity. it can be <code>1</code>, <code>1..2</code> or <code>1..*</code>
	 * @return a 2-size integer table, with the first element corresponding to the lower bound, the second corresponds to the upper bound
	 */
	public static int[] parseMultiplicity(String value) throws NumberFormatException {
		int lower = 0;
		int upper = 0;
		int firstIndex = value.indexOf("..");

		// ".." was not found => this should be an integer, for example a multiplicity ~ [1]
		if (firstIndex == -1) {
			// this should be directly an integer or a star
			if ("*".equals(value)) {
				lower = 0;
				upper = -1;
			} else {
				lower = Integer.parseInt(value);
				upper = lower;
			}
		} else {
			String lowerValue = value.substring(0, firstIndex);
			String upperValue = value.substring(firstIndex + "..".length());

			lower = Integer.parseInt(lowerValue);
			upper = -2;
			if ("*".equals(upperValue)) {
				upper = -1;
			} else {
				upper = Integer.parseInt(upperValue);
			}
		}
		return new int[] { lower, upper };
	}

	/**
	 * This allow to get the value specification (with the quote management for the edition mode).
	 * 
	 * @param valueSpecification
	 *            The value specification.
	 * @param isEdition
	 *            Boolean to determinate if the quote will be added for the LiteralString
	 * @return The string representing the value specification
	 */
	private static String getStringSpecificationValue(final ValueSpecification valueSpecification, final boolean isEdition) {
		String boundStr = ValueSpecificationUtil.getSpecificationValue(valueSpecification, true);
		if (isEdition && valueSpecification instanceof LiteralString) {
			final StringBuffer buffer = new StringBuffer();
			buffer.append(QUOTE);
			buffer.append(boundStr);
			buffer.append(QUOTE);
			boundStr = buffer.toString();
		}
		return boundStr;
	}

	public static String manageDefaultMultiplicity(String multiplicity, boolean blockDefault) {
		return blockDefault && DEFAULT_MULTIPLICITY.contains(multiplicity) ? "" : multiplicity;
	}

}
