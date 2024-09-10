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
package org.eclipse.papyrus.toolsmiths.validation.profile.tests;

import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerMessage;
import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerSeverity;
import static org.eclipse.papyrus.toolsmiths.validation.profile.constants.ProfilePluginValidationConstants.PROFILE_PLUGIN_VALIDATION_MARKER_TYPE;
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
import org.eclipse.uml2.uml.util.UMLValidator;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the <em>UML Profile</em> validation of the model resource
 * in the project builder.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.profile.example")
@MarkerType(PROFILE_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
public class ProfileModelBuilderTest extends AbstractPapyrusTest {

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture().filterDiagnosticSource(UMLValidator.DIAGNOSTIC_SOURCE);

	/**
	 * Test the reporting of a relative URI that results in profiles being unreferencerable from
	 * model resources such as <em>Architecture Models</em>.
	 *
	 * @see <a href="https://eclip.se/573886">bug 573886</a>
	 */
	@Test
	@OverlayFile("bug573886/resources/bookstore.profile.ecore")
	@OverlayFile("bug573886/resources/bookstore.profile.uml")
	@OverlayFile("bug573886/plugin.xml")
	public void profileRelativeURI() {
		final List<IMarker> modelMarkers = fixture.getMarkers("resources/bookstore.profile.uml"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(
				isMarkerMessage(containsString("is a relative URI"))))); //$NON-NLS-1$
	}

}
