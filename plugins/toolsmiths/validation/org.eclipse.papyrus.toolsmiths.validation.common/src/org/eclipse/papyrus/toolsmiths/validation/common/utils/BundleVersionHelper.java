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
 *   Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *   Christian W. Damus - bug 570097
 *
 *****************************************************************************/

package org.eclipse.papyrus.toolsmiths.validation.common.utils;

import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.papyrus.eclipse.project.editors.file.ManifestEditor;
import org.eclipse.papyrus.toolsmiths.validation.common.Activator;
import org.osgi.framework.Bundle;
import org.osgi.framework.Version;
import org.osgi.framework.VersionRange;

/**
 * This class allows
 * <ul>
 * <li>to get the current version of a bundle</li>
 * <li>to calculate the dependency range to declare in the manifest</li>
 * </ul>
 */
public class BundleVersionHelper {

	/**
	 * the name of the bundle for which we are manipulating dependencies
	 */
	private final String bundleName;

	/**
	 *
	 * Constructor.
	 *
	 * @param bundleName
	 *            the name of the bundle
	 */
	public BundleVersionHelper(final String bundleName) {
		this.bundleName = bundleName;
	}

	/**
	 *
	 * @return
	 *         an array containing 2 Version, representing the version range to declare.
	 *         Version[0] -> minVersion under the form x.y.0
	 *         Version[1] -> maxVersion under the form x+1.0.0
	 */
	public final VersionRange getVersionRange() {
		final Version moreUpperVersion = getMoreUpperBundleVersion();
		final Version minVersion = new Version(moreUpperVersion.getMajor(), moreUpperVersion.getMinor(), 0);
		final Version maxVersion = new Version(moreUpperVersion.getMajor() + 1, 0, 0);
		return new VersionRange(VersionRange.LEFT_CLOSED, minVersion, maxVersion, VersionRange.RIGHT_OPEN);
	}

	/**
	 *
	 * @return
	 *         the string to declare in manifest to define the version range of the bundle
	 */
	public final String getVersionRangeForManifest() {
		VersionRange ranges = getVersionRange();
		return ranges.toString();
	}

	/**
	 * @param minimumVersion
	 *            the minimum version
	 * @param maximumVersion
	 *            the maximum version
	 * @return the string to declare in manifest to define the version range of the bundle
	 */
	public final String getVersionRangeForManifest(Version minimumVersion, Version maximumVersion) {
		return new VersionRange(VersionRange.LEFT_CLOSED, minimumVersion, maximumVersion, VersionRange.RIGHT_OPEN).toString();
	}

	/**
	 *
	 * @return
	 *         the upper version between workspace and installed bundle or Version0.0.0) if there is no available version (bundle not found)
	 */
	protected Version getMoreUpperBundleVersion() {
		final Version workspaceVersion = getWorkspaceBundleVersion();
		final Version installedVersion = getInstalledBundleVersion();
		Version versionToUse = new Version(0, 0, 0);
		if (workspaceVersion != null && installedVersion != null) {
			if (installedVersion.compareTo(workspaceVersion) >= 0) {
				versionToUse = workspaceVersion;
			}
		} else if (workspaceVersion == null && installedVersion != null) {
			versionToUse = installedVersion;
		} else if (workspaceVersion != null && installedVersion == null) {
			versionToUse = workspaceVersion;
		}
		return versionToUse;
	}

	/**
	 *
	 * @return
	 *         the version of the bundle in the workspace or <code>null</code> if not found
	 */
	public final Version getWorkspaceBundleVersion() {
		Version version = null;
		final IProject currentProject = ResourcesPlugin.getWorkspace().getRoot().getProject(this.bundleName);
		if (currentProject != null && currentProject.exists() && currentProject.isOpen()) {
			ManifestEditor editor;
			try {
				editor = new ManifestEditor(currentProject);
				editor.init();
				final String currentVersion = editor.getBundleVersion();
				version = new Version(currentVersion);

			} catch (IOException e) {
				Activator.log.error(e);
			} catch (CoreException e) {
				Activator.log.error(e);
			}
		}
		return version;

	}

	/**
	 *
	 * @return
	 *         the version of the bundle in the installation or <code>null</code> if not found
	 */
	public final Version getInstalledBundleVersion() {
		final Bundle bundle = Platform.getBundle(this.bundleName);
		if (bundle != null) {
			return bundle.getVersion();
		}
		return null;
	}

}
