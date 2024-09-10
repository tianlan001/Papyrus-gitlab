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

import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.MarkerType;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.junit.Rule;
import org.junit.Test;

/**
 * General test cases for the <em>Properties Model</em> project builder.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.properties.example")
@MarkerType(PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
public class PropertiesPluginBuilderTest extends AbstractPapyrusTest {

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test the reporting of a project that passes all checks.
	 */
	@Test
	public void contextOK() {
		final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.ctx"); //$NON-NLS-1$
		assertThat(modelMarkers, not(hasItem(anything())));
	}

	/**
	 * Test the reporting of a project that passes all checks.
	 */
	@Test
	public void environmentOK() {
		final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.xmi"); //$NON-NLS-1$
		assertThat(modelMarkers, not(hasItem(anything())));
	}

	/**
	 * Test the reporting of a project that passes all checks.
	 */
	@Test
	public void manifestOK() {
		final List<IMarker> manifestMarkers = fixture.getMarkers("META-INF/MANIFEST.MF"); //$NON-NLS-1$
		assertThat(manifestMarkers, not(hasItem(anything())));
	}

	/**
	 * Test the reporting of a project that passes all checks.
	 */
	@Test
	public void extensionsOK() {
		final List<IMarker> pluginXMLMarkers = fixture.getMarkers("plugin.xml"); //$NON-NLS-1$
		assertThat(pluginXMLMarkers, not(hasItem(anything())));
	}

}
