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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeThat;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.eclipse.papyrus.eclipse.project.editors.interfaces.IFeatureProjectEditor;
import org.eclipse.papyrus.junit.framework.classification.ClassificationRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Test cases for the implementation of the {@link IFeatureProjectEditor} API.
 */
@RunWith(ClassificationRunner.class)
public class FeatureProjectEditorTest {

	@Rule
	public final ProjectEditorFixture<? extends IFeatureProjectEditor> fixture = new ProjectEditorFixture<>(IFeatureProjectEditor.class);

	@MissingFiles
	@Test
	public void getMissingFiles() {
		assertThat(fixture.getEditor().getMissingFiles(), hasItem("feature.xml"));
	}

	@CreatedProject(false)
	@Test
	public void getMissingNature() {
		assertThat(fixture.getEditor().getMissingNature(), hasItem("org.eclipse.pde.FeatureNature"));
		fixture.getEditor().create();
		fixture.getEditor().save();
		assertThat(fixture.getEditor().getMissingNature(), not(hasItem(anything())));

		// And it's in the file
		assertThat(fixture.slurp(".project"), hasItem(containsString("org.eclipse.pde.FeatureNature")));
	}

	@CreatedProject(false)
	@Test
	public void getMissingBuildCommand() {
		assertThat(fixture.getEditor().getMissingBuildCommand(), hasItem("org.eclipse.pde.FeatureBuilder"));
		fixture.getEditor().create();
		fixture.getEditor().save();
		assertThat(fixture.getEditor().getMissingBuildCommand(), not(hasItem(anything())));

		// And it's in the file
		assertThat(fixture.slurp(".project"), hasItem(containsString("org.eclipse.pde.FeatureBuilder")));
	}

	@MissingFiles
	@Test
	public void createFiles() {
		assumeThat(fixture.getProject().getFile("feature.xml").isAccessible(), is(false));
		fixture.getEditor().createFiles(Collections.singleton("feature.xml"));

		Element manifest = getManifest();
		assertThat(manifest.getAttributes().getLength(), is(0));
		assertThat(manifest.getTextContent().trim(), is(""));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void getId() {
		assertThat(fixture.getEditor().getId(), is("org.eclipse.papyrus.extra.umlrt.feature"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void getLabel() {
		assertThat(fixture.getEditor().getLabel(), is("Papyrus RT"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void getVersion() {
		assertThat(fixture.getEditor().getVersion(), is("1.2.0.qualifier"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void getProviderName() {
		assertThat(fixture.getEditor().getProviderName(), is("Papyrus Project"));
	}

	@WithResource("platform_feature/feature.xml")
	@Test
	public void getOS() {
		assertThat(fixture.getEditor().getOS(), is("macosx"));
	}

	@WithResource("platform_feature/feature.xml")
	@Test
	public void getWS() {
		assertThat(fixture.getEditor().getWS(), is("cocoa,carbon"));
	}

	@WithResource("platform_feature/feature.xml")
	@Test
	public void getNL() {
		assertThat(fixture.getEditor().getNL(), is("en_CA"));
	}

	@WithResource("platform_feature/feature.xml")
	@Test
	public void getArch() {
		assertThat(fixture.getEditor().getArch(), is("x86_64,ppc"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void getDescriptionText() {
		assertThat(fixture.getEditor().getDescriptionText(), containsString("UML for Real-Time"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void getDescriptionURL() {
		assertThat(fixture.getEditor().getDescriptionURL(), is("http://localhost/description.html"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void getCopyrightText() {
		assertThat(fixture.getEditor().getCopyrightText(), containsString("2013 CEA LIST"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void getCopyrightURL() {
		assertThat(fixture.getEditor().getCopyrightURL(), is("http://www.eclipse.org/legal/epl-2.0"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void getLicenseText() {
		assertThat(fixture.getEditor().getLicenseText(), containsString("%license"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void getLicenseURL() {
		assertThat(fixture.getEditor().getLicenseURL(), is("http://localhost/license.html"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setId() {
		testRootAttribute("id", IFeatureProjectEditor::setId, "org.eclipse.foo");
	}

	void testRootAttribute(String name, BiConsumer<IFeatureProjectEditor, String> setter, String value) {
		setter.accept(fixture.getEditor(), value);
		fixture.getEditor().save();

		assertThat(getManifest().getAttribute(name), is(value));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setLabel() {
		testRootAttribute("label", IFeatureProjectEditor::setLabel, "Foo Feature");
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setVersion() {
		testRootAttribute("version", IFeatureProjectEditor::setVersion, "0.1.0.qualifier");
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setProviderName() {
		testRootAttribute("provider-name", IFeatureProjectEditor::setProviderName, "Me, myself");
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setOS() {
		testRootAttribute("os", IFeatureProjectEditor::setOS, "macosx");
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setWS() {
		testRootAttribute("ws", IFeatureProjectEditor::setWS, "cocoa");
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setNL() {
		testRootAttribute("nl", IFeatureProjectEditor::setNL, "en_CA");
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setArch() {
		testRootAttribute("arch", IFeatureProjectEditor::setArch, "x86_64");
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setDescription() {
		testDescriptionoid("description", IFeatureProjectEditor::setDescription,
				"http:///newdescription.html", "New description");
	}

	void testDescriptionoid(String name, TriConsumer<IFeatureProjectEditor, String, String> setter, String url, String text) {
		setter.accept(fixture.getEditor(), url, text);
		fixture.getEditor().save();

		Element description = findElement(getManifest(), name).get();
		assertThat(description.getAttribute("url"), is(url));
		assertThat(description.getTextContent(), is(text));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setCopyright() {
		testDescriptionoid("copyright", IFeatureProjectEditor::setCopyright,
				"http:///newcopy.html", "Copyright (c) 2016 Me");
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setLicense() {
		testDescriptionoid("license", IFeatureProjectEditor::setLicense,
				"http:///newlic.html", "Free for all!");
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void setUpdateURL() {
		fixture.getEditor().setUpdateURL("Get stuff here", "http:///update");
		fixture.getEditor().save();

		Element url = findElement(getManifest(), "url").get();
		Element discovery = findElement(url, "update").get();

		assertThat(discovery.getAttribute("label"), is("Get stuff here"));
		assertThat(discovery.getAttribute("url"), is("http:///update"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void addPlugin() {
		fixture.getEditor().addPlugin("org.eclipse.foo");
		fixture.getEditor().save();

		Element plugin = oneElement(getManifest(), "plugin", "id", "org.eclipse.foo");
		assertThat(plugin.getAttribute("version"), is("0.0.0"));
		assertThat(plugin.getAttribute("download-size"), is("0"));
		assertThat(plugin.getAttribute("install-size"), is("0"));
		assertThat(plugin.getAttribute("unpack"), is("false"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void addRequiredFeature() {
		fixture.getEditor().addRequiredFeature("org.eclipse.foo.feature", "0.7.0");
		fixture.getEditor().save();

		Element requires = findElement(getManifest(), "requires").get();
		Element feature = oneElement(requires, "import", "feature", "org.eclipse.foo.feature");
		assertThat(feature.getAttribute("version"), is("0.7.0"));
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void addRequiredPlugin() {
		fixture.getEditor().addRequiredPlugin("org.eclipse.foo");
		fixture.getEditor().save();

		Element requires = findElement(getManifest(), "requires").get();
		oneElement(requires, "import", "plugin", "org.eclipse.foo");
	}

	@WithResource("feature_project/feature.xml")
	@Test
	public void addInclude() {
		fixture.getEditor().addInclude("org.eclipse.foo.feature", "0.7.0");
		fixture.getEditor().addInclude("org.eclipse.bar.feature", null);
		fixture.getEditor().save();

		Element feature = oneElement(getManifest(), "includes", "id", "org.eclipse.bar.feature");
		assertThat(feature.getAttribute("version"), is("0.0.0"));
	}

	//
	// Test framework
	//

	Element getManifest() {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			return builder.parse(fixture.getURI("feature.xml").toString()).getDocumentElement();
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to parse feature.xml: " + e.getMessage());
			return null; // unreachable
		}
	}

	Optional<Element> findElement(Element root, String name) {
		return allOf(root)
				.filter(element -> element.getNodeName().equals(name))
				.findFirst();
	}

	/**
	 * Obtains a stream over the depth-first element tree of the given {@code root}.
	 *
	 * @param root
	 *            the root of a tree to traverse
	 *
	 * @return the elements of {@code root}, starting with it
	 */
	Stream<Element> allOf(Element root) {
		return Stream.concat(
				Stream.of(root),
				childrenOf(root).flatMap(this::allOf));
	}

	Stream<Element> childrenOf(Element element) {
		NodeList children = element.getChildNodes();
		return IntStream.range(0, children.getLength())
				.mapToObj(children::item)
				.filter(Element.class::isInstance)
				.map(Element.class::cast);
	}

	Element oneElement(Element root, String name, String attribute, String value) {
		int[] count = { 0 };
		return allOf(root)
				.filter(e -> e.getNodeName().equals(name))
				.filter(e -> Objects.equals(e.getAttribute(attribute), value))
				.peek(e -> assertThat("not exactly one " + name, ++count[0], is(1)))
				.collect(Collectors.toList())
				.get(0);
	}
}
