/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
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

package org.eclipse.papyrus.infra.emf.readonly.internal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Code directly reused from org.eclipse.pde.internal.ui.util.LocaleUtil to avoid dependency on ui and internal code
 */
public class LocaleUtil {

	/**
	 * Smartly trims a {@link String} representing a <code>Bundle-Localization</code> key removing trailing locale and/or .properties extension<br>
	 * <em>e.g.</em> <code>"bundle_es.properties  "</code> --> <code>"bundle"</code>
	 *
	 * @param localization
	 *            the {@link String} to trim
	 * @return the trimmed {@link String}
	 */
	public static String trimLocalization(String localization) {
		String sTrim = localization.trim();
		if (sTrim.endsWith(".properties")) {
			sTrim = sTrim.replaceAll(".properties", ""); //$NON-NLS-1$ //$NON-NLS-2$
		}
		Pattern p = Pattern.compile(".*(_[a-z]{2}(_[A-Z]{2})?)$"); //$NON-NLS-1$
		Matcher m = p.matcher(sTrim);
		if (m.matches()) {
			sTrim = sTrim.substring(0, m.start(1));
		}
		return sTrim;
	}

}