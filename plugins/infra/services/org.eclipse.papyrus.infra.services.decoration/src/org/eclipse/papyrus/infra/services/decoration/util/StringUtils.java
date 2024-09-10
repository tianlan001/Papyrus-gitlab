/*****************************************************************************
 * Copyright (c) 2022 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   CEA LIST - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.services.decoration.util;

/**
 * This class provides useful methods for String
 *
 * @since 3.1
 */
class StringUtils {

	/**
	 * replace WordUtils.wrap of Apache
	 * code found on https://stackoverflow.com/questions/4212675/wrap-the-string-after-a-number-of-characters-word-wise-in-java
	 *
	 * @param s
	 *            the message
	 * @param length
	 *            number of carters per line
	 * @param separator
	 *            the line separator
	 * @return
	 *         the wrapped string
	 */
	static String stringWrap(String s, int length, String separator) {
		String message = ""; //$NON-NLS-1$
		int position = 0;
		for (String token : s.split(" ", -1)) { //$NON-NLS-1$
			if (message.length() - position + token.length() > length) {
				message = message + separator + token;
				position = message.length() + 1;
			} else {
				message += (message.isEmpty() ? "" : " ") + token; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return message;
	}
}
