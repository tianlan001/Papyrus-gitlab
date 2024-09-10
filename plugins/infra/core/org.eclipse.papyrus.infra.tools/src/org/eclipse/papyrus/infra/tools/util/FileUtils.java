/*****************************************************************************
 * Copyright (c) 2014 CEA LIST and others.
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
 *   
 *****************************************************************************/

package org.eclipse.papyrus.infra.tools.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.eclipse.core.runtime.Assert;
import org.eclipse.papyrus.infra.tools.Activator;

/**
 * @author Vincent Lorenzo
 *
 */
public class FileUtils {

	public static final String PLATFORM_STRING = "platform"; //$NON-NLS-1$

	public static final String PLUGIN_STRING = "plugin"; //$NON-NLS-1$

	public static final String SLASH_STRING = "/"; //$NON-NLS-1$

	public static final String COLON_STRING = ":"; //$NON-NLS-1$

	public static final String DOT_STRING = ".";//$NON-NLS-1$

	public static final String TEXT_EXTENSION = "txt";//$NON-NLS-1$

	public static final String CSV_EXTENSIOn = "csv";//$NON-NLS-1$

	public static final String UNDERSCORE = "_";//$NON-NLS-1$

	public static final String LINE_SEPARATOR = "line.separator";//$NON-NLS-1$

	private FileUtils() {
		// to prevent instanciation
	}
	
	/**
	 * return the system property line seperator
	 */
	public static final String getSystemPropertyLineSeparator(){
		return System.getProperty(LINE_SEPARATOR);
	}

	/**
	 * this method read a file and return a string, the line separator used will we System.getProperty("line.separator")
	 * 
	 * @param pluginName
	 *            the name of the plugin owning the file
	 * @param filePath
	 *            the path of the file
	 * @param fileNameWithExtension
	 *            the name fo the file with its extension
	 * @return
	 */
	public static final String getStringFromPlatformFile(final String pluginName, final String filePath, final String fileNameWithExtension) {
		return getStringFromPlatformFile(pluginName, filePath, fileNameWithExtension, System.getProperty("line.separator")); //$NON-NLS-1$
	}

	/**
	 * 
	 * @param pluginName
	 *            the name of the plugin owning the file
	 * @param filePath
	 *            the path of the file
	 * @param fileNameWithExtension
	 *            the name fo the file with its extension
	 * @param lineSeparator
	 *            the line separator to use
	 * @return
	 */
	public static final String getStringFromPlatformFile(final String pluginName, final String filePath, final String fileNameWithExtension, final String lineSeparator) {
		Assert.isNotNull(pluginName);
		Assert.isNotNull(filePath);
		Assert.isNotNull(fileNameWithExtension);
		StringBuilder pathBuilder = new StringBuilder();
		pathBuilder.append(PLATFORM_STRING);
		pathBuilder.append(COLON_STRING);
		pathBuilder.append(SLASH_STRING);
		pathBuilder.append(PLUGIN_STRING);
		pathBuilder.append(SLASH_STRING);
		pathBuilder.append(pluginName);
		if (!filePath.startsWith(SLASH_STRING)) {
			pathBuilder.append(SLASH_STRING);
		}
		pathBuilder.append(filePath);
		if (!filePath.endsWith(SLASH_STRING)) {
			pathBuilder.append(SLASH_STRING);
		}
		pathBuilder.append(fileNameWithExtension);
		StringBuilder builder = new StringBuilder();
		URL url;
		try {
			url = new URL(pathBuilder.toString());
			InputStream inputStream = url.openConnection().getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String inputLine = in.readLine();

			while (inputLine != null) {
				builder.append(inputLine);
				inputLine = in.readLine();
				if (inputLine != null) {
					builder.append(lineSeparator); // $NON-NLS-1$
				}
			}

			in.close();

		} catch (IOException e) {
			Activator.log.error(e);
		}
		return builder.toString();
	}
}
