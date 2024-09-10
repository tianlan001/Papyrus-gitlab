/*****************************************************************************
 * Copyright (c) 2018 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.infra.onefile.model.impl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.eclipse.core.resources.IContainer;
import org.eclipse.papyrus.infra.internationalization.utils.PropertiesFilesUtils;
import org.eclipse.papyrus.infra.onefile.model.IDiViewFilter;

/**
 * The papyrus di view filter.
 *
 * @since 2.2
 */
public class PapyrusDiViewFilter implements IDiViewFilter {

	/**
	 * The point static char.
	 */
	private static final char POINT = '.';

	/**
	 * {@inheritDoc}
	 *
	 * @see org.eclipse.papyrus.infra.onefile.model.IDiViewFilter#getFileNameForDi(java.lang.String, org.eclipse.core.resources.IContainer)
	 */
	public String getFileNameForDi(final String fileName, final IContainer parent) {
		String result = fileName;
		if (fileName.indexOf(POINT) > 0) {
			// Manage the properties files which contains languages
			final String extension = fileName.substring(fileName.lastIndexOf(POINT) + 1);
			String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf(POINT));

			// For the properties file with underscore in name, we need to check if a locale is available in the name
			// If this is true, the file name is the name without the locale name
			// Example: projectName_en_US.properties must return projectName
			if (extension.equals(PropertiesFilesUtils.PROPERTIES_FILE_EXTENSION) && fileNameWithoutExtension.contains("_")) { //$NON-NLS-1$
				boolean localeFound = false;
				// Get the available locales
				final List<Locale> availableLocales = Arrays.asList(Locale.getAvailableLocales());
				String substring = fileNameWithoutExtension;

				// Loop until no underscore in the name or if a locale is found
				while (substring.contains("_") && !localeFound) { //$NON-NLS-1$
					// Remove the first part of the name to check the possible locale
					substring = substring.substring(substring.indexOf("_") + 1); //$NON-NLS-1$

					final Iterator<Locale> localesIterator = availableLocales.iterator();

					// Loop on available locales
					while (localesIterator.hasNext() && !localeFound) {
						final Locale currentAvailableLocale = localesIterator.next();

						// The available locale is corresponding to the substring of the file name
						if (currentAvailableLocale.toString().equals(substring)) {
							localeFound = true;
							// Get the initial name without the locale as string
							fileNameWithoutExtension = fileNameWithoutExtension.substring(0, fileNameWithoutExtension.length() - substring.length() - 1);
						}
					}
				}
			}

			result = fileNameWithoutExtension;
		}
		return result;
	}

}
