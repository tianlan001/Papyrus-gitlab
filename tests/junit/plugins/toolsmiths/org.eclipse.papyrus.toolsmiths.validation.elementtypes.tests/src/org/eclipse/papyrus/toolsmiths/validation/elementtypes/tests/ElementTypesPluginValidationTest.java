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
 *   Christian W. Damus - bugs 569357, 573245
 *
 *****************************************************************************/
package org.eclipse.papyrus.toolsmiths.validation.elementtypes.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.greaterThanOrEqual;
import static org.eclipse.papyrus.junit.matchers.MoreMatchers.hasAtLeast;
import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerSeverity;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE;
import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.junit.utils.rules.ProjectFixture;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.MarkerType;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.eclipse.papyrus.toolsmiths.validation.elementtypes.checkers.ElementTypesPluginCheckerService;
import org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

/**
 * This class allows to test the profile plug-in validation.
 * This will check the markers resulting from a failing profile plug-in definition.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.elementtypes.example")
@MarkerType(ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
@SuppressWarnings("removal")
public class ElementTypesPluginValidationTest extends AbstractPapyrusTest {

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
		ElementTypesPluginCheckerService.checkElementTypesPlugin(fixture.getProject(), new NullProgressMonitor());

		// Get the markers
		List<IMarker> markers = null;
		try {
			markers = Arrays.asList(fixture.getProject().findMarkers(ElementTypesPluginValidationConstants.ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE, true, IResource.DEPTH_INFINITE));
		} catch (CoreException e) {
			Assert.fail("Error with resource"); //$NON-NLS-1$
		}

		// Now check the markers

		// Check the elementtypesconfigurations file markers
		final List<IMarker> elementtypesFileMarkers = markers.stream().filter(marker -> marker.getResource().getFullPath().toString().endsWith("BookStore.elementtypesconfigurations")).collect(Collectors.toList()); //$NON-NLS-1$
		assertThat("The number of markers for elementtypesconfigurations file is not correct", elementtypesFileMarkers.size(), greaterThanOrEqual(2)); //$NON-NLS-1$
		assertThat("The severity of elementtypesconfigurations marker is not correct", elementtypesFileMarkers, everyItem(isMarkerSeverity(IMarker.SEVERITY_ERROR))); //$NON-NLS-1$

		// Check the dependencies markers
		final List<IMarker> dependenciesMarkers = markers.stream().filter(marker -> marker.getResource().getFullPath().toString().endsWith("MANIFEST.MF")).collect(Collectors.toList()); //$NON-NLS-1$
		assertThat("The number of markers for dependencies is not correct", dependenciesMarkers.size(), greaterThanOrEqual(5)); //$NON-NLS-1$
		assertThat("The number of warning markers for dependencies is not correct", dependenciesMarkers, hasAtLeast(2, isMarkerSeverity(IMarker.SEVERITY_WARNING))); //$NON-NLS-1$
		assertThat("The number of error markers for dependencies is not correct", dependenciesMarkers, hasAtLeast(3, isMarkerSeverity(IMarker.SEVERITY_ERROR))); //$NON-NLS-1$

		// Check the extensions markers
		final List<IMarker> extensionsMarkers = markers.stream().filter(marker -> marker.getResource().getFullPath().toString().endsWith("plugin.xml")).collect(Collectors.toList()); //$NON-NLS-1$
		assertThat("The number of markers for extensions is not correct", extensionsMarkers.size(), greaterThanOrEqual(1)); //$NON-NLS-1$
		assertThat("Missing extension should be a warning", extensionsMarkers, hasItem(isMarkerSeverity(IMarker.SEVERITY_WARNING))); //$NON-NLS-1$
	}

}
