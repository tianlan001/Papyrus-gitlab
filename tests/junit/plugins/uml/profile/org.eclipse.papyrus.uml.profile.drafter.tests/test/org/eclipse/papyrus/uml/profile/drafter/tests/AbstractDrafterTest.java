/*****************************************************************************
 * Copyright (c) 2015 CEA.
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
 *  Francois Le Fevre  francois.le-fevre@cea.fr - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.uml.profile.drafter.tests;

import java.io.IOException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.junit.utils.PapyrusProjectUtils;
import org.eclipse.papyrus.junit.utils.ProjectUtils;
import org.eclipse.papyrus.junit.utils.tests.AbstractEditorTest;
import org.osgi.framework.Bundle;


/**
 * @author Francois Le Fevre
 *
 */
public abstract class AbstractDrafterTest extends AbstractEditorTest {

	public static final String FROM_BUNDLE_NAME = "org.eclipse.papyrus.uml.profile.drafter.tests"; //$NON-NLS-1$
	
	/**
	 * Created project.
	 */
	protected IProject project;
	protected IFile diModelFile;

	/**
	 * Inits this.editor
	 * Fails or throws an exception if an error occurs
	 *
	 * @param bundle
	 *        the source bundle where the model is store
	 * @param projectName
	 *        the project that will created at runtime to execute test
	 * @param modelName
	 *        the model that will be copied and test executed on.
	 * @throws IOException 
	 */
	protected void initModel(String projectName, String modelName, Bundle bundle) throws CoreException, IOException {
		project = ProjectUtils.createProject(projectName);
		diModelFile = PapyrusProjectUtils.copyPapyrusModel(project, bundle, getSourcePath(), modelName);
	}
	

	protected String getSourcePath() {
		return "models/"; //$NON-NLS-1$
	}


	/**
	 *
	 * @return
	 *         the current bundle
	 */
	protected Bundle getBundle() {
		return Activator.getDefault().getBundle();
	}


}
