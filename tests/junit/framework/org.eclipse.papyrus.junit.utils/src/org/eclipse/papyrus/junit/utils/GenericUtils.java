/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
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
 *  Vincent Lorenzo (CEA LIST) Vincent.Lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.junit.utils;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.intro.IIntroPart;

/**
 * 
 Some useful methods for Tests
 * 
 */
public class GenericUtils {

	private GenericUtils() {
		//to prevent instanciation
	}

	/**
	 * close the Intro part
	 */
	public static final void closeIntroPart() {
		final IIntroPart introPart = PlatformUI.getWorkbench().getIntroManager().getIntro();
		PlatformUI.getWorkbench().getIntroManager().closeIntro(introPart);
	}

	/**
	 * Close all the editors
	 */
	public static final void closeAllEditors() {
		PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().closeAllEditors(false);
	}

	/**
	 * Clean the workspace
	 * 
	 * @throws CoreException
	 */
	public static final void cleanWorkspace() throws CoreException {
		// we clean the workspace and create a new project to test the handlers
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		for(final IProject project : workspace.getRoot().getProjects()) {

			project.delete(true, new NullProgressMonitor());

		}
	}
}
