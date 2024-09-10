/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.eclipse.project.editors.interfaces;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.papyrus.eclipse.project.editors.Activator;
import org.eclipse.papyrus.eclipse.project.editors.file.ManifestEditor;
import org.eclipse.papyrus.eclipse.project.editors.project.FeatureProjectEditor;
import org.eclipse.papyrus.eclipse.project.editors.project.JavaProjectEditor;
import org.eclipse.papyrus.eclipse.project.editors.project.PluginEditor;
import org.eclipse.papyrus.eclipse.project.editors.project.PluginProjectEditor;
import org.eclipse.papyrus.eclipse.project.editors.project.ProjectEditor;
import org.xml.sax.SAXException;

/**
 * Factory for access to editors for the various project metadata files supported
 * by the framework.
 * 
 * @since 2.0
 */
public class ProjectEditors {

	/**
	 * Not instantiable by clients.
	 */
	private ProjectEditors() {
		super();
	}

	/**
	 * Obtains an editor for all of the various metadata of a plug-in bundle project.
	 * 
	 * @param project
	 *            the project to edit
	 * @return the complete editor
	 * 
	 * @throws CoreException
	 *             on failure to create the editor (for example, if the project
	 *             is not {@link IResource#isAccessible() accessible})
	 */
	public static IPluginEditor getPluginEditor(IProject project) throws CoreException {
		try {
			return new PluginEditor(project);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to create plugin.xml editor", e));
		}
	}

	/**
	 * Obtains an editor for the <tt>.project</tt> file of a project.
	 * 
	 * @param project
	 *            the project to edit
	 * @return the <tt>.project</tt> editor
	 * 
	 * @throws CoreException
	 *             on failure to create the editor (for example, if the project
	 *             is not {@link IResource#isAccessible() accessible})
	 */
	public static IProjectEditor getProjectEditor(IProject project) throws CoreException {
		return new ProjectEditor(project);
	}

	/**
	 * Obtains an editor for the <tt>.project</tt>, <tt>.classpath</tt>, and
	 * <tt>build.properties</tt> files of a Java project.
	 * 
	 * @param project
	 *            the project to edit
	 * @return the Java project editor
	 * 
	 * @throws CoreException
	 *             on failure to create the editor (for example, if the project
	 *             is not {@link IResource#isAccessible() accessible})
	 */
	public static IJavaProjectEditor getJavaProjectEditor(IProject project) throws CoreException {
		return new JavaProjectEditor(project);
	}

	/**
	 * Obtains an editor for the <tt>plugin.xml</tt> file of a bundle project.
	 * 
	 * @param project
	 *            the project to edit
	 * @return the <tt>plugin.xml</tt> editor
	 * 
	 * @throws CoreException
	 *             on failure to create the editor (for example, if the project
	 *             is not {@link IResource#isAccessible() accessible})
	 */
	public static IPluginProjectEditor getPluginProjectEditor(IProject project) throws CoreException {
		try {
			return new PluginProjectEditor(project);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to create plugin.xml editor", e));
		}
	}

	/**
	 * Obtains an editor for the <tt>META-INF/MANIFEST</tt> file of a bundle project.
	 * 
	 * @param project
	 *            the project to edit
	 * @return the <tt>META-INF/MANIFEST.MF</tt> editor
	 * 
	 * @throws CoreException
	 *             on failure to create the editor (for example, if the project
	 *             is not {@link IResource#isAccessible() accessible})
	 */
	public static IManifestEditor getManifestEditor(IProject project) throws CoreException {
		try {
			return new ManifestEditor(project);
		} catch (IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to create manifest editor", e));
		}
	}

	/**
	 * Obtains an editor for the <tt>feature.xml</tt> file of a feature project.
	 * 
	 * @param project
	 *            the project to edit
	 * @return the <tt>feature.xml</tt> editor
	 * 
	 * @throws CoreException
	 *             on failure to create the editor (for example, if the project
	 *             is not {@link IResource#isAccessible() accessible})
	 */
	public static IFeatureProjectEditor getFeatureProjectEditor(IProject project) throws CoreException {
		try {
			return new FeatureProjectEditor(project);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new CoreException(new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Failed to create feature editor", e));
		}
	}
}
