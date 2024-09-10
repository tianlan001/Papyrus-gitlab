/*****************************************************************************
 * Copyright (c) 2011, 2016, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr
 *  Christian W. Damus - bug 485220
 *  Vincent Lorenzo (CEA-LIST) vincent.lorenzo@cea.fr - Bug 574258
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.file;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.ecore.resource.impl.PlatformResourceURIHandlerImpl;
import org.eclipse.papyrus.eclipse.project.editors.Activator;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IBuildEditor;

public class BuildEditor extends AbstractFileEditor implements IBuildEditor {

	/** the build config */
	private Properties buildConfig;

	/** the build file */
	private IFile buildFile;

	/**
	 * The build key to edit
	 * Defaults to "bin.includes" (The eclipse standard binary build)
	 */
	private String buildKey = "bin.includes"; //$NON-NLS-1$

	/**
	 *
	 * Constructor.
	 *
	 * @param project
	 *            the eclipse project
	 */
	public BuildEditor(final IProject project) {
		this(project, null);
	}

	/**
	 *
	 * Constructor.
	 *
	 * @param project
	 *            the eclipse project
	 * @param buildKey
	 *            the build key to edit. If null, the "bin.includes" key will be used
	 *
	 * @see IBuildEditor#BINARY_BUILD
	 * @see IBuildEditor#SOURCE_BUILD
	 */
	public BuildEditor(final IProject project, String buildKey) {
		super(project);

		buildFile = project.getFile(BUILD_PROPERTIES_FILE);
		if (buildKey != null) {
			this.buildKey = buildKey;
		}
	}

	@Override
	public void init() {
		this.buildConfig = new Properties();
		if (this.buildFile.exists()) {
			try (InputStream input = this.buildFile.getContents()) {
				this.buildConfig.load(input);
			} catch (CoreException e) {
				Activator.log.log(e.getStatus());
			} catch (IOException e) {
				Activator.log.error(e);
			}
		}
	}

	@Override
	public void addToBuild(final String path) {
		if (exists()) {
			String currentValue = this.buildConfig.getProperty(buildKey);
			if (Arrays.asList(getElementsInBuild()).contains(path)) {
				return;
			}
			touch();
			if (currentValue == null || currentValue.trim().equals("")) { //$NON-NLS-1$
				this.buildConfig.setProperty(buildKey, path);
			} else {
				this.buildConfig.setProperty(buildKey, currentValue + "," + path); //$NON-NLS-1$
			}
		}
	}

	@Override
	public void removeFromBuild(String path) {
		if (isRegisteredSourceFolder(path)) {
			touch();

			// Get the files from the build
			List<String> allFiles = Arrays.asList(getElementsInBuild());

			// Clear the build
			this.buildConfig.setProperty(buildKey, "");

			// Recreate the build without the removed files
			for (String filePath : allFiles) {
				if (!filePath.equals(path)) {
					addToBuild(filePath);
				}
			}
		}
	}

	@Override
	protected void doSave() {
		if (exists()) {
			try (OutputStream output = new PlatformResourceURIHandlerImpl.PlatformResourceOutputStream(this.buildFile, false, true, null)) {
				this.buildConfig.store(output, ""); //$NON-NLS-1$
			} catch (IOException ex) {
				Activator.log.error(ex);
			}
		}
	}


	@Override
	public void registerSourceFolder(final String path) {
		if (exists() && !isRegisteredSourceFolder(path)) {
			touch();

			String currentValue = this.buildConfig.getProperty(SOURCE_FOLDER_KEY);
			if (currentValue == null || currentValue.trim().equals("")) { //$NON-NLS-1$
				this.buildConfig.setProperty(SOURCE_FOLDER_KEY, path);
			} else {
				this.buildConfig.setProperty(SOURCE_FOLDER_KEY, currentValue + "," + path); //$NON-NLS-1$
			}
		}
	}

	@Override
	public boolean isRegisteredSourceFolder(final String path) {
		return Arrays.asList(getSourceFolders()).contains(path);
	}

	@Override
	public Set<String> getMissingFiles() {
		Set<String> files = super.getMissingFiles();
		if (!this.buildFile.exists()) {
			files.add(BUILD_PROPERTIES_FILE);
		}
		return files;
	}

	@Override
	public String[] getSourceFolders() {
		if (exists()) {
			String currentValue = this.buildConfig.getProperty(SOURCE_FOLDER_KEY, "").replaceAll("\t|\r|\n", "").trim();
			String[] values = currentValue.split(","); //$NON-NLS-1$
			return values;
		}
		return new String[0];
	}

	@Override
	public boolean exists() {
		return super.exists() && this.buildFile.exists();
	}

	@Override
	public void createFiles(final Set<String> files) {
		if (files.contains(BUILD_PROPERTIES_FILE)) {
			if (!this.buildFile.exists()) {
				try {
					this.buildFile.create(new ByteArrayInputStream(new byte[0]), false, null);
					init();
				} catch (CoreException e) {
					Activator.log.log(e.getStatus());
				}
			}
		}
	}

	@Override
	public String[] getElementsInBuild() {
		String value = this.buildConfig.getProperty(buildKey);
		return splitValues(value);
	}

	private String[] splitValues(String value) {
		if (value == null) {
			return new String[0];
		}
		return value.replace("\t|\r|\n", "").split(","); //$NON-NLS-1$ //$NON-NLS-2$
	}

	@Override
	public void registerBinFolder(String binFolder) {
		if (isRegisteredBinFolder(binFolder)) {
			return;
		}

		touch();

		String value = this.buildConfig.getProperty(BIN_KEY, ""); //$NON-NLS-1$
		if (value.trim().equals("")) { //$NON-NLS-1$
			value = binFolder;
		} else {
			value = value + "," + binFolder;
		}
		this.buildConfig.setProperty(BIN_KEY, value);
	}

	@Override
	public boolean isRegisteredBinFolder(String binFolder) {
		List<String> folders = Arrays.asList(splitValues(this.buildConfig.getProperty(BIN_KEY, ""))); //$NON-NLS-1$
		return folders.contains(binFolder);
	}

	/**
	 *
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IBuildEditor#getSourceIncludes()
	 *
	 * @return
	 */
	@Override
	public String[] getSourceIncludes() {
		String value = this.buildConfig.getProperty(SOURCE_BUILD);
		return splitValues(value);
	}
}
