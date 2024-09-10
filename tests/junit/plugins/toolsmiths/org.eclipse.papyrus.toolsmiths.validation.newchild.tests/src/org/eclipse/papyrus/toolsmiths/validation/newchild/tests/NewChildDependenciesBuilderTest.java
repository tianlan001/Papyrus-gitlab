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
package org.eclipse.papyrus.toolsmiths.validation.newchild.tests;

import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerMessage;
import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerSeverity;
import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
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
 * Test cases for the <em>Element Creation Menu Model</em> validation of bundle dependencies
 * in the project builder.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.newchild.example")
@MarkerType(NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
public class NewChildDependenciesBuilderTest extends AbstractPapyrusTest {

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test the reporting of a missing core static dependency (not depending on model content).
	 */
	@Test
	@OverlayFile(value = "bug572633-dependencies/META-INF/MANIFEST-missingNewChild.MF", path = "META-INF/MANIFEST.MF")
	public void missingNewChildBundleDependency() {
		final List<IMarker> modelMarkers = fixture.getMarkers("META-INF/MANIFEST.MF"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(containsString("infra.newchild"))))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of an icon resource that exists but which the bundle dependency is undeclared.
	 */
	@Test
	@OverlayFile(value = "bug572633-dependencies/BookStore-missingIconBundle.creationmenumodel", path = "resources/BookStore.creationmenumodel")
	public void iconResourceBundle() {
		final List<IMarker> modelMarkers = fixture.getMarkers("META-INF/MANIFEST.MF"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("uml2.uml.edit"))))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of a referenced model resource that exists but which the bundle dependency is undeclared.
	 */
	@Test
	@OverlayFile(value = "bug572633-dependencies/BookStore-missingElementTypesBundle.creationmenumodel", path = "resources/BookStore.creationmenumodel")
	public void modelResourceBundle() {
		final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.creationmenumodel"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("uml.service.types"))))); //$NON-NLS-1$
	}

}
