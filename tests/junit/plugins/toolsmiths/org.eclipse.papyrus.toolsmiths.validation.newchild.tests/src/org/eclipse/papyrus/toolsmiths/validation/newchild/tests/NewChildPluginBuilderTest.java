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
package org.eclipse.papyrus.toolsmiths.validation.newchild.tests;

import static org.eclipse.papyrus.toolsmiths.validation.newchild.internal.NewChildMenuPluginValidationConstants.NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.eclipse.core.resources.IMarker;
import org.eclipse.papyrus.junit.framework.classification.tests.AbstractPapyrusTest;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.MarkerType;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.junit.Rule;
import org.junit.Test;

/**
 * General test cases for the <em>Element Creation Menu Model</em> project builder.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.newchild.example")
@MarkerType(NEWCHILD_PLUGIN_VALIDATION_MARKER_TYPE)
@Build
public class NewChildPluginBuilderTest extends AbstractPapyrusTest {

	/**
	 * The project fixture to manage easily the project.
	 */
	@Rule
	public final TestProjectFixture fixture = new TestProjectFixture();

	/**
	 * Test the reporting of a project that passes all checks.
	 */
	@Test
	public void ok() {
		final List<IMarker> modelMarkers = fixture.getMarkers("resources/BookStore.creationmenumodel"); //$NON-NLS-1$
		assertThat(modelMarkers, not(hasItem(anything())));
	}

}
