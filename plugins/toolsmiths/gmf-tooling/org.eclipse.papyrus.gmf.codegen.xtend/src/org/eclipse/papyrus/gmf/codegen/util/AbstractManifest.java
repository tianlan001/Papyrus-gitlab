/*****************************************************************************
 * Copyright (c) 2021 CEA LIST and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 * 	Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.gmf.codegen.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.eclipse.project.editors.file.ManifestEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;
import org.eclipse.pde.core.project.IPackageImportDescription;
import org.eclipse.pde.core.project.IRequiredBundleDescription;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

/**
 *
 * Abstract class used to read previous contents of the manifest and preserve it at each re-generation
 *
 */
public abstract class AbstractManifest {

	/**
	 * the exported packages defined in the manifest before the regeneration
	 */
	private final Collection<String> existingExportedPackages = new TreeSet<>();

	/**
	 * the dependencies defined in the manifest before the regeneration
	 */
	private final TreeMap<String, IRequiredBundleDescription> existingBundleDependencies = new TreeMap<>();

	/**
	 * the imported packages defined in the manifest before the regeneration
	 */
	private final TreeMap<String, IPackageImportDescription> existingImportPackages = new TreeMap<>();

	/**
	 * the previously existing bundle description
	 */
	private String bundleDescription = null;
	/**
	 * boolean indicating if we eclipse lazy start instruction is present
	 */
	private boolean isEclipseLazyStart = false;

	/**
	 * the edited project
	 */
	private IProject project;


	/**
	 * This method inits the field of the class
	 *
	 * @param projectName
	 *            the name of the project for which we are generating a manifest
	 */
	protected void init(final String projectName) {
		if (projectName != null && !projectName.isEmpty()) {
			this.project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			initFields();
		}
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if a bundle description exists
	 */
	protected boolean hasBundleDescription() {
		return this.bundleDescription != null && !this.bundleDescription.isEmpty();
	}

	/**
	 *
	 * @return
	 *         the bundle description
	 */
	protected String getBundleDescription() {
		return this.bundleDescription;
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the plugin is lazy start
	 */
	protected boolean isEclipseLazyStart() {
		return this.isEclipseLazyStart;
	}


	/**
	 * Fills the field
	 */
	private void initFields() {
		ManifestEditor manifestEditor;
		try {
			manifestEditor = new ManifestEditor(project);
			manifestEditor.init();
			initExportPackageField(manifestEditor);
			initRequiredBundleField(manifestEditor);
			initImportedPackageField(manifestEditor);
			initBundleDescription(manifestEditor);
			initEclipseLazyStart(manifestEditor);
		} catch (Exception e) {
			// silent exception
		}
	}

	/**
	 * Fills the field {@link #existingExportedPackages}
	 *
	 * @param editor
	 *            the manifest editor
	 */
	private void initExportPackageField(final IManifestEditor editor) {
		final String exportedPackage = editor.getValue("Export-Package"); //$NON-NLS-1$
		if (exportedPackage != null) {
			final String[] packages = exportedPackage.split(","); //$NON-NLS-1$
			this.existingExportedPackages.addAll(Arrays.asList(packages));
		}
	}

	/**
	 * Fills the field {@link #existingBundleDependencies}
	 *
	 * @param editor
	 *            the manifest editor
	 */
	private void initRequiredBundleField(final IManifestEditor editor) {
		final Iterator<IRequiredBundleDescription> bundleIter = editor.getRequiredBundles().iterator();
		while (bundleIter.hasNext()) {
			final IRequiredBundleDescription current = bundleIter.next();
			this.existingBundleDependencies.put(current.getName(), current);
		}
	}

	/**
	 * Fills the field {@link #existingImportPackages}
	 *
	 * @param editor
	 *            the manifest editor
	 */
	private void initImportedPackageField(final IManifestEditor editor) {
		final Iterator<IPackageImportDescription> importedPackageIter = editor.getImportedPackages().iterator();
		while (importedPackageIter.hasNext()) {
			final IPackageImportDescription current = importedPackageIter.next();
			this.existingImportPackages.put(current.getName(), current);
		}
	}

	/**
	 * Init the field {@link #bundleDescription}
	 *
	 * @param editor
	 *            the manifest editor
	 */
	private void initBundleDescription(final IManifestEditor editor) {
		this.bundleDescription = editor.getValue("Bundle-Description"); //$NON-NLS-1$
	}

	/**
	 * Fills the field {@link #isEclipseLazyStart}
	 *
	 * @param editor
	 *            the manifest editor
	 */
	private void initEclipseLazyStart(final IManifestEditor editor) {
		final String value = editor.getValue("Eclipse-LazyStart"); //$NON-NLS-1$
		if (value != null && Boolean.valueOf(value)) {
			this.isEclipseLazyStart = true;
		} else {
			this.isEclipseLazyStart = false;
		}
	}

	/**
	 * Reuse the existing declared version range or create a new one
	 *
	 * @param bundleID
	 *            the id of a bundle
	 * @return
	 *         the bundle version declaration for it (bundle-Version="[a.b.0,a+1.0.0)"
	 */
	protected String getOrCreateBundleVersionRange(final String bundleID) {
		final IRequiredBundleDescription bundleDesc = this.existingBundleDependencies.get(bundleID);
		final StringBuilder builder = new StringBuilder("bundle-version=\"["); //$NON-NLS-1$
		String minVersion = null;
		String maxVersion = null;
		Version leftVersion = null;
		Version rightVersion = null;

		if (bundleDesc != null) {
			final VersionRange range = bundleDesc.getVersionRange();
			leftVersion = range != null ? range.getLeft() : null;
			rightVersion = range != null ? range.getRight() : null;
		}

		if (leftVersion != null) {
			minVersion = leftVersion.toString();
		} else {
			final Bundle bundle = Platform.getBundle(bundleID);
			leftVersion = bundle.getVersion();
			minVersion = buildVersion(leftVersion.getMajor(), leftVersion.getMinor(), 0);
		}

		if (rightVersion != null) {
			maxVersion = rightVersion.toString();
		} else {
			maxVersion = buildVersion(leftVersion.getMajor() + 1, 0, 0);
		}
		builder.append(minVersion);
		builder.append(","); //$NON-NLS-1$
		builder.append(maxVersion);
		builder.append(")\""); //$NON-NLS-1$
		return builder.toString();
	}

	/**
	 *
	 * Build a String representing a version like a.B.c
	 *
	 * @param major
	 * @param minor
	 * @param micro
	 * @return
	 */
	private final String buildVersion(int major, int minor, int micro) {
		var builder = new StringBuilder();
		builder.append(major);
		builder.append("."); //$NON-NLS-1$
		builder.append(minor);
		builder.append("."); //$NON-NLS-1$
		builder.append(micro);
		return builder.toString();
	}

	/**
	 *
	 * @return
	 *         the list of required dependencies (concatenation of already declared dependencies + dependencies required by the code generation)
	 */
	protected Collection<String> getRequiredDependencies() {
		Set<String> dependencies = new TreeSet<>();
		dependencies.addAll(getGMFGenerationDependencies());
		dependencies.addAll(existingBundleDependencies.keySet());
		return dependencies;
	}

	/**
	 *
	 * @return
	 *         the list of bundle required by the generated code
	 */
	private Collection<String> getGMFGenerationDependencies() {
		Collection<String> dependencies = new ArrayList<>();
		dependencies.add("com.google.guava"); //$NON-NLS-1$
		dependencies.add("org.eclipse.core.expressions"); //$NON-NLS-1$
		dependencies.add("org.eclipse.emf.databinding"); //$NON-NLS-1$
		dependencies.add("org.eclipse.gmf.runtime.diagram.ui.properties"); //$NON-NLS-1$
		dependencies.add("org.eclipse.gmf.runtime.diagram.ui.resources.editor.ide"); //$NON-NLS-1$
		dependencies.add("org.eclipse.ocl.ecore"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.architecture"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.core.architecture"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.core.log"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.core.sasheditor"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.core.sashwindows.di"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.core"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.emf.appearance"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.emf.gmf"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.emf"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.gmfdiag.commands"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.gmfdiag.common"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.gmfdiag.extensionpoints.editors"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.gmfdiag.hyperlink"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.gmfdiag.preferences"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.gmfdiag.tooling.runtime"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.hyperlink"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.internationalization.common"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.internationalization.utils"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.services.edit"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.services.viewersearch"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.ui"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.viewpoints.policy"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.infra.widgets"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.uml.appearance"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.uml.diagram.common"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.uml.internationalization.utils"); //$NON-NLS-1$
		dependencies.add("org.eclipse.papyrus.uml.tools.utils"); //$NON-NLS-1$
		dependencies.add("org.eclipse.ui.navigator.resources"); //$NON-NLS-1$
		dependencies.add("org.eclipse.ui.navigator"); //$NON-NLS-1$
		dependencies.add("org.eclipse.uml2.uml.editor"); //$NON-NLS-1$
		return dependencies;
	}

	/**
	 *
	 * @return
	 *         the list of declare exported packages
	 */
	protected final Collection<String> getExistingExportedPackages() {
		return new TreeSet<>(this.existingExportedPackages);
	}

	/**
	 *
	 * @return
	 *         the string to use to declare imported packages
	 */
	protected final String declareImportPackage() {
		final StringBuilder builder = new StringBuilder();
		if (!this.existingImportPackages.isEmpty()) {
			final Iterator<Entry<String, IPackageImportDescription>> iter = this.existingImportPackages.entrySet().iterator();
			builder.append("Import-Package:"); //$NON-NLS-1$
			while (iter.hasNext()) {
				final Entry<String, IPackageImportDescription> current = iter.next();
				builder.append(" "); //$NON-NLS-1$
				builder.append(current.getKey());
				final IPackageImportDescription packDesc = current.getValue();
				final String vRange = packDesc.getVersionRange().toString();
				if (vRange != null) {
					builder.append(";version=\""); //$NON-NLS-1$
					builder.append(vRange);
					builder.append("\""); //$NON-NLS-1$
				}
				if (iter.hasNext()) {
					builder.append(","); //$NON-NLS-1$
				}
				builder.append("\n"); //$NON-NLS-1$
			}
		}
		return builder.toString();
	}

	/**
	 *
	 * @return
	 *         the string to use to declare required bundles
	 */
	protected final String buildRequiredBundle() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Require-Bundle:"); //$NON-NLS-1$
		Iterator<String> iter = getRequiredDependencies().iterator();
		while (iter.hasNext()) {
			var next = iter.next();
			builder.append(" "); //$NON-NLS-1$
			builder.append(next);
			builder.append(";"); //$NON-NLS-1$
			builder.append(getOrCreateBundleVersionRange(next));
			if (iter.hasNext()) {
				builder.append(","); //$NON-NLS-1$
			}
			builder.append("\n"); //$NON-NLS-1$
		}
		return builder.toString();
	}
}
