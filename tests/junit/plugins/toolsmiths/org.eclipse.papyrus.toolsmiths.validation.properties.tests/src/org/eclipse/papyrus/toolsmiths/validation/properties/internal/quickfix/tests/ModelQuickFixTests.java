/*****************************************************************************
 * Copyright (c) 2021 Christian W. Damus, CEA LIST, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus - Initial API and implementation
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.properties.internal.quickfix.tests;

import static org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants.PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE;

import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.MarkerType;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.OverlayFile;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.QuickFix;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.QuickFixWith;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.constants.PropertiesPluginValidationConstants;
import org.eclipse.papyrus.toolsmiths.validation.properties.internal.quickfix.PropertiesMarkerResolutionGenerator;
import org.junit.Rule;
import org.junit.Test;

/**
 * Specific tests for the <em>Properties Context Model</em> quick fixes.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.properties.example")
@MarkerType(PROPERTIES_PLUGIN_VALIDATION_MARKER_TYPE)
@QuickFixWith(PropertiesMarkerResolutionGenerator.class)
@Build
public class ModelQuickFixTests {

	private static final String CONTEXT = "resources/BookStore.ctx"; //$NON-NLS-1$
	private static final String PROFILE = "resources/BookStore.profile.uml"; //$NON-NLS-1$

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	public ModelQuickFixTests() {
		super();
	}

	@Test
	@OverlayFile(value = "models/BookStore-newProperty.profile.uml", path = PROFILE)
	@QuickFix(PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_PROPERTY)
	public void missingDataContextProperty() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-newStereotype.profile.uml", path = PROFILE)
	@QuickFix(PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_ELEMENT)
	public void missingDataContextElement() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-deletedProperty.profile.uml", path = PROFILE)
	@QuickFix(PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_PROPERTY)
	public void obsoleteDataContextProperty() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-deletedStereotype.profile.uml", path = PROFILE)
	@QuickFix(PropertiesPluginValidationConstants.OBSOLETE_DATA_CONTEXT_ELEMENT)
	public void obsoleteDataContextElement() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-enumPropertyWrongType.ctx", path = CONTEXT)
	@QuickFix(PropertiesPluginValidationConstants.INCONSISTENT_DATA_CONTEXT_PROPERTY_TYPE)
	public void enumPropertyWrongType() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-stringPropertyWrongType.ctx", path = CONTEXT)
	@QuickFix(PropertiesPluginValidationConstants.INCONSISTENT_DATA_CONTEXT_PROPERTY_TYPE)
	public void stringPropertyWrongType() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "ui/SingleVendor-wrongWidgetType.xwt", path = "resources/ui/SingleVendor.xwt")
	@QuickFix(PropertiesPluginValidationConstants.INCONSISTENT_WIDGET_TYPE)
	public void stringPropertyEditorWrongWidgetType() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-renamedProperty.profile.uml", path = PROFILE)
	@QuickFix(PropertiesPluginValidationConstants.RENAMED_PROPERTY)
	public void renamedProperty() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-renamedStereotype.profile.uml", path = PROFILE)
	@QuickFix(PropertiesPluginValidationConstants.RENAMED_CLASS)
	public void renamedStereotype() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-renamedProfile.profile.uml", path = PROFILE)
	@QuickFix(PropertiesPluginValidationConstants.RENAMED_PACKAGE)
	public void renamedProfile() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-newNestedProfile.profile.uml", path = PROFILE)
	@QuickFix(PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_PACKAGE)
	public void missingDataContextPackage_nestedProfile() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-newNestedPackage.profile.uml", path = PROFILE)
	@QuickFix(PropertiesPluginValidationConstants.MISSING_DATA_CONTEXT_PACKAGE)
	public void missingDataContextPackage_nestedPackage() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-unresolvedInstanceOf.ctx", path = CONTEXT)
	@QuickFix(PropertiesPluginValidationConstants.UNRESOLVED_CONSTRAINT_CLASS)
	public void unresolvedConstraintClass_instanceOf() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-unresolvedHasStereotype.ctx", path = CONTEXT)
	@QuickFix(PropertiesPluginValidationConstants.UNRESOLVED_CONSTRAINT_CLASS)
	public void unresolvedConstraintClass_hasStereotype() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-renamedProfile.profile.uml", path = PROFILE)
	@QuickFix(PropertiesPluginValidationConstants.UNRESOLVED_CONSTRAINT_CLASS)
	public void unresolvedConstraintClass_hasStereotype_byRenamedProfile() {
		// Automatic test
	}

	@Test
	@OverlayFile(value = "models/BookStore-propertyWrongMultiplicity.ctx", path = CONTEXT)
	@QuickFix(PropertiesPluginValidationConstants.INCONSISTENT_DATA_CONTEXT_PROPERTY_MULTIPLICITY)
	public void propertyWrongMultiplicity() {
		// Automatic test
	}

}
