/*****************************************************************************
 * Copyright (c) 2019 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.utils;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.osgi.service.resolver.BundleSpecification;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.ProjectManagementUtils;
import org.eclipse.pde.core.build.IBuildModel;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.eclipse.pde.core.plugin.IPluginModelBase;

/**
 * This allows to provide services about project and files into project.
 * For example, we can found methods to get the dependencies, the extensions, the build but to get IFiles too.
 */
public class ProjectManagementService {

	/**
	 * This allows to get the plugin model base from the project.
	 * This allows to manipulate the 'plugin.xml' and 'MANIFEST.MF' files content.
	 *
	 * @param project
	 *            The current project.
	 * @return The {@link IPluginModelBase} representing the plug-in project content.
	 */
	public static IPluginModelBase getPluginModelBase(final IProject project) {
		return ProjectManagementUtils.getPluginModelBase(project);
	}

	/**
	 * This allows to get the list of extensions in the 'plugin.xml' file.
	 *
	 * @param project
	 *            The current project.
	 * @return The list of extensions (can be empty).
	 */
	public static List<IPluginExtension> getPluginExtensions(final IProject project) {
		return ProjectManagementUtils.getPluginExtensions(project);
	}

	/**
	 * This allows to get the list of required plug-ins.
	 *
	 * @param project
	 *            The current project.
	 * @return The list of required plug-ins (can be empty).
	 */
	public static List<BundleSpecification> getPluginDependencies(final IProject project) {
		return ProjectManagementUtils.getPluginDependencies(project);
	}

	/**
	 * This allows to get the build model of the project.
	 *
	 * @param project
	 *            The current project.
	 * @return The build model representing the 'build.properties' file.
	 */
	public static IBuildModel getPluginBuild(final IProject project) {
		return ProjectManagementUtils.getPluginBuild(project);
	}

	/**
	 * This allows to check the file corresponding to the file name in parameter exists.
	 *
	 * @param container
	 *            The container resource.
	 * @param foundFile
	 *            The name of the found file.
	 * @param isExtensionCheck
	 *            <code>true</code> if the found file is an extension to find, <code>false</code> otherwise.
	 * @return The corresponding file or <code>null</code>.
	 */
	public static boolean existFileFromProject(final IContainer container, final String foundFile, final boolean isExtensionCheck) {
		return ProjectManagementUtils.existFileFromProject(container, foundFile, isExtensionCheck);
	}

	/**
	 * This allows to get the files corresponding to the file name in parameter.
	 *
	 * @param container
	 *            The container resource.
	 * @param foundFile
	 *            The name of the found file.
	 * @param isExtensionCheck
	 *            <code>true</code> if the found file is an extension to find, <code>false</code> otherwise.
	 * @return The corresponding file or <code>null</code>.
	 */
	public static Collection<IFile> getFilesFromProject(final IContainer container, final String foundFile, final boolean isExtensionCheck) {
		return ProjectManagementUtils.getFilesFromProject(container, foundFile, isExtensionCheck);
	}

	/**
	 * This allows to get the 'MANIFEST.MF' file.
	 *
	 * @param container
	 *            The container.
	 * @return The found file 'MANIFEST.MF' or <code>null</code>.
	 */
	public static IFile getManifestFile(final IContainer container) {
		return ProjectManagementUtils.getManifestFile(container);
	}

	/**
	 * This allows to get the 'plugin.xml' file.
	 *
	 * @param container
	 *            The container.
	 * @return The found file 'plugin.xml' or <code>null</code>.
	 */
	public static IFile getPluginXMLFile(final IContainer container) {
		return ProjectManagementUtils.getPluginXMLFile(container);
	}

	/**
	 * This allows to get the 'build.properties' file.
	 *
	 * @param container
	 *            The container.
	 * @return The found file 'build.properties' or <code>null</code>.
	 */
	public static IFile getBuildFile(final IContainer container) {
		return ProjectManagementUtils.getBuildFile(container);
	}

}
