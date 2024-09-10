/*****************************************************************************
 * Copyright (c) 2016 Christian W. Damus and others.
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

package org.eclipse.papyrus.eclipse.project.editors.tests;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginProjectEditor;
import org.junit.Rule;
import org.junit.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Test cases for the implementation of the {@link IPluginProjectEditor} API.
 */
@CreatedProject
public class PluginProjectEditorTest {

	@Rule
	public final ProjectEditorFixture<? extends IPluginProjectEditor> fixture;

	public PluginProjectEditorTest() {
		this(new ProjectEditorFixture<>(IPluginProjectEditor.class));
	}

	PluginProjectEditorTest(ProjectEditorFixture<? extends IPluginProjectEditor> fixture) {
		super();

		this.fixture = fixture;
	}

	@CreatedProject(false)
	@Test
	public void exists() {
		assertThat(fixture.getEditor().exists(), is(false));
		fixture.getEditor().create();
		fixture.getEditor().save();

		assertThat(fixture.getEditor().exists(), is(true));

		assertThat(fixture.slurp("plugin.xml"), hasItem(containsString("<plugin>")));
	}

	@Test
	public void addExtension() {
		fixture.getEditor().addExtension("org.eclipse.foo");
		fixture.getEditor().save();

		assertThat(fixture.slurp("plugin.xml"), hasItem(containsString("point=\"org.eclipse.foo\"")));
	}

	@Test
	public void getExtensions() {
		fixture.getEditor().addExtension("org.eclipse.foo");
		fixture.getEditor().save();

		List<Node> extensions = fixture.getEditor().getExtensions("org.eclipse.foo");
		Element extension = extensions.stream()
				.filter(Element.class::isInstance)
				.map(Element.class::cast)
				.findAny().orElse(null);
		assertThat(extension, notNullValue());
		assertThat(extension.getNodeName(), is("extension"));
		assertThat(extension.getAttribute("point"), is("org.eclipse.foo"));
	}

	@Test
	public void setAttribute() {
		Element extension = fixture.getEditor().addExtension("org.eclipse.foo");
		fixture.getEditor().setAttribute(extension, "id", "org.eclipse.papyrus.foo.test");
		fixture.getEditor().save();

		assertThat(fixture.slurp("plugin.xml"), hasItem(containsString("id=\"org.eclipse.papyrus.foo.test\"")));
	}

	@MissingFiles
	@Test
	public void getMissingFiles() {
		assertThat(fixture.getEditor().getMissingFiles(), hasItem("plugin.xml"));
	}

	@CreatedProject(false)
	@Test
	public void getMissingNature() {
		assertThat(fixture.getEditor().getMissingNature(), hasItem(IPluginProjectEditor.PLUGIN_NATURE));
		fixture.getEditor().create();
		fixture.getEditor().save();
		assertThat(fixture.getEditor().getMissingNature(), not(hasItem(anything())));

		// And it's in the file
		assertThat(fixture.slurp(".project"), hasItem(containsString(IPluginProjectEditor.PLUGIN_NATURE)));
	}

	@CreatedProject(false)
	@Test
	public void getMissingBuildCommand() {
		assertThat(fixture.getEditor().getMissingBuildCommand(), hasItem(IPluginProjectEditor.PLUGIN_BUILD_COMMAND));
		fixture.getEditor().create();
		fixture.getEditor().save();
		assertThat(fixture.getEditor().getMissingBuildCommand(), not(hasItem(anything())));

		// And it's in the file
		assertThat(fixture.slurp(".project"), hasItem(containsString(IPluginProjectEditor.PLUGIN_BUILD_COMMAND)));
	}

}
