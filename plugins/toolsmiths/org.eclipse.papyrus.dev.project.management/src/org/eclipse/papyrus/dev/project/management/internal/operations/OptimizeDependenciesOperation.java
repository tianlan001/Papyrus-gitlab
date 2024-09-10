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
import java.util.Map;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.papyrus.dev.project.management.internal.operations.DependencyAnalysisContext.APIExports;
import org.eclipse.papyrus.dev.project.management.internal.operations.DependencyAnalysisContext.BundleAnalysis;
import org.eclipse.papyrus.dev.project.management.internal.operations.DependencyAnalysisContext.DependencyGraph;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;
import org.eclipse.papyrus.infra.tools.util.Iterables2;
import org.eclipse.pde.core.project.IRequiredBundleDescription;

/**
 * Undoable "optimize dependencies" operation, which ensures re-exports of dependencies
 * that contribute types to the exported API and removes redundancies (except where they are
 * re-exported).
 */
public class OptimizeDependenciesOperation extends AbstractManifestUpdateOperation {

	public OptimizeDependenciesOperation(Map<? extends IFile, ? extends IManifestEditor> manifests) {
		super("Optimize Bundle Dependencies", manifests);
	}

	@Override
	protected IStatus doExecute(IProgressMonitor monitor, Map<? extends IFile, ? extends IManifestEditor> manifests) throws ExecutionException {
		SubMonitor sub = SubMonitor.convert(monitor, "Analyzing ...", manifests.size() + 1);

		sub.split(1).beginTask("Initializing", IProgressMonitor.UNKNOWN);

		DependencyAnalysisContext analysisContext = new DependencyAnalysisContext(manifests.keySet());

		// This map is sorted from root to leaves of the dependency tree
		List<BundleAnalysis> bundles = Iterables2.topoSort(
				analysisContext.getAnalysisRoots(),
				BundleAnalysis::partialCompare);

		for (BundleAnalysis bundle : bundles) {
			IManifestEditor editor = manifests.get(bundle.getManifest());

			SubMonitor step = sub.split(1);

			if (step.isCanceled()) {
				throw new OperationCanceledException();
			}

			editor.init();
			optimizeDependencies(bundle, editor, step);
		}

		return Status.OK_STATUS;
	}

	private void optimizeDependencies(BundleAnalysis bundle, IManifestEditor manifest, IProgressMonitor monitor) {
		monitor.beginTask(bundle.getBundleID(), IProgressMonitor.UNKNOWN);
		bundle.pushMonitor(monitor);

		try {
			DependencyGraph graph = bundle.getDependencyGraph();
			APIExports api = bundle.getAPIExports();

			for (IRequiredBundleDescription requireBundle : manifest.getRequiredBundles()) {
				String required = requireBundle.getName();

				if (graph.isRedundant(required)) {
					// Remove it from the manifest
					manifest.removeRequiredBundle(required);

					// Update the graph
					graph.removeDependency(required);
				} else if (api.isExposed(required) && !requireBundle.isExported()) {
					// We need to retain this dependency and ensure that it is re-exported,
					// unless it is already re-exported by some other dependency that we re-export
					// (which would be the redundant case, above)
					manifest.setRequiredBundleExported(required, true);

					// Update the graph
					graph.reexport(required);
				} // else just retain this dependency as is
			}
		} finally {
			bundle.popMonitor();
		}
	}
}
