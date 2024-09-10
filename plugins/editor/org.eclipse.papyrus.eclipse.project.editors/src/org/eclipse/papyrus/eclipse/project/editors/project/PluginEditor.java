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
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr - Initial API and implementation
 *  Christian W. Damus - bug 485220
 *  Vincent Lorenzo (CEA LIST) - bug 525876
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.project;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.papyrus.eclipse.project.editors.file.ManifestEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IPluginEditor;
import org.eclipse.pde.core.project.IPackageImportDescription;
import org.eclipse.pde.core.project.IRequiredBundleDescription;
import org.xml.sax.SAXException;

/**
 * @since 2.0
 */
public class PluginEditor extends PluginProjectEditor implements IPluginEditor {

	protected IManifestEditor manifest;

	protected IJavaProjectEditor java;

	public PluginEditor(IProject project) throws CoreException, IOException, SAXException, ParserConfigurationException {
		super(project);

		manifest = new ManifestEditor(project);
		java = new JavaProjectEditor(this);
	}

	public PluginProjectEditor getPluginEditor() {
		return this;
	}

	public IManifestEditor getManifestEditor() {
		return manifest;
	}

	public IJavaProjectEditor getJavaProjectEditor() {
		return java;
	}

	@Override
	public Set<String> getMissingFiles() {
		Set<String> result = super.getMissingFiles();
		result.addAll(java.getMissingFiles());
		result.addAll(manifest.getMissingFiles());
		return result;
	}

	@Override
	public Set<String> getMissingNature() {
		Set<String> result = super.getMissingNature();
		result.addAll(java.getMissingNature());
		result.addAll(manifest.getMissingNature());
		return result;
	}

	@Override
	public Set<String> getMissingBuildCommand() {
		Set<String> result = super.getMissingBuildCommand();
		result.addAll(java.getMissingBuildCommand());
		result.addAll(manifest.getMissingBuildCommand());
		return result;
	}

	@Override
	public void init() {
		super.init();

		java.init();
		manifest.init();
	}

	@Override
	public void create() {
		super.create();

		java.create();
		manifest.create();
	}

	@Override
	public void createFiles(Set<String> files) {
		super.createFiles(files);

		java.createFiles(files);
		manifest.createFiles(files);
	}

	@Override
	public boolean exists() {
		// The plugin.xml doesn't have to exist for me to exist as a plug-in
		return java.exists() && manifest.exists();
	}

	@Override
	public boolean pluginManifestExists() {
		return super.exists();
	}

	@Override
	public void registerSourceFolder(String source) {
		java.registerSourceFolder(source);
	}

	@Override
	public void addToBuild(String path) {
		java.addToBuild(path);
	}

	@Override
	public String[] getSourceFolders() {
		return java.getSourceFolders();
	}

	@Override
	public void addDependency(String dependency) {
		manifest.addDependency(dependency);
	}

	@Override
	public void addDependency(String dependency, String version) {
		manifest.addDependency(dependency, version);
	}

	/**
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#hasDependency(java.lang.String)
	 *
	 * @param dependency
	 * @return
	 */
	@Override
	public boolean hasDependency(String dependency) {
		return manifest.hasDependency(dependency);
	}

	@Override
	public void setValue(String key, String value) {
		manifest.setValue(key, value);
	}

	@Override
	public void setValue(String key, String name, String value) {
		manifest.setValue(key, name, value);
	}

	@Override
	public void removeValue(String key, String value) {
		manifest.removeValue(key, value);
	}

	@Override
	public void removeValue(String key) {
		manifest.removeValue(key);
	}

	@Override
	public void setBundleName(String name) {
		manifest.setBundleName(name);
	}

	@Override
	public String getSymbolicBundleName() {
		return manifest.getSymbolicBundleName();
	}

	@Override
	public String getBundleVersion() {
		return manifest.getBundleVersion();
	}

	@Override
	public void setBundleVersion(String version) {
		manifest.setBundleVersion(version);
	}

	@Override
	public boolean isDirty() {
		return super.isDirty() || manifest.isDirty() || java.isDirty();
	}

	@Override
	protected void doSave() {
		super.doSave();
		manifest.save();
		java.save();
	}

	@Override
	public void registerBinFolder(String binFolder) {
		java.registerBinFolder(binFolder);
	}

	@Override
	public void removeFromBuild(String path) {
		java.removeFromBuild(path);
	}

	@Override
	public boolean isRegisteredSourceFolder(String path) {
		return java.isRegisteredSourceFolder(path);
	}

	@Override
	public boolean isRegisteredBinFolder(String binFolder) {
		return java.isRegisteredBinFolder(binFolder);
	}

	@Override
	public String[] getElementsInBuild() {
		return java.getElementsInBuild();
	}

	@Override
	public String getBundleVendor() {
		return manifest.getBundleVendor();
	}

	@Override
	public void setBundleVendor(String vendor) {
		manifest.setBundleVendor(vendor);
	}

	@Override
	public String getValue(String key) {
		return manifest.getValue(key);
	}

	@Override
	public String getValue(String key, String name) {
		return manifest.getValue(key, name);
	}

	@Override
	public void setSymbolicBundleName(String name) {
		manifest.setSymbolicBundleName(name);
	}

	@Override
	public String getBundleName() {
		return manifest.getBundleName();
	}

	@Override
	public String getBundleLocalization() {
		return manifest.getBundleLocalization();
	}

	@Override
	public void setSingleton(boolean singleton) {
		manifest.setSingleton(singleton);
	}

	@Override
	@Deprecated
	public void setDependenciesVersion(String dependencyPattern, String newVersion) {
		manifest.setDependenciesVersion(dependencyPattern, newVersion);
	}

	/**
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#addImportPackage(java.lang.String)
	 *
	 * @param packageName
	 */
	@Override
	public void addImportPackage(String packageName) {
		manifest.addImportPackage(packageName);
	}

	/**
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#addImportPackage(java.lang.String, java.lang.String)
	 *
	 * @param packageName
	 * @param version
	 */
	@Override
	public void addImportPackage(String packageName, String version) {
		manifest.addImportPackage(packageName, version);
	}

	/**
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#addExportPackage(java.lang.String)
	 *
	 * @param packageName
	 */
	@Override
	public void addExportPackage(String packageName) {
		manifest.addExportPackage(packageName);
	}

	/**
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#addExportPackage(java.lang.String, java.lang.String)
	 *
	 * @param packageName
	 * @param version
	 */
	@Override
	public void addExportPackage(String packageName, String version) {
		manifest.addExportPackage(packageName, version);
	}

	@Override
	public List<IRequiredBundleDescription> getRequiredBundles() {
		return manifest.getRequiredBundles();
	}

	@Override
	public List<IPackageImportDescription> getImportedPackages() {
		return manifest.getImportedPackages();
	}

	@Override
	public void setRequiredBundleExported(String bundleName, boolean exported) {
		manifest.setRequiredBundleExported(bundleName, exported);
	}

	@Override
	public void removeRequiredBundle(String bundleName) {
		manifest.removeRequiredBundle(bundleName);
	}

	@Override
	public void removeImportedPackage(String packageName) {
		manifest.removeImportedPackage(packageName);
	}

	@Override
	public void addJavaSourceFolder(String path) {
		java.addJavaSourceFolder(path);
	}

	@Override
	public void addSourceFolderToClasspath(String folderPath) {
		java.addSourceFolderToClasspath(folderPath);
	}

	@Override
	public void addSourceFolderToClasspath(String srcPath, String binPath) {
		java.addSourceFolderToClasspath(srcPath, binPath);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor#addPackage(String, java.lang.String)
	 */
	@Override
	public IPackageFragment addPackage(String sourceFolderName, final String packageName) {
		return java.addPackage(sourceFolderName, packageName);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor#addClass(String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ICompilationUnit addClass(String sourceFolderName, final String packageName, final String className, final String classContent) {
		return java.addClass(sourceFolderName, packageName, className, classContent);
	}
	

	@Override
	public boolean isSourceFolderRegistered(String folderPath) {
		return java.isSourceFolderRegistered(folderPath);
	}

	@Override
	public String[] getBinFolders() {
		return java.getBinFolders();
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IJavaProjectEditor#getJavaProject()
	 */
	@Override
	public IJavaProject getJavaProject() {
		return java.getJavaProject();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#setFragmentHost(java.lang.String)
	 */
	@Override
	public void setFragmentHost(final String fragmentHost) {
		manifest.setFragmentHost(fragmentHost);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#setFragmentHost(java.lang.String, java.lang.String)
	 */
	@Override
	public void setFragmentHost(final String fragmentHost, final String version) {
		manifest.setFragmentHost(fragmentHost, version);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @see org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor#getFragmentHost()
	 */
	@Override
	public String getFragmentHost() {
		return manifest.getFragmentHost();
	}
	
}
