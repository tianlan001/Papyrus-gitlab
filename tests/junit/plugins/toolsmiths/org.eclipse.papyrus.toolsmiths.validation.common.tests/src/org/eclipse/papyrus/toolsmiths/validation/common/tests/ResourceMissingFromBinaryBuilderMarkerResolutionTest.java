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

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.regexContains;
import static org.eclipse.papyrus.toolsmiths.validation.common.checkers.CommonProblemConstants.BINARY_BUILD_PATH;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;
import java.util.stream.Stream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.AbstractMissingAttributeMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.ResourceMissingFromBinaryBuildMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.eclipse.ui.IMarkerResolution;
import org.junit.Rule;
import org.junit.Test;

/**
 * Specific test cases for the {@link AbstractMissingAttributeMarkerResolution} class.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.common.example")
@Build(false)
public class ResourceMissingFromBinaryBuilderMarkerResolutionTest {

	@Rule
	public final TestProjectFixture project = new TestProjectFixture();

	/**
	 * Verify that the marker resolution generates the expected file entry in the <tt>build.properties</tt>.
	 */
	@Test
	public void addFile() {
		IFile build = project.getFile("build.properties");

		IMarker marker = project.mockMarker(build, Map.of(BINARY_BUILD_PATH, "folder/file.txt"));

		IMarkerResolution fix = getFileFix(marker);
		fix.run(marker);

		String fileContent = project.getContent(build);
		assertThat(fileContent, regexContains(
				"(?ms),\\s*folder/file.txt"));
	}

	/**
	 * Verify that the marker resolution generates the expected folder entry in the <tt>build.properties</tt>.
	 */
	@Test
	public void addFolder() {
		IFile build = project.getFile("build.properties");

		IMarker marker = project.mockMarker(build, Map.of(BINARY_BUILD_PATH, "folder/file.txt"));

		IMarkerResolution fix = getFolderFix(marker);
		fix.run(marker);

		String fileContent = project.getContent(build);
		assertThat(fileContent, regexContains(
				"(?ms),\\s*folder/(,\\s*\\??)?\n"));
	}

	/**
	 * Verify that the marker resolution generates the expected folder entry in the <tt>build.properties</tt>
	 * when the file is in a nested folder.
	 */
	@Test
	public void addFolder_withNesting() {
		IFile build = project.getFile("build.properties");

		IMarker marker = project.mockMarker(build, Map.of(BINARY_BUILD_PATH, "folder/nested/deeply/file.txt"));

		IMarkerResolution fix = getFolderFix(marker);
		fix.run(marker);

		String fileContent = project.getContent(build);
		assertThat(fileContent, regexContains(
				"(?ms),\\s*folder/(,\\s*\\??)?\n"));
	}


	//
	// Test framework
	//

	IMarkerResolution getFileFix(IMarker marker) {
		IMarkerResolution[] result = ResourceMissingFromBinaryBuildMarkerResolution.forMarker(marker);
		return Stream.of(result).filter(fix -> fix.getClass().getName().contains("$File"))
				.findAny()
				.orElseThrow(() -> new AssertionError("No file-specific fix obtains"));
	}

	IMarkerResolution getFolderFix(IMarker marker) {
		IMarkerResolution[] result = ResourceMissingFromBinaryBuildMarkerResolution.forMarker(marker);
		return Stream.of(result).filter(fix -> fix.getClass().getName().contains("$Folder"))
				.findAny()
				.orElseThrow(() -> new AssertionError("No file-specific fix obtains"));
	}

}
