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

package org.eclipse.papyrus.toolsmiths.validation.architecture.internal.quickfix.tests;

import static org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants.ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE;

import org.eclipse.papyrus.toolsmiths.validation.architecture.constants.ArchitecturePluginValidationConstants;
import org.eclipse.papyrus.toolsmiths.validation.architecture.internal.quickfix.ArchitectureMarkerResolutionGenerator;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.MarkerType;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.OverlayFile;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.QuickFix;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.QuickFixWith;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.junit.Rule;
import org.junit.Test;

/**
 * Specific tests for the <em>Architecture Model</em> quick fixes.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.architecture.example")
@MarkerType(ARCHITECTURE_PLUGIN_VALIDATION_MARKER_TYPE)
@QuickFixWith(ArchitectureMarkerResolutionGenerator.class)
@Build
public class ModelQuickFixTests {

	private static final String MODEL_PATH = "resources/BookStore.architecture"; //$NON-NLS-1$

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	public ModelQuickFixTests() {
		super();
	}

	@OverlayFile(value = "bug573788-models/BookStore-noRepresentationsAdvice.architecture", path = MODEL_PATH)
	@QuickFix(ArchitecturePluginValidationConstants.MISSING_REPRESENTATIONS_ADVICE_ID)
	@Test
	public void representationsAdviceFix() {
		// Automatic test
	}

}
