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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.util.stream.Stream;

import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginProjectEditor;
import org.eclipse.papyrus.junit.utils.rules.AnnotationRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;

/**
 * Test cases for the implementation of the {@link IPluginProjectEditor} API.
 */
public class PluginEditorTest {

	private final AnnotationRule<String> delegateKey = AnnotationRule.create(Delegate.class);

	private final ProjectEditorFixture<IPluginEditor> fixture = new ProjectEditorFixture<>(IPluginEditor.class, this::activeDelegate);

	@Rule
	public final TestRule rules = RuleChain.outerRule(delegateKey).around(fixture);

	@Delegate("project")
	private final ProjectEditorTest project = new ProjectEditorTest(fixture);

	@Delegate("plugin")
	private final PluginProjectEditorTest pluginProject = new PluginProjectEditorTest(fixture);

	@Delegate("java")
	private final JavaProjectEditorTest javaProject = new JavaProjectEditorTest(fixture);

	@Delegate("manifest")
	private final ManifestEditorTest manifest = new ManifestEditorTest(fixture);

	//
	// Tests for the IPluginEditor specific API
	//

	@Test
	public void pluginManifestExists() {
		assertThat(fixture.getEditor().pluginManifestExists(), is(false));
		pluginProject.exists();
		assertThat(fixture.getEditor().pluginManifestExists(), is(true));
	}

	//
	// Tests for the IProjectEditor API
	//

	@Delegate("project")
	@Test
	public void getMissingFiles_project() {
		delegate();
	}

	@Delegate("plugin")
	@Test
	public void getMissingFiles_plugin() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void getMissingFiles_java() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void getMissingFiles_manifest() {
		delegate();
	}

	@Delegate("project")
	@Test
	public void addFile() {
		delegate();
	}

	//
	// Tests for the IPluginProjectEditor API
	//

	@Delegate("plugin")
	@Test
	public void addExtension() {
		delegate();
	}

	@Delegate("plugin")
	@Test
	public void getExtensions() {
		delegate();
	}

	@Delegate("plugin")
	@Test
	public void setAttribute() {
		delegate();
	}

	@Delegate("plugin")
	@Test
	public void getMissingNature_plugin() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void getMissingNature_java() {
		delegate();
	}

	@Delegate("plugin")
	@Test
	public void getMissingBuildCommand_plugin() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void getMissingBuildCommand_java() {
		delegate();
	}

	//
	// Tests for the IJavaProjectEditor API
	//

	@Delegate("java")
	@Test
	public void createFiles_java() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void createFiles_manifest() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void hasNature() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void addBuildCommands() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void hasBuildCommand() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void exists_java() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void exists_manifest() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void registerSourceFolder() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void registerBinFolder() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void addToBuild() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void isRegisteredSourceFolder() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void isRegisteredBinFolder() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void getSourceFolders() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void getBinFolders() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void getElementsInBuild() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void addSourceFolderToClasspath() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void addJavaSourceFolder() {
		delegate();
	}

	@Delegate("java")
	@Test
	public void isSourceFolderRegistered() {
		delegate();
	}

	//
	// Tests for the IManifestEditor API
	//

	@Delegate("manifest")
	@Test
	public void addDependencyString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void addDependencyStringString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void hasDependency() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void setValueStringString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void getValueString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void getValueStringString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void setValueStringStringString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void removeValueStringString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void removeValueString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void setSymbolicBundleName() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void getSymbolicBundleName() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void getBundleName() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void setBundleName() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void getBundleVersion() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void setBundleVersion() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void getBundleVendor() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void setBundleVendor() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void getBundleLocalization() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void setSingleton() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void addImportPackageString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void addImportPackageStringString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void addExportPackageString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void addExportPackageStringString() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void getRequiredBundles() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void getImportedPackages() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void setRequiredBundleExported() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void removeRequiredBundle() {
		delegate();
	}

	@Delegate("manifest")
	@Test
	public void removeImportedPackage() {
		delegate();
	}

	//
	// Test framework
	//

	private Object activeDelegate() {
		String key = delegateKey.get();
		Field field = (key == null) ? null : Stream.of(PluginEditorTest.class.getDeclaredFields())
				.filter(f -> {
					Delegate delegate = f.getAnnotation(Delegate.class);
					return (delegate != null) && key.equals(delegate.value());
				})
				.findFirst().get();

		try {
			return (field == null) ? null : field.get(this);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Reflection failed: " + e.getMessage());
			return null; // unreachable
		}
	}

	private void delegate() {
		Object delegate = activeDelegate();
		String methodName = fixture.getTestMethod();
		int index = methodName.indexOf('_');
		if (index >= 0) {
			methodName = methodName.substring(0, index);
		}

		try {
			MethodHandle testCase = MethodHandles.lookup().findVirtual(delegate.getClass(), methodName, MethodType.methodType(void.class));
			testCase.bindTo(delegate).invoke();
		} catch (Error e) {
			throw e;
		} catch (Throwable e) {
			e.printStackTrace();
			fail("Test reflection failed: " + e.getMessage());
		}
	}

	@Target({ ElementType.METHOD, ElementType.FIELD })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Delegate {
		String value();
	}
}
