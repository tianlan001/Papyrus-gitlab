/*****************************************************************************
 * Copyright (c) 2011, 2016, 2017 CEA LIST, Christian W. Damus, and others.
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
 *  Christian W. Damus - bug 485220
 *  Vincent Lorenzo (CEA LIST) - bug 525876
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.interfaces;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;

/**
 *
 * Editor for the java project
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IJavaProjectEditor extends IProjectEditor, IBuildEditor, IClasspathEditor {

	/** The java nature */
	public static final String JAVA_NATURE = "org.eclipse.jdt.core.javanature"; //$NON-NLS-1$

	/** the build command for the java project */
	public static final String JAVA_BUILD_COMMAND = "org.eclipse.jdt.core.javabuilder"; //$NON-NLS-1$

	/**
	 * The class file extension.
	 * 
	 * @since 2.1
	 */
	public static final String CLASS_FILE_EXTENSION = ".java"; //$NON-NLS-1$

	/**
	 * The basic source folder name.
	 * 
	 * @since 2.1
	 */
	public static final String BASIC_SOURCE_FOLDER_NAME = "src"; //$NON-NLS-1$

	/**
	 * The basic bin folder name.
	 * 
	 * @since 2.1
	 */
	public static final String BASIC_BIN_FOLDER_NAME = "bin"; //$NON-NLS-1$

	/**
	 * Add a new java source folder in the .classpath and in the build.properties
	 *
	 * @param path
	 *            the path of the java source folder
	 */
	public void addJavaSourceFolder(final String path);

	/**
	 * Add a new package in the java project.
	 * 
	 * @param sourceFolderName
	 *            the name of the source folder where the package will be added
	 * @param packageName
	 *            The package name.
	 * @since 2.1
	 */
	public IPackageFragment addPackage(final String sourceFolderName, final String packageName);

	/**
	 * Add a class in a package.
	 * 
	 * @param sourceFolderName
	 *            the source folder name
	 * @param packageName
	 *            The package name.
	 * @param className
	 *            The class name.
	 * @param content
	 *            The class content.
	 * @since 2.1
	 */
	public ICompilationUnit addClass(final String sourceFolderName, final String packageName, final String className, final String content);

	/**
	 * Get the current java project.
	 * 
	 * @return the current java project.
	 * @since 2.1
	 */
	public IJavaProject getJavaProject();

}
