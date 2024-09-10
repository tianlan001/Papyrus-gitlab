/*****************************************************************************
 * Copyright (c) 2019, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Nicolas FAUVERGUE (CEA LIST) nicolas.fauvergue@cea.fr - Initial API and implementation
 *   Christian W. Damus - bugs 569357, 570097
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.elementtypes.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThanOrEqual;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.hasAtLeast;
import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerMessage;
import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerSeverity;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
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
 * Test cases for the <em>Element Types Configurations</em> project builder configurations.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.elementtypes.example")
@MarkerType(ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
public class ElementTypesPluginBuilderTest extends AbstractPapyrusTest {

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test the reporting of problems on the element types model resource.
	 */
	@Test
	public void modelValidationFails() {
		final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$
		assertThat("The number of markers for model file is not correct", modelMarkers.size(), greaterThanOrEqual(2)); //$NON-NLS-1$
		assertThat("The severity of the model markesr is not correct", modelMarkers, everyItem(isMarkerSeverity(IMarker.SEVERITY_ERROR))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of problems on the bundle manifest.
	 */
	@Test
	public void dependencyValidationFails() {
		final List<IMarker> dependenciesMarkers = fixture.getMarkers("META-INF/MANIFEST.MF"); //$NON-NLS-1$
		assertThat("The number of markers for dependencies is not correct", dependenciesMarkers.size(), greaterThanOrEqual(5)); //$NON-NLS-1$
		assertThat("The number of warning markers for dependencies is not correct", dependenciesMarkers, hasAtLeast(2, isMarkerSeverity(IMarker.SEVERITY_WARNING))); //$NON-NLS-1$
		assertThat("The number of error markers for dependencies is not correct", dependenciesMarkers, hasAtLeast(3, isMarkerSeverity(IMarker.SEVERITY_ERROR))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of a missing dependency on the bundle that deploys an icon referenced by the model.
	 */
	@Test
	@OverlayFile(value = "bug569357-models/BookStore-iconBundleDependency.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
	@AuxProject("org.eclipse.papyrus.toolsmiths.validation.elementtypes.resources")
	public void iconBundleDependency() {
		final List<IMarker> modelMarkers = fixture.getMarkers("META-INF/MANIFEST.MF"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("elementtypes.resources"))))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of problems on the <tt>build.properties</tt> file.
	 */
	@Test
	public void buildPropertiesValidationFails() {
		final List<IMarker> buildMarkers = fixture.getMarkers("build.properties"); //$NON-NLS-1$
		assertThat("The number of markers for dependencies is not correct", buildMarkers.size(), greaterThanOrEqual(1)); //$NON-NLS-1$
		assertThat("The number of error markers for build.properties is not correct", buildMarkers, hasAtLeast(1, isMarkerSeverity(IMarker.SEVERITY_ERROR))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of problems on the <tt>plugin.xml</tt> file.
	 */
	@Test
	public void extensionValidationFails() {
		final List<IMarker> extensionsMarkers = fixture.getMarkers("plugin.xml"); //$NON-NLS-1$
		assertThat("The number of markers for extensions is not correct", extensionsMarkers.size(), greaterThanOrEqual(1)); //$NON-NLS-1$
		assertThat("Missing extension should be a warning", extensionsMarkers, hasItem(isMarkerSeverity(IMarker.SEVERITY_WARNING))); //$NON-NLS-1$
	}

	/**
	 * Test that a build of a correct project produces no markers on the bundle manifest.
	 */
	@Test
	@OverlayFile("bug569357-ok/META-INF/MANIFEST.MF")
	@AuxProject("org.eclipse.papyrus.toolsmiths.validation.elementtypes.resources")
	public void dependencyValidationPasses() {
		final List<IMarker> markers = fixture.getMarkers("META-INF/MANIFEST.MF"); //$NON-NLS-1$
		assertThat(markers, not(hasItem(anything())));
	}

	/**
	 * Test that a build of a correct project produces no markers on the <tt>build.properties</tt> file.
	 */
	@Test
	@OverlayFile("bug569357-ok/build.properties")
	public void buildPropertiesValidationPasses() {
		final List<IMarker> markers = fixture.getMarkers("build.properties"); //$NON-NLS-1$
		assertThat(markers, not(hasItem(anything())));
	}

	/**
	 * Test that a build of a correct project produces no markers on the <tt>plugin.xml</tt> file.
	 */
	@Test
	@OverlayFile("bug569357-ok/plugin.xml")
	public void extensionValidationPasses() {
		final List<IMarker> markers = fixture.getMarkers("plugin.xml"); //$NON-NLS-1$
		assertThat(markers, not(hasItem(anything())));
	}

	/**
	 * Test that a build of a correct project produces no markers on the element-types model file.
	 */
	@Test
	@OverlayFile("bug569357-ok/plugin.xml")
	@OverlayFile("bug569357-ok/resources/BookStore.elementtypesconfigurations")
	@OverlayFile("bug569357-ok/resources/BookStore.profile.uml")
	public void modelValidationPasses() {
		final List<IMarker> markers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$
		assertThat(markers, not(hasItem(anything())));
	}

}
