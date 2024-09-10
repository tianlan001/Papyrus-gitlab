/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.toolsmiths.validation.common.internal.utils.ProjectManagementUtils;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.junit.Rule;
import org.junit.Test;

/**
 * Specific test cases for the {@link ProjectManagementUtils} class.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.common.example")
@Build(false)
public class ProjectManagementUtilsTest {

	@Rule
	public final TestProjectFixture project = new TestProjectFixture();

	@Test
	public void existFileFromProject() {
		assertThat("UML file not found", ProjectManagementUtils.existFileFromProject(project.getProject(), "uml", true), is(true));
		assertThat("Bogus file found", ProjectManagementUtils.existFileFromProject(project.getProject(), "bogus", true), is(false));
	}

	@Test
	public void existFileFromProject_closed() {
		try {
			project.getProject().close(null);
		} catch (CoreException e) {
			e.printStackTrace();
			fail("Failed to close test project: " + e.getMessage());
		}

		assertThat("UML file found", ProjectManagementUtils.existFileFromProject(project.getProject(), "uml", true), is(false));
	}

	@Test
	public void getFilesFromProject() {
		Collection<IFile> files = ProjectManagementUtils.getFilesFromProject(project.getProject(), "uml", true);
		assertThat("UML files not found", files, not(isEmpty()));
	}

	@Test
	public void getFilesFromProject_closed() {
		try {
			project.getProject().close(null);
		} catch (CoreException e) {
			e.printStackTrace();
			fail("Failed to close test project: " + e.getMessage());
		}

		Collection<IFile> files = ProjectManagementUtils.getFilesFromProject(project.getProject(), "uml", true);
		assertThat("UML files found", files, isEmpty());
	}

}
