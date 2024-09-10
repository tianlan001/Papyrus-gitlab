/*****************************************************************************
 * Copyright (c) 2016, 2021 Christian W. Damus, CEA LIST, and others.
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

import static org.eclipse.papyrus.junit.matchers.MoreMatchers.regexContains;
import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.osgi.service.resolver.VersionRange;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;
import org.eclipse.pde.core.project.IPackageImportDescription;
import org.eclipse.pde.core.project.IRequiredBundleDescription;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test cases for the implementation of the {@link IManifestEditor} API.
 */
@CreatedProject
public class ManifestEditorTest {

	@Rule
	public final ProjectEditorFixture<? extends IManifestEditor> fixture;

	public ManifestEditorTest() {
		this(new ProjectEditorFixture<>(IManifestEditor.class));
	}

	ManifestEditorTest(ProjectEditorFixture<? extends IManifestEditor> fixture) {
		super();

		this.fixture = fixture;
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void addDependencyString() {
		// Add a dependency that wasn't there before
		fixture.getEditor().addDependency("org.eclipse.jface");

		// And try to add one that was there before
		fixture.getEditor().addDependency("org.eclipse.ui");

		fixture.getEditor().save();

		List<String> manifest = getManifest();

		// We have JFace without a version
		assertThat(one(manifest, "org.eclipse.jface"), not(containsString(";")));

		// And still just the one org.eclipse.ui with its version
		assertThat(one(manifest, "org.eclipse.ui"), containsString(";bundle-version=\"3.6.0\""));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void addDependencyStringString() {
		// Add a dependency that wasn't there before
		fixture.getEditor().addDependency("org.eclipse.jface", "3.10.0");

		// And set the version of an existing dependency
		fixture.getEditor().addDependency("org.eclipse.core.resources", "3.6.0");

		// And change the version of an existing dependency
		fixture.getEditor().addDependency("org.eclipse.ui", "[3.2.0,4.0.0)");

		fixture.getEditor().save();

		List<String> manifest = getManifest();

		// We have JFace with a version
		assertThat(one(manifest, "org.eclipse.jface"), containsString(";bundle-version=\"3.10.0\""));

		// And one Resources with a new version and the same other directives
		assertThat(one(manifest, "org.eclipse.core.resources"),
				both(containsString(";bundle-version=\"3.6.0\"")).and(
						containsString(";visibility:=reexport")));

		// And one UI with a new range (not an appended range)
		assertThat(one(manifest, "org.eclipse.ui"),
				both(containsString(";bundle-version=\"[3.2.0,4.0.0)\"")).and(
						not(containsString(";bundle-version=\"3.6.0\""))));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void hasDependency() {
		assertThat(fixture.getEditor().hasDependency("org.eclipse.jface"), is(false));
		assertThat(fixture.getEditor().hasDependency("org.eclipse.ui"), is(true));
		assertThat(fixture.getEditor().hasDependency("org.eclipse.core.resources"), is(true));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void setValueStringString() {
		// One that doesn't yet exist
		fixture.getEditor().setValue("Eclipse-LazyStart", "true");
		// And one that does
		fixture.getEditor().setValue("Bundle-Localization", "OSGI-INF/l10n/bundle");

		fixture.getEditor().save();

		List<String> manifest = getManifest();
		assertThat(one(manifest, "Eclipse-LazyStart"), is("Eclipse-LazyStart: true"));
		assertThat(one(manifest, "Bundle-Localization"), is("Bundle-Localization: OSGI-INF/l10n/bundle"));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void getValueString() {
		assertThat(fixture.getEditor().getValue("Bundle-Vendor"), is("Papyrus Project"));
		assertThat(fixture.getEditor().getValue("Eclipse-LazyStart"), nullValue());
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void getValueStringString() {
		assertThat(fixture.getEditor().getValue("author-info", "Full-Name"), is("Christian W. Damus"));
		assertThat(fixture.getEditor().getValue("author-info", "Disposition"), nullValue());
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void setValueStringStringString() {
		// One that doesn't yet exist
		fixture.getEditor().setValue("author-info", "Disposition", "happy");
		// And one that does
		fixture.getEditor().setValue("author-info", "Company", "self-employed");

		fixture.getEditor().save();

		List<String> manifest = getManifest();
		assertThat(one(manifest, "Disposition"), is("Disposition: happy"));
		assertThat(one(manifest, "Company"), is("Company: self-employed"));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void removeValueStringString() {
		// Attempt to remove one that doesn't exist
		fixture.getEditor().removeValue("author-info", "Disposition");
		// And one that does
		fixture.getEditor().removeValue("author-info", "Company");

		fixture.getEditor().save();

		assertThat(getManifest(), not(hasItem(containsString("Company:"))));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void removeValueString() {
		// Attempt to remove one that doesn't exist
		fixture.getEditor().removeValue("Eclipse-LazyStart");
		// And one that does
		fixture.getEditor().removeValue("Bundle-Localization");

		fixture.getEditor().save();

		assertThat(getManifest(), not(hasItem(containsString("Bundle-Localization:"))));
	}

	@Test
	public void setSymbolicBundleName() {
		fixture.getEditor().setSymbolicBundleName("org.eclipse.papyrus.something");
		fixture.getEditor().save();

		assertThat(one(getManifest(), "Bundle-SymbolicName:"), regexContains("\\sorg\\.eclipse\\.papyrus\\.something$"));

		// And change the name
		fixture.getEditor().setSymbolicBundleName("org.eclipse.papyrus.new.name");
		fixture.getEditor().save();

		assertThat(one(getManifest(), "Bundle-SymbolicName:"), regexContains("\\sorg\\.eclipse\\.papyrus\\.new\\.name$"));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void getSymbolicBundleName() {
		assertThat(fixture.getEditor().getSymbolicBundleName(), is("org.eclipse.papyrus.foo"));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void getBundleName() {
		assertThat(fixture.getEditor().getBundleName(), is("Papyrus Test Bundle"));
	}

	@Test
	public void setBundleName() {
		fixture.getEditor().setBundleName("My Bundle");
		fixture.getEditor().save();

		assertThat(one(getManifest(), "Bundle-Name:"), regexContains("\\sMy Bundle$"));

		// And change the name
		fixture.getEditor().setBundleName("A Better Name");
		fixture.getEditor().save();

		assertThat(one(getManifest(), "Bundle-Name:"), regexContains("\\sA Better Name$"));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void getBundleVersion() {
		assertThat(fixture.getEditor().getBundleVersion(), is("2.0.0.qualifier"));
	}

	@Test
	public void setBundleVersion() {
		fixture.getEditor().setBundleVersion("0.1.0.qualifier");
		fixture.getEditor().save();

		assertThat(one(getManifest(), "Bundle-Version:"), regexContains("\\s0\\.1\\.0\\.qualifier$"));

		// And change the name
		fixture.getEditor().setBundleVersion("2.0.0");
		fixture.getEditor().save();

		assertThat(one(getManifest(), "Bundle-Version:"), regexContains("\\s2\\.0\\.0$"));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void getBundleVendor() {
		assertThat(fixture.getEditor().getBundleVendor(), is("Papyrus Project"));
	}

	@Test
	public void setBundleVendor() {
		fixture.getEditor().setBundleVendor("Me");
		fixture.getEditor().save();

		assertThat(one(getManifest(), "Bundle-Vendor:"), regexContains("\\sMe$"));

		// And change the name
		fixture.getEditor().setBundleVendor("Eclipse Modeling Project");
		fixture.getEditor().save();

		assertThat(one(getManifest(), "Bundle-Vendor:"), regexContains("\\sEclipse Modeling Project$"));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void getBundleLocalization() {
		assertThat(fixture.getEditor().getBundleLocalization(), is("plugin"));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void setSingleton() {
		fixture.getEditor().setSingleton(true);
		fixture.getEditor().save();

		assertThat(one(getManifest(), "Bundle-SymbolicName:"), regexContains("\\.foo;singleton:=true$"));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void addImportPackageString() {
		// Add a dependency that wasn't there before
		fixture.getEditor().addImportPackage("org.eclipse.jface.viewers");

		// And try to add one that was there before
		fixture.getEditor().addImportPackage("org.eclipse.emf.ecore");

		fixture.getEditor().save();

		List<String> manifest = getManifest();

		// We have viewers without a version
		assertThat(one(manifest, "org.eclipse.jface.viewers"), not(containsString(";")));

		// And still just the one one Ecore with its version
		assertThat(one(manifest, "org.eclipse.emf.ecore"), containsString(";version=\"2.10.0\""));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void addImportPackageStringString() {
		// Add a dependency that wasn't there before
		fixture.getEditor().addImportPackage("org.eclipse.jface.viewers", "3.10.0");

		// And set the version of an existing dependency
		fixture.getEditor().addImportPackage("com.ibm.icu.text", "54.0.0");

		// And change the version of an existing dependency
		fixture.getEditor().addImportPackage("org.eclipse.emf.ecore", "[2.12.0,3.0.0)");

		fixture.getEditor().save();

		List<String> manifest = getManifest();

		// We have viewers with a version
		assertThat(one(manifest, "org.eclipse.jface.viewers"), containsString(";version=\"3.10.0\""));

		// And one ICU with a new version and the same other directives
		assertThat(one(manifest, "com.ibm.icu.text"),
				both(containsString(";version=\"54.0.0\"")).and(
						containsString(";resolution:=optional")));

		// And one Ecore with a new range (not an appended range)
		assertThat(one(manifest, "org.eclipse.emf.ecore"),
				both(containsString(";version=\"[2.12.0,3.0.0)\"")).and(
						not(containsString(";version=\"2.10.0\""))));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void addExportPackageString() {
		// Add a package that wasn't there before
		fixture.getEditor().addExportPackage("org.eclipse.papyrus.bar");

		// And try to add one that was there before
		fixture.getEditor().addExportPackage("org.eclipse.papyrus.foo.tests");

		fixture.getEditor().save();

		List<String> manifest = getManifest();

		// We have bar without a version
		assertThat(one(manifest, "org.eclipse.papyrus.bar"), not(containsString(";")));

		// And still just the one one foo with its version
		assertThat(one(manifest, "org.eclipse.papyrus.foo.tests"), containsString(";version=\"2.0.0\""));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void addExportPackageStringString() {
		// Add a package that wasn't there before
		fixture.getEditor().addExportPackage("org.eclipse.papyrus.bar", "1.2.0");

		// And set the version of an existing package
		fixture.getEditor().addExportPackage(
				"org.eclipse.papyrus.eclipse.project.editors.tests", "2.0.0");

		// And change the version of an existing dependency
		fixture.getEditor().addExportPackage("org.eclipse.papyrus.foo.tests", "1.1.100");

		fixture.getEditor().save();

		List<String> manifest = getManifest();

		// We have bar with a version
		assertThat(one(manifest, "org.eclipse.papyrus.bar"), containsString(";version=\"1.2.0\""));

		// And one tests with a new version and the same other directives
		assertThat(one(manifest, "org.eclipse.papyrus.eclipse.project.editors.tests"),
				both(containsString(";version=\"2.0.0\"")).and(
						containsString(";x-internal:=true")));

		// And one foo with a new range (not an appended range)
		assertThat(one(manifest, "org.eclipse.papyrus.foo.tests"),
				both(containsString(";version=\"1.1.100\"")).and(
						not(containsString(";version=\"2.0.0\""))));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void getRequiredBundles() {
		List<IRequiredBundleDescription> required = fixture.getEditor().getRequiredBundles();

		assertThat(required.size(), is(4));
		assertThat(required.get(0).getVersionRange(), is(VersionRange.valueOf("[1.2.0,2.0.0)")));
		assertThat(required.get(1).getName(), is("org.eclipse.papyrus.eclipse.project.editors"));
		assertThat(required.get(2).isOptional(), is(true));
		assertThat(required.get(3).getName(), is("org.eclipse.core.resources"));
		assertThat(required.get(3).getVersionRange(), either(nullValue()).or(is(VersionRange.emptyRange)));
		assertThat(required.get(3).isExported(), is(true));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void getImportedPackages() {
		List<IPackageImportDescription> imported = fixture.getEditor().getImportedPackages();

		assertThat(imported.size(), is(2));
		assertThat(imported.get(0).getName(), is("com.ibm.icu.text"));
		assertThat(imported.get(0).getVersionRange(), either(nullValue()).or(is(VersionRange.emptyRange)));
		assertThat(imported.get(0).isOptional(), is(true));
		assertThat(imported.get(1).getName(), is("org.eclipse.emf.ecore"));
		assertThat(imported.get(1).getVersionRange(), is(VersionRange.valueOf("2.10.0")));
		assertThat(imported.get(1).isOptional(), is(false));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void setRequiredBundleExported() {
		fixture.getEditor().setRequiredBundleExported("org.eclipse.papyrus.junit.utils", true);
		fixture.getEditor().save();

		assertThat(one(getManifest(), "org.eclipse.papyrus.junit.utils"), containsString(";visibility:=reexport"));

		// And remove it
		fixture.getEditor().setRequiredBundleExported("org.eclipse.papyrus.junit.utils", false);
		fixture.getEditor().save();

		assertThat(one(getManifest(), "org.eclipse.papyrus.junit.utils"), not(containsString("visibility:=reexport")));

		// And try to remove it again! (bug 569105)
		fixture.getEditor().setRequiredBundleExported("org.eclipse.papyrus.junit.utils", false);
		fixture.getEditor().save();

		assertThat(one(getManifest(), "org.eclipse.papyrus.junit.utils"), not(containsString("visibility:=")));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void removeRequiredBundle() {
		fixture.getEditor().removeRequiredBundle("org.eclipse.ui");
		fixture.getEditor().removeRequiredBundle("org.eclipse.core.resources");
		fixture.getEditor().save();

		List<String> manifest = getManifest();

		assertThat(manifest, hasItem("Require-Bundle: org.eclipse.papyrus.junit.utils;bundle-version=\"[1.2.0,2.0.0)\","));

		// This is now the last one (no trailing comma)
		assertThat(manifest, hasItem(" org.eclipse.papyrus.eclipse.project.editors;bundle-version=\"[2.0.0,3.0.0)\""));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void removeImportedPackage() {
		fixture.getEditor().removeImportedPackage("org.eclipse.emf.ecore");
		fixture.getEditor().save();

		List<String> manifest = getManifest();

		// This is now the last one (no trailing comma)
		assertThat(manifest, hasItem("Import-Package: com.ibm.icu.text;resolution:=optional"));
	}

	@MissingFiles
	@Test
	public void exists() {
		assertThat(fixture.getEditor().exists(), is(false));
		fixture.getEditor().create();
		fixture.getEditor().save();

		assertThat(fixture.getEditor().exists(), is(true));
	}

	@MissingFiles
	@Test
	public void getMissingFiles() {
		// The .project file already exists
		assertThat(fixture.getEditor().getMissingFiles(), hasItem("META-INF/MANIFEST.MF"));
	}

	@MissingFiles
	@Test
	public void createFiles() {
		fixture.getEditor().createFiles(Collections.singleton("META-INF/MANIFEST.MF"));
		assertThat(fixture.slurp("META-INF/MANIFEST.MF"), hasItem(containsString("Manifest-Version:")));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void headerOrderMaintained_bug489075() {
		// Make a simple change
		fixture.getEditor().addDependency("org.eclipse.jface", "3.10.0");

		fixture.getEditor().save();

		List<String> manifest = getManifest();
		List<String> headerNames = manifest.stream()
				.filter(l -> !l.startsWith(" "))
				.filter(l -> !l.trim().isEmpty())
				.map(l -> l.substring(0, l.indexOf(':')))
				.collect(Collectors.toList());

		assertThat(headerNames, is(Arrays.asList(
				"Manifest-Version",
				"Require-Bundle",
				"Import-Package",
				"Export-Package",
				"Bundle-Vendor",
				"Bundle-ActivationPolicy",
				"Bundle-Version",
				"Bundle-Name",
				"Bundle-ManifestVersion",
				"Bundle-SymbolicName",
				"Bundle-Localization",
				"Bundle-RequiredExecutionEnvironment",
				"Name",
				"Full-Name",
				"Company",
				"Committer")));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void addNewCustomSection_bug489075() {
		// Add a new manifest section with custom attributes
		fixture.getEditor().setValue("test-section", "Attr1", "foo");
		fixture.getEditor().setValue("test-section", "Other", "something else");
		fixture.getEditor().setValue("test-section", "Favorite", "true");

		fixture.getEditor().save();

		List<String> manifest = getManifest();
		List<String> headerNames = manifest.stream()
				.filter(l -> !l.startsWith(" "))
				.filter(l -> !l.trim().isEmpty())
				.map(l -> l.substring(0, l.indexOf(':')))
				.collect(Collectors.toList());

		// Isolate the new headers (the last four, including the "Name: test-section")
		headerNames = headerNames.subList(headerNames.size() - 4, headerNames.size());

		// The first is the section name header
		assertThat(headerNames.get(0), is("Name"));

		// But the others aren't in any defined order
		assertThat(new HashSet<>(headerNames), is(new HashSet<>(Arrays.asList(
				"Name",
				"Attr1",
				"Other",
				"Favorite"))));
	}

	@WithResource("manifest_project/META-INF/MANIFEST.MF")
	@Test
	public void removeCustomSection_bug489075() {
		// Add a new manifest section with custom attributes
		fixture.getEditor().removeValue("author-info", "Full-Name");
		fixture.getEditor().removeValue("author-info", "Company");
		fixture.getEditor().removeValue("author-info", "Committer");

		fixture.getEditor().save();

		List<String> manifest = getManifest();
		List<String> headerNames = manifest.stream()
				.filter(l -> !l.startsWith(" "))
				.filter(l -> !l.trim().isEmpty())
				.map(l -> l.substring(0, l.indexOf(':')))
				.collect(Collectors.toList());

		// The custom section does not appear at all. Not even the section name
		assertThat(headerNames, not(either(hasItem("Name"))
				.or(hasItem("Full-Name")).or(hasItem("Company")).or(hasItem("Committer"))));
	}

	//
	// Test framework
	//

	List<String> getManifest() {
		return fixture.slurp("META-INF/MANIFEST.MF");
	}

	String one(List<String> list, String substring) {
		int[] count = { 0 };
		return list.stream()
				.filter(s -> s.contains(substring))
				.peek(s -> assertThat("not exactly one " + s, ++count[0], is(1)))
				.collect(Collectors.toList())
				.get(0);
	}
}
