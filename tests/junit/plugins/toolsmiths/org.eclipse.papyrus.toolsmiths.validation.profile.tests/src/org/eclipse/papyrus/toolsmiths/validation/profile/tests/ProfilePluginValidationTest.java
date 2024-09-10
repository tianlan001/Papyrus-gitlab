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
 *   Christian W. Damus - bugs 571125, 573245, 572677
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.profile.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThanOrEqual;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ProjectFixture;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.MarkerType;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.eclipse.papyrus.toolsmiths.validation.profile.checkers.ProfilePluginCheckerService;
import org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class allows to test the profile plug-in validation.
 * This will check the markers resulting from a failing profile plug-in definition.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.profile.example")
@MarkerType(ProfilePluginValidationConstants.PROFILE_PLUGIN_VALIDATION_MARKER_TYPE)
@SuppressWarnings("removal")
public class ProfilePluginValidationTest extends AbstractPapyrusTest {

	/**
	 * The severity attribute identifier.
	 */
	private static final String SEVERITY_ID = "severity"; //$NON-NLS-1$

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final ProjectFixture fixture = new TestProjectFixture();

	/**
	 * This allows to test the profile plug-in validation.
	 */
	@Test
	public void testProfilePluginValidation() {
		// First, run the validation
		ProfilePluginCheckerService.checkProfilePlugin(fixture.getProject(), null);

		// Get the markers
		List<IMarker> markers = null;
		try {
			markers = Arrays.asList(fixture.getProject().findMarkers(ProfilePluginValidationConstants.PROFILE_PLUGIN_VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE));
		} catch (CoreException e) {
			Assert.fail("Error with resource"); //$NON-NLS-1$
		}

		// Now check the markers
		Assert.assertNotNull("The markers have to be found", markers); //$NON-NLS-1$
		assertThat("The number of markers is not correct", markers.size(), greaterThanOrEqual(6)); //$NON-NLS-1$

		// Check the profile.uml markers
		final List<IMarker> profileFileMarkers = markers.stream().filter(marker -> marker.getResource().getFullPath().toString().endsWith("bookstore.profile.uml")).collect(Collectors.toList()); //$NON-NLS-1$
		Assert.assertNotNull("Profile file markers are not found", profileFileMarkers); //$NON-NLS-1$
		Assert.assertEquals("The number of markers for profile file is not correct", 0, profileFileMarkers.size()); //$NON-NLS-1$

		// Check the dependencies markers
		final List<IMarker> manifestMarkers = markers.stream().filter(marker -> marker.getResource().getFullPath().toString().endsWith("MANIFEST.MF")).collect(Collectors.toList()); //$NON-NLS-1$
		Assert.assertNotNull("Dependencies markers are not found", manifestMarkers); //$NON-NLS-1$
		Assert.assertEquals("The number of markers for dependencies is not correct", 4, manifestMarkers.size()); //$NON-NLS-1$
		IMarker manifestWarning = manifestMarkers.stream().filter(m -> isMarkerSeverity(m, IMarker.SEVERITY_WARNING)).findAny().orElse(null);
		Assert.assertNotNull("The severity of profile marker is not correct", manifestWarning); //$NON-NLS-1$

		// Check the build markers. The profile and genmodel need to be included in the binary build
		final List<IMarker> buildMarkers = markers.stream().filter(marker -> marker.getResource().getFullPath().toString().endsWith("build.properties")).collect(Collectors.toList()); //$NON-NLS-1$
		Assert.assertNotNull("Build markers are not found", buildMarkers); //$NON-NLS-1$
		final List<IMarker> errorBuildMarkers = buildMarkers.stream().filter(marker -> isMarkerSeverity(marker, IMarker.SEVERITY_ERROR)).collect(Collectors.toList());
		assertThat("The number of error markers for build is not correct", errorBuildMarkers.size(), greaterThanOrEqual(1)); //$NON-NLS-1$

		// Check the extensions markers
		final List<IMarker> extensionsMarkers = markers.stream().filter(marker -> marker.getResource().getFullPath().toString().endsWith("plugin.xml")).collect(Collectors.toList()); //$NON-NLS-1$
		Assert.assertNotNull("Extensions markers are not found", extensionsMarkers); //$NON-NLS-1$
		Assert.assertEquals("The number of markers for extensions is not correct", 2, extensionsMarkers.size()); //$NON-NLS-1$
		final List<IMarker> warningExtensionsMarkers = extensionsMarkers.stream().filter(marker -> isMarkerSeverity(marker, IMarker.SEVERITY_WARNING)).collect(Collectors.toList());
		Assert.assertEquals("The number of warning markers for extensions is not correct", 1, warningExtensionsMarkers.size()); //$NON-NLS-1$
		final List<IMarker> errorExtensionsMarkers = extensionsMarkers.stream().filter(marker -> isMarkerSeverity(marker, IMarker.SEVERITY_ERROR)).collect(Collectors.toList());
		Assert.assertEquals("The number of error markers for extensions is not correct", 1, errorExtensionsMarkers.size()); //$NON-NLS-1$

	}

	/**
	 * This allows to determinate if a marker got the correct severity.
	 *
	 * @param marker
	 *            the marker.
	 * @param severity
	 *            The severity to get.
	 * @return <code>true</code> if the marker got the correct severity, <code>false</code> otherwise.
	 */
	private boolean isMarkerSeverity(final IMarker marker, final int severity) {
		try {
			return severity == (int) marker.getAttribute(SEVERITY_ID);
		} catch (CoreException e) {
			Assert.fail("Error while getting " + SEVERITY_ID); //$NON-NLS-1$
			return false;
		}
	}

}
