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
@FixMethodOrder(MethodSorters.JVM)
public class PropertiesContextModelBuilderUMLTest extends AbstractPapyrusTest {

	private static final String CONTEXT = "resources/BookStore.ctx"; //$NON-NLS-1$
	private static final String PROFILE = "resources/BookStore.profile.uml"; //$NON-NLS-1$

	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test that a context model with implicit dependencies only (generated before source annotations)
	 * does not report errors on missing data context elements.
	 */
	@Test
	@OverlayFile(value = "models/BookStore-implicitTracesOK.ctx", path = CONTEXT)
	public void implicitSourceTracesOK() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);

		assertThat(modelMarkers, not(hasItem(anything())));
	}

	@Test
	@OverlayFile(value = "models/BookStore-newProperty.profile.uml", path = PROFILE)
	public void missingDataContextProperty() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("is missing a Property")).and(containsString("taxClass"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-newStereotype.profile.uml", path = PROFILE)
	public void missingDataContextElement() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("is missing a Data Context Element")).and(containsString("LoanContract"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-deletedProperty.profile.uml", path = PROFILE)
	public void obsoleteDataContextProperty() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("source model property")).and(containsString("has been deleted"))
						.and(containsString("licenseNumber"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-deletedStereotype.profile.uml", path = PROFILE)
	public void obsoleteDataContextElement() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("source model class")).and(containsString("has been deleted"))
						.and(containsString("Vendor"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-enumPropertyWrongType.ctx", path = CONTEXT)
	public void enumPropertyWrongType() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("should have type Enumeration for consistency")).and(containsString("bookDispositions"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-stringPropertyWrongType.ctx", path = CONTEXT)
	public void stringPropertyWrongType() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("should have type String for consistency")).and(containsString("licenseNumber"))))));
	}

	/**
	 * Test that complex data types (not primitives) are treated as classes for the purposes of
	 * editing in the properties view.
	 */
	@Test
	@OverlayFile(value = "models/BookStore-withComplexDataType.ctx", path = CONTEXT)
	@OverlayFile(value = "models/BookStore-withComplexDataType.profile.uml", path = PROFILE)
	public void dataTypeAsClass() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("Address")).and(containsString("is missing a Property")).and(containsString("zipcode"))))));
	}

	@Test
	@OverlayFile(value = "ui/SingleVendor-wrongWidgetType.xwt", path = "resources/ui/SingleVendor.xwt")
	public void stringPropertyEditorWrongWidgetType() {
		// The problem is reported on the Context resource because that is where we edit the XWT content
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("is not consistent with its type")).and(containsString("IntegerEditor"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-unresolvedInstanceOf.ctx", path = CONTEXT)
	public void unresolvedInstanceOfConstraint() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("Unresolved UML metaclass name 'BogusMetaclass'")).and(containsString("UML 'instance-of' constraint 'isSingleBookStore'"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-unresolvedHasStereotype.ctx", path = CONTEXT)
	public void unresolvedHasStereotypeConstraint() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("Unresolved stereotype qualified name 'BookStore::Store'")).and(containsString("UML 'has-stereotype' constraint 'isSingleBookStore'"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-renamedProfile.profile.uml", path = PROFILE)
	public void unresolvedHasStereotypeConstraint_byRenamedProfile() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("Unresolved stereotype qualified name 'BookStore::BookStore'")).and(containsString("UML 'has-stereotype' constraint 'isSingleBookStore'"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-renamedProperty.profile.uml", path = PROFILE)
	public void renamedProperty() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("must be named 'license'")).and(containsString("licenseNumber"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-renamedStereotype.profile.uml", path = PROFILE)
	public void renamedStereotype() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				both(containsString("must be named 'Seller'")).and(containsString("Vendor"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-renamedProfile.profile.uml", path = PROFILE)
	public void renamedProfile() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_ERROR)).and(isMarkerMessage(
				containsString("must be named 'BookStoreProfile'")))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-newNestedProfile.profile.uml", path = PROFILE)
	public void missingDataContextPackage_nestedProfile() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("is missing a Data Context Package")).and(containsString("Legal"))))));

		// The new stereotype in the new profile is not reported as its newness follows from the profile
		assertThat(modelMarkers, not(hasItem(isMarkerMessage(containsString("is missing a Data Context Element")))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-newNestedPackage.profile.uml", path = PROFILE)
	public void missingDataContextPackage_nestedPackage() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("is missing a Data Context Package")).and(containsString("Legal"))))));

		// The new stereotype in the new nested package is not reported as its newness follows from the package
		assertThat(modelMarkers, not(hasItem(isMarkerMessage(containsString("is missing a Data Context Element")))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-profileMoved.ctx", path = CONTEXT)
	public void profileMoved() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("is not resolved. Perhaps it has been moved to another location?")).and(containsString("Data Context Root BookStore"))))));
	}

	@Test
	@OverlayFile(value = "models/BookStore-propertyWrongMultiplicity.ctx", path = CONTEXT)
	public void propertyWrongMultiplicity() {
		final List<IMarker> modelMarkers = fixture.getMarkers(CONTEXT);
		assertThat(modelMarkers, hasItem(both(isMarkerSeverity(IMarker.SEVERITY_WARNING)).and(isMarkerMessage(
				both(containsString("should have multiplicity -1 for consistency")).and(containsString("bookDispositions"))))));
	}

}
