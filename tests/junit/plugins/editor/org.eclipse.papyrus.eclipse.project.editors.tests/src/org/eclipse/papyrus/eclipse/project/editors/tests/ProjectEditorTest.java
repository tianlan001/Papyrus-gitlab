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

package org.eclipse.papyrus.eclipse.project.editors.tests;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.eclipse.papyrus.eclipse.project.editors.interfaces.IProjectEditor;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the implementation of the {@link IProjectEditor} API.
 */
public class ProjectEditorTest {

	@Rule
	public final ProjectEditorFixture<? extends IProjectEditor> fixture;

	public ProjectEditorTest() {
		this(new ProjectEditorFixture<>(IProjectEditor.class));
	}

	ProjectEditorTest(ProjectEditorFixture<? extends IProjectEditor> fixture) {
		super();

		this.fixture = fixture;
	}

	@Test
	@MissingFiles
	public void getMissingFiles() {
		// The .project file already exists (cannot be avoided)
		assertThat(fixture.getEditor().getMissingFiles(), not(hasItem(".project")));
	}

	@Test
	public void addFile() {
		fixture.getEditor().addFile(fixture.getResource("simple_project/hello.txt"), "hello.txt", true);
		List<String> lines = fixture.slurp("hello.txt");
		assertThat(lines, hasItem("Hello, world!"));
	}

}
