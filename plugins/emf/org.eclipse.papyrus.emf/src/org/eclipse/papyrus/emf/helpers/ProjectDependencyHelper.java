/*****************************************************************************
 * Copyright (c) 2020 CEA LIST, Christian W. Damus, and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Vincent Lorenzo (CEA LIST) <vincent.lorenzo@cea.fr> - Initial API and implementation
 *    Christian W. Damus - bug 569357
 *
 *****************************************************************************/

package org.eclipse.papyrus.emf.helpers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.osgi.service.resolver.BundleSpecification;
import org.eclipse.pde.core.plugin.IPluginModelBase;
import org.eclipse.pde.core.plugin.PluginRegistry;

/**
 * Helper used to manipulate dependencies
 * TODO : move this class ? (pde.core dependency could not be wanted in this project??)
 */
public class ProjectDependencyHelper {


	public static final ProjectDependencyHelper INSTANCE = new ProjectDependencyHelper();

	/**
	 *
	 * Constructor.
	 *
	 */
	private ProjectDependencyHelper() {
		// to prevent instanciation
	}

	public Collection<String> getAllAvailableDependencies(final String bundleName) {
		final Set<String> allDependencies = new TreeSet<>();
		final Collection<String> declaredDependencies = getDeclaredPluginDependencies(bundleName);
		for (final String current : declaredDependencies) {
			allDependencies.add(current);// of course
			allDependencies.addAll(getInheritedBundle(current));
		}

		return allDependencies;
	}

	/**
	 *
	 * @param project
	 *            a project
	 * @return
	 *         the list of current declared dependencies in the project
	 */
	public Collection<String> getDeclaredPluginDependencies(final String bundleName) {
		final Set<String> currentDeclaredDependencies = new HashSet<>();
		final IPluginModelBase pluginModelBase = PluginRegistry.findModel(bundleName);
		for (final BundleSpecification requiredBundle : pluginModelBase.getBundleDescription().getRequiredBundles()) {
			currentDeclaredDependencies.add(requiredBundle.getName());
		}
		return currentDeclaredDependencies;
	}


	public Collection<String> getInheritedBundle(final String bundleName) {
		final Set<String> currentDeclaredDependencies = new HashSet<>();
		final IPluginModelBase pluginModelBase = PluginRegistry.findModel(bundleName);
		if (pluginModelBase != null) { // The bundle may not exist
			for (BundleSpecification spec : pluginModelBase.getBundleDescription().getRequiredBundles()) {
				if (spec.isExported()) {
					final String bundleSymbolicName = spec.getName();
					currentDeclaredDependencies.add(bundleSymbolicName);
					currentDeclaredDependencies.addAll(getInheritedBundle(bundleSymbolicName));
				}
			}
		}

		return currentDeclaredDependencies;
	}



	/**
	 *
	 * @param projectName
	 *            the name of a project
	 * @param wantedDependency
	 *            a dependency identified by its name
	 * @return
	 *         <code>true</code> if the project depends on this dependency and <code>false</code> otherwise of if the project can't be found
	 */
	public boolean hasDependency(final String projectName, final String wantedDependency) {
		return getAllAvailableDependencies(projectName).contains(wantedDependency);
	}
}
