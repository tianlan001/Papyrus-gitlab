/*****************************************************************************
 * Copyright (c) 2021 CEA LIST.
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
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.widgets.providers;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;

/**
 *
 * Workspace Content Provider able to show only the current project
 *
 * @since 4.1
 */
public class SingleProjectContentProvider extends WorkspaceContentProvider {

	/**
	 * the name of the project to show
	 */
	private String projectName;

	/**
	 *
	 * Constructor.
	 *
	 * @param projectName
	 *            the name of the project to show
	 */
	public SingleProjectContentProvider(final String projectName) {
		super();
		this.projectName = projectName;
	}

	@Override
	public Object[] getElements() {
		// no override required for the initial usecase
		return super.getElements();
	}

	/**
	 *
	 * @see org.eclipse.papyrus.infra.widgets.providers.EncapsulatedContentProvider#getElements(java.lang.Object)
	 *
	 * @param inputElement
	 * @return
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if (projectName != null
				&& !projectName.isEmpty()
				&& ResourcesPlugin.getWorkspace() != null
				&& ResourcesPlugin.getWorkspace().getRoot() != null) {
			final IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(this.projectName);
			if (project != null) {
				return new Object[] { project };
			}
		}

		return super.getElements(inputElement);
	}


}
