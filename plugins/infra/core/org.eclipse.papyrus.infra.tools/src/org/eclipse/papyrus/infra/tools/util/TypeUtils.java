/*****************************************************************************
 * Copyright (c) 2014, 2015 CEA LIST, Christian W. Damus, and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *   Christian W. Damus - bug 433206
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.tools.util;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Vincent Lorenzo
 *
 */
public class TypeUtils {

	/**
	 * Constructor.
	 *
	 */
	private TypeUtils() {
		// to prevent instanciation
	}

	/**
	 * 
	 * @param str
	 *            a string representing a boolean
	 * @return
	 *         <code>true</code> if the string represents a valid boolean
	 */
	public static final boolean isBooleanValue(String str) {
		return "true".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * 
	 * @param str
	 *            a string representing a boolean
	 * @return
	 *         <code>true</code> if the string represents a valid boolean
	 */
	public static final boolean isIntegerValue(String str) {
		/** the pattern that checks visual ids are valid integers */
		Pattern digit = Pattern.compile("-?\\d+"); //$NON-NLS-1$
		boolean result = false;
		Matcher matcher = digit.matcher(str);
		if (matcher != null) {
			result = matcher.matches();
		}
		return result;
	}

	/**
	 * 
	 * @param str
	 *            a string
	 * @return
	 *         <code>true</code> if the string represents a double
	 */
	public static final boolean isDoubleValue(String str) {
		try {
			new BigDecimal(str);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param str
	 *            a string
	 * @return
	 *         <code>true</code> if the string represents a double
	 */
	public static final boolean isNaturalValue(String str) {
		boolean res = isIntegerValue(str);
		if (res) {
			int tmp = Integer.parseInt(str);
			return tmp >= 0;
		}
		return res;
	}

	/**
	 * 
	 * @param object
	 *            an object
	 * @return
	 *         <code>true</code> if the object represents a numeric value
	 */
	public static final boolean isNumericValue(Object object) {
		if (object instanceof String) {
			try {
				new BigDecimal((String) object);
			} catch (Exception e) {
				return false;
			}
			return true;
		}
		if (object instanceof Integer || object instanceof Double || object instanceof Float) {
			return true;
		}
		return false;

	}

	/**
	 * Attempts to cast an {@code object} as the required {@code type}.
	 * 
	 * @param object
	 *            an object to cast
	 * @param type
	 *            the type to cast it to
	 * 
	 * @return the {@code object} or {@code null} if it is not of the required {@code type}
	 */
	public static <T> T as(Object object, Class<T> type) {
		T result = null;

		if (type.isInstance(object)) {
			result = type.cast(object);
		}

		return result;
	}

	/**
	 * Attempts to cast an {@code object} as an instance of the type implied by the given {@code default_}.
	 * 
	 * @param object
	 *            an object to cast
	 * @param default_
	 *            the default value to return if it is not of the required type. May not be {@code null}
	 * 
	 * @return the {@code object} or {@code default_} if it is not of the required type
	 * 
	 * @throws NullPointerException
	 *             if {@code default_} is {@code null}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T as(Object object, T default_) {
		T result = default_;

		if (default_.getClass().isInstance(object)) {
			result = (T) object;
		}

		return result;
	}

	/**
	 * Attempts to cast the object at an {@code index} of an {@code array} as the required {@code type}.
	 * 
	 * @param array
	 *            an array of objects
	 * @param index
	 *            the position of an object in the {@code array}
	 * @param type
	 *            the type to cast it to
	 * 
	 * @return the {@code index}-th object in the {@code array} or {@code null} if it is not of the required {@code type} or the {@code array} has no such {@code index}
	 */
	public static <T> T as(Object[] array, int index, Class<T> type) {
		Object object = ((index >= 0) && (index < array.length)) ? array[index] : null;
		T result = null;

		if (type.isInstance(object)) {
			result = type.cast(object);
		}

		return result;
	}
}
