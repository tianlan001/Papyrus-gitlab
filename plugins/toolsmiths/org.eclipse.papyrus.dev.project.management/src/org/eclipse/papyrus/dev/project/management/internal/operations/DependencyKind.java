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

package org.eclipse.papyrus.dev.project.management.internal.operations;

import java.util.List;
import java.util.function.Function;

import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;
import org.eclipse.pde.core.project.IPackageImportDescription;
import org.eclipse.pde.core.project.IRequiredBundleDescription;
import org.osgi.framework.VersionRange;

public enum DependencyKind {
	/** A <tt>Require-Bundle</tt> type of dependency. */
	REQUIRE_BUNDLE(IRequiredBundleDescription.class, IManifestEditor::getRequiredBundles, IRequiredBundleDescription::getName, IManifestEditor::addDependency),

	/** An <tt>Import-Package</tt> type of dependency. */
	IMPORT_PACKAGE(IPackageImportDescription.class, IManifestEditor::getImportedPackages, IPackageImportDescription::getName, IManifestEditor::addImportPackage);

	private final Function<IManifestEditor, List<?>> dependenciesFunction;

	private final Function<?, String> dependencyNameFunction;

	private final DependencyUpdater dependencyUpdater;

	private <T> DependencyKind(Class<T> dependencyType, Function<IManifestEditor, List<?>> dependenciesFunction, Function<T, String> dependencyNameFunction, DependencyUpdater dependencyUpdater) {
		this.dependenciesFunction = dependenciesFunction;
		this.dependencyNameFunction = dependencyNameFunction;
		this.dependencyUpdater = dependencyUpdater;
	}

	String getVersionAttribute() {
		return (this == REQUIRE_BUNDLE)
				? "bundle-version" //$NON-NLS-1$
				: "version"; //$NON-NLS-1$
	}

	static DependencyKind forHeader(String headerName) {
		switch (headerName) {
		case "Require-Bundle":
			return REQUIRE_BUNDLE;
		case "Import-Package":
			return IMPORT_PACKAGE;
		default:
			throw new IllegalArgumentException(headerName);
		}
	}

	public List<?> getDependencies(IManifestEditor manifest) {
		return dependenciesFunction.apply(manifest);
	}

	@SuppressWarnings("unchecked")
	public String getDependencyName(Object dependency) {
		return ((Function<Object, String>) dependencyNameFunction).apply(dependency);
	}

	public void setDependencyRange(IManifestEditor manifest, String dependencyName, VersionRange range) {
		dependencyUpdater.setDependencyRange(manifest, dependencyName, range.toString());
	}

	//
	// Nested types
	//

	@FunctionalInterface
	private interface DependencyUpdater {
		void setDependencyRange(IManifestEditor manifest, String dependencyName, String versionRange);
	}
}