/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.papyrus.eclipse.project.editors.file.BuildEditor;

/**
 *
 * Abstract class used to read previous contents of the build.properties file and preserve it at each re-generation
 *
 */
public abstract class AbstractBuild {

	/**
	 * the files declared in the binary build before the regeneration
	 */
	private Collection<String> existingBinaryIncludes = new HashSet<>();

	/**
	 * the files declared in the source build before the regeneration
	 */
	private Collection<String> existingSourceIncludes = new HashSet<>();

	/**
	 * the source folder declared before the regeneration
	 */
	private Collection<String> existingSourceFolders = new HashSet<>();

	private static final String SOURCE_INCLUDES_DECLARATION = "src.includes = "; //$NON-NLS-1$

	private static final String BINARY_INCLUDES_DECLARATION = "bin.includes = "; //$NON-NLS-1$

	private static final String SOURCE_DECLARATION = "source.. = "; //$NON-NLS-1$

	private static final String LICENCE_TAG = "#################################################################################"; //$NON-NLS-1$

	/**
	 * the edited project
	 */
	private IProject project;

	/**
	 * This method inits the field of the class
	 *
	 * @param projectName
	 *            the name of the project for which we are generatint a build.properties
	 */
	protected void init(final String projectName) {
		if (projectName != null && !projectName.isEmpty()) {
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			initFields();
		}
	}

	/**
	 * Fills the field
	 */
	private void initFields() {
		BuildEditor buildEditor;
		try {
			buildEditor = new BuildEditor(project);
			buildEditor.init();
			for (final String current : buildEditor.getElementsInBuild()) {
				this.existingBinaryIncludes.add(current);
			}
			for (final String current : buildEditor.getSourceFolders()) {
				this.existingSourceFolders.add(current);
			}
			for (final String current : buildEditor.getSourceIncludes()) {
				this.existingSourceIncludes.add(current);
			}


		} catch (Exception e) {
			// silent exception
		}
	}

	/**
	 *
	 * @return
	 *         the string to use to declare the binary folder
	 */
	public final String buildBinaryIncludes() {
		final Collection<String> includes = getGMFBinIncludes();
		includes.addAll(this.existingBinaryIncludes);
		return buildEntry(BINARY_INCLUDES_DECLARATION, includes);
	}

	/**
	 *
	 * @return
	 *         the string to use to declare the source folder
	 */
	public final String buildSourceFolder() {
		final Collection<String> includes = getGMFSourceFolder();
		includes.addAll(this.existingSourceFolders);
		return buildEntry(SOURCE_DECLARATION, includes);
	}

	/**
	 *
	 * @param key
	 *            the ket
	 * @param values
	 *            the value for this key
	 * @return
	 *         the string to use to declare the value associated to the key
	 */
	private final String buildEntry(final String key, final Collection<String> values) {
		final StringBuilder builder = new StringBuilder();
		if (values.size() > 0) {
			final Iterator<String> iter = values.iterator();
			final String spaces = getAlignmentSpacesFor(key);
			builder.append(key);
			if (iter.hasNext()) {
				builder.append(iter.next());
				if (iter.hasNext()) {
					builder.append(",\\\n"); //$NON-NLS-1$
				}
			}
			while (iter.hasNext()) {
				builder.append(spaces);
				builder.append(iter.next());
				if (iter.hasNext()) {
					builder.append(",\\\n"); //$NON-NLS-1$
				}
			}
		}
		return builder.toString();
	}

	/**
	 *
	 * @return
	 *         the string to use to declare the source includes
	 */
	public final String buildSourceIncludes() {
		return buildEntry(SOURCE_INCLUDES_DECLARATION, this.existingSourceIncludes);
	}

	/**
	 *
	 * @param entryDeclaration
	 *            the entry
	 * @return
	 *         the string with the expected number of spaces for a nice alignement
	 */
	private static final String getAlignmentSpacesFor(final String entryDeclaration) {
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < entryDeclaration.length(); i++) {
			builder.append(" "); //$NON-NLS-1$
		}
		return builder.toString();
	}

	/**
	 *
	 * @return
	 *         the gmf generated code source folder
	 */
	private final Collection<String> getGMFSourceFolder() {
		final Collection<String> sources = new TreeSet<>();
		sources.add("src-gen/"); // not src) //$NON-NLS-1$
		return sources;
	}

	/**
	 *
	 * @return
	 *         the list of bin includes required by the GMF generation
	 */
	private final Collection<String> getGMFBinIncludes() {
		final Collection<String> includes = new TreeSet<>();
		includes.add("."); //$NON-NLS-1$
		includes.add("icons/"); //$NON-NLS-1$
		includes.add("META-INF/"); //$NON-NLS-1$
		includes.add("plugin.xml"); //$NON-NLS-1$
		includes.add("plugin.properties"); //$NON-NLS-1$
		includes.add("messages.properties"); //$NON-NLS-1$
		includes.add(".options"); //$NON-NLS-1$
		return includes;

	}

	/**
	 *
	 * @param license
	 *            the license to include in the file
	 * @return
	 *         the string representing the license to include in the build.properties
	 */
	public final String buildLicense(final String license) {
		final StringBuilder builder = new StringBuilder(LICENCE_TAG);
		builder.append("\n"); //$NON-NLS-1$
		for (final String line : license.split("\n")) { //$NON-NLS-1$
			builder.append("# "); //$NON-NLS-1$
			builder.append(line);
			builder.append("\n"); //$NON-NLS-1$
		}

		builder.append(LICENCE_TAG);
		return builder.toString();
	}
}
