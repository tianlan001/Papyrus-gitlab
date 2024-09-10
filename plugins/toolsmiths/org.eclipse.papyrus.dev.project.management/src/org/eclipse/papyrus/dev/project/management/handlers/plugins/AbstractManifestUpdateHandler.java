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

package org.eclipse.papyrus.dev.project.management.handlers.plugins;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.IOperationHistory;
import org.eclipse.core.commands.operations.IUndoableOperation;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.IManifestEditor;
import org.eclipse.papyrus.eclipse.project.editors.interfaces.ProjectEditors;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.operations.IWorkbenchOperationSupport;
import org.eclipse.ui.progress.IProgressService;

/**
 * Handler for the "update dependency ranges" command, which updates all dependencies
 * in the selected <tt.MANIFEST.MF</tt> file(s) to be version ranges lower-bounded
 * by the current PDE Target version, except for certain 3rd-party Orbit bundles
 * (such as Guava, ICU4J) that are known to increase major versions frequently without
 * breaking compatibility.
 */
abstract class AbstractManifestUpdateHandler extends AbstractHandler {

	public AbstractManifestUpdateHandler() {
		super();
	}

	protected abstract IUndoableOperation createUpdateOperation(Map<? extends IFile, ? extends IManifestEditor> manifests);

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		List<IFile> manifests = Stream.of(((IStructuredSelection) HandlerUtil.getCurrentSelection(event)).toArray())
				.map(this::getManifestFile)
				.filter(Objects::nonNull)
				.filter(IResource::isAccessible)
				.distinct()
				.collect(Collectors.toList());

		Map<IFile, IManifestEditor> editors = new HashMap<>();
		try {
			getEditors(manifests, editors);
		} catch (CoreException e) {
			throw new ExecutionException("Failed to open manifest(s) for editing", e);
		}

		IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindow(event);
		IWorkbench workbench = window.getWorkbench();
		IWorkbenchOperationSupport support = workbench.getOperationSupport();

		IUndoableOperation operation = createUpdateOperation(editors);
		IOperationHistory history = support.getOperationHistory();
		operation.addContext(support.getUndoContext());

		IProgressService progress = workbench.getProgressService();

		try {
			IRunnableWithProgress run = new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					try {
						history.execute(operation, monitor, null);

						// Save the manifests
						for (IManifestEditor next : editors.values()) {
							if (next.isDirty()) {
								next.save();
							}
						}
					} catch (ExecutionException e) {
						throw new InvocationTargetException(e);
					}

				}
			};

			progress.busyCursorWhile(new WorkspaceModifyDelegatingOperation(run));
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				Throwable wrapped = ((InvocationTargetException) e).getTargetException();
				if (wrapped instanceof ExecutionException) {
					throw ((ExecutionException) wrapped);
				}
			}
			throw new ExecutionException("Failed to execute manifest update operation", e);
		}

		return null;
	}

	private IFile getManifestFile(Object object) {
		IFile result = (object instanceof IFile) ? (IFile) object : ((IAdaptable) object).getAdapter(IFile.class);
		if (result == null) {
			IResource resource = (object instanceof IResource) ? (IResource) object : ((IAdaptable) object).getAdapter(IResource.class);
			if (resource != null) {
				result = resource.getProject().getFile(new Path("META-INF/MANIFEST.MF"));
			}
		}

		return result;
	}

	private void getEditors(Collection<? extends IFile> manifests, Map<? super IFile, ? super IManifestEditor> editors) throws CoreException {
		for (IFile next : manifests) {
			editors.put(next, ProjectEditors.getManifestEditor(next.getProject()));
		}
	}
}
