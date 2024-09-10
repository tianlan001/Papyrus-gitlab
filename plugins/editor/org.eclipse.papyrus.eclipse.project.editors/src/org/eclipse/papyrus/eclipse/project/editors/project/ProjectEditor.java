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
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.project;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.papyrus.eclipse.project.editors.Activator;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IProjectEditor;

/**
 *
 * This class allows to manage the eclipse project
 *
 */
public class ProjectEditor extends AbstractProjectEditor implements IProjectEditor {


	/**
	 *
	 * Constructor.
	 *
	 * @param project
	 *            the eclipse project
	 * @throws CoreException
	 */
	public ProjectEditor(final IProject project) throws CoreException {
		super(project);
	}

	/**
	 * Initializes me as a slave to another editor, which maintains the canonical
	 * project description.
	 *
	 * @param master
	 *            my master editor
	 */
	ProjectEditor(AbstractProjectEditor master) {
		super(master);
	}

	/**
	 * Create the project file
	 * TODO NOT TESTED
	 *
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IProjectEditor#createFiles(Set)
	 *
	 *      {@inheritDoc}
	 */
	@Override
	public void createFiles(final Set<String> files) {
		if (files.contains(PROJECT_FILE)) {
			final IFile file = getProject().getFile(PROJECT_FILE);
			if (!file.exists()) {
				String input = ""; //$NON-NLS-1$
				input += AbstractProjectEditor.XML_HEADER;
				input += "<" + NAME + ">"; //$NON-NLS-1$ //$NON-NLS-2$
				input += getProject().getName();
				input += "</" + NAME + ">"; //$NON-NLS-1$ //$NON-NLS-2$
				input += "<" + COMMENT + ">"; //$NON-NLS-1$ //$NON-NLS-2$
				input += "</" + COMMENT + ">"; //$NON-NLS-1$ //$NON-NLS-2$
				input += "<" + BUILD_SPEC + ">"; //$NON-NLS-1$ //$NON-NLS-2$
				input += "</" + BUILD_SPEC + ">"; //$NON-NLS-1$ //$NON-NLS-2$
				input += "<" + NATURE + ">"; //$NON-NLS-1$ //$NON-NLS-2$
				input += "</" + NATURE + ">"; //$NON-NLS-1$ //$NON-NLS-2$

				try {
					file.create(getInputStream(input), true, null);
				} catch (final CoreException e) {
					Activator.log.error(e);
				}
			}
		}
	}


	@Override
	public Set<String> getMissingFiles() {
		final Set<String> missingFile = super.getMissingFiles();
		final IFile projectFile = getProject().getFile(PROJECT_FILE);
		if (!projectFile.exists()) {
			missingFile.add(IProjectEditor.PROJECT_FILE);
		}
		return missingFile;
	}

	@Override
	public void addFile(final URL url, final String fileDestinationPath, final boolean eraseExitingFile) {
		final IFile targetFile = getProject().getFile(new Path(fileDestinationPath));
		if (targetFile.exists()) {
			if (eraseExitingFile) {
				try {
					targetFile.delete(true, new NullProgressMonitor());
				} catch (final CoreException e) {
					Activator.log.error(e);
				}
			} else {
				return;
			}
		}
		try {
			final InputStream is = url.openStream();
			targetFile.create(is, false, new NullProgressMonitor());
			is.close();
			targetFile.refreshLocal(IResource.DEPTH_ZERO, new NullProgressMonitor());
		} catch (final CoreException e) {
			Activator.log.error(e);
		} catch (final IOException e) {
			Activator.log.error(e);
		}


	}
}
