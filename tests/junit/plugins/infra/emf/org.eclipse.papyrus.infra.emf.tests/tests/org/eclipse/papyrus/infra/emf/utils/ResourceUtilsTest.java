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

package org.eclipse.papyrus.infra.emf.utils;

import static org.eclipse.papyrus.junit.utils.rules.ProjectFixture.replaceTokens;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.papyrus.junit.utils.rules.ProjectFixture;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;

import junit.framework.AssertionFailedError;

/**
 * JUnit tests for the {@link ResourceUtils} class.
 */
public class ResourceUtilsTest {

	@Rule
	public final ProjectFixture project = new ProjectFixture();

	public ResourceUtilsTest() {
		super();
	}

	@Test
	public void createWorkspaceAwareURIConverter() {
		createPluginXML("resources/bug572865/plugin-uriMappings.xml");

		URIConverter converter = ResourceUtils.createWorkspaceAwareURIConverter();
		assertThat(converter.normalize(URI.createURI("pathmap://BUG_572865/whatever.ecore")),
				is(createProjectResourceURI("models/whatever.ecore")));
	}

	@Test
	public void createWorkspaceAwareURIConverter_badMapping() {
		createPluginXML("resources/bug572865/plugin-uriMappings-bad.xml");

		// There should be no NPE
		ResourceUtils.createWorkspaceAwareURIConverter();
	}

	@Test
	public void createWorkspaceAwarePackageRegistry_static() {
		createPluginXML("resources/bug572865/plugin-staticPackage.xml");

		EPackage.Registry registry = ResourceUtils.createWorkspaceAwarePackageRegistry();
		EPackage ePackage = registry.getEPackage("bogus://bug572865");
		assertThat(ePackage, notNullValue());
		assertThat(ePackage.getNsURI(), is(EcorePackage.eNS_URI)); // This comes from the actual package definition
	}

	@Test
	public void createWorkspaceAwarePackageRegistry_dynamic() {
		createPluginXML("resources/bug572865/plugin-dynamicPackage.xml");

		try {
			project.createFile("models/bug572865.ecore", "resources/bug572865/bug572865.ecore");
		} catch (IOException e) {
			e.printStackTrace();
			throw new AssertionFailedError("Failed to create test model: " + e.getMessage());
		}

		EPackage.Registry registry = ResourceUtils.createWorkspaceAwarePackageRegistry();
		EPackage ePackage = registry.getEPackage("bogus://bug572865");
		assertThat(ePackage, notNullValue());
		assertThat(ePackage.getName(), is("bug572865"));
	}

	@Test
	public void createWorkspaceAwarePackageRegistry_static_badRegistration() {
		createPluginXML("resources/bug572865/plugin-staticPackage-bad.xml");

		// There should be no NPE
		ResourceUtils.createWorkspaceAwarePackageRegistry();
	}

	@Test
	public void createWorkspaceAwarePackageRegistry_dynamic_badRegistration() {
		createPluginXML("resources/bug572865/plugin-dynamicPackage-bad.xml");

		// There should be no NPE
		ResourceUtils.createWorkspaceAwarePackageRegistry();
	}

	//
	// Test framework
	//

	@Before
	public void configureProject() throws CoreException, IOException {
		IProject project = this.project.getProject();
		IProjectDescription desc = project.getDescription();
		desc.setNatureIds(new String[] {
				"org.eclipse.pde.PluginNature",
				"org.eclipse.jdt.core.javanature",
		});
		project.setDescription(desc, null);

		IFolder meta = project.getFolder("META-INF");
		meta.create(false, true, null);

		String content = "Manifest-Version: 1.0\n"
				+ "Bundle-Version: 1.0.0.qualifier\n"
				+ "Bundle-SymbolicName: " + project.getName() + ";singleton:=true\n";

		try (InputStream input = CharSource.wrap(content).asByteSource(Charsets.UTF_8).openStream()) {
			meta.getFile("MANIFEST.MF").create(input, false, null);
		}
	}

	void createPluginXML(String path) {
		try {
			project.createFile("plugin.xml", path, replaceTokens("PROJECT", project.getProject().getName()));
		} catch (IOException e) {
			e.printStackTrace();

			throw new AssertionFailedError("Failed to create plugin.xml file: " + e.getMessage());
		}
	}

	URI createProjectResourceURI(String projectRelativePath) {
		return URI.createURI(String.format("platform:/resource/%s/%s", project.getProject().getName(), projectRelativePath), true);
	}

}
