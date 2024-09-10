/*****************************************************************************
 * Copyright (c) 2013 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Juan Cadavid (CEA LIST) juan.cadavid@cea.fr - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.utils;

import java.util.Comparator;

/**
 *
 * String comparator ignoring all non-words character
 *
 */
public class StringComparator implements Comparator<String> {

	/**
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	@Override
	public int compare(String str1, String str2) {
		str1 = str1.replaceAll(AxisUtils.REGEX, "");//$NON-NLS-1$ //we keep only words characters (letters + numbers) + whitespace
		str2 = str2.replaceAll(AxisUtils.REGEX, ""); //$NON-NLS-1$
		return str1.compareToIgnoreCase(str2);
	};
}
