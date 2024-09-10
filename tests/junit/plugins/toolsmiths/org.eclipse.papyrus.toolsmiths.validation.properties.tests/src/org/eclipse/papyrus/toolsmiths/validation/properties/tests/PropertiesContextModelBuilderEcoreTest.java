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
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * Test cases for the <em>Properties Model</em> validation of <em>Context</em> resources
 * sourced in <em>UML Profiles</em>.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.properties.example")
@MarkerType(PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
@OverlayFile(value = "models/BookStore-ecore.ctx", path = PropertiesContextModelBuilderEcoreTest.CONTEXT)
@OverlayFile(value = "models/BookStore.ecore", path = PropertiesContextModelBuilderEcoreTest.ECORE)
@OverlayFile(value = "models/plugin-ecore.xml", path = "plugin.xml")
@FixMethodOrder(MethodSorters.JVM)
public class PropertiesContextModelBuilderEcoreTest extends AbstractPapyrusTest {

	static final String CONTEXT = "resources/BookStore.ctx"; //$NON-NLS-1$
	static final String ECORE = "resources/BookStore.ecore"; //$NON-NLS-1$

	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test that a context model with implicit dependencies only (generated before source annotations)
	 * does not report errors on missing data context elements.
	 */
	@Test
	@OverlayFile(value = "models/BookStore-ecore-implicitTracesOK.ctx", path = CONTEXT)
	public void implicitSourceTracesOK() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);

		assertThat(modelMarkers, not(hasItem(anything())));
	}

	@Test
	@OverlayFile(value = "models/BookStore-newProperty.ecore", path = ECORE)
	public void missingDataContextProperty() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("is missing a Property")).and(containsString("taxClass"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-newEClass.ecore", path = ECORE)
	public void missingDataContextElement() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("is missing a Data Context Element")).and(containsString("LoanContract"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-deletedProperty.ecore", path = ECORE)
	public void obsoleteDataContextProperty() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("source model property")).and(containsString("has been deleted"))
						.and(containsString("licenseNumber"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-deletedEClass.ecore", path = ECORE)
	public void obsoleteDataContextElement() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("source model class")).and(containsString("has been deleted"))
						.and(containsString("Vendor"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-ecore-enumPropertyWrongType.ctx", path = CONTEXT)
	public void enumPropertyWrongType() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("should have type Enumeration for consistency")).and(containsString("bookDispositions"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-ecore-stringPropertyWrongType.ctx", path = CONTEXT)
	public void stringPropertyWrongType() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("should have type String for consistency")).and(containsString("licenseNumber"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-ecore-unresolvedInstanceOf.ctx", path = CONTEXT)
	public void unresolvedInstanceOfConstraint() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("Unresolved EClass name 'Store'")).and(containsString("Ecore 'instance-of' constraint 'isSingleBookStore'"))))));
	}

}
