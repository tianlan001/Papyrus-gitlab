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
package org.eclipse.papyrus.toolsmiths.validation.properties.tests;

import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerMessage;
import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerSeverity;
import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE;
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
 * Test cases for the <em>Properties Model</em> validation of <tt>plugin.xml</tt>
 * in the project builder.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.properties.example")
@MarkerType(PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
public class PropertiesPluginXMLBuilderTest extends AbstractPapyrusTest {

	private static final String PLUGIN = "plugin.xml"; //$NON-NLS-1$

	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test the reporting of a missing properties context registration extension.
	 */
	@Test
	@OverlayFile(value = "extensions/plugin-noContext.xml", path = PLUGIN)
	public void noContextExtension() {
		final List<IMarker> modelMarkers = fixture.getMarkers(PLUGIN);

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("Missing extension")).and(containsString("infra.properties.contexts")))))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of a missing properties environment registration extension.
	 */
	@Test
	@OverlayFile(value = "extensions/plugin-noEnvironment.xml", path = PLUGIN)
	public void noEnvironmentExtension() {
		final List<IMarker> modelMarkers = fixture.getMarkers(PLUGIN);

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("Missing extension")).and(containsString("infra.properties.environments")))))); //$NON-NLS-1$//$NON-NLS-2$
	}

}
