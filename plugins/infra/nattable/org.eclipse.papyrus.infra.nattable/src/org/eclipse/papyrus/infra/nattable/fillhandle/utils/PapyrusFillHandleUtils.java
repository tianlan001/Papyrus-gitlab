/*****************************************************************************
 * Copyright (c) 2016, 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (ALL4TEC) nicolas.fauvergue@all4tec.net - Initial API and implementation
 *   Thanh Liem PHAN (ALL4TEC) thanhliem.phan@all4tec.net - Bug 519383
 *****************************************************************************/

package org.eclipse.papyrus.infra.nattable.fillhandle.utils;

/**
 * The papyrus fill handle utils.
 */
public class PapyrusFillHandleUtils {

	/**
	 * The negative char.
	 */
	private static String NEGATIVE_CHAR = "-"; //$NON-NLS-1$

	/**
	 * Get the template string from a string (without beginning and ending number).
	 * 
	 * @param initialString
	 *            The initial string.
	 * @return The modified string (without beginning and ending number).
	 */
	public static String getTemplateString(final String initialString) {
		return getTemplateWithoutEndingNumber(getTemplateWithoutBeginningNumber(initialString));
	}

	/**
	 * Get the template string from a string (without beginning number).
	 * 
	 * @param initialString
	 *            The initial string.
	 * @return The modified string (without beginning number).
	 */
	public static String getTemplateWithoutBeginningNumber(final String initialString) {
		String result = initialString;

		// The number must be a negative number
		try {
			if (2 <= result.length() && NEGATIVE_CHAR.equals(result.substring(0, 1))) {
				Integer.parseInt(result.substring(1, 2));
				result = result.substring(2);
			}
		} catch (Exception e) {
			// Continue
		}

		// Try to remove beginning number
		try {
			while (!result.isEmpty()) {
				final String firstChar = result.substring(0, 1);
				Integer.parseInt(firstChar);
				result = result.substring(1);
			}
		} catch (Exception e) {
			// Continue
		}

		return result;
	}

	/**
	 * Get the template string from a string (without ending number).
	 * 
	 * @param initialString
	 *            The initial string.
	 * @return The modified string (without ending number).
	 */
	public static String getTemplateWithoutEndingNumber(final String initialString) {
		String result = initialString;

		boolean hasNumber = false;

		// Try to remove ending number
		try {
			while (!result.isEmpty()) {
				final String lastChar = result.substring(result.length() - 1);
				Integer.parseInt(lastChar);
				hasNumber = true;
				result = result.substring(0, result.length() - 1);
			}
		} catch (Exception e) {
			// Continue
		}

		// If result is not empty, a number is found, and if this is not a negative number
		if (!result.isEmpty() && hasNumber && NEGATIVE_CHAR.equals(result.substring(result.length() - 1))) {
			result = result.substring(0, result.length() - 1);
		}

		return result;
	}

	/**
	 * Check if a string is starting with the number string.
	 * 
	 * @param initialString
	 *            The initial string to check
	 * @param numberString
	 *            The number string
	 * @return <code>true</code> if the initial string starts with the number string, <code>false</code> otherwise
	 */
	public static boolean isBeginningByNumber(final String initialString, final String numberString) {
		return !numberString.isEmpty() && initialString.startsWith(numberString);
	}

	/**
	 * Check if a string is ending with the number string.
	 * 
	 * @param initialString
	 *            The initial string to check
	 * @param numberString
	 *            The number string
	 * @return <code>true</code> if the initial string ends with the number string, <code>false</code> otherwise
	 */
	public static boolean isEndingByNumber(final String initialString, final String numberString) {
		return !numberString.isEmpty() && initialString.endsWith(numberString);
	}

	/**
	 * Create the string format for zero leading number.
	 * For instance, if the number of digits is equal to 5, the format string becomes %05d
	 * which specifies an integer with a width of 5 printing leading zeroes.
	 * 
	 * @param numDigits
	 *            The number of digits to display
	 * @return The created string format
	 */
	public static String getZeroLeadingFormatString(final int numDigits, final int numValue) {

		// If the number value is a negative integer, increase the number of digits by 1
		// which is used for the minus sign
		int currentNumDigits = numValue >= 0 ? numDigits : numDigits + 1;

		// Meaning of the string format
		// %% --> %
		// 0 --> 0
		// %d --> <value of numDigits>
		// d --> d
		return String.format("%%0%dd", currentNumDigits);
	}
}
