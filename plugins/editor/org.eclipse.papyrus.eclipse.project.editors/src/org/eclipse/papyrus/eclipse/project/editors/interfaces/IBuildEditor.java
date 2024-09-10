/*****************************************************************************
 * Copyright (c) 2011, 2021 CEA LIST.
 *
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
 *  Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr - Bug 574258
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.interfaces;

/**
 *
 * Editor for the build.properties
 *
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IBuildEditor extends IFileEditor {

	/**
	 * key for the bin
	 *
	 * @Deprecated The build key should be specified for each IBuildEditor instance
	 */
	@Deprecated
	public static final String BUILD_KEY = "bin.includes"; //$NON-NLS-1$

	/** key for the source folders */
	public static final String SOURCE_FOLDER_KEY = "source.."; //$NON-NLS-1$

	/** key for the bin folders */
	public static final String BIN_KEY = "bin.."; //$NON-NLS-1$

	/** name of the file build.properties */
	public static final String BUILD_PROPERTIES_FILE = "build.properties"; //$NON-NLS-1$

	/**
	 * The build key for the Eclipse Binary Build
	 */
	public static final String BINARY_BUILD = "bin.includes";

	/**
	 * The build key for the Eclipse Source Build
	 */
	public static final String SOURCE_BUILD = "src.includes";

	/** the method to register a new source folder */
	public void registerSourceFolder(String string);

	/** registers a new bin folder */
	public void registerBinFolder(String binFolder);

	/** the method to add an element to the build */
	public void addToBuild(final String path);

	/** removes the given path from the build */
	public void removeFromBuild(final String path);

	/**
	 * Returns <code>true</code> if the folder is registered as a Source Folder
	 *
	 * @param path
	 *            the path of a source folder
	 * @return
	 *         <code>true</code> if the folder is registered
	 */
	public boolean isRegisteredSourceFolder(final String path);

	/**
	 * Returns <code>true</code> if the folder is registered as a Bin Folder
	 *
	 * @param path
	 *            the path of a bin folder
	 * @return
	 *         <code>true</code> if the folder is registered
	 */
	public boolean isRegisteredBinFolder(final String binFolder);

	/**
	 * Returns all the registered source folders
	 *
	 * @return
	 *         all the registered source folders
	 */
	public String[] getSourceFolders();

	/**
	 * Returns all the files added to the binary build
	 *
	 * @return
	 *         all the files added to binary the build
	 */
	public String[] getElementsInBuild();


	/**
	 * Returns all the files added to the source build
	 *
	 * @return
	 *         all the files added to source the build
	 * @since 3.1
	 */
	public default String[] getSourceIncludes() {// default for API compatibility
		return new String[0];
	}

}
