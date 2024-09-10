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

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;

import org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the implementation of the {@link IJavaProjectEditor} API.
 */
public class JavaProjectEditorTest {

	@Rule
	public final ProjectEditorFixture<? extends IJavaProjectEditor> fixture;

	public JavaProjectEditorTest() {
		this(new ProjectEditorFixture<>(IJavaProjectEditor.class));
	}

	JavaProjectEditorTest(ProjectEditorFixture<? extends IJavaProjectEditor> fixture) {
		super();

		this.fixture = fixture;
	}

	@Test
	@MissingFiles
	public void getMissingFiles() {
		// The .project file already exists
		assertThat(fixture.getEditor().getMissingFiles(),
				both(hasItem(".classpath")).and(hasItem("build.properties")));
	}

	@Test
	@MissingFiles
	public void createFiles() {
		fixture.getEditor().createFiles(new HashSet<>(Arrays.asList(".classpath", "build.properties")));
		assertThat(fixture.slurp(".classpath"), hasItem(anything()));
		assertThat(fixture.getProject().getFile("build.properties").isAccessible(), is(true));
	}

	@Test
	public void getMissingNature() {
		assertThat(fixture.getEditor().getMissingNature(), hasItem(IJavaProjectEditor.JAVA_NATURE));
	}

	@Test
	public void hasNature() {
		assertThat(fixture.getEditor().hasNature(IJavaProjectEditor.JAVA_NATURE), is(false));
		fixture.getEditor().create();
		assertThat(fixture.getEditor().hasNature(IJavaProjectEditor.JAVA_NATURE), is(true));
		fixture.getEditor().save();

		// It's actually in the file
		assertThat(fixture.slurp(".project"), hasItem(containsString(IJavaProjectEditor.JAVA_NATURE)));
	}

	@Test
	public void getMissingBuildCommand() {
		assertThat(fixture.getEditor().getMissingBuildCommand(), hasItem(IJavaProjectEditor.JAVA_BUILD_COMMAND));
	}

	@Test
	public void addBuildCommands() {
		fixture.getEditor().addBuildCommands(Collections.singleton("org.eclipse.papyrus.foo"));
		fixture.getEditor().save();
		assertThat(fixture.slurp(".project"), hasItem(containsString("org.eclipse.papyrus.foo")));
	}

	@Test
	public void hasBuildCommand() {
		assertThat(fixture.getEditor().hasBuildCommand(IJavaProjectEditor.JAVA_BUILD_COMMAND), is(false));
		fixture.getEditor().create();
		assertThat(fixture.getEditor().hasBuildCommand(IJavaProjectEditor.JAVA_BUILD_COMMAND), is(true));
		fixture.getEditor().save();

		// It's actually in the file
		assertThat(fixture.slurp(".project"), hasItem(containsString(IJavaProjectEditor.JAVA_BUILD_COMMAND)));
	}

	@MissingFiles
	@Test
	public void exists() {
		assertThat(fixture.getEditor().exists(), is(false));
		fixture.getEditor().create();
		fixture.getEditor().save();

		assertThat(fixture.getEditor().exists(), is(true));
	}

	@Test
	public void registerSourceFolder() {
		fixture.getEditor().registerSourceFolder("src-gen");
		fixture.getEditor().save();
		assertThat(getBuildProperties().getProperty("source..", ""), containsString("src-gen"));
	}

	@Test
	public void registerBinFolder() {
		fixture.getEditor().registerBinFolder("classes");
		fixture.getEditor().save();
		assertThat(getBuildOutputProperty(), containsString("classes"));
	}

	@Test
	public void addToBuild() {
		fixture.getEditor().addToBuild("foo.xml");
		fixture.getEditor().save();
		assertThat(getBuildProperties().getProperty("bin.includes", ""), containsString("foo.xml"));
	}

	@Test
	public void isRegisteredSourceFolder() {
		fixture.getEditor().registerSourceFolder("src-gen");
		fixture.getEditor().save();
		assertThat(fixture.getEditor().isRegisteredSourceFolder("src-gen"), is(true));
	}

	@Test
	public void isRegisteredBinFolder() {
		fixture.getEditor().registerBinFolder("classes");
		fixture.getEditor().save();
		assertThat(fixture.getEditor().isRegisteredBinFolder("classes"), is(true));
	}

	@Test
	public void getSourceFolders() {
		fixture.getEditor().registerSourceFolder("src");
		fixture.getEditor().registerSourceFolder("src-gen");
		fixture.getEditor().save();

		assertThat(Arrays.asList(fixture.getEditor().getSourceFolders()),
				both(hasItem("src")).and(hasItem("src-gen")));
	}

	@CreatedProject
	@Test
	public void getBinFolders() {
		fixture.getEditor().addSourceFolderToClasspath("src", "bin");
		fixture.getEditor().addSourceFolderToClasspath("src-gen", "bin-gen");
		fixture.getEditor().save();

		assertThat(Arrays.asList(fixture.getEditor().getBinFolders()),
				both(hasItem("bin")).and(hasItem("bin-gen")));
	}

	@Test
	public void getElementsInBuild() {
		fixture.getEditor().addToBuild("META-INF/MANIFEST.MF");
		fixture.getEditor().addToBuild("plugin.xml");
		fixture.getEditor().addToBuild("plugin.properties");
		fixture.getEditor().save();

		assertThat(Arrays.asList(fixture.getEditor().getElementsInBuild()),
				both(hasItem("META-INF/MANIFEST.MF")).and(hasItem("plugin.xml")).and(hasItem("plugin.properties")));
	}

	@CreatedProject
	@Test
	public void addSourceFolderToClasspath() {
		fixture.getEditor().addSourceFolderToClasspath("src-gen");
		fixture.getEditor().save();

		assertThat(fixture.slurp(".classpath"),
				hasItem(both(containsString("\"src\"")).and(containsString("src-gen"))));
	}

	@CreatedProject
	@Test
	public void addJavaSourceFolder() {
		fixture.getEditor().addJavaSourceFolder("src-gen");
		fixture.getEditor().save();

		assertThat(fixture.slurp(".classpath"),
				hasItem(both(containsString("\"src\"")).and(containsString("src-gen"))));
		assertThat(getBuildProperties().getProperty("source..", ""), containsString("src-gen"));
	}

	@CreatedProject
	@Test
	public void isSourceFolderRegistered() {
		fixture.getEditor().addSourceFolderToClasspath("src-gen");
		fixture.getEditor().save();

		assertThat(fixture.getEditor().isSourceFolderRegistered("src-gen"), is(true));
	}

	//
	// Test framework
	//

	Properties getBuildProperties() {
		Properties result = new Properties();
		try (InputStream input = fixture.getProject().getFile("build.properties").getContents()) {
			result.load(input);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to load build properties: " + e.getMessage());
		}
		return result;
	}

	// The build editor may or may not use the modern convention of "output.."
	// instead of "bin.." for the output folder(s)
	String getBuildOutputProperty() {
		Properties build = getBuildProperties();
		String result = build.getProperty("output..");
		if (result == null) {
			result = build.getProperty("bin..", "");
		}
		return result;
	}
}
