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

import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IBuildEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IClasspathEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IFeatureProjectEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IFileEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginProjectEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IProjectEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.ProjectEditors;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.eclipse.papyrus.junit.utils.rules.ProjectFixture;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.osgi.framework.FrameworkUtil;

/**
 * Test fixture managing a project editor under test with utilities for
 * manipulating and verifying its contents.
 */
public class ProjectEditorFixture<T extends IFileEditor> extends ProjectFixture {
	private final Class<T> editorType;

	private final UnaryOperator<Description> testTransform;

	private T editor;

	private Description test;
	private boolean isMissingFilesTest;
	private boolean isCreatedProjectTest;
	private String[] testResources;

	public ProjectEditorFixture(Class<T> editorType) {
		this(editorType, UnaryOperator.identity());
	}

	public ProjectEditorFixture(Class<T> editorType, Supplier<?> testInstance) {
		this(editorType, createTestTransform(testInstance));
	}

	public ProjectEditorFixture(Class<T> editorType, UnaryOperator<Description> testTransform) {
		super();

		this.editorType = editorType;
		this.testTransform = testTransform;
	}

	private static UnaryOperator<Description> createTestTransform(Supplier<?> testInstance) {
		return desc -> {
			Description result = desc;

			Object test = testInstance.get();
			if (test != null) {
				Class<?> testClass = test.getClass();
				String testMethodName = upTo(desc.getMethodName(), '_');

				Method testMethod = Stream.of(testClass.getMethods())
						.filter(m -> m.isAnnotationPresent(Test.class))
						.filter(m -> m.getParameterCount() == 0)
						.filter(m -> m.getReturnType() == void.class)
						.filter(m -> testMethodName.equals(m.getName()))
						.findFirst().get();

				result = Description.createTestDescription(testClass, testMethod.getName(), testMethod.getAnnotations());
			}

			return result;
		};
	}

	private static String upTo(String s, char stop) {
		int index = s.indexOf(stop);
		return (index < 0) ? s : s.substring(0, index);
	}

	@Override
	public Statement apply(Statement base, Description description) {
		test = description;

		return super.apply(base, description);
	}

	public String getTestMethod() {
		return test.getMethodName();
	}

	private void initTest() {
		Description test = testTransform.apply(this.test);

		isMissingFilesTest = Optional.ofNullable(JUnitUtils.getAnnotation(test, MissingFiles.class))
				.map(MissingFiles::value).orElse(false);

		if (!isMissingFilesTest) {
			isCreatedProjectTest = Optional.ofNullable(JUnitUtils.getAnnotation(test, CreatedProject.class))
					.map(CreatedProject::value).orElse(false);
		}

		testResources = Optional.ofNullable(JUnitUtils.getAnnotation(test, WithResource.class))
				.map(WithResource::value).orElseGet(() -> new String[0]);
	}

	@Override
	protected void createProject(String name) throws CoreException {
		initTest();

		super.createProject(name);

		IProject project = getProject();

		// Create the required test resources in the project *before* initializing the editor
		Stream.of(testResources).forEach(res -> {
			try (InputStream input = getResource(res).openStream()) {
				// Remove the first segment for the folder partition below resources/
				IFile file = getProject().getFile(new Path(res).removeFirstSegments(1));
				ensureContainer(file);
				file.create(input, false, null);
			} catch (Exception e) {
				e.printStackTrace();
				fail("Failed to create test resource: " + e.getMessage());
			}
		});

		if (editorType == IProjectEditor.class) {
			editor = editorType.cast(ProjectEditors.getProjectEditor(project));
		} else if (editorType == IPluginProjectEditor.class) {
			editor = editorType.cast(ProjectEditors.getPluginProjectEditor(project));
		} else if (editorType == IJavaProjectEditor.class) {
			editor = editorType.cast(ProjectEditors.getJavaProjectEditor(project));
		} else if (editorType == IManifestEditor.class) {
			editor = editorType.cast(ProjectEditors.getManifestEditor(project));
		} else if (editorType == IPluginEditor.class) {
			editor = editorType.cast(ProjectEditors.getPluginEditor(project));
		} else if (editorType == IFeatureProjectEditor.class) {
			editor = editorType.cast(ProjectEditors.getFeatureProjectEditor(project));
		} else {
			fail("Unsupported editor type: " + editorType);
		}

		editor.init();

		if (!isMissingFilesTest) {
			editor.createFiles(getRequiredFiles());
		}
		if (isCreatedProjectTest) {
			editor.create();
			editor.save();
		}
	}

	private void ensureContainer(IResource resource) throws CoreException {
		IContainer container = resource.getParent();
		if (!container.exists() && (container instanceof IFolder)) {
			// Ultimately, the project exists
			ensureContainer(container);
			((IFolder) container).create(false, true, null);
		}
	}

	protected Set<String> getRequiredFiles() {
		Set<String> result = new HashSet<>();

		result.add(".project");
		if (editor instanceof IManifestEditor) {
			result.add("META-INF/MANIFEST.MF");
		}
		if (editor instanceof IBuildEditor) {
			result.add("build.properties");
		}
		if (editor instanceof IClasspathEditor) {
			result.add(".classpath");
		}
		if (editor instanceof IPluginProjectEditor) {
			result.add("plugin.xml");
		}
		if (editor instanceof IFeatureProjectEditor) {
			result.add("feature.xml");
		}

		return result;
	}

	@Override
	protected void deleteProject() {
		editor = null;

		super.deleteProject();
	}

	public T getEditor() {
		return editor;
	}

	public URL getResource(String resourcePath) {
		IPath fullPath = new Path("resources").append(resourcePath).removeTrailingSeparator();
		return FrameworkUtil.getBundle(getClass()).getEntry(fullPath.toString());
	}

	public List<String> slurp(String filePath) {
		IFile file = getProject().getFile(filePath);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getContents(), file.getCharset()))) {
			return reader.lines().collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Failed to load test project file: " + e.getMessage());
			return null; // Unreachable
		}
	}
}
