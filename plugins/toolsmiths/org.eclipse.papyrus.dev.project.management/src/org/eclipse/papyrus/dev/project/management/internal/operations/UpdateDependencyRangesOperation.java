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

import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;
import org.osgi.framework.VersionRange;

/**
 * Undoable "update dependency ranges" operation, which updates all dependencies
 * in the selected <tt.MANIFEST.MF</tt> file(s) to be version ranges lower-bounded
 * by the current PDE Target version, except for certain 3rd-party Orbit bundles
 * (such as Guava, ICU4J) that are known to increase major versions frequently without
 * breaking compatibility.
 */
public class UpdateDependencyRangesOperation extends AbstractManifestUpdateOperation {

	private final VersionRules versionRules = new VersionRules();

	public UpdateDependencyRangesOperation(Map<? extends IFile, ? extends IManifestEditor> manifests) {
		super("Update Dependency Ranges", manifests);
	}

	@Override
	protected IStatus doExecute(IProgressMonitor monitor, Map<? extends IFile, ? extends IManifestEditor> manifests) throws ExecutionException {
		SubMonitor sub = SubMonitor.convert(monitor, "Updating manifests...", manifests.size() * 2);

		for (IManifestEditor editor : manifests.values()) {
			if (sub.isCanceled()) {
				throw new OperationCanceledException();
			}

			editor.init();

			updateDependencies(editor, DependencyKind.REQUIRE_BUNDLE);
			sub.worked(1);

			if (sub.isCanceled()) {
				throw new OperationCanceledException();
			}

			updateDependencies(editor, DependencyKind.IMPORT_PACKAGE);
			sub.worked(1);
		}

		sub.done();

		return Status.OK_STATUS;
	}

	private void updateDependencies(IManifestEditor manifest, DependencyKind kind) {
		// The API interfaces for dependencies have no common supertype
		for (Object dependency : kind.getDependencies(manifest)) {
			String dependencyName = kind.getDependencyName(dependency);

			VersionRange range = versionRules.getDependencyVersionRange(kind, dependencyName);
			if (range != null) {
				kind.setDependencyRange(manifest, dependencyName, range);
			} // else this bundle needs no update
		}
	}
}
