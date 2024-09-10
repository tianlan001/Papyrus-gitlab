/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.toolsmiths.validation.architecture.tests;

import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerMessage;
import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerSeverity;
import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.MarkerType;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.OverlayFile;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the <em>Architecture Domain Model</em> validation of <tt>build.properties</tt>
 * in the project builder.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.architecture.example")
@MarkerType(ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
public class ArchitectureBuildPropertiesBuilderTest extends AbstractPapyrusTest {

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test the reporting of the architecture model itself not being included in the build.
	 */
	@Test
	@OverlayFile(value = "bug570097-build/build-noArchitecture.properties", path = "build.properties")
	public void missingArchitecture() {
		final List<IMarker> modelMarkers = fixture.getMarkers("build.properties"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("BookStore.architecture"))))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of a co-defined model resource (a palette configuration model in this case) that is not included in the build.
	 */
	@Test
	@OverlayFile(value = "bug570097-build/build-noPalette.properties", path = "build.properties")
	public void missingReferencedResource() {
		final List<IMarker> modelMarkers = fixture.getMarkers("build.properties"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("BookStore.paletteconfiguration"))))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of an icon resource that is not included in the build.
	 */
	@Test
	@OverlayFile(value = "bug570097-build/build-noIcons.properties", path = "build.properties")
	public void missingIcon() {
		final List<IMarker> modelMarkers = fixture.getMarkers("build.properties"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("bookstore.png"))))); //$NON-NLS-1$
	}

	/**
	 * Test that it is okay when all required resources from the project are specifically and individually listed.
	 */
	@Test
	@OverlayFile(value = "bug570097-build/build-ok-specific.properties", path = "build.properties")
	public void allReferencesSpecific() {
		final List<IMarker> modelMarkers = fixture.getMarkers("build.properties"); //$NON-NLS-1$

		assertThat(modelMarkers, not(hasItem(anything())));
	}

	/**
	 * Test that it is okay when all required resources from the project are implicitly listed by a folder.
	 */
	@Test
	public void allReferencedByFolder() {
		final List<IMarker> modelMarkers = fixture.getMarkers("build.properties"); //$NON-NLS-1$

		assertThat(modelMarkers, not(hasItem(anything())));
	}

}
