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
import org.eclipse.uml2.uml.util.UMLValidator;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * Test cases for the <em>Element Types Configurations</em> validation of the model resource
 * in the project builder.
 */
@RunWith(Enclosed.class)
public class ElementTypesModelBuilderTest extends AbstractPapyrusTest {

	@TestProject("org.eclipse.papyrus.toolsmiths.validation.elementtypes.example")
	@OverlayFile("bug569357-ok/resources/BookStore.profile.uml")
	@OverlayFile("bug569357-ok/plugin.xml")
	@MarkerType(ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
	@Build
	public static class General {
		/**
		 * The project fixture to manage easily the project.
		 */
		@Rule
		public final TestProjectFixture fixture = new TestProjectFixture().filterDiagnosticSource(UMLValidator.DIAGNOSTIC_SOURCE);

		/**
		 * Test the reporting of an unresolved metamodel NS URI.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/BookStore-metamodelNSURI.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void unresolvedMetamodelNSURI() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("Unresolved metamodel NS URI"))))); //$NON-NLS-1$
		}

		/**
		 * Test the reporting of a non-existent icon.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/BookStore-iconNotFound.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void unresolvedIconReference() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("Book_Class.png"))))); //$NON-NLS-1$
		}
	}

	@TestProject("org.eclipse.papyrus.toolsmiths.validation.elementtypes.example")
	@OverlayFile("bug569357-ok/resources/BookStore.profile.uml")
	@OverlayFile("bug569357-ok/plugin.xml")
	@MarkerType(ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
	@Build
	public static class ApplyStereotypeAdvice {
		/**
		 * The project fixture to manage easily the project.
		 */
		@Rule
		public final TestProjectFixture fixture = new TestProjectFixture().filterDiagnosticSource(UMLValidator.DIAGNOSTIC_SOURCE);

		/**
		 * Test the reporting of an unresolved profile qualified name.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/applyStereotype/BookStore-unresolvedProfileName.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void unresolvedProfileQualifiedName() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("Unresolved profile 'NoSuchProfile'"))))); //$NON-NLS-1$
		}

		/**
		 * Test the reporting of an unresolved stereotype qualified name.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/applyStereotype/BookStore-unresolvedStereotypeName.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void unresolvedStereotypeQualifiedName() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("Unresolved stereotype"))))); //$NON-NLS-1$
		}

		/**
		 * Test the reporting of an unresolved feature name to set.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/applyStereotype/BookStore-unresolvedFeatureName.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void unresolvedFeatureName() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("No such feature 'whatever'"))))); //$NON-NLS-1$
		}

		/**
		 * Test the reporting of an unresolved stereotype because the name was not qualified.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/applyStereotype/BookStore-unqualifiedStereotypeName.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void unqualifiedStereotypeQualifiedName() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("Unresolved stereotype"))))); //$NON-NLS-1$
		}

		/**
		 * Test the validation of a model that is OK.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/applyStereotype/BookStore-ok.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void ok() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, not(hasItem(anything())));
		}
	}

	@TestProject("org.eclipse.papyrus.toolsmiths.validation.elementtypes.example")
	@OverlayFile("bug569357-ok/resources/BookStore.profile.uml")
	@OverlayFile("bug569357-ok/plugin.xml")
	@MarkerType(ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
	@Build
	public static class StereotypeMatcherAdvice {
		/**
		 * The project fixture to manage easily the project.
		 */
		@Rule
		public final TestProjectFixture fixture = new TestProjectFixture().filterDiagnosticSource(UMLValidator.DIAGNOSTIC_SOURCE);

		/**
		 * Test the reporting of an unresolved profile qualified name.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/stereotypeMatcher/BookStore-unresolvedProfileURI.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void unresolvedProfileURI() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("Unresolved profile"))))); //$NON-NLS-1$
		}

		/**
		 * Test the reporting of an unresolved stereotype qualified name.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/stereotypeMatcher/BookStore-unresolvedStereotypeName.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void unresolvedStereotypeQualifiedName() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("Unresolved stereotype"))))); //$NON-NLS-1$
		}

		/**
		 * Test the validation of a model that is OK.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/stereotypeMatcher/BookStore-ok.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void ok() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, not(hasItem(anything())));
		}
	}

	@TestProject("org.eclipse.papyrus.toolsmiths.validation.elementtypes.example")
	@OverlayFile("bug569357-ok/resources/BookStore.profile.uml")
	@OverlayFile("bug569357-ok/plugin.xml")
	@MarkerType(ELEMENTTYPES_PLUGIN_VALIDATION_MARKER_TYPE)
	@Build
	public static class StereotypeReferenceEdgeAdvice {
		/**
		 * The project fixture to manage easily the project.
		 */
		@Rule
		public final TestProjectFixture fixture = new TestProjectFixture().filterDiagnosticSource(UMLValidator.DIAGNOSTIC_SOURCE);

		/**
		 * Test the reporting of an unresolved stereotype qualified name.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/stereotypeReference/BookStore-unresolvedStereotype.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void unresolvedStereotypeQualifiedName() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("Unresolved stereotype"))))); //$NON-NLS-1$
		}

		/**
		 * Test the reporting of an unresolved feature name to set.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/stereotypeReference/BookStore-unresolvedFeature.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void unresolvedFeatureName() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(containsString("Unresolved feature 'writer'"))))); //$NON-NLS-1$
		}

		/**
		 * Test the validation of a model that is OK.
		 */
		@Test
		@OverlayFile(value = "bug569357-models/applyStereotype/BookStore-ok.elementtypesconfigurations", path = "resources/BookStore.elementtypesconfigurations")
		public void ok() {
			final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.elementtypesconfigurations"); //$NON-NLS-1$

			assertThat(modelMarkers, not(hasItem(anything())));
		}
	}

}
