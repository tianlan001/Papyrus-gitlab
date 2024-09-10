/*****************************************************************************
 * Copyright (c) 2017 CEA LIST and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *   
 *****************************************************************************/

package org.eclipse.papyrus.eclipse.project.editors.project.filter;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginProjectEditor;

/**
 *
 * Filter used to find java plugin project in the workspace
 * 
 * @since 2.1
 */
public class JavaPluginProjectFilter extends ViewerFilter {

	/**
	 *
	 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 *
	 * @param viewer
	 * @param parentElement
	 * @param element
	 * @return
	 */
	@Override
	public boolean select(final Viewer viewer, final Object parentElement, final Object element) {
		if (element instanceof IProject) {
			final IProject project = ((IProject) element).getProject();
			if (project.exists() && project.isOpen()) {
				try {
					return project.hasNature(IJavaProjectEditor.JAVA_NATURE) && project.hasNature(IPluginProjectEditor.PLUGIN_NATURE);
				} catch (final CoreException e) {
					// nothing to do here, we already check if the project exists and is open
				}
			}
		}
		return false;
	}
}