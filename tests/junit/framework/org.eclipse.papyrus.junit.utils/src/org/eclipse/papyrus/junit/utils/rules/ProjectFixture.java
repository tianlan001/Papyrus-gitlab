/*
 * Copyright (c) 2014, 2021 CEA, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Christian W. Damus (CEA) - Initial API and implementation
 *   Christian W. Damus - bugs 451230, 468030, 569357, 570097, 572865
 *
 */
package org.eclipse.papyrus.junit.utils.rules;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.function.Function;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourceAttributes;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.papyrus.junit.utils.JUnitUtils;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import com.google.common.base.Charsets;
import com.google.common.io.CharSource;
import com.google.common.io.Resources;


/**
 * A self-creating and self-destroying workspace project named according to the current test case.
 */
public class ProjectFixture implements TestRule {

	private IProject project;
	private Class<?> testClass;

	public ProjectFixture() {
		super();
	}

	public final IProject getProject() {
		return project;
	}

	public URI getURI(IPath path) {
		return URI.createPlatformResourceURI(project.getFile(path).getFullPath().toString(), true);
	}

	public URI getURI(String path) {
		return URI.createPlatformResourceURI(project.getFile(new Path(path)).getFullPath().toString(), true);
	}

	public IFile getFile(URI uri) {
		return !uri.isPlatformResource() ? null : project.getWorkspace().getRoot().getFile(new Path(uri.toPlatformString(true)));
	}

	/**
	 * Get the file in the test project at the given project-relative {@code path}.
	 *
	 * @param path
	 *            a project-relative path
	 * @return the file, if it exists (else fail the test)
	 */
	public IFile getFile(String path) {
		IResource resource = getProject().findMember(path);
		assertThat("No such file in project: " + path, resource, instanceOf(IFile.class)); //$NON-NLS-1$
		assertThat("File not accessible: " + path, resource.isAccessible(), is(true)); //$NON-NLS-1$
		return (IFile) resource;
	}

	/**
	 * Creates a new file at the specified project-relative path with the contents of a bundle resource.
	 *
	 * @param relativeFilePath
	 *            the project-relative path of the file to create
	 * @param classFromBundle
	 *            the bundle in which its content is to be found
	 * @param resourcePath
	 *            the path in the context bundle of the resource to copy
	 *
	 * @return the new file
	 *
	 * @throws IOException
	 *             on any problem in creating the file
	 */
	public IFile createFile(String relativeFilePath, Class<?> classFromBundle, String resourcePath) throws IOException {
		return createFile(relativeFilePath, classFromBundle, resourcePath, null);
	}

	/**
	 * Creates a new file at the specified project-relative path with the contents of a bundle resource.
	 *
	 * @param relativeFilePath
	 *            the project-relative path of the file to create
	 * @param resourcePath
	 *            the path in the context bundle of the resource to copy
	 *
	 * @return the new file
	 *
	 * @throws IOException
	 *             on any problem in creating the file
	 */
	public IFile createFile(String relativeFilePath, String resourcePath) throws IOException {
		return createFile(relativeFilePath, testClass, resourcePath, null);
	}

	/**
	 * Creates a new text file at the specified project-relative path with the contents of a bundle resource, filtered
	 * through the given text transformation.
	 *
	 * @param relativeFilePath
	 *            the project-relative path of the file to create
	 * @param classFromBundle
	 *            the bundle in which its content is to be found
	 * @param resourcePath
	 *            the path in the context bundle of the resource to copy
	 * @param textFilter
	 *            a transformation to apply to text content of the resource, or {@code null} if none
	 *
	 * @return the new file
	 *
	 * @throws IOException
	 *             on any problem in creating the file
	 */
	public IFile createFile(String relativeFilePath, Class<?> classFromBundle, String resourcePath, Function<String, String> textFilter) throws IOException {
		IFile result;

		Bundle bundle = FrameworkUtil.getBundle(classFromBundle);
		URL resource = (bundle == null) ? null : bundle.getResource(resourcePath);
		if (resource == null) {
			throw new IOException("No such bundle resource: " + resourcePath);
		}

		IPath path = new Path(relativeFilePath);

		InputStream input;
		if (textFilter != null) {
			String content = Resources.toString(resource, Charsets.UTF_8);
			content = textFilter.apply(content);
			input = CharSource.wrap(content).asByteSource(Charsets.UTF_8).openStream();
		} else {
			input = resource.openStream();
		}

		try (input) {
			createFolders(path.removeLastSegments(1));
			result = project.getFile(path);
			result.create(input, false, null);
		} catch (CoreException e) {
			if (e.getStatus().getException() instanceof IOException) {
				throw (IOException) e.getStatus().getException();
			} else if (e.getCause() instanceof IOException) {
				throw (IOException) e.getCause();
			}
			throw new IOException("Failed to create file", e);
		}

		return result;
	}

	/**
	 * Creates a new text file at the specified project-relative path with the contents of a bundle resource, filtered
	 * through the given text transformation.
	 *
	 * @param relativeFilePath
	 *            the project-relative path of the file to create
	 * @param resourcePath
	 *            the path in the context bundle of the resource to copy
	 * @param textFilter
	 *            a transformation to apply to text content of the resource, or {@code null} if none
	 *
	 * @return the new file
	 *
	 * @throws IOException
	 *             on any problem in creating the file
	 */
	public IFile createFile(String relativeFilePath, String resourcePath, Function<String, String> textFilter) throws IOException {
		return createFile(relativeFilePath, testClass, resourcePath, textFilter);
	}

	/**
	 * Replace tokens in a file. Tokens in the template file look like <tt>{{TOKEN}}</tt> where <tt>TOKEN</tt> is the
	 * {@code token} string supplied to this method.
	 *
	 * @param token
	 *            the token string to replace, without the surrounding braces
	 * @param replacement
	 *            the replacement text for the token
	 * @return the token replacement function
	 *
	 * @see #createFile(String, Class, String, Function)
	 * @see #createFile(String, String, Function)
	 */
	public static Function<String, String> replaceTokens(String token, String replacement) {
		String delimitedToken = String.format("{{%s}}", token); //$NON-NLS-1$
		return input -> input.replace(delimitedToken, replacement);
	}

	private void createFolders(IPath folderPath) throws CoreException {
		if ((folderPath.segmentCount() > 0) && !folderPath.lastSegment().isEmpty()) {
			createFolders(folderPath.removeLastSegments(1));
			IFolder folder = project.getFolder(folderPath);
			if (!folder.isAccessible()) {
				folder.create(false, true, null);
			}
		}
	}

	/**
	 * Creates a new file in my project with the contents of a bundle resource.
	 *
	 * @param classFromBundle
	 *            the bundle in which its content is to be found
	 * @param resourcePath
	 *            the path in the context bundle of the resource to copy
	 *
	 * @return the new file, which will have the same name as the bundle resource and will be at the top level of the project
	 *
	 * @throws IOException
	 *             on any problem in creating the file
	 *
	 * @see #createFile(String, Class, String)
	 */
	public IFile createFile(Class<?> classFromBundle, String resourcePath) throws IOException {
		return createFile(new Path(resourcePath).lastSegment(), classFromBundle, resourcePath);
	}

	/**
	 * This allows to copy a entire folder into the current project.
	 *
	 * @param classFromBundle
	 *            The bundle in which its content is to be found.
	 * @param folderPath
	 *            The path in the context bundle of the folder to copy.
	 * @throws IOException
	 *             On any problem while creating the files and folders.
	 */
	public void copyFolder(final Class<?> classFromBundle, final String folderPath) throws IOException {
		copyFolder(classFromBundle, folderPath, project);
	}

	/**
	 * Copy a folder into a container.
	 *
	 * @param classFromBundle
	 *            The bundle in which its content is to be found.
	 * @param folderPath
	 *            The path in the context bundle of the folder to copy.
	 * @param destination
	 *            the destination container
	 * @throws IOException
	 *             On any problem while creating the files and folders.
	 */
	public void copyFolder(final Class<?> classFromBundle, final String folderPath, final IContainer destination) throws IOException {
		// Get the bundle and check that the resource exists
		final Bundle bundle = FrameworkUtil.getBundle(classFromBundle);
		final URL resource = (bundle == null) ? null : bundle.getResource(folderPath);
		if (resource == null) {
			throw new IOException("No such bundle resource: " + folderPath); //$NON-NLS-1$
		}

		// Get the file corresponding to the resource
		File pluginFile = null;
		try {
			pluginFile = new File(FileLocator.toFileURL(resource).toURI());
		} catch (URISyntaxException e1) {
			throw new IOException("Error while getting project files"); //$NON-NLS-1$
		}
		// Copy its content
		try {
			copyFiles(pluginFile, destination);
		} catch (Exception e) {
			throw new IOException("Error while copying project files"); //$NON-NLS-1$
		}
	}

	/**
	 * This allows to copy recursively the files from sources to destination folder.
	 *
	 * @param srcFolder
	 *            The source folder.
	 * @param destFolder
	 *            The destination folder.
	 * @throws CoreException
	 *             Possible core exception while manipulating files.
	 * @throws FileNotFoundException
	 *             In case where the file is not found.
	 */
	private void copyFiles(final File srcFolder, final IContainer destFolder) throws CoreException, FileNotFoundException {
		if (null != srcFolder && srcFolder.isDirectory()) {
			for (final File currentFile : srcFolder.listFiles()) {
				if (currentFile.isDirectory()) {
					// In the case of sub folders, create it (if needed) and copy files
					final IFolder newFolder = destFolder.getFolder(new Path(currentFile.getName()));
					if (!newFolder.exists()) {
						newFolder.create(true, true, null);
					}
					copyFiles(currentFile, newFolder);
				} else {
					// In the case of files, create or replace it
					final IFile newFile = destFolder.getFile(new Path(currentFile.getName()));
					// If the file already exists, replace it
					if (newFile.exists()) {
						newFile.delete(true, null);
					}
					newFile.create(new FileInputStream(currentFile), true, null);
				}
			}
		}
	}

	@Override
	public Statement apply(final Statement base, Description description) {
		String name = description.getMethodName();

		ProjectName annotation = JUnitUtils.getAnnotation(description, ProjectName.class);
		if (annotation != null && annotation.value() != null) {
			name = annotation.value();
		}
		if (name == null) {
			// We are used as a class rule, then
			name = description.getClassName();
			if (name != null) {
				name = name.substring(name.lastIndexOf('.') + 1);
			}
		}

		final String projectName = name;
		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				testClass = JUnitUtils.getTestClass(description);

				createProject(projectName);

				try {
					base.evaluate();
				} finally {
					deleteProject();

					testClass = null;
				}
			}
		};
	}

	protected void createProject(String name) throws CoreException {
		project = ResourcesPlugin.getWorkspace().getRoot().getProject(name);

		if (project.exists()) {
			// Start clean, if we can
			deleteProject();
		}

		if (!project.exists()) {
			project.create(null);
		}

		if (!project.isOpen()) {
			project.open(null);
		}

		project.refreshLocal(IResource.DEPTH_INFINITE, null);
	}

	protected void deleteProject() {
		try {
			project.refreshLocal(IResource.DEPTH_INFINITE, null);

			if (project.isAccessible()) {
				// Make sure that we can delete everything
				project.accept(new IResourceVisitor() {

					@Override
					public boolean visit(IResource resource) throws CoreException {
						switch (resource.getType()) {
						case IResource.FILE:
						case IResource.FOLDER:
							ensureWritable(resource);
							break;
						}

						return true;
					}
				});
			}

			project.delete(true, null);
		} catch (CoreException e) {
			e.printStackTrace();
			// leave the project. We may end up re-using it, who knows?
		}
	}

	protected void ensureWritable(IResource resource) throws CoreException {
		ResourceAttributes attr = resource.getResourceAttributes();
		if (attr.isReadOnly()) {
			attr.setReadOnly(false);
			resource.setResourceAttributes(attr);
		}
	}

	public void setReadOnly(String projectRelativePath) {
		setReadOnly(new Path(projectRelativePath));
	}

	public void setReadOnly(IPath projectRelativePath) {
		setReadOnly(project.findMember(projectRelativePath));
	}

	public void setReadOnly(Resource resource) {
		IFile file = WorkspaceSynchronizer.getFile(resource);
		assertThat("Cannot set non-workspace resource read-only", file, notNullValue());
		setReadOnly(file);
	}

	public void setReadOnly(IResource resource) {
		setReadOnly(resource, true);
	}

	public void setReadOnly(IResource resource, boolean readOnly) {
		ResourceAttributes attr = resource.getResourceAttributes();

		if (attr.isReadOnly() != readOnly) {
			attr.setReadOnly(readOnly);

			try {
				resource.setResourceAttributes(attr);
			} catch (CoreException e) {
				e.getLocalizedMessage();
				fail(String.format("Failed to make workspace resource %s: %s", readOnly ? "read-only" : "writable", e.getLocalizedMessage()));
			}
		}
	}

}
