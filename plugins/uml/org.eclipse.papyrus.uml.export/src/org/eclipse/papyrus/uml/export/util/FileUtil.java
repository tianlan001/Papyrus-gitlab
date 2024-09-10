/*****************************************************************************
 * Copyright (c) 2017 CEA LIST.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Benoit Maggi (CEA LIST) - Initial API and implementation
 *****************************************************************************/
package org.eclipse.papyrus.uml.export.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.papyrus.uml.export.Activator;


/**
 * The Class FileUtil.
 */
public class FileUtil {

	/**
	 * Copy file from platform.
	 *
	 * @param targetContainer the target container
	 * @param fileName the file name
	 * @param platformPath the platform path
	 */
	public static void copyFileFromPlatform(IPath targetContainer, String fileName, String platformPath) {
		try {
			URL url = new URL(platformPath);
			URL fileURL = FileLocator.toFileURL(url);
			InputStream is = fileURL.openStream();
			String string = targetContainer.append(fileName).toString();
			File file = new File(string);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			OutputStream os = new FileOutputStream(file);

			byte[] b = new byte[2048];
			int length;

			while ((length = is.read(b)) != -1) {
				os.write(b, 0, length);
			}

			is.close();
			os.close();
			
		} catch (IOException e) {
			Activator.log(e);
		} 
	}

	/**
	 * Gets the name from platform URL.
	 *
	 * @param platformURL the platform URL
	 * @return the name from platform URL
	 */
	public static String getNameFromPlatformURL(String platformURL) {
		return new File(platformURL).getName();
	}
	
}
