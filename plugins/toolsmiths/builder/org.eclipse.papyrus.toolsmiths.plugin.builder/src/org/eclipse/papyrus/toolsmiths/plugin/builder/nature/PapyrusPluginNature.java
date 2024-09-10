/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.plugin.builder.nature;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.papyrus.toolsmiths.plugin.builder.PapyrusPluginBuilder;

/**
 * The Papyrus project nature
 */
public class PapyrusPluginNature implements IProjectNature {

	public static final String PAPYRUS_PLUGIN_NATURE_ID = "org.eclipse.papyrus.plugin.nature"; //$NON-NLS-1$

	public static final String JAVA_NATURE = JavaCore.NATURE_ID;

	public static final String PLUGIN_NATURE = "org.eclipse.pde.PluginNature"; //$NON-NLS-1$

	/** The platform project this <code>IJavaProject</code> is based on */
	private IProject project;

	/** @see org.eclipse.core.resources.IProjectNature#configure() */
	@Override
	public void configure() throws CoreException {
		// Add nature-specific information
		// for the project, such as adding a builder
		// to a project's build spec.
		final IProjectDescription projectDescription = this.project.getDescription();
		final ICommand[] oldBuildSpec = this.project.getDescription().getBuildSpec();
		final ICommand[] newBuildSpec = new ICommand[oldBuildSpec.length + 1];
		System.arraycopy(oldBuildSpec, 0, newBuildSpec, 0, oldBuildSpec.length);
		final ICommand command = this.project.getDescription().newCommand();
		command.setBuilderName(PapyrusPluginBuilder.BUILDER_ID);
		newBuildSpec[oldBuildSpec.length] = command;
		projectDescription.setBuildSpec(newBuildSpec);
		this.project.setDescription(projectDescription, new NullProgressMonitor());
	}

	/** @see org.eclipse.core.resources.IProjectNature#deconfigure() */
	@Override
	public void deconfigure() throws CoreException {
		// Remove the nature-specific information here.
		// not yet implemented
	}

	/** @see org.eclipse.core.resources.IProjectNature#getProject() */
	@Override
	public IProject getProject() {
		return this.project;
	}

	/** @see org.eclipse.core.resources.IProjectNature#setProject(org.eclipse.core.resources.IProject) */
	@Override
	public void setProject(final IProject value) {
		this.project = value;
	}

}
