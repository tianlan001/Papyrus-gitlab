/*****************************************************************************
 * Copyright (c) 2020 CEA LIST and others.
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
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.plugin.builder;

import java.util.Map;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.osgi.service.resolver.BundleSpecification;
import org.eclipse.osgi.service.resolver.VersionRange;
import org.eclipse.papyrus.toolsmiths.plugin.builder.preferences.PluginBuilderPreferencesConstants;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;
import org.osgi.framework.Version;

/**
 * This builder check if the manifest follow papyrus's coding rules:
 * - required bundles should not be reexported
 * - bundle version should be specified using a range
 */
public class ManifestBuilder extends AbstractPapyrusBuilder {

	public static final String DEPENDENCY_MARKER_ATTRIBUTE = "dependency"; //$NON-NLS-1$

	public static final String KIND_MARKER_ATTRIBUTE = "kind"; //$NON-NLS-1$

	public static final int REEXPORT_KIND = 0;

	public static final int MISSING_VERSION_RANGE_KIND = REEXPORT_KIND + 1;

	public static final int MAXIMUM_RANGE_MISSING_KIND = MISSING_VERSION_RANGE_KIND + 1;

	public static final int EXCLUDE_MINIMUM_RANGE_KIND = MAXIMUM_RANGE_MISSING_KIND + 1;

	public static final int INCLUDE_MAXIMUM_RANGE_KIND = EXCLUDE_MINIMUM_RANGE_KIND + 1;

	/**
	 * @see org.eclipse.papyrus.toolsmiths.plugin.builder.AbstractPapyrusBuilder#build(org.eclipse.core.resources.IProject, org.eclipse.papyrus.toolsmiths.plugin.builder.PapyrusPluginBuilder, int, java.util.Map, org.eclipse.core.runtime.IProgressMonitor)
	 *
	 * @param builtProject
	 * @param papyrusBuilder
	 * @param kind
	 * @param args
	 * @param monitor
	 * @return
	 * @throws CoreException
	 */
	@Override
	public IProject[] build(IProject builtProject, PapyrusPluginBuilder papyrusBuilder, int kind, Map<String, String> args, IProgressMonitor monitor) throws CoreException {
		final IPluginModelBase pluginModelBase = PluginRegistry.findModel(builtProject);
		BundleSpecification[] requiredBundles = pluginModelBase.getBundleDescription().getRequiredBundles();
		String message = ""; //$NON-NLS-1$

		for (BundleSpecification bundle : requiredBundles) {
			if (bundle.isExported() && isCheckReexportActivated()) {
				message = bundle.getName() + " : this bundle should not be reexported"; //$NON-NLS-1$
				createErrorMarker(pluginModelBase, bundle, message, REEXPORT_KIND);
			}

			if (isCheckDependencyRangeActivated()) {
				VersionRange versionRange = bundle.getVersionRange();
				if (versionRange != null) {
					if (versionRange.isExact()) {
						message = bundle.getName() + " : we should define range as bundle version"; //$NON-NLS-1$
						createErrorMarker(pluginModelBase, bundle, message, MISSING_VERSION_RANGE_KIND);
					} else {
						if (false == versionRange.getIncludeMinimum()) {
							message = bundle.getName() + " : we should include the minimum bundle version -> ["; //$NON-NLS-1$
							createErrorMarker(pluginModelBase, bundle, message, EXCLUDE_MINIMUM_RANGE_KIND);
						}
						if (true == versionRange.getIncludeMaximum()) {
							message = bundle.getName() + " : we should exclude the maximum bundle version -> )"; //$NON-NLS-1$
							createErrorMarker(pluginModelBase, bundle, message, INCLUDE_MAXIMUM_RANGE_KIND);
						}
						if (versionRange.getLeft() == null || versionRange.getLeft().equals(Version.emptyVersion)) {
							message = bundle.getName() + " : we should define a minimum bundle version"; //$NON-NLS-1$
							createErrorMarker(pluginModelBase, bundle, message, MISSING_VERSION_RANGE_KIND);

						} else if (versionRange.getRight() == null) {
							message = bundle.getName() + " : we should define a maximum bundle version"; //$NON-NLS-1$
							createErrorMarker(pluginModelBase, bundle, message, MAXIMUM_RANGE_MISSING_KIND);
						}
					}
				}
			}
		}

		return null;
	}

	private void createErrorMarker(IPluginModelBase pluginModelBase, BundleSpecification bundle, String message, int versionRangeMissingKind) {
		IResource manifest = pluginModelBase.getUnderlyingResource();
		IMarker marker = createErrorMarker(manifest, message);
		try {
			marker.setAttribute(DEPENDENCY_MARKER_ATTRIBUTE, bundle.getName());
			marker.setAttribute(KIND_MARKER_ATTRIBUTE, versionRangeMissingKind);
		} catch (CoreException e) {
			Activator.log.error(e);
		}
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the check of the reexport is activated
	 */
	private boolean isCheckReexportActivated() {
		return Activator.getDefault().getPreferenceStore().getBoolean(PluginBuilderPreferencesConstants.PAPYRUS_MANIFEST_BUILDER_CHECK_NO_REEXPORT);
	}

	/**
	 *
	 * @return
	 *         <code>true</code> if the check of dependency range is activated
	 */
	private boolean isCheckDependencyRangeActivated() {
		return Activator.getDefault().getPreferenceStore().getBoolean(PluginBuilderPreferencesConstants.PAPYRUS_MANIFEST_BUILDER_CHECK_DEPENDENCY_RANGE);

	}

}