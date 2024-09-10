/*****************************************************************************
 * Copyright (c) 2011, 2016, 2017 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  Vincent Lorenzo (CEA LIST) - bug 525876
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.project;

import java.util.Set;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.papyrus.eclipse.project.editors.Activator;
import org.eclipse.papyrus.eclipse.project.editors.file.BuildEditor;
import org.eclipse.papyrus.eclipse.project.editors.file.ClasspathEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IBuildEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IClasspathEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor;

/**
 *
 * This editor allows to manage a JavaProject
 *
 */
public class JavaProjectEditor extends ProjectEditor implements IJavaProjectEditor {


	/** the java project */
	private final IJavaProject javaProject;

	/**
	 * Gets the classpath editor.
	 *
	 * @return the classpath editor
	 */
	public IClasspathEditor getClasspathEditor() {
		return classpathEditor;
	}


	/**
	 * Gets the builds the editor.
	 *
	 * @return the builds the editor
	 */
	public IBuildEditor getBuildEditor() {
		return buildEditor;
	}

	/** the classpath editor */
	private final IClasspathEditor classpathEditor;

	/** the build editor */
	private final IBuildEditor buildEditor;

	/**
	 *
	 * Constructor.
	 *
	 * @param project
	 *            the eclipse project
	 * @throws CoreException
	 */
	public JavaProjectEditor(final IProject project) throws CoreException {
		super(project);

		this.javaProject = JavaCore.create(project);
		this.buildEditor = new BuildEditor(project);
		this.classpathEditor = new ClasspathEditor(this.javaProject);
	}

	/**
	 * Initializes me as a slave to another editor, which maintains the canonical
	 * project description.
	 *
	 * @param master
	 *            my master editor
	 */
	JavaProjectEditor(AbstractProjectEditor master) {
		super(master);

		this.javaProject = JavaCore.create(master.getProject());
		this.buildEditor = new BuildEditor(master.getProject());
		this.classpathEditor = new ClasspathEditor(this.javaProject);
	}

	/**
	 *
	 * @return
	 * 		<code>true</code> if the project is a java project :
	 *
	 */
	@Override
	public boolean exists() {
		return super.exists() && this.javaProject.exists();
	}

	@Override
	public Set<String> getMissingFiles() {
		Set<String> files = super.getMissingFiles();
		files.addAll(this.classpathEditor.getMissingFiles());
		files.addAll(this.buildEditor.getMissingFiles());
		return files;
	}

	@Override
	public Set<String> getMissingNature() {
		Set<String> natures = super.getMissingNature();
		if (!hasNature(JAVA_NATURE)) {
			natures.add(JAVA_NATURE);
		}
		return natures;
	}

	@Override
	public void init() {
		this.classpathEditor.init();
		this.buildEditor.init();
	}

	@Override
	public void createFiles(final Set<String> files) {
		super.createFiles(files);
		this.classpathEditor.createFiles(files);
		this.buildEditor.createFiles(files);
	}


	@Override
	public boolean isDirty() {
		return super.isDirty() || classpathEditor.isDirty() || buildEditor.isDirty();
	}

	/**
	 * save the modification
	 * 
	 * @since 2.0
	 */
	@Override
	protected void doSave() {
		super.doSave();
		try {
			this.javaProject.save(new NullProgressMonitor(), true);
		} catch (JavaModelException ex) {
			Activator.log.error(ex);
		}
		this.classpathEditor.save();
		this.buildEditor.save();
	}

	@Override
	public void addJavaSourceFolder(final String path) {
		if (exists()) {
			// we add this source folder to the class path
			// String classPath = "/" + this.project.getName()+"/" + path;
			String classPath = path;
			this.classpathEditor.addSourceFolderToClasspath(classPath);

			// we add this source folder in the build file
			this.buildEditor.registerSourceFolder(path + "/"); //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor#addPackage(String, java.lang.String)
	 */
	@Override
	public IPackageFragment addPackage(final String sourceFolderName, final String packageName) {
		IPackageFragment createdPackage = null;
		if (exists()) {
			touch();

			IFolder sourceFolder = null;
			if (null != sourceFolderName && !sourceFolderName.isEmpty() && this.classpathEditor.isSourceFolderRegistered(sourceFolderName)) {
				sourceFolder = javaProject.getProject().getFolder(sourceFolderName);
			} else {
				final String[] sourceFolders = this.classpathEditor.getSourceFolders();
				if (sourceFolders.length > 0) {
					sourceFolder = javaProject.getProject().getFolder(sourceFolders[0]);
				}
			}
			if (null != sourceFolder) {
				createdPackage = javaProject.getPackageFragmentRoot(sourceFolder).getPackageFragment(packageName);
				if (null == createdPackage || !createdPackage.exists()) {
					try {
						createdPackage = javaProject.getPackageFragmentRoot(sourceFolder).createPackageFragment(packageName, false, null);
					} catch (final JavaModelException e) {
						Activator.log.error(e);
					}
				}
			}
		}

		return createdPackage;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor#addClass(String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ICompilationUnit addClass(String sourceFolderName, final String packageName, final String className, final String content) {
		ICompilationUnit createdClass = null;
		if (exists()) {
			touch();

			final IPackageFragment packageFragment = addPackage(sourceFolderName, packageName);
			try {
				createdClass = packageFragment.createCompilationUnit(className + CLASS_FILE_EXTENSION, content, false, null);
			} catch (final JavaModelException e) {
				Activator.log.error(e);
			}
		}

		return createdClass;
	}

	@Override
	public void registerSourceFolder(final String string) {
		this.buildEditor.registerSourceFolder(string);
	}

	@Override
	public void addToBuild(final String path) {
		this.buildEditor.addToBuild(path);
	}

	@Override
	public boolean isRegisteredSourceFolder(final String path) {
		return this.buildEditor.isRegisteredSourceFolder(path);
	}

	@Override
	public String[] getSourceFolders() {
		return this.buildEditor.getSourceFolders();
	}

	@Override
	public Set<String> getMissingBuildCommand() {
		Set<String> buildCommand = super.getMissingBuildCommand();
		if (!hasBuildCommand(IJavaProjectEditor.JAVA_BUILD_COMMAND)) {
			buildCommand.add(IJavaProjectEditor.JAVA_BUILD_COMMAND);
		}
		return buildCommand;
	}

	@Override
	public void removeFromBuild(String path) {
		buildEditor.removeFromBuild(path);
	}

	@Override
	public String[] getElementsInBuild() {
		return buildEditor.getElementsInBuild();
	}

	@Override
	public void registerBinFolder(String binFolder) {
		buildEditor.registerBinFolder(binFolder);
	}

	@Override
	public boolean isRegisteredBinFolder(String binFolder) {
		return buildEditor.isRegisteredBinFolder(binFolder);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void addSourceFolderToClasspath(String folderPath) {
		classpathEditor.addSourceFolderToClasspath(folderPath);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public void addSourceFolderToClasspath(String srcPath, String binPath) {
		classpathEditor.addSourceFolderToClasspath(srcPath, binPath);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public boolean isSourceFolderRegistered(String folderPath) {
		return classpathEditor.isSourceFolderRegistered(folderPath);
	}

	/**
	 * @since 2.0
	 */
	@Override
	public String[] getBinFolders() {
		return classpathEditor.getBinFolders();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor#getJavaProject()
	 */
	@Override
	public IJavaProject getJavaProject() {
		return javaProject;
	}
}
