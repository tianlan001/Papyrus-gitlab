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
import org.eclipse.papyrus.dev.project.management.Activator;
import org.eclipse.papyrus.dev.project.management.internal.operations.DependencyAnalysisContext.BundleAnalysis;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;
import org.eclipse.papyrus.infra.tools.util.Iterables2;

/**
 * Undoable "set singleton" operation
 */
public class SingletonBundle extends AbstractManifestUpdateOperation {

	public SingletonBundle(Map<? extends IFile, ? extends IManifestEditor> manifests) {
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

			if (editor != null) {
				editor.init();

				editor.setSingleton(true);
				editor.save();
			} else {
				Activator.log.warn("Null editor: " + bundle.getBundleID());
			}
		}

		return Status.OK_STATUS;
	}
}
