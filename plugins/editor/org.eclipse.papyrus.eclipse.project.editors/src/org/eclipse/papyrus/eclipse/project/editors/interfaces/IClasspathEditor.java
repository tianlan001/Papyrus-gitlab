/*****************************************************************************
 * Copyright (c) 2011, 2016 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr
 *  Christian W. Damus - bug 485220
 *
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.interfaces;


/**
 *
 * This interface for the file .classpath
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IClasspathEditor extends IFileEditor {

	/** the classpath file */
	public static final String CLASSPATH_FILE = ".classpath"; //$NON-NLS-1$

	/**
	 * Add a source folder to the class path.
	 *
	 * @param folderPath
	 *            a folder path relative to the project path
	 */
	public void addSourceFolderToClasspath(final String folderPath);

	/**
	 * Add a source folder with optional output folder to the class path.
	 *
	 * @param srcPath
	 *            the source path relative to the project path (may not be {@code null})
	 * @param binPath
	 *            the output path relative to the project path (may be {@code null})
	 * 
	 * @since 2.0
	 */
	public void addSourceFolderToClasspath(final String srcPath, final String binPath);

	/**
	 * Tests if a folder is already registered in the classpath.
	 *
	 * @param folderPath
	 *            a folder path relative to the project path
	 * @return
	 * 		<code>true</code> if the folderPath is already registered
	 */
	public boolean isSourceFolderRegistered(final String folderPath);

	/**
	 *
	 * @return the source folders for this classpath
	 */
	public String[] getSourceFolders();

	/**
	 *
	 * @return the bin folders for this classpath
	 */
	public String[] getBinFolders();
}
