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
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.AuxProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.MarkerType;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.OverlayFile;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Test cases for the <em>Properties Model</em> validation of <tt>MANIFEST.MF</tt>
 * dependencies in the project builder.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.properties.example")
@MarkerType(PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
@FixMethodOrder(MethodSorters.JVM)
public class PropertiesDependencyBuilderTest extends AbstractPapyrusTest {

	private static final String MANIFEST = "META-INF/MANIFEST.MF"; //$NON-NLS-1$
	private static final String ENVIRONMENT = "resources/BookStore.xmi"; //$NON-NLS-1$

	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test the reporting of a missing bundle dependency implied by a
	 * reference to <em>properties context</em> model via a
	 * <tt>ppe:/</tt> scheme URI.
	 */
	@Test
	@OverlayFile(value = "manifest/MANIFEST-missingDependency.MF", path = MANIFEST)
	public void missingDependencyByContextReference() {
		final List<IMarker> modelMarkers = fixture.getMarkers(MANIFEST);

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("required plug-in")).and(containsString("uml.properties")))))); //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * Test the reporting of an unresolved class name in a <em>properties environment model</em>.
	 */
	@Test
	@OverlayFile(value = "manifest/BookStore-unresolvedClassName.xmi", path = ENVIRONMENT)
	public void unresolvedEnvironmentModelFactory() {
		final List<IMarker> modelMarkers = fixture.getMarkers(ENVIRONMENT);

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("No such class")).and(containsString("BooModelElementFactory")))))); //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * Test the reporting of a missing bundle dependency implied by a
	 * reference to a <em>model-element factory class</em> by Java qualified name
	 * in a <em>properties environment model</em>.
	 */
	@Test
	@OverlayFile(value = "manifest/BookStore-missingClassDependency.xmi", path = ENVIRONMENT)
	@AuxProject("org.eclipse.papyrus.toolsmiths.validation.properties.example.dependency1")
	public void missingDependencyByEnvironmentModelFactory() {
		final List<IMarker> modelMarkers = fixture.getMarkers(MANIFEST);

		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("required plug-in")).and(containsString("properties.example.dependency1")))))); //$NON-NLS-1$//$NON-NLS-2$
	}

}
