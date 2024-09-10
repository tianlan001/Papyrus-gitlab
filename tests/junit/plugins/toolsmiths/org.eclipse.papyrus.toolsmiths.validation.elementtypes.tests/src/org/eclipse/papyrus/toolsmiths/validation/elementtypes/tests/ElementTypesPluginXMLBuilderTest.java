/*****************************************************************************
 * Copyright (c) 2020, 2021 Christian W. Damus, CEA LIST, and others.
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
package org.eclipse.papyrus.toolsmiths.validation.elementtypes.tests;

import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerMessage;
import static org.eclipse.papyrus.junit.matchers.WorkspaceMatchers.isMarkerSeverity;
import static org.eclipse.papyrus.toolsmiths.validation.elementtypes.constants.ElementTypesPluginValidationConstants.ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE;
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
 * Test cases for the <em>Element Types Configurations</em> validation of <tt>plugin.xml</tt>
 * in the project builder.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.elementtypes.example")
@MarkerType(ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
public class ElementTypesPluginXMLBuilderTest extends AbstractPapyrusTest {

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test the reporting of a missing extension point where the extension point is just entirely missing.
	 */
	@Test
	@OverlayFile(value = "bug569357-extensions/plugin-noExtension.xml", path = "plugin.xml")
	public void noExtension() {
		final List<IMarker> modelMarkers = fixture.getMarkers("plugin.xml"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(containsString("Missing extension"))))); //$NON-NLS-1$
	}

	/**
	 * Test that no problem is reported when the extension point is absent but a registered
	 * architecture model references the element types configuration set.
	 */
	@Test
	@OverlayFile(value = "bug569357-extensions/plugin-noExtension.xml", path = "plugin.xml")
	@OverlayFile(value = "bug569357-extensions/BookStore.architecture", path = "resources/BookStore.architecture")
	public void noExtensionButArchitectureReference() {
		final List<IMarker> modelMarkers = fixture.getMarkers("plugin.xml"); //$NON-NLS-1$

		assertThat(modelMarkers, not(hasItem(isMarkerMessage(containsString("Missing extension"))))); //$NON-NLS-1$
	}

	/**
	 * Test the reporting of a missing client-context ID where the extension point is otherwise present.
	 */
	@Test
	@OverlayFile(value = "bug569357-extensions/plugin-noClientContext.xml", path = "plugin.xml")
	public void noClientContext() {
		final List<IMarker> modelMarkers = fixture.getMarkers("plugin.xml"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("Missing client context ID"))))); //$NON-NLS-1$
	}

	/**
	 * Test that an extension that references a non-existent element types model file does not report
	 * an error from our builder because that is covered by the PDE Builder.
	 */
	@Test
	@OverlayFile(value = "bug569357-extensions/plugin-wrongPath.xml", path = "plugin.xml")
	public void noSuchModelFile() {
		final List<IMarker> modelMarkers = fixture.getMarkers("plugin.xml"); //$NON-NLS-1$

		assertThat(modelMarkers, not(hasItem(anything())));
	}

	/**
	 * Test the reporting of a client-context ID that is not known either in GMF nor in any Architecture Context.
	 *
	 * @see <a href="http://eclip.se/542945">bug 542945</a>
	 */
	@Test
	@OverlayFile(value = "bug542945/plugin-customClientContext.xml", path = "plugin.xml")
	public void unknownClientContextID() {
		final List<IMarker> modelMarkers = fixture.getMarkers("plugin.xml"); //$NON-NLS-1$

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(
				isMarkerMessage(containsString("Unknown architecture context or GMF client context")))));
	}

	/**
	 * Test that a client context is matched against a registered Architecture Context.
	 *
	 * @see <a href="http://eclip.se/542945">bug 542945</a>
	 */
	@Test
	@OverlayFile(value = "bug542945/plugin-customClientContext.xml", path = "plugin.xml")
	@OverlayFile(value = "bug542945/BookStore.architecture", path = "resources/BookStore.architecture")
	public void clientContextIDViaArchitectureCongtext() {
		final List<IMarker> modelMarkers = fixture.getMarkers("plugin.xml"); //$NON-NLS-1$

		assertThat(modelMarkers, not(hasItem(anything())));
	}

	/**
	 * Test that a client context is matched against a registration of the ID in GMF.
	 *
	 * @see <a href="http://eclip.se/542945">bug 542945</a>
	 */
	@Test
	@OverlayFile(value = "bug542945/plugin-customClientContextGMF.xml", path = "plugin.xml")
	public void clientContextIDViaGMF() {
		final List<IMarker> modelMarkers = fixture.getMarkers("plugin.xml"); //$NON-NLS-1$

		assertThat(modelMarkers, not(hasItem(anything())));
	}

}
