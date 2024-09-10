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
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.AuxProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.MarkerType;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.OverlayFile;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the <em>Architecture Domain Model</em> validation of bundle dependencies
 * in the project builder.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.architecture.example")
@MarkerType(ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
public class ArchitectureDependenciesBuilderTest extends AbstractPapyrusTest {

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test the reporting of a missing core static dependency (not depending on model content).
	 */
	@Test
	@OverlayFile(value = "bug570097-dependencies/META-INF/MANIFEST-missingArchitecture.MF", path = "META-INF/MANIFEST.MF")
	public void missingArchitectureFrameworkDependency() {
		final List<IMarker> modelMarkers = fixture.getMarkers("META-INF/MANIFEST.MF"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(containsString("infra.architecture"))))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of a creation command class that exists but which the bundle dependency is undeclared.
	 * In this case, the class is a "source type" from the JDT workspace.
	 */
	@Test
	@OverlayFile(value = "bug570097-dependencies/BookStore-missingCommandBundle.architecture", path = "resources/BookStore.architecture")
	// Create another project that contains the command class referenced in this test
	@AuxProject("org.eclipse.papyrus.toolsmiths.validation.architecture.classdiagram")
	public void creationCommandClassBundle() {
		final List<IMarker> modelMarkers = fixture.getMarkers("META-INF/MANIFEST.MF"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("architecture.classdiagram"))))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of a creation command class that exists but which the bundle dependency is undeclared.
	 * In this case, the class is a "binary type" from the PDE target platform.
	 */
	@Test
	@OverlayFile(value = "bug570097-dependencies/BookStore-missingCommandBundleBinaryType.architecture", path = "resources/BookStore.architecture")
	// Create another project that brings Papyrus UML Class Diagram onto the classpath so that our
	// command class reference is resolvable by JDT as a binary type
	@AuxProject("org.eclipse.papyrus.toolsmiths.validation.architecture.classdiagram")
	public void creationCommandClassBinaryTypeResolved() {
		final List<IMarker> modelMarkers = fixture.getMarkers("META-INF/MANIFEST.MF"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("uml.diagram.clazz"))))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of a creation command class that exists but which the bundle dependency is undeclared.
	 */
	@Test
	@OverlayFile(value = "bug570097-dependencies/BookStore-missingIconBundle.architecture", path = "resources/BookStore.architecture")
	public void iconBundle() {
		final List<IMarker> modelMarkers = fixture.getMarkers("META-INF/MANIFEST.MF"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("uml.diagram.clazz"))))); //$NON-NLS-1$
	}

}
