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
 *  Camille Letavernier (CEA LIST) camille.letavernier@cea.fr
 *  Christian W. Damus - bug 485220
 *  Vincent Lorenzo (CEA LIST) - bug 525876
 *****************************************************************************/
package org.eclipse.papyrus.eclipse.project.editors.interfaces;

import java.util.List;

import org.eclipse.pde.core.project.IPackageImportDescription;
import org.eclipse.pde.core.project.IRequiredBundleDescription;

/**
 *
 * Editor for the Manifest
 * 
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IManifestEditor extends IProjectEditor, IFileEditor {

	/** the path for the manifest file */
	public static final String MANIFEST_PATH = "META-INF/MANIFEST.MF"; //$NON-NLS-1$

	/** the path for the META-INF folder */
	public static final String META_INF_PATH = "META-INF/"; //$NON-NLS-1$

	/** the key for the bundle symbolic name */
	public static final String BUNDLE_SYMBOLIC_NAME = "Bundle-SymbolicName"; //$NON-NLS-1$

	/** the key for the bundle name */
	public static final String BUNDLE_NAME = "Bundle-Name";//$NON-NLS-1$

	/** the key for the required bundle */
	public static final String REQUIRED_BUNDLE = "Require-Bundle"; //$NON-NLS-1$

	/** the key for the bundle version */
	public static final String BUNDLE_VERSION = "Bundle-Version"; //$NON-NLS-1$

	/** the key for the bundle vendor */
	public static final String BUNDLE_VENDOR = "Bundle-Vendor"; //$NON-NLS-1$

	/** the build command for the manifest */
	public static final String MANIFEST_BUILD_COMMAND = "org.eclipse.pde.ManifestBuilder"; //$NON-NLS-1$

	/** the key for the bundle localization */
	public static final String BUNDLE_LOCALIZATION = "Bundle-Localization"; //$NON-NLS-1$

	/**
	 * Add a dependency to the MANIFEST
	 * 
	 * @param dependency
	 *            the dependency to add
	 */
	public void addDependency(final String dependency);

	/**
	 * Add a specific version of a dependency to the MANIFEST
	 *
	 * @param dependency
	 *            the dependency to add
	 * @param version
	 *            the version of the dependency
	 */
	public void addDependency(final String dependency, final String version);

	/**
	 * Check whether a dependency is already present in the MANIFEST
	 *
	 * @param dependency
	 *            the dependency to check
	 */
	public boolean hasDependency(final String dependency);

	/**
	 * Sets the value of a header in the main section of the manifest.
	 * 
	 * @param name
	 *            the header name
	 * @param value
	 *            the new value for the header
	 */
	public void setValue(final String name, final String value);

	/**
	 * Queries the value of a header in the main section of the manifest.
	 * 
	 * @param name
	 *            the header name
	 * @return the value of the header, {@code null} if none
	 */
	public String getValue(final String name);

	/**
	 * Sets the value of an attribute in a named section of the manifest.
	 * 
	 * @param key
	 *            the key (section name)
	 * @param name
	 *            the name of the attribute
	 * @param value
	 *            the new value for the attribute
	 */
	public void setValue(final String key, final String name, final String value);

	/**
	 * Queries the value of a header in a named section of the manifest.
	 * 
	 * @param key
	 *            the key (section name)
	 * @param name
	 *            the header (attribute) name to set
	 * @return the value of the header, {@code null} if none
	 * 
	 * @since 2.0
	 */
	public String getValue(final String key, String name);

	/**
	 * Removes a header from the main section of the manifest
	 * 
	 * @param key
	 *            the header to remove from the manifest
	 *
	 */
	public void removeValue(final String key);

	/**
	 * Removes an attribute in a named section of the manifest.
	 * 
	 * @param key
	 *            the key (section name)
	 * @param name
	 *            the attribute to remove from this section
	 */
	public void removeValue(final String key, final String name);

	/**
	 *
	 * @param name
	 *            the symbolic name for the bundle
	 */
	public void setSymbolicBundleName(final String name);


	/**
	 *
	 * @return
	 * 		the bundle name for the project
	 */
	public String getSymbolicBundleName();

	/**
	 *
	 * @return
	 * 		the symbolic bundle name for the project
	 */
	public String getBundleName();

	/**
	 *
	 * @param name
	 *            the bundle name for the bundle
	 */
	public void setBundleName(final String name);

	/**
	 *
	 * @return
	 * 		the version of the bundle
	 */
	public String getBundleVersion();

	/**
	 * Set the version of the bundle
	 */
	public void setBundleVersion(final String version);

	/**
	 * Gets this plug-in's provider
	 *
	 * @return
	 * 		this plug-in's provider
	 */
	public String getBundleVendor();

	/**
	 * Sets this plug-in's provider
	 *
	 * @param vendor
	 *            this plug-in's provider
	 */
	public void setBundleVendor(final String vendor);

	/**
	 * Get the bundle localization
	 *
	 * @return
	 * 		the bundle localization
	 */
	public String getBundleLocalization();

	/**
	 * Sets the Manifest's singleton directive
	 *
	 * @param singleton
	 *            Whether this plug-in should be a singleton
	 */
	public void setSingleton(boolean singleton);

	/**
	 *
	 * @param dependencyPattern
	 *            the pattern for the dependency to update
	 * @param newVersion
	 *            the version for the dependency
	 * 
	 * @deprecated Bulk updates of the bundle dependencies are no longer policy since release 2.0.
	 */
	@Deprecated
	public void setDependenciesVersion(final String dependencyPattern, final String newVersion);

	/**
	 * Adds an import package to the MANIFEST.
	 *
	 * @param packageName
	 *            the package name to add
	 * @since 2.0
	 */
	public void addImportPackage(String packageName);

	/**
	 * Adds an import package and its version to the MANIFEST.
	 *
	 * @param packageName
	 *            the package name to add
	 * @param version
	 *            the package version
	 * @since 2.0
	 */
	public void addImportPackage(String packageName, String version);

	/**
	 * Adds an export package to the MANFIEST.
	 *
	 * @param packageName
	 *            the package name to add
	 * @since 2.0
	 */
	public void addExportPackage(String packageName);

	/**
	 * Adds an export package and its version to the MANIFEST.
	 *
	 * @param packageName
	 *            the package name to add
	 * @param version
	 *            the package version
	 * @since 2.0
	 */
	public void addExportPackage(String packageName, String version);

	/**
	 * Queries the existing bundle requirements.
	 * 
	 * @return the existing <tt>Require-Bundle</tt> dependencies
	 * 
	 * @since 2.0
	 */
	public List<IRequiredBundleDescription> getRequiredBundles();

	/**
	 * Queries the existing package imports.
	 * 
	 * @return the existing <tt>Import-Package</tt> dependencies
	 * 
	 * @since 2.0
	 */
	public List<IPackageImportDescription> getImportedPackages();

	/**
	 * Sets whether a <tt>Require-Bundle</tt> dependency is re-exported.
	 * 
	 * @param bundleName
	 *            the required bundle
	 * @param exported
	 *            whether the required bundle is re-exported
	 * 
	 * @since 2.0
	 */
	public void setRequiredBundleExported(String bundleName, boolean exported);

	/**
	 * Removes a <tt>Require-Bundle</tt> dependency.
	 * 
	 * @param bundleName
	 *            the required bundle to remove
	 * 
	 * @since 2.0
	 */
	public void removeRequiredBundle(String bundleName);

	/**
	 * Removes an <tt>Import-package</tt> dependency.
	 * 
	 * @param packageName
	 *            the imported package to remove
	 * 
	 * @since 2.0
	 */
	public void removeImportedPackage(String packageName);

	/**
	 * Set the fragment host of the bundle.
	 * 
	 * @param name
	 *            The fragment host for the bundle
	 * @since 2.1
	 */
	public void setFragmentHost(final String fragmentHost);

	/**
	 * Set the fragment host of the bundle.
	 * 
	 * @param name
	 *            The fragment host for the bundle
	 * @param version
	 *            The fragment version
	 * @since 2.1
	 */
	public void setFragmentHost(final String fragmentHost, final String version);


	/**
	 * Get the fragment host for the bundle.
	 * 
	 * @return
	 * 		The fragment host of the bundle.
	 * @since 2.1
	 */
	public String getFragmentHost();
}
