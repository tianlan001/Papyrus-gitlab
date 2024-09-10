/*****************************************************************************
 * Copyright (c) 2020, 2021 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Pauline DEVILLE (CEA LIST) <pauline.deville@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder.quickfix;

import java.io.IOException;
import java.util.Arrays;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.osgi.service.resolver.BundleSpecification;
import org.eclipse.papyrus.eclipse.project.editors.file.ManifestEditor;
import org.eclipse.papyrus.toolsmiths.plugin.builder.Activator;
import org.eclipse.papyrus.toolsmiths.plugin.builder.ManifestBuilder;
import org.eclipse.papyrus.toolsmiths.plugin.builder.helper.BundleVersionHelper;
import org.eclipse.papyrus.toolsmiths.validation.common.quickfix.AbstractPapyrusMarkerResolution;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Version;

/**
 * Quick fix to change bundle-version to a range
 */
public class DependencyRangeMarkerResolution extends AbstractPapyrusMarkerResolution {

	/**
	 * @see org.eclipse.ui.IMarkerResolution#getLabel()
	 *
	 * @return
	 */
	@Override
	public String getLabel() {
		return "Add dependency version range"; //$NON-NLS-1$
	}

	/**
	 * @see org.eclipse.ui.IMarkerResolution#run(org.eclipse.core.resources.IMarker)
	 *
	 * @param marker
	 */
	@Override
	public void run(IMarker marker) {
		String dependency = marker.getAttribute(ManifestBuilder.DEPENDENCY_MARKER_ATTRIBUTE, ""); //$NON-NLS-1$
		IResource resource = marker.getResource();
		IProject project = resource != null ? resource.getProject() : null;
		BundleVersionHelper bundleHelper = new BundleVersionHelper(dependency);
		if (project != null) {
			try {
				ManifestEditor me = new ManifestEditor(project);
				me.init();

				String version = ""; //$NON-NLS-1$
				BundleSpecification bundle = null;
				switch (marker.getAttribute(ManifestBuilder.KIND_MARKER_ATTRIBUTE, 0)) {
				case ManifestBuilder.EXCLUDE_MINIMUM_RANGE_KIND:
				case ManifestBuilder.INCLUDE_MAXIMUM_RANGE_KIND:
					// keep minimum version and maximum version but include minimum and exclude maximum : [minimum, maximum)
					bundle = getBundle(project, dependency);
					if (bundle != null) {
						Version minimumVersion = bundle.getVersionRange().getLeft();
						Version maximumVersion = bundle.getVersionRange().getRight();
						version = bundleHelper.getVersionRangeForManifest(minimumVersion, maximumVersion);
					} else {
						version = bundleHelper.getVersionRangeForManifest();
					}
					break;
				case ManifestBuilder.MAXIMUM_RANGE_MISSING_KIND:
					// keep minimum and add maximum version
					bundle = getBundle(project, dependency);
					if (bundle != null) {
						Version minimumVersion = bundle.getVersionRange().getLeft();
						Version maximumVersion = bundleHelper.getVersionRange().getRight();
						version = bundleHelper.getVersionRangeForManifest(minimumVersion, maximumVersion);
					} else {
						version = bundleHelper.getVersionRangeForManifest();
					}
					break;
				case ManifestBuilder.MISSING_VERSION_RANGE_KIND:
					// add version range
					version = bundleHelper.getVersionRangeForManifest();
					break;
				default:
					version = bundleHelper.getVersionRangeForManifest();
					break;
				}

				me.addDependency(dependency, version);
				me.save();
			} catch (IOException | CoreException e) {
				Activator.log.error(e);
			}
		}

	}

	/**
	 * @see org.eclipse.ui.IMarkerResolution2#getDescription()
	 *
	 * @return
	 */
	@Override
	public String getDescription() {
		return "Add dependency version range"; //$NON-NLS-1$
	}

	private BundleSpecification getBundle(IProject project, String dependency) {
		final IPluginModelBase pluginModelBase = PluginRegistry.findModel(project);
		BundleSpecification[] requiredBundles = pluginModelBase.getBundleDescription().getRequiredBundles();
		BundleSpecification bundle = Arrays.stream(requiredBundles).filter(b -> b.getName().equals(dependency)).findFirst().orElse(null);
		return bundle;
	}

}
