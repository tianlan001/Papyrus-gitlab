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

package org.eclipse.papyrus.toolsmiths.validation.common.tests;

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.regexContains;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CommonProblemConstants;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.AbstractMissingAttributeMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.OverlayFile;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.junit.Rule;
import org.junit.Test;

/**
 * Specific test cases for the {@link AbstractMissingAttributeMarkerResolution} class.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.common.example")
@Build(false)
public class AbstractMissingAttributeMarkerResolutionTest {

	@SuppressWarnings("restriction")
	private static final String ATTR_LOCATION_PATH = org.eclipse.pde.internal.core.builders.PDEMarkerFactory.MPK_LOCATION_PATH;
	private static final String ATTR_NAME = "attrName";
	private static final String ATTR_VALUE = "attrValue";
	private static final String NAME = "name";

	@Rule
	public final TestProjectFixture project = new TestProjectFixture();

	/**
	 * Verify that the marker resolution generates the expected attribute content in the <tt>plugin.xml</tt>.
	 */
	@Test
	@OverlayFile(value = "bug572677/plugin.xml", path = "plugin.xml")
	public void addMissingAttribute() {
		IFile pluginXML = project.getFile("plugin.xml");

		IMarker marker = project.mockMarker(pluginXML, Map.of(ATTR_NAME, NAME,
				ATTR_VALUE, "MyTest",
				ATTR_LOCATION_PATH, "(0)plugin>(0)extension>(0)profile@name"));

		AbstractMissingAttributeMarkerResolution fix = new TestMarkerResolution();
		fix.run(marker);

		String fileContent = project.getContent(pluginXML);
		assertThat(fileContent, regexContains(
				"(?ms)<extension\\s+" +
						"         point=\"org.eclipse.papyrus.uml.extensionpoints.UMLProfile\">\\s+" +
						"      <profile\\s+" +
						"            name=\"MyTest\"\\s+" +
						"            path=\"resources/MyTest.profile.uml\">\\s+" +
						"      </profile>\\s+" +
						"   </extension>"));
	}

	//
	// Test framework
	//

	private static final class TestMarkerResolution extends AbstractMissingAttributeMarkerResolution {

		TestMarkerResolution() {
			super(CommonProblemConstants.MAX_PROBLEM_ID, NAME);
		}

		@Override
		protected String getAttributeValue(IMarker marker) throws CoreException {
			return marker.getAttribute(ATTR_VALUE, "");
		}

		@Override
		public String getDescription() {
			return "This is a quick fix in a test.";
		}

		@Override
		public String getLabel() {
			return "Test Fix";
		}

	}

}
