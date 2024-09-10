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

package org.eclipse.papyrus.infra.tools.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

/**
 * A simple class providing access to the file system relative to a base folder (and
 * optionally a sub-folder within)
 */
public class JavaIoFileSystemAccess implements IPFileSystemAccess {

	File baseFolder;

	String subFolderName;

	/**
	 * @see org.eclipse.papyrus.infra.tools.util.IPFileSystemAccess.generator.IFileSystemAccess#generateFile(java.lang.String, java.lang.String)
	 *
	 * @param fileName
	 *            The filename
	 * @param content
	 *            The content that is written to a file
	 */
	public void generateFile(String fileName, String content) {
		File file = getFile(fileName);
		try {
			if (!file.exists()) {
				// the file does not exists
				file.createNewFile();
			}
			BufferedWriter writer = Files.newBufferedWriter(file.toPath(), Charset.defaultCharset());
			writer.write(content);
		} catch (IOException e) {
			throw new RuntimeException("Code generation: " + e.getMessage()); //$NON-NLS-1$
		}
	}

	/**
	 * @see org.eclipse.papyrus.infra.tools.util.IPFileSystemAccess.generator.IFileSystemAccess#deleteFile(java.lang.String)
	 *
	 * @param fileName
	 */
	public void deleteFile(String fileName) {
		File file = getFile(fileName);
		file.delete();
	}

	/**
	 * Set the output path, all file operations are relative to this path
	 * 
	 * @param baseFolder
	 *            the base folder (instance of File)
	 */
	public void setOutputPath(File baseFolder) {
		setOutputPath(baseFolder, null);
	}

	/**
	 * Set the output path to a give base folder and a sub-folder within. All file operations are relative to this path
	 * 
	 * @param baseFolder
	 *            the base folder (instance of File)
	 * @param folderName
	 *            name of a sub folder within the base folder
	 */
	public void setOutputPath(File baseFolder, String folderName) {
		this.baseFolder = baseFolder;
		this.subFolderName = folderName;
	}

	/**
	 * Return a container (folder) for a given filename.
	 * Folders will be created, if the do not exist (comparable to "mkdir -p" in Unix).
	 *
	 * @param filename
	 *            a filename with '/' (IPFileSystemAccess.SEP_CHAR) as separation character
	 * @return file for this element
	 */
	public File getFile(String filename) {
		String paths[] = filename.split(IPFileSystemAccess.SEP_CHAR); // $NON-NLS-1$
		File folder = getFile(baseFolder, subFolderName);
		try {
			for (int i = 0; i < paths.length - 1; i++) {
				String path = paths[i];
				folder = getFile(folder, path);
				if (!folder.exists()) {
					// if packageContainer is a Project, it necessarily exists
					folder.createNewFile();
				}
			}
			String last = paths[paths.length - 1];
			return getFile(folder, last);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Get a file within a folder
	 * 
	 * @param folder
	 *            A folder (instance of class File)
	 * @param fileName
	 *            A file name
	 * @return the file within the folder (instance of class File)
	 */
	public File getFile(File folder, String fileName) {
		return new File(folder, fileName);
	}
}
