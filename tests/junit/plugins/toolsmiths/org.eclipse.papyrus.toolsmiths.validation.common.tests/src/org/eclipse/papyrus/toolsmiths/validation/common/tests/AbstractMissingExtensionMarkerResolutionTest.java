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
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.papyrus.toolsmiths.validation.common.checkers.CommonProblemConstants;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.AbstractMissingExtensionMarkerResolution;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.Build;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProject;
import org.eclipse.papyrus.toolsmiths.validation.common.tests.rules.TestProjectFixture;
import org.eclipse.pde.core.plugin.IPluginElement;
import org.eclipse.pde.core.plugin.IPluginExtension;
import org.junit.Rule;
import org.junit.Test;

/**
 * Specific test cases for the {@link AbstractMissingExtensionMarkerResolution} class.
 */
@TestProject("org.eclipse.papyrus.toolsmiths.validation.common.example")
@Build(false)
public class AbstractMissingExtensionMarkerResolutionTest {

	private static final String ATTR_EXT_PT = "extensionPoint";
	private static final String ATTR_ELEMENT_NAME = "elementName";
	private static final String ATTR_ATTRIBUTE_NAME_PREFIX = "attrName";
	private static final String ATTR_ATTRIBUTE_VALUE_PREFIX = "attrValue";

	@Rule
	public final TestProjectFixture project = new TestProjectFixture();

	/**
	 * Verify that the marker resolution generates the expected extension content in the <tt>plugin.xml</tt>.
	 */
	@Test
	public void addMissingExtension() {
		IFile pluginXML = project.getFile("plugin.xml");

		IMarker marker = project.mockMarker(pluginXML, Map.of(ATTR_EXT_PT, "my.extension.for.testing",
				ATTR_ELEMENT_NAME, "thing",
				ATTR_ATTRIBUTE_NAME_PREFIX + 1, "color",
				ATTR_ATTRIBUTE_VALUE_PREFIX + 1, "green"));

		AbstractMissingExtensionMarkerResolution fix = new TestMarkerResolution();
		fix.run(marker);

		String fileContent = project.getContent(pluginXML);
		assertThat(fileContent, regexContains(
				"(?ms)<extension\\s+point=\"my.extension.for.testing\">\\s+" +
						"<thing\\s+color=\"green\">\\s+" +
						"</thing>\\s+" +
						"</extension>"));
	}

	/**
	 * Verify that the marker resolution does not generate all of the intermediate states of the
	 * configuration of a new extension in the <tt>plugin.xml</tt>.
	 *
	 * @see <a href="https://eclip.se/572677">bug 572677<a>
	 */
	@Test
	public void noIntermediateExtensionPointConfigurations() {
		IFile pluginXML = project.getFile("plugin.xml");

		IMarker marker = project.mockMarker(pluginXML, Map.of(ATTR_EXT_PT, "my.extension.for.testing",
				ATTR_ELEMENT_NAME, "thing",
				ATTR_ATTRIBUTE_NAME_PREFIX + 1, "color",
				ATTR_ATTRIBUTE_VALUE_PREFIX + 1, "green"));

		AbstractMissingExtensionMarkerResolution fix = new TestMarkerResolution();
		fix.run(marker);

		String fileContent = project.getContent(pluginXML);
		assertThat(fileContent, not(containsString("<thing></thing>")));
	}

	//
	// Test framework
	//

	private static final class TestMarkerResolution extends AbstractMissingExtensionMarkerResolution {

		TestMarkerResolution() {
			super(CommonProblemConstants.MAX_PROBLEM_ID);
		}

		@Override
		protected String getExtensionPoint(IMarker marker) throws CoreException {
			return marker.getAttribute(ATTR_EXT_PT, "");
		}

		@Override
		protected void configureExtension(IPluginExtension extension, IMarker marker) throws CoreException {
			String elementName = marker.getAttribute(ATTR_ELEMENT_NAME, "");

			IPluginElement element = createElement(extension, elementName);

			for (int i = 1;; i = i + 1) {
				String attrName = marker.getAttribute(ATTR_ATTRIBUTE_NAME_PREFIX + i, "");
				if (attrName.isBlank()) {
					break;
				}
				String attrValue = marker.getAttribute(ATTR_ATTRIBUTE_VALUE_PREFIX + i, "");
				setAttribute(element, attrName, attrValue);
			}
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
